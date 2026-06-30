# 6. Decisioni architetturali del dominio

## 6.1 Decisione: dominio puro prima di infrastruttura

Il progetto è stato costruito partendo dal dominio puro.

Questa scelta permette di definire prima il linguaggio del business e solo dopo collegare database, API, servizi esterni e interfacce.

Il dominio non deve sapere come viene salvato, esposto o serializzato.

## 6.2 Decisione: separare User da Operational

`User` rappresenta l’account applicativo.

Le figure operative rappresentano persone reali nel contesto aziendale.

Questa distinzione evita che il login diventi il centro del dominio operativo.

Un account può essere collegato a un autista, a un dispatcher o a un manager tramite `UserId`, ma non ingloba quei concetti.

## 6.3 Decisione: usare Qualification come catalogo unico

Patenti, CQC, ADR, ATP, HACCP e corsi interni sono tutti trattati come qualificazioni.

Non è stato creato un `LicenseType` separato perché avrebbe duplicato il concetto di abilitazione.

Il catalogo dice cosa esiste. Il dominio operativo dice chi possiede cosa.

## 6.4 Decisione: niente scadenze nel catalogo

Una qualificazione del catalogo non scade.

Scade il documento o l’abilitazione posseduta da una persona.

Per questo il catalogo non contiene date, file o validità.

## 6.5 Decisione: usare OperationalCode e FleetCode

Sono stati introdotti codici aziendali leggibili:

- `OperationalCode` per le figure operative;
- `FleetCode` per i veicoli.

Questi codici sono diversi dagli ID tecnici del dominio.

Gli ID servono al sistema. I codici servono agli utenti aziendali.

## 6.6 Decisione: VehicleUnit e VehicleCombination

Il dominio veicoli distingue tra singola unità e combinazione.

Questa scelta è fondamentale per il trasporto reale.

Un bilico non è un veicolo singolo, ma una combinazione tra trattore e semirimorchio.

Un autotreno non è una motrice, ma una combinazione tra motrice e rimorchio.

## 6.7 Decisione: allestimento separato dal tipo unità

`VehicleUnitType` descrive la natura fisica del mezzo.

`VehicleBodyType` descrive l’allestimento.

Quindi frigo, cisterna, bisarca e centinato non sono tipi principali di veicolo, ma allestimenti.

Questa scelta evita enum confusionari e migliora la scalabilità.

## 6.8 Decisione: profili specifici invece di Map generica

Per gli allestimenti speciali non è stata usata una mappa generica.

Sono stati creati profili specifici, come:

- `RefrigeratedBodyProfile`;
- `TankBodyProfile`;
- `CarCarrierBodyProfile`.

Questo rende il dominio più esplicito e controllato.

## 6.9 Decisione: assi come lista reale

La specifica assi usa una lista di `VehicleAxle`.

Ogni asse può essere:

- sterzante;
- sollevabile;
- gemellato.

Questa scelta è più realistica rispetto a un semplice numero totale di assi.

## 6.10 Decisione: stato tecnico separato dalla disponibilità

`VehicleStatus` indica stato tecnico/anagrafico:

- attivo;
- sospeso;
- fuori servizio;
- dismesso.

Non indica se il mezzo è disponibile oggi.

Disponibilità, prenotazione e assegnazione viaggio appartengono a moduli futuri.

## 6.11 Decisione: riferimenti tramite ID tra aggregati

Quando un concetto appartiene a un altro aggregato, viene usato il suo ID.

Esempi:

- una figura operativa contiene `UserId`;
- una combinazione veicolare contiene `VehicleUnitId`.

Questa scelta riduce l’accoppiamento e rende il dominio più modulare.

## 6.12 Cosa potrà essere aggiunto in futuro

Il dominio attuale è una base.

In futuro si potranno aggiungere:

- application layer;
- repository port;
- persistence infrastructure;
- REST API;
- pianificazione viaggi;
- disponibilità risorse;
- manutenzione;
- scadenze;
- documenti;
- audit trail;
- dashboard KPI;
- gestione costi;
- telematica;
- tachigrafo;
- integrazioni esterne.

Queste estensioni dovranno rispettare la separazione già definita.

## 6.13 Conclusione

Le scelte fatte rendono TruckFlow Manager più ordinato e più vicino a un gestionale enterprise reale.

Il dominio attuale non prova a fare tutto subito. Descrive bene le fondamenta:

- chi accede;
- quali abilitazioni esistono;
- quali persone operative lavorano in azienda;
- quali mezzi compongono il parco veicoli;
- come si collegano unità e combinazioni.

Questa base permette di crescere senza dover riscrivere i concetti fondamentali.
