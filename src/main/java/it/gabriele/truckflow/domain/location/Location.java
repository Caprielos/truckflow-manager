package it.gabriele.truckflow.domain.location;

import java.time.ZoneId;
import java.util.Objects;

/**
 * Rappresenta un luogo fisico del dominio. Esempio: magazzino, cliente, deposito, punto di ritiro o
 * punto di consegna.
 */
public final class Location {

  private static final int MAX_NAME_LENGTH = 150;

  private final String name;
  private final Address address;
  private final ZoneId zoneId;

  private Location(String name, Address address, ZoneId zoneId) {
    this.name = validateName(name);

    if (address == null) {
      throw new IllegalArgumentException("L'indirizzo del luogo è obbligatorio.");
    }

    if (zoneId == null) {
      throw new IllegalArgumentException("Il fuso orario del luogo è obbligatorio.");
    }

    this.address = address;
    this.zoneId = zoneId;
  }

  public static Location of(String name, Address address, ZoneId zoneId) {
    return new Location(name, address, zoneId);
  }

  public static Location of(String name, Address address, String zoneId) {
    if (zoneId == null) {
      throw new IllegalArgumentException("Il fuso orario del luogo è obbligatorio.");
    }

    String normalizedZoneId = zoneId.trim();

    if (normalizedZoneId.isEmpty()) {
      throw new IllegalArgumentException("Il fuso orario del luogo non può essere vuoto.");
    }

    return new Location(name, address, ZoneId.of(normalizedZoneId));
  }

  private static String validateName(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Il nome del luogo è obbligatorio.");
    }

    String normalizedName = name.trim();

    if (normalizedName.isEmpty()) {
      throw new IllegalArgumentException("Il nome del luogo non può essere vuoto.");
    }

    if (normalizedName.length() > MAX_NAME_LENGTH) {
      throw new IllegalArgumentException(
          "Il nome del luogo non può superare " + MAX_NAME_LENGTH + " caratteri.");
    }

    return normalizedName;
  }

  public String getName() {
    return name;
  }

  public Address getAddress() {
    return address;
  }

  public ZoneId getZoneId() {
    return zoneId;
  }

  public boolean hasCoordinates() {
    return address.hasCoordinates();
  }

  public GeoCoordinates getCoordinates() {
    return address.getCoordinates();
  }

  public boolean isInCountry(String countryCode) {
    return address.isInCountry(countryCode);
  }

  public boolean isInSameTimeZone(Location other) {
    if (other == null) {
      throw new IllegalArgumentException("Il luogo da confrontare è obbligatorio.");
    }

    return this.zoneId.equals(other.zoneId);
  }

  public String formatSingleLine() {
    return name + " - " + address.formatSingleLine() + " [" + zoneId + "]";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Location location)) return false;
    return name.equals(location.name)
        && address.equals(location.address)
        && zoneId.equals(location.zoneId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, address, zoneId);
  }

  @Override
  public String toString() {
    return formatSingleLine();
  }
}
