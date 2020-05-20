package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class TwoScenariesPerformanceTestingHU017 extends Simulation {

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
        	.formParam("username", "doctor1")
        	.formParam("password", "doctor1")        
        	.formParam("_csrf", "${stoken}")
   		 ).pause(142)
 	 }

	  object ListPatients {
		  val listPatients = exec(http("ListPatients")
			.get("/doctor/patients")
			.headers(headers_0))
		.pause(7)
	  }

	  object ListPatientsPrescriptions {
		  val listPatientsPrescriptions = exec(http("ListPatientsPrescriptions")
			.get("/doctor/patients/4/prescriptions")
			.headers(headers_0))
		.pause(16)
	  }

	  object PrescriptionDetails {
		  val prescriptionDetails = exec(http("PrescriptionDetails")
			.get("/doctor/patients/4/prescriptions/2")
			.headers(headers_0))
		.pause(12)
	  }

	  object DeletePrescription {
		  val deletePrescription = exec(http("DeletePrescription")
			.get("/doctor/patients/4/prescriptions/2/delete")
			.headers(headers_0))
		.pause(16)
	  }

	val prescriptionDetailsScn = scenario("Prescription Details").exec(
		Home.home,
		Login.login,
		ListPatients.listPatients,
		ListPatientsPrescriptions.listPatientsPrescriptions,
		PrescriptionDetails.prescriptionDetails
	)

	val deletePrescriptionScn = scenario("Delete Prescription").exec(
		Home.home,
		Login.login,
		ListPatients.listPatients,
		ListPatientsPrescriptions.listPatientsPrescriptions,
		PrescriptionDetails.prescriptionDetails,
		DeletePrescription.deletePrescription
	)

	setUp(
		prescriptionDetailsScn.inject(rampUsers(10000) during (100 seconds)),
		deletePrescriptionScn.inject(rampUsers(10000) during (100 seconds))
		)
	.protocols(httpProtocol)
	.assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}