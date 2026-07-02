# Archivio storico — 08-domain-locations

> Questo documento fa parte dell'archivio storico `docs/old_style/`.
> Mantiene il percorso step-by-step del progetto, ma la documentazione principale aggiornata si trova in:
> `docs/simple/`, `docs/professional/` e `docs/digital/`.

---

# 8. Dominio `domain.locations`

## 8.1 Scopo del dominio locations

Il package `domain.locations` rappresenta i luoghi logistici e geografici usati da TruckFlow Manager.

Una location può essere un deposito, un magazzino, un cliente, un fornitore, un hub, un porto, un aeroporto, un terminal ferroviario, un terminal intermodale, un punto di confine, un'area di servizio o un piazzale operativo.

La scelta di creare un dominio separato per le location nasce da un principio importante: i luoghi non appartengono solo ai percorsi. In futuro potranno essere usati anche da spedizioni, magazzini, clienti, fornitori, pianificazione, documenti, tracking e prenotazioni di slot.

Per questo `Location` non è stata inserita dentro `domain.triptemplates`. È un concetto riutilizzabile e merita un proprio bounded context.

## 8.2 Cosa rappresenta una Location

`Location` rappresenta un luogo astratto del dominio.

Non rappresenta ancora:

- disponibilità di una baia di carico;
- slot di carico o scarico;
- inventario di magazzino;
- geofencing;
- tracking GPS;
- prenotazioni;
- chiusure temporanee;
- code in piazzale;
- operazioni reali di carico o scarico.

Queste informazioni appartengono a domini futuri, come warehouse, planning, dispatching, slot booking o tracking.

Nel dominio puro, una location descrive solo:

- come il luogo è identificato;
- come si chiama;
- che tipo di luogo è;
- qual è il suo stato anagrafico;
- qual è il suo indirizzo;
- quali coordinate geografiche possiede;
- eventuali note descrittive.

## 8.3 Struttura del package

Il package contiene:

```text
Location
LocationId
LocationCode
LocationType
LocationStatus
LocationAddress
GeoCoordinates
```

Questa struttura è volutamente semplice, ma enterprise: separa l'identità tecnica dal codice aziendale, l'indirizzo dalle coordinate e lo stato anagrafico dallo stato operativo.

## 8.4 `Location`

`Location` è l'entità principale del dominio locations.

Contiene:

```text
Location
├─ LocationId id
├─ LocationCode code
├─ name
├─ LocationType type
├─ LocationStatus status
├─ LocationAddress address
├─ GeoCoordinates coordinates
└─ notes
```

Il campo `id` identifica tecnicamente la location nel dominio.

Il campo `code` identifica la location con un codice leggibile dall'azienda.

Il campo `name` contiene il nome descrittivo del luogo.

Il campo `type` classifica il luogo.

Il campo `status` indica se la location è attiva, sospesa, archiviata o dismessa dal punto di vista anagrafico.

Il campo `address` contiene l'indirizzo strutturato.

Il campo `coordinates` contiene latitudine e longitudine, quando disponibili.

Le `notes` servono solo come annotazioni descrittive.

## 8.5 `LocationId`

`LocationId` è l'identificatore tecnico della location.

È un value object basato su UUID.

Serve al sistema per distinguere in modo univoco una location da un'altra.

Non è pensato per essere letto o gestito manualmente dagli utenti aziendali.

La distinzione è:

```text
LocationId = identificatore tecnico interno
LocationCode = codice aziendale leggibile
name = nome descrittivo del luogo
```

Questa scelta è coerente con gli altri domini del progetto, come `CargoId`, `VehicleUnitId`, `TripTemplateId`, `UserId` e `DriverId`.

## 8.6 `LocationCode`

`LocationCode` è il codice aziendale leggibile della location.

Esempi:

```text
DEP-MIL-001
HUB-BO-001
YARD-FI-002
WH-ROMA-001
PORT-GEN-001
CUS-ROMA-010
```

Questo codice è utile perché in un gestionale reale gli utenti non lavorano con UUID, ma con codici interni comprensibili.

`LocationCode` viene normalizzato in maiuscolo e accetta lettere, numeri, trattini e underscore.

Questa scelta è coerente con:

- `OperationalCode` per le figure operative;
- `FleetCode` per i veicoli;
- `CargoCode` per la merce;
- `TripTemplateCode` per i percorsi tipo.

## 8.7 `LocationType`

`LocationType` descrive il tipo di luogo.

I valori previsti sono:

```text
DEPOT
WAREHOUSE
CUSTOMER
SUPPLIER
YARD
HUB
PORT
AIRPORT
RAIL_TERMINAL
INTERMODAL_TERMINAL
BORDER_CROSSING
SERVICE_AREA
OTHER
```

Questa classificazione serve per distinguere contesti logistici diversi senza introdurre ancora logiche operative.

Per esempio:

