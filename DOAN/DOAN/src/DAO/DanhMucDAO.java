package DAO;

import DTO.DanhMucDTO;
import config.JDBC;

import java.sql.*;
import java.util.ArrayList;

public class DanhMucDAO {

    // Lấy toàn bộ danh mục
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

    // Thêm danh mục mới
    public boolean insert(DanhMucDTO danhMuc) {
        String sql = "INSERT INTO danh_muc (id_danh_muc, ten_danh_muc) VALUES (?, ?)";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, danhMuc.getIdDanhMuc());
            pst.setString(2, danhMuc.getTenDanhMuc());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm danh mục: " + e.getMessage());
            return false;
        }
    }

    // Cập nhật thông tin danh mục
    public boolean update(DanhMucDTO danhMuc) {
        String sql = "UPDATE danh_muc SET ten_danh_muc = ? WHERE id_danh_muc = ?";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, danhMuc.getTenDanhMuc());
            pst.setString(2, danhMuc.getIdDanhMuc());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật danh mục: " + e.getMessage());
            return false;
        }
    }

    // Xóa danh mục
    public boolean delete(String idDanhMuc) {
        String sql = "DELETE FROM danh_muc WHERE id_danh_muc = ?";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, idDanhMuc);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa danh mục: " + e.getMessage());
            return false;
        }
    }

    // Tìm danh mục theo ID
    public DanhMucDTO selectById(String idDanhMuc) {
        String sql = "SELECT * FROM danh_muc WHERE id_danh_muc = ?";
        DanhMucDTO danhMuc = null;

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, idDanhMuc);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    danhMuc = new DanhMucDTO(
                        rs.getString("id_danh_muc"),
                        rs.getString("ten_danh_muc")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn danh mục theo ID: " + e.getMessage());
        }

        return danhMuc;
    }
}