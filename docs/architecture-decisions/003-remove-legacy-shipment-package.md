# ADR — Rimozione legacy shipment fuori domain

## Decisione

Il package `it.gabriele.truckflow.shipment` era un duplicato didattico. È stato rimosso per lasciare una sola implementazione corretta in `it.gabriele.truckflow.domain.shipment`.

## Motivazione

La scelta mantiene il progetto più pulito, più testabile e più vicino a un software reale.

## Conseguenze

- Il dominio resta indipendente.
- Le regole possono essere testate senza infrastruttura.
- Il progetto può crescere verso application, infrastructure e web senza riscrivere il cuore del sistema.
