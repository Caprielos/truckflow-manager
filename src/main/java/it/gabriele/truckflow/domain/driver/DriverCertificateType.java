package it.gabriele.truckflow.domain.driver;

/**
 * Certificato reale dell'autista, con scadenza. Le enum già presenti restano utili per
 * compatibilità e regole semplici, questa enum serve per l'anagrafica professionale completa.
 */
public enum DriverCertificateType {
  LICENSE_B,
  LICENSE_C1,
  LICENSE_C,
  LICENSE_BE,
  LICENSE_C1E,
  LICENSE_CE,
  CQC_GOODS,
  ADR_BASIC,
  ADR_TANK,
  ADR_CLASS_1_EXPLOSIVES,
  ADR_CLASS_7_RADIOACTIVE,
  TRUCK_MOUNTED_CRANE,
  AERIAL_PLATFORM,
  FORKLIFT,
  EARTH_MOVING_MACHINES,
  LIVE_ANIMAL_TRANSPORT,
  TEMPERATURE_CONTROLLED_TRANSPORT,
  OVERSIZED_TRANSPORT,
  HIGH_VALUE_CARGO,
  INTERNATIONAL_TRANSPORT
}
