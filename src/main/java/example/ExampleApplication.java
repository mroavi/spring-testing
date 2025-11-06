// This class serves as the main entry point for the Spring Boot application.
// It configures and launches the application context and defines a RestTemplate
// bean that other components (such as WeatherClient) can use for HTTP requests.

package example;

// Import core Spring Boot classes and annotations that enable auto-configuration
// and component scanning.
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Import classes used to create and configure a RestTemplate bean.
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

// The @SpringBootApplication annotation marks this class as the primary
// configuration class for the application. It combines three annotations:
//
// - @Configuration: indicates that this class can define Spring beans.
// - @EnableAutoConfiguration: tells Spring Boot to automatically configure
//   the application based on dependencies on the classpath.
// - @ComponentScan: enables automatic scanning for @Component, @Service,
//   @Repository, and @Controller annotations within this package and its subpackages.
@SpringBootApplication
public class ExampleApplication {

  // The main() method is the entry point of the Spring Boot application.
  // It delegates to SpringApplication.run(), which starts the embedded
  // application server (such as Tomcat) and initializes the Spring context.
  public static void main(String[] args) {
    SpringApplication.run(ExampleApplication.class, args);
  }

  // The @Bean annotation tells Spring that the return value of this method
  // should be registered as a bean in the application context.
  //
  // RestTemplate is a Spring-provided class used for making HTTP requests.
  // Here, it is created using a RestTemplateBuilder, which provides
  // convenient defaults and allows further customization if needed.
  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplateBuilder().build();
  }
}
