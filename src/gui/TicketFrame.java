package gui;

import db.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class TicketFrame extends JFrame {
    public TicketFrame(int bookingId) {
        setTitle("Your Ticket");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(9, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT u.username, f.airline, f.departure, f.destination, f.price, b.seat_number " +
                            "FROM bookings b " +
                            "JOIN users u ON b.user_id = u.user_id " +
                            "JOIN flights f ON b.flight_id = f.flight_id " +
                            "WHERE b.booking_id = ?"
            );
            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                panel.add(new JLabel("Passenger: " + rs.getString("username")));
                panel.add(new JLabel("Airline: " + rs.getString("airline")));
                panel.add(new JLabel("From: " + rs.getString("departure")));
                panel.add(new JLabel("To: " + rs.getString("destination")));
                panel.add(new JLabel("Seat: " + rs.getString("seat_number")));
                panel.add(new JLabel("Fare: ₹" + rs.getDouble("price")));
                panel.add(new JLabel("Booking ID: " + bookingId));
                panel.add(new JLabel("Thank you for booking with us!", SwingConstants.CENTER));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JButton backToLogin = new JButton("Back to Login");
        backToLogin.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        panel.add(backToLogin);
        add(panel);
        setVisible(true);
    }
}
