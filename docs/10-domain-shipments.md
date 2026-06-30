# 10. Dominio `domain.shipments`

## 10.1 Obiettivo del dominio shipments

Il dominio `domain.shipments` rappresenta la **richiesta di spedizione**: cosa deve essere spedito, quali merci compongono la spedizione, da quali luoghi parte, verso quali luoghi arriva e quali requisiti devono essere rispettati.

Una `Shipment` non è una semplice anagrafica statica come `CargoUnit`, ma non è nemmeno l'esecuzione reale di un viaggio. È il concetto intermedio che descrive una spedizione da registrare, confermare e poi passare in futuro ai moduli di planning e dispatching.

La distinzione fondamentale è:

```text
Cargo = scheda della merce
Shipment = cosa devo spedire e tra quali luoghi
TripTemplate = modello astratto di percorso
Vehicles = mezzi e capacità tecniche
Planning / Dispatching = assegnazione reale di veicoli, autisti, orari e risorse
```

Il dominio shipments descrive la richiesta di spedizione, ma non decide ancora come eseguirla.

## 10.2 Cosa descrive una Shipment

Una `Shipment` descrive:

- la spedizione richiesta;
- il codice aziendale leggibile;
- il nome e la descrizione;
- lo stato della richiesta;
- la priorità;
- il livello di servizio richiesto;
- gli item di merce inclusi nella spedizione;
- le tratte logiche della spedizione;
- le proprietà generali della spedizione;
- i requisiti termici;
- i requisiti di trasporto dichiarati;
- le metriche dichiarate o riepilogative;
- i riferimenti cliente, fornitore e interni;
- le note interne ed esterne.

Non descrive invece l'esecuzione reale del trasporto.

## 10.3 Cosa non deve contenere `domain.shipments`

Il dominio shipments non contiene:

- veicolo assegnato;
- autista assegnato;
- orari reali;
- partenza effettiva;
- arrivo effettivo;
- tracking GPS;
- stato in viaggio;
- ritardi;
- prova di consegna;
- documenti operativi;
- costi reali;
- disponibilità mezzi;
- compatibilità veicolo-cargo implementata;
- pianificazione reale.

Questi concetti arriveranno in moduli futuri come:

```text
planning
dispatching
transport execution
tracking
documents
costing
claims
incidents
```

## 10.4 Struttura del package dopo la riorganizzazione

Il dominio shipments è stato diviso in sottopackage tematici per rendere il codice più leggibile.

La regola fondamentale è questa:

```text
Shipment rimane l'unico aggregate root.
Tutti gli altri componenti sono entity interne o value object appartenenti all'aggregate Shipment.
La divisione in sottopackage serve solo a organizzare il codice.
```

La struttura attuale è:

```text
domain.shipments
├─ core
│  ├─ Shipment
│  ├─ ShipmentId
│  ├─ ShipmentCode
│  ├─ ShipmentStatus
│  ├─ ShipmentPriority
│  ├─ ShipmentServiceLevel
│  └─ ShipmentValidation
│
├─ items
│  ├─ ShipmentItem
│  ├─ ShipmentItemId
│  └─ ShipmentUnitOfMeasure
│
├─ legs
│  ├─ ShipmentLeg
│  ├─ ShipmentLegId
│  └─ ShipmentLegType
│
├─ requirements
│  ├─ ShipmentRequirementSet
│  └─ ShipmentTransportRequirement
│
├─ metrics
│  ├─ ShipmentMetrics
│  ├─ ShipmentVolume
│  ├─ ShipmentVolumeUnit
│  ├─ ShipmentWeight
│  └─ ShipmentWeightUnit
│
├─ properties
│  ├─ ShipmentProperties
│  └─ ShipmentTemperature
│
├─ notes
│  └─ ShipmentNotes
│
└─ references
   └─ ShipmentReferences
```

Questa divisione non crea nuovi aggregate, nuovi repository o nuovi bounded context. Serve solo a evitare un package piatto con molte classi tutte insieme.

