// import javax.swing.SwingUtilities; // DELETE this line

// Note: No package declaration is here, making it the default package.
public class App {
    private static ComplaintManager manager = new ComplaintManager();
    public static ComplaintManager getManager() { return manager; }

    public static void main(String[] args) {

        // SAMPLE DATA (Testing before GUI launch)
        ComplaintManager mgr = getManager();
        try {
            mgr.submitComplaint(new Complaint(
                IDGenerator.nextComplaintId(),
                "Phishing attempt",
                "I received a phishing email asking for credentials",
                "U2001",
                1
            ));
            mgr.submitComplaint(new Complaint(
                IDGenerator.nextComplaintId(),
                "Account hacked",
                "My social account was accessed without permission",
                "U2002",
                1
            ));
            mgr.submitComplaint(new Complaint(
                IDGenerator.nextComplaintId(),
                "Fake website",
                "Fake banking site collecting user data",
                "U2003",
                2
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // DELETE the GUI launch code below:
        /*
        SwingUtilities.invokeLater(() -> {
            MainUI ui = new MainUI();
            ui.setVisible(true);
        });
        */
    }
}
