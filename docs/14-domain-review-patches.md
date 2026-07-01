# 14 – Domain Review: interventi correttivi

## 1. Overview

Questo documento riepiloga gli interventi correttivi eseguiti durante la prima review concreta del dominio puro di TruckFlow.

L'obiettivo non era aggiungere nuove funzionalità operative, ma rafforzare la qualità architetturale del dominio prima di passare al livello `application`.

Gli interventi hanno seguito le regole definite in [`13-domain-rules.md`](13-domain-rules.md):

- mantenere il dominio puro;
- proteggere gli invarianti;
- usare eccezioni di dominio coerenti;
- evitare mutazioni parziali dello stato;
- mantenere confini chiari tra bounded context;
- ridurre file locali e artefatti generati dal repository.

---

## 2. Obiettivo della review correttiva

La review concreta ha trasformato la Domain Foundation da semplice base modellata a base più robusta e più sicura.

Prima della review il dominio era già coerente, ma presentava alcuni punti migliorabili:

- alcuni aggregate modificavano lo stato prima di completare tutte le validazioni;
- le eccezioni custom erano state introdotte ma non erano ancora usate in modo diffuso;
- `OperationalCode` era opzionale, mentre gli altri codici aziendali erano obbligatori;
- alcuni test del catalogo qualificazioni erano fragili perché basati su conteggi rigidi;
- alcuni file locali dell'ambiente di sviluppo erano ancora presenti o visibili nel repository;
- la targa e il VIN erano ancora rappresentati come primitive `String` nel dominio veicoli.

Gli interventi hanno corretto questi punti senza introdurre application layer, database, API REST o logica infrastrutturale.

---

## 3. Intervento 1 — Validare prima di mutare lo stato

### Problema

Alcuni aggregate modificavano campi interni prima di completare tutte le validazioni.

Questo poteva creare un problema importante: se una validazione falliva dopo una prima assegnazione, l'oggetto poteva rimanere in uno stato parzialmente modificato.

Esempio concettuale del problema:

```text
1. cambio lo stato in CONFIRMED
2. valido gli item
3. la validazione fallisce
4. l'aggregate resta con lo stato già cambiato
```

### Correzione

La regola introdotta è:

```text
prima si validano tutti i nuovi valori
poi si assegna lo stato interno
```

Questa logica è stata applicata ai metodi di modifica degli aggregate più importanti, in particolare nei domini:

- `domain.users`;
- `domain.operational`;
- `domain.vehicles`;
- `domain.cargo`;
- `domain.triptemplates`;
- `domain.shipments`;
- `domain.documents`;
- `domain.compliance`.

### Perché è importante

Questa correzione rende le modifiche di stato più sicure.

Un aggregate non deve mai restare in uno stato incoerente se un'operazione fallisce. La validazione preventiva protegge l'integrità del dominio e rende più affidabili i futuri use case applicativi.

---

## 4. Intervento 2 — Eccezioni custom nei domini semplici

### Problema

Le eccezioni custom erano state definite, ma alcuni domini continuavano a usare eccezioni standard Java come `IllegalArgumentException`.

Nei domini più semplici questo rendeva meno chiaro quale bounded context avesse prodotto l'errore.

### Correzione

Sono state introdotte eccezioni specifiche nei domini più piccoli e isolati:

- `InvalidDocumentException` per `domain.documents`;
- `InvalidComplianceRequirementException` per `domain.compliance`;
- `InvalidLocationException` per `domain.locations`;
- `InvalidTripTemplateException` e `InvalidTripTemplateSegmentException` per `domain.triptemplates`.

Sono stati aggiornati anche i test collegati, in modo che verifichino le eccezioni di dominio corrette.

### Perché è importante

Le eccezioni custom rendono il dominio più leggibile e più professionale.

Un errore di validazione di un documento non è più un generico `IllegalArgumentException`, ma un errore specifico del dominio documents. Questo migliora chiarezza, testabilità e futura gestione degli errori nel livello application.

