# Archivio storico — 09-domain-triptemplates

> Questo documento fa parte dell'archivio storico `docs/old_style/`.
> Mantiene il percorso step-by-step del progetto, ma la documentazione principale aggiornata si trova in:
> `docs/simple/`, `docs/professional/` e `docs/digital/`.

---

# 9. Dominio `domain.triptemplates`

## 9.1 Scopo del dominio triptemplates

Il package `domain.triptemplates` rappresenta i percorsi tipo e le missioni tecniche astratte.

Il nome `TripTemplate` è stato scelto appositamente per evitare ambiguità.

In molti sistemi logistici la parola "trip" può indicare un viaggio reale, con autista, veicolo, cargo, orari, partenza, arrivo, tracking e stato operativo.

Nel nostro dominio puro non vogliamo ancora modellare quel viaggio reale.

Per questo usiamo `TripTemplate`.

Un `TripTemplate` descrive la struttura astratta di un percorso, non la sua esecuzione.

## 9.2 Cosa rappresenta un TripTemplate

`TripTemplate` risponde alla domanda:

```text
Qual è il percorso tipo o la missione tecnica che l'azienda conosce?
```

Esempi:

- Milano - Bologna - Firenze - Roma;
- navetta deposito - hub;
- movimento interno Yard A - Yard B;
- trasferimento tecnico tra sedi;
- percorso standard di distribuzione;
- tratta line-haul tra due hub;
- trasferimento verso porto o terminal ferroviario.

Un `TripTemplate` non contiene:

- autista assegnato;
- mezzo assegnato;
- cargo assegnato;
- orari reali;
- partenza effettiva;
- arrivo effettivo;
- ritardi;
- stato di esecuzione;
- tracking GPS;
- documenti;
- costi reali;
- pedaggi;
- carburante;
- disponibilità risorse.

Questi concetti appartengono a domini futuri come planning, dispatching, shipments, tracking, documents e costing.

## 9.3 Struttura del package

Il package contiene:

```text
TripTemplate
TripTemplateId
TripTemplateCode
TripTemplateType
TripTemplateStatus

TripTemplateSegment
TripTemplateSegmentId
TripTemplateSegmentType

RouteSpecification
RouteRoadType
Distance
DistanceUnit
```

La struttura è separata dal dominio locations.

`TripTemplateSegment` usa `LocationId`, non oggetti `Location` completi.

## 9.4 `TripTemplate`

`TripTemplate` è l'entità principale del dominio.

Contiene:

```text
TripTemplate
├─ TripTemplateId id
├─ TripTemplateCode code
├─ name
├─ description
├─ TripTemplateType type
├─ TripTemplateStatus status
├─ List<TripTemplateSegment> segments
├─ RouteSpecification routeSpecification
└─ notes
```

Il template è una struttura riutilizzabile.

Può essere usato in futuro per generare o supportare una pianificazione reale, ma non è ancora una pianificazione.

La lista dei segmenti descrive le tratte del percorso.

La `RouteSpecification` contiene informazioni descrittive generali, come distanza totale indicativa, durata stimata, paesi ammessi e tipi di strada previsti.

## 9.5 `TripTemplateId`

`TripTemplateId` è l'identificatore tecnico del template.

È basato su UUID e serve al sistema.

Non è il codice leggibile usato dall'azienda.

La distinzione è:

```text
TripTemplateId = identificatore tecnico interno
TripTemplateCode = codice aziendale leggibile
name = nome descrittivo del percorso tipo
```

## 9.6 `TripTemplateCode`

`TripTemplateCode` è il codice aziendale leggibile del percorso tipo.

Esempi:

```text
TPL-001
LINEHAUL-001
DIST-023
YARD-004
SHUTTLE-010
```

Il codice viene normalizzato in maiuscolo e accetta lettere, numeri, trattini e underscore.

È coerente con gli altri codici del progetto:

- `LocationCode`;
- `CargoCode`;
- `FleetCode`;
- `OperationalCode`.

## 9.7 `TripTemplateType`

`TripTemplateType` descrive il tipo generale del percorso.

I valori sono:

```text
LINE_HAUL
DISTRIBUTION
LAST_MILE
TRANSFER
SHUTTLE
WAREHOUSE_MOVEMENT
YARD_MOVEMENT
SPECIAL_OPERATION
```

Significato:

- `LINE_HAUL`: lunga percorrenza tra hub, depositi o grandi tratte;
- `DISTRIBUTION`: distribuzione territoriale;
- `LAST_MILE`: ultimo miglio;
- `TRANSFER`: trasferimento tecnico tra sedi o aree;
- `SHUTTLE`: navetta ripetitiva;
- `WAREHOUSE_MOVEMENT`: movimento collegato al contesto magazzino;
- `YARD_MOVEMENT`: movimento interno o tecnico in piazzale;
- `SPECIAL_OPERATION`: operazione non standard.

