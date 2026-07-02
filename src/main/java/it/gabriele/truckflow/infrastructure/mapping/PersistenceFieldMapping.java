package it.gabriele.truckflow.infrastructure.mapping;

import java.util.Objects;

/**
 * Describes one planned mapping rule between a domain field and a future persistence field.
 *
 * <p>This is a blueprint element, not a JPA entity, not a database column declaration
 * and not a real mapper implementation.
 */
public record PersistenceFieldMapping(
    String domainField,
    String persistenceField,
    PersistenceMappingKind kind,
    boolean mandatory,
    String notes) {

  /** Creates a validated persistence field mapping blueprint entry. */
  public PersistenceFieldMapping {
    domainField = requireText(domainField, "domainField");
    persistenceField = requireText(persistenceField, "persistenceField");
    kind = Objects.requireNonNull(kind, "kind must not be null");
    notes = requireText(notes, "notes");
  }

  private static String requireText(String value, String fieldName) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException(fieldName + " must not be blank");
    }
    return value.strip();
  }
}