## 10.5 Perché questa divisione è corretta

La riorganizzazione è corretta perché:

- `Shipment` rimane l'aggregate root;
- `ShipmentItem` e `ShipmentLeg` rimangono elementi interni della shipment;
- `ShipmentRequirementSet`, `ShipmentMetrics`, `ShipmentProperties`, `ShipmentTemperature`, `ShipmentReferences` e `ShipmentNotes` rimangono value object dell'aggregate;
- non vengono creati repository separati come `ShipmentItemRepository` o `ShipmentLegRepository`;
- non vengono introdotti servizi di sottopackage;
- la struttura è più navigabile e più adatta a un dominio enterprise.

In DDD, la struttura dei package può aiutare la leggibilità, ma non deve cambiare il confine dell'aggregate.

## 10.6 Package `core`

Il package `domain.shipments.core` contiene il cuore dell'aggregate.

```text
domain.shipments.core
├─ Shipment
├─ ShipmentId
├─ ShipmentCode
├─ ShipmentStatus
├─ ShipmentPriority
├─ ShipmentServiceLevel
└─ ShipmentValidation
```

### `Shipment`

`Shipment` è l'aggregate root.

Rappresenta una **richiesta di spedizione** o una **spedizione da organizzare**.

Contiene:

- `ShipmentId id`;
- `ShipmentCode code`;
- `name`;
- `description`;
- `ShipmentStatus status`;
- `ShipmentPriority priority`;
- `ShipmentServiceLevel serviceLevel`;
- `List<ShipmentItem> items`;
- `List<ShipmentLeg> legs`;
- `ShipmentProperties properties`;
- `ShipmentTemperature temperature`;
- `ShipmentRequirementSet requirementSet`;
- `ShipmentMetrics metrics`;
- `ShipmentReferences references`;
- `ShipmentNotes notes`;
- `generalNotes`.

La shipment non contiene oggetti completi di altri domini. Quando deve riferirsi a cargo o location usa solo i rispettivi ID.

### `ShipmentId`

`ShipmentId` è l'identificatore tecnico della spedizione.

Serve al sistema e non all'utente finale.

È simile a:

- `CargoId`;
- `VehicleUnitId`;
- `TripTemplateId`;
- `LocationId`;
- `UserId`.

La regola è:

```text
ShipmentId = identificatore tecnico interno
ShipmentCode = codice aziendale leggibile
```

### `ShipmentCode`

`ShipmentCode` è il codice leggibile della spedizione.

Esempi:

```text
SHP-001
ORD-2024-033
EXP-00012
FOOD-SHP-044
ADR-SHP-009
```

Questa scelta è coerente con altri codici aziendali già presenti nel progetto:

- `CargoCode`;
- `FleetCode`;
- `OperationalCode`;
- `LocationCode`;
- `TripTemplateCode`.

L'ID tecnico serve al sistema, mentre il codice aziendale serve agli utenti.

### `ShipmentStatus`

`ShipmentStatus` rappresenta lo stato della **richiesta di spedizione**, non lo stato operativo del viaggio.

I valori sono:

```text
DRAFT
REGISTERED
CONFIRMED
SUSPENDED
CANCELLED
ARCHIVED
```

Significato:

- `DRAFT`: spedizione ancora in bozza;
- `REGISTERED`: spedizione registrata nel sistema;
- `CONFIRMED`: spedizione confermata e pronta per essere pianificata;
- `SUSPENDED`: spedizione temporaneamente bloccata;
- `CANCELLED`: richiesta di spedizione annullata;
- `ARCHIVED`: spedizione storica archiviata.

Non sono stati di `domain.shipments`:

- `IN_TRANSIT`;
- `DELIVERED`;
- `FAILED`;
- `DAMAGED`;
- `DELAYED`;
- `PARTIALLY_DELIVERED`.

Questi ultimi appartengono all'esecuzione reale del trasporto, al tracking, alla consegna, ai reclami o agli incidenti.

### `ShipmentPriority`

`ShipmentPriority` descrive quanto è importante o urgente una spedizione.

