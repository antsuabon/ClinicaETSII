package clinicaetsii

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class TwoScenariesPerformanceTestingHU013 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png""", """.*.jpg"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es,es-ES;q=0.8,en-US;q=0.5,en;q=0.3")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")

	val headers_0 = Map(
		"Proxy-Connection" -> "keep-alive", 
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map("Accept" -> "image/webp,*/*")

	val headers_3 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")


	object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(9)
	}

	object LoginForm1 {
		val loginForm1 = exec(http("LoginForm")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(9)
		.exec(http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "doctor1")
			.formParam("password", "doctor1")
			.formParam("_csrf", "${stoken}"))
		.pause(9)
	}

	object LoginForm2 {
		val loginForm2 = exec(http("LoginForm")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(9)
		.exec(http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "doctor2")
			.formParam("password", "doctor2")
			.formParam("_csrf", "${stoken}"))
		.pause(9)
	}

	object ShowProfile {
		val showProfile = exec(http("ShowProfile")
			.get("/doctor")
			.headers(headers_0))
		.pause(9)
	}

	object ShowUpdatedSimpleProfile {
		val showUpdatedSimpleProfile = exec(http("UpdateProfileForm")
			.get("/doctor/edit")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(9)
		.exec(http("ShowUpdatedProfile")
			.post("/doctor/edit")
			.headers(headers_3)
			.formParam("doctor.name", "Pablo Editado")
			.formParam("doctor.surname", "Rodriguez Garrido Editado")
			.formParam("doctor.dni", "12333378N")
			.formParam("doctor.email", "paao@gmail.com")
			.formParam("doctor.phone", "956722325")
			.formParam("doctor.username", "doctor1")
			.formParam("newPassword", "")
			.formParam("repeatPassword", "")
			.formParam("doctor.collegiateCode", "303012245")
			.formParam("doctor.services", "2")
			.formParam("doctor.services", "4")
			.formParam("doctor.services", "7")
			.formParam("_doctor.services", "1")
			.formParam("_csrf", "${stoken}"))
		.pause(9)
	}

	object UpdateFullProfile {
		val updateFullProfile = exec(http("UpdateProfileForm")
			.get("/doctor/edit")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(9)
		.exec(http("UpdatedProfile")
			.post("/doctor/edit")
			.headers(headers_3)
			.formParam("doctor.name", "Pablo Editado")
			.formParam("doctor.surname", "Rodriguez Garrido Editado")
			.formParam("doctor.dni", "12333678N")
			.formParam("doctor.email", "paao@gmail.com")
			.formParam("doctor.phone", "956784322")
			.formParam("doctor.username", "doctor2Nuevo")
			.formParam("newPassword", "aaaaaaA1")
			.formParam("repeatPassword", "aaaaaaA1")
			.formParam("doctor.collegiateCode", "303012333")
			.formParam("doctor.services", "2")
			.formParam("doctor.services", "4")
			.formParam("doctor.services", "6")
			.formParam("_doctor.services", "1")
			.formParam("_csrf", "${stoken}")
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(9)
		.exec(http("LoggedOut")
			.post("/logout")
			.headers(headers_3)
			.formParam("_csrf", "${stoken}"))
		.pause(9)
	}


	val scn1 = scenario("UpdateSimpleDoctorProfile").exec(Home.home,
														LoginForm1.loginForm1,
														ShowProfile.showProfile,
														ShowUpdatedSimpleProfile.showUpdatedSimpleProfile)

	val scn2 = scenario("UpdateFullDoctorProfile").exec(Home.home,
														LoginForm2.loginForm2,
														ShowProfile.showProfile,
														UpdateFullProfile.updateFullProfile)

	setUp(
		scn1.inject(rampUsers(5000) during (100 seconds)), 
		scn2.inject(rampUsers(5000) during (100 seconds)))
	.protocols(httpProtocol)
	.assertions(
		global.responseTime.max.lt(5000),
		global.responseTime.min.lt(1000),
		global.successfulRequests.percent.gt(95)
	)

}