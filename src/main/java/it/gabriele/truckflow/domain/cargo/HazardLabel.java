package it.gabriele.truckflow.domain.cargo;

/** Rappresenta le etichette di pericolo ADR principali. */
public enum HazardLabel {
  LABEL_1_EXPLOSIVES("1"),
  LABEL_2_1_FLAMMABLE_GAS("2.1"),
  LABEL_2_2_NON_FLAMMABLE_GAS("2.2"),
  LABEL_2_3_TOXIC_GAS("2.3"),
  LABEL_3_FLAMMABLE_LIQUID("3"),
  LABEL_4_1_FLAMMABLE_SOLID("4.1"),
  LABEL_4_2_SPONTANEOUS_COMBUSTION("4.2"),
  LABEL_4_3_DANGEROUS_WHEN_WET("4.3"),
  LABEL_5_1_OXIDIZER("5.1"),
  LABEL_5_2_ORGANIC_PEROXIDE("5.2"),
  LABEL_6_1_TOXIC("6.1"),
  LABEL_6_2_INFECTIOUS("6.2"),
  LABEL_7_RADIOACTIVE("7"),
  LABEL_8_CORROSIVE("8"),
  LABEL_9_MISCELLANEOUS("9");

  private final String code;

  HazardLabel(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  public boolean isGasLabel() {
    return this == LABEL_2_1_FLAMMABLE_GAS
        || this == LABEL_2_2_NON_FLAMMABLE_GAS
        || this == LABEL_2_3_TOXIC_GAS;
  }

  public boolean isTankRelevantLabel() {
    return this == LABEL_2_1_FLAMMABLE_GAS
        || this == LABEL_2_2_NON_FLAMMABLE_GAS
        || this == LABEL_2_3_TOXIC_GAS
        || this == LABEL_3_FLAMMABLE_LIQUID
        || this == LABEL_8_CORROSIVE;
  }
}
