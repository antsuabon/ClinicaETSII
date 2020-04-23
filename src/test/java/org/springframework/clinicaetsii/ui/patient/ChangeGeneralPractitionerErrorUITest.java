package org.springframework.clinicaetsii.ui.patient;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.service.DoctorService;
import org.springframework.clinicaetsii.service.PatientService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChangeGeneralPractitionerErrorUITest  {
  
  @LocalServerPort
  private int port;
  
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  
  @Autowired
  private PatientService patientService;
  
  @Autowired
  private DoctorService doctorService;
  
  private void createPatients() {
	  
	  Doctor d1 = doctorService.findDoctorById(2);
		
	  Patient p1 = new Patient();
	  p1.setId(100);
	  p1.setUsername("asdf1");
	  p1.setEnabled(true);
	  p1.setName("a1");
	  p1.setSurname("a1");
	  p1.setEmail("asdf@gmail.com");
	  p1.setPhone("123456789");
	  p1.setNss("12345678912");
	  p1.setAddress("asdf");
	  p1.setState("raro");
	  p1.setDni("12345678P");
	  p1.setGeneralPractitioner(d1);
		
	  Patient p2 = new Patient();
	  p2.setId(101);
	  p2.setUsername("asdf2");
	  p2.setEnabled(true);
	  p2.setName("a1");
	  p2.setSurname("a1");
	  p2.setEmail("asdf@gmail.com");
	  p2.setPhone("123456789");
	  p2.setNss("12345678912");
	  p2.setAddress("asdf");
	  p2.setState("raro");
	  p2.setDni("12345678P");
	  p2.setGeneralPractitioner(d1);
		
	  Patient p3 = new Patient();
	  p3.setId(102);
	  p3.setUsername("asdf3");
	  p3.setEnabled(true);
	  p3.setName("a1");
	  p3.setSurname("a1");
	  p3.setEmail("asdf@gmail.com");
	  p3.setPhone("123456789");
	  p3.setNss("12345678912");
	  p3.setAddress("asdf");
	  p3.setState("raro");
	  p3.setDni("12345678P");
	  p3.setGeneralPractitioner(d1);
		
	  patientService.save(p1);
	  patientService.save(p2);
	  patientService.save(p3);
	  
  }
  
  @BeforeEach
  public void setUp() throws Exception { 
	System.setProperty("webdriver.gecko.driver","C:\\Users\\aleja\\Desktop\\universidad\\gecko\\geckodriver.exe");
    driver = new FirefoxDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testUi006() throws Exception {
	createPatients();
    driver.get("http://localhost:" + port + "/");
    driver.findElement(By.xpath("//a[contains(text(),'Iniciar sesión')]")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("patient1");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("patient1");
    driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
    driver.findElement(By.xpath("//a[contains(text(),'Paciente')]")).click();
    driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).click();
    driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/ul/li[3]/a/span[2]")).click();
    driver.findElement(By.xpath("//a[contains(text(),'Editar Paciente')]")).click();
    driver.findElement(By.id("patient.generalPractitioner")).click();
    new Select(driver.findElement(By.id("patient.generalPractitioner"))).selectByVisibleText("Laso Escot, María");
    driver.findElement(By.xpath("//option[@value='2']")).click();
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    assertEquals("Este doctor tiene 5 pacientes asignados", driver.findElement(By.xpath("//form[@id='add-patient-form']/div/div[11]/div/div/span[2]")).getText());
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
