package GUI;

import BLL.Session;
import DAO.TaiKhoanDAO;
import DTO.TaiKhoanDTO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

class BackgroundPanel extends JPanel {
    private final Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
        backgroundImage = icon.getImage();
        if (backgroundImage == null || icon.getIconWidth() == -1) {
            System.err.println("Lỗi: Không tìm thấy ảnh nền tại " + imagePath);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}

public class DangNhapGUI extends JFrame {
    private static final String ADMIN_ROLE = "1";
    private static final String USER_ROLE = "2";
    private final TrangChuGUI trangChuGUI;

    public DangNhapGUI(TrangChuGUI trangChuGUI) {
        this.trangChuGUI = trangChuGUI;
        setTitle("Đăng Nhập");
        setSize(600, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        BackgroundPanel backgroundPanel = new BackgroundPanel("/images/anhnendnhap.jpeg");
        backgroundPanel.setLayout(new BorderLayout());

        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setOpaque(false);
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

        gbc.gridwidth = 1;
        gbc.gridy++;
        JLabel userLabel = new JLabel("Tên đăng nhập");
        userLabel.setForeground(Color.BLACK);
        loginPanel.add(userLabel, gbc);
        gbc.gridx = 1;
        JTextField usernameField = new JTextField(15);
        loginPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel passLabel = new JLabel("Mật khẩu");
        passLabel.setForeground(Color.BLACK);
        loginPanel.add(passLabel, gbc);
        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(15);
        loginPanel.add(passwordField, gbc);

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

        gbc.gridy++;
        JLabel forgotPasswordLabel = new JLabel("Quên mật khẩu...");
        forgotPasswordLabel.setForeground(Color.BLACK);
        forgotPasswordLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginPanel.add(forgotPasswordLabel, gbc);

        loginButton.addActionListener((ActionEvent e) -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên đăng nhập và mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
            TaiKhoanDTO tk = taiKhoanDAO.login(username, password);

            if (tk != null) {
                Session.setCurrentTaiKhoan(tk);
                
                String quyen = tk.getPhanQuyen();
                if (ADMIN_ROLE.equals(quyen)) {
                    JOptionPane.showMessageDialog(this, "Đăng nhập thành công! (Admin)");
                    new QuanTriVienGUI(trangChuGUI).setVisible(true);
                    dispose();
                } else if (USER_ROLE.equals(quyen)) {
                    JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
                    new NguoiDungGUI().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Không xác định được quyền đăng nhập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Tên đăng nhập hoặc mật khẩu sai!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

            usernameField.setText("");
            passwordField.setText("");
            usernameField.requestFocus();
        });

        forgotPasswordLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JOptionPane.showMessageDialog(DangNhapGUI.this, "Liên hệ quản trị viên để đặt lại mật khẩu.", "Quên mật khẩu", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        backgroundPanel.add(loginPanel, BorderLayout.CENTER);
        setContentPane(backgroundPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TrangChuGUI trangChuGUI = new TrangChuGUI();
            new DangNhapGUI(trangChuGUI).setVisible(true);
        });
    }
}