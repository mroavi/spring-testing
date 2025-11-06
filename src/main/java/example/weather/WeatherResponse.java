// This class represents the structure of the JSON response returned by the
// weather API (for example, OpenWeatherMap). It is designed to be compatible
// with Jackson, which automatically maps JSON fields to Java objects.

package example.weather;

// Import Jackson annotation to ignore unknown fields in the JSON response.
// This prevents errors if the API returns extra fields that are not defined
// in this class.
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

// Instructs Jackson to ignore any fields in the JSON that are not present
// in this class or its inner class. This makes the mapping more robust to
// API changes.
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {

  // Represents the "weather" array in the JSON response.
  // Each element in this list corresponds to a weather condition, such as:
  // [{ "main": "Rain", "description": "light rain" }]
  private List<Weather> weather;

  // Default no-argument constructor required by Jackson for deserialization.
  public WeatherResponse() {}

  // Convenience constructor for manually creating an instance
  // with a single weather condition.
  public WeatherResponse(String main, String description) {
    this.weather = Collections.singletonList(new Weather(main, description));
  }

  // Returns a summary string that combines all weather entries.
  // Example output:
  // "Rain: light rain"
  // "Clouds: overcast clouds"
  public String getSummary() {
    return weather.stream()
        .map(w -> w.main + ": " + w.description)
        .collect(Collectors.joining("\n"));
  }

  // Getter for the list of Weather objects.
  public List<Weather> getWeather() {
    return weather;
  }

  // Standard equality check comparing WeatherResponse objects by their
  // list of Weather entries.
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    WeatherResponse that = (WeatherResponse) o;
    return Objects.equals(weather, that.weather);
  }

  // Computes hash code based on the weather list.
  @Override
  public int hashCode() {
    return Objects.hash(weather);
  }

  // Returns a readable string representation for debugging or logging.
  @Override
  public String toString() {
    return "WeatherResponse{" + "weather=" + weather + '}';
  }

  // Inner class representing a single weather condition within the
  // "weather" array in the JSON response.
  // Example JSON object:
  // { "main": "Rain", "description": "light rain" }
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Weather {

    // "main" typically contains the general condition (e.g., "Rain").
    private String main;

    // "description" provides a more detailed explanation (e.g., "light rain").
    private String description;

    // Default constructor for Jackson deserialization.
    public Weather() {}

    // Constructor for manual creation of Weather objects.
    public Weather(String main, String description) {
      this.main = main;
      this.description = description;
    }

    // Getter for the general weather condition.
    public String getMain() {
      return main;
    }

    // Getter for the detailed weather description.
    public String getDescription() {
      return description;
    }

    // Equality check for comparing Weather objects by their fields.
    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Weather weather = (Weather) o;
      return Objects.equals(main, weather.main) && Objects.equals(description, weather.description);
    }

    // Computes hash code based on main and description.
    @Override
    public int hashCode() {
      return Objects.hash(main, description);
    }

    // Readable string representation of a single Weather entry.
    @Override
    public String toString() {
      return "Weather{" + "main='" + main + '\'' + ", description='" + description + '\'' + '}';
    }
  }
}
