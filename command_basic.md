# Command Basic - TruckFlow Manager

Documento con i comandi base più usati per lavorare sul progetto TruckFlow Manager.

---

## Comandi Maven principali

### Test completo del progetto

```bash
mvn clean test
```

Questo comando:

- pulisce il progetto
- compila il codice
- controlla il formato Java con Spotless
- esegue i test

Nel progetto TruckFlow Manager, Spotless è collegato alla fase `validate`, quindi `mvn clean test` controlla anche il Google Java Format.

---

### Controllare solo il formato Google Java

```bash
mvn spotless:check
```

Serve per verificare se il codice Java rispetta Google Java Format.

---

### Formattare automaticamente il codice Java

```bash
mvn spotless:apply
```

Serve per formattare automaticamente il codice Java secondo Google Java Format.

Flusso consigliato:

```bash
mvn spotless:apply
mvn clean test
```

---

### Avviare Spring Boot

```bash
mvn spring-boot:run
```

Serve per avviare l'applicazione Spring Boot.

---

### Creare il pacchetto JAR

```bash
mvn clean package
```

Serve per compilare, testare e creare il file `.jar`.

---

### Installare il progetto nel repository Maven locale

```bash
mvn clean install
```

Serve per compilare, testare, creare il pacchetto e installarlo nel repository Maven locale.

---

## Comandi Git principali

### Controllare lo stato del progetto

```bash
git status
```

Mostra i file modificati, aggiunti o eliminati.

---

### Vedere i branch disponibili

```bash
git branch
```

Mostra i branch locali.

---

### Creare un nuovo branch

```bash
git checkout -b nome-branch
```

Esempio:

```bash
git checkout -b new-app-enterprise
```

---

### Spostarsi su un branch esistente

```bash
git checkout nome-branch
```

Esempio:

```bash
git checkout main
```

---

### Aggiungere tutte le modifiche

```bash
git add -A
```

Aggiunge tutti i file modificati, nuovi o eliminati.

---

### Creare un commit

```bash
git commit -m "Descrizione modifica"
```

Esempio:

```bash
git commit -m "Add basic commands document"
```

---

### Mandare il branch su GitHub

```bash
git push
```

Se il branch è nuovo:

```bash
git push -u origin nome-branch
```

Esempio:

```bash
git push -u origin new-app-enterprise
```

---

## Flusso consigliato prima di fare commit

Prima di salvare una modifica su Git, usare sempre:

```bash
mvn spotless:apply
mvn clean test
git status
```

Poi:

```bash
git add -A
git commit -m "Descrizione modifica"
git push
```

---

## Errori comuni

### Comando sbagliato

```bash
mvn cleant est
```

Questo comando è sbagliato.

### Comando corretto

```bash
mvn clean test
```

---

## Riassunto veloce

| Azione | Comando |
|---|---|
| Test completo | `mvn clean test` |
| Controllo formato | `mvn spotless:check` |
| Formattazione automatica | `mvn spotless:apply` |
| Avvio Spring Boot | `mvn spring-boot:run` |
| Creazione JAR | `mvn clean package` |
| Installazione locale | `mvn clean install` |
| Stato Git | `git status` |
| Commit | `git add -A && git commit -m "messaggio"` |
| Push | `git push` |


---

## Nota progetto — Punto 6F

Dopo l'aggiunta del Punto 6F — Application Use Case Review & Hardening, il controllo consigliato resta:

```bash
mvn spotless:apply
mvn clean test
git status
```

Questo step aggiunge `CancelShipmentUseCase`, protegge le mutazioni shipment con approccio copy-on-write e amplia i test applicativi negativi. Quindi `mvn spotless:apply` e `mvn clean test` restano i controlli più importanti da eseguire prima del commit.

---

## Nota progetto — Punto 6G

Dopo l'aggiunta del Punto 6G — Application Use Cases Expansion, il controllo consigliato resta sempre:

```bash
mvn spotless:apply
mvn spotless:check
mvn clean test
git status
```

Il Punto 6G aggiunge i primi use case applicativi Documents, quindi prima del commit conviene controllare anche che siano presenti i nuovi package:

```bash
find src/main/java/it/gabriele/truckflow/application -type d | grep documents
find src/main/java/it/gabriele/truckflow/infrastructure/memory -type d | grep documents
```

Commit consigliato:

```bash
git add -A
git commit -m "Expand application use cases with documents"
```

---

## Nota progetto — Punto 6H

Dopo l'aggiunta del Punto 6H — Application Use Case Expansion Review & Documentation Alignment, il controllo consigliato resta:

```bash
mvn spotless:apply
mvn spotless:check
mvn clean test
git status
```

Il Punto 6H non aggiunge nuovi domini o REST API. Rafforza la coerenza dell'application layer dopo Documents con:

- `ApplicationUseCaseReviewTest`;
- result applicativi null-safe;
- controlli più completi sulle repository in memory;
- verifica copy-on-write dei document use case;
- documentazione Markdown, HTML e CSS aggiornata.

Commit consigliato:

```bash
git add -A
git commit -m "Review application use case expansion"
```

---

## Nota progetto — Punto 6I

Dopo l'aggiunta del Punto 6I — Application Use Cases Expansion II: Vehicles, il controllo consigliato resta:

