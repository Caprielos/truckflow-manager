# Package `domain.contract`

Package domain del progetto TruckFlow.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| ChargeUnit | enum | Enum: insieme chiuso di valori ammessi dal dominio. | isPercentageBased |
| ContractRateCard | class | Classe del package domain.contract; rappresenta un concetto del modello TruckFlow. | of, getRateCardCode, getName, getRules, getNotes, findRule, containsRule |
| CustomerContract | class | Classe del package domain.contract; rappresenta un concetto del modello TruckFlow. | active, inactive, getContractCode, getCustomerCode, getValidity, getRateCard, isActive, getNotes, isValidOn, hasTariff |
| CustomerContractRules | class | Classe di regole per validare e calcolare comportamenti nel package domain.contract. | canPriceShipmentOn, requiresManualPricingForAdr, requiresManualPricingForTemperatureControlledTransport |
| TariffRule | class | Classe del package domain.contract; rappresenta un concetto del modello TruckFlow. | amount, percentage, getRuleCode, getType, getDescription, getUnit, getAmount, getPercentage, isMandatory, getNotes |
| TariffRuleType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | isOperationalCircumstance |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
