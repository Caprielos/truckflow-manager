# Archivio storico — 24-application-use-cases-expansion-vehicles

> Questo documento fa parte dell'archivio storico `docs/old_style/`.
> Mantiene il percorso step-by-step del progetto, ma la documentazione principale aggiornata si trova in:
> `docs/simple/`, `docs/professional/` e `docs/digital/`.

---

# Punto 6I — Application Use Cases Expansion II: Vehicles

Il Punto 6I espande l'application layer verso il dominio `vehicles`.

La fase è volutamente controllata: rende disponibili i primi use case applicativi per registrare e consultare unità veicolo e combinazioni operative, senza introdurre ancora REST API, controller Spring, database, JPA, security, tracking, planning, dispatching, manutenzione, disponibilità mezzi, dashboard o assegnazioni operative alle shipment.

Il dominio `vehicles` era già stato modellato come dominio puro. Il Punto 6I lo collega ora all'application layer tramite command, result, port in, port out, application service, repository in memory e test.

## Obiettivo dello step

L'obiettivo non è creare un fleet management completo.

L'obiettivo è introdurre il primo blocco applicativo per il registro logico della flotta:

- registrare una `VehicleUnit` fisica;
- trovare una `VehicleUnit` per ID;
- cambiare lo stato applicativo di una `VehicleUnit`;
- registrare una `VehicleCombination` da unità veicolo già esistenti;
- trovare una `VehicleCombination` per ID;
- mantenere il dominio `vehicles` indipendente da infrastructure, web e database;
- continuare a distinguere errori applicativi da errori di dominio.

## Package aggiunti

Sono stati aggiunti nuovi package applicativi dedicati ai vehicles:

```text
src/main/java/it/gabriele/truckflow/application/command/vehicles
src/main/java/it/gabriele/truckflow/application/result/vehicles
src/main/java/it/gabriele/truckflow/application/port/in/vehicles
src/main/java/it/gabriele/truckflow/application/port/out/vehicles
src/main/java/it/gabriele/truckflow/application/usecase/vehicles
src/main/java/it/gabriele/truckflow/infrastructure/memory/vehicles
```

Questi package seguono la stessa architettura già usata per Locations, Cargo, Shipments e Documents.

## Command aggiunti per VehicleUnit

Sono stati aggiunti sei command applicativi per le unità veicolo:

- `RegisterVehicleUnitCommand`;
- `FindVehicleUnitCommand`;
- `ActivateVehicleUnitCommand`;
- `SuspendVehicleUnitCommand`;
- `MarkVehicleUnitOutOfServiceCommand`;
- `DismissVehicleUnitCommand`.

`RegisterVehicleUnitCommand` rappresenta l'input applicativo necessario per registrare una nuova unità fisica di flotta.

Richiede:

- `FleetCode`;
- `VehicleIdentificationNumber`;
- `VehicleUnitType`;
- `VehicleBodyType`;
- `PowerSource`;
- `VehicleTechnicalSpecification`;
- `VehicleStatus`.

Accetta inoltre:

- `LicensePlate`, opzionale per mezzi non stradali;
- `VehicleBodyProfile`;
- `Set<VehicleCapability>`;
- `Set<VehicleOperationalRole>`;
- `CouplingProfile`;
- `notes`.

Gli insiemi nulli di capability e ruoli operativi vengono normalizzati a set vuoto. Se presenti, non possono contenere elementi nulli.

## Command aggiunti per VehicleCombination

Sono stati aggiunti due command applicativi per le combinazioni veicolo:

- `RegisterVehicleCombinationCommand`;
- `FindVehicleCombinationCommand`.

`RegisterVehicleCombinationCommand` non crea unità veicolo nuove. Riceve invece gli ID di unità già registrate e delega al dominio `VehicleCombination.fromUnits(...)` la validazione della forma della combinazione.

Questa scelta mantiene corretti i confini:

- l'application layer orchestra il caricamento delle unità;
- il dominio valida se la combinazione è sensata;
- l'infrastructure memory salva il risultato tramite repository port;
- non viene introdotta pianificazione operativa.

## Result aggiunti

Sono stati aggiunti due result applicativi:

```text
VehicleUnitResult
VehicleCombinationResult
```

