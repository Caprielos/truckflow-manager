package it.gabriele.truckflow.domain.tachograph;

/** Tipologia violazione tempi guida/riposo. */
public enum DrivingTimeViolationType {
  DAILY_DRIVING_EXCEEDED,
  WEEKLY_DRIVING_EXCEEDED,
  FORTNIGHTLY_DRIVING_EXCEEDED,
  BREAK_MISSING,
  DAILY_REST_MISSING,
  WEEKLY_REST_MISSING,
  CARD_NOT_INSERTED,
  MANUAL_ENTRY_MISSING
}
