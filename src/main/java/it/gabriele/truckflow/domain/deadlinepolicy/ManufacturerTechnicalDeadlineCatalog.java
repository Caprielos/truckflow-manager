package it.gabriele.truckflow.domain.deadlinepolicy;

import java.util.List;
import java.util.Locale;

/** Catalogo tecnico delle scadenze definite da costruttore e modello. */
public final class ManufacturerTechnicalDeadlineCatalog {

  private ManufacturerTechnicalDeadlineCatalog() {}

  public static List<DeadlinePolicyRule> rulesFor(String manufacturer, String modelFamily) {
    return ConfigurableTechnicalDeadlineRuleBook.rulesFor(
        normalize(manufacturer), normalize(modelFamily));
  }

  private static String normalize(String value) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException("Costruttore e modello sono obbligatori.");
    }
    return value.trim().toUpperCase(Locale.ROOT);
  }
}
