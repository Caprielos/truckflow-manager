# Infrastructure memory

Il package:

```text
src/main/java/it/gabriele/truckflow/infrastructure/memory
```

contiene implementazioni in memoria dei repository.

## Classe base

`InMemoryRepository<T>` contiene la logica comune:

- `findById`;
- `save`;
- `findAll`;
- `deleteById`;
- `clear`.

## Repository specifici

Ogni repository specifico estende o usa la logica base.

Esempi:

- `InMemoryParkingSpotRepository`;
- `InMemoryVehicleRepository`;
- `InMemoryDriverRepository`;
- `InMemoryShipmentRepository`;
- `InMemoryTransportMissionRepository`.

## Perché sono utili

Permettono di provare gli use case senza database.

## Limite

Non sono persistenti: quando l'app finisce, i dati spariscono.
