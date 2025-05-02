package BLL;

import DAO.NhanVienDAO;
import DTO.NhanVienDTO;

public class NhanVienBLL {
    private NhanVienDAO nhanVienDAO;

    public NhanVienBLL() {
        nhanVienDAO = new NhanVienDAO();
    }

    // Lấy thông tin nhân viên theo ID
    public NhanVienDTO getNhanVienById(String idNhanVien) {
        return nhanVienDAO.selectById(idNhanVien);
    }
}