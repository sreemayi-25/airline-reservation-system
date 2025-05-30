package gui;

import javax.swing.*;
import java.awt.*;

public class FareFrame extends JFrame {
    public FareFrame(int userId, int flightId, String airline, String from, String to, double price) {
        setTitle("Flight Fare");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Airline: " + airline));
        panel.add(new JLabel("From: " + from));
        panel.add(new JLabel("To: " + to));
        panel.add(new JLabel("Fare: ₹" + price));

        JButton proceedBtn = new JButton("Proceed to select seat");
        JButton backBtn = new JButton("Back to Search Flights");

        proceedBtn.addActionListener(e -> {
            dispose();
            new BookingFrame(userId, flightId);
        });


        backBtn.addActionListener(e -> {
            dispose();
            new FlightSearchFrame(userId);
        });

        panel.add(proceedBtn);
        panel.add(backBtn);

        add(panel);
        setVisible(true);
    }
}
