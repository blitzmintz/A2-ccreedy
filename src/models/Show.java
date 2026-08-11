package models;

public class Show extends Attraction {

    private String showName;
    private String stageLocationName;

    public Show(String name, int maxConcurrentVisitors, Employee runBy) {
        super(name, maxConcurrentVisitors, runBy);
    }

    public Show(String name, int maxConcurrentVisitors, Employee runBy, String status) {
        super(name, maxConcurrentVisitors, runBy, status);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Show other = (Show) o;
        return this.getId() == other.getId();
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public void setStageLocationName(String stageLocationName) {
        this.stageLocationName = stageLocationName;
    }

    public String getShowName() {
        return showName;
    }

    public String getStageLocationName() {
        return stageLocationName;
    }
}
