package it.gabriele.truckflow.domain.adr;

import it.gabriele.truckflow.domain.cargo.AdrClass;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

/** Regole ADR per idoneità mezzo, equipaggiamento, documenti e restrizioni percorso. */
public final class AdrRules {

  private static final Set<AdrEquipmentType> CORE_EQUIPMENT =
      EnumSet.of(
          AdrEquipmentType.FIRE_EXTINGUISHER,
          AdrEquipmentType.WHEEL_CHOCK,
          AdrEquipmentType.WARNING_VEST,
          AdrEquipmentType.PORTABLE_LIGHT,
          AdrEquipmentType.PROTECTIVE_GLOVES,
          AdrEquipmentType.EYE_PROTECTION,
          AdrEquipmentType.WRITTEN_INSTRUCTIONS,
          AdrEquipmentType.ORANGE_PLATES,
          AdrEquipmentType.HAZARD_LABELS);

  private AdrRules() {}

  public static boolean hasCoreEquipment(AdrComplianceProfile profile) {
    Objects.requireNonNull(profile, "Il profilo ADR è obbligatorio.");
    return profile.equipment().containsAll(CORE_EQUIPMENT) && profile.orangePlatesAvailable();
  }

  public static Set<AdrEquipmentType> missingCoreEquipment(AdrComplianceProfile profile) {
    Objects.requireNonNull(profile, "Il profilo ADR è obbligatorio.");
    EnumSet<AdrEquipmentType> missing = EnumSet.copyOf(CORE_EQUIPMENT);
    missing.removeAll(profile.equipment());
    if (!profile.orangePlatesAvailable()) {
      missing.add(AdrEquipmentType.ORANGE_PLATES);
    }
    return Set.copyOf(missing);
  }

  public static boolean canCarryAdrClass(AdrComplianceProfile profile, AdrClass adrClass) {
    Objects.requireNonNull(profile, "Il profilo ADR è obbligatorio.");
    return profile.vehicleAdrCertificateValid()
        && profile.supportsAdrClass(adrClass)
        && hasCoreEquipment(profile);
  }

  public static boolean canDepart(AdrComplianceProfile profile, AdrOperationalChecklist checklist) {
    Objects.requireNonNull(checklist, "La checklist ADR è obbligatoria.");
    return hasCoreEquipment(profile)
        && profile.vehicleAdrCertificateValid()
        && checklist.isComplete()
        && checklist.adrParkingPlanned();
  }

  public static boolean routeAllowsTunnelRestriction(
      AdrTunnelRestrictionCode routeMaximumAllowed, AdrTunnelRestrictionCode cargoRestriction) {
    Objects.requireNonNull(routeMaximumAllowed, "Il limite tunnel della rotta è obbligatorio.");
    return routeMaximumAllowed.allows(cargoRestriction);
  }
}
