# ADR — Application layer come prossimo step

## Decisione

Il prossimo step non è aggiungere altri campi al domain, ma orchestrare casi d’uso realistici tramite application layer e repository ports.

## Motivazione

La scelta mantiene il progetto più pulito, più testabile e più vicino a un software reale.

## Conseguenze

- Il dominio resta indipendente.
- Le regole possono essere testate senza infrastruttura.
- Il progetto può crescere verso application, infrastructure e web senza riscrivere il cuore del sistema.
