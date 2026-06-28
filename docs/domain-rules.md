# TruckFlow Manager — Domain Rules

## Scopo

Questo documento raccoglie le regole di business principali del dominio.

Le regole non sono ancora codice, ma devono guidare la futura implementazione.

---

# Regole su Shipment

1. Una spedizione nasce in stato `CREATED`.
2. Una spedizione può diventare `ASSIGNED` solo se ha un autista e una combinazione veicolo assegnati.
3. Una spedizione può diventare `PLANNED` solo se esiste una pianificazione minima.
4. Una spedizione può diventare `IN_TRANSIT` solo se è assegnata e pianificata.
5. Una spedizione può diventare `DELIVERED` solo se era `IN_TRANSIT`.
6. Una spedizione consegnata non può tornare a uno stato precedente.
7. Una spedizione annullata non può ripartire senza nuova procedura.
8. Una spedizione non assegna un `Truck`, ma una `VehicleCombination`.
9. Una spedizione deve poter generare o essere collegata a una `TransportMission`.

---

# Regole su TransportMission

1. Una missione rappresenta il viaggio operativo reale.
2. Una missione può contenere una o più spedizioni.
3. Una missione deve avere almeno una combinazione veicolo.
4. Una missione deve avere almeno un autista principale.
5. Una missione deve avere fermate ordinate.
6. Una missione deve poter avere un piano di carico.
7. Una missione deve registrare eventi importanti.
8. Una missione può avere più ritiri e più consegne.

---

# Regole su VehicleCombination

1. Una combinazione veicolo può essere:
   - furgone singolo;
   - camion rigido;
   - camion con rimorchio;
   - trattore stradale con semirimorchio;
   - motrice con rimorchio.
2. Una combinazione deve avere almeno un veicolo motorizzato.
3. Una combinazione può avere zero o un rimorchio/semirimorchio nella prima versione.
4. Motrice e rimorchio devono essere tecnicamente compatibili.
5. La capacità totale deve considerare peso, volume e dimensioni.
6. La combinazione non può essere usata se uno dei mezzi è in manutenzione o fuori servizio.
7. La combinazione deve essere compatibile con il tipo di carico.

---

# Regole su Cargo

1. Il carico deve avere almeno descrizione, peso e tipo.
2. Il carico può avere volume, dimensioni, colli e pallet.
3. Il carico deve rispettare la capacità del mezzo.
4. Il carico refrigerato richiede mezzo compatibile.
5. Il carico ADR richiede qualifiche e documenti compatibili.
6. Il carico fragile richiede requisiti adeguati.
7. Il carico di alto valore può richiedere sicurezza aggiuntiva.
8. Il carico non deve superare:
   - peso massimo;
   - volume utile;
   - lunghezza utile;
   - larghezza utile;
   - altezza utile;
   - numero pallet supportato.
9. Il sistema deve poter gestire carichi multipli in una missione.

---

# Regole su Driver

1. Un autista deve avere una patente valida.
2. L’età non si salva come dato fisso: si calcola dalla data di nascita.
3. La patente deve essere compatibile con il mezzo.
4. La CQC deve essere valida se richiesta dal tipo di trasporto.
5. L’ADR deve essere valida se il carico lo richiede.
6. La carta tachigrafica deve essere valida se richiesta.
7. L’autista deve essere disponibile.
8. L’autista sospeso o non disponibile non può essere assegnato.
9. Un autista può essere collegato a un account utente, ma `Driver` e `UserAccount` restano concetti separati.

---

# Regole su Planning

1. Il piano viaggio deve stimare partenza e arrivo.
2. Il piano viaggio deve prevedere soste e pause.
3. Le regole sui tempi di guida devono essere configurabili.
4. Le pause non devono essere hardcodate direttamente nella spedizione.
5. Il sistema deve poter simulare una posizione veicolo futura.
6. La posizione del veicolo non dipende da Google Maps nel dominio.
7. Google Maps o servizi simili saranno integrazioni esterne.

---

# Regole su Regulation

1. I divieti di circolazione devono essere rappresentabili.
2. Le deroghe devono essere rappresentabili.
3. I permessi devono essere rappresentabili.
4. ZTL, LEZ, gallerie, ponti e restrizioni ADR devono poter essere modellati.
5. Le normative reali non devono essere hardcodate senza verifica ufficiale.
6. Le regole devono essere configurabili tramite `Rule` e `RuleSet`.

---

# Regole su Compliance

1. Ogni controllo deve produrre un risultato.
2. Un risultato può essere:
   - `VALID`;
   - `INVALID`;
   - `WARNING`;
   - `INSUFFICIENT_DATA`.
3. Una violazione bloccante impedisce l’esecuzione della spedizione.
4. Una violazione warning non blocca necessariamente, ma deve essere mostrata.
5. I controlli devono essere separati per responsabilità:
   - patente;
   - CQC;
   - ADR;
   - peso;
   - volume;
   - dimensioni;
   - documenti;
   - permessi;
   - strada;
   - tempi di guida.
6. Nuovi controlli devono poter essere aggiunti senza riscrivere `Shipment`.

---

# Regole su carico e scarico

1. TruckFlow Manager rappresenta un’azienda che trasporta.
2. L’autista non è responsabile del carico fisico.
3. L’autista non è responsabile dello scarico fisico.
4. Carico e scarico sono responsabilità di:
   - cliente;
   - destinatario;
   - magazzino;
   - terza parte.
5. Il sistema deve comunque conoscere requisiti del sito:
   - appuntamento;
   - muletto;
   - baia di carico;
   - orari;
   - accesso;
   - referente;
   - tempi stimati.

---

# Regole su Identity e Audit

1. `UserAccount` rappresenta l’accesso al sistema.
2. `Driver` rappresenta l’autista come figura professionale.
3. `Customer` rappresenta il cliente commerciale.
4. Un account può essere collegato a un autista, cliente, dipendente o vettore esterno.
5. Le password non devono mai essere salvate in chiaro.
6. Ogni azione importante deve essere auditabile.
7. L’audit deve sapere:
   - chi ha agito;
   - cosa ha fatto;
   - quando;
   - su quale oggetto;
   - eventuali note.

---

# Regole architetturali

1. Il dominio non dipende da Spring.
2. Il dominio non dipende dal database.
3. Il dominio non dipende da Google Maps.
4. Il dominio non dipende dal frontend.
5. Il dominio non dipende dal login tecnico.
6. Le integrazioni esterne stanno in infrastructure.
7. Le API REST stanno fuori dal dominio.
8. Le classi di dominio devono essere testabili con unit test.