---

## 5. Intervento 3 — Eccezioni custom nei domini complessi

### Problema

Dopo l'intervento sui domini semplici, restavano ancora eccezioni standard nei domini più grandi:

- `domain.cargo`;
- `domain.shipments`;
- `domain.vehicles`;
- `domain.operational`;
- `domain.users`.

Questi domini contengono aggregate più ricchi e regole più numerose, quindi richiedevano una sostituzione più attenta.

### Correzione

Sono state applicate le eccezioni custom anche ai domini complessi:

- `InvalidCargoException`;
- `InvalidShipmentException`;
- `InvalidShipmentItemException`;
- `InvalidShipmentLegException`;
- `InvalidVehicleException`;
- `InvalidVehicleCombinationException`;
- `InvalidDriverException`;
- `InvalidMechanicException`;
- `InvalidWarehouseOperatorException`;
- `InvalidDispatcherException`;
- `InvalidManagerException`;
- `InvalidUserException`.

Anche i test sono stati adeguati per riflettere il nuovo comportamento.

### Perché è importante

Questa correzione completa il passaggio principale dalle eccezioni generiche alle eccezioni di dominio.

Il dominio diventa più esplicito: ogni violazione viene espressa usando il vocabolario del bounded context corretto.

Questo prepara meglio il progetto a future funzionalità come use case applicativi, mapping errori API, gestione centralizzata degli errori e reportistica di validazione.

---

## 6. Intervento 4 — Rendere `OperationalCode` obbligatorio

### Problema

`OperationalCode` era opzionale, mentre altri codici aziendali del progetto erano obbligatori.

Esempi di codici obbligatori già presenti:

- `FleetCode`;
- `CargoCode`;
- `ShipmentCode`;
- `DocumentCode`;
- `ComplianceRequirementCode`.

Questa differenza creava una piccola incoerenza nel modello.

### Correzione

`OperationalCode` è stato reso obbligatorio per le figure operative:

- `Driver`;
- `Mechanic`;
- `WarehouseOperator`;
- `Dispatcher`;
- `Manager`.

Il codice operativo non può più essere `null`, vuoto o composto solo da spazi.

### Perché è importante

In un sistema enterprise, le figure operative devono avere un codice aziendale leggibile e stabile.

Codici come `DRV-001`, `MEC-001`, `WH-001`, `DSP-001` o `MNG-001` sono utili per:

- ricerca;
- documentazione;
- comunicazione interna;
- integrazione futura;
- audit;
- reportistica;
- collegamento con sistemi HR o gestionali.

Questa scelta rende `domain.operational` più coerente con gli altri domini.

---

## 7. Intervento 5 — Rendere meno fragili i test del catalogo qualificazioni

### Problema

Alcuni test del catalogo qualificazioni controllavano numeri fissi, per esempio il totale degli elementi o il numero di qualificazioni per categoria.

Questo rendeva i test fragili: aggiungere una nuova qualificazione corretta avrebbe potuto far fallire il test solo perché cambiava un conteggio.

### Correzione

I test sono stati orientati verso controlli di comportamento e coerenza:

- codici univoci;
- presenza delle qualificazioni fondamentali;
- ricerca per codice;
- coerenza tra qualificazione e categoria;
- metadati minimi presenti;
- copertura delle categorie principali.

### Perché è importante

Un catalogo statico è destinato a crescere.

I test devono proteggere la qualità del catalogo, non bloccarne inutilmente l'evoluzione.

Questa correzione rende il catalogo qualificazioni più facile da mantenere nel tempo.

---

## 8. Intervento 6 — Pulizia file locali, IDE e artefatti generati

### Problema

Nel repository potevano comparire file non appartenenti al codice sorgente o alla documentazione ufficiale, come:

- configurazioni locali dell'IDE;
- artefatti generati da Maven;
- file temporanei;
- file generati da macOS;
- script o patch temporanee.

Questi file non rappresentano il dominio e possono sporcare la history o creare differenze inutili tra ambienti di sviluppo.

