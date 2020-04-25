
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
public class ListDoctorsUITest {

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

	private ListDoctorsUITest asAnonymous() {
		this.driver.get("http://localhost:" + this.port);

		return this;
	}

	private ListDoctorsUITest thenISeeMyUserRole() {
		this.driver.findElement(By.xpath("//a[contains(text(),'Anónimo')]")).click();
		return this;
	}

	private ListDoctorsUITest thenIEnterDoctorList() {
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[2]/ul/li/a/span[2]"))
				.click();
		return this;
	}

	private ListDoctorsUITest thenISeeDoctorList() {
		Assertions.assertEquals("Nombre Completo", this.driver
				.findElement(By.xpath("//table[@id='doctorsTable']/thead/tr/th")).getText());
		Assertions.assertEquals("Servicios", this.driver
				.findElement(By.xpath("//table[@id='doctorsTable']/thead/tr/th[2]")).getText());
		Assertions.assertEquals("Médicos", this.driver.findElement(By.xpath("//h2")).getText());
		Assertions.assertEquals("Salado Asenjo, José", this.driver
				.findElement(By.xpath("//table[@id='doctorsTable']/tbody/tr/td")).getText());
		return this;
	}

	@Test
	public void shouldListDoctors() throws Exception {
		asAnonymous().thenISeeMyUserRole().thenIEnterDoctorList().thenISeeDoctorList();
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
