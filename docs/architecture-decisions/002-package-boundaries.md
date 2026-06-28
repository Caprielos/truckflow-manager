# ADR — Package boundary espliciti

## Decisione

Ogni package domain ha una responsabilità chiara. Evitiamo classi giganti o package generici che contengono tutto.

## Motivazione

La scelta mantiene il progetto più pulito, più testabile e più vicino a un software reale.

## Conseguenze

- Il dominio resta indipendente.
- Le regole possono essere testate senza infrastruttura.
- Il progetto può crescere verso application, infrastructure e web senza riscrivere il cuore del sistema.
