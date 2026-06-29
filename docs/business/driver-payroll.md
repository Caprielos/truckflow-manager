# Payroll autista

Il payroll calcola il costo aziendale dell’autista per una missione.

## Componenti considerate

- ore guida
- ore lavoro non guida
- attesa
- carico/scarico
- straordinario
- notturno
- diaria estera
- pernottamento
- premi patente CE
- CQC merci
- ADR base/cisterna
- rifiuti pericolosi
- veicolo articolato
- cisterna/semirimorchio
- contributi aziendali

## Classi principali

- `DriverMissionWorkReport`
- `DriverPayrollPolicy`
- `DriverPayRule`
- `DriverMissionPayroll`
- `MissionPayrollProjection`

## Ragionamento

Il costo autista non è solo stipendio base. In un trasporto reale incidono ore effettive, qualifiche, merce trasportata e tipo mezzo.
