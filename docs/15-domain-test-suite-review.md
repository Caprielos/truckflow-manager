# 15 – Domain Test Suite Review

## 1. Obiettivo

Questo documento descrive la revisione finale della test suite del dominio puro di TruckFlow Manager prima dell'introduzione del livello `application`.

L'obiettivo non è testare funzionalità future non ancora modellate, ma rendere i test più rappresentativi del dominio attuale:

- value object;
- aggregate root;
- invarianti;
- transizioni di stato;
- mutazioni lecite e vietate;
- eccezioni custom;
- confini tra bounded context;
- assenza di dipendenze vietate nel domain layer.

Questa fase completa il Punto 5 della roadmap come **Punto 5E — Revisione finale della test suite del dominio puro**.

## 2. Principio seguito

La regola principale è:

```text
Testare bene il dominio puro attuale, senza inventare moduli futuri.
```

Quindi i test non devono introdurre indirettamente concetti che appartengono a fasi successive, come:

- availability operativa;
- maintenance completa;
- planning;
- dispatching;
- tracking;
- audit;
- repository database;
- controller REST;
- compatibilità cargo-veicolo eseguita realmente;
- assegnazione autista-mezzo;
- workflow documentali;
- compliance check concreti.

Questi aspetti sono importanti, ma verranno testati quando esisteranno i relativi moduli.

## 3. Cosa è stato aggiunto

### 3.1 Test architetturali del dominio

È stato aggiunto `DomainArchitectureTest`.

Questo test controlla che il dominio puro rimanga indipendente da tecnologia e layer esterni.

Verifica che sotto `src/main/java/it/gabriele/truckflow/domain` non vengano importati:

- Spring;
- JPA;
- Jakarta Persistence;
- Lombok;
- application layer;
- infrastructure layer.

Verifica inoltre che nel dominio non siano presenti punti di validazione con:

```java
throw new IllegalArgumentException(...)
throw new IllegalStateException(...)
```

Il dominio deve usare eccezioni custom specifiche, non eccezioni standard generiche nei punti di validazione.

### 3.2 Test dei confini tra domini

`DomainArchitectureTest` controlla anche che i bounded context non importino aggregate root completi di altri domini.

Esempi di regole protette:

- `domain.cargo` non deve importare `domain.vehicles` o `domain.shipments`;
- `domain.vehicles` non deve importare `domain.cargo` o `domain.shipments`;
- `domain.shipments` può usare `CargoId` e `LocationId`, ma non deve importare `CargoUnit` o `Location` completi;
- `domain.triptemplates` può usare `LocationId`, ma non deve importare `Location` completo;
- `domain.operational` può usare `UserId`, ma non deve importare `User` completo;
- `domain.documents` deve usare riferimenti astratti, non aggregate root concreti;
- `domain.compliance` deve usare target astratti, non istanze concrete di altri domini.

Questi test proteggono una regola fondamentale della Domain Foundation:

```text
I domini si collegano tramite ID, value object stabili o concetti astratti, non tramite aggregate root completi.
```

### 3.3 Test contrattuali dei value object

È stato aggiunto `DomainValueObjectContractTest`.

Questo test verifica che i value object principali del dominio:

- normalizzino correttamente i valori;
- rifiutino valori non validi;
- lancino eccezioni custom coerenti;
- mantengano il significato specifico del bounded context.

Sono coperti, tra gli altri:

- `CargoCode`;
- `ShipmentCode`;
- `LocationCode`;
- `TripTemplateCode`;
- `DocumentCode`;
- `ComplianceRequirementCode`;
- `FleetCode`;
- `OperationalScopeCode`;
- `LicensePlate`;
- `VehicleIdentificationNumber`;
- `LanguageCode`;
- `UserTheme`;
- `CountryCode`;
- `JurisdictionRegion`;
- `ComplianceJurisdictionScope`.

### 3.4 Test su `ComplianceJurisdiction`

I test chiariscono una scelta importante:

```text
Il dominio rappresenta la giurisdizione.
L'application layer e la UI decideranno in futuro quali regioni, paesi e default mostrare.
```

Per questo vengono testati casi come:

```text
scope = NATIONAL
country = IT
region = assente
```

```text
scope = EUROPEAN_UNION
country = assente
region = EU
```

```text
scope = REGIONAL
country = IT
region = LOMBARDY
```

Non viene inserita nel dominio la logica:

```text
Europa -> lista nazioni -> default Italia
```

Quella sarà una futura logica di configurazione, application layer o interfaccia utente.

### 3.5 Test aggiuntivi sul dominio cargo

Sono stati rafforzati i test di `domain.cargo` con casi limite e negativi su:

- dimensioni negative;
- temperatura controllata con minimo maggiore del massimo;
- packaging con quantità negativa;
- requisiti di compatibilità con elementi nulli;
- sostituzione fallita della temperatura senza mutazione parziale dell'aggregate.

Questi test rendono più espliciti gli invarianti su dimensioni, temperature, packaging e requisiti di trasporto.

