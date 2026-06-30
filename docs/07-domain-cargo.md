# 7. Package `domain.cargo`

## 7.1 Scopo del dominio cargo

Il package `domain.cargo` rappresenta la merce gestita da TruckFlow Manager.

Il suo scopo è descrivere la merce come concetto di dominio puro, senza collegarla direttamente a viaggi, mezzi, autisti, magazzini, documenti o pianificazione.

Il dominio cargo risponde alla domanda:

```text
Che tipo di merce devo trasportare e quali requisiti ha?
```

Esempi di cargo sono:

- merce generica pallettizzata;
- prodotti alimentari freschi;
- farmaci a temperatura controllata;
- merci ADR;
- rifiuti;
- liquidi in cisterna;
- container;
- auto da trasportare con bisarca;
- animali vivi;
- merci fragili o di alto valore.

## 7.2 Cosa non contiene il dominio cargo

Il dominio cargo non contiene logiche operative.

Quindi non gestisce:

- assegnazione del cargo a un veicolo;
- assegnazione a un viaggio;
- disponibilità del mezzo;
- pianificazione;
- magazzino;
- tracking;
- documenti di trasporto;
- stato della spedizione;
- consegna;
- danni, sinistri o reclami.

Questi concetti verranno modellati più avanti in altri bounded context, come `shipments`, `planning`, `dispatching`, `warehouse`, `documents`, `tracking` o `claims`.

## 7.3 Struttura del package

La struttura introdotta è:

```text
domain.cargo
├─ CargoUnit
├─ CargoId
├─ CargoCode
├─ CargoType
├─ CargoCategory
├─ CargoStatus
│
├─ CargoDimensions
├─ CargoWeights
├─ CargoPackaging
├─ CargoPackagingType
├─ CargoTemperature
├─ CargoHazard
├─ CargoRegulatory
├─ CargoProperties
│
├─ CargoCompatibilityRequirement
├─ CargoTransportRequirement
└─ CargoValidation
```

`CargoValidation` è una classe interna di supporto usata solo per mantenere coerenti le validazioni del package.

## 7.4 `CargoUnit`

`CargoUnit` rappresenta la scheda tecnica della merce.

Non rappresenta una spedizione specifica, ma il tipo di merce che l'azienda può gestire.

Contiene:

- identificativo tecnico (`CargoId`);
- codice aziendale leggibile (`CargoCode`);
- nome e descrizione;
- tipo principale della merce (`CargoType`);
- categorie logistiche multiple (`Set<CargoCategory>`);
- dimensioni;
- pesi;
- imballaggio;
- temperatura richiesta;
- pericolosità;
- requisiti normativi;
- proprietà generali;
- requisiti di compatibilità al trasporto;
- stato anagrafico;
- note.

La scelta importante è separare:

```text
CargoType = che merce è
CargoCategory = come viene classificata logisticamente
CargoCompatibilityRequirement = quali requisiti impone al trasporto
```

## 7.5 `CargoId` e `CargoCode`

`CargoId` è l'identificatore tecnico del dominio, pensato per il sistema.

`CargoCode` è invece un codice aziendale leggibile dagli utenti.

Esempi:

```text
CGO-001
FOOD-023
ADR-120
PHARMA-004
AUTO-010
WASTE-044
```

Questa scelta è coerente con altri codici interni del progetto, come `OperationalCode` e `FleetCode`.

## 7.6 `CargoType`

`CargoType` indica il tipo principale della merce.

Esempi:

- `GENERAL_GOODS`;
- `FOOD`;
- `PHARMACEUTICAL`;
- `CHEMICAL`;
- `FUEL`;
- `LIVESTOCK`;
- `WASTE`;
- `AUTOMOTIVE`;
- `CONSTRUCTION_MATERIAL`;
- `INDUSTRIAL_GOODS`;
- `OTHER`.

Non contiene concetti come `PALLETIZED` o `CONTAINERIZED`, perché quelli non descrivono la natura della merce, ma il modo in cui la merce viene gestita o confezionata.

## 7.7 `CargoCategory`

`CargoCategory` classifica la merce dal punto di vista logistico.

Una merce può avere più categorie contemporaneamente.

Per questo `CargoUnit` usa:

```text
Set<CargoCategory>
```

Esempi:

```text
Prodotto chimico liquido ADR:
- CHEMICAL come CargoType
- ADR, LIQUID come CargoCategory
```

```text
Farmaci refrigerati su pallet:
- PHARMACEUTICAL come CargoType
- REFRIGERATED, HIGH_VALUE, PALLETIZED come CargoCategory
```

Questa scelta evita di forzare la merce dentro una sola categoria e rende il modello più realistico.

## 7.8 `CargoStatus`

`CargoStatus` è uno stato anagrafico, non operativo.

Gli stati sono:

- `ACTIVE`;
- `SUSPENDED`;
- `ARCHIVED`;
- `DISCONTINUED`.

Non sono stati del cargo:

- `IN_TRANSIT`;
- `DELIVERED`;
- `DAMAGED`;
- `CANCELLED`.

Questi ultimi appartengono a spedizioni, consegne, incidenti o reclami, non alla scheda tecnica della merce.

## 7.9 Dimensioni, pesi e imballaggio

`CargoDimensions` descrive l'ingombro fisico della merce:

- lunghezza;
- larghezza;
- altezza;
- volume.

