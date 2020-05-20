package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class TwoScenariesPerformanceTestingHU03 extends Simulation {

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

	val headers_3 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_4 = Map(
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
        .headers(headers_3)
        .check(css("input[name=_csrf]", "value").saveAs("stoken"))
    ).pause(20)
    .exec(
      http("Logged")
        .post("/login")
        .headers(headers_4)
        .formParam("username", "doctor1")
        .formParam("password", "doctor1")        
        .formParam("_csrf", "${stoken}")
    ).pause(11)
 	}

	object ListMedicinesAnonymous {
		val listMedicinesAnonymous = exec(http("ListMedicinesAnonymous")
							.get("/anonymous/medicines")
							.headers(headers_0)
			)
		.pause(29)
	}

	object ListMedicinesDoctor {
		val listMedicinesDoctor = exec(http("ListMedicinesDoctor")
								.get("/anonymous/medicines")
								.headers(headers_0)
			)
		.pause(22)
	}


	val anonymousScn = scenario("Anonymous").exec(
		Home.home,
		ListMedicinesAnonymous.listMedicinesAnonymous
	)

	val doctorScn = scenario("Doctor").exec(
		Home.home,
		Login.login,
		ListMedicinesDoctor.listMedicinesDoctor
	)
	

	setUp(
		anonymousScn.inject(rampUsers(10000) during (100 seconds)),
		doctorScn.inject(rampUsers(10000) during (100 seconds))
	)
	.protocols(httpProtocol)
	.assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}