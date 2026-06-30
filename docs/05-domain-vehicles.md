# 5. Package `domain.vehicles`

## 5.1 Scopo del package

Il package `domain.vehicles` rappresenta il parco mezzi di TruckFlow Manager.

È stato progettato per gestire in modo realistico:

- veicoli singoli;
- trattori stradali;
- semirimorchi;
- camion/motrici;
- rimorchi;
- furgoni;
- mezzi speciali;
- mezzi di magazzino;
- bilici;
- autotreni;
- furgoni con rimorchio.

La scelta fondamentale è distinguere tra:

- `VehicleUnit`: singola unità fisica;
- `VehicleCombination`: complesso veicolare composto da una o più unità.

Questa separazione permette di modellare correttamente casi reali come:

- trattore + semirimorchio;
- motrice + rimorchio;
- motrice da sola;
- furgone da solo;
- furgone con rimorchio;
- mezzo da magazzino.

## 5.2 Cosa non gestisce ancora

Il dominio veicoli non gestisce ancora:

- disponibilità giornaliera;
- assegnazione a viaggio;
- driver assignment;
- manutenzione dettagliata;
- revisioni;
- assicurazioni;
- documenti;
- GPS;
- telematica;
- consumi reali;
- anomalie;
- prenotazioni.

Queste informazioni verranno modellate in altri moduli.

## 5.3 `VehicleUnit`

`VehicleUnit` rappresenta una singola unità fisica del parco mezzi.

Esempi:

- un trattore stradale;
- un semirimorchio frigo;
- una motrice centinata;
- un rimorchio;
- un furgone;
- un muletto.

Contiene:

- `VehicleUnitId`;
- `FleetCode`;
- targa;
- VIN/telaio;
- `VehicleUnitType`;
- `VehicleBodyType`;
- `PowerSource`;
- `VehicleTechnicalSpecification`;
- `VehicleBodyProfile`;
- capacità;
- ruoli operativi;
- `CouplingProfile`;
- `VehicleStatus`;
- note.

## 5.4 `VehicleUnitId`

È l’identificatore tecnico della singola unità veicolo.

È diverso da:

- targa;
- telaio;
- codice flotta.

La targa può cambiare in certi contesti, il codice flotta è aziendale, il telaio è un dato reale del veicolo. L’ID è l’identità del dominio.

## 5.5 `FleetCode`

`FleetCode` è il codice interno aziendale del mezzo.

Esempi:

- `TRK-001`;
- `TRC-012`;
- `TRL-044`;
- `VAN-006`;
- `FRK-003`.

Serve per identificare il mezzo in modo leggibile dagli utenti aziendali.

## 5.6 `VehicleUnitType`

`VehicleUnitType` descrive la natura fisica dell’unità.

I valori sono:

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

Esempio:

- una cisterna non è un `VehicleUnitType`, è un allestimento;
- una bisarca non è un `VehicleUnitType`, è un allestimento;
- un frigo non è un `VehicleUnitType`, è un allestimento.

## 5.7 `VehicleBodyType`

`VehicleBodyType` descrive l’allestimento.

I valori sono:

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

## 5.8 `PowerSource`

`PowerSource` rappresenta l’alimentazione o la sorgente di energia.

I valori sono:

- `DIESEL`;
- `ELECTRIC`;
- `CNG`;
- `LNG`;
- `HYBRID`;
- `NONE`.

`NONE` è fondamentale per trailer e semirimorchi non motorizzati.

Regola importante: trailer e semirimorchi devono avere `PowerSource.NONE`.

## 5.9 `VehicleStatus`

`VehicleStatus` rappresenta lo stato tecnico/anagrafico del mezzo.

I valori sono:

- `ACTIVE`;
- `SUSPENDED`;
- `OUT_OF_SERVICE`;
- `DISMISSED`.

Non contiene stati come `AVAILABLE`, `BOOKED` o `IN_TRIP`, perché quelli appartengono alla pianificazione e alla disponibilità operativa.

## 5.10 `VehicleTechnicalSpecification`

`VehicleTechnicalSpecification` raccoglie la scheda tecnica generale del mezzo.

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

Non tutte le sezioni valgono per tutti i mezzi.

Esempi:

- un semirimorchio non ha motore;
- un trattore non ha vano di carico;
- un veicolo elettrico ha specifiche elettriche;
- un rimorchio ha assi, pneumatici, pesi e dimensioni.

### `VehicleDimensions`

Rappresenta dimensioni esterne:

- lunghezza;
- larghezza;
- altezza;
- passo.

### `VehicleLoadSpace`

Rappresenta lo spazio utile di carico:

- lunghezza interna;
- larghezza interna;
- altezza interna;
- volume;
- capacità pallet.

### `VehicleWeights`

Rappresenta i pesi tecnici:

