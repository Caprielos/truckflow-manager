# Package `domain.payroll`

Stipendio/costo autista per missione, inclusi premi, straordinari, ADR e contributi.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| DriverMissionPayLine | class | Riga di dettaglio: rappresenta una voce numerabile/economica/documentale. | of, getLineCode, getComponentType, getUnit, getDescription, getQuantity, getAmount, getNotes, isAllowanceOrReimbursement, isEmployerCost |
| DriverMissionPayroll | class | Classe del package domain.payroll; rappresenta un concetto del modello TruckFlow. | of, calculateTotalEmployerCost, calculateAllowancesAndReimbursements, calculateEmployerCharges, containsComponent, toMissionCostLine, getPayrollCode, getMissionNumber, getDriverCode, getPayrollPolicyCode |
| DriverMissionWorkReport | class | Classe del package domain.payroll; rappresenta un concetto del modello TruckFlow. | builder, getDrivingHours, getOtherWorkHours, getWaitingHours, getLoadingUnloadingHours, getOvertimeHours, getNightWorkHours, getSundayWorkHours, getHolidayWorkHours, hasCargoCategory |
| DriverPayComponentType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | isAllowanceOrReimbursement, isRiskOrQualificationPremium, isEmployerCost |
| DriverPayRule | class | Classe del package domain.payroll; rappresenta un concetto del modello TruckFlow. | amount, percentageOfBase, calculateLine, getRuleCode, getComponentType, getUnit, getDescription, getAmount, getPercentage, getNotes |
| DriverPayUnit | enum | Enum: insieme chiuso di valori ammessi dal dominio. | usesWorkedQuantity, usesPercentage |
| DriverPayrollPolicy | class | Policy configurabile: contiene regole aziendali parametrizzabili. | of, isValidOn, findRule, hasRule, getPolicyCode, getDescription, getValidFrom, getValidTo, getRules, getNotes |
| DriverPayrollRules | class | Classe di regole per validare e calcolare comportamenti nel package domain.payroll. | calculateMissionPayroll, missionHasDriverCost |
| MissionPayrollProjection | class | Classe del package domain.payroll; rappresenta un concetto del modello TruckFlow. | fromPayroll, getPayroll, getMissionCostLine, equals, hashCode |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
