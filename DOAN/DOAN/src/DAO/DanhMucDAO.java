package DAO;

import DTO.DanhMucDTO;
import config.JDBC;
import java.sql.*;
import java.util.ArrayList;

public class DanhMucDAO {

    // Phương thức selectAll: Lấy danh sách tất cả danh mục
    public ArrayList<DanhMucDTO> selectAll() {
        ArrayList<DanhMucDTO> danhMucList = new ArrayList<>();
        String sql = "SELECT * FROM danh_muc";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                DanhMucDTO danhMuc = new DanhMucDTO(
                    rs.getString("id_danh_muc"),
                    rs.getString("ten_danh_muc")
                );
                danhMucList.add(danhMuc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhMucList;
    }

    // Phương thức insert: Thêm một danh mục mới
    public boolean insert(DanhMucDTO danhMuc) {
        String sql = "INSERT INTO danh_muc (id_danh_muc, ten_danh_muc) VALUES (?, ?)";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, danhMuc.getIdDanhMuc());
            pst.setString(2, danhMuc.getTenDanhMuc());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Phương thức update: Cập nhật thông tin danh mục
    public boolean update(DanhMucDTO danhMuc) {
        String sql = "UPDATE danh_muc SET ten_danh_muc=? WHERE id_danh_muc=?";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, danhMuc.getTenDanhMuc());
            pst.setString(2, danhMuc.getIdDanhMuc());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Phương thức delete: Xóa một danh mục theo id
    public boolean delete(String idDanhMuc) {
        String sql = "DELETE FROM danh_muc WHERE id_danh_muc=?";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, idDanhMuc);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Phương thức selectById: Lấy thông tin danh mục theo id
    public DanhMucDTO selectById(String idDanhMuc) {
        String sql = "SELECT * FROM danh_muc WHERE id_danh_muc=?";
        DanhMucDTO danhMuc = null;

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, idDanhMuc);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                danhMuc = new DanhMucDTO(
                    rs.getString("id_danh_muc"),
                    rs.getString("ten_danh_muc")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhMuc;
    }
}
