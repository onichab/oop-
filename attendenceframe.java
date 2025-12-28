package gettzfitnesscenter.frames;

import gettzfitnesscenter.models.Attendance;
import gettzfitnesscenter.models.Member;
import gettzfitnesscenter.utils.ThemeManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AttendanceFrame extends JFrame {
    private List<Attendance> attendanceRecords;
    private List<Member> members;
    private DefaultTableModel tableModel;
    private JTable attendanceTable;
    private JComboBox<String> memberCombo;
    private JComboBox<String> workoutTypeCombo;
    private JComboBox<String> dateFilter;

    public AttendanceFrame() {
        attendanceRecords = new ArrayList<>();
        members = new ArrayList<>();
        initializeSampleData();
        setupFrame();
        createUI();
    }

    private void initializeSampleData() {
        // Sample members
        members.add(new Member("M005", "Shakya", "Sathsarani", "Shakya@email.com", "0754584281", 
            145.0, 45.0, "Standard", "Female", 22));
        members.add(new Member("M006", "Sasanka", "Mihiran", "Sasanka@email.com", "0754587487", 
            160.0, 75.0, "Standard", "Male", 22));
        members.add(new Member("M007", "Lelum", "Karunathilaka", "Lelum@email.com", "0754587461", 
            185.0, 85.0, "Premium", "Male", 23));
        members.add(new Member("M008", "Nipun", "Chamara", "Lelum@email.com", "0754587461", 
            180.0, 70.0, "Premium", "Male", 30));

        // Sample attendance records
        Attendance att1 = new Attendance("ATT001", "M012", "Sanath weerakon", "Strength Training");
        att1.checkOut();
        attendanceRecords.add(att1);

        Attendance att2 = new Attendance("ATT002", "M024", "Ajith withanage", "Cardio");
        // att2 is still checked in
        attendanceRecords.add(att2);
    }

    private void setupFrame() {
        setTitle("GETIZ GYM - Attendance Tracking");
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
        String[] columns = {"Attendance ID", "Member ID", "Member Name", "Workout Type", 
                           "Check-in Time", "Check-out Time", "Duration", "Date", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        attendanceTable = new JTable(tableModel);
        attendanceTable.setFont(new Font("Arial", Font.PLAIN, 12));
        attendanceTable.setRowHeight(30);
        attendanceTable.setAutoCreateRowSorter(true);
        
        JScrollPane scrollPane = new JScrollPane(attendanceTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Statistics Panel
        JPanel statsPanel = createStatisticsPanel();
        mainPanel.add(statsPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
        refreshTable();
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(44, 62, 80));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel headerLabel = new JLabel("ATTENDANCE TRACKING SYSTEM", SwingConstants.CENTER);
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

        JLabel memberLabel = new JLabel("Member:");
        memberLabel.setFont(new Font("Arial", Font.BOLD, 14));
        memberLabel.setForeground(Color.WHITE);
        panel.add(memberLabel);

        memberCombo = new JComboBox<>();
        memberCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        updateMemberCombo();
        panel.add(memberCombo);

        JLabel workoutLabel = new JLabel("Workout Type:");
        workoutLabel.setFont(new Font("Arial", Font.BOLD, 14));
        workoutLabel.setForeground(Color.WHITE);
        panel.add(workoutLabel);

        workoutTypeCombo = new JComboBox<>(new String[]{
            "Strength Training", "Cardio", "Yoga", "CrossFit", "Personal Training", "Group Class"
        });
        workoutTypeCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(workoutTypeCombo);

        JButton checkInButton = new JButton("✅ Check In");
        checkInButton.setFont(new Font("Arial", Font.BOLD, 14));
        checkInButton.setBackground(new Color(46, 204, 113));
        checkInButton.setForeground(Color.WHITE);
        checkInButton.setFocusPainted(false);
        checkInButton.addActionListener(e -> checkInMember());
        panel.add(checkInButton);

        JButton checkOutButton = new JButton("⏹️ Check Out");
        checkOutButton.setFont(new Font("Arial", Font.BOLD, 14));
        checkOutButton.setBackground(new Color(241, 196, 15));
        checkOutButton.setForeground(Color.WHITE);
        checkOutButton.setFocusPainted(false);
        checkOutButton.addActionListener(e -> checkOutMember());
        panel.add(checkOutButton);

        JLabel dateLabel = new JLabel("Date Filter:");
        dateLabel.setFont(new Font("Arial", Font.BOLD, 14));
        dateLabel.setForeground(Color.WHITE);
        panel.add(dateLabel);

        dateFilter = new JComboBox<>(new String[]{"All", "Today", "This Week", "This Month"});
        dateFilter.setFont(new Font("Arial", Font.PLAIN, 14));
        dateFilter.addActionListener(e -> filterAttendance());
        panel.add(dateFilter);

        JButton refreshButton = new JButton("🔄 Refresh");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 14));
        refreshButton.setBackground(new Color(52, 152, 219));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(e -> refreshTable());
        panel.add(refreshButton);

        return panel;
    }

    private JPanel createStatisticsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 15, 0));
        panel.setBackground(new Color(52, 73, 94));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Calculate statistics
        long totalCheckIns = attendanceRecords.size();
        long activeNow = attendanceRecords.stream().filter(Attendance::isCheckedIn).count();
        long completedToday = attendanceRecords.stream()
            .filter(att -> !att.isCheckedIn() && att.getDate().equals(java.time.LocalDate.now().toString()))
            .count();
        String mostActive = getMostActiveMember();

        panel.add(createStatCard("Total Check-ins", String.valueOf(totalCheckIns), 
            new Color(52, 152, 219), "📊"));
        panel.add(createStatCard("Currently Active", String.valueOf(activeNow), 
            new Color(46, 204, 113), "🏃"));
        panel.add(createStatCard("Completed Today", String.valueOf(completedToday), 
            new Color(241, 196, 15), "✅"));
        panel.add(createStatCard("Most Active", mostActive, 
            new Color(155, 89, 182), "⭐"));

        return panel;
    }

    private JPanel createStatCard(String title, String value, Color color, String emoji) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(color);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel emojiLabel = new JLabel(emoji, SwingConstants.CENTER);
        emojiLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        titleLabel.setForeground(Color.WHITE);

        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 16));
        valueLabel.setForeground(Color.WHITE);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(color);
        contentPanel.add(emojiLabel, BorderLayout.NORTH);
        contentPanel.add(titleLabel, BorderLayout.CENTER);
        contentPanel.add(valueLabel, BorderLayout.SOUTH);

        card.add(contentPanel, BorderLayout.CENTER);
        return card;
    }

    private void updateMemberCombo() {
        memberCombo.removeAllItems();
        for (Member member : members) {
            memberCombo.addItem(member.getMemberId() + " - " + member.getFirstName() + " " + member.getLastName());
        }
    }

    private void checkInMember() {
        String selected = (String) memberCombo.getSelectedItem();
        if (selected == null || selected.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a member.");
            return;
        }

        String memberId = selected.split(" - ")[0];
        Member member = findMemberById(memberId);
        
        if (member == null) {
            JOptionPane.showMessageDialog(this, "Member not found.");
            return;
        }

        // Check if member is already checked in
        for (Attendance att : attendanceRecords) {
            if (att.getMemberId().equals(memberId) && att.isCheckedIn()) {
                JOptionPane.showMessageDialog(this, 
                    member.getFirstName() + " " + member.getLastName() + " is already checked in!",
                    "Already Checked In", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        String workoutType = (String) workoutTypeCombo.getSelectedItem();
        String attendanceId = "ATT" + String.format("%03d", attendanceRecords.size() + 1);
        Attendance attendance = new Attendance(attendanceId, memberId, 
            member.getFirstName() + " " + member.getLastName(), workoutType);
        attendanceRecords.add(attendance);
        
        refreshTable();
        
        JOptionPane.showMessageDialog(this, 
            "✅ " + member.getFirstName() + " " + member.getLastName() + 
            " checked in successfully!\n" +
            "Time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) +
            "\nWorkout: " + workoutType,
            "Check-in Successful", JOptionPane.INFORMATION_MESSAGE);
    }

    private void checkOutMember() {
        String selected = (String) memberCombo.getSelectedItem();
        if (selected == null || selected.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a member.");
            return;
        }

        String memberId = selected.split(" - ")[0];
        Member member = findMemberById(memberId);
        
        if (member == null) {
            JOptionPane.showMessageDialog(this, "Member not found.");
            return;
        }

        // Find active attendance record
        for (Attendance att : attendanceRecords) {
            if (att.getMemberId().equals(memberId) && att.isCheckedIn()) {
                att.checkOut();
                refreshTable();
                
                JOptionPane.showMessageDialog(this, 
                    "⏹️ " + member.getFirstName() + " " + member.getLastName() + 
                    " checked out successfully!\n" +
                    "Duration: " + att.getDuration(),
                    "Check-out Successful", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        
        JOptionPane.showMessageDialog(this, 
            "No active check-in found for " + member.getFirstName() + " " + member.getLastName(),
            "No Active Check-in", JOptionPane.WARNING_MESSAGE);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        
        for (Attendance att : attendanceRecords) {
            String status = att.isCheckedIn() ? "🟢 Active" : "🔴 Completed";
            tableModel.addRow(new Object[]{
                att.getAttendanceId(),
                att.getMemberId(),
                att.getMemberName(),
                att.getWorkoutType(),
                att.getCheckInTime().format(timeFormatter),
                att.getFormattedCheckOutTime(),
                att.getDuration() != null ? att.getDuration() : "N/A",
                att.getDate(),
                status
            });
        }
    }

    private void filterAttendance() {
        String filter = (String) dateFilter.getSelectedItem();
        tableModel.setRowCount(0);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        
        for (Attendance att : attendanceRecords) {
            boolean include = true;
            
            if (!"All".equals(filter)) {
                java.time.LocalDate recordDate = java.time.LocalDate.parse(att.getDate());
                java.time.LocalDate today = java.time.LocalDate.now();
                
                switch (filter) {
                    case "Today":
                        include = recordDate.equals(today);
                        break;
                    case "This Week":
                        java.time.LocalDate startOfWeek = today.with(java.time.DayOfWeek.MONDAY);
                        include = !recordDate.isBefore(startOfWeek) && !recordDate.isAfter(today);
                        break;
                    case "This Month":
                        java.time.LocalDate startOfMonth = today.withDayOfMonth(1);
                        include = !recordDate.isBefore(startOfMonth) && !recordDate.isAfter(today);
                        break;
                }
            }
            
            if (include) {
                String status = att.isCheckedIn() ? "🟢 Active" : "🔴 Completed";
                tableModel.addRow(new Object[]{
                    att.getAttendanceId(),
                    att.getMemberId(),
                    att.getMemberName(),
                    att.getWorkoutType(),
                    att.getCheckInTime().format(timeFormatter),
                    att.getFormattedCheckOutTime(),
                    att.getDuration() != null ? att.getDuration() : "N/A",
                    att.getDate(),
                    status
                });
            }
        }
    }

    private String getMostActiveMember() {
        if (attendanceRecords.isEmpty()) return "N/A";
        
        // Count attendance by member
        java.util.Map<String, Long> memberCount = new java.util.HashMap<>();
        for (Attendance att : attendanceRecords) {
            if (!att.isCheckedIn()) { // Only count completed sessions
                memberCount.merge(att.getMemberName(), 1L, Long::sum);
            }
        }
        
        return memberCount.entrySet().stream()
            .max(java.util.Map.Entry.comparingByValue())
            .map(java.util.Map.Entry::getKey)
            .orElse("N/A");
    }

    private Member findMemberById(String memberId) {
        for (Member member : members) {
            if (member.getMemberId().equals(memberId)) {
                return member;
            }
        }
        return null;
    }
}