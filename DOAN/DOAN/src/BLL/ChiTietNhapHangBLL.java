package BLL;

import DAO.ChiTietNhapHangDAO;
import DTO.ChiTietNhapHangDTO;
import java.util.ArrayList;
import java.util.List;

public class ChiTietNhapHangBLL {
    private ChiTietNhapHangDAO chiTietNhapHangDAO;

    public ChiTietNhapHangBLL() {
        chiTietNhapHangDAO = new ChiTietNhapHangDAO();
    }

    public String generateNewChiTietNhapHangId() {
        List<ChiTietNhapHangDTO> chiTietList = chiTietNhapHangDAO.selectAll();
        int maxId = 0;
        for (ChiTietNhapHangDTO ct : chiTietList) {
            String id = ct.getIdChiTietNhapHang().replace("CN", "");
            try {
                int num = Integer.parseInt(id);
                if (num > maxId) maxId = num;
            } catch (NumberFormatException e) {
                System.err.println("Lỗi khi parse ID chi tiết nhập hàng: " + id);
            }
        }
        String newId = String.format("CN%03d", maxId + 1);
        System.out.println("Tạo ID chi tiết nhập hàng mới: " + newId);
        return newId;
    }

    public void addChiTietNhapHang(ChiTietNhapHangDTO chiTiet) throws Exception {
        System.out.println("Bắt đầu thêm chi tiết nhập hàng: " + chiTiet.getIdChiTietNhapHang());
        if (chiTiet.getIdNhapHang() == null || chiTiet.getIdNhapHang().isEmpty()) {
            throw new Exception("ID Nhập Hàng không được để trống");
        }
        if (chiTiet.getIdSanPham() == null || chiTiet.getIdSanPham().isEmpty()) {
            throw new Exception("ID Sản Phẩm không được để trống");
        }
        if (chiTiet.getSoLuongNhap() <= 0) {
            throw new Exception("Số lượng nhập phải lớn hơn 0");
        }
        if (chiTiet.getGiaNhap() <= 0) {
            throw new Exception("Giá nhập phải lớn hơn 0");
        }

        if (chiTiet.getIdChiTietNhapHang() == null || chiTiet.getIdChiTietNhapHang().isEmpty()) {
            chiTiet.setIdChiTietNhapHang(generateNewChiTietNhapHangId());
        }

        chiTietNhapHangDAO.insert(chiTiet);
        System.out.println("Thêm chi tiết nhập hàng thành công: " + chiTiet.getIdChiTietNhapHang());
    }

    public void updateChiTietNhapHang(ChiTietNhapHangDTO chiTiet) throws Exception {
        System.out.println("Bắt đầu cập nhật chi tiết nhập hàng: " + chiTiet.getIdChiTietNhapHang());
        if (chiTiet.getIdSanPham() == null || chiTiet.getIdSanPham().isEmpty()) {
            throw new Exception("ID Sản Phẩm không được để trống");
        }
        if (chiTiet.getSoLuongNhap() <= 0) {
            throw new Exception("Số lượng nhập phải lớn hơn 0");
        }
        if (chiTiet.getGiaNhap() <= 0) {
            throw new Exception("Giá nhập phải lớn hơn 0");
        }

        chiTietNhapHangDAO.update(chiTiet);
        System.out.println("Cập nhật chi tiết nhập hàng thành công: " + chiTiet.getIdChiTietNhapHang());
    }

    public void deleteChiTietNhapHangByIdNhapHang(String idNhapHang) throws Exception {
        System.out.println("Bắt đầu xóa chi tiết nhập hàng theo ID nhập hàng: " + idNhapHang);
        List<ChiTietNhapHangDTO> chiTietList = chiTietNhapHangDAO.selectAll();
        for (ChiTietNhapHangDTO chiTiet : chiTietList) {
            if (chiTiet.getIdNhapHang().equals(idNhapHang)) {
                chiTietNhapHangDAO.delete(chiTiet.getIdChiTietNhapHang());
                System.out.println("Xóa chi tiết nhập hàng thành công: " + chiTiet.getIdChiTietNhapHang());
            }
        }
    }

    public ChiTietNhapHangDTO getChiTietNhapHangByIdNhapHang(String idNhapHang) {
        return chiTietNhapHangDAO.getByIdNhapHang(idNhapHang);
    }

    public List<ChiTietNhapHangDTO> getAllChiTietNhapHang() {
        return chiTietNhapHangDAO.selectAll();
    }
}