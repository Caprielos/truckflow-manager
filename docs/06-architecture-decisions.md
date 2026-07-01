# 6. Decisioni architetturali del dominio

## 6.1 Decisione: dominio puro prima di infrastruttura

Il progetto è stato costruito partendo dal dominio puro.

Questa scelta permette di definire prima il linguaggio del business e solo dopo collegare database, API, servizi esterni e interfacce.

Il dominio non deve sapere come viene salvato, esposto o serializzato.

## 6.2 Decisione: separare User da Operational

`User` rappresenta l’account applicativo.

Le figure operative rappresentano persone reali nel contesto aziendale.

Questa distinzione evita che il login diventi il centro del dominio operativo.

Un account può essere collegato a un autista, a un dispatcher o a un manager tramite `UserId`, ma non ingloba quei concetti.

## 6.3 Decisione: usare Qualification come catalogo unico

Patenti, CQC, ADR, ATP, HACCP e corsi interni sono tutti trattati come qualificazioni.

Non è stato creato un `LicenseType` separato perché avrebbe duplicato il concetto di abilitazione.

Il catalogo dice cosa esiste. Il dominio operativo dice chi possiede cosa.

## 6.4 Decisione: niente scadenze nel catalogo

Una qualificazione del catalogo non scade.

Scade il documento o l’abilitazione posseduta da una persona.

Per questo il catalogo non contiene date, file o validità.

## 6.5 Decisione: usare OperationalCode e FleetCode

Sono stati introdotti codici aziendali leggibili:

- `OperationalCode` per le figure operative;
- `FleetCode` per i veicoli.

Questi codici sono diversi dagli ID tecnici del dominio.

Gli ID servono al sistema. I codici servono agli utenti aziendali.

## 6.6 Decisione: VehicleUnit e VehicleCombination

Il dominio veicoli distingue tra singola unità e combinazione.

Questa scelta è fondamentale per il trasporto reale.

Un bilico non è un veicolo singolo, ma una combinazione tra trattore e semirimorchio.

Un autotreno non è una motrice, ma una combinazione tra motrice e rimorchio.

## 6.7 Decisione: allestimento separato dal tipo unità

`VehicleUnitType` descrive la natura fisica del mezzo.

`VehicleBodyType` descrive l’allestimento.

Quindi frigo, cisterna, bisarca e centinato non sono tipi principali di veicolo, ma allestimenti.

Questa scelta evita enum confusionari e migliora la scalabilità.

## 6.8 Decisione: profili specifici invece di Map generica

Per gli allestimenti speciali non è stata usata una mappa generica.

Sono stati creati profili specifici, come:

- `RefrigeratedBodyProfile`;
- `TankBodyProfile`;
- `CarCarrierBodyProfile`.

Questo rende il dominio più esplicito e controllato.

## 6.9 Decisione: assi come lista reale

La specifica assi usa una lista di `VehicleAxle`.

Ogni asse può essere:

- sterzante;
- sollevabile;
- gemellato.

Questa scelta è più realistica rispetto a un semplice numero totale di assi.

## 6.10 Decisione: stato tecnico separato dalla disponibilità

`VehicleStatus` indica stato tecnico/anagrafico:

- attivo;
- sospeso;
- fuori servizio;
- dismesso.

Non indica se il mezzo è disponibile oggi.

Disponibilità, prenotazione e assegnazione viaggio appartengono a moduli futuri.

## 6.11 Decisione: riferimenti tramite ID tra aggregati

Quando un concetto appartiene a un altro aggregato, viene usato il suo ID.

Esempi:

- una figura operativa contiene `UserId`;
- una combinazione veicolare contiene `VehicleUnitId`.

Questa scelta riduce l’accoppiamento e rende il dominio più modulare.


## 6.12 Decisione: dividere `domain.vehicles` in sottopackage per concetto

Il dominio veicoli è stato riorganizzato in sottopackage perché era diventato il package più ricco del progetto.

La divisione scelta è per concetto di dominio:

- `unit` per le unità fisiche;
- `combination` per i complessi veicolari;
- `coupling` per agganci e traino;
- `specification` per la scheda tecnica;
- `body` per allestimenti e profili;
- `operation` per capacità e ruoli operativi;
- `common` per validazioni comuni.