`CargoWeights` descrive:

- peso lordo;
- peso netto;
- tara.

`CargoPackaging` descrive il modo in cui la merce è confezionata o movimentata.

Usa `CargoPackagingType` per evitare stringhe libere troppo generiche.

Esempi di packaging:

- pallet;
- scatola;
- cartone;
- container da 20 piedi;
- container da 40 piedi;
- big bag;
- IBC;
- fusto;
- tank container;
- gabbia;
- merce sfusa.

## 7.10 Temperatura, pericolosità e normative

`CargoTemperature` descrive eventuali limiti di temperatura richiesti dalla merce.

Esempi:

- fresco alimentare: +2°C / +4°C;
- surgelato: -18°C;
- farmaceutico: +15°C / +25°C.

`CargoHazard` descrive la pericolosità, soprattutto per merci ADR:

- classe ADR;
- numero UN;
- packing group;
- disposizioni speciali.

`CargoRegulatory` descrive requisiti normativi richiesti dalla merce:

- ADR;
- ATP;
- food grade;
- pharma grade;
- autorizzazione rifiuti;
- autorizzazione animali vivi.

Queste classi descrivono i requisiti, ma non eseguono la compliance completa.

## 7.11 `CargoProperties`

`CargoProperties` raccoglie proprietà trasversali della merce:

- fragile;
- deperibile;
- pericolosa;
- alto valore;
- richiede separazione.

La proprietà `stackable` non è stata inserita qui, perché appartiene meglio a `CargoPackaging`: la sovrapponibilità dipende spesso dall'imballaggio e dalla movimentazione.

## 7.12 `CargoCompatibilityRequirement`

`CargoCompatibilityRequirement` è il collegamento concettuale tra cargo e futuro controllo di compatibilità.

Non importa direttamente il dominio veicoli e non copia le capacità dei veicoli.

Descrive solo cosa richiede la merce.

Contiene:

- un insieme di `CargoTransportRequirement`;
- payload minimo richiesto;
- volume minimo richiesto;
- lunghezza interna minima;
- larghezza interna minima;
- altezza interna minima;
- note.

Questa scelta è più scalabile rispetto a tanti booleani come `requiresADR`, `requiresRefrigerated`, `requiresContainerChassis`, ecc.

## 7.13 `CargoTransportRequirement`

`CargoTransportRequirement` è il linguaggio del cargo per dichiarare i propri requisiti di trasporto.

Esempi:

- `REFRIGERATED_VEHICLE_REQUIRED`;
- `FROZEN_TRANSPORT_REQUIRED`;
- `TEMPERATURE_CONTROL_REQUIRED`;
- `ADR_VEHICLE_REQUIRED`;
- `ATP_CERTIFICATION_REQUIRED`;
- `FOOD_GRADE_BODY_REQUIRED`;
- `PHARMA_GRADE_REQUIRED`;
- `WASTE_AUTHORIZATION_REQUIRED`;
- `LIVESTOCK_BODY_REQUIRED`;
- `BULK_BODY_REQUIRED`;
- `TANK_BODY_REQUIRED`;
- `CONTAINER_CHASSIS_REQUIRED`;
- `CAR_CARRIER_REQUIRED`;
- `LOW_LOADER_REQUIRED`;
- `SIDE_LOADING_REQUIRED`;
- `REAR_LOADING_REQUIRED`;
- `SEPARATION_REQUIRED`.

Il cargo quindi dichiara requisiti propri, mentre il dominio veicoli dichiara capacità e caratteristiche dei mezzi.

## 7.14 Relazione con `domain.vehicles`

La relazione concettuale è:

```text
Cargo dichiara requisiti.
Vehicles dichiara capacità.
Planning/Dispatching verifica la compatibilità.
```

Esempio:

```text
Cargo frigo:
- richiede temperatura controllata
- richiede ATP

Vehicle frigo:
- ha bodyType REFRIGERATED
- ha capability ATP
- ha reefer unit

Planning:
- verifica se quel mezzo può caricare quel cargo
```

Questa separazione evita dipendenze dirette e mantiene il dominio cargo puro.

## 7.15 Invarianti principali

Le principali invarianti sono:

- `CargoId` obbligatorio, generato se non fornito;
- `CargoCode` obbligatorio;
- nome obbligatorio;
- `CargoType` obbligatorio;
- almeno una `CargoCategory` obbligatoria;
- `CargoStatus` obbligatorio;
- pesi non negativi;
- peso netto non maggiore del peso lordo;
- tara non maggiore del peso lordo;
- temperatura minima non maggiore della temperatura massima;
- se la temperatura è controllata, deve essere presente almeno un limite;
- cargo ADR deve dichiarare il requisito `ADR_VEHICLE_REQUIRED`;
- cargo ATP deve dichiarare il requisito `ATP_CERTIFICATION_REQUIRED`;
- cargo a temperatura controllata deve dichiarare `TEMPERATURE_CONTROL_REQUIRED`;
- cargo che richiede separazione deve dichiarare `SEPARATION_REQUIRED`.

## 7.16 Sintesi

Il dominio cargo è stato modellato come dominio puro e separato.

Non decide quale veicolo usare, non assegna viaggi e non gestisce spedizioni.

Descrive invece la merce e i suoi requisiti.

Questa scelta rende TruckFlow più pulito, più scalabile e coerente con il dominio veicoli già introdotto.
