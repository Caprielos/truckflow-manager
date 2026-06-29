# Repository e memoria

## Repository

Un repository è una classe/interfaccia che rappresenta una raccolta di oggetti salvati.

Esempio:

```java
ParkingSpotRepository
```

Significa: posso cercare e salvare posti parcheggio.

## Repository port

Nel package `application/port/out`, i repository sono solo interfacce.

Non dicono ancora dove stanno i dati.

## InMemory repository

Nel package `infrastructure/memory`, i repository salvano dati in RAM.

Esempio:

```java
InMemoryParkingSpotRepository
```

Serve per test e demo.

## RAM non è database

I dati in memoria spariscono quando il programma finisce.

Però sono utilissimi perché permettono di testare gli use case senza PostgreSQL, file, server o Spring.

## Map

Alla base c'è una struttura tipo:

```text
id → oggetto
```

Esempio:

```text
"SPOT-100" → ParkingSpot
"DRV-001" → Driver
```

## Perché usare memoria prima del database?

Perché prima vogliamo capire se la logica funziona. Il database arriva dopo.
