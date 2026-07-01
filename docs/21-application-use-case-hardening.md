# Punto 6F — Application Use Case Review & Hardening

Il **Punto 6F** consolida i primi use case applicativi introdotti nel Punto 6E.

Questa fase non introduce ancora REST API, controller, database, JPA, Spring, security, frontend o integrazioni esterne. L'obiettivo è rendere più robusto il primo blocco applicativo già esistente, verificando che command, service, repository port, repository in memory e risultati applicativi lavorino insieme in modo coerente.

Il Punto 6F è quindi una fase di **review e hardening**, non una fase di espansione enterprise generale.

## Obiettivo dello step

Il Punto 6F serve a verificare che i primi use case applicativi siano abbastanza solidi prima di estendere il modello ad altri domini.

Gli obiettivi sono:

- completare il primo gruppo di azioni applicative sulle shipment;
- rafforzare i test negativi dei service applicativi;
- verificare la distinzione tra errori applicativi ed errori di dominio;
- controllare che le mutazioni fallite non lascino stati parziali;
- documentare cosa è stato coperto e cosa rimane fuori dalle fasi future;
- mantenere separati domain, application e infrastructure memory.

## Use case aggiunto

Il Punto 6F aggiunge un nuovo use case applicativo:

- `CancelShipmentUseCase`.

Il relativo command è:

- `CancelShipmentCommand`.

La relativa implementazione è:

- `CancelShipmentService`.

Questo use case permette di cancellare una shipment esistente usando il metodo di dominio `shipment.cancel()` e salvando poi l'aggregate aggiornato tramite `ShipmentRepository`.

La logica applicativa è semplice:

1. ricevere il command;
2. verificare che il command non sia nullo;
3. caricare la shipment tramite repository port;
4. se la shipment non esiste, sollevare `ResourceNotFoundException`;
5. chiamare `shipment.cancel()`;
6. salvare la shipment aggiornata;
7. restituire `ShipmentResult`.

## Perché è stato aggiunto CancelShipment

Nei documenti precedenti la cancellazione della shipment era già indicata tra le azioni applicative naturali del primo blocco Shipments.

Il Punto 6E aveva introdotto creazione, aggiunta item, aggiunta leg, conferma e ricerca. Il Punto 6F completa questo primo gruppo aggiungendo la cancellazione applicativa.

La cancellazione non introduce workflow avanzato, planning, dispatching o audit. È solo una prima azione applicativa coerente con lo stato `CANCELLED` già presente nel dominio shipment.

## Test aggiunti

È stato aggiunto il test:

- `ApplicationUseCaseHardeningTest`.

Questo test verifica il comportamento dei primi use case in situazioni positive, negative e di protezione dello stato.

I casi coperti sono:

- i service rifiutano command nulli con `UseCaseValidationException`;
- i command rifiutano input applicativi obbligatori mancanti;
- i find use case restituiscono `ResourceNotFoundException` quando la risorsa non esiste;
- la registrazione cargo rifiuta codici duplicati con `DuplicateResourceException`;
- `CancelShipmentUseCase` cancella e persiste una shipment esistente;
- `CancelShipmentUseCase` fallisce se la shipment non esiste;
- una mutazione fallita su shipment item non lascia stato parziale nella shipment salvata.

## Errori applicativi verificati

Il Punto 6F rafforza la distinzione tra errori applicativi e errori di dominio.

Sono errori applicativi:

- command nullo;
- input applicativo obbligatorio mancante;
- location non trovata;
- cargo non trovato;
- shipment non trovata;
- codice business duplicato.

Per questi casi vengono usate:

- `UseCaseValidationException`;
- `ResourceNotFoundException`;
- `DuplicateResourceException`.

Sono invece errori di dominio:

- shipment item con quantità non positiva;
- shipment confermata senza item;
- shipment confermata senza leg;
- shipment incoerente rispetto a temperatura, separazione o requisiti.

Questi errori restano nel domain layer e vengono propagati dall'application layer senza essere mascherati.

## Regola confermata

Il Punto 6F conferma una regola centrale dell'architettura:

> Il dominio decide se una regola di business è valida. L'application layer orchestra il caso d'uso, gestisce risorse mancanti o duplicate e salva il risultato.

Quindi un service applicativo non deve replicare le regole profonde del dominio.

Esempio:

- `AddShipmentItemService` verifica che shipment e cargo esistano;
- crea lo shipment item usando il dominio;
- se la quantità è invalida, il dominio solleva `InvalidShipmentException`;
- il service non converte quell'errore in un errore tecnico o infrastrutturale.

## Mutazioni fallite e stato parziale

Un punto importante del Punto 6F è la verifica delle mutazioni fallite.

Quando un use case prova ad aggiungere uno shipment item non valido, il dominio deve impedire la creazione dell'item e la shipment salvata non deve risultare parzialmente modificata.

Questo protegge il futuro application layer da errori difficili da individuare, come:

- shipment con item parzialmente aggiunti;
- repository aggiornati dopo una validazione fallita;
- result applicativi incoerenti rispetto allo stato reale dell'aggregate.

## Cosa è stato fatto

Con il Punto 6F il progetto ora contiene:

- domain layer puro;
- application foundation;
- repository port;
- repository in memory;
- primi use case per Locations, Cargo e Shipments;
- use case di cancellazione shipment;
- test del primo flusso applicativo completo;
- test di hardening su command, service, errori e mutazioni fallite.

## Cosa manca ancora

Il Punto 6F non introduce ancora:

- use case per Vehicles;
- use case per Documents;
- use case per Compliance;
- use case per Operational Roles;
- workflow documentali avanzati;
- controlli compliance concreti;
- planning e dispatching;
- disponibilità mezzi/autisti;
- assegnazione veicolo/cargo/shipment;
- tracking e monitoraggio;
- REST API;
- database;
- transazioni;
- security e permissions.

Queste parti non vengono aggiunte ora perché il primo obiettivo è stabilizzare il nucleo applicativo già introdotto.

## Stato dopo il Punto 6F

Dopo questo step, TruckFlow Manager ha un primo application layer più robusto.

Il progetto non è ancora una piattaforma enterprise completa e non espone ancora API esterne, ma ora possiede una base applicativa più affidabile per continuare verso i prossimi use case.

## Prossimo step consigliato

Il prossimo step consigliato è:

**Punto 6G — Application Use Cases Expansion**.

In quella fase si potrà decidere se estendere i casi d'uso a:

- Documents;
- Compliance;
- Vehicles;
- Operational Roles;
- altre azioni su Shipments.

Prima di passare a REST API, database o Spring, conviene completare un set minimo ma coerente di use case applicativi.
