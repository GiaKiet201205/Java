package BLL;

import DAO.TaiKhoanDAO;
import DTO.TaiKhoanDTO;
import GUI.DangNhapGUI;
import GUI.NguoiDungGUI;
import javax.swing.*;

public class TaiKhoanBLL {
    private TaiKhoanDAO taiKhoanDAO;

    public TaiKhoanBLL() {
        taiKhoanDAO = new TaiKhoanDAO();
    }

    public void showLoginForm(JFrame currentFrame) {
        currentFrame.setVisible(false); // Hide the current frame
        new DangNhapGUI().setVisible(true); // Show login form
    }

    public void handleLogin(String username, String password, JFrame loginFrame) {
        TaiKhoanDTO tk = taiKhoanDAO.login(username, password);

        if (tk != null) {
            Session.setCurrentTaiKhoan(tk); // Store user in session
            String quyen = tk.getPhanQuyen();

            if (quyen.equals("2")) {
                JOptionPane.showMessageDialog(null, "Đăng nhập thành công!");
                new NguoiDungGUI().setVisible(true); // Redirect to NguoiDungGUI
                loginFrame.dispose(); // Close login form
            } else {
                JOptionPane.showMessageDialog(null, "Chỉ người dùng có quyền 2 được phép đăng nhập vào giao diện này!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Sai tên đăng nhập hoặc mật khẩu!", 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isUserLoggedIn() {
        return Session.getCurrentTaiKhoan() != null; // Check if a user is logged in
    }

    public void handleCartCheckout(JFrame currentFrame) {
        if (isUserLoggedIn()) {
            JOptionPane.showMessageDialog(null, "Xác nhận thanh toán thành công!");
            // Add further checkout logic here if needed
        } else {
            JOptionPane.showMessageDialog(null, "Bạn chưa đăng nhập!", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
            showLoginForm(currentFrame); // Show login form if not logged in
        }
    }
}