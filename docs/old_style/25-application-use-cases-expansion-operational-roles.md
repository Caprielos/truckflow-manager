# Archivio storico — 25-application-use-cases-expansion-operational-roles

> Questo documento fa parte dell'archivio storico `docs/old_style/`.
> Mantiene il percorso step-by-step del progetto, ma la documentazione principale aggiornata si trova in:
> `docs/simple/`, `docs/professional/` e `docs/digital/`.

---

# Punto 6J — Application Use Cases Expansion III: Operational Roles

Il Punto 6J espande l'application layer verso il dominio `operational`.

La fase introduce i primi use case applicativi per le figure operative aziendali già modellate nel dominio puro:

- `Driver`;
- `Mechanic`;
- `WarehouseOperator`;
- `Dispatcher`;
- `Manager`.

Lo step rimane volutamente applicativo e controllato. Non introduce ancora turni, disponibilità, assegnazioni a shipment, pianificazione, dispatching reale, payroll, HR avanzato, tracking, dashboard, security, database, JPA, REST API o controller Spring.

## Obiettivo dello step

L'obiettivo del Punto 6J è rendere il dominio `operational` utilizzabile dai casi d'uso applicativi di base.

Lo step permette di:

- registrare figure operative aziendali;
- recuperarle per ID;
- cambiare il loro stato applicativo;
- impedire duplicati per codice operativo;
- impedire duplicati per `UserId` nello stesso ruolo;
- usare repository port astratte;
- fornire adapter in memory temporanei;
- testare copy-on-write sulle mutazioni di stato;
- aggiornare la documentazione del livello application.

Il dominio continua a decidere se una figura operativa è valida. L'application layer decide quando crearla, caricarla, mutarla, salvarla e trasformarla in result.

## Package aggiunti

Sono stati aggiunti nuovi package applicativi dedicati agli operational roles:

```text
src/main/java/it/gabriele/truckflow/application/command/operational
src/main/java/it/gabriele/truckflow/application/result/operational
src/main/java/it/gabriele/truckflow/application/port/in/operational
src/main/java/it/gabriele/truckflow/application/port/out/operational
src/main/java/it/gabriele/truckflow/application/usecase/operational
src/main/java/it/gabriele/truckflow/infrastructure/memory/operational
```

La struttura segue la stessa forma già usata per Locations, Cargo, Shipments, Documents e Vehicles.

## Command aggiunti

Per ogni figura operativa sono stati aggiunti command di registrazione, ricerca e mutazione stato.

Per i driver:

- `RegisterDriverCommand`;
- `FindDriverCommand`;
- `ActivateDriverCommand`;
- `SuspendDriverCommand`;
- `MarkNotEligibleDriverCommand`.

Per i mechanic:

- `RegisterMechanicCommand`;
- `FindMechanicCommand`;
- `ActivateMechanicCommand`;
- `SuspendMechanicCommand`;
- `MarkNotEligibleMechanicCommand`.

Per i warehouse operator:

- `RegisterWarehouseOperatorCommand`;
- `FindWarehouseOperatorCommand`;
- `ActivateWarehouseOperatorCommand`;
- `SuspendWarehouseOperatorCommand`;
- `MarkNotEligibleWarehouseOperatorCommand`.

Per i dispatcher:

- `RegisterDispatcherCommand`;
- `FindDispatcherCommand`;
- `ActivateDispatcherCommand`;
- `SuspendDispatcherCommand`;
- `MarkNotEligibleDispatcherCommand`.

Per i manager:

- `RegisterManagerCommand`;
- `FindManagerCommand`;
- `ActivateManagerCommand`;
- `SuspendManagerCommand`;
- `MarkNotEligibleManagerCommand`.

I command di registrazione per driver, mechanic e warehouse operator ricevono `Set<OperationalQualification>`. I command di registrazione per dispatcher e manager ricevono `Set<OperationalScope>`.

Gli insiemi nulli vengono normalizzati a set vuoto. Se presenti, non possono contenere elementi nulli.

## Result aggiunti

Sono stati aggiunti result applicativi dedicati:

```text
DriverResult
MechanicResult
WarehouseOperatorResult
DispatcherResult
ManagerResult
```

I result espongono una vista applicativa essenziale:

- ID della figura operativa;
- `OperationalCode`;
- `UserId` collegato;
- `OperationalStatus`;
- nome completo;
- indicatore `active`;
- conteggio qualificazioni o scope;
- note.

I result non espongono ancora dati HR avanzati, turni, disponibilità, assegnazioni, performance, costi, violazioni o dati payroll.

## Repository port aggiunte

Sono state aggiunte cinque repository port outbound:

```text
DriverRepository
MechanicRepository
WarehouseOperatorRepository
DispatcherRepository
ManagerRepository
```

Ogni repository consente:

- `save(...)`;
- `findById(...)`;
- `findByCode(OperationalCode code)`;
- `findByUserId(UserId userId)`;
- `existsById(...)`;
- `existsByCode(OperationalCode code)`;
- `existsByUserId(UserId userId)`.

La scelta di controllare anche `UserId` serve a evitare duplicati dello stesso account utente nello stesso ruolo operativo.

Non viene impedito, a livello applicativo, che lo stesso `UserId` possa appartenere a ruoli diversi. Questa decisione rimane aperta perché in aziende reali una persona può avere più responsabilità operative.

## Use case port aggiunte

Ogni command ha una port in dedicata che estende il contratto base `UseCase<C, R>`.

