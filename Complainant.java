public class Complainant extends User {
    public Complainant(String userId, String name, String email) {
        super(userId, name, email);
    }

    @Override
    public String getRole() { return "Complainant"; }

    // Example: Complainant-only actions can be added here
}