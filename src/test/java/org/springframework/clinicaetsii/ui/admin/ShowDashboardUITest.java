package org.springframework.clinicaetsii.ui.admin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import java.util.concurrent.TimeUnit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class ShowDashboardUITest {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	private String username;

	@BeforeEach
	public void setUp() throws Exception {
		String pathToGeckoDriver = "D:\\geckodriver";
		System.setProperty("webdriver.gecko.driver", pathToGeckoDriver + "\\geckodriver.exe");
		this.driver = new FirefoxDriver();

		this.baseUrl = "https://www.google.com/";
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		this.driver.get("http://localhost:" + this.port);
	}

	private ShowDashboardUITest as(final String username, final String password) {
		this.username = username;

		this.driver.findElement(By.xpath("//a[contains(text(),'Iniciar sesión')]")).click();

		this.driver.findElement(By.id("username")).click();
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys(username);

		this.driver.findElement(By.id("password")).click();
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys(password);

		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		return this;
	}

	private ShowDashboardUITest whenIamLoggedInTheSystem() {
		return this;
	}

	private ShowDashboardUITest thenISeeMyUsernameInTheMenuBar() {
		assertEquals(this.username.toUpperCase(),
				this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).getText());
		return this;
	}

	private ShowDashboardUITest thenISeeMyRoleDropDownMenu() {
		assertEquals("Administrador".toUpperCase(),
				this.driver.findElement(By.xpath("(//a[contains(@href, '#')])[2]")).getText());
		return this;
	}


	private ShowDashboardUITest thenIEnterDashboard() {

		this.driver.findElement(By.xpath("(//a[contains(@href, '#')])[2]")).click();

		assertEquals("Dashboard".toUpperCase(), this.driver
				.findElement(By.xpath("//a[contains(@href, '/admin/dashboard')]")).getText());
		this.driver.findElement(By.xpath("//a[contains(@href, '/admin/dashboard')]")).click();

		return this;
	}


	private ShowDashboardUITest thenIShowDashboard() {

		assertEquals("Dashboard", this.driver.findElement(By.xpath("//h2")).getText());
		assertEquals("Número medio de prescripciones por médico",
				this.driver.findElement(By.xpath("//th")).getText());
		assertEquals("Tiempo de espera medio",
				this.driver.findElement(By.xpath("//tr[2]/th")).getText());
		assertEquals("Edad media de los pacientes",
				this.driver.findElement(By.xpath("//tr[4]/th")).getText());



		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}
			try {
				if (isElementPresent(By.id("mostFrequentDiagnoses"))) {
					break;
				}
			} catch (Exception e) {
			}
		}

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}
			try {
				if (isElementPresent(By.id("numberOfConsultationsByDischargeType"))) {
					break;
				}
			} catch (Exception e) {
			}
		}

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}
			try {
				if (isElementPresent(By.id("mostFrequestMedicines"))) {
					break;
				}
			} catch (Exception e) {
			}
		}

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}
			try {
				if (isElementPresent(By.id("ratioServicesPatients"))) {
					break;
				}
			} catch (Exception e) {
			}
		}

		Assertions.assertThat(this.driver.findElement(By.xpath("//b")).getText()).isNotEmpty();
		Assertions
				.assertThat(
						this.driver.findElement(By.xpath("//table[2]/tbody/tr[2]/td")).getText())
				.isNotEmpty();
		Assertions.assertThat(this.driver.findElement(By.xpath("//tr[3]/td")).getText())
				.isNotEmpty();
		Assertions.assertThat(this.driver.findElement(By.xpath("//tr[4]/td")).getText())
				.isNotEmpty();

		return this;
	}

	@Test
	public void testShowDashboardUI() throws Exception {

		as("admin", "admin").whenIamLoggedInTheSystem().thenISeeMyUsernameInTheMenuBar()
				.thenISeeMyRoleDropDownMenu().thenIEnterDashboard().thenIShowDashboard();
	}


	@AfterEach
	public void tearDown() throws Exception {
		this.driver.quit();
		String verificationErrorString = this.verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			Assertions.fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(final By by) {
		try {
			this.driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			this.driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = this.driver.switchTo().alert();
			String alertText = alert.getText();
			if (this.acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			this.acceptNextAlert = true;
		}
	}

}
