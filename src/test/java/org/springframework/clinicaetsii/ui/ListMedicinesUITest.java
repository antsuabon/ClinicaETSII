
package org.springframework.clinicaetsii.ui;

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
public class ListMedicinesUITest {

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
	public void positiveTestIU003() throws Exception {

		this.driver.get("http://localhost:9090/");
		this.driver.findElement(By.xpath("//div/div/div/div")).click();
		this.driver.findElement(By.xpath("//a[contains(text(),'Anónimo')]")).click();
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[2]/ul/li[2]/a/span[2]")).click();
		Assertions.assertEquals("Medicamentos", this.driver.findElement(By.xpath("//h2")).getText());
		Assertions.assertEquals("Nombre Comercial", this.driver.findElement(By.xpath("//table[@id='medicinesTable']/thead/tr/th")).getText());
		Assertions.assertEquals("Nombre Genérico", this.driver.findElement(By.xpath("//table[@id='medicinesTable']/thead/tr/th[2]")).getText());
		Assertions.assertEquals("Indicaciones", this.driver.findElement(By.xpath("//table[@id='medicinesTable']/thead/tr/th[3]")).getText());
		Assertions.assertEquals("Contraindicaciones", this.driver.findElement(By.xpath("//table[@id='medicinesTable']/thead/tr/th[4]")).getText());
		Assertions.assertEquals("Ibuprofeno", this.driver.findElement(By.xpath("//table[@id='medicinesTable']/tbody/tr/td")).getText());
		Assertions.assertEquals("Dalsy", this.driver.findElement(By.xpath("//table[@id='medicinesTable']/tbody/tr/td[2]")).getText());
		Assertions.assertEquals("Dolor leve y moderado", this.driver.findElement(By.xpath("//table[@id='medicinesTable']/tbody/tr/td[3]")).getText());
		Assertions.assertEquals("En síndrome de pólipos nasales, angioedema y reactividad broncoespástica a aspirina u otros AINEs.", this.driver.findElement(By.xpath("//table[@id='medicinesTable']/tbody/tr/td[4]")).getText());
	}

	@Test
	public void negativeTestIU003() throws Exception {
		this.driver.get("http://localhost:9090/");
		this.driver.findElement(By.xpath("//div/div/div/div")).click();
		this.driver.findElement(By.xpath("//a[contains(text(),'Anónimo')]")).click();
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[2]/ul/li[2]/a/span[2]")).click();
		Assertions.assertNotEquals("Nombre incorrecto", this.driver.findElement(By.xpath("//h2")).getText());
		Assertions.assertNotEquals("Nombre incorrecto", this.driver.findElement(By.xpath("//table[@id='medicinesTable']/thead/tr/th")).getText());
		Assertions.assertNotEquals("Nombre incorrecto", this.driver.findElement(By.xpath("//table[@id='medicinesTable']/thead/tr/th[2]")).getText());
		Assertions.assertNotEquals("Nombre incorrecto", this.driver.findElement(By.xpath("//table[@id='medicinesTable']/thead/tr/th[3]")).getText());
		Assertions.assertNotEquals("Nombre incorrecto", this.driver.findElement(By.xpath("//table[@id='medicinesTable']/thead/tr/th[4]")).getText());
		Assertions.assertNotEquals("Nombre incorrecto", this.driver.findElement(By.xpath("//table[@id='medicinesTable']/tbody/tr/td")).getText());
		Assertions.assertNotEquals("Ibuprofeno", this.driver.findElement(By.xpath("//table[@id='medicinesTable']/tbody/tr/td[2]")).getText());
		Assertions.assertNotEquals("Dolor estómago", this.driver.findElement(By.xpath("//table[@id='medicinesTable']/tbody/tr/td[3]")).getText());
		Assertions.assertNotEquals("Descripción incorrecta", this.driver.findElement(By.xpath("//table[@id='medicinesTable']/tbody/tr/td[4]")).getText());
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
