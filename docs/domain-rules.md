# Domain rules

Le regole di business sono concentrate in classi `*Rules`.

## Perché usare classi Rules

Le entity devono rappresentare oggetti del dominio e proteggere la propria coerenza interna. Le regole che incrociano più oggetti stanno invece in classi dedicate.

Esempio:

```text
Driver + VehicleCombination + CargoLoad
```

non è responsabilità solo di `Driver`, solo di `Vehicle` o solo di `CargoLoad`. È una regola trasversale, quindi sta in `DriverRules`, `VehicleBodyCompatibilityRules` o `ComplianceRules`.

## Esempi principali

### CargoLoadRules

Controlla peso, volume, temperatura, ADR, dimensioni e categorie del carico.

### CargoOperationalRules

Deriva documenti e certificati richiesti da una categoria merce.

### VehicleBodyCompatibilityRules

Verifica se un carico può viaggiare su un allestimento/convoglio.

### VehicleCombinationRules

Controlla se una combinazione veicolare è assegnabile e coerente.

### VehicleCombinationTechnicalRules

Calcola masse, tara, portata, traino e limiti tecnici/legali del convoglio.

### DriverRules

Controlla patente, CQC, ADR e qualifiche operative dell’autista.

### ComplianceRules

Coordina controlli più generali tra spedizione, convoglio e autista.

### DocumentRules

Gestisce stato, verifica e validità dei documenti.

### MaintenanceRules

Gestisce stati e validità degli interventi di manutenzione.

## Regola importante

Nel domain evitiamo di hardcodare limiti normativi complessi quando possono cambiare per paese o contesto.

Esempio: invece di scrivere ovunque `44000 kg`, usiamo profili configurabili come `VehicleCombinationLegalLimitProfile`.
