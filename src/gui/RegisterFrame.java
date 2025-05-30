package gui;

import db.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RegisterFrame extends JFrame {
    JTextField usernameField, emailField;
    JPasswordField passwordField;

    public RegisterFrame() {
        setTitle("User Registration");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        JButton registerBtn = new JButton("Register");
        panel.add(registerBtn);

        registerBtn.addActionListener(e -> registerUser());

        add(panel);
        setVisible(true);
    }

    private void registerUser() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, usernameField.getText());
            ps.setString(2, new String(passwordField.getPassword()));
            ps.setString(3, emailField.getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Registration Successful!");
            dispose();
            new LoginFrame();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
