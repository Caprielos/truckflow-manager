# 5. Package `domain.vehicles`

## 5.1 Scopo del dominio veicoli

Il package `domain.vehicles` rappresenta il parco mezzi di TruckFlow Manager.

È stato progettato per descrivere in modo realistico i mezzi usati in una vera azienda di trasporti, distinguendo tra:

- unità fisiche singole;
- combinazioni veicolari;
- allestimenti;
- schede tecniche;
- profili di aggancio;
- capacità speciali;
- ruoli operativi del mezzo;
- stato tecnico/anagrafico.

La scelta fondamentale è separare due concetti che nella pratica vengono spesso confusi:

- `VehicleUnit`: una singola unità fisica del parco mezzi;
- `VehicleCombination`: un complesso veicolare composto da una o più unità.

Esempi:

- un trattore stradale è una `VehicleUnit`;
- un semirimorchio è una `VehicleUnit`;
- trattore stradale + semirimorchio formano una `VehicleCombination`;
- una motrice è una `VehicleUnit`;
- un rimorchio è una `VehicleUnit`;
- motrice + rimorchio formano una `VehicleCombination`;
- un furgone singolo è una `VehicleCombination` di tipo mezzo singolo;
- un muletto è una `VehicleUnit` di tipo mezzo da magazzino.

Questa distinzione permette di modellare correttamente bilici, autotreni, furgoni, rimorchi, mezzi speciali e mezzi di magazzino senza forzare tutto dentro una sola classe generica.

## 5.2 Perché il package è stato diviso in sottopackage

Il dominio veicoli è il package più ampio del progetto. Tenerlo tutto piatto dentro `domain.vehicles` avrebbe reso il codice difficile da leggere e da mantenere.

Per questo è stato riorganizzato in sottopackage, non per tipo di mezzo, ma per concetto di dominio.

La struttura finale è:

```text
domain.vehicles
├─ unit
├─ combination
├─ coupling
├─ specification
├─ body
├─ operation
└─ common
```

Questa divisione è importante perché evita una struttura sbagliata del tipo:

```text
vehicles.truck
vehicles.trailer
vehicles.van
vehicles.tractor
```

Una divisione per tipo di mezzo sarebbe meno pulita, perché molte classi sono condivise tra camion, trattori, rimorchi, semirimorchi e furgoni.

Per esempio:

- `VehicleWeights` serve sia a un camion sia a un trailer;
- `VehicleAxle` serve sia a una motrice sia a un semirimorchio;
- `CouplingProfile` serve sia a chi traina sia a chi viene trainato;
- `VehicleBodyType` serve a camion, trailer, furgoni e mezzi speciali;
- `VehicleCapability` può valere per più categorie di mezzi.

La divisione scelta è quindi più stabile e più coerente con un dominio enterprise.

## 5.3 Struttura finale del package

```text
domain.vehicles
├─ unit
│  ├─ VehicleUnit
│  ├─ VehicleUnitId
│  ├─ FleetCode
│  ├─ VehicleUnitType
│  ├─ PowerSource
│  └─ VehicleStatus
│
├─ combination
│  ├─ VehicleCombination
│  ├─ VehicleCombinationId
│  └─ VehicleCombinationType
│
├─ coupling
│  ├─ CouplingProfile
│  └─ CouplingType
│
├─ specification
│  ├─ VehicleTechnicalSpecification
│  ├─ VehicleDimensions
│  ├─ VehicleLoadSpace
│  ├─ VehicleWeights
│  ├─ VehicleAxleSpecification
│  ├─ VehicleAxle
│  ├─ VehicleTireSpecification
│  ├─ VehicleEngineSpecification
│  ├─ VehicleTransmissionSpecification
│  ├─ VehicleChassisSpecification
│  ├─ VehicleElectricSpecification
│  └─ VehicleCabSpecification
│
├─ body
│  ├─ VehicleBodyType
│  ├─ VehicleBodyProfile
│  ├─ RefrigeratedBodyProfile
│  ├─ TankBodyProfile
│  ├─ CarCarrierBodyProfile
│  ├─ ContainerChassisBodyProfile
│  ├─ LowLoaderBodyProfile
│  ├─ CurtainsiderBodyProfile
│  ├─ TipperBodyProfile
│  ├─ SiloBodyProfile
│  └─ LivestockBodyProfile
│
├─ operation
│  ├─ VehicleCapability
│  └─ VehicleOperationalRole
│
└─ common
   └─ VehicleValidation
```