Valori:

```text
LOW
NORMAL
HIGH
URGENT
```

La priorità non pianifica il viaggio e non assegna un mezzo. È solo una caratteristica dichiarata della richiesta, utile per il futuro planning.

### `ShipmentServiceLevel`

`ShipmentServiceLevel` descrive il livello di servizio richiesto.

Valori:

```text
ECONOMY
STANDARD
EXPRESS
DEDICATED
TIME_CRITICAL
```

Non è ancora SLA operativo, non calcola penali e non decide il mezzo.

### `ShipmentValidation`

`ShipmentValidation` contiene funzioni di validazione condivise usate dai sottopackage del dominio shipments.

È stato spostato in `core` perché gli altri sottopackage devono poter riutilizzare le stesse regole base, come:

- testo obbligatorio;
- valori non nulli;
- quantità positive;
- valori non negativi;
- normalizzazione delle note;
- controllo di collezioni senza elementi nulli.

## 10.7 Package `items`

Il package `domain.shipments.items` contiene le righe merce della spedizione.

```text
domain.shipments.items
├─ ShipmentItem
├─ ShipmentItemId
└─ ShipmentUnitOfMeasure
```

### `ShipmentItem`

`ShipmentItem` rappresenta una riga merce inclusa nella spedizione.

Contiene:

- `ShipmentItemId id`;
- `CargoId cargoId`;
- `quantity`;
- `ShipmentUnitOfMeasure unitOfMeasure`;
- `notes`.

La relazione corretta è:

```text
ShipmentItem -> CargoId
```

Non è:

```text
ShipmentItem -> CargoUnit completo
```

Questa scelta mantiene separati `domain.cargo` e `domain.shipments`.

### `ShipmentItemId`

`ShipmentItemId` identifica tecnicamente una riga merce dentro la shipment.

Una shipment può avere più item, per esempio:

```text
Shipment SHP-001
├─ Item 1: 33 pallet alimentari
├─ Item 2: 4 pallet farmaceutici
└─ Item 3: 2 colli fragili
```

### `ShipmentUnitOfMeasure`

`ShipmentUnitOfMeasure` evita stringhe libere per le unità di misura.

Valori:

```text
UNIT
PALLET
BOX
CARTON
KG
TON
LITER
CUBIC_METER
CONTAINER
ROLL_CONTAINER
OTHER
```

Esempi:

```text
33 PALLET
120 BOX
1500 KG
22 TON
1 CONTAINER
```

## 10.8 Package `legs`

Il package `domain.shipments.legs` contiene le tratte logiche richieste dalla spedizione.

```text
domain.shipments.legs
├─ ShipmentLeg
├─ ShipmentLegId
└─ ShipmentLegType
```

### `ShipmentLeg`

`ShipmentLeg` rappresenta una tratta logica richiesta dalla spedizione.

È diversa da `TripTemplateSegment`.

```text
TripTemplateSegment = tratta di un percorso tipo
ShipmentLeg = tratta richiesta da una spedizione specifica
```

Contiene:

- `ShipmentLegId id`;
- `sequenceNumber`;
- `ShipmentLegType type`;
- `LocationId originLocationId`;
- `LocationId destinationLocationId`;
- `estimatedDistanceKm`;
- `notes`.

La shipment usa `LocationId`, non l'oggetto completo `Location`.

Questo mantiene separati:

```text
domain.locations
domain.shipments
```

### `ShipmentLegId`

`ShipmentLegId` identifica tecnicamente una tratta della shipment.

Ogni shipment può avere più leg, per esempio:

```text
Shipment SHP-001
├─ Leg 1: Pickup da cliente
├─ Leg 2: Transit verso hub
└─ Leg 3: Delivery al destinatario
```

### `ShipmentLegType`

Valori:

```text
PICKUP
DELIVERY
TRANSIT
RETURN
TRANSFER
SPECIAL
```

Nel dominio `triptemplates` avevamo evitato `PICKUP` e `DELIVERY`, perché il template di percorso è astratto e non conosce la spedizione.

