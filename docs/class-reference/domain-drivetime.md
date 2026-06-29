# Domain `drivetime` spiegato

Regole ore guida, riposo e limiti operativi.

## Classi principali

### `DriverTimeRules`

Tipo: `class`.

Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

Campi principali:

- `MAX_CONTINUOUS_DRIVING`
- `STANDARD_DAILY_DRIVING`
- `EXTENDED_DAILY_DRIVING`
- `REQUIRED_BREAK`
- `STANDARD_DAILY_REST`
- `MAX_WEEKLY_DRIVING`
- `MAX_TWO_WEEK_DRIVING`

Metodi pubblici principali:

- `requiresBreakAfter()`
- `isWithinStandardDailyDriving()`
- `isWithinExtendedDailyDriving()`
- `requiredBreak()`
- `standardDailyRest()`
- `isWithinWeeklyDriving()`
- `isWithinTwoWeekDriving()`
