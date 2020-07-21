package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

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
    public void getLoginPage() throws InterruptedException {
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

    @Test
    @Order(2)
    public void testValidLogin() throws InterruptedException {
        driver.get("http://localhost:" + this.port + "/login");
        driver.findElement(By.id("inputUsername")).sendKeys("TomH");
        driver.findElement(By.id("inputPassword")).sendKeys("TomPass");
        driver.findElement(By.id("submit-button")).click();
        Assertions.assertEquals("Home", driver.getTitle());
    }

    @Test
    @Order(3)
    public void testUnauthorizedLogin() throws InterruptedException {
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    @Order(4)
    public void testNote() throws InterruptedException {
        driver.get("http://localhost:" + this.port + "/login");
        driver.findElement(By.id("inputUsername")).sendKeys("TomH");
        driver.findElement(By.id("inputPassword")).sendKeys("TomPass");
        driver.findElement(By.id("submit-button")).click();
        driver.findElement(By.id("nav-notes-tab")).click();
        Thread.sleep(4000);

        boolean noteCreated = false;
        try {
            driver.findElement(By.id("buttonAddNewNote")).click();
            Thread.sleep(4000);
            driver.findElement(By.id("note-title")).sendKeys("title");
            driver.findElement(By.id("note-description")).sendKeys("description");
            driver.findElement(By.id("saveNoteButton")).click();
            Thread.sleep(4000);
            driver.findElement(By.id("return-home")).click();
            Thread.sleep(4000);
            noteCreated = true;
            // add second note
            driver.findElement(By.id("nav-notes-tab")).click();
            Thread.sleep(4000);
            driver.findElement(By.id("buttonAddNewNote")).click();
            Thread.sleep(4000);
            driver.findElement(By.id("note-title")).sendKeys("title");
            driver.findElement(By.id("note-description")).sendKeys("description");
            driver.findElement(By.id("saveNoteButton")).click();
            Thread.sleep(4000);
            driver.findElement(By.id("return-home")).click();
            Thread.sleep(4000);
        } catch (Exception e) {
            System.out.println(e);
        }

        //testing deletion of note
        boolean noteDeleted = false;
        driver.findElement(By.id("nav-notes-tab")).click();
        Thread.sleep(4000);
        WebElement notesTable = driver.findElement(By.id("userTable"));
        List<WebElement> noteLink = notesTable.findElements(By.tagName("a"));
        for (int i = 0; i < noteLink.size(); i++) {
            WebElement deleteNoteButton = noteLink.get(i);
            deleteNoteButton.click();
            noteDeleted = true;
            break;
        }

        Assertions.assertTrue(noteCreated);
        Assertions.assertTrue(noteDeleted);
    }
}
