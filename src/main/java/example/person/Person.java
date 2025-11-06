// This class defines a JPA entity that represents a "Person" record in a
// database. It includes annotations that make it compatible with frameworks
// like Hibernate or Spring Data JPA.

package example.person;

// Import JPA annotations for defining entities, primary keys, and ID
// generation.
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// Import Objects utility for equals() and hashCode() implementations.
import java.util.Objects;

// Marks this class as a JPA entity, meaning it maps to a database table.
@Entity
public class Person {

  // Marks 'id' as the primary key of the entity.
  // The @GeneratedValue annotation tells JPA to let the database or provider
  // automatically generate the ID (e.g., using an auto-increment or sequence).
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  // Regular fields that will map to table columns.
  private String firstName;
  private String lastName;

  // Protected no-argument constructor required by JPA.
  // The framework uses it when loading entities from the database.
  protected Person() {}

  // Public constructor for creating new Person instances in code.
  public Person(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  // Getter for the auto-generated primary key.
  public int getId() {
    return id;
  }

  // Getter for firstName.
  public String getFirstName() {
    return firstName;
  }

  // Getter for lastName.
  public String getLastName() {
    return lastName;
  }

  // Overrides the equals() method to compare two Person objects by their fields.
  // This ensures that two persons are considered equal if their ID,
  // first name, and last name are the same.
  @Override
  public boolean equals(Object o) {
    if (this == o) return true; // Same object reference
    if (o == null || getClass() != o.getClass()) return false; // Null or different class
    var person = (Person) o;
    return id == person.id
        && Objects.equals(firstName, person.firstName)
        && Objects.equals(lastName, person.lastName);
  }

  // Overrides hashCode() to ensure consistent hashing behavior
  // when using this class in hash-based collections (e.g., HashSet, HashMap).
  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName);
  }

  // Provides a human-readable representation of the object,
  // useful for logging or debugging.
  @Override
  public String toString() {
    return "Person{"
        + "id='"
        + id
        + '\''
        + ", firstName='"
        + firstName
        + '\''
        + ", lastName='"
        + lastName
        + '\''
        + '}';
  }
}
