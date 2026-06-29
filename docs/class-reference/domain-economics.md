# Domain `economics` spiegato

Costi, ricavi, IVA, acquisti flotta, fatture fornitori, utile/perdita e cassa/debito.

## Classi principali

### `CustomerRevenueInvoice`

Tipo: `class`.

Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini.

Campi principali:

- `MAX_CODE_LENGTH`
- `invoiceNumber`
- `customerCode`
- `shipmentNumber`
- `issueDate`
- `dueDate`
- `lines`
- `notes`
- `currency`

Metodi pubblici principali:

- `of()`
- `getInvoiceNumber()`
- `getCustomerCode()`
- `getShipmentNumber()`
- `getIssueDate()`
- `getDueDate()`
- `getLines()`
- `getNotes()`
- `calculateNetTotal()`
- `calculateVatTotal()`
- `calculateGrossTotal()`
- `toMissionRevenueLines()`

### `EconomicsRules`

Tipo: `class`.

Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

Metodi pubblici principali:

- `isMissionProfitable()`
- `isMissionLossMaking()`
- `missionMeetsMinimumMargin()`
- `shouldReviewMissionBeforeAcceptance()`
- `isCompanyCashNegative()`
- `operatingPeriodIsProfitable()`
- `ledgerHasCashDebt()`
- `ledgerHasVatDebt()`
- `ledgerHasVatCredit()`
- `ledgerIsLossMakingAfterAllKnownCosts()`

### `FinancialBalance`

Tipo: `class`.

Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini.

Campi principali:

- `amount`
- `currency`

Metodi pubblici principali:

- `of()`
- `zero()`
- `from()`
- `getAmount()`
- `getCurrency()`
- `getCurrencyCode()`
- `isPositive()`
- `isZero()`
- `isNegative()`
- `add()`
- `subtract()`
- `absoluteMoney()`

### `FinancingAgreement`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_CODE_LENGTH`
- `agreementNumber`
- `assetCode`
- `providerCode`
- `startDate`
- `numberOfInstallments`
- `financedAmount`
- `downPayment`
- `installmentAmount`
- `finalBalloonPayment`
- `annualInterestRate`
- `notes`

Metodi pubblici principali:

- `of()`
- `getAgreementNumber()`
- `getAssetCode()`
- `getProviderCode()`
- `getStartDate()`
- `getNumberOfInstallments()`
- `getFinancedAmount()`
- `getDownPayment()`
- `getInstallmentAmount()`
- `getFinalBalloonPayment()`
- `getAnnualInterestRate()`
- `getNotes()`

### `FleetAssetAcquisition`

Tipo: `class`.

Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.

Campi principali:

- `MAX_CODE_LENGTH`
- `acquisitionNumber`
- `supplierCode`
- `supplierInvoiceNumber`
- `purchaseDate`
- `components`
- `notes`
- `currency`

Metodi pubblici principali:

- `of()`
- `getAcquisitionNumber()`
- `getSupplierCode()`
- `getSupplierInvoiceNumber()`
- `getPurchaseDate()`
- `getComponents()`
- `getNotes()`
- `calculateNetTotal()`
- `calculateVatTotal()`
- `calculateGrossTotal()`
- `calculateRecoverableVatTotal()`
- `calculateAccountingCostTotal()`

### `FleetAssetCategory`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Metodi pubblici principali:

- `isVehicleUnit()`
- `isEquipment()`
- `isTireRelated()`

### `FleetAssetCostComponent`

Tipo: `class`.

Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini.

Campi principali:

- `MAX_CODE_LENGTH`
- `MAX_DESCRIPTION_LENGTH`
- `componentCode`
- `type`
- `description`
- `price`
- `assignedVehicleFleetNumber`
- `notes`

Metodi pubblici principali:

- `of()`
- `taxableNet()`
- `getComponentCode()`
- `getType()`
- `getDescription()`
- `getPrice()`
- `getAssignedVehicleFleetNumber()`
- `isAssignedToVehicle()`
- `getNotes()`
- `getNetAmount()`
- `getVatAmount()`
- `getGrossAmount()`

### `FleetAssetCostComponentType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Metodi pubblici principali:

- `isVehicleUnit()`
- `isBodyOrEquipment()`
- `isTireRelated()`
- `isAdministrativePurchaseCost()`

### `FleetAssetPurchase`

Tipo: `class`.

Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.

Campi principali:

- `MAX_CODE_LENGTH`
- `MAX_DESCRIPTION_LENGTH`
- `assetCode`
- `category`
- `supplierInvoiceNumber`
- `assignedVehicleFleetNumber`
- `description`
- `purchaseDate`
- `purchasePrice`
- `residualValue`
- `usefulLifeMonths`
- `notes`

