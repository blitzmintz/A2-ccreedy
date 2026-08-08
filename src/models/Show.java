package models;

public class Show extends Attraction {

    public Show(String name, int maxConcurrentVisitors, Employee runBy, String status) {
        super(name, maxConcurrentVisitors, runBy, status);
    }

    public Show(String name, int maxConcurrentVisitors, Employee runBy) {
        super(name, maxConcurrentVisitors, runBy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Show other = (Show) o;
        return this.getId() == other.getId();
    }
}
