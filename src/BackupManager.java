import park.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

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
        System.out.println("Initiating park backup - generating backup for visitors.");
        String visitorBackup = generateVisitorText(themePark.getVisitorMap());
        System.out.println("Initiating park backup - generating backup for employees.");
        String employeeBackup = generateEmployeeText(themePark.getEmployeeMap());
        System.out.println("Initiating park backup - generating backup for inspections.");
        String inspectionBackup = generateInspectionText(themePark.getInspectionMap());
        System.out.println("Initiating park backup - generating backup for facilities.");
        String facilityBackup = generateFacilityText(themePark.getFacilityMap());
        System.out.println("Initiating park backup - generating backup for attractions.");
        String attractionBackup = generateAttractionText(themePark.getAttractionMap());
        String themeParkName = "THEME PARK NAME: " + themePark.getName();
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

    /**
     * This method restores the park from a backed up file. It tracks what lines to ingest as real data (done where insideObjectBlock = true)
     * and for header/footer lines it changes that state accordingly.
     * @param fileName the name of the file being read in to restore
     * @throws IOException thrown where an issue is encountered while reading the file
     * @throws FileNotFoundException thrown where the file cannot be found at the specified path
     */
    public ThemeParkManager initiateParkRestoreFromFile(File fileName) throws IOException, FileNotFoundException {
        System.out.println("Starting the restore");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean insideObjectBlock = false;
            List<String[]> currentObjectBlockLines = new ArrayList<>();
            String parkName = bufferedReader.readLine();
            String name = parkName.substring(parkName.lastIndexOf(": "));
            System.out.println("Restoring theme park with the name: " + name);
            ThemeParkManager themePark = createNewPark(name);

            while ((line = bufferedReader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }

                if (line.equals("***VISITORS***")) {
                    insideObjectBlock = true;
                    //this resets the array for the next block to start
                    currentObjectBlockLines.clear();
                    continue;
                }

                if (line.equals("***END-VISITORS***")) {
                    if (insideObjectBlock) {
                        insideObjectBlock = false;
                        processVisitorBlock(currentObjectBlockLines,themePark);
                    }
                    //if insideObjectBlock is false, this means that there was no beginning to the block,
                    // so there'll be nothing to process - so just keep going
                    continue;
                }

                if (line.equals("***EMPLOYEES***")) {
                    insideObjectBlock = true;
                    currentObjectBlockLines.clear();
                    continue;
                }

                if (line.equals("***END-EMPLOYEES***")) {
                    if (insideObjectBlock) {
                        insideObjectBlock = false;
                        processEmployeeBlock(currentObjectBlockLines,themePark);
                    }
                    continue;
                }
                if (line.equals("***FACILITIES***")) {
                    insideObjectBlock = true;
                    currentObjectBlockLines.clear();
                    continue;
                }

                if (line.equals("***END-FACILITIES***")) {
                    if (insideObjectBlock) {
                        insideObjectBlock = false;
                        processFacilitiesBlock(currentObjectBlockLines,themePark);
                    }
                    continue;
                }

                if (line.equals("***INSPECTIONS***")) {
                    insideObjectBlock = true;
                    currentObjectBlockLines.clear();
                    continue;
                }

                if (line.equals("***END-INSPECTIONS***")) {
                    if (insideObjectBlock) {
                        insideObjectBlock = false;
                        processInspectionBlock(currentObjectBlockLines,themePark);
                    }
                    continue;
                }
                if (line.equals("***ATTRACTIONS***")) {
                    insideObjectBlock = true;
                    currentObjectBlockLines.clear();
                    continue;
                }

                if (line.equals("***END-ATTRACTIONS***")) {
                    if (insideObjectBlock) {
                        insideObjectBlock = false;
                        processAttractionsBlock(currentObjectBlockLines, themePark);
                    }
                    //if insideObjectBlock is false, this means that there was no beginning to the block,
                    // so there'll be nothing to process - so just keep going
                    continue;
                }

                if (insideObjectBlock) {
                    // Splitting each line into an array that is split by the pipe delimiter used when generating the backup file
                    // split() method documentation advises that a negative limit parameter will allow the split to happen as many times as the delimiter is found
                    String[] objectLine = line.split("\\|", -1);
                    currentObjectBlockLines.add(objectLine);
                }
            }
            System.out.println("Your park has been successfully restored from backup!");
            return themePark;
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File Not found: " + e);
        } catch (IOException e) {
            throw new IOException("IO Exception: " + e);
        }
    }

    private ThemeParkManager createNewPark(String name) {
        ThemeParkManager parkFromRestore = new ThemeParkManager(name);
        System.out.println("New park successfully created.");
        return parkFromRestore;
    }

    private void processVisitorBlock(List<String[]> visitors, ThemeParkManager themePark) {
        for (String[] attribute : visitors) {
            Visitor visitor = new Visitor(attribute[0], attribute[1], attribute[2], attribute[3], attribute[4]);
            themePark.addVisitor(visitor);
        }
    }

    private void processEmployeeBlock(List<String[]> employees, ThemeParkManager themePark) {
        for (String[] attribute : employees) {
            Employee employee = new Employee(attribute[0], attribute[1], attribute[2], attribute[3], attribute[4], attribute[5], attribute[6]);
            themePark.addEmployee(employee);
        }
    }

    private void processInspectionBlock(List<String[]> inspections, ThemeParkManager themePark) {
        for (String[] attribute : inspections) {
            Employee employee = themePark.getEmployeeById(attribute[2]);
            Inspection inspection = new Inspection(attribute[0], attribute[1], employee ,attribute[3],attribute[4], attribute[5]);
            themePark.addInspection(inspection);
        }
    }

    private void processFacilitiesBlock(List<String[]> facilities, ThemeParkManager themePark) {
        for (String[] attribute : facilities) {
            if (attribute.length > 5) {
                String listOfInspectionsRaw = attribute[4].substring(1, attribute[4].length() - 1);
                List<String> listOfInspectionIds = Arrays.asList(listOfInspectionsRaw.split(","));
                LinkedList<Inspection> fullList = themePark.getInspectionsById(listOfInspectionIds);
                Toilet toilet = new Toilet(attribute[0], attribute[1], attribute[2], attribute[3], fullList, attribute[5], attribute[6]);
                themePark.addFacility(toilet);
            }
        }
    }
    private void processAttractionsBlock(List<String[]> attractions, ThemeParkManager themePark) {
        for (String[] attribute : attractions) {
            String rawVisitorWaitingId = attribute[6].substring(1, attribute[6].length() - 1);
            List<String> listOfVisitorWaitingIds = Arrays.asList(rawVisitorWaitingId.split(","));
            LinkedList<Visitor> fullVisitorWaitingList = themePark.getVisitorsById(listOfVisitorWaitingIds);

            String rawVisitoryHistoryId = attribute[7].substring(1, attribute[7].length() - 1);
            List<String> listOfVisitoryHistoryIds = Arrays.asList(rawVisitoryHistoryId.split(","));
            LinkedList<Visitor> fullVisitorHistoryList = themePark.getVisitorsById(listOfVisitoryHistoryIds);

            Employee runBy = themePark.getEmployeeById(attribute[4]);

            if (attribute[0].equals("RIDE")) {
                String rawInspectionList = attribute[10].substring(1, attribute[10].length() - 1);
                List<String> listOfInspectionIds = Arrays.asList(rawInspectionList.split(","));
                LinkedList<Inspection> fullInspectionList = themePark.getInspectionsById(listOfInspectionIds);

                Ride ride = new Ride(attribute[1], attribute[2], attribute[3], runBy,(attribute[5])
                , fullVisitorWaitingList, fullVisitorHistoryList, attribute[8], attribute[9], fullInspectionList);

                themePark.addAttraction(ride);

            } else {
                String rawPerfomerList = attribute[10].substring(1, attribute[10].length() - 1);
                List<String> listOfPerformerIds = Arrays.asList(rawPerfomerList.split(","));
                ArrayList<Employee> fullPerformerList = themePark.getEmployeesById(listOfPerformerIds);

                Show show = new Show(attribute[1], attribute[2], attribute[3], runBy, attribute[5]
                        , fullVisitorWaitingList, fullVisitorHistoryList, attribute[8], fullPerformerList,attribute[9]);

                themePark.addAttraction(show);
            }
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
        System.out.println("Visitor block processed successfully.");
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
        System.out.println("Employee block processed successfully.");
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
        System.out.println("Inspection block processed successfully.");
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
        System.out.println("Facility block processed successfully.");

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
        System.out.println("Attractions block processed successfully.");

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
