package DAO;

import DTO.NhapHangDTO;
import config.JDBC;
import java.sql.*;
import java.util.ArrayList;

public class NhapHangDAO {

    public ArrayList<NhapHangDTO> selectAll() {
        ArrayList<NhapHangDTO> ds = new ArrayList<>();
        String sql = "SELECT * FROM nhap_hang";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                NhapHangDTO nh = new NhapHangDTO(
                    rs.getString("id_nhap_hang"),
                    rs.getString("id_nha_cung_cap"),
                    rs.getString("id_nhan_vien"),
                    rs.getDate("ngay_nhap"),
                    rs.getInt("tong_gia_tri_nhap")
                );
                ds.add(nh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public boolean insert(NhapHangDTO nh) {
        String sql = "INSERT INTO nhap_hang VALUES (?, ?, ?, ?, ?)";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, nh.getIdNhapHang());
            pst.setString(2, nh.getIdNhaCungCap());
            pst.setString(3, nh.getIdNhanVien());
            pst.setDate(4, new java.sql.Date(nh.getNgayNhap().getTime())); // ép kiểu Date
            pst.setInt(5, nh.getTongGiaTriNhap());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(NhapHangDTO nh) {
        String sql = "UPDATE nhap_hang SET id_nha_cung_cap=?, id_nhan_vien=?, ngay_nhap=?, tong_gia_tri_nhap=? WHERE id_nhap_hang=?";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, nh.getIdNhaCungCap());
            pst.setString(2, nh.getIdNhanVien());
            pst.setDate(3, new java.sql.Date(nh.getNgayNhap().getTime()));
            pst.setInt(4, nh.getTongGiaTriNhap());
            pst.setString(5, nh.getIdNhapHang());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String idNhapHang) {
        String sql = "DELETE FROM nhap_hang WHERE id_nhap_hang=?";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, idNhapHang);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public NhapHangDTO selectById(String idNhapHang) {
        String sql = "SELECT * FROM nhap_hang WHERE id_nhap_hang=?";
        NhapHangDTO nh = null;

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, idNhapHang);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                nh = new NhapHangDTO(
                    rs.getString("id_nhap_hang"),
                    rs.getString("id_nha_cung_cap"),
                    rs.getString("id_nhan_vien"),
                    rs.getDate("ngay_nhap"),
                    rs.getInt("tong_gia_tri_nhap")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nh;
    }
}
