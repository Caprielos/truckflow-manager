# Flusso business reale

## 1. Cliente e ordine

Un cliente richiede un servizio di trasporto. L'ordine contiene servizio, cliente, origine, destinazione, merce e condizioni commerciali.

Package coinvolti:

```text
customer
order
contract
pricing
```

## 2. Spedizione

Dall'ordine accettato nasce una spedizione. La spedizione descrive cosa va trasportato e quali requisiti richiede.

Package coinvolti:

```text
shipment
cargo
document
compliance
```

## 3. Ufficio traffico

Il dispatch valuta candidati reali:

- autista disponibile e qualificato;
- veicolo compatibile;
- rimorchio o semirimorchio compatibile;
- convoglio già parcheggiato e pronto;
- documenti presenti;
- margine economico previsto;
- vincoli ore guida;
- requisiti cargo.

Package coinvolti:

```text
dispatch
driver
fleet
parking
availability
drivetime
economics
```

## 4. Missione operativa

Quando le risorse sono scelte, nasce la missione reale. La missione non è solo la spedizione: è il viaggio concreto con assegnazioni, stato operativo e route plan.

Package coinvolti:

```text
operation
route
tracking
loadsecurity
document
```

## 5. Esecuzione e controllo

Durante la missione possono comparire:

- eventi tracking;
- anomalie telematiche;
- rifornimenti;
- danni;
- difetti mezzo;
- consumi;
- documenti firmati;
- tempi attesa/carico/scarico.

Package coinvolti:

```text
tracking
telematics
fuel
claim
maintenance
document
payroll
```

## 6. Chiusura economica

Alla fine si controlla:

- prezzo fatturato al cliente;
- costi missione;
- costo autista;
- carburante;
- pedaggi;
- quota gomme/manutenzione;
- quota assicurazione;
- IVA vendite/acquisti;
- margine;
- utile o perdita;
- cassa negativa/debito.

Package coinvolti:

```text
pricing
billing
economics
payroll
fuel
maintenance
tire
facility
```