## 5.4 Cosa non gestisce ancora `domain.vehicles`

Il dominio veicoli descrive il mezzo e le sue regole tecniche di base, ma non gestisce ancora:

- disponibilità giornaliera;
- assegnazione a viaggio;
- assegnazione autista;
- compatibilità tra autista e mezzo;
- manutenzione dettagliata;
- revisioni;
- assicurazioni;
- documenti;
- scadenze;
- GPS;
- telematica;
- consumi reali;
- anomalie;
- prenotazioni;
- pianificazione operativa.

Queste parti verranno modellate in moduli futuri come planning, dispatching, fleet availability, maintenance, documents e compliance.

Il dominio veicoli, per ora, risponde a domande come:

- che unità fisica è questo mezzo?
- che allestimento ha?
- può trainare?
- può essere trainato?
- può formare un bilico?
- può formare un autotreno?
- che capacità tecniche possiede?
- per quale tipo di lavoro viene usato?

Non risponde ancora a domande come:

- è disponibile oggi?
- è già assegnato a un viaggio?
- l’autista ha la patente giusta?
- il rimorchio è libero?
- la revisione è scaduta?

---

# 5.5 Package `domain.vehicles.unit`

## 5.5.1 Scopo del package

Il package `unit` contiene tutto ciò che riguarda la singola unità fisica del parco mezzi.

Una `VehicleUnit` è un oggetto fisico reale che l’azienda possiede, noleggia o gestisce.

Esempi:

- trattore stradale;
- motrice rigida;
- furgone;
- pickup;
- semirimorchio;
- rimorchio;
- rimorchio ad asse centrale;
- mezzo speciale;
- muletto o mezzo da magazzino.

## 5.5.2 `VehicleUnit`

`VehicleUnit` rappresenta la singola unità fisica registrata nel parco mezzi.

Contiene:

- `VehicleUnitId`, cioè l’identità tecnica del dominio;
- `FleetCode`, cioè il codice interno aziendale;
- targa;
- VIN o numero telaio;
- `VehicleUnitType`, cioè la natura fisica dell’unità;
- `VehicleBodyType`, cioè l’allestimento;
- `PowerSource`, cioè la sorgente di alimentazione;
- `VehicleTechnicalSpecification`, cioè la scheda tecnica generale;
- `VehicleBodyProfile`, cioè il profilo tecnico dell’allestimento, quando presente;
- capacità tecniche;
- ruoli operativi;
- `CouplingProfile`, cioè il profilo di aggancio/traino;
- `VehicleStatus`, cioè lo stato tecnico/anagrafico;
- note.

Esempio concettuale:

- un trattore stradale avrà `VehicleUnitType.TRACTOR_UNIT`, `VehicleBodyType.NONE`, `PowerSource.DIESEL` e un profilo di aggancio che permette il traino;
- un semirimorchio frigo avrà `VehicleUnitType.SEMI_TRAILER`, `VehicleBodyType.REFRIGERATED`, `PowerSource.NONE` e un profilo di aggancio che permette di essere trainato;
- una motrice centinata avrà `VehicleUnitType.RIGID_TRUCK`, `VehicleBodyType.CURTAINSIDER` e, se predisposta, un profilo che permette di trainare un rimorchio.

## 5.5.3 `VehicleUnitId`

`VehicleUnitId` è l’identificatore tecnico della singola unità veicolo.

È diverso da:

- targa;
- telaio;
- codice flotta.

La targa è un dato reale del veicolo. Il telaio identifica fisicamente il mezzo. Il codice flotta è leggibile dall’azienda. L’ID, invece, è l’identità del dominio usata dal sistema.

## 5.5.4 `FleetCode`

`FleetCode` è il codice interno aziendale del mezzo.

Esempi:

- `TRK-001`;
- `TRC-012`;
- `TRL-044`;
- `VAN-006`;
- `FRK-003`.

Serve perché in azienda spesso si lavora con codici più leggibili rispetto agli UUID o ai numeri telaio.

## 5.5.5 `VehicleUnitType`

`VehicleUnitType` descrive la natura fisica dell’unità.

