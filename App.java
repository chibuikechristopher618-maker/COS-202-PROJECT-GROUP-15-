import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class App extends JFrame {
    private StudentStore store;
    private JTable table;
    private DefaultTableModel model;

    public App(StudentStore store) {
        this.store = store;
        setTitle("Smart Student Platform");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Table model
        model = new DefaultTableModel(new String[]{"ID", "Name", "CGPA"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // Input fields
        JTextField idField = new JTextField(5);
        JTextField nameField = new JTextField(10);
        JTextField cgpaField = new JTextField(5);

        // Buttons
        JButton addButton = new JButton("Add Student");
        JButton searchLinearButton = new JButton("Search Linear");
        JButton searchBinaryButton = new JButton("Search Binary");
        JButton sortNameButton = new JButton("Sort by Name");
        JButton sortCgpaButton = new JButton("Sort by CGPA");
        JButton saveButton = new JButton("Save to File");
        JButton loadButton = new JButton("Load from File");
        JButton summaryButton = new JButton("Show Summary"); // New button

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("ID:")); inputPanel.add(idField);
        inputPanel.add(new JLabel("Name:")); inputPanel.add(nameField);
        inputPanel.add(new JLabel("CGPA:")); inputPanel.add(cgpaField);
        inputPanel.add(addButton);
        inputPanel.add(searchLinearButton);
        inputPanel.add(searchBinaryButton);
        inputPanel.add(sortNameButton);
        inputPanel.add(sortCgpaButton);
        inputPanel.add(saveButton);
        inputPanel.add(loadButton);
        inputPanel.add(summaryButton); // add to panel

        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        // ======================
        // Button Actions
        // ======================

        // Add student
        addButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                String name = nameField.getText().trim();
                double cgpa = Double.parseDouble(cgpaField.getText().trim());

                store.addStudent(new Student(id, name, cgpa));
                updateTable();

                idField.setText(""); nameField.setText(""); cgpaField.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID or CGPA!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Search linear
        searchLinearButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                Student s = store.findById(id);
                if (s != null) JOptionPane.showMessageDialog(this, "Found: " + s);
                else JOptionPane.showMessageDialog(this, "Student not found!", "Warning", JOptionPane.WARNING_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Search binary
        searchBinaryButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                Student s = store.binarySearchById(id);
                if (s != null) JOptionPane.showMessageDialog(this, "Found: " + s);
                else JOptionPane.showMessageDialog(this, "Student not found!", "Warning", JOptionPane.WARNING_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Sort by Name
        sortNameButton.addActionListener(e -> {
            store.quickSortByName();
            updateTable();
        });

        // Sort by CGPA
        sortCgpaButton.addActionListener(e -> {
            store.bubbleSortByCGPA();
            updateTable();
        });

        // Save to file
        saveButton.addActionListener(e -> {
            store.saveToFile("students.dat");
            JOptionPane.showMessageDialog(this, "Students saved successfully!");
        });

        // Load from file
        loadButton.addActionListener(e -> {
            store.loadFromFile("students.dat");
            updateTable();
            JOptionPane.showMessageDialog(this, "Students loaded successfully!");
        });

        // Show summary
        summaryButton.addActionListener(e -> {
            if (store.getStudentCount() == 0) {
                JOptionPane.showMessageDialog(this, "No students in the system.", "Summary", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            Student top = store.getTopPerformer();
            Student low = store.getLowestPerformer();
            double average = store.averageCgpa();
            int total = store.getStudentCount();

            String message = String.format(
                    "Class Summary:\n" +
                    "Total Students: %d\n" +
                    "Class Average CGPA: %.2f\n" +
                    "Top Performer: %s (%.2f)\n" +
                    "Lowest Performer: %s (%.2f)",
                    total, average,
                    top.getName(), top.getCgpa(),
                    low.getName(), low.getCgpa()
            );

            JOptionPane.showMessageDialog(this, message, "Class Summary", JOptionPane.INFORMATION_MESSAGE);
        });

        // Initial table update
        updateTable();
    }

    // Update JTable
    private void updateTable() {
        model.setRowCount(0);
        for (Student s : store.getAllStudents()) {
            model.addRow(new Object[]{s.getId(), s.getName(), s.getCgpa()});
        }
    }

    // ======================
    // Main
    // ======================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentStore store = new StudentStore();
            // Optional: pre-populate some students
            store.addStudent(new Student(101, "Ella Cynthia", 4.50));
            store.addStudent(new Student(102, "Ikenna Divine", 4.80));
            store.addStudent(new Student(103, "Chris Chibuike", 4.30));

            new App(store).setVisible(true);
        });
    }
}
