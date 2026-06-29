# `domain/drivetime`

Regole ore guida, riposo e limiti operativi.

## Come leggerlo

Questo package contiene concetti o regole del business. Non dovrebbe dipendere da database, web o infrastructure.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `DriverTimeRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | MAX_CONTINUOUS_DRIVING, STANDARD_DAILY_DRIVING, EXTENDED_DAILY_DRIVING, REQUIRED_BREAK, STANDARD_DAILY_REST, MAX_WEEKLY_DRIVING, MAX_TWO_WEEK_DRIVING | requiresBreakAfter, isWithinStandardDailyDriving, isWithinExtendedDailyDriving, requiredBreak, standardDailyRest, isWithinWeeklyDriving, isWithinTwoWeekDriving |
