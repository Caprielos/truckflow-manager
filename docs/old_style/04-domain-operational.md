# Archivio storico — 04-domain-operational

> Questo documento fa parte dell'archivio storico `docs/old_style/`.
> Mantiene il percorso step-by-step del progetto, ma la documentazione principale aggiornata si trova in:
> `docs/simple/`, `docs/professional/` e `docs/digital/`.

---

# 4. Package `domain.operational`

## 4.1 Scopo del package

Il package `domain.operational` rappresenta le persone operative reali dell’azienda.

Contiene:

- autisti;
- meccanici;
- operatori di magazzino;
- dispatcher;
- manager.

Queste figure non sono account applicativi. Sono profili operativi collegati eventualmente a un account tramite `UserId`.

La distinzione è:

- `domain.users.User` = account che accede all’app;
- `domain.operational.*` = figura reale nel contesto aziendale.

## 4.2 Perché il dominio operativo è separato dagli utenti

Un utente può accedere all’app, ma non sempre coincide con una figura operativa.

Esempi:

- un amministratore può avere un account ma non essere autista;
- un autista può avere un account collegato;
- un manager può essere anche dispatcher in certi contesti;
- una persona può avere più profili operativi.

Separare questi concetti evita accoppiamenti sbagliati tra login e business.

## 4.3 Struttura del package

Il package è diviso in sottopackage:

- `common`;
- `driver`;
- `mechanic`;
- `warehouse`;
- `dispatcher`;
- `manager`.

`common` contiene value object condivisi. Gli altri package contengono le entità operative specifiche.

## 4.4 Package `common`

### `OperationalCode`

`OperationalCode` è il codice interno aziendale della figura operativa.

Esempi:

- `DRV-001`;
- `MEC-023`;
- `WH-112`;
- `DSP-004`;
- `MNG-002`.

È diverso dall’ID tecnico.

L’ID serve al dominio e al sistema. Il codice operativo serve all’azienda e agli utenti.

Nel progetto è obbligatorio: ogni figura operativa deve avere un codice interno aziendale valido. Il codice viene normalizzato in maiuscolo e validato.

### `OperationalMetadata`

`OperationalMetadata` traccia la creazione e l’aggiornamento della figura operativa.

Contiene:

- `createdAt`;
- `updatedAt`;
- `createdBy`;
- `updatedBy`.

È separato da `UserMetadata`, perché appartiene al contesto operativo e non al contesto utenti.

### `OperationalProfile`

`OperationalProfile` rappresenta l’anagrafica operativa della persona.

Contiene:

- nome;
- cognome;
- email;
- telefono;
- cellulare;
- reparto;
- posizione;
- note.

È diverso da `UserProfile`.

`UserProfile` descrive l’account applicativo. `OperationalProfile` descrive la persona nel contesto operativo aziendale.

### `OperationalQualification`

`OperationalQualification` rappresenta una qualificazione posseduta da una figura operativa.

Contiene:

- una `Qualification` del catalogo;
- numero di riferimento;
- paese di emissione;
- livello;
- note.

Il campo `level` serve per distinguere livelli o varianti operative senza creare troppe classi specifiche.

Esempi:

- primo soccorso livello 1;
- muletto avanzato;
- formazione interna senior;
- abilitazione con livello specifico.

Non contiene scadenze o documenti. Quelli verranno gestiti in moduli futuri.

### `OperationalScope`

`OperationalScope` rappresenta un ambito operativo.

È usato soprattutto da dispatcher e manager.

Contiene:

- `OperationalScopeCode`, cioè il codice dell'ambito operativo;
- nome;
- descrizione;
- area.

Esempi:

- ADR operations;
- North Italy;
- Rome branch;
- Warehouse A;
- International transport;
- Fleet operations.

Lo scope permette di dire dove o su cosa una figura ha responsabilità.

Il codice dello scope non è più una semplice `String`: è modellato tramite `OperationalScopeCode`.

Questa scelta rende il modello coerente con gli altri codici aziendali del dominio, come `OperationalCode`, `FleetCode`, `CargoCode`, `ShipmentCode`, `LocationCode`, `DocumentCode` e `ComplianceRequirementCode`.

Il campo `area` rimane invece descrittivo e flessibile. Non viene trasformato subito in enum, perché può indicare aree operative diverse come North Italy, ADR operations, Warehouse A, Fleet operations o International transport.

### `OperationalStatus`

`OperationalStatus` rappresenta lo stato operativo.

I valori sono:

- `ACTIVE`;
- `SUSPENDED`;
- `NOT_ELIGIBLE`.

Significato:

- `ACTIVE`: figura operativa attiva;
- `SUSPENDED`: figura temporaneamente sospesa;
- `NOT_ELIGIBLE`: figura non idonea o non abilitata.

## 4.5 Package `driver`

### `Driver`

