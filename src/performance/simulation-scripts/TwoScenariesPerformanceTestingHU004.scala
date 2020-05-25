package clinicaetsii

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class TwoScenariesPerformanceTestingHU004 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.js""", """.*.css""", """.*.png""", """.*.jpg""", """.*.ico"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-GB,en;q=0.9,es-ES;q=0.8,es;q=0.7,en-US;q=0.6")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

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

	object Perfil {
		val perfil = exec(http("Perfil")
			.get("/patient")
			.headers(headers_0))
		.pause(16)
	}

	object EditarPaciente {
		val editarPaciente = exec(http("EditarPaciente")
			.get("/patient/edit")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(3)
	}

	object Editado1 {
		val editado1 = exec(http("Editado1")
			.post("/patient/edit")
			.headers(headers_3)
			.formParam("patient.name", "Ale")
			.formParam("patient.surname", "Sán Saa")
			.formParam("patient.birthDate", "21/02/1982")
			.formParam("patient.address", "C/Calle de ejempl")
			.formParam("patient.state", "Sevill")
			.formParam("patient.nss", "12345678912")
			.formParam("patient.dni", "12345678P")
			.formParam("patient.email", "alejandr@gmail.com")
			.formParam("patient.phone", "956784226")
			.formParam("patient.phone2", "953333336")
			.formParam("patient.generalPractitioner", "7")
			.formParam("_csrf", "${stoken}"))
		.pause(23)
	}
	
	object Editado2 {
		val editado2 = exec(http("Editado2")
			.post("/patient/edit")
			.headers(headers_3)
			.formParam("patient.name", "María")
			.formParam("patient.surname", "Laso Escot")
			.formParam("patient.birthDate", "06/06/1998")
			.formParam("patient.address", "C/Laso")
			.formParam("patient.state", "Utrera")
			.formParam("patient.nss", "12345678911")
			.formParam("patient.dni", "12345675N")
			.formParam("patient.email", "maria@gmail.com")
			.formParam("patient.phone", "956787225")
			.formParam("patient.phone2", "953334333")
			.formParam("patient.generalPractitioner", "2")
			.formParam("_csrf", "${stoken}"))
		.pause(30)
	}
	

	val scn1 = scenario("EditarPaciente1").exec(Home.home,
												Login.login,
												Logged1.logged1,
												Perfil.perfil,
												EditarPaciente.editarPaciente,
												Editado1.editado1)

	val scn2 = scenario("EditarPaciente2").exec(Home.home,
												Login.login,
												Logged2.logged2,
												Perfil.perfil,
												EditarPaciente.editarPaciente,
												Editado2.editado2)		


	setUp(
		scn1.inject(rampUsers(5000) during (100 seconds)),
		scn2.inject(rampUsers(5000) during (100 seconds))
		)
		.protocols(httpProtocol)
		.assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
	
	
}