Non è stata scelta una divisione per tipo di mezzo, come `truck`, `trailer` o `van`, perché avrebbe duplicato concetti condivisi. Per esempio, assi, pesi, pneumatici, capacità, allestimenti e agganci sono concetti trasversali.

Questa scelta rende il dominio più leggibile e più facile da estendere senza perdere coerenza.


## 6.13 Decisione: separare cargo, veicoli e compatibilità

Il dominio cargo descrive la merce e i requisiti che la merce impone al trasporto.

Il dominio veicoli descrive i mezzi, le combinazioni, gli allestimenti e le capacità tecniche.

La compatibilità tra cargo e veicolo non viene implementata dentro `domain.cargo` e non viene implementata dentro `domain.vehicles`.

La regola architetturale è:

```text
Cargo dichiara requisiti.
Vehicles dichiara capacità.
Planning/Dispatching verifica la compatibilità.
```

Questa scelta evita dipendenze dirette tra bounded context e impedisce al dominio cargo di copiare concetti del dominio veicoli.

## 6.14 Decisione: `CargoStatus` è anagrafico, non operativo

`CargoStatus` non rappresenta stati di viaggio o consegna.

Per questo sono stati usati stati come:

- `ACTIVE`;
- `SUSPENDED`;
- `ARCHIVED`;
- `DISCONTINUED`.

Non sono stati del cargo puro:

- `IN_TRANSIT`;
- `DELIVERED`;
- `DAMAGED`;
- `CANCELLED`.

Questi ultimi appartengono a spedizioni, consegne, tracking, incidenti o reclami.

## 6.15 Decisione: requisiti cargo come set, non come tanti booleani

I requisiti di trasporto della merce sono stati modellati tramite `Set<CargoTransportRequirement>` dentro `CargoCompatibilityRequirement`.

Questa scelta è più scalabile di una lista di booleani come `requiresADR`, `requiresRefrigerated`, `requiresCarCarrier`, ecc.

Se in futuro verrà aggiunto un nuovo requisito, sarà sufficiente aggiungere un nuovo valore al catalogo dei requisiti, senza gonfiare la struttura principale della merce.


## 6.16 Decisione: separare Location da TripTemplate

Le location sono state modellate in `domain.locations` e non dentro `domain.triptemplates`.

Questa scelta evita di trattare i luoghi come semplici dettagli di un percorso. Un deposito, un hub, un cliente, un porto o uno yard sono concetti riutilizzabili da molti domini futuri: spedizioni, magazzino, clienti, fornitori, tracking, documenti e pianificazione.

Per questo `TripTemplateSegment` non contiene oggetti `Location` completi, ma solo:

```text
originLocationId
destinationLocationId
```

La relazione tramite ID riduce l'accoppiamento tra aggregati e segue la stessa regola già usata per `UserId` nelle figure operative e `VehicleUnitId` nelle combinazioni veicolari.

## 6.17 Decisione: usare TripTemplate invece di Trip

Il nome `TripTemplate` è stato scelto per evitare confusione con il viaggio reale operativo.

Un trip reale futuro potrà avere autista, veicolo, cargo, orari, tracking, stato di esecuzione, documenti e costi.

Il `TripTemplate`, invece, descrive solo la struttura astratta del percorso:

- codice;
- nome;
- tipo;
- stato anagrafico;
- segmenti ordinati;
- specifica descrittiva della rotta.

Questa distinzione mantiene il dominio puro e impedisce di introdurre pianificazione troppo presto.

## 6.18 Decisione: segmenti come tratte, non come soste

Un `TripTemplateSegment` rappresenta una tratta tra due location.

La sosta non è il segmento: la sosta è una `Location`.

Esempio:

```text
Segment 1: Milano Depot → Bologna Hub
Segment 2: Bologna Hub → Firenze Yard
Segment 3: Firenze Yard → Roma Depot
```

Questa scelta permette di modellare soste multiple in modo semplice, ordinato e coerente.

## 6.19 Decisione: Yard come LocationType

Il concetto di yard è stato modellato come `LocationType.YARD`.

Uno yard rappresenta piazzali, aree di scambio rimorchi, parcheggi mezzi, zone di staging, aree di pre-carico e pre-scarico.

È importante per TruckFlow perché molte operazioni logistiche non sono viaggi stradali completi, ma movimenti interni o tecnici.

Per questo `domain.triptemplates` supporta anche `TripTemplateType.YARD_MOVEMENT` e `TripTemplateSegmentType.YARD_MOVEMENT`, restando però nel dominio puro: nessuna assegnazione, nessun tracking, nessuna esecuzione reale.