### Correzione

La documentazione e la configurazione del repository sono state allineate alla regola:

```text
il repository deve contenere codice sorgente, test, configurazioni condivise e documentazione ufficiale
```

Devono restare fuori dal versionamento:

- `.idea/`;
- `target/`;
- `.DS_Store`;
- `__MACOSX/`;
- file `.patch`;
- script locali `.sh`.

### Perché è importante

Il repository deve essere pulito e riproducibile.

Tenere fuori file locali e artefatti generati evita commit inutili, conflitti non necessari e confusione tra ciò che è codice ufficiale e ciò che appartiene solo all'ambiente locale.

---

## 9. Intervento 7 — Trasformare targa e VIN in Value Object

### Problema

Nel dominio `domain.vehicles`, la targa era rappresentata come una semplice `String`.

Questo è un caso di primitive obsession: un concetto importante del dominio viene modellato con un tipo troppo generico.

La targa non è testo libero. È un identificatore ufficiale dell'unità fisica, usato in documenti, assicurazioni, revisioni, controlli, audit, compliance, spedizioni, ricerca veicoli e integrazioni future.

Lo stesso ragionamento vale per VIN, telaio o identificativo tecnico del mezzo.

### Correzione

Sono stati introdotti due value object dedicati:

- `LicensePlate`;
- `VehicleIdentificationNumber`.

`VehicleUnit` ora usa questi value object invece di semplici stringhe.

È stata inoltre formalizzata la regola:

```text
Ogni VehicleUnit stradale deve avere una LicensePlate.
Ogni VehicleUnit non stradale può non averla.
VehicleCombination non possiede una LicensePlate.
```

Quindi rimorchi e semirimorchi hanno una propria targa, distinta da quella del trattore o della motrice.

La `VehicleCombination` non ha una targa propria perché non è una unità fisica targabile, ma una struttura logica composta da `VehicleUnit`.

### Perché è importante

Questa correzione rende il dominio veicoli più robusto e più esplicito.

Il tipo `LicensePlate` comunica chiaramente il significato del dato e centralizza normalizzazione, validazione e invarianti.

Il tipo `VehicleIdentificationNumber` fa lo stesso per l'identificativo tecnico del mezzo.

Il modello diventa più coerente con gli altri value object già presenti nel progetto, come `FleetCode`, `CargoCode`, `ShipmentCode`, `LocationCode`, `DocumentCode` e `ComplianceRequirementCode`.

---

## 10. Intervento 8 — Rafforzare giurisdizione, scope operativi e preferenze utente

### Problema

Durante la pulizia finale del dominio puro erano rimasti alcuni concetti modellati come stringhe libere.

I casi principali erano:

- `UserPreferences.language`;
- `UserPreferences.theme`;
- `ComplianceJurisdiction.country`;
- `ComplianceJurisdiction.region`;
- `ComplianceJurisdiction.scope`;
- `OperationalScope.code`.

Questi campi non sono semplici testi descrittivi. Rappresentano concetti con semantica, normalizzazione e regole proprie.

### Correzione

Sono stati introdotti concetti più espliciti:

- `LanguageCode`;
- `UserTheme`;
- `CountryCode`;
- `JurisdictionRegion`;
- `ComplianceJurisdictionScope`;
- `OperationalScopeCode`.

La giurisdizione di compliance distingue ora tra:

```text
scope   = livello della giurisdizione
region  = area geografica o normativa ampia
country = paese specifico
```

La logica futura del tipo "Europa -> lista nazioni -> Italia come default" non è stata inserita nel dominio puro. Appartiene ad application layer, configurazione o interfaccia utente.

### Perché è importante

Questa correzione riduce primitive obsession senza introdurre overengineering.

Il dominio diventa più esplicito: lingua, tema, paese, regione, scope di giurisdizione e codice dello scope operativo non sono più stringhe generiche.

