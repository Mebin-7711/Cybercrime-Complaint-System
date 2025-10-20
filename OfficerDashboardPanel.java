import javax.swing.*;
import java.awt.*;


public class OfficerDashboardPanel extends JPanel {
    
    // Declare the manager field without immediate initialization
    private ComplaintManager manager; 
    private JTextArea displayArea;

    public OfficerDashboardPanel() {
        // Initialize the manager here using the static method from App
        manager = App.getManager(); 

        setLayout(new BorderLayout(6,6));
        JPanel top = new JPanel(new FlowLayout());
        
        JButton refresh = new JButton("Refresh Chronological");
        refresh.addActionListener(e -> refreshList());
        top.add(refresh);

        JButton sortPriority = new JButton("Sort by Priority");
        sortPriority.addActionListener(e -> showSortedByPriority());
        top.add(sortPriority);

        add(top, BorderLayout.NORTH);
        displayArea = new JTextArea(20,70);
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);
        
        // This call is now safe because 'manager' has been initialized above
        refreshList(); 
    }

    private void refreshList() {
        StringBuilder sb = new StringBuilder();
        MyLinkedList<Complaint> list = manager.listChronological();
        for (Complaint c: list) {
            sb.append(c.toString()).append("\n");
        }
        displayArea.setText(sb.toString());
    }

    private void showSortedByPriority() {
        Complaint[] arr = manager.sortedByPriority();
        StringBuilder sb = new StringBuilder();
        for (Complaint c: arr) sb.append(c.toString()).append("\n");
        displayArea.setText(sb.toString());
    }
}