package DAO;

import DTO.ChiTietNhapHangDTO;
import config.JDBC;
import java.sql.*;
import java.util.ArrayList;

public class ChiTietNhapHangDAO {

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
            System.err.println("Lỗi SQL khi lấy tất cả chi tiết nhập hàng: " + e.getMessage());
            e.printStackTrace();
        }
        return chiTietNhapHangList;
    }

    public ChiTietNhapHangDTO getByIdNhapHang(String idNhapHang) {
        String sql = "SELECT * FROM chi_tiet_nhap_hang WHERE id_nhap_hang = ?";
        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, idNhapHang);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new ChiTietNhapHangDTO(
                        rs.getString("id_chi_tiet_nhap_hang"),
                        rs.getString("id_nhap_hang"),
                        rs.getString("id_san_pham"),
                        rs.getInt("so_luong_nhap"),
                        rs.getInt("gia_nhap")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi lấy chi tiết nhập hàng theo id_nhap_hang: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void insert(ChiTietNhapHangDTO chiTietNhapHang) throws SQLException {
        String sql = "INSERT INTO chi_tiet_nhap_hang (id_chi_tiet_nhap_hang, id_nhap_hang, id_san_pham, so_luong_nhap, gia_nhap) VALUES (?, ?, ?, ?, ?)";
        System.out.println("Thực hiện insert chi tiết nhập hàng: " + chiTietNhapHang.getIdChiTietNhapHang() + ", id_nhap_hang: " + chiTietNhapHang.getIdNhapHang());

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, chiTietNhapHang.getIdChiTietNhapHang());
            pst.setString(2, chiTietNhapHang.getIdNhapHang());
            pst.setString(3, chiTietNhapHang.getIdSanPham());
            pst.setInt(4, chiTietNhapHang.getSoLuongNhap());
            pst.setInt(5, chiTietNhapHang.getGiaNhap());

            int rowsAffected = pst.executeUpdate();
            System.out.println("Số dòng ảnh hưởng khi insert chi tiết nhập hàng: " + rowsAffected);
            if (rowsAffected == 0) {
                throw new SQLException("Không thể thêm chi tiết nhập hàng: " + chiTietNhapHang.getIdChiTietNhapHang());
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi insert chi tiết nhập hàng: " + e.getMessage());
            throw e;
        }
    }

    public void update(ChiTietNhapHangDTO chiTietNhapHang) throws SQLException {
        String sql = "UPDATE chi_tiet_nhap_hang SET id_nhap_hang=?, id_san_pham=?, so_luong_nhap=?, gia_nhap=? WHERE id_chi_tiet_nhap_hang=?";
        System.out.println("Thực hiện update chi tiết nhập hàng: " + chiTietNhapHang.getIdChiTietNhapHang());

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, chiTietNhapHang.getIdNhapHang());
            pst.setString(2, chiTietNhapHang.getIdSanPham());
            pst.setInt(3, chiTietNhapHang.getSoLuongNhap());
            pst.setInt(4, chiTietNhapHang.getGiaNhap());
            pst.setString(5, chiTietNhapHang.getIdChiTietNhapHang());

            int rowsAffected = pst.executeUpdate();
            System.out.println("Số dòng ảnh hưởng khi update chi tiết nhập hàng: " + rowsAffected);
            if (rowsAffected == 0) {
                throw new SQLException("Không thể cập nhật chi tiết nhập hàng: " + chiTietNhapHang.getIdChiTietNhapHang());
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi update chi tiết nhập hàng: " + e.getMessage());
            throw e;
        }
    }

    public void delete(String idChiTietNhapHang) throws SQLException {
        String sql = "DELETE FROM chi_tiet_nhap_hang WHERE id_chi_tiet_nhap_hang=?";
        System.out.println("Thực hiện delete chi tiết nhập hàng: " + idChiTietNhapHang);

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, idChiTietNhapHang);
            int rowsAffected = pst.executeUpdate();
            System.out.println("Số dòng ảnh hưởng khi delete chi tiết nhập hàng: " + rowsAffected);
            if (rowsAffected == 0) {
                throw new SQLException("Không thể xóa chi tiết nhập hàng: " + idChiTietNhapHang);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi delete chi tiết nhập hàng: " + e.getMessage());
            throw e;
        }
    }
}