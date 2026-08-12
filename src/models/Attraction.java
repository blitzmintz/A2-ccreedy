package models;

import java.util.LinkedList;

public abstract class Attraction {
    private static int maxId = 0;
    private int id;
    private String name;
    private String status;
    private Employee runBy;
    private int maxConcurrentVisitors;
    private LinkedList<Visitor> visitorsWaiting = new LinkedList<>();
    private LinkedList<Visitor> visitorsVisited = new LinkedList<>();

    public Attraction (String name, int maxConcurrentVisitors) {
        this.id = ++maxId;
        this.name = name;
        this.maxConcurrentVisitors = maxConcurrentVisitors;
        this.visitorsWaiting = new LinkedList<>();
        this.visitorsVisited = new LinkedList<>();
        this.status = "Closed"; // default is to be closed, until expressly opened.
    }

    public Attraction (String name, int maxConcurrentVisitors, String status, Employee runBy) {
        this.id = ++maxId;
        this.name = name;
        this.maxConcurrentVisitors = maxConcurrentVisitors;
        this.visitorsWaiting = new LinkedList<>();
        this.visitorsVisited = new LinkedList<>();
        this.status = validateStatus(status);
        this.runBy = runBy;
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

    public void removeOperator() {
        this.runBy = null;
        //No operator means attraction must be closed
        this.status = "Closed";
    }

    public void setRunBy(Employee runBy) {
        this.runBy = runBy;
    }

    public LinkedList<Visitor> getVisitorsWaiting() {
        return visitorsWaiting;
    }

    public LinkedList<Visitor> getVisitorsVisited() {
        return visitorsVisited;
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

    private void addVisitorWaiting(Visitor visitorToAdd) {
        visitorsWaiting.addLast(visitorToAdd);
        System.out.println("Visitor ID " + getId() + "added to attraction queue.");
    }

    //having number to remove allows us to pass in the max concurrent visitors easily and loop through until we reach an empty list OR max vistiors have been retrieved
    private void removeNextVisitorsWaiting(int numberToRemove) {
        int i;
        for (i = 0; i == numberToRemove; i++) {
            Visitor visitor = visitorsWaiting.peekFirst();
            if (visitor != null) {
                visitorsWaiting.removeFirst();
                visitorsVisited.addLast(visitor);
                System.out.println("Visitor ID " + getId() + " was removed from the queue and added to the visitor history list.");
            }
            else {
                System.out.println("No more visitors waiting!");
            }
        }
    }
}
