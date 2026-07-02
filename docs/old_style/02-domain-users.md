# Archivio storico — 02-domain-users

> Questo documento fa parte dell'archivio storico `docs/old_style/`.
> Mantiene il percorso step-by-step del progetto, ma la documentazione principale aggiornata si trova in:
> `docs/simple/`, `docs/professional/` e `docs/digital/`.

---

# 2. Package `domain.users`

## 2.1 Scopo del package

Il package `domain.users` rappresenta gli **account applicativi** di TruckFlow Manager.

Un `User` è una persona che può accedere al sistema come utente dell’applicazione. Non rappresenta automaticamente un autista, un meccanico, un magazziniere o un manager operativo.

Questa distinzione è fondamentale:

- `User` = account per accedere all’app;
- `Driver`, `Mechanic`, `WarehouseOperator`, `Dispatcher`, `Manager` = figure operative reali dell’azienda.

Un utente può avere un ruolo applicativo, per esempio `DRIVER_USER` o `DISPATCHER`, ma il profilo operativo vero sta nel package `domain.operational`.

## 2.2 Perché User è separato dal dominio operativo

Se mettessimo dentro `User` dati come patenti, CQC, ADR, abilitazioni operative o scope gestionali, il modello diventerebbe confuso.

Un account applicativo deve rispondere a domande come:

- può accedere all’app?
- quale username usa?
- quale hash password possiede?
- quali ruoli applicativi ha?
- quali permessi ha?
- è attivo, sospeso o disabilitato?

Una figura operativa deve invece rispondere a domande diverse:

- è un autista?
- possiede qualificazioni operative?
- ha un codice interno aziendale?
- opera in un certo reparto?
- ha scope di coordinamento?

Per questo `User` e dominio operativo sono separati.

## 2.3 Classi principali

### `User`

`User` è l’entità principale del package.

Contiene:

- `UserId`;
- `Username`;
- `UserPasswordHash`;
- insieme di `UserRole`;
- `UserPermissions`;
- `UserStatus`;
- `UserProfile`;
- `UserMetadata`;
- `UserPreferences`;
- note.

La classe gestisce regole come:

- un utente attivo deve avere almeno un ruolo;
- un utente disabilitato non può essere modificato con le normali operazioni;
- i ruoli e i permessi non possono contenere valori nulli;
- le note vengono normalizzate;
- ogni modifica aggiorna i metadata.

`User` non contiene dati operativi come patenti, CQC, ADR o aree di lavoro.

### `UserId`

`UserId` è l’identificatore del dominio per un utente.

È separato da username ed email perché l’identità tecnica del dominio non deve cambiare quando cambiano i dati visibili dell’utente.

Viene usato anche da altri package come riferimento, per esempio nel dominio operativo.

La scelta importante è questa: gli altri domini referenziano l’utente tramite `UserId`, non tramite un oggetto `User` completo.

### `Username`

`Username` rappresenta il nome utente usato per l’accesso.

È un value object dedicato perché lo username non è una semplice stringa generica: è un concetto specifico del dominio utenti.

La sua presenza come classe separata permette di centralizzare normalizzazione e validazione.

### `UserPasswordHash`

`UserPasswordHash` rappresenta l’hash della password, non la password in chiaro.

La scelta è corretta perché il dominio non deve mai gestire password testuali.

Il dominio conserva solo il valore già trasformato in hash. La generazione dell’hash e la verifica tecnica appartengono a layer di sicurezza/applicazione, non al dominio puro.

### `UserRole`

`UserRole` rappresenta i ruoli applicativi.

I ruoli attuali sono:

- `ADMIN`;
- `DISPATCHER`;
- `WAREHOUSE_OPERATOR`;
- `DRIVER_USER`;
- `MECHANIC`;
- `MANAGER`.

Questi ruoli indicano cosa un account può fare o vedere nell’applicazione.

Non vanno confusi con le figure operative reali. Per esempio:

- `UserRole.DISPATCHER` = ruolo applicativo;
- `Dispatcher` = figura operativa nel package `domain.operational.dispatcher`.

### `UserPermission`

`UserPermission` rappresenta permessi applicativi più granulari.

I permessi sono organizzati per aree:

- utenti;
- autisti;
- veicoli;
- qualificazioni;
- viaggi;
- magazzino;
- manutenzione;
- report.

Esempi:

- `VIEW_USERS`;
- `MANAGE_USERS`;
- `VIEW_DRIVERS`;
- `MANAGE_DRIVERS`;
- `VIEW_VEHICLES`;
- `MANAGE_VEHICLES`.

