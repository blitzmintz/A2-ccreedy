package park;

import java.time.LocalDate;

public class Inspection {
    private static int maxId = 0;
    private int id;
    private LocalDate inspectionDate;
    private Employee inspectedBy;
    private String status;
    private String inspectionResult;
    private Inspectable inspectedObject;

    public Inspection(LocalDate inspectionDate, Employee inspectedBy, Inspectable inspectedObject) {
        this.id = ++maxId;
        this.inspectionDate = inspectionDate;
        this.inspectedBy = inspectedBy;
        this.inspectedObject = inspectedObject;
        this.status = "In Progress";
    }

    public Inspection(LocalDate inspectionDate, Employee inspectedBy, String status, Inspectable inspectedObject) {
        this.id = ++maxId;
        this.inspectionDate = inspectionDate;
        this.inspectedBy = inspectedBy;
        this.status = status;
        this.inspectedObject = inspectedObject;
    }

    public Inspection(LocalDate inspectionDate, Employee inspectedBy, String status, String inspectionResult, Inspectable inspectedObject) {
        this.id = ++maxId;
        this.inspectionDate = inspectionDate;
        this.inspectedBy = inspectedBy;
        this.status = status;
        this.inspectionResult = inspectionResult;
        this.inspectedObject = inspectedObject;
    }

    @Override
    public String toString() {
        return String.format("Inspection ID: %d\n" +
                "Inspector: %s\n" +
                "Inspection Date: %s\n" +
                "Inspected Object: %s\n" +
                "Status: %s\n" +
                "Result: %s"
                , this.id
                , this.inspectedBy.getFullName()
                , this.inspectionDate.toString()
                , this.getInspectedObjectName()
                , this.getStatus()
                , this.inspectionResult != null ? this.inspectionResult : "Not Yet Recorded"
                );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inspection other = (Inspection) o;
        return this.getId() == other.getId();
    }

    public int getId() {
        return id;
    }

    public LocalDate getInspectionDateTime() {
        return inspectionDate;
    }

    public void setInspectionDateTime(LocalDate inspectionDateTime) {
        this.inspectionDate = inspectionDate;
    }

    public Employee getInspectedBy() {
        return inspectedBy;
    }

    public void setInspectedBy(Employee inspectedBy) {
        this.inspectedBy = inspectedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInspectionResult() {
        return inspectionResult;
    }

    public void setInspectionResult(String inspectionResult) {
        this.inspectionResult = inspectionResult;
    }

    public Inspectable getInspectedObject() {
        return inspectedObject;
    }

    public String getInspectedObjectName() {
        return inspectedObject.getName();
    }

    public void setInspectedObject(Inspectable inspectedObject) {
        this.inspectedObject = inspectedObject;
    }

    public void finishInspection(String passResult) {
        this.setInspectionResult(passResult);
        this.setStatus("Complete");
    }
}
