# Regole domain principali

Le regole domain sono distribuite in classi `Rules`.

Esempi:

```text
CargoLoadRules
CargoOperationalRules
VehicleCombinationRules
DriverRules
TransportMissionRules
EconomicsRules
DriverPayrollRules
InventoryRules
ParkingRules
DocumentRules
DispatchRules
```

## Idea centrale

Le regole devono stare nel domain, non negli use case.

L'use case deve solo:

```text
caricare dati
chiamare factory/metodi domain
salvare risultato
```

## Esempi

### ParkingRules

Verifica se una risorsa parcheggiata è un convoglio pronto.

### EconomicsRules

Verifica profitto, perdita, cassa negativa e sostenibilità economica.

### DriverPayrollRules

Calcola il costo autista considerando ore, premi, ADR, patenti, tipi di convoglio e contributi.

### InventoryRules

Stabilisce riordino e stock minimo.

### DispatchRules

Aiuta a capire se una missione è pronta e compatibile.
