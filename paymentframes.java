package gettzfitnesscenter.frames;

import gettzfitnesscenter.models.Payment;
import gettzfitnesscenter.models.Member;
import gettzfitnesscenter.models.Coach;
import gettzfitnesscenter.utils.ThemeManager;
import gettzfitnesscenter.utils.ChartGenerator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PaymentsFrame extends JFrame {
    private List<Payment> payments;
    private List<Member> members;
    private List<Coach> coaches;
    private DefaultTableModel tableModel;
    private JTable paymentsTable;
    private JComboBox<String> typeFilter;
    private JComboBox<String> monthFilter;

    public PaymentsFrame() {
        payments = new ArrayList<>();
        members = new ArrayList<>();
        coaches = new ArrayList<>();
        initializeSampleData();
        setupFrame();
        createUI();
    }

    private void initializeSampleData() {
        // Sample members
        members.add(new Member("M001", "Janith", "Umayanga", "janith@email.com", "0771234567", 
            175.5, 70.0, "Premium", "Male", 28));
        members.add(new Member("M002", "Kaushalaya", "bandara", "Kaushalaya@email.com", "0777984328", 
            135.0, 55.0, "Standard", "Female", 25));
        members.add(new Member("M003", "Kasun", "danuddara", "Kasun@email.com", "0777587321", 
            165.0, 54.0, "Standard", "Male", 25));
        members.add(new Member("M004", "Arawinda", "de silva", "Arawinda@email.com", "0754587461", 
            165.0, 65.0, "Premium", "Male", 26));
        members.add(new Member("M005", "Shakya", "Sathsarani", "Shakya@email.com", "0754584281", 
            145.0, 45.0, "Standard", "Female", 22));
        members.add(new Member("M006", "Sasanka", "Mihiran", "Sasanka@email.com", "0754587487", 
            160.0, 75.0, "Standard", "Male", 22));
        members.add(new Member("M007", "Lelum", "Karunathilaka", "Lelum@email.com", "0754587461", 
            185.0, 85.0, "Premium", "Male", 23));
        members.add(new Member("M008", "Nipun", "Chamara", "Lelum@email.com", "0754587461", 
            180.0, 70.0, "Premium", "Male", 30));

        // Sample coaches
        coaches.add(new Coach("C001", "Nipun", " sathsara", "0773334444", "Nipun@email.com", 
            "Personal Training", "5 years", 50000, 30, "Male"));
        coaches.add(new Coach("C001", "Arjuna", "Kodithuwakku", "0777463544", "arjuna@email.com", 
            "Personal Training", "6 years", 50000, 35, "Male"));
        coaches.add(new Coach("C001", "Janaki", "Iddamalgoda", "077987548", "janaki@email.com", 
            "Yoga Training", "3 years", 45000, 28, "Female"));
        coaches.add(new Coach("C004", "Lisa", "Anderson", "0779990000", "lisa@email.com", 
            "Yoga & Fitness", "4 years", 48000, 29, "Female"));
        // Sample payments
        payments.add(new Payment("P001", "MEMBER", "M001", "Janith Umayanga", 5000.0, 
            "Premium Membership Fee", "Credit Card"));
        payments.add(new Payment("P002", "COACH", "C001", "Nipun sathsara", 50000.0, 
            "Monthly Salary", "Bank Transfer"));
        payments.add(new Payment("P003", "MEMBER", "M002", "Kaushalaya bandara", 3000.0, 
            "Standard Membership", "Cash"));
        payments.add(new Payment("P004", "MEMBER", "M003", "Kasun danuddara", 3000.0, 
            "Standard Membership", "Cash"));
    }

    private void setupFrame() {
        setTitle("GETIZ GYM - Payments Management");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        getContentPane().setBackground(ThemeManager.getBackgroundColor());
    }

    private void createUI() {
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Main Content
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(ThemeManager.getBackgroundColor());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Control Panel
        JPanel controlPanel = createControlPanel();
        mainPanel.add(controlPanel, BorderLayout.NORTH);

        // Table
        String[] columns = {"Payment ID", "Type", "Reference ID", "Name", "Amount", 
                           "Date", "Description", "Payment Method", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        paymentsTable = new JTable(tableModel);
        paymentsTable.setFont(new Font("Arial", Font.PLAIN, 12));
        paymentsTable.setRowHeight(30);
        paymentsTable.setAutoCreateRowSorter(true);
        
        JScrollPane scrollPane = new JScrollPane(paymentsTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Summary Panel
        JPanel summaryPanel = createSummaryPanel();
        mainPanel.add(summaryPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
        refreshTable();
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(44, 62, 80));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel headerLabel = new JLabel("PAYMENTS MANAGEMENT SYSTEM", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setForeground(Color.WHITE);

        JButton backButton = new JButton("Back to Dashboard");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(52, 152, 219));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> dispose());

        panel.add(headerLabel, BorderLayout.CENTER);
        panel.add(backButton, BorderLayout.EAST);

        return panel;
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panel.setBackground(new Color(52, 73, 94));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton addPaymentButton = new JButton("➕ Add Payment");
        addPaymentButton.setFont(new Font("Arial", Font.BOLD, 14));
        addPaymentButton.setBackground(new Color(46, 204, 113));
        addPaymentButton.setForeground(Color.WHITE);
        addPaymentButton.setFocusPainted(false);
        addPaymentButton.addActionListener(new AddPaymentListener());
        panel.add(addPaymentButton);

        JLabel typeLabel = new JLabel("Payment Type:");
        typeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        typeLabel.setForeground(Color.WHITE);
        panel.add(typeLabel);

        typeFilter = new JComboBox<>(new String[]{"All", "MEMBER", "COACH"});
        typeFilter.setFont(new Font("Arial", Font.PLAIN, 14));
        typeFilter.addActionListener(e -> filterPayments());
        panel.add(typeFilter);

        JLabel monthLabel = new JLabel("Month:");
        monthLabel.setFont(new Font("Arial", Font.BOLD, 14));
        monthLabel.setForeground(Color.WHITE);
        panel.add(monthLabel);

        monthFilter = new JComboBox<>(new String[]{"All", "January", "February", "March", 
            "April", "May", "June", "July", "August", "September", "October", "November", "December"});
        monthFilter.setFont(new Font("Arial", Font.PLAIN, 14));
        monthFilter.addActionListener(e -> filterPayments());
        panel.add(monthFilter);

        JButton refreshButton = new JButton("🔄 Refresh");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 14));
        refreshButton.setBackground(new Color(241, 196, 15));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(e -> refreshTable());
        panel.add(refreshButton);

        JButton reportsButton = new JButton("📊 Generate Report");
        reportsButton.setFont(new Font("Arial", Font.BOLD, 14));
        reportsButton.setBackground(new Color(155, 89, 182));
        reportsButton.setForeground(Color.WHITE);
        reportsButton.setFocusPainted(false);
        reportsButton.addActionListener(e -> generateReport());
        panel.add(reportsButton);

        return panel;
    }

    private JPanel createSummaryPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 15, 0));
        panel.setBackground(new Color(52, 73, 94));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Calculate summary statistics
        double totalRevenue = payments.stream()
            .filter(p -> "MEMBER".equals(p.getType()))
            .mapToDouble(Payment::getAmount)
            .sum();

        double totalExpenses = payments.stream()
            .filter(p -> "COACH".equals(p.getType()))
            .mapToDouble(Payment::getAmount)
            .sum();

        double netProfit = totalRevenue - totalExpenses;
        long totalTransactions = payments.size();

        panel.add(createSummaryCard("Total Revenue", String.format("Rs.%.2f", totalRevenue), 
            new Color(46, 204, 113), "💰"));
        panel.add(createSummaryCard("Total Expenses", String.format("Rs.%.2f", totalExpenses), 
            new Color(231, 76, 60), "💸"));
        panel.add(createSummaryCard("Net Profit", String.format("Rs.%.2f", netProfit), 
            new Color(52, 152, 219), "📈"));
        panel.add(createSummaryCard("Total Transactions", String.valueOf(totalTransactions), 
            new Color(155, 89, 182), "📋"));

        return panel;
    }

    private JPanel createSummaryCard(String title, String value, Color color, String emoji) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(color);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel emojiLabel = new JLabel(emoji, SwingConstants.CENTER);
        emojiLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(Color.WHITE);

        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 18));
        valueLabel.setForeground(Color.WHITE);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(color);
        contentPanel.add(emojiLabel, BorderLayout.NORTH);
        contentPanel.add(titleLabel, BorderLayout.CENTER);
        contentPanel.add(valueLabel, BorderLayout.SOUTH);

        card.add(contentPanel, BorderLayout.CENTER);
        return card;
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        for (Payment payment : payments) {
            tableModel.addRow(new Object[]{
                payment.getPaymentId(),
                payment.getType(),
                payment.getReferenceId(),
                payment.getReferenceName(),
                String.format("Rs.%.2f", payment.getAmount()),
                payment.getDate().format(formatter),
                payment.getDescription(),
                payment.getPaymentMethod(),
                payment.getStatus()
            });
        }
        updateSummaryPanel();
    }

    private void filterPayments() {
        String typeFilterText = (String) typeFilter.getSelectedItem();
        String monthFilterText = (String) monthFilter.getSelectedItem();
        
        tableModel.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        for (Payment payment : payments) {
            boolean matchesType = typeFilterText.equals("All") ||
                payment.getType().equals(typeFilterText);
            
            boolean matchesMonth = monthFilterText.equals("All") ||
                payment.getDate().getMonth().toString().equals(monthFilterText.toUpperCase());
            
            if (matchesType && matchesMonth) {
                tableModel.addRow(new Object[]{
                    payment.getPaymentId(),
                    payment.getType(),
                    payment.getReferenceId(),
                    payment.getReferenceName(),
                    String.format("Rs.%.2f", payment.getAmount()),
                    payment.getDate().format(formatter),
                    payment.getDescription(),
                    payment.getPaymentMethod(),
                    payment.getStatus()
                });
            }
        }
    }

    private void updateSummaryPanel() {
        // This would update the summary panel with fresh data
        // Implementation would recalculate and update the summary cards
    }

    private class AddPaymentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            showAddPaymentDialog();
        }
    }

    private void showAddPaymentDialog() {
        JDialog dialog = new JDialog(this, "Add New Payment", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(ThemeManager.getCardColor());

        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"MEMBER", "COACH"});
        JComboBox<String> referenceCombo = new JComboBox<>();
        JTextField amountField = new JTextField();
        JTextField descriptionField = new JTextField();
        JComboBox<String> methodCombo = new JComboBox<>(new String[]{"Cash", "Credit Card", 
            "Debit Card", "Bank Transfer", "Online Payment"});

        // Update reference combo based on type selection
        typeCombo.addActionListener(e -> updateReferenceCombo(typeCombo, referenceCombo));

        addFormField(formPanel, "Payment Type:", typeCombo);
        addFormField(formPanel, "Reference:", referenceCombo);
        addFormField(formPanel, "Amount:", amountField);
        addFormField(formPanel, "Description:", descriptionField);
        addFormField(formPanel, "Payment Method:", methodCombo);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(ThemeManager.getCardColor());

        JButton saveButton = new JButton("Save Payment");
        saveButton.setBackground(new Color(46, 204, 113));
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(e -> {
            try {
                String paymentId = "P" + String.format("%03d", payments.size() + 1);
                String type = (String) typeCombo.getSelectedItem();
                String reference = (String) referenceCombo.getSelectedItem();
                String referenceId = reference.split(" - ")[0];
                String referenceName = reference.substring(reference.indexOf("-") + 2);
                
                Payment newPayment = new Payment(
                    paymentId,
                    type,
                    referenceId,
                    referenceName,
                    Double.parseDouble(amountField.getText()),
                    descriptionField.getText(),
                    (String) methodCombo.getSelectedItem()
                );
                payments.add(newPayment);
                refreshTable();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, 
                    "Payment recorded successfully!\nPayment ID: " + paymentId, 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Please enter a valid amount.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Please fill in all fields correctly.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(231, 76, 60));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        // Initialize reference combo
        updateReferenceCombo(typeCombo, referenceCombo);
        dialog.setVisible(true);
    }

    private void updateReferenceCombo(JComboBox<String> typeCombo, JComboBox<String> referenceCombo) {
        referenceCombo.removeAllItems();
        String type = (String) typeCombo.getSelectedItem();
        
        if ("MEMBER".equals(type)) {
            for (Member member : members) {
                referenceCombo.addItem(member.getMemberId() + " - " + 
                    member.getFirstName() + " " + member.getLastName());
            }
        } else if ("COACH".equals(type)) {
            for (Coach coach : coaches) {
                referenceCombo.addItem(coach.getCoachId() + " - " + 
                    coach.getFirstName() + " " + coach.getLastName());
            }
        }
    }

    private void generateReport() {
        JDialog reportDialog = new JDialog(this, "Financial Report", true);
        reportDialog.setSize(800, 600);
        reportDialog.setLocationRelativeTo(this);
        reportDialog.setLayout(new BorderLayout());

        JPanel reportPanel = new JPanel(new BorderLayout());
        reportPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        reportPanel.setBackground(ThemeManager.getBackgroundColor());

        // Create charts
        JPanel chartsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        chartsPanel.setBackground(ThemeManager.getBackgroundColor());

        JPanel revenueChart = ChartGenerator.createRevenueChart(payments);
        JPanel membershipChart = ChartGenerator.createMembershipChart(members);

        chartsPanel.add(revenueChart);
        chartsPanel.add(membershipChart);

        // Report summary
        JTextArea summaryArea = new JTextArea();
        summaryArea.setFont(new Font("Arial", Font.PLAIN, 12));
        summaryArea.setEditable(false);
        summaryArea.setText(generateReportSummary());

        JScrollPane summaryScroll = new JScrollPane(summaryArea);

        reportPanel.add(chartsPanel, BorderLayout.CENTER);
        reportPanel.add(summaryScroll, BorderLayout.SOUTH);

        reportDialog.add(reportPanel, BorderLayout.CENTER);
        reportDialog.setVisible(true);
    }

    private String generateReportSummary() {
        double totalRevenue = payments.stream()
            .filter(p -> "MEMBER".equals(p.getType()))
            .mapToDouble(Payment::getAmount)
            .sum();

        double totalExpenses = payments.stream()
            .filter(p -> "COACH".equals(p.getType()))
            .mapToDouble(Payment::getAmount)
            .sum();

        double netProfit = totalRevenue - totalExpenses;

        return String.format(
            "FINANCIAL REPORT SUMMARY\n" +
            "========================\n\n" +
            "Total Revenue: Rs.%.2f\n" +
            "Total Expenses: Rs.%.2f\n" +
            "Net Profit: Rs.%.2f\n\n" +
            "Member Payments: %d\n" +
            "Coach Payments: %d\n" +
            "Total Transactions: %d\n\n" +
            "Report Generated: %s",
            totalRevenue, totalExpenses, netProfit,
            payments.stream().filter(p -> "MEMBER".equals(p.getType())).count(),
            payments.stream().filter(p -> "COACH".equals(p.getType())).count(),
            payments.size(),
            LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );
    }

    private void addFormField(JPanel panel, String label, JComponent field) {
        JLabel jLabel = new JLabel(label);
        jLabel.setForeground(ThemeManager.getForegroundColor());
        jLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(jLabel);
        panel.add(field);
    }
}