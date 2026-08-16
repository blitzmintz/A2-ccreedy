package park;

import exceptions.AttractionClosedException;
import exceptions.MissingOperatorException;

import java.util.ArrayList;
import java.util.LinkedList;

public class Show extends Attraction {
    private String stageLocationName;
    private ArrayList<Employee> performers = new ArrayList<>();

    public Show(String name, int maxConcurrentVisitors) {
        super(name, maxConcurrentVisitors);
    }

    public Show(String name, int maxConcurrentVisitors, ArrayList<Employee> performerList, String status, Employee runBy) {
        super(name, maxConcurrentVisitors, status, runBy);
        this.performers = performerList;

    }
    public Show(String name, int maxConcurrentVisitors, ArrayList<Employee> performerList, String status, String stageLocationName, Employee runBy) {
        super(name, maxConcurrentVisitors, status, runBy);
        this.stageLocationName = stageLocationName;
        this.performers = performerList;
    }
    /**
     * This constructor allows an id to be set manually (via the super type's constructor), intended for restore from backup.
     * @param id the ID of the backed up Ride
     * @param name the name of the ride
     * @param maxConcurrentVisitors the maximum number of concurrent visitors
     * @param performerList the list of performer employees
     * @param status the current status of the ride
     * @param runBy the employee running the ride
     * @param visitorsWaiting the visitors in queue for the show
     * @param visitorHistory the visitors that have visited the show
     * @param stageLocationName the name of the stage being performed on
     */

    public Show(String id, String name, String status, Employee runBy, String maxConcurrentVisitors, LinkedList<Visitor> visitorsWaiting, LinkedList<Visitor> visitorHistory, String numberOfCycles, ArrayList<Employee> performerList, String stageLocationName) {
        super(Integer.parseInt(id), name, status, runBy, Integer.parseInt(maxConcurrentVisitors), visitorsWaiting, visitorHistory, Integer.parseInt(numberOfCycles));
        this.stageLocationName = stageLocationName;
        this.performers = performerList;
    }

    @Override
    public void run() {
        while (!getVisitorsWaiting().isEmpty()) {
            this.runCycle();
            addTotalCycle();
        }
    }

    @Override
    public String toString() {
        return String.format("Show ID: %d \n" +
                        "Show Name: %s \n" +
                        "Stage Location Name: %s \n" +
                        "Run By: %s \n" +
                        "Maximum Visitors: %d \n" +
                        "Number of Visitors Waiting: %d \n" +
                        "Number of Visits: %d \n" +
                        "Status: %s \n"
                , this.getId()
                , this.getName()
                , this.getStageLocationName()
                , this.getRunByName()
                , this.getMaxConcurrentVisitors()
                , this.getVisitorsWaiting().size()
                , this.getVisitorsVisited().size()
                , this.getStatus());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Show other = (Show) o;
        return this.getId() == other.getId();
    }

    public void setStageLocationName(String stageLocationName) {
        this.stageLocationName = stageLocationName;
    }

    public String getStageLocationName() {
        return stageLocationName;
    }

    public ArrayList<Employee> getPerformerList() {
        return performers;
    }

    public void setPerformers(ArrayList<Employee> performerList) {
        this.performers = performerList;
    }

    @Override
    public void runCycle() throws MissingOperatorException, AttractionClosedException {
        if (getRunBy() == null) {
            throw new MissingOperatorException("The Show does not have an operator present, so it cannot start a cycle!");
        }
        if (getStatus().equalsIgnoreCase("Closed")) {
            throw new AttractionClosedException("The show is closed, so it cannot start a cycle!");
        }
            int visitorsToServe = Math.min(getVisitorsWaiting().size(), getMaxConcurrentVisitors());
            removeNextVisitorsWaiting(visitorsToServe);
            addCycle();
            System.out.println("Cycle completed");

    }

}
