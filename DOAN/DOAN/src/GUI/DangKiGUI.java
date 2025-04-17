package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URL;

public class DangKiGUI extends JFrame {

    // Tham chiếu đến Trang Chủ
    public DangKiGUI(TrangChuGUI trangChu) {

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
            System.err.println("Không tìm thấy ảnh logo!");
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

        // Nút quay lại Trang chủ
        gbc.gridy++;
        JButton backButton = new JButton("Quay lại Trang Chủ");
        backButton.setBackground(new Color(120, 200, 120));
        backButton.setForeground(Color.BLACK);
        backButton.setFocusPainted(false);
        backButton.setPreferredSize(new Dimension(160, 30));
        backButton.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 0), 2));
        rightPanel.add(backButton, gbc); // Thêm backButton vào panel

        // Xử lý nút Đăng ký
        registerButton.addActionListener((var e) -> {
            String fullName = fullNameField.getText();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String phone = phoneField.getText();

            if (fullName.isEmpty() || username.isEmpty() || password.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
               }

            JOptionPane.showMessageDialog(null, "Đăng ký thành công! Mời bạn đăng nhập.");

            DangNhapGUI dangNhapGUI = new DangNhapGUI(); // Truyền lại trang chủ
            dangNhapGUI.setVisible(true);
            dispose();
        });

        // Xử lý nút Quay lại Trang chủ
        backButton.addActionListener((ActionEvent e) -> {
            TrangChuGUI trangChu1 = new TrangChuGUI();
            trangChu1.setVisible(true);
            dispose();  // Đóng giao diện 
        });

        backgroundPanel.add(leftPanel, BorderLayout.WEST);
        backgroundPanel.add(rightPanel, BorderLayout.CENTER);
        add(backgroundPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TrangChuGUI trangChu = new TrangChuGUI();
            trangChu.setVisible(true);
            new DangKiGUI(trangChu).setVisible(true);
        });
    }

    DangKiGUI() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }