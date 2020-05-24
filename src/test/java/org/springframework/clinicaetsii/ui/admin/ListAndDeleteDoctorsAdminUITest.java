
package org.springframework.clinicaetsii.ui.admin;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class ListAndDeleteDoctorsAdminUITest {

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
	}

	private ListAndDeleteDoctorsAdminUITest as(final String username) {

		this.username = username;

		this.driver.get("http://localhost:" + this.port);
		this.driver.findElement(By.xpath("//a[contains(text(),'Iniciar sesión')]")).click();
		this.driver.findElement(By.id("username")).click();
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys(username);
		this.driver.findElement(By.id("password")).click();
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys(this.passwordOf(username));
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		return this;
	}

	private ListAndDeleteDoctorsAdminUITest whenIamLoggedInTheSystem() {
		return this;
	}

	private ListAndDeleteDoctorsAdminUITest thenISeeMyUsernameInTheMenuBar() {
		Assertions.assertEquals(this.username.toUpperCase(),
				this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).getText());
		return this;
	}

	private ListAndDeleteDoctorsAdminUITest thenIEnterDoctorList() {
		this.driver.findElement(By.xpath("//a[contains(text(),'Administrador')]")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/admin/doctors')]")).click();
		return this;
	}

	private ListAndDeleteDoctorsAdminUITest thenISeeDoctorList() {
		Assertions.assertEquals("Médicos", this.driver.findElement(By.xpath("//h2")).getText());
		this.driver.findElement(By.xpath("(//a[contains(text(),'Seleccionar Médico')])[4]"))
				.click();
		Assertions.assertEquals("Detalles del médico",
				this.driver.findElement(By.xpath("//h2")).getText());
		Assertions.assertEquals("doctor4", this.driver.findElement(By.xpath("//b")).getText());
		return this;
	}

	private ListAndDeleteDoctorsAdminUITest thenIDeleteDoctor() {
		this.driver.findElement(By.xpath("//a[contains(text(),'Eliminar Médico')]")).click();
		Assert.assertFalse(
				this.isElementPresent(By.xpath("//table[@id='doctorsTable']/tbody/tr[4]/td")));
		return this;
	}

	@Test
	public void shouldListDoctor() throws Exception {
		this.as("admin").whenIamLoggedInTheSystem().thenISeeMyUsernameInTheMenuBar()
				.thenIEnterDoctorList().thenISeeDoctorList();
	}

	@Test
	public void shouldDeleteDoctor() throws Exception {
		this.as("admin").whenIamLoggedInTheSystem().thenISeeMyUsernameInTheMenuBar()
				.thenIEnterDoctorList().thenISeeDoctorList().thenIDeleteDoctor();
	}

	private String passwordOf(final String username) {
		return username;
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
