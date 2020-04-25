
package org.springframework.clinicaetsii.ui.doctor;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ListMyPatientsConsultsUITest {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	private String username;


	@BeforeEach
	public void setUp() throws Exception {
		 String pathToGeckoDriver="C:\\Users\\angel\\Downloads\\webdrivers";
		 System.setProperty("webdriver.gecko.driver", pathToGeckoDriver + "\\geckodriver.exe");
		 this.driver = new FirefoxDriver();

//		System.setProperty("webdriver.chrome.driver",
//				"D:\\Aplicaciones\\chromedriver_win32\\chromedriver.exe");
//		this.driver = new ChromeDriver();
		this.baseUrl = "https://www.google.com/";
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	private String passwordOf(final String username) {
		return username;
	}

	private ListMyPatientsConsultsUITest as(final String username) {
		this.username = username;

		this.driver.get("http://localhost:" + this.port);
		this.driver.findElement(By.xpath("//a[contains(text(),'Iniciar sesión')]")).click();

		this.driver.findElement(By.xpath("//input[@id='username']")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).clear();
		this.driver.findElement(By.xpath("//input[@id='username']")).sendKeys(username);

		this.driver.findElement(By.xpath("//input[@id='password']")).click();
		this.driver.findElement(By.xpath("//input[@id='password']")).clear();
		this.driver.findElement(By.xpath("//input[@id='password']")).sendKeys(this.passwordOf(username));

		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		return this;
	}

	private ListMyPatientsConsultsUITest thenIListMyPatients() {
		this.driver.findElement(By.xpath("//a[contains(text(),'Médico')]")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/doctor/patients')]")).click();
		Assertions.assertEquals("Lista de Pacientes",
				this.driver.findElement(By.xpath("//h2")).getText());
		return this;
	}

	private ListMyPatientsConsultsUITest thenIListAPatientConsultations() {
		Assertions.assertEquals("Sánchez Saavedra, Alejandro", this.driver
			.findElement(By.xpath("//table[@id='patientsTable']/tbody/tr/td")).getText());
	this.driver.findElement(By.xpath("//a[contains(text(),'Consultas del paciente')]")).click();
	Assertions.assertEquals("Lista de Consultas",
			this.driver.findElement(By.xpath("//h2")).getText());
	return this;
	}

	private ListMyPatientsConsultsUITest thenIShowAPatientConsultation() {
		this.driver.findElement(By.xpath("//a[contains(text(),'Ver consulta')]")).click();
		Assertions.assertEquals("Detalles Consulta",
				this.driver.findElement(By.xpath("//h2")).getText());
		Assertions.assertEquals("Diagnósticos",
				this.driver.findElement(By.xpath("//h3")).getText());
		Assertions.assertEquals("Exploraciones",
				this.driver.findElement(By.xpath("//h3[2]")).getText());
		Assertions.assertEquals("Constantes",
				this.driver.findElement(By.xpath("//h3[3]")).getText());
		Assertions.assertEquals("07/03/2020 11:00",
				this.driver.findElement(By.xpath("//td")).getText());

		return this;
	}

	@Test
	public void positiveTestIU014() throws Exception {

		this.as("doctor1").thenIListMyPatients().thenIListAPatientConsultations()
		.thenIShowAPatientConsultation();

	}

	private ListMyPatientsConsultsUITest thenINotListMyPatients() {
		this.driver.findElement(By.xpath("//a[contains(text(),'Médico')]")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/doctor/patients')]")).click();
		Assertions.assertNotEquals("Lista diferente",
				this.driver.findElement(By.xpath("//h2")).getText());
		return this;
	}

	private ListMyPatientsConsultsUITest thenINotListAPatientConsultations() {
		Assertions.assertNotEquals("Laso Escot, María", this.driver
			.findElement(By.xpath("//table[@id='patientsTable']/tbody/tr/td")).getText());
	this.driver.findElement(By.xpath("//a[contains(text(),'Consultas del paciente')]")).click();
	Assertions.assertNotEquals("Lista diferente",
			this.driver.findElement(By.xpath("//h2")).getText());
	return this;
	}

	private ListMyPatientsConsultsUITest thenINotShowAPatientConsultation() {

		this.driver.findElement(By.xpath("//a[contains(text(),'Ver consulta')]")).click();
		Assertions.assertNotEquals("Detalles diferentes",
				this.driver.findElement(By.xpath("//h2")).getText());
		Assertions.assertNotEquals("Incorrecto",
				this.driver.findElement(By.xpath("//h3")).getText());
		Assertions.assertNotEquals("Incorrecto",
				this.driver.findElement(By.xpath("//h3[2]")).getText());
		Assertions.assertNotEquals("Incorrecto",
				this.driver.findElement(By.xpath("//h3[3]")).getText());
		Assertions.assertNotEquals("07/03/2021 11:00",
				this.driver.findElement(By.xpath("//td")).getText());

		return this;
	}


	@Test
	public void negativeTestIU001() throws Exception {

		this.as("doctor1").thenINotListMyPatients().thenINotListAPatientConsultations().thenINotShowAPatientConsultation();



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
