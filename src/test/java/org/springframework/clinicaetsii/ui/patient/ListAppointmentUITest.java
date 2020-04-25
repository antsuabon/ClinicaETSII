package org.springframework.clinicaetsii.ui.patient;

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
public class ListAppointmentUITest {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	private String username;
	// private String name;
	// private String surname;
	// private String dni;
	// private String email;
	// private String phone;
	// private String birthDate;
	// private String phone2;
	// private String nss;
	// private String state;
	// private String address;

	@BeforeEach
	public void setUp() throws Exception {
		// System.setProperty("webdriver.gecko.driver","C:\\Users\\angel\\Downloads\\webdrivers\\geckodriver.exe");
		// this.driver = new FirefoxDriver();
		System.setProperty("webdriver.chrome.driver",
				"D:\\Aplicaciones\\chromedriver_win32\\chromedriver.exe");
		this.driver = new ChromeDriver();
		this.baseUrl = "https://www.google.com/";
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		this.driver.get("http://localhost:" + this.port);
	}

	private ListAppointmentUITest as(final String username, final String password) {
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

	private ListAppointmentUITest whenIamLoggedInTheSystem() {
		return this;
	}

	private ListAppointmentUITest thenISeeMyUsernameInTheMenuBar() {
		Assertions.assertEquals(this.username.toUpperCase(),
				this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).getText());
		return this;
	}


	private ListAppointmentUITest thenIEnterListAppointment() {

		this.driver.findElement(By.xpath("//a[contains(text(),'Paciente')]")).click();
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/ul/li/a/span[2]"))
				.click();

		Assertions.assertEquals("Proximas Citas",
				this.driver.findElement(By.xpath("//h2")).getText());
		Assertions.assertEquals("Fecha de Inicio", this.driver
				.findElement(By.xpath("//table[@id='appointmentsTable1']/thead/tr/th")).getText());
		Assertions.assertEquals("Fecha de Fin",
				this.driver
						.findElement(By.xpath("//table[@id='appointmentsTable1']/thead/tr/th[2]"))
						.getText());
		Assertions.assertEquals("14/03/2020 11:00", this.driver
				.findElement(By.xpath("//table[@id='appointmentsTable1']/tbody/tr/td")).getText());
		Assertions.assertEquals("14/03/2020 11:07",
				this.driver
						.findElement(By.xpath("//table[@id='appointmentsTable1']/tbody/tr/td[2]"))
						.getText());
		Assertions.assertEquals("Eliminar Cita",
				this.driver.findElement(By.linkText("Eliminar Cita")).getText());
		Assertions.assertEquals("Citas Pasadas",
				this.driver.findElement(By.xpath("//h2[2]")).getText());
		Assertions.assertEquals("Fecha de Inicio",
				this.driver.findElement(By.xpath("(//table[@id='appointmentsTable2']/thead/tr/th)"))
						.getText());
		Assertions.assertEquals("Fecha de Fin",
				this.driver
						.findElement(By.xpath("(//table[@id='appointmentsTable2']/thead/tr/th[2])"))
						.getText());
		Assertions.assertEquals("07/03/2020 11:00",
				this.driver.findElement(By.xpath("(//table[@id='appointmentsTable2']/tbody/tr/td)"))
						.getText());
		Assertions.assertEquals("07/03/2020 11:07",
				this.driver
						.findElement(By.xpath("(//table[@id='appointmentsTable2']/tbody/tr/td[2])"))
						.getText());
		Assertions.assertEquals("09/03/2020 11:00",
				this.driver
						.findElement(By.xpath("//table[@id='appointmentsTable2']/tbody/tr[2]/td"))
						.getText());
		Assertions.assertEquals("09/03/2020 11:07",
				this.driver
						.findElement(
								By.xpath("//table[@id='appointmentsTable2']/tbody/tr[2]/td[2]"))
						.getText());

		return this;
	}

	private ListAppointmentUITest thenIEnterNotListAppointment() {

		this.driver.findElement(By.xpath("//a[contains(text(),'Paciente')]")).click();
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/ul/li/a/span[2]"))
				.click();

		Assertions.assertNotEquals("Proximas", this.driver.findElement(By.xpath("//h2")).getText());
		Assertions.assertNotEquals("Fecha", this.driver
				.findElement(By.xpath("//table[@id='appointmentsTable1']/thead/tr/th")).getText());
		Assertions.assertNotEquals("Fecha",
				this.driver
						.findElement(By.xpath("//table[@id='appointmentsTable1']/thead/tr/th[2]"))
						.getText());
		Assertions.assertNotEquals("14/03/2020 11:05", this.driver
				.findElement(By.xpath("//table[@id='appointmentsTable1']/tbody/tr/td")).getText());
		Assertions.assertNotEquals("14/03/2020 11:05",
				this.driver
						.findElement(By.xpath("//table[@id='appointmentsTable1']/tbody/tr/td[2]"))
						.getText());
		Assertions.assertNotEquals("Eliminar",
				this.driver.findElement(By.linkText("Eliminar Cita")).getText());
		Assertions.assertNotEquals("Citas", this.driver.findElement(By.xpath("//h2[2]")).getText());
		Assertions.assertNotEquals("Fecha",
				this.driver.findElement(By.xpath("(//table[@id='appointmentsTable2']/thead/tr/th)"))
						.getText());
		Assertions.assertNotEquals("Fecha",
				this.driver
						.findElement(By.xpath("(//table[@id='appointmentsTable2']/thead/tr/th[2])"))
						.getText());
		Assertions.assertNotEquals("07/03/2020 11:05",
				this.driver.findElement(By.xpath("(//table[@id='appointmentsTable2']/tbody/tr/td)"))
						.getText());
		Assertions.assertNotEquals("07/03/2020 11:05",
				this.driver
						.findElement(By.xpath("(//table[@id='appointmentsTable2']/tbody/tr/td[2])"))
						.getText());
		Assertions.assertNotEquals("09/03/2020 11:05",
				this.driver
						.findElement(By.xpath("//table[@id='appointmentsTable2']/tbody/tr[2]/td"))
						.getText());
		Assertions.assertNotEquals("09/03/2020 11:05",
				this.driver
						.findElement(
								By.xpath("//table[@id='appointmentsTable2']/tbody/tr[2]/td[2]"))
						.getText());

		return this;
	}

	private ListAppointmentUITest thenIDeleteAnAppointment() {
		this.driver.findElement(By.linkText("Eliminar Cita")).click();
		Assertions.assertEquals("No se han encontrado consultas registradas para ese paciente",
				this.driver.findElement(By.xpath("//body/div/div/p")).getText());
		return this;
	}


	private ListAppointmentUITest thenILoggedOut() {

		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).click();
		Assertions.assertEquals(this.username.toUpperCase(), this.driver
				.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).getText());
		this.driver.findElement(By.linkText("Logout")).click();
		Assertions.assertEquals("Log Out",
				this.driver.findElement(By.xpath("//button[@type='submit']")).getText());
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();


		return this;
	}


	@Test
	public void testListAppointmentsUI() throws Exception {


		as("patient1", "patient1").whenIamLoggedInTheSystem().thenISeeMyUsernameInTheMenuBar()
				.thenIEnterListAppointment().thenIDeleteAnAppointment().thenILoggedOut();

	}

	@Test
	public void testNotListAppointmentsUI() throws Exception {


		as("patient1", "patient1").whenIamLoggedInTheSystem().thenISeeMyUsernameInTheMenuBar()
				.thenIEnterNotListAppointment();

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
