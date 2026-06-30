# TruckFlow Manager — Documentazione completa del dominio

## Introduzione

TruckFlow Manager è un progetto pensato per diventare un gestionale professionale per aziende di trasporto, logistica e movimentazione merci. In questa fase il progetto non sta ancora costruendo API, database, controller o interfacce grafiche: sta costruendo prima il cuore del sistema, cioè il dominio.

Il dominio è la parte più importante dell’applicazione perché descrive i concetti reali dell’azienda: utenti applicativi, qualificazioni, autisti, meccanici, operatori di magazzino, dispatcher e manager. Tutto quello che verrà dopo, come database, REST API, microservizi, frontend, sicurezza, documenti, scadenze e notifiche, dovrà appoggiarsi a questo modello.

L’obiettivo di questa documentazione è spiegare in modo chiaro che cosa è stato fatto fino a questo momento, perché è stato fatto così e come i package principali collaborano tra loro.

Il progetto attuale è basato su tre macro-domini:

- `domain.users`
- `domain.qualifications`
- `domain.operational`

Questi tre package sono separati perché rappresentano concetti diversi. `users` rappresenta chi accede all’applicazione. `qualifications` rappresenta il catalogo statico delle abilitazioni. `operational` rappresenta le figure operative reali dell’azienda.

---

# 1. Obiettivo del progetto

TruckFlow Manager vuole modellare un sistema aziendale per la gestione del trasporto e della logistica. Il progetto nasce con una scelta precisa: prima costruire un dominio pulito e solo dopo aggiungere infrastruttura, database, API e servizi esterni.

Questa scelta evita di legare subito il modello aziendale a tecnologie specifiche. In altre parole, il dominio deve poter esistere anche senza Spring, senza JPA, senza JSON, senza controller e senza database. Il codice di dominio deve parlare il linguaggio del business, non quello dell’infrastruttura tecnica.

Il sistema risolve, o si prepara a risolvere, problemi come:

- distinguere account applicativi e persone operative reali;
- gestire ruoli e permessi degli utenti dell’applicazione;
- creare un catalogo ordinato di patenti, CQC, ADR, certificazioni e abilitazioni;
- collegare in futuro autisti, meccanici e operatori alle loro qualificazioni;
- rappresentare dispatcher e manager come figure operative con ambiti di responsabilità;
- mantenere separati dati statici, profili operativi, regole future e infrastruttura tecnica.

Il progetto segue alcuni principi architetturali semplici ma importanti:

- separazione dei contesti;
- dominio puro;
- value object per dati importanti;
- entity per oggetti con identità propria;
- assenza di dipendenze tecniche nel dominio;
- assenza di duplicazioni concettuali;
- predisposizione per evoluzione futura verso monolite modulare o microservizi.

Anche senza conoscere Domain-Driven Design, il principio è semplice: ogni package deve avere una responsabilità chiara e non deve fare il lavoro degli altri.

---

# 2. Package `domain.users`

## 2.1 Scopo del package

Il package `domain.users` rappresenta gli account applicativi, cioè le persone che possono accedere all’app TruckFlow Manager.

Un `User` non rappresenta automaticamente un autista, un meccanico o un magazziniere. Rappresenta prima di tutto un account: username, password hash, ruoli applicativi, permessi, stato dell’account, profilo, metadati e preferenze.

Questa separazione è fondamentale. Una persona può avere un account applicativo e, in più, un profilo operativo. Per esempio, Mario Rossi può avere un `User` per accedere all’app e un `Driver` nel dominio operativo per rappresentare il suo ruolo reale di autista.

Quindi:

- `User` = account applicativo;
- `Driver` = figura operativa reale;
- `Mechanic` = figura operativa reale;
- `WarehouseOperator` = figura operativa reale;
- `Dispatcher` = figura operativa reale;
- `Manager` = figura operativa reale.

Il package `domain.users` non contiene patenti, abilitazioni, scadenze, documenti o dati operativi specifici. Quelli appartengono ad altri contesti.

## 2.2 Perché `User` è separato dal dominio operativo

Separare `User` dalle figure operative evita un errore comune: mischiare il login con il lavoro reale della persona.

