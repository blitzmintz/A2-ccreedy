import park.*;

import java.util.*;

public class ThemeParkManager {
    private String name;
    private HashMap<Integer, Attraction> attractionMap = new HashMap<>();
    private HashMap<String, Visitor> visitorMap = new HashMap<>();
    private HashMap<Integer, Employee> employeeMap = new HashMap<>();
    private HashMap<Integer, Inspection> inspectionMap = new HashMap<>();
    private HashMap<Integer, Facility> facilityMap = new HashMap<>();

    public ThemeParkManager(String name) {
        this.name = name;
    }

    public void addAttraction(Attraction attractionToAdd) {
        // do something, this will be called by the main method
        attractionMap.put(attractionToAdd.getId(), attractionToAdd);
        System.out.println(attractionToAdd.getName() + " added to theme park attractions. [ID " + attractionToAdd.getId() + "]");
    }

    public void addVisitor(Visitor visitorToAdd) {
        if (!visitorMap.containsKey(visitorToAdd.getPhoneNumber())) {
            visitorMap.put(visitorToAdd.getPhoneNumber(), visitorToAdd);
            System.out.println(visitorToAdd.getFullName() + " has entered the theme park for the first time. [ID " + visitorToAdd.getId() + "]");
        } else {
            System.out.println(visitorToAdd.getFullName() + " has returned to the theme park! [ID " + visitorToAdd.getId() + "]");
        }
    }

    public void addInspection(Inspection inspection) {
        inspectionMap.put(inspection.getId(), inspection);
    }

    public void addEmployee(Employee employeeToAdd) {
        employeeMap.put(employeeToAdd.getId(), employeeToAdd);
        System.out.println(employeeToAdd.getFullName() + " has been registered as an employee. [ID " + employeeToAdd.getId() + "]");

    }

    public void addFacility(Facility facilityToAdd) {
        facilityMap.put(facilityToAdd.getId(), facilityToAdd);
    }

    public void addVisitorsFromSet(TreeSet<Visitor> visitors) {
        for (Visitor visitor : visitors) {
            visitorMap.put(visitor.getPhoneNumber(), visitor);
            System.out.println(visitor.getFullName() + " has entered the theme park. [ID " + visitor.getId() + "]");
        }
    }

    public Attraction getAttractionById(int id) {
        if (attractionMap.containsKey(id)) {
            System.out.println("Attraction found for ID " + id);
            return attractionMap.get(id);
        } else {
            System.out.println("No attractions found for ID " + id);
            return null;
        }
    }

    public Employee getEmployeeById(String id) {
        if (id.isEmpty()) {
            System.out.println("No ID passed in");
            return null;
        }
        int convertedId = Integer.parseInt(id);
        if (employeeMap.containsKey(convertedId)) {
            System.out.println("Employee found for ID " + id);
            return employeeMap.get(convertedId);
        } else {
            System.out.println("No employee found for ID " + id);
            return null;
        }
    }

    public ArrayList<Employee> getEmployeesById(List<String> employeeIds) {
        ArrayList<Employee> employees = new ArrayList<>();
        for (String id : employeeIds) {
            if (id.isEmpty()) {
                continue;
            }
            int convertedId = Integer.parseInt(id);
            if (employeeMap.containsKey(convertedId)) {
                System.out.println("Employee found for ID " + id);
                employees.add(employeeMap.get(convertedId));
            } else {
                System.out.println("No employee found for ID " + id);
            }
        }
        return employees;
    }

    public LinkedList<Inspection> getInspectionsById(List<String> inspectionIds) {
        LinkedList<Inspection> inspections = new LinkedList<>();
        for (String id : inspectionIds) {
            if (id.isEmpty()) {
                continue;
            }
            int convertedId = Integer.parseInt(id);
            if (inspectionMap.containsKey(convertedId)) {
                System.out.println("Inspection found for ID " + id);
                inspections.add(inspectionMap.get(convertedId));
            } else {
                System.out.println("No inspection found for ID " + id);
            }
        }
        return inspections;

    }

    public LinkedList<Visitor> getVisitorsById(List<String> visitorIds) {
        LinkedList<Visitor> visitors = new LinkedList<>();
        for (String id : visitorIds) {
            if (id.isEmpty()) {
                continue;
            }
            for (Visitor visitor : visitorMap.values()) {
                if (visitor.getId() == Integer.parseInt(id)) {
                    System.out.println("Visitor found for ID " + id);
                    visitors.add(visitor);
                } else {
                    continue;
                }
            }
        }
        return visitors;
    }


    public String reportAllAttractionVisitCount() {
        String reportOfCounts = "";
        for (Attraction attraction : attractionMap.values()) {
            reportOfCounts = reportOfCounts + attraction.getName() + " Visit Count: " + attraction.getVisitorsVisited().size() + "\n";
        }
        return reportOfCounts;
    }

    public String reportSpecificAttractionVisitCount(int id) {
        Attraction attractionToReport = getAttractionById(id);
        String report = attractionToReport.getName() + " Visit Count: " + attractionToReport.getVisitorsVisited().size() + "\n";
        return report;

    }

    public Attraction getAttractionByName(String name) {
        for (Attraction attraction : attractionMap.values()) {
            if (attraction.getName().equalsIgnoreCase(name)) {
                return attraction;
            }
        }
        return null;
    }

    public int reportUniqueVisitorCount() {
        return visitorMap.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<Integer,Attraction> getAttractionMap() {
        return attractionMap;
    }

    public HashMap<String, Visitor> getVisitorMap() {
        return visitorMap;
    }

    public HashMap<Integer, Employee> getEmployeeMap() {
        return employeeMap;
    }

    public HashMap<Integer, Inspection> getInspectionMap() {
        return inspectionMap;
    }

    public HashMap<Integer, Facility> getFacilityMap() {
        return facilityMap;
    }
}
