package DAO;

import DTO.PhuongThucThanhToanDTO;
import config.JDBC;
import java.sql.*;
import java.util.ArrayList;

public class PhuongThucThanhToanDAO {

    public ArrayList<PhuongThucThanhToanDTO> selectAll() {
        ArrayList<PhuongThucThanhToanDTO> ds = new ArrayList<>();
        String sql = "SELECT * FROM phuong_thuc_thanh_toan";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                PhuongThucThanhToanDTO pttt = new PhuongThucThanhToanDTO(
                    rs.getString("id_pttt"),
                    rs.getString("id_don_hang"),
                    rs.getString("loai_thanh_toan"),
                    rs.getString("trang_thai_thanh_toan")
                );
                ds.add(pttt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public boolean insert(PhuongThucThanhToanDTO pttt) {
        String sql = "INSERT INTO phuong_thuc_thanh_toan VALUES (?, ?, ?, ?)";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, pttt.getIdPttt());
            pst.setString(2, pttt.getIdDonHang());
            pst.setString(3, pttt.getLoaiThanhToan());
            pst.setString(4, pttt.getTrangThaiThanhToan());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(PhuongThucThanhToanDTO pttt) {
        String sql = "UPDATE phuong_thuc_thanh_toan SET id_don_hang=?, loai_thanh_toan=?, trang_thai_thanh_toan=? WHERE id_pttt=?";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, pttt.getIdDonHang());
            pst.setString(2, pttt.getLoaiThanhToan());
            pst.setString(3, pttt.getTrangThaiThanhToan());
            pst.setString(4, pttt.getIdPttt());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String idPttt) {
        String sql = "DELETE FROM phuong_thuc_thanh_toan WHERE id_pttt=?";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, idPttt);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public PhuongThucThanhToanDTO selectByDonHang(String idDonHang) {
        String sql = "SELECT * FROM phuong_thuc_thanh_toan WHERE id_don_hang=?";
        PhuongThucThanhToanDTO pttt = null;

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, idDonHang);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    pttt = new PhuongThucThanhToanDTO(
                        rs.getString("id_pttt"),
                        rs.getString("id_don_hang"),
                        rs.getString("loai_thanh_toan"),
                        rs.getString("trang_thai_thanh_toan")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pttt;
    }
}
