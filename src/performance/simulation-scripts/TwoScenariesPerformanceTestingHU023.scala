
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class TwoScenariesPerformanceTestingHU023 extends Simulation {

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
		.pause(11)
	}
	
	object Login {
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_2")
			.get("/login")
			.headers(headers_2)))
		.pause(17)
	}
	
	object Logged {
		val logged = exec(http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "admin")
			.formParam("password", "admin")
			.formParam("_csrf", "26396e36-a26e-4622-8e67-d2429db20d0c"))
		.pause(50)
	}
	
	object AdministrativeList {
		val administrativeList = exec(http("AdministrativeList")
			.get("/admin/administratives")
			.headers(headers_0))
		.pause(15)
	}
	
	object AdministrativeShow {
		val administrativeShow = exec(http("AdministrativeShow")
			.get("/admin/administratives/104")
			.headers(headers_0))
		.pause(12)
	}
	
	object AdministrativeDelete {
		val administrativeDelete = exec(http("AdministrativeDelete")
			.get("/admin/administratives/104/delete")
			.headers(headers_0))
		.pause(11)
	}


	val showAdministrative = scenario("ShowAdministrative").exec(Home.home, Login.login, Logged.logged, AdministrativeList.administrativeList, AdministrativeShow.administrativeShow)
	val deleteAdministrative = scenario("DeleteAdministrative").exec(Home.home, Login.login, Logged.logged, AdministrativeList.administrativeList, AdministrativeShow.administrativeShow, AdministrativeDelete.administrativeDelete)

	setUp(
		showAdministrative.inject(rampUsers(4300) during (100 seconds)),
		deleteAdministrative.inject(rampUsers(4300) during (100 seconds))
			)
		.protocols(httpProtocol)
}