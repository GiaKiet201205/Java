package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URL;

public class DangKiGUI extends JFrame {
    public DangKiGUI() {
        setTitle("Đăng Kí");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true); // Cho phép thay đổi kích thước

        // Panel nền
        BackgroundPanel backgroundPanel = new BackgroundPanel("/images/anhnendki.jpeg"); // Sử dụng đường dẫn resource
        backgroundPanel.setLayout(new BorderLayout());

        // Panel logo (bên trái)
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setOpaque(false); // Để panel trong suốt
        JLabel logoLabel = new JLabel();
        leftPanel.add(logoLabel);

        // Load ảnh logo và điều chỉnh kích thước tự động
        URL logoURL = getClass().getResource("/images/anhnen.jpeg");
        if (logoURL != null) {
            ImageIcon originalIcon = new ImageIcon(logoURL);
            leftPanel.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    int panelSize = Math.min(leftPanel.getWidth(), leftPanel.getHeight()) - 20;
                    if (panelSize > 0) {
                        Image scaledImg = originalIcon.getImage().getScaledInstance(panelSize, panelSize, Image.SCALE_SMOOTH);
                        logoLabel.setIcon(new ImageIcon(scaledImg));
                    }
                }
            });
        } else {
            System.err.println("Không tìm thấy ảnh logo!");
        }

        // Panel phải (Form đăng ký)
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Tiêu đề
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
        registerButton.setBackground(new Color(120, 200, 120)); // Màu xanh lá nhạt 
        registerButton.setForeground(Color.BLACK); // Chữ trắng
        registerButton.setFont(new Font("Arial", Font.BOLD, 16)); // Font to, đậm
        registerButton.setFocusPainted(false); // Bỏ viền khi chọn
        registerButton.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 0), 2));
        registerButton.setPreferredSize(new Dimension(109, 30)); // Bo góc nhẹ
        rightPanel.add(registerButton, gbc);

        // Thêm các panel vào BackgroundPanel
        backgroundPanel.add(leftPanel, BorderLayout.WEST);
        backgroundPanel.add(rightPanel, BorderLayout.CENTER);

        // Hiển thị giao diện
        add(backgroundPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DangKiGUI().setVisible(true);
        });
    }
}

// Class BackgroundPanel để vẽ ảnh nền
class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        URL imageURL = getClass().getResource(imagePath);
        if (imageURL != null) {
            backgroundImage = new ImageIcon(imageURL).getImage();
        } else {
            System.err.println("Không tìm thấy ảnh nền: " + imagePath);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

