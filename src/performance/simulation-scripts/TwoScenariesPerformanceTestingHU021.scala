package clinicaetsii

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class TwoScenariesPerformanceTestingHU021 extends Simulation {

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

	object ShowPatient {
		val showPatient = exec(http("ShowPatient")
			.get("/admin/patients/10"))
		.pause(17)
	}

	val scn1 = scenario("ListPatients").exec(
		Home.home,
		Login.login,
		PatientList.patientList,
		ShowPatient.showPatient
	)

	val scn2 = scenario("ShowPatient").exec(
		Home.home,
		Login.login,
		PatientList.patientList,
		ShowPatient.showPatient
	)

	setUp(
		scn1.inject(rampUsers(5000) during (100 seconds)),
		scn2.inject(rampUsers(5000) during (100 seconds)))
	.protocols(httpProtocol)
	.assertions(
		global.responseTime.max.lt(5000),
		global.responseTime.mean.lt(1000),
		global.successfulRequests.percent.gt(95)
	)
}