# Archivio storico — 03-domain-qualifications

> Questo documento fa parte dell'archivio storico `docs/old_style/`.
> Mantiene il percorso step-by-step del progetto, ma la documentazione principale aggiornata si trova in:
> `docs/simple/`, `docs/professional/` e `docs/digital/`.

---

# 3. Package `domain.qualifications`

## 3.1 Scopo del package

Il package `domain.qualifications` contiene il catalogo delle qualificazioni e abilitazioni utilizzabili nel sistema.

Una `Qualification` rappresenta un’abilitazione possibile, non il fatto che una persona la possieda.

Esempio:

- `DRIVING_LICENSE_C` esiste nel catalogo;
- Mario Rossi possiede `DRIVING_LICENSE_C` nel dominio operativo tramite una `OperationalQualification`.

Questa distinzione evita di mescolare catalogo statico e dati personali/operativi.

## 3.2 Perché il catalogo è separato dal dominio operativo

Il catalogo risponde alla domanda:

> Quali qualificazioni esistono nel sistema?

Il dominio operativo risponde invece alla domanda:

> Quali qualificazioni possiede questa figura operativa?

Per questo il catalogo è separato.

Se una patente o una certificazione fosse modellata direttamente dentro `Driver`, il sistema diventerebbe rigido. Con un catalogo unico, la stessa qualificazione può essere usata da autisti, meccanici, operatori di magazzino o altre figure future.

## 3.3 Classi principali

### `Qualification`

`Qualification` è l’enum principale del catalogo.

Ogni qualificazione contiene:

- codice;
- nome leggibile;
- categoria;
- descrizione breve;
- descrizione lunga.

Il catalogo comprende diverse famiglie di abilitazioni:

- patenti di guida;
- CQC;
- ADR;
- alimentare, farmaceutico e ATP;
- animali vivi;
- rifiuti;
- operatori macchine;
- sicurezza;
- porti e aeroporti;
- logistica aziendale.

Esempi di qualificazioni:

- `DRIVING_LICENSE_C`;
- `DRIVING_LICENSE_CE`;
- `CQC_GOODS`;
- `ADR_BASIC`;
- `ADR_TANK`;
- `ATP`;
- `HACCP`;
- `FORKLIFT`;
- `FIRST_AID`;
- `WAREHOUSE_MANAGEMENT`.

### `QualificationCategory`

`QualificationCategory` raggruppa le qualificazioni per categoria.

Le categorie attuali sono:

- `DRIVING_LICENSES`;
- `CQC`;
- `ADR`;
- `FOOD_PHARMACEUTICALS`;
- `ANIMALS`;
- `WASTE`;
- `MACHINE_OPERATORS`;
- `SAFETY`;
- `PORTS_AND_AIRPORTS`;
- `COMPANY_LOGISTICS`.

Ogni categoria contiene:

- codice;
- nome leggibile;
- descrizione.

Questa struttura permette di filtrare il catalogo e presentarlo in modo ordinato.

### `QualificationCatalog`

`QualificationCatalog` è il punto di accesso al catalogo.

Serve a:

- ottenere tutte le qualificazioni;
- cercare qualificazioni per categoria;
- cercare una qualificazione tramite codice.

È una classe di supporto del dominio, non un repository di database.

I test del catalogo devono verificare il comportamento e la coerenza dei dati, non bloccare numeri totali rigidi.

Esempi di controlli corretti:

- tutti i codici sono univoci;
- tutte le categorie hanno almeno una qualificazione;
- le qualificazioni fondamentali sono presenti;
- la ricerca per codice funziona;
- ogni qualificazione filtrata appartiene davvero alla categoria richiesta.

Esempio da evitare:

- fallire il test solo perché il catalogo passa da 64 a 65 qualificazioni.

## 3.4 Perché le patenti sono qualificazioni

Nel progetto TruckFlow, le patenti sono modellate come `Qualification`.

Questa scelta evita di creare un enum separato come `LicenseType`, che avrebbe duplicato il concetto.

Una patente C, una CQC merci o un ADR base sono tutte abilitazioni che permettono a una persona di svolgere certe attività.

Quindi è più pulito modellarle tutte come qualificazioni, distinguendole tramite categoria.

## 3.5 Perché il catalogo non contiene scadenze

Il catalogo dice solo che una qualificazione esiste.

Non deve contenere:

- data di rilascio;
- data di scadenza;
- documento PDF;
- ente emittente reale;
- validità personale;
- stato della certificazione.

Questi dati appartengono alla qualificazione posseduta dalla persona, non al catalogo.

Per esempio:

- `DRIVING_LICENSE_C` nel catalogo non scade;
- la patente C di Mario Rossi può scadere.

In futuro, le scadenze verranno gestite da un modulo dedicato a documenti, compliance e deadline.

## 3.6 Come viene usato dal dominio operativo

Le figure operative usano `OperationalQualification`.

`OperationalQualification` contiene una `Qualification` del catalogo più dati operativi come:

- numero riferimento;
- paese di rilascio;
- livello;
- note.

Quindi il flusso concettuale è:

1. il catalogo definisce le qualificazioni disponibili;
2. una figura operativa possiede una o più qualificazioni;
3. in futuro, documenti e scadenze potranno essere collegati a quelle qualificazioni possedute.

## 3.7 Cosa non fa questo package

`domain.qualifications` non gestisce:

- persone;
- autisti;
- scadenze;
- documenti;
- file;
- controllo automatico della compliance;
- assegnazione a viaggi;
- compatibilità veicolo/autista.

Questa scelta mantiene il catalogo semplice, stabile e riutilizzabile.