Non sono stati inseriti `PICKUP` e `DELIVERY` perché sono concetti legati a spedizioni, cargo e consegne reali.

Il template descrive un percorso, non un evento operativo di ritiro o consegna.

## 9.8 `TripTemplateStatus`

`TripTemplateStatus` è uno stato anagrafico.

I valori sono:

```text
ACTIVE
SUSPENDED
ARCHIVED
DISCONTINUED
```

Significato:

- `ACTIVE`: template utilizzabile nel sistema;
- `SUSPENDED`: template temporaneamente sospeso;
- `ARCHIVED`: template storico;
- `DISCONTINUED`: template non più gestito.

Non sono stati del template:

- `IN_PROGRESS`;
- `COMPLETED`;
- `CANCELLED`;
- `DELAYED`;
- `FAILED`.

Questi ultimi appartengono all'esecuzione reale di un viaggio, non alla struttura astratta del percorso.

## 9.9 `TripTemplateSegment`

`TripTemplateSegment` rappresenta una tratta del percorso tipo.

Non rappresenta una sosta.

La sosta è una location.

Il segmento è il collegamento tra due location.

Contiene:

```text
TripTemplateSegment
├─ TripTemplateSegmentId id
├─ sequenceNumber
├─ TripTemplateSegmentType type
├─ LocationId originLocationId
├─ LocationId destinationLocationId
├─ Distance distance
└─ notes
```

La scelta di usare `LocationId` è intenzionale.

`Location` vive in `domain.locations`, quindi il template non ingloba direttamente gli oggetti location.

Questo riduce l'accoppiamento tra aggregati.

## 9.10 `sequenceNumber`

`sequenceNumber` definisce l'ordine dei segmenti.

Esempio:

```text
Segment 1: Milano Depot → Bologna Hub
Segment 2: Bologna Hub → Firenze Yard
Segment 3: Firenze Yard → Roma Depot
```

Senza sequence number, il dominio dovrebbe fidarsi solo della posizione nella lista.

Con sequence number, l'ordine è esplicito e controllabile.

Il dominio valida che i numeri di sequenza siano univoci.

## 9.11 `TripTemplateSegmentType`

`TripTemplateSegmentType` descrive il tipo tecnico della tratta.

I valori sono:

```text
TRANSIT
RETURN
INTERNAL_TRANSFER
POSITIONING
YARD_MOVEMENT
SPECIAL
```

Significato:

- `TRANSIT`: tratta normale tra due luoghi;
- `RETURN`: tratta di rientro;
- `INTERNAL_TRANSFER`: trasferimento interno;
- `POSITIONING`: spostamento tecnico di posizionamento;
- `YARD_MOVEMENT`: movimento di piazzale;
- `SPECIAL`: tratta speciale.

Il modello evita volutamente `PICKUP` e `DELIVERY` perché sono concetti più operativi e collegati alla merce.

## 9.12 Origine, destinazione e LocationId

Ogni segmento ha:

```text
originLocationId
destinationLocationId
```

Questo significa che il segmento conosce i riferimenti ai luoghi, non i luoghi completi.

Esempio:

```text
TripTemplateSegment
├─ originLocationId: DEP-MIL-001
└─ destinationLocationId: HUB-BO-001
```

La location completa vive nel dominio locations:

```text
Location
├─ code: DEP-MIL-001
├─ name: Milano Depot
└─ type: DEPOT
```

Questa separazione è coerente con la regola usata anche in altri domini: quando un concetto appartiene a un altro aggregato, si usa il suo ID.

## 9.13 Movimento Yard

Il dominio supporta il concetto di movimento yard.

Un movimento yard può descrivere lo spostamento interno tra due aree di piazzale.

Esempio:

```text
TripTemplate
├─ code: YARD-001
├─ name: Spostamento Yard A - Yard B
├─ type: YARD_MOVEMENT
└─ segment:
   ├─ type: YARD_MOVEMENT
   ├─ originLocationId: YARD-A
   └─ destinationLocationId: YARD-B
```

Il modello permette anche, per segmenti interni, yard o speciali, di avere origine e destinazione uguali quando il movimento rappresenta una manovra interna o un'operazione tecnica nello stesso luogo.

Per una tratta normale di transito, invece, origine e destinazione uguali non sono ammesse.

## 9.14 `RouteSpecification`

`RouteSpecification` contiene informazioni descrittive generali sul percorso.

Contiene:

```text
RouteSpecification
├─ Distance totalDistance
├─ estimatedDuration
├─ Set<String> allowedCountries
├─ Set<RouteRoadType> allowedRoadTypes
└─ notes
```

Non è un motore di routing.

Non calcola il percorso.

Non ottimizza.

Non chiama servizi esterni.

Non decide le strade reali.

È solo una specifica descrittiva del template.

## 9.15 `RouteRoadType`

`RouteRoadType` evita stringhe libere per i tipi di strada.

I valori sono:

