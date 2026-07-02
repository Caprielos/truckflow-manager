# Archivio storico — 20-application-first-use-cases

> Questo documento fa parte dell'archivio storico `docs/old_style/`.
> Mantiene il percorso step-by-step del progetto, ma la documentazione principale aggiornata si trova in:
> `docs/simple/`, `docs/professional/` e `docs/digital/`.

---

# Punto 6E — First Use Cases

Il **Punto 6E** introduce i primi casi d'uso applicativi reali di TruckFlow Manager.

Dopo il blueprint dell'application layer, la foundation applicativa, le repository port e le repository in memory, il progetto può finalmente iniziare a orchestrare il dominio tramite azioni applicative chiare, testabili e indipendenti da web, database e framework.

Questa fase non introduce REST API, controller, JPA, Spring, database, security o frontend. L'obiettivo è costruire il primo flusso applicativo completo usando solo:

- domain layer;
- application command;
- application result;
- port in;
- port out;
- application service;
- repository in memory per i test.

## Obiettivo dello step

Il Punto 6E serve a trasformare la struttura applicativa in comportamento reale.

I primi use case permettono di:

- registrare una location;
- recuperare una location;
- registrare una cargo unit;
- recuperare una cargo unit;
- creare una shipment in stato draft;
- aggiungere un item alla shipment;
- aggiungere una leg alla shipment;
- confermare una shipment;
- recuperare una shipment.

Il primo flusso applicativo completo è quindi:

1. registrare una location di partenza;
2. registrare una location di destinazione;
3. registrare un cargo;
4. creare una shipment draft;
5. aggiungere il cargo alla shipment;
6. aggiungere una tratta dalla location A alla location B;
7. confermare la shipment;
8. recuperare la shipment confermata.

Questo è il primo scenario in cui l'application layer orchestra più domini senza duplicare le regole di business.

## Package introdotti

Il Punto 6E espande l'application layer con package specifici:

- `application.command.locations`;
- `application.command.cargo`;
- `application.command.shipments`;
- `application.result.locations`;
- `application.result.cargo`;
- `application.result.shipments`;
- `application.port.in.locations`;
- `application.port.in.cargo`;
- `application.port.in.shipments`;
- `application.usecase.locations`;
- `application.usecase.cargo`;
- `application.usecase.shipments`.

Questa struttura mantiene separati input, output, contratti e implementazioni.

## Command applicativi

I command rappresentano l'input dell'application layer.

Sono stati introdotti:

- `RegisterLocationCommand`;
- `FindLocationCommand`;
- `RegisterCargoUnitCommand`;
- `FindCargoUnitCommand`;
- `CreateShipmentCommand`;
- `AddShipmentItemCommand`;
- `AddShipmentLegCommand`;
- `ConfirmShipmentCommand`;
- `FindShipmentCommand`.

I command non sono DTO REST e non rappresentano ancora payload JSON. Sono input applicativi interni, pensati per essere chiamati in futuro da controller, CLI, batch, API o integrazioni.

I command validano solo le informazioni applicative minime, per esempio:

- command non nullo;
- identificatori richiesti;
- codice richiesto;
- nome richiesto;
- tipo richiesto;
- stato richiesto quando necessario;
- categorie cargo non vuote.

Le regole profonde restano nel dominio.

## Result applicativi

I result rappresentano l'output dei casi d'uso.

Sono stati introdotti:

- `LocationResult`;
- `CargoUnitResult`;
- `ShipmentResult`.

I result espongono le informazioni principali necessarie ai chiamanti applicativi senza obbligare il futuro web layer a restituire direttamente aggregate completi.

Questa scelta prepara meglio l'introduzione futura di DTO REST, API pubbliche e viste specifiche.

## Port in

Le port in sono i contratti dei casi d'uso.

Sono stati introdotti:

- `RegisterLocationUseCase`;
- `FindLocationUseCase`;
- `RegisterCargoUnitUseCase`;
- `FindCargoUnitUseCase`;
- `CreateShipmentUseCase`;
- `AddShipmentItemUseCase`;
- `AddShipmentLegUseCase`;
- `ConfirmShipmentUseCase`;
- `FindShipmentUseCase`.

