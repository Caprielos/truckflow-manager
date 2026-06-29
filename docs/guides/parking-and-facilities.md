# Parcheggi, depositi e strutture

Il progetto distingue:

```text
facility
→ struttura fisica: deposito, magazzino, piazzale

parking
→ posti parcheggio e risorse parcheggiate
```

## Facility

Una struttura può essere:

```text
di proprietà
affittata
in leasing
piazzale terzi
```

Può avere costi come:

```text
affitto
tasse proprietà
utenze
sicurezza
manutenzione piazzale
assicurazione
videosorveglianza
```

## Parking

Un parcheggio può contenere:

```text
furgone
camion rigido
trattore
rimorchio
semirimorchio
trattore + semirimorchio agganciati
autotreno
attrezzatura
```

La classe `ParkedResource` rappresenta cosa è parcheggiato.

La classe `ParkingAssignment` rappresenta l'assegnazione di una risorsa a un posto.
