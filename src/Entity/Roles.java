package Entity;

public class Roles {
    // Fields corresponding to the Roles table columns
    private int roleId;         // Primary key
    private String roleName;    // Name of the role (e.g., Administrator, Receptionist)

    // Constructor
    public Roles(int roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    // Getters and Setters in your preferred format
    public int getRoleId() { return roleId; }
    public void setRoleId(int roleId) { this.roleId = roleId; }

    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }

    // Optionally, override toString() for easy debugging/logging
    @Override
    public String toString() {
        return "Roles{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