Contiene:

- `RIGID_TRUCK`;
- `TRACTOR_UNIT`;
- `VAN`;
- `PICKUP`;
- `SEMI_TRAILER`;
- `DRAWBAR_TRAILER`;
- `CENTER_AXLE_TRAILER`;
- `SPECIAL_VEHICLE`;
- `WAREHOUSE_EQUIPMENT`.

Questa enum non contiene allestimenti.

Quindi:

- una cisterna non è un `VehicleUnitType`, è un `VehicleBodyType`;
- un frigo non è un `VehicleUnitType`, è un `VehicleBodyType`;
- una bisarca non è un `VehicleUnitType`, è un `VehicleBodyType`;
- un bilico non è un `VehicleUnitType`, è una `VehicleCombination`;
- un autotreno non è un `VehicleUnitType`, è una `VehicleCombination`.

## 5.5.6 `PowerSource`

`PowerSource` rappresenta la sorgente di alimentazione o energia del mezzo.

Contiene:

- `DIESEL`;
- `ELECTRIC`;
- `CNG`;
- `LNG`;
- `HYBRID`;
- `NONE`.

`NONE` è importante perché trailer e semirimorchi non sono motorizzati.

Regola principale:

- `SEMI_TRAILER`, `DRAWBAR_TRAILER` e `CENTER_AXLE_TRAILER` devono avere `PowerSource.NONE`.

## 5.5.7 `VehicleStatus`

`VehicleStatus` rappresenta lo stato tecnico/anagrafico del mezzo.

Contiene:

- `ACTIVE`;
- `SUSPENDED`;
- `OUT_OF_SERVICE`;
- `DISMISSED`.

Non contiene stati come `AVAILABLE`, `BOOKED` o `IN_TRIP`, perché quelli appartengono alla disponibilità e alla pianificazione operativa, non all’anagrafica tecnica del veicolo.

---

# 5.6 Package `domain.vehicles.body`

## 5.6.1 Scopo del package

Il package `body` contiene tutto ciò che riguarda l’allestimento del mezzo.

Questa separazione è una delle scelte più importanti del dominio veicoli.

Il tipo fisico del veicolo e l’allestimento non sono la stessa cosa.

Esempi:

- `RIGID_TRUCK` indica una motrice/camion rigido;
- `CURTAINSIDER` indica che è centinato;
- `SEMI_TRAILER` indica un semirimorchio;
- `REFRIGERATED` indica che è frigo;
- `CAR_CARRIER` indica che è bisarca.

## 5.6.2 `VehicleBodyType`

`VehicleBodyType` descrive l’allestimento generale.

Contiene:

- `NONE`;
- `CURTAINSIDER`;
- `BOX`;
- `FLATBED`;
- `TIPPER`;
- `REFRIGERATED`;
- `ISOTHERMAL`;
- `TANK`;
- `CONTAINER_CHASSIS`;
- `LOW_LOADER`;
- `CAR_CARRIER`;
- `COIL_CARRIER`;
- `DOUBLE_DECK`;
- `SILO`;
- `LIVESTOCK`.

Esempi:

- `TRACTOR_UNIT` + `NONE` = trattore stradale;
- `SEMI_TRAILER` + `REFRIGERATED` = semirimorchio frigo;
- `RIGID_TRUCK` + `CURTAINSIDER` = motrice centinata;
- `DRAWBAR_TRAILER` + `BOX` = rimorchio furgonato;
- `SEMI_TRAILER` + `CAR_CARRIER` = semirimorchio bisarca.

## 5.6.3 `VehicleBodyProfile`

`VehicleBodyProfile` rappresenta il profilo tecnico specifico dell’allestimento.

È stato scelto di non usare una mappa generica `Map<String, Object>`, perché in un dominio puro sarebbe troppo libera e poco controllata.

Invece sono stati creati profili espliciti.

Questa scelta rende il dominio:

- più leggibile;
- più sicuro;
- più controllabile;
- più facile da testare;
- più vicino al linguaggio reale del business.

## 5.6.4 Profili di allestimento presenti

I profili attuali sono:

