package it.gabriele.truckflow.domain.waste;

import java.time.LocalDate;
import java.util.Objects;

/** Regole per trasporto rifiuti, FIR, autorizzazioni e ADR rifiuti. */
public final class WasteTransportRules {

  private WasteTransportRules() {}

  public static boolean requiresAdrControls(WasteTransportDocument document) {
    Objects.requireNonNull(document, "Il documento rifiuti è obbligatorio.");
    return document.category() == WasteCategory.ADR_WASTE
        || document.category() == WasteCategory.DANGEROUS
        || document.eerCode().hazardous();
  }

  public static boolean canDepart(
      WasteTransportDocument document,
      EnvironmentalManagerRegistration registration,
      String vehicleCode,
      LocalDate date) {
    Objects.requireNonNull(registration, "L'iscrizione ambientale è obbligatoria.");
    return document.isCompleteAtDeparture()
        && registration.authorizes(vehicleCode, document.category(), date);
  }

  public static boolean requiresDpi(WasteTransportDocument document) {
    Objects.requireNonNull(document, "Il documento rifiuti è obbligatorio.");
    return document.category() != WasteCategory.NON_DANGEROUS;
  }
}
