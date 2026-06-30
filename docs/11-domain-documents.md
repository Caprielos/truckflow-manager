# 11. Dominio `domain.documents`

## Indice

1. [Obiettivo del dominio](#111-obiettivo-del-dominio)
2. [Principio fondamentale](#112-principio-fondamentale)
3. [Struttura del package](#113-struttura-del-package)
4. [Aggregate root `Document`](#114-aggregate-root-document)
5. [Identità e codice](#115-identità-e-codice)
6. [Tipo, categoria e stato](#116-tipo-categoria-e-stato)
7. [Metadati e contenuto logico](#117-metadati-e-contenuto-logico)
8. [Riferimenti astratti](#118-riferimenti-astratti)
9. [Validazioni e invarianti](#119-validazioni-e-invarianti)
10. [Cosa non appartiene al dominio documents](#1110-cosa-non-appartiene-al-dominio-documents)
11. [Relazione con gli altri domini](#1111-relazione-con-gli-altri-domini)
12. [Evoluzione futura](#1112-evoluzione-futura)
13. [Sintesi finale](#1113-sintesi-finale)

---

## 11.1 Obiettivo del dominio

Il package `domain.documents` rappresenta il concetto generico di documento aziendale dentro TruckFlow Manager.

Un documento può essere collegato a veicoli, persone operative, cargo, shipment, location, trip template, sicurezza, compliance, aree legali o amministrative, ma il dominio documents non appartiene a nessuno di questi contesti specifici.

Il suo obiettivo è descrivere:

- che documento è;
- come è identificato;
- come è classificato;
- qual è il suo stato astratto;
- quali metadati descrittivi possiede;
- qual è il suo contenuto logico;
- a quali concetti aziendali può essere riferito in modo astratto.

Il dominio documents non gestisce file fisici, storage, workflow, upload, firme digitali, scadenze o compliance operativa.

---

## 11.2 Principio fondamentale

La regola principale è:

```text
Documento = concetto aziendale astratto
File = dettaglio tecnico futuro
```

Quindi il documento non è:

- un PDF;
- un file caricato;
- un path del filesystem;
- una chiave S3;
- un URL;
- un allegato fisico;
- un workflow di approvazione;
- una scadenza;
- una firma digitale.

Il documento è un concetto puro del dominio.

Questo permette di riusare `domain.documents` in molti contesti senza accoppiarlo a infrastruttura, storage o processi operativi.

---

## 11.3 Struttura del package

La struttura scelta è piatta perché il dominio è ancora compatto e leggibile:

```text
it.gabriele.truckflow.domain.documents
├── Document.java
├── DocumentId.java
├── DocumentCode.java
├── DocumentType.java
├── DocumentCategory.java
├── DocumentStatus.java
├── DocumentMetadata.java
├── DocumentContent.java
├── DocumentReference.java
├── DocumentReferenceType.java
└── DocumentValidation.java
```

Questa struttura è coerente con gli altri domini puri già presenti nel progetto e mantiene `Document` come aggregate root centrale.

---

## 11.4 Aggregate root `Document`

`Document` è l'aggregate root del dominio documents.

Rappresenta un documento aziendale astratto composto da:

- `DocumentId`, identità tecnica;
- `DocumentCode`, codice aziendale leggibile;
- `DocumentType`, tipo documento;
- `DocumentCategory`, macro-categoria;
- `DocumentStatus`, stato astratto;
- `DocumentMetadata`, metadati descrittivi;
- `DocumentContent`, contenuto logico opzionale;
- `Set<DocumentReference>`, riferimenti astratti verso altri domini;
- `notes`, note generali normalizzate.

`Document` non contiene logica di upload, download, firma, verifica legale o scadenza.

Queste responsabilità verranno introdotte più avanti in moduli separati.

---

## 11.5 Identità e codice

### `DocumentId`

`DocumentId` è l'identificatore tecnico del documento.

Serve al sistema e non agli utenti finali.

È coerente con gli altri ID tecnici del progetto, come:

- `CargoId`;
- `VehicleUnitId`;
- `ShipmentId`;
- `LocationId`;
- `TripTemplateId`.

### `DocumentCode`

`DocumentCode` è il codice aziendale leggibile del documento.

Esempi:

```text
ADR-2024-VEH-001
CMR-SHP-001
DDT-CARGO-055
INS-VEH-044
```

La differenza è:

```text
DocumentId   = identificatore tecnico interno
DocumentCode = codice aziendale leggibile
```

Questa scelta è coerente con `CargoCode`, `FleetCode`, `OperationalCode`, `LocationCode`, `TripTemplateCode` e `ShipmentCode`.

---

## 11.6 Tipo, categoria e stato

### `DocumentType`

`DocumentType` rappresenta il tipo del documento.

La versione attuale usa un enum statico con i tipi principali:

```text
CMR
DDT
INVOICE
CERTIFICATE
LICENSE
INSURANCE
REGISTRATION
AUTHORIZATION
CONTRACT
SAFETY_REPORT
COMPLIANCE_DECLARATION
GENERIC
```

Questa scelta è adatta alla fase attuale del progetto.

In futuro, quando l'applicazione diventerà configurabile, `DocumentType` potrà evolvere in un catalogo gestito da application/infrastructure.

### `DocumentCategory`

`DocumentCategory` rappresenta la macro-categoria del documento.

```text
GENERIC
VEHICLE
OPERATIONAL
CARGO
SHIPMENT
LOCATION
TRIP_TEMPLATE
COMPLIANCE
SAFETY
FINANCIAL
LEGAL
```

La categoria non collega il documento a un aggregato specifico. Serve solo a classificare il documento.

Il collegamento astratto avviene tramite `DocumentReference`.

### `DocumentStatus`

`DocumentStatus` è uno stato astratto, non un workflow.

```text
DRAFT
ACTIVE
SUSPENDED
ARCHIVED
```

Non sono stati del dominio puro:

```text
SIGNED
EXPIRED
VALIDATED
REJECTED
PENDING_APPROVAL
```

Questi stati appartengono a firma digitale, compliance, scadenze o workflow futuri.

---

## 11.7 Metadati e contenuto logico

### `DocumentMetadata`

`DocumentMetadata` contiene informazioni descrittive del documento:

- titolo;
- autore;
- descrizione;
- versione;
- tag.

Il titolo è obbligatorio perché un documento deve sempre essere riconoscibile.

I tag sono normalizzati per facilitare ricerca e classificazione future.

### `DocumentContent`

`DocumentContent` rappresenta il contenuto logico del documento.

È opzionale e non fisico.

Può rappresentare:

- corpo testuale;
- riassunto;
- note formali;
- contenuto dichiarativo.

Non rappresenta:

- file PDF;
- immagini;
- `byte[]`;
- path;
- `mimeType` tecnico;
- URL;
- chiavi di storage.

Questa scelta permette di registrare un documento anche quando il file fisico verrà gestito più avanti da un modulo dedicato.

---

## 11.8 Riferimenti astratti

`DocumentReference` è il collegamento astratto tra un documento e un altro concetto aziendale.

La struttura è volutamente generica:

```text
DocumentReference
├─ DocumentReferenceType referenceType
├─ String referencedId
└─ String notes
```

`domain.documents` non importa classi come:

- `VehicleUnitId`;
- `CargoId`;
- `ShipmentId`;
- `LocationId`;
- `TripTemplateId`;
- `DriverId`.

Questo mantiene il bounded context completamente disaccoppiato.

Esempi:

```text
referenceType = SHIPMENT
referencedId = SHP-001
```

```text
referenceType = VEHICLE
referencedId = VEH-044
```

```text
referenceType = CARGO
referencedId = CARGO-055
```

`DocumentReferenceType` contiene i principali contesti verso cui un documento può puntare:

```text
VEHICLE
OPERATIONAL
CARGO
SHIPMENT
LOCATION
TRIP_TEMPLATE
COMPLIANCE
SAFETY
FINANCIAL
LEGAL
GENERIC
OTHER
```

---

## 11.9 Validazioni e invarianti

Il dominio protegge le sue regole minime tramite `DocumentValidation` e tramite i costruttori dei value object.

Le invarianti principali sono:

- `DocumentId` obbligatorio o generato automaticamente;
- `DocumentCode` obbligatorio e normalizzato in uppercase;
- `DocumentType` obbligatorio;
- `DocumentCategory` obbligatoria;
- `DocumentStatus` obbligatorio;
- `DocumentMetadata` obbligatorio;
- titolo del metadata obbligatorio;
- `DocumentContent` opzionale e normalizzato;
- riferimenti opzionali, ma senza elementi null;
- `DocumentReferenceType` obbligatorio per ogni riferimento;
- `referencedId` obbligatorio per ogni riferimento;
- note normalizzate.

Queste regole mantengono il dominio coerente senza introdurre workflow o logica tecnica.

---

## 11.10 Cosa non appartiene al dominio documents

Il dominio documents non contiene:

- PDF;
- upload;
- download;
- storage S3;
- path filesystem;
- URL;
- `mimeType` tecnico;
- `byte[]`;
- firma digitale;
- verifica firma;
- workflow approvativo;
- scadenze;
- compliance operativa;
- repository implementati;
- controller REST;
- DTO;
- JPA;
- Spring;
- servizi applicativi.

Questi aspetti verranno modellati più avanti in layer o moduli separati.

---

## 11.11 Relazione con gli altri domini

Il dominio documents può riferirsi ad altri domini solo tramite riferimenti astratti.

Esempio concettuale:

```text
Document
├─ category: SHIPMENT
└─ reference:
   ├─ referenceType: SHIPMENT
   └─ referencedId: SHP-001
```

Il documento non ingloba `Shipment`, `VehicleUnit`, `CargoUnit` o altri aggregate root.

La relazione è volutamente debole e generica.

Questo è coerente con la regola già usata in TruckFlow:

```text
Quando un aggregate deve riferirsi a un altro aggregate, usa un riferimento leggero.
```

Nel caso documents, il riferimento è ancora più generico perché il dominio deve rimanere riusabile da tutti i contesti.

---

## 11.12 Evoluzione futura

In futuro il dominio documents potrà essere collegato a moduli specifici, per esempio:

- application use case per creare, aggiornare e ricercare documenti;
- repository port nel layer application;
- repository in memoria o database nel layer infrastructure;
- file storage per PDF e allegati fisici;
- versioning documentale;
- scadenze documentali;
- firma digitale;
- workflow approvativo;
- compliance documentale;
- audit trail.

Queste funzionalità non vanno però introdotte dentro il dominio puro attuale.

---

## 11.13 Sintesi finale

`domain.documents` rappresenta il documento aziendale come concetto astratto, puro e riusabile.

Il dominio descrive:

- identità;
- codice;
- tipo;
- categoria;
- stato;
- metadati;
- contenuto logico;
- riferimenti astratti;
- validazioni minime.

Non descrive file fisici, upload, storage, firma, workflow, scadenze o compliance operativa.

Questa scelta rende il dominio documents coerente con il resto di TruckFlow e pronto a diventare una base stabile per i futuri moduli documentali enterprise.
