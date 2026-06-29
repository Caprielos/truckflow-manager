package it.gabriele.truckflow.domain.notification;

import it.gabriele.truckflow.domain.shared.Notes;
import java.time.Instant;
import java.util.Objects;

/** Messaggio di notifica generato dal sistema. */
public final class NotificationMessage {

  private static final int MAX_NOTIFICATION_NUMBER_LENGTH = 50;
  private static final int MAX_RECIPIENT_REFERENCE_LENGTH = 254;
  private static final int MAX_SUBJECT_LENGTH = 150;
  private static final int MAX_BODY_LENGTH = 4000;

  private final String notificationNumber;
  private final NotificationType type;
  private final NotificationChannel channel;
  private final NotificationRecipientType recipientType;
  private final String recipientReference;
  private final NotificationPriority priority;
  private final String subject;
  private final String body;
  private final Instant scheduledAt;
  private final Instant sentAt;
  private final NotificationStatus status;
  private final Notes notes;

  private NotificationMessage(
      String notificationNumber,
      NotificationType type,
      NotificationChannel channel,
      NotificationRecipientType recipientType,
      String recipientReference,
      NotificationPriority priority,
      String subject,
      String body,
      Instant scheduledAt,
      Instant sentAt,
      NotificationStatus status,
      Notes notes) {
    this.notificationNumber = validateNotificationNumber(notificationNumber);

    if (type == null) {
      throw new IllegalArgumentException("Il tipo notifica è obbligatorio.");
    }

    if (channel == null) {
      throw new IllegalArgumentException("Il canale notifica è obbligatorio.");
    }

    if (recipientType == null) {
      throw new IllegalArgumentException("Il tipo destinatario notifica è obbligatorio.");
    }

    this.recipientReference = validateRecipientReference(recipientReference);

    if (priority == null) {
      throw new IllegalArgumentException("La priorità notifica è obbligatoria.");
    }

    this.subject = validateText(subject, MAX_SUBJECT_LENGTH, "L'oggetto notifica è obbligatorio.");
    this.body = validateText(body, MAX_BODY_LENGTH, "Il corpo notifica è obbligatorio.");

    if (status == null) {
      throw new IllegalArgumentException("Lo stato notifica è obbligatorio.");
    }

    if (notes == null) {
      throw new IllegalArgumentException("Le note notifica sono obbligatorie.");
    }

    validateSchedule(status, scheduledAt, sentAt);

    this.type = type;
    this.channel = channel;
    this.recipientType = recipientType;
    this.priority = priority;
    this.scheduledAt = scheduledAt;
    this.sentAt = sentAt;
    this.status = status;
    this.notes = notes;
  }

  public static NotificationMessage draft(
      String notificationNumber,
      NotificationType type,
      NotificationChannel channel,
      NotificationRecipientType recipientType,
      String recipientReference,
      NotificationPriority priority,
      String subject,
      String body,
      Notes notes) {
    return new NotificationMessage(
        notificationNumber,
        type,
        channel,
        recipientType,
        recipientReference,
        priority,
        subject,
        body,
        null,
        null,
        NotificationStatus.DRAFT,
        notes);
  }

  public static NotificationMessage scheduled(
      String notificationNumber,
      NotificationType type,
      NotificationChannel channel,
      NotificationRecipientType recipientType,
      String recipientReference,
      NotificationPriority priority,
      String subject,
      String body,
      Instant scheduledAt,
      Notes notes) {
    return new NotificationMessage(
        notificationNumber,
        type,
        channel,
        recipientType,
        recipientReference,
        priority,
        subject,
        body,
        scheduledAt,
        null,
        NotificationStatus.SCHEDULED,
        notes);
  }

  private static String validateNotificationNumber(String notificationNumber) {
    if (notificationNumber == null) {
      throw new IllegalArgumentException("Il numero notifica è obbligatorio.");
    }

    String normalizedNotificationNumber = notificationNumber.trim().toUpperCase();

    if (normalizedNotificationNumber.isEmpty()) {
      throw new IllegalArgumentException("Il numero notifica non può essere vuoto.");
    }

    if (normalizedNotificationNumber.length() > MAX_NOTIFICATION_NUMBER_LENGTH) {
      throw new IllegalArgumentException(
          "Il numero notifica non può superare " + MAX_NOTIFICATION_NUMBER_LENGTH + " caratteri.");
    }

    if (!normalizedNotificationNumber.matches("[A-Z0-9_-]+")) {
      throw new IllegalArgumentException(
          "Il numero notifica può contenere solo lettere, numeri, trattini e underscore.");
    }

    return normalizedNotificationNumber;
  }

  private static String validateRecipientReference(String recipientReference) {
    if (recipientReference == null) {
      throw new IllegalArgumentException("Il riferimento destinatario notifica è obbligatorio.");
    }

    String normalizedRecipientReference = recipientReference.trim();

    if (normalizedRecipientReference.isEmpty()) {
      throw new IllegalArgumentException(
          "Il riferimento destinatario notifica non può essere vuoto.");
    }

    if (normalizedRecipientReference.length() > MAX_RECIPIENT_REFERENCE_LENGTH) {
      throw new IllegalArgumentException(
          "Il riferimento destinatario notifica non può superare "
              + MAX_RECIPIENT_REFERENCE_LENGTH
              + " caratteri.");
    }

    if (!normalizedRecipientReference.matches("[A-Za-z0-9@._+\\-]+")) {
      throw new IllegalArgumentException(
          "Il riferimento destinatario contiene caratteri non validi.");
    }

    return normalizedRecipientReference;
  }

