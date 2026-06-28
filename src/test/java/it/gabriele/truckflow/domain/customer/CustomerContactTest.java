package it.gabriele.truckflow.domain.customer;

import it.gabriele.truckflow.domain.shared.Notes;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa CustomerContact.
 */
class CustomerContactTest {

    @Test
    void shouldCreatePrimaryContact() {
        CustomerContact contact = CustomerContact.primary(
                "Mario Rossi",
                CustomerContactRole.LOGISTICS,
                "mario.rossi@example.com",
                "+39 333 1234567",
                Notes.empty()
        );

        assertEquals("Mario Rossi", contact.getFullName());
        assertEquals(CustomerContactRole.LOGISTICS, contact.getRole());
        assertEquals("mario.rossi@example.com", contact.getEmail());
        assertEquals("+39 333 1234567", contact.getPhoneNumber());
        assertEquals(Notes.empty(), contact.getNotes());
        assertTrue(contact.isPrimaryContact());
    }

    @Test
    void shouldCreateSecondaryContact() {
        CustomerContact contact = CustomerContact.secondary(
                "Laura Bianchi",
                CustomerContactRole.BILLING,
                "laura.bianchi@example.com",
                "+39 333 7654321",
                Notes.empty()
        );

        assertFalse(contact.isPrimaryContact());
    }

    @Test
    void shouldNormalizeFullNameAndEmailAndPhoneNumber() {
        CustomerContact contact = CustomerContact.primary(
                "  Mario Rossi  ",
                CustomerContactRole.LOGISTICS,
                "  MARIO.ROSSI@EXAMPLE.COM  ",
                "  +39 333 1234567  ",
                Notes.empty()
        );

        assertEquals("Mario Rossi", contact.getFullName());
        assertEquals("mario.rossi@example.com", contact.getEmail());
        assertEquals("+39 333 1234567", contact.getPhoneNumber());
    }

    @Test
    void shouldNotAllowInvalidFullName() {
        assertThrows(IllegalArgumentException.class, () -> CustomerContact.primary(
                null,
                CustomerContactRole.LOGISTICS,
                "mario.rossi@example.com",
                "+39 333 1234567",
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> CustomerContact.primary(
                "   ",
                CustomerContactRole.LOGISTICS,
                "mario.rossi@example.com",
                "+39 333 1234567",
                Notes.empty()
        ));
    }

    @Test
    void shouldNotAllowTooLongFullName() {
        String tooLongName = "A".repeat(151);

        assertThrows(IllegalArgumentException.class, () -> CustomerContact.primary(
                tooLongName,
                CustomerContactRole.LOGISTICS,
                "mario.rossi@example.com",
                "+39 333 1234567",
                Notes.empty()
        ));
    }

    @Test
    void shouldNotAllowNullRole() {
        assertThrows(IllegalArgumentException.class, () -> CustomerContact.primary(
                "Mario Rossi",
                null,
                "mario.rossi@example.com",
                "+39 333 1234567",
                Notes.empty()
        ));
    }

    @Test
    void shouldNotAllowInvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> CustomerContact.primary(
                "Mario Rossi",
                CustomerContactRole.LOGISTICS,
                null,
                "+39 333 1234567",
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> CustomerContact.primary(
                "Mario Rossi",
                CustomerContactRole.LOGISTICS,
                "   ",
                "+39 333 1234567",
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> CustomerContact.primary(
                "Mario Rossi",
                CustomerContactRole.LOGISTICS,
                "mario.rossi.example.com",
                "+39 333 1234567",
                Notes.empty()
        ));
    }

    @Test
    void shouldNotAllowTooLongEmail() {
        String tooLongEmail = "a".repeat(245) + "@example.com";

        assertThrows(IllegalArgumentException.class, () -> CustomerContact.primary(
                "Mario Rossi",
                CustomerContactRole.LOGISTICS,
                tooLongEmail,
                "+39 333 1234567",
                Notes.empty()
        ));
    }

    @Test
    void shouldNotAllowInvalidPhoneNumber() {
        assertThrows(IllegalArgumentException.class, () -> CustomerContact.primary(
                "Mario Rossi",
                CustomerContactRole.LOGISTICS,
                "mario.rossi@example.com",
                null,
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> CustomerContact.primary(
                "Mario Rossi",
                CustomerContactRole.LOGISTICS,
                "mario.rossi@example.com",
                "   ",
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> CustomerContact.primary(
                "Mario Rossi",
                CustomerContactRole.LOGISTICS,
                "mario.rossi@example.com",
                "ABC123",
                Notes.empty()
        ));
    }

    @Test
    void shouldNotAllowTooLongPhoneNumber() {
        String tooLongPhoneNumber = "1".repeat(31);

        assertThrows(IllegalArgumentException.class, () -> CustomerContact.primary(
                "Mario Rossi",
                CustomerContactRole.LOGISTICS,
                "mario.rossi@example.com",
                tooLongPhoneNumber,
                Notes.empty()
        ));
    }

    @Test
    void shouldNotAllowNullNotes() {
        assertThrows(IllegalArgumentException.class, () -> CustomerContact.primary(
                "Mario Rossi",
                CustomerContactRole.LOGISTICS,
                "mario.rossi@example.com",
                "+39 333 1234567",
                null
        ));
    }

    @Test
    void shouldCheckRole() {
        CustomerContact contact = CustomerContact.primary(
                "Mario Rossi",
                CustomerContactRole.LOGISTICS,
                "mario.rossi@example.com",
                "+39 333 1234567",
                Notes.empty()
        );

        assertTrue(contact.hasRole(CustomerContactRole.LOGISTICS));
        assertFalse(contact.hasRole(CustomerContactRole.BILLING));
    }

    @Test
    void shouldNotCheckNullRole() {
        CustomerContact contact = CustomerContact.primary(
                "Mario Rossi",
                CustomerContactRole.LOGISTICS,
                "mario.rossi@example.com",
                "+39 333 1234567",
                Notes.empty()
        );

        assertThrows(IllegalArgumentException.class, () -> contact.hasRole(null));
    }

    @Test
    void shouldDetectNotes() {
        CustomerContact contact = CustomerContact.primary(
                "Mario Rossi",
                CustomerContactRole.LOGISTICS,
                "mario.rossi@example.com",
                "+39 333 1234567",
                Notes.of("Chiamare prima delle consegne")
        );

        assertTrue(contact.hasNotes());
    }

    @Test
    void shouldFormatSingleLine() {
        CustomerContact contact = CustomerContact.primary(
                "Mario Rossi",
                CustomerContactRole.LOGISTICS,
                "mario.rossi@example.com",
                "+39 333 1234567",
                Notes.empty()
        );

        assertEquals(
                "Mario Rossi - LOGISTICS - mario.rossi@example.com - +39 333 1234567",
                contact.formatSingleLine()
        );
    }

    @Test
    void shouldConsiderEquivalentContactsEqual() {
        CustomerContact first = CustomerContact.primary(
                "  Mario Rossi  ",
                CustomerContactRole.LOGISTICS,
                "  MARIO.ROSSI@EXAMPLE.COM  ",
                "  +39 333 1234567  ",
                Notes.empty()
        );

        CustomerContact second = CustomerContact.primary(
                "Mario Rossi",
                CustomerContactRole.LOGISTICS,
                "mario.rossi@example.com",
                "+39 333 1234567",
                Notes.empty()
        );

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }
}
