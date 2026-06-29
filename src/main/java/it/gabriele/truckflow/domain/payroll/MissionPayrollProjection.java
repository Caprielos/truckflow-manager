package it.gabriele.truckflow.domain.payroll;

import it.gabriele.truckflow.domain.economics.MissionCostLine;
import it.gabriele.truckflow.domain.shared.Notes;

import java.util.Objects;

/**
 * Proiezione che collega il payroll autista a una riga costo missione.
 */
public final class MissionPayrollProjection {

    private final DriverMissionPayroll payroll;
    private final MissionCostLine missionCostLine;

    private MissionPayrollProjection(DriverMissionPayroll payroll, MissionCostLine missionCostLine) {
        if (payroll == null) {
            throw new IllegalArgumentException("Il payroll missione è obbligatorio.");
        }
        if (missionCostLine == null) {
            throw new IllegalArgumentException("La riga costo missione da payroll è obbligatoria.");
        }
        this.payroll = payroll;
        this.missionCostLine = missionCostLine;
    }

    public static MissionPayrollProjection fromPayroll(DriverMissionPayroll payroll, String costLineCode, Notes notes) {
        if (payroll == null) {
            throw new IllegalArgumentException("Il payroll missione è obbligatorio.");
        }
        return new MissionPayrollProjection(payroll, payroll.toMissionCostLine(costLineCode, notes));
    }

    public DriverMissionPayroll getPayroll() {
        return payroll;
    }

    public MissionCostLine getMissionCostLine() {
        return missionCostLine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MissionPayrollProjection that)) return false;
        return payroll.equals(that.payroll) && missionCostLine.equals(that.missionCostLine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(payroll, missionCostLine);
    }
}
