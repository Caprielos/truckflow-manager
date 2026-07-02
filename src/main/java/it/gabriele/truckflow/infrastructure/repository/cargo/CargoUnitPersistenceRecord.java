package it.gabriele.truckflow.infrastructure.repository.cargo;

import java.util.Objects;

/** File-oriented persistence representation for cargo unit repository expansion. */
public record CargoUnitPersistenceRecord(
    String id,
    String code,
    String name,
    String description,
    String type,
    String categories,
    String status,
    String notes,
    String lengthMeters,
    String widthMeters,
    String heightMeters,
    String volumeCubicMeters,
    String grossWeightKg,
    String netWeightKg,
    String tareWeightKg,
    String packagingType,
    String packagingUnits,
    String packagingPallets,
    String containerType,
    String stackable,
    String packagingNotes,
    String temperatureMin,
    String temperatureMax,
    String temperatureControlled,
    String temperatureNotes,
    String adrClass,
    String unNumber,
    String packingGroup,
    String specialProvisions,
    String hazardNotes,
    String adrRequired,
    String atpRequired,
    String foodGradeRequired,
    String pharmaGradeRequired,
    String wasteAuthorizationRequired,
    String livestockAuthorizationRequired,
    String regulatoryNotes,
    String fragile,
    String perishable,
    String dangerous,
    String highValue,
    String requiresSeparation,
    String propertiesNotes,
    String transportRequirements,
    String minPayloadKg,
    String minVolumeCubicMeters,
    String minInternalLengthMeters,
    String minInternalWidthMeters,
    String minInternalHeightMeters,
    String compatibilityNotes) {

  /** Creates a normalized persistence record. */
  public CargoUnitPersistenceRecord {
    id = requireText(id, "id");
    code = requireText(code, "code");
    name = requireText(name, "name");
    description = normalize(description);
    type = requireText(type, "type");
    categories = requireText(categories, "categories");
    status = requireText(status, "status");
    notes = normalize(notes);
    lengthMeters = normalize(lengthMeters);
    widthMeters = normalize(widthMeters);
    heightMeters = normalize(heightMeters);
    volumeCubicMeters = normalize(volumeCubicMeters);
    grossWeightKg = normalize(grossWeightKg);
    netWeightKg = normalize(netWeightKg);
    tareWeightKg = normalize(tareWeightKg);
    packagingType = requireText(packagingType, "packagingType");
    packagingUnits = normalize(packagingUnits);
    packagingPallets = normalize(packagingPallets);
    containerType = normalize(containerType);
    stackable = normalize(stackable);
    packagingNotes = normalize(packagingNotes);
    temperatureMin = normalize(temperatureMin);
    temperatureMax = normalize(temperatureMax);
    temperatureControlled = normalize(temperatureControlled);
    temperatureNotes = normalize(temperatureNotes);
    adrClass = normalize(adrClass);
    unNumber = normalize(unNumber);
    packingGroup = normalize(packingGroup);
    specialProvisions = normalize(specialProvisions);
    hazardNotes = normalize(hazardNotes);
    adrRequired = normalize(adrRequired);
    atpRequired = normalize(atpRequired);
    foodGradeRequired = normalize(foodGradeRequired);
    pharmaGradeRequired = normalize(pharmaGradeRequired);
    wasteAuthorizationRequired = normalize(wasteAuthorizationRequired);
    livestockAuthorizationRequired = normalize(livestockAuthorizationRequired);
    regulatoryNotes = normalize(regulatoryNotes);
    fragile = normalize(fragile);
    perishable = normalize(perishable);
    dangerous = normalize(dangerous);
    highValue = normalize(highValue);
    requiresSeparation = normalize(requiresSeparation);
    propertiesNotes = normalize(propertiesNotes);
    transportRequirements = normalize(transportRequirements);
    minPayloadKg = normalize(minPayloadKg);
    minVolumeCubicMeters = normalize(minVolumeCubicMeters);
    minInternalLengthMeters = normalize(minInternalLengthMeters);
    minInternalWidthMeters = normalize(minInternalWidthMeters);
    minInternalHeightMeters = normalize(minInternalHeightMeters);
    compatibilityNotes = normalize(compatibilityNotes);
  }

  private static String requireText(String value, String fieldName) {
    String normalized = normalize(value);
    if (normalized.isBlank()) {
      throw new IllegalArgumentException(fieldName + " must not be blank");
    }
    return normalized;
  }

  private static String normalize(String value) {
    return Objects.toString(value, "").strip();
  }
}
