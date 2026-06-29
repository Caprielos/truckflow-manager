# Requisiti non funzionali

I requisiti non funzionali descrivono come l'app deve comportarsi.

## Manutenibilità

Il codice deve essere diviso in livelli:

```text
domain → application → infrastructure → web
```

Così le regole non dipendono da database o API.

## Testabilità

Ogni regola importante deve essere testabile con JUnit. Gli use case devono essere provabili con repository in memoria.

## Estendibilità

Nuovi tipi di cargo, costi, documenti o import devono essere aggiungibili senza riscrivere tutto.

## Tracciabilità

Azioni importanti devono poter essere auditabili.

## Sicurezza futura

La futura API dovrà avere ruoli e permessi, soprattutto per:

- costi;
- stipendi;
- fatture;
- cancellazione dati;
- gestione utenti.

## Affidabilità

Il sistema deve evitare stati incoerenti, per esempio:

- missione senza spedizione;
- parcheggio occupato due volte;
- merce ADR senza requisiti;
- costo senza importo;
- fattura senza righe.
