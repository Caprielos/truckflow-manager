# Operational Modules

## Perché esistono package separati

Alcuni concetti non devono essere messi dentro `Vehicle` come semplici campi.

Esempio: pneumatici, carburante, manutenzione e telematica hanno una vita propria.

## Tire

Una gomma può essere comprata, montata, smontata, ruotata, ricostruita, riscolpita o scartata.

Per questo esiste `domain/tire`.

## Fuel

Un rifornimento ha data, litri, prezzo, provider fuel card e odometro. Le anomalie di consumo vengono confrontate tra transazioni.

Per questo esiste `domain/fuel`.

## Maintenance

Un mezzo può avere manutenzioni programmate, ticket autista, downtime e interventi diversi.

Per questo esiste `domain/maintenance`.

## Telematics

La telematica produce snapshot e anomalie, ma non è la stessa cosa del tracking commerciale.

Per questo esiste `domain/telematics`.

## Load Security

Il fissaggio carico è un controllo operativo pre-partenza. Dipende dalla merce e dal peso.

Per questo esiste `domain/loadsecurity`.
