package it.gabriele.truckflow.domain.economics;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

/**
 * Acquisto reale composto: trattore + semirimorchio + allestimento + frigo + gomme + telematica + pratiche.
 * Tiene distinti imponibile, IVA, lordo pagato e costo contabile effettivo.
 */
public final class FleetAssetAcquisition {

    private static final int MAX_CODE_LENGTH = 50;

    private final String acquisitionNumber;
    private final String supplierCode;
    private final String supplierInvoiceNumber;
    private final LocalDate purchaseDate;
    private final List<FleetAssetCostComponent> components;
    private final Notes notes;

    private FleetAssetAcquisition(
            String acquisitionNumber,
            String supplierCode,
            String supplierInvoiceNumber,
            LocalDate purchaseDate,
            List<FleetAssetCostComponent> components,
            Notes notes
    ) {
        this.acquisitionNumber = validateCode(acquisitionNumber, "Il numero acquisto flotta è obbligatorio.");
        this.supplierCode = validateCode(supplierCode, "Il codice fornitore acquisto flotta è obbligatorio.");
        this.supplierInvoiceNumber = validateCode(supplierInvoiceNumber, "Il numero fattura acquisto flotta è obbligatorio.");
        if (purchaseDate == null) {
            throw new IllegalArgumentException("La data acquisto flotta è obbligatoria.");
        }
        this.components = validateComponents(components);
        if (notes == null) {
            throw new IllegalArgumentException("Le note acquisto flotta sono obbligatorie.");
        }
        this.purchaseDate = purchaseDate;
        this.notes = notes;
    }

    public static FleetAssetAcquisition of(
            String acquisitionNumber,
            String supplierCode,
            String supplierInvoiceNumber,
            LocalDate purchaseDate,
            List<FleetAssetCostComponent> components,
            Notes notes
    ) {
        return new FleetAssetAcquisition(acquisitionNumber, supplierCode, supplierInvoiceNumber,
                purchaseDate, components, notes);
    }

    public static FleetAssetAcquisition of(
            String acquisitionNumber,
            String supplierCode,
            String supplierInvoiceNumber,
            LocalDate purchaseDate,
            FleetAssetCostComponent firstComponent,
            FleetAssetCostComponent... otherComponents
    ) {
        if (firstComponent == null) {
            throw new IllegalArgumentException("Il primo componente acquisto flotta è obbligatorio.");
        }
        List<FleetAssetCostComponent> components = new ArrayList<>();
        components.add(firstComponent);
        if (otherComponents != null) {
            for (FleetAssetCostComponent component : otherComponents) {
                components.add(component);
            }
        }
        return of(acquisitionNumber, supplierCode, supplierInvoiceNumber, purchaseDate, components, Notes.empty());
    }

    private static List<FleetAssetCostComponent> validateComponents(List<FleetAssetCostComponent> components) {
        if (components == null) {
            throw new IllegalArgumentException("I componenti acquisto flotta sono obbligatori.");
        }
        if (components.isEmpty()) {
            throw new IllegalArgumentException("Un acquisto flotta deve avere almeno un componente.");
        }
        if (components.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("I componenti acquisto flotta non possono contenere null.");
        }
        long uniqueCodes = components.stream().map(FleetAssetCostComponent::getComponentCode).distinct().count();
        if (uniqueCodes != components.size()) {
            throw new IllegalArgumentException("I componenti acquisto flotta non possono avere codici duplicati.");
        }
        Money reference = components.get(0).getGrossAmount();
        for (int i = 1; i < components.size(); i++) {
            reference.add(components.get(i).getGrossAmount());
        }
        return List.copyOf(components);
    }

    private static String validateCode(String code, String nullMessage) {
        if (code == null) {
            throw new IllegalArgumentException(nullMessage);
        }
        String normalized = code.trim().toUpperCase();
        if (normalized.isEmpty()) {
            throw new IllegalArgumentException(nullMessage);
        }
        if (normalized.length() > MAX_CODE_LENGTH) {
            throw new IllegalArgumentException("Il codice non può superare " + MAX_CODE_LENGTH + " caratteri.");
        }
        if (!normalized.matches("[A-Z0-9_-]+")) {
            throw new IllegalArgumentException("Il codice può contenere solo lettere, numeri, trattini e underscore.");
        }
        return normalized;
    }

    public String getAcquisitionNumber() {
        return acquisitionNumber;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public String getSupplierInvoiceNumber() {
        return supplierInvoiceNumber;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public List<FleetAssetCostComponent> getComponents() {
        return components;
    }

    public Notes getNotes() {
        return notes;
    }

    public Money calculateNetTotal() {
        return sum(components.stream().map(FleetAssetCostComponent::getNetAmount).toList());
    }

    public Money calculateVatTotal() {
        return sum(components.stream().map(FleetAssetCostComponent::getVatAmount).toList());
    }

    public Money calculateGrossTotal() {
        return sum(components.stream().map(FleetAssetCostComponent::getGrossAmount).toList());
    }

    public Money calculateRecoverableVatTotal() {
        return sum(components.stream().map(FleetAssetCostComponent::calculateRecoverableVatAmount).toList());
    }

    public Money calculateAccountingCostTotal() {
        return sum(components.stream().map(FleetAssetCostComponent::calculateAccountingCost).toList());
    }

    public Money calculateVehicleUnitCost() {
        return sum(components.stream()
                .filter(FleetAssetCostComponent::isVehicleUnitCost)
                .map(FleetAssetCostComponent::calculateAccountingCost)
                .toList());
    }

    public Money calculateBodyAndEquipmentCost() {
        return sum(components.stream()
                .filter(FleetAssetCostComponent::isBodyOrEquipmentCost)
                .map(FleetAssetCostComponent::calculateAccountingCost)
                .toList());
    }

    public Money calculateTireCost() {
        return sum(components.stream()
                .filter(FleetAssetCostComponent::isTireCost)
                .map(FleetAssetCostComponent::calculateAccountingCost)
                .toList());
    }

    public boolean contains(FleetAssetCostComponentType type) {
        if (type == null) {
            throw new IllegalArgumentException("Il tipo componente da cercare è obbligatorio.");
        }
        return components.stream().anyMatch(component -> component.getType() == type);
    }

    private Money sum(List<Money> amounts) {
        Currency currency = components.get(0).getGrossAmount().getCurrency();
        if (amounts.isEmpty()) {
            return Money.of(BigDecimal.ZERO, currency);
        }
        Money total = Money.of(BigDecimal.ZERO, currency);
        for (Money amount : amounts) {
            total = total.add(amount);
        }
        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FleetAssetAcquisition that)) return false;
        return acquisitionNumber.equals(that.acquisitionNumber)
                && supplierCode.equals(that.supplierCode)
                && supplierInvoiceNumber.equals(that.supplierInvoiceNumber)
                && purchaseDate.equals(that.purchaseDate)
                && components.equals(that.components)
                && notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(acquisitionNumber, supplierCode, supplierInvoiceNumber, purchaseDate, components, notes);
    }
}
