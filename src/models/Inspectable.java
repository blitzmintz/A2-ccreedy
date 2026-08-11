package models;

public interface Inspectable {

    void performInspection();
    void endInspection(boolean passResult);


    // a default method allows us to give an implementation body in an interface, that can be overridden by implementations
    default String validateInspectionResult(String result) {
        String validatedResult = "";
        switch (result.trim().toUpperCase()) {
            case "PASS", "PASSED", "SUCCESS" -> validatedResult = "Pass";
            case "FAIL", "FAILED", "UNSUCCESSFUL", "NOT SUCCESSFUL" -> validatedResult = "Fail";
            default -> validatedResult = "Unknown";
        }
        return validatedResult;
    }
}
