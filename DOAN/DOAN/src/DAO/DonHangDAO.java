package DAO;

import DTO.DonHangDTO;
import BLL.Session;
import config.JDBC;
import java.sql.*;
import java.util.ArrayList;

public class DonHangDAO {

    // Lấy tất cả đơn hàng
    public ArrayList<DonHangDTO> selectAll() {
        ArrayList<DonHangDTO> ds = new ArrayList<>();
        String sql = "SELECT * FROM don_hang";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                DonHangDTO donHang = new DonHangDTO(
                    rs.getString("id_don_hang"),
                    rs.getString("id_khach_hang"),
                    rs.getString("id_nhan_vien"),
                    rs.getInt("tong_tien"),
                    rs.getDate("ngay_dat_hang"),
                    rs.getString("trang_thai"),
                    rs.getString("hinh_thuc_mua_hang"),
                    rs.getString("dia_diem_giao")
                );
                ds.add(donHang);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
    
    public String insert(DonHangDTO donHang) {
        String sql = "INSERT INTO don_hang (id_don_hang, id_khach_hang, id_nhan_vien, tong_tien, ngay_dat_hang, trang_thai, hinh_thuc_mua_hang, dia_diem_giao) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        // Lấy id_khach_hang từ session
        String idKhachHang = Session.getIdKhachHang();
        if (idKhachHang == null) {
            System.out.println("Khách hàng chưa đăng nhập.");
            return null; 
        }
    
        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, donHang.getIdDonHang());
            pst.setString(2, donHang.getIdKhachHang());
            pst.setString(3, donHang.getIdNhanVien());
            pst.setInt(4, donHang.getTongTien());
            pst.setDate(5, new java.sql.Date(donHang.getNgayDatHang().getTime()));
            pst.setString(6, donHang.getTrangThai());
            pst.setString(7, donHang.getHinhThucMuaHang());
            pst.setString(8, donHang.getDiaDiemGiao());

            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                return donHang.getIdDonHang(); // Trả lại ID đã tạo
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Nếu không có ID đơn hàng hoặc có lỗi
    }

    // Cập nhật thông tin đơn hàng
    public boolean update(DonHangDTO donHang) {
        String sql = "UPDATE don_hang SET id_khach_hang=?, id_nhan_vien=?, tong_tien=?, ngay_dat_hang=?, trang_thai=?, hinh_thuc_mua_hang=?, dia_diem_giao=? WHERE id_don_hang=?";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, donHang.getIdKhachHang());
            pst.setString(2, donHang.getIdNhanVien());
            pst.setInt(3, donHang.getTongTien());
            pst.setDate(4, new java.sql.Date(donHang.getNgayDatHang().getTime()));
            pst.setString(5, donHang.getTrangThai());
            pst.setString(6, donHang.getHinhThucMuaHang());
            pst.setString(7, donHang.getDiaDiemGiao());
            pst.setString(8, donHang.getIdDonHang());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa đơn hàng theo ID
    public boolean delete(String idDonHang) {
        String sql = "DELETE FROM don_hang WHERE id_don_hang=?";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, idDonHang);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy thông tin đơn hàng theo ID
    public DonHangDTO selectById(String idDonHang) {
        String sql = "SELECT * FROM don_hang WHERE id_don_hang=?";
        DonHangDTO donHang = null;

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, idDonHang);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                donHang = new DonHangDTO(
                    rs.getString("id_don_hang"),
                    rs.getString("id_khach_hang"),
                    rs.getString("id_nhan_vien"),
                    rs.getInt("tong_tien"),
                    rs.getDate("ngay_dat_hang"),
                    rs.getString("trang_thai"),
                    rs.getString("hinh_thuc_mua_hang"),
                    rs.getString("dia_diem_giao")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return donHang;
    }

    // Tạo mã đơn hàng tiếp theo
    public String generateNextId() {
        String sql = "SELECT MAX(id_don_hang) AS max_id FROM don_hang";
        try (Connection conn = JDBC.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql); 
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                String maxId = rs.getString("max_id");
                if (maxId != null) {
                    int number = Integer.parseInt(maxId.substring(2)); // Lấy phần số sau "DH"
                    return String.format("DH%03d", number + 1); // Tạo ID tiếp theo
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "DH001"; // Trường hợp chưa có đơn hàng nào
    }
}