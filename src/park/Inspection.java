package park;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Inspection {
    private static int maxId = 0;
    private int id;
    private LocalDate inspectionDate;
    private Employee inspectedBy;
    private String status;
    private String inspectionResult;
    private String inspectedObjectName;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Inspection(LocalDate inspectionDate, Employee inspectedBy, String inspectedObjectName) {
        this.id = ++maxId;
        this.inspectionDate = inspectionDate;
        this.inspectedBy = inspectedBy;
        this.inspectedObjectName = inspectedObjectName;
        this.status = "In Progress";
    }

    public Inspection(LocalDate inspectionDate, Employee inspectedBy, String status, String inspectedObjectName) {
        this.id = ++maxId;
        this.inspectionDate = inspectionDate;
        this.inspectedBy = inspectedBy;
        this.status = status;
        this.inspectedObjectName = inspectedObjectName;
    }

    public Inspection(LocalDate inspectionDate, Employee inspectedBy, String status, String inspectionResult, String inspectedObjectName) {
        this.id = ++maxId;
        this.inspectionDate = inspectionDate;
        this.inspectedBy = inspectedBy;
        this.status = status;
        this.inspectionResult = inspectionResult;
        this.inspectedObjectName = inspectedObjectName;
    }

    public Inspection(String id, String inspectionDate, Employee inspectedBy, String status, String inspectionResult, String inspectedObjectName) {
        this.id = Integer.parseInt(id);
        this.inspectionDate = LocalDate.parse(inspectionDate, formatter);
        this.inspectedBy = inspectedBy;
        this.status = status;
        this.inspectionResult = inspectionResult;
        this.inspectedObjectName = inspectedObjectName;
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


    public String getInspectedObjectName() {
        return this.inspectedObjectName;
    }

    public void finishInspection(String passResult) {
        this.setInspectionResult(passResult);
        this.setStatus("Complete");
    }
}
