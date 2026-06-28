# TruckFlow Manager — Documentazione

Questa cartella contiene la documentazione ordinata del progetto.

## Ordine consigliato di lettura

1. `domain-reference.md` — documento principale completo.
2. `architecture.md` — architettura a livelli e regole di dipendenza.
3. `requirements.md` — requisiti funzionali e non funzionali.
4. `domain-overview.md` — panoramica del dominio e flussi principali.
5. `domain-model.md` — struttura dei package.
6. `domain-class-catalog.md` — catalogo classi implementate.
7. `domain-rules.md` — regole di business principali.
8. `glossary.md` — glossario termini.
9. `implementation-roadmap.md` — stato e prossimi step.
10. `java-coding-guidelines.md` — regole di scrittura Java.
11. `shared-value-objects.md` — dettagli sui value object condivisi.
12. `architecture-decisions/` — decisioni architetturali.

## Stato

Il domain MVP è sostanzialmente completo.

Package coperti:

```text
domain
├── shared
├── cargo
├── location
├── facility
├── customer
├── order
├── shipment
├── route
├── fleet
├── driver
├── compliance
├── operation
├── availability
├── tracking
├── maintenance
├── pricing
├── billing
├── document
├── claim
├── audit
├── notification
├── sustainability
├── identity
├── configuration
├── reporting
```

## Prossimo macro-step

Il prossimo passo consigliato è iniziare l'`application layer`, con use case e porte.