Allo stesso tempo, il modello rimane flessibile: non vengono creati `UserCode`, `VehicleCode` o `QualificationCode` solo per simmetria e non viene introdotta logica UI dentro il dominio.

---

## 11. Stato finale dopo gli otto interventi

Dopo questi interventi, il dominio puro di TruckFlow risulta più solido perché:

- le modifiche di stato sono più sicure;
- le eccezioni sono più coerenti con il linguaggio di dominio;
- i codici aziendali sono più uniformi;
- i test del catalogo qualificazioni sono meno fragili;
- il repository è più pulito;
- targa e VIN sono modellati come value object nel dominio veicoli;
- giurisdizione, scope operativi e preferenze utente usano concetti più espliciti;
- la documentazione architetturale è più allineata al codice reale.

Questa fase non introduce ancora:

- application layer;
- repository port;
- repository infrastructure;
- API REST;
- database;
- workflow;
- audit;
- verifiche concrete di compliance;
- pianificazione o dispatching.

Il risultato corretto è:

```text
TruckFlow Domain Foundation v1.0 rafforzata dalla prima review correttiva del dominio puro.
```

---

## 12. Revisione finale della test suite

Dopo gli otto interventi correttivi è stata aggiunta una revisione specifica della test suite del dominio puro, documentata in [`15-domain-test-suite-review.md`](15-domain-test-suite-review.md).

Questa revisione non introduce nuovi concetti operativi nel dominio. Rafforza invece i test esistenti con:

- test architetturali sui confini tra bounded context;
- controlli contro dipendenze vietate nel domain layer;
- controlli contro eccezioni standard nei punti di validazione;
- test contrattuali sui value object principali;
- casi limite aggiuntivi per cargo e shipment;
- spiegazione esplicita di ciò che non viene ancora testato perché appartiene a moduli futuri.

---

## 13. Prossimo passo consigliato

Il prossimo passo naturale è preparare il livello `application`.

Prima di introdurre API REST o database, conviene definire:

- use case applicativi principali;
- porte in ingresso;
- porte repository in uscita;
- repository in-memory per test e scenari;
- primi flussi applicativi semplici.

La direzione consigliata è:

```text
domain -> application -> infrastructure -> api
```

Il dominio deve rimanere stabile e protetto: i nuovi livelli dovranno consumare il dominio, non sporcarlo.

## Passaggio successivo documentato — Punto 6A

Dopo la review correttiva del dominio puro e la revisione finale della test suite, il progetto introduce `docs/16-application-layer-blueprint.md`.

Questo non è un nuovo refactoring del dominio. È il documento di handoff verso il livello applicativo. Definisce come TruckFlow Manager dovrà organizzare use case, command, result, port in, port out, repository in memory, eccezioni applicative e test applicativi.

La scelta protegge il lavoro fatto nel dominio: i futuri controller, database e moduli infrastrutturali dovranno passare dall'application layer e non manipolare direttamente gli aggregate in modo disordinato.

## Step successivo — Punto 6B Application Foundation

Dopo gli interventi correttivi sul dominio e il blueprint del Punto 6A, il progetto ha iniziato il Punto 6B.

Questo step non è una correzione del dominio, ma il primo passo applicativo: crea la foundation dell'application layer con contratti base, package dedicati, eccezioni applicative e test architetturali.

La review del dominio resta valida: il dominio rimane puro e non dipende dal nuovo layer applicativo.


## Punto 6C — Application Repository Ports

Dopo la foundation applicativa del Punto 6B, il progetto ha avviato il Punto 6C introducendo le prime porte repository specifiche.

Sono stati aggiunti:

- `RepositoryPort`;
- `LocationRepository`;
- `CargoUnitRepository`;
- `ShipmentRepository`;
- test contrattuali sulle porte repository;
- documentazione dedicata in `docs/18-application-repository-ports.md`.

Questa fase non introduce database, repository in memory ufficiali, use case operativi o REST API. Serve a stabilire i contratti che i prossimi use case useranno per salvare e recuperare aggregate.
