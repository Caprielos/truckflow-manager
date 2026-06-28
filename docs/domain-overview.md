# Domain overview

Il domain di TruckFlow Manager è costruito intorno a una domanda:

> Questo trasporto può essere fatto in modo valido, sicuro, documentato e redditizio?

Per rispondere, il dominio incrocia diversi blocchi.

## 1. Merce

La merce determina molte regole:

- allestimento compatibile;
- temperatura richiesta;
- ADR;
- documenti;
- fissaggio;
- certificati veicolo;
- abilitazioni autista;
- costi extra.

Esempi:

```text
REFRIGERATED_FOOD -> frigo/isotermico + ATP + temperatura
DANGEROUS_GOODS -> numero ONU + ADR autista + ADR veicolo
WASTE_DANGEROUS -> EER/CER + FIR + licenza aziendale
LIVESTOCK -> documenti veterinari + abilitazione animali vivi
```

## 2. Veicolo

Un veicolo non è descritto da una sola categoria. Il modello separa:

```text
VehicleUnitType
VehicleBodyBaseType
VehicleBodyConfiguration
VehicleTechnicalSpecification
VehicleCertificate
```

Questo permette di rappresentare casi realistici:

```text
trattore stradale + gru
semirimorchio centinato mega
rimorchio ribaltabile trilaterale
furgonato frigo con sponda
cisterna ADR
portacontainer con twist-lock
```

## 3. Convoglio

Il singolo mezzo è `Vehicle`.
Il mezzo operativo può essere una combinazione `VehicleCombination`:

```text
SINGLE_VEHICLE
TRUCK_AND_TRAILER
ARTICULATED_VEHICLE
```

Esempi:

```text
furgone singolo
motrice/autocarro singolo
autotreno = autocarro + rimorchio
bilico = trattore stradale + semirimorchio
```

## 4. Autista

L’autista deve avere:

- stato assegnabile;
- patente corretta;
- CQC merci;
- ADR se richiesto;
- patentini operativi se richiesti.

## 5. Azienda

Alcuni trasporti richiedono licenze aziendali:

- albo autotrasportatori;
- REN;
- licenza comunitaria;
- conto proprio;
- albo gestori ambientali.

## 6. Missione

La missione collega tutto:

```text
ordine accettato
spedizione
carico
convoglio
autista
rotta
documenti
tracking
costi
```

## 7. Moduli operativi

Il domain include già concetti per:

- manutenzione;
- pneumatici;
- carburante;
- telematica;
- fissaggio carico;
- sinistri/reclami;
- sostenibilità;
- pricing;
- fatturazione.
