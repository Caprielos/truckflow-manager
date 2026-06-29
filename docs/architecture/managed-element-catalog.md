# Managed Element Catalog

## Obiettivo

Il `ManagedElementCatalog` è il catalogo completo degli elementi che il `compliance-deadline-service` deve conoscere.

Serve per evitare dimenticanze. Ogni elemento deve avere almeno uno slot di regola nel `deadline-rule-pack.yml`.

## Categorie principali

```text
VEHICLE_DOCUMENT
DRIVER_DOCUMENT
VEHICLE_TECHNICAL_COMPONENT
TRAILER_TECHNICAL_COMPONENT
CARGO_OPERATIONAL_ELEMENT
WAREHOUSE_OPERATIONAL_ELEMENT
TRIP_OPERATIONAL_ELEMENT
PHYSICAL_SECURITY_ELEMENT
TELEMATICS_MONITORING_ELEMENT
QUALITY_ELEMENT
```

## Documenti mezzo

| Elemento | Categoria | Fonte principale | Note |
|---|---|---|---|
| `VEHICLE_REGISTRATION_DOCUMENT` | VEHICLE_DOCUMENT | legge nazionale | libretto mezzo |
| `VEHICLE_INSURANCE` | VEHICLE_DOCUMENT | legge nazionale | assicurazione obbligatoria |
| `VEHICLE_ROADWORTHINESS_TEST` | VEHICLE_DOCUMENT | legge UE/nazionale | revisione mezzo |
| `TACHOGRAPH_DEVICE_CALIBRATION` | VEHICLE_DOCUMENT | legge UE/nazionale | cronotachigrafo |
| `VEHICLE_ATP_CERTIFICATION` | VEHICLE_DOCUMENT | ATP/tecnico | certificazione ATP |
| `VEHICLE_ADR_CERTIFICATION` | VEHICLE_DOCUMENT | ADR/nazionale | certificazione ADR |

## Documenti autista

| Elemento | Categoria | Fonte principale | Note |
|---|---|---|---|
| `DRIVER_LICENSE` | DRIVER_DOCUMENT | legge UE/nazionale | patente |
| `DRIVER_CQC` | DRIVER_DOCUMENT | legge UE/nazionale | CQC/formazione conducente |
| `DRIVER_TACHOGRAPH_CARD` | DRIVER_DOCUMENT | legge UE/nazionale | carta tachigrafica |
| `DRIVER_MEDICAL_CHECK` | DRIVER_DOCUMENT | legge nazionale/azienda | visite mediche |
| `DRIVER_MANDATORY_TRAINING` | DRIVER_DOCUMENT | legge/policy | corsi obbligatori |
| `DRIVER_ADR_CERTIFICATE` | DRIVER_DOCUMENT | ADR | certificato ADR autista |

## Componenti camion

| Elemento | Categoria | Fonte principale | Note |
|---|---|---|---|
| `VEHICLE_ENGINE_OIL` | VEHICLE_TECHNICAL_COMPONENT | costruttore | olio motore |
| `VEHICLE_FILTERS` | VEHICLE_TECHNICAL_COMPONENT | costruttore | aggregato filtri |
| `VEHICLE_AIR_FILTER` | VEHICLE_TECHNICAL_COMPONENT | costruttore | filtro aria |
| `VEHICLE_OIL_FILTER` | VEHICLE_TECHNICAL_COMPONENT | costruttore | filtro olio |
| `VEHICLE_FUEL_FILTER` | VEHICLE_TECHNICAL_COMPONENT | costruttore | filtro carburante |
| `VEHICLE_BRAKES` | VEHICLE_TECHNICAL_COMPONENT | costruttore/sicurezza | aggregato freni |
| `VEHICLE_BRAKE_PADS` | VEHICLE_TECHNICAL_COMPONENT | costruttore/sicurezza | pastiglie freno |
| `VEHICLE_BRAKE_DISCS` | VEHICLE_TECHNICAL_COMPONENT | costruttore/sicurezza | dischi freno |
| `VEHICLE_COOLANT` | VEHICLE_TECHNICAL_COMPONENT | costruttore | liquido refrigerante |
| `VEHICLE_ADBLUE_SYSTEM` | VEHICLE_TECHNICAL_COMPONENT | costruttore | sistema AdBlue |
| `VEHICLE_BELTS` | VEHICLE_TECHNICAL_COMPONENT | costruttore | cinghie |
| `VEHICLE_BATTERY` | VEHICLE_TECHNICAL_COMPONENT | costruttore | batteria |
| `VEHICLE_SUSPENSION` | VEHICLE_TECHNICAL_COMPONENT | costruttore/sicurezza | sospensioni |
| `VEHICLE_LIGHTS` | VEHICLE_TECHNICAL_COMPONENT | costruttore/sicurezza | luci |
| `VEHICLE_ENGINE_DIAGNOSTIC` | VEHICLE_TECHNICAL_COMPONENT | costruttore/telemetria | diagnostica motore |

## Componenti rimorchio

