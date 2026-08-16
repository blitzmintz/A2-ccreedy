package park;

import java.util.LinkedList;
import java.util.Objects;

public abstract class Facility implements Inspectable {
    private static int maxId = 0;
    private int id;
    private String name;
    private String status;
    private boolean underInspection;
    private LinkedList<Inspection> listOfInspections = new LinkedList<>();


    public Facility(boolean underInspection, String name) {
        this.id = ++maxId;
        this.underInspection = underInspection;
        this.name = name;
        this.status = "Closed"; // default to closed if we don't know
    }
    public Facility(boolean underInspection, String name, String status) {
        this.id = ++maxId;
        this.status = validateStatus(status);
        this.underInspection = underInspection;
        this.name = name;
    }

    public Facility(String id, String name, String status, String underInspection, LinkedList<Inspection> listOfInspections) {
        this.id = Integer.parseInt(id);
        this.status = validateStatus(status);
        this.underInspection = underInspection.equalsIgnoreCase("true");
        this.name = name;
        this.listOfInspections = listOfInspections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Facility other = (Facility) o;
        return this.getId() == other.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public void performInspection(Inspection inspection) {
        this.underInspection = true;
        System.out.println("Facility ID " + this.getId() + " is under inspection.");
        this.addInspection(inspection);
    }

    public LinkedList<Inspection> getListOfInspections() {
        return listOfInspections;
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
        try {
            return listOfInspections.peekLast().getInspectionResult();
        } catch (NullPointerException e) {
            System.out.println("There is no inspection history for this ride!");
        }
        return "Unknown";
    }

    public boolean isUnderInspection() {
        return underInspection;
    }

    public void setUnderInspection(boolean underInspection) {
        this.underInspection = underInspection;
    }

    @Override
    public String getLastInspectedByName() {
        try {
            return listOfInspections.peekLast().getInspectedBy().getFullName();
        } catch (NullPointerException e) {
            System.out.println("There is no inspection history for this ride!");
        }
        return "Unknown";
    }

    @Override
    public void addInspection(Inspection inspection) {
        listOfInspections.addLast(inspection);
        System.out.println("Inspection " + inspection.getId() + " added to list.");
    }

    public void setStatus(String status) {
        this.status = validateStatus(status);
    }

    public String getStatus() {
        return this.status;
    }

    private String validateStatus(String status) {
        String validatedStatus = "";
        switch (status.trim().toUpperCase()) {
            case "OPEN", "OPENED", "AVAILABLE" -> validatedStatus = "Open";
            default -> validatedStatus = "Closed"; // won't open the attraction if it is not expressly open
        }
        return validatedStatus;
    }

}


