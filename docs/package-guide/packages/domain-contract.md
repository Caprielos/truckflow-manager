# `domain/contract`

Contratti cliente, listini, tariffe e regole prezzo commerciali.

## Come leggerlo

Questo package contiene concetti o regole del business. Non dovrebbe dipendere da database, web o infrastructure.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `ChargeUnit` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | isPercentageBased |
| `ContractRateCard` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_CODE_LENGTH, MAX_NAME_LENGTH, rateCardCode, name, rules, notes | of, getRateCardCode, getName, getRules, getNotes, findRule, containsRule |
| `CustomerContract` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_CODE_LENGTH, contractCode, customerCode, validity, rateCard, active, notes | active, inactive, getContractCode, getCustomerCode, getValidity, getRateCard, isActive, getNotes |
| `CustomerContractRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | — | canPriceShipmentOn, requiresManualPricingForAdr, requiresManualPricingForTemperatureControlledTransport |
| `TariffRule` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_CODE_LENGTH, MAX_DESCRIPTION_LENGTH, ruleCode, type, description, unit, amount, percentage | amount, percentage, getRuleCode, getType, getDescription, getUnit, getAmount, getPercentage |
| `TariffRuleType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | operationalCircumstance | isOperationalCircumstance |