Un dispatcher può accedere all’app con un ruolo applicativo `DISPATCHER`, ma il suo profilo operativo potrebbe contenere dati diversi: area gestita, reparto, responsabilità, scope operativi. Allo stesso modo, un autista può essere un utente dell’app, ma la sua patente C, CQC o ADR non appartengono direttamente all’account: appartengono al suo profilo operativo.

Questa separazione permette al sistema di evolvere meglio. Un `User` può avere zero profili operativi, uno solo o più di uno. Per esempio, una persona potrebbe essere sia meccanico sia operatore di magazzino, oppure un manager potrebbe avere anche responsabilità operative su più sedi.

## 2.3 Classi del package `domain.users`

### `User`

`User` è l’entity principale del package. Rappresenta l’account applicativo.

Contiene:

- identificativo utente;
- username;
- password hash;
- ruoli applicativi;
- permessi granulari;
- stato dell’account;
- profilo utente;
- metadati;
- preferenze;
- note.

`User` è una entity perché ha una propria identità e può cambiare stato nel tempo. Può essere attivo, sospeso o disabilitato. Può ricevere nuovi ruoli, perdere ruoli, ricevere permessi o aggiornare il profilo.

Il dominio contiene solo logica interna, come controllare se l’utente è attivo, se ha un ruolo, se ha un permesso o se può effettuare il login secondo lo stato dell’account. Non contiene login reale, JWT, sessioni, cifratura password o database.

### `UserId`

`UserId` è un value object che incapsula un identificativo UUID.

Invece di usare direttamente un UUID ovunque, il progetto usa un tipo dedicato. Questo rende il dominio più chiaro e riduce il rischio di confondere un identificativo utente con altri identificativi futuri, come `DriverId`, `VehicleId` o `TripId`.

`UserId` può essere creato casualmente quando serve un nuovo identificativo.

### `Username`

`Username` è un value object dedicato allo username.

Serve a centralizzare le regole sul nome utente:

- normalizzazione;
- obbligatorietà;
- formato valido;
- uso coerente del lowercase;
- controllo dei caratteri ammessi.

Questo evita che il progetto tratti lo username come una semplice stringa non controllata.

### `UserPasswordHash`

`UserPasswordHash` rappresenta solo l’hash della password.

Questa scelta è importante perché nel dominio non deve mai esistere una password in chiaro. Il dominio non si occupa di calcolare l’hash, verificare la password o gestire login. Si limita a dire che l’account conserva un hash valido.

La parte di sicurezza reale arriverà in un livello diverso, non nel dominio puro.

### `UserRole`

`UserRole` rappresenta i ruoli applicativi, cioè i ruoli usati per accedere alle funzionalità dell’app.

I ruoli presenti sono:

- `ADMIN`
- `DISPATCHER`
- `WAREHOUSE_OPERATOR`
- `DRIVER_USER`
- `MECHANIC`
- `MANAGER`

Questi ruoli non rappresentano automaticamente figure operative reali. Per esempio, `DRIVER_USER` indica che l’utente può usare l’app come autista, ma il profilo operativo reale dell’autista si trova nel package `domain.operational.driver`.

### `UserPermission`

`UserPermission` rappresenta i permessi granulari.

I permessi presenti coprono le principali aree funzionali:

- utenti;
- autisti;
- mezzi;
- qualificazioni;
- viaggi;
- magazzino;
- manutenzione;
- report.

Per ogni area sono previsti permessi di visualizzazione e gestione, per esempio `VIEW_DRIVERS` e `MANAGE_DRIVERS`.

La presenza dei permessi permette in futuro di avere controlli più precisi rispetto ai soli ruoli.

### `UserPermissions`

`UserPermissions` è un value object che contiene l’insieme dei permessi assegnati all’utente.

È stato creato per evitare di gestire direttamente un insieme grezzo di `UserPermission` dentro `User`. Espone metodi per verificare, aggiungere e rimuovere permessi in modo sicuro.

È immutabile: quando si aggiunge o si rimuove un permesso, viene prodotto un nuovo insieme di permessi. Questo riduce effetti collaterali e rende il dominio più sicuro.

### `UserStatus`

`UserStatus` rappresenta lo stato dell’account.

Gli stati presenti sono:

- `ACTIVE`
- `SUSPENDED`
- `DISABLED`

