import javax.swing.*;
import java.awt.*;


public class TrackComplaintPanel extends JPanel {
    private JTextField idField;
    private JTextArea resultArea;
    
    // Declare the manager field without immediate initialization
    private ComplaintManager manager; 

    public TrackComplaintPanel(){
        // INITIALIZE THE MANAGER HERE in the constructor
        manager = App.getManager();
        
        setLayout(new BorderLayout(6,6));
        JPanel top = new JPanel(new FlowLayout());
        top.add(new JLabel("Complaint ID:"));
        idField = new JTextField(12);
        top.add(idField);
        JButton b = new JButton("Track");
        b.addActionListener(e -> onTrack());
        top.add(b);
        add(top, BorderLayout.NORTH);
        resultArea = new JTextArea(10,60);
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);
    }

    private void onTrack(){
        String id = idField.getText().trim();
        if (id.isEmpty()) { JOptionPane.showMessageDialog(this, "Enter ID"); return; }
        
        // Use the initialized manager
        Complaint c = manager.findById(id); 
        
        if (c == null) resultArea.setText("No complaint found with ID: " + id);
        else resultArea.setText(c.toString());
    }
}