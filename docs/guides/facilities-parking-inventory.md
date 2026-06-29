# Facility, parking e inventory

## Facility

Package:

```text
src/main/java/it/gabriele/truckflow/domain/facility
```

Rappresenta strutture fisiche:

- deposito;
- piazzale;
- magazzino;
- parcheggio aziendale;
- parcheggio esterno;
- struttura di proprietà;
- struttura in affitto;
- struttura in leasing.

Classi principali:

```text
Facility
FacilityFinancialProfile
FacilityCostLine
FacilityCostType
FacilityOwnershipType
FacilityCostFrequency
```

## Spese struttura

Esempi di spese:

```text
affitto
tasse proprietà
utenze
elettricità
acqua
sicurezza
videosorveglianza
manutenzione piazzale
pulizia
assicurazione
condominio
ammortamento immobile
```

Se la struttura è di proprietà non paga canone di affitto, ma può avere tasse, manutenzione, assicurazione, utenze e ammortamento.

Se è in affitto, può avere canone, cauzione, utenze e servizi.

## Parking

Package:

```text
src/main/java/it/gabriele/truckflow/domain/parking
```

Rappresenta posti numerati e risorse parcheggiate.

Classi principali:

```text
ParkingSpot
ParkedResource
ParkingAssignment
ParkingRules
ParkingSpotType
ParkingResourceType
ParkingSpotStatus
```

Casi gestiti:

```text
posto 100 → furgone
posto A12 → trattore + semirimorchio agganciati
posto B20 → autotreno completo
posto C01 → rimorchio solo
posto D04 → attrezzatura
```

## Inventory

Package:

```text
src/main/java/it/gabriele/truckflow/domain/inventory
```

Rappresenta il magazzino vero:

- ricambi;
- gomme;
- DPI;
- olio;
- AdBlue;
- filtri;
- cinghie;
- attrezzature ADR;
- pallet;
- materiali consumabili.

Classi principali:

```text
InventoryItem
InventoryBalance
InventoryStockMovement
WarehouseLocation
InventoryRules
```

La regola di reorder serve quando la giacenza scende sotto la soglia minima, soprattutto per articoli safety-critical.
