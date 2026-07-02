package it.gabriele.truckflow.infrastructure.repository.locations;

import java.util.Objects;

/**
 * File-oriented persistence representation for the Punto 7E location repository prototype.
 *
 * <p>This record is not a JPA entity, not a database row declaration and not a web DTO. It is a
 * small technical model used only by the file-backed repository prototype.
 */
public record LocationPersistenceRecord(
    String id,
    String code,
    String name,
    String type,
    String status,
    String street,
    String city,
    String postalCode,
    String province,
    String country,
    String addressNotes,
    String latitude,
    String longitude,
    String notes) {

  /** Creates a validated file persistence record. */
  public LocationPersistenceRecord {
    id = requireText(id, "id");
    code = requireText(code, "code");
    name = requireText(name, "name");
    type = requireText(type, "type");
    status = requireText(status, "status");
    street = normalize(street);
    city = normalize(city);
    postalCode = normalize(postalCode);
    province = normalize(province);
    country = normalize(country);
    addressNotes = normalize(addressNotes);
    latitude = normalize(latitude);
    longitude = normalize(longitude);
    notes = normalize(notes);
  }

  private static String requireText(String value, String fieldName) {
    String normalized = normalize(value);
    if (normalized.isBlank()) {
      throw new IllegalArgumentException(fieldName + " must not be blank");
    }
    return normalized;
  }

  private static String normalize(String value) {
    return Objects.toString(value, "").strip();
  }
}