| Elemento | Categoria | Fonte principale | Note |
|---|---|---|---|
| `TRAILER_BRAKING_SYSTEM` | TRAILER_TECHNICAL_COMPONENT | costruttore/sicurezza | impianto frenante rimorchio |
| `TRAILER_ELECTRICAL_SYSTEM` | TRAILER_TECHNICAL_COMPONENT | costruttore/sicurezza | impianto elettrico rimorchio |
| `TRAILER_REFRIGERATION_UNIT` | TRAILER_TECHNICAL_COMPONENT | costruttore/ATP | impianto refrigerante |
| `TRAILER_BODY_FLOOR` | TRAILER_TECHNICAL_COMPONENT | costruttore/operativo | pianale |
| `TRAILER_DOORS_LOCKS` | TRAILER_TECHNICAL_COMPONENT | costruttore/sicurezza | porte e serrature |
| `TRAILER_FIFTH_WHEEL_COUPLING` | TRAILER_TECHNICAL_COMPONENT | costruttore/sicurezza | ralla/attacco |
| `TRAILER_TAIL_LIFT` | TRAILER_TECHNICAL_COMPONENT | costruttore/sicurezza | sponde idrauliche |
| `TRAILER_LANDING_GEAR` | TRAILER_TECHNICAL_COMPONENT | costruttore/sicurezza | piedini |

## Carico

| Elemento | Categoria | Fonte principale | Note |
|---|---|---|---|
| `CARGO_PALLET` | CARGO_OPERATIONAL_ELEMENT | operativo | pallet |
| `CARGO_CONTAINER` | CARGO_OPERATIONAL_ELEMENT | operativo/intermodale | container |
| `CARGO_PACKAGING` | CARGO_OPERATIONAL_ELEMENT | operativo/ADR/food | imballaggi |
| `CARGO_SEAL` | CARGO_OPERATIONAL_ELEMENT | sicurezza/operativo | sigilli |
| `CARGO_LABEL` | CARGO_OPERATIONAL_ELEMENT | operativo/ADR/food/waste | etichette |
| `CARGO_DECLARED_WEIGHT_CHECK` | CARGO_OPERATIONAL_ELEMENT | operativo/legale | peso reale vs dichiarato |

## Magazzino fisico

| Elemento | Categoria | Fonte principale | Note |
|---|---|---|---|
| `WAREHOUSE_LOCATION` | WAREHOUSE_OPERATIONAL_ELEMENT | operativo | ubicazioni |
| `WAREHOUSE_AISLE_RACK` | WAREHOUSE_OPERATIONAL_ELEMENT | sicurezza/tecnico | corsie e scaffali |
| `WAREHOUSE_COLD_CELL` | WAREHOUSE_OPERATIONAL_ELEMENT | operativo/food/tecnico | celle frigo |
| `WAREHOUSE_EQUIPMENT` | WAREHOUSE_OPERATIONAL_ELEMENT | sicurezza/tecnico | attrezzature |
| `WAREHOUSE_SAFETY_SYSTEM` | WAREHOUSE_OPERATIONAL_ELEMENT | sicurezza | impianti sicurezza |

## Viaggio reale

| Elemento | Categoria | Fonte principale | Note |
|---|---|---|---|
| `TRIP_POINT_OF_INTEREST` | TRIP_OPERATIONAL_ELEMENT | operativo | punti interesse |
| `TRIP_ROAD_RESTRICTION` | TRIP_OPERATIONAL_ELEMENT | legale/operativo | vincoli stradali |
| `TRIP_WEATHER_CONDITION` | TRIP_OPERATIONAL_ELEMENT | operativo/monitoraggio | condizioni meteo |
| `TRIP_TOLL` | TRIP_OPERATIONAL_ELEMENT | operativo/fiscale | pedaggi |
| `TRIP_ESTIMATED_VS_ACTUAL_TIME` | TRIP_OPERATIONAL_ELEMENT | operativo/SLA | tempi stimati vs reali |

## Sicurezza fisica

| Elemento | Categoria | Fonte principale | Note |
|---|---|---|---|
| `SECURITY_LOCK` | PHYSICAL_SECURITY_ELEMENT | policy/operativo | lucchetti |
| `SECURITY_ALARM` | PHYSICAL_SECURITY_ELEMENT | sicurezza/tecnico | allarmi |
| `SECURITY_INTRUSION_SENSOR` | PHYSICAL_SECURITY_ELEMENT | sicurezza/tecnico | sensori intrusione |
| `SECURITY_ONBOARD_CAMERA` | PHYSICAL_SECURITY_ELEMENT | sicurezza/privacy | telecamere bordo |

## Telemetria avanzata

| Elemento | Categoria | Fonte principale | Note |
|---|---|---|---|
| `TELEMATICS_CANBUS_SENSOR` | TELEMATICS_MONITORING_ELEMENT | tecnico/monitoraggio | sensori CANBUS |
| `TELEMATICS_DTC` | TELEMATICS_MONITORING_ELEMENT | tecnico/monitoraggio | errori motore DTC |
| `TELEMATICS_TEMPERATURE` | TELEMATICS_MONITORING_ELEMENT | tecnico/ATP/food | temperature |
| `TELEMATICS_TPMS` | TELEMATICS_MONITORING_ELEMENT | tecnico/sicurezza | pressione pneumatici |
| `TELEMATICS_FUEL_CONSUMPTION` | TELEMATICS_MONITORING_ELEMENT | tecnico/operativo | consumi reali |
| `TELEMATICS_DOOR_STATUS` | TELEMATICS_MONITORING_ELEMENT | sicurezza/monitoraggio | stato porte |
| `TELEMATICS_UNAUTHORIZED_OPENING` | TELEMATICS_MONITORING_ELEMENT | sicurezza | aperture non autorizzate |

## Regola di qualità

Ogni elemento elencato qui deve avere almeno uno slot nel rule pack.

Slot valido significa:

```text
regola compilata
oppure
regola vuota con status EMPTY_SLOT e fillableFromUi=true
```

Se manca lo slot, il test deve fallire.
