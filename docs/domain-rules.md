# Domain Rules

## Regole generali

1. Il domain deve rimanere puro Java.
2. Le regole di business devono stare nel domain.
3. Database, API, file, Spring, JPA e web non devono entrare nel domain.
4. Le Entity hanno identità.
5. I Value Object sono immutabili e validano i dati.
6. Le Rules sono classi statiche senza stato.
7. Le enum rappresentano scelte chiuse, non dati tecnici variabili.
8. I dati tecnici reali devono stare in value object o entity, non in enum enormi.

## Regola su Vehicle

Il mezzo non deve essere descritto con una sola macro-categoria.

Modello corretto:

```text
Vehicle
├── VehicleUnitType / VehicleType
├── VehicleStatus
├── VehicleTechnicalSpecification
├── VehicleBodyConfiguration
├── VehicleCertificate
└── Notes
```

`VehicleType` rimane come compatibilità/ponte verso il modello storico, ma il modello realistico usa `VehicleUnitType`, `VehicleBodyConfiguration` e `VehicleTechnicalSpecification`.

## Regola su Cargo

La merce guida il sistema.

Esempi:

- ADR richiede profilo ADR, autista ADR e mezzo/cisterna idonea.
- Frigo/isotermico richiede temperatura, ATP e allestimento compatibile.
- Rifiuti richiedono EER/CER, FIR e licenza aziendale.
- Animali vivi richiedono idoneità conducente e documentazione veterinaria.
- Merci pallettizzate usano capacità EPAL.
- Merci sfuse usano volume e allestimenti come ribaltabile, silo o walking floor.

## Regola su Driver

La patente dipende da peso e combinazione.

Le abilitazioni dipendono da trasporto e operazioni:

- CQC per guida professionale merci.
- ADR per merci pericolose.
- Gru, PLE, muletto, macchine movimento terra per operazioni specifiche.
- Animali vivi per trasporto bestiame.
- Temperatura controllata per frigo/farmaceutico.

## Regola su Company

L’azienda deve essere autorizzata al tipo di trasporto.

Esempi:

- internazionale UE sopra soglie previste → licenza comunitaria;
- rifiuti → Albo Gestori Ambientali;
- conto proprio → licenza conto proprio;
- autotrasporto conto terzi → Albo + REN.

## Regola su Documents

I documenti dipendono dalla missione.

Esempi:

- CMR per estero;
- FIR per rifiuti;
- SDS/Tremcards per ADR;
- HACCP/sanificazione per alimentare;
- veterinari per animali vivi;
- autorizzazione per trasporto eccezionale.

## Regola su Fleet Operations

Manutenzione, gomme, carburante e telematica non devono gonfiare `Vehicle`.

Sono moduli collegati al mezzo:

```text
Vehicle -> Maintenance
Vehicle -> Tire
Vehicle -> Fuel
Vehicle -> Telematics
Vehicle -> Claim
```
