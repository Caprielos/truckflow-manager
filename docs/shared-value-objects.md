# Shared Value Objects

Questo documento descrive le classi presenti nel package:

`it.gabriele.truckflow.domain.shared`

Queste classi sono Value Object condivisi dal dominio.

Un Value Object rappresenta un valore importante del dominio, valida i dati alla creazione e non cambia dopo essere stato creato.

Invece di usare primitive sparse come `double`, `String` o `BigDecimal`, usiamo classi dedicate come `Weight`, `Money`, `DateRange` e così via.

---

## Weight

Rappresenta un peso.

Internamente il valore viene salvato in chilogrammi.

Esempi:

- `Weight.ofKilograms(1200)`
- `Weight.ofTons(3.5)`

Serve per:

- peso del carico;
- portata massima del veicolo;
- limiti di peso;
- compatibilità tra carico e mezzo.

Regole principali:

- il peso non può essere negativo;
- il valore deve essere un numero valido;
- le tonnellate vengono convertite in chilogrammi.

---

## Distance

Rappresenta una distanza.

Internamente il valore viene salvato in chilometri.

Esempi:

- `Distance.ofKilometers(350)`
- `Distance.ofMeters(500)`

Serve per:

- distanza di una tratta;
- distanza tra due luoghi;
- calcolo dei costi chilometrici;
- report sui viaggi.

Regole principali:

- la distanza non può essere negativa;
- il valore deve essere un numero valido;
- i metri vengono convertiti in chilometri.

---

## Volume

Rappresenta un volume.

Internamente il valore viene salvato in metri cubi.

Esempi:

- `Volume.ofCubicMeters(12)`
- `Volume.ofLiters(500)`

Serve per:

- volume del carico;
- capacità interna di un vano;
- spazio disponibile;
- compatibilità tra carico e veicolo.

Regole principali:

- il volume non può essere negativo;
- il valore deve essere un numero valido;
- i litri vengono convertiti in metri cubi.

---

## Dimension

Rappresenta le dimensioni fisiche di un oggetto.

Contiene:

- lunghezza;
- larghezza;
- altezza.

Internamente tutti i valori sono salvati in metri.

Esempi:

- `Dimension.ofMeters(2.5, 1.2, 1.8)`
- `Dimension.ofCentimeters(250, 120, 180)`

Serve per:

- dimensioni del carico;
- dimensioni interne del veicolo;
- verifica se un carico entra in un vano;
- calcolo del volume occupato.

Regole principali:

- lunghezza, larghezza e altezza devono essere maggiori di zero;
- i valori devono essere numeri validi;
- può calcolare il volume;
- può verificare se una dimensione entra dentro un'altra.

Esempio:

`cargoDimension.fitsInside(vehicleDimension)`

---

## Money

Rappresenta un importo di denaro.

Contiene:

- importo;
- valuta.

Usa `BigDecimal` invece di `double`, perché i calcoli monetari devono essere precisi.

Esempi:

- `Money.of("150.50", "EUR")`
- `Money.of("20.00", "EUR")`

Serve per:

- prezzi;
- costi;
- preventivi;
- fatture;
- pagamenti;
- tariffe.

Regole principali:

- l'importo non può essere negativo;
- l'importo non può essere nullo;
- la valuta non può essere nulla;
- non si possono sommare importi con valute diverse;
- non si possono confrontare importi con valute diverse.

Esempio non valido:

`Money.of("100", "EUR").add(Money.of("50", "USD"))`

---

## Percentage

Rappresenta una percentuale.

Internamente usa `BigDecimal`.

Esempi:

- `Percentage.of("10")`
- `Percentage.of("25.5")`

Serve per:

- sconti;
- margini;
- commissioni;
- percentuali nei report;
- metriche operative.

Regole principali:

- il valore non può essere nullo;
- il valore non può essere negativo;
- il valore non può essere maggiore di 100;
- può essere convertito in moltiplicatore decimale.

Esempio:

`Percentage.of("25").toMultiplier()`

Risultato logico:

`0.25`