Sono quindi state aggiunte port in per:

- registrazione;
- ricerca;
- attivazione;
- sospensione;
- marcatura come non idoneo.

Questa forma mantiene chiaro il confine tra richiesta applicativa e implementazione concreta del service.

## Application service aggiunti

Sono stati aggiunti service applicativi per ogni port in.

I service:

- validano command nulli;
- validano dependency repository nulle;
- controllano duplicati per `OperationalCode`;
- controllano duplicati per `UserId` nello stesso ruolo;
- caricano l'aggregate tramite repository port;
- lanciano `ResourceNotFoundException` quando la risorsa richiesta non esiste;
- lanciano `DuplicateResourceException` quando viene rilevato un duplicato applicativo;
- delegano al dominio le invarianti su qualificazioni, scope e stato;
- restituiscono result applicativi;
- non importano Spring, controller, DTO web, database, JPA o infrastructure concreta.

## Copy-on-write sulle mutazioni operational

Per ogni ruolo operativo è stato aggiunto un supporto di copia:

```text
DriverMutationSupport
MechanicMutationSupport
WarehouseOperatorMutationSupport
DispatcherMutationSupport
ManagerMutationSupport
```

Le mutazioni di stato non modificano direttamente l'istanza caricata dalla repository in memory.

Il flusso è:

1. il service carica l'aggregate esistente;
2. crea una copia con lo stesso ID e gli stessi dati;
3. applica la mutazione sulla copia;
4. salva la copia solo se la mutazione di dominio va a buon fine.

Questa scelta protegge dai problemi tipici degli adapter in memory, dove la repository contiene riferimenti a oggetti mutabili.

## Repository in memory aggiunte

Sono stati aggiunti adapter in memory per tutte le nuove repository port:

```text
InMemoryDriverRepository
InMemoryMechanicRepository
InMemoryWarehouseOperatorRepository
InMemoryDispatcherRepository
InMemoryManagerRepository
```

Gli adapter:

- salvano in `Map` interne;
- indicizzano per ID;
- indicizzano per `OperationalCode`;
- indicizzano per `UserId`;
- rifiutano input nulli con `UseCaseValidationException`;
- rifiutano duplicati con `DuplicateResourceException`.

Sono adapter temporanei per sviluppo locale e test. Non sostituiscono un database reale e non introducono transazioni, query avanzate o persistenza definitiva.

## Test aggiunti e aggiornati

Sono stati aggiunti test dedicati al Punto 6J:

```text
ApplicationOperationalUseCaseExpansionTest
ApplicationOperationalRepositoryPortTest
InMemoryOperationalRepositoryTest
```

È stato inoltre aggiornato:

```text
ApplicationUseCaseReviewTest
```

I test verificano:

- registrazione e ricerca dei ruoli operativi;
- mutazioni di stato con copy-on-write;
- duplicati per codice operativo;
- duplicati per `UserId` nello stesso ruolo;
- risorse mancanti;
- command nulli;
- dependency repository nulle;
- set nulli normalizzati;
- set con elementi nulli rifiutati;
- repository port che estendono `RepositoryPort`;
- port in che estendono `UseCase`;
- service che implementano la propria port;
- result null-safe.

## Confini architetturali rispettati

Il Punto 6J non introduce:

- REST API;
- controller Spring;
- DTO web;
- database;
- JPA;
- Spring Data;
- security;
- JWT;
- planning;
- dispatching reale;
- turni;
- disponibilità persone;
- payroll;
- dashboard;
- tracking;
- assegnazione autista-veicolo-shipment;
- audit trail enterprise.

Il dominio `operational` rimane puro. L'application layer consuma il dominio tramite command, result, port, service e repository astratte.

## Stato finale dopo il Punto 6J

Dopo il Punto 6J, l'application layer copre:

- Locations;
- Cargo;
- Shipments;
- Documents;
- Vehicles;
- Operational Roles.

Il progetto è ancora prima di REST API e database.

Storicamente, dopo Operational Roles, il progetto ha scelto una fase di review/hardening dedicata nel Punto 6K. Successivamente sono stati completati anche Compliance base nel Punto 6L e il freeze finale nel Punto 6M.

## Aggiornamento dopo il Punto 6K

Il Punto 6K rafforza il blocco introdotto in questo documento.

La review aggiunge `ApplicationOperationalUseCaseHardeningTest`, copre i service di stato per tutti i ruoli operativi correnti, verifica command nulli e dependency nulle e controlla che una attivazione fallita per mancanza di qualificazioni o scope non modifichi l'aggregate persistito in memory.

Il Punto 6K non introduce nuovi use case business né cambia i contratti pubblici aggiunti nel Punto 6J.

## Aggiornamento dopo il Punto 6L

Il Punto 6L non modifica i use case Operational Roles. Aggiunge invece i primi use case applicativi per il catalogo base di Compliance.

Le verifiche concrete su patente, CQC, ADR, visite mediche, ruoli e abilitazioni restano fuori dal Punto 6L e appartengono a futuri moduli di compliance check.

## Allineamento Punto 6M

Il Punto 6M chiude il primo ciclo dell'application layer con una review/freeze finale. Da questo momento i contenuti documentati nei punti 6A-6L sono considerati fondazione applicativa stabile: eventuali evoluzioni future dovranno essere introdotte in nuovi punti roadmap, mantenendo ancora fuori REST API, controller, database, JPA, Spring Data, security, tracking, planning, dashboard, workflow e integrazioni esterne.
