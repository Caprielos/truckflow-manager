# TruckFlow Manager — Java Coding Guidelines

## Scopo

Questo documento definisce le regole di scrittura del codice Java per TruckFlow Manager.

L’obiettivo è scrivere codice:

- leggibile;
- professionale;
- coerente;
- manutenibile;
- facile da testare;
- vicino alle regole del dominio.

Queste linee guida saranno usate soprattutto per le classi del `domain`.

---

# 1. Lingua usata nel codice

Nel codice Java useremo l’inglese per:

- nomi delle classi;
- nomi dei metodi;
- nomi degli attributi;
- nomi dei package;
- nomi degli enum;
- nomi dei test.

Esempi:

```java
Shipment
Driver
Cargo
VehicleCombination
WeightComplianceCheck
```

Non useremo nomi italiani come:

```java
Spedizione
Autista
Camion
Carico
```

## Commenti

I commenti brevi possono essere in italiano.

Esempio:

```java
// Controlla che il carico non superi la capacità del mezzo.
```

I commenti devono aiutare a ricordare il motivo di una scelta, non spiegare cose ovvie.

Commento utile:

```java
// Una spedizione usa una combinazione veicolo, non un singolo camion.
```

Commento inutile:

```java
// Restituisce il nome.
```

Se un metodo si chiama `getName()`, è già chiaro cosa fa.

---

# 2. Convenzioni sui nomi

## PascalCase

PascalCase significa che ogni parola inizia con la lettera maiuscola.

Si usa per:

- classi;
- enum;
- interfacce;
- record.

Esempi:

```java
Shipment
DriverLicense
VehicleCombination
WeightComplianceCheck
TransportMission
```

Esempio sbagliato:

```java
vehicleCombination
driver_license
vehicle_combination
```

---

## camelCase

camelCase significa che la prima parola inizia minuscola, mentre le parole successive iniziano maiuscole.

Si usa per:

- metodi;
- attributi;
- variabili locali;
- parametri.

Esempi:

```java
assignDriver()
markAsDelivered()
vehicleCombination
driverLicense
totalWeight
```

Esempio sbagliato:

```java
AssignDriver()
vehicle_combination
TotalWeight
```

---

## UPPER_SNAKE_CASE

UPPER_SNAKE_CASE significa tutto maiuscolo, con parole separate da `_`.

Si usa per:

- costanti;
- valori degli enum.

Esempi:

```java
MAX_ALLOWED_WEIGHT
DEFAULT_CURRENCY
```

Valori enum:

```java
CREATED
ASSIGNED
IN_TRANSIT
DELIVERED
CANCELLED
```

---

## Package

I package devono essere scritti in minuscolo.

Esempi:

```java
com.truckflow.domain.shipment
com.truckflow.domain.driver
com.truckflow.domain.fleet
```

Esempio sbagliato:

```java
com.TruckFlow.Domain.Shipment
```

---

# 3. Incapsulamento

Gli attributi delle classi devono essere privati.

Esempio corretto:

```java
private String name;
private ShipmentStatus status;
```

Esempio sbagliato:

```java
public String name;
public ShipmentStatus status;
```

L’oggetto deve proteggere i propri dati.

---

# 4. Getters e setters

A scuola spesso si usa IntelliJ per generare automaticamente:

- getter;
- setter;
- `toString`;
- `equals`;
- `hashCode`;
- `compareTo`.

Non è sempre sbagliato, ma nel domain non bisogna generarli tutti automaticamente senza ragionare.

## Getter

I getter sono spesso accettabili.

Esempio:

```java
public ShipmentStatus getStatus() {
    return status;
}
```

Servono per leggere lo stato dell’oggetto.

## Setter

I setter non devono essere creati ovunque.

Esempio pericoloso:

```java
public void setStatus(ShipmentStatus status) {
    this.status = status;
}
```

Questo è pericoloso perché permette di cambiare lo stato della spedizione senza rispettare le regole.

Per esempio, qualcuno potrebbe fare:

```java
shipment.setStatus(ShipmentStatus.DELIVERED);
```

