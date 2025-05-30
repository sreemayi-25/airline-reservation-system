package gui;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class PaymentFrame extends JFrame {
    public PaymentFrame(int userId, int flightId, String seatNumber, String airline, String from, String to, double price)
    {
        setTitle("Payment");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField cardNumberField = new JTextField();
        JTextField expiryField = new JTextField();
        JPasswordField cvvField = new JPasswordField();
        JTextField upiField = new JTextField();

        panel.add(new JLabel("Card Number:"));
        panel.add(cardNumberField);
        panel.add(new JLabel("Expiry Date (MM/YY):"));
        panel.add(expiryField);
        panel.add(new JLabel("CVV:"));
        panel.add(cvvField);
        panel.add(new JLabel("OR"));
        panel.add(new JLabel(""));
        panel.add(new JLabel("UPI ID:"));
        panel.add(upiField);

        JButton payBtn = new JButton("Pay ₹" + price);
        JButton cancelBtn = new JButton("Cancel");

        payBtn.addActionListener(e -> {
            String card = cardNumberField.getText();
            String expiry = expiryField.getText();
            String cvv = new String(cvvField.getPassword());
            String upi = upiField.getText();

            if ((card.isEmpty() || cvv.isEmpty() || expiry.isEmpty()) && upi.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill either card details or UPI ID.");
                return;
            }

            try (Connection conn = DBConnection.getConnection()) {
                // ✅ Use the selected seat number here
                String insertQuery = "INSERT INTO bookings (user_id, flight_id, seat_number) VALUES (?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, userId);
                ps.setInt(2, flightId);
                ps.setString(3, seatNumber);  // <-- Here is the change
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int bookingId = rs.getInt(1);

                    // Insert into payments table (optional enhancement)
                    PreparedStatement payStmt = conn.prepareStatement(
                            "INSERT INTO payments (booking_id, amount, payment_status) VALUES (?, ?, 'Completed')"
                    );
                    payStmt.setInt(1, bookingId);
                    payStmt.setDouble(2, price);
                    payStmt.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Payment Successful!");
                    dispose();
                    new TicketFrame(bookingId);
                } else {
                    JOptionPane.showMessageDialog(this, "Booking failed.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Payment error occurred.");
            }
        });

        cancelBtn.addActionListener(e -> {
            dispose();
            new FareFrame(userId, flightId, airline, from, to, price);
        });

        panel.add(payBtn);
        panel.add(cancelBtn);

        add(panel);
        setVisible(true);
    }
}