In `shipments`, invece, `PICKUP` e `DELIVERY` sono coerenti perché una spedizione può avere punti logici di ritiro e consegna.

## 10.9 Package `requirements`

Il package `domain.shipments.requirements` contiene i requisiti dichiarati dalla spedizione.

```text
domain.shipments.requirements
├─ ShipmentRequirementSet
└─ ShipmentTransportRequirement
```

### `ShipmentRequirementSet`

`ShipmentRequirementSet` contiene un set di requisiti di trasporto dichiarati dalla spedizione.

È stato scelto un set invece di tanti booleani per evitare classi troppo rigide.

Con i booleani, ogni nuovo requisito richiederebbe un nuovo campo.

Con il set, è sufficiente aggiungere un nuovo valore all'enum `ShipmentTransportRequirement`.

### `ShipmentTransportRequirement`

Valori principali:

```text
ADR_REQUIRED
ATP_REQUIRED
FOOD_GRADE_REQUIRED
PHARMA_GRADE_REQUIRED
WASTE_AUTHORIZATION_REQUIRED
LIVESTOCK_AUTHORIZATION_REQUIRED
TEMPERATURE_CONTROL_REQUIRED
REFRIGERATED_TRANSPORT_REQUIRED
FROZEN_TRANSPORT_REQUIRED
SEPARATION_REQUIRED
HIGH_VALUE_HANDLING_REQUIRED
FRAGILE_HANDLING_REQUIRED
DEDICATED_VEHICLE_REQUIRED
EXPRESS_HANDLING_REQUIRED
SPECIAL_HANDLING_REQUIRED
```

Esempio spedizione alimentare fresca:

```text
ATP_REQUIRED
FOOD_GRADE_REQUIRED
TEMPERATURE_CONTROL_REQUIRED
REFRIGERATED_TRANSPORT_REQUIRED
```

Esempio spedizione ADR:

```text
ADR_REQUIRED
```

Esempio spedizione ad alto valore:

```text
HIGH_VALUE_HANDLING_REQUIRED
DEDICATED_VEHICLE_REQUIRED
```

Questa scelta è coerente con il modello del cargo, dove i requisiti sono espressi come set e non come lista di booleani.

## 10.10 Package `metrics`

Il package `domain.shipments.metrics` contiene le metriche dichiarate o riepilogative della shipment.

```text
domain.shipments.metrics
├─ ShipmentMetrics
├─ ShipmentVolume
├─ ShipmentVolumeUnit
├─ ShipmentWeight
└─ ShipmentWeightUnit
```

### `ShipmentMetrics`

`ShipmentMetrics` rappresenta metriche dichiarate, riepilogative o stimate della spedizione.

Contiene:

- `ShipmentVolume volume`;
- `ShipmentWeight weight`;
- `notes`.

È importante notare che queste metriche non sono sempre calcolate automaticamente. Nella realtà possono essere dichiarate dal cliente, stimate, verificate o calcolate in fasi successive.

Per ora il dominio le registra come informazioni della shipment.

### `ShipmentVolume`

`ShipmentVolume` contiene:

- `value`;
- `ShipmentVolumeUnit unit`.

L'unità attuale è:

```text
CUBIC_METER
```

### `ShipmentWeight`

`ShipmentWeight` contiene:

- `grossWeight`;
- `netWeight`;
- `ShipmentWeightUnit unit`.

Le unità sono:

```text
KG
TON
```

Il peso netto non può essere maggiore del peso lordo.

## 10.11 Package `properties`

Il package `domain.shipments.properties` contiene le proprietà generali e i requisiti termici della shipment.

```text
domain.shipments.properties
├─ ShipmentProperties
└─ ShipmentTemperature
```

### `ShipmentProperties`

`ShipmentProperties` contiene proprietà generali della spedizione.

Campi:

- `fragile`;
- `highValue`;
- `perishable`;
- `requiresSeparation`;
- `stackable`;
- `notes`.