anche se la spedizione non è mai partita.

Nel domain è meglio usare metodi con significato reale:

```java
assignDriver(driver)
assignVehicleCombination(vehicleCombination)
markAsPlanned()
markAsInTransit()
markAsDelivered()
cancel(reason)
```

Così dentro il metodo possiamo controllare le regole.

Esempio concettuale:

```java
public void markAsDelivered() {
    if (status != ShipmentStatus.IN_TRANSIT) {
        throw new IllegalStateException("Solo una spedizione in transito può essere consegnata.");
    }

    this.status = ShipmentStatus.DELIVERED;
}
```

---

# 5. Regola pratica su getter e setter

## Entity

Per le Entity:

- getter sì, quando servono;
- setter pochi;
- preferire metodi di dominio;
- proteggere i cambi di stato importanti.

Esempi di Entity:

```java
Shipment
Driver
VehicleCombination
Customer
TransportMission
```

## Value Object

Per i Value Object:

- niente setter;
- attributi `final` quando possibile;
- oggetti immutabili;
- getter sì;
- valori validati nel costruttore.

Esempi di Value Object:

```java
Weight
Money
Dimension
TimeWindow
TemperatureRange
```

## Enum

Per gli Enum:

- niente setter;
- valori fissi;
- eventuali attributi solo se servono davvero.

---

# 6. toString

`toString()` può essere utile per debug e log.

Non deve però stampare dati sensibili.

Esempio da evitare:

```java
passwordHash
token
dati personali inutili
documenti riservati
```

Per `UserAccount`, per esempio, non bisogna mai stampare password o token.

Regola:

```text
toString va bene, ma solo con dati sicuri e utili.
```

---

# 7. equals e hashCode

`equals()` e `hashCode()` non vanno generati a caso.

## Value Object

Nei Value Object ha senso confrontare tutti i valori.

Esempio:

```java
new Weight(100, KG)
```

è uguale a un altro `Weight` con stesso valore e stessa unità.

Quindi nei Value Object `equals` e `hashCode` sono importanti.

## Entity

Nelle Entity bisogna stare più attenti.

Una Entity ha identità propria.

Due `Driver` con stesso nome e cognome non sono per forza lo stesso autista.

Due `Truck` con stessi dati non sono per forza lo stesso mezzo.

Nelle Entity, `equals` e `hashCode` di solito si basano sull’id, ma bisogna fare attenzione quando l’id non è ancora stato assegnato.

Regola pratica iniziale:

```text
Nei Value Object implementiamo equals/hashCode.
Nelle Entity li implementiamo solo quando siamo sicuri della strategia sugli id.
```

---

# 8. compareTo

`compareTo()` serve solo se una classe ha un ordinamento naturale.

Esempi dove può avere senso:

```java
Weight
Distance
Money
```

Esempi dove non è sempre chiaro:

```java
Shipment
Driver
Truck
```

Una spedizione si ordina per data? Per priorità? Per stato?  
Dipende dal caso.

Quindi non generiamo `compareTo()` automaticamente.

Regola:

```text
compareTo si crea solo quando esiste un ordinamento naturale chiaro.
```

---

# 9. Costruttori

Gli oggetti devono nascere validi.

Esempio: un peso negativo non deve essere permesso.

Esempio concettuale:

```java
public Weight(double value, WeightUnit unit) {
    if (value < 0) {
        throw new IllegalArgumentException("Il peso non può essere negativo.");
    }

    this.value = value;
    this.unit = unit;
}
```

Non dobbiamo creare oggetti vuoti e poi riempirli con 20 setter.

Esempio da evitare nel domain:

```java
Shipment shipment = new Shipment();
shipment.setCustomer(customer);
shipment.setCargo(cargo);
shipment.setStatus(CREATED);
```

Meglio creare oggetti già coerenti.

---

# 10. final

Quando un attributo non deve cambiare dopo la creazione, usiamo `final`.

Esempio:

```java
private final double value;
private final WeightUnit unit;
```

Questo è molto utile nei Value Object.

---

# 11. Validazione

Le classi devono proteggere i dati importanti.

