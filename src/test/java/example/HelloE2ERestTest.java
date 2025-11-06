package example;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;

import example.person.Person;
import example.person.PersonRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// This annotation launches the entire Spring Boot application,
// including the web server, controllers, and repositories.
// The application runs on a random free port for isolation.
// Because it tests the whole system through real HTTP requests,
// this is considered an end-to-end (E2E) test.
public class HelloE2ERestTest {

  // Injects the actual repository bean connected to the test database.
  // Used to insert and clear data between tests.
  @Autowired private PersonRepository personRepository;

  // Injects the random port number the application is running on.
  // Used to build the full URL for HTTP requests.
  @LocalServerPort private int port;

  @AfterEach
  public void tearDown() throws Exception {
    // Clean up the database after each test to ensure isolation.
    personRepository.deleteAll();
  }

  @Test
  public void shouldReturnHelloWorld() throws Exception {
    // Send an HTTP GET request to /hello on the running test server.
    // RestAssured is used to perform the call and verify the response.
    when()
        .get(String.format("http://localhost:%s/hello", port))
        .then()
        // Expect an HTTP 200 OK status.
        .statusCode(is(200))
        // Expect the response body to contain the greeting.
        .body(containsString("Hello World!"));
  }

  @Test
  public void shouldReturnGreeting() throws Exception {
    // Arrange: create and save a person in the test database.
    var peter = new Person("Peter", "Pan");
    personRepository.save(peter);

    // Act: send an HTTP GET request to /hello/Pan.
    when()
        .get(String.format("http://localhost:%s/hello/Pan", port))
        .then()
        // Assert: expect an HTTP 200 OK status.
        .statusCode(is(200))
        // Assert: the response should greet Peter Pan by name.
        .body(containsString("Hello Peter Pan!"));
  }
}
