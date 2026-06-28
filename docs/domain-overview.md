# TruckFlow Manager — Domain Overview

## Obiettivo del progetto

TruckFlow Manager è una web application gestionale pensata per aziende di trasporto merci su strada.

L’obiettivo è costruire un sistema realistico, professionale e scalabile per gestire l’intero ciclo operativo di un trasporto:

- richiesta del cliente;
- ordine di trasporto;
- preventivo;
- spedizione;
- assegnazione autista;
- assegnazione combinazione veicolo;
- verifica compatibilità carico/mezzo/autista;
- pianificazione viaggio;
- gestione soste e pause;
- monitoraggio della missione;
- gestione documenti;
- prova di consegna;
- reclami, incidenti, ritardi e audit.

Il sistema deve poter crescere nel tempo senza dover riscrivere il dominio quando verranno aggiunte nuove funzionalità.

---

## Tipo di applicazione

TruckFlow Manager sarà una web application gestionale.

L’utilizzo previsto è:

1. Un operatore accede al sistema tramite browser.
2. Crea o gestisce clienti.
3. Registra ordini di trasporto.
4. Prepara preventivi.
5. Pianifica spedizioni.
6. Assegna autisti e combinazioni di veicoli.
7. Verifica se il trasporto è eseguibile.
8. Monitora missioni, ritardi, eventi e consegne.
9. Gestisce documenti, prove di consegna e reclami.

In futuro potranno esistere anche:

- portale cliente;
- vista/app autista;
- dashboard amministrativa;
- tracking su mappa;
- integrazione con servizi esterni.

---

## Architettura generale prevista

```text
Frontend Web
    ↓
REST API
    ↓
Application Layer
    ↓
Domain
    ↓
Infrastructure
```

Il cuore del progetto è il `Domain`.

Il dominio rappresenta le regole aziendali pure, senza dipendere da tecnologie esterne.

---

## Regola architetturale principale

Il dominio non deve dipendere da:

- Spring Boot;
- database;
- JPA;
- API REST;
- Google Maps;
- frontend;
- filesystem;
- email;
- autenticazione tecnica;
- servizi esterni.

Questo significa che le classi del dominio devono essere classi Java pure.

Esempi:

- `Shipment` non deve sapere nulla del database.
- `VehicleCombination` non deve sapere nulla di Spring.
- `Route` non deve dipendere da Google Maps.
- `Driver` non deve sapere come viene fatto il login.
- `ComplianceCheck` non deve dipendere da una API esterna.

Il dominio deve poter funzionare anche senza database, senza internet e senza interfaccia grafica.

---

## Perché progettiamo prima il dominio

Il progetto è grande e realistico.  
Per evitare di dover riscrivere tutto in futuro, prima definiamo bene i concetti principali.

Esempio sbagliato:

```text
Shipment → Truck
```

Questo sarebbe troppo limitato, perché nella realtà una spedizione può usare:

- furgone singolo;
- camion rigido;
- camion con rimorchio;
- trattore stradale con semirimorchio;
- combinazioni diverse di veicoli.

La scelta corretta è:

```text
Shipment → VehicleCombination
```

In questo modo, se in futuro aggiungiamo nuovi tipi di mezzi o rimorchi, non dobbiamo cambiare tutta la struttura di `Shipment`.

---

## Differenza tra ordine, spedizione e missione

### TransportOrder

Rappresenta la richiesta iniziale del cliente.

Esempio:

> Il cliente chiede di trasportare 10 pallet da Roma a Milano.

L’ordine è ancora una richiesta commerciale e operativa.  
Non è detto che sia già stato accettato o pianificato.

### Shipment

Rappresenta la spedizione collegata al cliente e al carico.

Esempio:

> La spedizione del cliente ACME riguarda 10 pallet da Roma a Milano, con consegna entro venerdì.

La spedizione contiene:

- cliente;
- carico;
- ritiro;
- consegna;
- stato;
- documenti;
- prova di consegna;
- prezzo;
- requisiti.

### TransportMission

Rappresenta il viaggio operativo reale del mezzo.

Esempio:

> Il camion parte dal deposito, ritira merce da due clienti, consegna in tre punti diversi e poi rientra.

Una missione può contenere:

- una sola spedizione;
- più spedizioni;
- più clienti;
- più ritiri;
- più consegne;
- più carichi.

Questa distinzione rende il sistema più realistico.

---

## Flusso aziendale principale

```text
Customer
    ↓
TransportOrder
    ↓
TransportQuote
    ↓
Shipment
    ↓
TransportMission
    ↓
VehicleCombination + Driver
    ↓
TripPlan
    ↓
Tracking / Events
    ↓
ProofOfDelivery
    ↓
Closure / Claim / Billing
```

Descrizione:

1. Il cliente richiede un trasporto.
2. L’azienda registra un ordine.
3. Viene creato un preventivo.
4. Il cliente accetta il preventivo.
5. Viene creata una spedizione.
6. La spedizione viene collegata a una missione operativa.
7. Vengono assegnati autista e combinazione veicolo.
8. Il sistema controlla compatibilità e conformità.
9. Viene pianificato il viaggio.
10. La missione viene eseguita.
11. Vengono registrati eventi, ritardi o incidenti.
12. Alla consegna viene registrata la prova di consegna.
13. La spedizione viene chiusa.
14. In futuro potranno essere gestiti fatturazione, reclami e report.

---

