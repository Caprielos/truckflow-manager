package it.gabriele.truckflow.domain.notification;

import static org.junit.jupiter.api.Assertions.*;

import it.gabriele.truckflow.domain.shared.Notes;
import java.time.Instant;
import org.junit.jupiter.api.Test;

/** Testa NotificationRules. */
class NotificationRulesTest {

  @Test
  void shouldCheckLifecycleRules() {
    NotificationMessage draft = draftMessage();
    NotificationMessage scheduled = draft.schedule(scheduledAt());
    NotificationMessage sent = scheduled.send(sentAt());
    NotificationMessage failed = draft.fail();
    NotificationMessage cancelled = draft.cancel();

    assertTrue(NotificationRules.canBeScheduled(draft));
    assertFalse(NotificationRules.canBeScheduled(scheduled));

    assertTrue(NotificationRules.canBeSent(draft));
    assertTrue(NotificationRules.canBeSent(scheduled));
    assertFalse(NotificationRules.canBeSent(sent));

    assertTrue(NotificationRules.canBeFailed(draft));
    assertTrue(NotificationRules.canBeFailed(scheduled));
    assertFalse(NotificationRules.canBeFailed(sent));

    assertTrue(NotificationRules.canBeCancelled(draft));
    assertTrue(NotificationRules.canBeCancelled(scheduled));
    assertFalse(NotificationRules.canBeCancelled(sent));

    assertTrue(NotificationRules.isTerminal(sent));
    assertTrue(NotificationRules.isTerminal(failed));
    assertTrue(NotificationRules.isTerminal(cancelled));
  }

  @Test
  void shouldDetectImmediateAttention() {
    assertTrue(NotificationRules.requiresImmediateAttention(draftMessage()));

    NotificationMessage normalMessage =
        NotificationMessage.draft(
            "NTF-002",
            NotificationType.SHIPMENT_PLANNED,
            NotificationChannel.EMAIL,
            NotificationRecipientType.CUSTOMER_CONTACT,
            "customer@example.com",
            NotificationPriority.NORMAL,
            "Spedizione pianificata",
            "La spedizione SHP-001 è stata pianificata.",
            Notes.empty());

    NotificationMessage securityMessage =
        NotificationMessage.draft(
            "NTF-003",
            NotificationType.SECURITY_ALERT,
            NotificationChannel.IN_APP,
            NotificationRecipientType.ADMIN,
            "ADMIN-001",
            NotificationPriority.NORMAL,
            "Alert sicurezza",
            "Accesso non autorizzato rilevato.",
            Notes.empty());

    assertFalse(NotificationRules.requiresImmediateAttention(normalMessage));
    assertTrue(NotificationRules.requiresImmediateAttention(securityMessage));
  }

  @Test
  void shouldCheckCustomerNotification() {
    NotificationMessage customerMessage = draftMessage();

    NotificationMessage internalMessage =
        NotificationMessage.draft(
            "NTF-002",
            NotificationType.MAINTENANCE_ALERT,
            NotificationChannel.IN_APP,
            NotificationRecipientType.DISPATCHER,
            "DISPATCHER-001",
            NotificationPriority.HIGH,
            "Manutenzione mezzo",
            "Il mezzo TRUCK-001 richiede manutenzione.",
            Notes.empty());

    assertTrue(NotificationRules.shouldNotifyCustomer(customerMessage));
    assertFalse(NotificationRules.shouldNotifyCustomer(internalMessage));
  }

  @Test
  void shouldCheckNotificationCategories() {
    NotificationMessage operational = draftMessage();

    NotificationMessage financial =
        NotificationMessage.draft(
            "NTF-002",
            NotificationType.INVOICE_ISSUED,
            NotificationChannel.EMAIL,
            NotificationRecipientType.CUSTOMER_CONTACT,
            "customer@example.com",
            NotificationPriority.NORMAL,
            "Fattura emessa",
            "La fattura INV-001 è stata emessa.",
            Notes.empty());

    NotificationMessage security =
        NotificationMessage.draft(
            "NTF-003",
            NotificationType.SECURITY_ALERT,
            NotificationChannel.IN_APP,
            NotificationRecipientType.ADMIN,
            "ADMIN-001",
            NotificationPriority.URGENT,
            "Alert sicurezza",
            "Login fallito ripetuto.",
            Notes.empty());

    assertTrue(NotificationRules.isOperationalNotification(operational));
    assertTrue(NotificationRules.isFinancialNotification(financial));
    assertTrue(NotificationRules.isSecurityNotification(security));
    assertTrue(NotificationRules.usesExternalChannel(financial));
    assertFalse(NotificationRules.usesExternalChannel(security));
  }

  @Test
  void shouldNotAllowNullMessage() {
    assertThrows(IllegalArgumentException.class, () -> NotificationRules.canBeScheduled(null));
    assertThrows(IllegalArgumentException.class, () -> NotificationRules.canBeSent(null));
    assertThrows(IllegalArgumentException.class, () -> NotificationRules.canBeFailed(null));
    assertThrows(IllegalArgumentException.class, () -> NotificationRules.canBeCancelled(null));
    assertThrows(IllegalArgumentException.class, () -> NotificationRules.isTerminal(null));
    assertThrows(
        IllegalArgumentException.class, () -> NotificationRules.requiresImmediateAttention(null));
    assertThrows(
        IllegalArgumentException.class, () -> NotificationRules.shouldNotifyCustomer(null));
    assertThrows(
        IllegalArgumentException.class, () -> NotificationRules.isOperationalNotification(null));
    assertThrows(
        IllegalArgumentException.class, () -> NotificationRules.isFinancialNotification(null));
    assertThrows(
        IllegalArgumentException.class, () -> NotificationRules.isSecurityNotification(null));
    assertThrows(IllegalArgumentException.class, () -> NotificationRules.usesExternalChannel(null));
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
        Notes.empty());
  }

  private static Instant scheduledAt() {
    return Instant.parse("2026-07-01T10:00:00Z");
  }

  private static Instant sentAt() {
    return Instant.parse("2026-07-01T10:05:00Z");
  }
}
