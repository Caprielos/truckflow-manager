# ADR — Moduli operativi separati

## Decisione

Gomme, carburante, manutenzione, telematica e fissaggio carico sono package separati perché hanno vita, storico e regole proprie.

## Motivazione

La scelta mantiene il progetto più pulito, più testabile e più vicino a un software reale.

## Conseguenze

- Il dominio resta indipendente.
- Le regole possono essere testate senza infrastruttura.
- Il progetto può crescere verso application, infrastructure e web senza riscrivere il cuore del sistema.
