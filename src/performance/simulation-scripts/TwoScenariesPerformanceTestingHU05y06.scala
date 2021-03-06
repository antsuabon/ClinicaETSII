
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class TwoScenariesPerformanceTestingHU05y06 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es,es-ES;q=0.8,en-US;q=0.5,en;q=0.3")
		.upgradeInsecureRequestsHeader("1")
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
		.pause(5)
	}
	
	object Login {
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(10)
	}
	
	object Logged {
		val logged = exec(http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "patient1")
			.formParam("password", "patient1")
			.formParam("_csrf", "${stoken}"))
		.pause(9)
	}
	
	object PatientForm {
		val patientForm = exec(http("PatientForm")
			.get("/patient")
			.headers(headers_0))
		.pause(7)
	}
	
	object EditPatientForm {
		val editPatientForm = exec(http("EditPatientForm")
			.get("/patient/edit")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(9)
	}
	
	object PatientEdited {
		val patientEdited = exec(http("PatientEdited")
			.post("/patient/edit")
			.headers(headers_3)
			.formParam("patient.name", "Alejandro")
			.formParam("patient.surname", "Sánchez Saavedra")
			.formParam("patient.birthDate", "22/02/1982")
			.formParam("patient.address", "C/Calle de ejemplo")
			.formParam("patient.state", "Sevilla")
			.formParam("patient.nss", "12345678911")
			.formParam("patient.dni", "12345678N")
			.formParam("patient.email", "alejandro@gmail.com")
			.formParam("patient.phone", "956784225")
			.formParam("patient.phone2", "953333333")
			.formParam("patient.generalPractitioner", "11")
			.formParam("_csrf", "${stoken}"))
		.pause(5)
	}


	val showPatientDetails = scenario("ShowPatient").exec(Home.home, Login.login, Logged.logged, PatientForm.patientForm)
	val editPatientDetails = scenario("EditPatient").exec(Home.home, Login.login, Logged.logged, PatientForm.patientForm, EditPatientForm.editPatientForm, PatientEdited.patientEdited)

	setUp(
		showPatientDetails.inject(rampUsers(40) during (30 seconds)),
		editPatientDetails.inject(rampUsers(40) during (30 seconds))
			)
		.protocols(httpProtocol)
}