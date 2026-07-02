# Archivio storico — 17-application-foundation

> Questo documento fa parte dell'archivio storico `docs/old_style/`.
> Mantiene il percorso step-by-step del progetto, ma la documentazione principale aggiornata si trova in:
> `docs/simple/`, `docs/professional/` e `docs/digital/`.

---

# 17 — Application Foundation

Questo documento descrive il **Punto 6B — Application Foundation** di TruckFlow Manager.

Il Punto 6A ha definito il blueprint del livello applicativo. Il Punto 6B inizia a trasformare quel blueprint in codice, creando la fondazione tecnica dell'application layer senza introdurre ancora casi d'uso operativi completi, repository concreti, database, controller REST o framework applicativi.

---

## 1. Obiettivo del Punto 6B

L'obiettivo del Punto 6B è creare la base minima, stabile e riusabile su cui costruire i futuri use case.

Questa fase introduce:

- il package `application`;
- i sottopackage `command`, `result`, `port.in`, `port.out`, `usecase` ed `exception`;
- i contratti base `ApplicationCommand`, `ApplicationResult` e `UseCase`;
- le eccezioni applicative fondamentali;
- test architetturali e contrattuali dedicati all'application layer;
- documentazione aggiornata sul nuovo step.

Il risultato non è ancora un'applicazione operativa completa, ma una fondazione pulita per iniziare il lavoro sui casi d'uso.

---

## 2. Cosa è stato aggiunto

### 2.1 Package `application`

È stato creato il package:

```text
it.gabriele.truckflow.application
```

Questo package rappresenta il secondo livello architetturale di TruckFlow Manager.

Il dominio rimane il livello che protegge le regole di business. L'application layer diventa il livello che, in futuro, coordinerà quelle regole tramite casi d'uso.

---

### 2.2 `application.command`

È stato creato il package:

```text
it.gabriele.truckflow.application.command
```

Questo package contiene il contratto base:

```text
ApplicationCommand
```

Un command rappresenta l'input di un caso d'uso applicativo.

Non è un DTO REST, non è un JSON e non contiene annotazioni di framework.

Esempi futuri:

- `RegisterLocationCommand`;
- `RegisterCargoUnitCommand`;
- `CreateShipmentCommand`;
- `AddShipmentItemCommand`;
- `AddShipmentLegCommand`;
- `ConfirmShipmentCommand`.

---

### 2.3 `application.result`

È stato creato il package:

```text
it.gabriele.truckflow.application.result
```

Questo package contiene il contratto base:

```text
ApplicationResult
```

Un result rappresenta l'output stabile di un caso d'uso applicativo.

Serve a evitare che gli adapter futuri, come controller REST o job, dipendano direttamente dagli aggregate root completi del dominio.

Esempi futuri:

- `LocationResult`;
- `CargoUnitResult`;
- `ShipmentResult`;
- `DocumentResult`;
- `ComplianceRequirementResult`.

---

### 2.4 `application.port.in`

È stato creato il package:

```text
it.gabriele.truckflow.application.port.in
```

Questo package contiene il contratto generico:

```text
UseCase
```

Il contratto esprime una regola semplice:

```text
un command entra, un result esce
```

I casi d'uso specifici potranno estendere questo concetto.

Esempi futuri:

- `RegisterLocationUseCase`;
- `RegisterCargoUnitUseCase`;
- `CreateShipmentUseCase`;
- `ConfirmShipmentUseCase`.

---

### 2.5 `application.port.out`

È stato creato il package:

```text
it.gabriele.truckflow.application.port.out
```

Per ora contiene solo la documentazione di package.

I repository port concreti non sono stati introdotti nel Punto 6B perché appartengono allo step successivo. Sono stati poi aggiunti nel Punto 6C e documentati in `18-application-repository-ports.md`.

Esempi futuri:

- `LocationRepository`;
- `CargoUnitRepository`;
- `ShipmentRepository`.

---

### 2.6 `application.usecase`

È stato creato il package:

```text
it.gabriele.truckflow.application.usecase
```

Per ora contiene solo la documentazione di package.

Le implementazioni dei casi d'uso non sono state ancora aggiunte.

La convenzione prevista rimane:

```text
Interfaccia use case: RegisterLocationUseCase
Implementazione: RegisterLocationService
```

La parola `Service` indica un application service, non un domain service.

---

### 2.7 `application.exception`

È stato creato il package:

```text
it.gabriele.truckflow.application.exception
```

Sono state introdotte le prime eccezioni applicative:

- `ApplicationException`;
- `UseCaseValidationException`;
- `ResourceNotFoundException`;
- `DuplicateResourceException`.

Queste eccezioni non sostituiscono le eccezioni del dominio.

Le eccezioni di dominio rappresentano violazioni delle regole di business.

Le eccezioni applicative rappresentano problemi di orchestrazione del caso d'uso.

---