Questi contratti rappresentano ciò che l'esterno potrà chiedere all'applicazione.

In futuro i controller REST non dovranno conoscere le implementazioni concrete. Dovranno dipendere da queste interfacce.

## Application service

Le implementazioni dei casi d'uso sono state collocate in `application.usecase`.

Sono stati introdotti:

- `RegisterLocationService`;
- `FindLocationService`;
- `RegisterCargoUnitService`;
- `FindCargoUnitService`;
- `CreateShipmentService`;
- `AddShipmentItemService`;
- `AddShipmentLegService`;
- `ConfirmShipmentService`;
- `FindShipmentService`.

Ogni service segue lo stesso schema:

1. riceve un command;
2. valida che il command non sia nullo;
3. usa una o più repository port;
4. crea o carica aggregate di dominio;
5. chiama il dominio;
6. salva l'aggregate aggiornato;
7. restituisce un result applicativo.

## Regola più importante

L'application layer non duplica le regole del dominio.

Per esempio:

- `ConfirmShipmentService` non decide se una shipment è confermabile;
- `ConfirmShipmentService` carica la shipment, chiama `shipment.confirm()` e salva il risultato;
- se la shipment è incompleta, l'eccezione resta una `InvalidShipmentException` del dominio;
- l'application layer interviene solo quando il problema è applicativo, come risorsa mancante o duplicato.

La regola da ricordare è:

> Il dominio decide se una cosa è valida. L'application layer decide quando chiamare il dominio e dove salvare il risultato.

## Errori applicativi

I primi use case distinguono tra errori applicativi ed errori di dominio.

Sono errori applicativi:

- command nullo;
- repository nullo nel costruttore del service;
- shipment non trovata;
- cargo non trovato;
- location non trovata;
- codice duplicato.

Per questi casi vengono usate:

- `UseCaseValidationException`;
- `ResourceNotFoundException`;
- `DuplicateResourceException`.

Sono invece errori di dominio:

- shipment confermata senza item;
- shipment confermata senza leg;
- temperatura controllata senza requisito corrispondente;
- quantity non positiva;
- leg incoerente.

Questi errori restano nel domain layer.

## Primo flusso testato

È stato aggiunto un test applicativo completo che dimostra lo scenario principale:

1. registrazione del deposito di Milano;
2. registrazione del deposito di Roma;
3. registrazione di un cargo alimentare refrigerato;
4. creazione di una shipment draft;
5. aggiunta del cargo come shipment item;
6. aggiunta della tratta Milano - Roma;
7. conferma della shipment;
8. recupero della shipment confermata.

Questo test dimostra che:

- i command sono utilizzabili;
- i service orchestrano repository e dominio;
- le repository in memory permettono test senza database;
- la shipment viene salvata e aggiornata dopo ogni mutazione;
- le regole di conferma restano nel dominio;
- il result finale racconta lo stato applicativo ottenuto.

## Casi negativi testati

Sono stati aggiunti anche casi negativi importanti:

- registrazione di una location con codice duplicato;
- aggiunta di un item con cargo inesistente;
- aggiunta di una leg con location di destinazione inesistente;
- conferma di una shipment incompleta.

Questi test verificano che l'application layer gestisca correttamente:

- duplicati;
- lookup mancanti;
- propagazione delle eccezioni di dominio.

## Cosa non è stato fatto

Il Punto 6E non introduce ancora:

- REST controller;
- DTO web;
- database;
- JPA;
- Spring annotations;
- transazioni;
- security;
- paginazione;
- ricerca avanzata;
- workflow;
- planning e dispatching;
- availability;
- tracking;
- fleet operations;
- use case per tutti i domini;
- mapping enterprise completo tra result e viste pubbliche.

Queste funzionalità arriveranno dopo.

## Perché partire da Locations + Cargo + Shipments

Locations, Cargo e Shipments sono il primo blocco corretto perché rappresentano un flusso logistico reale ma ancora controllabile.

Una shipment usa:

- `LocationId` per origine e destinazione;
- `CargoId` per gli item;
- `ShipmentItem` per la merce richiesta;
- `ShipmentLeg` per le tratte;
- `ShipmentRequirementSet` per i requisiti di trasporto.

