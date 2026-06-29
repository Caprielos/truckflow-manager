package it.gabriele.truckflow.domain.contract;

import java.time.LocalDate;

/** Regole di dominio sui contratti cliente e sui listini. */
public final class CustomerContractRules {

  private CustomerContractRules() {}

  public static boolean canPriceShipmentOn(CustomerContract contract, LocalDate date) {
    validateContract(contract);
    if (date == null) {
      throw new IllegalArgumentException("La data prezzo contratto è obbligatoria.");
    }
    return contract.isValidOn(date) && contract.hasTariff(TariffRuleType.BASE_TRANSPORT_FEE);
  }

  public static boolean requiresManualPricingForAdr(CustomerContract contract) {
    validateContract(contract);
    return !contract.hasTariff(TariffRuleType.ADR_SURCHARGE);
  }

  public static boolean requiresManualPricingForTemperatureControlledTransport(
      CustomerContract contract) {
    validateContract(contract);
    return !contract.hasTariff(TariffRuleType.REFRIGERATED_SURCHARGE)
        && !contract.hasTariff(TariffRuleType.PHARMACEUTICAL_SURCHARGE);
  }

  private static void validateContract(CustomerContract contract) {
    if (contract == null) {
      throw new IllegalArgumentException("Il contratto cliente è obbligatorio.");
    }
  }
}
