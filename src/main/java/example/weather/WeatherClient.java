// This class defines a Spring component responsible for fetching weather data
// from an external weather service (e.g., OpenWeatherMap). It uses dependency
// injection to obtain the API URL, API key, and a RestTemplate for making HTTP
// requests.

package example.weather;

// Import Optional for safely handling results that may be missing.
// Import Spring annotations for dependency injection and component scanning.
// Import RestTemplate for making HTTP requests to the weather API.
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

// Marks this class as a Spring-managed component so that it can be injected
// wherever needed (for example, in a controller or service).
@Component
public class WeatherClient {

  // Constant defining the target city for the weather query.
  // This could later be made configurable.
  public static final String CITY = "Hamburg,de";

  // Dependencies injected by Spring:
  // - RestTemplate: used for performing HTTP requests.
  // - weatherServiceUrl: base URL of the weather API (e.g., https://api.openweathermap.org).
  // - weatherServiceApiKey: API key used for authentication.
  private final RestTemplate restTemplate;
  private final String weatherServiceUrl;
  private final String weatherServiceApiKey;

  // Constructor-based dependency injection is used here.
  // Spring automatically provides the RestTemplate bean and reads the two
  // property values (weather.url and weather.api_secret) from the application's
  // configuration (application.properties or environment variables).
  @Autowired
  public WeatherClient(
      final RestTemplate restTemplate,
      @Value("${weather.url}") final String weatherServiceUrl,
      @Value("${weather.api_secret}") final String weatherServiceApiKey) {
    this.restTemplate = restTemplate;
    this.weatherServiceUrl = weatherServiceUrl;
    this.weatherServiceApiKey = weatherServiceApiKey;
  }

  // This method builds the full API request URL and performs the HTTP GET call.
  // It returns an Optional<WeatherResponse>, which may be empty if the call fails.
  public Optional<WeatherResponse> fetchWeather() {
    // Build the complete URL with query parameters.
    // Example:
    // https://api.openweathermap.org/data/2.5/weather?q=Hamburg,de&appid=YOUR_KEY
    var url =
        String.format(
            "%s/data/2.5/weather?q=%s&appid=%s", weatherServiceUrl, CITY, weatherServiceApiKey);

    try {
      // Perform the HTTP GET request and map the JSON response to a WeatherResponse object.
      return Optional.ofNullable(restTemplate.getForObject(url, WeatherResponse.class));
    } catch (RestClientException e) {
      // If the HTTP request fails (e.g., due to network error or invalid key),
      // print the error message and return an empty Optional instead of throwing an exception.
      System.err.println(e.getMessage());
      return Optional.empty();
    }
  }
}
