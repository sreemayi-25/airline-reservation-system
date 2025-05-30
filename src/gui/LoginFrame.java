package gui;

import db.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginFrame extends JFrame {
    JTextField usernameField;
    JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Welcome!");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register");

        panel.add(loginBtn);
        panel.add(registerBtn);

        loginBtn.addActionListener(e -> login());
        registerBtn.addActionListener(e -> {
            dispose();
            new RegisterFrame();
        });

        add(panel);
        setVisible(true);
    }

    private void login() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, usernameField.getText());
            ps.setString(2, new String(passwordField.getPassword()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("user_id");
                JOptionPane.showMessageDialog(this, "Login successful!");
                dispose();
                new FlightSearchFrame(userId);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
