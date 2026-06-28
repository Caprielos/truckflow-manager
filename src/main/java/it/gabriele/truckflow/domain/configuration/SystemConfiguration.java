package it.gabriele.truckflow.domain.configuration;

import it.gabriele.truckflow.domain.shared.Notes;

import java.util.Objects;

/**
 * Configurazione applicativa.
 */
public final class SystemConfiguration {

    private static final int MAX_KEY_LENGTH = 100;
    private static final int MAX_SCOPE_REFERENCE_LENGTH = 80;
    private static final int MAX_DESCRIPTION_LENGTH = 250;

    private final String configurationKey;
    private final ConfigurationCategory category;
    private final ConfigurationScope scope;
    private final String scopeReference;
    private final ConfigurationValue value;
    private final String description;
    private final boolean active;
    private final Notes notes;

    private SystemConfiguration(
            String configurationKey,
            ConfigurationCategory category,
            ConfigurationScope scope,
            String scopeReference,
            ConfigurationValue value,
            String description,
            boolean active,
            Notes notes
    ) {
        this.configurationKey = validateConfigurationKey(configurationKey);

        if (category == null) {
            throw new IllegalArgumentException("La categoria configurazione è obbligatoria.");
        }

        if (scope == null) {
            throw new IllegalArgumentException("L'ambito configurazione è obbligatorio.");
        }

        this.scopeReference = validateScopeReference(scope, scopeReference);

        if (value == null) {
            throw new IllegalArgumentException("Il valore configurazione è obbligatorio.");
        }

        this.description = validateDescription(description);

        if (notes == null) {
            throw new IllegalArgumentException("Le note configurazione sono obbligatorie.");
        }

        this.category = category;
        this.scope = scope;
        this.value = value;
        this.active = active;
        this.notes = notes;
    }

    public static SystemConfiguration activeGlobal(
            String configurationKey,
            ConfigurationCategory category,
            ConfigurationValue value,
            String description,
            Notes notes
    ) {
        return new SystemConfiguration(
                configurationKey,
                category,
                ConfigurationScope.GLOBAL,
                "GLOBAL",
                value,
                description,
                true,
                notes
        );
    }

    public static SystemConfiguration activeForScope(
            String configurationKey,
            ConfigurationCategory category,
            ConfigurationScope scope,
            String scopeReference,
            ConfigurationValue value,
            String description,
            Notes notes
    ) {
        return new SystemConfiguration(
                configurationKey,
                category,
                scope,
                scopeReference,
                value,
                description,
                true,
                notes
        );
    }

    public static SystemConfiguration inactiveForScope(
            String configurationKey,
            ConfigurationCategory category,
            ConfigurationScope scope,
            String scopeReference,
            ConfigurationValue value,
            String description,
            Notes notes
    ) {
        return new SystemConfiguration(
                configurationKey,
                category,
                scope,
                scopeReference,
                value,
                description,
                false,
                notes
        );
    }

    private static String validateConfigurationKey(String configurationKey) {
        if (configurationKey == null) {
            throw new IllegalArgumentException("La chiave configurazione è obbligatoria.");
        }

        String normalizedKey = configurationKey.trim().toUpperCase();

        if (normalizedKey.isEmpty()) {
            throw new IllegalArgumentException("La chiave configurazione non può essere vuota.");
        }

        if (normalizedKey.length() > MAX_KEY_LENGTH) {
            throw new IllegalArgumentException("La chiave configurazione non può superare "
                    + MAX_KEY_LENGTH + " caratteri.");
        }

        if (!normalizedKey.matches("[A-Z0-9_.-]+")) {
            throw new IllegalArgumentException("La chiave configurazione può contenere solo lettere, numeri, punti, trattini e underscore.");
        }

        return normalizedKey;
    }