`VehicleUnitResult` espone una vista applicativa essenziale della unità veicolo:

- id;
- fleet code;
- targa, quando presente;
- VIN;
- tipo unità;
- tipo allestimento;
- alimentazione;
- stato;
- presenza targa;
- capacità di trainare;
- capacità di essere trainato;
- indicatore trailer;
- numero di capability;
- numero di ruoli operativi.

`VehicleCombinationResult` espone:

- id;
- tipo combinazione;
- ID unità primaria;
- ID unità secondaria, quando presente;
- presenza unità secondaria;
- stato;
- numero di capability combinate;
- numero di ruoli operativi combinati.

I result non espongono ancora disponibilità, costi, manutenzione, telematica, scadenze, pianificazione o assegnazioni a viaggi.

## Repository port aggiunte

Sono state aggiunte due porte outbound:

```text
VehicleUnitRepository
VehicleCombinationRepository
```

`VehicleUnitRepository` consente:

- `save(VehicleUnit vehicleUnit)`;
- `findById(VehicleUnitId id)`;
- `findByFleetCode(FleetCode fleetCode)`;
- `findByVin(VehicleIdentificationNumber vin)`;
- `findByLicensePlate(LicensePlate licensePlate)`;
- `existsById(VehicleUnitId id)`;
- `existsByFleetCode(FleetCode fleetCode)`;
- `existsByVin(VehicleIdentificationNumber vin)`;
- `existsByLicensePlate(LicensePlate licensePlate)`.

La repository controlla quindi gli identificativi principali della flotta: ID tecnico, codice flotta, VIN e targa.

`VehicleCombinationRepository` consente:

- `save(VehicleCombination vehicleCombination)`;
- `findById(VehicleCombinationId id)`;
- `existsById(VehicleCombinationId id)`.

Le combinazioni veicolo attuali non hanno ancora un codice business dedicato. Per questo la prima repository espone solo identità tecnica di dominio.

## Use case port aggiunte

Sono state aggiunte le seguenti port in:

- `RegisterVehicleUnitUseCase`;
- `FindVehicleUnitUseCase`;
- `ActivateVehicleUnitUseCase`;
- `SuspendVehicleUnitUseCase`;
- `MarkVehicleUnitOutOfServiceUseCase`;
- `DismissVehicleUnitUseCase`;
- `RegisterVehicleCombinationUseCase`;
- `FindVehicleCombinationUseCase`.

Ogni port in estende il contratto base `UseCase<C, R>`.

## Application service aggiunti

Sono stati aggiunti i seguenti service applicativi:

- `RegisterVehicleUnitService`;
- `FindVehicleUnitService`;
- `ActivateVehicleUnitService`;
- `SuspendVehicleUnitService`;
- `MarkVehicleUnitOutOfServiceService`;
- `DismissVehicleUnitService`;
- `RegisterVehicleCombinationService`;
- `FindVehicleCombinationService`.

I service:

- validano command nulli;
- validano dependency repository nulle;
- usano solo repository port;
- restituiscono result applicativi;
- lanciano `ResourceNotFoundException` quando una risorsa richiesta non esiste;
- lanciano `DuplicateResourceException` per codici flotta, VIN o targhe duplicati;
- delegano al dominio le regole di coerenza tecnica del veicolo;
- non importano Spring, REST, JPA, controller o database.

## Mutazioni VehicleUnit copy-on-write

È stato aggiunto:

```text
VehicleUnitMutationSupport
```

Le mutazioni di stato delle unità veicolo lavorano su una copia dell'aggregate prima del salvataggio.

Questo riguarda:

- activate;
- suspend;
- mark out of service;
- dismiss.

La scelta è coerente con quanto fatto per Shipments e Documents: una mutazione deve diventare persistente solo dopo aver completato correttamente il use case.

## Mutazioni VehicleCombination copy-on-write

È stato aggiunto anche:

```text
VehicleCombinationMutationSupport
```

In questa fase i use case pubblici sulle combinazioni sono solo register e find, ma il supporto alla copia viene preparato ora per mantenere coerenza architetturale e per futuri use case di stato sulle combinazioni.

## Repository in memory aggiunte

Sono state aggiunte:

