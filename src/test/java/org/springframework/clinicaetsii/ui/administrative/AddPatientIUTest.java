package org.springframework.clinicaetsii.ui.administrative;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddPatientIUTest {


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

	  private AddPatientIUTest as(final String username, final String password) {
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

	  private AddPatientIUTest whenIamLoggedInTheSystem() {
			return this;
		}

	  private AddPatientIUTest thenISeeMyUsernameInTheMenuBar() {
			Assertions.assertEquals(this.username.toUpperCase(),
			this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).getText());
			return this;
		}

	  private AddPatientIUTest thenIEnterForm() {

		  	this.driver.findElement(By.xpath("//a[contains(text(),'Administrativo')]")).click();
//			this.driver.findElement(By.xpath("//a[contains(@href, '/admin/patients/new')]")).click();
		    this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/ul/li/a/span[2]")).click();

		  	Assertions.assertEquals("Nombre de usuario", this.driver.findElement(By.xpath("//form[@id='add-patient-form']/div/div/label")).getText());
		  	Assertions.assertEquals("Nombre", this.driver.findElement(By.xpath("//form[@id='add-patient-form']/div/div[2]/label")).getText());
		  	Assertions.assertEquals("Apellidos", this.driver.findElement(By.xpath("//form[@id='add-patient-form']/div/div[3]/label")).getText());
		  	Assertions.assertEquals("Fecha de nacimiento", this.driver.findElement(By.xpath("//form[@id='add-patient-form']/div/div[4]/label")).getText());
		  	Assertions.assertEquals("Dirección", this.driver.findElement(By.xpath("//form[@id='add-patient-form']/div/div[5]/label")).getText());
		  	Assertions.assertEquals("Provincia", this.driver.findElement(By.xpath("//form[@id='add-patient-form']/div/div[6]/label")).getText());
		  	Assertions.assertEquals("NSS", this.driver.findElement(By.xpath("//form[@id='add-patient-form']/div/div[7]/label")).getText());
		  	Assertions.assertEquals("DNI", this.driver.findElement(By.xpath("//form[@id='add-patient-form']/div/div[8]/label")).getText());
		  	Assertions.assertEquals("Correo electrónico", this.driver.findElement(By.xpath("//form[@id='add-patient-form']/div/div[9]/label")).getText());
		  	Assertions.assertEquals("Teléfono pricipal", this.driver.findElement(By.xpath("//form[@id='add-patient-form']/div/div[10]/label")).getText());
		  	Assertions.assertEquals("Teléfono secundario", this.driver.findElement(By.xpath("//form[@id='add-patient-form']/div/div[11]/label")).getText());
		  	Assertions.assertEquals("Médicos disponibles", this.driver.findElement(By.xpath("//form[@id='add-patient-form']/div/div[12]/div/label")).getText());


		  	this.driver.findElement(By.id("username")).click();
		    this.driver.findElement(By.id("username")).clear();
		    this.driver.findElement(By.id("username")).sendKeys(this.user);
		    this.driver.findElement(By.id("name")).click();
		    this.driver.findElement(By.id("name")).clear();
		    this.driver.findElement(By.id("name")).sendKeys(this.name);
		    this.driver.findElement(By.id("surname")).click();
		    this.driver.findElement(By.id("surname")).clear();
		    this.driver.findElement(By.id("surname")).sendKeys(this.surname);
		    this.driver.findElement(By.id("birthDate")).click();
		    this.driver.findElement(By.id("birthDate")).clear();
		    this.driver.findElement(By.id("birthDate")).sendKeys(this.birthDate);
		    this.driver.findElement(By.id("address")).click();
		    this.driver.findElement(By.id("address")).clear();
		    this.driver.findElement(By.id("address")).sendKeys(this.address);
		    this.driver.findElement(By.id("state")).click();
		    this.driver.findElement(By.id("state")).clear();
		    this.driver.findElement(By.id("state")).sendKeys(this.state);
		    this.driver.findElement(By.id("dni")).click();
		    this.driver.findElement(By.id("dni")).clear();
		    this.driver.findElement(By.id("dni")).sendKeys(this.dni);
		    this.driver.findElement(By.id("nss")).click();
		    this.driver.findElement(By.id("nss")).clear();
		    this.driver.findElement(By.id("nss")).sendKeys(this.nss);
		    this.driver.findElement(By.id("email")).click();
		    this.driver.findElement(By.id("email")).clear();
		    this.driver.findElement(By.id("email")).sendKeys(this.email);
		    this.driver.findElement(By.id("phone")).click();
		    this.driver.findElement(By.id("phone")).clear();
		    this.driver.findElement(By.id("phone")).sendKeys(this.phone);
		    this.driver.findElement(By.id("phone2")).click();
		    this.driver.findElement(By.id("phone2")).clear();
		    this.driver.findElement(By.id("phone2")).sendKeys(this.phone2);
		    this.driver.findElement(By.id("generalPractitioner")).click();
		    new Select(this.driver.findElement(By.id("generalPractitioner"))).selectByVisibleText("Laso Escot, María");
		    this.driver.findElement(By.xpath("//option[@value='2']")).click();

		    Assertions.assertEquals("Crear Paciente", this.driver.findElement(By.xpath("//button[@type='submit']")).getText());
		    this.driver.findElement(By.xpath("//button[@type='submit']")).click();

		return this;

	  }

	  private AddPatientIUTest thenILoggedOut() {

		  	this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).click();
		  	Assertions.assertEquals(this.username.toUpperCase(), this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).getText());
		    this.driver.findElement(By.linkText("Logout")).click();
		    Assertions.assertEquals("Log Out",
				this.driver.findElement(By.xpath("//button[@type='submit']")).getText());
		    this.driver.findElement(By.xpath("//button[@type='submit']")).click();


			return this;
		}

	  private AddPatientIUTest thenISeeInPaientListNewPatient() {

		  	this.driver.findElement(By.xpath("//a[contains(text(),'Administrativo')]")).click();
		  	this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/ul/li[2]/a/span[2]")).click();

		    Assertions.assertEquals(this.surname + ", " + this.name, this.driver.findElement(By.xpath("//table[@id='patientsTable']/tbody/tr[4]/td")).getText());
		    Assertions.assertEquals("Ver detalles", this.driver.findElement(By.xpath("(//a[contains(text(),'Ver detalles')])[4]")).getText());
		    this.driver.findElement(By.xpath("(//a[contains(text(),'Ver detalles')])[4]")).click();

		    return this;
		}

	  private AddPatientIUTest thenISeePaientDetails() {

		  Assertions.assertEquals("Nombre de usuario", this.driver.findElement(By.xpath("//th")).getText());
		  Assertions.assertEquals("Nombre completo", this.driver.findElement(By.xpath("//tr[2]/th")).getText());
		  Assertions.assertEquals("Fecha de nacimiento", this.driver.findElement(By.xpath("//tr[3]/th")).getText());
		  Assertions.assertEquals("Dirección", this.driver.findElement(By.xpath("//tr[4]/th")).getText());
		  Assertions.assertEquals("Provincia", this.driver.findElement(By.xpath("//tr[5]/th")).getText());
		  Assertions.assertEquals("NSS", this.driver.findElement(By.xpath("//tr[6]/th")).getText());
		  Assertions.assertEquals("DNI", this.driver.findElement(By.xpath("//tr[7]/th")).getText());
		  Assertions.assertEquals("Correo electrónico", this.driver.findElement(By.xpath("//tr[8]/th")).getText());
		  Assertions.assertEquals("Teléfono principal", this.driver.findElement(By.xpath("//tr[9]/th")).getText());
		  Assertions.assertEquals("Teléfono secudario", this.driver.findElement(By.xpath("//tr[10]/th")).getText());

		  		String[] a = this.birthDate.split("/");

		  	  	String date = a[2] + "-" + a[1] + "-" + a[0];

		  	    Assertions.assertEquals(date, this.driver.findElement(By.xpath("//tr[3]/td")).getText());
		  	    Assertions.assertEquals(this.address, this.driver.findElement(By.xpath("//tr[4]/td")).getText());
		  	    Assertions.assertEquals(this.state, this.driver.findElement(By.xpath("//tr[5]/td")).getText());
		  	    Assertions.assertEquals(this.nss, this.driver.findElement(By.xpath("//tr[6]/td")).getText());
		  	    Assertions.assertEquals(this.dni, this.driver.findElement(By.xpath("//tr[7]/td")).getText());
		  	    Assertions.assertEquals(this.email, this.driver.findElement(By.xpath("//tr[8]/td")).getText());
		  	    Assertions.assertEquals(this.phone, this.driver.findElement(By.xpath("//tr[9]/td")).getText());
		  	    Assertions.assertEquals(this.phone2, this.driver.findElement(By.xpath("//tr[10]/td")).getText());
		  	    Assertions.assertEquals(this.name + " " + this.surname, this.driver.findElement(By.xpath("//tr[2]/td/b")).getText());
		  	    Assertions.assertEquals(this.user, this.driver.findElement(By.xpath("//b")).getText());

		    return this;
		}


	  private AddPatientIUTest thenISeeFormWithErrors() {

		    Assertions.assertEquals("Este campo es obligatorio", this.driver.findElement(By.xpath("//form[@id='add-patient-form']/div/div/div/span[2]")).getText());
		    Assertions.assertEquals("Este campo es obligatorio", this.driver.findElement(By.xpath("//form[@id='add-patient-form']/div/div[2]/div/span[2]")).getText());
		    Assertions.assertEquals("Este campo es obligatorio", this.driver.findElement(By.xpath("//form[@id='add-patient-form']/div/div[3]/div/span[2]")).getText());
		    Assertions.assertEquals("Este campo es obligatorio", this.driver.findElement(By.xpath("//form[@id='add-patient-form']/div/div[4]/div/span[2]")).getText());
		    Assertions.assertEquals("Este campo es obligatorio", this.driver.findElement(By.xpath("//form[@id='add-patient-form']/div/div[5]/div/span[2]")).getText());
		    Assertions.assertEquals("Este campo es obligatorio", this.driver.findElement(By.xpath("//form[@id='add-patient-form']/div/div[10]/div/span[2]")).getText());


		    if(this.email.isEmpty()) {
			    Assertions.assertEquals("Este campo es obligatorio", this.driver.findElement(By.xpath("//form[@id='add-patient-form']/div/div[9]/div/span[2]")).getText());
		    } else {
			    Assertions.assertEquals("No se ha introducido un Email adecuado", this.driver.findElement(By.xpath("//form[@id='add-patient-form']/div/div[9]/div/span[2]")).getText());
		    }

		    if(this.nss.isEmpty()) {
		    	Assertions.assertEquals("Este campo es obligatorio", this.driver.findElement(By.xpath("//form[@id='add-patient-form']/div/div[7]/div/span[2]")).getText());
		    } else {
		    	 Assertions.assertEquals("Este campo debe de estar formado por 11 dígitos", this.driver.findElement(By.xpath("//form[@id='add-patient-form']/div/div[7]/div/span[2]")).getText());
		    }

		    if(this.dni.isEmpty()) {
		    	Assertions.assertEquals("Este campo es obligatorio", this.driver.findElement(By.xpath("//form[@id='add-patient-form']/div/div[8]/div/span[2]")).getText());
		    } else {
		    	Assertions.assertEquals("El campo debe seguir el formato: 12345678A", this.driver.findElement(By.xpath("//form[@id='add-patient-form']/div/div[8]/div/span[2]")).getText());
		    }

		    return this;
		}


	  @ParameterizedTest
		@CsvSource({
				"Prueba, Pablo Prueba, Rodriguez Garrido Prueba, 32145678A, pabloPrueba@gmail.com, +(600) 300 200, 02/02/1999, +(600) 300 200, 12345678912, Prueba, C/Prueba"})
		public void testCreatePatientUI(final String user,
				final String name,
				final String surname,
				final String dni,
				final String email,
				final String phone,
				final String birthDate,
				final String phone2,
				final String nss,
				final String state,
				final String address
				) throws Exception {

		    this.user = user;
			this.name = name;
			this.surname = surname;
			this.dni = dni;
			this.email = email;
			this.phone = phone;
			this.birthDate = birthDate;
			this.phone2 = phone2;
			this.nss = nss;
			this.state = state;
			this.address = address;


			this.as("administrative1", "administrative1").whenIamLoggedInTheSystem().thenISeeMyUsernameInTheMenuBar()
					.thenIEnterForm().thenISeeInPaientListNewPatient().thenISeePaientDetails().thenILoggedOut();

		}


	  @ParameterizedTest
		@CsvSource({
			"'', '', '', 3214567444448A, pablogmail.com, '', '', +(600) 300 200, 12348912, '', ''"})
		public void testNotCreatePatientUI(final String user,
				final String name,
				final String surname,
				final String dni,
				final String email,
				final String phone,
				final String birthDate,
				final String phone2,
				final String nss,
				final String state,
				final String address
				) throws Exception {

		    this.user = user;
			this.name = name;
			this.surname = surname;
			this.dni = dni;
			this.email = email;
			this.phone = phone;
			this.birthDate = birthDate;
			this.phone2 = phone2;
			this.nss = nss;
			this.state = state;
			this.address = address;


			this.as("administrative1", "administrative1").whenIamLoggedInTheSystem().thenISeeMyUsernameInTheMenuBar()
					.thenIEnterForm().thenISeeFormWithErrors();

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
