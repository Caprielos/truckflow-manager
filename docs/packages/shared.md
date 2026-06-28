# Package `shared` — Value Object comuni

Contiene tipi immutabili e validati usati da tutto il dominio: peso, denaro, distanze, volumi, dimensioni, finestre temporali, range date, temperatura e note.

## Responsabilità

- Usato da quasi tutti i package.
- Evita primitive obsession: non si passa un double generico quando serve Weight, Money o Distance.

## Classi

- `DateRange` — modello/domain object del package.
- `Dimension` — modello/domain object del package.
- `Distance` — modello/domain object del package.
- `Money` — modello/domain object del package.
- `Notes` — modello/domain object del package.
- `Percentage` — modello/domain object del package.
- `TemperatureRange` — modello/domain object del package.
- `TimeWindow` — modello/domain object del package.
- `Volume` — modello/domain object del package.
- `Weight` — modello/domain object del package.

## Regole importanti

- Valori nulli, vuoti o negativi vengono rifiutati quando non ammessi.
- Ogni value object espone factory statiche leggibili.

## Collegamenti

- Usato da quasi tutti i package.
- Evita primitive obsession: non si passa un double generico quando serve Weight, Money o Distance.
