import javax.swing.*;
import java.awt.*;

/**
 * Main frame that hosts multiple panels
 */
public class MainUI extends JFrame {
    public MainUI() {
        setTitle("Cybercrime Complaint System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JTabbedPane tabs = new JTabbedPane();
        
        // These classes are now visible directly as they are in the default package
        tabs.addTab("Submit Complaint", new SubmitComplaintPanel());
        tabs.addTab("Track Complaint", new TrackComplaintPanel());
        tabs.addTab("Officer Dashboard", new OfficerDashboardPanel());

        add(tabs, BorderLayout.CENTER);
        setLocationRelativeTo(null);
    }
}