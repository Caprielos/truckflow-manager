package it.gabriele.truckflow.domain.securitypolicy;

/** Livello accesso dati. */
public enum DataAccessLevel {
  NONE,
  READ_OWN,
  READ_DEPARTMENT,
  READ_COMPANY,
  WRITE_OWN,
  WRITE_DEPARTMENT,
  WRITE_COMPANY,
  APPROVE,
  ADMIN
}
