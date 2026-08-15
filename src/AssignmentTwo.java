import exceptions.EmptyRideQueueException;
import exceptions.MissingOperatorException;
import exceptions.UnderInspectionException;
import park.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

public class AssignmentTwo {
    public static void main(String[] args) {

        // PART 1 - VISITORS AND EMPLOYEES
        System.out.println("PART 1 HERE \n" + "--------");
        // Create a new tree set that contains visitors and is ordered by age, then by ID in case of 2 visitors having the same age
        TreeSet<Visitor> visitorSet = new TreeSet<>(Comparator.comparingInt(Visitor::getAge).thenComparing(Visitor::getId));

        Visitor firstVisitor = new Visitor("Charlotte", "Creedy", 25, "0490419035");
        System.out.println(firstVisitor);
        visitorSet.add(firstVisitor);

        Visitor secondVisitor = new Visitor("Mimi", "Kyu", 20, "0466677788");
        System.out.println(secondVisitor);
        visitorSet.add(secondVisitor);

        //print the visitor set, expecting firstVisitor Charlotte to appear AFTER secondVisitor Mimi, due to age ordering
        System.out.println(visitorSet);

        // Create a new tree set that contains employees and is ordered by id
        TreeSet<Employee> employeeSet = new TreeSet<>(Comparator.comparingInt(Employee::getId));

        Employee firstEmployee = new Employee("Pika", "Chu", 100, "0433355566", LocalDate.of(2020, 1, 1), "Ride Attendant");
        System.out.println(firstEmployee);
        employeeSet.add(firstEmployee);

        Employee secondEmployee = new Employee("Evie", "Blue", 23, "0498175433", LocalDate.of(2026, 3, 3), "Ride Attendant");
        System.out.println(secondEmployee);
        employeeSet.add(secondEmployee);

        Employee performerEmployee = new Employee("Lindsey", "Star", 29, "0455566677", LocalDate.of(2024, 1, 20), "Performer");
        System.out.println(performerEmployee);
        employeeSet.add(performerEmployee);

        Employee performerEmployee2 = new Employee("Gabriel", "White", 26, "0423288897", LocalDate.of(2025, 10, 10), "Performer");
        System.out.println(performerEmployee2);
        employeeSet.add(performerEmployee2);

        Employee performerEmployee3 = new Employee("Mary", "Conklin", 30, "0424465897", LocalDate.of(2020, 8, 1), "Lead Female Performer");
        System.out.println(performerEmployee3);
        employeeSet.add(performerEmployee3);

        Employee performerEmployee4 = new Employee("Tyler", "Conklin", 31, "0424465897", LocalDate.of(2020, 8, 1), "Lead Male Performer");
        System.out.println(performerEmployee4);
        employeeSet.add(performerEmployee4);

        Employee inspector1 = new Employee("Daniel", "Wood", 35, "0424998797", LocalDate.of(2021, 9, 3), "Inspector");
        System.out.println(inspector1);
        employeeSet.add(inspector1);

        Employee inspector2 = new Employee("Maya", "Redd", 30, "0484568797", LocalDate.of(2023, 7, 5), "Inspector");
        System.out.println(inspector2);
        employeeSet.add(inspector2);

        //PART 2 - ATTRACTIONS
        System.out.println("PART 2 HERE \n" + "--------");
        // Create a show
        // Add performers to a list to be passed to Show constructor - a show typically has many performers. one of them is running the show.
        ArrayList<Employee> performerList1 = new ArrayList<>();
        performerList1.add(performerEmployee4);
        performerList1.add(performerEmployee2);

        Show fairyPerformance = new Show("Fairy Fun Frenzy", 15, performerList1, "Open", "East Stage", performerEmployee4);
        System.out.println("Print new Show object");
        System.out.println(fairyPerformance);

        // Prove that removing an operator closes the attraction
        System.out.println("Remove an operator, show that the attraction is closed");
        fairyPerformance.removeOperator();
        System.out.println(fairyPerformance);


        //Create a ride
        Ride crazyCoasterRide = new Ride("Crazy Coaster 3000", 20, false, "Open", firstEmployee);
        System.out.println("Print new Ride object");
        System.out.println(crazyCoasterRide);

        // Create a toilet
        System.out.println("Print new Toilet object");
        Toilet basicToilet = new Toilet("West Toilet", true, true);
        System.out.println(basicToilet);

        // create an inspection that is in progress and start it
        System.out.println("Create new Inspection object and perform inspection on toilet");
        Inspection toiletInspection = new Inspection(LocalDate.now(), inspector1, basicToilet);
        basicToilet.performInspection(toiletInspection);

        // Show the most recently added (i.e. most recently started) inspection
        System.out.println("Show that the toilet is being inspected");
        System.out.println("\n" + basicToilet.getListOfInspections().getLast() + "\n");
        //Show the toilet status during inspection
        System.out.println("Toilet status during inspection: " + basicToilet.getStatus() + "\n");

        // End the inspection with a pass result
        System.out.println("Updating toilet inspection result");
        toiletInspection.finishInspection("Pass");
        basicToilet.endInspection(toiletInspection.getInspectionResult());
        //Show the toilet status POST inspection
        System.out.println("Toilet status after inspection: " + basicToilet.getStatus() + "\n");

        // Show that the inspection status is now Complete after it was finished, and that the result was recorded.
        // Note that I am accessing this via the toilet object not the inspection object, showing the same object is being updated
        System.out.println("Show that inspection status is now complete after inspection is finished.");
        System.out.println(basicToilet.getListOfInspections().getLast() + "\n");

        // Now we will inspect a ride
        Inspection rideInspection = new Inspection(LocalDate.now(), inspector2, crazyCoasterRide);
        crazyCoasterRide.performInspection(rideInspection);

        // Show the most recently added (i.e. most recently started) inspection
        System.out.println("\n" + crazyCoasterRide.getListOfInspections().getLast() + "\n");
        //Show the ride status during inspection
        System.out.println("Ride status during inspection: " + crazyCoasterRide.getStatus() + "\n");
        rideInspection.finishInspection("Fail");
        crazyCoasterRide.endInspection(rideInspection.getInspectionResult());
        System.out.println("Ride status after inspection failed: " + crazyCoasterRide.getStatus() + "\n");

        // Show that the inspection status is now Complete after it was finished, and that the result was recorded.
        System.out.println("Display inspection status is Complete and that result was recorded:");
        System.out.println(crazyCoasterRide.getListOfInspections().getLast() + "\n");




        //PART 3 - WAITING LINE
        System.out.println("PART 3 HERE \n" + "--------");
        //I already have a few visitors from the above, so I will add them to my waiting queue, and create more to add
        Visitor thirdVisitor = new Visitor("Brock", "Stub", 12, "0400099911");
        Visitor fourthVisitor = new Visitor("Amy", "Stub", 41, "0400099912");
        visitorSet.add(thirdVisitor);
        visitorSet.add(fourthVisitor);

        crazyCoasterRide.addVisitorWaiting(thirdVisitor);
        crazyCoasterRide.addVisitorWaiting(firstVisitor);
        crazyCoasterRide.addVisitorWaiting(fourthVisitor);
        crazyCoasterRide.addVisitorWaiting(secondVisitor);

        System.out.println("Printing visitor list in order");
        System.out.println(crazyCoasterRide.getVisitorsWaitingAsString());

        //removing one visitor from head of queue to be served as per requirements
        crazyCoasterRide.removeNextVisitorsWaiting(1);
        System.out.println("Printing visitor list in order after visitor has been served");
        System.out.println(crazyCoasterRide.getVisitorsWaitingAsString());


        // PART 4 - VISIT HISTORY
        System.out.println("PART 4 HERE \n" + "--------");
        System.out.println("Printing visitor HISTORY list after visitor was served");
        System.out.println(crazyCoasterRide.getVisitorsVisitedAsString());

        System.out.println("Count of visits to this ride: " + crazyCoasterRide.getVisitorsVisited().size());

        crazyCoasterRide.removeNextVisitorsWaiting(2);
        System.out.println("Count of visits to this ride: " + crazyCoasterRide.getVisitorsVisited().size());
        System.out.println("Show the history list after 2 more customers were served");
        System.out.println(crazyCoasterRide.getVisitorsVisitedAsString());

        // Now to display the sorted list by Age (lowest to highest), then ID for same ages
        System.out.println("Printing the list of visitors visited ordered by Age, then ID");
        System.out.println(crazyCoasterRide.getVisitorsVisitedOrderByAge() + "\n");
        // And by Last Name, then ID for same Last Names
        System.out.println("Printing the list of visitors visited ordered by Last Name, then ID");
        System.out.println(crazyCoasterRide.getVisitorsVisitedOrderByLastName());

        System.out.println();

        // PART 5 - OPERATING ATTRACTIONS
        System.out.println("PART 5 HERE \n" + "--------");
        Visitor fifthVisitor = new Visitor("Karly", "McManus", 23, "0400777911");
        Visitor sixthVisitor = new Visitor("Amy", "Starkly", 24, "0400996611");
        visitorSet.add(fifthVisitor);
        visitorSet.add(sixthVisitor);

        System.out.println("Creating a new show with no operator.");
        Show wizardShow = new Show("Wonderful Wizards", 5,performerList1, "Open", null);
        wizardShow.addVisitorWaiting(firstVisitor);
        wizardShow.addVisitorWaiting(secondVisitor);
        wizardShow.addVisitorWaiting(thirdVisitor);
        wizardShow.addVisitorWaiting(fourthVisitor);
        wizardShow.addVisitorWaiting(fifthVisitor);
        wizardShow.addVisitorWaiting(sixthVisitor);
        System.out.println("Displaying visitors waiting and cycle count before cycle begins.");
        System.out.println(wizardShow.getVisitorsWaitingAsString());
        System.out.println("Cycle count: " + wizardShow.getNumberOfCycles());
        System.out.println("Displaying visitor history before cycle begins:");
        System.out.println(wizardShow.getVisitorsVisitedAsString());
        System.out.println("Starting the cycle with no operator - expecting to throw exception.");
        try {
            wizardShow.runCycle();
        } catch (MissingOperatorException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Adding an operator to allow us to run the cycle.");
        wizardShow.setRunBy(performerEmployee4);
        System.out.println("Starting the cycle with an operator.");
        try {
            wizardShow.runCycle();
        } catch (MissingOperatorException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Printing visitor queue & cycle count after cycle - waiting queue should have 1 person still waiting.");
        System.out.println(wizardShow.getVisitorsWaitingAsString());
        System.out.println("Cycle count: " + wizardShow.getNumberOfCycles());
        System.out.println("Displaying visitor history after cycle completes:");
        System.out.println(wizardShow.getVisitorsVisitedAsString());
        System.out.println("Running another cycle to empty the queue.");
        try {
            wizardShow.runCycle();
        } catch (MissingOperatorException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("\nPrinting visitor queue & cycle count after cycle - waiting queue should have no one waiting.");
        System.out.println(wizardShow.getVisitorsWaitingAsString());
        System.out.println("Cycle count: " + wizardShow.getNumberOfCycles());

        System.out.println("\nNow running a cycle with no one in queue - it's a show, so it should still go");
        try {
            wizardShow.runCycle();
        } catch (MissingOperatorException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nPrinting visitor queue & cycle count after cycle - waiting queue should have no one waiting.");
        System.out.println(wizardShow.getVisitorsWaitingAsString());
        System.out.println("Cycle count: " + wizardShow.getNumberOfCycles());

        System.out.println("\n Creating a new Ride that we will close for inspection.");
        Ride logFlumeRide = new Ride("Log Flume", 6, "Open", firstEmployee);
        logFlumeRide.addVisitorWaiting(thirdVisitor);
        logFlumeRide.addVisitorWaiting(secondVisitor);
        logFlumeRide.addVisitorWaiting(firstVisitor);
        logFlumeRide.addVisitorWaiting(fourthVisitor);
        logFlumeRide.addVisitorWaiting(sixthVisitor);
        logFlumeRide.addVisitorWaiting(fifthVisitor);

        Inspection flumeRideInspection = new Inspection(LocalDate.now(), inspector1, logFlumeRide);
        logFlumeRide.performInspection(flumeRideInspection);

        System.out.println("Displaying visitors waiting and cycle count before cycle begins.");
        System.out.println(logFlumeRide.getVisitorsWaitingAsString());
        System.out.println("Cycle count: " + logFlumeRide.getNumberOfCycles());
        System.out.println("Displaying visitor history before cycle begins:");
        System.out.println(logFlumeRide.getVisitorsVisitedAsString());
        System.out.println("Starting the cycle while under inspection - expecting to throw exception.");
        try {
            logFlumeRide.runCycle();
        } catch (EmptyRideQueueException | MissingOperatorException | UnderInspectionException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Ending the inspection as a pass to reopen the ride.");
        logFlumeRide.endInspection("Pass");
        System.out.println("Running a cycle again.");
        try {
            logFlumeRide.runCycle();
        } catch (EmptyRideQueueException | MissingOperatorException | UnderInspectionException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Printing visitor queue & cycle count after cycle - queue should have no one waiting.");
        System.out.println(logFlumeRide.getVisitorsWaitingAsString());
        System.out.println("Cycle count: " + logFlumeRide.getNumberOfCycles());
        System.out.println("Displaying visitor history after cycle completes:");
        System.out.println(logFlumeRide.getVisitorsVisitedAsString());

        System.out.println("Running a cycle again with no one in the queue, expecting to throw empty queue exception");
        try {
            logFlumeRide.runCycle();
        } catch (EmptyRideQueueException | MissingOperatorException | UnderInspectionException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("PART 6 HERE \n" + "--------");
        // instantiate ThemeParkManager object
        ThemeParkManager themePark = new ThemeParkManager("Wacky Theme Park");
        // creating another performer list to be used for show
        ArrayList<Employee> performerList2 = new ArrayList<>();
        performerList2.add(performerEmployee3);
        performerList2.add(performerEmployee);

        Ride teacups = new Ride("Teacups", 12, "Open", firstEmployee);
        Ride bumperCars = new Ride("Bumper Cars", 5, "Open", secondEmployee);
        Show animalShow = new Show("Animal Kingdom", 6,performerList1, "Open", performerEmployee4);
        Show clownShow = new Show("Clowning Around", 5,performerList2, "Open", performerEmployee3);

        System.out.println("\n*Adding newly created attractions to Theme Park.*");
        themePark.addAttraction(teacups);
        themePark.addAttraction(bumperCars);
        themePark.addAttraction(animalShow);
        themePark.addAttraction(clownShow);

        System.out.println("\n*Adding visitors to Theme Park from existing visitor set.*");
        themePark.addVisitorsFromSet(visitorSet);
        //create some more visitors
        Visitor seventhVisitor = new Visitor("Reuben", "Pollak", 25, "0499911001");
        Visitor eighthVisitor = new Visitor("Sara", "Thorn", 44, "0400099900");
        themePark.addVisitor(seventhVisitor);
        themePark.addVisitor(eighthVisitor);

        System.out.println("\n*Retrieving attraction by ID.*");
        System.out.println(themePark.getAttractionById(5).toString());

        System.out.println("\n*Displaying distinct visitor count for the park.*");
        System.out.println("Unique visitor count for this park: " + themePark.reportUniqueVisitorCount());

        System.out.println("\n*Creating a repeat visitor and checking if unique visitor count increases (it should remain the same as previous line).*");
        Visitor repeatVisitor = new Visitor("Sara", "Thorn", 44, "0400099900");
        themePark.addVisitor(repeatVisitor);
        System.out.println("Unique visitor count for this park: " + themePark.reportUniqueVisitorCount());


        System.out.println("\n*Displaying each attraction's total visit count. Expecting no visits.*");
        System.out.println("Attraction Visits Report\n" + themePark.reportAllAttractionVisitCount() );

        teacups.addVisitorWaiting(sixthVisitor);
        teacups.addVisitorWaiting(eighthVisitor);
        clownShow.addVisitorWaiting(secondVisitor);
        animalShow.addVisitorWaiting(thirdVisitor);
        animalShow.addVisitorWaiting(fifthVisitor);
        animalShow.addVisitorWaiting(fourthVisitor);

        System.out.println("\n*Running cycles for 3 attractions.*");

        teacups.runCycle();
        animalShow.runCycle();
        clownShow.runCycle();

        System.out.println("\n*Displaying each attraction's total visit count. Expecting visit count to increase for 3 of 4 attractions.*");
        System.out.println("Attraction Visits Report\n" + themePark.reportAllAttractionVisitCount() );

        System.out.println("\n*Visitor 4 wants to see the Animal show again, which should increase visit count even though they've seen it before.*");
        animalShow.addVisitorWaiting(fourthVisitor);
        animalShow.runCycle();
        System.out.println("\n*Printing visit report for specific attraction Animal Kingdom.*");
        System.out.println("Attraction Visits Report\n" + themePark.reportSpecificAttractionVisitCount(animalShow.getId()));

        System.out.println("PART 7 HERE \n" + "--------");


        //System.out.println("PART 8 HERE \n" + "--------");


    }

}
