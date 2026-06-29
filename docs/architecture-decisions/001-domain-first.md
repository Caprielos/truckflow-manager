# ADR 001 - Domain first

## Decisione

Il progetto è stato costruito prima partendo dal domain.

## Motivazione

Le regole di trasporto, costi, flotta, autisti e documenti sono il cuore del sistema. Prima di collegare database o API era importante modellarle correttamente.

## Conseguenze

Il domain è ampio e testato. Application e infrastructure sono state aggiunte dopo.
