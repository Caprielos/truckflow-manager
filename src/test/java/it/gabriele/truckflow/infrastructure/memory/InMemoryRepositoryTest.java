package it.gabriele.truckflow.infrastructure.memory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.out.cargo.CargoUnitRepository;
import it.gabriele.truckflow.application.port.out.documents.DocumentRepository;
import it.gabriele.truckflow.application.port.out.locations.LocationRepository;
import it.gabriele.truckflow.application.port.out.shipments.ShipmentRepository;
import it.gabriele.truckflow.domain.cargo.CargoCategory;
import it.gabriele.truckflow.domain.cargo.CargoCode;
import it.gabriele.truckflow.domain.cargo.CargoCompatibilityRequirement;
import it.gabriele.truckflow.domain.cargo.CargoDimensions;
import it.gabriele.truckflow.domain.cargo.CargoHazard;
import it.gabriele.truckflow.domain.cargo.CargoId;
import it.gabriele.truckflow.domain.cargo.CargoPackaging;
import it.gabriele.truckflow.domain.cargo.CargoProperties;
import it.gabriele.truckflow.domain.cargo.CargoRegulatory;
import it.gabriele.truckflow.domain.cargo.CargoStatus;
import it.gabriele.truckflow.domain.cargo.CargoTemperature;
import it.gabriele.truckflow.domain.cargo.CargoType;
import it.gabriele.truckflow.domain.cargo.CargoUnit;
import it.gabriele.truckflow.domain.cargo.CargoWeights;
import it.gabriele.truckflow.domain.documents.Document;
import it.gabriele.truckflow.domain.documents.DocumentCategory;
import it.gabriele.truckflow.domain.documents.DocumentCode;
import it.gabriele.truckflow.domain.documents.DocumentContent;
import it.gabriele.truckflow.domain.documents.DocumentId;
import it.gabriele.truckflow.domain.documents.DocumentMetadata;
import it.gabriele.truckflow.domain.documents.DocumentReference;
import it.gabriele.truckflow.domain.documents.DocumentStatus;
import it.gabriele.truckflow.domain.documents.DocumentType;
import it.gabriele.truckflow.domain.locations.Location;
import it.gabriele.truckflow.domain.locations.LocationAddress;
import it.gabriele.truckflow.domain.locations.LocationCode;
import it.gabriele.truckflow.domain.locations.LocationId;
import it.gabriele.truckflow.domain.locations.LocationStatus;
import it.gabriele.truckflow.domain.locations.LocationType;
import it.gabriele.truckflow.domain.shipments.core.Shipment;
import it.gabriele.truckflow.domain.shipments.core.ShipmentCode;
import it.gabriele.truckflow.domain.shipments.core.ShipmentId;
import it.gabriele.truckflow.domain.shipments.core.ShipmentPriority;
import it.gabriele.truckflow.domain.shipments.core.ShipmentServiceLevel;
import it.gabriele.truckflow.domain.shipments.core.ShipmentStatus;
import it.gabriele.truckflow.domain.shipments.metrics.ShipmentMetrics;
import it.gabriele.truckflow.domain.shipments.notes.ShipmentNotes;
import it.gabriele.truckflow.domain.shipments.properties.ShipmentProperties;
import it.gabriele.truckflow.domain.shipments.properties.ShipmentTemperature;
import it.gabriele.truckflow.domain.shipments.references.ShipmentReferences;
import it.gabriele.truckflow.domain.shipments.requirements.ShipmentRequirementSet;
import it.gabriele.truckflow.infrastructure.memory.cargo.InMemoryCargoUnitRepository;
import it.gabriele.truckflow.infrastructure.memory.documents.InMemoryDocumentRepository;
import it.gabriele.truckflow.infrastructure.memory.locations.InMemoryLocationRepository;
import it.gabriele.truckflow.infrastructure.memory.shipments.InMemoryShipmentRepository;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class InMemoryRepositoryTest {

  @Test
  void inMemoryLocationRepositoryImplementsApplicationPortAndFindsByIdAndCode() {
    LocationRepository repository = new InMemoryLocationRepository();
    var location = location("LOC-MIL-001");

    Location saved = repository.save(location);

    assertEquals(location, saved);
    assertTrue(repository.existsById(location.id()));
    assertTrue(repository.existsByCode(LocationCode.of("loc-mil-001")));
    assertEquals(location, repository.findById(location.id()).orElseThrow());
    assertEquals(location, repository.findByCode(location.code()).orElseThrow());
    assertFalse(repository.findById(LocationId.random()).isPresent());
  }

  @Test
  void inMemoryLocationRepositoryRejectsDuplicateCodesForDifferentLocations() {
    LocationRepository repository = new InMemoryLocationRepository();
    repository.save(location("LOC-DUP-001"));

    var duplicate = location("loc-dup-001");

    assertThrows(DuplicateResourceException.class, () -> repository.save(duplicate));
  }

  @Test
  void inMemoryCargoUnitRepositoryImplementsApplicationPortAndFindsByIdAndCode() {
    CargoUnitRepository repository = new InMemoryCargoUnitRepository();
    var cargoUnit = cargoUnit("CGO-001");

    CargoUnit saved = repository.save(cargoUnit);

    assertEquals(cargoUnit, saved);
    assertTrue(repository.existsById(cargoUnit.id()));
    assertTrue(repository.existsByCode(CargoCode.of("cgo-001")));
    assertEquals(cargoUnit, repository.findById(cargoUnit.id()).orElseThrow());
    assertEquals(cargoUnit, repository.findByCode(cargoUnit.code()).orElseThrow());
    assertFalse(repository.findById(CargoId.random()).isPresent());
  }

  @Test
  void inMemoryCargoUnitRepositoryRejectsDuplicateCodesForDifferentCargoUnits() {
    CargoUnitRepository repository = new InMemoryCargoUnitRepository();
    repository.save(cargoUnit("CGO-DUP-001"));

    var duplicate = cargoUnit("cgo-dup-001");

    assertThrows(DuplicateResourceException.class, () -> repository.save(duplicate));
  }

  @Test
  void inMemoryShipmentRepositoryImplementsApplicationPortAndFindsByIdAndCode() {
    ShipmentRepository repository = new InMemoryShipmentRepository();
    var shipment = shipment("SHP-001");

    Shipment saved = repository.save(shipment);

    assertEquals(shipment, saved);
    assertTrue(repository.existsById(shipment.id()));
    assertTrue(repository.existsByCode(ShipmentCode.of("shp-001")));
    assertEquals(shipment, repository.findById(shipment.id()).orElseThrow());
    assertEquals(shipment, repository.findByCode(shipment.code()).orElseThrow());
    assertFalse(repository.findById(ShipmentId.random()).isPresent());
  }

  @Test
  void inMemoryShipmentRepositoryRejectsDuplicateCodesForDifferentShipments() {
    ShipmentRepository repository = new InMemoryShipmentRepository();
    repository.save(shipment("SHP-DUP-001"));

    var duplicate = shipment("shp-dup-001");

    assertThrows(DuplicateResourceException.class, () -> repository.save(duplicate));
  }

  @Test
  void inMemoryDocumentRepositoryImplementsApplicationPortAndFindsByIdAndCode() {
    DocumentRepository repository = new InMemoryDocumentRepository();
    var document = document("DOC-001");

    Document saved = repository.save(document);

    assertEquals(document, saved);
    assertTrue(repository.existsById(document.id()));
    assertTrue(repository.existsByCode(DocumentCode.of("doc-001")));
    assertEquals(document, repository.findById(document.id()).orElseThrow());
    assertEquals(document, repository.findByCode(document.code()).orElseThrow());
    assertFalse(repository.findById(DocumentId.random()).isPresent());
  }

  @Test
  void inMemoryDocumentRepositoryRejectsDuplicateCodesForDifferentDocuments() {
    DocumentRepository repository = new InMemoryDocumentRepository();
    repository.save(document("DOC-DUP-001"));

    var duplicate = document("doc-dup-001");

    assertThrows(DuplicateResourceException.class, () -> repository.save(duplicate));
  }

  @Test
  void inMemoryRepositoriesRejectNullInputsAsApplicationValidationErrors() {
    LocationRepository locationRepository = new InMemoryLocationRepository();
    CargoUnitRepository cargoUnitRepository = new InMemoryCargoUnitRepository();
    ShipmentRepository shipmentRepository = new InMemoryShipmentRepository();
    DocumentRepository documentRepository = new InMemoryDocumentRepository();

    assertThrows(UseCaseValidationException.class, () -> locationRepository.save(null));
    assertThrows(UseCaseValidationException.class, () -> locationRepository.findById(null));
    assertThrows(UseCaseValidationException.class, () -> locationRepository.findByCode(null));

    assertThrows(UseCaseValidationException.class, () -> cargoUnitRepository.save(null));
    assertThrows(UseCaseValidationException.class, () -> cargoUnitRepository.findById(null));
    assertThrows(UseCaseValidationException.class, () -> cargoUnitRepository.findByCode(null));

    assertThrows(UseCaseValidationException.class, () -> shipmentRepository.save(null));
    assertThrows(UseCaseValidationException.class, () -> shipmentRepository.findById(null));
    assertThrows(UseCaseValidationException.class, () -> shipmentRepository.findByCode(null));

    assertThrows(UseCaseValidationException.class, () -> documentRepository.save(null));
    assertThrows(UseCaseValidationException.class, () -> documentRepository.findById(null));
    assertThrows(UseCaseValidationException.class, () -> documentRepository.findByCode(null));
  }

  private static Location location(String code) {
    return new Location(
        null,
        LocationCode.of(code),
        "Location " + code,
        LocationType.WAREHOUSE,
        LocationStatus.ACTIVE,
        LocationAddress.empty(),
        null,
        "In-memory repository test location");
  }

  private static CargoUnit cargoUnit(String code) {
    return new CargoUnit(
        null,
        CargoCode.of(code),
        "Cargo " + code,
        "In-memory repository test cargo",
        CargoType.GENERAL_GOODS,
        Set.of(CargoCategory.DRY, CargoCategory.PALLETIZED),
        CargoDimensions.empty(),
        CargoWeights.empty(),
        CargoPackaging.loose(),
        CargoTemperature.uncontrolled(),
        CargoHazard.none(),
        CargoRegulatory.none(),
        CargoProperties.standard(),
        CargoCompatibilityRequirement.none(),
        CargoStatus.ACTIVE,
        "");
  }

  private static Shipment shipment(String code) {
    return new Shipment(
        null,
        ShipmentCode.of(code),
        "Shipment " + code,
        "In-memory repository test shipment",
        ShipmentStatus.DRAFT,
        ShipmentPriority.NORMAL,
        ShipmentServiceLevel.STANDARD,
        List.of(),
        List.of(),
        ShipmentProperties.standard(),
        ShipmentTemperature.uncontrolled(),
        ShipmentRequirementSet.none(),
        ShipmentMetrics.empty(),
        ShipmentReferences.empty(),
        ShipmentNotes.empty(),
        "");
  }

  private static Document document(String code) {
    return new Document(
        null,
        DocumentCode.of(code),
        DocumentType.GENERIC,
        DocumentCategory.GENERIC,
        DocumentStatus.DRAFT,
        DocumentMetadata.minimal("Document " + code),
        DocumentContent.empty(),
        Set.<DocumentReference>of(),
        "In-memory repository test document");
  }
}