```text
InMemoryVehicleUnitRepository
InMemoryVehicleCombinationRepository
```

`InMemoryVehicleUnitRepository`:

- implementa `VehicleUnitRepository`;
- indicizza per `VehicleUnitId`;
- indicizza per `FleetCode`;
- indicizza per `VehicleIdentificationNumber`;
- indicizza per `LicensePlate`, quando presente;
- rifiuta input nulli con `UseCaseValidationException`;
- rifiuta duplicati con `DuplicateResourceException`.

`InMemoryVehicleCombinationRepository`:

- implementa `VehicleCombinationRepository`;
- indicizza per `VehicleCombinationId`;
- rifiuta input nulli con `UseCaseValidationException`.

Queste implementazioni restano adapter tecnici temporanei per sviluppo e test.

## Test aggiunti e aggiornati

Sono stati aggiunti:

```text
ApplicationVehicleUseCaseExpansionTest
ApplicationVehicleRepositoryPortTest
InMemoryVehicleRepositoryTest
```

Sono stati aggiornati anche i test di review applicativa esistenti per includere le nuove port, i nuovi service e i nuovi result Vehicles.

I test verificano:

- registrazione unità veicolo;
- ricerca unità veicolo;
- activate/suspend/out of service/dismiss;
- copy-on-write delle mutazioni di stato VehicleUnit;
- duplicati per fleet code, VIN e targa;
- command nulli;
- dependency repository nulle;
- risorse mancanti;
- registrazione combinazione articolata da trattore e semirimorchio esistenti;
- ricerca combinazione;
- unità mancanti nella registrazione combinazione;
- forma combinazione non valida delegata al dominio;
- contratti repository port;
- repository in memory Vehicles con input nulli.

## Confini mantenuti

Il Punto 6I non introduce:

- REST API;
- controller Spring;
- DTO web;
- database;
- JPA;
- Hibernate;
- Spring Data;
- security;
- JWT;
- tracking GPS;
- telematica;
- pianificazione viaggi;
- dispatching;
- assegnazione veicolo/autista a shipment;
- disponibilità giornaliera mezzi;
- manutenzione;
- pneumatici;
- scadenze tecniche;
- costi flotta;
- dashboard;
- audit trail;
- workflow enterprise.

Il Punto 6I registra e consulta solo il registro applicativo della flotta logica.

## Stato finale dopo il Punto 6I

Dopo questo step l'application layer copriva:

- Locations;
- Cargo;
- Shipments;
- Documents;
- Vehicles.

L'application layer ora possiede un primo nucleo coerente per:

- luoghi logistici;
- unità cargo;
- richieste di spedizione;
- documenti logici;
- unità veicolo;
- combinazioni veicolo.

Il prossimo step, dopo il Punto 6I, è stato applicato come Punto 6J — Application Use Cases Expansion III: Operational Roles.

## Aggiornamento dopo il Punto 6J

Il Punto 6J non modifica i use case Vehicles. Aggiunge invece il primo blocco applicativo Operational Roles. Le assegnazioni concrete driver/vehicle/shipment rimangono fuori: verranno valutate solo in una fase futura di planning o dispatching.

## Aggiornamento dopo il Punto 6K

Il Punto 6K non modifica i use case Vehicles. Rafforza invece il blocco Operational Roles introdotto dopo Vehicles. Le assegnazioni concrete tra driver, veicoli e shipment restano fuori dal progetto attuale e appartengono a future fasi di planning o dispatching.

## Aggiornamento dopo il Punto 6L

Il Punto 6L non modifica i use case Vehicles. Aggiunge invece un blocco applicativo separato per Compliance base.

Le compatibilità concrete tra veicoli, cargo, documenti e requisiti compliance restano fuori: verranno trattate solo in moduli futuri di planning, dispatching o compliance check.

## Allineamento Punto 6M

Il Punto 6M chiude il primo ciclo dell'application layer con una review/freeze finale. Da questo momento i contenuti documentati nei punti 6A-6L sono considerati fondazione applicativa stabile: eventuali evoluzioni future dovranno essere introdotte in nuovi punti roadmap, mantenendo ancora fuori REST API, controller, database, JPA, Spring Data, security, tracking, planning, dashboard, workflow e integrazioni esterne.