Questa distinzione tra ruoli e permessi rende il modello più enterprise: un ruolo può rappresentare una macro-funzione, mentre i permessi permettono controlli più specifici.

### `UserPermissions`

`UserPermissions` incapsula l’insieme di permessi di un utente.

È stato creato come value object perché un set di permessi ha regole proprie:

- non deve contenere valori nulli;
- deve essere copiato in modo sicuro;
- deve offrire metodi per verificare se un permesso è presente;
- deve permettere aggiunta e rimozione restituendo un nuovo stato coerente.

Non viene usato un semplice `Set<UserPermission>` ovunque, perché questo disperderebbe la logica e renderebbe più facile creare stati non validi.

### `UserStatus`

`UserStatus` rappresenta lo stato dell’account.

I valori sono:

- `ACTIVE`;
- `SUSPENDED`;
- `DISABLED`.

Significato:

- `ACTIVE`: l’utente può accedere;
- `SUSPENDED`: l’utente è sospeso temporaneamente;
- `DISABLED`: l’utente è disabilitato e non deve essere modificato con le normali operazioni.

La differenza tra sospeso e disabilitato è importante. Un utente sospeso può essere riattivato con logiche normali. Un utente disabilitato rappresenta uno stato più forte.

### `UserProfile`

`UserProfile` rappresenta i dati di profilo dell’account applicativo.

Non è il profilo operativo aziendale. Quello è `OperationalProfile`.

`UserProfile` raccoglie dati anagrafici e collegamenti a contatto e indirizzo dell’utente applicativo.

### `UserContact`

`UserContact` rappresenta i dati di contatto associati al profilo utente.

Serve a evitare che email, telefono o altri contatti siano sparsi direttamente nella classe `User`.

### `UserAddress`

`UserAddress` rappresenta l’indirizzo dell’utente.

È separato per mantenere il profilo ordinato e per permettere eventuali evoluzioni future senza modificare direttamente `User`.

### `UserMetadata`

`UserMetadata` traccia informazioni di creazione e aggiornamento dell’account.

Contiene:

- data di creazione;
- data di aggiornamento;
- autore della creazione;
- autore dell’aggiornamento.

Non è un audit trail completo. È solo metadata base di dominio.

Per le figure operative è stato creato `OperationalMetadata`, perché `UserMetadata` appartiene al contesto utenti.

### `UserPreferences`

`UserPreferences` rappresenta preferenze dell’account applicativo.

Tiene separate le impostazioni dell’utente dal resto del dominio.

Le preferenze non usano più stringhe libere per lingua e tema. Usano value object e enum dedicati:

- `LanguageCode`, per rappresentare un codice lingua normalizzato;
- `UserTheme`, per rappresentare il tema grafico scelto dall'utente.

Questa scelta evita valori casuali come `italiano`, `english`, `nero` o `dark-mode` e rende il modello più chiaro.

I valori principali di `UserTheme` sono:

- `LIGHT`;
- `DARK`;
- `SYSTEM`.

`LanguageCode` è pensato come codice breve normalizzato, per esempio `EN`, `IT`, `FR`, `DE` o `ES`.

## 2.4 Perché usare value object dedicati

Il progetto usa value object dedicati perché molte informazioni non sono semplici stringhe o set generici.

Esempi:

- `Username` non è una stringa qualsiasi;
- `UserPasswordHash` non è una password qualsiasi;
- `UserId` non è un UUID qualunque;
- `UserPermissions` non è un set senza regole;
- `LanguageCode` non è una lingua scritta come testo libero;
- `UserTheme` non è una stringa arbitraria.

Questa scelta rende il dominio più chiaro, leggibile e sicuro.

## 2.5 Perché User non contiene patenti o abilitazioni

Patenti, CQC, ADR, HACCP, ATP e altre abilitazioni appartengono al mondo operativo.

Un account può essere collegato a un autista, ma non è l’autista.

Per questo le abilitazioni non stanno in `User`. Stanno nel dominio operativo tramite `OperationalQualification`, che a sua volta referenzia il catalogo `Qualification`.

## 2.6 Perché UserId è un riferimento e non un User

Le entità operative contengono `UserId`, non `User`.

Questa scelta riduce l’accoppiamento tra contesti.

Il dominio operativo non deve modificare direttamente l’account applicativo. Deve solo sapere a quale account è collegata una figura operativa.

Questo approccio è più scalabile e coerente con DDD.
