package models;

import java.util.LinkedList;

public abstract class Attraction {
    private static int maxId = 0;
    private int id;
    private String name;
    private String status;
    private int maxConcurrentVisitors;
    private Employee runBy;
    private LinkedList<Visitor> visitorsWaiting;
    private LinkedList<Visitor> visitorsVisited;

    public Attraction (String name, int maxConcurrentVisitors, Employee runBy) {
        this.id = ++maxId;
        this.name = name;
        this.maxConcurrentVisitors = maxConcurrentVisitors;
        this.runBy = runBy;
        this.visitorsWaiting = new LinkedList<>();
        this.visitorsVisited = new LinkedList<>();
        this.status = "Closed"; // default is to be closed, until expressly opened.
    }

    public Attraction (String name, int maxConcurrentVisitors, Employee runBy, String status) {
        this.id = ++maxId;
        this.name = name;
        this.maxConcurrentVisitors = maxConcurrentVisitors;
        this.runBy = runBy;
        this.visitorsWaiting = new LinkedList<>();
        this.visitorsVisited = new LinkedList<>();
        this.status = validateStatus(status);
    }

    @Override
    public String toString() {
        return String.format("Ride ID: %d \n" +
                        "Name: %s \n" +
                        "Status %s \n" +
                        "Maximum Visitors: %d \n" +
                        "Number of Visitors Waiting: %d \n" +
                        "Number of Visits: %d"
                , this.getId()
                , this.getName()
                , this.getStatus()
                , this.getMaxConcurrentVisitors()
                , this.getVisitorsWaiting().size()
                , this.getVisitorsVisited().size());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxConcurrentVisitors() {
        return maxConcurrentVisitors;
    }

    public void setMaxConcurrentVisitors(int maxConcurrentVisitors) {
        this.maxConcurrentVisitors = maxConcurrentVisitors;
    }

    public Employee getRunBy() {
        return runBy;
    }

    public void setRunBy(Employee runBy) {
        this.runBy = runBy;
    }

    public LinkedList<Visitor> getVisitorsWaiting() {
        return visitorsWaiting;
    }

    public void setVisitorsWaiting(LinkedList<Visitor> visitorList) {
        this.visitorsWaiting = visitorList;
    }

    public LinkedList<Visitor> getVisitorsVisited() {
        return visitorsVisited;
    }

    public void setVisitorsVisited(LinkedList<Visitor> visitorList) {
        this.visitorsVisited = visitorList;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = validateStatus(status);
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
