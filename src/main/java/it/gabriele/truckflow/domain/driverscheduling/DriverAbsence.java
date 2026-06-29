package it.gabriele.truckflow.domain.driverscheduling;

import java.time.LocalDate;

/** Assenza pianificata o registrata di un autista. */
public record DriverAbsence(
    String absenceCode,
    String driverCode,
    DriverAbsenceType absenceType,
    LocalDate fromDate,
    LocalDate toDate,
    boolean approved) {

  public DriverAbsence {
    absenceCode = normalize(absenceCode, "Il codice assenza è obbligatorio.");
    driverCode = normalize(driverCode, "Il codice autista è obbligatorio.");
    if (absenceType == null) {
      throw new IllegalArgumentException("Il tipo assenza è obbligatorio.");
    }
    if (fromDate == null || toDate == null || toDate.isBefore(fromDate)) {
      throw new IllegalArgumentException("Il periodo assenza non è valido.");
    }
  }

  public boolean covers(LocalDate date) {
    return date != null && !date.isBefore(fromDate) && !date.isAfter(toDate);
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
