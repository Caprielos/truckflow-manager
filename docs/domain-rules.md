# Regole domain principali

## Regole di purezza

- Le classi domain validano i propri dati in ingresso.
- I value object non devono accettare valori nulli, negativi o incoerenti quando non hanno senso.
- Le collezioni esposte devono essere copie immutabili o comunque protette.
- Le regole non devono chiamare database, API, filesystem o servizi esterni.

## Regole operative

### Cargo

Il cargo determina requisiti reali:

- ADR;
- temperatura controllata;
- ATP;
- rifiuti/FIR;
- animali vivi;
- alimentare/HACCP;
- liquidi/cisterna;
- carichi eccezionali;
- fissaggio carico.

### Driver

L'autista deve avere abilitazioni coerenti con la missione:

- patente corretta;
- CQC merci;
- ADR base/cisterna/classe speciale se richiesto;
- qualifiche operative;
- stato disponibile;
- limiti ore guida rispettati.

### Fleet

Il mezzo deve essere compatibile con:

- peso;
- volume;
- temperatura;
- tipo carrozzeria/allestimento;
- rimorchio/semirimorchio;
- certificati;
- combinazione legale.

### Economics

IVA, ricavi e costi non vanno confusi:

```text
IVA incassata dal cliente ≠ guadagno
IVA detraibile sugli acquisti ≠ costo reale
IVA non detraibile = costo contabile reale
ricavi - costi = margine
cassa negativa = esposizione/debito operativo
```

### Payroll

Il costo autista dipende da:

- ore guida;
- ore lavoro non guida;
- attesa;
- carico/scarico;
- notturno;
- festivo;
- trasferta;
- estero;
- ADR;
- patente/qualifica;
- tipo rimorchio/convoglio;
- tipo trasporto.

### Parking

Un posto può contenere una risorsa coerente:

- furgone;
- camion rigido;
- trattore stradale;
- rimorchio;
- semirimorchio;
- trattore + semirimorchio agganciati;
- autotreno;
- attrezzatura.

La readiness del parcheggio può dire se il mezzo è già pronto per partire.