  private static String validateText(String text, int maxLength, String nullMessage) {
    if (text == null) {
      throw new IllegalArgumentException(nullMessage);
    }

    String normalizedText = text.trim();

    if (normalizedText.isEmpty()) {
      throw new IllegalArgumentException(nullMessage);
    }

    if (normalizedText.length() > maxLength) {
      throw new IllegalArgumentException("Il testo non può superare " + maxLength + " caratteri.");
    }

    return normalizedText;
  }

  private static void validateSchedule(
      NotificationStatus status, Instant scheduledAt, Instant sentAt) {
    if (status == NotificationStatus.SCHEDULED && scheduledAt == null) {
      throw new IllegalArgumentException(
          "Una notifica programmata deve avere una data di pianificazione.");
    }

    if (status == NotificationStatus.SENT && sentAt == null) {
      throw new IllegalArgumentException("Una notifica inviata deve avere una data di invio.");
    }

    if (status != NotificationStatus.SENT && sentAt != null) {
      throw new IllegalArgumentException("Solo una notifica inviata può avere una data di invio.");
    }

    if (scheduledAt != null && sentAt != null && sentAt.isBefore(scheduledAt)) {
      throw new IllegalArgumentException(
          "La data invio notifica non può essere precedente alla data pianificata.");
    }
  }

  public NotificationMessage schedule(Instant scheduledAt) {
    if (!NotificationRules.canBeScheduled(this)) {
      throw new IllegalStateException("La notifica non può essere pianificata.");
    }

    return new NotificationMessage(
        notificationNumber,
        type,
        channel,
        recipientType,
        recipientReference,
        priority,
        subject,
        body,
        scheduledAt,
        null,
        NotificationStatus.SCHEDULED,
        notes);
  }

  public NotificationMessage send(Instant sentAt) {
    if (!NotificationRules.canBeSent(this)) {
      throw new IllegalStateException("La notifica non può essere inviata.");
    }

    return new NotificationMessage(
        notificationNumber,
        type,
        channel,
        recipientType,
        recipientReference,
        priority,
        subject,
        body,
        scheduledAt,
        sentAt,
        NotificationStatus.SENT,
        notes);
  }

  public NotificationMessage fail() {
    if (!NotificationRules.canBeFailed(this)) {
      throw new IllegalStateException("La notifica non può essere marcata come fallita.");
    }

    return new NotificationMessage(
        notificationNumber,
        type,
        channel,
        recipientType,
        recipientReference,
        priority,
        subject,
        body,
        scheduledAt,
        null,
        NotificationStatus.FAILED,
        notes);
  }

  public NotificationMessage cancel() {
    if (!NotificationRules.canBeCancelled(this)) {
      throw new IllegalStateException("La notifica non può essere cancellata.");
    }

    return new NotificationMessage(
        notificationNumber,
        type,
        channel,
        recipientType,
        recipientReference,
        priority,
        subject,
        body,
        scheduledAt,
        null,
        NotificationStatus.CANCELLED,
        notes);
  }

  public String getNotificationNumber() {
    return notificationNumber;
  }

  public NotificationType getType() {
    return type;
  }

  public NotificationChannel getChannel() {
    return channel;
  }

  public NotificationRecipientType getRecipientType() {
    return recipientType;
  }

  public String getRecipientReference() {
    return recipientReference;
  }

  public NotificationPriority getPriority() {
    return priority;
  }

  public String getSubject() {
    return subject;
  }

  public String getBody() {
    return body;
  }

  public Instant getScheduledAt() {
    return scheduledAt;
  }

  public Instant getSentAt() {
    return sentAt;
  }

  public NotificationStatus getStatus() {
    return status;
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean isDraft() {
    return status == NotificationStatus.DRAFT;
  }

  public boolean isScheduled() {
    return status == NotificationStatus.SCHEDULED;
  }

  public boolean isSent() {
    return status == NotificationStatus.SENT;
  }

  public boolean isFailed() {
    return status == NotificationStatus.FAILED;
  }

  public boolean isCancelled() {
    return status == NotificationStatus.CANCELLED;
  }

  public boolean isTerminal() {
    return status.isTerminal();
  }

  public boolean isDelivered() {
    return status.isDelivered();
  }

  public boolean hasScheduledAt() {
    return scheduledAt != null;
  }

  public boolean hasSentAt() {
    return sentAt != null;
  }

  public boolean hasNotes() {
    return notes.hasText();
  }

  public boolean isCustomerVisible() {
    return type.isCustomerVisible();
  }

  public boolean isOperationalNotification() {
    return type.isOperational();
  }

  public boolean isFinancialNotification() {
    return type.isFinancial();
  }

  public boolean isSecurityNotification() {
    return type.isSecurity();
  }

  public boolean isExternalChannel() {
    return channel.isExternalChannel();
  }

  public boolean isHumanRecipient() {
    return recipientType.isHumanRecipient();
  }

  public String formatSingleLine() {
    return notificationNumber
        + " - "
        + type
        + " - "
        + recipientType
        + ":"
        + recipientReference
        + " - "
        + channel
        + " - "
        + status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof NotificationMessage that)) return false;
    return notificationNumber.equals(that.notificationNumber)
        && type == that.type
        && channel == that.channel
        && recipientType == that.recipientType
        && recipientReference.equals(that.recipientReference)
        && priority == that.priority
        && subject.equals(that.subject)
        && body.equals(that.body)
        && Objects.equals(scheduledAt, that.scheduledAt)
        && Objects.equals(sentAt, that.sentAt)
        && status == that.status
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        notificationNumber,
        type,
        channel,
        recipientType,
        recipientReference,
        priority,
        subject,
        body,
        scheduledAt,
        sentAt,
        status,
        notes);
  }

  @Override
  public String toString() {
    return formatSingleLine();
  }
}
