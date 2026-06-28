# Project Overview

## Obiettivo

**TruckFlow Manager** vuole rappresentare il cuore di un sistema TMS/FMS realistico, cioè un software per gestire trasporti, spedizioni, mezzi, autisti, documenti, costi e operazioni quotidiane di una flotta.

Il progetto non è un semplice CRUD. Il valore principale è nel dominio:

- validare dati importanti;
- modellare i concetti reali del trasporto;
- impedire stati incoerenti;
- preparare regole riutilizzabili dall'application layer;
- separare ciò che è commerciale da ciò che è operativo;
- mantenere indipendenza da database e framework.

## Cosa contiene adesso

Il dominio copre:

- clienti, contatti e account;
- ordini di trasporto;
- spedizioni;
- missioni operative;
- carichi e merci speciali;
- ADR, temperatura controllata, rifiuti, animali vivi, liquidi alimentari;
- flotta con veicoli, allestimenti, assi, masse, dimensioni, agganci e certificati;
- convogli: veicolo singolo, autotreno, articolato;
- autisti con patenti, CQC, ADR, qualifiche e certificati a scadenza;
- azienda di trasporto e licenze operative;
- documenti di trasporto;
- disponibilità risorse;
- tracking operativo;
- telematica;
- carburante;
- pneumatici;
- manutenzione e downtime;
- fissaggio carico;
- reclami e danni;
- pricing e billing;
- sostenibilità;
- notifiche;
- audit;
- identity;
- configuration;
- reporting.

## Numeri del progetto documentato

- Package domain: **31**
- Classi Java nel domain: **187**
- Test class rilevate: **74**
- Test rilevati dai report presenti nello zip: **788**
- Failure/Error nei report presenti: **0**

Nota: la documentazione è stata generata analizzando il codice sorgente e i report già presenti nello zip. Nel mio ambiente non è disponibile Maven, quindi il comando finale `mvn clean test` va sempre eseguito localmente.

## Filosofia progettuale

Il dominio deve essere abbastanza realistico da sembrare un sistema aziendale vero, ma non deve diventare confuso. Ogni package deve avere una responsabilità precisa.

Esempio:

```text
Shipment  = spedizione commerciale/logistica
Mission   = esecuzione operativa reale
Vehicle   = mezzo fisico
Tire      = gomma fisica tracciabile
Document  = evidenza/documento richiesto
Compliance = regole che incrociano requisiti diversi
```
