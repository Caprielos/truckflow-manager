# 12. Cosa succederà nel Punto 8

Il Punto 8 è il ciclo dedicato all'API Layer.

Questo significa che TruckFlow Manager inizierà a preparare l'esposizione di alcune funzioni verso l'esterno tramite REST API.

## Stato attuale

Il primo step è stato avviato con:

```text
Punto 8A — API Layer Blueprint
```

Questo step non crea ancora API vere.

Serve a definire:

- regole dell’API Layer;
- package futuro `it.gabriele.truckflow.api`;
- versionamento `/api/v1`;
- primo contesto REST: Locations;
- primi endpoint futuri;
- test architetturale che protegge i layer.

## Cosa introdurrà il Punto 8 nei prossimi step

Il Punto 8 potrà introdurre gradualmente:

- package API;
- controller REST;
- DTO web;
- mapping tra request HTTP e command applicativi;
- mapping tra result applicativi e response HTTP;
- gestione degli errori HTTP;
- primi endpoint;
- documentazione API.

## Cosa deve restare pulito

Anche quando arriveranno le API, Domain e Application devono restare puliti.

Il controller non deve contenere business logic.

Il controller deve solo ricevere la richiesta, chiamare il use case giusto e restituire una risposta.

Il controller non deve chiamare repository concreti o file-backed repository.

## Primo contesto REST

Il primo contesto scelto sarà:

```text
Locations
```

Per registrare una location, un endpoint REST futuro potrebbe ricevere una richiesta HTTP.

Il controller trasformerebbe quella richiesta in `RegisterLocationCommand` e chiamerebbe `RegisterLocationUseCase`.

Questo è il motivo per cui abbiamo costruito prima i use case: l'API dovrà appoggiarsi su qualcosa di già solido.

## Prossimo step

Dopo il blueprint, il prossimo step sarà:

```text
Punto 8B — API Layer Foundation
```

Nel Punto 8B si potrà creare la struttura base del package API, ma senza espansione incontrollata.