Un utente attivo può accedere. Un utente sospeso è temporaneamente bloccato. Un utente disabilitato è in uno stato più forte e non viene modificato normalmente senza un metodo esplicito.

### `UserProfile`

`UserProfile` rappresenta il profilo dell’account applicativo.

Contiene dati anagrafici e riferimenti a contatto e indirizzo. Serve a separare i dati descrittivi dell’utente dalla logica principale di `User`.

Non va confuso con `OperationalProfile`, che invece descrive la persona nel contesto operativo aziendale.

### `UserContact`

`UserContact` contiene i dati di contatto dell’utente:

- email;
- telefono;
- cellulare.

Richiede che almeno un contatto sia presente e valida l’email se inserita.

### `UserAddress`

`UserAddress` rappresenta l’indirizzo dell’utente:

- via;
- numero civico;
- CAP;
- città;
- provincia;
- paese.

Contiene normalizzazione e validazioni essenziali.

### `UserMetadata`

`UserMetadata` contiene informazioni sulla creazione e sull’ultimo aggiornamento dell’account:

- data creazione;
- data aggiornamento;
- autore creazione;
- autore aggiornamento.

Non è audit tecnico. Non registra IP, device, login o tentativi falliti. Serve solo a tracciare la storia base dell’account nel dominio.

### `UserPreferences`

`UserPreferences` contiene preferenze dell’utente:

- lingua;
- tema;
- notifiche abilitate o disabilitate.

È utile per personalizzare l’esperienza applicativa senza mischiare queste informazioni con ruoli e permessi.

## 2.4 Perché abbiamo scelto value object dedicati

Il progetto usa value object come `UserId`, `Username`, `UserPasswordHash`, `UserContact`, `UserAddress`, `UserMetadata` e `UserPreferences` perché questi concetti hanno regole proprie.

Usare semplici stringhe o UUID ovunque renderebbe il dominio più fragile. Un value object invece concentra validazione, normalizzazione e significato in un punto preciso.

Questa scelta rende il codice più chiaro e riduce errori futuri.

## 2.5 Perché `User` non contiene patenti o qualificazioni

Le patenti, CQC, ADR e altre abilitazioni non appartengono all’account applicativo. Appartengono alla persona operativa, per esempio all’autista o al magazziniere.

Per questo `User` non contiene qualificazioni. Le qualificazioni sono modellate nel catalogo `domain.qualifications` e vengono assegnate alle figure operative tramite `OperationalQualification`.

## 2.6 Perché `UserId` è un riferimento e non un `User`

Nel dominio operativo, le entity come `Driver`, `Mechanic` o `Dispatcher` contengono `UserId`, non direttamente `User`.

Questo evita accoppiamenti forti tra package diversi. Il profilo operativo non deve conoscere tutta la struttura dell’account. Deve solo sapere a quale account è collegato.

Questa scelta rende il dominio più modulare e più facile da evolvere.

---

# 3. Package `domain.qualifications`

## 3.1 Scopo del package

Il package `domain.qualifications` contiene il catalogo statico delle qualificazioni disponibili in TruckFlow Manager.

Una qualificazione può essere una patente, una CQC, un’abilitazione ADR, una certificazione alimentare, una formazione per sicurezza, una qualifica per macchine operatrici o una formazione interna aziendale.

Il catalogo risponde alla domanda:

“Quali qualificazioni esistono nel sistema?”

Non risponde invece a domande come:

- chi possiede questa qualificazione?
- è scaduta?
- è valida?
- qual è il PDF collegato?
- serve per questo viaggio?
- serve per questo mezzo?

Queste domande verranno gestite da altri moduli futuri.

## 3.2 Classi del package

### `Qualification`

`Qualification` è l’enum principale che contiene tutte le qualificazioni note al sistema.

Ogni qualificazione ha:

- codice tecnico;
- nome leggibile;
- categoria;
- descrizione breve;
- descrizione lunga.

Sono incluse categorie come:

- patenti di guida;
- CQC;
- ADR;
- alimenti e farmaci;
- animali;
- rifiuti;
- macchine operatrici;
- sicurezza;
- porti e aeroporti;
- logistica aziendale.

