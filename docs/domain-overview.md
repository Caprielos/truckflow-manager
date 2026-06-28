# Domain Overview

## Ragionamento generale

TruckFlow Manager è stato modellato come dominio logistico realistico.

La regola principale è:

```text
Non si forza la realtà dentro una sola enum.
Si separano i concetti reali.
```

Esempio nel fleet:

```text
VehicleUnitType
→ che unità fisica è

VehicleBodyConfiguration
→ che allestimento ha

VehicleTechnicalSpecification
→ quali dati tecnici possiede

VehicleCertificate
→ quali certificati e scadenze ha

VehicleCombination
→ come viene agganciato in un convoglio
```

## Merce come centro delle regole

La merce non è una descrizione libera.

La categoria merce attiva vincoli:

- pallet;
- temperatura;
- ADR;
- EER/CER rifiuti;
- FIR;
- ATP;
- HACCP;
- documentazione veterinaria;
- allestimento compatibile;
- driver qualificato;
- azienda autorizzata;
- surcharge di pricing.

## Mezzi e convogli

Un mezzo singolo è `Vehicle`.

Un convoglio è `VehicleCombination`.

Esempi:

```text
Autocarro singolo
→ VehicleCombinationType.SINGLE_VEHICLE

Autotreno
→ RIGID_TRUCK + DRAWBAR_TRAILER / CENTER_AXLE_TRAILER
→ VehicleCombinationType.TRUCK_AND_TRAILER

Bilico / autoarticolato
→ TRACTOR_UNIT + SEMI_TRAILER
→ VehicleCombinationType.ARTICULATED_VEHICLE
```

## Autista

L’autista non è solo una persona con patente.

Può avere:

- patenti B, C1, C, BE, C1E, CE;
- CQC merci;
- ADR base/cisterne/classe 1/classe 7;
- patentini operativi;
- certificati con scadenza;
- stato operativo.

## Azienda

L’azienda può avere licenze:

- Albo Autotrasportatori;
- REN;
- licenza comunitaria;
- conto proprio;
- Albo Gestori Ambientali.

## Missione

La missione è il punto dove tutto si incontra.

Una missione corretta deve rispettare:

- compatibilità merce/allestimento;
- idoneità driver;
- idoneità veicolo/convoglio;
- licenze azienda;
- documenti obbligatori;
- disponibilità;
- manutenzione/scadenze;
- tempi guida;
- costi e marginalità.
