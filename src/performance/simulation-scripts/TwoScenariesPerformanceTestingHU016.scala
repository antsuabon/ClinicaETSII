package clinicaetsii

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class TwoScenariesPerformanceTestingHU016 extends Simulation {

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
		.pause(9)
	}

	object Login {
		val login = exec(http("LoginForm")
			.get("/login")
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(17)
		.exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "doctor1")
			.formParam("password", "doctor1")
			.formParam("_csrf", "${stoken}"))
		.pause(36)
	}

	object AppointmentsList {
		val appointmentsList = exec(http("AppointmentsList")
			.get("/doctor/appointments"))
		.pause(16)
	}

	object NewConsultation {
		val newConsultation = exec(http("NewConsultationForm")
			.get("/doctor/patients/4/consultations/new?appointmentId=4")
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(38)
		.exec(http("ShowNewConsultation")
			.post("/doctor/patients/4/consultations/new?appointmentId=4")
			.headers(headers_2)
			.formParam("id", "")
			.formParam("appointmentId", "4")
			.formParam("startTime", "20/05/2020 13:14")
			.formParam("anamnesis", "Anamnesis de prueba")
			.formParam("remarks", "Observaciones de prueba")
			.formParam("_csrf", "${stoken}"))
		.pause(24)
	}

	object NewExamination {
		val newExamination = exec(http("NewExaminationForm")
			.get("/doctor/patients/4/consultations/4/examinations/new")
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(25)
		.exec(http("AddedExamination")
			.post("/doctor/patients/4/consultations/4/examinations/new")
			.headers(headers_2)
			.formParam("description", "Exploración de prueba")
			.formParam("_csrf", "${stoken}"))
		.pause(57)
	}

	object NewConstant {
		val newConstant = exec(http("NewConstantForm")
			.get("/doctor/patients/4/consultations/4/constants/new")
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(25)
		.exec(http("AddedConstant")
			.post("/doctor/patients/4/consultations/4/constants/new")
			.headers(headers_2)
			.formParam("id", "")
			.formParam("consultationId", "4")
			.formParam("constantType", "5")
			.formParam("value", "37")
			.formParam("_csrf", "${stoken}"))
		.pause(15)
	}

	object UpdateConsultation {
		val updateConsultation = exec(http("UpdateConsultationForm")
			.get("/doctor/patients/4/consultations/4/edit")
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(25)
		.exec(http("ShowUpdatedConsultation")
			.post("/doctor/patients/4/consultations/4/edit")
			.headers(headers_2)
			.formParam("id", "4")
			.formParam("appointmentId", "4")
			.formParam("startTime", "20/05/2020 13:14")
			.formParam("anamnesis", "Anamnesis de prueba")
			.formParam("remarks", "Observaciones de prueba")
			.formParam("diagnoses", "1")
			.formParam("_diagnoses", "1")
			.formParam("dischargeType", "2")
			.formParam("_csrf", "${stoken}"))
		.pause(11)
	}

	val scn1 = scenario("NewFullConsultation").exec(
		Home.home,
		Login.login,
		AppointmentsList.appointmentsList,
		NewConsultation.newConsultation,
		NewExamination.newExamination,
		NewConstant.newConstant,
		UpdateConsultation.updateConsultation
	)

	val scn2 = scenario("IncompleteNewConsultation").exec(
		Home.home,
		Login.login,
		AppointmentsList.appointmentsList,
		NewConsultation.newConsultation,
		UpdateConsultation.updateConsultation
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