```bash
mvn spotless:apply
mvn spotless:check
mvn clean test
git status
```

Il Punto 6I aggiunge i primi use case applicativi Vehicles, quindi prima del commit conviene controllare anche che siano presenti i nuovi package:

```bash
find src/main/java/it/gabriele/truckflow/application -type d | grep vehicles
find src/main/java/it/gabriele/truckflow/infrastructure/memory -type d | grep vehicles
```

Commit consigliato:

```bash
git add -A
git commit -m "Expand application use cases with vehicles"
```


---

## Nota progetto — Punto 6J

Dopo l'aggiunta del Punto 6J — Application Use Cases Expansion III: Operational Roles, il controllo consigliato resta:

```bash
mvn spotless:apply
mvn spotless:check
mvn clean test
git status
```

Il Punto 6J aggiunge i primi use case applicativi Operational Roles, quindi prima del commit conviene controllare anche che siano presenti i nuovi package:

```bash
find src/main/java/it/gabriele/truckflow/application -type d | grep operational
find src/main/java/it/gabriele/truckflow/infrastructure/memory -type d | grep operational
```

Commit consigliato:

```bash
git add -A
git commit -m "Expand application use cases with operational roles"
```

---

## Nota progetto — Punto 6K

Dopo l'aggiunta del Punto 6K — Application Operational Use Case Review & Hardening, il controllo consigliato resta:

```bash
mvn spotless:apply
mvn spotless:check
mvn clean test
git status
```

Il Punto 6K non aggiunge nuovi domini o API. Rafforza i use case Operational Roles con:

- `ApplicationOperationalUseCaseHardeningTest`;
- copertura dei service di stato per Driver, Mechanic, WarehouseOperator, Dispatcher e Manager;
- verifica copy-on-write sulle attivazioni fallite;
- controllo di command nulli e dependency repository nulle;
- documentazione Markdown, HTML e CSS aggiornata.

Commit consigliato:

```bash
git add -A
git commit -m "Harden operational application use cases"
```

---

## Nota progetto — Punto 6L

Dopo l'aggiunta del Punto 6L — Application Compliance Base Use Cases, il controllo consigliato resta:

```bash
mvn spotless:apply
mvn spotless:check
mvn clean test
git status
```

Il Punto 6L aggiunge i primi use case applicativi Compliance base, quindi prima del commit conviene controllare anche che siano presenti i nuovi package:

```bash
find src/main/java/it/gabriele/truckflow/application -type d | grep compliance
find src/main/java/it/gabriele/truckflow/infrastructure/memory -type d | grep compliance
```

Il Punto 6L introduce:

- `RegisterComplianceRequirementUseCase`;
- `FindComplianceRequirementUseCase`;
- `ActivateComplianceRequirementUseCase`;
- `SuspendComplianceRequirementUseCase`;
- `ArchiveComplianceRequirementUseCase`;
- `DiscontinueComplianceRequirementUseCase`;
- `ComplianceRequirementRepository`;
- `InMemoryComplianceRequirementRepository`;
- test applicativi, repository port test e test repository in memory.

Commit consigliato:

```bash
git add -A
git commit -m "Expand application use cases with compliance base"
```

---

## Nota progetto — Punto 6M

Dopo l'aggiunta del Punto 6M — Application Layer Final Review & Freeze, il controllo consigliato resta:

```bash
mvn spotless:apply
mvn spotless:check
mvn clean test
git status
```

Il Punto 6M chiude il primo ciclo dell'application layer. Non aggiunge nuovi use case business, ma introduce il test finale di freeze:

- `ApplicationLayerFinalFreezeTest`;
- controllo dei package applicativi attivi;
- controllo del rapporto tra port in concrete e service applicativi;
- controllo della documentazione applicativa dal Punto 6A al Punto 6M;
- controllo contro layer prematuri come web, security, JPA, persistence o database.

Prima del commit conviene controllare anche che il documento finale sia presente:

```bash
ls docs/28-application-layer-final-review-freeze.md
```

Commit consigliato:

```bash
git add -A
git commit -m "Finalize application layer review and freeze"
```

---

## Nota progetto — Pulizia documentale finale dopo il Punto 6M

Dopo il Punto 6M, è stata prevista una piccola patch solo documentale per chiarire la roadmap finale.

Questa pulizia non modifica codice Java, test, package, use case o dipendenze Maven. Serve solo a rendere più chiara la documentazione ufficiale.

La patch documentale chiarisce che:

- il Punto 6 è completato da 6A a 6M;
- il Punto 6G Documents include register, find, activate e archive;
- il Punto 6G non include attach fisico, generazione PDF, upload, storage, versioning o workflow documentali;
- i punti 6I, 6J, 6K, 6L e 6M sono già stati applicati con la sequenza reale Vehicles, Operational Roles, hardening Operational Roles, Compliance base e final freeze;
- le dipendenze Spring eventualmente presenti nel `pom.xml` sono preparatorie o storiche, non ancora usate per controller REST, JPA, Spring Data o security;
- la documentazione digitale usa Guided Links per navigare in modo ordinato i file Markdown ufficiali.

Controllo consigliato dopo questa patch solo documentale:

```bash
mvn spotless:check
mvn clean test
git status
```

Commit consigliato:

```bash
git add -A
git commit -m "Align final roadmap documentation"
```