## Struttura generale del dominio

```text
domain
├── organization
├── customer
├── order
├── pricing
├── billing
├── driver
├── fleet
├── maintenance
├── cargo
├── route
├── shipment
├── operation
├── planning
├── tracking
├── document
├── regulation
├── compliance
├── facility
├── carrier
├── availability
├── notification
├── claim
├── audit
├── sustainability
├── security
├── identity
├── configuration
├── reporting
├── location
└── shared
```

Non tutti questi package verranno implementati subito.  
Questa struttura rappresenta la visione completa del progetto.

---

## Package principali

### organization

Gestisce l’azienda che usa il gestionale: azienda, sedi, filiali, reparti e dipendenti interni.

### customer

Gestisce i clienti. Un cliente può essere persona fisica o azienda, con contratti, condizioni di pagamento e accordi di servizio.

### order

Gestisce la richiesta iniziale di trasporto. Un ordine rappresenta ciò che il cliente chiede prima che venga creata una spedizione vera e propria.

### pricing

Gestisce preventivi, costi stimati, pedaggi futuri, carburante futuro, margini e sconti.

### billing

Gestisce la futura parte di fatturazione. È separato da `pricing`.

```text
pricing = preventivo e stima
billing = fattura e pagamento
```

### driver

Gestisce autisti, patenti, CQC, ADR, disponibilità, carta tachigrafica e qualifiche.

### fleet

Gestisce tutta la flotta: furgoni, camion, motrici, trattori stradali, rimorchi, semirimorchi e combinazioni di veicoli.

### cargo

Gestisce il carico: peso, volume, dimensioni, numero pallet, tipo merce, requisiti speciali, temperatura, ADR, fragilità e sicurezza.

### shipment

Gestisce la spedizione commerciale-operativa.

### operation

Gestisce la missione operativa reale. Una missione può contenere più spedizioni.

### planning

Gestisce pianificazione viaggio, orari, soste, pause, riposi, tempi di guida, progressione tratta e posizione veicolo.

### tracking

Gestisce eventi, ritardi e incidenti.

### document

Gestisce documenti di trasporto e prova di consegna.

### regulation

Gestisce regole normative, divieti, permessi, ZTL, restrizioni e calendari di circolazione. Le regole precise saranno configurabili e dovranno essere verificate su fonti ufficiali quando implementate.

### compliance

Gestisce i controlli. È il package che risponde alla domanda:

```text
Posso eseguire questa spedizione con questo autista,
questa combinazione di veicoli, questo carico,
questa tratta e questa data?
```

### identity

Gestisce utenti, ruoli e permessi applicativi.

### audit

Gestisce lo storico delle azioni. Serve per sapere chi ha fatto cosa.

### configuration

Gestisce impostazioni e cataloghi configurabili, evitando di mettere tutto fisso nel codice.

### reporting

Gestisce report e metriche future.

### shared

Contiene value object riutilizzabili come `Money`, `Weight`, `Volume`, `Distance`, `Dimension`, `TemperatureRange`, `DateRange`, `TimeWindow`, `Percentage`, `Notes`.

---

## Regole fondamentali del dominio

1. Una spedizione non può partire senza autista.
2. Una spedizione non può partire senza combinazione veicolo.
3. Una spedizione assegna una `VehicleCombination`, non un semplice `Truck`.
4. Una combinazione veicolo può essere un furgone, un camion rigido, un camion con rimorchio o un trattore con semirimorchio.
5. Il carico deve rispettare peso, volume e dimensioni disponibili.
6. Il carico deve essere compatibile con il tipo di allestimento.
7. Il carico refrigerato richiede mezzo compatibile.
8. Il carico pericoloso richiede requisiti ADR.
9. L’autista deve avere patente compatibile con il mezzo.
10. L’autista deve avere CQC valida se richiesta.
11. L’autista deve avere ADR valida se il carico lo richiede.
12. La carta tachigrafica deve essere valida se richiesta.
13. Il mezzo non può essere assegnato se è in manutenzione.
14. Il rimorchio non può essere assegnato se non è disponibile.
15. La motrice deve essere compatibile con il rimorchio.
16. Il piano viaggio deve rispettare tempi di guida e pause.
17. I divieti di circolazione devono essere controllabili.
18. Il sistema deve distinguere spedizione e missione.
19. Una missione può contenere più spedizioni.
20. L’azienda trasporta ma non effettua carico e scarico fisico.
21. Ogni evento importante deve poter essere registrato.
22. Una spedizione consegnata non può tornare allo stato precedente.
23. Una spedizione annullata non può ripartire senza nuova procedura.
24. La prova di consegna chiude il ciclo operativo.
25. Ogni azione importante deve essere auditabile.
26. I servizi esterni devono stare fuori dal dominio.

---

## Cosa non implementiamo subito

Non implementiamo subito:

- frontend web;
- login reale;
- Spring Security;
- database;
- Google Maps;
- simulazione tracking;
- fatturazione completa;
- reportistica completa;
- notifiche reali;
- integrazione pedaggi;
- integrazione carburante;
- portale cliente;
- app autista.

Queste funzionalità verranno aggiunte in fasi successive.

---

## Decisione finale

TruckFlow Manager sarà progettato partendo dal dominio.

Il dominio deve essere stabile, indipendente e scalabile.

La regola più importante è:

```text
Il dominio rappresenta il business.
La tecnologia serve solo a farlo funzionare.
```
