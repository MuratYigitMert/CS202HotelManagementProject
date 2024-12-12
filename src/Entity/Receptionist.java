package Entity;

public class Receptionist {
    // Fields corresponding to the Receptionist table columns
    private int employeeId;            // Foreign key from Employee (employee_id)
    private String languagesSpoken;    // Languages spoken by the receptionist
    private String workShift;          // Work shift for the receptionist

    // Constructor
    public Receptionist(int employeeId, String languagesSpoken, String workShift) {
        this.employeeId = employeeId;
        this.languagesSpoken = languagesSpoken;
        this.workShift = workShift;
    }

    // Getters and Setters in your preferred format
    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

    public String getLanguagesSpoken() { return languagesSpoken; }
    public void setLanguagesSpoken(String languagesSpoken) { this.languagesSpoken = languagesSpoken; }

    public String getWorkShift() { return workShift; }
    public void setWorkShift(String workShift) { this.workShift = workShift; }

    // Optionally, override toString() for easy debugging/logging
    @Override
    public String toString() {
        return "Receptionist{" +
                "employeeId=" + employeeId +
                ", languagesSpoken='" + languagesSpoken + '\'' +
                ", workShift='" + workShift + '\'' +
                '}';
    }
}
