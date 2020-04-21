
package org.springframework.clinicaetsii.ui.doctor;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit; 
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class CreatePrescriptionUITest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @BeforeEach
  public void setUp() throws Exception {
	System.setProperty("webdriver.gecko.driver","C:\\Users\\José\\Desktop\\Programación\\geckodriver.exe");
    driver = new FirefoxDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testUntitledTestCase() throws Exception {
    driver.get("http://localhost:9090/");
    driver.findElement(By.xpath("//a[contains(text(),'Iniciar sesión')]")).click();
    driver.findElement(By.id("username")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("doctor1");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("doctor1");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.xpath("//a[contains(text(),'Médico')]")).click();
    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/ul/li[1]/a/span[2]")).click();
    driver.findElement(By.xpath("//a[contains(text(),'Prescripciones del paciente')]")).click();
    driver.findElement(By.xpath("//a[contains(text(),'Añadir prescripción')]")).click();
    driver.findElement(By.id("dosage")).click();
    driver.findElement(By.id("dosage")).clear();
    driver.findElement(By.id("dosage")).sendKeys("3");
    driver.findElement(By.id("days")).click();
    driver.findElement(By.id("days")).clear();
    driver.findElement(By.id("days")).sendKeys("3");
    driver.findElement(By.id("pharmaceuticalWarning")).click();
    driver.findElement(By.id("pharmaceuticalWarning")).clear();
    driver.findElement(By.id("pharmaceuticalWarning")).sendKeys("asdf");
    driver.findElement(By.id("patientWarning")).click();
    driver.findElement(By.id("patientWarning")).clear();
    driver.findElement(By.id("patientWarning")).sendKeys("asdf");
    driver.findElement(By.id("medicine")).click();
    new Select(driver.findElement(By.id("medicine"))).selectByVisibleText("Paracel (Paracetamol)");
    driver.findElement(By.xpath("//option[@value='2']")).click();
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.xpath("(//a[contains(text(),'Ver prescripción')])[3]")).click();
    assertEquals("asdf", driver.findElement(By.xpath("//tr[6]/td")).getText());
  }

  @AfterEach
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}