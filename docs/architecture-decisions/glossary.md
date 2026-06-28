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


# Glossary

## ADR

Trasporto merci pericolose. Nel domain è rappresentato da profili dangerous goods, classi ADR e certificati autista.

## Application layer

Strato futuro che coordina i casi d'uso usando il domain.

## Audit

Storico delle azioni importanti: chi ha fatto cosa, quando, su quale aggregate.

## Billing

Area di fatture e pagamenti. Diversa da pricing.

## CargoLoad

Carico composto da uno o più CargoItem.

## Compliance

Controlli di compatibilità e conformità tra driver, veicolo, carico, route e shipment.

## Domain

Cuore del business, Java puro, senza framework e senza dettagli tecnici.

## Driver

Autista operativo. Non è un account applicativo.

## Entity

Oggetto con identità propria, come Shipment, Driver, Vehicle o Invoice.

## Facility

Punto operativo: warehouse, depot, customer site, terminal, port, ecc.

## Infrastructure

Strato tecnico futuro: database, API esterne, email, storage documenti.

## Pricing

Preventivi, righe prezzo, surcharge, discount e stime costi.

## Shipment

Spedizione generata da un ordine accettato.

## TransportMission

Viaggio operativo reale che assegna shipment, driver, vehicle combination e route plan.

## TransportOrder

Richiesta commerciale iniziale del cliente.

## UserAccount

Account di accesso al sistema, separato da Driver e Customer.

## Value Object

Oggetto senza identità propria, valido per i suoi valori, come Money, Weight o DateRange.

## VehicleCombination

Unità assegnabile composta da mezzo singolo o powered unit + trailer.

## ViaMichelin

Provider esterno futuro per costi/percorso. Non viene chiamato dal domain.
