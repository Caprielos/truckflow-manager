package it.gabriele.truckflow.domain.company;

import it.gabriele.truckflow.domain.cargo.CargoCategory;

import java.time.LocalDate;

/**
 * Regole aziendali: albo, REN, licenze comunitarie e rifiuti.
 */
public final class CompanyComplianceRules {

    private CompanyComplianceRules() {
    }

    public static boolean hasBaseRoadHaulageAuthorizations(TransportCompany company, LocalDate date) {
        validate(company, date);
        return company.hasValidLicense(CompanyLicenseType.ROAD_HAULAGE_REGISTER, date)
                && company.hasValidLicense(CompanyLicenseType.REN, date);
    }

    public static boolean canOperateInternationalTransport(TransportCompany company, LocalDate date) {
        validate(company, date);
        return hasBaseRoadHaulageAuthorizations(company, date)
                && company.hasValidLicense(CompanyLicenseType.COMMUNITY_LICENSE, date);
    }

    public static boolean canTransportCargo(TransportCompany company, CargoCategory cargoCategory, boolean international, LocalDate date) {
        validate(company, date);
        if (cargoCategory == null) {
            throw new IllegalArgumentException("La categoria merce è obbligatoria.");
        }
        if (international && !canOperateInternationalTransport(company, date)) {
            return false;
        }
        if (!international && !hasBaseRoadHaulageAuthorizations(company, date)
                && !company.hasValidLicense(CompanyLicenseType.OWN_ACCOUNT_LICENSE, date)) {
            return false;
        }
        if (cargoCategory.isWaste()) {
            return company.hasAnyValidEnvironmentalLicense(date);
        }
        return true;
    }

    private static void validate(TransportCompany company, LocalDate date) {
        if (company == null) {
            throw new IllegalArgumentException("L'azienda è obbligatoria.");
        }
        if (date == null) {
            throw new IllegalArgumentException("La data verifica è obbligatoria.");
        }
    }
}
