# Next Application Layer

## Perché serve

Il domain contiene regole e modelli. Però per usare il progetto come software vero serve un application layer.

Il domain dice cosa è valido. L'application layer orchestra i casi d'uso.

## Esempio: pianificare una missione

Un caso d'uso realistico potrebbe fare:

1. carica shipment;
2. carica driver;
3. carica vehicle combination;
4. controlla disponibilità;
5. controlla cargo requirements;
6. controlla patente/CQC/ADR;
7. controlla licenze azienda;
8. controlla certificati mezzo;
9. controlla documenti richiesti;
10. crea TransportMission.

## Package futuri suggeriti

```text
it.gabriele.truckflow.application.order
it.gabriele.truckflow.application.shipment
it.gabriele.truckflow.application.operation
it.gabriele.truckflow.application.compliance
it.gabriele.truckflow.application.fleet
```

## Ports futuri

```text
ShipmentRepository
TransportMissionRepository
DriverRepository
VehicleRepository
TransportCompanyRepository
DocumentRepository
```

## Regola

Non mettere repository o database nel domain.
