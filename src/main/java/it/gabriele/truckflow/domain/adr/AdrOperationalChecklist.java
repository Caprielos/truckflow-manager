package it.gabriele.truckflow.domain.adr;

/** Checklist operativa ADR prima della partenza. */
public record AdrOperationalChecklist(
    String checklistCode,
    String missionCode,
    boolean transportDocumentPresent,
    boolean writtenInstructionsPresent,
    boolean equipmentChecked,
    boolean orangePanelsDisplayed,
    boolean hazardLabelsChecked,
    boolean adrParkingPlanned,
    boolean routeRestrictionsChecked) {

  public AdrOperationalChecklist {
    checklistCode = normalize(checklistCode, "Il codice checklist ADR è obbligatorio.");
    missionCode = normalize(missionCode, "Il codice missione è obbligatorio.");
  }

  public boolean isComplete() {
    return transportDocumentPresent
        && writtenInstructionsPresent
        && equipmentChecked
        && orangePanelsDisplayed
        && hazardLabelsChecked
        && routeRestrictionsChecked;
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
