package clinicaetsii

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class TwoScenariesPerformanceTestingHU022 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png""", """.*.jpg"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es,es-ES;q=0.8,en-US;q=0.5,en;q=0.3")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")

	val headers_2 = Map("Origin" -> "http://www.dp2.com")

	object Home {
		val home = exec(http("Home")
			.get("/"))
		.pause(18)
	}

	object Login {
		val login = exec(http("LoginForm")
			.get("/login")
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(13)
		.exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "admin")
			.formParam("password", "admin")
			.formParam("_csrf", "${stoken}"))
		.pause(12)
	}

	object PatientList {
		val patientList = exec(http("PatientsList")
			.get("/admin/patients"))
		.pause(31)
	}

	object ShowDeletablePatient {
		val showDeletablePatient = exec(http("ShowPatient")
			.get("/admin/patients/10"))
		.pause(17)
	}

	object ShowNotDeletablePatient {
		val showNotDeletablePatient = exec(http("ShowPatient")
			.get("/admin/patients/4"))
		.pause(17)
	}

	object DeleteDeletablePatient {
		val deleteDeletablePatient = exec(http("DeletePatient")
			.get("/admin/patients/10/delete"))
		.pause(15)
	}

	object DeleteNotDeletablePatient {
		val deleteNotDeletablePatient = exec(http("DeletePatient")
			.get("/admin/patients/4/delete"))
		.pause(15)
	}

	val scn1 = scenario("DeletePatient").exec(
		Home.home,
		Login.login,
		PatientList.patientList,
		ShowDeletablePatient.showDeletablePatient,
		DeleteDeletablePatient.deleteDeletablePatient
	)

	val scn2 = scenario("DontDeletePatient").exec(
		Home.home,
		Login.login,
		PatientList.patientList,
		ShowNotDeletablePatient.showNotDeletablePatient,
		DeleteNotDeletablePatient.deleteNotDeletablePatient
	)

	setUp(
		scn1.inject(rampUsers(2000) during (100 seconds)),
		scn2.inject(rampUsers(2000) during (100 seconds)))
	.protocols(httpProtocol)
	.assertions(
		global.responseTime.max.lt(5000),
		global.responseTime.min.lt(1000),
		global.successfulRequests.percent.gt(95)
	)
}