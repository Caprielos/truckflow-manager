# `domain/notification`

Messaggi, notifiche, canali e regole di invio.

## Come leggerlo

Questo package contiene concetti o regole del business. Non dovrebbe dipendere da database, web o infrastructure.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `NotificationChannel` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | externalChannel, realTime | isExternalChannel, isRealTime |
| `NotificationMessage` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_NOTIFICATION_NUMBER_LENGTH, MAX_RECIPIENT_REFERENCE_LENGTH, MAX_SUBJECT_LENGTH, MAX_BODY_LENGTH, notificationNumber, type, channel, recipientType | draft, scheduled, schedule, send, fail, cancel, getNotificationNumber, getType |
| `NotificationPriority` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | level, immediateAttention | getLevel, requiresImmediateAttention, isAtLeast |
| `NotificationRecipientType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | humanRecipient | isHumanRecipient |
| `NotificationRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | — | canBeScheduled, canBeSent, canBeFailed, canBeCancelled, isTerminal, requiresImmediateAttention, shouldNotifyCustomer, isOperationalNotification |
| `NotificationStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | terminal, delivered | isTerminal, isDelivered |
| `NotificationType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | customerVisible, operational, financial, security | isCustomerVisible, isOperational, isFinancial, isSecurity |
