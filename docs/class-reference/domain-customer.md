# Package `domain.customer`

Clienti, account cliente e contatti operativi/commerciali.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| Customer | class | Classe del package domain.customer; rappresenta un concetto del modello TruckFlow. | active, inactive, suspended, getCode, getLegalName, getType, getStatus, getPrimaryLocation, getNotes, isActive |
| CustomerAccount | class | Classe del package domain.customer; rappresenta un concetto del modello TruckFlow. | of, getCustomer, getContacts, getContactCount, getPrimaryContact, getContactsByRole, hasContactRole, hasBillingContact, canRequestTransportOrder, isSuspended |
| CustomerContact | class | Classe del package domain.customer; rappresenta un concetto del modello TruckFlow. | primary, secondary, getFullName, getRole, getEmail, getPhoneNumber, isPrimaryContact, getNotes, hasRole, hasNotes |
| CustomerContactRole | enum | Enum: insieme chiuso di valori ammessi dal dominio. | - |
| CustomerStatus | enum | Enum di stato del ciclo di vita. | - |
| CustomerType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | isBusinessCustomer |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
