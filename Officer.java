public class Officer extends User {
    private String department;

    public Officer(String userId, String name, String email, String department) {
        super(userId, name, email);
        this.department = department;
    }

    @Override
    public String getRole() { return "Officer"; }

    public String getDepartment() { return department; }

    // Officer-specific methods
}