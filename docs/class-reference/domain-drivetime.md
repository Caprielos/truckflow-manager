# Package `domain.drivetime`

Regole su ore guida e riposo.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| DriverTimeRules | class | Classe di regole per validare e calcolare comportamenti nel package domain.drivetime. | requiresBreakAfter, isWithinStandardDailyDriving, isWithinExtendedDailyDriving, requiredBreak, standardDailyRest, isWithinWeeklyDriving, isWithinTwoWeekDriving |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