## 6.20 Cosa potrà essere aggiunto in futuro

Il dominio attuale è una base.

In futuro si potranno aggiungere:

- application layer;
- repository port;
- persistence infrastructure;
- REST API;
- pianificazione viaggi;
- disponibilità risorse;
- manutenzione;
- scadenze;
- file storage e workflow documentale;
- audit trail;
- dashboard KPI;
- gestione costi;
- telematica;
- tachigrafo;
- integrazioni esterne.

Queste estensioni dovranno rispettare la separazione già definita.

## 6.21 Conclusione

Le scelte fatte rendono TruckFlow Manager più ordinato e più vicino a un gestionale enterprise reale.

Il dominio attuale non prova a fare tutto subito. Descrive bene le fondamenta:

- chi accede;
- quali abilitazioni esistono;
- quali persone operative lavorano in azienda;
- quali mezzi compongono il parco veicoli;
- come si collegano unità e combinazioni;
- quali merci esistono e quali requisiti di trasporto impongono;
- quali luoghi logistici esistono e come sono classificati;
- quali percorsi tipo esistono e quali tratte li compongono.

Questa base permette di crescere senza dover riscrivere i concetti fondamentali.

## 6.20 Decisione: introdurre `domain.shipments` come richiesta di spedizione, non come esecuzione

Il dominio shipments è stato introdotto per rappresentare la richiesta di spedizione.

Una shipment descrive:

- cosa deve essere spedito;
- quali cargo compongono la spedizione;
- da quali location parte e verso quali location arriva;
- quali tratte logiche sono richieste;
- quali requisiti di trasporto devono essere rispettati;
- quale priorità e quale livello di servizio sono richiesti.

Non rappresenta però il viaggio reale operativo.

Per questo `domain.shipments` non contiene:

- veicoli assegnati;
- autisti assegnati;
- orari reali;
- tracking;
- prove di consegna;
- costi reali;
- documenti operativi;
- stato in viaggio.

Questa scelta mantiene separati il concetto di richiesta di spedizione e quello di esecuzione reale del trasporto.

## 6.21 Decisione: usare `CargoId` e `LocationId` dentro Shipment

`ShipmentItem` non contiene un oggetto `CargoUnit` completo, ma solo `CargoId`.

`ShipmentLeg` non contiene oggetti `Location` completi, ma solo:

```text
originLocationId
destinationLocationId
```

Questa scelta segue la regola già applicata nel resto del dominio: quando un aggregato deve riferirsi a un altro aggregato, usa il suo ID e non l'oggetto completo.

Il risultato è un dominio meno accoppiato e più facile da evolvere.

## 6.22 Decisione: `ShipmentStatus` non è stato operativo di tracking

Gli stati di `ShipmentStatus` rappresentano la vita della richiesta di spedizione:

- `DRAFT`;
- `REGISTERED`;
- `CONFIRMED`;
- `SUSPENDED`;
- `CANCELLED`;
- `ARCHIVED`.

Non sono stati della shipment pura:

- `IN_TRANSIT`;
- `DELIVERED`;
- `FAILED`;
- `DAMAGED`;
- `DELAYED`.

Questi stati appartengono alla futura esecuzione del trasporto, al tracking, ai reclami o agli incidenti.

## 6.23 Decisione: requisiti shipment come set, non come booleani

Come già fatto per `domain.cargo`, anche per `domain.shipments` i requisiti di trasporto sono stati modellati come set.

`ShipmentRequirementSet` contiene un `Set<ShipmentTransportRequirement>`.

Questa scelta è più scalabile rispetto a una classe con molti booleani come `adrRequired`, `atpRequired`, `foodGradeRequired`, ecc.

Se in futuro nasceranno nuovi requisiti, sarà sufficiente aggiungere un valore a `ShipmentTransportRequirement`, senza rendere la struttura principale sempre più grande.

## 6.24 Decisione: priorità e livello di servizio non sono pianificazione

`ShipmentPriority` e `ShipmentServiceLevel` sono stati aggiunti perché sono informazioni realistiche in una piattaforma enterprise.

Tuttavia non pianificano nulla.

Una shipment urgente o time-critical non assegna automaticamente un mezzo, un autista o una finestra temporale.

