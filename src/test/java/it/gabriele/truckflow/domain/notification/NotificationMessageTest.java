package it.gabriele.truckflow.domain.notification;

import it.gabriele.truckflow.domain.shared.Notes;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa NotificationMessage.
 */
class NotificationMessageTest {

    @Test
    void shouldCreateDraftNotification() {
        NotificationMessage message = draftMessage();

        assertEquals("NTF-001", message.getNotificationNumber());
        assertEquals(NotificationType.SHIPMENT_DELAYED, message.getType());
        assertEquals(NotificationChannel.EMAIL, message.getChannel());
        assertEquals(NotificationRecipientType.CUSTOMER_CONTACT, message.getRecipientType());
        assertEquals("customer@example.com", message.getRecipientReference());
        assertEquals(NotificationPriority.HIGH, message.getPriority());
        assertEquals("Ritardo spedizione", message.getSubject());
        assertEquals("La spedizione SHP-001 è in ritardo.", message.getBody());
        assertEquals(NotificationStatus.DRAFT, message.getStatus());
        assertTrue(message.isDraft());
        assertFalse(message.isTerminal());
        assertTrue(message.isCustomerVisible());
        assertTrue(message.isOperationalNotification());
    }

    @Test
    void shouldCreateScheduledNotification() {
        NotificationMessage message = NotificationMessage.scheduled(
                "NTF-002",
                NotificationType.DOCUMENT_REQUESTED,
                NotificationChannel.EMAIL,
                NotificationRecipientType.CUSTOMER_CONTACT,
                "customer@example.com",
                NotificationPriority.NORMAL,
                "Documento richiesto",
                "Carica il documento CMR.",
                scheduledAt(),
                Notes.empty()
        );

        assertTrue(message.isScheduled());
        assertEquals(scheduledAt(), message.getScheduledAt());
        assertTrue(message.hasScheduledAt());
    }

    @Test
    void shouldNormalizeNotificationNumberAndTrimText() {
        NotificationMessage message = NotificationMessage.draft(
                "  ntf_001  ",
                NotificationType.SHIPMENT_PLANNED,
                NotificationChannel.EMAIL,
                NotificationRecipientType.CUSTOMER_CONTACT,
                "  customer@example.com  ",
                NotificationPriority.NORMAL,
                "  Spedizione pianificata  ",
                "  La spedizione è stata pianificata.  ",
                Notes.empty()
        );

        assertEquals("NTF_001", message.getNotificationNumber());
        assertEquals("customer@example.com", message.getRecipientReference());
        assertEquals("Spedizione pianificata", message.getSubject());
        assertEquals("La spedizione è stata pianificata.", message.getBody());
    }

