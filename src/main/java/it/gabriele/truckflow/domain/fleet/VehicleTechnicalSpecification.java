package it.gabriele.truckflow.domain.fleet;

import java.util.List;
import java.util.Objects;

/** Scheda tecnica aggregata del mezzo. */
public final class VehicleTechnicalSpecification {

  private final VehicleMassSpecification massSpecification;
  private final VehicleDimensionSpecification dimensionSpecification;
  private final VehicleAxleSpecification axleSpecification;
  private final VehicleCouplingSpecification couplingSpecification;
  private final VehicleBodyConfiguration bodyConfiguration;
  private final List<VehicleCertificate> certificates;

  private VehicleTechnicalSpecification(
      VehicleMassSpecification massSpecification,
      VehicleDimensionSpecification dimensionSpecification,
      VehicleAxleSpecification axleSpecification,
      VehicleCouplingSpecification couplingSpecification,
      VehicleBodyConfiguration bodyConfiguration,
      List<VehicleCertificate> certificates) {
    if (massSpecification == null) {
      throw new IllegalArgumentException("Le masse del veicolo sono obbligatorie.");
    }
    if (dimensionSpecification == null) {
      throw new IllegalArgumentException("Le dimensioni del veicolo sono obbligatorie.");
    }
    if (axleSpecification == null) {
      throw new IllegalArgumentException("Gli assi del veicolo sono obbligatori.");
    }
    if (couplingSpecification == null) {
      throw new IllegalArgumentException("Le specifiche di aggancio sono obbligatorie.");
    }
    if (bodyConfiguration == null) {
      throw new IllegalArgumentException("La configurazione allestimento è obbligatoria.");
    }
    if (certificates == null) {
      throw new IllegalArgumentException("I certificati veicolo sono obbligatori.");
    }
    if (certificates.stream().anyMatch(Objects::isNull)) {
      throw new IllegalArgumentException(
          "I certificati veicolo non possono contenere valori nulli.");
    }
    this.massSpecification = massSpecification;
    this.dimensionSpecification = dimensionSpecification;
    this.axleSpecification = axleSpecification;
    this.couplingSpecification = couplingSpecification;
    this.bodyConfiguration = bodyConfiguration;
    this.certificates = List.copyOf(certificates);
  }

  public static VehicleTechnicalSpecification of(
      VehicleMassSpecification massSpecification,
      VehicleDimensionSpecification dimensionSpecification,
      VehicleAxleSpecification axleSpecification,
      VehicleCouplingSpecification couplingSpecification,
      VehicleBodyConfiguration bodyConfiguration,
      List<VehicleCertificate> certificates) {
    return new VehicleTechnicalSpecification(
        massSpecification,
        dimensionSpecification,
        axleSpecification,
        couplingSpecification,
        bodyConfiguration,
        certificates);
  }

  public VehicleMassSpecification getMassSpecification() {
    return massSpecification;
  }

  public VehicleDimensionSpecification getDimensionSpecification() {
    return dimensionSpecification;
  }

  public VehicleAxleSpecification getAxleSpecification() {
    return axleSpecification;
  }

  public VehicleCouplingSpecification getCouplingSpecification() {
    return couplingSpecification;
  }

  public VehicleBodyConfiguration getBodyConfiguration() {
    return bodyConfiguration;
  }

  public List<VehicleCertificate> getCertificates() {
    return certificates;
  }

  public boolean hasValidCertificate(VehicleCertificateType type, java.time.LocalDate date) {
    if (type == null) {
      throw new IllegalArgumentException("Il tipo certificato è obbligatorio.");
    }
    if (date == null) {
      throw new IllegalArgumentException("La data di verifica è obbligatoria.");
    }
    return certificates.stream()
        .anyMatch(certificate -> certificate.getType() == type && certificate.isValidOn(date));
  }
}
