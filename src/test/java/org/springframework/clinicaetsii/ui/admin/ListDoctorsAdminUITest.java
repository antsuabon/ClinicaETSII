
package org.springframework.clinicaetsii.ui.admin;

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
public class ListDoctorsAdminUITest {

	@LocalServerPort
	private int				port;

	private WebDriver		driver;
	private String			baseUrl;
	private boolean			acceptNextAlert		= true;
	private StringBuffer	verificationErrors	= new StringBuffer();


	@BeforeEach
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "C:\\webdrivers\\chromedriver.exe");
		this.driver = new ChromeDriver();
		this.baseUrl = "https://www.google.com/";
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void positiveTestIU019() throws Exception {

		this.driver.get("http://localhost:9090/");
		this.driver.findElement(By.xpath("//a[contains(text(),'Iniciar sesión')]")).click();
		this.driver.findElement(By.id("username")).click();
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("admin");
		this.driver.findElement(By.id("password")).click();
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("admin");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//a[contains(text(),'Administrador')]")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/admin/doctors')]")).click();
		Assertions.assertEquals("Médicos", this.driver.findElement(By.xpath("//h2")).getText());
		try {
			Assertions.assertEquals("Pablo Rodriguez Garrido", this.driver.findElement(By.xpath("//table[@id='doctorsTable']/tbody/tr/td")).getText());
		} catch (Error e) {
			this.verificationErrors.append(e.toString());
		}
		this.driver.findElement(By.xpath("//a[contains(text(),'Seleccionar Médico')]")).click();
		Assertions.assertEquals("Detalles del médico", this.driver.findElement(By.xpath("//h2")).getText());
		Assertions.assertEquals("doctor1", this.driver.findElement(By.xpath("//b")).getText());
	}

	@Test
	public void negativeTestIU019() throws Exception {

		this.driver.get("http://localhost:9090/");
		this.driver.findElement(By.xpath("//a[contains(text(),'Iniciar sesión')]")).click();
		this.driver.findElement(By.id("username")).click();
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("admin");
		this.driver.findElement(By.id("password")).click();
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("admin");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//a[contains(text(),'Administrador')]")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/admin/doctors')]")).click();
		Assertions.assertNotEquals("Nombre incorrecto", this.driver.findElement(By.xpath("//h2")).getText());
		try {
			Assertions.assertNotEquals("Alejandro Sánchez Saavedra", this.driver.findElement(By.xpath("//table[@id='doctorsTable']/tbody/tr/td")).getText());
		} catch (Error e) {
			this.verificationErrors.append(e.toString());
		}
		this.driver.findElement(By.xpath("//a[contains(text(),'Seleccionar Médico')]")).click();
		Assertions.assertNotEquals("Detalles incorrectos", this.driver.findElement(By.xpath("//h2")).getText());
		Assertions.assertEquals("doctor2", this.driver.findElement(By.xpath("//b")).getText());
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
