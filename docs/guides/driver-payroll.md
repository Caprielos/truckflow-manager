# Driver payroll e costo autista

## Dove si trova

```text
src/main/java/it/gabriele/truckflow/domain/payroll
```

## Perché è separato dal driver

`driver` descrive la persona, le patenti, certificati e stato operativo.

`payroll` descrive quanto costa il lavoro dell'autista in una missione.

Questa separazione è realistica perché lo stipendio non è una proprietà fissa del driver: dipende da ore, missione, contratto, giorno, trasferta, merci, rimorchio e qualifiche richieste.

## Classi principali

```text
DriverPayrollPolicy
DriverPayRule
DriverPayComponentType
DriverPayUnit
DriverMissionWorkReport
DriverMissionPayLine
DriverMissionPayroll
MissionPayrollProjection
DriverPayrollRules
```

## Cosa considera

Il modello può rappresentare voci come:

- paga base;
- ore guida;
- ore lavoro non guida;
- attesa;
- carico/scarico;
- straordinario;
- notturno;
- festivo;
- trasferta nazionale;
- trasferta estera;
- pernottamento;
- rimborso pasti;
- rimborso hotel;
- CQC;
- patente C/CE;
- ADR base;
- ADR cisterna;
- ADR classe 1;
- ADR classe 7;
- merci pericolose;
- rifiuti pericolosi;
- trasporto frigo;
- farmaceutico;
- food grade;
- animali vivi;
- trasporto eccezionale;
- cisterna/liquidi;
- semirimorchio;
- autotreno;
- carrellone/low loader;
- gru/sponda;
- contributi aziendali;
- formazione;
- sorveglianza sanitaria.

## Collegamento con economics

Il risultato payroll produce un costo missione che può entrare in:

```text
MissionCostLine
MissionEconomics
FleetFinancialStatement
```

Così il progetto può calcolare se una missione è davvero profittevole anche considerando il costo lavoro.
