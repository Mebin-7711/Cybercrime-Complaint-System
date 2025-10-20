import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CyberComplaintUI extends JFrame {

    private ComplaintManager manager; // Manager instance from App.java
    
    // UI Layout Components
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // --- Screen 1: Add Complaint Components (Submission) ---
    private JTextField titleField, priorityField, reporterField, nameField, emailField; // Re-purposing fields
    private JTextArea descArea;
    private JButton submitBtn;

    // --- Screen 2: View/Track/Sort Components (Officer/Tracker) ---
    private JTextArea displayArea; // For chronological/sorted list
    private JTextField trackIdField; // For tracking a single ID
    private JButton trackBtn;

    // Define colors (for modern styling) 
    Color colorHeaderBlue = new Color(0, 114, 198);
    Color colorNavDark = new Color(51, 51, 51);
    Color colorNavHover = new Color(80, 80, 80);
    Color colorSubmitGreen = new Color(40, 167, 69);
    Color colorSectionBorder = new Color(220, 220, 220);

    public CyberComplaintUI() {
        // Initialize the ComplaintManager instance
        manager = App.getManager(); 
        
        setTitle("Cybersecurity Complaint System (DS/OOP Project)");
        setSize(1000, 700); // Make it large
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());

        // 1. Create Header Panel (NORTH)
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(colorHeaderBlue);
        
        JLabel appTitle = new JLabel("Cybersecurity Complaint System"); 
        appTitle.setFont(new Font("Arial", Font.BOLD, 18));
        appTitle.setForeground(Color.WHITE);
        appTitle.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        headerPanel.add(appTitle);
        
        add(headerPanel, BorderLayout.NORTH);

        // 2. Create Navigation Panel (WEST)
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBackground(colorNavDark);
        navPanel.setPreferredSize(new Dimension(200, 0));
        navPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Add navigation buttons
        JButton addBtn = createNavButton("1. Submit Complaint");   
        JButton viewBtn = createNavButton("2. Officer Dashboard");
        JButton trackSingleBtn = createNavButton("3. Track by ID");
        
        navPanel.add(addBtn);
        navPanel.add(viewBtn);
        navPanel.add(trackSingleBtn);
        navPanel.add(Box.createVerticalGlue()); 

        add(navPanel, BorderLayout.WEST);

        // 3. Create Main Content Panel (CENTER)
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(Color.WHITE);

        // Initialize Panels
        JPanel addComplaintPanel = createSubmitComplaintPanel(); // Submission
        JPanel officerDashboardPanel = createOfficerDashboardPanel(); // Chronological/Sorting
        JPanel trackComplaintPanel = createTrackComplaintPanel(); // Track by ID

        mainPanel.add(addComplaintPanel, "Submit");
        mainPanel.add(officerDashboardPanel, "Dashboard");
        mainPanel.add(trackComplaintPanel, "Track");

        add(mainPanel, BorderLayout.CENTER);

        // Add actions to nav buttons
        addBtn.addActionListener(e -> cardLayout.show(mainPanel, "Submit"));
        viewBtn.addActionListener(e -> { cardLayout.show(mainPanel, "Dashboard"); refreshDashboard(); });
        trackSingleBtn.addActionListener(e -> cardLayout.show(mainPanel, "Track"));
        
        // Initial view
        cardLayout.show(mainPanel, "Submit");
        setVisible(true);
    }

    // ====================================================================
    // UI HELPER METHODS
    // ====================================================================

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(colorNavDark);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getPreferredSize().height));

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) { button.setBackground(colorNavHover); }
            public void mouseExited(MouseEvent evt) { button.setBackground(colorNavDark); }
        });
        return button;
    }

    private JLabel createSectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(colorHeaderBlue);
        label.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, colorSectionBorder),
            BorderFactory.createEmptyBorder(15, 0, 5, 0)
        ));
        return label;
    }
    
    // ====================================================================
    // 1. SUBMIT COMPLAINT PANEL (Form)
    // ====================================================================

    private JPanel createSubmitComplaintPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 1. Title Bar (NORTH)
        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(Color.WHITE);
        
        JLabel title = new JLabel("1. Submit New Complaint");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        titleBar.add(title, BorderLayout.WEST);

        submitBtn = new JButton("Submit Complaint");
        submitBtn.setBackground(colorSubmitGreen);
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFocusPainted(false);
        submitBtn.setFont(new Font("Arial", Font.BOLD, 14));
        submitBtn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        submitBtn.addActionListener(e -> onSubmitComplaint());
        
        titleBar.add(submitBtn, BorderLayout.EAST);
        panel.add(titleBar, BorderLayout.NORTH);

        // 2. Form Panel (CENTER) - Using GridBagLayout for flexible form
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST; 
        gbc.weightx = 1.0; 

        // --- Complaint Details ---
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        form.add(createSectionLabel("Complaint Details"), gbc);

        // Row 1: Title and Priority
        gbc.gridy++; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        form.add(new JLabel("Complaint Title:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        titleField = new JTextField(20); form.add(titleField, gbc);

        gbc.gridx = 2; gbc.weightx = 0.0; gbc.fill = GridBagConstraints.NONE;
        form.add(new JLabel("Priority (1=High):"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.5; gbc.fill = GridBagConstraints.HORIZONTAL;
        priorityField = new JTextField("2", 5); form.add(priorityField, gbc);

        // Row 2: Reporter ID
        gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        form.add(new JLabel("Reporter ID (e.g., U2001):"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        reporterField = new JTextField(); form.add(reporterField, gbc);
        
        // Row 3: Description (Grows Vertically)
        gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 4; gbc.fill = GridBagConstraints.HORIZONTAL;
        form.add(createSectionLabel("Description and Details"), gbc);

        gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        form.add(new JLabel("Detailed Description:"), gbc);
        
        gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 4; gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0; // Allow the text area to take up vertical space
        descArea = new JTextArea(10, 50);
        form.add(new JScrollPane(descArea), gbc);
        
        panel.add(form, BorderLayout.CENTER);
        return panel;
    }

    private void onSubmitComplaint() {
        String id = IDGenerator.nextComplaintId();
        String title = titleField.getText().trim();
        String desc = descArea.getText().trim();
        String reporter = reporterField.getText().trim();
        int pr = 2;
        
        try {
            // Priority Validation
            String prText = priorityField.getText().trim();
            if (prText.isEmpty()) throw new ValidationException("Priority cannot be empty.");
            pr = Integer.parseInt(prText);
            if (pr < 1) pr = 1;
        } catch (NumberFormatException ex) { 
            JOptionPane.showMessageDialog(this, "Error: Priority must be a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return; 
        } catch (ValidationException ve) {
             JOptionPane.showMessageDialog(this, "Error: " + ve.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
            return; 
        }
        
        Complaint c = new Complaint(id, title, desc, reporter, pr);
        
        try {
            manager.submitComplaint(c); 
            
            JOptionPane.showMessageDialog(this, 
                String.format("Complaint Submitted!%nID: %s%nTitle: %s", id, title), 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Clear fields (keep reporter ID for convenience)
            titleField.setText(""); descArea.setText(""); priorityField.setText("2"); 
        } catch (ValidationException ve) {
            JOptionPane.showMessageDialog(this, "Submission Error: " + ve.getMessage(), "Submission Failed", JOptionPane.ERROR_MESSAGE);
        }
    }


    // ====================================================================
    // 2. OFFICER DASHBOARD PANEL (Chronological and Sorted Views)
    // ====================================================================
    
    private JPanel createOfficerDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title and Control Bar
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        top.setBackground(Color.WHITE);

        JLabel title = new JLabel("2. Officer Dashboard");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        top.add(title);
        
        top.add(Box.createHorizontalStrut(50));
        
        JButton refresh = new JButton("View Chronological (Linked List)");
        refresh.addActionListener(e -> refreshDashboard());
        top.add(refresh);

        JButton sortPriority = new JButton("Sort by Priority (Insertion Sort)");
        sortPriority.addActionListener(e -> showSortedByPriority());
        top.add(sortPriority);

        panel.add(top, BorderLayout.NORTH);
        
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        panel.add(new JScrollPane(displayArea), BorderLayout.CENTER);
        
        refreshDashboard(); // Initial load
        return panel;
    }

    private void refreshDashboard() {
        StringBuilder sb = new StringBuilder("--- ALL COMPLAINTS (CHRONOLOGICAL ORDER - LINKED LIST) ---\n\n");
        MyLinkedList<Complaint> list = manager.listChronological();
        if (list.size() == 0) {
            sb.append("No complaints have been submitted yet.");
        } else {
            for (Complaint c: list) {
                sb.append(c.toString()).append("\n");
            }
        }
        displayArea.setText(sb.toString());
    }

    private void showSortedByPriority() {
        StringBuilder sb = new StringBuilder("--- ALL COMPLAINTS (SORTED BY PRIORITY - INSERTION SORT) ---\n\n");
        Complaint[] arr = manager.sortedByPriority();
        if (arr.length == 0) {
            sb.append("No complaints have been submitted yet.");
        } else {
            for (Complaint c: arr) sb.append(c.toString()).append("\n");
        }
        displayArea.setText(sb.toString());
    }


    // ====================================================================
    // 3. TRACK COMPLAINT PANEL (Search by ID)
    // ====================================================================

    private JPanel createTrackComplaintPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE); 
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // 1. Title and Input Bar (NORTH)
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        top.setBackground(Color.WHITE);
        
        JLabel title = new JLabel("3. Track Complaint by ID");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        top.add(title);

        top.add(Box.createHorizontalStrut(20));
        
        top.add(new JLabel("Complaint ID (e.g., C1001):"));
        trackIdField = new JTextField(12);
        top.add(trackIdField);
        
        trackBtn = new JButton("Track Complaint (BST Search)");
        trackBtn.addActionListener(e -> onTrackComplaint());
        top.add(trackBtn);
        
        panel.add(top, BorderLayout.NORTH);
        
        // 2. Result Area (CENTER)
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        displayArea.setText("\nEnter a Complaint ID and click 'Track Complaint'.\n\n(Uses the custom Binary Search Tree for fast lookup)");
        panel.add(new JScrollPane(displayArea), BorderLayout.CENTER);
        
        return panel;
    }

    private void onTrackComplaint(){
        String id = trackIdField.getText().trim();
        if (id.isEmpty()) { 
            JOptionPane.showMessageDialog(this, "Enter Complaint ID to track.", "Input Error", JOptionPane.ERROR_MESSAGE); 
            return; 
        }
        
        // Use the initialized manager, which performs a BST search
        Complaint c = manager.findById(id); 
        
        if (c == null) {
            displayArea.setText(String.format("No complaint found with ID: %s", id));
        } else {
            displayArea.setText(String.format("--- COMPLAINT FOUND (BST LOOKUP) ---\n\n%s", c.toString()));
        }
    }


    // ====================================================================
    // MAIN METHOD
    // ====================================================================

    public static void main(String[] args) {
        // Since this file now contains the main method, we need a simple App runner
        // to initialize the manager before the UI is built.
        
        // This structure ensures App.main() is run first to initialize the manager
        // and then the UI is launched.
        App.main(args); 
        
        SwingUtilities.invokeLater(() -> new CyberComplaintUI());
    }
}
