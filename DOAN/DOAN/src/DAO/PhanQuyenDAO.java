package DAO;

import DTO.PhanQuyenDTO;
import config.JDBC;
import java.sql.*;
import java.util.ArrayList;

public class PhanQuyenDAO {

    public ArrayList<PhanQuyenDTO> selectAll() {
        ArrayList<PhanQuyenDTO> ds = new ArrayList<>();
        String sql = "SELECT * FROM phan_quyen";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                PhanQuyenDTO pq = new PhanQuyenDTO(
                    rs.getInt("id_phan_quyen"),
                    rs.getString("ten_quyen")
                );
                ds.add(pq);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public boolean insert(PhanQuyenDTO pq) {
        String sql = "INSERT INTO phan_quyen (id_phan_quyen, ten_quyen) VALUES (?, ?)";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, pq.getIdPhanQuyen());
            pst.setString(2, pq.getTenQuyen());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(PhanQuyenDTO pq) {
        String sql = "UPDATE phan_quyen SET ten_quyen = ? WHERE id_phan_quyen = ?";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, pq.getTenQuyen());
            pst.setInt(2, pq.getIdPhanQuyen());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int idPhanQuyen) {
        String sql = "DELETE FROM phan_quyen WHERE id_phan_quyen = ?";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, idPhanQuyen);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public PhanQuyenDTO selectById(int idPhanQuyen) {
        String sql = "SELECT * FROM phan_quyen WHERE id_phan_quyen = ?";
        PhanQuyenDTO pq = null;

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, idPhanQuyen);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                pq = new PhanQuyenDTO(
                    rs.getInt("id_phan_quyen"),
                    rs.getString("ten_quyen")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pq;
    }
    
    public PhanQuyenDTO getPhanQuyenByTaiKhoan(String idTaiKhoan) {
        String sql = "SELECT pq.ten_quyen FROM tai_khoan tk " +
                     "JOIN phan_quyen pq ON tk.id_phan_quyen = pq.id_phan_quyen " +
                     "WHERE tk.id_tai_khoan = ?";
        PhanQuyenDTO phanQuyen = null;

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, idTaiKhoan);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    phanQuyen = new PhanQuyenDTO();
                    phanQuyen.setTenQuyen(rs.getString("ten_quyen"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return phanQuyen;
    }
}
