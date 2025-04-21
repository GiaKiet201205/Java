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
                    rs.getDouble("tong_gia_tri_nhap")
                );
                ds.add(nh);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi lấy tất cả nhập hàng: " + e.getMessage());
            e.printStackTrace();
        }
        return ds;
    }

    public NhapHangDTO getById(String idNhapHang) {
        String sql = "SELECT * FROM nhap_hang WHERE id_nhap_hang = ?";
        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, idNhapHang);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new NhapHangDTO(
                        rs.getString("id_nhap_hang"),
                        rs.getString("id_nha_cung_cap"),
                        rs.getString("id_nhan_vien"),
                        rs.getDate("ngay_nhap"),
                        rs.getDouble("tong_gia_tri_nhap")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi lấy nhập hàng theo id_nhap_hang: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void insert(NhapHangDTO nh) throws SQLException {
        String sql = "INSERT INTO nhap_hang VALUES (?, ?, ?, ?, ?)";
        System.out.println("Thực hiện insert nhập hàng: " + nh.getIdNhapHang());

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, nh.getIdNhapHang());
            pst.setString(2, nh.getIdNhaCungCap());
            pst.setString(3, nh.getIdNhanVien());
            pst.setDate(4, new java.sql.Date(nh.getNgayNhap().getTime()));
            pst.setDouble(5, nh.getTongGiaTriNhap());

            int rowsAffected = pst.executeUpdate();
            System.out.println("Số dòng ảnh hưởng khi insert nhập hàng: " + rowsAffected);
            if (rowsAffected == 0) {
                throw new SQLException("Không thể thêm nhập hàng: " + nh.getIdNhapHang());
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi insert nhập hàng: " + e.getMessage());
            throw e;
        }
    }

    public void update(NhapHangDTO nh) throws SQLException {
        String sql = "UPDATE nhap_hang SET id_nha_cung_cap=?, id_nhan_vien=?, ngay_nhap=?, tong_gia_tri_nhap=? WHERE id_nhap_hang=?";
        System.out.println("Thực hiện update nhập hàng: " + nh.getIdNhapHang());

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, nh.getIdNhaCungCap());
            pst.setString(2, nh.getIdNhanVien());
            pst.setDate(3, new java.sql.Date(nh.getNgayNhap().getTime()));
            pst.setDouble(4, nh.getTongGiaTriNhap());
            pst.setString(5, nh.getIdNhapHang());

            int rowsAffected = pst.executeUpdate();
            System.out.println("Số dòng ảnh hưởng khi update nhập hàng: " + rowsAffected);
            if (rowsAffected == 0) {
                throw new SQLException("Không thể cập nhật nhập hàng: " + nh.getIdNhapHang());
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi update nhập hàng: " + e.getMessage());
            throw e;
        }
    }

    public void delete(String idNhapHang) throws SQLException {
        String sql = "DELETE FROM nhap_hang WHERE id_nhap_hang=?";
        System.out.println("Thực hiện delete nhập hàng: " + idNhapHang);

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, idNhapHang);
            int rowsAffected = pst.executeUpdate();
            System.out.println("Số dòng ảnh hưởng khi delete nhập hàng: " + rowsAffected);
            if (rowsAffected == 0) {
                throw new SQLException("Không thể xóa nhập hàng: " + idNhapHang);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi delete nhập hàng: " + e.getMessage());
            throw e;
        }
    }
}