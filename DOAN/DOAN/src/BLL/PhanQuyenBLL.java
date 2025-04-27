package BLL;

import DAO.TaiKhoanDAO;
import DTO.TaiKhoanDTO;
import DAO.PhanQuyenDAO;
import DTO.PhanQuyenDTO;

public class PhanQuyenBLL {
    private TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
    private PhanQuyenDAO phanQuyenDAO = new PhanQuyenDAO();

    // Phương thức kiểm tra đăng nhập và phân quyền
    public int loginAndCheckPhanQuyen(String tenUser, String password) {
        // Kiểm tra tài khoản và mật khẩu
        TaiKhoanDTO taiKhoan = taiKhoanDAO.login(tenUser, password);
        
        if (taiKhoan != null) {
            PhanQuyenDTO phanQuyen = phanQuyenDAO.getPhanQuyenByTaiKhoan(taiKhoan.getIdTaiKhoan());
            
            if (phanQuyen != null) {
                if (phanQuyen.getTenQuyen().equalsIgnoreCase("admin")) {
                    return 1; // Quản trị viên
                } else if (phanQuyen.getTenQuyen().equalsIgnoreCase("user")) {
                    return 2; // Người dùng
                }
            }
        }
        return -1; // Nếu không tìm thấy tài khoản hoặc phân quyền không hợp lệ
    }
}
