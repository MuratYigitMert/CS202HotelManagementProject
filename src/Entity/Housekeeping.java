package Entity;

public class Housekeeping {
    // Fields corresponding to the Housekeeping table columns
    private int employeeId;          // Foreign key from Employee (employee_id)
    private String cleaningArea;     // Area assigned for cleaning
    private String shift;            // Shift of the employee

    // Constructor
    public Housekeeping(int employeeId, String cleaningArea, String shift) {
        this.employeeId = employeeId;
        this.cleaningArea = cleaningArea;
        this.shift = shift;
    }

    // Getters and Setters in your preferred format
    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

    public String getCleaningArea() { return cleaningArea; }
    public void setCleaningArea(String cleaningArea) { this.cleaningArea = cleaningArea; }

    public String getShift() { return shift; }
    public void setShift(String shift) { this.shift = shift; }

    // Optionally, override toString() for easy debugging/logging
    @Override
    public String toString() {
        return "Housekeeping{" +
                "employeeId=" + employeeId +
                ", cleaningArea='" + cleaningArea + '\'' +
                ", shift='" + shift + '\'' +
                '}';
    }
}

