package models;


import java.util.LinkedList;

public class Ride extends Attraction implements Inspectable {
    private boolean underInspection;
    private LinkedList<Inspection> listOfInspections;

    public Ride(String name, int maxConcurrentVisitors, Employee runBy) {
        super(name, maxConcurrentVisitors);
        this.underInspection = false;
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
        setUnderInspection(true);
        setStatus("Closed");
        this.addInspection(inspection);
        System.out.println(getName() + " has been closed for an inspection.");
    }

    @Override
    public void endInspection(String result) {
        try {
            if (listOfInspections.peekLast().getStatus().equals("In Progress")) {
                listOfInspections.peekLast().setStatus("Complete");
                setUnderInspection(false);
                System.out.println(getName() + " is no longer under inspection. Inspection Passed?: " + listOfInspections.peekLast().getInspectionResult());
                if (validateInspectionResult(result).equals("Pass")) {
                    setStatus("Open");
                    System.out.println(getName() + " is now open again!");
                } else {
                    System.out.println(getName() + " did not pass it's inspection and will remain closed.");
                }
            } else {
                System.out.println("There is no inspection in progress to end.");
            }

        } catch (NullPointerException e) {
            System.out.println("No inspections found, result cannot be displayed.");
        }
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
                "Number of Visits: %d \n" +
                "Inspection Result: %s"
                , this.getId()
                , this.getName()
                , this.getMaxConcurrentVisitors()
                , this.getVisitorsWaiting().size()
                , this.getVisitorsVisited().size()
                , this.getLastInspectionResult());
    }

    public boolean isUnderInspection() {
        return underInspection;
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
