/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;
        
import javax.swing.*;
import java.awt.*;

public class DangKiGUI extends JFrame {
    public DangKiGUI() {
        setTitle("Đăng Kí");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel chính (dùng BorderLayout)
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.LIGHT_GRAY);

        // Panel logo (bên trái, ở giữa theo chiều dọc)
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(Color.LIGHT_GRAY);
        JLabel logoLabel = new JLabel("SSS");
        logoLabel.setFont(new Font("Serif", Font.BOLD, 36));
        leftPanel.add(logoLabel);

        // Panel phải (Form đăng ký)
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.LIGHT_GRAY);
        rightPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Tiêu đề "Đăng Kí"
        JLabel titleLabel = new JLabel("Đăng Kí");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        rightPanel.add(titleLabel, gbc);

        // Họ và tên
        gbc.gridwidth = 1;
        gbc.gridy++;
        rightPanel.add(new JLabel("Họ và tên"), gbc);
        gbc.gridx = 1;
        JTextField fullNameField = new JTextField(15);
        rightPanel.add(fullNameField, gbc);

        // Tên đăng nhập
        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(new JLabel("Tên đăng nhập"), gbc);
        gbc.gridx = 1;
        JTextField usernameField = new JTextField(15);
        rightPanel.add(usernameField, gbc);

        // Mật khẩu
        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(new JLabel("Mật khẩu"), gbc);
        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(15);
        rightPanel.add(passwordField, gbc);

        // Số điện thoại
        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(new JLabel("Số điện thoại"), gbc);
        gbc.gridx = 1;
        JTextField phoneField = new JTextField(15);
        rightPanel.add(phoneField, gbc);

        // Nút Đăng ký
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton registerButton = new JButton("Đăng kí");
        rightPanel.add(registerButton, gbc);

        // Thêm các panel vào giao diện chính
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        // Hiển thị giao diện
        add(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DangKiGUI().setVisible(true);
        });
    }
}

