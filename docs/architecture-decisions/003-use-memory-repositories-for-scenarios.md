# ADR 003 - Repository in memoria per scenari

## Decisione

Prima del database reale, il progetto usa repository in memoria.

## Motivazione

Permettono di provare application layer e flussi reali senza introdurre subito database, JPA o Spring.

## Limiti

I dati non sono persistenti.

## Prossimo passo

Aggiungere repository persistenti in un nuovo package infrastructure/persistence.
