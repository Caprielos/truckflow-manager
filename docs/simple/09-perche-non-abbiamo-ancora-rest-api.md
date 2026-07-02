# 9. Perché non abbiamo ancora REST API

Non abbiamo ancora REST API perché prima volevamo costruire una base solida.

Una REST API è il modo con cui un'applicazione espone funzioni verso l'esterno usando HTTP.

È importante, ma non deve arrivare troppo presto.

## Cosa sarebbe successo se fossimo partiti dalle API

Avremmo rischiato di mettere logica dentro i controller.

Questo è un errore comune: il controller riceve una richiesta HTTP e poi inizia a fare troppe cose.

Nel nostro progetto vogliamo invece che il controller, quando arriverà, sia sottile:

1. riceve la richiesta;
2. la trasforma in command;
3. chiama un use case;
4. restituisce una risposta.

## Perché il Punto 8 arriva dopo il Punto 7

Prima di esporre il sistema verso l'esterno, serviva avere:

- dominio stabile;
- application layer stabile;
- infrastructure layer stabile;
- wiring controllato;
- test architetturali.

Ora che il Punto 7 è chiuso, il Punto 8 potrà introdurre l'API Layer in modo più pulito.

## Cosa arriverà nel Punto 8

Nel Punto 8 potremo iniziare a progettare:

- controller REST;
- DTO web;
- mapping API ↔ application;
- error handling HTTP;
- documentazione API;
- primi endpoint.

Ma lo faremo senza sporcare dominio e application layer.