Queste informazioni possono derivare dai cargo inclusi nella spedizione, ma possono anche essere dichiarate a livello shipment.

Esempio:

```text
Cargo 1 fragile = true
Cargo 2 highValue = true
ShipmentProperties fragile = true, highValue = true
```

### `ShipmentTemperature`

`ShipmentTemperature` descrive i requisiti termici della spedizione.

Campi:

- `requiredMinCelsius`;
- `requiredMaxCelsius`;
- `controlled`;
- `notes`.

Se `controlled` è `true`, devono essere presenti temperatura minima e massima.

La classe non decide quale veicolo usare. Dice solo che la spedizione richiede determinate condizioni termiche.

La verifica con i veicoli verrà più avanti.

## 10.12 Package `references`

Il package `domain.shipments.references` contiene i riferimenti interni o esterni collegati alla spedizione.

```text
domain.shipments.references
└─ ShipmentReferences
```

`ShipmentReferences` contiene:

- `customerReference`;
- `supplierReference`;
- `internalReference`;
- `purchaseOrderReference`;
- `salesOrderReference`;
- `notes`.

Questi non sono documenti e non sono allegati. Sono riferimenti testuali utili al gestionale.

Esempi:

- riferimento cliente;
- riferimento ordine fornitore;
- riferimento interno aziendale;
- numero ordine di acquisto;
- numero ordine di vendita.

## 10.13 Package `notes`

Il package `domain.shipments.notes` contiene le note interne ed esterne della spedizione.

```text
domain.shipments.notes
└─ ShipmentNotes
```

`ShipmentNotes` separa:

- `internalNotes`;
- `externalNotes`.

Le note interne sono visibili solo all'azienda.

Le note esterne possono essere comunicate a cliente, destinatario o partner.

Questa separazione è utile perché non tutte le note devono uscire dall'azienda.

## 10.14 Relazioni con gli altri domini

### Shipment e Cargo

```text
ShipmentItem
└─ CargoId
```

La shipment non contiene direttamente `CargoUnit`.

### Shipment e Locations

```text
ShipmentLeg
├─ originLocationId
└─ destinationLocationId
```

La shipment non contiene direttamente `Location`.

### Shipment e Vehicles

`Shipment` non contiene:

- `VehicleUnitId`;
- `VehicleCombinationId`.

L'assegnazione del mezzo appartiene a planning/dispatching.

### Shipment e Operational

`Shipment` non contiene:

- `DriverId`;
- `DispatcherId`;
- `ManagerId`.

Le persone operative verranno collegate più avanti tramite moduli applicativi o operativi.

### Shipment e TripTemplate

`Shipment` non deve contenere obbligatoriamente un `TripTemplateId`.

Il template di percorso potrà essere usato più avanti dal planning per suggerire una rotta o creare un piano di trasporto.

Per ora la shipment ha le sue `ShipmentLeg`.

## 10.15 Linee guida DDD sui sottopackage

La divisione in sottopackage non deve essere interpretata come divisione in micro-domini.

Regole:

- non creare `ShipmentItemRepository`;
- non creare `ShipmentLegRepository`;
- non creare servizi separati solo perché esiste un sottopackage;
- non trattare `items`, `legs`, `metrics`, `requirements`, `properties`, `notes` o `references` come aggregate indipendenti;
- usare `Shipment` come unico aggregate root;
- salvare, caricare e modificare la shipment come aggregate unitario.

Il repository futuro sarà concettualmente:

```text
ShipmentRepository
```

non:

```text
ShipmentItemRepository
ShipmentLegRepository
ShipmentMetricsRepository
```

## 10.16 Invarianti principali

### Shipment

- `id` obbligatorio;
- `code` obbligatorio;
- `name` obbligatorio;
- `status` obbligatorio;
- `priority` obbligatoria;
- `serviceLevel` obbligatorio;
- `items` non nullo;
- `legs` non nullo;
- note normalizzate;
- una shipment `CONFIRMED` deve avere almeno un item;
- una shipment `CONFIRMED` deve avere almeno una leg;
- i `sequenceNumber` delle leg devono essere univoci;
- le leg vengono ordinate per `sequenceNumber`.

