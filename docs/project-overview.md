# Project overview

TruckFlow Manager è pensato come base backend Java per un gestionale di trasporti e flotta.

## Obiettivo

Rappresentare in modo realistico le regole di un'azienda che gestisce:

- clienti e ordini;
- spedizioni e missioni;
- autisti, patenti, CQC, ADR e qualifiche;
- veicoli, rimorchi, semirimorchi, combinazioni e certificati;
- cargo secco, frigo, ADR, rifiuti, animali vivi, alimentare e merci speciali;
- costi, ricavi, IVA, asset acquistati, debiti, utile/perdita;
- magazzino ricambi, gomme, attrezzature e materiali;
- depositi, piazzali, parcheggi, posti numerati e convogli già pronti;
- manutenzione, fuel, gomme, telematica, tracking e documenti.

## Stato tecnico

Il progetto attuale è volontariamente concentrato sul domain layer. Questo significa:

```text
presente: entità, value object, enum, regole di business, test
assente: Spring, REST API, database, JPA, frontend, login reale, servizi esterni
```

Questa scelta è corretta per costruire prima il motore logico del progetto e poi appoggiarci application layer e infrastruttura.

## Perché è realistico

Il modello evita una struttura troppo semplice tipo:

```java
Shipment {
    Driver driver;
    Truck truck;
    Cargo cargo;
}
```

Al contrario distingue responsabilità reali:

```text
Order = richiesta commerciale
Shipment = spedizione nata dall'ordine
TransportMission = viaggio operativo reale
Fleet = mezzi e combinazioni
Driver = persona/abilitazioni/stato
Payroll = costo lavoro autista
Economics = costi/ricavi/IVA/margine
Facility/Parking = dove stanno fisicamente mezzi e rimorchi
Inventory = scorte e magazzino
Dispatch = scelta operativa delle risorse
```
