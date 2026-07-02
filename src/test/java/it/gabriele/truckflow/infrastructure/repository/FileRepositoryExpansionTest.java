package it.gabriele.truckflow.infrastructure.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.out.cargo.CargoUnitRepository;
import it.gabriele.truckflow.application.port.out.compliance.ComplianceRequirementRepository;
import it.gabriele.truckflow.application.port.out.documents.DocumentRepository;
import it.gabriele.truckflow.domain.cargo.CargoCategory;
import it.gabriele.truckflow.domain.cargo.CargoCode;
import it.gabriele.truckflow.domain.cargo.CargoCompatibilityRequirement;
import it.gabriele.truckflow.domain.cargo.CargoDimensions;
import it.gabriele.truckflow.domain.cargo.CargoHazard;
import it.gabriele.truckflow.domain.cargo.CargoId;
import it.gabriele.truckflow.domain.cargo.CargoPackaging;
import it.gabriele.truckflow.domain.cargo.CargoPackagingType;
import it.gabriele.truckflow.domain.cargo.CargoProperties;
import it.gabriele.truckflow.domain.cargo.CargoRegulatory;
import it.gabriele.truckflow.domain.cargo.CargoStatus;
import it.gabriele.truckflow.domain.cargo.CargoTemperature;
import it.gabriele.truckflow.domain.cargo.CargoTransportRequirement;
import it.gabriele.truckflow.domain.cargo.CargoType;
import it.gabriele.truckflow.domain.cargo.CargoUnit;
import it.gabriele.truckflow.domain.cargo.CargoWeights;
import it.gabriele.truckflow.domain.compliance.ComplianceCategory;
import it.gabriele.truckflow.domain.compliance.ComplianceJurisdiction;
import it.gabriele.truckflow.domain.compliance.ComplianceObligationLevel;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirement;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirementCode;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirementId;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirementStatus;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirementType;
import it.gabriele.truckflow.domain.compliance.ComplianceRule;
import it.gabriele.truckflow.domain.compliance.ComplianceSeverity;
import it.gabriele.truckflow.domain.compliance.ComplianceSource;
import it.gabriele.truckflow.domain.compliance.ComplianceSourceType;
import it.gabriele.truckflow.domain.compliance.ComplianceTarget;
import it.gabriele.truckflow.domain.compliance.ComplianceTargetType;
import it.gabriele.truckflow.domain.documents.Document;
import it.gabriele.truckflow.domain.documents.DocumentCategory;
import it.gabriele.truckflow.domain.documents.DocumentCode;
import it.gabriele.truckflow.domain.documents.DocumentContent;
import it.gabriele.truckflow.domain.documents.DocumentId;
import it.gabriele.truckflow.domain.documents.DocumentMetadata;
import it.gabriele.truckflow.domain.documents.DocumentReference;
import it.gabriele.truckflow.domain.documents.DocumentReferenceType;
import it.gabriele.truckflow.domain.documents.DocumentStatus;
import it.gabriele.truckflow.domain.documents.DocumentType;
import it.gabriele.truckflow.infrastructure.mapping.PersistenceMapper;
import it.gabriele.truckflow.infrastructure.repository.cargo.CargoUnitPersistenceMapper;
import it.gabriele.truckflow.infrastructure.repository.cargo.FileCargoUnitRepository;
import it.gabriele.truckflow.infrastructure.repository.compliance.ComplianceRequirementPersistenceMapper;
import it.gabriele.truckflow.infrastructure.repository.compliance.FileComplianceRequirementRepository;
import it.gabriele.truckflow.infrastructure.repository.documents.DocumentPersistenceMapper;
import it.gabriele.truckflow.infrastructure.repository.documents.FileDocumentRepository;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileRepositoryExpansionTest {

  @TempDir Path tempDirectory;

  @Test
  void fileRepositoryExpansionAdaptersImplementApplicationPorts() {
    assertInstanceOf(
        CargoUnitRepository.class, new FileCargoUnitRepository(tempDirectory.resolve("cargo.db")));
    assertInstanceOf(
        DocumentRepository.class,
        new FileDocumentRepository(tempDirectory.resolve("documents.db")));
    assertInstanceOf(
        ComplianceRequirementRepository.class,
        new FileComplianceRequirementRepository(tempDirectory.resolve("compliance.db")));
  }

  @Test
  void fileRepositoryExpansionAdaptersExposeInfrastructureMetadata() {
    var cargoRepository = new FileCargoUnitRepository(tempDirectory.resolve("cargo.db"));
    var documentRepository = new FileDocumentRepository(tempDirectory.resolve("documents.db"));
    var complianceRepository =
        new FileComplianceRequirementRepository(tempDirectory.resolve("compliance.db"));

    assertInstanceOf(InfrastructureRepositoryAdapter.class, cargoRepository);
    assertInstanceOf(InfrastructureRepositoryAdapter.class, documentRepository);
    assertInstanceOf(InfrastructureRepositoryAdapter.class, complianceRepository);
    assertEquals("file-cargo-unit-repository", cargoRepository.adapterName());
    assertEquals("file-document-repository", documentRepository.adapterName());
    assertEquals("file-compliance-requirement-repository", complianceRepository.adapterName());
  }

  @Test
  void cargoUnitsAreSavedAndReloadedThroughFileRepository() {
    Path file = tempDirectory.resolve("cargo.db");
    CargoUnit cargoUnit = sampleCargoUnit("CARGO_A");

    new FileCargoUnitRepository(file).save(cargoUnit);
    CargoUnit loaded = new FileCargoUnitRepository(file).findByCode(cargoUnit.code()).orElseThrow();

    assertEquals(cargoUnit.id(), loaded.id());
    assertEquals(cargoUnit.name(), loaded.name());
    assertEquals(cargoUnit.status(), loaded.status());
    assertTrue(loaded.requires(CargoTransportRequirement.REFRIGERATED_VEHICLE_REQUIRED));
  }

  @Test
  void documentsAreSavedAndReloadedThroughFileRepository() {
    Path file = tempDirectory.resolve("documents.db");
    Document document = sampleDocument("DOC_A");

    new FileDocumentRepository(file).save(document);
    Document loaded = new FileDocumentRepository(file).findByCode(document.code()).orElseThrow();

    assertEquals(document.id(), loaded.id());
    assertEquals(document.metadata().title(), loaded.metadata().title());
    assertEquals(document.content().summary(), loaded.content().summary());
    assertTrue(loaded.hasReference(DocumentReferenceType.SHIPMENT, "SHP-001"));
  }

  @Test
  void complianceRequirementsAreSavedAndReloadedThroughFileRepository() {
    Path file = tempDirectory.resolve("compliance.db");
    ComplianceRequirement requirement = sampleComplianceRequirement("CMP_A");

    new FileComplianceRequirementRepository(file).save(requirement);
    ComplianceRequirement loaded =
        new FileComplianceRequirementRepository(file).findByCode(requirement.code()).orElseThrow();

    assertEquals(requirement.id(), loaded.id());
    assertEquals(requirement.name(), loaded.name());
    assertEquals(requirement.jurisdiction().countryValue(), loaded.jurisdiction().countryValue());
    assertTrue(loaded.appliesTo(ComplianceTargetType.VEHICLE));
  }

  @Test
  void fileRepositoryExpansionRejectsDuplicateBusinessCodes() {
    FileCargoUnitRepository cargoRepository =
        new FileCargoUnitRepository(tempDirectory.resolve("cargo.db"));
    FileDocumentRepository documentRepository =
        new FileDocumentRepository(tempDirectory.resolve("documents.db"));
    FileComplianceRequirementRepository complianceRepository =
        new FileComplianceRequirementRepository(tempDirectory.resolve("compliance.db"));

    cargoRepository.save(sampleCargoUnit("DUP_CARGO"));
    documentRepository.save(sampleDocument("DUP_DOC"));
    complianceRepository.save(sampleComplianceRequirement("DUP_CMP"));

    assertThrows(
        DuplicateResourceException.class, () -> cargoRepository.save(sampleCargoUnit("DUP_CARGO")));
    assertThrows(
        DuplicateResourceException.class, () -> documentRepository.save(sampleDocument("DUP_DOC")));
    assertThrows(
        DuplicateResourceException.class,
        () -> complianceRepository.save(sampleComplianceRequirement("DUP_CMP")));
  }

  @Test
  void fileRepositoryExpansionTreatsMissingFilesAsEmptyRepositories() {
    assertFalse(
        new FileCargoUnitRepository(tempDirectory.resolve("missing-cargo.db"))
            .existsByCode(CargoCode.of("UNKNOWN")));
    assertFalse(
        new FileDocumentRepository(tempDirectory.resolve("missing-docs.db"))
            .existsByCode(DocumentCode.of("UNKNOWN")));
    assertFalse(
        new FileComplianceRequirementRepository(tempDirectory.resolve("missing-compliance.db"))
            .existsByCode(ComplianceRequirementCode.of("UNKNOWN")));
  }

  @Test
  void fileRepositoryExpansionRejectsInvalidInputs() {
    var cargoRepository = new FileCargoUnitRepository(tempDirectory.resolve("cargo.db"));
    var documentRepository = new FileDocumentRepository(tempDirectory.resolve("documents.db"));
    var complianceRepository =
        new FileComplianceRequirementRepository(tempDirectory.resolve("compliance.db"));

    assertThrows(UseCaseValidationException.class, () -> new FileCargoUnitRepository(null));
    assertThrows(UseCaseValidationException.class, () -> new FileDocumentRepository(null));
    assertThrows(
        UseCaseValidationException.class, () -> new FileComplianceRequirementRepository(null));
    assertThrows(UseCaseValidationException.class, () -> cargoRepository.save(null));
    assertThrows(UseCaseValidationException.class, () -> documentRepository.save(null));
    assertThrows(UseCaseValidationException.class, () -> complianceRepository.save(null));
    assertThrows(UseCaseValidationException.class, () -> cargoRepository.findByCode(null));
    assertThrows(UseCaseValidationException.class, () -> documentRepository.findByCode(null));
    assertThrows(UseCaseValidationException.class, () -> complianceRepository.findByCode(null));
  }

  @Test
  void expansionMappersRoundTripDomainAndPersistenceModels() {
    var cargoMapper = new CargoUnitPersistenceMapper();
    var documentMapper = new DocumentPersistenceMapper();
    var complianceMapper = new ComplianceRequirementPersistenceMapper();

    CargoUnit cargoUnit = sampleCargoUnit("MAP_CARGO");
    Document document = sampleDocument("MAP_DOC");
    ComplianceRequirement requirement = sampleComplianceRequirement("MAP_CMP");

    assertInstanceOf(PersistenceMapper.class, cargoMapper);
    assertInstanceOf(PersistenceMapper.class, documentMapper);
    assertInstanceOf(PersistenceMapper.class, complianceMapper);
    assertEquals(
        cargoUnit.code(), cargoMapper.toDomain(cargoMapper.toPersistence(cargoUnit)).code());
    assertEquals(
        document.code(), documentMapper.toDomain(documentMapper.toPersistence(document)).code());
    assertEquals(
        requirement.code(),
        complianceMapper.toDomain(complianceMapper.toPersistence(requirement)).code());
  }

  private static CargoUnit sampleCargoUnit(String code) {
    return new CargoUnit(
        CargoId.random(),
        CargoCode.of(code),
        "Fresh cargo " + code,
        "Temperature-controlled palletized goods",
        CargoType.FOOD,
        Set.of(CargoCategory.REFRIGERATED, CargoCategory.PALLETIZED),
        new CargoDimensions(
            new BigDecimal("1.20"),
            new BigDecimal("0.80"),
            new BigDecimal("1.60"),
            new BigDecimal("1.50")),
        new CargoWeights(new BigDecimal("700"), new BigDecimal("650"), new BigDecimal("50")),
        new CargoPackaging(CargoPackagingType.PALLET, 20, 10, "EUR", true, "Stack carefully"),
        new CargoTemperature(new BigDecimal("2"), new BigDecimal("6"), true, "Chilled"),
        CargoHazard.none(),
        new CargoRegulatory(false, true, true, false, false, false, "ATP and food grade"),
        new CargoProperties(false, true, false, false, true, "Separated from dry goods"),
        new CargoCompatibilityRequirement(
            Set.of(
                CargoTransportRequirement.REFRIGERATED_VEHICLE_REQUIRED,
                CargoTransportRequirement.TEMPERATURE_CONTROL_REQUIRED,
                CargoTransportRequirement.ATP_CERTIFICATION_REQUIRED,
                CargoTransportRequirement.FOOD_GRADE_BODY_REQUIRED,
                CargoTransportRequirement.SEPARATION_REQUIRED),
            new BigDecimal("900"),
            new BigDecimal("2"),
            new BigDecimal("2"),
            new BigDecimal("1"),
            new BigDecimal("2"),
            "Refrigerated vehicle required"),
        CargoStatus.ACTIVE,
        "Repository expansion sample");
  }

  private static Document sampleDocument(String code) {
    return new Document(
        DocumentId.random(),
        DocumentCode.of(code),
        DocumentType.CMR,
        DocumentCategory.SHIPMENT,
        DocumentStatus.ACTIVE,
        new DocumentMetadata(
            "CMR " + code, "Ops", "Transport document", "v1", Set.of("cmr", "transport")),
        new DocumentContent("Logical body", "CMR summary", "No file storage"),
        Set.of(new DocumentReference(DocumentReferenceType.SHIPMENT, "SHP-001", "Linked shipment")),
        "Logical document repository sample");
  }

  private static ComplianceRequirement sampleComplianceRequirement(String code) {
    return new ComplianceRequirement(
        ComplianceRequirementId.random(),
        ComplianceRequirementCode.of(code),
        "Vehicle revision " + code,
        "Base legal requirement catalog entry",
        ComplianceRequirementStatus.ACTIVE,
        ComplianceCategory.VEHICLE,
        ComplianceRequirementType.LEGAL_REQUIREMENT,
        ComplianceObligationLevel.MANDATORY,
        ComplianceSeverity.HIGH,
        new ComplianceTarget(ComplianceTargetType.VEHICLE, "Vehicle target"),
        new ComplianceRule(
            "Revision valid", "Vehicle must have a valid revision", "not expired", "Rule notes"),
        new ComplianceSource(
            "Codice della Strada",
            ComplianceSourceType.NATIONAL_REGULATION,
            "ART-80",
            "Italian vehicle revision source",
            "Source notes"),
        ComplianceJurisdiction.italy(),
        "Compliance repository sample");
  }
}
