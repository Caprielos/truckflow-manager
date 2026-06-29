package it.gabriele.truckflow.domain.pod;

/** Stato prova di consegna digitale. */
public enum PodStatus {
  DRAFT,
  COLLECTED,
  WITH_RESERVATIONS,
  REJECTED,
  VALIDATED,
  DISPUTED,
  ARCHIVED
}
