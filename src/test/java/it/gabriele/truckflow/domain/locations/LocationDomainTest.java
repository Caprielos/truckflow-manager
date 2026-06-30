package it.gabriele.truckflow.domain.locations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class LocationDomainTest {

  @Test
  void createsDepotLocationWithBusinessCode() {
    var location = milanDepot();

    assertTrue(location.isActive());
    assertEquals(LocationCode.of("DEP-MIL-001"), location.code());
    assertEquals(LocationType.DEPOT, location.type());
    assertEquals("IT", location.address().country());
  }

  @Test
  void createsYardLocationAsPureAnagraphicPlace() {
    var yard = florenceYard();

    assertTrue(yard.isYard());
    assertEquals(LocationStatus.ACTIVE, yard.status());
    assertEquals(LocationCode.of("YARD-FI-002"), yard.code());
  }

  @Test
  void normalizesLocationCode() {
    assertEquals("HUB-BO-001", LocationCode.of("hub-bo-001").value());
  }

  @Test
  void rejectsInvalidCoordinates() {
    assertThrows(
        IllegalArgumentException.class,
        () -> GeoCoordinates.of(new BigDecimal("91"), new BigDecimal("12.50")));
  }

  @Test
  void statusIsAnagraphicAndNotOperationalAvailability() {
    var location = milanDepot();

    location.suspend();
    assertEquals(LocationStatus.SUSPENDED, location.status());

    location.archive();
    assertEquals(LocationStatus.ARCHIVED, location.status());
  }

  public static Location milanDepot() {
    return new Location(
        null,
        LocationCode.of("dep-mil-001"),
        "Milano Depot",
        LocationType.DEPOT,
        LocationStatus.ACTIVE,
        new LocationAddress("Via Roma 10", "Milano", "20100", "MI", "IT", "Main depot"),
        GeoCoordinates.of(new BigDecimal("45.4642"), new BigDecimal("9.1900")),
        "Primary depot");
  }

  public static Location bolognaHub() {
    return new Location(
        null,
        LocationCode.of("HUB-BO-001"),
        "Bologna Hub",
        LocationType.HUB,
        LocationStatus.ACTIVE,
        LocationAddress.empty(),
        null,
        "Main hub");
  }

  public static Location florenceYard() {
    return new Location(
        null,
        LocationCode.of("YARD-FI-002"),
        "Firenze Yard Area B",
        LocationType.YARD,
        LocationStatus.ACTIVE,
        LocationAddress.empty(),
        null,
        "Trailer exchange yard");
  }

  public static Location romeDepot() {
    return new Location(
        null,
        LocationCode.of("DEP-ROMA-001"),
        "Roma Depot",
        LocationType.DEPOT,
        LocationStatus.ACTIVE,
        LocationAddress.empty(),
        null,
        "Destination depot");
  }
}
