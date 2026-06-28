package it.gabriele.truckflow.domain.customer;

import it.gabriele.truckflow.domain.location.Address;
import it.gabriele.truckflow.domain.location.Location;
import it.gabriele.truckflow.domain.shared.Notes;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa CustomerAccount.
 */
class CustomerAccountTest {

    @Test
    void shouldCreateCustomerAccountFromList() {
        Customer customer = activeCustomer();
        CustomerContact primaryContact = primaryLogisticsContact();
        CustomerContact billingContact = secondaryBillingContact();

        CustomerAccount account = CustomerAccount.of(
                customer,
                List.of(primaryContact, billingContact)
        );

        assertEquals(customer, account.getCustomer());
        assertEquals(2, account.getContactCount());
        assertEquals(List.of(primaryContact, billingContact), account.getContacts());
        assertEquals(primaryContact, account.getPrimaryContact());
    }

    @Test
    void shouldCreateCustomerAccountFromContacts() {
        CustomerAccount account = CustomerAccount.of(
                activeCustomer(),
                primaryLogisticsContact(),
                secondaryBillingContact()
        );

        assertEquals(2, account.getContactCount());
    }

    @Test
    void shouldNotAllowNullCustomer() {
        assertThrows(IllegalArgumentException.class, () -> CustomerAccount.of(
                null,
                List.of(primaryLogisticsContact())
        ));
    }

    @Test
    void shouldNotAllowNullOrEmptyContactList() {
        assertThrows(IllegalArgumentException.class, () -> CustomerAccount.of(
                activeCustomer(),
                (List<CustomerContact>) null
        ));

        assertThrows(IllegalArgumentException.class, () -> CustomerAccount.of(
                activeCustomer(),
                List.of()
        ));
    }

    @Test
    void shouldNotAllowNullContactsInsideList() {
        assertThrows(IllegalArgumentException.class, () -> CustomerAccount.of(
                activeCustomer(),
                Arrays.asList(primaryLogisticsContact(), null)
        ));
    }

    @Test
    void shouldNotAllowNullPrimaryContactInFactory() {
        assertThrows(IllegalArgumentException.class, () -> CustomerAccount.of(
                activeCustomer(),
                (CustomerContact) null
        ));
    }

    @Test
    void shouldRequireExactlyOnePrimaryContact() {
        assertThrows(IllegalArgumentException.class, () -> CustomerAccount.of(
                activeCustomer(),
                List.of(secondaryBillingContact(), secondaryOperationsContact())
        ));

        assertThrows(IllegalArgumentException.class, () -> CustomerAccount.of(
                activeCustomer(),
                List.of(primaryLogisticsContact(), anotherPrimaryContact())
        ));
    }

    @Test
    void shouldReturnUnmodifiableContacts() {
        CustomerAccount account = CustomerAccount.of(
                activeCustomer(),
                primaryLogisticsContact()
        );

        assertThrows(UnsupportedOperationException.class,
                () -> account.getContacts().add(secondaryBillingContact()));
    }

    @Test
    void shouldGetContactsByRole() {
        CustomerAccount account = CustomerAccount.of(
                activeCustomer(),
                primaryLogisticsContact(),
                secondaryBillingContact()
        );

        List<CustomerContact> billingContacts = account.getContactsByRole(CustomerContactRole.BILLING);

        assertEquals(1, billingContacts.size());
        assertEquals(CustomerContactRole.BILLING, billingContacts.get(0).getRole());
    }

    @Test
    void shouldCheckContactRole() {
        CustomerAccount account = CustomerAccount.of(
                activeCustomer(),
                primaryLogisticsContact(),
                secondaryBillingContact()
        );

        assertTrue(account.hasContactRole(CustomerContactRole.LOGISTICS));
        assertTrue(account.hasBillingContact());
        assertFalse(account.hasContactRole(CustomerContactRole.MANAGEMENT));
    }

