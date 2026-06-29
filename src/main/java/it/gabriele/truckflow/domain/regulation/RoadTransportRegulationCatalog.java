package it.gabriele.truckflow.domain.regulation;

import java.util.Set;

/** Catalogo centrale per caricare il profilo normativo in base al paese scelto. */
public final class RoadTransportRegulationCatalog {

  private RoadTransportRegulationCatalog() {}

  public static Set<EuropeanCountry> selectableCountries() {
    return Set.of(EuropeanCountry.values());
  }

  public static Set<EuropeanCountry> fullyConfiguredCountries() {
    return Set.of(EuropeanCountry.ITALY);
  }

  public static CountryRegulatoryProfile forCountry(EuropeanCountry country) {
    if (country == null) {
      throw new IllegalArgumentException("Il paese da configurare è obbligatorio.");
    }
    if (country == EuropeanCountry.ITALY) {
      return italyProfile();
    }
    return new CountryRegulatoryProfile(country, false, Set.of(), Set.of());
  }

  private static CountryRegulatoryProfile italyProfile() {
    return new CountryRegulatoryProfile(
        EuropeanCountry.ITALY,
        true,
        Set.of(
            blocking(
                RegulatoryRequirementCode.CMR_CONSIGNMENT_NOTE,
                RegulatoryArea.CARRIER_LIABILITY,
                RegulatoryLevel.INTERNATIONAL,
                "Lettera CMR e riserve per responsabilità vettore internazionale."),
            blocking(
                RegulatoryRequirementCode.VEHICLE_REGISTRATION_DOCUMENT,
                RegulatoryArea.ROAD_TRANSPORT,
                RegulatoryLevel.NATIONAL,
                "Carta di circolazione e dati tecnici del veicolo."),
            blocking(
                RegulatoryRequirementCode.VEHICLE_INSURANCE_CERTIFICATE,
                RegulatoryArea.ROAD_TRANSPORT,
                RegulatoryLevel.NATIONAL,
                "Copertura assicurativa del mezzo."),
            blocking(
                RegulatoryRequirementCode.ROAD_WORTHINESS_INSPECTION,
                RegulatoryArea.ROAD_TRANSPORT,
                RegulatoryLevel.NATIONAL,
                "Revisione tecnica e idoneità alla circolazione."),
            blocking(
                RegulatoryRequirementCode.ADR_TRANSPORT_DOCUMENT,
                RegulatoryArea.ADR,
                RegulatoryLevel.INTERNATIONAL,
                "Documento di trasporto ADR per merci pericolose."),
            blocking(
                RegulatoryRequirementCode.ADR_WRITTEN_INSTRUCTIONS,
                RegulatoryArea.ADR,
                RegulatoryLevel.INTERNATIONAL,
                "Istruzioni scritte ADR disponibili a bordo."),
            blocking(
                RegulatoryRequirementCode.ADR_EQUIPMENT_KIT,
                RegulatoryArea.ADR,
                RegulatoryLevel.INTERNATIONAL,
                "Dotazioni ADR obbligatorie in base al carico."),
            blocking(
                RegulatoryRequirementCode.ADR_ORANGE_PLATES,
                RegulatoryArea.ADR,
                RegulatoryLevel.INTERNATIONAL,
                "Pannelli arancioni ADR quando richiesti."),
            advisory(
                RegulatoryRequirementCode.ADR_TUNNEL_RESTRICTIONS,
                RegulatoryArea.ADR,
                RegulatoryLevel.INTERNATIONAL,
                "Controllo restrizioni gallerie e percorso ADR."),
            blocking(
                RegulatoryRequirementCode.ATP_CERTIFICATE,
                RegulatoryArea.ATP,
                RegulatoryLevel.INTERNATIONAL,
                "Certificato ATP valido per trasporto a temperatura controllata."),
            blocking(
                RegulatoryRequirementCode.ATP_TEMPERATURE_RECORDING,
                RegulatoryArea.ATP,
                RegulatoryLevel.INTERNATIONAL,
                "Registrazione temperatura tramite sonda o termografo."),
            advisory(
                RegulatoryRequirementCode.ATP_REFRIGERATION_MAINTENANCE,
                RegulatoryArea.ATP,
                RegulatoryLevel.INTERNATIONAL,
                "Manutenzione del gruppo frigo e controlli temperatura."),
            blocking(
                RegulatoryRequirementCode.HACCP_FOOD_SAFETY_PLAN,
                RegulatoryArea.FOOD_SAFETY,
                RegulatoryLevel.EUROPEAN_UNION,
                "Piano e controlli HACCP per trasporto alimentare."),
            blocking(
                RegulatoryRequirementCode.FOOD_SANITATION_RECORD,
                RegulatoryArea.FOOD_SAFETY,
                RegulatoryLevel.NATIONAL,
                "Registro pulizia e sanificazione per alimentare."),
            blocking(
                RegulatoryRequirementCode.VETERINARY_TRANSPORT_DOCUMENTS,
                RegulatoryArea.LIVESTOCK,
                RegulatoryLevel.EUROPEAN_UNION,
                "Documenti veterinari per trasporto animali."),
            blocking(
                RegulatoryRequirementCode.LIVESTOCK_VEHICLE_AUTHORIZATION,
                RegulatoryArea.LIVESTOCK,
                RegulatoryLevel.EUROPEAN_UNION,
                "Autorizzazione del mezzo per animali vivi."),
            blocking(
                RegulatoryRequirementCode.OVERSIZED_TRANSPORT_PERMIT,
                RegulatoryArea.OVERSIZED_TRANSPORT,
                RegulatoryLevel.NATIONAL,
                "Permesso per trasporto eccezionale."),
            advisory(
                RegulatoryRequirementCode.OVERSIZED_TECHNICAL_ESCORT,
                RegulatoryArea.OVERSIZED_TRANSPORT,
                RegulatoryLevel.NATIONAL,
                "Scorta tecnica quando richiesta dal permesso."),
            blocking(
                RegulatoryRequirementCode.TACHOGRAPH_DRIVING_TIME_CONTROLS,
                RegulatoryArea.TACHOGRAPH,
                RegulatoryLevel.EUROPEAN_UNION,
                "Controllo tempi guida, pause e riposi."),
            blocking(
                RegulatoryRequirementCode.DRIVER_CARD_DOWNLOAD,
                RegulatoryArea.TACHOGRAPH,
                RegulatoryLevel.EUROPEAN_UNION,
                "Scarico e archiviazione dati carta conducente."),
            blocking(
                RegulatoryRequirementCode.TACHOGRAPH_CALIBRATION_CERTIFICATE,
                RegulatoryArea.TACHOGRAPH,
                RegulatoryLevel.EUROPEAN_UNION,
                "Taratura tachigrafo valida."),
            blocking(
                RegulatoryRequirementCode.ITALIAN_WASTE_IDENTIFICATION_FORM,
                RegulatoryArea.WASTE,
                RegulatoryLevel.NATIONAL,
                "Formulario identificazione rifiuti per trasporto rifiuti in Italia."),
            blocking(
                RegulatoryRequirementCode.ITALIAN_ENVIRONMENTAL_MANAGER_REGISTRATION,
                RegulatoryArea.WASTE,
                RegulatoryLevel.NATIONAL,
                "Iscrizione Albo Gestori Ambientali per trasporto rifiuti in Italia."),
            advisory(
                RegulatoryRequirementCode.ITALIAN_WASTE_TRACEABILITY,
                RegulatoryArea.WASTE,
                RegulatoryLevel.NATIONAL,
                "Tracciabilità rifiuti e riconciliazione documentale italiana."),
            advisory(
                RegulatoryRequirementCode.ITALIAN_CUSTOMS_AIDA,
                RegulatoryArea.CUSTOMS,
                RegulatoryLevel.NATIONAL,
                "Integrazione doganale italiana AIDA quando configurata."),
            advisory(
                RegulatoryRequirementCode.ITALIAN_ELECTRONIC_INVOICING_SDI,
                RegulatoryArea.ELECTRONIC_INVOICING,
                RegulatoryLevel.NATIONAL,
                "Integrazione fatturazione elettronica italiana SDI."),
            advisory(
                RegulatoryRequirementCode.SEPA_BANKING_RECONCILIATION,
                RegulatoryArea.BANKING,
                RegulatoryLevel.EUROPEAN_UNION,
                "Riconciliazione bancaria SEPA."),
            blocking(
                RegulatoryRequirementCode.ROAD_INSPECTION_REPORT,
                RegulatoryArea.ROAD_INSPECTION,
                RegulatoryLevel.NATIONAL,
                "Verbale controllo su strada e gestione esito controllo."),
            blocking(
                RegulatoryRequirementCode.CARRIER_LIABILITY_RESERVATION,
                RegulatoryArea.CARRIER_LIABILITY,
                RegulatoryLevel.INTERNATIONAL,
                "Riserve e responsabilità vettore su danni, ritardi o perdite."),
            advisory(
                RegulatoryRequirementCode.PROOF_OF_DELIVERY_DIGITAL_EVIDENCE,
                RegulatoryArea.CARRIER_LIABILITY,
                RegulatoryLevel.NATIONAL,
                "Prove digitali di consegna: firma, foto, timestamp e geolocalizzazione.")),
        Set.of(
            RegulatoryIntegrationSystem.CUSTOMS_SYSTEM,
            RegulatoryIntegrationSystem.ELECTRONIC_INVOICING_SYSTEM,
            RegulatoryIntegrationSystem.ENVIRONMENTAL_REGISTER,
            RegulatoryIntegrationSystem.DIGITAL_WASTE_TRACEABILITY,
            RegulatoryIntegrationSystem.SEPA_BANKING,
            RegulatoryIntegrationSystem.TACHOGRAPH_PROVIDER,
            RegulatoryIntegrationSystem.TELEMATICS_PROVIDER));
  }

  private static RegulatoryRequirement blocking(
      RegulatoryRequirementCode code,
      RegulatoryArea area,
      RegulatoryLevel level,
      String description) {
    return RegulatoryRequirement.blocking(code, area, level, description);
  }

  private static RegulatoryRequirement advisory(
      RegulatoryRequirementCode code,
      RegulatoryArea area,
      RegulatoryLevel level,
      String description) {
    return RegulatoryRequirement.advisory(code, area, level, description);
  }
}
