package DAO;

import DTO.SanPhamDTO;
import config.JDBC;
import java.sql.*;
import java.util.ArrayList;

public class SanPhamDAO {
    private Connection connection;

    public SanPhamDAO() {
        this.connection = JDBC.getConnection(); // Assuming JDBC.getConnection() provides the DB connection
    }

    // Lấy tất cả sản phẩm
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
    String checkQuery = "SELECT COUNT(*) FROM nha_cung_cap WHERE id_nha_cung_cap = ?";
    try (Connection con = JDBC.getConnection();
         PreparedStatement pstCheck = con.prepareStatement(checkQuery)) {

        pstCheck.setString(1, sp.getIdNhaCungCap());
        try (ResultSet rs = pstCheck.executeQuery()) {
            if (rs.next() && rs.getInt(1) == 0) {
                System.out.println("id_nha_cung_cap không tồn tại trong bảng nha_cung_cap.");
                return false;
            }
        }

        String sql = "INSERT INTO san_pham (id_san_pham, id_danh_muc, id_nha_cung_cap, hinh_anh, gia, ten_san_pham, so_luong_ton_kho) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, sp.getIdSanPham());
            pst.setString(2, sp.getIdDanhMuc());
            pst.setString(3, sp.getIdNhaCungCap());
            pst.setString(4, sp.getHinhAnh());  // Lưu đường dẫn hình ảnh
            pst.setInt(5, sp.getGia());
            pst.setString(6, sp.getTenSanPham());
            pst.setInt(7, sp.getSoLuongTonKho());

            int rowsInserted = pst.executeUpdate();
            System.out.println("Rows inserted: " + rowsInserted);
            return rowsInserted > 0;
        }
    } catch (SQLException e) {
        System.out.println("Error when inserting: " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}
    // Cập nhật thông tin sản phẩm
    public boolean update(SanPhamDTO sp) {
        // Kiểm tra sự tồn tại của id_nha_cung_cap trước khi cập nhật
        String checkQuery = "SELECT COUNT(*) FROM nha_cung_cap WHERE id_nha_cung_cap = ?";
        try (Connection con = JDBC.getConnection();
             PreparedStatement pstCheck = con.prepareStatement(checkQuery)) {

            pstCheck.setString(1, sp.getIdNhaCungCap());
            try (ResultSet rs = pstCheck.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    System.out.println("id_nha_cung_cap không tồn tại trong bảng nha_cung_cap.");
                    return false;  // id_nha_cung_cap không tồn tại
                }
            }

            // Nếu id_nha_cung_cap tồn tại, tiến hành cập nhật thông tin sản phẩm
            String sql = "UPDATE san_pham SET id_danh_muc=?, id_nha_cung_cap=?, hinh_anh=?, gia=?, ten_san_pham=?, so_luong_ton_kho=? WHERE id_san_pham=?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, sp.getIdDanhMuc());
                pst.setString(2, sp.getIdNhaCungCap());
                pst.setString(3, sp.getHinhAnh());
                pst.setString(4, String.valueOf(sp.getGia()));
                pst.setString(5, sp.getTenSanPham());
                pst.setString(6, String.valueOf(sp.getSoLuongTonKho()));
                pst.setString(7, sp.getIdSanPham());

                return pst.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tìm kiếm sản phẩm theo tên
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
    public boolean deleteProductFromUI(String idSanPham) {
        return true;  
    }
}
