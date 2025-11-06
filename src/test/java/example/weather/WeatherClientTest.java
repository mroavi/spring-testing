// This class contains **unit tests** for the WeatherClient component.
// It tests the behavior of the fetchWeather() method in isolation,
// without making real HTTP requests or starting a Spring context.

package example.weather;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

// @ExtendWith(SpringExtension.class) allows Spring test features such as
// dependency injection and mocking to be used in JUnit 5 tests.
// However, in this case, we're mainly using Mockito for mocking behavior.
@ExtendWith(SpringExtension.class)
public class WeatherClientTest {

  // The class under test (also called the "subject").
  private WeatherClient subject;

  // Mocked RestTemplate dependency — no real HTTP calls are made.
  @Mock private RestTemplate restTemplate;

  // This method runs before each test.
  // It creates a new WeatherClient with the mocked RestTemplate and
  // dummy configuration values for the URL and API key.
  @BeforeEach
  public void setUp() throws Exception {
    subject = new WeatherClient(restTemplate, "http://localhost:8089", "someAppId");
  }

  // -------------------------
  // Test 1: Successful request
  // -------------------------

  // This test verifies that WeatherClient calls the correct URL and
  // returns the expected WeatherResponse wrapped in an Optional.
  @Test
  public void shouldCallWeatherService() throws Exception {
    // Arrange: define the expected response and the URL that the client should call.
    var expectedResponse = new WeatherResponse("raining", "a light drizzle");
    given(
            restTemplate.getForObject(
                "http://localhost:8089/data/2.5/weather?q=Hamburg,de&appid=someAppId",
                WeatherResponse.class))
        .willReturn(expectedResponse);

    // Act: call the method under test.
    var actualResponse = subject.fetchWeather();

    // Assert: verify that the returned Optional contains the expected response.
    assertThat(actualResponse, is(Optional.of(expectedResponse)));
  }

  // -------------------------
  // Test 2: Failure case
  // -------------------------

  // This test checks that when the weather service call fails (for example,
  // network error or bad API key), the client returns an empty Optional
  // instead of throwing an exception.
  @Test
  public void shouldReturnEmptyOptionalIfWeatherServiceIsUnavailable() throws Exception {
    // Arrange: make the RestTemplate throw an exception when called.
    given(
            restTemplate.getForObject(
                "http://localhost:8089/data/2.5/weather?q=Hamburg,de&appid=someAppId",
                WeatherResponse.class))
        .willThrow(new RestClientException("something went wrong"));

    // Act: call the method under test.
    var actualResponse = subject.fetchWeather();

    // Assert: verify that the result is an empty Optional.
    assertThat(actualResponse, is(Optional.empty()));
  }
}