- `RefrigeratedBodyProfile`, per frigo, isotermici e ATP;
- `TankBodyProfile`, per cisterne;
- `CarCarrierBodyProfile`, per bisarche;
- `ContainerChassisBodyProfile`, per porta-container;
- `LowLoaderBodyProfile`, per pianali ribassati;
- `CurtainsiderBodyProfile`, per centinati;
- `TipperBodyProfile`, per ribaltabili;
- `SiloBodyProfile`, per silos;
- `LivestockBodyProfile`, per trasporto animali vivi.

Ogni profilo deve essere coerente con il `VehicleBodyType` della `VehicleUnit`.

Esempio:

- un veicolo con `VehicleBodyType.REFRIGERATED` deve avere un profilo refrigerato;
- un veicolo con `VehicleBodyType.TANK` deve avere un profilo cisterna;
- un veicolo con `VehicleBodyType.CAR_CARRIER` deve avere un profilo bisarca.

Questa regola evita incoerenze come una cisterna con profilo da frigo.

---

# 5.7 Package `domain.vehicles.specification`

## 5.7.1 Scopo del package

Il package `specification` contiene la scheda tecnica generale del mezzo.

Non descrive l’allestimento specifico, che sta in `body`. Descrive invece caratteristiche tecniche comuni come:

- dimensioni;
- pesi;
- vano di carico;
- assi;
- pneumatici;
- motore;
- trasmissione;
- telaio;
- elettrico;
- cabina.

Non tutte le sezioni valgono per tutti i mezzi.

Esempi:

- un semirimorchio non ha motore;
- un trattore stradale non ha vano di carico;
- un rimorchio ha assi, pesi, pneumatici e dimensioni;
- un mezzo elettrico ha una specifica elettrica;
- un mezzo senza cabina non ha `VehicleCabSpecification`.

## 5.7.2 `VehicleTechnicalSpecification`

`VehicleTechnicalSpecification` raccoglie la scheda tecnica generale.

Contiene:

- `VehicleDimensions`;
- `VehicleLoadSpace`;
- `VehicleWeights`;
- `VehicleAxleSpecification`;
- `VehicleTireSpecification`;
- `VehicleEngineSpecification`;
- `VehicleTransmissionSpecification`;
- `VehicleChassisSpecification`;
- `VehicleElectricSpecification`;
- `VehicleCabSpecification`.

## 5.7.3 `VehicleDimensions`

Rappresenta le dimensioni esterne:

- lunghezza;
- larghezza;
- altezza;
- passo.

## 5.7.4 `VehicleLoadSpace`

Rappresenta lo spazio utile di carico:

- lunghezza interna;
- larghezza interna;
- altezza interna;
- volume;
- capacità pallet.

Esempi:

- un semirimorchio centinato avrà lo spazio di carico;
- un furgone avrà lo spazio di carico;
- un trattore stradale normalmente non avrà lo spazio di carico.

## 5.7.5 `VehicleWeights`

Rappresenta i pesi tecnici:

- tara;
- massa massima;
- portata utile;
- massa massima del complesso;
- peso massimo rimorchiabile.

Questi dati sono fondamentali per camion, trattori, rimorchi e semirimorchi.

## 5.7.6 `VehicleAxleSpecification` e `VehicleAxle`

La gestione degli assi è modellata con una lista di assi.

Questa è una scelta importante perché un semplice numero totale di assi non basta in un dominio realistico.

Ogni `VehicleAxle` contiene:

- numero asse;
- indicazione se è sterzante;
- indicazione se è sollevabile;
- indicazione se è gemellato;
- note.

Questa struttura permette di sapere:

- quali assi sono sterzanti;
- quali assi sono sollevabili;
- quali assi sono gemellati;
- in quale ordine sono montati.

Esempio concettuale:

- asse 1: sterzante;
- asse 2: gemellato;
- asse 3: sollevabile.

## 5.7.7 `VehicleTireSpecification`

Rappresenta informazioni generali sugli pneumatici:

- misura;
- tipo;
- numero ruote;
- informazione generale sul gemellato.

L’informazione generale sul gemellato è solo riassuntiva.

La verità tecnica dettagliata resta nella lista degli assi.

## 5.7.8 `VehicleEngineSpecification`

Rappresenta i dati motore per i mezzi motorizzati:

- potenza;
- coppia;
- cilindrata;
- classe emissioni;
- capacità AdBlue;
- capacità serbatoio;
- consumo medio.

