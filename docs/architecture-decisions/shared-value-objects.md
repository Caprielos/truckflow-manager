# TruckFlow Manager — Shared Value Objects

Package:

```text
it.gabriele.truckflow.domain.shared
```

## Scopo

I value object condivisi evitano primitive sparse come `double`, `String` o `BigDecimal` senza significato.

## Weight

Peso in chilogrammi.

Factory:

```java
Weight.ofKilograms(1200)
Weight.ofTons(3.5)
```

Regole:

- non negativo;
- no NaN;
- no infinito.

## Distance

Distanza in chilometri.

Factory:

```java
Distance.ofKilometers(350)
Distance.ofMeters(500)
```

Regole:

- non negativa;
- no NaN;
- no infinito.

## Volume

Volume in metri cubi.

Factory:

```java
Volume.ofCubicMeters(12)
Volume.ofLiters(500)
```

Regole:

- non negativo;
- conversione litri → m³.

## Dimension

Dimensioni fisiche in metri:

- length;
- width;
- height.

Factory:

```java
Dimension.ofMeters(2.5, 1.2, 1.8)
Dimension.ofCentimeters(250, 120, 180)
```

Regole:

- valori > 0;
- calcolo volume;
- `fitsInside`.

Nota: non ruota automaticamente il carico.

## Money

Importo con valuta.

Factory:

```java
Money.of("150.50", "EUR")
```

Regole:

- importo non negativo;
- valuta obbligatoria;
- add/subtract/compare solo stessa valuta;
- sottrazione non può produrre importo negativo.

## Percentage

Percentuale 0-100.

Factory:

```java
Percentage.of("15")
```

Regole:

- non negativa;
- massimo 100;
- conversione in moltiplicatore.

## TemperatureRange

Range in Celsius.

Factory:

```java
TemperatureRange.ofCelsius(2, 8)
```

Regole:

- min <= max;
- contains;
- isCoveredBy.

## TimeWindow

Finestra oraria con `LocalTime`.

Regole:

- start obbligatorio;
- end obbligatorio;
- start < end;
- contains;
- overlaps;
- end-touch non è overlap.

## DateRange

Intervallo di date con `LocalDate`.

Regole:

- start obbligatorio;
- end obbligatorio;
- start <= end;
- contains;
- overlaps inclusivo;
- daysInclusive.

## Notes

Note testuali.

Factory:

```java
Notes.of("Merce fragile")
Notes.empty()
```

Regole:

- testo non null;
- trim;
- max 2000 caratteri;
- vuoto consentito;
- `hasText`;
- contains case-insensitive.
