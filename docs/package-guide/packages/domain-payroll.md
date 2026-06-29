# `domain/payroll`

Costo autista: ore, straordinari, trasferte, ADR, CE, notturno, festivo e supplementi.

## Come leggerlo

Questo package contiene concetti o regole del business. Non dovrebbe dipendere da database, web o infrastructure.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `DriverMissionPayLine` | class | Classe legata all’autista, alle sue abilitazioni o al suo costo operativo. | MAX_CODE_LENGTH, MAX_DESCRIPTION_LENGTH, lineCode, componentType, unit, description, quantity, amount | of, getLineCode, getComponentType, getUnit, getDescription, getQuantity, getAmount, getNotes |
| `DriverMissionPayroll` | class | Classe legata all’autista, alle sue abilitazioni o al suo costo operativo. | MAX_CODE_LENGTH, payrollCode, missionNumber, driverCode, payrollPolicyCode, payLines, notes, currency | of, calculateTotalEmployerCost, calculateAllowancesAndReimbursements, calculateEmployerCharges, containsComponent, toMissionCostLine, getPayrollCode, getMissionNumber |
| `DriverMissionWorkReport` | class | Classe legata all’autista, alle sue abilitazioni o al suo costo operativo. | MAX_CODE_LENGTH, reportCode, missionNumber, driver, drivingTime, otherWorkTime, waitingTime, loadingUnloadingTime | builder, getDrivingHours, getOtherWorkHours, getWaitingHours, getLoadingUnloadingHours, getOvertimeHours, getNightWorkHours, getSundayWorkHours |
| `DriverPayComponentType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | isAllowanceOrReimbursement, isRiskOrQualificationPremium, isEmployerCost |
| `DriverPayRule` | class | Classe legata all’autista, alle sue abilitazioni o al suo costo operativo. | MAX_CODE_LENGTH, MAX_DESCRIPTION_LENGTH, ruleCode, componentType, unit, description, amount, percentage | amount, percentageOfBase, calculateLine, getRuleCode, getComponentType, getUnit, getDescription, getAmount |
| `DriverPayUnit` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | usesWorkedQuantity, usesPercentage |
| `DriverPayrollPolicy` | class | Classe legata all’autista, alle sue abilitazioni o al suo costo operativo. | MAX_CODE_LENGTH, MAX_DESCRIPTION_LENGTH, policyCode, description, validFrom, validTo, rules, notes | of, isValidOn, findRule, hasRule, getPolicyCode, getDescription, getValidFrom, getValidTo |
| `DriverPayrollRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | baseLines | calculateMissionPayroll, missionHasDriverCost |
| `MissionPayrollProjection` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | payroll, missionCostLine | fromPayroll, getPayroll, getMissionCostLine, equals, hashCode |
