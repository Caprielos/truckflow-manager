package it.gabriele.truckflow.domain.qualifications;

public enum QualificationCategory {
  DRIVING_LICENSES(
      "DRIVING_LICENSES",
      "Driving Licenses",
      "Driving license categories required to operate road vehicles."),

  CQC(
      "CQC",
      "Driver Qualification Cards",
      "Professional driver qualification cards for goods and passenger transport."),

  ADR("ADR", "Dangerous Goods", "Qualifications required for dangerous goods transport."),

  FOOD_PHARMACEUTICALS(
      "FOOD_PHARMACEUTICALS",
      "Food & Pharmaceuticals",
      "Certifications for food, refrigerated and pharmaceutical transport."),

  ANIMALS(
      "ANIMALS",
      "Animal Transport",
      "Qualifications for live animal transport and animal welfare requirements."),

  WASTE("WASTE", "Waste Transport", "Authorizations related to waste transport operations."),

  MACHINE_OPERATORS(
      "MACHINE_OPERATORS",
      "Machine Operators",
      "Qualifications for operating industrial, warehouse and construction equipment."),

  SAFETY(
      "SAFETY",
      "Safety",
      "Training qualifications related to workplace and road safety procedures."),

  PORTS_AND_AIRPORTS(
      "PORTS_AND_AIRPORTS",
      "Ports & Airports",
      "Qualifications and permits for port, terminal and airport operations."),

  COMPANY_LOGISTICS(
      "COMPANY_LOGISTICS",
      "Company Logistics",
      "Internal company training for logistics, warehouse and special transport operations.");

  private final String code;
  private final String displayName;
  private final String description;

  QualificationCategory(String code, String displayName, String description) {
    this.code = code;
    this.displayName = displayName;
    this.description = description;
  }

  public String code() {
    return code;
  }

  public String displayName() {
    return displayName;
  }

  public String description() {
    return description;
  }
}
