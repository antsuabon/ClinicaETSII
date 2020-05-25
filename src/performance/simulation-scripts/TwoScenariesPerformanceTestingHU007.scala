package clinicaetsii

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class TwoScenariesPerformanceTestingHU007 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.js""", """.*.css""", """.*.png""", """.*.jpg""", """.*.ico"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")

	val headers_0 = Map("Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Origin" -> "http://www.dp2.com",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_3 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_5 = Map("Accept" -> "image/webp,*/*")

	object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(10)
	}

	object Login {
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(11)
	}

	object Logged1 {
		val logged1 = exec(http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "patient1")
			.formParam("password", "patient1")
			.formParam("_csrf", "${stoken}"))
		.pause(13)
	}

	object Logged2 {
		val logged2 = exec(http("Logged2")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "patient2")
			.formParam("password", "patient2")
			.formParam("_csrf", "${stoken}"))
		.pause(10)
	}

	object ListarCitas {
		val listarCitas = exec(http("ListarCitas")
			.get("/patient/appointments")
			.headers(headers_0))
		.pause(19)
	}
		
	val scn1 = scenario("ListarCitas1").exec(Home.home,
												Login.login,
												Logged1.logged1,
												ListarCitas.listarCitas)

	val scn2 = scenario("ListarCitas2").exec(Home.home,
												Login.login,
												Logged2.logged2,
												ListarCitas.listarCitas)		


	setUp(
		scn1.inject(rampUsers(10000) during (100 seconds)),
		scn2.inject(rampUsers(10000) during (100 seconds))
		)
		.protocols(httpProtocol)
		.assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )

}