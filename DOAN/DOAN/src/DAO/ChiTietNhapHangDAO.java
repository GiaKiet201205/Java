package DAO;

import DTO.ChiTietNhapHangDTO;
import config.JDBC;
import java.sql.*;
import java.util.ArrayList;

public class ChiTietNhapHangDAO {

    // Phương thức selectAll: Lấy tất cả chi tiết nhập hàng
    public ArrayList<ChiTietNhapHangDTO> selectAll() {
        ArrayList<ChiTietNhapHangDTO> chiTietNhapHangList = new ArrayList<>();
        String sql = "SELECT * FROM chi_tiet_nhap_hang";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                ChiTietNhapHangDTO chiTietNhapHang = new ChiTietNhapHangDTO(
                    rs.getString("id_chi_tiet_nhap_hang"),
                    rs.getString("id_nhap_hang"),
                    rs.getString("id_san_pham"),
                    rs.getInt("so_luong_nhap"),
                    rs.getInt("gia_nhap")
                );
                chiTietNhapHangList.add(chiTietNhapHang);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chiTietNhapHangList;
    }

    // Phương thức update: Cập nhật chi tiết nhập hàng
    public boolean update(ChiTietNhapHangDTO chiTietNhapHang) {
        String sql = "UPDATE chi_tiet_nhap_hang SET id_nhap_hang=?, id_san_pham=?, so_luong_nhap=?, gia_nhap=? WHERE id_chi_tiet_nhap_hang=?";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, chiTietNhapHang.getIdNhapHang());
            pst.setString(2, chiTietNhapHang.getIdSanPham());
            pst.setInt(3, chiTietNhapHang.getSoLuongNhap());
            pst.setInt(4, chiTietNhapHang.getGiaNhap());
            pst.setString(5, chiTietNhapHang.getIdChiTietNhapHang());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Phương thức delete: Xóa chi tiết nhập hàng theo id
    public boolean delete(String idChiTietNhapHang) {
        String sql = "DELETE FROM chi_tiet_nhap_hang WHERE id_chi_tiet_nhap_hang=?";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, idChiTietNhapHang);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Phương thức selectById: Lấy thông tin chi tiết nhập hàng theo id
    public ChiTietNhapHangDTO selectById(String idChiTietNhapHang) {
        String sql = "SELECT * FROM chi_tiet_nhap_hang WHERE id_chi_tiet_nhap_hang=?";
        ChiTietNhapHangDTO chiTietNhapHang = null;

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, idChiTietNhapHang);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                chiTietNhapHang = new ChiTietNhapHangDTO(
                    rs.getString("id_chi_tiet_nhap_hang"),
                    rs.getString("id_nhap_hang"),
                    rs.getString("id_san_pham"),
                    rs.getInt("so_luong_nhap"),
                    rs.getInt("gia_nhap")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chiTietNhapHang;
    }
}
