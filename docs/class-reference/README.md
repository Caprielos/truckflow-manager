# Riferimento classi

Questa sezione è la parte più didattica “classe per classe”.

## Numeri

- Classi main: 384
- Package con classi: 44

## File principali

- `application.md`: common, port/in, port/out, usecase.
- `infrastructure-memory.md`: repository in memoria.
- `domain-*.md`: ogni package del domain.

## Package domain

| Package | File | Scopo |
| --- | --- | --- |
|  | .md |  |
| domain.audit | domain-audit.md | Audit trail: chi ha fatto cosa, quando e con quale severità. |
| domain.availability | domain-availability.md | Disponibilità di risorse e regole di indisponibilità. |
| domain.billing | domain-billing.md | Fatturazione e pagamenti cliente. |
| domain.cargo | domain-cargo.md | Merce e carico: categorie, ADR, peso, dimensioni e regole operative. |
| domain.claim | domain-claim.md | Danni, sinistri, reclami e ispezioni. |
| domain.company | domain-company.md | Azienda di trasporto e licenze operative. |
| domain.compliance | domain-compliance.md | Regole trasversali di conformità. |
| domain.configuration | domain-configuration.md | Configurazioni di sistema e valori parametrizzabili. |
| domain.contract | domain-contract.md |  |
| domain.customer | domain-customer.md | Clienti, account cliente e contatti operativi/commerciali. |
| domain.dataimport | domain-dataimport.md | Import da fonti esterne: carte carburante, pedaggi, telematica, banca, fatture, paghe. |
| domain.dispatch | domain-dispatch.md | Ufficio traffico: candidati, controlli di readiness e piano dispatch. |
| domain.document | domain-document.md | Documenti di trasporto, DDT/bolla, fascicolo documentale e regole documentali. |
| domain.driver | domain-driver.md | Autisti, patenti, CQC, ADR, qualifiche operative e stato disponibilità. |
| domain.drivetime | domain-drivetime.md | Regole su ore guida e riposo. |
| domain.economics | domain-economics.md | Economia aziendale: IVA, acquisti, costi, ricavi, utile/perdita, cassa e debito. |
| domain.facility | domain-facility.md | Strutture fisiche: sedi, depositi, magazzini, piazzali e relativi costi. |
| domain.fleet | domain-fleet.md | Flotta: veicoli, rimorchi, convogli, allestimenti, specifiche tecniche, certificati e limiti. |
| domain.fuel | domain-fuel.md | Rifornimenti, fuel card e regole consumo carburante. |
| domain.identity | domain-identity.md | Utenti, ruoli, permessi e stato account. |
| domain.inventory | domain-inventory.md | Magazzino materiali: ricambi, gomme, DPI, AdBlue, olio, movimenti e riordino. |
| domain.loadsecurity | domain-loadsecurity.md | Fissaggio carico: attrezzature, checklist e regole. |
| domain.location | domain-location.md | Indirizzi, coordinate e location con timezone. |
| domain.maintenance | domain-maintenance.md | Manutenzione, ticket difetti autista, fermi veicolo e ordini di lavoro. |
| domain.notification | domain-notification.md | Notifiche operative: canale, destinatario, priorità e stato. |
| domain.operation | domain-operation.md | Missione operativa reale: viaggio pianificato/eseguito con autista, convoglio e rotta. |
| domain.order | domain-order.md | Ordini di trasporto: richiesta commerciale prima della spedizione. |
| domain.parking | domain-parking.md | Posti parcheggio, risorse parcheggiate, convogli già agganciati e regole di occupazione. |
| domain.payroll | domain-payroll.md | Stipendio/costo autista per missione, inclusi premi, straordinari, ADR e contributi. |
| domain.pricing | domain-pricing.md | Prezzo/preventivo verso il cliente e breakdown commerciale. |
| domain.reporting | domain-reporting.md | Report generati, definizioni, metriche e stato report. |
| domain.route | domain-route.md | Piano di viaggio con fermate, distanze e finestre temporali. |
| domain.shared | domain-shared.md | Value object riutilizzabili: denaro, peso, distanza, volume, dimensioni, percentuali, finestre temporali e note. |
| domain.shipment | domain-shipment.md | Spedizione generata da un ordine accettato: merce, origine/destinazione, stato e regole logistiche. |
| domain.sustainability | domain-sustainability.md | Stime emissioni, standard ambientali e rating. |
| domain.telematics | domain-telematics.md | Telematica: snapshot GPS/CAN e eventi comportamento guida. |
| domain.tire | domain-tire.md | Pneumatici come beni tracciabili: installazione, rotazione, stato e posizione ruota. |
| domain.tracking | domain-tracking.md | Tracking operativo e timeline eventi. |