Queste informazioni saranno usate più avanti dai moduli di planning e dispatching, ma nel dominio puro restano solo caratteristiche dichiarate della richiesta.

## 6.25 Decisione: organizzare `domain.shipments` in sottopackage senza spezzare l'aggregate

Il dominio `domain.shipments` è stato riorganizzato in sottopackage tematici:

```text
domain.shipments.core
domain.shipments.items
domain.shipments.legs
domain.shipments.requirements
domain.shipments.metrics
domain.shipments.properties
domain.shipments.notes
domain.shipments.references
```

Questa scelta è stata fatta per migliorare la leggibilità del codice, perché il dominio shipment contiene molte classi tra aggregate root, entity interne e value object.

La decisione architetturale importante è che questa divisione **non crea nuovi aggregate**.

`Shipment` rimane l'unico aggregate root.

Gli altri elementi rimangono interni all'aggregate:

- `ShipmentItem` e `ShipmentLeg` sono entity interne della shipment;
- `ShipmentRequirementSet`, `ShipmentMetrics`, `ShipmentProperties`, `ShipmentTemperature`, `ShipmentReferences` e `ShipmentNotes` sono value object della shipment;
- i sottopackage servono solo per organizzare il codice.

Di conseguenza, in futuro non dovranno nascere repository separati come:

```text
ShipmentItemRepository
ShipmentLegRepository
ShipmentMetricsRepository
```

Il repository corretto sarà concettualmente uno solo:

```text
ShipmentRepository
```

Questa scelta mantiene il modello DDD pulito: organizzazione interna più leggibile, ma confine dell'aggregate invariato.

## 6.26 Decisione: introdurre `domain.documents` come concetto puro di documento aziendale

Il dominio documents è stato introdotto per rappresentare il documento aziendale come concetto astratto e riusabile.

Un `Document` descrive:

- identità tecnica;
- codice aziendale leggibile;
- tipo documento;
- categoria;
- stato astratto;
- metadati;
- contenuto logico opzionale;
- riferimenti astratti verso altri domini.

Non rappresenta un file fisico.

Per questo `domain.documents` non contiene:

- PDF;
- upload;
- download;
- path filesystem;
- URL;
- chiavi di storage;
- firma digitale;
- scadenze;
- workflow approvativi;
- compliance operativa.

Questa scelta mantiene il dominio puro e lascia file storage, versioning, firme, scadenze e workflow a moduli futuri.

## 6.27 Decisione: `DocumentReference` generico e non accoppiato agli altri domini

`domain.documents` deve poter riferirsi a veicoli, persone operative, cargo, shipment, location e trip template senza dipendere dalle loro classi Java.

Per questo `DocumentReference` usa:

```text
DocumentReferenceType referenceType
String referencedId
```

Il dominio documents non importa direttamente:

- `VehicleUnitId`;
- `CargoId`;
- `ShipmentId`;
- `LocationId`;
- `TripTemplateId`;
- `DriverId`.

Questa scelta è più disaccoppiata rispetto ai riferimenti tipizzati e mantiene il bounded context documents riusabile da tutto TruckFlow.

## 6.28 Decisione: stato documento astratto, non workflow

`DocumentStatus` rappresenta solo lo stato astratto del documento nel dominio puro:

- `DRAFT`;
- `ACTIVE`;
- `SUSPENDED`;
- `ARCHIVED`.

Non sono stati del dominio documents puro:

- `SIGNED`;
- `EXPIRED`;
- `VALIDATED`;
- `REJECTED`;
- `PENDING_APPROVAL`.

Questi stati appartengono a firma digitale, compliance, scadenze o workflow futuri, non al concetto base di documento.


## 6.29 Decisione: introdurre `domain.compliance` come catalogo dei requisiti astratti

Il dominio compliance è stato introdotto per rappresentare i requisiti astratti di conformità di TruckFlow.

Un `ComplianceRequirement` descrive:

- identità tecnica;
- codice aziendale leggibile;
- nome e descrizione;
- stato anagrafico;
- categoria;
- tipo di requisito;
- livello di obbligatorietà;
- severità;
- target astratto;
- regola descrittiva;
- fonte;
- giurisdizione.

Il dominio non esegue controlli concreti. Non dice se una shipment reale è conforme, se un documento è scaduto o se un veicolo può partire. Descrive solo che un requisito esiste.

Le verifiche concrete arriveranno più avanti in application layer, planning, dispatching, audit o moduli di compliance check.

