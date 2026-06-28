# TruckFlow Manager — Glossary

## Scopo

Questo glossario raccoglie i termini principali del progetto.

Serve per evitare confusione tra concetti simili.

---

## AccountLink

Collegamento tra un account utente e una figura reale del sistema, come autista, cliente, dipendente o vettore esterno.

---

## ADR

Qualifica o requisito legato al trasporto di merci pericolose.  
Nel dominio viene modellata come requisito del carico e/o qualifica professionale dell’autista.

---

## Audit

Storico delle azioni importanti.  
Serve per sapere chi ha fatto cosa, quando e su quale oggetto.

---

## Billing

Area che riguarda fatture e pagamenti.

Differenza:

```text
pricing = preventivo e stima
billing = fattura e pagamento
```

---

## BodyType

Tipo di allestimento o carrozzeria del mezzo.

Esempi:

- telonato;
- frigo;
- cisterna;
- pianale;
- furgonato;
- ribaltabile.

---

## Cargo

Carico trasportato.

Può avere:

- peso;
- volume;
- dimensioni;
- pallet;
- colli;
- requisiti speciali.

---

## CargoSpace

Spazio fisico disponibile per il carico.

Comprende:

- lunghezza utile;
- larghezza utile;
- altezza utile;
- volume utile;
- numero pallet;
- tipo vano.

---

## Compliance

Area dei controlli di compatibilità e conformità.

Risponde alla domanda:

```text
Questa spedizione si può eseguire con questi dati?
```

---

## Customer

Cliente che richiede o paga un trasporto.

Può essere persona fisica o azienda.

---

## Dispatcher

Utente che pianifica missioni e assegna autisti/mezzi.

---

## Driver

Autista professionale.

Da non confondere con `UserAccount`.

`Driver` rappresenta la figura operativa.  
`UserAccount` rappresenta l’accesso al sistema.

---

## Fleet

Flotta aziendale.

Include:

- furgoni;
- camion;
- rimorchi;
- semirimorchi;
- combinazioni.

---

## LoadingResponsibility

Responsabilità del carico/scarico fisico.

Nel nostro progetto la regola standard è:

```text
NOT_PROVIDED_BY_CARRIER
```

L’azienda trasporta, ma non carica e non scarica fisicamente.

---

## MissionStop

Fermata all’interno di una missione operativa.

Può essere:

- ritiro;
- consegna;
- sosta;
- pausa;
- carburante;
- frontiera.

---

## ProofOfDelivery

Prova di consegna.

Può contenere:

- data consegna;
- nome ricevente;
- firma;
- note;
- danni;
- allegati.

---

## Regulation

Area che rappresenta regole esterne, divieti, permessi e restrizioni.

Le regole reali vanno verificate su fonti ufficiali al momento dell’implementazione.

---

## Shipment

Spedizione collegata al cliente e al carico.

È ciò che il cliente vuole trasportare.

---

## TransportMission

Viaggio operativo reale del mezzo.

Una missione può contenere una o più spedizioni.

---

## TransportOrder

Richiesta iniziale del cliente.

Non è ancora necessariamente una spedizione accettata.

---

## TransportQuote

Preventivo proposto al cliente.

---

## UserAccount

Account di accesso al sistema.

Contiene dati come username, email, ruoli e stato account.  
Non deve salvare password in chiaro.

---

## VehicleCombination

Combinazione di veicoli usata per una spedizione o missione.

Può essere:

- furgone singolo;
- camion rigido;
- camion + rimorchio;
- trattore + semirimorchio.

È il concetto corretto da collegare a `Shipment`, non `Truck`.

---

## VehicleLegalCategory

Categoria legale del veicolo.

Esempi previsti:

```text
N1
N2
N3
O1
O2
O3
O4
```

---

## Value Object

Oggetto senza identità propria, che conta per i suoi valori.

Esempi:

- `Money`;
- `Weight`;
- `Address`;
- `Dimension`.

---

## Entity

Oggetto con identità propria.

Esempi:

- `Customer`;
- `Driver`;
- `Shipment`;
- `VehicleCombination`.

---

## Enum

Insieme chiuso di valori.

Esempi:

- `ShipmentStatus`;
- `DriverStatus`;
- `VehicleStatus`.
