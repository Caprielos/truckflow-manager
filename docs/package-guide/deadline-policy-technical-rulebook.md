# Regole tecniche di scadenza per camion e rimorchi

Il dominio delle scadenze tecniche è diviso in due parti:

- `ManufacturerTechnicalDeadlineCatalog`: punto di ingresso usato dagli use case.
- `ConfigurableTechnicalDeadlineRuleBook`: classe da aggiornare quando si vogliono inserire o modificare intervalli tecnici per costruttore, modello, camion, rimorchio o componente.

La classe configurabile copre già le aree principali:

- olio motore
- filtri aria, olio e carburante
- pastiglie e dischi freno
- liquido refrigerante
- sistema AdBlue
- cinghie
- batteria
- sospensioni
- luci
- diagnostica motore
- impianto frenante rimorchio
- impianto elettrico rimorchio
- impianto refrigerante rimorchio
- pianale/cassone
- porte e serrature
- ralla, perno e accoppiamento
- piedini/attacchi
- sponde idrauliche

I valori presenti sono una configurazione iniziale personalizzabile. Non sono valori legali o tecnici ufficiali: quando si hanno manuale costruttore, scheda tecnica o contratto di manutenzione, si aggiornano gli intervalli nella rule book.
