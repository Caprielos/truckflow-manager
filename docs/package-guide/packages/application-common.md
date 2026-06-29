# `application/common`

Oggetti comuni dell’application layer: risultati applicativi, errori e eccezioni leggibili.

## Come leggerlo

Questo package fa parte dell’application layer. Coordina azioni e dipendenze, ma non deve contenere dettagli di database o web.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `ApplicationError` | record | Record: piccolo oggetto immutabile usato per trasportare dati in modo compatto. | — | ApplicationError, of |
| `ApplicationResult` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | value, errors | success, failure, isSuccess, isFailure, getValue, getValueOrThrow, getErrors |
| `ResourceNotFoundException` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | — | ResourceNotFoundException |
