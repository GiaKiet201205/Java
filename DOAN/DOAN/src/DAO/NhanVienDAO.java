package DAO;

import DTO.NhanVienDTO;
import config.JDBC;
import java.sql.*;
import java.util.ArrayList;

public class NhanVienDAO {

    public ArrayList<NhanVienDTO> selectAll() {
        ArrayList<NhanVienDTO> ds = new ArrayList<>();
        String sql = "SELECT * FROM nhan_vien";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                NhanVienDTO nv = new NhanVienDTO(
                    rs.getString("id_nhan_vien"),
                    rs.getString("ho_ten_nv"),
                    rs.getString("email"),
                    rs.getString("sdt"),
                    rs.getString("chuc_vu"),
                    rs.getInt("luong")
                );
                ds.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    // Phương thức tạo mã nhân viên tự động
    public String generateNextId() {
        String nextId = null;
        String sql = "SELECT MAX(CAST(SUBSTRING(id_nhan_vien, 3) AS UNSIGNED)) FROM nhan_vien";  // Hoặc bảng bạn sử dụng

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                int nextNumber = rs.getInt(1) + 1;  // Lấy số tiếp theo
                nextId = "NV" + String.format("%03d", nextNumber);  // Tạo mã nhân viên mới với 3 chữ số
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nextId;
    }

    public boolean insert(NhanVienDTO nv) {
        String sql = "INSERT INTO nhan_vien VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, nv.getIdNhanVien());  // Sử dụng mã nhân viên được tạo
            pst.setString(2, nv.getHoTenNV());
            pst.setString(3, nv.getEmail());
            pst.setString(4, nv.getSdt());
            pst.setString(5, nv.getchucVu());
            pst.setInt(6, nv.getLuong());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(NhanVienDTO nv) {
        String sql = "UPDATE nhan_vien SET ho_ten_nv=?, email=?, sdt=?, chuc_vu=?, luong=? WHERE id_nhan_vien=?";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, nv.getHoTenNV());
            pst.setString(2, nv.getEmail());
            pst.setString(3, nv.getSdt());
            pst.setString(4, nv.getchucVu());
            pst.setInt(5, nv.getLuong());
            pst.setString(6, nv.getIdNhanVien());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String idNhanVien) {
        String sql = "DELETE FROM nhan_vien WHERE id_nhan_vien=?";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, idNhanVien);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public NhanVienDTO selectById(String idNhanVien) {
        String sql = "SELECT * FROM nhan_vien WHERE id_nhan_vien=?";
        NhanVienDTO nv = null;

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, idNhanVien);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                nv = new NhanVienDTO(
                    rs.getString("id_nhan_vien"),
                    rs.getString("ho_ten_nv"),
                    rs.getString("email"),
                    rs.getString("sdt"),
                    rs.getString("chuc_vu"),
                    rs.getInt("luong")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nv;
    }
}