`Driver` rappresenta un autista reale dell’azienda.

Contiene:

- `DriverId`;
- `OperationalCode`;
- `UserId`;
- `OperationalProfile`;
- qualificazioni operative;
- stato operativo;
- metadata;
- note.

La classe permette di aggiungere e rimuovere qualificazioni, aggiornare il profilo e cambiare stato.

Regola importante: un autista `ACTIVE` deve avere almeno una qualificazione.

Le patenti sono qualificazioni. Non esiste un `DriverLicense` separato perché avrebbe duplicato il catalogo `Qualification`.

### `DriverId`

`DriverId` è l’identificatore tecnico dell’autista nel dominio operativo.

È distinto da `UserId`, perché l’identità dell’account e l’identità della figura operativa non sono la stessa cosa.

## 4.6 Package `mechanic`

### `Mechanic`

`Mechanic` rappresenta un meccanico reale dell’azienda.

Contiene:

- `MechanicId`;
- `OperationalCode`;
- `UserId`;
- `OperationalProfile`;
- qualificazioni tecniche;
- stato;
- metadata;
- note.

Può avere qualificazioni come:

- carrello elevatore;
- gru su autocarro;
- sicurezza antincendio;
- primo soccorso;
- corsi interni di manutenzione.

Regola importante: un meccanico `ACTIVE` deve avere almeno una qualificazione.

### `MechanicId`

`MechanicId` identifica il profilo operativo del meccanico.

## 4.7 Package `warehouse`

### `WarehouseOperator`

`WarehouseOperator` rappresenta un operatore di magazzino.

Contiene:

- `WarehouseOperatorId`;
- `OperationalCode`;
- `UserId`;
- `OperationalProfile`;
- qualificazioni;
- stato;
- metadata;
- note.

Può avere qualificazioni come:

- muletto;
- transpallet elettrico;
- gestione magazzino;
- rampe e baie di carico;
- movimentazione carichi.

Regola importante: un operatore di magazzino `ACTIVE` deve avere almeno una qualificazione.

### `WarehouseOperatorId`

Identifica il profilo operativo del magazziniere.

## 4.8 Package `dispatcher`

### `Dispatcher`

`Dispatcher` rappresenta la figura che coordina operazioni, viaggi, mezzi, autisti e attività logistiche.

A differenza di autisti, meccanici e magazzinieri, il dispatcher non ha necessariamente qualificazioni tecniche.

Ha invece `OperationalScope`, cioè ambiti di responsabilità.

Esempi di scope:

- domestic transport planning;
- international transport planning;
- ADR coordination;
- North Italy area;
- customer operations.

Regola importante: un dispatcher `ACTIVE` deve avere almeno uno scope.

### `DispatcherId`

Identifica il profilo operativo del dispatcher.

## 4.9 Package `manager`

### `Manager`

`Manager` rappresenta una figura di supervisione o responsabilità operativa.

Usa `OperationalScope` per indicare le aree su cui ha responsabilità.

Esempi:

- fleet operations;
- warehouse operations;
- maintenance supervision;
- transport performance;
- regional operations.

Regola importante: un manager `ACTIVE` deve avere almeno uno scope.

### `ManagerId`

Identifica il profilo operativo del manager.

## 4.10 Relazione con `domain.users`

Ogni figura operativa contiene un `UserId`.

Questa scelta permette di collegare una persona operativa a un account applicativo senza inserire direttamente un oggetto `User` dentro il dominio operativo.

Il vantaggio è che:

- il dominio operativo non dipende dal ciclo di vita dell’account;
- l’account non contiene dati operativi;
- una persona può avere più profili operativi;
- le modifiche all’account non rompono le entità operative.

## 4.11 Cosa non contiene il dominio operativo

Il dominio operativo non contiene:

- turni;
- disponibilità;
- assegnazioni a viaggio;
- documenti;
- scadenze;
- visite mediche;
- controllo automatico patenti;
- buste paga;
- ferie;
- pianificazione.

Queste parti saranno gestite in moduli successivi.

## Nota applicativa dopo il Punto 6J

Il dominio `operational` rimane puro. Dopo il Punto 6J l'application layer introduce i primi use case per registrare, trovare e cambiare stato a driver, mechanic, warehouse operator, dispatcher e manager. Non sono stati introdotti turni, disponibilità, payroll, planning, tracking o assegnazioni operative concrete.


## Nota applicativa dopo il Punto 6K

Il dominio `operational` rimane puro anche dopo il Punto 6K. La review applicativa rafforza i use case Operational Roles nel livello application, ma non sposta nel dominio concetti futuri come turni, disponibilità, payroll, assegnazioni operative, planning o tracking.

Il Punto 6K aggiunge test applicativi di hardening e conferma che le invarianti rimangono nel dominio mentre l'application layer orchestra command, repository port, service e result.
