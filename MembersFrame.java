package gettzfitness;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MembersFrame extends JFrame {
    private JTextField firstField, lastField, ageField, genderField, contactField;
    private JTextField searchField;
    private DefaultTableModel tableModel;

    public MembersFrame() {
        setTitle("Members - Gettz Fitness");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        setSize(900,600);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel form = new JPanel(new GridLayout(6,2,5,5));
        form.setBorder(BorderFactory.createTitledBorder("Add Member"));
        form.add(new JLabel("First Name:")); firstField = new JTextField(); form.add(firstField);
        form.add(new JLabel("Last Name:")); lastField = new JTextField(); form.add(lastField);
        form.add(new JLabel("Age:")); ageField = new JTextField(); form.add(ageField);
        form.add(new JLabel("Gender:")); genderField = new JTextField(); form.add(genderField);
        form.add(new JLabel("Contact:")); contactField = new JTextField(); form.add(contactField);
        JButton addBtn = new JButton("Add Member"); form.add(addBtn);
        addBtn.addActionListener(e -> addMember());

        JPanel searchPanel = new JPanel();
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search / Remove by ID"));
        searchPanel.add(new JLabel("ID:")); searchField = new JTextField(10); searchPanel.add(searchField);
        JButton searchBtn = new JButton("Search"); JButton removeBtn = new JButton("Remove");
        searchPanel.add(searchBtn); searchPanel.add(removeBtn);
        searchBtn.addActionListener(e -> doSearch());
        removeBtn.addActionListener(e -> doRemove());

        tableModel = new DefaultTableModel(new Object[]{"ID","First","Last","Age","Gender","Contact"}, 0);
        JTable table = new JTable(tableModel);
        refreshTable();

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.WEST);
        top.add(searchPanel, BorderLayout.CENTER);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void addMember() {
        try {
            String first = firstField.getText().trim();
            String last = lastField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            String gender = genderField.getText().trim();
            String contact = contactField.getText().trim();
            Member m = DataStore.addMember(first, last, age, gender, contact);
            JOptionPane.showMessageDialog(this, "Added: " + m);
            clearForm();
            refreshTable();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid data. Age must be a number.");
        }
    }

    private void clearForm() {
        firstField.setText(""); lastField.setText(""); ageField.setText(""); genderField.setText(""); contactField.setText("");
    }

    private void doSearch() {
        String id = searchField.getText().trim();
        if (id.isEmpty()) { JOptionPane.showMessageDialog(this, "Enter ID to search."); return; }
        DataStore.findMemberById(id).ifPresentOrElse(m -> {
            JOptionPane.showMessageDialog(this, "Found: " + m + "\nAge: " + m.getAge() + "\nContact: " + m.getContact());
        }, () -> {
            JOptionPane.showMessageDialog(this, "Member not found.");
        });
    }

    private void doRemove() {
        String id = searchField.getText().trim();
        if (id.isEmpty()) { JOptionPane.showMessageDialog(this, "Enter ID to remove."); return; }
        boolean removed = DataStore.removeMemberById(id);
        if (removed) { JOptionPane.showMessageDialog(this, "Member removed."); refreshTable(); }
        else JOptionPane.showMessageDialog(this, "Member not found.");
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Member m : DataStore.getAllMembers()) {
            tableModel.addRow(new Object[]{m.getId(), m.getFirstName(), m.getLastName(), m.getAge(), m.getGender(), m.getContact()});
        }
    }
}
