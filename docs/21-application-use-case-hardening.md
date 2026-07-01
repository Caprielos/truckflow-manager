# Punto 6F — Application Use Case Review & Hardening

Il **Punto 6F** consolida i primi use case applicativi introdotti nel Punto 6E.

Questa fase non introduce ancora REST API, controller, database, JPA, Spring, security, frontend o integrazioni esterne. L'obiettivo è rendere più robusto il primo blocco applicativo già esistente, verificando che command, service, repository port, repository in memory e result applicativi lavorino insieme in modo coerente.

Il Punto 6F è quindi una fase di **review e hardening**, non una fase di espansione enterprise generale.

## Obiettivo dello step

Il Punto 6F serve a verificare che i primi use case applicativi siano abbastanza solidi prima di estendere il modello ad altri domini.

Gli obiettivi sono:

- completare il primo gruppo di azioni applicative sulle shipment;
- rafforzare i test negativi dei service applicativi;
- verificare la distinzione tra errori applicativi ed errori di dominio;
- controllare che le mutazioni fallite non lascino stati parziali;
- rafforzare il comportamento dei service che modificano una shipment;
- verificare la consistenza dei `result` rispetto allo stato persistito;
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

La logica applicativa è:

1. ricevere il command;
2. verificare che il command non sia nullo;
3. caricare la shipment tramite repository port;
4. se la shipment non esiste, sollevare `ResourceNotFoundException`;
5. creare una copia applicativa della shipment caricata;
6. chiamare `cancel()` sulla copia;
7. salvare la shipment aggiornata solo dopo il completamento della mutazione;
8. restituire `ShipmentResult`.

## Perché è stato aggiunto CancelShipment

Nei documenti precedenti la cancellazione della shipment era già indicata tra le azioni applicative naturali del primo blocco Shipments.

Il Punto 6E aveva introdotto creazione, aggiunta item, aggiunta leg, conferma e ricerca. Il Punto 6F completa questo primo gruppo aggiungendo la cancellazione applicativa.

La cancellazione non introduce workflow avanzato, planning, dispatching o audit. È solo una prima azione applicativa coerente con lo stato `CANCELLED` già presente nel dominio shipment.

## Hardening delle mutazioni shipment

Il Punto 6F rafforza anche i service che modificano una shipment già esistente:

- `AddShipmentItemService`;
- `AddShipmentLegService`;
- `ConfirmShipmentService`;
- `CancelShipmentService`.

Questi service ora usano un helper interno al package shipment application:

- `ShipmentMutationSupport`.

`ShipmentMutationSupport.copyOf(...)` crea una copia dell'aggregate caricato dal repository. La mutazione viene applicata alla copia e la copia viene salvata solo se tutte le validazioni di dominio sono passate.

Questa scelta è importante perché le repository in memory conservano aggregate mutabili. Senza questa protezione, un service potrebbe modificare l'oggetto caricato e lasciare uno stato parziale nel repository anche quando una validazione successiva fallisce.

La regola applicativa diventa:

> caricare l'aggregate, copiare l'aggregate, mutare la copia, salvare solo la copia valida.

Questo non sostituisce transazioni, unit of work o persistenza enterprise. È un hardening locale coerente con lo stato attuale del progetto.

## Test aggiunti e rafforzati

È stato rafforzato il test:

- `ApplicationUseCaseHardeningTest`.

Questo test verifica il comportamento dei primi use case in situazioni positive, negative e di protezione dello stato.

I casi coperti sono:

- tutti i service rifiutano command nulli con `UseCaseValidationException`;
- i service rifiutano repository dependency nulle in fase di costruzione;
- i command rifiutano input applicativi obbligatori mancanti;
- i find use case restituiscono `ResourceNotFoundException` quando la risorsa non esiste;
- le registrazioni di location, cargo e shipment rifiutano codici business duplicati con `DuplicateResourceException`;
- i use case di mutazione shipment falliscono con `ResourceNotFoundException` quando mancano shipment, cargo o location necessarie;
- `CancelShipmentUseCase` cancella e persiste una shipment esistente;
- una mutazione fallita su shipment item non lascia stato parziale;
- una mutazione fallita su shipment leg non lascia stato parziale;
- una conferma fallita non modifica lo stato della shipment persistita.

