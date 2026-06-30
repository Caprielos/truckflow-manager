package it.gabriele.truckflow.domain.qualifications;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.EnumSet;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class QualificationCatalogTest {

  @Test
  void exposesAllDeclaredQualificationsWithoutDependingOnFixedCatalogSize() {
    assertEquals(EnumSet.allOf(Qualification.class), EnumSet.copyOf(QualificationCatalog.all()));
  }

  @Test
  void containsUniqueCodes() {
    var uniqueCodes =
        QualificationCatalog.all().stream().map(Qualification::code).collect(Collectors.toSet());

    assertEquals(QualificationCatalog.all().size(), uniqueCodes.size());
  }

  @Test
  void everyQualificationHasCompleteMetadata() {
    for (var qualification : QualificationCatalog.all()) {
      assertFalse(qualification.code().isBlank());
      assertFalse(qualification.displayName().isBlank());
      assertNotNull(qualification.category());
      assertFalse(qualification.shortDescription().isBlank());
      assertFalse(qualification.longDescription().isBlank());
    }
  }

  @Test
  void everyCategoryContainsAtLeastOneQualification() {
    for (var category : QualificationCategory.values()) {
      assertFalse(
          QualificationCatalog.byCategory(category).isEmpty(),
          () -> "Expected at least one qualification in category " + category);
    }
  }

  @Test
  void filtersDrivingLicensesByCategory() {
    assertCategoryContains(
        QualificationCategory.DRIVING_LICENSES,
        Qualification.DRIVING_LICENSE_C,
        Qualification.DRIVING_LICENSE_CE,
        Qualification.DRIVING_LICENSE_D1E);
  }

  @Test
  void filtersCqcByCategory() {
    assertCategoryContains(
        QualificationCategory.CQC, Qualification.CQC_GOODS, Qualification.CQC_PASSENGERS);
  }

  @Test
  void filtersAdrByCategory() {
    assertCategoryContains(
        QualificationCategory.ADR,
        Qualification.ADR_BASIC,
        Qualification.ADR_TANK,
        Qualification.ADR_CLASS_9);
  }

  @Test
  void filtersFoodAndPharmaceuticalsByCategory() {
    assertCategoryContains(
        QualificationCategory.FOOD_PHARMACEUTICALS,
        Qualification.ATP,
        Qualification.HACCP,
        Qualification.PHARMACEUTICAL_TRANSPORT);
  }

  @Test
  void filtersAnimalsByCategory() {
    assertCategoryContains(
        QualificationCategory.ANIMALS,
        Qualification.LIVE_ANIMALS,
        Qualification.ANIMAL_WELFARE,
        Qualification.SLAUGHTER_ANIMALS,
        Qualification.PET_ANIMALS);
  }

  @Test
  void filtersWasteByCategory() {
    assertCategoryContains(
        QualificationCategory.WASTE,
        Qualification.WASTE_CATEGORY_1,
        Qualification.WASTE_CATEGORY_5,
        Qualification.WASTE_CATEGORY_10);
  }

  @Test
  void filtersMachineOperatorsByCategory() {
    assertCategoryContains(
        QualificationCategory.MACHINE_OPERATORS,
        Qualification.FORKLIFT,
        Qualification.MEWP,
        Qualification.TRUCK_MOUNTED_CRANE,
        Qualification.TELEHANDLER);
  }

  @Test
  void filtersSafetyByCategory() {
    assertCategoryContains(
        QualificationCategory.SAFETY,
        Qualification.CONSTRUCTION_SITE_SAFETY,
        Qualification.PPE_CATEGORY_III,
        Qualification.ROAD_SIGNAGE,
        Qualification.LOAD_HANDLING,
        Qualification.FIRE_SAFETY,
        Qualification.FIRST_AID);
  }

  @Test
  void filtersPortsAndAirportsByCategory() {
    assertCategoryContains(
        QualificationCategory.PORTS_AND_AIRPORTS,
        Qualification.CONTAINER_TRANSPORT,
        Qualification.IMO,
        Qualification.PORT_AREA_ACCESS,
        Qualification.PORT_ADR,
        Qualification.AIRPORT_CARGO,
        Qualification.AIRPORT_SECURITY);
  }

  @Test
  void filtersCompanyLogisticsByCategory() {
    assertCategoryContains(
        QualificationCategory.COMPANY_LOGISTICS,
        Qualification.LOGISTICS,
        Qualification.WAREHOUSE_MANAGEMENT,
        Qualification.LOAD_SLINGING,
        Qualification.RAMPS_AND_DOCKS,
        Qualification.SENSITIVE_GOODS_TRANSPORT,
        Qualification.MEDICAL_EQUIPMENT_TRANSPORT);
  }

  @Test
  void findsQualificationByCode() {
    var qualification = QualificationCatalog.findByCode("DRIVING_LICENSE_C");

    assertTrue(qualification.isPresent());
    assertEquals("Driving License C", qualification.orElseThrow().displayName());
  }

  @Test
  void returnsEmptyWhenCodeDoesNotExist() {
    assertTrue(QualificationCatalog.findByCode("UNKNOWN_QUALIFICATION").isEmpty());
  }

  @Test
  void exposesCategoryMetadata() {
    assertEquals("Driving Licenses", QualificationCategory.DRIVING_LICENSES.displayName());
    assertEquals("DRIVING_LICENSES", QualificationCategory.DRIVING_LICENSES.code());
  }

  @Test
  void exposesQualificationMetadata() {
    var qualification = Qualification.DRIVING_LICENSE_C;

    assertEquals("DRIVING_LICENSE_C", qualification.code());
    assertEquals("Driving License C", qualification.displayName());
    assertEquals(QualificationCategory.DRIVING_LICENSES, qualification.category());
    assertTrue(qualification.shortDescription().startsWith("License for"));
    assertTrue(qualification.longDescription().contains("heavy goods vehicles"));
  }

  private static void assertCategoryContains(
      QualificationCategory category, Qualification... expectedQualifications) {
    var qualifications = QualificationCatalog.byCategory(category);

    assertFalse(qualifications.isEmpty(), () -> "Expected category to contain values: " + category);
    assertTrue(
        qualifications.stream().allMatch(qualification -> qualification.category() == category),
        () -> "Expected all qualifications to belong to category " + category);

    for (var expectedQualification : expectedQualifications) {
      assertTrue(
          qualifications.contains(expectedQualification),
          () -> "Expected " + expectedQualification + " in category " + category);
    }
  }
}
