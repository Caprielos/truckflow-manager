# Project Overview

## Cos’è TruckFlow Manager

TruckFlow Manager è un gestionale per autotrasporto e fleet management.

L’obiettivo non è solo registrare camion e viaggi, ma rappresentare l’intero ciclo operativo:

1. cliente richiede un trasporto;
2. il trasporto diventa ordine;
3. l’ordine accettato diventa spedizione;
4. la spedizione viene assegnata a missione;
5. la missione richiede autista, mezzo/convoglio, documenti, regole di compliance, tracking e costi;
6. la flotta viene mantenuta nel tempo con manutenzioni, gomme, carburante, telematica e sinistri.

## Filosofia del progetto

Il domain è stato costruito con una logica realistica:

- la merce determina molti vincoli;
- il mezzo non è una macro-categoria unica, ma una composizione tecnica;
- il convoglio è distinto dal singolo mezzo;
- l’autista non ha solo una patente, ma anche certificati e abilitazioni;
- l’azienda deve avere licenze;
- i documenti dipendono dal tipo di missione e dal tipo merce;
- costi, manutenzione e telematica sono parte della vita reale della flotta.

## Concetto centrale

La missione reale nasce dall’incrocio di:

```text
Cargo
+ Vehicle / VehicleCombination
+ Driver
+ Company
+ Documents
+ Route
+ Compliance
+ Pricing
```

Se anche uno di questi blocchi non è conforme, la missione non dovrebbe partire.

## Perché il domain è separato

Il domain non conosce:

- database;
- REST API;
- frontend;
- Spring;
- JPA;
- Google Maps;
- provider GPS;
- provider route cost;
- file system.

Questo permette di testare le regole in modo veloce e stabile con JUnit.
