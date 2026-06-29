# `domain/configuration`

Configurazioni di sistema, parametri modificabili e valori di configurazione.

## Come leggerlo

Questo package contiene concetti o regole del business. Non dovrebbe dipendere da database, web o infrastructure.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `ConfigurationCategory` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | sensitive | isSensitive |
| `ConfigurationRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | — | canBeApplied, canOverride, isApplicableTo, isSensitiveConfiguration, requiresRestrictedAccess, isNumericConfiguration, isPricingConfiguration, isSecurityConfiguration |
| `ConfigurationScope` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | requiresReference | requiresReference, isGlobal |
| `ConfigurationValue` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_TEXT_LENGTH, type, rawValue, normalizedValue, normalized, percentage, minutes | ofText, ofBoolean, ofInteger, ofDecimal, ofPercentage, ofDurationMinutes, getType, getRawValue |
| `ConfigurationValueType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | isNumeric |
| `SystemConfiguration` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_KEY_LENGTH, MAX_SCOPE_REFERENCE_LENGTH, MAX_DESCRIPTION_LENGTH, configurationKey, category, scope, scopeReference, value | activeGlobal, activeForScope, inactiveForScope, activate, deactivate, changeValue, getConfigurationKey, getCategory |