Esempi:

- `Weight` non può essere negativo;
- `Money` non può avere valuta vuota;
- `TimeWindow` deve avere inizio prima della fine;
- `Shipment` non può diventare `DELIVERED` se non è `IN_TRANSIT`;
- `VehicleCombination` deve avere almeno un mezzo motorizzato.

---

# 12. Metodi di dominio

Nel domain preferiamo metodi che rappresentano azioni reali.

Esempi buoni:

```java
assignDriver(driver)
assignVehicleCombination(vehicleCombination)
markAsInTransit()
markAsDelivered()
cancel(reason)
addCargoItem(item)
recordDelay(delayReport)
```

Esempi più deboli:

```java
setDriver(driver)
setVehicleCombination(vehicleCombination)
setStatus(status)
```

I metodi di dominio rendono il codice più leggibile e proteggono le regole aziendali.

---

# 13. Eccezioni

Quando una regola viene violata, possiamo usare eccezioni.

All’inizio useremo eccezioni standard come:

```java
IllegalArgumentException
IllegalStateException
```

Esempi:

- `IllegalArgumentException`: parametro non valido;
- `IllegalStateException`: stato dell’oggetto non permette l’operazione.

In futuro potremo creare eccezioni specifiche del dominio.

---

# 14. Record Java

I `record` possono essere utili per Value Object semplici.

Esempio futuro:

```java
public record Weight(double value, WeightUnit unit) {
}
```

Però anche nei record bisogna validare i dati.

All’inizio possiamo usare classi normali, così capiamo meglio costruttori, attributi e metodi.

---

# 15. Lombok

Non useremo Lombok all’inizio.

Anche se Lombok può generare getter, costruttori, `equals`, `hashCode` e `toString`, per ora è meglio scrivere il codice manualmente.

Motivo:

- impariamo meglio Java;
- capiamo cosa viene generato;
- evitiamo di nascondere regole importanti;
- manteniamo il domain più chiaro.

In futuro si potrà valutare.

---

# 16. IntelliJ Generate Code

Il comando di IntelliJ “Generate” è utile, ma va usato con attenzione.

Possiamo usarlo per:

- creare getter;
- creare costruttori;
- creare `equals/hashCode` nei Value Object;
- creare `toString` quando serve.

Non lo useremo per generare automaticamente setter ovunque.

Regola:

```text
Generate Code va bene.
Generate Everything senza ragionare no.
```

---

# 17. Test

Ogni regola importante deve avere un test.

Esempi:

- un peso negativo non è valido;
- una spedizione non può essere consegnata se non è in transito;
- un carico troppo pesante non è compatibile;
- un autista senza patente corretta non può essere assegnato;
- un rimorchio non disponibile non può essere usato.

I test servono anche come documentazione viva del comportamento del sistema.

---

# 18. Stile generale

Regole pratiche:

- classi piccole quando possibile;
- una responsabilità chiara per classe;
- nomi descrittivi;
- evitare abbreviazioni inutili;
- evitare metodi troppo lunghi;
- evitare classi che fanno troppe cose;
- non mischiare dominio e tecnologia;
- scrivere codice semplice prima di renderlo sofisticato.

---

# 19. Riassunto rapido

## Fare

- nomi in inglese;
- commenti brevi in italiano;
- attributi privati;
- value object immutabili;
- getter quando servono;
- metodi di dominio per cambiare stato;
- test per le regole;
- validazione nei costruttori;
- enum per valori chiusi.

## Evitare

- attributi pubblici;
- setter ovunque;
- oggetti vuoti riempiti dopo;
- `toString` con dati sensibili;
- `equals/hashCode` generati senza criterio;
- `compareTo` senza ordinamento naturale;
- dipendenze da Spring nel domain;
- logica di business nei controller futuri.

---

# Decisione finale

Nel domain di TruckFlow Manager non genereremo automaticamente tutto per ogni classe.

Useremo il codice generato da IntelliJ solo quando ha senso.

La regola principale è:

```text
Il domain deve proteggere le regole aziendali, non solo contenere dati.
```
