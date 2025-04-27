package BLL;

import DAO.TaiKhoanDAO;
import DTO.TaiKhoanDTO;
import GUI.QuanTriVienGUI;
import GUI.TrangChuGUI;

public class TaiKhoanBLL {
    private PhanQuyenBLL phanQuyenBLL = new PhanQuyenBLL();

    // Phương thức xử lý đăng nhập và phân quyền
    public TaiKhoanDTO login(String tenUser, String password) {
        // Kiểm tra tài khoản và phân quyền
        int phanQuyenValue = phanQuyenBLL.loginAndCheckPhanQuyen(tenUser, password);
        
        if (phanQuyenValue != -1) {
            TaiKhoanDTO taiKhoan = new TaiKhoanDAO().login(tenUser, password);
            
            if (taiKhoan != null) {
                // lưu tài khoản khách hàng đã đăng nhập để lấy id khách hàng
                Session.setCurrentTaiKhoan(taiKhoan);
                // Kiểm tra phân quyền và chuyển hướng
                if (phanQuyenValue == 1) {
                    System.out.println("Đăng nhập với quyền Admin, chuyển đến giao diện quản trị viên.");
                    QuanTriVienGUI quanTriVienGUI = new QuanTriVienGUI();
                    quanTriVienGUI.setVisible(true);
                } else if (phanQuyenValue == 2) {
                    System.out.println("Đăng nhập với quyền User, chuyển đến giao diện trang chủ.");
                    TrangChuGUI trangChuGUI = new TrangChuGUI();
                    trangChuGUI.setVisible(true);
                }
                
                return taiKhoan;
            }
        }
        
        // Nếu không có tài khoản hợp lệ, hiển thị thông báo lỗi
        System.out.println("Đăng nhập không thành công, tài khoản hoặc mật khẩu sai.");
        return null;
    }
}
