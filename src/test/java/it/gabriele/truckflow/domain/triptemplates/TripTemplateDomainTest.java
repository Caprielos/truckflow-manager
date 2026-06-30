package it.gabriele.truckflow.domain.triptemplates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.domain.locations.LocationDomainTest;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TripTemplateDomainTest {

  @Test
  void createsLineHaulTemplateWithMultipleOrderedSegments() {
    var template = milanRomeTemplate();

    assertTrue(template.isActive());
    assertEquals(TripTemplateCode.of("LINEHAUL-001"), template.code());
    assertEquals(TripTemplateType.LINE_HAUL, template.type());
    assertEquals(3, template.segmentCount());
    assertEquals(1, template.segments().getFirst().sequenceNumber());
    assertTrue(template.isContinuous());
    assertTrue(template.routeSpecification().allowsCountry("it"));
    assertTrue(template.routeSpecification().allowsRoadType(RouteRoadType.MOTORWAY));
  }

  @Test
  void tripTemplateUsesLocationIdsInsteadOfEmbeddingLocations() {
    var origin = LocationDomainTest.milanDepot();
    var destination = LocationDomainTest.bolognaHub();

    var segment =
        new TripTemplateSegment(
            null,
            1,
            TripTemplateSegmentType.TRANSIT,
            origin.id(),
            destination.id(),
            Distance.km(new BigDecimal("210")),
            "Milano to Bologna");

    assertEquals(origin.id(), segment.originLocationId());
    assertEquals(destination.id(), segment.destinationLocationId());
  }

  @Test
  void activeTemplateRequiresAtLeastOneSegment() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            new TripTemplate(
                null,
                TripTemplateCode.of("EMPTY-001"),
                "Empty active template",
                "Invalid template",
                TripTemplateType.LINE_HAUL,
                TripTemplateStatus.ACTIVE,
                List.of(),
                RouteSpecification.empty(),
                ""));
  }

  @Test
  void duplicateSequenceNumbersAreRejected() {
    var milan = LocationDomainTest.milanDepot();
    var bologna = LocationDomainTest.bolognaHub();
    var rome = LocationDomainTest.romeDepot();

    var first =
        new TripTemplateSegment(
            null,
            1,
            TripTemplateSegmentType.TRANSIT,
            milan.id(),
            bologna.id(),
            Distance.km(new BigDecimal("210")),
            "first");
    var duplicate =
        new TripTemplateSegment(
            null,
            1,
            TripTemplateSegmentType.TRANSIT,
            bologna.id(),
            rome.id(),
            Distance.km(new BigDecimal("370")),
            "duplicate");

    assertThrows(
        IllegalArgumentException.class,
        () ->
            new TripTemplate(
                null,
                TripTemplateCode.of("DUP-001"),
                "Duplicate sequence",
                "Invalid sequence",
                TripTemplateType.LINE_HAUL,
                TripTemplateStatus.ACTIVE,
                List.of(first, duplicate),
                RouteSpecification.empty(),
                ""));
  }

  @Test
  void transitSegmentCannotHaveSameOriginAndDestination() {
    var depot = LocationDomainTest.milanDepot();

    assertThrows(
        IllegalArgumentException.class,
        () ->
            new TripTemplateSegment(
                null,
                1,
                TripTemplateSegmentType.TRANSIT,
                depot.id(),
                depot.id(),
                Distance.zeroKm(),
                "Invalid transit"));
  }

  @Test
  void yardMovementCanUseSameLocationWhenNeeded() {
    var yard = LocationDomainTest.florenceYard();

    var segment =
        new TripTemplateSegment(
            null,
            1,
            TripTemplateSegmentType.YARD_MOVEMENT,
            yard.id(),
            yard.id(),
            Distance.zeroKm(),
            "Internal yard movement");

    assertEquals(yard.id(), segment.originLocationId());
    assertEquals(yard.id(), segment.destinationLocationId());
  }

  @Test
  void distanceCannotBeNegative() {
    assertThrows(IllegalArgumentException.class, () -> Distance.km(new BigDecimal("-1.00")));
  }

  @Test
  void statusIsAnagraphicAndNotExecutionStatus() {
    var template = milanRomeTemplate();

    template.suspend();
    assertEquals(TripTemplateStatus.SUSPENDED, template.status());

    template.archive();
    assertEquals(TripTemplateStatus.ARCHIVED, template.status());
  }

  @Test
  void detectsNonContinuousTemplatesWithoutTreatingItAsPlanning() {
    var milan = LocationDomainTest.milanDepot();
    var bologna = LocationDomainTest.bolognaHub();
    var florence = LocationDomainTest.florenceYard();
    var rome = LocationDomainTest.romeDepot();

    var first =
        new TripTemplateSegment(
            null,
            1,
            TripTemplateSegmentType.TRANSIT,
            milan.id(),
            bologna.id(),
            Distance.km(new BigDecimal("210")),
            "first");
    var second =
        new TripTemplateSegment(
            null,
            2,
            TripTemplateSegmentType.TRANSIT,
            florence.id(),
            rome.id(),
            Distance.km(new BigDecimal("280")),
            "not connected with first");

    var template =
        new TripTemplate(
            null,
            TripTemplateCode.of("BROKEN-001"),
            "Non continuous template",
            "Example of a non-continuous template",
            TripTemplateType.LINE_HAUL,
            TripTemplateStatus.ACTIVE,
            List.of(first, second),
            RouteSpecification.empty(),
            "");

    assertFalse(template.isContinuous());
  }

  private static TripTemplate milanRomeTemplate() {
    var milan = LocationDomainTest.milanDepot();
    var bologna = LocationDomainTest.bolognaHub();
    var florence = LocationDomainTest.florenceYard();
    var rome = LocationDomainTest.romeDepot();

    var first =
        new TripTemplateSegment(
            null,
            1,
            TripTemplateSegmentType.TRANSIT,
            milan.id(),
            bologna.id(),
            Distance.km(new BigDecimal("210")),
            "Milano to Bologna");
    var second =
        new TripTemplateSegment(
            null,
            2,
            TripTemplateSegmentType.TRANSIT,
            bologna.id(),
            florence.id(),
            Distance.km(new BigDecimal("115")),
            "Bologna to Firenze Yard");
    var third =
        new TripTemplateSegment(
            null,
            3,
            TripTemplateSegmentType.TRANSIT,
            florence.id(),
            rome.id(),
            Distance.km(new BigDecimal("280")),
            "Firenze Yard to Roma");

    var routeSpecification =
        new RouteSpecification(
            Distance.km(new BigDecimal("605")),
            Duration.ofHours(7).plusMinutes(30),
            Set.of("it"),
            Set.of(RouteRoadType.MOTORWAY, RouteRoadType.NATIONAL_ROAD),
            "Indicative route specification");

    return new TripTemplate(
        null,
        TripTemplateCode.of("linehaul-001"),
        "Milano - Roma con soste",
        "Percorso tipo con hub e yard intermedi",
        TripTemplateType.LINE_HAUL,
        TripTemplateStatus.ACTIVE,
        List.of(first, second, third),
        routeSpecification,
        "Template descrittivo, non operativo");
  }
}
