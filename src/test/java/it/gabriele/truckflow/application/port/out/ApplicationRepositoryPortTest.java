package it.gabriele.truckflow.application.port.out;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ApplicationRepositoryPortTest {

  @Test
  void locationRepositoryPortStoresAndFindsLocationsByIdAndCode() {
    var repository = new InMemoryTestLocationRepository();
    var location = location("LOC-MILANO");

    Location saved = repository.save(location);

    assertEquals(location, saved);
    assertTrue(repository.existsById(location.id()));
    assertTrue(repository.existsByCode(LocationCode.of("loc-milano")));
    assertEquals(location, repository.findById(location.id()).orElseThrow());
    assertEquals(location, repository.findByCode(location.code()).orElseThrow());
    assertFalse(repository.existsByCode(LocationCode.of("LOC-ROMA")));
  }

  @Test
  void cargoUnitRepositoryPortStoresAndFindsCargoUnitsByIdAndCode() {
    var repository = new InMemoryTestCargoUnitRepository();
    var cargoUnit = cargoUnit("CGO-001");

    CargoUnit saved = repository.save(cargoUnit);

    assertEquals(cargoUnit, saved);
    assertTrue(repository.existsById(cargoUnit.id()));
    assertTrue(repository.existsByCode(CargoCode.of("cgo-001")));
    assertEquals(cargoUnit, repository.findById(cargoUnit.id()).orElseThrow());
    assertEquals(cargoUnit, repository.findByCode(cargoUnit.code()).orElseThrow());
    assertFalse(repository.existsByCode(CargoCode.of("CGO-404")));
  }

  @Test
  void shipmentRepositoryPortStoresAndFindsShipmentsByIdAndCode() {
    var repository = new InMemoryTestShipmentRepository();
    var shipment = shipment("SHP-001");

    Shipment saved = repository.save(shipment);

    assertEquals(shipment, saved);
    assertTrue(repository.existsById(shipment.id()));
    assertTrue(repository.existsByCode(ShipmentCode.of("shp-001")));
    assertEquals(shipment, repository.findById(shipment.id()).orElseThrow());
    assertEquals(shipment, repository.findByCode(shipment.code()).orElseThrow());
    assertFalse(repository.existsByCode(ShipmentCode.of("SHP-404")));
  }

  @Test
  void documentRepositoryPortStoresAndFindsDocumentsByIdAndCode() {
    var repository = new InMemoryTestDocumentRepository();
    var document = document("DOC-001");

    Document saved = repository.save(document);

    assertEquals(document, saved);
    assertTrue(repository.existsById(document.id()));
    assertTrue(repository.existsByCode(DocumentCode.of("doc-001")));
    assertEquals(document, repository.findById(document.id()).orElseThrow());
    assertEquals(document, repository.findByCode(document.code()).orElseThrow());
    assertFalse(repository.existsByCode(DocumentCode.of("DOC-404")));
  }

  @Test
  void repositoryPortsAreApplicationContractsAndNotConcreteInfrastructure() {
    assertTrue(RepositoryPort.class.isAssignableFrom(LocationRepository.class));
    assertTrue(RepositoryPort.class.isAssignableFrom(CargoUnitRepository.class));
    assertTrue(RepositoryPort.class.isAssignableFrom(ShipmentRepository.class));
    assertTrue(RepositoryPort.class.isAssignableFrom(DocumentRepository.class));
    assertTrue(LocationRepository.class.isInterface());
    assertTrue(CargoUnitRepository.class.isInterface());
    assertTrue(ShipmentRepository.class.isInterface());
    assertTrue(DocumentRepository.class.isInterface());
  }

  private static Location location(String code) {
    return new Location(
        null,
        LocationCode.of(code),
        "Warehouse " + code,
        LocationType.WAREHOUSE,
        LocationStatus.ACTIVE,
        LocationAddress.empty(),
        null,
        "Application repository port test location");
  }

  private static CargoUnit cargoUnit(String code) {
    return new CargoUnit(
        null,
        CargoCode.of(code),
        "General cargo " + code,
        "Application repository port test cargo",
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
        "Application repository port test shipment",
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
        "Application repository port test document");
  }

  private static final class InMemoryTestLocationRepository implements LocationRepository {

    private final Map<LocationId, Location> byId = new HashMap<>();
    private final Map<LocationCode, Location> byCode = new HashMap<>();

    @Override
    public Location save(Location location) {
      byId.put(location.id(), location);
      byCode.put(location.code(), location);
      return location;
    }

    @Override
    public Optional<Location> findById(LocationId id) {
      return Optional.ofNullable(byId.get(id));
    }

    @Override
    public Optional<Location> findByCode(LocationCode code) {
      return Optional.ofNullable(byCode.get(code));
    }

    @Override
    public boolean existsById(LocationId id) {
      return byId.containsKey(id);
    }

    @Override
    public boolean existsByCode(LocationCode code) {
      return byCode.containsKey(code);
    }
  }

  private static final class InMemoryTestCargoUnitRepository implements CargoUnitRepository {

    private final Map<CargoId, CargoUnit> byId = new HashMap<>();
    private final Map<CargoCode, CargoUnit> byCode = new HashMap<>();

    @Override
    public CargoUnit save(CargoUnit cargoUnit) {
      byId.put(cargoUnit.id(), cargoUnit);
      byCode.put(cargoUnit.code(), cargoUnit);
      return cargoUnit;
    }

    @Override
    public Optional<CargoUnit> findById(CargoId id) {
      return Optional.ofNullable(byId.get(id));
    }

    @Override
    public Optional<CargoUnit> findByCode(CargoCode code) {
      return Optional.ofNullable(byCode.get(code));
    }

    @Override
    public boolean existsById(CargoId id) {
      return byId.containsKey(id);
    }

    @Override
    public boolean existsByCode(CargoCode code) {
      return byCode.containsKey(code);
    }
  }

  private static final class InMemoryTestShipmentRepository implements ShipmentRepository {

    private final Map<ShipmentId, Shipment> byId = new HashMap<>();
    private final Map<ShipmentCode, Shipment> byCode = new HashMap<>();

    @Override
    public Shipment save(Shipment shipment) {
      byId.put(shipment.id(), shipment);
      byCode.put(shipment.code(), shipment);
      return shipment;
    }

    @Override
    public Optional<Shipment> findById(ShipmentId id) {
      return Optional.ofNullable(byId.get(id));
    }

    @Override
    public Optional<Shipment> findByCode(ShipmentCode code) {
      return Optional.ofNullable(byCode.get(code));
    }

    @Override
    public boolean existsById(ShipmentId id) {
      return byId.containsKey(id);
    }

    @Override
    public boolean existsByCode(ShipmentCode code) {
      return byCode.containsKey(code);
    }
  }

  private static final class InMemoryTestDocumentRepository implements DocumentRepository {

    private final Map<DocumentId, Document> byId = new HashMap<>();
    private final Map<DocumentCode, Document> byCode = new HashMap<>();

    @Override
    public Document save(Document document) {
      byId.put(document.id(), document);
      byCode.put(document.code(), document);
      return document;
    }

    @Override
    public Optional<Document> findById(DocumentId id) {
      return Optional.ofNullable(byId.get(id));
    }

    @Override
    public Optional<Document> findByCode(DocumentCode code) {
      return Optional.ofNullable(byCode.get(code));
    }

    @Override
    public boolean existsById(DocumentId id) {
      return byId.containsKey(id);
    }

    @Override
    public boolean existsByCode(DocumentCode code) {
      return byCode.containsKey(code);
    }
  }
}
