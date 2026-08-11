package models;

public class Toilet extends Facility implements Inspectable {
    private final String facilityType = "Toilet";
    private String name;
    private boolean hasChildFacilities;
    private boolean hasDisabledFacilities;


    public Toilet(boolean underInspection, String lastInspectionResult, String name) {
        super(underInspection, lastInspectionResult);
        this.name = name;
    }

    public Toilet(boolean underInspection, String lastInspectionResult, String name, boolean hasChildFacilities, boolean hasDisabledFacilities) {
        super(underInspection, lastInspectionResult);
        this.hasChildFacilities = hasChildFacilities;
        this.hasDisabledFacilities = hasDisabledFacilities;
        this.name = name;
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
        this.setUnderInspection(true);
        System.out.println(this.name + " has been closed for an inspection.");
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
