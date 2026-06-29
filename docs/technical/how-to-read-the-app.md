# Come leggere l'app senza perdersi

## Prima domanda: che livello sto guardando?

Quando apri una classe, guarda subito il package.

```text
it.gabriele.truckflow.domain...
```

Vuol dire: regola o concetto del business.

```text
it.gabriele.truckflow.application...
```

Vuol dire: caso d'uso o contratto applicativo.

```text
it.gabriele.truckflow.infrastructure...
```

Vuol dire: implementazione tecnica.

## Seconda domanda: questa classe conserva dati o fa regole?

Esempi:

```text
Vehicle, Driver, ParkingAssignment
→ conservano dati e comportamenti principali

VehicleCombinationRules, InventoryRules
→ fanno controlli/regole
```

## Terza domanda: è una interfaccia o una implementazione?

```text
AssignParkingSpotUseCase
→ interfaccia, dice cosa si può fare

DefaultAssignParkingSpotUseCase
→ implementazione, dice come lo fa
```

## Quarta domanda: se vedo Repository?

```text
ParkingSpotRepository
→ porta, non salva direttamente

InMemoryParkingSpotRepository
→ implementazione in RAM
```

## Metodo pratico di lettura

Per capire uno use case, apri in questo ordine:

1. `application/port/in/...UseCase.java`
2. `application/usecase/Default...UseCase.java`
3. repository port usati dal service
4. domain object creati/usati dal service
5. test relativo
