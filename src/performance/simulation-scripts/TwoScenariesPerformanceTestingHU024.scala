package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class TwoScenariesPerformanceTestingHU024 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

	val headers_0 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_3 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(12)
	}

 	object Login {
    val login = exec(
      http("Login")
        .get("/login")
        .headers(headers_2)
        .check(css("input[name=_csrf]", "value").saveAs("stoken"))
    ).pause(20)
    .exec(
      http("Logged")
        .post("/login")
        .headers(headers_3)
        .formParam("username", "admin1")
        .formParam("password", "4dm1n")        
        .formParam("_csrf", "${stoken}")
    ).pause(142)
  }
	object ListMedicines{
		val listMedicines = exec(http("ListMedicines")
			.get("/administrative/medicines")
			.headers(headers_0))
		.pause(9)
	}

	object MedicineDetails{
		val medicineDetails = exec(http("MedicineDetails")
			.get("/administrative/medicines/1")
			.headers(headers_0))
		.pause(9)
	}

	object EditMedicine{
		val editMedicine = exec(http("EditMedicineForm")
			.get("/administrative/medicines/1/edit")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
			)
		.pause(11)
		.exec(http("EditMedicine")
			.post("/administrative/medicines/1/edit")
			.headers(headers_3)
			.formParam("id", "1")
			.formParam("genericalName", "IbuprofenoEditado")
			.formParam("commercialName", "Dalsy")
			.formParam("quantity", "1.0")
			.formParam("indications", "Dolor leve y moderado")
			.formParam("contraindications", "En síndrome de pólipos nasales, angioedema y reactividad broncoespástica a aspirina u otros AINEs.")
			.formParam("_csrf", "${stoken}"))
		.pause(8)
	}

	object DeleteMedicine{
		val deleteMedicine = exec(http("DeleteMedicine")
			.get("/administrative/medicines/1/delete")
			.headers(headers_0))
		.pause(28)
	}


	val editMedicineScn = scenario("Editar Medicina").exec(
		Home.home,
		Login.login,
		ListMedicines.listMedicines,
		MedicineDetails.medicineDetails,
		EditMedicine.editMedicine
	)

	val deleteMedicineScn = scenario("Borrar Medicina").exec(
		Home.home,
		Login.login,
		ListMedicines.listMedicines,
		MedicineDetails.medicineDetails,
		DeleteMedicine.deleteMedicine
	)

	setUp(
		editMedicineScn.inject(rampUsers(10000) during (100 seconds)),
		deleteMedicineScn.inject(rampUsers(10000) during (100 seconds))
	)
	.protocols(httpProtocol)
	.assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}