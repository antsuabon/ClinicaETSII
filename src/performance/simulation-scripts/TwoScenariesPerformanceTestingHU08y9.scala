package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class TwoScenariesPerformanceTestingHU08y9 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
		.acceptEncodingHeader("gzip, deflate")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Proxy-Connection" -> "keep-alive")

	val headers_3 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_11 = Map(
		"A-IM" -> "x-bm,gzip",
		"Proxy-Connection" -> "keep-alive")

    val uri1 = "http://clientservices.googleapis.com/chrome-variations/seed"






	object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(12)
	}

  	object LoginPatient1 {
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
       		 .formParam("username", "patient1")
        	 .formParam("password", "patient1")        
            .formParam("_csrf", "${stoken}")
   		).pause(142)
  }

  	object LoginPatient2 {
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
       		 .formParam("username", "patient2")
        	 .formParam("password", "patient2")        
            .formParam("_csrf", "${stoken}")
   		).pause(142)
  	}

	object TablaCitas{
		val tablaCitas =  exec(
					http("tablaCitas")
					.get("/patient/appointments/table")
					.headers(headers_0)
			)
		.pause(41)
	}

	object CrearCita1{
		val crearCita1 = exec(
			http("CrearCita")
			.get("/patient/appointments/new?fecha=2020-05-19T09%3A00")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
			)
		.pause(48)
		.exec(http("CrearCita")
			.post("/patient/appointments/save")
			.headers(headers_3)
			.formParam("startTime", "2020-05-19T09:00")
			.formParam("endTime", "2020-05-19T09:07")
			.formParam("_csrf", "${stoken}")
			)
		.pause(37)
	}

	object CrearCita2{
		val crearCita2 =  exec(
			http("TwoScenariesPerformanceTestingHU08y9_14")
			.get("/patient/appointments/new?fecha=2020-05-19T10%3A03")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
			)
		.pause(48)
		.exec(
			http("CrearCita")
			.post("/patient/appointments/save")
			.headers(headers_3)
			.formParam("startTime", "2020-05-19T10:03")
			.formParam("endTime", "2020-05-19T10:10")
			.formParam("_csrf", "${stoken}")
			)
		.pause(37)

	}

	val pacient1Scn = scenario("Patient1").exec(
		Home.home,
		LoginPatient1.login,
		TablaCitas.tablaCitas,
		CrearCita1.crearCita1
	)

	val pacient2Scn = scenario("Patient2").exec(
		Home.home,
		LoginPatient2.login,
		TablaCitas.tablaCitas,
		CrearCita2.crearCita2
	)

	setUp(
		pacient1Scn.inject(rampUsers(10000) during (100 seconds)),
		pacient2Scn.inject(rampUsers(10000) during (100 seconds))
		)
		.protocols(httpProtocol)
		.assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}