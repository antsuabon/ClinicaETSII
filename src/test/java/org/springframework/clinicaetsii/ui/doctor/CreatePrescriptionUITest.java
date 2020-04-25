
package org.springframework.clinicaetsii.ui.doctor;

import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreatePrescriptionUITest {

	@LocalServerPort
	private int port;
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	private String username;

	private String dosage;
	private String days;
	private String pharmaceuticalWarning;
	private String patientWarning;
	private String medicine;

	@BeforeEach
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver",
				"D:\\Aplicaciones\\chromedriver_win32\\chromedriver.exe");
		this.driver = new ChromeDriver();
		this.baseUrl = "https://www.google.com/";
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	private CreatePrescriptionUITest as(final String username, final String password) {
		this.username = username;

		this.driver.get("http://localhost:" + this.port);

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

	private CreatePrescriptionUITest whenIamLoggedInTheSystem() {
		return this;
	}

	private CreatePrescriptionUITest thenISeeMyUsernameInTheMenuBar() {
		Assertions.assertEquals(this.username.toUpperCase(),
				this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).getText());
		return this;
	}

	private CreatePrescriptionUITest thenIEnterPatientList() {
		this.driver.findElement(By.xpath("//a[contains(text(),'Médico')]")).click();
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/ul/li/a/span[2]"))
				.click();
		return this;
	}

	private CreatePrescriptionUITest thenIEnterPrescriptionList() {
		this.driver.findElement(By.xpath("//a[contains(text(),'Prescripciones del paciente')]"))
				.click();
		return this;
	}

	private CreatePrescriptionUITest thenIEnterPrescriptionForm() {
		this.driver.findElement(By.xpath("//a[contains(text(),'Añadir prescripción')]")).click();

		this.driver.findElement(By.id("dosage")).click();
		this.driver.findElement(By.id("dosage")).clear();
		this.driver.findElement(By.id("dosage")).sendKeys(this.dosage);

		this.driver.findElement(By.id("days")).click();
		this.driver.findElement(By.id("days")).clear();
		this.driver.findElement(By.id("days")).sendKeys(this.days);

		this.driver.findElement(By.id("pharmaceuticalWarning")).click();
		this.driver.findElement(By.id("pharmaceuticalWarning")).clear();
		this.driver.findElement(By.id("pharmaceuticalWarning"))
				.sendKeys(this.pharmaceuticalWarning);

		this.driver.findElement(By.id("patientWarning")).click();
		this.driver.findElement(By.id("patientWarning")).clear();
		this.driver.findElement(By.id("patientWarning")).sendKeys(this.patientWarning);

		this.driver.findElement(By.id("medicine")).click();
		new Select(this.driver.findElement(By.id("medicine"))).selectByVisibleText(this.medicine);
		this.driver.findElement(By.xpath("//option[@value='2']")).click();

		this.driver.findElement(By.xpath("//button[@type='submit']")).click();

		this.driver.findElement(By.xpath("(//a[contains(text(),'Ver prescripción')])[3]")).click();
		return this;
	}

	@ParameterizedTest
	@CsvSource({"3, 3, Advertencia 1, Advertencia 2, Paracel (Paracetamol)"})
	public void shouldCreatePrescription(final String dosage,
			final String days,
			final String pharmaceuticalWarning,
			final String patientWarning,
			final String medicine) throws Exception {

		this.dosage = dosage;
		this.days = days;
		this.pharmaceuticalWarning = pharmaceuticalWarning;
		this.patientWarning = patientWarning;
		this.medicine = medicine;

		as("doctor1", "doctor1").whenIamLoggedInTheSystem().thenISeeMyUsernameInTheMenuBar()
				.thenIEnterPatientList().thenIEnterPrescriptionList().thenIEnterPrescriptionForm();
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
