# 12. Cosa succederà nel Punto 8

Il Punto 8 sarà il ciclo dedicato all'API Layer.

Questo significa che TruckFlow Manager inizierà a esporre alcune funzioni verso l'esterno tramite REST API.

## Cosa introdurrà il Punto 8

Il Punto 8 potrà introdurre:

- controller REST;
- DTO web;
- mapping tra request HTTP e command applicativi;
- mapping tra result applicativi e response HTTP;
- gestione degli errori HTTP;
- primi endpoint;
- documentazione API.

## Cosa deve restare pulito

Anche quando arriveranno le API, domain e application devono restare puliti.

Il controller non deve contenere business logic.

Il controller deve solo ricevere la richiesta, chiamare il use case giusto e restituire una risposta.

## Esempio futuro

Per registrare una location, un endpoint REST potrebbe ricevere una richiesta HTTP.

Il controller trasformerebbe quella richiesta in `RegisterLocationCommand` e chiamerebbe `RegisterLocationUseCase`.

Questo è il motivo per cui abbiamo costruito prima i use case: l'API dovrà appoggiarsi su qualcosa di già solido.

## Prossima regola

Nel Punto 8 dovremo procedere come sempre: piccoli step, test, documentazione e confini chiari.
