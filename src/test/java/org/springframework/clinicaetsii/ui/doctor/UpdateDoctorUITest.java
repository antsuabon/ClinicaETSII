package org.springframework.clinicaetsii.ui.doctor;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class UpdateDoctorUITest {

	@LocalServerPort
	private int port;


	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	private String username;

	private String name;
	private String surname;
	private String dni;
	private String email;
	private String phone;
	private String newUsername;
	private String newPassword;
	private String repeatPassword;
	private String collegiateCode;

	@BeforeEach
	public void setUp() throws Exception {
		String pathToGeckoDriver = "D:\\geckodriver";
		System.setProperty("webdriver.gecko.driver", pathToGeckoDriver + "\\geckodriver.exe");
		this.driver = new FirefoxDriver();

		this.baseUrl = "https://www.google.com/";
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		this.driver.get("http://localhost:" + this.port);
	}

	private UpdateDoctorUITest as(final String username, final String password) {
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

	private UpdateDoctorUITest whenIamLoggedInTheSystem() {
		return this;
	}

	private UpdateDoctorUITest thenISeeMyUsernameInTheMenuBar() {
		Assertions.assertEquals(this.username.toUpperCase(),
				this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).getText());
		return this;
	}

	private UpdateDoctorUITest thenIEnterMyProfile() {
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).click();
		Assertions.assertEquals("Mi perfil".toUpperCase(),
				this.driver
						.findElement(
								By.xpath("//div[@id='main-navbar']/ul[2]/li/ul/li[3]/a/span[2]"))
						.getText());
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/ul/li[3]/a/span[2]"))
				.click();

		Assertions.assertEquals("Datos Personales",
				this.driver.findElement(By.xpath("//h2")).getText());
		Assertions.assertEquals("Nombre completo",
				this.driver.findElement(By.xpath("//th")).getText());
		Assertions.assertEquals("DNI", this.driver.findElement(By.xpath("//tr[2]/th")).getText());
		Assertions.assertEquals("Correo electrónico",
				this.driver.findElement(By.xpath("//tr[3]/th")).getText());
		Assertions.assertEquals("Teléfono",
				this.driver.findElement(By.xpath("//tr[4]/th")).getText());
		Assertions.assertEquals("Datos Médicos",
				this.driver.findElement(By.xpath("//h2[2]")).getText());
		Assertions.assertEquals("Número de colegiado",
				this.driver.findElement(By.xpath("//table[2]/tbody/tr/th")).getText());
		Assertions.assertEquals("Cartera de Servicios",
				this.driver.findElement(By.xpath("//h2[3]")).getText());
		return this;
	}

	private UpdateDoctorUITest thenIEnterUpdateForm() {
		this.driver.findElement(By.xpath("//a[contains(text(),'Editar perfil')]")).click();

		Assertions.assertEquals("Médico", this.driver.findElement(By.xpath("//h2")).getText());
		Assertions.assertEquals("Datos Personales", this.driver
				.findElement(By.xpath("//form[@id='update-doctor-form']/div/h3")).getText());
		Assertions.assertEquals("Nombre", this.driver
				.findElement(By.xpath("//form[@id='update-doctor-form']/div/div/label")).getText());
		Assertions.assertEquals("Apellidos",
				this.driver
						.findElement(By.xpath("//form[@id='update-doctor-form']/div/div[2]/label"))
						.getText());
		Assertions.assertEquals("DNI",
				this.driver
						.findElement(By.xpath("//form[@id='update-doctor-form']/div/div[3]/label"))
						.getText());
		Assertions.assertEquals("Correo electrónico",
				this.driver
						.findElement(By.xpath("//form[@id='update-doctor-form']/div/div[4]/label"))
						.getText());
		Assertions.assertEquals("Teléfono",
				this.driver
						.findElement(By.xpath("//form[@id='update-doctor-form']/div/div[5]/label"))
						.getText());
		Assertions.assertEquals("Datos de Usuario", this.driver
				.findElement(By.xpath("//form[@id='update-doctor-form']/div/h3[2]")).getText());
		Assertions.assertEquals("Nombre de usuario",
				this.driver
						.findElement(By.xpath("//form[@id='update-doctor-form']/div/div[6]/label"))
						.getText());
		Assertions.assertEquals("Nueva contraseña",
				this.driver
						.findElement(By.xpath("//form[@id='update-doctor-form']/div/div[7]/label"))
						.getText());
		Assertions.assertEquals("Nueva contraseña",
				this.driver
						.findElement(By.xpath("//form[@id='update-doctor-form']/div/div[8]/label"))
						.getText());
		Assertions.assertEquals("Datos Médicos", this.driver
				.findElement(By.xpath("//form[@id='update-doctor-form']/div/h3[3]")).getText());
		Assertions.assertEquals("Número de colegiado",
				this.driver
						.findElement(By.xpath("//form[@id='update-doctor-form']/div/div[9]/label"))
						.getText());
		Assertions.assertEquals("Servicios",
				this.driver
						.findElement(
								By.xpath("//form[@id='update-doctor-form']/div/div[10]/div/label"))
						.getText());

		this.driver.findElement(By.id("doctor.name")).click();
		this.driver.findElement(By.id("doctor.name")).clear();
		this.driver.findElement(By.id("doctor.name")).sendKeys(this.name);
		this.driver.findElement(By.id("doctor.surname")).click();
		this.driver.findElement(By.id("doctor.surname")).clear();
		this.driver.findElement(By.id("doctor.surname")).sendKeys(this.surname);
		this.driver.findElement(By.id("doctor.dni")).click();
		this.driver.findElement(By.id("doctor.dni")).clear();
		this.driver.findElement(By.id("doctor.dni")).sendKeys(this.dni);
		this.driver.findElement(By.id("doctor.email")).click();
		this.driver.findElement(By.id("doctor.email")).clear();
		this.driver.findElement(By.id("doctor.email")).sendKeys(this.email);
		this.driver.findElement(By.id("doctor.phone")).click();
		this.driver.findElement(By.id("doctor.phone")).clear();
		this.driver.findElement(By.id("doctor.phone")).sendKeys(this.phone);
		this.driver.findElement(By.id("doctor.username")).click();
		this.driver.findElement(By.id("doctor.username")).clear();
		this.driver.findElement(By.id("doctor.username")).sendKeys(this.newUsername);
		this.driver.findElement(By.id("newPassword")).click();
		this.driver.findElement(By.id("newPassword")).clear();
		this.driver.findElement(By.id("newPassword")).sendKeys(this.newPassword);
		this.driver.findElement(By.id("repeatPassword")).click();
		this.driver.findElement(By.id("repeatPassword")).clear();
		this.driver.findElement(By.id("repeatPassword")).sendKeys(this.repeatPassword);
		this.driver.findElement(By.id("doctor.collegiateCode")).click();
		this.driver.findElement(By.id("doctor.collegiateCode")).clear();
		this.driver.findElement(By.id("doctor.collegiateCode")).sendKeys(this.collegiateCode);


		((JavascriptExecutor) this.driver).executeScript("arguments[0].scrollIntoView();",
				this.driver.findElement(By.xpath("//button[@type='submit']")));

		Actions actions = new Actions(this.driver);
		actions.keyDown(Keys.LEFT_CONTROL)
				.click(this.driver.findElement(By.xpath("//option[@value='10']")))
				.click(this.driver.findElement(By.xpath("//option[@value='11']")))
				.keyUp(Keys.LEFT_CONTROL).build().perform();

		Assertions.assertEquals("Actualizar perfil",
				this.driver.findElement(By.xpath("//button[@type='submit']")).getText());
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		return this;
	}

	private UpdateDoctorUITest thenILoggedOut() {
		Assertions.assertEquals("Log Out",
				this.driver.findElement(By.xpath("//button[@type='submit']")).getText());
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		return this;
	}

	private UpdateDoctorUITest thenISeeMyUpdatedProfile() {
		Assertions.assertEquals(this.surname + ", " + this.name,
				this.driver.findElement(By.xpath("//b")).getText());
		Assertions.assertEquals(this.dni,
				this.driver.findElement(By.xpath("//tr[2]/td")).getText());
		Assertions.assertEquals(this.email,
				this.driver.findElement(By.xpath("//tr[3]/td")).getText());
		Assertions.assertEquals(this.phone,
				this.driver.findElement(By.xpath("//tr[4]/td")).getText());
		Assertions.assertEquals(this.collegiateCode,
				this.driver.findElement(By.xpath("//table[2]/tbody/tr/td")).getText());
		return this;
	}

	private UpdateDoctorUITest thenISeeFormWithErrors() {
		Assertions.assertEquals("Este campo es obligatorio",
				this.driver
						.findElement(
								By.xpath("//form[@id='update-doctor-form']/div/div/div/span[2]"))
						.getText());
		Assertions.assertEquals("Este campo es obligatorio",
				this.driver
						.findElement(
								By.xpath("//form[@id='update-doctor-form']/div/div[2]/div/span[2]"))
						.getText());
		Assertions.assertEquals("El campo debe seguir el formato: 12345678A",
				this.driver
						.findElement(
								By.xpath("//form[@id='update-doctor-form']/div/div[3]/div/span[2]"))
						.getText());
		Assertions.assertEquals("No se ha introducido un Email adecuado",
				this.driver
						.findElement(
								By.xpath("//form[@id='update-doctor-form']/div/div[4]/div/span[2]"))
						.getText());
		Assertions.assertEquals("No se ha introducido un número de teléfono adecuado",
				this.driver
						.findElement(
								By.xpath("//form[@id='update-doctor-form']/div/div[5]/div/span[2]"))
						.getText());
		Assertions.assertEquals("Este campo es obligatorio",
				this.driver
						.findElement(
								By.xpath("//form[@id='update-doctor-form']/div/div[6]/div/span[2]"))
						.getText());
		Assertions.assertEquals(
				"La contraseña debe tener entre 6 y 20 caracteres y debe contener al menos un caracter en minúscula, un caracter en mayúscula y un dígito",
				this.driver
						.findElement(
								By.xpath("//form[@id='update-doctor-form']/div/div[7]/div/span[2]"))
						.getText());
		Assertions.assertEquals("La contraseña no coincide",
				this.driver
						.findElement(
								By.xpath("//form[@id='update-doctor-form']/div/div[8]/div/span[2]"))
						.getText());
		Assertions.assertEquals("Este campo debe de estar formado por 9 dígitos",
				this.driver
						.findElement(
								By.xpath("//form[@id='update-doctor-form']/div/div[9]/div/span[2]"))
						.getText());
		return this;
	}

	@ParameterizedTest
	@CsvSource({
			"Pablo Prueba, Rodriguez Garrido Prueba, 32145678A, pabloPrueba@gmail.com, +(600) 300 200, doctor1Prueba, aaaaaaA1, aaaaaaA1, 303312345"})
	public void testUpdateDoctorUI(final String name,
			final String surname,
			final String dni,
			final String email,
			final String phone,
			final String newUsername,
			final String newPassword,
			final String repeatPassword,
			final String collegiateCode) throws Exception {

		this.name = name;
		this.surname = surname;
		this.dni = dni;
		this.email = email;
		this.phone = phone;
		this.newUsername = newUsername;
		this.newPassword = newPassword;
		this.repeatPassword = repeatPassword;
		this.collegiateCode = collegiateCode;


		as("doctor1", "doctor1").whenIamLoggedInTheSystem().thenISeeMyUsernameInTheMenuBar()
				.thenIEnterMyProfile().thenIEnterUpdateForm().thenILoggedOut();

		if (newUsername.equals(this.username)) {
			as(newUsername, newPassword).whenIamLoggedInTheSystem().thenISeeMyUsernameInTheMenuBar()
					.thenIEnterMyProfile().thenISeeMyUpdatedProfile();
		}

	}

	@ParameterizedTest
	@CsvSource({
			"'', '', 12345678NPrueba, pablogmail.com, 955555555Prueba, '', contra, nocontra, 303012345Prueba"})
	public void testNotUpdateDoctorUI(final String name,
			final String surname,
			final String dni,
			final String email,
			final String phone,
			final String newUsername,
			final String newPassword,
			final String repeatPassword,
			final String collegiateCode) throws Exception {

		this.name = name;
		this.surname = surname;
		this.dni = dni;
		this.email = email;
		this.phone = phone;
		this.newUsername = newUsername;
		this.newPassword = newPassword;
		this.repeatPassword = repeatPassword;
		this.collegiateCode = collegiateCode;

		as("doctor1", "doctor1").whenIamLoggedInTheSystem().thenISeeMyUsernameInTheMenuBar()
				.thenIEnterMyProfile().thenIEnterUpdateForm().thenISeeFormWithErrors();
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
