# Magazzino, manutenzione e materiali

Il magazzino serve a gestire materiali usati nella flotta:

- pastiglie freno
- filtri
- gomme
- olio
- AdBlue
- DPI
- attrezzature ADR
- catene
- pallet

## Movimenti

Un articolo può entrare o uscire per:

- acquisto
- consumo manutenzione
- trasferimento
- rettifica
- riserva

## Scorta minima

`InventoryRules.shouldReorder(...)` permette di capire quando un articolo scende sotto la soglia minima.