- tara;
- massa massima;
- portata utile;
- massa massima del complesso;
- peso massimo rimorchiabile.

### `VehicleAxleSpecification` e `VehicleAxle`

La gestione degli assi è modellata con una lista di assi.

Ogni `VehicleAxle` contiene:

- numero asse;
- sterzante sì/no;
- sollevabile sì/no;
- gemellato sì/no;
- note.

Questa scelta è più realistica di un semplice numero totale di assi.

Permette di sapere:

- quale asse è sterzante;
- quale asse è sollevabile;
- quale asse è gemellato;
- in quale ordine sono montati gli assi.

### `VehicleTireSpecification`

Rappresenta informazioni generali sugli pneumatici:

- misura;
- tipo;
- numero ruote;
- informazione generale sul gemellato.

La verità tecnica sul gemellato resta comunque nella lista degli assi.

### `VehicleEngineSpecification`

Rappresenta i dati motore per mezzi motorizzati:

- potenza;
- coppia;
- cilindrata;
- classe emissioni;
- capacità AdBlue;
- capacità serbatoio;
- consumo medio.

### `VehicleTransmissionSpecification`

Descrive cambio e trasmissione:

- tipo cambio;
- numero marce;
- retarder;
- PTO.

### `VehicleChassisSpecification`

Descrive telaio e sicurezza:

- sospensioni;
- freni;
- ABS;
- EBS;
- ESP.

### `VehicleElectricSpecification`

Descrive i dati elettrici:

- capacità batteria;
- tipo ricarica;
- connettore;
- tempo di ricarica.

### `VehicleCabSpecification`

Descrive la cabina:

- tipo cabina;
- posti;
- cuccetta;
- infotainment;
- climatizzazione.

## 5.11 `VehicleBodyProfile`

`VehicleBodyProfile` rappresenta il profilo tecnico specifico dell’allestimento.

È stato scelto di non usare una mappa generica `Map<String, Object>`, perché in un dominio puro sarebbe troppo libera e poco controllata.

Invece il progetto usa profili specifici.

### Profili presenti

- `RefrigeratedBodyProfile`;
- `TankBodyProfile`;
- `CarCarrierBodyProfile`;
- `ContainerChassisBodyProfile`;
- `LowLoaderBodyProfile`;
- `CurtainsiderBodyProfile`;
- `TipperBodyProfile`;
- `SiloBodyProfile`;
- `LivestockBodyProfile`.

Ogni profilo deve essere coerente con il `VehicleBodyType` del mezzo.

Esempio: un veicolo con `bodyType = REFRIGERATED` deve avere un profilo refrigerato, non un profilo cisterna.

## 5.12 `VehicleCapability`

`VehicleCapability` descrive cosa il mezzo supporta tecnicamente.

Esempi:

- `ADR`;
- `ATP`;
- `REEFER_UNIT`;
- `TEMPERATURE_CONTROLLED`;
- `DUAL_TEMPERATURE`;
- `HYDRAULIC_TAIL_LIFT`;
- `CONTAINER_LOCKS`;
- `FOODGRADE_TANK`;
- `CHEMICAL_TANK`;
- `LOW_LOADER`;
- `EXCEPTIONAL_TRANSPORT`;
- `CAR_TRANSPORT`;
- `LIVESTOCK_TRANSPORT`.

La differenza è:

- `VehicleBodyType` = che allestimento ha;
- `VehicleBodyProfile` = dati tecnici dell’allestimento;
- `VehicleCapability` = cosa può supportare.

## 5.13 `VehicleOperationalRole`

`VehicleOperationalRole` descrive per quale tipo di lavoro il mezzo viene usato.

Esempi:

- `LINE_HAUL`;
- `DISTRIBUTION`;
- `LAST_MILE`;
- `GROUPAGE`;
- `FULL_LOAD`;
- `ADR_TRANSPORT`;
- `REFRIGERATED_TRANSPORT`;
- `CONTAINER_TRANSPORT`;
- `CAR_TRANSPORT`;
- `WAREHOUSE_SUPPORT`;
- `MAINTENANCE_SUPPORT`.

Non è una caratteristica fisica. È una classificazione d’uso.

## 5.14 `CouplingProfile` e `CouplingType`

`CouplingProfile` descrive se un mezzo può trainare, essere trainato o agganciarsi a un’altra unità.

Contiene:

- tipo di aggancio;
- possibilità di trainare;
- possibilità di essere trainato;
- peso massimo rimorchiabile;
- massa massima del complesso;
- note.

`CouplingType` contiene:

- `NONE`;
- `FIFTH_WHEEL`;
- `KINGPIN`;
- `DRAWBAR_HITCH`;
- `DRAWBAR`;
- `CENTER_AXLE_HITCH`;
- `SPECIAL_COUPLING`.

Esempi:

