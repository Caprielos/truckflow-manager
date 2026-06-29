package it.gabriele.truckflow.application.usecase;

import it.gabriele.truckflow.application.port.in.CalculateDriverMissionPayrollUseCase;
import it.gabriele.truckflow.application.port.out.DriverMissionPayrollRepository;
import it.gabriele.truckflow.application.port.out.DriverMissionWorkReportRepository;
import it.gabriele.truckflow.application.port.out.DriverPayrollPolicyRepository;
import it.gabriele.truckflow.domain.payroll.DriverMissionPayroll;
import it.gabriele.truckflow.domain.payroll.DriverMissionWorkReport;
import it.gabriele.truckflow.domain.payroll.DriverPayrollPolicy;
import it.gabriele.truckflow.domain.payroll.DriverPayrollRules;
import java.util.Objects;

/** Caso d'uso: trasformare ore, qualifiche, merci e convoglio in costo autista. */
public final class DefaultCalculateDriverMissionPayrollUseCase
    implements CalculateDriverMissionPayrollUseCase {

  private final DriverMissionWorkReportRepository workReportRepository;
  private final DriverPayrollPolicyRepository payrollPolicyRepository;
  private final DriverMissionPayrollRepository payrollRepository;

  public DefaultCalculateDriverMissionPayrollUseCase(
      DriverMissionWorkReportRepository workReportRepository,
      DriverPayrollPolicyRepository payrollPolicyRepository,
      DriverMissionPayrollRepository payrollRepository) {
    this.workReportRepository =
        Objects.requireNonNull(
            workReportRepository, "Il repository report lavoro autista è obbligatorio.");
    this.payrollPolicyRepository =
        Objects.requireNonNull(
            payrollPolicyRepository, "Il repository policy paga è obbligatorio.");
    this.payrollRepository =
        Objects.requireNonNull(payrollRepository, "Il repository payroll è obbligatorio.");
  }

  @Override
  public DriverMissionPayroll handle(Command command) {
    Objects.requireNonNull(command, "Il comando payroll missione è obbligatorio.");
    DriverMissionWorkReport report =
        workReportRepository.getRequired(command.workReportId(), "Report lavoro autista");
    DriverPayrollPolicy policy =
        payrollPolicyRepository.getRequired(command.payrollPolicyId(), "Politica paga autista");
    DriverMissionPayroll payroll =
        DriverPayrollRules.calculateMissionPayroll(
            command.payrollCode(), report, policy, command.notes());
    payrollRepository.save(payroll);
    return payroll;
  }
}
