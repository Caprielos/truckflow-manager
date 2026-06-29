# Payroll e costo autista

Il payroll serve per trasformare lavoro reale dell'autista in costo aziendale.

## Elementi considerati

```text
ore guida
ore lavoro non guida
attesa
carico/scarico
straordinario
notturno
festivo
diaria nazionale/estera
pernottamento
rimborsi
patente CE
CQC merci
ADR base/cisterna/classi speciali
merci pericolose
rifiuti pericolosi
trasporto frigo
cisterna
semirimorchio
autoarticolato
contributi aziendali
```

## Flusso

```text
DriverMissionWorkReport
→ DriverPayrollPolicy
→ DriverPayrollRules
→ DriverMissionPayroll
→ MissionEconomics come costo autista
```

Quindi il costo autista entra nei costi missione e influenza il profitto.
