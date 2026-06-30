package it.gabriele.truckflow.domain.locations;

public record LocationAddress(
    String street, String city, String postalCode, String province, String country, String notes) {

  public LocationAddress {
    street = LocationValidation.normalize(street);
    city = LocationValidation.normalize(city);
    postalCode = LocationValidation.normalize(postalCode);
    province = LocationValidation.normalize(province).toUpperCase();
    country = LocationValidation.normalize(country).toUpperCase();
    notes = LocationValidation.normalize(notes);
  }

  public static LocationAddress empty() {
    return new LocationAddress("", "", "", "", "", "");
  }

  public boolean isEmpty() {
    return street.isBlank()
        && city.isBlank()
        && postalCode.isBlank()
        && province.isBlank()
        && country.isBlank()
        && notes.isBlank();
  }
}