    @Test
    void shouldNotCheckNullContactRole() {
        CustomerAccount account = CustomerAccount.of(
                activeCustomer(),
                primaryLogisticsContact()
        );

        assertThrows(IllegalArgumentException.class, () -> account.hasContactRole(null));
        assertThrows(IllegalArgumentException.class, () -> account.getContactsByRole(null));
    }

    @Test
    void shouldAllowActiveCustomerToRequestTransportOrder() {
        CustomerAccount account = CustomerAccount.of(
                activeCustomer(),
                primaryLogisticsContact()
        );

        assertTrue(account.canRequestTransportOrder());
    }

    @Test
    void shouldNotAllowInactiveCustomerToRequestTransportOrder() {
        CustomerAccount account = CustomerAccount.of(
                inactiveCustomer(),
                primaryLogisticsContact()
        );

        assertFalse(account.canRequestTransportOrder());
    }

    @Test
    void shouldDetectSuspendedCustomer() {
        CustomerAccount account = CustomerAccount.of(
                suspendedCustomer(),
                primaryLogisticsContact()
        );

        assertTrue(account.isSuspended());
        assertFalse(account.canRequestTransportOrder());
    }

    @Test
    void shouldExposeCustomerCode() {
        CustomerAccount account = CustomerAccount.of(
                activeCustomer(),
                primaryLogisticsContact()
        );

        assertEquals("CUST-001", account.getCustomerCode());
    }

    @Test
    void shouldFormatSingleLine() {
        CustomerAccount account = CustomerAccount.of(
                activeCustomer(),
                primaryLogisticsContact(),
                secondaryBillingContact()
        );

        assertEquals(
                "CUST-001 - ACME Logistics S.r.l. - COMPANY - ACTIVE - contacts: 2",
                account.formatSingleLine()
        );
    }

    @Test
    void shouldConsiderEquivalentCustomerAccountsEqual() {
        CustomerAccount first = CustomerAccount.of(
                activeCustomer(),
                primaryLogisticsContact(),
                secondaryBillingContact()
        );

        CustomerAccount second = CustomerAccount.of(
                activeCustomer(),
                primaryLogisticsContact(),
                secondaryBillingContact()
        );

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    private static Customer activeCustomer() {
        return Customer.active(
                "CUST-001",
                "ACME Logistics S.r.l.",
                CustomerType.COMPANY,
                customerLocation(),
                Notes.empty()
        );
    }

    private static Customer inactiveCustomer() {
        return Customer.inactive(
                "CUST-001",
                "ACME Logistics S.r.l.",
                CustomerType.COMPANY,
                customerLocation(),
                Notes.empty()
        );
    }

    private static Customer suspendedCustomer() {
        return Customer.suspended(
                "CUST-001",
                "ACME Logistics S.r.l.",
                CustomerType.COMPANY,
                customerLocation(),
                Notes.empty()
        );
    }

    private static CustomerContact primaryLogisticsContact() {
        return CustomerContact.primary(
                "Mario Rossi",
                CustomerContactRole.LOGISTICS,
                "mario.rossi@example.com",
                "+39 333 1234567",
                Notes.empty()
        );
    }

    private static CustomerContact anotherPrimaryContact() {
        return CustomerContact.primary(
                "Laura Verdi",
                CustomerContactRole.OPERATIONS,
                "laura.verdi@example.com",
                "+39 333 2223333",
                Notes.empty()
        );
    }

    private static CustomerContact secondaryBillingContact() {
        return CustomerContact.secondary(
                "Laura Bianchi",
                CustomerContactRole.BILLING,
                "laura.bianchi@example.com",
                "+39 333 7654321",
                Notes.empty()
        );
    }

    private static CustomerContact secondaryOperationsContact() {
        return CustomerContact.secondary(
                "Giuseppe Neri",
                CustomerContactRole.OPERATIONS,
                "giuseppe.neri@example.com",
                "+39 333 4445555",
                Notes.empty()
        );
    }

    private static Location customerLocation() {
        return Location.of(
                "Sede Cliente Milano",
                Address.of("Via Cliente 10", "Milano", "20100", "IT"),
                "Europe/Rome"
        );
    }
}
