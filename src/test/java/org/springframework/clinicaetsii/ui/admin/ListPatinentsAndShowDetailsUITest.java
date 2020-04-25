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
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ListPatinentsAndShowDetailsUITest {

	@LocalServerPort
	  private int port;

	  private WebDriver driver;
	  private String baseUrl;
	  private boolean acceptNextAlert = true;
	  private StringBuffer verificationErrors = new StringBuffer();

	  private String username;
	  private String user;
	  private String name;
	  private String surname;
	  private String dni;
	  private String email;
	  private String phone;
	  private String birthDate;
	  private String phone2;
	  private String nss;
	  private String state;
	  private String address;

	  @BeforeEach
		public void setUp() throws Exception {
			System.setProperty("webdriver.gecko.driver", "D:\\Descargas\\geckodriver.exe");
			this.driver = new FirefoxDriver();
			this.baseUrl = "https://www.google.com/";
			this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

			this.driver.get("http://localhost:" + this.port);
		}

	  private ListPatinentsAndShowDetailsUITest as(final String username, final String password) {
		    this.username = username;

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

	  private ListPatinentsAndShowDetailsUITest whenIamLoggedInTheSystem() {
			return this;
		}

	  private ListPatinentsAndShowDetailsUITest thenISeeMyUsernameInTheMenuBar() {
			Assertions.assertEquals(this.username.toUpperCase(),
			this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).getText());
			return this;
		}

	  private ListPatinentsAndShowDetailsUITest thenIListPPatients() {

		  	this.driver.findElement(By.xpath("//a[contains(text(),'Administrador')]")).click();
		  	this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/ul/li/a/span[2]")).click();
		  	Assertions.assertEquals("Lista de Pacientes del Centro", this.driver.findElement(By.xpath("//h2")).getText());
		  	Assertions.assertEquals("Nombre Completo", this.driver.findElement(By.xpath("//table[@id='patientsTable']/thead/tr/th")).getText());
		  	Assertions.assertEquals("Sánchez Saavedra, Alejandro", this.driver.findElement(By.xpath("//table[@id='patientsTable']/tbody/tr/td")).getText());
		  	Assertions.assertEquals("Ver Detalles", this.driver.findElement(By.linkText("Ver Detalles")).getText());
		  	Assertions.assertEquals("Laso Escot, Maria", this.driver.findElement(By.xpath("//table[@id='patientsTable']/tbody/tr[2]/td")).getText());
		  	Assertions.assertEquals("Ver Detalles", this.driver.findElement(By.xpath("(//a[contains(text(),'Ver Detalles')])[2]")).getText());
		  	Assertions.assertEquals("Rovira Vázquez, Jaime", this.driver.findElement(By.xpath("//table[@id='patientsTable']/tbody/tr[3]/td")).getText());
		  	Assertions.assertEquals("Ver Detalles", this.driver.findElement(By.xpath("(//a[contains(text(),'Ver Detalles')])[3]")).getText());
		  	Assertions.assertEquals("Perez Lopez, Lucas", this.driver.findElement(By.xpath("//table[@id='patientsTable']/tbody/tr[4]/td")).getText());
		  	Assertions.assertEquals("Ver Detalles", this.driver.findElement(By.xpath("(//a[contains(text(),'Ver Detalles')])[4]")).getText());
		    this.driver.findElement(By.xpath("(//a[contains(text(),'Ver Detalles')])[4]")).click();

			return this;
		}

	  private ListPatinentsAndShowDetailsUITest thenISeePatientDetails() {

		  Assertions.assertEquals("Detalles del paciente", this.driver.findElement(By.xpath("//h2")).getText());
		  Assertions.assertEquals("Nombre completo", this.driver.findElement(By.xpath("//th")).getText());
		  Assertions.assertEquals("Fecha de nacimiento", this.driver.findElement(By.xpath("//tr[2]/th")).getText());
		  Assertions.assertEquals("Dirección", this.driver.findElement(By.xpath("//tr[3]/th")).getText());
		  Assertions.assertEquals("Provincia", this.driver.findElement(By.xpath("//tr[4]/th")).getText());
		  Assertions.assertEquals("NSS", this.driver.findElement(By.xpath("//tr[5]/th")).getText());
		  Assertions.assertEquals("DNI", this.driver.findElement(By.xpath("//tr[6]/th")).getText());
		  Assertions.assertEquals("Correo electrónico", this.driver.findElement(By.xpath("//tr[7]/th")).getText());
		  Assertions.assertEquals("Teléfono principal", this.driver.findElement(By.xpath("//tr[8]/th")).getText());
		  Assertions.assertEquals("Teléfono secundario", this.driver.findElement(By.xpath("//tr[9]/th")).getText());
		  Assertions.assertEquals("Médico de cabecera", this.driver.findElement(By.xpath("//tr[10]/th")).getText());

		  Assertions.assertEquals("Lucas Perez Lopez", this.driver.findElement(By.xpath("//b")).getText());
		  Assertions.assertEquals("1999-08-28", this.driver.findElement(By.xpath("//tr[2]/td")).getText());
		  Assertions.assertEquals("C/García Lorca", this.driver.findElement(By.xpath("//tr[3]/td")).getText());
		  Assertions.assertEquals("Utrera", this.driver.findElement(By.xpath("//tr[4]/td")).getText());
		  Assertions.assertEquals("12345678912", this.driver.findElement(By.xpath("//tr[5]/td")).getText());
		  Assertions.assertEquals("12435655S", this.driver.findElement(By.xpath("//tr[6]/td")).getText());
		  Assertions.assertEquals("lucas@gmail.com", this.driver.findElement(By.xpath("//tr[7]/td")).getText());
		  Assertions.assertEquals("956784321", this.driver.findElement(By.xpath("//tr[8]/td")).getText());
		  Assertions.assertEquals("953334333", this.driver.findElement(By.xpath("//tr[9]/td")).getText());
		  Assertions.assertEquals("Laso Escot, María", this.driver.findElement(By.xpath("//tr[10]/td")).getText());

			return this;
		}

	  private ListPatinentsAndShowDetailsUITest thenILoggedOut() {

		  	this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).click();
		  	Assertions.assertEquals(this.username.toUpperCase(), this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).getText());
		    this.driver.findElement(By.linkText("Logout")).click();
		    Assertions.assertEquals("Log Out",
				this.driver.findElement(By.xpath("//button[@type='submit']")).getText());
		    this.driver.findElement(By.xpath("//button[@type='submit']")).click();


			return this;
		}

	  @Test
		public void testListAndSeeDeatailsPatientUI() throws Exception {

			this.as("admin", "admin").whenIamLoggedInTheSystem().thenISeeMyUsernameInTheMenuBar().thenIListPPatients()
										.thenISeePatientDetails().thenILoggedOut();
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
