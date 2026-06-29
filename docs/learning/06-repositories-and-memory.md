# 06 - Repository e memoria

## Repository

Un repository è un oggetto che salva e carica altri oggetti.

Esempio:

```java
ParkingSpotRepository
```

può trovare un posto parcheggio.

## Repository in memoria

`InMemoryParkingSpotRepository` salva i dati in RAM.

Internamente la classe base usa:

```java
ConcurrentHashMap<String, T>
```

## Perché usarlo

Perché permette di provare gli use case senza database.

## Limite

Se chiudi il programma, i dati spariscono.
