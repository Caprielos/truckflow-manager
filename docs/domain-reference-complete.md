# Domain reference completa

Questa pagina è una vista unica e sintetica di tutto il domain.

Per i dettagli, leggere le pagine sotto `packages/`.

## Package presenti

- [`audit`](packages/audit.md): Registra eventi importanti del dominio, chi li ha generati, quando sono avvenuti e se richiedono revisione.
- [`availability`](packages/availability.md): Gestisce finestre di disponibilità per autisti, veicoli, convogli, trailer e facility.
- [`billing`](packages/billing.md): Gestisce fatture, stati, importi e pagamenti collegati a spedizioni e prezzi.
- [`cargo`](packages/cargo.md): Modella la merce come centro operativo: categoria, peso, volume, temperatura, ADR e documenti richiesti.
- [`claim`](packages/claim.md): Gestisce danni merce, ritardi, contestazioni, sinistri, danni veicolo e pratiche assicurative.
- [`company`](packages/company.md): Rappresenta licenze e iscrizioni aziendali necessarie per svolgere trasporti specifici.
- [`compliance`](packages/compliance.md): Coordina regole tra autista, mezzo, carico e spedizione.
- [`configuration`](packages/configuration.md): Parametri configurabili, scope e valori sensibili, senza dipendere da file o database.
- [`customer`](packages/customer.md): Anagrafica clienti, account e contatti operativi/commerciali.
- [`document`](packages/document.md): Documenti richiesti dalla missione, dal carico o dai certificati.
- [`driver`](packages/driver.md): Patenti, CQC, ADR, patentini operativi e regole di assegnazione autista.
- [`drivetime`](packages/drivetime.md): Regole base su guida giornaliera, pausa 4h30 e riposo giornaliero.
- [`facility`](packages/facility.md): Depositi, magazzini, punti di carico/scarico e loro caratteristiche.
- [`fleet`](packages/fleet.md): Anagrafica e regole tecniche per veicoli, rimorchi, allestimenti, certificati e convogli.
- [`fuel`](packages/fuel.md): Registra rifornimenti, provider carte carburante e calcolo del consumo reale.
- [`identity`](packages/identity.md): Account, ruoli e autorizzazioni applicative.
- [`loadsecurity`](packages/loadsecurity.md): Checklist e dispositivi per bloccare fisicamente la merce durante il viaggio.
- [`location`](packages/location.md): Indirizzi, coordinate geografiche e località operative.
- [`maintenance`](packages/maintenance.md): Ordini di manutenzione, guasti, tagliandi, controlli tecnici e stati.
- [`notification`](packages/notification.md): Messaggi, canali, destinatari, priorità e stati di invio.
- [`operation`](packages/operation.md): Il viaggio reale assegnato a carico, mezzo, autista e pianificazione.
- [`order`](packages/order.md): Richiesta commerciale del cliente prima di diventare spedizione/missione.
- [`pricing`](packages/pricing.md): Costi tratta, pedaggi, carburante, usura, surcharge e breakdown prezzo.
- [`reporting`](packages/reporting.md): Definizioni report, metriche e report generati.
- [`route`](packages/route.md): Piani di rotta con stop, sequenza e regole operative.
- [`shared`](packages/shared.md): Oggetti riutilizzabili come peso, distanza, volume, money, date e time window.
- [`shipment`](packages/shipment.md): Richiesta accettata pronta a essere pianificata e movimentata.
- [`sustainability`](packages/sustainability.md): Stime emissioni, rating e standard ambientali.
- [`telematics`](packages/telematics.md): Snapshot GPS/CAN-bus e dati letti da centraline esterne.
- [`tire`](packages/tire.md): Gomme singole, posizioni ruota e ciclo vita pneumatici.
- [`tracking`](packages/tracking.md): Timeline di eventi di spedizione, missione e mezzo.
