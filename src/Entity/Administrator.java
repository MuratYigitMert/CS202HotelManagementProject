package Entity;

public class Administrator {
    // Fields corresponding to the Administrator table columns
    private int employeeId;           // Foreign key from Employee (employee_id)
    private String accessLevel;       // Access level for the administrator
    private String department;        // Department the administrator works in

    // Constructor
    public Administrator(int employeeId, String accessLevel, String department) {
        this.employeeId = employeeId;
        this.accessLevel = accessLevel;
        this.department = department;
    }

    // Getters and Setters in your preferred format
    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

    public String getAccessLevel() { return accessLevel; }
    public void setAccessLevel(String accessLevel) { this.accessLevel = accessLevel; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    // Optionally, override toString() for easy debugging/logging
    @Override
    public String toString() {
        return "Administrator{" +
                "employeeId=" + employeeId +
                ", accessLevel='" + accessLevel + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
