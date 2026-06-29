# ADR 011 - Separare la gestione scadenze in compliance-deadline-service

## Stato

Proposta accettata per implementazione graduale.

## Contesto

TruckFlow Manager deve gestire scadenze legali, tecniche, operative e monitoraggi continui per camion, rimorchi, autisti, carico, magazzino, viaggio, sicurezza e telemetria.

Tenere questa logica dentro i singoli domini rende il sistema difficile da scalare, da configurare e da mantenere.

## Decisione

La logica delle scadenze verrà spostata progressivamente in un microservizio autonomo:

```text
compliance-deadline-service
```

Il servizio sarà responsabile di:

- regole legali;
- regole tecniche costruttore;
- regole operative interne;
- monitoraggio continuo;
- calcolo scadenze;
- stati;
- alert;
- workflow;
- audit;
- blocchi;
- override autorizzati;
- rule pack versionati.

Il dominio principale non calcolerà più le scadenze. Fornirà solo descrittori e fatti.

## Conseguenze positive

- Regole centralizzate.
- Maggiore scalabilità.
- Maggiore auditabilità.
- Possibilità di configurazione da interfaccia grafica.
- Supporto multi-tenant.
- Separazione netta tra oggetti reali e regole di conformità.
- Possibilità di aggiornare normative o manuali senza modificare il dominio principale.

## Conseguenze negative

- Maggiore complessità architetturale.
- Necessità di API o eventi tra servizi.
- Necessità di versionare contratti DTO.
- Necessità di gestire fallback quando il microservizio non è disponibile.

## Mitigazioni

- Migrazione per fasi.
- Prima implementazione nello stesso repository Maven.
- Gateway in memoria per test locali.
- API REST solo dopo il modello core.
- Test di copertura sul catalogo elementi.
- Stato `CONFIGURATION_MISSING` per regole non compilate.
