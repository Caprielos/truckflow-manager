package it.gabriele.truckflow.domain.qualifications;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class QualificationCatalogTest {

  @Test
  void containsAllQualifications() {
    assertEquals(64, QualificationCatalog.all().size());
  }

  @Test
  void containsUniqueCodes() {
    var uniqueCodes =
        QualificationCatalog.all().stream().map(Qualification::code).collect(Collectors.toSet());

    assertEquals(QualificationCatalog.all().size(), uniqueCodes.size());
  }

  @Test
  void filtersDrivingLicensesByCategory() {
    var qualifications = QualificationCatalog.byCategory(QualificationCategory.PATENTI_GUIDA);

    assertEquals(9, qualifications.size());
    assertTrue(qualifications.contains(Qualification.DRIVING_LICENSE_C));
    assertTrue(qualifications.contains(Qualification.DRIVING_LICENSE_CE));
    assertTrue(qualifications.contains(Qualification.DRIVING_LICENSE_D1E));
  }

  @Test
  void filtersCqcByCategory() {
    var qualifications = QualificationCatalog.byCategory(QualificationCategory.CQC);

    assertEquals(2, qualifications.size());
    assertTrue(qualifications.contains(Qualification.CQC_GOODS));
    assertTrue(qualifications.contains(Qualification.CQC_PASSENGERS));
  }

  @Test
  void filtersAdrByCategory() {
    var qualifications = QualificationCatalog.byCategory(QualificationCategory.ADR);

    assertEquals(11, qualifications.size());
    assertTrue(qualifications.contains(Qualification.ADR_BASIC));
    assertTrue(qualifications.contains(Qualification.ADR_TANK));
    assertTrue(qualifications.contains(Qualification.ADR_CLASS_9));
  }

  @Test
  void filtersFoodAndPharmaceuticalByCategory() {
    var qualifications = QualificationCatalog.byCategory(QualificationCategory.ALIMENTI_FARMACI);

    assertEquals(3, qualifications.size());
    assertTrue(qualifications.contains(Qualification.ATP));
    assertTrue(qualifications.contains(Qualification.HACCP));
    assertTrue(qualifications.contains(Qualification.PHARMACEUTICAL_TRANSPORT));
  }

  @Test
  void filtersAnimalsByCategory() {
    var qualifications = QualificationCatalog.byCategory(QualificationCategory.ANIMALI);

    assertEquals(4, qualifications.size());
    assertTrue(qualifications.contains(Qualification.LIVE_ANIMALS));
    assertTrue(qualifications.contains(Qualification.ANIMAL_WELFARE));
    assertTrue(qualifications.contains(Qualification.SLAUGHTER_ANIMALS));
    assertTrue(qualifications.contains(Qualification.PET_ANIMALS));
  }

  @Test
  void filtersWasteByCategory() {
    var qualifications = QualificationCatalog.byCategory(QualificationCategory.RIFIUTI);

    assertEquals(9, qualifications.size());
    assertTrue(qualifications.contains(Qualification.WASTE_CATEGORY_1));
    assertTrue(qualifications.contains(Qualification.WASTE_CATEGORY_5));
    assertTrue(qualifications.contains(Qualification.WASTE_CATEGORY_10));
  }

  @Test
  void filtersMachineOperatorsByCategory() {
    var qualifications = QualificationCatalog.byCategory(QualificationCategory.MACCHINE_OPERATRICI);

    assertEquals(8, qualifications.size());
    assertTrue(qualifications.contains(Qualification.FORKLIFT));
    assertTrue(qualifications.contains(Qualification.MEWP));
    assertTrue(qualifications.contains(Qualification.TRUCK_MOUNTED_CRANE));
    assertTrue(qualifications.contains(Qualification.TELEHANDLER));
  }

  @Test
  void filtersSafetyByCategory() {
    var qualifications = QualificationCatalog.byCategory(QualificationCategory.SICUREZZA);

    assertEquals(6, qualifications.size());
    assertTrue(qualifications.contains(Qualification.CONSTRUCTION_SITE_SAFETY));
    assertTrue(qualifications.contains(Qualification.PPE_CATEGORY_III));
    assertTrue(qualifications.contains(Qualification.ROAD_SIGNAGE));
    assertTrue(qualifications.contains(Qualification.LOAD_HANDLING));
    assertTrue(qualifications.contains(Qualification.FIRE_SAFETY));
    assertTrue(qualifications.contains(Qualification.FIRST_AID));
  }

  @Test
  void filtersPortAndAirportByCategory() {
    var qualifications = QualificationCatalog.byCategory(QualificationCategory.PORTI_AEROPORTI);

    assertEquals(6, qualifications.size());
    assertTrue(qualifications.contains(Qualification.CONTAINER_TRANSPORT));
    assertTrue(qualifications.contains(Qualification.IMO));
    assertTrue(qualifications.contains(Qualification.PORT_AREA_ACCESS));
    assertTrue(qualifications.contains(Qualification.PORT_ADR));
    assertTrue(qualifications.contains(Qualification.AIRPORT_CARGO));
    assertTrue(qualifications.contains(Qualification.AIRPORT_SECURITY));
  }

  @Test
  void filtersCompanyLogisticsByCategory() {
    var qualifications = QualificationCatalog.byCategory(QualificationCategory.AZIENDALI_LOGISTICA);

    assertEquals(6, qualifications.size());
    assertTrue(qualifications.contains(Qualification.LOGISTICS));
    assertTrue(qualifications.contains(Qualification.WAREHOUSE_MANAGEMENT));
    assertTrue(qualifications.contains(Qualification.LOAD_SLINGING));
    assertTrue(qualifications.contains(Qualification.RAMPS_AND_DOCKS));
    assertTrue(qualifications.contains(Qualification.SENSITIVE_GOODS_TRANSPORT));
    assertTrue(qualifications.contains(Qualification.MEDICAL_EQUIPMENT_TRANSPORT));
  }

  @Test
  void findsQualificationByCode() {
    var qualification = QualificationCatalog.findByCode("DRIVING_LICENSE_C");

    assertTrue(qualification.isPresent());
    assertEquals("Driving License C", qualification.orElseThrow().nameValue());
  }
}
