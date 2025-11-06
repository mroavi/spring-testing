// This interface defines a repository for managing Person entities.
// It uses Spring Data JPA to automatically provide CRUD operations
// and query generation based on method names.

package example.person;

// Import CrudRepository, which provides basic create, read, update,
// and delete operations for an entity class.
import org.springframework.data.repository.CrudRepository;

// Import Optional, used for safely handling results that might be empty.
import java.util.Optional;

// The repository interface extends CrudRepository, parameterized with:
// - Person: the entity type this repository manages
// - Integer: the type of the entity's primary key (should match Person.id)
public interface PersonRepository extends CrudRepository<Person, Integer> {

  // Custom query method that finds a Person by last name.
  // Spring Data JPA automatically generates the query implementation
  // at runtime based on the method name.
  // The result is wrapped in Optional to handle the case
  // where no matching Person is found.
  Optional<Person> findByLastName(String lastName);
}
