package elektronik;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class login {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/barang_elektronik";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton button1;
    private JFrame frame;

    public login() {
        frame = new JFrame("Login Form");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 250));
        frame.setResizable(false);

        frame.add(createPanel());
        frame.getContentPane().setBackground(Color.WHITE); // Set the background color to white
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Tambahkan judul "Login" di atas panel
        JLabel titleLabel = new JLabel("Login", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        // Username
        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        usernamePanel.setBackground(Color.WHITE);
        usernamePanel.add(new JLabel("Username:"));
        textField1 = new JTextField(20);
        usernamePanel.add(textField1);
        panel.add(usernamePanel);

        // Password
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passwordPanel.setBackground(Color.WHITE);
        passwordPanel.add(new JLabel("Password:"));
        passwordField1 = new JPasswordField(20);
        passwordPanel.add(passwordField1);
        panel.add(passwordPanel);

        // Login button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        button1 = new JButton("Login");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateLogin()) {
                    JOptionPane.showMessageDialog(frame, "Login Successful!");
                    openMainForm();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password");
                }
            }
        });
        buttonPanel.add(button1);
        panel.add(buttonPanel);

        // Tambahkan panel login ke dalam panel utama
        mainPanel.add(panel, BorderLayout.CENTER);

        return mainPanel;
    }

    private boolean validateLogin() {
        String username = textField1.getText();
        String password = new String(passwordField1.getPassword());

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String query = "SELECT * FROM login WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private void openMainForm() {
        Mainscreen mainscreen = new Mainscreen();
        mainscreen.setVisible(true);

        frame.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new login();
            }
        });
    }
}
