package models;

import java.time.LocalDateTime;

public class Inspection {
    private static int maxId = 0;
    private int id;
    private LocalDateTime inspectionDateTime;
    private Employee inspectedBy;
    private String status;
    private String inspectionResult;
    private Inspectable inspectedObject;

    public Inspection(LocalDateTime inspectionDateTime, Employee inspectedBy, Inspectable inspectedObject) {
        this.id = ++maxId;
        this.inspectionDateTime = inspectionDateTime;
        this.inspectedBy = inspectedBy;
        this.inspectedObject = inspectedObject;
        this.status = "In Progress";
    }

    public Inspection(LocalDateTime inspectionDateTime, Employee inspectedBy, String status, Inspectable inspectedObject) {
        this.id = ++maxId;
        this.inspectionDateTime = inspectionDateTime;
        this.inspectedBy = inspectedBy;
        this.status = status;
        this.inspectedObject = inspectedObject;
    }

    public Inspection(LocalDateTime inspectionDateTime, Employee inspectedBy, String status, String inspectionResult, Inspectable inspectedObject) {
        this.id = ++maxId;
        this.inspectionDateTime = inspectionDateTime;
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
                "What was inspected: %s\n"
                , this.id
                , this.inspectedBy.getFullName()
                , this.inspectionDateTime.toString()
                , this.getInspectedObjectName()
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

    public LocalDateTime getInspectionDateTime() {
        return inspectionDateTime;
    }

    public void setInspectionDateTime(LocalDateTime inspectionDateTime) {
        this.inspectionDateTime = inspectionDateTime;
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
}
