# Domain package map

Questa pagina mappa tutti i package presenti sotto:

```text
src/main/java/it/gabriele/truckflow/domain
```

| Package | Scopo | Classi |
|---|---|---:|
| [`audit`](packages/audit.md) | Registra eventi importanti del dominio, chi li ha generati, quando sono avvenuti e se richiedono revisione. | 6 |
| [`availability`](packages/availability.md) | Gestisce finestre di disponibilità per autisti, veicoli, convogli, trailer e facility. | 4 |
| [`billing`](packages/billing.md) | Gestisce fatture, stati, importi e pagamenti collegati a spedizioni e prezzi. | 5 |
| [`cargo`](packages/cargo.md) | Modella la merce come centro operativo: categoria, peso, volume, temperatura, ADR e documenti richiesti. | 9 |
| [`claim`](packages/claim.md) | Gestisce danni merce, ritardi, contestazioni, sinistri, danni veicolo e pratiche assicurative. | 5 |
| [`company`](packages/company.md) | Rappresenta licenze e iscrizioni aziendali necessarie per svolgere trasporti specifici. | 2 |
| [`compliance`](packages/compliance.md) | Coordina regole tra autista, mezzo, carico e spedizione. | 1 |
| [`configuration`](packages/configuration.md) | Parametri configurabili, scope e valori sensibili, senza dipendere da file o database. | 6 |
| [`customer`](packages/customer.md) | Anagrafica clienti, account e contatti operativi/commerciali. | 6 |
| [`document`](packages/document.md) | Documenti richiesti dalla missione, dal carico o dai certificati. | 4 |
| [`driver`](packages/driver.md) | Patenti, CQC, ADR, patentini operativi e regole di assegnazione autista. | 7 |
| [`drivetime`](packages/drivetime.md) | Regole base su guida giornaliera, pausa 4h30 e riposo giornaliero. | 1 |
| [`facility`](packages/facility.md) | Depositi, magazzini, punti di carico/scarico e loro caratteristiche. | 2 |
| [`fleet`](packages/fleet.md) | Anagrafica e regole tecniche per veicoli, rimorchi, allestimenti, certificati e convogli. | 37 |
| [`fuel`](packages/fuel.md) | Registra rifornimenti, provider carte carburante e calcolo del consumo reale. | 2 |
| [`identity`](packages/identity.md) | Account, ruoli e autorizzazioni applicative. | 5 |
| [`loadsecurity`](packages/loadsecurity.md) | Checklist e dispositivi per bloccare fisicamente la merce durante il viaggio. | 3 |
| [`location`](packages/location.md) | Indirizzi, coordinate geografiche e località operative. | 3 |
| [`maintenance`](packages/maintenance.md) | Ordini di manutenzione, guasti, tagliandi, controlli tecnici e stati. | 4 |
| [`notification`](packages/notification.md) | Messaggi, canali, destinatari, priorità e stati di invio. | 7 |
| [`operation`](packages/operation.md) | Il viaggio reale assegnato a carico, mezzo, autista e pianificazione. | 3 |
| [`order`](packages/order.md) | Richiesta commerciale del cliente prima di diventare spedizione/missione. | 3 |
| [`pricing`](packages/pricing.md) | Costi tratta, pedaggi, carburante, usura, surcharge e breakdown prezzo. | 6 |
| [`reporting`](packages/reporting.md) | Definizioni report, metriche e report generati. | 8 |
| [`route`](packages/route.md) | Piani di rotta con stop, sequenza e regole operative. | 4 |
| [`shared`](packages/shared.md) | Oggetti riutilizzabili come peso, distanza, volume, money, date e time window. | 10 |
| [`shipment`](packages/shipment.md) | Richiesta accettata pronta a essere pianificata e movimentata. | 3 |
| [`sustainability`](packages/sustainability.md) | Stime emissioni, rating e standard ambientali. | 5 |
| [`telematics`](packages/telematics.md) | Snapshot GPS/CAN-bus e dati letti da centraline esterne. | 1 |
| [`tire`](packages/tire.md) | Gomme singole, posizioni ruota e ciclo vita pneumatici. | 5 |
| [`tracking`](packages/tracking.md) | Timeline di eventi di spedizione, missione e mezzo. | 4 |
