# ADR 004 — La merce guida le regole operative

## Decisione

`CargoCategory` è il punto di partenza per calcolare compatibilità, documenti, certificati, abilitazioni e costi.

## Motivazione

La merce determina cosa serve:

- frigo;
- ATP;
- ADR;
- FIR;
- documenti veterinari;
- fissaggio;
- patentini;
- costi extra.

## Conseguenze

- `CargoOperationalRules` deriva documenti e certificati.
- `VehicleBodyCompatibilityRules` collega carico e allestimento.
- `DriverRules` verifica ADR e qualifiche operative.
