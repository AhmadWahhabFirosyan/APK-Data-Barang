package elektronik;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Registration {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/barang_elektronik";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private JTextField fullNameField;
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JFrame frame;

    public Registration() {
        frame = new JFrame("Registration Form");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400); // Set the initial size as needed
        frame.setResizable(false);

        frame.add(createPanel());
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);
    }

    private JPanel createPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Increased insets for better spacing

        // Registrasi Label
        JLabel registrationLabel = new JLabel("Registrasi");
        registrationLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(registrationLabel, gbc);

        // Full Name
        gbc.gridy++;
        addLabel(panel, gbc, "Full Name:");
        addRegistrationField(panel, gbc, fullNameField = new JTextField(20));

        // Username
        gbc.gridy++;
        addLabel(panel, gbc, "Username:");
        addRegistrationField(panel, gbc, usernameField = new JTextField(20));

        // Email
        gbc.gridy++;
        addLabel(panel, gbc, "Email:");
        addRegistrationField(panel, gbc, emailField = new JTextField(20));

        // Password
        gbc.gridy++;
        addLabel(panel, gbc, "Password:");
        addRegistrationField(panel, gbc, passwordField = new JPasswordField(20));

        // Register button
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (registerUser()) {
                    JOptionPane.showMessageDialog(frame, "Registration Successful!");
                    openLoginForm();
                } else {
                    JOptionPane.showMessageDialog(frame, "Registration Failed. Please try again.");
                }
            }
        });
        panel.add(registerButton, gbc);

        return panel;
    }

    private void addLabel(JPanel panel, GridBagConstraints gbc, String text) {
        gbc.gridx = 0;
        JLabel label = new JLabel(text);
        panel.add(label, gbc);
    }

    private void addRegistrationField(JPanel panel, GridBagConstraints gbc, JTextField textField) {
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(textField, gbc);
        gbc.gridy++;
    }

    private boolean registerUser() {
        String fullName = fullNameField.getText();
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO registration (Fullname, Username, Email, Password) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, fullName);
                preparedStatement.setString(2, username);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, password);

                int result = preparedStatement.executeUpdate();
                return result > 0;  // Return true if at least one row was affected (registration successful)
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;  // Return false if an exception occurred (registration failed)
        }
    }

    private void openLoginForm() {
        login loginForm = new login();
        frame.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Registration();
            }
        });
    }
}
