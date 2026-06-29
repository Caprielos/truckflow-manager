package it.gabriele.truckflow.domain.driver;

/** Rappresenta le abilitazioni ADR possedute dall'autista. */
public enum DriverAdrCertificateType {
  ADR_BASIC(false),
  ADR_TANK(true),
  ADR_CLASS_1_EXPLOSIVES(true),
  ADR_CLASS_7_RADIOACTIVE(true);

  private final boolean specialization;

  DriverAdrCertificateType(boolean specialization) {
    this.specialization = specialization;
  }

  public boolean isSpecialization() {
    return specialization;
  }
}
