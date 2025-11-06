package example.person;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest // Sets up a lightweight Spring context for JPA tests.
// It configures an in-memory database (like H2) and initializes JPA repositories.
public class PersonRepositoryIntegrationTest {

  // Injects the actual PersonRepository bean connected to the test database.
  // This is not a mock — it performs real database operations.
  @Autowired private PersonRepository subject;

  @AfterEach
  public void tearDown() throws Exception {
    // Clean up after each test to ensure no leftover data affects other tests.
    subject.deleteAll();
  }

  @Test
  public void shouldSaveAndFetchPerson() throws Exception {
    // Arrange: create a Person entity to save.
    var peter = new Person("Peter", "Pan");

    // Act: save the entity into the test database.
    subject.save(peter);

    // Fetch the person by last name using the repository query method.
    var maybePeter = subject.findByLastName("Pan");

    // Assert: verify that the repository correctly retrieves the saved person.
    // The expected result is an Optional containing the same Person object.
    assertThat(maybePeter, is(Optional.of(peter)));
  }
}

