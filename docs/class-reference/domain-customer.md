# Domain `customer` spiegato

Clienti, account cliente e contatti operativi/amministrativi.

## Classi principali

### `Customer`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_CODE_LENGTH`
- `MAX_LEGAL_NAME_LENGTH`
- `code`
- `legalName`
- `type`
- `status`
- `primaryLocation`
- `notes`

Metodi pubblici principali:

- `active()`
- `inactive()`
- `suspended()`
- `getCode()`
- `getLegalName()`
- `getType()`
- `getStatus()`
- `getPrimaryLocation()`
- `getNotes()`
- `isActive()`
- `isSuspended()`
- `isBusinessCustomer()`

### `CustomerAccount`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `customer`
- `contacts`

Metodi pubblici principali:

- `of()`
- `getCustomer()`
- `getContacts()`
- `getContactCount()`
- `getPrimaryContact()`
- `getContactsByRole()`
- `hasContactRole()`
- `hasBillingContact()`
- `canRequestTransportOrder()`
- `isSuspended()`
- `getCustomerCode()`
- `formatSingleLine()`

### `CustomerContact`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_NAME_LENGTH`
- `MAX_EMAIL_LENGTH`
- `MAX_PHONE_LENGTH`
- `fullName`
- `role`
- `email`
- `phoneNumber`
- `primaryContact`
- `notes`

Metodi pubblici principali:

- `primary()`
- `secondary()`
- `getFullName()`
- `getRole()`
- `getEmail()`
- `getPhoneNumber()`
- `isPrimaryContact()`
- `getNotes()`
- `hasRole()`
- `hasNotes()`
- `formatSingleLine()`
- `equals()`

### `CustomerContactRole`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

### `CustomerStatus`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

### `CustomerType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `businessCustomer`

Metodi pubblici principali:

- `isBusinessCustomer()`
