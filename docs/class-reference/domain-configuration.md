# Domain `configuration` spiegato

Configurazioni di sistema, parametri modificabili e valori di configurazione.

## Classi principali

### `ConfigurationCategory`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `sensitive`

Metodi pubblici principali:

- `isSensitive()`

### `ConfigurationRules`

Tipo: `class`.

Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

Metodi pubblici principali:

- `canBeApplied()`
- `canOverride()`
- `isApplicableTo()`
- `isSensitiveConfiguration()`
- `requiresRestrictedAccess()`
- `isNumericConfiguration()`
- `isPricingConfiguration()`
- `isSecurityConfiguration()`

### `ConfigurationScope`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `requiresReference`

Metodi pubblici principali:

- `requiresReference()`
- `isGlobal()`

### `ConfigurationValue`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_TEXT_LENGTH`
- `type`
- `rawValue`
- `normalizedValue`
- `normalized`
- `percentage`
- `minutes`

Metodi pubblici principali:

- `ofText()`
- `ofBoolean()`
- `ofInteger()`
- `ofDecimal()`
- `ofPercentage()`
- `ofDurationMinutes()`
- `getType()`
- `getRawValue()`
- `asText()`
- `asBoolean()`
- `asInteger()`
- `asDecimal()`

### `ConfigurationValueType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Metodi pubblici principali:

- `isNumeric()`

### `SystemConfiguration`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_KEY_LENGTH`
- `MAX_SCOPE_REFERENCE_LENGTH`
- `MAX_DESCRIPTION_LENGTH`
- `configurationKey`
- `category`
- `scope`
- `scopeReference`
- `value`
- `description`
- `active`
- `notes`

Metodi pubblici principali:

- `activeGlobal()`
- `activeForScope()`
- `inactiveForScope()`
- `activate()`
- `deactivate()`
- `changeValue()`
- `getConfigurationKey()`
- `getCategory()`
- `getScope()`
- `getScopeReference()`
- `getValue()`
- `getDescription()`
