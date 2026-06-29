# Shipment vs TransportMission

Questa distinzione è fondamentale.

## Shipment

`Shipment` rappresenta la spedizione da organizzare.

Risponde a domande come:

```text
cosa va trasportato?
da dove a dove?
quale merce?
quali requisiti documentali?
```

## TransportMission

`TransportMission` rappresenta il viaggio reale operativo.

Risponde a domande come:

```text
quale autista?
quale convoglio?
quale route plan?
è partita?
è completata?
```

## Flusso corretto

```text
TransportOrder
→ Shipment
→ TransportMission
```
