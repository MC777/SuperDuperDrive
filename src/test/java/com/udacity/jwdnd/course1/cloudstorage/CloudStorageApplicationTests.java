package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	@Order(1)
	public void getLoginPage() throws InterruptedException{
		driver.get("http://localhost:" + this.port + "/signup");
		driver.findElement(By.id("inputFirstName")).sendKeys("Tom");
		driver.findElement(By.id("inputLastName")).sendKeys("Hanks");
		driver.findElement(By.id("inputUsername")).sendKeys("TomH");
		driver.findElement(By.id("inputPassword")).sendKeys("TomPass");
		Thread.sleep(5000);
		driver.findElement(By.id("submit-button")).click();
		driver.get("http://localhost:" + this.port + "/login");
		driver.findElement(By.id("inputUsername")).sendKeys("TomH");
		driver.findElement(By.id("inputPassword")).sendKeys("TomPass");
		driver.findElement(By.id("submit-button")).click();
		Thread.sleep(5000);
		Assertions.assertEquals("Home", driver.getTitle());
	}

}
