# Requisiti non funzionali

## Manutenibilità

Il codice deve essere diviso in package chiari: domain, application, infrastructure e futuro web.

## Testabilità

Le regole domain devono essere testabili senza database. Gli use case devono essere testabili con repository in memoria.

## Estendibilità

Il sistema deve poter sostituire `infrastructure.memory` con database reale senza riscrivere gli use case.

## Sicurezza futura

Il sistema dovrà gestire autenticazione, autorizzazioni e ruoli.

## Tracciabilità

Le operazioni critiche devono essere tracciabili tramite audit trail.

## Configurabilità

Aliquote IVA, policy paghe, tariffe, supplementi e regole aziendali non devono essere cablate in modo rigido: devono poter diventare configurazioni.