## 6.30 Decisione: separare obbligatorietà e severità

Nel dominio compliance, obbligatorietà e severità sono concetti diversi.

`ComplianceObligationLevel` descrive se un requisito è:

- `MANDATORY`;
- `REQUIRED`;
- `RECOMMENDED`;
- `OPTIONAL`.

`ComplianceSeverity` descrive la gravità del mancato rispetto:

- `LOW`;
- `MEDIUM`;
- `HIGH`;
- `CRITICAL`.

Questa separazione permette di rappresentare correttamente casi come:

```text
obligationLevel = MANDATORY
severity = CRITICAL
```

oppure:

```text
obligationLevel = REQUIRED
severity = MEDIUM
```

`MANDATORY` indica un obbligo forte, legale, normativo o aziendale non derogabile. `REQUIRED` indica un requisito richiesto da processo, cliente o contratto.

## 6.31 Decisione: usare `ComplianceTarget`, non `ComplianceReference`

Il dominio compliance deve descrivere requisiti generali, non applicazioni concrete a singole entità.

Per questo è stato introdotto `ComplianceTarget`, che indica a quale tipo di dominio si applica il requisito:

```text
VEHICLE
OPERATIONAL
CARGO
SHIPMENT
LOCATION
TRIP_TEMPLATE
DOCUMENT
GENERIC
OTHER
```

Non è stato introdotto `ComplianceReference` con `referencedId`, perché quello collegherebbe il requisito a un caso concreto come `VEH-001` o `SHP-001`.

Il collegamento concreto tra requisito e istanza reale verrà modellato più avanti in moduli di compliance check, planning, dispatching o audit.

## 6.32 Decisione: regole descrittive, non eseguibili

`ComplianceRule` è una regola descrittiva.

Non contiene:

- funzioni eseguibili;
- predicati;
- metodi `check`;
- validatori operativi;
- logica di confronto con veicoli, cargo, shipment o documenti.

Questa scelta mantiene il dominio puro. La regola spiega cosa deve essere rispettato, mentre la verifica concreta sarà responsabilità di servizi applicativi futuri.

## 6.33 Decisione: fonte e giurisdizione del requisito

Ogni requisito di compliance può avere una fonte e una giurisdizione.

`ComplianceSource` indica da dove nasce il requisito: norma europea, regolamento nazionale, policy interna, richiesta cliente, requisito contrattuale, standard di sicurezza o altra fonte.

`ComplianceJurisdiction` indica l'ambito concettuale in cui il requisito vale, per esempio Italia, Unione Europea, ambito internazionale, policy interna aziendale o requisito specifico cliente.

La giurisdizione è stata rafforzata con tre concetti espliciti:

- `CountryCode`, per il paese specifico, per esempio `IT`, `FR` o `DE`;
- `JurisdictionRegion`, per un'area geografica o normativa ampia, per esempio `EU` o `EMEA`;
- `ComplianceJurisdictionScope`, per il livello della giurisdizione, per esempio `NATIONAL`, `EUROPEAN_UNION` o `COMPANY_INTERNAL`.

Questi concetti non applicano automaticamente la legge e non gestiscono date di validità. Servono solo a descrivere il requisito in modo enterprise e scalabile.

La logica di selezione futura, come "Europa -> lista nazioni -> Italia come default", appartiene ad application layer, configurazione o interfaccia utente, non al dominio puro.

## 6.34 Decisione: formalizzare TruckFlow Domain Foundation v1.0

La fondazione del dominio puro viene formalizzata come **TruckFlow Domain Foundation v1.0**.

Questa decisione non indica che tutta la piattaforma sia enterprise ready. Indica che il domain layer puro ha una base stabile, documentata e pronta per una review concreta dominio per dominio.

La Domain Review Finale è quindi considerata avviata e la roadmap è approvata, ma non ancora completata.

## 6.35 Decisione: introdurre eccezioni custom gradualmente

Sono state definite eccezioni base condivise in `it.gabriele.truckflow.domain.shared.exceptions`:

- `DomainException`;
- `DomainValidationException`;
- `InvariantViolationException`.

Sono state inoltre definite eccezioni specifiche nei package `exceptions` dei singoli domini.

Questa introduzione non obbliga a sostituire immediatamente tutte le eccezioni standard Java già presenti. Durante la fase MVP sono ancora accettabili `IllegalArgumentException` e `IllegalStateException`, purché siano localizzate e coerenti.