## 3. Differenza tra errori di dominio ed errori applicativi

Esempi di errori di dominio:

- una shipment incompleta non può essere confermata;
- una temperatura minima non può essere maggiore della massima;
- un veicolo stradale non può essere creato senza targa quando la targa è obbligatoria;
- un documento non può avere metadati incoerenti.

Esempi di errori applicativi:

- la shipment richiesta non è stata trovata;
- esiste già una location con lo stesso codice;
- il command ricevuto è nullo;
- un campo richiesto dal caso d'uso è blank;
- un cargo da collegare alla shipment non esiste.

La regola è:

```text
Il dominio decide se il modello è valido.
L'application layer decide come orchestrare il modello e come gestire risorse mancanti, duplicati e input applicativi.
```

---

## 4. Cosa è stato testato

Sono stati aggiunti test dedicati all'application foundation.

I test verificano:

- normalizzazione dei messaggi di `ApplicationException`;
- comportamento di `ResourceNotFoundException`;
- comportamento di `DuplicateResourceException`;
- validazione base di `UseCaseValidationException`;
- contratto generico `UseCase`;
- assenza di dipendenze da Spring, JPA, Lombok, web e infrastructure nell'application layer;
- assenza di dipendenze del domain layer verso l'application layer.

Questi test non sostituiscono i test del dominio.

Servono a proteggere la nuova architettura applicativa prima dell'introduzione dei veri use case.

---

## 5. Cosa non è stato ancora fatto

Il Punto 6B non introduce ancora:

- use case specifici come `RegisterLocationUseCase` o `CreateShipmentUseCase`;
- command specifici come `RegisterLocationCommand`;
- result specifici come `ShipmentResult`;
- repository port specifici;
- repository in memory;
- controller REST;
- database;
- JPA;
- Spring annotations;
- sicurezza;
- API pubbliche;
- workflow operativi.

Queste parti sono escluse volontariamente perché devono arrivare in step successivi.

---

## 6. Perché questo step è necessario

Questo step è utile perché evita di iniziare l'application layer in modo disordinato.

Prima di creare use case concreti, il progetto ora ha:

- package chiari;
- contratti base;
- vocabolario applicativo condiviso;
- eccezioni applicative dedicate;
- test architetturali;
- separazione esplicita da framework e infrastruttura.

In questo modo i prossimi use case potranno seguire una forma coerente.

---

## 7. Stato dopo il Punto 6C

Il passo successivo al Punto 6B è stato avviato con:

```text
Punto 6C — Repository Ports
```

Nel Punto 6C sono state introdotte le prime porte repository astratte del blocco:

- Locations;
- Cargo;
- Shipments.

Repository aggiunti:

- `LocationRepository`;
- `CargoUnitRepository`;
- `ShipmentRepository`;
- `RepositoryPort`.

Questi repository sono interfacce applicative, non implementazioni tecniche.

Le implementazioni in memory arriveranno nello step successivo, Punto 6D.

## Collegamento con il Punto 6D

Dopo la foundation applicativa e le repository port, il Punto 6D introduce il primo adapter infrastructure reale: le repository in memory.

Questo step usa le eccezioni applicative definite nella foundation:

- `UseCaseValidationException` per input nulli;
- `DuplicateResourceException` per codici duplicati.

In questo modo la foundation non rimane teorica: viene utilizzata concretamente da adapter tecnici leggeri, senza introdurre framework o database.

## Aggiornamento dopo il Punto 6E

La foundation introdotta nel Punto 6B è ora usata dai primi use case reali.

I marker `ApplicationCommand` e `ApplicationResult` sono implementati da command e result specifici per Locations, Cargo e Shipments. Il contratto generico `UseCase` è specializzato dalle nuove port in, mentre le eccezioni applicative sono usate per command nulli, risorse mancanti e duplicati.

Questo conferma che la foundation è sufficiente per avviare casi d'uso concreti senza introdurre Spring, REST API, database o framework esterni.

## Collegamento con il Punto 6F

La foundation applicativa introdotta nel Punto 6B viene utilizzata pienamente nel Punto 6F.

`ApplicationCommand`, `ApplicationResult`, `UseCase`, `UseCaseValidationException`, `ResourceNotFoundException` e `DuplicateResourceException` vengono esercitati nei test di hardening dei primi use case. Questo conferma che la foundation è abbastanza stabile per supportare casi d'uso reali senza dipendere da framework o infrastruttura concreta.

## Allineamento Punto 6M

Il Punto 6M chiude il primo ciclo dell'application layer con una review/freeze finale. Da questo momento i contenuti documentati nei punti 6A-6L sono considerati fondazione applicativa stabile: eventuali evoluzioni future dovranno essere introdotte in nuovi punti roadmap, mantenendo ancora fuori REST API, controller, database, JPA, Spring Data, security, tracking, planning, dashboard, workflow e integrazioni esterne.
