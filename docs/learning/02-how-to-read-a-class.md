# Come leggere una classe Java

Prendiamo una struttura tipica.

```java
public final class Example {
    private final String code;

    public Example(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
```

## 1. Nome della classe

```java
public final class Example
```

Dice che esiste un tipo chiamato `Example`.

## 2. Campi

```java
private final String code;
```

Un campo è un dato conservato dentro l'oggetto.

Nel progetto trovi spesso campi come:

```text
missionNumber
shipment
vehicleCombination
notes
status
```

## 3. Costruttore

```java
public Example(String code)
```

Il costruttore crea l'oggetto e riceve i dati iniziali.

## 4. `this`

```java
this.code = code;
```

`this.code` è il campo dell'oggetto. `code` è il parametro del costruttore.

## 5. Getter

```java
public String getCode()
```

Un getter permette di leggere un campo privato.

## 6. Factory method

Nel progetto spesso trovi metodi tipo:

```java
ParkingAssignment.active(...)
```

Sono metodi statici che creano oggetti con nomi più leggibili di `new`.

## 7. Metodi di business

Esempio:

```java
mission.complete(...)
```

Non è solo un setter. Cambia lo stato rispettando regole.

## Regola pratica

Quando leggi una classe, chiediti:

```text
Che concetto reale rappresenta?
Quali dati conserva?
Quali regole protegge?
Quali metodi pubblici espone?
Quale test la verifica?
```