Non vale per rimorchi e semirimorchi non motorizzati.

## 5.7.9 `VehicleTransmissionSpecification`

Descrive cambio e trasmissione:

- tipo cambio;
- numero marce;
- retarder;
- PTO.

## 5.7.10 `VehicleChassisSpecification`

Descrive telaio e sistemi di base:

- sospensioni;
- freni;
- ABS;
- EBS;
- ESP.

## 5.7.11 `VehicleElectricSpecification`

Descrive i dati elettrici:

- capacità batteria;
- tipo ricarica;
- connettore;
- tempo di ricarica.

Vale per van elettrici, mezzi da magazzino elettrici e altri mezzi elettrici o ibridi, quando applicabile.

## 5.7.12 `VehicleCabSpecification`

Descrive la cabina:

- tipo cabina;
- posti;
- cuccetta;
- infotainment;
- climatizzazione.

Vale per trattori, motrici, van e altri mezzi con cabina rilevante.

---

# 5.8 Package `domain.vehicles.coupling`

## 5.8.1 Scopo del package

Il package `coupling` contiene tutto ciò che riguarda aggancio, traino e possibilità tecnica di collegare due unità.

È fondamentale per gestire:

- trattore + semirimorchio;
- motrice + rimorchio;
- furgone + rimorchio;
- combinazioni speciali.

## 5.8.2 `CouplingProfile`

`CouplingProfile` descrive se un mezzo può trainare, essere trainato o agganciarsi a un’altra unità.

Contiene:

- tipo di aggancio;
- possibilità di trainare;
- possibilità di essere trainato;
- peso massimo rimorchiabile;
- massa massima del complesso;
- note.

Esempi:

- un trattore stradale può trainare ed è dotato di ralla;
- un semirimorchio può essere trainato ed è dotato di kingpin;
- una motrice può trainare tramite gancio traino;
- un rimorchio può essere trainato tramite timone.

## 5.8.3 `CouplingType`

`CouplingType` rappresenta il tipo di aggancio.

Contiene:

- `NONE`;
- `FIFTH_WHEEL`;
- `KINGPIN`;
- `DRAWBAR_HITCH`;
- `DRAWBAR`;
- `CENTER_AXLE_HITCH`;
- `SPECIAL_COUPLING`.

Questa separazione permette al dominio di controllare la coerenza tecnica tra unità.

---

# 5.9 Package `domain.vehicles.combination`

## 5.9.1 Scopo del package

Il package `combination` contiene i complessi veicolari.

Una combinazione rappresenta il mezzo completo che può essere usato operativamente.

Esempi:

- camion singolo;
- furgone singolo;
- trattore + semirimorchio;
- motrice + rimorchio;
- furgone + rimorchio;
- mezzo da magazzino.

## 5.9.2 `VehicleCombination`

`VehicleCombination` rappresenta una combinazione di una o più unità.

Contiene:

- `VehicleCombinationId`;
- `VehicleCombinationType`;
- ID dell’unità primaria;
- ID dell’unità secondaria, quando presente;
- capacità combinate;
- ruoli operativi;
- stato;
- note.

La combinazione contiene `VehicleUnitId`, non direttamente gli oggetti completi `VehicleUnit`.

Questa scelta riduce l’accoppiamento tra aggregati.

La factory concettuale da unità serve a validare che la combinazione sia tecnicamente coerente e a combinare capacità e ruoli operativi delle unità coinvolte.

## 5.9.3 `VehicleCombinationId`

È l’identificatore tecnico della combinazione.

Serve perché una combinazione può essere trattata come concetto operativo autonomo, anche se è composta da unità già esistenti.

## 5.9.4 `VehicleCombinationType`

`VehicleCombinationType` descrive il tipo di complesso veicolare.

Contiene:

- `SINGLE_VEHICLE`;
- `ARTICULATED_VEHICLE`;
- `ROAD_TRAIN`;
- `VAN_WITH_TRAILER`;
- `SPECIAL_COMBINATION`;
- `WAREHOUSE_UNIT`.

Significato:

