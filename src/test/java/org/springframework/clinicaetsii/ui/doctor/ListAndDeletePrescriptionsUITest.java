
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
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ListAndDeletePrescriptionsUITest {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();


	@BeforeEach
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver",
				"D:\\Aplicaciones\\chromedriver_win32\\chromedriver.exe");
		this.driver = new ChromeDriver();
		this.baseUrl = "https://www.google.com/";
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	private String username;

	private ListAndDeletePrescriptionsUITest as(final String username) {

		this.username = username;

		this.driver.get("http://localhost:" + this.port);
		this.driver.findElement(By.xpath("//a[contains(text(),'Iniciar sesión')]")).click();

		this.driver.findElement(By.id("username")).click();
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys(username);

		this.driver.findElement(By.id("password")).click();
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys(passwordOf(username));

		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		return this;
	}

	private ListAndDeletePrescriptionsUITest whenIAmLoggedIntoTheSystem() {
		return this;
	}

	private ListAndDeletePrescriptionsUITest thenISeeMyUsernameInTheMenuBar() {
		Assertions.assertEquals(this.username.toUpperCase(),
				this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).getText());
		return this;
	}

	private ListAndDeletePrescriptionsUITest thenISeeMyRoleDropdownInTheMenuBar() {
		Assertions.assertEquals("Médico".toUpperCase(),
				this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/a")).getText());
		return this;
	}

	private ListAndDeletePrescriptionsUITest thenIEnterPatientsList() {
		this.driver.findElement(By.xpath("//a[contains(text(),'Médico')]")).click();
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/ul/li/a/span[2]"))
				.click();
		return this;
	}

	private ListAndDeletePrescriptionsUITest thenIEnterPrescriptionsList() {
		this.driver.findElement(By.xpath("//a[contains(text(),'Prescripciones del paciente')]"))
				.click();

		Assertions.assertEquals("Lista de Prescripciones",
				this.driver.findElement(By.xpath("//h2")).getText());
		return this;
	}

	private ListAndDeletePrescriptionsUITest thenICanSeePrescriptionsList() {

		Assertions.assertEquals("20/02/2020 13:00", this.driver
				.findElement(By.xpath("//table[@id='prescriptionsTable']/tbody/tr/td")).getText());

		this.driver.findElement(By.xpath("//a[contains(text(),'Ver prescripción')]")).click();

		Assertions.assertEquals("Detalles de la prescripción",
				this.driver.findElement(By.xpath("//h2")).getText());


		return this;
	}

	private ListAndDeletePrescriptionsUITest thenIDeletePescription() {
		this.driver.findElement(By.xpath("//h4")).click();
		Assertions.assertEquals("09/03/2020 11:00", this.driver
				.findElement(By.xpath("//table[@id='prescriptionsTable']/tbody/tr/td")).getText());
		return this;
	}

	private ListAndDeletePrescriptionsUITest thenICantSeePrescriptionsList() {

		Assertions.assertNotEquals("09/03/2020 11:00", this.driver
				.findElement(By.xpath("//table[@id='prescriptionsTable']/tbody/tr/td")).getText());

		this.driver.findElement(By.xpath("//a[contains(text(),'Ver prescripción')]")).click();

		Assertions.assertNotEquals("Detalles diferentes",
				this.driver.findElement(By.xpath("//h2")).getText());

		this.driver.findElement(By.xpath("//h4")).click();

		Assertions.assertNotEquals("20/02/2020 13:00", this.driver
				.findElement(By.xpath("//table[@id='prescriptionsTable']/tbody/tr/td")).getText());
		return this;
	}

	@Test
	public void shouldListPrescriptions() throws Exception {

		as("doctor1").whenIAmLoggedIntoTheSystem().thenISeeMyUsernameInTheMenuBar()
				.thenISeeMyRoleDropdownInTheMenuBar().thenIEnterPatientsList()
				.thenIEnterPrescriptionsList().thenICanSeePrescriptionsList()
				.thenIDeletePescription();

	}

	@Test
	public void shouldNotListPrescriptions() throws Exception {

		as("doctor1").whenIAmLoggedIntoTheSystem().thenISeeMyUsernameInTheMenuBar()
				.thenISeeMyRoleDropdownInTheMenuBar().thenIEnterPatientsList()
				.thenIEnterPrescriptionsList().thenICantSeePrescriptionsList();

	}

	@AfterEach
	public void tearDown() throws Exception {
		this.driver.quit();
		String verificationErrorString = this.verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			Assertions.fail(verificationErrorString);
		}
	}

	private String passwordOf(final String username) {
		return username;
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
