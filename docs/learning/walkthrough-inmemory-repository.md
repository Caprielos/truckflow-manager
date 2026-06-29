# Walkthrough — InMemoryRepository

`InMemoryRepository<T>` è una base comune per repository in RAM.

## Cosa significa in RAM?

I dati stanno nella memoria del programma e spariscono quando il programma termina.

## Perché esiste?

Per provare gli use case senza database.

## Struttura mentale

```text
Map<String, T>
```

Esempio:

```text
"SPOT-100" → ParkingSpot
"SHP-001" → Shipment
"DRV-001" → Driver
```

## Metodi principali

```java
findById(String id)
```

cerca un oggetto.

```java
save(T aggregate)
```

salva un oggetto.

```java
findAll()
```

ritorna tutti gli oggetti salvati.

```java
clear()
```

svuota il repository.

## Perché normalizza gli ID?

Per evitare differenze tra:

```text
spot-100
SPOT-100
 Spot-100 
```

Il repository li tratta in modo coerente.
