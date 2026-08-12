package models;


import java.util.LinkedList;
import java.util.Objects;

public class Ride extends Attraction implements Inspectable {
    private boolean underInspection;
    private LinkedList<Inspection> listOfInspections = new LinkedList<>();

    public Ride(String name, int maxConcurrentVisitors) {
        super(name, maxConcurrentVisitors);
        this.underInspection = false;
        // No employee running it, so cannot be open
        this.setStatus("Closed");
    }

    public Ride(String name, int maxConcurrentVisitors, String status, Employee runBy) {
        super(name, maxConcurrentVisitors, status, runBy);
        this.underInspection = false;
    }

    public Ride(String name, int maxConcurrentVisitors, boolean underInspection, String status, Employee runBy) {
        super(name, maxConcurrentVisitors, status, runBy);
        this.underInspection = underInspection;
    }

    @Override
    public void addInspection(Inspection inspection) {
        listOfInspections.addLast(inspection);
        System.out.println("Inspection " + inspection.getId() + " added to list.");
    }

    @Override
    public void performInspection(Inspection inspection) {
        this.addInspection(inspection);
        setUnderInspection(true);
        setStatus("Closed");
        System.out.println(getName() + " has been closed for an inspection.");
    }

    @Override
    public void endInspection(String passResult) {
        setUnderInspection(false);
        // If an inspection fails, noone should use the ride, so we keep it closed unless it passes
        if (Objects.equals(validateInspectionResult(passResult), "Pass")) {
            setStatus("Open");
        }
        System.out.println(this.getName() + " is no longer under inspection. Inspection Result: " + this.getLastInspectionResult());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ride other = (Ride) o;
        return this.getId() == other.getId();
    }

    @Override
    public String toString() {
        return String.format("Ride ID: %d \n" +
                "Name: %s \n" +
                "Maximum Visitors: %d \n" +
                "Number of Visitors Waiting: %d \n" +
                "Number of Visits: %d \n"
                , this.getId()
                , this.getName()
                , this.getMaxConcurrentVisitors()
                , this.getVisitorsWaiting().size()
                , this.getVisitorsVisited().size());
    }

    public boolean isUnderInspection() {
        return underInspection;
    }

    public LinkedList<Inspection> getListOfInspections() {
        return listOfInspections;
    }

    public String getLastInspectionResult() {
        try {
            return listOfInspections.peekLast().getInspectionResult();
        } catch (NullPointerException e) {
            System.out.println("There is no inspection history for this ride!");
        }
        return "Unknown";
    }

    @Override
    public String getLastInspectedByName() {
        try {
            return listOfInspections.peekLast().getInspectedBy().getFullName();
        } catch (NullPointerException e) {
            System.out.println("There is no employee name against the last inspection for this ride!");
        }
        return "Unknown";
    }

    @Override
    public Employee getLastInspectedByObject() {
        try {
            return listOfInspections.peekLast().getInspectedBy();
        } catch (NullPointerException e) {
            System.out.println("There is no employee against the last inspection for this ride!");
        }
        return null;
    }

    public void setUnderInspection(boolean underInspection) {
        this.underInspection = underInspection;
    }





}
