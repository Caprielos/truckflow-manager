# Realistic fleet model

## Dove si trova

```text
src/main/java/it/gabriele/truckflow/domain/fleet
```

## Cosa modella

Il package `fleet` non descrive solo un camion generico. Modella:

- veicoli singoli;
- furgoni;
- camion rigidi;
- trattori stradali;
- rimorchi;
- semirimorchi;
- autotreni;
- autoarticolati;
- assi;
- masse;
- dimensioni;
- allestimenti;
- sponda;
- gru;
- frigo;
- certificati;
- compatibilità tecnica.

## Classi chiave

```text
Vehicle
VehicleUnitType
VehicleTechnicalSpecification
VehicleMassSpecification
VehicleDimensionSpecification
VehicleBodyConfiguration
VehicleAxleSpecification
VehicleCombination
VehicleCombinationType
VehicleCombinationTechnicalRules
VehicleCombinationLegalLimitProfile
VehicleCertificate
```

## Separazione corretta

- `fleet` descrive il mezzo e la sua compatibilità.
- `tire` descrive le gomme fisiche.
- `parking` descrive dove il mezzo è parcheggiato.
- `economics` descrive quanto è costato il mezzo.
- `maintenance` descrive la manutenzione.
- `telematics` descrive posizione e dati CAN-bus.
