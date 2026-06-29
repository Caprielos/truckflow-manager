# Walkthrough - InMemoryRepository<T>

`InMemoryRepository<T>` è una classe generica per salvare oggetti in RAM.

## Campo principale

```java
private final ConcurrentMap<String, T> storage = new ConcurrentHashMap<>();
```

Significa:

```text
ho una mappa che collega un id stringa a un oggetto T.
```

## idExtractor

```java
private final Function<T, String> idExtractor;
```

Serve per sapere come estrarre l’id da un oggetto.

Esempio:

```java
new InMemoryRepository<>(ParkingSpot::getFullCode)
```

## save

Salva l’oggetto dentro la mappa.

## findById

Cerca nella mappa usando l’id.

## findAll

Restituisce tutti gli oggetti salvati.

## normalizeId

Converte gli id in maiuscolo e rimuove spazi. Così `spot-1` e `SPOT-1` vengono trattati in modo coerente.
