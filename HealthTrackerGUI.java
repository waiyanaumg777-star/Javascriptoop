package ui;

import model.*;
import service.HealthDataManager;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class HealthTrackerGUI extends JFrame {
    private HealthDataManager dataManager;
    private JTabbedPane tabbedPane;
    private DefaultTableModel userTableModel;
    private User currentUser;
    private JLabel currentUserLabel;
    private JTextArea outputArea;
    private Color primaryColor = new Color(46, 204, 113);
    private Color secondaryColor = new Color(52, 152, 219);
    private Color warningColor = new Color(241, 196, 15);
    private Color dangerColor = new Color(231, 76, 60);
    
    public HealthTrackerGUI() {
        dataManager = HealthDataManager.getInstance();
        initUI();
        loadSampleData();
    }
    
    private void initUI() {
        setTitle("🏥 Student Health Tracker - Supporting SDG 3: Good Health & Well-being");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 800);
        setLocationRelativeTo(null);
        
        // Set modern look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        // Header panel
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        
        // Status bar
        mainPanel.add(createStatusBar(), BorderLayout.SOUTH);
        
        // Tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabbedPane.addTab("👥 Student Management", createStudentManagementPanel());
        tabbedPane.addTab("➕ Add Health Record", createAddRecordPanel());
        tabbedPane.addTab("📋 View Records", createViewRecordsPanel());
        tabbedPane.addTab("📊 Health Analytics", createAnalyticsPanel());
        tabbedPane.addTab("💡 Recommendations", createRecommendationsPanel());
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        add(mainPanel);
        
        // Disable tabs until user is selected
        enableTabs(false);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(primaryColor);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setBackground(primaryColor);
        
        JLabel titleLabel = new JLabel("🏥 Student Health Tracker System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel sdgLabel = new JLabel("🌍 Supporting UN Sustainable Development Goal 3: Good Health and Well-being");
        sdgLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        sdgLabel.setForeground(Color.WHITE);
        
        titlePanel.add(titleLabel);
        titlePanel.add(sdgLabel);
        
        currentUserLabel = new JLabel("👤 No Student Selected");
        currentUserLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        currentUserLabel.setForeground(Color.WHITE);
        currentUserLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 1),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        
        panel.add(titlePanel, BorderLayout.WEST);
        panel.add(currentUserLabel, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel createStatusBar() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        JLabel statusLabel = new JLabel("✅ System Ready | Data is automatically saved");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        statusLabel.setForeground(new Color(100, 100, 100));
        panel.add(statusLabel);
        
        return panel;
    }
    
    private JPanel createStudentManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);
        
        // Form panel
        JPanel formPanel = createStudentFormPanel();
        
        // Table panel
        JPanel tablePanel = createStudentTablePanel();
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, formPanel, tablePanel);
        splitPane.setResizeWeight(0.3);
        splitPane.setBorder(null);
        
        panel.add(splitPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createStudentFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)), 
            "📝 Register New Student",
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12)
        ));
        panel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JTextField nameField = createStyledTextField();
        JTextField studentIdField = createStyledTextField();
        JTextField emailField = createStyledTextField();
        JTextField ageField = createStyledTextField();
        JComboBox<String> genderCombo = createStyledComboBox(new String[]{"Male", "Female", "Other"});
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Full Name:*"), gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Student ID:*"), gbc);
        gbc.gridx = 1;
        panel.add(studentIdField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        panel.add(emailField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 1;
        panel.add(ageField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Gender:"), gbc);
        gbc.gridx = 1;
        panel.add(genderCombo, gbc);
        
        JButton addButton = createStyledButton("➕ Register Student", primaryColor);
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(addButton, gbc);
        
        addButton.addActionListener(e -> {
            try {
                String userId = "STU" + System.currentTimeMillis();
                String name = nameField.getText().trim();
                String studentId = studentIdField.getText().trim();
                String email = emailField.getText().trim();
                int age = Integer.parseInt(ageField.getText().trim());
                String gender = (String) genderCombo.getSelectedItem();
                
                if (name.isEmpty() || studentId.isEmpty()) {
                    showMessage("Please fill required fields!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                User user = new User(userId, name, studentId, email, age, gender);
                if (dataManager.addUser(user)) {
                    showMessage("✅ Student registered successfully!\nUser ID: " + userId, "Success", JOptionPane.INFORMATION_MESSAGE);
                    nameField.setText("");
                    studentIdField.setText("");
                    emailField.setText("");
                    ageField.setText("");
                    refreshUserTable();
                } else {
                    showMessage("Failed to register student!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                showMessage("Please enter a valid age!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        return panel;
    }
    
    private JPanel createStudentTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "📋 Registered Students",
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12)
        ));
        panel.setBackground(Color.WHITE);
        
        String[] columns = {"User ID", "Full Name", "Student ID", "Email", "Age", "Gender"};
        userTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable userTable = new JTable(userTableModel);
        userTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        userTable.setRowHeight(25);
        userTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBorder(null);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton selectButton = createStyledButton("✅ Select Student", secondaryColor);
        JButton refreshButton = createStyledButton("🔄 Refresh", new Color(149, 165, 166));
        
        selectButton.addActionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow >= 0) {
                String userId = (String) userTableModel.getValueAt(selectedRow, 0);
                currentUser = dataManager.getUser(userId);
                currentUserLabel.setText("👤 " + currentUser.getFullName() + " (" + currentUser.getStudentId() + ")");
                enableTabs(true);
                showMessage("✅ Selected: " + currentUser.getFullName() + 
                           "\nYou can now add health records and view analytics.", 
                           "Student Selected", JOptionPane.INFORMATION_MESSAGE);
            } else {
                showMessage("Please select a student first!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        refreshButton.addActionListener(e -> refreshUserTable());
        
        buttonPanel.add(selectButton);
        buttonPanel.add(refreshButton);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        refreshUserTable();
        return panel;
    }
    
    private JPanel createAddRecordPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTabbedPane recordTabs = new JTabbedPane();
        recordTabs.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        recordTabs.addTab("😴 Sleep", createSleepRecordPanel());
        recordTabs.addTab("🏃 Exercise", createExerciseRecordPanel());
        recordTabs.addTab("🍎 Nutrition", createNutritionRecordPanel());
        recordTabs.addTab("🧠 Stress", createStressRecordPanel());
        
        panel.add(recordTabs, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createSleepRecordPanel() {
        JPanel panel = createFormPanel("😴 Add Sleep Record");
        
        JTextField hoursField = createStyledTextField();
        JComboBox<Integer> qualityCombo = createStyledComboBox(new Integer[]{1, 2, 3, 4, 5});
        
        addFormField(panel, "Hours Slept:", hoursField, 0);
        addFormField(panel, "Sleep Quality (1-5):", qualityCombo, 1);
        
        JButton saveButton = createStyledButton("💾 Save Sleep Record", primaryColor);
        addButtonToPanel(panel, saveButton, 2);
        
        saveButton.addActionListener(e -> saveRecord(() -> {
            double hours = Double.parseDouble(hoursField.getText());
            int quality = (Integer) qualityCombo.getSelectedItem();
            String recordId = "SLEEP" + System.currentTimeMillis();
            SleepRecord record = new SleepRecord(recordId, currentUser.getUserId(), LocalDate.now(), hours, quality);
            dataManager.addHealthRecord(record);
            hoursField.setText("");
            return record;
        }));
        
        return panel;
    }
    
    private JPanel createExerciseRecordPanel() {
        JPanel panel = createFormPanel("🏃 Add Exercise Record");
        
        JComboBox<String> typeCombo = createStyledComboBox(new String[]{"Running", "Walking", "Swimming", "Cycling", "Gym", "Yoga", "Sports"});
        JTextField durationField = createStyledTextField();
        JComboBox<Integer> intensityCombo = createStyledComboBox(new Integer[]{1, 2, 3, 4, 5});
        
        addFormField(panel, "Exercise Type:", typeCombo, 0);
        addFormField(panel, "Duration (minutes):", durationField, 1);
        addFormField(panel, "Intensity (1-5):", intensityCombo, 2);
        
        JButton saveButton = createStyledButton("💾 Save Exercise Record", primaryColor);
        addButtonToPanel(panel, saveButton, 3);
        
        saveButton.addActionListener(e -> saveRecord(() -> {
            String type = (String) typeCombo.getSelectedItem();
            int duration = Integer.parseInt(durationField.getText());
            int intensity = (Integer) intensityCombo.getSelectedItem();
            String recordId = "EXER" + System.currentTimeMillis();
            ExerciseRecord record = new ExerciseRecord(recordId, currentUser.getUserId(), LocalDate.now(), type, duration, intensity);
            dataManager.addHealthRecord(record);
            durationField.setText("");
            return record;
        }));
        
        return panel;
    }
    
    private JPanel createNutritionRecordPanel() {
        JPanel panel = createFormPanel("🍎 Add Nutrition Record");
        
        JComboBox<Integer> mealsCombo = createStyledComboBox(new Integer[]{1, 2, 3, 4, 5});
        JTextField waterField = createStyledTextField();
        JComboBox<Integer> fruitVegCombo = createStyledComboBox(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        JCheckBox breakfastCheck = new JCheckBox("Had Breakfast");
        breakfastCheck.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        breakfastCheck.setBackground(Color.WHITE);
        
        addFormField(panel, "Meals Per Day:", mealsCombo, 0);
        addFormField(panel, "Water Intake (Liters):", waterField, 1);
        addFormField(panel, "Fruit/Veg Servings:", fruitVegCombo, 2);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(breakfastCheck, gbc);
        
        JButton saveButton = createStyledButton("💾 Save Nutrition Record", primaryColor);
        addButtonToPanel(panel, saveButton, 4);
        
        saveButton.addActionListener(e -> saveRecord(() -> {
            int meals = (Integer) mealsCombo.getSelectedItem();
            double water = Double.parseDouble(waterField.getText());
            int fruitVeg = (Integer) fruitVegCombo.getSelectedItem();
            boolean hadBreakfast = breakfastCheck.isSelected();
            String recordId = "NUTR" + System.currentTimeMillis();
            NutritionRecord record = new NutritionRecord(recordId, currentUser.getUserId(), LocalDate.now(), meals, water, fruitVeg, hadBreakfast);
            dataManager.addHealthRecord(record);
            waterField.setText("");
            breakfastCheck.setSelected(false);
            return record;
        }));
        
        return panel;
    }
    
    private JPanel createStressRecordPanel() {
        JPanel panel = createFormPanel("🧠 Add Stress Record");
        
        JComboBox<Integer> stressCombo = createStyledComboBox(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        JComboBox<String> sourceCombo = createStyledComboBox(new String[]{"Academics", "Personal", "Social", "Financial", "Health", "Other"});
        JTextField copingField = createStyledTextField();
        
        addFormField(panel, "Stress Level (1-10):", stressCombo, 0);
        addFormField(panel, "Stress Source:", sourceCombo, 1);
        addFormField(panel, "Coping Strategy:", copingField, 2);
        
        JButton saveButton = createStyledButton("💾 Save Stress Record", primaryColor);
        addButtonToPanel(panel, saveButton, 3);
        
        saveButton.addActionListener(e -> saveRecord(() -> {
            int stress = (Integer) stressCombo.getSelectedItem();
            String source = (String) sourceCombo.getSelectedItem();
            String coping = copingField.getText().trim();
            if (coping.isEmpty()) coping = "Not specified";
            String recordId = "STR" + System.currentTimeMillis();
            StressRecord record = new StressRecord(recordId, currentUser.getUserId(), LocalDate.now(), stress, coping, source);
            dataManager.addHealthRecord(record);
            copingField.setText("");
            return record;
        }));
        
        return panel;
    }
    
    private JPanel createViewRecordsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);
        
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        outputArea.setBackground(new Color(250, 250, 250));
        
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "📋 Health Records",
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12)
        ));
        
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        filterPanel.setBackground(Color.WHITE);
        
        JButton refreshButton = createStyledButton("🔄 Refresh", secondaryColor);
        JButton sleepButton = createStyledButton("😴 Sleep", new Color(52, 73, 94));
        JButton exerciseButton = createStyledButton("🏃 Exercise", new Color(52, 73, 94));
        JButton nutritionButton = createStyledButton("🍎 Nutrition", new Color(52, 73, 94));
        JButton stressButton = createStyledButton("🧠 Stress", new Color(52, 73, 94));
        JButton allButton = createStyledButton("📊 All", primaryColor);
        
        refreshButton.addActionListener(e -> refreshRecords("All"));
        sleepButton.addActionListener(e -> refreshRecords("Sleep"));
        exerciseButton.addActionListener(e -> refreshRecords("Exercise"));
        nutritionButton.addActionListener(e -> refreshRecords("Nutrition"));
        stressButton.addActionListener(e -> refreshRecords("Stress"));
        allButton.addActionListener(e -> refreshRecords("All"));
        
        filterPanel.add(refreshButton);
        filterPanel.add(sleepButton);
        filterPanel.add(exerciseButton);
        filterPanel.add(nutritionButton);
        filterPanel.add(stressButton);
        filterPanel.add(allButton);
        
        panel.add(filterPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createAnalyticsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);
        
        JTextArea analyticsArea = new JTextArea();
        analyticsArea.setEditable(false);
        analyticsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        analyticsArea.setBackground(new Color(250, 250, 250));
        
        JScrollPane scrollPane = new JScrollPane(analyticsArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "📊 Health Analytics Dashboard",
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12)
        ));
        
        JButton refreshButton = createStyledButton("🔄 Generate Analytics", secondaryColor);
        refreshButton.addActionListener(e -> {
            if (currentUser == null) {
                showMessage("Please select a student first!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            StringBuilder sb = new StringBuilder();
            sb.append("═══════════════════════════════════════════════════════════\n");
            sb.append("              📊 HEALTH ANALYTICS DASHBOARD\n");
            sb.append("═══════════════════════════════════════════════════════════\n\n");
            
            sb.append("👤 Student: ").append(currentUser.getFullName()).append("\n");
            sb.append("📅 Generated: ").append(LocalDate.now()).append("\n\n");
            
            double overallScore = dataManager.calculateOverallHealthScore(currentUser.getUserId());
            sb.append("🏆 OVERALL HEALTH SCORE: ");
            if (overallScore >= 85) {
                sb.append(String.format("🌟 %.1f/100 - EXCELLENT\n", overallScore));
            } else if (overallScore >= 70) {
                sb.append(String.format("✅ %.1f/100 - GOOD\n", overallScore));
            } else if (overallScore >= 50) {
                sb.append(String.format("⚠️ %.1f/100 - FAIR\n", overallScore));
            } else {
                sb.append(String.format("🔴 %.1f/100 - NEEDS IMPROVEMENT\n", overallScore));
            }
            
            sb.append("\n📈 CATEGORY PERFORMANCE:\n");
            sb.append("───────────────────────────────────────────────────────────\n");
            
            Map<String, Double> categoryScores = dataManager.getCategoryAverages(currentUser.getUserId());
            for (Map.Entry<String, Double> entry : categoryScores.entrySet()) {
                String icon = getCategoryIcon(entry.getKey());
                sb.append(String.format("%s %-10s: ", icon, entry.getKey()));
                sb.append(String.format("%.1f/100", entry.getValue()));
                
                if (entry.getValue() >= 85) sb.append(" 🌟");
                else if (entry.getValue() >= 70) sb.append(" ✅");
                else if (entry.getValue() >= 50) sb.append(" ⚠️");
                else sb.append(" 🔴");
                sb.append("\n");
            }
            
            Map<String, Integer> recordCounts = dataManager.getRecordCounts(currentUser.getUserId());
            sb.append("\n📊 TRACKING SUMMARY:\n");
            sb.append("───────────────────────────────────────────────────────────\n");
            if (recordCounts.isEmpty()) {
                sb.append("No records found. Start tracking your health today!\n");
            } else {
                int total = 0;
                for (Map.Entry<String, Integer> entry : recordCounts.entrySet()) {
                    sb.append(String.format("   • %s: %d records\n", entry.getKey(), entry.getValue()));
                    total += entry.getValue();
                }
                sb.append(String.format("\n📝 Total Records: %d\n", total));
            }
            
            sb.append("\n═══════════════════════════════════════════════════════════\n");
            analyticsArea.setText(sb.toString());
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(refreshButton);
        
        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createRecommendationsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);
        
        JTextArea recommendationsArea = new JTextArea();
        recommendationsArea.setEditable(false);
        recommendationsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        recommendationsArea.setBackground(new Color(250, 250, 250));
        
        JScrollPane scrollPane = new JScrollPane(recommendationsArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "💡 Personalized Health Recommendations",
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12)
        ));
        
        JButton generateButton = createStyledButton("🎯 Generate Recommendations", primaryColor);
        generateButton.addActionListener(e -> {
            if (currentUser == null) {
                showMessage("Please select a student first!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String recommendations = dataManager.getComprehensiveRecommendation(currentUser.getUserId());
            recommendationsArea.setText(recommendations);
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(generateButton);
        
        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    // Helper Methods
    private JPanel createFormPanel(String title) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            title,
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12)
        ));
        panel.setBackground(Color.WHITE);
        return panel;
    }
    
    private void addFormField(JPanel panel, String label, JComponent field, int row) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.2;
        panel.add(new JLabel(label), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        panel.add(field, gbc);
    }
    
    private void addButtonToPanel(JPanel panel, JButton button, int row) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(button, gbc);
    }
    
    private JTextField createStyledTextField() {
        JTextField field = new JTextField(15);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return field;
    }
    
    private JComboBox createStyledComboBox(Object[] items) {
        JComboBox combo = new JComboBox(items);
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        combo.setBackground(Color.WHITE);
        return combo;
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
    
    private void refreshUserTable() {
        userTableModel.setRowCount(0);
        List<User> users = dataManager.getAllUsers();
        for (User user : users) {
            userTableModel.addRow(new Object[]{
                user.getUserId(),
                user.getFullName(),
                user.getStudentId(),
                user.getEmail(),
                user.getAge(),
                user.getGender()
            });
        }
    }
    
    private void refreshRecords(String filter) {
        if (currentUser == null) {
            outputArea.setText("Please select a student first!");
            return;
        }
        
        List<HealthRecord> records;
        if (filter.equals("All")) {
            records = dataManager.getUserRecords(currentUser.getUserId());
        } else {
            records = dataManager.getUserRecordsByType(currentUser.getUserId(), filter);
        }
        
        if (records.isEmpty()) {
            outputArea.setText("No " + (filter.equals("All") ? "" : filter + " ") + "records found for " + currentUser.getFullName());
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════════════════════════\n");
        sb.append("              📋 HEALTH RECORDS - ").append(currentUser.getFullName().toUpperCase()).append("\n");
        sb.append("═══════════════════════════════════════════════════════════\n\n");
        
        for (HealthRecord record : records) {
            sb.append(record.getSummary()).append("\n");
            sb.append("   📌 Status: ").append(record.getStatus()).append("\n");
            sb.append("   💡 Tip: ").append(record.getRecommendation()).append("\n");
            sb.append("   ").append("─".repeat(50)).append("\n\n");
        }
        
        outputArea.setText(sb.toString());
    }
    
    private String getCategoryIcon(String category) {
        switch (category) {
            case "Sleep": return "😴";
            case "Exercise": return "🏃";
            case "Nutrition": return "🍎";
            case "Stress": return "🧠";
            default: return "📊";
        }
    }
    
    private void saveRecord(RecordCreator creator) {
        if (currentUser == null) {
            showMessage("Please select a student first!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            HealthRecord record = creator.create();
            showMessage("✅ Record saved successfully!\n\n" + record.getSummary(), "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            showMessage("Please enter valid numbers!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            showMessage("Error saving record: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void showMessage(String message, String title, int type) {
        JOptionPane.showMessageDialog(this, message, title, type);
    }
    
    private void enableTabs(boolean enable) {
        for (int i = 1; i < tabbedPane.getTabCount(); i++) {
            tabbedPane.setEnabledAt(i, enable);
        }
    }
    
    private void loadSampleData() {
        // Add sample user for demonstration
        User sampleUser = new User("SAMPLE001", "John Doe", "STU2024001", "john.doe@student.edu", 20, "Male");
        if (dataManager.getAllUsers().isEmpty()) {
            dataManager.addUser(sampleUser);
            
            // Add sample health records
            SleepRecord sleep1 = new SleepRecord("SLEEP001", "SAMPLE001", LocalDate.now().minusDays(1), 7.5, 4);
            SleepRecord sleep2 = new SleepRecord("SLEEP002", "SAMPLE001", LocalDate.now(), 6.5, 3);
            ExerciseRecord exercise1 = new ExerciseRecord("EXER001", "SAMPLE001", LocalDate.now().minusDays(1), "Running", 30, 4);
            NutritionRecord nutrition1 = new NutritionRecord("NUTR001", "SAMPLE001", LocalDate.now(), 3, 2.0, 5, true);
            StressRecord stress1 = new StressRecord("STR001", "SAMPLE001", LocalDate.now(), 4, "Deep breathing", "Academics");
            
            dataManager.addHealthRecord(sleep1);
            dataManager.addHealthRecord(sleep2);
            dataManager.addHealthRecord(exercise1);
            dataManager.addHealthRecord(nutrition1);
            dataManager.addHealthRecord(stress1);
        }
        refreshUserTable();
    }
    
    @FunctionalInterface
    private interface RecordCreator {
        HealthRecord create() throws Exception;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new HealthTrackerGUI().setVisible(true);
        });
    }
}
