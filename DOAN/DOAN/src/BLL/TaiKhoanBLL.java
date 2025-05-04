package BLL;

import DAO.TaiKhoanDAO;
import DTO.TaiKhoanDTO;
import GUI.DangNhapGUI;
import GUI.NguoiDungGUI;
import javax.swing.*;
import java.util.Random;

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
        String id = generateUniqueId();
        tk.setIdTaiKhoan(id);
        tk.setIdKhachHang(null);

        System.out.println("ID tài khoản trước khi chèn: " + id); // Debug

        return taiKhoanDAO.insert(tk);
    }

    private String generateUniqueId() {
        Random random = new Random();
        String id;
        boolean isUnique;
        do {
            int randomNum = 100 + random.nextInt(900); // Số ngẫu nhiên 3 chữ số (100-999)
            id = "TK" + randomNum; // Ví dụ: TK123
            System.out.println("ID được tạo: " + id + " (độ dài: " + id.length() + ")"); // Debug
            if (id.length() > 5) {
                System.err.println("Cảnh báo: ID vượt quá 5 ký tự!");
                id = id.substring(0, 5); // Cắt bớt nếu cần
            }
            isUnique = !isIdExists(id);
        } while (!isUnique);
        return id;
    }

    private boolean isIdExists(String id) {
        String sql = "SELECT COUNT(*) FROM tai_khoan WHERE id_tai_khoan = ?";
        try (java.sql.Connection con = config.JDBC.getConnection();
             java.sql.PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, id);
            try (java.sql.ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}