Metodi pubblici principali:

- `of()`
- `vehicle()`
- `getAssetCode()`
- `getCategory()`
- `getSupplierInvoiceNumber()`
- `getAssignedVehicleFleetNumber()`
- `isAssignedToVehicle()`
- `getDescription()`
- `getPurchaseDate()`
- `getPurchasePrice()`
- `getResidualValue()`
- `getUsefulLifeMonths()`

### `FleetEconomicLedger`

Tipo: `class`.

Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.

Campi principali:

- `MAX_CODE_LENGTH`
- `ledgerNumber`
- `period`
- `openingCash`
- `customerInvoices`
- `missions`
- `supplierInvoices`
- `assetAcquisitions`
- `recurringExpenses`
- `financingAgreements`
- `notes`
- `reference`

Metodi pubblici principali:

- `of()`
- `getLedgerNumber()`
- `getPeriod()`
- `getOpeningCash()`
- `getCustomerInvoices()`
- `getMissions()`
- `getSupplierInvoices()`
- `getAssetAcquisitions()`
- `getRecurringExpenses()`
- `getFinancingAgreements()`
- `getNotes()`
- `calculateCustomerRevenueNet()`

### `FleetFinancialStatement`

Tipo: `class`.

Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini.

Campi principali:

- `MAX_CODE_LENGTH`
- `statementNumber`
- `period`
- `missions`
- `supplierInvoices`
- `assetPurchases`
- `notes`
- `reference`

Metodi pubblici principali:

- `of()`
- `getStatementNumber()`
- `getPeriod()`
- `getMissions()`
- `getSupplierInvoices()`
- `getAssetPurchases()`
- `getNotes()`
- `calculateTotalRevenue()`
- `calculateMissionCosts()`
- `calculateSupplierInvoiceTotal()`
- `calculateAssetInvestmentTotal()`
- `calculateDepreciationForPeriod()`

### `InsurancePolicy`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_CODE_LENGTH`
- `MAX_PROVIDER_LENGTH`
- `policyNumber`
- `providerName`
- `insuredAssetCode`
- `coveragePeriod`
- `annualPremium`
- `deductible`
- `notes`

Metodi pubblici principali:

- `of()`
- `getPolicyNumber()`
- `getProviderName()`
- `getInsuredAssetCode()`
- `getCoveragePeriod()`
- `getAnnualPremium()`
- `getDeductible()`
- `getNotes()`
- `covers()`
- `calculateDailyPremiumCost()`
- `equals()`
- `hashCode()`

### `MissionCostLine`

Tipo: `class`.

Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini.

Campi principali:

- `MAX_CODE_LENGTH`
- `MAX_DESCRIPTION_LENGTH`
- `lineCode`
- `type`
- `description`
- `amount`
- `notes`

Metodi pubblici principali:

- `of()`
- `fuelFromTransaction()`
- `fuelFromEstimate()`
- `tollsFromEstimate()`
- `vehicleWearFromEstimate()`
- `depreciation()`
- `getLineCode()`
- `getType()`
- `getDescription()`
- `getAmount()`
- `getNotes()`
- `isVariableOperationalCost()`

### `MissionCostType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Metodi pubblici principali:

- `isVariableOperationalCost()`

### `MissionEconomics`

Tipo: `class`.

Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini.

Campi principali:

- `MAX_CODE_LENGTH`
- `missionNumber`
- `shipmentNumber`
- `revenueLines`
- `costLines`
- `notes`
- `reference`

Metodi pubblici principali:

- `of()`
- `getMissionNumber()`
- `getShipmentNumber()`
- `getRevenueLines()`
- `getCostLines()`
- `getNotes()`
- `calculateTotalRevenue()`
- `calculateTotalCosts()`
- `calculateVariableCosts()`
- `calculateProfitability()`
- `isProfitable()`
- `isLossMaking()`

### `MissionRevenueLine`

Tipo: `class`.

Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini.

Campi principali:

- `MAX_CODE_LENGTH`
- `MAX_DESCRIPTION_LENGTH`
- `lineCode`
- `type`
- `description`
- `amount`
- `notes`

Metodi pubblici principali:

- `of()`
- `baseTransportFee()`
- `fromPricingLine()`
- `getLineCode()`
- `getType()`
- `getDescription()`
- `getAmount()`
- `getNotes()`
- `equals()`
- `hashCode()`

### `MissionRevenueType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Metodi pubblici principali:

- `isSurcharge()`

### `ProfitabilityResult`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `totalRevenue`
- `totalCosts`
- `netResult`
- `marginPercentage`
- `status`

Metodi pubblici principali:

- `of()`
- `getTotalRevenue()`
- `getTotalCosts()`
- `getNetResult()`
- `getMarginPercentage()`
- `getStatus()`
- `isProfitable()`
- `isLossMaking()`
- `isInDebt()`
- `getDebtAmount()`
- `equals()`
- `hashCode()`

### `ProfitabilityStatus`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Metodi pubblici principali:

- `isCritical()`

### `PurchaseCategory`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `capitalAsset`

Metodi pubblici principali:

- `isCapitalAsset()`
- `isOperatingExpense()`

### `PurchaseLine`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_CODE_LENGTH`
- `MAX_DESCRIPTION_LENGTH`
- `lineCode`
- `category`
- `description`
- `amount`
- `vatBreakdown`
- `notes`

Metodi pubblici principali:

- `of()`
- `taxed()`
- `taxableNet()`
- `getLineCode()`
- `getCategory()`
- `getDescription()`
- `getAmount()`
- `getVatBreakdown()`
- `hasVatBreakdown()`
- `getNotes()`
- `calculateNetAmount()`
- `calculateVatAmount()`

### `RecurringExpense`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_CODE_LENGTH`
- `MAX_DESCRIPTION_LENGTH`
- `expenseCode`
- `category`
- `description`
- `period`
- `amount`
- `assignedVehicleFleetNumber`
- `notes`

Metodi pubblici principali:

- `of()`
- `noVat()`
- `getExpenseCode()`
- `getCategory()`
- `getDescription()`
- `getPeriod()`
- `getAmount()`
- `getAssignedVehicleFleetNumber()`
- `isAssignedToVehicle()`
- `getNotes()`
- `calculateGrossAmount()`
- `calculateAccountingCost()`

### `RecurringExpenseCategory`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Metodi pubblici principali:

- `isVehicleRelated()`

### `SupplierInvoice`

Tipo: `class`.

Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini.

Campi principali:

- `MAX_CODE_LENGTH`
- `invoiceNumber`
- `supplierCode`
- `issueDate`
- `dueDate`
- `status`
- `lines`
- `notes`
- `reference`

Metodi pubblici principali:

- `received()`
- `approve()`
- `markPaid()`
- `getInvoiceNumber()`
- `getSupplierCode()`
- `getIssueDate()`
- `getDueDate()`
- `getStatus()`
- `getLines()`
- `getNotes()`
- `calculateTotal()`
- `calculateNetTotal()`

### `SupplierInvoiceStatus`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Metodi pubblici principali:

- `isPayable()`

### `TaxableRevenueLine`

Tipo: `class`.

Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini.

Campi principali:

- `MAX_CODE_LENGTH`
- `MAX_DESCRIPTION_LENGTH`
- `lineCode`
- `type`
- `description`
- `price`
- `notes`

Metodi pubblici principali:

- `of()`
- `taxableNet()`
- `baseTransportFee()`
- `getLineCode()`
- `getType()`
- `getDescription()`
- `getPrice()`
- `getNotes()`
- `getNetAmount()`
- `getVatAmount()`
- `getGrossAmount()`
- `toMissionRevenueLine()`

### `VatBreakdown`

Tipo: `class`.

Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini.

Campi principali:

- `FULLY_RECOVERABLE`
- `NOT_RECOVERABLE`
- `netAmount`
- `vatRate`
- `treatment`
- `vatAmount`
- `grossAmount`
- `recoverableVatPercentage`
- `value`

Metodi pubblici principali:

- `taxableFromNet()`
- `taxableFromGross()`
- `exempt()`
- `reverseCharge()`
- `outOfScope()`
- `noVatKnown()`
- `nonDeductibleFromNet()`
- `getNetAmount()`
- `getVatRate()`
- `getTreatment()`
- `getVatAmount()`
- `getGrossAmount()`

### `VatRate`

Tipo: `class`.

Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini.

Campi principali:

- `MAX_CODE_LENGTH`
- `code`
- `percentage`

Metodi pubblici principali:

- `of()`
- `italianStandard22()`
- `italianReduced10()`
- `italianReduced5()`
- `italianReduced4()`
- `zero()`
- `getCode()`
- `getPercentage()`
- `toMultiplier()`
- `isZero()`
- `equals()`
- `hashCode()`

### `VatTreatment`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Metodi pubblici principali:

- `generatesVatAmount()`