- `SINGLE_VEHICLE`: mezzo singolo;
- `ARTICULATED_VEHICLE`: trattore stradale + semirimorchio, cioè bilico o autoarticolato;
- `ROAD_TRAIN`: motrice + rimorchio, cioè autotreno;
- `VAN_WITH_TRAILER`: furgone + rimorchio;
- `SPECIAL_COMBINATION`: combinazione speciale;
- `WAREHOUSE_UNIT`: mezzo operativo interno.

## 5.9.5 Perché non salvare `totalTechnicalSpecification`

La scheda tecnica totale della combinazione non viene salvata come campo principale.

È preferibile calcolarla quando serve, partendo dalle unità coinvolte.

Motivo: se la stessa informazione viene salvata sia sulle unità sia sulla combinazione, può nascere incoerenza.

Esempio:

- trattore = 8.000 kg;
- semirimorchio = 7.000 kg;
- totale salvato = 12.000 kg.

Quale valore è corretto?

Per questo i totali tecnici devono essere calcolati da regole di dominio o da servizi applicativi quando saranno necessari.

---

# 5.10 Package `domain.vehicles.operation`

## 5.10.1 Scopo del package

Il package `operation` contiene classificazioni operative del mezzo.

Qui ci sono due concetti diversi:

- `VehicleCapability`: cosa il mezzo supporta tecnicamente;
- `VehicleOperationalRole`: per quale tipo di lavoro il mezzo viene usato.

## 5.10.2 `VehicleCapability`

`VehicleCapability` descrive capacità tecniche o speciali del mezzo.

Esempi:

- `ADR`;
- `ATP`;
- `REEFER_UNIT`;
- `TEMPERATURE_CONTROLLED`;
- `DUAL_TEMPERATURE`;
- `HYDRAULIC_TAIL_LIFT`;
- `HYDRAULIC_RAMP`;
- `SLIDING_ROOF`;
- `SLIDING_CURTAINS`;
- `DOUBLE_DECK`;
- `COIL_WELL`;
- `BULK_TRANSPORT`;
- `CONTAINER_LOCKS`;
- `FOODGRADE_TANK`;
- `CHEMICAL_TANK`;
- `FUEL_TANK`;
- `LOW_LOADER`;
- `HEAVY_DUTY`;
- `EXCEPTIONAL_TRANSPORT`;
- `CAR_TRANSPORT`;
- `LIVESTOCK_TRANSPORT`.

La differenza è:

- `VehicleBodyType` dice che allestimento ha il mezzo;
- `VehicleBodyProfile` descrive i dati tecnici dell’allestimento;
- `VehicleCapability` dice cosa il mezzo può supportare tecnicamente.

## 5.10.3 `VehicleOperationalRole`

`VehicleOperationalRole` descrive per quale tipo di lavoro viene usato il mezzo.

Esempi:

- `LINE_HAUL`;
- `DISTRIBUTION`;
- `LAST_MILE`;
- `GROUPAGE`;
- `FULL_LOAD`;
- `ADR_TRANSPORT`;
- `REFRIGERATED_TRANSPORT`;
- `BULK_TRANSPORT`;
- `CONTAINER_TRANSPORT`;
- `COIL_TRANSPORT`;
- `HEAVY_DUTY`;
- `SPECIAL_TRANSPORT`;
- `CAR_TRANSPORT`;
- `WAREHOUSE_SUPPORT`;
- `FLEET_SUPPORT`;
- `MAINTENANCE_SUPPORT`.

Non è una caratteristica fisica.

È una classificazione d’uso aziendale.

Esempio:

- un semirimorchio frigo può avere capacità `REEFER_UNIT` e ruolo operativo `REFRIGERATED_TRANSPORT`;
- una bisarca può avere capacità `CAR_TRANSPORT` e ruolo operativo `CAR_TRANSPORT`;
- un furgone può avere ruolo operativo `LAST_MILE`.

---

# 5.11 Package `domain.vehicles.common`

## 5.11.1 Scopo del package

Il package `common` contiene utilità di dominio condivise dal package veicoli.

Al momento contiene `VehicleValidation`.

## 5.11.2 `VehicleValidation`

`VehicleValidation` raccoglie controlli comuni usati dalle classi del dominio veicoli.

Serve per evitare duplicazione di piccole regole ripetitive, come:

- obbligatorietà dei valori;
- normalizzazione del testo;
- controllo di collezioni senza elementi null.

È una utility interna del dominio veicoli, non un servizio applicativo e non un componente infrastrutturale.

