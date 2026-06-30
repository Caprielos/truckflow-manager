# 10. Dominio `domain.shipments`

## 10.1 Obiettivo del dominio shipments

Il package `domain.shipments` rappresenta la **spedizione richiesta**, cioè ciò che l'azienda deve trasportare da uno o più luoghi verso uno o più luoghi.

Una shipment non è una semplice anagrafica come `CargoUnit`, ma non è nemmeno un viaggio operativo reale. È il concetto intermedio che descrive **cosa deve essere spedito**, **quali merci compongono la spedizione**, **quali tratte logiche sono richieste** e **quali requisiti devono essere rispettati**.

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

## 10.4 Struttura del package

Il package è stato modellato così:

```text
domain.shipments
├─ Shipment
├─ ShipmentId
├─ ShipmentCode
├─ ShipmentStatus
├─ ShipmentPriority
├─ ShipmentServiceLevel
│
├─ ShipmentItem
├─ ShipmentItemId
├─ ShipmentUnitOfMeasure
│
├─ ShipmentLeg
├─ ShipmentLegId
├─ ShipmentLegType
│
├─ ShipmentProperties
├─ ShipmentTemperature
├─ ShipmentMetrics
├─ ShipmentVolume
├─ ShipmentVolumeUnit
├─ ShipmentWeight
├─ ShipmentWeightUnit
│
├─ ShipmentReferences
├─ ShipmentNotes
│
├─ ShipmentRequirementSet
├─ ShipmentTransportRequirement
└─ ShipmentValidation
```

La struttura è volutamente piatta, come `domain.cargo`, perché il dominio shipment è ricco ma ancora leggibile. Se in futuro dovesse crescere molto, potrà essere diviso in sottopackage come è stato fatto per `domain.vehicles`.

## 10.5 `Shipment`

`Shipment` è l'entità principale.

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

## 10.6 `ShipmentId`

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

## 10.7 `ShipmentCode`

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

## 10.8 `ShipmentStatus`

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

## 10.9 `ShipmentPriority`

`ShipmentPriority` descrive quanto è importante o urgente una spedizione.

Valori:

```text
LOW
NORMAL
HIGH
URGENT
```

La priorità non pianifica il viaggio e non assegna un mezzo. È solo una caratteristica dichiarata della richiesta, utile per il futuro planning.

## 10.10 `ShipmentServiceLevel`

`ShipmentServiceLevel` descrive il livello di servizio richiesto.

Valori:

```text
ECONOMY
STANDARD
EXPRESS
DEDICATED
TIME_CRITICAL
```

Significato:

- `ECONOMY`: servizio economico;
- `STANDARD`: servizio normale;
- `EXPRESS`: servizio rapido;
- `DEDICATED`: servizio dedicato;
- `TIME_CRITICAL`: spedizione critica rispetto al tempo.

Non è ancora SLA operativo, non calcola penali e non decide il mezzo.

## 10.11 `ShipmentItem`

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

## 10.12 `ShipmentUnitOfMeasure`

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

## 10.13 `ShipmentLeg`

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

## 10.14 `ShipmentLegType`

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

## 10.15 `ShipmentProperties`

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

## 10.16 `ShipmentTemperature`

`ShipmentTemperature` descrive i requisiti termici della spedizione.

Campi:

- `requiredMinCelsius`;
- `requiredMaxCelsius`;
- `controlled`;
- `notes`.

Se `controlled` è `true`, devono essere presenti temperatura minima e massima.

La classe non decide quale veicolo usare. Dice solo che la spedizione richiede determinate condizioni termiche.

La verifica con i veicoli verrà più avanti.

## 10.17 `ShipmentRequirementSet`

`ShipmentRequirementSet` contiene un set di requisiti di trasporto dichiarati dalla spedizione.

È stato scelto un set invece di tanti booleani per evitare classi troppo rigide.

Con i booleani, ogni nuovo requisito richiederebbe un nuovo campo.

Con il set, è sufficiente aggiungere un nuovo valore all'enum `ShipmentTransportRequirement`.

## 10.18 `ShipmentTransportRequirement`

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

## 10.19 `ShipmentMetrics`

`ShipmentMetrics` rappresenta metriche dichiarate, riepilogative o stimate della spedizione.

Contiene:

- `ShipmentVolume volume`;
- `ShipmentWeight weight`;
- `notes`.

È importante notare che queste metriche non sono sempre calcolate automaticamente. Nella realtà possono essere dichiarate dal cliente, stimate, verificate o calcolate in fasi successive.

Per ora il dominio le registra come informazioni della shipment.

## 10.20 `ShipmentVolume` e `ShipmentWeight`

`ShipmentVolume` contiene:

- `value`;
- `ShipmentVolumeUnit unit`.

L'unità attuale è:

```text
CUBIC_METER
```

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

## 10.21 `ShipmentReferences`

`ShipmentReferences` contiene riferimenti interni o esterni collegati alla spedizione.

Campi:

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

## 10.22 `ShipmentNotes`

`ShipmentNotes` separa:

- `internalNotes`;
- `externalNotes`.

Le note interne sono visibili solo all'azienda.

Le note esterne possono essere comunicate a cliente, destinatario o partner.

Questa separazione è utile perché non tutte le note devono uscire dall'azienda.

## 10.23 Relazioni con gli altri domini

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

## 10.24 Invarianti principali

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

## 10.25 Esempio completo

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

## 10.26 Perché questa scelta è coerente con DDD

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
- protegge invarianti semplici ma importanti.

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
