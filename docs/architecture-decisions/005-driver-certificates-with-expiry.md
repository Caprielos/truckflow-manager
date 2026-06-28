# ADR — Certificati driver con scadenza

## Decisione

Patenti, CQC, ADR e qualifiche operative possono essere rappresentate anche come certificati con data rilascio/scadenza.

## Motivazione

La scelta mantiene il progetto più pulito, più testabile e più vicino a un software reale.

## Conseguenze

- Il dominio resta indipendente.
- Le regole possono essere testate senza infrastruttura.
- Il progetto può crescere verso application, infrastructure e web senza riscrivere il cuore del sistema.