    @Test
    void shouldRejectInvalidNotificationNumber() {
        assertThrows(IllegalArgumentException.class, () -> NotificationMessage.draft(
                null,
                NotificationType.SHIPMENT_DELAYED,
                NotificationChannel.EMAIL,
                NotificationRecipientType.CUSTOMER_CONTACT,
                "customer@example.com",
                NotificationPriority.HIGH,
                "Ritardo spedizione",
                "La spedizione SHP-001 è in ritardo.",
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> NotificationMessage.draft(
                "NTF 001",
                NotificationType.SHIPMENT_DELAYED,
                NotificationChannel.EMAIL,
                NotificationRecipientType.CUSTOMER_CONTACT,
                "customer@example.com",
                NotificationPriority.HIGH,
                "Ritardo spedizione",
                "La spedizione SHP-001 è in ritardo.",
                Notes.empty()
        ));
    }

    @Test
    void shouldRejectInvalidRecipientReference() {
        assertThrows(IllegalArgumentException.class, () -> NotificationMessage.draft(
                "NTF-001",
                NotificationType.SHIPMENT_DELAYED,
                NotificationChannel.EMAIL,
                NotificationRecipientType.CUSTOMER_CONTACT,
                "customer example.com",
                NotificationPriority.HIGH,
                "Ritardo spedizione",
                "La spedizione SHP-001 è in ritardo.",
                Notes.empty()
        ));
    }

    @Test
    void shouldRejectNullMandatoryFields() {
        assertThrows(IllegalArgumentException.class, () -> NotificationMessage.draft(
                "NTF-001",
                null,
                NotificationChannel.EMAIL,
                NotificationRecipientType.CUSTOMER_CONTACT,
                "customer@example.com",
                NotificationPriority.HIGH,
                "Ritardo spedizione",
                "La spedizione SHP-001 è in ritardo.",
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> NotificationMessage.draft(
                "NTF-001",
                NotificationType.SHIPMENT_DELAYED,
                null,
                NotificationRecipientType.CUSTOMER_CONTACT,
                "customer@example.com",
                NotificationPriority.HIGH,
                "Ritardo spedizione",
                "La spedizione SHP-001 è in ritardo.",
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> NotificationMessage.draft(
                "NTF-001",
                NotificationType.SHIPMENT_DELAYED,
                NotificationChannel.EMAIL,
                null,
                "customer@example.com",
                NotificationPriority.HIGH,
                "Ritardo spedizione",
                "La spedizione SHP-001 è in ritardo.",
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> NotificationMessage.draft(
                "NTF-001",
                NotificationType.SHIPMENT_DELAYED,
                NotificationChannel.EMAIL,
                NotificationRecipientType.CUSTOMER_CONTACT,
                "customer@example.com",
                null,
                "Ritardo spedizione",
                "La spedizione SHP-001 è in ritardo.",
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> NotificationMessage.draft(
                "NTF-001",
                NotificationType.SHIPMENT_DELAYED,
                NotificationChannel.EMAIL,
                NotificationRecipientType.CUSTOMER_CONTACT,
                "customer@example.com",
                NotificationPriority.HIGH,
                null,
                "La spedizione SHP-001 è in ritardo.",
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> NotificationMessage.draft(
                "NTF-001",
                NotificationType.SHIPMENT_DELAYED,
                NotificationChannel.EMAIL,
                NotificationRecipientType.CUSTOMER_CONTACT,
                "customer@example.com",
                NotificationPriority.HIGH,
                "Ritardo spedizione",
                null,
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> NotificationMessage.draft(
                "NTF-001",
                NotificationType.SHIPMENT_DELAYED,
                NotificationChannel.EMAIL,
                NotificationRecipientType.CUSTOMER_CONTACT,
                "customer@example.com",
                NotificationPriority.HIGH,
                "Ritardo spedizione",
                "La spedizione SHP-001 è in ritardo.",
                null
        ));
    }

    @Test
    void shouldMoveThroughLifecycle() {
        NotificationMessage draft = draftMessage();
        NotificationMessage scheduled = draft.schedule(scheduledAt());
        NotificationMessage sent = scheduled.send(sentAt());

        assertTrue(scheduled.isScheduled());
        assertTrue(sent.isSent());
        assertTrue(sent.isTerminal());
        assertTrue(sent.isDelivered());
        assertEquals(sentAt(), sent.getSentAt());
    }

    @Test
    void shouldSendDraftImmediately() {
        NotificationMessage sent = draftMessage().send(Instant.parse("2026-07-01T08:00:00Z"));

        assertTrue(sent.isSent());
        assertTrue(sent.hasSentAt());
    }

    @Test
    void shouldFailOrCancelNonTerminalNotification() {
        NotificationMessage draft = draftMessage();
        NotificationMessage scheduled = draft.schedule(scheduledAt());

        assertTrue(draft.fail().isFailed());
        assertTrue(scheduled.cancel().isCancelled());
    }

    @Test
    void shouldNotAllowInvalidLifecycleTransitions() {
        NotificationMessage sent = draftMessage().send(sentAt());
        NotificationMessage cancelled = draftMessage().cancel();

        assertThrows(IllegalStateException.class, () -> sent.schedule(scheduledAt()));
        assertThrows(IllegalStateException.class, () -> sent.send(sentAt()));
        assertThrows(IllegalStateException.class, sent::fail);
        assertThrows(IllegalStateException.class, sent::cancel);

        assertThrows(IllegalStateException.class, () -> cancelled.schedule(scheduledAt()));
        assertThrows(IllegalStateException.class, () -> cancelled.send(sentAt()));
    }

    @Test
    void shouldRejectSentBeforeScheduledAt() {
        NotificationMessage scheduled = draftMessage().schedule(scheduledAt());

        assertThrows(IllegalArgumentException.class, () -> scheduled.send(
                Instant.parse("2026-07-01T09:00:00Z")
        ));
    }

    @Test
    void shouldDetectNotes() {
        NotificationMessage message = NotificationMessage.draft(
                "NTF-001",
                NotificationType.SHIPMENT_DELAYED,
                NotificationChannel.EMAIL,
                NotificationRecipientType.CUSTOMER_CONTACT,
                "customer@example.com",
                NotificationPriority.HIGH,
                "Ritardo spedizione",
                "La spedizione SHP-001 è in ritardo.",
                Notes.of("Notifica importante")
        );

        assertTrue(message.hasNotes());
    }

    @Test
    void shouldFormatSingleLine() {
        assertEquals(
                "NTF-001 - SHIPMENT_DELAYED - CUSTOMER_CONTACT:customer@example.com - EMAIL - DRAFT",
                draftMessage().formatSingleLine()
        );
    }

    @Test
    void shouldConsiderEquivalentNotificationsEqual() {
        NotificationMessage first = draftMessage();
        NotificationMessage second = draftMessage();

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void shouldExposeEnumDetails() {
        assertTrue(NotificationType.SHIPMENT_DELAYED.isCustomerVisible());
        assertTrue(NotificationType.SHIPMENT_DELAYED.isOperational());
        assertTrue(NotificationType.INVOICE_ISSUED.isFinancial());
        assertTrue(NotificationType.SECURITY_ALERT.isSecurity());

        assertTrue(NotificationChannel.EMAIL.isExternalChannel());
        assertTrue(NotificationChannel.SMS.isRealTime());
        assertFalse(NotificationChannel.IN_APP.isExternalChannel());

        assertTrue(NotificationRecipientType.CUSTOMER_CONTACT.isHumanRecipient());
        assertFalse(NotificationRecipientType.INTEGRATION.isHumanRecipient());

        assertTrue(NotificationPriority.URGENT.requiresImmediateAttention());
        assertTrue(NotificationPriority.HIGH.isAtLeast(NotificationPriority.NORMAL));

        assertFalse(NotificationStatus.DRAFT.isTerminal());
        assertTrue(NotificationStatus.SENT.isDelivered());
        assertTrue(NotificationStatus.FAILED.isTerminal());
    }

    private static NotificationMessage draftMessage() {
        return NotificationMessage.draft(
                "NTF-001",
                NotificationType.SHIPMENT_DELAYED,
                NotificationChannel.EMAIL,
                NotificationRecipientType.CUSTOMER_CONTACT,
                "customer@example.com",
                NotificationPriority.HIGH,
                "Ritardo spedizione",
                "La spedizione SHP-001 è in ritardo.",
                Notes.empty()
        );
    }

    private static Instant scheduledAt() {
        return Instant.parse("2026-07-01T10:00:00Z");
    }

    private static Instant sentAt() {
        return Instant.parse("2026-07-01T10:05:00Z");
    }
}
