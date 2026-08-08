import models.Employee;
import models.Visitor;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class AssignmentTwo {
    public static void main(String[] args) {

        // PART 1
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

        Employee firstEmployee = new Employee("Pika", "Chu", 100, "0433355566", LocalDate.of(2020, 1, 1), "Mascot");
        System.out.println(firstEmployee);
        employeeSet.add(firstEmployee);

        Employee secondEmployee = new Employee("Evie", "Blue", 23, "0498175433", LocalDate.of(2026, 3, 3), "Performer");
        System.out.println(secondEmployee);
        employeeSet.add(secondEmployee);


    }
}