Il refactoring verso eccezioni custom deve avvenire gradualmente, dominio per dominio, aggiornando i test a ogni passaggio.

## 6.36 Decisione: non introdurre `ComplianceViolationException` in questa fase

`ComplianceViolationException` non viene introdotta nel dominio puro attuale.

Il dominio compliance modella requisiti astratti tramite `ComplianceRequirement`. Una violazione di compliance, invece, rappresenta un risultato concreto di un controllo eseguito su una shipment, un veicolo, un documento, un cargo o un'altra istanza reale.

Le violazioni, i check, gli audit, le approvazioni e i risultati di verifica saranno modellati in una fase successiva, dentro application layer o moduli dedicati di compliance check, planning, dispatching e audit.

## 6.37 Decisione: validare prima di mutare gli aggregate

Durante la prima review correttiva è stata formalizzata una regola importante: un aggregate deve validare il nuovo stato prima di modificare i propri campi interni.

La sequenza corretta è:

```text
1. calcolare i nuovi valori
2. validare i nuovi valori
3. verificare la coerenza complessiva
4. assegnare i campi interni
```

Questa scelta evita stati parzialmente modificati quando una validazione fallisce.

## 6.38 Decisione: usare eccezioni custom nei domini

Le eccezioni custom definite nella Domain Foundation vengono usate nei domini al posto delle eccezioni standard Java per rappresentare violazioni di validazione e invarianti.

Questa scelta rende il modello più leggibile e prepara il livello application a gestire errori di dominio in modo chiaro.

## 6.39 Decisione: rendere `OperationalCode` obbligatorio

`OperationalCode` viene trattato come codice aziendale obbligatorio per le figure operative.

Questa scelta lo allinea a `FleetCode`, `CargoCode`, `ShipmentCode`, `DocumentCode` e `ComplianceRequirementCode`.

Un codice operativo leggibile è utile per ricerca, reportistica, audit, comunicazione interna e integrazioni future.

## 6.40 Decisione: testare i cataloghi per coerenza, non per conteggi fragili

I test dei cataloghi statici non devono bloccare l'evoluzione del dominio con conteggi rigidi non necessari.

Devono invece verificare proprietà di qualità:

- codici univoci;
- ricerca per codice;
- metadati completi;
- categorie coerenti;
- presenza degli elementi fondamentali.

Questa scelta permette al catalogo qualificazioni di crescere senza rendere fragili i test.

## 6.41 Decisione: escludere file locali e artefatti generati dal repository

Il repository deve contenere codice sorgente, test, configurazioni condivise e documentazione ufficiale.

File locali dell'IDE, artefatti Maven, file macOS, patch temporanee e script locali non rappresentano il dominio e non devono essere versionati.

Questa scelta mantiene il repository pulito, riproducibile e più adatto al lavoro futuro su più ambienti.

## 6.42 Decisione: documentare gli interventi correttivi della review

Gli interventi della prima review concreta del dominio puro sono documentati in `docs/14-domain-review-patches.md`.

Il documento non descrive procedure tecniche di applicazione, ma spiega cosa è stato cambiato e perché la modifica migliora la qualità del dominio.


## 6.43 Decisione: modellare `LicensePlate` come Value Object della `VehicleUnit`

La targa non deve essere rappresentata come una semplice `String`.

Nel dominio `domain.vehicles`, la targa è un identificatore ufficiale dell'unità fisica e possiede semantica, regole di normalizzazione, validazione e invarianti propri.

Per questo viene introdotto il value object `LicensePlate`.

La targa appartiene alla singola `VehicleUnit`, non alla `VehicleCombination`.

Questo significa che:

- una `VehicleUnit` di tipo `TRACTOR_UNIT` deve avere una propria `LicensePlate`;
- una `VehicleUnit` di tipo `SEMI_TRAILER` deve avere una propria `LicensePlate`;
- una `VehicleUnit` di tipo `RIGID_TRUCK` deve avere una propria `LicensePlate`;
- una `VehicleUnit` di tipo `DRAWBAR_TRAILER` o `CENTER_AXLE_TRAILER` deve avere una propria `LicensePlate`;
- una `VehicleCombination` di tipo bilico, autotreno o furgone con rimorchio non possiede una targa propria, perché è una struttura logica composta da unità fisiche.

