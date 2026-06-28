# TruckFlow Manager — Requirements

## Scopo

Questo documento descrive i requisiti funzionali e non funzionali di TruckFlow Manager.

I requisiti non vengono implementati tutti subito.  
Servono a mantenere una visione completa del progetto.

---

## Requisiti funzionali principali

### Clienti

Il sistema deve permettere di:

- registrare clienti persone fisiche;
- registrare clienti aziende;
- gestire contatti;
- gestire condizioni di pagamento;
- gestire contratti e accordi di servizio;
- sospendere o disattivare un cliente.

---

### Ordini di trasporto

Il sistema deve permettere di:

- creare un ordine di trasporto;
- collegare l’ordine a un cliente;
- indicare punto di ritiro;
- indicare punto di consegna;
- indicare carico richiesto;
- indicare finestra di ritiro;
- indicare finestra di consegna;
- assegnare priorità;
- modificare lo stato dell’ordine;
- annullare l’ordine.

---

### Preventivi

Il sistema deve permettere di:

- creare preventivi;
- stimare costi;
- indicare prezzo al cliente;
- indicare carburante stimato;
- indicare pedaggi stimati;
- indicare maggiorazioni;
- indicare sconti;
- inviare, accettare, rifiutare o far scadere un preventivo.

---

### Spedizioni

Il sistema deve permettere di:

- creare una spedizione da un ordine accettato;
- collegare cliente, carico, tratta e prezzo;
- assegnare autista;
- assegnare combinazione veicolo;
- pianificare ritiro e consegna;
- cambiare stato spedizione;
- registrare eventi;
- registrare prova di consegna;
- annullare la spedizione secondo regole valide.

---

### Missioni operative

Il sistema deve distinguere spedizione e missione.

Il sistema deve permettere di:

- creare una missione operativa;
- collegare una o più spedizioni;
- collegare autista;
- collegare combinazione veicolo;
- gestire fermate;
- gestire piano di carico;
- gestire avanzamento missione.

---

### Flotta

Il sistema deve permettere di gestire:

- furgoni;
- camion rigidi;
- camion frigo;
- camion telonati;
- camion cisterna;
- camion per carico sfuso;
- motrici;
- trattori stradali;
- rimorchi;
- semirimorchi;
- combinazioni veicolo.

Il sistema deve gestire:

- targa;
- marca;
- modello;
- anno;
- chilometri;
- categoria legale;
- stato;
- capacità peso;
- capacità volume;
- dimensioni utili;
- posti pallet;
- allestimento;
- assicurazione;
- manutenzioni;
- compatibilità motrice/rimorchio.

---

### Carico

Il sistema deve permettere di descrivere il carico con:

- tipo merce;
- peso;
- volume;
- dimensioni;
- numero colli;
- numero pallet;
- tipo imballaggio;
- valore dichiarato;
- requisiti speciali;
- temperatura;
- ADR;
- fragilità;
- sicurezza.

Il sistema deve verificare se il carico entra nel mezzo per:

- peso;
- volume;
- lunghezza;
- larghezza;
- altezza;
- numero pallet.

---

### Autisti

Il sistema deve permettere di gestire:

- dati anagrafici;
- patente;
- categorie patente;
- CQC;
- ADR;
- carta tachigrafica;
- disponibilità;
- controlli medici;
- qualifiche;
- stato.

Il sistema deve verificare se l’autista può guidare il mezzo e trasportare il carico.

---

### Pianificazione

Il sistema deve permettere di:

- pianificare una tratta;
- stimare distanza;
- stimare durata;
- pianificare soste;
- pianificare pause;
- pianificare riposi;
- stimare arrivo;
- registrare posizione;
- simulare tracking futuro.

---

### Compliance

Il sistema deve rispondere alla domanda:

```text
Posso eseguire questa spedizione con questo autista,
questa combinazione di veicoli, questo carico,
questa tratta e questa data?
```

Il sistema deve controllare:

- patente;
- CQC;
- ADR;
- ATP se richiesto;
- tachigrafo;
- disponibilità autista;
- disponibilità mezzo;
- disponibilità rimorchio;
- compatibilità motrice/rimorchio;
- peso;
- volume;
- dimensioni;
- documenti;
- permessi;
- divieti;
- tempi guida;
- pause;
- finestre orarie.

---

### Documenti

Il sistema deve permettere di gestire:

- DDT;
- CMR;
- note di consegna;
- documenti ADR;
- documenti assicurativi;
- prova di consegna;
- firma;
- allegati;
- danni segnalati.

---

### Utenti e ruoli

Il sistema deve prevedere account utente con ruoli diversi:

```text
ADMIN
OPERATIONS_MANAGER
DISPATCHER
FLEET_MANAGER
DRIVER
CUSTOMER
ACCOUNTING
VIEWER
```

Il sistema dovrà supportare permessi come:

```text
MANAGE_USERS
VIEW_CUSTOMERS
MANAGE_CUSTOMERS
CREATE_ORDER
CREATE_QUOTE
APPROVE_QUOTE
CREATE_SHIPMENT
ASSIGN_DRIVER
ASSIGN_VEHICLE
VIEW_TRACKING
MANAGE_FLEET
MANAGE_MAINTENANCE
VIEW_PRICING
MANAGE_DOCUMENTS
CLOSE_SHIPMENT
VIEW_REPORTS
```

---

### Audit

Il sistema deve registrare azioni importanti:

- creazione ordine;
- creazione spedizione;
- assegnazione autista;
- assegnazione veicolo;
- modifica prezzo;
- annullamento spedizione;
- chiusura spedizione;
- caricamento documento.

---

## Requisiti non funzionali

### Scalabilità del dominio

Il dominio deve essere progettato per aggiungere nuove regole senza riscrivere tutto.

### Indipendenza tecnologica

Il dominio non deve dipendere da framework, database o servizi esterni.

### Testabilità

Le regole di business devono essere testabili con unit test.

### Manutenibilità

Le classi devono avere responsabilità chiare e nomi leggibili.

### Estendibilità

Il sistema deve poter aggiungere:

- nuovi tipi mezzo;
- nuovi tipi rimorchio;
- nuove regole;
- nuovi permessi;
- nuovi controlli compliance;
- nuove integrazioni.

---

## Fuori scope iniziale

Non verranno implementati subito:

- frontend completo;
- login reale;
- Spring Security;
- database completo;
- Google Maps reale;
- pedaggi reali;
- carburante reale;
- fatturazione completa;
- reportistica completa;
- app autista;
- portale cliente.
