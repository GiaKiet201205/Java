package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

class BackgroundPanel extends JPanel {
    private final Image backgroundImage;

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
        
        // Nút quay lại trang chủ
        gbc.gridy++;
        JButton backButton = new JButton("Quay lại Trang Chủ"); 
        backButton.setBackground(new Color(120, 200, 120));
        backButton.setForeground(Color.BLACK);
        backButton.setFocusPainted(false);
        backButton.setPreferredSize(new Dimension(160, 30));
        backButton.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 0), 2));
        loginPanel.add(backButton, gbc);

        // Xử lý sự kiện quay lại
        backButton.addActionListener((ActionEvent e) -> {
            TrangChuGUI trangChu = new TrangChuGUI();
            trangChu.setVisible(true);
            dispose();  // Đóng giao diện đăng nhập
        });
        
        // Xử lý sự kiện nút đăng nhập
        loginButton.addActionListener((ActionEvent e) -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            
            // Giả lập kiểm tra tài khoản hợp lệ
            if (username.equals("admin") && password.equals("123456")) {
                JOptionPane.showMessageDialog(null, "Đăng nhập thành công!");
                
                // Mở giao diện trang chủ
                TrangChuGUI trangChu = new TrangChuGUI();
                trangChu.setVisible(true);
                
                // Đóng giao diện đăng nhập
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Tên đăng nhập hoặc mật khẩu sai!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