Le patenti sono state modellate come qualificazioni perché, nel sistema, sono un tipo di abilitazione possedibile da una figura operativa.

### `QualificationCategory`

`QualificationCategory` raggruppa le qualificazioni per area.

Le categorie attuali sono:

- `DRIVING_LICENSES`
- `CQC`
- `ADR`
- `FOOD_PHARMACEUTICALS`
- `ANIMALS`
- `WASTE`
- `MACHINE_OPERATORS`
- `SAFETY`
- `PORTS_AND_AIRPORTS`
- `COMPANY_LOGISTICS`

Ogni categoria ha anche un nome leggibile e una descrizione. Questo permette di mostrare il catalogo in modo più ordinato in future API o interfacce.

### `QualificationCatalog`

`QualificationCatalog` è una classe di consultazione del catalogo.

Permette di:

- ottenere tutte le qualificazioni;
- filtrare per categoria;
- cercare una qualificazione tramite codice.

Non contiene logica di business complessa. Serve solo a rendere il catalogo più comodo da usare.

## 3.3 Perché il catalogo è statico

Il catalogo è statico perché rappresenta le tipologie di qualificazioni conosciute dal sistema, non le qualificazioni possedute da una persona.

Per esempio, `DRIVING_LICENSE_C` indica che esiste la patente C come tipo di qualificazione. Non dice che Mario Rossi la possiede. La relazione tra una figura operativa e una qualificazione viene fatta nel package `domain.operational`.

## 3.4 Perché il catalogo non contiene scadenze o documenti

Scadenze e documenti sono dati dinamici e specifici della singola persona o del singolo certificato.

Il catalogo non deve contenere:

- data di rilascio;
- data di scadenza;
- PDF;
- documentId;
- validità;
- regole di rinnovo;
- obblighi normativi.

Questi dati saranno gestiti in futuro da package o moduli separati, come documenti, scadenze e compliance.

## 3.5 Come viene usato dalle figure operative

Le figure operative non duplicano patenti o abilitazioni. Usano direttamente `Qualification` attraverso `OperationalQualification`.

Esempio concettuale:

- il catalogo contiene `DRIVING_LICENSE_C`;
- un `Driver` possiede una `OperationalQualification` che punta a `DRIVING_LICENSE_C`;
- in futuro, un modulo scadenze potrà collegarsi a quella qualificazione posseduta per verificare validità e rinnovi.

---

# 4. Package `domain.operational`

## 4.1 Scopo del package

Il package `domain.operational` rappresenta le figure operative reali dell’azienda.

Sono incluse:

- autisti;
- meccanici;
- operatori di magazzino;
- dispatcher;
- manager.

Questo package è separato da `domain.users` perché una figura operativa non è un account. Un account serve per accedere all’applicazione. Una figura operativa rappresenta invece il ruolo reale svolto in azienda.

Il package è separato da `domain.qualifications` perché il catalogo delle qualificazioni è statico, mentre le figure operative possiedono qualificazioni concrete.

## 4.2 Cosa non contiene il dominio operativo

Il dominio operativo non contiene:

- scadenze;
- date di validità;
- documenti PDF;
- file caricati;
- disponibilità giornaliera;
- turni;
- pianificazione viaggi;
- assegnazioni mezzi;
- compliance automatica;
- microservizi;
- controller;
- database;
- sicurezza tecnica.

Questi concetti arriveranno dopo, in contesti separati.

## 4.3 Perché è diviso in sottopackage

Il package `domain.operational` è diviso in sottopackage perché ogni figura operativa ha una responsabilità diversa.

La struttura attuale è:

- `common` per concetti condivisi;
- `driver` per gli autisti;
- `mechanic` per i meccanici;
- `warehouse` per gli operatori di magazzino;
- `dispatcher` per chi coordina l’operatività;
- `manager` per chi supervisiona l’operatività.

Questa separazione rende il dominio più leggibile e permette di evolvere ogni figura senza confondere le responsabilità.

---

## 4.1 Package `common`

Il package `domain.operational.common` contiene value object ed enum condivisi da tutte le figure operative.

### `OperationalStatus`

`OperationalStatus` rappresenta lo stato operativo di una figura aziendale.

Gli stati sono:

- `ACTIVE`
- `SUSPENDED`
- `NOT_ELIGIBLE`