- trattore stradale: può trainare, usa ralla/fifth wheel;
- semirimorchio: può essere trainato, usa kingpin;
- motrice: può trainare un rimorchio tramite gancio;
- rimorchio: può essere trainato tramite timone.

Questa parte è fondamentale per gestire bilici e autotreni.

## 5.15 `VehicleCombination`

`VehicleCombination` rappresenta il complesso veicolare operativo.

Esempi:

- camion singolo;
- furgone singolo;
- trattore + semirimorchio;
- motrice + rimorchio;
- furgone + rimorchio;
- mezzo da magazzino.

Contiene:

- `VehicleCombinationId`;
- `VehicleCombinationType`;
- ID unità primaria;
- ID unità secondaria, quando presente;
- capacità combinate;
- ruoli operativi;
- stato;
- note.

Il progetto usa `VehicleUnitId` dentro `VehicleCombination`, non oggetti completi `VehicleUnit`. Questa scelta riduce l’accoppiamento tra aggregati.

La factory concettuale da unità serve a validare che la combinazione sia coerente.

## 5.16 `VehicleCombinationType`

I tipi di combinazione sono:

- `SINGLE_VEHICLE`;
- `ARTICULATED_VEHICLE`;
- `ROAD_TRAIN`;
- `VAN_WITH_TRAILER`;
- `SPECIAL_COMBINATION`;
- `WAREHOUSE_UNIT`.

Significato:

- `SINGLE_VEHICLE`: mezzo singolo;
- `ARTICULATED_VEHICLE`: trattore + semirimorchio;
- `ROAD_TRAIN`: motrice + rimorchio;
- `VAN_WITH_TRAILER`: furgone + rimorchio;
- `SPECIAL_COMBINATION`: combinazione speciale;
- `WAREHOUSE_UNIT`: mezzo operativo interno.

## 5.17 Regole di dominio principali

Regole su `VehicleUnit`:

- ID obbligatorio o generato;
- `FleetCode` obbligatorio;
- `unitType` obbligatorio;
- `bodyType` obbligatorio;
- `powerSource` obbligatorio;
- scheda tecnica obbligatoria;
- stato obbligatorio;
- note normalizzate;
- trailer e semitrailer devono avere `PowerSource.NONE`;
- un trattore deve avere `VehicleBodyType.NONE`;
- un trattore deve poter trainare;
- un semirimorchio deve poter essere trainato;
- un rimorchio deve poter essere trainato;
- il profilo allestimento deve corrispondere al body type.

Regole su `VehicleCombination`:

- `SINGLE_VEHICLE` non deve avere unità secondaria;
- trailer e semitrailer non possono essere usati come veicoli singoli;
- `ARTICULATED_VEHICLE` richiede `TRACTOR_UNIT` + `SEMI_TRAILER`;
- `ROAD_TRAIN` richiede `RIGID_TRUCK` + rimorchio;
- `VAN_WITH_TRAILER` richiede `VAN` + rimorchio compatibile;
- `WAREHOUSE_UNIT` richiede una sola unità di tipo `WAREHOUSE_EQUIPMENT`;
- `SPECIAL_COMBINATION` richiede unità tecnicamente compatibili.

## 5.18 Perché non salvare `totalTechnicalSpecification`

La scheda tecnica totale di una combinazione non viene salvata come campo principale.

È preferibile calcolarla quando serve, partendo dalle unità coinvolte.

Motivo: se la stessa informazione viene salvata sia sulle unità sia sulla combinazione, può nascere incoerenza.

Esempio:

- trattore = 8.000 kg;
- semirimorchio = 7.000 kg;
- totale salvato = 12.000 kg.

Quale valore è corretto?

Meglio calcolare i totali quando servono.

## 5.19 Caso specifico: “camion dove se disponibile metto un rimorchio”

Nel dominio veicoli puro si modella così:

- la motrice è un `VehicleUnit` di tipo `RIGID_TRUCK`;
- ha un `CouplingProfile` che permette il traino;
- il rimorchio è un `VehicleUnit` di tipo `DRAWBAR_TRAILER` o `CENTER_AXLE_TRAILER`;
- il rimorchio può essere trainato;
- la combinazione operativa è un `VehicleCombination` di tipo `ROAD_TRAIN`.

La disponibilità del rimorchio non appartiene ancora a `domain.vehicles`.

Appartiene a futuri moduli come:

- planning;
- dispatching;
- fleet availability;
- trip assignment.

## 5.20 Sintesi

Il dominio veicoli è stato modellato per distinguere chiaramente:

- unità singola;
- combinazione;
- natura fisica;
- allestimento;
- profilo tecnico dell’allestimento;
- scheda tecnica generale;
- assi e pneumatici;
- agganci;
- capacità;
- ruoli operativi;
- stato tecnico.

Questa struttura è più complessa di un semplice `Vehicle`, ma è necessaria per un gestionale trasporti realistico ed enterprise.
