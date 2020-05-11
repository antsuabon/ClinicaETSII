package org.springframework.clinicaetsii.ui.patient;

import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChangeGeneralPractitionerUITest {

	@LocalServerPort
	private int port;
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@BeforeEach
	public void setUp() throws Exception {
		String pathToGeckoDriver = "D:\\geckodriver";
		System.setProperty("webdriver.gecko.driver", pathToGeckoDriver + "\\geckodriver.exe");
		this.driver = new FirefoxDriver();

		this.baseUrl = "https://www.google.com/";
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}


	private ChangeGeneralPractitionerUITest as(final String username, final String password) {
		this.driver.get("http://localhost:" + this.port);

		this.driver.findElement(By.xpath("//a[contains(text(),'Iniciar sesión')]")).click();
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys(username);
		this.driver.findElement(By.id("password")).click();
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys(password);
		this.driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
		return this;
	}

	private ChangeGeneralPractitionerUITest whenIamLoggedInTheSystem() {
		return this;
	}

	private ChangeGeneralPractitionerUITest thenCheckImLoggedInAsPatient() {
		Assert.assertEquals("PATIENT1", this.driver
				.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).getText());
		return this;
	}

	private ChangeGeneralPractitionerUITest thenIChangeGeneralPractitioner() {
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).click();
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/ul/li[3]/a/span[2]"))
				.click();
		this.driver.findElement(By.xpath("//a[contains(text(),'Editar Paciente')]")).click();
		this.driver.findElement(By.id("patient.generalPractitioner")).click();
		new Select(this.driver.findElement(By.id("patient.generalPractitioner")))
				.selectByVisibleText("Laso Escot, María");
		this.driver.findElement(By.xpath("//option[@value='2']")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		return this;
	}

	private ChangeGeneralPractitionerUITest thenICheckTheGeneralPractitionerHasChanged() {
		Assert.assertEquals("Laso Escot, María",
				this.driver.findElement(By.xpath("//tr[10]/td")).getText());
		return this;

	}


	@Test
	public void shouldChangeGeneralPractitioner() throws Exception {

		as("patient1", "patient1").whenIamLoggedInTheSystem().thenCheckImLoggedInAsPatient()
				.thenIChangeGeneralPractitioner().thenICheckTheGeneralPractitionerHasChanged();



	}


	@AfterEach
	public void tearDown() throws Exception {
		this.driver.quit();
		String verificationErrorString = this.verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			Assert.fail(verificationErrorString);
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
