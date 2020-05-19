package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class TwoScenariesPerformanceTestingHU011 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9")
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
		.pause(12)
	}

  	object Login {
    	val login = exec(
      		http("Login")
        	.get("/login")
       	 	.headers(headers_2)
        	.check(css("input[name=_csrf]", "value").saveAs("stoken"))
   		 ).pause(20)
    	.exec(
     		 http("Logged")
        	.post("/login")
      	  	.headers(headers_3)
        	.formParam("username", "administrative1")
        	.formParam("password", "administrative1")        
        	.formParam("_csrf", "${stoken}")
   		 ).pause(142)
  	}
	
	object ListPatients{
		val listPatients = exec(http("ListPatients")
			.get("/administrative/patients")
			.headers(headers_0))
		.pause(9)
	}

	object TablaCitas {
		val tablaCitas = exec(http("TablaCitas")
			.get("/administrative/patients/4/appointments/table")
			.headers(headers_0))
		.pause(8)
	}

	object CrearCitaPrioridadAlta{
		val crearCitaPrioridadAlta = exec(http("CrearCitaPrioridadAlta")
			.get("/administrative/patients/4/appointments/new?fecha=2020-05-19T09:07")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
			)
		.pause(17)
	
		.exec(http("CrearCitaPrioridadAlta")
			.post("/administrative/patients/4/appointments/save")
			.headers(headers_3)
			.formParam("startTime", "2020-05-19T09:07")
			.formParam("endTime", "2020-05-19T09:14")
			.formParam("priority", "true")
			.formParam("_csrf", "${stoken}")
			)
		.pause(17)
	}


	object CrearCitaPrioridadBaja{
		val crearCitaPrioridadBaja = exec(http("CrearCitaPrioridadBaja")
			.get("/administrative/patients/5/appointments/new?fecha=2020-05-19T09:00")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
			)
		.pause(11)

		.exec(http("CrearCitaPrioridadBaja")
			.post("/administrative/patients/5/appointments/save")
			.headers(headers_3)
			.formParam("startTime", "2020-05-19T09:00")
			.formParam("endTime", "2020-05-19T09:07")
			.formParam("priority", "false")
			.formParam("_csrf", "${stoken}")
			)
		.pause(14)
	}

	val prioridadAltaScn = scenario("Cita Prioridad Alta").exec(
		Home.home,
		Login.login,
		ListPatients.listPatients,
		TablaCitas.tablaCitas,
		CrearCitaPrioridadAlta.crearCitaPrioridadAlta
	)

	val prioridadBajaScn = scenario("Cita Prioridad Baja").exec(
		Home.home,
		Login.login,
		ListPatients.listPatients,
		TablaCitas.tablaCitas,
		CrearCitaPrioridadBaja.crearCitaPrioridadBaja
	)

	setUp(
		prioridadAltaScn.inject(rampUsers(10000) during (100 seconds)),
		prioridadBajaScn.inject(rampUsers(10000) during (100 seconds))
	)
	.protocols(httpProtocol)
	.assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}