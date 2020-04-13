
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
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FindExistingMedicineUITest {

	@LocalServerPort
	private int				port;

	private WebDriver		driver;
	private String			baseUrl;
	private boolean			acceptNextAlert		= true;
	private StringBuffer	verificationErrors	= new StringBuffer();

	private String			username;

	private String			nombreMedicamento;
	private String			nombrePractivo;
	private String			practivo1;
	private String			practivo2;
	private String			nombreLabtitular;
	private String			labtitular;
	private String			medicamento;


	@BeforeEach
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "C:\\webdrivers\\chromedriver.exe");
		this.driver = new ChromeDriver();
		this.baseUrl = "https://www.google.com/";
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		this.driver.get("http://localhost:" + this.port);
	}

	private FindExistingMedicineUITest as(final String username) {
		this.username = username;

		this.driver.findElement(By.xpath("//a[contains(text(),'Iniciar sesión')]")).click();

		this.driver.findElement(By.id("username")).click();
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys(username);

		this.driver.findElement(By.id("password")).click();
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys(this.passwordOf(username));

		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		return this;
	}

	private FindExistingMedicineUITest whenIamLoggedInTheSystem() {
		return this;
	}

	private FindExistingMedicineUITest thenISeeMyUsernameInTheMenuBar() {
		Assertions.assertEquals(this.username.toUpperCase(), this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).getText());
		return this;
	}

	private FindExistingMedicineUITest thenISeeMyRoleDropdownInTheMenuBar() {
		Assertions.assertEquals("Médico".toUpperCase(), this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/a")).getText());
		return this;
	}

	private FindExistingMedicineUITest thenIEnterMedicineSearchForm() {
		this.driver.findElement(By.xpath("//a[contains(text(),'Médico')]")).click();
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/ul/li[3]/a")).click();

		this.driver.findElement(By.id("nombre")).click();
		this.driver.findElement(By.id("nombre")).clear();
		this.driver.findElement(By.id("nombre")).sendKeys(this.nombreMedicamento);

		this.driver.findElement(By.id("pactivos")).click();
		this.driver.findElement(By.id("pactivos")).clear();
		this.driver.findElement(By.id("pactivos")).sendKeys(this.nombrePractivo);
		Assertions.assertEquals(this.practivo1, this.driver.findElement(By.xpath("//ul[@id='ui-id-1']/li[1]")).getText());
		Assertions.assertEquals(this.practivo2, this.driver.findElement(By.xpath("//ul[@id='ui-id-1']/li[2]")).getText());
		this.driver.findElement(By.xpath("//ul[@id='ui-id-1']/li[2]")).click();

		this.driver.findElement(By.id("labtitular")).click();
		this.driver.findElement(By.id("labtitular")).clear();
		this.driver.findElement(By.id("labtitular")).sendKeys(this.nombreLabtitular);
		Assertions.assertEquals(this.labtitular, this.driver.findElement(By.xpath("//ul[@id='ui-id-2']/li[1]")).getText());
		this.driver.findElement(By.xpath("//ul[@id='ui-id-2']/li[1]")).click();

		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		return this;
	}

	private FindExistingMedicineUITest thenISeeSearchResults() {
		Assertions.assertEquals("Medicamentos", this.driver.findElement(By.xpath("//h2")).getText());
		Assertions.assertEquals(this.medicamento, this.driver.findElement(By.xpath("//table[@id='medicamentosTable']/tbody/tr/td")).getText());
		this.driver.findElement(By.xpath("//a[contains(text(),'Ver detalles')]")).click();
		return this;
	}

	private FindExistingMedicineUITest thenISeeFirstResultDetails() {
		Assertions.assertEquals("Medicamento: " + this.medicamento, this.driver.findElement(By.xpath("//h2")).getText());
		Assertions.assertEquals("Principios activos", this.driver.findElement(By.xpath("//th")).getText());
		Assertions.assertEquals("Laboratorio titular", this.driver.findElement(By.xpath("//tr[2]/th")).getText());
		Assertions.assertEquals("Condiciones de prescripción", this.driver.findElement(By.xpath("//tr[3]/th")).getText());
		Assertions.assertEquals("Forma farmacéutica", this.driver.findElement(By.xpath("//tr[4]/th")).getText());
		Assertions.assertEquals("Dosis", this.driver.findElement(By.xpath("//tr[5]/th")).getText());
		Assertions.assertEquals("Principios activos", this.driver.findElement(By.xpath("//h3")).getText());
		Assertions.assertEquals("Excipientes", this.driver.findElement(By.xpath("//td[2]/h3")).getText());
		Assertions.assertEquals("Vias de administración", this.driver.findElement(By.xpath("//table[3]/tbody/tr/td/h3")).getText());
		Assertions.assertEquals("Presentaciones", this.driver.findElement(By.xpath("//table[3]/tbody/tr/td[2]/h3")).getText());
		return this;
	}

	@ParameterizedTest
	@CsvSource({
		"ibuprofeno, ibuprofeno, DEXIBUPROFENO, IBUPROFENO, cinfa, 'LABORATORIOS CINFA, S.A.', CINFADOL IBUPROFENO 50 mg/g GEL",
		"aspirina, acetilsalici, ACETILSALICILATO LISINA, ACETILSALICILICO ACIDO, bayer hispania, 'BAYER HISPANIA, S.L.', ASPIRINA C 400 mg/240 mg COMPRIMIDOS EFERVESCENTES"
	})
	public void testFindExistingMedicineUI(final String nombreMedicamento, final String nombrePractivo, final String practivo1, final String practivo2, final String nombreLabtitular, final String labtitular, final String medicamento) throws Exception {

		this.nombreMedicamento = nombreMedicamento;
		this.nombrePractivo = nombrePractivo;
		this.practivo1 = practivo1;
		this.practivo2 = practivo2;
		this.nombreLabtitular = nombreLabtitular;
		this.labtitular = labtitular;
		this.medicamento = medicamento;

		this.as("doctor1").whenIamLoggedInTheSystem().thenISeeMyUsernameInTheMenuBar().thenISeeMyRoleDropdownInTheMenuBar().thenIEnterMedicineSearchForm().thenISeeSearchResults().thenISeeFirstResultDetails();
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