`ACTIVE` indica che la figura è attiva. `SUSPENDED` indica una sospensione temporanea. `NOT_ELIGIBLE` indica che la figura non è attualmente idonea o abilitata per operare.

Non esistono stati come “expired”, perché il dominio operativo non gestisce scadenze.

### `OperationalMetadata`

`OperationalMetadata` traccia creazione e modifica delle figure operative.

Contiene:

- data creazione;
- data aggiornamento;
- autore creazione;
- autore aggiornamento.

È separato da `UserMetadata` perché appartiene al contesto operativo, non all’account applicativo.

### `OperationalQualification`

`OperationalQualification` rappresenta una qualificazione posseduta da una figura operativa.

Contiene:

- riferimento alla `Qualification` del catalogo;
- numero di riferimento;
- paese di emissione;
- livello;
- note.

Il campo livello è utile per qualificazioni che possono avere gradi o livelli diversi, come formazione base/avanzata o qualificazioni interne.

Non contiene scadenze, documenti, date di rilascio o PDF.

### `OperationalScope`

`OperationalScope` rappresenta l’ambito operativo di responsabilità.

È usato soprattutto da dispatcher e manager.

Contiene:

- codice;
- nome;
- descrizione;
- area.

Esempi di scope possono essere: pianificazione nazionale, coordinamento ADR, sede di Roma, area Nord Italia, operazioni di magazzino o supervisione manutenzione.

### `OperationalProfile`

`OperationalProfile` rappresenta il profilo operativo della persona.

Contiene:

- nome;
- cognome;
- nome completo derivato;
- email;
- telefono;
- cellulare;
- reparto;
- posizione;
- note.

È diverso da `UserProfile`. `UserProfile` appartiene all’account. `OperationalProfile` descrive la persona nel contesto operativo aziendale.

### `OperationalCode`

Il progetto attuale include anche `OperationalCode`, un value object per il codice interno aziendale della figura operativa.

Esempi:

- DRV-001;
- MEC-023;
- WH-112;
- DSP-004;
- MNG-002.

Questo codice è diverso dall’identificativo tecnico. L’ID serve al sistema, mentre l’OperationalCode serve all’azienda e alle persone che leggono il gestionale.

---

## 4.2 Package `driver`

### `Driver`

`Driver` rappresenta l’autista reale dell’azienda.

Contiene:

- identificativo driver;
- codice operativo interno;
- riferimento all’account utente tramite `UserId`;
- profilo operativo;
- qualificazioni possedute;
- stato operativo;
- metadati;
- note.

Il driver non contiene un oggetto `User`, ma solo `UserId`. Questo mantiene separato il dominio operativo dal dominio degli account.

Il driver usa `OperationalQualification` per rappresentare patenti, CQC, ADR e altre abilitazioni. Non esiste una classe separata `DriverLicense` perché le patenti sono già presenti nel catalogo `Qualification`.

Questa scelta evita duplicazioni. La patente C è una `Qualification`. Se un autista la possiede, sarà rappresentata come `OperationalQualification` collegata a `DRIVING_LICENSE_C`.

### `DriverId`

`DriverId` è il value object che identifica un autista nel dominio operativo.

Serve a non confondere l’identità dell’autista con l’identità dell’account utente.

### Significato dello stato per un autista

Per un driver:

- `ACTIVE` significa che l’autista è operativo;
- `SUSPENDED` significa che è sospeso;
- `NOT_ELIGIBLE` significa che non è idoneo o non abilitato.

Il dominio non decide ancora se può guidare un mezzo specifico. Questa regola arriverà dopo.

---

## 4.3 Package `mechanic`

### `Mechanic`

`Mechanic` rappresenta il meccanico reale dell’azienda.

Contiene:

- identificativo meccanico;
- codice operativo interno;
- riferimento a `UserId`;
- profilo operativo;
- qualificazioni tecniche;
- stato operativo;
- metadati;
- note.

Le qualificazioni tecniche possono essere, per esempio, sicurezza, primo soccorso, carrello elevatore, gru autocarro o altre abilitazioni presenti nel catalogo.

Il meccanico è separato dagli altri ruoli perché ha responsabilità tecniche e operative diverse da autisti, magazzinieri, dispatcher e manager.

