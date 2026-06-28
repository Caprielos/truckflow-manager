# Domain Package Map

Questa mappa collega ogni package al suo ruolo.

| Package | Scopo | Documentazione |
|---|---|---|
| `audit` | Registra gli eventi importanti del sistema: chi ha fatto cosa, quando, con che severità e su quale risorsa. | [`packages/audit.md`](packages/audit.md) |
| `availability` | Gestisce disponibilità o indisponibilità temporanea di risorse come veicoli, autisti o asset. | [`packages/availability.md`](packages/availability.md) |
| `billing` | Gestisce fatture, pagamenti e regole amministrative economiche. | [`packages/billing.md`](packages/billing.md) |
| `cargo` | Modella la merce, che è il centro delle regole: determina allestimento, documenti, requisiti autista, certificati mezzo e costi. | [`packages/cargo.md`](packages/cargo.md) |
| `claim` | Gestisce reclami, danni, sinistri e controlli visivi legati a mezzi e missioni. | [`packages/claim.md`](packages/claim.md) |
| `company` | Rappresenta l’azienda di trasporto e le licenze necessarie per operare legalmente. | [`packages/company.md`](packages/company.md) |
| `compliance` | Coordina controlli di conformità tra merce, mezzo, autista, documenti e regole generali. | [`packages/compliance.md`](packages/compliance.md) |
| `configuration` | Gestisce configurazioni di sistema, profili e valori configurabili senza hardcodare tutto nel codice. | [`packages/configuration.md`](packages/configuration.md) |
| `customer` | Gestisce cliente, account e contatti commerciali/operativi. | [`packages/customer.md`](packages/customer.md) |
| `document` | Gestisce documenti di trasporto, legali, sanitari, ADR, CMR, FIR e allegati di missione. | [`packages/document.md`](packages/document.md) |
| `driver` | Gestisce anagrafica autista, patenti, CQC, ADR, certificati operativi e idoneità alla missione. | [`packages/driver.md`](packages/driver.md) |
| `drivetime` | Contiene regole base sui tempi di guida e riposo per la pianificazione del viaggio. | [`packages/drivetime.md`](packages/drivetime.md) |
| `facility` | Rappresenta luoghi operativi come magazzini, hub, terminal, officine o punti di carico/scarico. | [`packages/facility.md`](packages/facility.md) |
| `fleet` | È il package più importante per mezzi, allestimenti, schede tecniche, convogli, certificati e compatibilità con il carico. | [`packages/fleet.md`](packages/fleet.md) |
| `fuel` | Traccia rifornimenti, carte carburante, litri, costo e consumo reale. | [`packages/fuel.md`](packages/fuel.md) |
| `identity` | Gestisce account utenti, ruoli e permessi applicativi. | [`packages/identity.md`](packages/identity.md) |
| `loadsecurity` | Gestisce dispositivi di fissaggio del carico e checklist di sicurezza. | [`packages/loadsecurity.md`](packages/loadsecurity.md) |
| `location` | Gestisce indirizzi, coordinate geografiche e fusi orari. | [`packages/location.md`](packages/location.md) |
| `maintenance` | Gestisce manutenzioni preventive, straordinarie, ticket autista e fermi macchina. | [`packages/maintenance.md`](packages/maintenance.md) |
| `notification` | Modella messaggi e notifiche verso utenti, clienti, autisti o operatori. | [`packages/notification.md`](packages/notification.md) |
| `operation` | Rappresenta la missione reale: il viaggio operativo eseguito con autista, mezzo/convoglio e percorso. | [`packages/operation.md`](packages/operation.md) |
| `order` | Gestisce la richiesta commerciale del cliente prima che diventi spedizione/missione. | [`packages/order.md`](packages/order.md) |
| `pricing` | Gestisce preventivi, breakdown prezzo, costi tratta, supplementi e sconti. | [`packages/pricing.md`](packages/pricing.md) |
| `reporting` | Modella definizioni report, metriche e report generati. | [`packages/reporting.md`](packages/reporting.md) |
| `route` | Gestisce piano percorso, fermate, pickup, delivery e stop intermedi. | [`packages/route.md`](packages/route.md) |
| `shared` | Contiene value object riutilizzabili e puri come peso, distanza, denaro, volume, dimensioni e finestre temporali. | [`packages/shared.md`](packages/shared.md) |
| `shipment` | Rappresenta la spedizione logistica derivata da un ordine accettato. | [`packages/shipment.md`](packages/shipment.md) |
| `sustainability` | Calcola emissioni, rating ambientale e informazioni su carburante/emission standard. | [`packages/sustainability.md`](packages/sustainability.md) |
| `telematics` | Gestisce dati da GPS/blackbox/CAN-bus e comportamento di guida. | [`packages/telematics.md`](packages/telematics.md) |
| `tire` | Gestisce pneumatici singoli, posizioni ruota, montaggi e rotazioni. | [`packages/tire.md`](packages/tire.md) |
| `tracking` | Gestisce eventi di tracking logistico e timeline della spedizione/missione. | [`packages/tracking.md`](packages/tracking.md) |

## Lettura consigliata

Per capire il sistema completo:

1. `shared`
2. `cargo`
3. `fleet`
4. `driver`
5. `company`
6. `document`
7. `compliance`
8. `order`, `shipment`, `operation`
9. moduli operativi: `maintenance`, `tire`, `fuel`, `telematics`, `loadsecurity`
10. moduli gestionali: `pricing`, `billing`, `reporting`, `notification`, `audit`
