import models.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class AssignmentTwo {
    public static void main(String[] args) {

        // PART 1 - VISITORS AND EMPLOYEES
        // Create a new tree set that contains visitors and is ordered by age, then by ID in case of 2 visitors having the same age
        Set<Visitor> visitorSet = new TreeSet<>(Comparator.comparingInt(Visitor::getAge).thenComparing(Visitor::getId));

        Visitor firstVisitor = new Visitor("Charlotte", "Creedy", 25, "0490419035");
        System.out.println(firstVisitor);
        visitorSet.add(firstVisitor);

        Visitor secondVisitor = new Visitor("Mimi", "Kyu", 20, LocalDate.of(2026, 8, 20), "0466677788");
        System.out.println(secondVisitor);
        visitorSet.add(secondVisitor);

        //print the visitor set, expecting firstVisitor Charlotte to appear AFTER secondVisitor Mimi, due to age ordering
        System.out.println(visitorSet);

        // Create a new tree set that contains employees and is ordered by id
        Set<Employee> employeeSet = new TreeSet<>(Comparator.comparingInt(Employee::getId));

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
        // Create a show
        // Add performers to a list to be passed to Show constructor - a show typically has many performers. one of them is running the show.
        ArrayList<Employee> performerList1 = new ArrayList<>();
        performerList1.add(performerEmployee4);
        performerList1.add(performerEmployee2);

        Show fairyPerformance = new Show("Fairy Fun Frenzy", 15, performerList1, "Open", "East Stage", performerEmployee4);

        //Create a ride
        Ride crazyCoasterRide = new Ride("Crazy Coaster 3000", 20, false, "Open", firstEmployee);
        System.out.println(crazyCoasterRide);
        // Create a ride that doesn't have an operator, and see that the status is defaulted to closed
        Ride logFlumeRide = new Ride("Log Flume", 10);
        System.out.println(logFlumeRide);

        // Create a toilet
        Toilet basicToilet = new Toilet("West Toilet", true, true);

        // create an inspection that is in progress and start it
        Inspection toiletInspection = new Inspection(LocalDate.now(), inspector1, basicToilet);
        basicToilet.performInspection(toiletInspection);

        // Show the most recently added (i.e. most recently started) inspection
        System.out.println("\n" + basicToilet.getListOfInspections().getLast() + "\n");
        //Show the toilet status during inspection
        System.out.println("Toilet status during inspection: " + basicToilet.getStatus() + "\n");

        // End the inspection with a pass result
        toiletInspection.finishInspection("Pass");
        basicToilet.endInspection(toiletInspection.getInspectionResult());
        //Show the toilet status POST inspection
        System.out.println("Toilet status after inspection: " + basicToilet.getStatus() + "\n");


        // Show that the inspection status is now Complete after it was finished, and that the result was recorded.
        // Note that I am accessing this via the toilet object not the inspection object, showing the same object is being updated
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
        System.out.println(crazyCoasterRide.getListOfInspections().getLast() + "\n");




        //PART 3 - WAITING LINE
        //I already have visitors from the above, so I will add them to my waiting queue
    }
}
