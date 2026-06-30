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
