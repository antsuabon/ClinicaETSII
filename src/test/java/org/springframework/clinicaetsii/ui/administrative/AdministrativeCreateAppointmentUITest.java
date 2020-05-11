
package org.springframework.clinicaetsii.ui.administrative;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdministrativeCreateAppointmentUITest {

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

	private AdministrativeCreateAppointmentUITest as(final String username, final String password) {
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

	private AdministrativeCreateAppointmentUITest whenIamLoggedInTheSystem() {
		return this;
	}

	private AdministrativeCreateAppointmentUITest thenICreateAnAppointment() {
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li/a")).click();
		this.driver.findElement(By.xpath("//a[contains(text(),'Administrativo')]")).click();
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/ul/li[2]/a")).click();
		this.driver.findElement(By.xpath("//a[contains(text(),'Crear Cita')]")).click();
		this.driver.findElement(By.xpath("//a[contains(text(),'Seleccionar hora')]")).click();
		this.driver.findElement(By.xpath("//table[@id='table']/tbody/tr/td")).click();
		return this;
	}

	private AdministrativeCreateAppointmentUITest thenILoggedOut() {
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).click();
		this.driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		return this;
	}

	private AdministrativeCreateAppointmentUITest thenICheckTheAppointmentCreated(
			final String fechaCreada) {
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li/a")).click();
		this.driver.findElement(By.xpath("//a[contains(text(),'Paciente')]")).click();
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/ul/li/a")).click();
		List<WebElement> citasCreadas =
				this.driver.findElements(By.xpath("//table[@id='appointmentsTable1']/tbody/tr/td"));
		String fechaPaciente = citasCreadas.get(citasCreadas.size() - 3).getText();
		Assertions.assertEquals(fechaCreada, fechaPaciente);
		return this;
	}

	@Test
	public void shouldCreateAppointment() throws Exception {

		as("administrative1", "administrative1").whenIamLoggedInTheSystem()
				.thenICreateAnAppointment();
		String fechaCreada =
				this.driver.findElement(By.xpath("//table[@id='table']/tbody/tr/td")).getText();
		thenILoggedOut().as("patient1", "patient1").thenICheckTheAppointmentCreated(fechaCreada);

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
