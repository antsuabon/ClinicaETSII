
package org.springframework.clinicaetsii.ui.administrative;

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
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdministrativeDeleteMedicinesUITest {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();


	@BeforeEach
	public void setUp() throws Exception {
//		System.setProperty("webdriver.chrome.driver",
//				"D:\\Aplicaciones\\chromedriver_win32\\chromedriver.exe");

		 String pathToGeckoDriver="C:\\Users\\angel\\Downloads\\webdrivers";
		 System.setProperty("webdriver.gecko.driver", pathToGeckoDriver + "\\geckodriver.exe");
		 this.driver = new FirefoxDriver();
		this.baseUrl = "https://www.google.com/";
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	private AdministrativeDeleteMedicinesUITest as(final String username, final String password) {
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

	private AdministrativeDeleteMedicinesUITest thenISeeMyUserRole() {
		 this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li/a")).click();
		 this.driver.findElement(By.xpath("//a[contains(text(),'Administrativo')]")).click();
		return this;
	}

	private AdministrativeDeleteMedicinesUITest thenIEnterMedicineList() {
		this.driver.findElement(By.xpath("//a[contains(@href, '/administrative/medicines')]")).click();
		return this;
	}

	private AdministrativeDeleteMedicinesUITest thenISeeMedicineList() {

		Assertions.assertEquals("Medicamentos",
				this.driver.findElement(By.xpath("//h2")).getText());
		Assertions.assertEquals("Nombre Comercial", this.driver
				.findElement(By.xpath("//table[@id='medicinesTable']/thead/tr/th")).getText());
		Assertions.assertEquals("Nombre Genérico", this.driver
				.findElement(By.xpath("//table[@id='medicinesTable']/thead/tr/th[2]")).getText());
		Assertions.assertEquals("Indicaciones", this.driver
				.findElement(By.xpath("//table[@id='medicinesTable']/thead/tr/th[3]")).getText());
		Assertions.assertEquals("Contraindicaciones", this.driver
				.findElement(By.xpath("//table[@id='medicinesTable']/thead/tr/th[4]")).getText());
		Assertions.assertEquals("Ibuprofeno", this.driver
				.findElement(By.xpath("//table[@id='medicinesTable']/tbody/tr/td")).getText());
		Assertions.assertEquals("Dalsy", this.driver
				.findElement(By.xpath("//table[@id='medicinesTable']/tbody/tr/td[2]")).getText());
		Assertions.assertEquals("Dolor leve y moderado", this.driver
				.findElement(By.xpath("//table[@id='medicinesTable']/tbody/tr/td[3]")).getText());
		Assertions.assertEquals(
				"En síndrome de pólipos nasales, angioedema y reactividad broncoespástica a aspirina u otros AINEs.",
				this.driver.findElement(By.xpath("//table[@id='medicinesTable']/tbody/tr/td[4]"))
						.getText());

		return this;
	}

	private AdministrativeDeleteMedicinesUITest thenIDeleteMedicine(){

		this.driver.findElement(By.xpath("//a[contains(text(),'Omeprazol')]")).click();
		this.driver.findElement(By.xpath("//a[2]/h4")).click();
	  Assertions.assertFalse(this.isElementPresent(By.xpath("//table[@id='medicinesTable']/tbody/tr[3]/td")));


	    return this;
	}
	@Test
	public void shouldListMedicines() throws Exception {
		this.as("administrative1","administrative1").thenISeeMyUserRole().thenIEnterMedicineList().thenIDeleteMedicine();

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
