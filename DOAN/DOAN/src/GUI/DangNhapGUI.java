package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DangNhapGUI extends JFrame {
    public DangNhapGUI() {
        setTitle("Đăng Nhập");
        setSize(600, 350);
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
        
        // Panel phải (Form đăng nhập)
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.LIGHT_GRAY);
        rightPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Tiêu đề "Đăng Nhập"
        JLabel titleLabel = new JLabel("Đăng Nhập");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        rightPanel.add(titleLabel, gbc);

        // Tên đăng nhập
        gbc.gridwidth = 1;
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

        // Nút Đăng nhập
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton loginButton = new JButton("Đăng nhập");
        rightPanel.add(loginButton, gbc);

        // Quên mật khẩu (có thể ấn được)
        gbc.gridy++;
        JLabel forgotPasswordLabel = new JLabel("Quên mật khẩu...");
        forgotPasswordLabel.setForeground(Color.BLUE);
        forgotPasswordLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgotPasswordLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "Chức năng quên mật khẩu đang phát triển!");
            }
        });
        rightPanel.add(forgotPasswordLabel, gbc);

        // Thêm các panel vào giao diện chính
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        // Hiển thị giao diện
        add(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DangNhapGUI().setVisible(true);
        });
    }
}
