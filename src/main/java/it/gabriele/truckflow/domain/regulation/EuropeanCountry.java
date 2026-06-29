package it.gabriele.truckflow.domain.regulation;

/** Paese europeo selezionabile per caricare il profilo normativo nazionale. */
public enum EuropeanCountry {
  ITALY("IT", true),
  FRANCE("FR", true),
  GERMANY("DE", true),
  AUSTRIA("AT", true),
  SWITZERLAND("CH", false),
  SPAIN("ES", true),
  PORTUGAL("PT", true),
  BELGIUM("BE", true),
  NETHERLANDS("NL", true),
  LUXEMBOURG("LU", true),
  DENMARK("DK", true),
  SWEDEN("SE", true),
  NORWAY("NO", false),
  FINLAND("FI", true),
  POLAND("PL", true),
  CZECHIA("CZ", true),
  SLOVAKIA("SK", true),
  SLOVENIA("SI", true),
  CROATIA("HR", true),
  HUNGARY("HU", true),
  ROMANIA("RO", true),
  BULGARIA("BG", true),
  GREECE("GR", true),
  IRELAND("IE", true);

  private final String isoCode;
  private final boolean europeanUnionMember;

  EuropeanCountry(String isoCode, boolean europeanUnionMember) {
    this.isoCode = isoCode;
    this.europeanUnionMember = europeanUnionMember;
  }

  public String getIsoCode() {
    return isoCode;
  }

  public boolean isEuropeanUnionMember() {
    return europeanUnionMember;
  }
}
