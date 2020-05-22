
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class TwoScenariesPerformanceTestingHU020 extends Simulation {

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
		.pause(6)
	}
	
	object Login {
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_2")
			.get("/login")
			.headers(headers_2)))
		.pause(10)
	}
	
	object Logged {
		val logged = exec(http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "admin")
			.formParam("password", "admin")
			.formParam("_csrf", "583065bb-2a66-4949-b17a-147887c9a3e1"))
		.pause(128)
	}
	
	object DoctorList {
		val doctorList = exec(http("DoctorList")
			.get("/admin/doctors")
			.headers(headers_0))
		.pause(37)
	}
	
	object ShowDoctor {
		val showDoctor = exec(http("ShowDoctor")
			.get("/admin/doctors/7")
			.headers(headers_0))
		.pause(16)
	}
	
	object DeleteDoctor {
		val deleteDoctor = exec(http("DeleteDoctor")
			.get("/admin/doctors/7/delete")
			.headers(headers_0))
		.pause(15)
	}


	val listDoctors = scenario("ListDoctors").exec(Home.home, Login.login, Logged.logged, DoctorList.doctorList)
	val deleteADoctor = scenario("DeleteADoctor").exec(Home.home, Login.login, Logged.logged, DoctorList.doctorList, ShowDoctor.showDoctor, DeleteDoctor.deleteDoctor)

	setUp(
		listDoctors.inject(rampUsers(5800) during (100 seconds)),
		deleteADoctor.inject(rampUsers(5800) during (100 seconds))
			)
		.protocols(httpProtocol)
}