    private static String validateScopeReference(
            ConfigurationScope scope,
            String scopeReference
    ) {
        if (scope.isGlobal()) {
            return "GLOBAL";
        }

        if (scopeReference == null) {
            throw new IllegalArgumentException("Il riferimento ambito configurazione è obbligatorio.");
        }

        String normalizedScopeReference = scopeReference.trim().toUpperCase();

        if (normalizedScopeReference.isEmpty()) {
            throw new IllegalArgumentException("Il riferimento ambito configurazione non può essere vuoto.");
        }

        if (normalizedScopeReference.length() > MAX_SCOPE_REFERENCE_LENGTH) {
            throw new IllegalArgumentException("Il riferimento ambito configurazione non può superare "
                    + MAX_SCOPE_REFERENCE_LENGTH + " caratteri.");
        }

        if (!normalizedScopeReference.matches("[A-Z0-9_.-]+")) {
            throw new IllegalArgumentException("Il riferimento ambito configurazione può contenere solo lettere, numeri, punti, trattini e underscore.");
        }

        return normalizedScopeReference;
    }

    private static String validateDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException("La descrizione configurazione è obbligatoria.");
        }

        String normalizedDescription = description.trim();

        if (normalizedDescription.isEmpty()) {
            throw new IllegalArgumentException("La descrizione configurazione non può essere vuota.");
        }

        if (normalizedDescription.length() > MAX_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException("La descrizione configurazione non può superare "
                    + MAX_DESCRIPTION_LENGTH + " caratteri.");
        }

        return normalizedDescription;
    }

    public SystemConfiguration activate() {
        return copyWith(active, value, true);
    }

    public SystemConfiguration deactivate() {
        return copyWith(active, value, false);
    }

    public SystemConfiguration changeValue(ConfigurationValue newValue) {
        if (newValue == null) {
            throw new IllegalArgumentException("Il nuovo valore configurazione è obbligatorio.");
        }

        return copyWith(active, newValue, active);
    }

    private SystemConfiguration copyWith(
            boolean currentActive,
            ConfigurationValue newValue,
            boolean newActive
    ) {
        return new SystemConfiguration(
                configurationKey,
                category,
                scope,
                scopeReference,
                newValue,
                description,
                newActive,
                notes
        );
    }

    public String getConfigurationKey() {
        return configurationKey;
    }

    public ConfigurationCategory getCategory() {
        return category;
    }

    public ConfigurationScope getScope() {
        return scope;
    }

    public String getScopeReference() {
        return scopeReference;
    }

    public ConfigurationValue getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

    public Notes getNotes() {
        return notes;
    }

    public boolean isGlobal() {
        return scope.isGlobal();
    }

    public boolean isSensitive() {
        return category.isSensitive()
                || configurationKey.contains("PASSWORD")
                || configurationKey.contains("SECRET")
                || configurationKey.contains("TOKEN")
                || configurationKey.contains("API_KEY");
    }

    public boolean isPricingConfiguration() {
        return category == ConfigurationCategory.PRICING;
    }

    public boolean isSecurityConfiguration() {
        return category == ConfigurationCategory.SECURITY;
    }

    public boolean hasSameKey(SystemConfiguration other) {
        if (other == null) {
            throw new IllegalArgumentException("La configurazione da confrontare è obbligatoria.");
        }

        return configurationKey.equals(other.configurationKey);
    }

    public boolean hasSameCategory(SystemConfiguration other) {
        if (other == null) {
            throw new IllegalArgumentException("La configurazione da confrontare è obbligatoria.");
        }

        return category == other.category;
    }

    public boolean isForScope(
            ConfigurationScope scope,
            String scopeReference
    ) {
        if (scope == null) {
            throw new IllegalArgumentException("L'ambito da verificare è obbligatorio.");
        }

        String normalizedReference = validateScopeReference(scope, scopeReference);

        return this.scope == scope && this.scopeReference.equals(normalizedReference);
    }

    public boolean hasNotes() {
        return notes.hasText();
    }

    public String formatSingleLine() {
        return configurationKey
                + " - " + category
                + " - " + scope + ":" + scopeReference
                + " - " + value
                + " - active: " + active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SystemConfiguration that)) return false;
        return active == that.active
                && configurationKey.equals(that.configurationKey)
                && category == that.category
                && scope == that.scope
                && scopeReference.equals(that.scopeReference)
                && value.equals(that.value)
                && description.equals(that.description)
                && notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(configurationKey, category, scope, scopeReference, value, description, active, notes);
    }

    @Override
    public String toString() {
        return formatSingleLine();
    }
}
