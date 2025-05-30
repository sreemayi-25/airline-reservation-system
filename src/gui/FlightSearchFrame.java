package gui;

import db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FlightSearchFrame extends JFrame {
    int userId;

    public FlightSearchFrame(int userId) {
        this.userId = userId;
        setTitle("Search Flights");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTextField fromField = new JTextField();
        JTextField toField = new JTextField();

        SpinnerDateModel dateModel = new SpinnerDateModel();
        JSpinner dateSpinner = new JSpinner(dateModel);
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));

        JButton searchBtn = new JButton("Search Flights");

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("From:"));
        inputPanel.add(fromField);

        inputPanel.add(new JLabel("To:"));
        inputPanel.add(toField);

        inputPanel.add(new JLabel("Departure Date:"));
        inputPanel.add(dateSpinner);

        inputPanel.add(new JLabel(""));
        inputPanel.add(searchBtn);

        // Table to show flights
        String[] columns = {"Flight ID", "Airline", "From", "To", "Departure Time", "Price"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton selectBtn = new JButton("Select Flight");
        selectBtn.setEnabled(false);

        searchBtn.addActionListener(e -> {
            model.setRowCount(0); // Clear previous results
            try (Connection conn = DBConnection.getConnection()) {
                String sql = "SELECT * FROM flights WHERE departure = ? AND destination = ? AND DATE(departure_time) = ?";
                PreparedStatement ps = conn.prepareStatement(sql);

                String from = fromField.getText();
                String to = toField.getText();
                Date selectedDate = (Date) dateSpinner.getValue();
                String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(selectedDate);

                ps.setString(1, from);
                ps.setString(2, to);
                ps.setString(3, dateStr);

                ResultSet rs = ps.executeQuery();

                boolean found = false;
                while (rs.next()) {
                    found = true;
                    int flightId = rs.getInt("flight_id");
                    String airline = rs.getString("airline");
                    String departure = rs.getString("departure");
                    String destination = rs.getString("destination");
                    Timestamp time = rs.getTimestamp("departure_time");
                    double price = rs.getDouble("price");

                    model.addRow(new Object[]{flightId, airline, departure, destination, time, price});
                }

                if (!found) {
                    JOptionPane.showMessageDialog(this, "No flights found.");
                } else {
                    selectBtn.setEnabled(true);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        selectBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int flightId = (int) model.getValueAt(selectedRow, 0);
                String airline = model.getValueAt(selectedRow, 1).toString();
                String departure = model.getValueAt(selectedRow, 2).toString();
                String destination = model.getValueAt(selectedRow, 3).toString();
                double price = Double.parseDouble(model.getValueAt(selectedRow, 5).toString());

                dispose();
                new FareFrame(userId, flightId, airline, departure, destination, price);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a flight.");
            }
        });

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Available Flights"));
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.add(selectBtn, BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);

        setVisible(true);
    }
}
