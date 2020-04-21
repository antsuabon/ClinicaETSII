
package org.springframework.clinicaetsii.ui.doctor;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class CreatePrescriptionUITest {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@BeforeEach
	public void setUp() throws Exception {
		// System.setProperty("webdriver.gecko.driver","C:\\Users\\José\\Desktop\\Programación\\geckodriver.exe");
		// driver = new FirefoxDriver();
		System.setProperty("webdriver.chrome.driver",
				"D:\\Aplicaciones\\chromedriver_win32\\chromedriver.exe");
		this.driver = new ChromeDriver();
		this.baseUrl = "https://www.google.com/";
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testUntitledTestCase() throws Exception {
		this.driver.get("http://localhost:9090/");
		this.driver.findElement(By.xpath("//a[contains(text(),'Iniciar sesión')]")).click();
		this.driver.findElement(By.id("username")).click();
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("doctor1");
		this.driver.findElement(By.id("password")).click();
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("doctor1");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//a[contains(text(),'Médico')]")).click();
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/ul/li[1]/a/span[2]"))
				.click();
		this.driver.findElement(By.xpath("//a[contains(text(),'Prescripciones del paciente')]"))
				.click();
		this.driver.findElement(By.xpath("//a[contains(text(),'Añadir prescripción')]")).click();
		this.driver.findElement(By.id("dosage")).click();
		this.driver.findElement(By.id("dosage")).clear();
		this.driver.findElement(By.id("dosage")).sendKeys("3");
		this.driver.findElement(By.id("days")).click();
		this.driver.findElement(By.id("days")).clear();
		this.driver.findElement(By.id("days")).sendKeys("3");
		this.driver.findElement(By.id("pharmaceuticalWarning")).click();
		this.driver.findElement(By.id("pharmaceuticalWarning")).clear();
		this.driver.findElement(By.id("pharmaceuticalWarning")).sendKeys("asdf");
		this.driver.findElement(By.id("patientWarning")).click();
		this.driver.findElement(By.id("patientWarning")).clear();
		this.driver.findElement(By.id("patientWarning")).sendKeys("asdf");
		this.driver.findElement(By.id("medicine")).click();
		new Select(this.driver.findElement(By.id("medicine")))
				.selectByVisibleText("Paracel (Paracetamol)");
		this.driver.findElement(By.xpath("//option[@value='2']")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("(//a[contains(text(),'Ver prescripción')])[3]")).click();
		assertEquals("asdf", this.driver.findElement(By.xpath("//tr[6]/td")).getText());
	}

	@AfterEach
	public void tearDown() throws Exception {
		this.driver.quit();
		String verificationErrorString = this.verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
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
