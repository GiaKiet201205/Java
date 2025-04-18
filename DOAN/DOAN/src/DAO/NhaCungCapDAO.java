package DAO;

import DTO.NhaCungCapDTO;
import config.JDBC;
import java.sql.*;
import java.util.ArrayList;

public class NhaCungCapDAO {

    // Lấy tất cả nhà cung cấp
    public ArrayList<NhaCungCapDTO> selectAll() {
        ArrayList<NhaCungCapDTO> ds = new ArrayList<>();
        String sql = "SELECT * FROM nha_cung_cap";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                NhaCungCapDTO nhaCungCap = new NhaCungCapDTO(
                    rs.getString("id_nha_cung_cap"),
                    rs.getString("ten_nha_cung_cap"),
                    rs.getString("dia_chi"),
                    rs.getString("sdt")
                );
                ds.add(nhaCungCap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    // Thêm nhà cung cấp mới
    public boolean insert(NhaCungCapDTO nhaCungCap) {
        String sql = "INSERT INTO nha_cung_cap (id_nha_cung_cap, ten_nha_cung_cap, dia_chi, sdt) VALUES (?, ?, ?, ?)";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, nhaCungCap.getIdNhaCungCap());
            pst.setString(2, nhaCungCap.getTenNhaCungCap());
            pst.setString(3, nhaCungCap.getDiaChi());
            pst.setString(4, nhaCungCap.getSdt());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật thông tin nhà cung cấp
    public boolean update(NhaCungCapDTO nhaCungCap) {
        String sql = "UPDATE nha_cung_cap SET ten_nha_cung_cap=?, dia_chi=?, sdt=? WHERE id_nha_cung_cap=?";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, nhaCungCap.getTenNhaCungCap());
            pst.setString(2, nhaCungCap.getDiaChi());
            pst.setString(3, nhaCungCap.getSdt());
            pst.setString(4, nhaCungCap.getIdNhaCungCap());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa nhà cung cấp theo ID
    public boolean delete(String idNhaCungCap) {
        String sql = "DELETE FROM nha_cung_cap WHERE id_nha_cung_cap=?";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, idNhaCungCap);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy thông tin nhà cung cấp theo ID
    public NhaCungCapDTO selectById(String idNhaCungCap) {
        String sql = "SELECT * FROM nha_cung_cap WHERE id_nha_cung_cap=?";
        NhaCungCapDTO nhaCungCap = null;

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, idNhaCungCap);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                nhaCungCap = new NhaCungCapDTO(
                    rs.getString("id_nha_cung_cap"),
                    rs.getString("ten_nha_cung_cap"),
                    rs.getString("dia_chi"),
                    rs.getString("sdt")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nhaCungCap;
    }
}
