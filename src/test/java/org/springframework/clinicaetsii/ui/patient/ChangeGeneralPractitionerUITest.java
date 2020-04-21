package org.springframework.clinicaetsii.ui.patient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class ChangeGeneralPractitionerUITest {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@BeforeEach
	public void setUp() throws Exception {
		// System.setProperty("webdriver.gecko.driver","C:\\Users\\aleja\\Desktop\\universidad\\gecko\\geckodriver.exe");
		// driver = new FirefoxDriver();
		System.setProperty("webdriver.chrome.driver",
				"D:\\Aplicaciones\\chromedriver_win32\\chromedriver.exe");
		this.driver = new ChromeDriver();
		this.baseUrl = "https://www.google.com/";
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testUi005() throws Exception {
		this.driver.get("http://localhost:9090/");
		this.driver.findElement(By.xpath("//a[contains(text(),'Iniciar sesión')]")).click();
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("patient1");
		this.driver.findElement(By.id("password")).click();
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("patient1");
		this.driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
		assertEquals("PATIENT1", this.driver
				.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).getText());
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).click();
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/ul/li[3]/a/span[2]"))
				.click();
		this.driver.findElement(By.xpath("//a[contains(text(),'Editar Paciente')]")).click();
		this.driver.findElement(By.id("patient.generalPractitioner")).click();
		new Select(this.driver.findElement(By.id("patient.generalPractitioner")))
				.selectByVisibleText("Laso Escot, María");
		this.driver.findElement(By.xpath("//option[@value='2']")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertEquals("Laso Escot, María",
				this.driver.findElement(By.xpath("//tr[10]/td")).getText());
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
