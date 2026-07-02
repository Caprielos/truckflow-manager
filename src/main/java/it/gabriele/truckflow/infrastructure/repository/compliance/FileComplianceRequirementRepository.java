package it.gabriele.truckflow.infrastructure.repository.compliance;

import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.out.compliance.ComplianceRequirementRepository;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirement;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirementCode;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirementId;
import it.gabriele.truckflow.infrastructure.repository.InfrastructureRepositoryAdapter;
import it.gabriele.truckflow.infrastructure.repository.file.FileRepositoryStorage;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/** File-backed implementation of the compliance requirement repository port. */
public final class FileComplianceRequirementRepository
    implements ComplianceRequirementRepository, InfrastructureRepositoryAdapter {

  private final FileRepositoryStorage<ComplianceRequirementPersistenceRecord> storage;
  private final ComplianceRequirementPersistenceMapper mapper;

  public FileComplianceRequirementRepository(Path storageFile) {
    this(storageFile, new ComplianceRequirementPersistenceMapper());
  }

  public FileComplianceRequirementRepository(
      Path storageFile, ComplianceRequirementPersistenceMapper mapper) {
    UseCaseValidationException.requireNonNull(mapper, "mapper");
    this.storage =
        new FileRepositoryStorage<>(
            storageFile, new ComplianceRequirementFileRecordCodec(), "compliance requirement");
    this.mapper = mapper;
  }

  @Override
  public String adapterName() {
    return "file-compliance-requirement-repository";
  }

  @Override
  public String implementedPortName() {
    return ComplianceRequirementRepository.class.getName();
  }

  @Override
  public ComplianceRequirement save(ComplianceRequirement requirement) {
    UseCaseValidationException.requireNonNull(requirement, "requirement");

    Map<ComplianceRequirementId, ComplianceRequirement> requirements = loadAllById();
    Optional<ComplianceRequirement> duplicate =
        requirements.values().stream()
            .filter(existing -> existing.code().equals(requirement.code()))
            .filter(existing -> !existing.id().equals(requirement.id()))
            .findFirst();

    if (duplicate.isPresent()) {
      throw new DuplicateResourceException("ComplianceRequirement", requirement.code().value());
    }

    requirements.put(requirement.id(), requirement);
    writeAll(requirements);
    return requirement;
  }

  @Override
  public Optional<ComplianceRequirement> findById(ComplianceRequirementId id) {
    UseCaseValidationException.requireNonNull(id, "id");
    return Optional.ofNullable(loadAllById().get(id));
  }

  @Override
  public Optional<ComplianceRequirement> findByCode(ComplianceRequirementCode code) {
    UseCaseValidationException.requireNonNull(code, "code");
    return loadAllById().values().stream()
        .filter(requirement -> requirement.code().equals(code))
        .findFirst();
  }

  @Override
  public boolean existsById(ComplianceRequirementId id) {
    UseCaseValidationException.requireNonNull(id, "id");
    return loadAllById().containsKey(id);
  }

  @Override
  public boolean existsByCode(ComplianceRequirementCode code) {
    UseCaseValidationException.requireNonNull(code, "code");
    return findByCode(code).isPresent();
  }

  private Map<ComplianceRequirementId, ComplianceRequirement> loadAllById() {
    Map<ComplianceRequirementId, ComplianceRequirement> requirements = new LinkedHashMap<>();
    for (ComplianceRequirementPersistenceRecord record : storage.readAll()) {
      ComplianceRequirement requirement = mapper.toDomain(record);
      requirements.put(requirement.id(), requirement);
    }
    return requirements;
  }

  private void writeAll(Map<ComplianceRequirementId, ComplianceRequirement> requirements) {
    storage.writeAll(
        requirements.values().stream().map(mapper::toPersistence).toList(),
        Comparator.comparing(ComplianceRequirementPersistenceRecord::code));
  }
}
