package it.gabriele.truckflow.domain.regulation;

import java.util.Objects;

/** Regole comuni per usare il profilo paese selezionato. */
public final class RegulatoryConfigurationRules {

  private RegulatoryConfigurationRules() {}

  public static boolean canOperateWithFullCompliance(TransportRegulatorySelection selection) {
    Objects.requireNonNull(selection, "La selezione normativa è obbligatoria.");
    return selection.isFullyConfigured();
  }

  public static boolean shouldBlockBecauseCountryIsNotConfigured(
      TransportRegulatorySelection selection) {
    Objects.requireNonNull(selection, "La selezione normativa è obbligatoria.");
    return !selection.isFullyConfigured();
  }

  public static boolean activeCountryRequires(
      TransportRegulatorySelection selection, RegulatoryRequirementCode code) {
    Objects.requireNonNull(selection, "La selezione normativa è obbligatoria.");
    Objects.requireNonNull(code, "Il codice requisito è obbligatorio.");
    return selection.requires(code);
  }
}