## Errori applicativi verificati

Il Punto 6F rafforza la distinzione tra errori applicativi e errori di dominio.

Sono errori applicativi:

- command nullo;
- dipendenza repository nulla nella costruzione del service;
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
- shipment leg con sequenza non valida;
- shipment leg con sequenza duplicata;
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
- crea una copia della shipment caricata;
- crea lo shipment item usando il dominio;
- se la quantità è invalida, il dominio solleva `InvalidShipmentException`;
- il service non converte quell'errore in un errore tecnico o infrastrutturale;
- la repository non viene aggiornata con uno stato parziale.

## Mutazioni fallite e stato parziale

Un punto importante del Punto 6F è la verifica delle mutazioni fallite.

Quando un use case prova ad aggiungere uno shipment item non valido, aggiungere una shipment leg con sequenza duplicata o confermare una shipment incompleta, il dominio deve impedire l'operazione e la shipment salvata deve restare nello stato precedente.

Questo protegge il futuro application layer da errori difficili da individuare, come:

- shipment con item parzialmente aggiunti;
- shipment con leg parzialmente aggiunte;
- shipment marcate come confermate dopo una conferma fallita;
- repository aggiornati dopo una validazione fallita;
- result applicativi incoerenti rispetto allo stato reale dell'aggregate.

## Confini architetturali confermati

Il Punto 6F mantiene invariati i confini già stabiliti:

- il domain layer non importa application o infrastructure;
- l'application layer non importa framework, web adapter o repository concrete;
- l'application layer dipende solo da port e dominio;
- `infrastructure.memory` implementa le repository port;
- non vengono introdotti controller, DTO web, database o annotazioni di persistenza.

## Cosa è stato fatto

Con il Punto 6F il progetto ora contiene:

- domain layer puro;
- application foundation;
- repository port;
- repository in memory;
- primi use case per Locations, Cargo e Shipments;
- use case di cancellazione shipment;
- hardening copy-on-write dei service di mutazione shipment;
- test del primo flusso applicativo completo;
- test di hardening su command, service, errori, repository mancanti, risorse mancanti, duplicati e mutazioni fallite.

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
- transazioni enterprise;
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

## Aggiornamento successivo — Punto 6G completato

Dopo il Punto 6F è stato applicato il Punto 6G — Application Use Cases Expansion.

L'espansione non ha modificato il significato del 6F: il nucleo Locations + Cargo + Shipments resta stabile e hardenizzato. Il Punto 6G aggiunge invece un nuovo blocco applicativo separato per `documents`, mantenendo gli stessi principi di validazione, error handling, repository usage, test e documentazione.

La nuova documentazione di riferimento è:

```text
docs/22-application-use-case-expansion.md
```

Il prossimo step consigliato non è ancora REST API o database, ma una review del nuovo blocco applicativo Documents nel Punto 6H.

## Aggiornamento dopo il Punto 6H

Il Punto 6H estende l'idea di hardening applicativo anche dopo l'espansione Documents.

Non cambia le mutazioni shipment già rafforzate nel Punto 6F, ma aggiunge una review trasversale su port, service, result e repository in memory. Inoltre conferma che anche le mutazioni documentali introdotte nel Punto 6G seguono l'approccio copy-on-write.

## Aggiornamento di contesto dopo il Punto 6J

Le regole di hardening introdotte nel Punto 6F rimangono valide anche per Documents, Vehicles e Operational Roles: command nulli rifiutati, repository dependency nulle rifiutate, errori applicativi distinti dagli errori di dominio e mutazioni protette tramite copy-on-write dove gli aggregate sono mutabili.

## Aggiornamento dopo il Punto 6K

Il principio di hardening introdotto nel Punto 6F viene confermato anche per Operational Roles nel Punto 6K: le mutazioni applicative devono evitare stati parziali quando una validazione di dominio fallisce.

Per questo i service Operational Roles continuano a usare mutation support copy-on-write prima del salvataggio.
