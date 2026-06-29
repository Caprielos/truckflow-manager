# Package map spiegato

Questa guida spiega cosa contiene ogni package principale.

## Numeri attuali

| Area | Classi |
|---|---:|
| Domain | 264 |
| Application | 73 |
| Infrastructure | 46 |

## Regola semplice

```text
domain = oggetti e regole reali
application = azioni del sistema
infrastructure = dettagli tecnici
```

## Package principali
| Package | Scopo | Classi |
|---|---|---:|
| `application/common` | Oggetti comuni dell’application layer: risultati applicativi, errori e eccezioni leggibili. | 3 |
| `application/port` | Porte di ingresso e uscita: use case richiesti dall’esterno e repository richiesti dall’application. | 58 |
| `application/usecase` | Implementazioni dei casi d’uso: coordinano domain e repository, senza conoscere database o web. | 12 |
| `domain/audit` | Traccia modifiche e azioni importanti: chi ha fatto cosa, quando e con che gravità. | 6 |
| `domain/availability` | Disponibilità di risorse: veicoli, driver, rimorchi, strutture o altre risorse operative. | 4 |
| `domain/billing` | Fatture cliente, pagamenti e stato incassi. | 5 |
| `domain/cargo` | Merce trasportata: categoria, peso, volume, temperatura, ADR e regole operative cargo. | 9 |
| `domain/claim` | Danni, reclami, incidenti e ispezioni danni. | 7 |
| `domain/company` | Azienda di trasporto, licenze aziendali e autorizzazioni operative. | 4 |
| `domain/compliance` | Regole generali di conformità tra cargo, driver, veicolo e documenti. | 1 |
| `domain/configuration` | Configurazioni di sistema, parametri modificabili e valori di configurazione. | 6 |
| `domain/contract` | Contratti cliente, listini, tariffe e regole prezzo commerciali. | 6 |
| `domain/customer` | Clienti, account cliente e contatti operativi/amministrativi. | 6 |
| `domain/dataimport` | Import da fonti esterne: carte carburante, pedaggi, telematica, banca, paghe e fatture. | 5 |
| `domain/dispatch` | Ufficio traffico: candidati di assegnazione, controlli readiness e piani di dispatch. | 6 |
| `domain/document` | Documenti di trasporto: bolla/DDT, CMR, POD, fascicoli documentali. | 7 |
| `domain/driver` | Autisti, patenti, certificati, qualifiche operative e regole abilitative. | 9 |
| `domain/drivetime` | Regole ore guida, riposo e limiti operativi. | 1 |
| `domain/economics` | Costi, ricavi, IVA, acquisti flotta, fatture fornitori, utile/perdita e cassa/debito. | 29 |
| `domain/facility` | Strutture aziendali: deposito, sede, magazzino, piazzale, proprietà/affitto e spese. | 7 |
| `domain/fleet` | Mezzi, rimorchi, convogli, schede tecniche, assi, allestimenti e certificati veicolo. | 37 |
| `domain/fuel` | Rifornimenti carburante, carta carburante e transazioni fuel. | 3 |
| `domain/identity` | Account utente, ruoli e permessi applicativi. | 5 |
| `domain/inventory` | Magazzino: ricambi, DPI, gomme, AdBlue, scorte, movimenti e riordino. | 7 |
| `domain/loadsecurity` | Fissaggio carico, attrezzature e checklist sicurezza carico. | 4 |
| `domain/location` | Indirizzi, coordinate geografiche e luoghi fisici. | 3 |
| `domain/maintenance` | Manutenzione veicoli, ordini lavoro, scadenze e ticket difetti driver. | 6 |
| `domain/notification` | Messaggi, notifiche, canali e regole di invio. | 7 |
| `domain/operation` | Missione operativa reale: autista, convoglio, rotta e stati missione. | 3 |
| `domain/order` | Ordini di trasporto commerciali prima della spedizione. | 3 |
| `domain/parking` | Posti parcheggio numerati e risorse parcheggiate, inclusi convogli già agganciati. | 7 |
| `domain/payroll` | Costo autista: ore, straordinari, trasferte, ADR, CE, notturno, festivo e supplementi. | 9 |
| `domain/pricing` | Preventivi e breakdown prezzo cliente. | 6 |
| `domain/reporting` | Report generati, metriche e regole reporting. | 8 |
| `domain/route` | Tappe, pianificazione route e regole di percorso. | 4 |
| `domain/shared` | Value object riutilizzabili: Money, Weight, Distance, Notes, ecc. | 10 |
| `domain/shipment` | Spedizione nata da ordine accettato: cosa deve essere trasportato e stato logistico. | 3 |
| `domain/sustainability` | Emissioni e sostenibilità del trasporto. | 5 |
| `domain/telematics` | Snapshot GPS/CAN-bus, comportamento guida e dati telematici. | 4 |
| `domain/tire` | Gomme fisiche tracciabili, posizioni ruota, installazioni e stato pneumatico. | 8 |
| `domain/tracking` | Timeline eventi tracking della spedizione/missione. | 4 |
| `infrastructure/memory` | Repository in memoria basati su Map/ConcurrentHashMap per provare il sistema senza database. | 46 |
