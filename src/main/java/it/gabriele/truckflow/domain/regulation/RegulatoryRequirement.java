package it.gabriele.truckflow.domain.regulation;

import java.util.Objects;

/** Singolo obbligo normativo o operativo attivo in un profilo paese. */
public record RegulatoryRequirement(
    RegulatoryRequirementCode code,
    RegulatoryArea area,
    RegulatoryLevel level,
    boolean blocksOperation,
    String description) {

  public RegulatoryRequirement {
    Objects.requireNonNull(code, "Il codice requisito normativo è obbligatorio.");
    Objects.requireNonNull(area, "L'area normativa è obbligatoria.");
    Objects.requireNonNull(level, "Il livello normativo è obbligatorio.");
    description = normalize(description, "La descrizione requisito normativo è obbligatoria.");
  }

  public static RegulatoryRequirement blocking(
      RegulatoryRequirementCode code,
      RegulatoryArea area,
      RegulatoryLevel level,
      String description) {
    return new RegulatoryRequirement(code, area, level, true, description);
  }

  public static RegulatoryRequirement advisory(
      RegulatoryRequirementCode code,
      RegulatoryArea area,
      RegulatoryLevel level,
      String description) {
    return new RegulatoryRequirement(code, area, level, false, description);
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim();
  }
}
