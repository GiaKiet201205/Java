package DAO;

import DTO.KhachHangDTO;
import config.JDBC;
import java.sql.*;
import java.util.ArrayList;

public class KhachHangDAO {

    // Lấy tất cả khách hàng
    public ArrayList<KhachHangDTO> selectAll() {
        ArrayList<KhachHangDTO> ds = new ArrayList<>();
        String sql = "SELECT * FROM khach_hang";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                KhachHangDTO khachHang = new KhachHangDTO(
                    rs.getString("id_khach_hang"),
                    rs.getString("ho_ten"),
                    rs.getString("email"),
                    rs.getString("sdt")
                );
                ds.add(khachHang);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    // Thêm khách hàng mới
    public boolean insert(KhachHangDTO khachHang) {
        String sql = "INSERT INTO khach_hang (id_khach_hang, ho_ten, email, sdt) VALUES (?, ?, ?, ?)";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, khachHang.getIdKhachHang());
            pst.setString(2, khachHang.getHoTen());
            pst.setString(3, khachHang.getEmail());
            pst.setString(4, khachHang.getSdt());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật thông tin khách hàng
    public boolean update(KhachHangDTO khachHang) {
        String sql = "UPDATE khach_hang SET ho_ten=?, email=?, sdt=? WHERE id_khach_hang=?";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, khachHang.getHoTen());
            pst.setString(2, khachHang.getEmail());
            pst.setString(3, khachHang.getSdt());
            pst.setString(4, khachHang.getIdKhachHang());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa khách hàng theo ID
    public boolean delete(String idKhachHang) {
        String sql = "DELETE FROM khach_hang WHERE id_khach_hang=?";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, idKhachHang);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy thông tin khách hàng theo ID
    public KhachHangDTO selectById(String idKhachHang) {
        String sql = "SELECT * FROM khach_hang WHERE id_khach_hang=?";
        KhachHangDTO khachHang = null;

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, idKhachHang);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                khachHang = new KhachHangDTO(
                    rs.getString("id_khach_hang"),
                    rs.getString("ho_ten"),
                    rs.getString("email"),
                    rs.getString("sdt")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return khachHang;
    }
}
