package it.gabriele.truckflow.domain.users;

import it.gabriele.truckflow.domain.users.exceptions.InvalidUserException;

public record UserAddress(
    String street,
    String streetNumber,
    String postalCode,
    String city,
    String province,
    String country) {

  public UserAddress {
    street = requireText(street, "street");
    streetNumber = normalize(streetNumber);
    postalCode = normalize(postalCode);
    city = requireText(city, "city");
    province = normalize(province);
    country = requireText(country, "country");
  }

  private static String requireText(String value, String fieldName) {
    String normalized = normalize(value);

    if (normalized.isBlank()) {
      throw new InvalidUserException(fieldName + " is required.");
    }

    return normalized;
  }

  private static String normalize(String value) {
    return value == null ? "" : value.trim();
  }
}
