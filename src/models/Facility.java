package models;

public abstract class Facility implements Inspectable {
    private static int maxId = 0;
    private int id;
    private boolean underInspection;
    private String lastInspectionResult;
    private String name;


    public Facility (boolean underInspection, String lastInspectionResult, String name) {
        this.id = ++maxId;
        this.underInspection = underInspection;
        this.lastInspectionResult = lastInspectionResult;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Facility other = (Facility) o;
        return this.getId() == other.getId();
    }

    @Override
    public void performInspection() {

    }

    @Override
    public void endInspection(boolean passResult) {

    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getLastInspectionResult() {
        return lastInspectionResult;
    }

    public void setLastInspectionResult(String lastInspectionResult) {
        this.lastInspectionResult = lastInspectionResult;
    }

    public boolean isUnderInspection() {
        return underInspection;
    }

    public void setUnderInspection(boolean underInspection) {
        this.underInspection = underInspection;
    }
}
