# Assert JUnit spiegati

## `assertEquals`

```java
assertEquals(expected, actual);
```

Significa: mi aspetto che il valore reale sia uguale a quello previsto.

## `assertTrue`

```java
assertTrue(condition);
```

Significa: mi aspetto che la condizione sia vera.

## `assertFalse`

```java
assertFalse(condition);
```

Significa: mi aspetto che la condizione sia falsa.

## `assertNotNull`

```java
assertNotNull(value);
```

Significa: mi aspetto che il valore non sia null.

## `assertThrows`

```java
assertThrows(IllegalArgumentException.class, () -> metodoSbagliato());
```

Significa: mi aspetto che quel codice lanci un errore.

È utile per controllare che il domain rifiuti input impossibili.

## Come leggere un errore test

Esempio:

```text
expected: <false> but was: <true>
```

Significa:

```text
il test si aspettava false, ma il codice ha prodotto true
```

Non vuol dire per forza che il codice è sbagliato. A volte è il test che si aspettava una cosa non più corretta.
