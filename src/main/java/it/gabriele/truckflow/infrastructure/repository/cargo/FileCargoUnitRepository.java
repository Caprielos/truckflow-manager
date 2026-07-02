package it.gabriele.truckflow.infrastructure.repository.cargo;

import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.out.cargo.CargoUnitRepository;
import it.gabriele.truckflow.domain.cargo.CargoCode;
import it.gabriele.truckflow.domain.cargo.CargoId;
import it.gabriele.truckflow.domain.cargo.CargoUnit;
import it.gabriele.truckflow.infrastructure.repository.InfrastructureRepositoryAdapter;
import it.gabriele.truckflow.infrastructure.repository.file.FileRepositoryStorage;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/** File-backed implementation of the cargo unit repository port. */
public final class FileCargoUnitRepository
    implements CargoUnitRepository, InfrastructureRepositoryAdapter {

  private final FileRepositoryStorage<CargoUnitPersistenceRecord> storage;
  private final CargoUnitPersistenceMapper mapper;

  public FileCargoUnitRepository(Path storageFile) {
    this(storageFile, new CargoUnitPersistenceMapper());
  }

  public FileCargoUnitRepository(Path storageFile, CargoUnitPersistenceMapper mapper) {
    UseCaseValidationException.requireNonNull(mapper, "mapper");
    this.storage =
        new FileRepositoryStorage<>(storageFile, new CargoUnitFileRecordCodec(), "cargo unit");
    this.mapper = mapper;
  }

  @Override
  public String adapterName() {
    return "file-cargo-unit-repository";
  }

  @Override
  public String implementedPortName() {
    return CargoUnitRepository.class.getName();
  }

  @Override
  public CargoUnit save(CargoUnit cargoUnit) {
    UseCaseValidationException.requireNonNull(cargoUnit, "cargoUnit");

    Map<CargoId, CargoUnit> cargoUnits = loadAllById();
    Optional<CargoUnit> duplicate =
        cargoUnits.values().stream()
            .filter(existing -> existing.code().equals(cargoUnit.code()))
            .filter(existing -> !existing.id().equals(cargoUnit.id()))
            .findFirst();

    if (duplicate.isPresent()) {
      throw new DuplicateResourceException("CargoUnit", cargoUnit.code().value());
    }

    cargoUnits.put(cargoUnit.id(), cargoUnit);
    writeAll(cargoUnits);
    return cargoUnit;
  }

  @Override
  public Optional<CargoUnit> findById(CargoId id) {
    UseCaseValidationException.requireNonNull(id, "id");
    return Optional.ofNullable(loadAllById().get(id));
  }

  @Override
  public Optional<CargoUnit> findByCode(CargoCode code) {
    UseCaseValidationException.requireNonNull(code, "code");
    return loadAllById().values().stream()
        .filter(cargoUnit -> cargoUnit.code().equals(code))
        .findFirst();
  }

  @Override
  public boolean existsById(CargoId id) {
    UseCaseValidationException.requireNonNull(id, "id");
    return loadAllById().containsKey(id);
  }

  @Override
  public boolean existsByCode(CargoCode code) {
    UseCaseValidationException.requireNonNull(code, "code");
    return findByCode(code).isPresent();
  }

  private Map<CargoId, CargoUnit> loadAllById() {
    Map<CargoId, CargoUnit> cargoUnits = new LinkedHashMap<>();
    for (CargoUnitPersistenceRecord record : storage.readAll()) {
      CargoUnit cargoUnit = mapper.toDomain(record);
      cargoUnits.put(cargoUnit.id(), cargoUnit);
    }
    return cargoUnits;
  }

  private void writeAll(Map<CargoId, CargoUnit> cargoUnits) {
    storage.writeAll(
        cargoUnits.values().stream().map(mapper::toPersistence).toList(),
        Comparator.comparing(CargoUnitPersistenceRecord::code));
  }
}
