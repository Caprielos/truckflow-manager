# Glossario

## ADR
Accordo europeo relativo al trasporto internazionale di merci pericolose su strada. Nel progetto compare tramite classi ADR cargo, certificati driver, documenti e certificati veicolo.

## ATP
Accordo relativo ai trasporti internazionali di derrate deperibili e ai mezzi speciali da usare. Nel progetto è modellato come certificato veicolo e requisito per merci a temperatura controllata.

## CMR
Lettera di vettura internazionale. È un documento richiesto per trasporti internazionali.

## CQC
Carta di qualificazione del conducente. Nel progetto è una qualifica professionale/certificato driver.

## Cargo
Merce trasportata. Può essere generica, pallettizzata, refrigerata, ADR, rifiuto, animale vivo, liquido alimentare, bulk, oversized, ecc.

## Carrier / Transport Company
Azienda di trasporto. Nel progetto è `TransportCompany`, con licenze operative.

## Convoglio
Combinazione operativa di uno o più veicoli: mezzo singolo, autotreno, articolato.

## Domain layer
Strato del software che contiene regole e concetti di business puri.

## Facility
Sede fisica: magazzino, deposito, cliente, terminal, porto, aeroporto o centro manutenzione.

## FIR
Formulario identificazione rifiuto. Nel progetto è rappresentato dai documenti per rifiuti.

## Missione
Esecuzione reale del trasporto: spedizione + rotta + autista + convoglio.

## Shipment
Spedizione nata da un ordine accettato. Non è ancora la missione reale.

## Telematics
Dati GPS/CAN-bus del mezzo: posizione, odometro, fuel level, velocità, eventi guida.

## Tire
Gomma fisica tracciabile, distinta dalla specifica pneumatico del veicolo.

## VehicleTechnicalSpecification
Scheda tecnica realistica del veicolo: masse, dimensioni, assi, agganci, body configuration e certificati.
