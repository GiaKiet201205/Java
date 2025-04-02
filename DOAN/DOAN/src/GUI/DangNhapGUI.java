package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
        if (backgroundImage == null) {
            System.out.println("Lỗi: Không tìm thấy ảnh nền!");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}

public class DangNhapGUI extends JFrame {
    public DangNhapGUI() {
        setTitle("Đăng Nhập");
        setSize(600, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Sử dụng JPanel có ảnh nền tự động co giãn
        BackgroundPanel backgroundPanel = new BackgroundPanel("/images/anhnendnhap.jpeg");
        backgroundPanel.setLayout(new BorderLayout());

        // Panel đăng nhập
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setOpaque(false); // Không che nền
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel titleLabel = new JLabel("Đăng Nhập");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginPanel.add(titleLabel, gbc);

        // Tên đăng nhập
        gbc.gridwidth = 1;
        gbc.gridy++;
        JLabel userLabel = new JLabel("Tên đăng nhập");
        userLabel.setForeground(Color.BLACK);
        loginPanel.add(userLabel, gbc);
        gbc.gridx = 1;
        JTextField usernameField = new JTextField(15);
        loginPanel.add(usernameField, gbc);

        // Mật khẩu
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel passLabel = new JLabel("Mật khẩu");
        passLabel.setForeground(Color.BLACK);
        loginPanel.add(passLabel, gbc);
        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(15);
        loginPanel.add(passwordField, gbc);

        // Nút đăng nhập
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton loginButton = new JButton("Đăng nhập");
        loginButton.setBackground(new Color(120, 200, 120));
        loginButton.setForeground(Color.BLACK);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 0), 2));
        loginButton.setPreferredSize(new Dimension(109, 30));
        loginPanel.add(loginButton, gbc);

        // Quên mật khẩu
        gbc.gridy++;
        JLabel forgotPasswordLabel = new JLabel("Quên mật khẩu...");
        forgotPasswordLabel.setForeground(Color.BLACK);
        forgotPasswordLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginPanel.add(forgotPasswordLabel, gbc);

        // Xử lý sự kiện nút đăng nhập
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                System.out.println("Tên đăng nhập: " + username);
                System.out.println("Mật khẩu: " + password);
                // Thêm xử lý xác thực đăng nhập ở đây
            }
        });

        // Thêm panel vào backgroundPanel
        backgroundPanel.add(loginPanel, BorderLayout.CENTER);
        setContentPane(backgroundPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DangNhapGUI().setVisible(true));
    }
}