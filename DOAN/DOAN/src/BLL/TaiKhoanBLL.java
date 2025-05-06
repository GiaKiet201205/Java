package BLL;

import DAO.TaiKhoanDAO;
import DAO.KhachHangDAO;
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

    public boolean register(String ten_user, String email, String sdt, String password) {
        // Kiểm tra xem tên đăng nhập đã tồn tại chưa
        if (taiKhoanDAO.isUsernameExists(ten_user)) {
            return false;
        }

        // Tạo khách hàng mới
        KhachHangDAO khachHangDAO = new KhachHangDAO();
        String idKhachHang = khachHangDAO.insertAndReturnID(ten_user, email, sdt); // Gọi hàm insert và lấy ID khách hàng
        if (idKhachHang == null) {
            return false; 
        }

        // Tạo tài khoản cho khách hàng
        TaiKhoanDTO taiKhoan = new TaiKhoanDTO();
        String idNumber = idKhachHang.substring(2); // Lấy số từ KH0XX (ví dụ: 001)
        String idTaiKhoan = "TK" + idNumber; // TK0XX
        String userName = "user" + idNumber; // user0XX

        taiKhoan.setIdTaiKhoan(idTaiKhoan);
        taiKhoan.setIdKhachHang(idKhachHang);
        taiKhoan.setTenUser(userName);
        taiKhoan.setPassword(password); // Giữ mật khẩu gốc, không mã hóa
        taiKhoan.setPhanQuyen("2");
        taiKhoan.setTrangThai("use");

        // Thêm tài khoản vào cơ sở dữ liệu
        return taiKhoanDAO.insert(taiKhoan);
    }

    private String generateUniqueId() {
        return "TK" + System.currentTimeMillis();
    }
}