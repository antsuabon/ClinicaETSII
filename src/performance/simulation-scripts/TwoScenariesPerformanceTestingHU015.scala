
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class TwoScenariesPerformanceTestingHU015 extends Simulation {

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
		.pause(7)
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
			.formParam("username", "doctor2")
			.formParam("password", "doctor2")
			.formParam("_csrf", "4bbdf38b-1c3a-495b-9a87-a3b984bc828f"))
		.pause(22)
	}
	
	object PatientList {
		val patientList = exec(http("PatientList")
			.get("/doctor/patients")
			.headers(headers_0))
		.pause(6)
	}
	
	object ExaminationList {
		val examinationList = exec(http("ExaminationList")
			.get("/doctor/patients/5/consultations")
			.headers(headers_0))
		.pause(3)
		.exec(http("ExaminationListAux")
			.get("/doctor/patients/5/consultations/3")
			.headers(headers_0))
		.pause(28)
	}
	
	object NewExamination {
		val newExamination = exec(http("NewExamination")
			.get("/doctor/patients/5/consultations/3/examinations/new")
			.headers(headers_0))
		.pause(23)
	}
	
	object ExaminationAdded {
		val examinationAdded = exec(http("ExaminationAdded")
			.post("/doctor/patients/5/consultations/3/examinations/new")
			.headers(headers_3)
			.formParam("description", "Some description")
			.formParam("_csrf", "65645060-5149-4dee-9ceb-aeef8b9be010"))
		.pause(9)
	}



	val listExaminations = scenario("ListExaminations").exec(Home.home, Login.login, Logged.logged, PatientList.patientList, ExaminationList.examinationList)
	val newExamination = scenario("NewExamination").exec(Home.home, Login.login, Logged.logged, PatientList.patientList, ExaminationList.examinationList, NewExamination.newExamination, ExaminationAdded.examinationAdded)

	setUp(
		listExaminations.inject(rampUsers(5500) during (100 seconds)),
		newExamination.inject(rampUsers(5500) during (100 seconds))
			)
		.protocols(httpProtocol)
}