### 3.6 Test aggiuntivi sul dominio shipments

Sono stati rafforzati i test di `domain.shipments` con casi su:

- sostituzione fallita delle proprietà senza mutazione parziale della shipment;
- rifiuto di una tratta `PICKUP` con stessa origine e destinazione;
- accettazione di stessa origine e destinazione solo per tratte `TRANSFER` o `SPECIAL`.

Questi test proteggono meglio gli invarianti sulle mutazioni atomiche e sulle regole delle tratte logiche.

## 4. Cosa era già coperto prima

Prima di questa revisione erano già presenti test significativi su:

- creazione di utenti applicativi;
- ruoli e permessi;
- stato utente;
- figure operative;
- qualificazioni operative;
- catalogo qualificazioni;
- unità veicolo;
- combinazioni veicolari;
- targa e VIN come value object;
- cargo ADR e cargo a temperatura controllata;
- location e coordinate;
- trip template e segmenti;
- shipment confermate e draft;
- documenti astratti;
- requisiti di compliance astratti;
- eccezioni base condivise.

Questa patch non sostituisce quei test: li completa con test trasversali e alcuni casi limite aggiuntivi.

## 5. Cosa manca ancora e perché

### 5.1 Availability

Non sono stati aggiunti test su availability operativa perché il dominio attuale non modella ancora disponibilità giornaliera, prenotazioni, assegnazioni o indisponibilità risorse.

Questi test arriveranno quando esisterà un modulo dedicato a fleet availability, planning o dispatching.

### 5.2 Maintenance

Non sono stati aggiunti test completi su manutenzione perché non esiste ancora un bounded context completo per:

- manutenzioni programmate;
- interventi;
- difetti segnalati;
- downtime;
- costi manutentivi;
- pneumatici;
- ricambi.

Questi test arriveranno quando verrà modellato il dominio maintenance.

### 5.3 Fleet operations

Non sono stati aggiunti test su fleet operations operative perché il dominio `vehicles` attuale descrive unità, combinazioni, capacità tecniche e stato anagrafico, ma non esecuzione operativa, disponibilità o assegnazione.

### 5.4 Compatibilità cargo-veicolo

Non sono stati aggiunti test di compatibilità reale tra cargo e veicolo perché la regola architetturale attuale è:

```text
Cargo dichiara requisiti.
Vehicles dichiara capacità.
Planning/Dispatching verifica la compatibilità.
```

Quindi oggi è corretto testare che il cargo dichiari requisiti e che il veicolo dichiari capacità, ma non è ancora corretto creare un test che assegna un cargo a un mezzo.

### 5.5 Compliance check concreti

Non sono stati aggiunti test su `ComplianceCheck`, `ComplianceResult` o `ComplianceViolation` perché questi concetti non appartengono ancora al dominio compliance puro.

Il dominio attuale modella solo `ComplianceRequirement`, cioè il requisito astratto.

### 5.6 Workflow documentali

Non sono stati aggiunti test su firma, scadenza, validazione documento, approvazione o upload file perché `domain.documents` rappresenta il documento astratto, non il workflow documentale.

Questi test arriveranno con moduli futuri di document management, file storage e workflow.

## 6. Regola per i prossimi test

Ogni nuovo test del domain layer deve rispettare queste regole:

- deve raccontare una regola di business o un invariante;
- deve avere un nome espressivo;
- deve usare eccezioni custom del dominio;
- deve evitare dipendenze da framework, database o API;
- deve evitare di testare funzionalità future non modellate;
- deve proteggere confini tra bounded context;
- deve preferire comportamento e coerenza rispetto a conteggi fragili.

## 7. Stato finale

Dopo questa revisione, la suite di test del dominio puro protegge meglio:

- value object principali;
- codici aziendali;
- identificatori veicolo;
- preferenze utente;
- giurisdizione compliance;
- operational scope;
- invarianti cargo;
- invarianti shipment;
- eccezioni custom;
- confini tra domini;
- indipendenza del domain layer da framework e infrastructure.

Il dominio rimane pronto per il prossimo passo:

```text
Punto 6 — Application Layer
```

## Collegamento con il Punto 6A

La revisione della test suite del dominio puro prepara il progetto all'application layer.

I test di dominio continuano a proteggere invarianti, value object, aggregate root, eccezioni custom e confini tra bounded context. I futuri test applicativi avranno invece un compito diverso: verificare che i casi d'uso orchestrino correttamente repository, command, result e aggregate già validati dal dominio.

Per questo il nuovo documento `16-application-layer-blueprint.md` separa chiaramente test di dominio e test applicativi.

## Estensione successiva — test della Application Foundation

Con il Punto 6B sono stati aggiunti test dedicati all'application layer.

Questi test non sostituiscono la test suite del dominio. Servono a verificare che la nuova foundation applicativa resti separata da framework, web, JPA e infrastructure concreta, e che il dominio non importi il livello application.
