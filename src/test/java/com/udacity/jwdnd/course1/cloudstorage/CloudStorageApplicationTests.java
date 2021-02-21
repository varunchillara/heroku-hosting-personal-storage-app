package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.HashMap;
import java.util.Map;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private static WebDriver driver;
	private SignupPage signupPage;
	private LoginPage loginPage;
	private HomePage homePage;


	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		signupPage = new SignupPage(driver);
		loginPage = new LoginPage(driver);
		homePage = new HomePage(driver);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	public void signupAndLogin() throws Exception {

		driver.get("http://localhost:" + this.port + "/signup");
		Thread.sleep(5000);
		Map<String, String> loginInfo =
				signupPage.createUser("varun", "chillara", "user", "pass");
		driver.get("http://localhost:" + this.port + "/login");
		loginPage.login(loginInfo);
	}

	public void login() throws Exception {

		driver.get("http://localhost:" + this.port + "/login");
		Map<String, String> loginInfo = new HashMap<>();
		loginInfo.put("user", "pass");
		loginPage.login(loginInfo);
	}

	//Write a test that verifies that an unauthorized user can only access the login and signup pages.
	@Test
	@Order((1))
	public void userAuthorizationTest() {

		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());

	}

	//Write a test that signs up a new user, logs in, verifies that the home page is accessible, logs out,
	// and verifies that the home page is no longer accessible.
	@Test
	@Order(2)
	public void userSignupAndLoginTest() {

		try {
			signupAndLogin();
			Assertions.assertEquals("Home", driver.getTitle());

			homePage.logout();
			Thread.sleep(5000);
			Assertions.assertEquals("Login", driver.getTitle());

			driver.get("http://localhost:" + this.port + "/home");
			Thread.sleep(5000);
			Assertions.assertEquals("Login", driver.getTitle());

		}catch(Exception e) {
			Assertions.fail("test failed");
			e.printStackTrace();
		}
	}

	//Write a test that creates a note, and verifies it is displayed.
	@Test
	@Order(3)
	public void addNoteTest() {

		String inputTitle = "note title test";
		String inputDescription = "note body test";

		try {
			login();
			homePage.createNote(driver, inputTitle, inputDescription);
			Thread.sleep(5000);

			String title = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[2]/div[1]/table/tbody/tr/th")).getText();
			Assertions.assertEquals(inputTitle, title);

			String description = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[2]/div[1]/table/tbody/tr/td[2]")).getText();
			Assertions.assertEquals(inputDescription, description);
			homePage.logout();

		}catch(Exception e) {
			Assertions.fail();
		}
	}

	//Write a test that edits an existing note and verifies that the changes are displayed.
	@Test
	@Order(4)
	public void editNoteTest() {

		String inputTitle = "note title test edit";
		String inputDescription = "note body test edit";

		try {
			login();
			homePage.editNote(driver, inputTitle, inputDescription);
			Thread.sleep(5000);

			String title = driver.findElement(By.xpath("//th[contains(text(),'" + inputTitle + "')]")).getText();
			Assertions.assertEquals(inputTitle, title);

			String description = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[2]/div[1]/table/tbody/tr/td[2]")).getText();
			Assertions.assertEquals(inputDescription, description);

			homePage.logout();

		}catch(Exception e) {
			e.printStackTrace();
			Assertions.fail();
		}
	}

	//Write a test that deletes a note and verifies that the note is no longer displayed.
	@Test
	@Order(5)
	public void deleteNoteTest() {

		try {
			login();
			homePage.deleteNote(driver);

			WebElement deleteNoteButton = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[1]/table/tbody/tr/td[1]/a"));

			Assertions.assertNull(deleteNoteButton);

			homePage.logout();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	//Write a test that creates a set of credentials, verifies that they are displayed,
	// and verifies that the displayed password is encrypted.
	//Write a test that views an existing set of credentials, verifies that the viewable password is unencrypted,
	@Test
	@Order(6)
	public void createCredentialSet() {
		try {
			login();

			String urlTest = "urlTest";
			String usernameTest = "usernameTest";
			String passwordTest = "passwordTest";

			String urlTest1 = "urlTest1";
			String usernameTest1 = "usernameTest1";
			String passwordTest1 = "passwordTest1";

			homePage.createCredentialSet(driver, urlTest, usernameTest, passwordTest, urlTest1, usernameTest1, passwordTest1);

			String urlRead = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div[1]/table/tbody/tr[1]/th")).getText();
			String usernameRead = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div[1]/table/tbody/tr[1]/td[2]")).getText();
			String passwordRead = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div[1]/table/tbody/tr[1]/td[3]")).getText();

			String urlRead1 = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div[1]/table/tbody/tr[2]/th")).getText();
			String usernameRead1 = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div[1]/table/tbody/tr[2]/td[2]")).getText();
			String passwordRead1 = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div[1]/table/tbody/tr[2]/td[3]")).getText();

			Assertions.assertEquals(urlTest, urlRead);
			Assertions.assertEquals(usernameTest, usernameRead);
			Assertions.assertNotEquals(passwordTest, passwordRead);

			Assertions.assertEquals(urlTest1, urlRead1);
			Assertions.assertEquals(usernameTest1, usernameRead1);
			Assertions.assertNotEquals(passwordTest1, passwordRead1);

			WebElement credentialEditButton = driver.findElement(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr/td[1]/button"));
			new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(credentialEditButton)).click();

			String readPasswordInput = driver.findElement(By.xpath("//*[@id=\"credential-password\"]")).getAttribute("value");
			Thread.sleep(1000);

			Assertions.assertEquals(passwordTest, readPasswordInput);

			WebElement credentialCancelButton = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div[2]/div/div/div[3]/button[1]"));

			credentialCancelButton.click();
			Thread.sleep(2500);

			homePage.logout();

		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	// edits the credentials, and verifies that the changes are displayed.
	@Test
	@Order(7)
	public void editCredentialTest() {

		try {
			login();

			String urlTest = "urlTest edit";
			String usernameTest = "usernameTest edit";
			//String passwordTest = "passwordTest edit";

			String urlTest1 = "urlTest edit1";
			String usernameTest1 = "usernameTest edit1";
			//String passwordTest1 = "passwordTest edit1";


			homePage.editCredential(driver, urlTest, usernameTest, urlTest1, usernameTest1);

			String readUrl = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div[1]/table/tbody/tr[1]/th")).getText();
			String readUsername = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div[1]/table/tbody/tr[1]/td[2]")).getText();
			//String readPassword = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div[1]/table/tbody/tr[1]/td[3]")).getText();


			String readUrl1= driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div[1]/table/tbody/tr[2]/th")).getText();
			String readUsername1 = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div[1]/table/tbody/tr[2]/td[2]")).getText();
			//String readPassword1 = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div[1]/table/tbody/tr[2]/td[3]")).getText();

			Assertions.assertEquals(urlTest, readUrl);
			Assertions.assertEquals(usernameTest, readUsername);
			//Assertions.assertEquals(passwordTest, readPassword);

			Assertions.assertEquals(urlTest1, readUrl1);
			Assertions.assertEquals(usernameTest1, readUsername1);

			homePage.logout();
			Thread.sleep(3000);

		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(8)
	public void deleteCredentials() {

		try{
			login();
			homePage.deleteCredentials(driver);

			WebElement deleteNoteButton = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div[1]/table/tbody/tr[1]/td[1]/a"));

			Assertions.assertNull(deleteNoteButton);

			Thread.sleep(5000);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
