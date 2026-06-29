# Package structure

## Package principali

| Package | Classi | Scopo |
| --- | --- | --- |
|  | 1 | Package del progetto TruckFlow. |
| application.common | 3 | Oggetti comuni dell’application layer: risultato applicativo, errore applicativo ed eccezione per risorse non trovate. |
| application.port.in | 12 | Porte di ingresso: interfacce degli use case che rappresentano azioni reali richieste dall’esterno. |
| application.port.out | 46 | Porte di uscita: interfacce repository usate dagli use case per leggere e salvare dati senza conoscere il database. |
| application.usecase | 12 | Implementazioni concrete degli use case. Coordinano repository e domain object. |
| domain.audit | 6 | Audit trail: chi ha fatto cosa, quando e con quale severità. |
| domain.availability | 4 | Disponibilità di risorse e regole di indisponibilità. |
| domain.billing | 5 | Fatturazione e pagamenti cliente. |
| domain.cargo | 9 | Merce e carico: categorie, ADR, peso, dimensioni e regole operative. |
| domain.claim | 7 | Danni, sinistri, reclami e ispezioni. |
| domain.company | 4 | Azienda di trasporto e licenze operative. |
| domain.compliance | 1 | Regole trasversali di conformità. |
| domain.configuration | 6 | Configurazioni di sistema e valori parametrizzabili. |
| domain.contract | 6 | Package del progetto TruckFlow. |
| domain.customer | 6 | Clienti, account cliente e contatti operativi/commerciali. |
| domain.dataimport | 5 | Import da fonti esterne: carte carburante, pedaggi, telematica, banca, fatture, paghe. |
| domain.dispatch | 6 | Ufficio traffico: candidati, controlli di readiness e piano dispatch. |
| domain.document | 7 | Documenti di trasporto, DDT/bolla, fascicolo documentale e regole documentali. |
| domain.driver | 9 | Autisti, patenti, CQC, ADR, qualifiche operative e stato disponibilità. |
| domain.drivetime | 1 | Regole su ore guida e riposo. |
| domain.economics | 29 | Economia aziendale: IVA, acquisti, costi, ricavi, utile/perdita, cassa e debito. |
| domain.facility | 7 | Strutture fisiche: sedi, depositi, magazzini, piazzali e relativi costi. |
| domain.fleet | 37 | Flotta: veicoli, rimorchi, convogli, allestimenti, specifiche tecniche, certificati e limiti. |
| domain.fuel | 3 | Rifornimenti, fuel card e regole consumo carburante. |
| domain.identity | 5 | Utenti, ruoli, permessi e stato account. |
| domain.inventory | 7 | Magazzino materiali: ricambi, gomme, DPI, AdBlue, olio, movimenti e riordino. |
| domain.loadsecurity | 4 | Fissaggio carico: attrezzature, checklist e regole. |
| domain.location | 3 | Indirizzi, coordinate e location con timezone. |
| domain.maintenance | 6 | Manutenzione, ticket difetti autista, fermi veicolo e ordini di lavoro. |
| domain.notification | 7 | Notifiche operative: canale, destinatario, priorità e stato. |
| domain.operation | 3 | Missione operativa reale: viaggio pianificato/eseguito con autista, convoglio e rotta. |
| domain.order | 3 | Ordini di trasporto: richiesta commerciale prima della spedizione. |
| domain.parking | 7 | Posti parcheggio, risorse parcheggiate, convogli già agganciati e regole di occupazione. |
| domain.payroll | 9 | Stipendio/costo autista per missione, inclusi premi, straordinari, ADR e contributi. |
| domain.pricing | 6 | Prezzo/preventivo verso il cliente e breakdown commerciale. |
| domain.reporting | 8 | Report generati, definizioni, metriche e stato report. |
| domain.route | 4 | Piano di viaggio con fermate, distanze e finestre temporali. |
| domain.shared | 10 | Value object riutilizzabili: denaro, peso, distanza, volume, dimensioni, percentuali, finestre temporali e note. |
| domain.shipment | 3 | Spedizione generata da un ordine accettato: merce, origine/destinazione, stato e regole logistiche. |
| domain.sustainability | 5 | Stime emissioni, standard ambientali e rating. |
| domain.telematics | 4 | Telematica: snapshot GPS/CAN e eventi comportamento guida. |
| domain.tire | 8 | Pneumatici come beni tracciabili: installazione, rotazione, stato e posizione ruota. |
| domain.tracking | 4 | Tracking operativo e timeline eventi. |
| infrastructure.memory | 46 | Implementazioni in memoria dei repository port. Servono per test, demo e sviluppo prima del database. |

## Regola pratica

- Se una classe rappresenta una regola o concetto del business, va in `domain`.
- Se una classe rappresenta un’azione del sistema, va in `application`.
- Se una classe salva/carica dati o dialoga con tecnologia esterna, va in `infrastructure`.
