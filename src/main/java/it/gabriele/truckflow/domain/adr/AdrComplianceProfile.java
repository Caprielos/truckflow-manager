package it.gabriele.truckflow.domain.adr;

import it.gabriele.truckflow.domain.cargo.AdrClass;
import java.util.Objects;
import java.util.Set;

/** Profilo ADR del mezzo: classi trasportabili, certificati e dotazioni obbligatorie. */
public record AdrComplianceProfile(
    String profileCode,
    String vehicleCode,
    Set<AdrClass> authorizedClasses,
    Set<AdrEquipmentType> equipment,
    AdrTunnelRestrictionCode maximumTunnelRestrictionCode,
    boolean vehicleAdrCertificateValid,
    boolean orangePlatesAvailable,
    boolean driverAdrCertificateRequired) {

  public AdrComplianceProfile {
    profileCode = normalize(profileCode, "Il codice profilo ADR è obbligatorio.");
    vehicleCode = normalize(vehicleCode, "Il codice veicolo è obbligatorio.");
    authorizedClasses =
        copyRequired(authorizedClasses, "Le classi ADR autorizzate sono obbligatorie.");
    equipment = copyRequired(equipment, "L'equipaggiamento ADR è obbligatorio.");
    Objects.requireNonNull(
        maximumTunnelRestrictionCode, "Il codice massimo restrizione tunnel è obbligatorio.");
  }

  public boolean supportsAdrClass(AdrClass adrClass) {
    if (adrClass == null) {
      throw new IllegalArgumentException("La classe ADR è obbligatoria.");
    }
    return authorizedClasses.contains(adrClass);
  }

  public boolean hasEquipment(AdrEquipmentType equipmentType) {
    if (equipmentType == null) {
      throw new IllegalArgumentException("Il tipo equipaggiamento ADR è obbligatorio.");
    }
    return equipment.contains(equipmentType);
  }

  private static <T> Set<T> copyRequired(Set<T> values, String message) {
    if (values == null || values.isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return Set.copyOf(values);
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
