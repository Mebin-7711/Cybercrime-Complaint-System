/**
 * Abstract base class for users in the system.
 */
public abstract class User {
    private String userId;
    private String name;
    private String email;

    protected User(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    public abstract String getRole();
}