package it.gabriele.truckflow.domain.claim;

import it.gabriele.truckflow.domain.shared.Notes;

import java.util.Objects;

public final class DamageInspectionItem {

    private final String area;
    private final boolean damaged;
    private final Notes notes;

    private DamageInspectionItem(String area, boolean damaged, Notes notes) {
        if (area == null || area.trim().isEmpty()) {
            throw new IllegalArgumentException("L'area controllata è obbligatoria.");
        }
        if (notes == null) {
            throw new IllegalArgumentException("Le note controllo danni sono obbligatorie.");
        }
        this.area = area.trim();
        this.damaged = damaged;
        this.notes = notes;
    }

    public static DamageInspectionItem of(String area, boolean damaged, Notes notes) {
        return new DamageInspectionItem(area, damaged, notes);
    }

    public String getArea() {
        return area;
    }

    public boolean isDamaged() {
        return damaged;
    }

    public Notes getNotes() {
        return notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DamageInspectionItem that)) return false;
        return damaged == that.damaged && area.equals(that.area) && notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(area, damaged, notes);
    }
}
