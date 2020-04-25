package org.springframework.clinicaetsii.ui.doctor;

import static org.junit.Assert.assertEquals;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NewConsultationUITest {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	private String username;

	private String fullName;
	private String appointmentStartTime;
	private String consultationStartTime;
	private String anamnesis;
	private String remarks;
	private String examination;
	private String constantType;
	private String value;

	@BeforeEach
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver",
				"D:\\Aplicaciones\\chromedriver_win32\\chromedriver.exe");
		this.driver = new ChromeDriver();
		this.baseUrl = "https://www.google.com/";
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		this.driver.get("http://localhost:" + this.port);
	}

	private NewConsultationUITest as(final String username) {
		this.username = username;

		this.driver.findElement(By.xpath("//a[contains(text(),'Iniciar sesión')]")).click();

		this.driver.findElement(By.id("username")).click();
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys(username);

		this.driver.findElement(By.id("password")).click();
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys(passwordOf(username));

		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		return this;
	}

	private NewConsultationUITest whenIamLoggedInTheSystem() {
		return this;
	}

	private NewConsultationUITest thenISeeMyUsernameInTheMenuBar() {
		Assertions.assertEquals(this.username.toUpperCase(),
				this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).getText());
		return this;
	}

	private NewConsultationUITest thenISeeMyRoleDropdownInTheMenuBar() {
		Assertions.assertEquals("Médico".toUpperCase(),
				this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/a")).getText());
		return this;
	}

	private NewConsultationUITest thenIEnterAppointmentsList() {
		this.driver.findElement(By.xpath("//a[contains(text(),'Médico')]")).click();
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/ul/li[2]/a/span[2]"))
				.click();
		assertEquals("Lista de Citas por Atender",
				this.driver.findElement(By.xpath("//h2")).getText());
		assertEquals("Hora de inicio", this.driver
				.findElement(By.xpath("//table[@id='appointmentsTable']/thead/tr/th")).getText());
		assertEquals("Hora de fin",
				this.driver.findElement(By.xpath("//table[@id='appointmentsTable']/thead/tr/th[2]"))
						.getText());
		assertEquals("Nombre Completo",
				this.driver.findElement(By.xpath("//table[@id='appointmentsTable']/thead/tr/th[3]"))
						.getText());
		assertEquals(this.appointmentStartTime, this.driver
				.findElement(By.xpath("//table[@id='appointmentsTable']/tbody/tr/td")).getText());
		assertEquals(this.fullName,
				this.driver.findElement(By.xpath("//table[@id='appointmentsTable']/tbody/tr/td[3]"))
						.getText());
		assertEquals("Añadir consulta", this.driver
				.findElement(By.xpath("//a[contains(text(),'Añadir consulta')]")).getText());
		return this;
	}

	private NewConsultationUITest thenIEnterNewConsultationForm() {
		this.driver.findElement(By.xpath("//a[contains(text(),'Añadir consulta')]")).click();

		assertEquals("Nueva Consulta", this.driver.findElement(By.xpath("//h2")).getText());
		assertEquals("Paciente", this.driver
				.findElement(By.xpath("//form[@id='consultation']/div/div/label")).getText());
		assertEquals("Fecha de inicio",
				this.driver.findElement(By.xpath("//form[@id='consultation']/div/div[2]/div/label"))
						.getText());
		assertEquals("Anamnesis", this.driver
				.findElement(By.xpath("//form[@id='consultation']/div/div[3]/label")).getText());
		assertEquals("Observaciones", this.driver
				.findElement(By.xpath("//form[@id='consultation']/div/div[4]/label")).getText());

		assertEquals("Sánchez Saavedra, Alejandro", this.driver
				.findElement(By.xpath("//form[@id='consultation']/div/div/div")).getText());

		this.driver.findElement(By.id("startTime")).click();
		this.driver.findElement(By.id("startTime")).clear();
		this.driver.findElement(By.id("startTime")).sendKeys(this.consultationStartTime);

		this.driver.findElement(By.id("anamnesis")).click();
		this.driver.findElement(By.id("anamnesis")).clear();
		this.driver.findElement(By.id("anamnesis")).sendKeys(this.anamnesis);

		this.driver.findElement(By.id("remarks")).click();
		this.driver.findElement(By.id("remarks")).clear();
		this.driver.findElement(By.id("remarks")).sendKeys(this.remarks);

		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		return this;
	}

	private NewConsultationUITest thenISeeNewConsultationFormWithErrors() {
		assertEquals("La fecha de inicio de la consulta debe ser igual o posterior al de la cita",
				this.driver
						.findElement(
								By.xpath("//form[@id='consultation']/div/div[2]/div/div/span[2]"))
						.getText());
		return this;
	}

	private NewConsultationUITest thenISeeNewConsultationDetails() {
		assertEquals("Detalles Consulta", this.driver.findElement(By.xpath("//h2")).getText());
		assertEquals("Fecha y hora de inicio", this.driver.findElement(By.xpath("//th")).getText());
		assertEquals("Fecha y hora de fin",
				this.driver.findElement(By.xpath("//tr[2]/th")).getText());
		assertEquals("Anamnesis", this.driver.findElement(By.xpath("//tr[3]/th")).getText());
		assertEquals("Observaciones", this.driver.findElement(By.xpath("//tr[4]/th")).getText());
		assertEquals("Tipo de alta", this.driver.findElement(By.xpath("//tr[5]/th")).getText());
		assertEquals("Diagnósticos", this.driver.findElement(By.xpath("//h3")).getText());
		assertEquals("Exploraciones", this.driver.findElement(By.xpath("//h3[2]")).getText());
		assertEquals("Constantes", this.driver.findElement(By.xpath("//h3[3]")).getText());
		assertEquals("Editar Consulta", this.driver
				.findElement(By.xpath("//a[contains(text(),'Editar Consulta')]")).getText());
		assertEquals("Añadir exploración", this.driver
				.findElement(By.xpath("//a[contains(text(),'Añadir exploración')]")).getText());
		assertEquals("Añadir Constante", this.driver
				.findElement(By.xpath("//a[contains(text(),'Añadir Constante')]")).getText());
		return this;
	}

	private NewConsultationUITest thenIEnterNewExamination() {
		this.driver.findElement(By.xpath("//a[contains(text(),'Añadir exploración')]")).click();

		assertEquals("Nueva exploración", this.driver.findElement(By.xpath("//h2")).getText());
		assertEquals("Description",
				this.driver
						.findElement(By.xpath("//form[@id='add-examination-form']/div/div/label"))
						.getText());
		assertEquals("Añadir exploración",
				this.driver.findElement(By.xpath("//button[@type='submit']")).getText());

		this.driver.findElement(By.id("description")).click();
		this.driver.findElement(By.id("description")).clear();
		this.driver.findElement(By.id("description")).sendKeys(this.examination);

		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		return this;
	}

	private NewConsultationUITest thenIEnterNewConstant() {
		this.driver.findElement(By.xpath("//a[contains(text(),'Añadir Constante')]")).click();

		assertEquals("Nueva Constante", this.driver.findElement(By.xpath("//h2")).getText());
		assertEquals("Tipo de constante", this.driver
				.findElement(By.xpath("//form[@id='constant']/div/div[2]/div/label")).getText());
		assertEquals("Valor", this.driver
				.findElement(By.xpath("//form[@id='constant']/div/div[3]/div/label")).getText());

		new Select(this.driver.findElement(By.id("constantType")))
				.selectByVisibleText(this.constantType);
		this.driver.findElement(By.xpath("//form[@id='constant']/div/div[3]/div/div")).click();
		this.driver.findElement(By.id("value")).clear();
		this.driver.findElement(By.id("value")).sendKeys(this.value);

		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		return this;
	}

	private NewConsultationUITest thenIUpdateConsultation() {
		this.driver.findElement(By.xpath("//a[contains(text(),'Editar Consulta')]")).click();

		assertEquals("Consulta", this.driver.findElement(By.xpath("//h2")).getText());
		assertEquals("Paciente", this.driver
				.findElement(By.xpath("//form[@id='consultation']/div/div/label")).getText());
		assertEquals("Fecha de inicio",
				this.driver.findElement(By.xpath("//form[@id='consultation']/div/div[2]/div/label"))
						.getText());
		assertEquals("Anamnesis", this.driver
				.findElement(By.xpath("//form[@id='consultation']/div/div[3]/label")).getText());
		assertEquals("Observaciones", this.driver
				.findElement(By.xpath("//form[@id='consultation']/div/div[4]/label")).getText());
		assertEquals("Diagnosticos",
				this.driver.findElement(By.xpath("//form[@id='consultation']/div/div[5]/div/label"))
						.getText());
		assertEquals("Alta",
				this.driver
						.findElement(By.xpath("//form[@id='consultation']/div/div[5]/div[2]/label"))
						.getText());

		this.driver.findElement(By.xpath("//option[@value='1']")).click();
		new Select(this.driver.findElement(By.id("dischargeType")))
				.selectByVisibleText("Domicilio");
		this.driver.findElement(By.xpath("(//option[@value='1'])[2]")).click();

		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		return this;
	}

	public NewConsultationUITest thenISeeUpdateConsultationFormWithErrors() {
		assertEquals("No es posible dar de alta una consulta sin exploraciones", this.driver
				.findElement(By.xpath("//form[@id='consultation']/div/div[5]/div[2]/div/span[2]"))
				.getText());
		return this;
	}

	private NewConsultationUITest thenICantSeeButtonsAnymore() {
		assertEquals(this.anamnesis, this.driver.findElement(By.xpath("//tr[3]/td")).getText());
		assertEquals(this.remarks, this.driver.findElement(By.xpath("//tr[4]/td")).getText());
		return this;
	}

	@ParameterizedTest
	@Order(3)
	@CsvSource({
			"'Sánchez Saavedra, Alejandro', 14/03/2020 11:00, 20/04/2020 22:51, Anamnesis de prueba, observaciones de prueba, Exploracion de prueba, Temperatura, 37"})
	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	public void shouldCreateNewConsultation(final String fullName,
			final String appointmentStartTime,
			final String consultationStartTime,
			final String anamnesis,
			final String remarks,
			final String examination,
			final String constantType,
			final String value) throws Exception {

		this.fullName = fullName;
		this.appointmentStartTime = appointmentStartTime;
		this.consultationStartTime = consultationStartTime;
		this.anamnesis = anamnesis;
		this.remarks = remarks;
		this.examination = examination;
		this.constantType = constantType;
		this.value = value;


		as("doctor1").whenIamLoggedInTheSystem().thenISeeMyUsernameInTheMenuBar()
				.thenISeeMyRoleDropdownInTheMenuBar().thenIEnterAppointmentsList()
				.thenIEnterNewConsultationForm().thenISeeNewConsultationDetails()
				.thenIEnterNewExamination().thenIEnterNewConstant().thenIUpdateConsultation()
				.thenICantSeeButtonsAnymore();
	}

	@ParameterizedTest
	@Order(1)
	@CsvSource({
			"'Sánchez Saavedra, Alejandro', 14/03/2020 11:00, 14/03/2020 10:00, '', '', '', Temperatura, 37"})
	public void shouldNotCreateNewConsultation(final String fullName,
			final String appointmentStartTime,
			final String consultationStartTime,
			final String anamnesis,
			final String remarks,
			final String examination,
			final String constantType,
			final String value) throws Exception {

		this.fullName = fullName;
		this.appointmentStartTime = appointmentStartTime;
		this.consultationStartTime = consultationStartTime;
		this.anamnesis = anamnesis;
		this.remarks = remarks;
		this.examination = examination;
		this.constantType = constantType;
		this.value = value;


		as("doctor1").whenIamLoggedInTheSystem().thenISeeMyUsernameInTheMenuBar()
				.thenISeeMyRoleDropdownInTheMenuBar().thenIEnterAppointmentsList()
				.thenIEnterNewConsultationForm().thenISeeNewConsultationFormWithErrors();
	}

	@ParameterizedTest
	@Order(2)
	@CsvSource({
			"'Sánchez Saavedra, Alejandro', 14/03/2020 11:00, 20/04/2020 22:51, '', '', '', Temperatura, 37"})
	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	public void shouldNotUpdateConsultation(final String fullName,
			final String appointmentStartTime,
			final String consultationStartTime,
			final String anamnesis,
			final String remarks,
			final String examination,
			final String constantType,
			final String value) throws Exception {

		this.fullName = fullName;
		this.appointmentStartTime = appointmentStartTime;
		this.consultationStartTime = consultationStartTime;
		this.anamnesis = anamnesis;
		this.remarks = remarks;
		this.examination = examination;
		this.constantType = constantType;
		this.value = value;


		as("doctor1").whenIamLoggedInTheSystem().thenISeeMyUsernameInTheMenuBar()
				.thenISeeMyRoleDropdownInTheMenuBar().thenIEnterAppointmentsList()
				.thenIEnterNewConsultationForm().thenIUpdateConsultation()
				.thenISeeUpdateConsultationFormWithErrors();
	}



	@AfterEach
	public void tearDown() throws Exception {
		this.driver.quit();
		String verificationErrorString = this.verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			Assertions.fail(verificationErrorString);
		}
	}

	private String passwordOf(final String username) {
		return username;
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
