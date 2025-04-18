package DAO;

import DTO.SanPhamDTO;
import config.JDBC;
import java.sql.*;
import java.util.ArrayList;

public class SanPhamDAO {

    public ArrayList<SanPhamDTO> selectAll() {
        ArrayList<SanPhamDTO> ds = new ArrayList<>();
        String sql = "SELECT * FROM san_pham";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                SanPhamDTO sp = new SanPhamDTO();
                sp.setIdSanPham(rs.getString("id_san_pham"));
                sp.setIdDanhMuc(rs.getString("id_danh_muc"));
                sp.setIdNhaCungCap(rs.getString("id_nha_cung_cap"));
                sp.setHinhAnh(rs.getString("hinh_anh"));
                sp.setGia(Integer.parseInt(rs.getString("gia")));  // xử lý chuỗi -> số
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                sp.setSoLuongTonKho(Integer.parseInt(rs.getString("so_luong_ton_kho")));
                ds.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public boolean insert(SanPhamDTO sp) {
        String sql = "INSERT INTO san_pham VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, sp.getIdSanPham());
            pst.setString(2, sp.getIdDanhMuc());
            pst.setString(3, sp.getIdNhaCungCap());
            pst.setString(4, sp.getHinhAnh());
            pst.setString(5, String.valueOf(sp.getGia())); // lưu dạng chuỗi
            pst.setString(6, sp.getTenSanPham());
            pst.setString(7, String.valueOf(sp.getSoLuongTonKho()));

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(SanPhamDTO sp) {
        String sql = "UPDATE san_pham SET id_danh_muc=?, id_nha_cung_cap=?, hinh_anh=?, gia=?, ten_san_pham=?, so_luong_ton_kho=? WHERE id_san_pham=?";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, sp.getIdDanhMuc());
            pst.setString(2, sp.getIdNhaCungCap());
            pst.setString(3, sp.getHinhAnh());
            pst.setString(4, String.valueOf(sp.getGia()));
            pst.setString(5, sp.getTenSanPham());
            pst.setString(6, String.valueOf(sp.getSoLuongTonKho()));
            pst.setString(7, sp.getIdSanPham());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String idSanPham) {
        String sql = "DELETE FROM san_pham WHERE id_san_pham=?";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, idSanPham);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<SanPhamDTO> searchByName(String keyword) {
        ArrayList<SanPhamDTO> ds = new ArrayList<>();
        String sql = "SELECT * FROM san_pham WHERE ten_san_pham LIKE ?";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, "%" + keyword + "%");
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    SanPhamDTO sp = new SanPhamDTO();
                    sp.setIdSanPham(rs.getString("id_san_pham"));
                    sp.setIdDanhMuc(rs.getString("id_danh_muc"));
                    sp.setIdNhaCungCap(rs.getString("id_nha_cung_cap"));
                    sp.setHinhAnh(rs.getString("hinh_anh"));
                    sp.setGia(Integer.parseInt(rs.getString("gia")));
                    sp.setTenSanPham(rs.getString("ten_san_pham"));
                    sp.setSoLuongTonKho(Integer.parseInt(rs.getString("so_luong_ton_kho")));
                    ds.add(sp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
}

