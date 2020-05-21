package clinicaetsii

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class TwoScenariesPerformanceTestingHU012 extends Simulation {

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

	val headers_7 = Map("Accept" -> "image/webp,*/*")


	object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(10)
	}

	object Login {
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_0))
		.pause(11)
	}

	object Logged {
		val logged = exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "administrative1")
			.formParam("password", "administrative1")
			.formParam("_csrf", "191b7ee2-ead0-4b9a-a21d-0f0e467c5dd2"))
		.pause(22)
	}

	object CrearPaciente {
		val crearPaciente = exec(http("CrearPaciente")
			.get("/administrative/patients/new")
			.headers(headers_0))
		.pause(175)
	}

	object PacienteCreado {
		val pacienteCreado = exec(http("PacienteCreado")
			.post("/administrative/patients/new")
			.headers(headers_2)
			.formParam("username", "Perceus")
			.formParam("name", "Percy")
			.formParam("surname", "Jackson")
			.formParam("birthDate", "04/05/1998")
			.formParam("address", "C/ Olimpo")
			.formParam("state", "Sevilla")
			.formParam("nss", "12345678911")
			.formParam("dni", "12345678P")
			.formParam("email", "percy@gmail.com")
			.formParam("phone", "654321987")
			.formParam("phone2", "654987321")
			.formParam("generalPractitioner", "2")
			.formParam("_csrf", "369b7e2e-d617-4265-b9fe-86c015e7a206"))
		.pause(19)
	}
	
	val scn1 = scenario("CrearPaciente1").exec(Home.home,
												Login.login,
												Logged.logged,
												CrearPaciente.crearPaciente,
												PacienteCreado.pacienteCreado)

	val scn2 = scenario("CrearPaciente2").exec(Home.home,
												Login.login,
												Logged.logged,
												CrearPaciente.crearPaciente,
												PacienteCreado.pacienteCreado)		


	setUp(
		scn1.inject(rampUsers(2000) during (100 seconds)),
		scn2.inject(rampUsers(2000) during (100 seconds))
		)
		.protocols(httpProtocol)
		.assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
	) 
}