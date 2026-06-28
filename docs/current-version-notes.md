# Note versione attuale

## Pulizia del vecchio package shipment

Nella versione attuale è stato risolto un punto architetturale importante: il vecchio package fuori da `domain` non deve più esistere.

Prima era presente una struttura didattica/iniziale simile a:

```text
src/main/java/it/gabriele/truckflow/shipment
src/test/java/it/gabriele/truckflow/shipment
```

Quella struttura era ormai duplicata rispetto al dominio vero:

```text
src/main/java/it/gabriele/truckflow/domain/shipment
src/test/java/it/gabriele/truckflow/domain/shipment
```

La scelta corretta è stata **non allargare il vecchio package**, ma eliminarlo. In un progetto serio deve esserci una sola sorgente di verità.

## Perché non andava integrato fuori da domain

Il package `it.gabriele.truckflow.shipment` era fuori dall'architettura attuale. Lasciarlo avrebbe creato problemi:

- doppio modello di spedizione;
- test duplicati;
- confusione tra codice didattico e dominio reale;
- rischio di usare per errore la classe sbagliata;
- package root non coerente con il resto del progetto.

Ora la spedizione corretta è solo:

```text
it.gabriele.truckflow.domain.shipment.Shipment
```

## Cosa rimane da fare sullo shipment

Lo `Shipment` dentro `domain` è corretto come ruolo: nasce da un `TransportOrder` accettato e segue il ciclo logistico della spedizione.

Non deve contenere direttamente driver, mezzo, rimorchio, carburante, gomme o telematica. Quelle cose appartengono alla missione operativa e ai moduli specifici.

In futuro si può migliorare `ShipmentRules` con una classe di riepilogo requisiti, per esempio:

```text
ShipmentRequirementSummary
ShipmentRequirementType
```

Questa evoluzione servirebbe a dire: “questa spedizione richiede ADR, ATP, FIR, CMR, checklist fissaggio, ecc.” senza trasformare Shipment in una missione.

## Stato realistico attuale

Il progetto ora è più coerente:

```text
order      → richiesta commerciale
shipment   → spedizione generata da ordine accettato
operation  → missione reale assegnata a driver/convoglio/route
fleet      → mezzi e schede tecniche
cargo      → merce e requisiti
company    → licenze aziendali
document   → documenti richiesti/verificati
compliance → controlli trasversali
```