### `MechanicId`

`MechanicId` identifica in modo univoco il meccanico nel dominio operativo.

---

## 4.4 Package `warehouse`

### `WarehouseOperator`

`WarehouseOperator` rappresenta il magazziniere o operatore di magazzino.

Contiene:

- identificativo operatore;
- codice operativo interno;
- riferimento a `UserId`;
- profilo operativo;
- qualificazioni;
- stato;
- metadati;
- note.

Può avere abilitazioni come muletto, PLE, movimentazione carichi, gestione magazzino o uso rampe e baie.

È separato dagli altri ruoli perché le attività di magazzino hanno competenze e responsabilità specifiche.

### `WarehouseOperatorId`

`WarehouseOperatorId` identifica l’operatore di magazzino nel dominio operativo.

---

## 4.5 Package `dispatcher`

### `Dispatcher`

`Dispatcher` rappresenta la figura operativa che coordina viaggi, autisti, mezzi e attività logistiche.

A differenza di driver, meccanico e magazziniere, il dispatcher non è modellato tramite qualificazioni tecniche. Il suo elemento principale è lo scope operativo.

Contiene:

- identificativo dispatcher;
- codice operativo interno;
- riferimento a `UserId`;
- profilo operativo;
- scope operativi;
- stato;
- metadati;
- note.

Gli scope possono rappresentare aree geografiche, clienti, reparti, trasporti nazionali, internazionali o ADR.

### `DispatcherId`

`DispatcherId` identifica il dispatcher nel dominio operativo.

---

## 4.6 Package `manager`

### `Manager`

`Manager` rappresenta una figura di supervisione operativa o gestionale.

Come il dispatcher, non è basato principalmente su qualificazioni tecniche ma su scope di responsabilità.

Contiene:

- identificativo manager;
- codice operativo interno;
- riferimento a `UserId`;
- profilo operativo;
- scope gestionali o operativi;
- stato;
- metadati;
- note.

Gli scope possono rappresentare fleet operations, magazzino, manutenzione, performance trasporti o aree regionali.

### `ManagerId`

`ManagerId` identifica il manager nel dominio operativo.

---

# 5. Relazione tra `User` e `Operational`

La relazione tra `domain.users` e `domain.operational` è uno dei punti più importanti del progetto.

`User` rappresenta l’account applicativo. Le figure operative rappresentano la persona nel contesto aziendale reale.

Per questo motivo, una figura operativa contiene solo `UserId`, non tutto l’oggetto `User`.

Questa scelta significa che:

- un profilo operativo può essere collegato a un account;
- il dominio operativo non dipende dalla struttura interna di `User`;
- login, permessi e ruoli applicativi restano separati dal business operativo;
- una persona può avere più profili operativi;
- il sistema può evolvere senza accoppiamenti inutili.

Esempio concettuale:

Un utente con ruolo applicativo `DRIVER_USER` può essere collegato a un `Driver`. Un utente con ruolo `DISPATCHER` può essere collegato a un `Dispatcher`. Un manager può avere un account `User` e un profilo operativo `Manager`.

Questa è una scelta enterprise perché separa identità applicativa e responsabilità aziendale.

---

# 6. Invarianti del dominio

Le invarianti sono regole che devono essere sempre rispettate dal dominio.

## 6.1 Invarianti comuni

Nel progetto attuale valgono queste regole generali:

- gli identificativi sono obbligatori;
- i riferimenti a `UserId` sono obbligatori nelle figure operative;
- il profilo è obbligatorio;
- lo stato è obbligatorio;
- i metadati sono obbligatori;
- le note vengono normalizzate;
- gli insiemi non devono contenere valori null;
- i metadata non possono avere `updatedAt` precedente a `createdAt`.

## 6.2 Invarianti di `domain.users`

Per gli utenti:

- username obbligatorio;
- password hash obbligatorio;
- almeno un ruolo obbligatorio;
- non si può rimuovere l’ultimo ruolo;
- un utente attivo deve avere ruoli;
- un utente disabilitato non può essere modificato con i normali metodi;
- un utente disabilitato non può tornare attivo senza metodo esplicito;
- email valida se presente;
- metadata sempre presenti;
- preferences sempre presenti.

