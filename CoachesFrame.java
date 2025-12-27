package gettzfitness;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CoachesFrame extends JFrame {
    private JTextField firstField, lastField, specialityField, contactField;
    private JTextField searchField;
    private DefaultTableModel tableModel;

    public CoachesFrame() {
        setTitle("Coaches - Gettz Fitness");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        setSize(900,600);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel form = new JPanel(new GridLayout(5,2,5,5));
        form.setBorder(BorderFactory.createTitledBorder("Add Coach"));
        form.add(new JLabel("First Name:")); firstField = new JTextField(); form.add(firstField);
        form.add(new JLabel("Last Name:")); lastField = new JTextField(); form.add(lastField);
        form.add(new JLabel("Speciality:")); specialityField = new JTextField(); form.add(specialityField);
        form.add(new JLabel("Contact:")); contactField = new JTextField(); form.add(contactField);
        JButton addBtn = new JButton("Add Coach"); form.add(addBtn);
        addBtn.addActionListener(e -> addCoach());

        JPanel searchPanel = new JPanel();
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search / Remove by ID"));
        searchPanel.add(new JLabel("ID:")); searchField = new JTextField(10); searchPanel.add(searchField);
        JButton searchBtn = new JButton("Search"); JButton removeBtn = new JButton("Remove");
        searchPanel.add(searchBtn); searchPanel.add(removeBtn);
        searchBtn.addActionListener(e -> doSearch());
        removeBtn.addActionListener(e -> doRemove());

        tableModel = new DefaultTableModel(new Object[]{"ID","First","Last","Speciality","Contact"}, 0);
        JTable table = new JTable(tableModel);
        refreshTable();

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.WEST);
        top.add(searchPanel, BorderLayout.CENTER);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void addCoach() {
        String first = firstField.getText().trim();
        String last = lastField.getText().trim();
        String spec = specialityField.getText().trim();
        String contact = contactField.getText().trim();
        if (first.isEmpty() || last.isEmpty()) {
            JOptionPane.showMessageDialog(this, "First and Last name required.");
            return;
        }
        Coach c = DataStore.addCoach(first, last, spec, contact);
        JOptionPane.showMessageDialog(this, "Added: " + c);
        clearForm();
        refreshTable();
    }

    private void clearForm() {
        firstField.setText(""); lastField.setText(""); specialityField.setText(""); contactField.setText("");
    }

    private void doSearch() {
        String id = searchField.getText().trim();
        if (id.isEmpty()) { JOptionPane.showMessageDialog(this, "Enter ID to search."); return; }
        DataStore.findCoachById(id).ifPresentOrElse(c -> {
            JOptionPane.showMessageDialog(this, "Found: " + c + "\nSpeciality: " + c.getSpeciality() + "\nContact: " + c.getContact());
        }, () -> {
            JOptionPane.showMessageDialog(this, "Coach not found.");
        });
    }

    private void doRemove() {
        String id = searchField.getText().trim();
        if (id.isEmpty()) { JOptionPane.showMessageDialog(this, "Enter ID to remove."); return; }
        boolean removed = DataStore.removeCoachById(id);
        if (removed) { JOptionPane.showMessageDialog(this, "Coach removed."); refreshTable(); }
        else JOptionPane.showMessageDialog(this, "Coach not found.");
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Coach c : DataStore.getAllCoaches()) {
            tableModel.addRow(new Object[]{c.getId(), c.getFirstName(), c.getLastName(), c.getSpeciality(), c.getContact()});
        }
    }
}
