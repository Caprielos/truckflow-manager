# Domain package map

Questa tabella riassume i package domain presenti nello zip.

| Package | Tema | Classi | Test class | Descrizione |
| --- | --- | --- | --- | --- |
| audit | Tracciamento audit | 6 | 3 | Registra azioni importanti, attori, severità e trail degli eventi. Serve per sapere chi ha fatto cosa e con quale impatto. |
| availability | Disponibilità risorse | 4 | 2 | Gestisce disponibilità e indisponibilità di autisti, veicoli, rimorchi, strutture o altre risorse operative. |
| billing | Fatturazione cliente | 5 | 3 | Rappresenta fatture emesse, stato fattura, pagamenti e regole base di incasso. |
| cargo | Merce e requisiti di carico | 9 | 6 | Descrive il carico, categorie merce, ADR, temperatura, rifiuti, animali vivi, alimentare e regole operative richieste dalla merce. |
| claim | Danni, sinistri e reclami | 7 | 2 | Gestisce danni, ispezioni, reclami cliente, severità e stato delle pratiche. |
| company | Azienda e licenze operative | 4 | 0 | Modella l’impresa di trasporto, le licenze aziendali e le verifiche di conformità aziendale. |
| compliance | Conformità generale | 1 | 1 | Contiene regole trasversali per verificare requisiti di spedizione, autista, mezzo, cargo, documenti e missione. |
| configuration | Configurazioni dominio | 6 | 1 | Permette di rappresentare valori configurabili per regole aziendali, soglie, parametri e impostazioni. |
| contract | Contratti cliente e listini | 6 | 1 | Gestisce contratti, rate card, tariff rules, supplementi e logiche tariffarie realistiche. |
| customer | Clienti e contatti | 6 | 3 | Gestisce cliente, account cliente, contatti, ruoli di contatto, stato e tipologia cliente. |
| dataimport | Import dati esterni | 5 | 1 | Rappresenta batch e record importati da carburante, pedaggi, telematica, banca, paghe o fatture fornitore. |
| dispatch | Ufficio traffico / pianificazione | 6 | 1 | Valuta candidati di assegnazione autista/mezzo/convoglio, readiness e scelta del candidato migliore. |
| document | Documenti trasporto | 7 | 2 | Gestisce documenti richiesti, bolla/DDT strutturata, bundle documentale spedizione e stati documentali. |
| driver | Autisti e abilitazioni | 9 | 2 | Modella autisti, patenti, CQC, ADR, qualifiche operative, certificati con validità e stato autista. |
| drivetime | Ore guida e riposo | 1 | 1 | Contiene regole per limiti di guida, pausa, riposo e compatibilità temporale del lavoro autista. |
| economics | Economia, costi, IVA e marginalità | 29 | 8 | Centro economico: acquisti asset, IVA, fatture fornitore, costi missione, ricavi, ledger, utile/perdita e debito/cassa negativa. |
| facility | Strutture aziendali e costi immobiliari | 7 | 2 | Gestisce depositi, piazzali, magazzini, proprietà/affitto/leasing e spese di struttura. |
| fleet | Flotta e mezzi | 37 | 5 | Modella veicoli, unità, allestimenti, assi, masse, dimensioni, certificati, combinazioni e compatibilità tecnica. |
| fuel | Carburante e consumi | 3 | 1 | Gestisce rifornimenti, provider carte carburante e regole di consumo. |
| identity | Utenti, ruoli e permessi | 5 | 3 | Gestisce account utente, ruoli e permessi per distinguere accesso operativo, amministrativo ed economico. |
| inventory | Magazzino ricambi e materiali | 7 | 1 | Gestisce articoli, giacenze, ubicazioni, movimenti stock, scorte minime e reorder signal. |
| loadsecurity | Fissaggio carico | 4 | 1 | Gestisce attrezzature e checklist per fissaggio carico, cinghie, barre, tappeti antiscivolo e controlli. |
| location | Luoghi e coordinate | 3 | 3 | Rappresenta indirizzi, coordinate geografiche e location operative. |
| maintenance | Manutenzione e fermi mezzo | 6 | 2 | Gestisce work order, difetti segnalati dall’autista, fermi mezzo, stati e tipi manutenzione. |
| notification | Notifiche | 7 | 2 | Modella messaggi, canali, priorità, destinatari e stato notifica. |
| operation | Missione operativa | 3 | 2 | Rappresenta il viaggio reale: missione, stato, regole operative, assegnazioni e chiusura. |
| order | Ordini di trasporto | 3 | 1 | Rappresenta richiesta commerciale cliente prima che diventi spedizione pianificata. |
| parking | Parcheggi e posti numerati | 7 | 1 | Gestisce posti, risorse parcheggiate, furgoni, rimorchi, trattori, convogli agganciati e readiness. |
| payroll | Costo autista e stipendio missione | 9 | 1 | Calcola voci paga in base a ore, patenti, ADR, rimorchio, trasporto speciale, straordinari, trasferte e costo aziendale. |
| pricing | Preventivi e prezzo cliente | 6 | 4 | Gestisce prezzo da proporre/fatturare al cliente, voci prezzo e breakdown commerciale. |
| reporting | Reportistica domain | 8 | 2 | Modella definizioni report, metriche, formato, stato e regole di generazione logica. |
| route | Percorsi e soste | 4 | 3 | Gestisce route plan, stop, carico/scarico, sequenza e regole di coerenza della rotta. |
| shared | Value object condivisi | 10 | 10 | Contiene Money, Weight, Distance, Dimension, Volume, TemperatureRange, TimeWindow, DateRange, Notes e Percentage. |
| shipment | Spedizione | 3 | 2 | Rappresenta la spedizione nata da un ordine accettato, con stato e regole; non contiene direttamente driver e mezzo. |
| sustainability | Emissioni e sostenibilità | 5 | 2 | Modella stime emissioni, standard, rating e regole di sostenibilità. |
| telematics | Telematica e comportamento guida | 4 | 1 | Gestisce snapshot GPS/CAN-bus e eventi comportamento guida come frenate, accelerazioni, consumo e odometro. |
| tire | Pneumatici | 8 | 1 | Gestisce gomma fisica, installazioni, rotazioni, stato, posizioni ruota e regole usura/sicurezza. |
| tracking | Tracking spedizione/missione | 4 | 3 | Gestisce eventi tracking e timeline di avanzamento operativo. |

## Note

- `shipment` corretto è solo `it.gabriele.truckflow.domain.shipment`.
- Il vecchio package `it.gabriele.truckflow.shipment` fuori da `domain` è stato rimosso.
- `economics` è il centro per costi/ricavi/IVA/profitto.
- `payroll` è separato perché lo stipendio autista ha regole proprie.
- `facility` e `parking` sono separati: una cosa è la struttura/deposito, un'altra è il posto parcheggio e cosa ci sta sopra.
- `inventory` è separato perché il magazzino ricambi/materiali non coincide con la flotta.
