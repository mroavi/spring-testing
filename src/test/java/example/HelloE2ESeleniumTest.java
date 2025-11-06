package example;

// Importing Hamcrest matchers for expressive assertions
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

// WebDriverManager automatically downloads and manages browser drivers
import io.github.bonigarcia.wdm.WebDriverManager;

// JUnit 5 annotations for test lifecycle management
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Selenium imports for browser automation
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

// Spring Boot imports for integration testing
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

// This annotation starts a real Spring Boot server on a random port for testing
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloE2ESeleniumTest {

  // WebDriver instance (browser automation interface)
  private WebDriver driver;

  // Injects the randomly assigned port where the Spring Boot app runs
  @LocalServerPort private int port;

  // Runs once before all tests — sets up the ChromeDriver binary
  @BeforeAll
  public static void setUpClass() throws Exception {
    WebDriverManager.chromedriver().setup();
  }

  // Runs before each test — configures and launches a new browser instance
  @BeforeEach
  public void setUp() throws Exception {
    var chromeOptions = new ChromeOptions();

    // Specify the Brave browser executable (Brave is Chromium-based)
    chromeOptions.setBinary("/usr/bin/brave");

    // Run in headless mode so no window appears during automated testing
    chromeOptions.addArguments("--headless=new");
    chromeOptions.addArguments("--disable-gpu");
    chromeOptions.addArguments("--no-sandbox");

    // Create a ChromeDriver instance using the given options
    driver = new ChromeDriver(chromeOptions);
  }

  // Runs after each test — closes the browser to free resources
  @AfterEach
  public void tearDown() {
    if (driver != null) {
      driver.quit();
    }
  }

  // The actual test: checks that the /hello page contains "Hello World!"
  @Test
  public void helloPageHasTextHelloWorld() {
    // Navigate to the /hello page on the running Spring Boot server
    driver.navigate().to(String.format("http://localhost:%s/hello", port));

    // Find the <body> element of the page
    var body = driver.findElement(By.tagName("body"));

    // Verify that the body text contains "Hello World!"
    assertThat(body.getText(), containsString("Hello World!"));
  }
}
