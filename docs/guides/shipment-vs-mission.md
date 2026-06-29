# Shipment vs TransportMission

## Order

`TransportOrder` è la richiesta commerciale del cliente.

## Shipment

`Shipment` è la spedizione creata da un ordine accettato.

Contiene il concetto logistico di cosa deve essere trasportato e con quale stato.

Non deve contenere direttamente tutto:

```text
autista
camion
rimorchio
fuel
manutenzione
tracking GPS
stipendio
```

Queste cose appartengono alla missione o ad altri moduli.

## TransportMission

`TransportMission` è il viaggio reale operativo.

Qui entrano:

- assegnazione operativa;
- mission status;
- rotta;
- tracking;
- documenti;
- chiusura;
- collegamento con costi e payroll.

## Perché il vecchio shipment fuori da domain è stato rimosso

Prima esisteva un package vecchio:

```text
it.gabriele.truckflow.shipment
```

Era un modello iniziale/didattico duplicato. È stato rimosso perché la sola fonte di verità deve essere:

```text
it.gabriele.truckflow.domain.shipment
```

Questo evita confusione e rende l'architettura più pulita.
