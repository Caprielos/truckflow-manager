package it.gabriele.truckflow.domain.cargo;

import it.gabriele.truckflow.domain.document.TransportDocumentType;
import it.gabriele.truckflow.domain.fleet.VehicleCertificateType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CargoOperationalRulesTest {

    @Test
    void shouldRequireAdrDocumentsAndVehicleApprovalForDangerousGoods() {
        assertTrue(CargoCategory.DANGEROUS_GOODS.requiresAdrData());
        assertTrue(CargoOperationalRules.requiredDocumentsFor(CargoCategory.DANGEROUS_GOODS)
                .contains(TransportDocumentType.SAFETY_DATA_SHEET));
        assertTrue(CargoOperationalRules.requiredVehicleCertificatesFor(CargoCategory.DANGEROUS_GOODS)
                .contains(VehicleCertificateType.ADR_VEHICLE_APPROVAL));
    }

    @Test
    void shouldRequireFirAndEerForWaste() {
        assertTrue(CargoOperationalRules.requiresEerCode(CargoCategory.WASTE_DANGEROUS));
        assertTrue(CargoOperationalRules.requiredDocumentsFor(CargoCategory.WASTE_DANGEROUS)
                .contains(TransportDocumentType.WASTE_IDENTIFICATION_FORM));
    }

    @Test
    void shouldRequireAtpForTemperatureControlledGoods() {
        assertTrue(CargoOperationalRules.requiredVehicleCertificatesFor(CargoCategory.REFRIGERATED_FOOD)
                .contains(VehicleCertificateType.ATP));
    }
}
