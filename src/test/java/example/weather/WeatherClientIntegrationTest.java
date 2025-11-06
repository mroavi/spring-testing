package example.weather;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import example.helper.FileLoader;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

@SpringBootTest // Loads the full Spring application context,
// so the real WeatherClient bean (with all its dependencies) is used.
@WireMockTest(httpPort = 8089) // Starts a local WireMock server on port 8089.
// WireMock simulates the external weather API for integration testing.
public class WeatherClientIntegrationTest {

  // Inject the real WeatherClient bean from the Spring context.
  @Autowired private WeatherClient subject;

  @Test
  public void shouldCallWeatherService() throws Exception {
    // Arrange: configure WireMock to simulate the external weather API endpoint.
    // When a GET request is made to the given URL, WireMock will return a fake JSON response.
    stubFor(
        get(urlEqualTo("/data/2.5/weather?q=Hamburg,de&appid=someAppId"))
            .willReturn(
                aResponse()
                    // Load a sample weather API JSON response from the classpath.
                    .withBody(FileLoader.read("classpath:weatherApiResponse.json"))
                    // Indicate that the response is JSON data.
                    .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    // Simulate a successful HTTP 200 OK response.
                    .withStatus(200)));

    // Act: call the real WeatherClient, which will make an HTTP request to the mocked server.
    var weatherResponse = subject.fetchWeather();

    // Assert: verify that the WeatherClient correctly parsed the mocked JSON response
    // into a WeatherResponse object wrapped in an Optional.
    var expectedResponse = Optional.of(new WeatherResponse("raining", "a light drizzle"));
    assertThat(weatherResponse, is(expectedResponse));
  }
}