Le unità non stradali, come `WAREHOUSE_EQUIPMENT`, possono non avere una targa stradale e possono essere identificate tramite `FleetCode` o futuri identificativi interni.

`SPECIAL_VEHICLE` deve essere valutato caso per caso in una fase successiva, perché può rappresentare sia mezzi stradali immatricolati sia mezzi tecnici non stradali.

## 6.44 Decisione: modellare `VehicleIdentificationNumber` come Value Object

Anche VIN, numero telaio o identificativo tecnico del mezzo non devono rimanere semplici stringhe.

Per questo viene introdotto `VehicleIdentificationNumber`, un value object dedicato alla rappresentazione dell'identificativo tecnico della singola `VehicleUnit`.

Questa scelta evita primitive obsession e rende il dominio veicoli più coerente con gli altri identificativi già modellati come value object, come `FleetCode`, `CargoCode`, `ShipmentCode`, `DocumentCode` e `ComplianceRequirementCode`.

## 6.44 Decisione: rafforzare giurisdizione, scope operativi e preferenze utente

Durante la pulizia finale del dominio puro sono stati rafforzati alcuni concetti che erano ancora rappresentati da stringhe libere.

Nel dominio users, `UserPreferences` usa ora `LanguageCode` e `UserTheme` per evitare che lingua e tema siano valori arbitrari.

Nel dominio compliance, `ComplianceJurisdiction` usa ora `CountryCode`, `JurisdictionRegion` e `ComplianceJurisdictionScope` per separare paese, area geografica o normativa e livello della giurisdizione.

Nel dominio operational, `OperationalScope` usa ora `OperationalScopeCode` per rappresentare il codice dello scope operativo.

Queste modifiche non introducono logica applicativa, UI, default aziendali o configurazioni dinamiche. Rafforzano solo il linguaggio del dominio e riducono primitive obsession.

## Decisione architetturale — Application Layer prima di Web e Database

Dopo il dominio puro, TruckFlow Manager introduce l'application layer come secondo livello architetturale.

La decisione è costruire prima use case, command, result, port in, port out, repository astratti e repository in memory, rimandando controller REST, JPA, database, sicurezza avanzata e integrazioni.

Questa scelta mantiene il dominio indipendente, evita controller che manipolano direttamente gli aggregate e prepara una Clean Architecture ordinata:

```text
web / api / jobs
        ↓
application
        ↓
domain
        ↑
infrastructure implementa le port out definite dall'application layer
```

L'application layer potrà dipendere dal dominio. Il dominio non potrà dipendere dall'application layer. L'infrastructure potrà implementare le porte definite dall'application layer.

## Decisione architetturale — Application Foundation prima dei use case concreti

Dopo il blueprint del Punto 6A, TruckFlow Manager introduce il Punto 6B: Application Foundation.

La foundation applicativa crea package, contratti base ed eccezioni applicative prima di implementare use case specifici. Questa scelta evita di introdurre `RegisterLocationUseCase`, `CreateShipmentUseCase` o repository specifici senza una struttura comune.

Le regole sono:

- l'application layer può dipendere dal dominio;
- il dominio non può dipendere dall'application layer;
- l'application layer non deve dipendere da Spring, JPA, web, database o infrastructure concreta;
- gli errori applicativi sono separati dagli errori di dominio;
- i repository concreti arriveranno in infrastructure, non nel dominio e non nella foundation.

Il Punto 6B non implementa ancora casi d'uso completi. Prepara la base tecnica per il Punto 6C, dedicato alle prime repository port specifiche.


## Decisione architetturale — Repository port prima delle implementazioni

Con il Punto 6C il progetto introduce le prime repository port dell'application layer prima di creare repository in memory, database o adapter tecnici.

Questa scelta conferma la direzione architetturale del progetto:

```text
application -> domain
infrastructure -> application
web -> application
```

Le porte `LocationRepository`, `CargoUnitRepository` e `ShipmentRepository` appartengono ad `application.port.out` e sono contratti astratti. Possono importare aggregate, ID e code del dominio perché devono esprimere ciò che i futuri use case richiedono al mondo esterno.

Non possono invece dipendere da Spring, JPA, Hibernate, Lombok, web, infrastructure concreta o database.

Il vantaggio è che i futuri use case potranno essere scritti e testati contro contratti stabili, mentre le implementazioni potranno cambiare: in memory, file, database, API o altro.
