package BLL;

import DAO.SanPhamDAO;
import DTO.SanPhamDTO;

public class SanPhamBLL {
    private SanPhamDAO sanPhamDAO;

    public SanPhamBLL() {
        sanPhamDAO = new SanPhamDAO();
    }

    // Lấy thông tin sản phẩm theo ID
    public SanPhamDTO getSanPhamById(String idSanPham) {
        for (SanPhamDTO sp : sanPhamDAO.selectAll()) {
            if (sp.getIdSanPham().equals(idSanPham)) {
                return sp;
            }
        }
        return null;
    }
}