- `DEPOT` indica un deposito aziendale;
- `WAREHOUSE` indica un magazzino;
- `CUSTOMER` indica un luogo cliente;
- `SUPPLIER` indica un luogo fornitore;
- `YARD` indica un piazzale operativo;
- `HUB` indica un nodo logistico;
- `PORT` indica un porto;
- `RAIL_TERMINAL` indica un terminal ferroviario;
- `INTERMODAL_TERMINAL` indica un terminal intermodale;
- `SERVICE_AREA` indica una sosta tecnica o area di servizio.

## 8.8 Il concetto di Yard

`YARD` è uno dei tipi più importanti per TruckFlow.

Un yard rappresenta un piazzale operativo, cioè un luogo interno o controllato dove avvengono movimenti tecnici.

Esempi reali:

- piazzale del deposito;
- area di scambio rimorchi;
- parcheggio camion;
- area di staging;
- zona di attesa;
- area di pre-carico;
- area di pre-scarico;
- zona dove si lascia un semirimorchio;
- area dove si aggancia o sgancia un rimorchio.

Il concetto di yard è stato separato da magazzino, deposito e cliente perché molti movimenti logistici non sono viaggi stradali completi. Sono movimenti interni o tecnici.

Un esempio futuro potrebbe essere:

```text
TripTemplateSegment
├─ type: YARD_MOVEMENT
├─ originLocationId: YARD-A
└─ destinationLocationId: YARD-B
```

Questo non significa ancora che il movimento sia stato eseguito. Significa solo che esiste una struttura descrittiva del movimento.

## 8.9 `LocationStatus`

`LocationStatus` è uno stato anagrafico.

I valori sono:

```text
ACTIVE
SUSPENDED
ARCHIVED
DISCONTINUED
```

Significato:

- `ACTIVE`: location utilizzabile nel sistema;
- `SUSPENDED`: location temporaneamente sospesa;
- `ARCHIVED`: location storica, conservata per consultazione;
- `DISCONTINUED`: location non più gestita dall'azienda.

Non sono stati di `LocationStatus`:

- `AVAILABLE`;
- `FULL`;
- `CLOSED_TODAY`;
- `BOOKED`;
- `BUSY`.

Questi ultimi sarebbero stati operativi, non anagrafici. Appartengono a moduli futuri come slot booking, warehouse operations o planning.

## 8.10 `LocationAddress`

`LocationAddress` rappresenta l'indirizzo strutturato di una location.

Contiene:

```text
LocationAddress
├─ street
├─ city
├─ postalCode
├─ province
├─ country
└─ notes
```

Non è una semplice stringa perché l'indirizzo ha parti importanti e distinte.

La normalizzazione mantiene i testi puliti e porta provincia e paese in maiuscolo.

Questa classe non fa geocoding, non consulta servizi esterni e non valida l'esistenza reale dell'indirizzo.

È solo un value object del dominio puro.

## 8.11 `GeoCoordinates`

`GeoCoordinates` rappresenta latitudine e longitudine.

Contiene:

```text
GeoCoordinates
├─ latitude
└─ longitude
```

Le coordinate sono validate nei limiti geografici fondamentali:

- latitudine tra -90 e 90;
- longitudine tra -180 e 180.

Questa classe non introduce tracking GPS, mappe, navigazione o geofencing.

Serve solo a rappresentare dove si trova una location quando questa informazione è disponibile.

## 8.12 Perché Location è un dominio separato

Separare `domain.locations` da `domain.triptemplates` è una scelta importante.

Un percorso tipo usa location, ma non le possiede.

Un magazzino potrà usare location.

Un cliente potrà usare location.

Una spedizione potrà usare location.

Un documento potrà riferirsi a una location.

Un sistema di tracking potrà confrontare una posizione reale con una location.

Per questo `Location` non deve vivere dentro `TripTemplate`.

La relazione corretta è tramite ID:

```text
TripTemplateSegment
├─ originLocationId
└─ destinationLocationId
```

In questo modo il percorso conosce i riferimenti ai luoghi, ma non ingloba l'intero aggregato Location.

## 8.13 Invarianti principali

Le invarianti principali sono:

- `id` obbligatorio;
- `code` obbligatorio;
- `name` obbligatorio;
- `type` obbligatorio;
- `status` obbligatorio;
- `address` opzionale, con default vuoto;
- `coordinates` opzionali;
- note normalizzate;
- latitudine e longitudine entro limiti validi.

Queste regole proteggono il dominio senza trasformarlo in un modulo operativo.

## 8.14 Cosa non gestisce questo dominio

`domain.locations` non gestisce:

- disponibilità banchine;
- orari di apertura;
- prenotazioni;
- chiusure temporanee;
- slot di carico e scarico;
- inventario;
- tracking GPS;
- geofencing;
- mappe;
- routing;
- code in piazzale.

Queste funzionalità verranno modellate più avanti in domini o moduli dedicati.

## 8.15 Sintesi

`domain.locations` fornisce una base pulita, riutilizzabile ed enterprise per rappresentare i luoghi del sistema.

La scelta più importante è che Location non appartiene al dominio dei percorsi, ma è un concetto autonomo.

Questo rende TruckFlow più scalabile perché lo stesso luogo potrà essere usato da percorsi tipo, spedizioni, magazzini, clienti, fornitori, documenti, pianificazione e tracking senza duplicazioni.