---

## TemperatureRange

Rappresenta un intervallo di temperatura.

Internamente usa gradi Celsius.

Esempi:

- `TemperatureRange.ofCelsius(2, 8)`
- `TemperatureRange.ofCelsius(0, 10)`

Serve per:

- merce refrigerata;
- alimenti;
- farmaci;
- veicoli frigo;
- controlli di compatibilità tra carico e mezzo.

Regole principali:

- la temperatura minima non può essere maggiore della massima;
- i valori devono essere numeri validi;
- può verificare se una temperatura è dentro l'intervallo;
- può verificare se un intervallo richiesto è coperto da un intervallo disponibile.

Esempio:

`requiredRange.isCoveredBy(vehicleRange)`

---

## TimeWindow

Rappresenta una finestra oraria nella giornata.

Usa `LocalTime`.

Esempi:

- `TimeWindow.of("08:00", "12:00")`
- `TimeWindow.of("14:00", "18:00")`

Serve per:

- fasce di ritiro;
- fasce di consegna;
- orari di apertura magazzino;
- appuntamenti;
- disponibilità operative.

Regole principali:

- l'orario di inizio è obbligatorio;
- l'orario di fine è obbligatorio;
- l'inizio deve essere precedente alla fine;
- può verificare se un orario è dentro la finestra;
- può verificare sovrapposizioni tra finestre orarie;
- può verificare se una finestra è contenuta dentro un'altra.

Nota importante:

`TimeWindow` non gestisce il fuso orario.

Il fuso orario verrà gestito più avanti da classi come `Location`, `Facility`, `TrackingEvent` o `TransportMission`.

---

## DateRange

Rappresenta un intervallo di date.

Usa `LocalDate`.

Esempi:

- `DateRange.of("2026-01-01", "2026-12-31")`
- `DateRange.of("2026-03-01", "2026-06-30")`

Serve per:

- validità patente;
- validità CQC;
- validità assicurazione;
- validità contratti;
- disponibilità autisti;
- periodi di manutenzione;
- permessi.

Regole principali:

- la data di inizio è obbligatoria;
- la data di fine è obbligatoria;
- la data di inizio non può essere successiva alla data di fine;
- può verificare se una data è dentro l'intervallo;
- può verificare sovrapposizioni tra intervalli;
- può verificare se un intervallo è contenuto dentro un altro;
- può calcolare il numero di giorni inclusi.

Nota importante:

`DateRange` non gestisce orari o fusi orari.

Serve solo per periodi basati su date di calendario.

---

## Notes

Rappresenta note testuali del dominio.

Esempi:

- `Notes.of("Merce fragile")`
- `Notes.empty()`

Serve per:

- note su spedizioni;
- istruzioni operative;
- note cliente;
- note su carico o consegna;
- commenti interni.

Regole principali:

- il testo non può essere nullo;
- gli spazi iniziali e finali vengono rimossi;
- il testo può essere vuoto;
- la lunghezza massima è 2000 caratteri;
- può verificare se contiene una parola o frase.

Esempio:

`notes.contains("fragile")`

---

## Riepilogo

| Classe | Cosa rappresenta |
|---|---|
| `Weight` | Peso |
| `Distance` | Distanza |
| `Volume` | Volume |
| `Dimension` | Lunghezza, larghezza e altezza |
| `Money` | Importo economico con valuta |
| `Percentage` | Percentuale |
| `TemperatureRange` | Intervallo di temperatura |
| `TimeWindow` | Finestra oraria |
| `DateRange` | Intervallo di date |
| `Notes` | Note testuali |

---

## Perché queste classi sono importanti

Senza questi Value Object, il dominio userebbe primitive sparse:

- `double weight`
- `double distance`
- `String notes`
- `BigDecimal price`

Questo renderebbe più facile commettere errori.

Con i Value Object, invece, il codice diventa più chiaro:

- `Weight weight`
- `Distance distance`
- `Notes notes`
- `Money price`

Ogni concetto del dominio ha così le proprie regole e il proprio significato.
