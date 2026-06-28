# Realistic Fleet Model

## Idea principale

Il vecchio modello con pochi tipi veicolo non basta per un gestionale reale.

Un mezzo vero non è solo:

```text
TRUCK + REFRIGERATED
```

È una combinazione di:

- unità veicolo;
- massa tecnica;
- dimensioni;
- configurazione assi;
- tipo aggancio;
- allestimento;
- equipaggiamenti;
- certificati;
- stato operativo.

## Classi principali

- `Vehicle`
- `VehicleUnitType`
- `VehicleTechnicalSpecification`
- `VehicleMassSpecification`
- `VehicleDimensionSpecification`
- `VehicleAxleSpecification`
- `VehicleCouplingSpecification`
- `VehicleBodyConfiguration`
- `VehicleCertificate`
- `VehicleCombination`
- `VehicleCombinationTechnicalRules`

## Refrigerato

Il refrigerato non deve essere modellato come tipo veicolo separato. È più corretto modellarlo come:

```text
VehicleUnitType.RIGID_TRUCK
VehicleBodyBaseType.REFRIGERATED_BOX
VehicleTechnicalFeature.ACTIVE_REFRIGERATION
VehicleCertificateType.ATP
```

Questo permette di rappresentare anche semirimorchi frigo, motrici frigo, isotermici, body swap e mezzi misti.

## Convogli

`VehicleCombination` distingue:

- veicolo singolo;
- camion + rimorchio;
- trattore + semirimorchio.

Questo è più realistico rispetto ad assegnare una spedizione a un semplice `Truck`.
