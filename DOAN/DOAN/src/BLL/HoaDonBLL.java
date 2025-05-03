package BLL;

import DAO.DonHangDAO;
import DTO.DonHangDTO;
import java.util.Date;

public class HoaDonBLL {
    private DonHangDAO donHangDAO = new DonHangDAO();

    // Phương thức tạo đơn hàng mới
    public String createDonHang(DonHangDTO donHang, String idKhachHang, String idNhanVien, int tongTien, 
                                String trangThai, String hinhThucMuaHang, String diaDiemGiao) {
        // Lấy id đơn hàng mới
        String idDonHang = donHangDAO.generateNextId();

        // Lấy ngày hiện tại
        Date ngayDatHang = new Date();

        // Cập nhật các thông tin đơn hàng
        donHang.setIdDonHang(idDonHang);
        donHang.setIdKhachHang(idKhachHang); // Lấy id khách hàng từ session
        donHang.setIdNhanVien(idNhanVien);
        donHang.setTongTien(tongTien);
        donHang.setNgayDatHang(ngayDatHang);
        donHang.setTrangThai(trangThai);
        donHang.setHinhThucMuaHang(hinhThucMuaHang);
        donHang.setDiaDiemGiao(diaDiemGiao);

        return donHangDAO.insert(donHang);
    }

    // Phương thức cập nhật đơn hàng
    public boolean updateDonHang(DonHangDTO donHang) {
        return donHangDAO.update(donHang);
    }

    // Phương thức xóa đơn hàng
    public boolean deleteDonHang(String idDonHang) {
        return donHangDAO.delete(idDonHang);
    }

    // Phương thức lấy thông tin đơn hàng theo ID
    public DonHangDTO getDonHangById(String idDonHang) {
        return donHangDAO.selectById(idDonHang);
    }
}
