package park;


import exceptions.AttractionClosedException;
import exceptions.EmptyRideQueueException;
import exceptions.MissingOperatorException;
import exceptions.UnderInspectionException;

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

    public Ride(String name, int maxConcurrentVisitors, boolean underInspection, String status) {
        super(name, maxConcurrentVisitors, status);
        this.underInspection = underInspection;
    }

    /**
     * This constructor allows an id to be set manually (via the super type's constructor), intended for restore from backup.
     * @param id the ID of the backed up Ride
     * @param name the name of the ride
     * @param maxConcurrentVisitors the maximum number of concurrent visitors
     * @param status the current status of the ride
     * @param runBy the employee running the ride
     * @param underInspection whether the ride is currently under inspection
     * @param visitorHistory the list of visitors that have visited the ride
     * @param visitorsWaiting the list of visitors in queue
     */
    public Ride(String id, String name, String status, Employee runBy, String maxConcurrentVisitors, LinkedList<Visitor> visitorsWaiting, LinkedList<Visitor> visitorHistory, String numberOfCycles, String underInspection, LinkedList<Inspection> listOfInspections) {
        super(Integer.parseInt(id), name, status, runBy, Integer.parseInt(maxConcurrentVisitors), visitorsWaiting, visitorHistory, Integer.parseInt(numberOfCycles));
        this.underInspection = underInspection.equals("true");
    }

    /**
     * This method triggers the ride cycles to run while the queue waiting is not empty.
     * This is managed by the Theme Park Manager (where attractions are submitted to the executor service).
     * After a cycle is run, it increments the total cycles for the park at the super class level
     */
    @Override
    public void run() {
        while (!getVisitorsWaiting().isEmpty()) {
            this.runCycle();
            addTotalCycle();
        }
    }

    /**
     * This method adds an inspection object to the list of inspections managed by the Ride object
     * @param inspection the inspection to be added
     */
    @Override
    public void addInspection(Inspection inspection) {
        listOfInspections.addLast(inspection);
        System.out.println("Inspection " + inspection.getId() + " added to list.");
    }

    /**
     * This method triggers an inspection on a ride and adds it to the list of inspections. It closes the ride and prints a message to say that.
     * @param inspection the inspection being performed on the ride
     */
    @Override
    public void performInspection(Inspection inspection) {
        this.addInspection(inspection);
        setUnderInspection(true);
        setStatus("Closed");
        System.out.println(getName() + " has been closed for an inspection.");
    }

    /**
     * This method ends the inspection. It marks the ride as not under inspection, and validates the result is a pass before re-opening the ride.
     * @param passResult the result of the inspection
     */
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
                "Number of Visits: %d \n" +
                "Status: %s"
                , this.getId()
                , this.getName()
                , this.getMaxConcurrentVisitors()
                , this.getVisitorsWaiting().size()
                , this.getVisitorsVisited().size()
                , this.getStatus());
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

    /**
     * This method contains the logic for running a cycle of a ride. It removes the visitors from the waiting queue by calling the removeNextVisitorsWaiting() method and then increments the cycle count by calling addCycle().
     * @throws MissingOperatorException this exception is thrown if there is no employee running the ride
     * @throws EmptyRideQueueException this exception is thrown if there is no one waiting for the ride
     * @throws UnderInspectionException this exception is thrown if the ride is under inspection
     * @throws AttractionClosedException this exception is thrown if the ride is closed
     */
    @Override
    public void runCycle() throws MissingOperatorException, EmptyRideQueueException, UnderInspectionException, AttractionClosedException {
        if (getRunBy() == null) {
            throw new MissingOperatorException("The Ride does not have an operator present, so it cannot start a cycle!");
        }
        if (getVisitorsWaiting().isEmpty()) {
            throw new EmptyRideQueueException("The ride has no one waiting, so it cannot start a cycle!");
        }
        if (isUnderInspection()) {
            throw new UnderInspectionException("The ride is under inspection, so it cannot start a cycle!");
        }
        if (getStatus().equalsIgnoreCase("Closed")) {
            throw new AttractionClosedException("The ride is closed, so it cannot start a cycle!");
        }
        int visitorsToServe = Math.min(getVisitorsWaiting().size(), getMaxConcurrentVisitors());
        removeNextVisitorsWaiting(visitorsToServe);
        addCycle();
        System.out.println("Cycle completed for " + visitorsToServe + " visitors on attraction " + this.getName());


    }





}
