# TruckFlow Manager — Java Coding Guidelines

## Scopo

Regole per scrivere codice Java coerente nel progetto.

## Lingua

Nel codice Java usare inglese per:

- classi;
- metodi;
- attributi;
- enum;
- package;
- test.

Commenti brevi possono essere in italiano se spiegano il motivo di una scelta.

## Package

Package root:

```text
it.gabriele.truckflow
```

Domain root:

```text
it.gabriele.truckflow.domain
```

## Convenzioni nomi

- Classi: `PascalCase`
- Metodi/variabili: `camelCase`
- Enum values: `UPPER_SNAKE_CASE`
- Package: lowercase

## Incapsulamento

- attributi `private`;
- value object immutabili;
- preferire `final` dove possibile;
- evitare setter generici nel domain.

## Metodi di dominio

Preferire:

```java
shipment.plan()
shipment.dispatch()
invoice.issue()
claim.startReview()
```

Evitare:

```java
setStatus(...)
setDriver(...)
setVehicle(...)
```

## Validazione

Gli oggetti devono nascere validi.

Usare:

- `IllegalArgumentException` per parametri non validi;
- `IllegalStateException` per transizioni non consentite.

## Entity

Entity con identità propria.

Esempi:

- `Shipment`;
- `Driver`;
- `Vehicle`;
- `Invoice`;
- `TransportMission`.

## Value Object

Oggetti senza identità propria.

Esempi:

- `Money`;
- `Weight`;
- `DateRange`;
- `TimeWindow`.

Regole:

- no setter;
- validazione nel costruttore/factory;
- equals/hashCode per valore.

## Domain Service / Rules

Usare classi `Rules` quando una regola coinvolge più oggetti.

Esempi:

- `DriverRules`;
- `VehicleCombinationRules`;
- `ComplianceRules`;
- `BillingRules`.

## toString

Non stampare dati sensibili:

- password;
- token;
- API key;
- documenti riservati.

## Lombok

Non usare Lombok per ora.

Motivi:

- imparare Java;
- mantenere codice esplicito;
- evitare regole nascoste.

## Spring/JPA

Non importare Spring o JPA nel domain.

No:

```java
@Entity
@Service
@Repository
@Autowired
```

## Test

Ogni regola importante deve avere test.

Comando:

```bash
mvn clean test
```
