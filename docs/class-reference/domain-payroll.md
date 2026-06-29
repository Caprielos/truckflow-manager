# Domain `payroll` spiegato

Costo autista: ore, straordinari, trasferte, ADR, CE, notturno, festivo e supplementi.

## Classi principali

### `DriverMissionPayLine`

Tipo: `class`.

Classe legata all’autista, alle sue abilitazioni o al suo costo operativo.

Campi principali:

- `MAX_CODE_LENGTH`
- `MAX_DESCRIPTION_LENGTH`
- `lineCode`
- `componentType`
- `unit`
- `description`
- `quantity`
- `amount`
- `notes`

Metodi pubblici principali:

- `of()`
- `getLineCode()`
- `getComponentType()`
- `getUnit()`
- `getDescription()`
- `getQuantity()`
- `getAmount()`
- `getNotes()`
- `isAllowanceOrReimbursement()`
- `isEmployerCost()`
- `equals()`
- `hashCode()`

### `DriverMissionPayroll`

Tipo: `class`.

Classe legata all’autista, alle sue abilitazioni o al suo costo operativo.

Campi principali:

- `MAX_CODE_LENGTH`
- `payrollCode`
- `missionNumber`
- `driverCode`
- `payrollPolicyCode`
- `payLines`
- `notes`
- `currency`
- `reference`

Metodi pubblici principali:

- `of()`
- `calculateTotalEmployerCost()`
- `calculateAllowancesAndReimbursements()`
- `calculateEmployerCharges()`
- `containsComponent()`
- `toMissionCostLine()`
- `getPayrollCode()`
- `getMissionNumber()`
- `getDriverCode()`
- `getPayrollPolicyCode()`
- `getPayLines()`
- `getNotes()`

### `DriverMissionWorkReport`

Tipo: `class`.

Classe legata all’autista, alle sue abilitazioni o al suo costo operativo.

Campi principali:

- `MAX_CODE_LENGTH`
- `reportCode`
- `missionNumber`
- `driver`
- `drivingTime`
- `otherWorkTime`
- `waitingTime`
- `loadingUnloadingTime`
- `overtime`
- `nightWorkTime`
- `sundayWorkTime`
- `holidayWorkTime`

Metodi pubblici principali:

- `builder()`
- `getDrivingHours()`
- `getOtherWorkHours()`
- `getWaitingHours()`
- `getLoadingUnloadingHours()`
- `getOvertimeHours()`
- `getNightWorkHours()`
- `getSundayWorkHours()`
- `getHolidayWorkHours()`
- `hasCargoCategory()`
- `transportsAdrCargo()`
- `transportsWaste()`

### `DriverPayComponentType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Metodi pubblici principali:

- `isAllowanceOrReimbursement()`
- `isRiskOrQualificationPremium()`
- `isEmployerCost()`

### `DriverPayRule`

Tipo: `class`.

Classe legata all’autista, alle sue abilitazioni o al suo costo operativo.

Campi principali:

- `MAX_CODE_LENGTH`
- `MAX_DESCRIPTION_LENGTH`
- `ruleCode`
- `componentType`
- `unit`
- `description`
- `amount`
- `percentage`
- `notes`

Metodi pubblici principali:

- `amount()`
- `percentageOfBase()`
- `calculateLine()`
- `getRuleCode()`
- `getComponentType()`
- `getUnit()`
- `getDescription()`
- `getAmount()`
- `getPercentage()`
- `getNotes()`
- `equals()`
- `hashCode()`

### `DriverPayUnit`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Metodi pubblici principali:

- `usesWorkedQuantity()`
- `usesPercentage()`

### `DriverPayrollPolicy`

Tipo: `class`.

Classe legata all’autista, alle sue abilitazioni o al suo costo operativo.

Campi principali:

- `MAX_CODE_LENGTH`
- `MAX_DESCRIPTION_LENGTH`
- `policyCode`
- `description`
- `validFrom`
- `validTo`
- `rules`
- `notes`

Metodi pubblici principali:

- `of()`
- `isValidOn()`
- `findRule()`
- `hasRule()`
- `getPolicyCode()`
- `getDescription()`
- `getValidFrom()`
- `getValidTo()`
- `getRules()`
- `getNotes()`
- `equals()`
- `hashCode()`

### `DriverPayrollRules`

Tipo: `class`.

Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

Campi principali:

- `baseLines`

Metodi pubblici principali:

- `calculateMissionPayroll()`
- `missionHasDriverCost()`

### `MissionPayrollProjection`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `payroll`
- `missionCostLine`

Metodi pubblici principali:

- `fromPayroll()`
- `getPayroll()`
- `getMissionCostLine()`
- `equals()`
- `hashCode()`
