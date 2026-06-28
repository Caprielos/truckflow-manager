# Project overview

**TruckFlow Manager** è un progetto Java pensato come gestionale per trasporto merci, flotta e operazioni logistiche.

Il progetto parte da un domain model puro, senza Spring, database, REST API o UI. Questa scelta serve a costruire prima le regole di business e poi collegarle, in modo pulito, a repository, web app, database e integrazioni esterne.

## Cosa gestisce il dominio

Il dominio copre:

- clienti e ordini di trasporto;
- spedizioni e missioni operative;
- merci e carichi;
- veicoli, rimorchi, semirimorchi e convogli;
- autisti, patenti e abilitazioni;
- regole di compatibilità tra carico, veicolo e autista;
- documenti e compliance;
- manutenzione, pneumatici, carburante, tracking e telematica;
- pricing, fatturazione e reportistica;
- audit, notifiche, utenti e configurazione.

## Stato attuale

Il progetto contiene soprattutto il **domain layer**. Le classi sono organizzate per package funzionali e i test verificano le regole principali.

Il prossimo passo naturale è l’**application layer**, cioè i casi d’uso:

```text
CreateTransportOrderUseCase
AcceptTransportOrderUseCase
CreateShipmentFromOrderUseCase
CreateVehicleCombinationUseCase
AssignDriverToMissionUseCase
```

## Visione futura

L’app finale potrà diventare un Fleet Management System con:

- database persistente;
- back office web;
- portale autisti;
- API REST;
- integrazione GPS/telematica;
- import carte carburante;
- gestione documentale;
- alert scadenze;
- report operativi e marginalità viaggio.
