# TruckFlow Manager

> Documentazione aggiornata e allineata al domain MVP implementato.

Package root del domain:

```text
it.gabriele.truckflow.domain
```

Regola principale:

```text
Il domain rappresenta il business.
La tecnologia serve solo a farlo funzionare.
```


# Domain Rules

## Regole architetturali

1. Il domain non dipende da Spring.
2. Il domain non dipende dal database.
3. Il domain non dipende da JPA.
4. Il domain non dipende da REST/API.
5. Il domain non dipende da Google Maps, ViaMichelin, HERE o PTV.
6. Le integrazioni esterne stanno in infrastructure.
7. Le azioni applicative stanno in application layer.
8. Le regole di business stanno nel domain.
9. Le classi domain devono essere testabili con unit test semplici.
10. Gli oggetti domain devono nascere validi.

## Regole su ordini e spedizioni

1. `TransportOrder` rappresenta la richiesta cliente.
2. `Shipment` nasce solo da un ordine accettato.
3. Una spedizione non deve essere confusa con una missione.
4. Una spedizione terminale non torna indietro.
5. Un ordine rejected/cancelled non genera spedizione.

## Regole su missioni

1. `TransportMission` rappresenta il viaggio operativo reale.
2. Una missione richiede shipment, driver, vehicle combination e route plan.
3. Una missione può essere creata solo se compliant.
4. Completed e cancelled sono stati terminali.

## Regole su VehicleCombination

1. La shipment assegna `VehicleCombination`, non `Truck`.
2. Una combination ha una powered unit.
3. Un trailer è opzionale.
4. Il trailer deve essere davvero trailer.
5. La cargo unit è trailer se presente e cargo, altrimenti powered unit.
6. La combinazione deve rispettare peso, volume, dimensioni e temperatura.

## Regole su cargo

1. Un carico ha almeno un item.
2. Ogni item ha categoria, peso e dimensioni.
3. Carico refrigerato richiede mezzo compatibile.
4. Carico ADR richiede profilo dangerous goods.
5. ADR tank richiede mezzo/allestimento e certificato driver adeguato.
6. Oversized richiede allestimento adeguato.
7. Fragile può richiedere attenzione operativa.

## Regole su driver

1. Driver non è UserAccount.
2. Driver assegnabile deve essere available.
3. Camion rigido richiede C.
4. Combinazione con trailer/semitrailer richiede C + E.
5. Trasporto professionale merci richiede CQC.
6. ADR richiede ADR basic.
7. Cisterna ADR richiede ADR tank.
8. Esplosivi richiedono certificato ADR class 1.
9. Radioattivi richiedono certificato ADR class 7.
10. Trasporto refrigerato richiede qualifica operativa adeguata.
11. Trasporto internazionale richiede qualifica internazionale.

## Regole su route

1. Un route plan ha almeno due stop.
2. Il primo stop è START.
3. L'ultimo stop è END.
4. Deve esistere esattamente uno START e uno END.
5. Le sequence devono essere progressive.
6. Per essere operativo deve avere pickup e delivery.
7. Facility inattive rendono la route non usable.

## Regole su maintenance

1. Scheduled e in progress richiedono date range.
2. Safety inspection, repair critici, ADR tank inspection e breakdown possono bloccare disponibilità mezzo.
3. Tire replacement richiede servizio pneumatici.
4. ADR tank inspection richiede specialista ADR.

## Regole su pricing

1. `pricing` è preventivo/stima; `billing` è fattura/pagamento.
2. Le righe prezzo devono avere codici unici.
3. Le righe del breakdown devono avere stessa valuta.
4. Gli sconti vengono sottratti.
5. Il totale non può diventare negativo.
6. Provider esterni possono essere indicati come source, ma non chiamati dal domain.

## Regole su billing

1. Invoice draft può diventare issued.
2. Invoice issued può diventare paid.
3. Draft e issued possono essere cancelled.
4. Paid e cancelled sono terminali.
5. PaymentRecord deve riferirsi alla invoice corretta.
6. I pagamenti devono coprire il totale per poter considerare invoice pagata.

## Regole documentali

1. Documento draft può essere requested.
2. Draft/requested può essere received.
3. Received può essere verified.
4. Requested/received può essere rejected.
5. Verified con expirationDate può diventare expired.
6. Documenti expirable received/verified richiedono expirationDate.
7. ADR shipment deve avere documentazione ADR.

## Regole claim

1. Claim open può andare under review.
2. Under review può essere accepted o rejected.
3. Accepted può essere settled.
4. Terminale richiede closedDate.
5. Accepted/settled richiede acceptedCompensation.
6. Accepted compensation non può superare requested compensation.
7. High/critical richiedono review urgente.

## Regole audit

1. Ogni evento audit indica actor, action, aggregate e timestamp.
2. AuditTrail contiene eventi dello stesso aggregate.
3. EventId deve essere unico nel trail.
4. Eventi security/financial possono richiedere review.

## Regole notification

1. Scheduled richiede scheduledAt.
2. Sent richiede sentAt.
3. sentAt non può essere prima di scheduledAt.
4. Sent, failed, cancelled sono terminali.
5. Security notification richiede attenzione.

## Regole identity

1. Solo account ACTIVE può fare login.
2. UserAccount è separato da Driver e Customer.
3. Admin può gestire utenti/config/audit.
4. Permessi sensitive richiedono strong authentication.
5. Deleted è terminale.

## Regole configuration

1. Global non richiede scope reference.
2. Customer/facility/user/organization richiedono scope reference.
3. Security e integration sono sensitive.
4. Key con PASSWORD, SECRET, TOKEN o API_KEY è sensitive.
5. Override valido solo da global attiva a scoped attiva con stessa key/category/type.

## Regole reporting

1. Draft report non ha metriche.
2. Generated/published/archived richiedono metriche e generatedAt.
3. MetricCode unici.
4. Financial/compliance report richiedono restricted access.
5. Metriche non possono avere valore negativo.
