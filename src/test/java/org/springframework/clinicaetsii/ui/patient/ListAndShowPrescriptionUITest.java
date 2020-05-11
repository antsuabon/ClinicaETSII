package org.springframework.clinicaetsii.ui.patient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ListAndShowPrescriptionUITest {

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



	private ListAndShowPrescriptionUITest as(final String username, final String password) {
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

	private ListAndShowPrescriptionUITest whenIamLoggedInTheSystem() {
		return this;
	}

	private ListAndShowPrescriptionUITest thenIListMyPrescriptions() {
		this.driver.findElement(By.xpath("//a[contains(text(),'Paciente')]")).click();
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/ul/li[3]/a/span[2]"))
				.click();
		return this;
	}

	private ListAndShowPrescriptionUITest thenIEnterAPrescription() {
		this.driver.findElement(By.xpath("//a[contains(text(),'Ibuprofeno - Dalsy')]")).click();
		return this;
	}

	private ListAndShowPrescriptionUITest thenIShowAPrescription() {
		assertEquals("Detalles de la prescripción",
				this.driver.findElement(By.xpath("//h2")).getText());
		assertEquals("Fecha de inicio", this.driver.findElement(By.xpath("//th")).getText());
		assertEquals("Fecha de fin", this.driver.findElement(By.xpath("//tr[2]/th")).getText());
		assertEquals("Días", this.driver.findElement(By.xpath("//tr[3]/th")).getText());
		assertEquals("Dosificación", this.driver.findElement(By.xpath("//tr[4]/th")).getText());
		assertEquals("Número de dosis", this.driver.findElement(By.xpath("//tr[5]/th")).getText());
		assertEquals("Advertencia al Farmacéutico",
				this.driver.findElement(By.xpath("//tr[6]/th")).getText());
		assertEquals("Advertencia al Paciente",
				this.driver.findElement(By.xpath("//tr[7]/th")).getText());

		assertEquals("Detalles del Medicamento",
				this.driver.findElement(By.xpath("//h3")).getText());
		assertEquals("Nombre Comercial",
				this.driver.findElement(By.xpath("//table[2]/tbody/tr/th")).getText());
		assertEquals("Nombre Genérico",
				this.driver.findElement(By.xpath("//table[2]/tbody/tr[2]/th")).getText());
		assertEquals("Indicaciones",
				this.driver.findElement(By.xpath("//table[2]/tbody/tr[3]/th")).getText());

		assertEquals("09/03/2020 11:00", this.driver.findElement(By.xpath("//td")).getText());
		assertEquals("16/03/2020 11:00", this.driver.findElement(By.xpath("//tr[2]/td")).getText());
		assertEquals("7.0", this.driver.findElement(By.xpath("//tr[3]/td")).getText());
		assertEquals("1.0", this.driver.findElement(By.xpath("//tr[4]/td")).getText());
		assertEquals("168.0", this.driver.findElement(By.xpath("//tr[5]/td")).getText());
		assertEquals("Vender solo con receta",
				this.driver.findElement(By.xpath("//tr[6]/td")).getText());
		assertEquals("Puede provocar efectos secundarios",
				this.driver.findElement(By.xpath("//tr[7]/td")).getText());

		assertEquals("Dalsy",
				this.driver.findElement(By.xpath("//table[2]/tbody/tr/td")).getText());
		assertEquals("Ibuprofeno",
				this.driver.findElement(By.xpath("//table[2]/tbody/tr[2]/td")).getText());
		assertEquals("Dolor leve y moderado",
				this.driver.findElement(By.xpath("//table[2]/tbody/tr[3]/td")).getText());
		return this;
	}

	@Test
	public void shouldListAndShow() throws Exception {
		as("patient1", "patient1").whenIamLoggedInTheSystem().thenIListMyPrescriptions()
				.thenIEnterAPrescription().thenIShowAPrescription();


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
