package DAO;

import DTO.ChiTietDonHangDTO;
import config.JDBC;
import java.sql.*;
import java.util.ArrayList;

public class ChiTietDonHangDAO {

    // Phương thức selectAll: Lấy tất cả chi tiết đơn hàng
    public ArrayList<ChiTietDonHangDTO> selectAll() {
        ArrayList<ChiTietDonHangDTO> chiTietDonHangList = new ArrayList<>();
        String sql = "SELECT * FROM chi_tiet_don_hang";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                ChiTietDonHangDTO chiTietDonHang = new ChiTietDonHangDTO(
                    rs.getString("id_chi_tiet_don_hang"),
                    rs.getString("id_don_hang"),
                    rs.getString("id_san_pham"),
                    rs.getInt("so_luong"),
                    rs.getInt("gia_ban")
                );
                chiTietDonHangList.add(chiTietDonHang);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chiTietDonHangList;
    }
    
    public boolean insert(ChiTietDonHangDTO chiTietDonHang) {
        String sql = "INSERT INTO chi_tiet_don_hang (id_chi_tiet_don_hang, id_don_hang, id_san_pham, so_luong, gia_ban) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, chiTietDonHang.getIdChiTietDonHang());
            pst.setString(2, chiTietDonHang.getIdDonHang());
            pst.setString(3, chiTietDonHang.getIdSanPham());
            pst.setInt(4, chiTietDonHang.getSoLuong());
            pst.setInt(5, chiTietDonHang.getGiaBan());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Phương thức update: Cập nhật chi tiết đơn hàng
    public boolean update(ChiTietDonHangDTO chiTietDonHang) {
        String sql = "UPDATE chi_tiet_don_hang SET id_don_hang=?, id_san_pham=?, so_luong=?, gia_ban=? WHERE id_chi_tiet_don_hang=?";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, chiTietDonHang.getIdDonHang());
            pst.setString(2, chiTietDonHang.getIdSanPham());
            pst.setInt(3, chiTietDonHang.getSoLuong());
            pst.setInt(4, chiTietDonHang.getGiaBan());
            pst.setString(5, chiTietDonHang.getIdChiTietDonHang());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Phương thức delete: Xóa chi tiết đơn hàng theo id
    public boolean delete(String idChiTietDonHang) {
        String sql = "DELETE FROM chi_tiet_don_hang WHERE id_chi_tiet_don_hang=?";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, idChiTietDonHang);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Phương thức selectById: Lấy thông tin chi tiết đơn hàng theo id
    public ChiTietDonHangDTO selectById(String idChiTietDonHang) {
        String sql = "SELECT * FROM chi_tiet_don_hang WHERE id_chi_tiet_don_hang=?";
        ChiTietDonHangDTO chiTietDonHang = null;

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, idChiTietDonHang);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                chiTietDonHang = new ChiTietDonHangDTO(
                    rs.getString("id_chi_tiet_don_hang"),
                    rs.getString("id_don_hang"),
                    rs.getString("id_san_pham"),
                    rs.getInt("so_luong"),
                    rs.getInt("gia_ban")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chiTietDonHang;
    }
}
