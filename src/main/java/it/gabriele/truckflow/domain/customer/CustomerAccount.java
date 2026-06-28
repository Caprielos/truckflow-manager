package it.gabriele.truckflow.domain.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Rappresenta un cliente con i suoi contatti.
 * Serve per gestire il cliente come unità operativa completa.
 */
public final class CustomerAccount {

    private final Customer customer;
    private final List<CustomerContact> contacts;

    private CustomerAccount(Customer customer, List<CustomerContact> contacts) {
        if (customer == null) {
            throw new IllegalArgumentException("Il cliente è obbligatorio.");
        }

        if (contacts == null) {
            throw new IllegalArgumentException("La lista dei contatti è obbligatoria.");
        }

        if (contacts.isEmpty()) {
            throw new IllegalArgumentException("Il cliente deve avere almeno un contatto.");
        }

        if (contacts.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("La lista dei contatti non può contenere elementi nulli.");
        }

        long primaryContactCount = contacts.stream()
                .filter(CustomerContact::isPrimaryContact)
                .count();

        if (primaryContactCount != 1) {
            throw new IllegalArgumentException("Il cliente deve avere esattamente un contatto principale.");
        }

        this.customer = customer;
        this.contacts = List.copyOf(contacts);
    }

    public static CustomerAccount of(Customer customer, List<CustomerContact> contacts) {
        return new CustomerAccount(customer, contacts);
    }

    public static CustomerAccount of(
            Customer customer,
            CustomerContact primaryContact,
            CustomerContact... otherContacts
    ) {
        if (primaryContact == null) {
            throw new IllegalArgumentException("Il contatto principale è obbligatorio.");
        }

        List<CustomerContact> contacts = new ArrayList<>();
        contacts.add(primaryContact);

        if (otherContacts != null) {
            for (CustomerContact contact : otherContacts) {
                contacts.add(contact);
            }
        }

        return new CustomerAccount(customer, contacts);
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<CustomerContact> getContacts() {
        return contacts;
    }

    public int getContactCount() {
        return contacts.size();
    }

    public CustomerContact getPrimaryContact() {
        return contacts.stream()
                .filter(CustomerContact::isPrimaryContact)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Contatto principale non trovato."));
    }

    public List<CustomerContact> getContactsByRole(CustomerContactRole role) {
        if (role == null) {
            throw new IllegalArgumentException("Il ruolo da cercare è obbligatorio.");
        }

        return contacts.stream()
                .filter(contact -> contact.hasRole(role))
                .toList();
    }

    public boolean hasContactRole(CustomerContactRole role) {
        if (role == null) {
            throw new IllegalArgumentException("Il ruolo da cercare è obbligatorio.");
        }

        return contacts.stream().anyMatch(contact -> contact.hasRole(role));
    }

    public boolean hasBillingContact() {
        return hasContactRole(CustomerContactRole.BILLING);
    }

    public boolean canRequestTransportOrder() {
        return customer.isActive();
    }

    public boolean isSuspended() {
        return customer.isSuspended();
    }

    public String getCustomerCode() {
        return customer.getCode();
    }

    public String formatSingleLine() {
        return customer.formatSingleLine() + " - contacts: " + contacts.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerAccount that)) return false;
        return customer.equals(that.customer)
                && contacts.equals(that.contacts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, contacts);
    }

    @Override
    public String toString() {
        return formatSingleLine();
    }
}
