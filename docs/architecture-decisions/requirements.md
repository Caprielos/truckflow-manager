# TruckFlow Manager

> Documentazione aggiornata e allineata al domain MVP implementato.

Package root del domain:

```text
it.gabriele.truckflow.domain
```

Regola principale:

```text
Il domain rappresenta il business.
La tecnologia serve solo a farlo funzionare.
```


# Requirements

## Scopo

Raccoglie i requisiti funzionali e non funzionali di TruckFlow Manager, allineati al domain MVP.

## Requisiti funzionali

### Clienti

Il sistema deve permettere di:

- registrare clienti;
- distinguere tipo cliente;
- gestire stato cliente;
- gestire contatti cliente;
- identificare un contatto primario;
- bloccare nuovi ordini per clienti non attivi.

### Ordini

Il sistema deve permettere di:

- creare un `TransportOrder`;
- collegarlo a un `CustomerAccount`;
- indicare carico, pickup, delivery e finestre orarie;
- scegliere tipo servizio;
- validare compatibilità servizio/carico;
- gestire stati draft, submitted, accepted, rejected, cancelled.

### Spedizioni

Il sistema deve permettere di:

- creare una `Shipment` solo da ordine accettato;
- pianificare, dispatchare, portare in transito, consegnare o cancellare;
- distinguere shipment da missione operativa.

### Missioni operative

Il sistema deve permettere di:

- creare una `TransportMission`;
- collegare shipment, driver, vehicle combination e route plan;
- controllare compliance prima dell'esecuzione;
- gestire stati planned, dispatched, in progress, completed, cancelled.

### Flotta

Il sistema deve gestire:

- veicoli motorizzati;
- trailer/semirimorchi;
- allestimenti;
- targhe;
- chassis/VIN;
- pneumatici;
- capacità peso/dimensioni;
- temperatura;
- disponibilità operativa;
- `VehicleCombination`.

### Autisti

Il sistema deve gestire:

- driver status;
- patente B/C/E;
- CQC merci;
- certificati ADR;
- qualifiche operative;
- compatibilità con mezzo, carico e tratta.

### Carico

Il sistema deve gestire:

- categoria merce;
- peso;
- dimensioni;
- volume;
- temperatura richiesta;
- dangerous goods profile;
- ADR tank;
- classi ADR;
- compatibilità allestimento.

### Route

Il sistema deve gestire:

- route plan;
- stop ordinati;
- start/end;
- pickup/delivery;
- rest break e fuel stop;
- distanza stimata;
- tratte internazionali.

### Compliance

Il sistema deve rispondere alla domanda:

```text
Posso eseguire questa spedizione con questo driver,
questa vehicle combination e questa route?
```

### Tracking

Il sistema deve registrare:

- posizione;
- partenza;
- arrivo;
- pickup completed;
- delivery completed;
- ritardi;
- incidenti;
- mission completed.

### Maintenance

Il sistema deve gestire manutenzioni, controlli sicurezza, pneumatici, riparazioni, frigo, ADR tank inspection e breakdown.

### Pricing

Il sistema deve gestire:

- stime route cost;
- fuel cost;
- toll cost;
- vehicle wear cost;
- surcharge;
- discount;
- price breakdown;
- fonte della stima.

### Billing

Il sistema deve gestire:

- invoice;
- issue;
- paid;
- cancel;
- payment record;
- overdue;
- copertura totale invoice con pagamenti.

### Documenti

Il sistema deve gestire:

- CMR;
- proof of delivery;
- delivery note;
- documento ADR;
- temperature log;
- copia fattura;
- insurance certificate;
- vehicle registration;
- driver license copy;
- documenti con scadenza.

### Claim

Il sistema deve gestire reclami per danni, perdita, ritardo, temperatura, documenti, billing dispute.

### Audit

Il sistema deve registrare azioni importanti e sapere:

- chi ha agito;
- cosa ha fatto;
- quando;
- su quale aggregate.

### Notification

Il sistema deve modellare notifiche operative, finanziarie, security, customer-visible e system.

### Sustainability

Il sistema deve modellare stime emissioni e rating ambientale.

### Identity

Il sistema deve gestire account, ruoli, permessi e regole accesso base.

### Configuration

Il sistema deve gestire configurazioni globali e scoped per operation, pricing, notification, document, security, sustainability, reporting, integration.

### Reporting

Il sistema deve generare report con metriche operative, finanziarie, compliance, documentali e sostenibilità.

## Requisiti non funzionali

- domain Java puro;
- testabilità con unit test;
- nessuna dipendenza tecnica nel domain;
- oggetti sempre validi;
- transizioni di stato protette;
- nomi coerenti;
- regole chiare e documentate;
- possibilità di aggiungere nuove integrazioni senza riscrivere il domain.
