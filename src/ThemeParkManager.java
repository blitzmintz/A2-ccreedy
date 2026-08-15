import park.Attraction;
import park.Visitor;

import java.util.Map;
import java.util.HashMap;
import java.util.TreeSet;

public class ThemeParkManager {
    private String name;
    private Map<Integer, Attraction> attractionMap = new HashMap<>();
    private Map<String, Visitor> visitorMap = new HashMap<>();

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

    public void addVisitorsFromSet(TreeSet<Visitor> visitors) {
        for (Visitor visitor : visitors) {
            visitorMap.put(visitor.getPhoneNumber(), visitor);
            System.out.println(visitor.getFullName() + " has entered the theme park for the first time. [ID " + visitor.getId() + "]");
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

    public int reportUniqueVisitorCount() {
        return visitorMap.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
