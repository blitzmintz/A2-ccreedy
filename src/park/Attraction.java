package park;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Attraction implements Cycleable, Runnable  {
    private static int maxId = 0;
    private int id;
    private String name;
    private String status;
    private Employee runBy;
    private int maxConcurrentVisitors;
    private LinkedList<Visitor> visitorsWaiting = new LinkedList<>();
    private LinkedList<Visitor> visitorsVisited = new LinkedList<>();
    private int numberOfCycles = 0;
    Lock lock = new ReentrantLock();
    private static int totalCycles = 0;

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

    public Attraction(String name, int maxConcurrentVisitors, String status) {
        this.id = ++maxId;
        this.name = name;
        this.maxConcurrentVisitors = maxConcurrentVisitors;
        this.status = validateStatus(status);
    }

    /**
     * This method is for use by the restore from file functionality. An id can be directly set as well as all other attributes.
     * Sub class constructors convert the file data to the correct data type when calling this constructor.
     * @param id the ID of the attraction being restored
     * @param name the name of the attraction
     * @param status the status of the attraction
     * @param runBy which employee the attraction is operated by
     * @param maxConcurrentVisitors the max number of visitors that can be served at once
     * @param visitorsWaiting the list of visitors in queue
     * @param visitorsVisited the list of visitors that have been served
     * @param numberOfCycles the number of run cycles the attraction has undergone
     */
    public Attraction(int id, String name, String status, Employee runBy, int maxConcurrentVisitors, LinkedList<Visitor> visitorsWaiting, LinkedList<Visitor> visitorsVisited,int numberOfCycles) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.runBy = runBy;
        this.maxConcurrentVisitors = maxConcurrentVisitors;
        this.visitorsVisited = visitorsVisited;
        this.visitorsWaiting = visitorsWaiting;
        this.numberOfCycles = numberOfCycles;
    }

    /**
     * This constructor allows you to directly set the ID rather than auto-incrementing the maxID.
     * This is intended to be used on restore from backup.
     * @param id the ID of the backed up attraction
     * @param name the name of the attraction
     * @param maxConcurrentVisitors the maximum number of concurrent visitors for the attraction
     * @param status the status of the attraction
     * @param runBy who the attraction is run by
     */
    public Attraction (int id, String name, int maxConcurrentVisitors, String status, Employee runBy) {
        this.id = id;
        this.name = name;
        this.maxConcurrentVisitors = maxConcurrentVisitors;
        this.visitorsWaiting = new LinkedList<>();
        this.visitorsVisited = new LinkedList<>();
        this.status = validateStatus(status);
        this.runBy = runBy;
    }

    public static int getTotalCycles() {
        return totalCycles;
    }

    public static void setTotalCycles(int totalCycles) {
        Attraction.totalCycles = totalCycles;
    }

    public void addTotalCycle() {
        lock.lock();
        totalCycles++;
        lock.unlock();
    }

    /**
     * This method compares two Attraction objects by ID.
     * @param o   the reference object with which to compare.
     * @return true/false for object equivalency
     */
    @Override
    public boolean equals(Object o) { if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attraction other = (Attraction) o;
        return this.id == other.id;
    }

    @Override
    public void run() {

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

    public String getRunByName() {
        if (runBy == null) {
            return "No operator assigned";
        } else {
            return runBy.getFullName();
        }
    }

    public int getRunById() {
        return runBy.getId();
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

    public String getVisitorsWaitingAsString() {
        String visitorListOrdered = "";
        if (visitorsWaiting.isEmpty()) {
            visitorListOrdered = "There are no visitors waiting.";
        } else {
            for (Visitor visitor : visitorsWaiting) {
                visitorListOrdered = visitorListOrdered + (visitorsWaiting.indexOf(visitor) + 1) + ". " + visitor.getFullName() + " [ID " + visitor.getId() + "]\n";
            }
        }
        return visitorListOrdered;

    }

    public LinkedList<Visitor> getVisitorsVisited() {
        return visitorsVisited;
    }

    public String getVisitorsVisitedAsString() {
        String visitorHistoryListOrdered = "";
        if (visitorsVisited.isEmpty()) {
            visitorHistoryListOrdered = "There are no visits in the attraction history.";
        } else {
            for (Visitor visitor : visitorsVisited) {
                visitorHistoryListOrdered = visitorHistoryListOrdered + (visitorsVisited.indexOf(visitor) + 1) + ". " + visitor.getFullName() + " [ID " + visitor.getId() + "]\n";
            }
        }
        return visitorHistoryListOrdered;
    }


    public String getVisitorsVisitedOrderByAge() {
        LinkedList<Visitor> sortedByAgeList = new LinkedList<>(visitorsVisited);
        sortedByAgeList.sort(Comparator.comparing(Visitor::getAge).thenComparing(Visitor::getId));
        String orderedList = "";
        for (Visitor visitor: sortedByAgeList) {
            orderedList = orderedList + (sortedByAgeList.indexOf(visitor) + 1) + ". " + visitor.getFullName() + " | Age: " + visitor.getAge() + " [ID " + visitor.getId() + "] \n";
        }
        return orderedList;
    }

    public String getVisitorsVisitedOrderByLastName() {
        LinkedList<Visitor> sortedByLastNameList = new LinkedList<>(visitorsVisited);
        sortedByLastNameList.sort(Comparator.comparing(Visitor::getLastName).thenComparing(Visitor::getId));
        String orderedList = "";
        for (Visitor visitor: sortedByLastNameList) {
            orderedList = orderedList + (sortedByLastNameList.indexOf(visitor) + 1) + ". " + visitor.getFullName() + " [ID " + visitor.getId() + "]\n";
        }
        return orderedList;
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


    //I think later when I need to have concurrency i am going to have to refactor this
    public void addVisitorWaiting(Visitor visitorToAdd) {
        visitorsWaiting.addLast(visitorToAdd);
        System.out.println("Visitor ID " + visitorToAdd.getId() + " added to queue for attraction '" + getName() + "'.");
    }

    //having number to remove allows us to pass in the max concurrent visitors easily and loop through until we reach an empty list OR max visitors have been retrieved
    public void removeNextVisitorsWaiting(int numberToRemove) {
        int i;
        int queueSizeAtStart = visitorsWaiting.size();
        for (i = 0; i < Math.min(numberToRemove, Math.min(maxConcurrentVisitors, queueSizeAtStart)); i++) {
            Visitor visitor = visitorsWaiting.peekFirst();
            if (visitor != null) {
                visitorsWaiting.removeFirst();
                visitorsVisited.addLast(visitor);
                System.out.println("Visitor ID " + visitor.getId() + " was removed from the queue and added to the visitor history list for attraction '" + getName() + "'");
            }
            else {
                System.out.println("No more visitors waiting!");
            }
        }
    }

    public int getNumberOfCycles() {
        return numberOfCycles;
    }

    public void setNumberOfCycles(int numberOfCycles) {
        this.numberOfCycles = numberOfCycles;
    }

    public void addCycle() {
        this.numberOfCycles = this.numberOfCycles + 1;
    }


}
