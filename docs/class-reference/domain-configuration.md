# Package `domain.configuration`

Configurazioni di sistema e valori parametrizzabili.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| ConfigurationCategory | enum | Categoria funzionale usata per distinguere casi operativi o contabili. | isSensitive |
| ConfigurationRules | class | Classe di regole per validare e calcolare comportamenti nel package domain.configuration. | canBeApplied, canOverride, isApplicableTo, isSensitiveConfiguration, requiresRestrictedAccess, isNumericConfiguration, isPricingConfiguration, isSecurityConfiguration |
| ConfigurationScope | enum | Enum: insieme chiuso di valori ammessi dal dominio. | requiresReference, isGlobal |
| ConfigurationValue | class | Classe del package domain.configuration; rappresenta un concetto del modello TruckFlow. | ofText, ofBoolean, ofInteger, ofDecimal, ofPercentage, ofDurationMinutes, getType, getRawValue, asText, asBoolean |
| ConfigurationValueType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | isNumeric |
| SystemConfiguration | class | Classe del package domain.configuration; rappresenta un concetto del modello TruckFlow. | activeGlobal, activeForScope, inactiveForScope, activate, deactivate, changeValue, getConfigurationKey, getCategory, getScope, getScopeReference |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
