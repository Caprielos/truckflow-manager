package it.gabriele.truckflow.domain.company;

import it.gabriele.truckflow.domain.shared.Notes;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/** Azienda di trasporto o azienda in conto proprio. */
public final class TransportCompany {

  private static final int MAX_CODE_LENGTH = 50;
  private static final int MAX_NAME_LENGTH = 200;

  private final String companyCode;
  private final String businessName;
  private final String vatNumber;
  private final List<CompanyLicense> licenses;
  private final Notes notes;

  private TransportCompany(
      String companyCode,
      String businessName,
      String vatNumber,
      List<CompanyLicense> licenses,
      Notes notes) {
    this.companyCode = normalizeCode(companyCode, "Il codice azienda è obbligatorio.");
    this.businessName = normalizeName(businessName);
    this.vatNumber = vatNumber == null ? "" : vatNumber.trim().toUpperCase();
    if (licenses == null) {
      throw new IllegalArgumentException("Le licenze aziendali sono obbligatorie.");
    }
    if (licenses.stream().anyMatch(Objects::isNull)) {
      throw new IllegalArgumentException(
          "Le licenze aziendali non possono contenere valori nulli.");
    }
    if (notes == null) {
      throw new IllegalArgumentException("Le note azienda sono obbligatorie.");
    }
    this.licenses = List.copyOf(licenses);
    this.notes = notes;
  }

  public static TransportCompany of(
      String companyCode,
      String businessName,
      String vatNumber,
      List<CompanyLicense> licenses,
      Notes notes) {
    return new TransportCompany(companyCode, businessName, vatNumber, licenses, notes);
  }

  private static String normalizeCode(String value, String nullMessage) {
    if (value == null) {
      throw new IllegalArgumentException(nullMessage);
    }
    String normalized = value.trim().toUpperCase();
    if (normalized.isEmpty()) {
      throw new IllegalArgumentException("Il codice azienda non può essere vuoto.");
    }
    if (normalized.length() > MAX_CODE_LENGTH) {
      throw new IllegalArgumentException(
          "Il codice azienda non può superare " + MAX_CODE_LENGTH + " caratteri.");
    }
    if (!normalized.matches("[A-Z0-9_-]+")) {
      throw new IllegalArgumentException(
          "Il codice azienda può contenere solo lettere, numeri, trattini e underscore.");
    }
    return normalized;
  }

  private static String normalizeName(String value) {
    if (value == null) {
      throw new IllegalArgumentException("La ragione sociale è obbligatoria.");
    }
    String normalized = value.trim();
    if (normalized.isEmpty()) {
      throw new IllegalArgumentException("La ragione sociale non può essere vuota.");
    }
    if (normalized.length() > MAX_NAME_LENGTH) {
      throw new IllegalArgumentException(
          "La ragione sociale non può superare " + MAX_NAME_LENGTH + " caratteri.");
    }
    return normalized;
  }

  public String getCompanyCode() {
    return companyCode;
  }

  public String getBusinessName() {
    return businessName;
  }

  public String getVatNumber() {
    return vatNumber;
  }

  public List<CompanyLicense> getLicenses() {
    return licenses;
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean hasLicense(CompanyLicenseType type) {
    if (type == null) {
      throw new IllegalArgumentException("Il tipo licenza è obbligatorio.");
    }
    return licenses.stream().anyMatch(license -> license.getType() == type);
  }

  public boolean hasValidLicense(CompanyLicenseType type, LocalDate date) {
    if (type == null) {
      throw new IllegalArgumentException("Il tipo licenza è obbligatorio.");
    }
    if (date == null) {
      throw new IllegalArgumentException("La data verifica licenza è obbligatoria.");
    }
    return licenses.stream()
        .anyMatch(license -> license.getType() == type && license.isValidOn(date));
  }

  public boolean hasAnyValidEnvironmentalLicense(LocalDate date) {
    return hasValidLicense(CompanyLicenseType.ENVIRONMENTAL_MANAGERS_REGISTER_CATEGORY_2_BIS, date)
        || hasValidLicense(CompanyLicenseType.ENVIRONMENTAL_MANAGERS_REGISTER_CATEGORY_4, date)
        || hasValidLicense(CompanyLicenseType.ENVIRONMENTAL_MANAGERS_REGISTER_CATEGORY_5, date);
  }
}
