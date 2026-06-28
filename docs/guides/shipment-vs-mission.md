# Shipment vs TransportMission

Questa è una distinzione centrale del progetto.

## Shipment

`Shipment` rappresenta la spedizione generata da un ordine accettato.

Risponde a domande come:

- questa spedizione esiste?
- da quale ordine nasce?
- qual è il cliente?
- qual è il carico?
- da dove parte e dove arriva?
- è creata, pianificata, spedita, in transito, consegnata o cancellata?

Non deve sapere quale gomma è montata, quanto gasolio è stato consumato o che evento CAN-bus è arrivato.

## TransportMission

`TransportMission` rappresenta l’esecuzione reale del trasporto.

Risponde a domande come:

- quale driver è assegnato?
- quale convoglio parte?
- quale route plan segue?
- quando viene dispatchata?
- quando viene completata?

## Perché il vecchio package shipment è stato eliminato

Il vecchio `it.gabriele.truckflow.shipment` era un duplicato fuori dal domain layer. In un progetto reale questo è pericoloso: due modelli per la stessa cosa portano bug e confusione.

La soluzione corretta è avere solo:

```text
it.gabriele.truckflow.domain.shipment
```

## Evoluzione futura

Si può aggiungere un modello di requisiti spedizione:

```text
ShipmentRequirementType
ShipmentRequirementSummary
```

Così `Shipment` potrà dichiarare requisiti come ADR, ATP, FIR, CMR o checklist fissaggio, mentre `TransportMission` continuerà ad assegnare persone, mezzi e rotta.