```text
MOTORWAY
NATIONAL_ROAD
REGIONAL_ROAD
URBAN_ROAD
PRIVATE_ROAD
YARD_INTERNAL_ROAD
PORT_ROAD
OTHER
```

Questi valori descrivono le tipologie stradali ammesse o previste dal template.

Non implicano calcolo GPS o navigazione.

## 9.16 `Distance` e `DistanceUnit`

`Distance` rappresenta una distanza.

Contiene:

```text
Distance
├─ value
└─ unit
```

Le unità sono:

```text
KM
MI
```

Nel contesto principale del progetto useremo soprattutto `KM`, perché TruckFlow nasce con un contesto europeo e italiano.

Il valore della distanza non può essere negativo.

## 9.17 Esempio completo

Esempio di location:

```text
Location
├─ code: DEP-MIL-001
├─ name: Milano Depot
├─ type: DEPOT
└─ status: ACTIVE
```

```text
Location
├─ code: HUB-BO-001
├─ name: Bologna Hub
├─ type: HUB
└─ status: ACTIVE
```

```text
Location
├─ code: YARD-FI-002
├─ name: Firenze Yard
├─ type: YARD
└─ status: ACTIVE
```

Esempio di template:

```text
TripTemplate
├─ code: LINEHAUL-001
├─ name: Milano - Roma con soste
├─ type: LINE_HAUL
├─ status: ACTIVE
├─ segments:
│  ├─ Segment 1:
│  │  ├─ sequenceNumber: 1
│  │  ├─ type: TRANSIT
│  │  ├─ originLocationId: DEP-MIL-001
│  │  └─ destinationLocationId: HUB-BO-001
│  ├─ Segment 2:
│  │  ├─ sequenceNumber: 2
│  │  ├─ type: TRANSIT
│  │  ├─ originLocationId: HUB-BO-001
│  │  └─ destinationLocationId: YARD-FI-002
│  └─ Segment 3:
│     ├─ sequenceNumber: 3
│     ├─ type: TRANSIT
│     ├─ originLocationId: YARD-FI-002
│     └─ destinationLocationId: DEP-ROMA-001
└─ routeSpecification:
   ├─ totalDistance: 605 km
   ├─ estimatedDuration: 7h 30m
   └─ allowedCountries: IT
```

Questo esempio contiene soste multiple, ma non contiene ancora alcun viaggio reale.

## 9.18 Invarianti principali

Le invarianti principali di `TripTemplate` sono:

- `id` obbligatorio;
- `code` obbligatorio;
- `name` obbligatorio;
- `type` obbligatorio;
- `status` obbligatorio;
- `segments` non nullo;
- un template `ACTIVE` deve avere almeno un segmento;
- i sequence number dei segmenti devono essere univoci;
- i segmenti vengono ordinati per sequence number;
- note normalizzate.

Le invarianti principali di `TripTemplateSegment` sono:

- `id` obbligatorio;
- `sequenceNumber` positivo;
- `type` obbligatorio;
- `originLocationId` obbligatorio;
- `destinationLocationId` obbligatorio;
- distanza opzionale ma, se presente, non negativa;
- note normalizzate;
- origine e destinazione uguali ammesse solo per movimenti interni, yard o speciali.

Le invarianti principali di `Distance` sono:

- valore non negativo;
- unità obbligatoria.

## 9.19 Continuità dei segmenti

Il dominio espone la possibilità di verificare se un template è continuo.

Un template è continuo quando la destinazione di un segmento coincide con l'origine del segmento successivo.

Esempio continuo:

```text
Milano → Bologna
Bologna → Firenze
Firenze → Roma
```

Esempio non continuo:

```text
Milano → Bologna
Firenze → Roma
```

La verifica di continuità è descrittiva e interna al template.

Non è ancora pianificazione, non assegna mezzi e non calcola percorsi.

## 9.20 Cosa non gestisce questo dominio

`domain.triptemplates` non gestisce:

- `VehicleUnitId`;
- `VehicleCombinationId`;
- `DriverId`;
- `CargoId`;
- orari pianificati;
- orari reali;
- ritardi;
- tracking;
- documenti;
- costi;
- pedaggi;
- carburante;
- assegnazioni;
- disponibilità.

Questi concetti verranno aggiunti in domini o layer futuri.

## 9.21 Sintesi

`domain.triptemplates` descrive percorsi tipo e missioni tecniche astratte.

La scelta di usare `TripTemplate` invece di `Trip` evita di confondere il modello astratto con il viaggio reale.

La scelta di usare `LocationId` evita di accoppiare direttamente il dominio dei percorsi al dominio delle location.

Il risultato è un dominio pulito, riutilizzabile e coerente con TruckFlow:

```text
Locations = luoghi
TripTemplates = percorsi tipo
Vehicles = mezzi
Cargo = merce
Operational = persone operative
Planning/Dispatching = assegnazione reale futura
```
