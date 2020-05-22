
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class TwoScenariesPerformanceTestingHU018 extends Simulation {

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
		.pause(4)
	}
	
	object Login {
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_2")
			.get("/login")
			.headers(headers_2)))
		.pause(13)
	}
	
	object Logged {
		val logged = exec(http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "doctor2")
			.formParam("password", "doctor2")
			.formParam("_csrf", "df65fe40-ba5d-40ba-8c2d-68ebb693a31e"))
		.pause(10)
	}
	
	object PrescriptionList {
		val prescriptionList = exec(http("PatientList")
			.get("/doctor/patients")
			.headers(headers_0))
		.pause(5)
		.exec(http("PrescriptionList")
			.get("/doctor/patients/5/prescriptions")
			.headers(headers_0))
		.pause(51)
	}
	
	object NewPrescription {
		val newPrescription = exec(http("NewPrescription")
			.get("/doctor/patients/5/prescriptions/new")
			.headers(headers_0))
		.pause(41)
	}
	
	object PrescriptionAdded {
		val prescriptionAdded = exec(http("PrescriptionAdded")
			.post("/doctor/patients/5/prescriptions/new")
			.headers(headers_3)
			.formParam("dosage", "5.0")
			.formParam("days", "3.0")
			.formParam("pharmaceuticalWarning", "Sin avisos")
			.formParam("patientWarning", "Un aviso")
			.formParam("medicine", "2")
			.formParam("_csrf", "a07949a4-3a7f-4cef-8e49-023882ad0d38"))
		.pause(11)
	}


	val listPatientPrescriptions = scenario("ListPatientPrescriptions").exec(Home.home, Login.login, Logged.logged, PrescriptionList.prescriptionList)
	val newPatientPrescription = scenario("NewPatientPrescription").exec(Home.home, Login.login, Logged.logged, PrescriptionList.prescriptionList, NewPrescription.newPrescription, PrescriptionAdded.prescriptionAdded)

	setUp(
		listPatientPrescriptions.inject(rampUsers(5000) during (100 seconds)),
		newPatientPrescription.inject(rampUsers(5000) during (100 seconds))
			)
		.protocols(httpProtocol)
}