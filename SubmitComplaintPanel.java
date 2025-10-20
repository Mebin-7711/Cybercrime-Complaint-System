import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Simple form to submit complaints
 */
public class SubmitComplaintPanel extends JPanel {
    private JTextField titleField, priorityField, reporterField;
    private JTextArea descArea;
    private JButton submitBtn;
    
    // The previous manager declaration/call is now correctly inside onSubmit()

    public SubmitComplaintPanel() {
        setLayout(new BorderLayout(8,8));
        JPanel form = new JPanel(new GridLayout(0,2,6,6));
        titleField = new JTextField();
        reporterField = new JTextField();
        priorityField = new JTextField("2"); // default priority
        descArea = new JTextArea(6,40);
        form.add(new JLabel("Title:")); form.add(titleField);
        form.add(new JLabel("Reporter ID:")); form.add(reporterField);
        form.add(new JLabel("Priority (1=High):")); form.add(priorityField);
        form.add(new JLabel("Description:")); form.add(new JScrollPane(descArea));

        submitBtn = new JButton("Submit Complaint");
        submitBtn.addActionListener(e -> onSubmit());
        add(form, BorderLayout.NORTH);
        add(submitBtn, BorderLayout.SOUTH);
    }

    private void onSubmit() {
        String id = IDGenerator.nextComplaintId();
        String title = titleField.getText().trim();
        String desc = descArea.getText().trim();
        String reporter = reporterField.getText().trim();
        int pr = 2;
        try {
            pr = Integer.parseInt(priorityField.getText().trim());
            if (pr < 1) pr = 1;
        } catch (NumberFormatException ex) { pr = 2; }
        
        Complaint c = new Complaint(id, title, desc, reporter, pr);
        
        try {
            // CALL App.getManager() DIRECTLY HERE (This is now correct)
            App.getManager().submitComplaint(c); 
            
            JOptionPane.showMessageDialog(this, "Submitted. Complaint ID: " + id);
            // clear fields
            titleField.setText(""); descArea.setText(""); // keep reporter for convenience
        } catch (ValidationException ve) {
            JOptionPane.showMessageDialog(this, "Error: " + ve.getMessage());
        }
    }
}