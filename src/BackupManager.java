import park.*;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class BackupManager {
    private static final String rideLine = "RIDE|{ID}|{NAME}|{STATUS}|{RUNBYID}|{MAXCONCVISITORS}|[{VISITORWAITING}]|[{VISITORHISTORY}]|{NUMBEROFCYCLES}|{UNDERINSPECTION}|[{LISTOFINSPECTIONS}]\n";
    private static final String showLine = "SHOW|{ID}|{NAME}|{STATUS}|{RUNBYID}|{MAXCONCVISITORS}|[{VISITORWAITING}]|[{VISITORHISTORY}]|{NUMBEROFCYCLES}|{STAGENAME}|[{LISTOFPERFORMERS}]\n";
    private static final String employeeLine = "{ID}|{FIRSTNAME}|{LASTNAME}|{AGE}|{PHONENUMBER}|{STARTDATE}|{JOBTITLE}\n";
    private static final String visitorLine = "{ID}|{FIRSTNAME}|{LASTNAME}|{AGE}|{PHONENUMBER}\n";
    private static final String inspectionLine = "{ID}|{INSPECTIONDATE}|{INSPECTEDBY}|{STATUS}|{RESULT}|{INSPECTEDOBJECTNAME}\n";
    private static final String facilityLine = "{ID}|{NAME}|{STATUS}|{UNDERINSPECTION}|[{LISTOFINSPECTIONS}]|{HASCHILDFACILITIES}|{HASDISABLEDFACILITIES}\n";

    public BackupManager() {
    }

    public void initiateParkBackup(ThemeParkManager themePark) throws IOException {
        String visitorBackup = generateVisitorText(themePark.getVisitorMap());
        String employeeBackup = generateEmployeeText(themePark.getEmployeeMap());
        String inspectionBackup = generateInspectionText(themePark.getInspectionMap());
        String facilityBackup = generateFacilityText(themePark.getFacilityMap());
        String attractionBackup = generateAttractionText(themePark.getAttractionMap());
        System.out.println(visitorBackup);
        System.out.println(employeeBackup);
        System.out.println(facilityBackup);
        System.out.println(inspectionBackup);
        System.out.println(attractionBackup);
        String themeParkName = themePark.getName();
        String backupText = String.join("\n", themeParkName, visitorBackup, employeeBackup, inspectionBackup, facilityBackup, attractionBackup);
        createParkBackup(backupText);
    }

    private void createParkBackup(String backupText) throws IOException {
        String fileName = LocalDate.now() + "-backup.txt";
        Path path = Paths.get(fileName);
        try {
            Files.writeString(path, backupText);
            System.out.println("File successfully created inside project path with filename " + fileName);
        } catch (IOException e) {
            throw new IOException("File could not be created:" + e);
        }

    }

    private String generateVisitorText(HashMap<String,Visitor> visitors)  {
        String visitorBlock = "***VISITORS***\n";
        for (Visitor visitor : visitors.values()) {
            visitorBlock = visitorBlock + visitorLine.replace("{ID}", String.valueOf(visitor.getId()))
                    .replace("{FIRSTNAME}", visitor.getFirstName())
                    .replace("{LASTNAME}", visitor.getLastName())
                    .replace("{AGE}", String.valueOf(visitor.getAge()))
                    .replace("{PHONENUMBER}", visitor.getPhoneNumber());
        }
        visitorBlock = visitorBlock + "***END-VISITORS***";
        return visitorBlock;
    }
    
    private String generateEmployeeText(HashMap<Integer, Employee> employees) {
        String employeeBlock = "***EMPLOYEES***\n";
        for (Employee employee : employees.values()) {
            employeeBlock = employeeBlock + employeeLine.replace("{ID}", String.valueOf(employee.getId()))
                    .replace("{FIRSTNAME}", employee.getFirstName())
                    .replace("{LASTNAME}", employee.getLastName())
                    .replace("{AGE}", String.valueOf(employee.getAge()))
                    .replace("{PHONENUMBER}", employee.getPhoneNumber())
                    .replace("{STARTDATE}", employee.getStartOfEmployment().toString())
                    .replace("{JOBTITLE}", employee.getJobTitle());
        }
        employeeBlock = employeeBlock + "***END-EMPLOYEES***";
        return employeeBlock;
    }


    private String generateInspectionText(HashMap<Integer, Inspection> inspections) {
        String inspectionBlock = "***INSPECTIONS***\n";
        for (Inspection inspection : inspections.values()) {
            inspectionBlock = inspectionBlock + inspectionLine.replace("{ID}", String.valueOf(inspection.getId()))
                    .replace("{INSPECTIONDATE}", inspection.getInspectionDateTime().toString())
                    .replace("{INSPECTEDBY}", String.valueOf(inspection.getInspectedBy().getId()))
                    .replace("{STATUS}", inspection.getStatus())
                    .replace("{RESULT}", inspection.getInspectionResult())
                    .replace("{INSPECTEDOBJECTNAME}", inspection.getInspectedObjectName());
        }
        inspectionBlock = inspectionBlock + "***END-INSPECTIONS***";
        return inspectionBlock;
    }


    private String generateFacilityText(HashMap<Integer, Facility> facilities) {
        String facilityBlock = "***FACILITIES***\n";
        for (Facility facility : facilities.values()) {
            facilityBlock = facilityBlock + facilityLine.replace("{ID}", String.valueOf(facility.getId()))
                    .replace("{NAME}", facility.getName())
                    .replace("{STATUS}", facility.getStatus())
                    .replace("{UNDERINSPECTION}", String.valueOf(facility.isUnderInspection()))
                    .replace("{LISTOFINSPECTIONS}", deconstructInspectionListToId(facility.getListOfInspections()))
                    .replace("{HASCHILDFACILITIES}", String.valueOf(((Toilet) facility).isHasChildFacilities()))
                    .replace("{HASDISABLEDFACILITIES}", String.valueOf(((Toilet) facility).isHasDisabledFacilities()));
        }
        facilityBlock = facilityBlock + "***END-FACILITIES***";
        return facilityBlock;
    }


    private String generateAttractionText(HashMap<Integer, Attraction> attractions)  {
        String attractionBlock = "***ATTRACTIONS***\n";
        for (Attraction attraction : attractions.values()) {
            if (attraction instanceof Ride) {
                attractionBlock = attractionBlock + rideLine.replace("{ID}", String.valueOf(attraction.getId()))
                        .replace("{NAME}", attraction.getName())
                        .replace("{STATUS}", attraction.getStatus())
                        .replace("{RUNBYID}", attraction.getRunBy() == null ? "" : String.valueOf(attraction.getRunById()))
                        .replace("{MAXCONCVISITORS}", String.valueOf(attraction.getMaxConcurrentVisitors()))
                        .replace("{VISITORWAITING}",deconstructVisitorListToId(attraction.getVisitorsWaiting()))
                        .replace("{VISITORHISTORY}", deconstructVisitorListToId(attraction.getVisitorsVisited()))
                        .replace("{NUMBEROFCYCLES}", String.valueOf(attraction.getNumberOfCycles()))
                        .replace("{UNDERINSPECTION}", String.valueOf(((Ride) attraction).isUnderInspection()))
                        .replace("{LISTOFINSPECTIONS}", deconstructInspectionListToId(((Ride) attraction).getListOfInspections()));

            } else if (attraction instanceof Show) {
                attractionBlock = attractionBlock + showLine.replace("{ID}", String.valueOf(attraction.getId()))
                        .replace("{NAME}", attraction.getName())
                        .replace("{STATUS}", attraction.getStatus())
                        .replace("{RUNBYID}",  attraction.getRunBy() == null ? "" : String.valueOf(attraction.getRunById()))
                        .replace("{MAXCONCVISITORS}", String.valueOf(attraction.getMaxConcurrentVisitors()))
                        .replace("{VISITORWAITING}",deconstructVisitorListToId(attraction.getVisitorsWaiting()))
                        .replace("{VISITORHISTORY}", deconstructVisitorListToId(attraction.getVisitorsVisited()))
                        .replace("{NUMBEROFCYCLES}", String.valueOf(attraction.getNumberOfCycles()))
                        .replace("{STAGENAME}", ((Show) attraction).getStageLocationName() == null ? "" : ((Show) attraction).getStageLocationName())
                        .replace("{LISTOFPERFORMERS}",deconstructPerformerListToId(((Show) attraction).getPerformerList()));
            }
        }
        attractionBlock = attractionBlock + "***END-ATTRACTIONS***";
        return attractionBlock;
    }

    private String deconstructPerformerListToId(ArrayList<Employee> performerList) {
        List<String> idList = new ArrayList<>();
        for (Employee emp : performerList) {
            idList.add(String.valueOf(emp.getId()));
        }
        return String.join(",", idList);

    }

    private String deconstructInspectionListToId(LinkedList<Inspection> inspections) {
        String inspectionIdList = "";
        for (Inspection inspection : inspections) {
            inspectionIdList = inspectionIdList + String.valueOf(inspection.getId()) + ",";
        }
        if (inspectionIdList.endsWith(",")) {
            inspectionIdList = inspectionIdList.substring(0, inspectionIdList.length() - 1);
        }
        return inspectionIdList;
    }

    private String deconstructVisitorListToId(LinkedList<Visitor> visitors) {
        String visitorIdList = "";
        for (Visitor visitor : visitors) {
            visitorIdList = visitorIdList + String.valueOf(visitor.getId()) + ",";
        }
        if (visitorIdList.endsWith(",")) {
            visitorIdList = visitorIdList.substring(0, visitorIdList.length() - 1);
        }
        return visitorIdList;
    }


}
