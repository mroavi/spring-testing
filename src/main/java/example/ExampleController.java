// This class defines a REST controller that exposes simple HTTP endpoints
// for greeting users and fetching current weather information. It uses
// dependency injection to access a PersonRepository (for database lookups)
// and a WeatherClient (for external API calls).

package example;

// Import custom classes for interacting with the database and the weather API.
import example.person.PersonRepository;
import example.weather.WeatherClient;
import example.weather.WeatherResponse;

// Import Spring annotations for creating REST controllers and mapping endpoints.
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

// Marks this class as a REST controller, meaning that its methods handle
// HTTP requests and automatically return their results as JSON or plain text
// (instead of rendering a view template).
@RestController
public class ExampleController {

    // Dependencies injected by Spring:
    // - personRepository: used to look up Person objects in the database.
    // - weatherClient: used to fetch current weather information.
    private final PersonRepository personRepository;
    private final WeatherClient weatherClient;

    // Constructor-based dependency injection is used here. Spring automatically
    // provides implementations of PersonRepository and WeatherClient when
    // creating this controller.
    @Autowired
    public ExampleController(final PersonRepository personRepository, final WeatherClient weatherClient) {
        this.personRepository = personRepository;
        this.weatherClient = weatherClient;
    }

    // -------------------------
    // Simple text endpoint
    // -------------------------

    // Handles HTTP GET requests sent to /hello.
    // Returns a plain "Hello World!" message.
    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

    // -------------------------
    // Personalized greeting endpoint
    // -------------------------

    // Handles HTTP GET requests to /hello/{lastName}.
    // The {lastName} part of the URL is captured as a path variable.
    //
    // Example request: GET /hello/Smith
    // If a person with lastName "Smith" exists in the database, it responds:
    //     "Hello John Smith!"
    // Otherwise:
    //     "Who is this 'Smith' you're talking about?"
    @GetMapping("/hello/{lastName}")
    public String hello(@PathVariable final String lastName) {
        var foundPerson = personRepository.findByLastName(lastName);

        // The repository method returns an Optional<Person>.
        // If a matching person exists, build a greeting using their name.
        // Otherwise, return a default "unknown person" message.
        return foundPerson
                .map(person -> String.format("Hello %s %s!", person.getFirstName(), person.getLastName()))
                .orElse(String.format("Who is this '%s' you're talking about?", lastName));
    }

    // -------------------------
    // Weather endpoint
    // -------------------------

    // Handles HTTP GET requests to /weather.
    // Uses WeatherClient to fetch the current weather and returns a human-readable
    // summary (e.g., "Rain: light rain"). If the API call fails, returns a fallback
    // message.
    @GetMapping("/weather")
    public String weather() {
        return weatherClient.fetchWeather()
                .map(WeatherResponse::getSummary)
                .orElse("Sorry, I couldn't fetch the weather for you :(");
    }
}
