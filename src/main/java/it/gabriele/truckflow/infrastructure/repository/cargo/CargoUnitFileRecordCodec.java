package it.gabriele.truckflow.infrastructure.repository.cargo;

import it.gabriele.truckflow.infrastructure.repository.file.FileRecordCodec;
import java.util.List;

/** Encodes cargo unit persistence records to the Punto 7F file format. */
final class CargoUnitFileRecordCodec implements FileRecordCodec<CargoUnitPersistenceRecord> {

  private static final int FIELD_COUNT = 50;

  @Override
  public int fieldCount() {
    return FIELD_COUNT;
  }

  @Override
  public List<String> encode(CargoUnitPersistenceRecord record) {
    return List.of(
        record.id(),
        record.code(),
        record.name(),
        record.description(),
        record.type(),
        record.categories(),
        record.status(),
        record.notes(),
        record.lengthMeters(),
        record.widthMeters(),
        record.heightMeters(),
        record.volumeCubicMeters(),
        record.grossWeightKg(),
        record.netWeightKg(),
        record.tareWeightKg(),
        record.packagingType(),
        record.packagingUnits(),
        record.packagingPallets(),
        record.containerType(),
        record.stackable(),
        record.packagingNotes(),
        record.temperatureMin(),
        record.temperatureMax(),
        record.temperatureControlled(),
        record.temperatureNotes(),
        record.adrClass(),
        record.unNumber(),
        record.packingGroup(),
        record.specialProvisions(),
        record.hazardNotes(),
        record.adrRequired(),
        record.atpRequired(),
        record.foodGradeRequired(),
        record.pharmaGradeRequired(),
        record.wasteAuthorizationRequired(),
        record.livestockAuthorizationRequired(),
        record.regulatoryNotes(),
        record.fragile(),
        record.perishable(),
        record.dangerous(),
        record.highValue(),
        record.requiresSeparation(),
        record.propertiesNotes(),
        record.transportRequirements(),
        record.minPayloadKg(),
        record.minVolumeCubicMeters(),
        record.minInternalLengthMeters(),
        record.minInternalWidthMeters(),
        record.minInternalHeightMeters(),
        record.compatibilityNotes());
  }

  @Override
  public CargoUnitPersistenceRecord decode(List<String> fields) {
    return new CargoUnitPersistenceRecord(
        fields.get(0),
        fields.get(1),
        fields.get(2),
        fields.get(3),
        fields.get(4),
        fields.get(5),
        fields.get(6),
        fields.get(7),
        fields.get(8),
        fields.get(9),
        fields.get(10),
        fields.get(11),
        fields.get(12),
        fields.get(13),
        fields.get(14),
        fields.get(15),
        fields.get(16),
        fields.get(17),
        fields.get(18),
        fields.get(19),
        fields.get(20),
        fields.get(21),
        fields.get(22),
        fields.get(23),
        fields.get(24),
        fields.get(25),
        fields.get(26),
        fields.get(27),
        fields.get(28),
        fields.get(29),
        fields.get(30),
        fields.get(31),
        fields.get(32),
        fields.get(33),
        fields.get(34),
        fields.get(35),
        fields.get(36),
        fields.get(37),
        fields.get(38),
        fields.get(39),
        fields.get(40),
        fields.get(41),
        fields.get(42),
        fields.get(43),
        fields.get(44),
        fields.get(45),
        fields.get(46),
        fields.get(47),
        fields.get(48),
        fields.get(49));
  }
}
