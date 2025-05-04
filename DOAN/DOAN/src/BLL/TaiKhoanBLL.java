package BLL;

import DAO.TaiKhoanDAO;
import DTO.TaiKhoanDTO;
import GUI.DangNhapGUI;
import GUI.NguoiDungGUI;
import javax.swing.*;

public class TaiKhoanBLL {
    private TaiKhoanDAO taiKhoanDAO;

    public enum Role {
        USER("2"),
        ADMIN("1");

        private final String value;

        Role(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public TaiKhoanBLL() {
        try {
            taiKhoanDAO = new TaiKhoanDAO();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi khởi tạo TaiKhoanDAO: " + e.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void showLoginForm(JFrame currentFrame) {
        currentFrame.setVisible(false);
        new DangNhapGUI(null).setVisible(true);
    }

    public void handleLogin(String username, String password, JFrame loginFrame) {
        TaiKhoanDTO tk = taiKhoanDAO.login(username, password);

        if (tk != null) {
            Session.setCurrentTaiKhoan(tk);
            String quyen = tk.getPhanQuyen();

            if (quyen.equals(Role.USER.getValue())) {
                JOptionPane.showMessageDialog(null, "Đăng nhập thành công!");
                new NguoiDungGUI().setVisible(true);
                loginFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Chỉ người dùng có quyền USER được phép đăng nhập vào giao diện này!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Sai tên đăng nhập hoặc mật khẩu!", 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isUserLoggedIn() {
        return Session.getCurrentTaiKhoan() != null;
    }

    public void handleCartCheckout(JFrame currentFrame) {
        if (isUserLoggedIn()) {
            JOptionPane.showMessageDialog(null, "Xác nhận thanh toán thành công!");
        } else {
            JOptionPane.showMessageDialog(null, "Bạn chưa đăng nhập!", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
            showLoginForm(currentFrame);
        }
    }

    public boolean register(String username, String password) {
        if (taiKhoanDAO.isUsernameExists(username)) {
            return false;
        }

        TaiKhoanDTO tk = new TaiKhoanDTO();
        tk.setTenUser(username);
        tk.setPassword(password);
        tk.setPhanQuyen("2");
        tk.setTrangThai("1");
        tk.setIdTaiKhoan(generateUniqueId());
        tk.setIdKhachHang(null);

        return taiKhoanDAO.insert(tk); // Thay addTaiKhoan thành insert
    }

    private String generateUniqueId() {
        return "TK" + System.currentTimeMillis();
    }
}