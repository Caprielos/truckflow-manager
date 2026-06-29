# START HERE — Come leggere TruckFlow Manager

TruckFlow Manager non è più un esercizio con `Truck`, `Driver` e `Shipment` buttati insieme. È organizzato come un backend professionale, diviso in livelli.

## I quattro livelli principali

```text
src/main/java/it/gabriele/truckflow
├── domain
│   └── regole e concetti puri del business
├── application
│   └── use case, cioè azioni reali dell'app
├── infrastructure
│   └── implementazioni tecniche, oggi in memoria
└── Main.java
    └── entry point minimale, non ancora app completa
```

## La frase più importante

```text
domain = cosa è vero nel business
application = cosa il sistema sa fare
infrastructure = come salvo/carico dati
web futura = come espongo tutto via API REST
```

## Esempio semplice

Azione reale: parcheggiare un convoglio già pronto.

```text
1. Il test crea un ParkingSpot.
2. Il test crea un ParkedResource con trattore + semirimorchio.
3. Il test chiama AssignParkingSpotUseCase.
4. Il service usa ParkingSpotRepository per trovare il posto.
5. Il service crea ParkingAssignment.
6. Il service salva l'assegnazione in InMemoryParkingAssignmentRepository.
```

Questa è la logica che devi imparare a leggere.

## Non leggere tutto in ordine alfabetico

Segui questo ordine:

1. `docs/client/product-brief.md`
2. `docs/technical/architecture-complete.md`
3. `docs/learning/00-learning-path.md`
4. `docs/package-guide/package-map-explained.md`
5. `docs/scenarios/real-scenario-walkthrough.md`
6. `docs/class-reference/application-explained.md`
