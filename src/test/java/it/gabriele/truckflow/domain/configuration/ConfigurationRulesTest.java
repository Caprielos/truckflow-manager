package it.gabriele.truckflow.domain.configuration;

import it.gabriele.truckflow.domain.shared.Notes;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa ConfigurationRules.
 */
class ConfigurationRulesTest {

    @Test
    void shouldCheckIfConfigurationCanBeApplied() {
        assertTrue(ConfigurationRules.canBeApplied(globalPricingConfiguration()));

        SystemConfiguration inactive = SystemConfiguration.inactiveForScope(
                "PRICING.FUEL_SURCHARGE_PERCENTAGE",
                ConfigurationCategory.PRICING,
                ConfigurationScope.CUSTOMER,
                "CUST-001",
                ConfigurationValue.ofPercentage("12"),
                "Supplemento carburante cliente",
                Notes.empty()
        );

        assertFalse(ConfigurationRules.canBeApplied(inactive));
    }

    @Test
    void shouldAllowScopedConfigurationToOverrideGlobalConfiguration() {
        assertTrue(ConfigurationRules.canOverride(
                globalPricingConfiguration(),
                customerPricingConfiguration()
        ));
    }

    @Test
    void shouldRejectInvalidOverride() {
        SystemConfiguration differentType = SystemConfiguration.activeForScope(
                "PRICING.FUEL_SURCHARGE_PERCENTAGE",
                ConfigurationCategory.PRICING,
                ConfigurationScope.CUSTOMER,
                "CUST-001",
                ConfigurationValue.ofText("twelve"),
                "Supplemento carburante cliente",
                Notes.empty()
        );

        SystemConfiguration differentKey = SystemConfiguration.activeForScope(
                "PRICING.TOLL_SURCHARGE_PERCENTAGE",
                ConfigurationCategory.PRICING,
                ConfigurationScope.CUSTOMER,
                "CUST-001",
                ConfigurationValue.ofPercentage("12"),
                "Supplemento pedaggi cliente",
                Notes.empty()
        );

        assertFalse(ConfigurationRules.canOverride(customerPricingConfiguration(), globalPricingConfiguration()));
        assertFalse(ConfigurationRules.canOverride(globalPricingConfiguration(), differentType));
        assertFalse(ConfigurationRules.canOverride(globalPricingConfiguration(), differentKey));
    }

    @Test
    void shouldCheckApplicabilityToScope() {
        assertTrue(ConfigurationRules.isApplicableTo(
                globalPricingConfiguration(),
                ConfigurationScope.CUSTOMER,
                "CUST-001"
        ));

        assertTrue(ConfigurationRules.isApplicableTo(
                customerPricingConfiguration(),
                ConfigurationScope.CUSTOMER,
                "cust-001"
        ));

        assertFalse(ConfigurationRules.isApplicableTo(
                customerPricingConfiguration(),
                ConfigurationScope.CUSTOMER,
                "CUST-999"
        ));
    }

    @Test
    void shouldCheckConfigurationKinds() {
        SystemConfiguration security = securityConfiguration();

        assertTrue(ConfigurationRules.isPricingConfiguration(globalPricingConfiguration()));
        assertTrue(ConfigurationRules.isSecurityConfiguration(security));
        assertTrue(ConfigurationRules.isSensitiveConfiguration(security));
        assertTrue(ConfigurationRules.requiresRestrictedAccess(security));
        assertTrue(ConfigurationRules.isNumericConfiguration(globalPricingConfiguration()));
    }

    @Test
    void shouldDetectSensitiveTokenConfiguration() {
        SystemConfiguration token = SystemConfiguration.activeGlobal(
                "INTEGRATION.VIAMICHELIN.API_TOKEN",
                ConfigurationCategory.INTEGRATION,
                ConfigurationValue.ofText("secret-token"),
                "Token integrazione ViaMichelin",
                Notes.empty()
        );

        assertTrue(ConfigurationRules.isSensitiveConfiguration(token));
        assertTrue(ConfigurationRules.requiresRestrictedAccess(token));
    }

    @Test
    void shouldNotAllowNullValues() {
        SystemConfiguration configuration = globalPricingConfiguration();

        assertThrows(IllegalArgumentException.class, () -> ConfigurationRules.canBeApplied(null));
        assertThrows(IllegalArgumentException.class, () -> ConfigurationRules.canOverride(null, configuration));
        assertThrows(IllegalArgumentException.class, () -> ConfigurationRules.canOverride(configuration, null));
        assertThrows(IllegalArgumentException.class, () -> ConfigurationRules.isApplicableTo(null, ConfigurationScope.CUSTOMER, "CUST-001"));
        assertThrows(IllegalArgumentException.class, () -> ConfigurationRules.isApplicableTo(configuration, null, "CUST-001"));
        assertThrows(IllegalArgumentException.class, () -> ConfigurationRules.isSensitiveConfiguration(null));
        assertThrows(IllegalArgumentException.class, () -> ConfigurationRules.requiresRestrictedAccess(null));
        assertThrows(IllegalArgumentException.class, () -> ConfigurationRules.isNumericConfiguration(null));
        assertThrows(IllegalArgumentException.class, () -> ConfigurationRules.isPricingConfiguration(null));
        assertThrows(IllegalArgumentException.class, () -> ConfigurationRules.isSecurityConfiguration(null));
    }

    private static SystemConfiguration globalPricingConfiguration() {
        return SystemConfiguration.activeGlobal(
                "PRICING.FUEL_SURCHARGE_PERCENTAGE",
                ConfigurationCategory.PRICING,
                ConfigurationValue.ofPercentage("15"),
                "Supplemento carburante standard",
                Notes.empty()
        );
    }

    private static SystemConfiguration customerPricingConfiguration() {
        return SystemConfiguration.activeForScope(
                "PRICING.FUEL_SURCHARGE_PERCENTAGE",
                ConfigurationCategory.PRICING,
                ConfigurationScope.CUSTOMER,
                "CUST-001",
                ConfigurationValue.ofPercentage("12"),
                "Supplemento carburante cliente",
                Notes.empty()
        );
    }

    private static SystemConfiguration securityConfiguration() {
        return SystemConfiguration.activeGlobal(
                "SECURITY.MAX_LOGIN_ATTEMPTS",
                ConfigurationCategory.SECURITY,
                ConfigurationValue.ofInteger(5),
                "Numero massimo tentativi login",
                Notes.empty()
        );
    }
}
