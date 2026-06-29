# Package `domain.economics`

Economia aziendale: IVA, acquisti, costi, ricavi, utile/perdita, cassa e debito.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| CustomerRevenueInvoice | class | Classe del package domain.economics; rappresenta un concetto del modello TruckFlow. | of, getInvoiceNumber, getCustomerCode, getShipmentNumber, getIssueDate, getDueDate, getLines, getNotes, calculateNetTotal, calculateVatTotal |
| EconomicsRules | class | Classe di regole per validare e calcolare comportamenti nel package domain.economics. | isMissionProfitable, isMissionLossMaking, missionMeetsMinimumMargin, shouldReviewMissionBeforeAcceptance, isCompanyCashNegative, operatingPeriodIsProfitable, ledgerHasCashDebt, ledgerHasVatDebt, ledgerHasVatCredit, ledgerIsLossMakingAfterAllKnownCosts |
| FinancialBalance | class | Classe del package domain.economics; rappresenta un concetto del modello TruckFlow. | of, zero, from, getAmount, getCurrency, getCurrencyCode, isPositive, isZero, isNegative, add |
| FinancingAgreement | class | Classe del package domain.economics; rappresenta un concetto del modello TruckFlow. | of, getAgreementNumber, getAssetCode, getProviderCode, getStartDate, getNumberOfInstallments, getFinancedAmount, getDownPayment, getInstallmentAmount, getFinalBalloonPayment |
| FleetAssetAcquisition | class | Classe del package domain.economics; rappresenta un concetto del modello TruckFlow. | of, getAcquisitionNumber, getSupplierCode, getSupplierInvoiceNumber, getPurchaseDate, getComponents, getNotes, calculateNetTotal, calculateVatTotal, calculateGrossTotal |
| FleetAssetCategory | enum | Categoria funzionale usata per distinguere casi operativi o contabili. | isVehicleUnit, isEquipment, isTireRelated |
| FleetAssetCostComponent | class | Classe del package domain.economics; rappresenta un concetto del modello TruckFlow. | of, taxableNet, getComponentCode, getType, getDescription, getPrice, getAssignedVehicleFleetNumber, isAssignedToVehicle, getNotes, getNetAmount |
| FleetAssetCostComponentType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | isVehicleUnit, isBodyOrEquipment, isTireRelated, isAdministrativePurchaseCost |
| FleetAssetPurchase | class | Classe del package domain.economics; rappresenta un concetto del modello TruckFlow. | of, vehicle, getAssetCode, getCategory, getSupplierInvoiceNumber, getAssignedVehicleFleetNumber, isAssignedToVehicle, getDescription, getPurchaseDate, getPurchasePrice |
| FleetEconomicLedger | class | Classe del package domain.economics; rappresenta un concetto del modello TruckFlow. | of, getLedgerNumber, getPeriod, getOpeningCash, getCustomerInvoices, getMissions, getSupplierInvoices, getAssetAcquisitions, getRecurringExpenses, getFinancingAgreements |
| FleetFinancialStatement | class | Classe del package domain.economics; rappresenta un concetto del modello TruckFlow. | of, getStatementNumber, getPeriod, getMissions, getSupplierInvoices, getAssetPurchases, getNotes, calculateTotalRevenue, calculateMissionCosts, calculateSupplierInvoiceTotal |
| InsurancePolicy | class | Policy configurabile: contiene regole aziendali parametrizzabili. | of, getPolicyNumber, getProviderName, getInsuredAssetCode, getCoveragePeriod, getAnnualPremium, getDeductible, getNotes, covers, calculateDailyPremiumCost |
| MissionCostLine | class | Riga di dettaglio: rappresenta una voce numerabile/economica/documentale. | of, fuelFromTransaction, fuelFromEstimate, tollsFromEstimate, vehicleWearFromEstimate, depreciation, getLineCode, getType, getDescription, getAmount |
| MissionCostType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | isVariableOperationalCost |
| MissionEconomics | class | Classe del package domain.economics; rappresenta un concetto del modello TruckFlow. | of, getMissionNumber, getShipmentNumber, getRevenueLines, getCostLines, getNotes, calculateTotalRevenue, calculateTotalCosts, calculateVariableCosts, calculateProfitability |
| MissionRevenueLine | class | Riga di dettaglio: rappresenta una voce numerabile/economica/documentale. | of, baseTransportFee, fromPricingLine, getLineCode, getType, getDescription, getAmount, getNotes, equals, hashCode |
| MissionRevenueType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | isSurcharge |
| ProfitabilityResult | class | Risultato di una valutazione o calcolo. | of, getTotalRevenue, getTotalCosts, getNetResult, getMarginPercentage, getStatus, isProfitable, isLossMaking, isInDebt, getDebtAmount |
| ProfitabilityStatus | enum | Enum di stato del ciclo di vita. | isCritical |
| PurchaseCategory | enum | Categoria funzionale usata per distinguere casi operativi o contabili. | isCapitalAsset, isOperatingExpense |
| PurchaseLine | class | Riga di dettaglio: rappresenta una voce numerabile/economica/documentale. | of, taxed, taxableNet, getLineCode, getCategory, getDescription, getAmount, getVatBreakdown, hasVatBreakdown, getNotes |
| RecurringExpense | class | Classe del package domain.economics; rappresenta un concetto del modello TruckFlow. | of, noVat, getExpenseCode, getCategory, getDescription, getPeriod, getAmount, getAssignedVehicleFleetNumber, isAssignedToVehicle, getNotes |
| RecurringExpenseCategory | enum | Categoria funzionale usata per distinguere casi operativi o contabili. | isVehicleRelated |
| SupplierInvoice | class | Classe del package domain.economics; rappresenta un concetto del modello TruckFlow. | received, approve, markPaid, getInvoiceNumber, getSupplierCode, getIssueDate, getDueDate, getStatus, getLines, getNotes |
| SupplierInvoiceStatus | enum | Enum di stato del ciclo di vita. | isPayable |
| TaxableRevenueLine | class | Riga di dettaglio: rappresenta una voce numerabile/economica/documentale. | of, taxableNet, baseTransportFee, getLineCode, getType, getDescription, getPrice, getNotes, getNetAmount, getVatAmount |
| VatBreakdown | class | Classe del package domain.economics; rappresenta un concetto del modello TruckFlow. | taxableFromNet, taxableFromGross, exempt, reverseCharge, outOfScope, noVatKnown, nonDeductibleFromNet, getNetAmount, getVatRate, getTreatment |
| VatRate | class | Classe del package domain.economics; rappresenta un concetto del modello TruckFlow. | of, italianStandard22, italianReduced10, italianReduced5, italianReduced4, zero, getCode, getPercentage, toMultiplier, isZero |
| VatTreatment | enum | Enum: insieme chiuso di valori ammessi dal dominio. | generatesVatAmount |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