Questa scelta permette di testare l'orchestrazione senza introdurre ancora veicoli, autisti, pianificazione, disponibilità o tracking.

## Relazione con il Punto 6F

Il Punto 6E introduce i primi use case.

Storicamente, dopo il Punto 6E, il passo successivo è stato il **Punto 6F — Application Use Case Review & Hardening**. Oggi quel percorso è già stato completato fino al Punto 6M.

Il Punto 6F dovrebbe:

- rivedere i primi use case;
- rafforzare i test applicativi;
- controllare naming e package;
- verificare che l'application layer non importi infrastructure;
- verificare che il domain layer non importi application;
- decidere se estendere i use case a Documents, Compliance, Vehicles e Operational;
- preparare la futura esposizione REST senza introdurla troppo presto.

## Stato dopo il Punto 6E

Dopo questo step, il progetto contiene:

- domain layer puro;
- test suite del dominio;
- application foundation;
- repository port;
- repository in memory;
- primi command specifici;
- primi result specifici;
- prime port in;
- primi application service;
- primo flusso applicativo reale testato.

Il progetto non è ancora una REST API e non usa ancora database, ma ha iniziato a comportarsi come una vera applicazione organizzata per casi d'uso.

## Aggiornamento successivo — Punto 6F

Dopo il Punto 6E è stato aggiunto il Punto 6F — Application Use Case Review & Hardening.

Questa fase completa e rafforza il primo gruppo di use case aggiungendo `CancelShipmentUseCase` e testando casi negativi più espliciti: command nulli, input obbligatori mancanti, risorse inesistenti, duplicati cargo, cancellazione shipment e mutazioni fallite senza stato parziale.

Il Punto 6F non cambia la natura del Punto 6E: l'application layer continua a orchestrare il dominio senza duplicare le regole business.

## Aggiornamento successivo — Punto 6G

Dopo l'hardening del Punto 6F, il Punto 6G ha aggiunto il primo blocco applicativo fuori dal flusso Locations + Cargo + Shipments: i use case Documents.

Il nuovo blocco non modifica il significato del Punto 6E, ma estende l'application layer con una seconda area applicativa coerente:

- registrare un documento logico;
- trovare un documento;
- attivare un documento;
- archiviare un documento.

La documentazione specifica è disponibile in `docs/old_style/22-application-use-case-expansion.md`.

## Aggiornamento dopo il Punto 6H

Il Punto 6H non modifica il primo flusso Locations + Cargo + Shipments introdotto nel Punto 6E.

La review aggiunge però una garanzia trasversale: i result applicativi collegati ai primi use case (`LocationResult`, `CargoUnitResult` e `ShipmentResult`) ora rifiutano input nulli con `UseCaseValidationException`, evitando errori Java impliciti e mantenendo coerente l'error handling applicativo.

## Aggiornamento di contesto dopo il Punto 6J

I primi use case Locations + Cargo + Shipments restano il nucleo iniziale. Gli step successivi hanno esteso lo stesso modello applicativo a Documents, Vehicles e Operational Roles senza cambiare il principio architetturale del Punto 6E.

## Aggiornamento dopo il Punto 6K

Il primo nucleo Locations + Cargo + Shipments rimane invariato. Il Punto 6K opera sui use case Operational Roles, ma conferma lo stesso stile applicativo già avviato nel Punto 6E: command espliciti, service piccoli, repository port astratte, result applicativi e test con adapter in memory.

## Aggiornamento dopo il Punto 6L

Il nucleo iniziale Locations + Cargo + Shipments rimane invariato.

Il Punto 6L estende lo stesso modello applicativo al catalogo base di Compliance: command espliciti, service piccoli, repository port astratte, result applicativi e test con adapter in memory.

Anche in questo step non vengono introdotti REST API, database o framework.

## Allineamento Punto 6M

Il Punto 6M chiude il primo ciclo dell'application layer con una review/freeze finale. Da questo momento i contenuti documentati nei punti 6A-6L sono considerati fondazione applicativa stabile: eventuali evoluzioni future dovranno essere introdotte in nuovi punti roadmap, mantenendo ancora fuori REST API, controller, database, JPA, Spring Data, security, tracking, planning, dashboard, workflow e integrazioni esterne.
