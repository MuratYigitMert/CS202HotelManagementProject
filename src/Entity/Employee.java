package Entity;

public class Employee {
    // Fields corresponding to the Employee table columns
    private int employeeId;       // Primary key
    private int userId;           // Foreign key referencing Users table
    private int hotelId;          // Foreign key referencing Hotel table
    private int roleId;           // Foreign key referencing Roles table
    private double salary;        // Employee's salary

    // Constructor
    public Employee(int employeeId, int userId, int hotelId, int roleId, double salary) {
        this.employeeId = employeeId;
        this.userId = userId;
        this.hotelId = hotelId;
        this.roleId = roleId;
        this.salary = salary;
    }

    // Getters and Setters in your preferred format

    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getHotelId() { return hotelId; }
    public void setHotelId(int hotelId) { this.hotelId = hotelId; }

    public int getRoleId() { return roleId; }
    public void setRoleId(int roleId) { this.roleId = roleId; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    // Optionally, override toString() for easy debugging/logging
    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", userId=" + userId +
                ", hotelId=" + hotelId +
                ", roleId=" + roleId +
                ", salary=" + salary +
                '}';
    }
}