### ShipmentItem

- `id` obbligatorio;
- `cargoId` obbligatorio;
- `quantity` positiva;
- `unitOfMeasure` obbligatoria;
- note normalizzate.

### ShipmentLeg

- `id` obbligatorio;
- `sequenceNumber` positivo;
- `type` obbligatorio;
- `originLocationId` obbligatorio;
- `destinationLocationId` obbligatorio;
- `estimatedDistanceKm` opzionale ma non negativa;
- note normalizzate.

### ShipmentTemperature

- se `controlled = true`, devono esserci temperatura minima e massima;
- la temperatura minima non può essere maggiore della massima;
- una shipment a temperatura controllata deve dichiarare `TEMPERATURE_CONTROL_REQUIRED`.

### ShipmentProperties

- se `requiresSeparation = true`, la shipment deve dichiarare `SEPARATION_REQUIRED`.

## 10.17 Esempio completo

```text
Shipment
├─ code: SHP-001
├─ name: Spedizione alimentare Milano - Roma
├─ status: CONFIRMED
├─ priority: HIGH
├─ serviceLevel: EXPRESS
│
├─ items:
│  ├─ Item 1:
│  │  ├─ cargoId: FOOD-023
│  │  ├─ quantity: 33
│  │  └─ unitOfMeasure: PALLET
│  │
│  └─ Item 2:
│     ├─ cargoId: PHARMA-004
│     ├─ quantity: 4
│     └─ unitOfMeasure: PALLET
│
├─ legs:
│  ├─ Leg 1:
│  │  ├─ sequenceNumber: 1
│  │  ├─ type: PICKUP
│  │  ├─ originLocationId: CUS-MIL-001
│  │  └─ destinationLocationId: HUB-BO-001
│  │
│  └─ Leg 2:
│     ├─ sequenceNumber: 2
│     ├─ type: DELIVERY
│     ├─ originLocationId: HUB-BO-001
│     └─ destinationLocationId: DEP-ROMA-001
│
├─ properties:
│  ├─ fragile: false
│  ├─ highValue: true
│  ├─ perishable: true
│  └─ requiresSeparation: true
│
├─ temperature:
│  ├─ requiredMin: +2°C
│  ├─ requiredMax: +4°C
│  └─ controlled: true
│
├─ requirementSet:
│  ├─ ATP_REQUIRED
│  ├─ FOOD_GRADE_REQUIRED
│  ├─ TEMPERATURE_CONTROL_REQUIRED
│  ├─ REFRIGERATED_TRANSPORT_REQUIRED
│  └─ SEPARATION_REQUIRED
│
├─ metrics:
│  ├─ volume: 60 m³
│  └─ weight: 12000 kg
│
├─ references:
│  ├─ customerReference: ORD-2024-033
│  ├─ supplierReference: SUP-8831
│  └─ internalReference: INT-0001
│
└─ notes:
   ├─ internalNotes: Verificare separazione merce pharma
   └─ externalNotes: Consegnare in area refrigerata
```

## 10.18 Perché questa scelta è coerente con DDD

`domain.shipments` è coerente con il resto del progetto perché:

- usa ID per riferirsi ad altri aggregati;
- non ingloba `CargoUnit` o `Location` completi;
- non assegna veicoli;
- non assegna autisti;
- non pianifica;
- non traccia;
- non gestisce documenti operativi;
- non implementa compatibilità veicolo-cargo;
- usa value object espliciti;
- protegge invarianti semplici ma importanti;
- organizza il package in sottopackage senza spezzare l'aggregate root.

La separazione finale è:

```text
Cargo = merce
Shipments = richiesta di spedizione
Locations = luoghi
TripTemplates = percorsi tipo
Vehicles = mezzi
Operational = persone operative
Planning/Dispatching = esecuzione reale futura
```

Questa struttura mantiene TruckFlow pulito, scalabile e pronto per i moduli operativi futuri.
