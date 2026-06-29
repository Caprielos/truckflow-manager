package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.payroll.DriverMissionPayroll;
import it.gabriele.truckflow.domain.shared.Notes;

public interface CalculateDriverMissionPayrollUseCase {

    DriverMissionPayroll handle(Command command);

    record Command(String payrollCode, String workReportId, String payrollPolicyId, Notes notes) {
    }
}
