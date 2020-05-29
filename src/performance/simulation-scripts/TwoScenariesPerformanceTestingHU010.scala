
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class TwoScenariesPerformanceTestingHU010 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-US,en;q=0.9,es;q=0.8")
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
		.pause(8)
	}
	
	object Login {
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(20)
	}
	
	object Logged {
		val logged = exec(http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "patient1")
			.formParam("password", "patient1")
			.formParam("_csrf", "${stoken}"))
		.pause(62)
	}
	
	object ListPrescriptions {
		val listPrescriptions = exec(http("ListPrescriptions")
			.get("/patient/prescriptions")
			.headers(headers_0))
		.pause(11)
	}
	
	object ShowPrescriptions {
		val showPrescriptions = exec(http("ShowPrescriptions")
			.get("/patient/prescriptions/1")
			.headers(headers_0))
		.pause(12)
	}

	val listPatientPresc = scenario("ListPatientPrescriptions").exec(Home.home, Login.login, Logged.logged, ListPrescriptions.listPrescriptions)
	val showPatientPresc = scenario("ShowPatientPrescription").exec(Home.home, Login.login, Logged.logged, ListPrescriptions.listPrescriptions, ShowPrescriptions.showPrescriptions)

	setUp(
		listPatientPresc.inject(rampUsers(5000) during (100 seconds)),
		showPatientPresc.inject(rampUsers(5000) during (100 seconds))
			)
		.protocols(httpProtocol)
}