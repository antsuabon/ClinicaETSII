package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class TwoScenariesPerformanceTestingHU019 extends Simulation {

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
		"A-IM" -> "x-bm,gzip",
		"Proxy-Connection" -> "keep-alive")

	val headers_4 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

    val uri1 = "http://clientservices.googleapis.com/chrome-variations/seed"



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
        .headers(headers_4)
        .formParam("username", "admin")
        .formParam("password", "admin")        
        .formParam("_csrf", "${stoken}")
   		 ).pause(142)
 	
	}

	object ListDoctors{
		val listDoctors = exec(http("ListDoctors")
			.get("/admin/doctors")
			.headers(headers_0))
		.pause(9)
	}

	object DoctorDetails1 {
		val doctorDetails1 = exec(http("DoctorDetails1")
			.get("/admin/doctors/3")
			.headers(headers_0))
		.pause(7)
	}

	object DoctorDetails2 {
		val doctorDetails2 = exec(http("DoctorDetails2")
			.get("/admin/doctors/1")
			.headers(headers_0))
		.pause(7)
	}

	val details1Scn = scenario("Doctor Details 1").exec(
		Home.home,
		Login.login,
		ListDoctors.listDoctors,
		DoctorDetails1.doctorDetails1
	)

	val details2Scn = scenario("Doctor Details 2").exec(
		Home.home,
		Login.login,
		ListDoctors.listDoctors,
		DoctorDetails2.doctorDetails2
	)

	setUp(
		details1Scn.inject(rampUsers(10000) during (100 seconds)),
		details2Scn.inject(rampUsers(10000) during (100 seconds))
	)
	.protocols(httpProtocol)
	.assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}