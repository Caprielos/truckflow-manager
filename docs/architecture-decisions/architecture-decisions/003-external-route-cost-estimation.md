# ADR 003 — Provider esterni per stime route cost fuori dal domain

## Stato

Accettata

## Contesto

Per stimare costi di percorso si potranno usare provider come ViaMichelin, HERE, PTV o Google Maps.

Questi provider possono cambiare API, costi, condizioni e formato risposta.

## Decisione

Il domain non chiama direttamente provider esterni.

Il domain modella solo il risultato tramite `RouteCostEstimate` e la fonte tramite `CostEstimationSource`.

Architettura futura:

```text
application port: RouteCostEstimator
infrastructure adapter: ViaMichelinRouteCostEstimator
domain object: RouteCostEstimate
```

## Motivazioni

- il domain resta stabile;
- si possono cambiare provider;
- si possono testare le regole senza internet;
- si evita scraping o dipendenza diretta da servizi esterni.

## Conseguenze

Qualunque client ViaMichelin/HERE/PTV/Google sarà implementato in infrastructure.
