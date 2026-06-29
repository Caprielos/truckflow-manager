# `domain/customer`

Clienti, account cliente e contatti operativi/amministrativi.

## Come leggerlo

Questo package contiene concetti o regole del business. Non dovrebbe dipendere da database, web o infrastructure.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `Customer` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_CODE_LENGTH, MAX_LEGAL_NAME_LENGTH, code, legalName, type, status, primaryLocation, notes | active, inactive, suspended, getCode, getLegalName, getType, getStatus, getPrimaryLocation |
| `CustomerAccount` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | customer, contacts | of, getCustomer, getContacts, getContactCount, getPrimaryContact, getContactsByRole, hasContactRole, hasBillingContact |
| `CustomerContact` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_NAME_LENGTH, MAX_EMAIL_LENGTH, MAX_PHONE_LENGTH, fullName, role, email, phoneNumber, primaryContact | primary, secondary, getFullName, getRole, getEmail, getPhoneNumber, isPrimaryContact, getNotes |
| `CustomerContactRole` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | — |
| `CustomerStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | — |
| `CustomerType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | businessCustomer | isBusinessCustomer |
