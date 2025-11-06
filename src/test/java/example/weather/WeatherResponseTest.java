package example.weather;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.helper.FileLoader;
import org.junit.jupiter.api.Test;

public class WeatherResponseTest {

  @Test
  public void shouldDeserializeJson() throws Exception {
    // Load a sample JSON response from the classpath (e.g. test/resources folder).
    // This simulates what the weather API would return.
    var jsonResponse = FileLoader.read("classpath:weatherApiResponse.json");

    // Define the expected WeatherResponse object after deserialization.
    // It represents what we expect the JSON to map into.
    var expectedResponse = new WeatherResponse("raining", "a light drizzle");

    // Use Jackson's ObjectMapper to convert the JSON string into a WeatherResponse object.
    // This step tests if the JSON structure matches the class fields correctly.
    var parsedResponse = new ObjectMapper().readValue(jsonResponse, WeatherResponse.class);

    // Verify that the parsed object matches the expected one.
    // If they are equal, the deserialization works as intended.
    assertThat(parsedResponse, is(expectedResponse));
  }
}