## 6.3 Invarianti operative

Per le figure operative:

- un `Driver` attivo deve avere almeno una qualificazione;
- un `Mechanic` attivo deve avere almeno una qualificazione;
- un `WarehouseOperator` attivo deve avere almeno una qualificazione;
- un `Dispatcher` attivo deve avere almeno uno scope;
- un `Manager` attivo deve avere almeno uno scope.

Queste regole non sono compliance avanzata. Sono solo regole minime per evitare stati incoerenti.

---

# 7. Perché abbiamo fatto così

## 7.1 Separazione dei contesti

Il progetto separa utenti, qualificazioni e figure operative perché rappresentano concetti diversi.

Questa separazione permette di ragionare meglio sul dominio e impedisce che una classe diventi troppo grande o troppo generica.

## 7.2 Indipendenza dal sistema di login

Il dominio non implementa login, JWT, sessioni o Spring Security. Questo è corretto perché la sicurezza tecnica appartiene a un livello diverso.

Il dominio dice solo se un account è attivo, sospeso o disabilitato. Non autentica nessuno.

## 7.3 Indipendenza dal database

Nessuna classe del dominio usa annotazioni JPA o concetti di persistenza.

Questo rende il modello libero dal database. In futuro potrà essere salvato su PostgreSQL, MongoDB, file, API o altro senza cambiare il cuore del dominio.

## 7.4 Indipendenza dai microservizi

Il progetto non dipende da microservizi. Può essere usato in un monolite modulare e, in futuro, alcuni package potranno diventare servizi separati.

Questa è una scelta pratica: prima si costruisce un dominio pulito, poi si decide come distribuirlo.

## 7.5 Scalabilità futura

La struttura attuale permette di aggiungere in futuro:

- documenti;
- scadenze;
- veicoli;
- viaggi;
- assegnazioni;
- manutenzione;
- compliance;
- turni;
- disponibilità;
- notifiche;
- audit tecnico;
- API e database.

Senza dover riscrivere i concetti principali già modellati.

## 7.6 Assenza di duplicazioni

Le patenti non sono duplicate nel dominio operativo. Sono qualificazioni del catalogo.

L’account utente non è duplicato dentro Driver, Mechanic o Manager. Viene usato solo `UserId`.

I metadata operativi non usano `UserMetadata`, ma `OperationalMetadata`, perché appartengono a un contesto diverso.

## 7.7 Coerenza con DDD

Il progetto applica concetti DDD in modo semplice:

- entity per oggetti con identità e ciclo di vita;
- value object per dati immutabili e validati;
- enum per insiemi controllati di valori;
- catalogo statico per qualificazioni;
- separazione dei contesti;
- dominio indipendente dalla tecnologia.

Anche senza conoscere DDD, il risultato è un modello ordinato, leggibile e adatto a crescere.

---

# 8. Conclusione

Fino a questo momento TruckFlow Manager ha costruito una base di dominio molto solida.

Il package `domain.users` gestisce gli account applicativi, i ruoli, i permessi, lo stato, il profilo e le preferenze degli utenti.

Il package `domain.qualifications` gestisce il catalogo statico di patenti, CQC, ADR, certificazioni e abilitazioni.

Il package `domain.operational` gestisce le figure operative reali dell’azienda: autisti, meccanici, operatori di magazzino, dispatcher e manager.

La scelta più importante è la separazione:

- un utente non è automaticamente un autista;
- una patente non è un campo scritto dentro l’autista;
- una qualificazione non contiene scadenze;
- un profilo operativo non contiene login;
- il dominio non dipende da database o framework.

Questa struttura porta diversi benefici:

- chiarezza;
- scalabilità;
- meno duplicazioni;
- facilità di manutenzione;
- possibilità di aggiungere nuove funzionalità senza rompere il modello;
- preparazione naturale verso un monolite modulare o microservizi futuri.

La visione futura del progetto è continuare a costruire il dominio una parte alla volta. Dopo utenti, qualificazioni e figure operative, i prossimi passi naturali saranno mezzi, documenti, scadenze, viaggi, assegnazioni, magazzino, manutenzione e compliance.

La base attuale è quindi corretta: pulita, indipendente, enterprise e pronta per crescere.
