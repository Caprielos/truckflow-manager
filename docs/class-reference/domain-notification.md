# Package `domain.notification`

Notifiche operative: canale, destinatario, priorità e stato.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| NotificationChannel | enum | Enum: insieme chiuso di valori ammessi dal dominio. | isExternalChannel, isRealTime |
| NotificationMessage | class | Classe del package domain.notification; rappresenta un concetto del modello TruckFlow. | draft, scheduled, schedule, send, fail, cancel, getNotificationNumber, getType, getChannel, getRecipientType |
| NotificationPriority | enum | Enum: insieme chiuso di valori ammessi dal dominio. | getLevel, requiresImmediateAttention, isAtLeast |
| NotificationRecipientType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | isHumanRecipient |
| NotificationRules | class | Classe di regole per validare e calcolare comportamenti nel package domain.notification. | canBeScheduled, canBeSent, canBeFailed, canBeCancelled, isTerminal, requiresImmediateAttention, shouldNotifyCustomer, isOperationalNotification, isFinancialNotification, isSecurityNotification |
| NotificationStatus | enum | Enum di stato del ciclo di vita. | isTerminal, isDelivered |
| NotificationType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | isCustomerVisible, isOperational, isFinancial, isSecurity |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
