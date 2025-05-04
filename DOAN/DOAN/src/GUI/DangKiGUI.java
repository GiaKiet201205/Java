package GUI;

import BLL.TaiKhoanBLL;
import DTO.TaiKhoanDTO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URL;

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

public class DangKiGUI extends JFrame {

    private final TaiKhoanBLL taiKhoanBLL;

    public DangKiGUI(TrangChuGUI trangChu) {
        taiKhoanBLL = new TaiKhoanBLL();

        setTitle("Đăng Kí");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        BackgroundPanel backgroundPanel = new BackgroundPanel("/images/anhnendki.jpeg");
        backgroundPanel.setLayout(new BorderLayout());

        // Panel trái - logo
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setOpaque(false);
        JLabel logoLabel = new JLabel();
        leftPanel.add(logoLabel);

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
            System.err.println("Không tìm thấy ảnh logo.");
        }

        // Panel phải - form đăng ký
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel titleLabel = new JLabel("Đăng Kí");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        rightPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        rightPanel.add(new JLabel("Họ và tên"), gbc);
        gbc.gridx = 1;
        JTextField fullNameField = new JTextField(15);
        rightPanel.add(fullNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(new JLabel("Tên đăng nhập"), gbc);
        gbc.gridx = 1;
        JTextField usernameField = new JTextField(15);
        rightPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(new JLabel("Mật khẩu"), gbc);
        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(15);
        rightPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(new JLabel("Số điện thoại"), gbc);
        gbc.gridx = 1;
        JTextField phoneField = new JTextField(15);
        rightPanel.add(phoneField, gbc);

        // Nút đăng ký
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton registerButton = new JButton("Đăng kí");
        registerButton.setBackground(new Color(120, 200, 120));
        registerButton.setForeground(Color.BLACK);
        registerButton.setFont(new Font("Arial", Font.BOLD, 13));
        registerButton.setFocusPainted(false);
        registerButton.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 0), 2));
        registerButton.setPreferredSize(new Dimension(109, 30));
        rightPanel.add(registerButton, gbc);

        // Xử lý nút Đăng ký
        registerButton.addActionListener((ActionEvent e) -> {
            String fullName = fullNameField.getText().trim();
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String phone = phoneField.getText().trim();

            // Kiểm tra thông tin đầu vào
            if (fullName.isEmpty() || username.isEmpty() || password.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!", 
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Kiểm tra định dạng số điện thoại (chỉ chứa số, độ dài 10-11)
            if (!phone.matches("\\d{10,11}")) {
                JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ! Vui lòng nhập 10-11 chữ số.", 
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Gọi TaiKhoanBLL để đăng ký
            boolean isRegistered = taiKhoanBLL.register(username, password);

            if (isRegistered) {
                JOptionPane.showMessageDialog(null, "Đăng ký thành công! Mời bạn đăng nhập.");
                new DangNhapGUI(trangChu).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Tên đăng nhập đã tồn tại! Vui lòng chọn tên khác.", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        backgroundPanel.add(leftPanel, BorderLayout.WEST);
        backgroundPanel.add(rightPanel, BorderLayout.CENTER);
        add(backgroundPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DangKiGUI(null).setVisible(true);
        });
    }
}