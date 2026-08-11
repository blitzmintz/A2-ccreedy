package models;

public class Toilet extends Facility implements Inspectable {
    private final String facilityType = "Toilet";
    private boolean hasChildFacilities;
    private boolean hasDisabledFacilities;


    public Toilet(boolean underInspection, String lastInspectionResult, String name) {
        super(underInspection, lastInspectionResult, name);
    }

    public Toilet(boolean underInspection, String lastInspectionResult, String name, boolean hasChildFacilities, boolean hasDisabledFacilities) {
        super(underInspection, lastInspectionResult, name);
        this.hasChildFacilities = hasChildFacilities;
        this.hasDisabledFacilities = hasDisabledFacilities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Toilet other = (Toilet) o;
        return this.getId() == other.getId();
    }

    @Override
    public void performInspection() {
        setUnderInspection(true);
        System.out.println(this.getName() + " has been closed for an inspection.");
    }

    @Override
    public void endInspection(String passResult) {
        setUnderInspection(false);
        setLastInspectionResult(passResult);
        System.out.println(this.getName() + " is no longer under inspection. Inspection Passed?: " + this.getLastInspectionResult());
    }

    public boolean isHasChildFacilities() {
        return hasChildFacilities;
    }

    public boolean isHasDisabledFacilities() {
        return hasDisabledFacilities;
    }

    public void setHasChildFacilities(boolean childFacilities) {
        this.hasChildFacilities = childFacilities;
    }

    public void setHasDisabledFacilities(boolean disabledFacilities) {
        this.hasDisabledFacilities = disabledFacilities;
    }
}
