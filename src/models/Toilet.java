package models;

public class Toilet extends Facility implements Inspectable {
    private final String facilityType = "Toilet";
    private boolean hasChildFacilities;
    private boolean hasDisabledFacilities;

    public Toilet(String name) {
        super(false, name);
    }

    public Toilet(boolean underInspection, String name) {
        super(underInspection, name);
    }

    public Toilet(boolean underInspection, String name, String status) {
        super(underInspection, name, status);
    }

    public Toilet(String name, boolean hasChildFacilities, boolean hasDisabledFacilities) {
        super(false, name);
        this.hasChildFacilities = hasChildFacilities;
        this.hasDisabledFacilities = hasDisabledFacilities;
    }


    public Toilet(boolean underInspection, String name, boolean hasChildFacilities, boolean hasDisabledFacilities, String status) {
        super(underInspection, name, status);
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
    public void performInspection(Inspection inspection) {
        this.addInspection(inspection);
        this.setStatus("Closed");
        setUnderInspection(true);
        System.out.println(this.getName() + " has been closed for an inspection.");
    }

    @Override
    public void endInspection(String passResult) {
        setUnderInspection(false);
        if (validateInspectionResult(passResult) == "Pass") {
            setStatus("Open");
        }
        System.out.println(this.getName() + " is no longer under inspection. Inspection Result: " + this.getLastInspectionResult());
    }

    @Override
    public String getLastInspectedByName() {
        try {
            return getListOfInspections().peekLast().getInspectedBy().getFullName();
        } catch (NullPointerException e) {
            System.out.println("There is no employee name against the last inspection for this toilet!");
        }
        return "Unknown";
    }

    @Override
    public Employee getLastInspectedByObject() {
        try {
            return getListOfInspections().peekLast().getInspectedBy();
        } catch (NullPointerException e) {
            System.out.println("There is no employee against the last inspection for this toilet!");
        }
        return null;
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
