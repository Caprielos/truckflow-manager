# Domain `contract` spiegato

Contratti cliente, listini, tariffe e regole prezzo commerciali.

## Classi principali

### `ChargeUnit`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Metodi pubblici principali:

- `isPercentageBased()`

### `ContractRateCard`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_CODE_LENGTH`
- `MAX_NAME_LENGTH`
- `rateCardCode`
- `name`
- `rules`
- `notes`

Metodi pubblici principali:

- `of()`
- `getRateCardCode()`
- `getName()`
- `getRules()`
- `getNotes()`
- `findRule()`
- `containsRule()`

### `CustomerContract`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_CODE_LENGTH`
- `contractCode`
- `customerCode`
- `validity`
- `rateCard`
- `active`
- `notes`

Metodi pubblici principali:

- `active()`
- `inactive()`
- `getContractCode()`
- `getCustomerCode()`
- `getValidity()`
- `getRateCard()`
- `isActive()`
- `getNotes()`
- `isValidOn()`
- `hasTariff()`
- `equals()`
- `hashCode()`

### `CustomerContractRules`

Tipo: `class`.

Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

Metodi pubblici principali:

- `canPriceShipmentOn()`
- `requiresManualPricingForAdr()`
- `requiresManualPricingForTemperatureControlledTransport()`

### `TariffRule`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_CODE_LENGTH`
- `MAX_DESCRIPTION_LENGTH`
- `ruleCode`
- `type`
- `description`
- `unit`
- `amount`
- `percentage`
- `mandatory`
- `notes`

Metodi pubblici principali:

- `amount()`
- `percentage()`
- `getRuleCode()`
- `getType()`
- `getDescription()`
- `getUnit()`
- `getAmount()`
- `getPercentage()`
- `isMandatory()`
- `getNotes()`
- `appliesTo()`
- `equals()`

### `TariffRuleType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `operationalCircumstance`

Metodi pubblici principali:

- `isOperationalCircumstance()`
