package gui;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.*;

public class BookingFrame extends JFrame {
    public BookingFrame(int userId, int flightId) {
        setTitle("Seat Booking");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Generate all seat numbers (1A to 16F)
        ArrayList<Object> allSeats = new ArrayList<>();
        String[] seatLetters = {"A", "B", "C", "D", "E", "F"};
        for (int row = 1; row <= 16; row++) {
            for (String letter : seatLetters) {
                allSeats.add(row + letter);
            }
        }

        // Fetch booked seats for the selected flight
        Set<String> bookedSeats = new HashSet<>();
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT seat_number FROM bookings WHERE flight_id = ?");
            ps.setInt(1, flightId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                bookedSeats.add(rs.getString("seat_number"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Filter available seats
        ArrayList<Object> availableSeats = new ArrayList<>();
        for (Object seat : allSeats) {
            if (!bookedSeats.contains(seat)) {
                availableSeats.add(seat);
            }
        }

        JComboBox<String> seatDropdown = new JComboBox<>(availableSeats.toArray(new String[0]));
        JButton bookBtn = new JButton("Book Selected Seat");

        panel.add(new JLabel("Select Available Seat:"));
        panel.add(seatDropdown);
        panel.add(new JLabel());  // empty cell
        panel.add(bookBtn);

        // Button Action
        bookBtn.addActionListener(e -> {
            String selectedSeat = (String) seatDropdown.getSelectedItem();
            if (selectedSeat == null) {
                JOptionPane.showMessageDialog(this, "No seat selected!");
                return;
            }

            try (Connection conn = DBConnection.getConnection()) {
                // Fetch flight details
                PreparedStatement p2 = conn.prepareStatement("SELECT price, airline, departure, destination FROM flights WHERE flight_id=?");
                p2.setInt(1, flightId);
                ResultSet r2 = p2.executeQuery();
                if (r2.next()) {
                    double price = r2.getDouble("price");
                    String airline = r2.getString("airline");
                    String from = r2.getString("departure");
                    String to = r2.getString("destination");

                    // Redirect to PaymentFrame with seatNumber
                    dispose();
                    new PaymentFrame(userId, flightId, selectedSeat, airline, from, to, price);
                } else {
                    JOptionPane.showMessageDialog(this, "Flight details not found.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error fetching flight details.");
            }
        });


        add(panel);
        setVisible(true);
    }
}
