package models;


public class Ride extends Attraction implements Inspectable {
    public boolean underInspection;
    public String lastInspectionResult;

    public Ride(String name, int maxConcurrentVisitors, Employee runBy) {
        super(name, maxConcurrentVisitors, runBy);
        this.underInspection = false;
    }

    public Ride(String name, int maxConcurrentVisitors, Employee runBy, String status) {
        super(name, maxConcurrentVisitors, runBy, status);
        this.underInspection = false;
    }

    public Ride(String name, int maxConcurrentVisitors, Employee runBy, boolean underInspection, String status, String lastInspectionResult) {
        super(name, maxConcurrentVisitors, runBy, status);
        this.underInspection = underInspection;
        this.lastInspectionResult = validateInspectionResult(lastInspectionResult);
    }

    @Override
    public void performInspection() {
        this.underInspection = true;
        System.out.println(getName() + " has been closed for an inspection.");
    }

    @Override
    public void endInspection(boolean result) {
        this.underInspection = false;
        System.out.println(getName() + " is no longer under inspection. Inspection Passed?: " + result);
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
                "Last Inspection Date: %s \n" +
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
        return lastInspectionResult;
    }

    public void setUnderInspection(boolean underInspection) {
        this.underInspection = underInspection;
    }

    public void setLastInspectionResult(String result) {
        this.lastInspectionResult = validateInspectionResult(result);
    }



}
