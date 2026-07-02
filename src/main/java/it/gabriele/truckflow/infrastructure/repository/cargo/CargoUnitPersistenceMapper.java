package it.gabriele.truckflow.infrastructure.repository.cargo;

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
import it.gabriele.truckflow.infrastructure.exception.MappingException;
import it.gabriele.truckflow.infrastructure.mapping.PersistenceMapper;
import it.gabriele.truckflow.infrastructure.repository.file.FileRepositoryText;
import java.util.Objects;
import java.util.UUID;

/** Maps cargo units to and from the file-backed persistence record used by Punto 7F. */
public final class CargoUnitPersistenceMapper
    implements PersistenceMapper<CargoUnit, CargoUnitPersistenceRecord> {

  @Override
  public CargoUnitPersistenceRecord toPersistence(CargoUnit domainModel) {
    Objects.requireNonNull(domainModel, "domainModel must not be null");

    CargoDimensions dimensions = domainModel.dimensions();
    CargoWeights weights = domainModel.weights();
    CargoPackaging packaging = domainModel.packaging();
    CargoTemperature temperature = domainModel.temperature();
    CargoHazard hazard = domainModel.hazard();
    CargoRegulatory regulatory = domainModel.regulatory();
    CargoProperties properties = domainModel.properties();
    CargoCompatibilityRequirement compatibility = domainModel.compatibilityRequirement();

    return new CargoUnitPersistenceRecord(
        domainModel.id().value().toString(),
        domainModel.code().value(),
        domainModel.name(),
        domainModel.description(),
        domainModel.type().name(),
        FileRepositoryText.enumSet(domainModel.categories()),
        domainModel.status().name(),
        domainModel.notes(),
        FileRepositoryText.value(dimensions.lengthMeters()),
        FileRepositoryText.value(dimensions.widthMeters()),
        FileRepositoryText.value(dimensions.heightMeters()),
        FileRepositoryText.value(dimensions.volumeCubicMeters()),
        FileRepositoryText.value(weights.grossWeightKg()),
        FileRepositoryText.value(weights.netWeightKg()),
        FileRepositoryText.value(weights.tareWeightKg()),
        packaging.packagingType().name(),
        FileRepositoryText.value(packaging.units()),
        FileRepositoryText.value(packaging.pallets()),
        packaging.containerType(),
        Boolean.toString(packaging.stackable()),
        packaging.notes(),
        FileRepositoryText.value(temperature.requiredMinCelsius()),
        FileRepositoryText.value(temperature.requiredMaxCelsius()),
        Boolean.toString(temperature.controlled()),
        temperature.notes(),
        hazard.adrClass(),
        hazard.unNumber(),
        hazard.packingGroup(),
        hazard.specialProvisions(),
        hazard.notes(),
        Boolean.toString(regulatory.adrRequired()),
        Boolean.toString(regulatory.atpRequired()),
        Boolean.toString(regulatory.foodGradeRequired()),
        Boolean.toString(regulatory.pharmaGradeRequired()),
        Boolean.toString(regulatory.wasteAuthorizationRequired()),
        Boolean.toString(regulatory.livestockAuthorizationRequired()),
        regulatory.notes(),
        Boolean.toString(properties.fragile()),
        Boolean.toString(properties.perishable()),
        Boolean.toString(properties.dangerous()),
        Boolean.toString(properties.highValue()),
        Boolean.toString(properties.requiresSeparation()),
        properties.notes(),
        FileRepositoryText.enumSet(compatibility.transportRequirements()),
        FileRepositoryText.value(compatibility.minPayloadKg()),
        FileRepositoryText.value(compatibility.minVolumeCubicMeters()),
        FileRepositoryText.value(compatibility.minInternalLengthMeters()),
        FileRepositoryText.value(compatibility.minInternalWidthMeters()),
        FileRepositoryText.value(compatibility.minInternalHeightMeters()),
        compatibility.notes());
  }

  @Override
  public CargoUnit toDomain(CargoUnitPersistenceRecord persistenceModel) {
    Objects.requireNonNull(persistenceModel, "persistenceModel must not be null");

    try {
      return new CargoUnit(
          new CargoId(UUID.fromString(persistenceModel.id())),
          CargoCode.of(persistenceModel.code()),
          persistenceModel.name(),
          persistenceModel.description(),
          CargoType.valueOf(persistenceModel.type()),
          FileRepositoryText.parseEnumSet(persistenceModel.categories(), CargoCategory.class),
          dimensionsFrom(persistenceModel),
          weightsFrom(persistenceModel),
          packagingFrom(persistenceModel),
          temperatureFrom(persistenceModel),
          hazardFrom(persistenceModel),
          regulatoryFrom(persistenceModel),
          propertiesFrom(persistenceModel),
          compatibilityFrom(persistenceModel),
          CargoStatus.valueOf(persistenceModel.status()),
          persistenceModel.notes());
    } catch (IllegalArgumentException exception) {
      throw new MappingException("Unable to rebuild CargoUnit from persistence record.", exception);
    }
  }

  private static CargoDimensions dimensionsFrom(CargoUnitPersistenceRecord record) {
    return new CargoDimensions(
        FileRepositoryText.decimal(record.lengthMeters()),
        FileRepositoryText.decimal(record.widthMeters()),
        FileRepositoryText.decimal(record.heightMeters()),
        FileRepositoryText.decimal(record.volumeCubicMeters()));
  }

  private static CargoWeights weightsFrom(CargoUnitPersistenceRecord record) {
    return new CargoWeights(
        FileRepositoryText.decimal(record.grossWeightKg()),
        FileRepositoryText.decimal(record.netWeightKg()),
        FileRepositoryText.decimal(record.tareWeightKg()));
  }

  private static CargoPackaging packagingFrom(CargoUnitPersistenceRecord record) {
    return new CargoPackaging(
        CargoPackagingType.valueOf(record.packagingType()),
        FileRepositoryText.integer(record.packagingUnits()),
        FileRepositoryText.integer(record.packagingPallets()),
        record.containerType(),
        Boolean.parseBoolean(record.stackable()),
        record.packagingNotes());
  }

  private static CargoTemperature temperatureFrom(CargoUnitPersistenceRecord record) {
    return new CargoTemperature(
        FileRepositoryText.decimal(record.temperatureMin()),
        FileRepositoryText.decimal(record.temperatureMax()),
        Boolean.parseBoolean(record.temperatureControlled()),
        record.temperatureNotes());
  }

  private static CargoHazard hazardFrom(CargoUnitPersistenceRecord record) {
    return new CargoHazard(
        record.adrClass(),
        record.unNumber(),
        record.packingGroup(),
        record.specialProvisions(),
        record.hazardNotes());
  }

  private static CargoRegulatory regulatoryFrom(CargoUnitPersistenceRecord record) {
    return new CargoRegulatory(
        Boolean.parseBoolean(record.adrRequired()),
        Boolean.parseBoolean(record.atpRequired()),
        Boolean.parseBoolean(record.foodGradeRequired()),
        Boolean.parseBoolean(record.pharmaGradeRequired()),
        Boolean.parseBoolean(record.wasteAuthorizationRequired()),
        Boolean.parseBoolean(record.livestockAuthorizationRequired()),
        record.regulatoryNotes());
  }

  private static CargoProperties propertiesFrom(CargoUnitPersistenceRecord record) {
    return new CargoProperties(
        Boolean.parseBoolean(record.fragile()),
        Boolean.parseBoolean(record.perishable()),
        Boolean.parseBoolean(record.dangerous()),
        Boolean.parseBoolean(record.highValue()),
        Boolean.parseBoolean(record.requiresSeparation()),
        record.propertiesNotes());
  }

  private static CargoCompatibilityRequirement compatibilityFrom(
      CargoUnitPersistenceRecord record) {
    return new CargoCompatibilityRequirement(
        FileRepositoryText.parseEnumSet(
            record.transportRequirements(), CargoTransportRequirement.class),
        FileRepositoryText.decimal(record.minPayloadKg()),
        FileRepositoryText.decimal(record.minVolumeCubicMeters()),
        FileRepositoryText.decimal(record.minInternalLengthMeters()),
        FileRepositoryText.decimal(record.minInternalWidthMeters()),
        FileRepositoryText.decimal(record.minInternalHeightMeters()),
        record.compatibilityNotes());
  }
}
