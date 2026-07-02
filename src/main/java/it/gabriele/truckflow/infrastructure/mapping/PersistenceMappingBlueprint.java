package it.gabriele.truckflow.infrastructure.mapping;

import java.util.List;
import java.util.Objects;

/**
 * Immutable technical blueprint describing how one domain type should be mapped in a future
 * persistence adapter.
 *
 * <p>The blueprint intentionally avoids JPA, SQL, Spring Data and database-specific concepts. Real
 * persistence models and real repository adapters will be introduced only in later Punto 7 steps.
 */
public record PersistenceMappingBlueprint(
    String contextName,
    String domainType,
    String persistenceModelName,
    List<PersistenceFieldMapping> fields,
    String notes) {

  /** Creates a validated persistence mapping blueprint. */
  public PersistenceMappingBlueprint {
    contextName = requireText(contextName, "contextName");
    domainType = requireText(domainType, "domainType");
    persistenceModelName = requireText(persistenceModelName, "persistenceModelName");
    fields = List.copyOf(Objects.requireNonNull(fields, "fields must not be null"));
    if (fields.isEmpty()) {
      throw new IllegalArgumentException("fields must not be empty");
    }
    notes = requireText(notes, "notes");
  }

  /** Returns whether this blueprint contains a mandatory field with the given domain-side name. */
  public boolean hasMandatoryDomainField(String domainField) {
    String normalized = requireText(domainField, "domainField");
    return fields.stream()
        .anyMatch(field -> field.mandatory() && field.domainField().equals(normalized));
  }

  private static String requireText(String value, String fieldName) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException(fieldName + " must not be blank");
    }
    return value.strip();
  }
}