---

# 5.12 Regole principali del dominio veicoli

## 5.12.1 Regole su `VehicleUnit`

Le regole principali sono:

- ID obbligatorio o generato;
- `FleetCode` obbligatorio;
- `VehicleUnitType` obbligatorio;
- `VehicleBodyType` obbligatorio;
- `PowerSource` obbligatorio;
- `VehicleTechnicalSpecification` obbligatoria;
- `VehicleStatus` obbligatorio;
- note normalizzate;
- trailer e semitrailer devono avere `PowerSource.NONE`;
- un trattore stradale deve avere `VehicleBodyType.NONE`;
- un trattore stradale deve poter trainare;
- un semirimorchio deve poter essere trainato;
- un rimorchio deve poter essere trainato;
- il profilo dell’allestimento deve corrispondere al `VehicleBodyType`.

## 5.12.2 Regole su `VehicleCombination`

Le regole principali sono:

- `SINGLE_VEHICLE` non deve avere unità secondaria;
- trailer e semitrailer non possono essere usati come veicoli singoli;
- `ARTICULATED_VEHICLE` richiede `TRACTOR_UNIT` + `SEMI_TRAILER`;
- `ROAD_TRAIN` richiede `RIGID_TRUCK` + `DRAWBAR_TRAILER` oppure `CENTER_AXLE_TRAILER`;
- `VAN_WITH_TRAILER` richiede `VAN` + trailer compatibile;
- `WAREHOUSE_UNIT` richiede una sola unità di tipo `WAREHOUSE_EQUIPMENT`;
- `SPECIAL_COMBINATION` richiede unità tecnicamente compatibili;
- le unità devono essere compatibili dal punto di vista del traino e dell’aggancio.

---

# 5.13 Caso specifico: motrice con rimorchio se disponibile

Il caso pratico era:

> camion dove, se disponibile, metto un rimorchio.

Nel dominio veicoli puro si modella così:

- la motrice è una `VehicleUnit` di tipo `RIGID_TRUCK`;
- la motrice ha un `CouplingProfile` che permette il traino;
- il rimorchio è una `VehicleUnit` di tipo `DRAWBAR_TRAILER` oppure `CENTER_AXLE_TRAILER`;
- il rimorchio ha un `CouplingProfile` che permette di essere trainato;
- la combinazione è una `VehicleCombination` di tipo `ROAD_TRAIN`.

La frase “se disponibile” non appartiene ancora a `domain.vehicles`.

Appartiene a futuri moduli come:

- fleet availability;
- planning;
- dispatching;
- trip assignment.

Questa separazione è importante perché il dominio veicoli deve descrivere la possibilità tecnica, non la disponibilità giornaliera.

---

# 5.14 Benefici della nuova divisione in sottopackage

La nuova divisione rende il dominio più leggibile perché ogni area ha una responsabilità chiara:

- `unit`: unità fisiche;
- `combination`: complessi veicolari;
- `coupling`: agganci e traino;
- `specification`: scheda tecnica generale;
- `body`: allestimenti e profili di allestimento;
- `operation`: capacità e usi operativi;
- `common`: validazioni condivise.

Questa struttura è più facile da navigare e da estendere.

Per esempio:

- se in futuro aggiungiamo nuovi profili di allestimento, lavoriamo in `body`;
- se aggiungiamo nuove specifiche tecniche, lavoriamo in `specification`;
- se miglioriamo le regole di aggancio, lavoriamo in `coupling`;
- se introduciamo nuovi tipi di combinazione, lavoriamo in `combination`.

Il risultato è un dominio più ordinato, più professionale e più vicino a una struttura enterprise.

## 5.15 Sintesi finale

Il dominio veicoli ora distingue chiaramente:

- unità fisica;
- combinazione;
- tipo fisico;
- allestimento;
- profilo tecnico dell’allestimento;
- scheda tecnica generale;
- assi e pneumatici;
- motore e trasmissione;
- agganci;
- capacità;
- ruoli operativi;
- stato tecnico.

La riorganizzazione in sottopackage non cambia il significato del dominio, ma lo rende molto più leggibile.

Questa è la struttura corretta per continuare a far crescere TruckFlow senza trasformare `domain.vehicles` in un package troppo grande e difficile da mantenere.
