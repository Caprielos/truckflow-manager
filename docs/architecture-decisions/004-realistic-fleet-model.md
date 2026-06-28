# ADR — Modello flotta realistico

## Decisione

Il veicolo viene modellato tramite unit type, scheda tecnica, masse, dimensioni, assi, agganci, allestimenti, certificati e convogli.

## Motivazione

La scelta mantiene il progetto più pulito, più testabile e più vicino a un software reale.

## Conseguenze

- Il dominio resta indipendente.
- Le regole possono essere testate senza infrastruttura.
- Il progetto può crescere verso application, infrastructure e web senza riscrivere il cuore del sistema.
