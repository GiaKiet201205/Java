package BLL;

import DAO.NhapHangDAO;
import DTO.ChiTietNhapHangDTO;
import DTO.NhapHangDTO;
import config.JDBC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NhapHangBLL {
    private NhapHangDAO nhapHangDAO;
    private ChiTietNhapHangBLL chiTietNhapHangBLL;

    public NhapHangBLL() {
        nhapHangDAO = new NhapHangDAO();
        chiTietNhapHangBLL = new ChiTietNhapHangBLL();
    }

    public String generateNewNhapHangId() {
        List<NhapHangDTO> nhapHangList = nhapHangDAO.selectAll();
        int maxId = 0;
        for (NhapHangDTO nh : nhapHangList) {
            String id = nh.getIdNhapHang().replace("NH", "");
            try {
                int num = Integer.parseInt(id);
                if (num > maxId) maxId = num;
            } catch (NumberFormatException e) {
                System.err.println("Lỗi khi parse ID nhập hàng: " + id);
            }
        }
        String newId = String.format("NH%03d", maxId + 1);
        System.out.println("Tạo ID nhập hàng mới: " + newId);
        return newId;
    }

    public void addNhapHang(NhapHangDTO nhapHang, List<ChiTietNhapHangDTO> chiTietList) throws Exception {
        System.out.println("Bắt đầu thêm nhập hàng: " + nhapHang.getIdNhapHang());
        if (nhapHang.getIdNhaCungCap() == null || nhapHang.getIdNhaCungCap().isEmpty()) {
            throw new Exception("ID Nhà Cung Cấp không được để trống");
        }
        if (nhapHang.getIdNhanVien() == null || nhapHang.getIdNhanVien().isEmpty()) {
            throw new Exception("ID Nhân Viên không được để trống");
        }
        if (nhapHang.getTongGiaTriNhap() <= 0) {
            throw new Exception("Tổng giá trị nhập phải lớn hơn 0");
        }
        if (chiTietList == null || chiTietList.isEmpty()) {
            throw new Exception("Phải có ít nhất một sản phẩm trong phiếu nhập");
        }

        // Kiểm tra tồn tại id_nha_cung_cap, id_nhan_vien, id_san_pham
        Connection con = JDBC.getConnection();
        if (con == null) {
            throw new Exception("Không thể kết nối đến cơ sở dữ liệu");
        }
        try {
            // Kiểm tra id_nha_cung_cap
            PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM nha_cung_cap WHERE id_nha_cung_cap = ?");
            ps.setString(1, nhapHang.getIdNhaCungCap());
            ResultSet rs = ps.executeQuery();
            rs.next();
            if (rs.getInt(1) == 0) {
                throw new Exception("ID Nhà Cung Cấp không tồn tại");
            }

            // Kiểm tra id_nhan_vien
            ps = con.prepareStatement("SELECT COUNT(*) FROM nhan_vien WHERE id_nhan_vien = ?");
            ps.setString(1, nhapHang.getIdNhanVien());
            rs = ps.executeQuery();
            rs.next();
            if (rs.getInt(1) == 0) {
                throw new Exception("ID Nhân Viên không tồn tại");
            }

            // Kiểm tra id_san_pham
            for (ChiTietNhapHangDTO chiTiet : chiTietList) {
                ps = con.prepareStatement("SELECT COUNT(*) FROM san_pham WHERE id_san_pham = ?");
                ps.setString(1, chiTiet.getIdSanPham());
                rs = ps.executeQuery();
                rs.next();
                if (rs.getInt(1) == 0) {
                    throw new Exception("ID Sản Phẩm " + chiTiet.getIdSanPham() + " không tồn tại");
                }
            }

            nhapHang.setIdNhapHang(generateNewNhapHangId());
            for (ChiTietNhapHangDTO chiTiet : chiTietList) {
                chiTiet.setIdNhapHang(nhapHang.getIdNhapHang());
            }

            con.setAutoCommit(false);

            System.out.println("Thêm vào bảng nhap_hang: " + nhapHang.getIdNhapHang());
            nhapHangDAO.insert(nhapHang);

            for (ChiTietNhapHangDTO chiTiet : chiTietList) {
                System.out.println("Thêm chi tiết nhập hàng cho ID nhập hàng: " + chiTiet.getIdNhapHang());
                chiTietNhapHangBLL.addChiTietNhapHang(chiTiet);
            }

            con.commit();
            System.out.println("Thêm nhập hàng thành công: " + nhapHang.getIdNhapHang());
        } catch (SQLException e) {
            if (con != null) {
                try {
                    System.out.println("Rollback giao dịch do lỗi SQL: " + e.getMessage());
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw new Exception("Lỗi SQL: " + e.getMessage());
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void deleteNhapHang(String idNhapHang) throws Exception {
        System.out.println("Bắt đầu xóa nhập hàng: " + idNhapHang);
        Connection con = null;
        try {
            con = JDBC.getConnection();
            if (con == null) {
                throw new Exception("Không thể kết nối đến cơ sở dữ liệu");
            }
            con.setAutoCommit(false);

            chiTietNhapHangBLL.deleteChiTietNhapHangByIdNhapHang(idNhapHang);

            System.out.println("Xóa bảng nhap_hang: " + idNhapHang);
            nhapHangDAO.delete(idNhapHang);

            con.commit();
            System.out.println("Xóa nhập hàng thành công: " + idNhapHang);
        } catch (SQLException e) {
            if (con != null) {
                try {
                    System.out.println("Rollback giao dịch do lỗi SQL: " + e.getMessage());
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw new Exception("Lỗi SQL: " + e.getMessage());
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void updateNhapHang(NhapHangDTO nhapHang, ChiTietNhapHangDTO chiTiet) throws Exception {
        System.out.println("Bắt đầu cập nhật nhập hàng: " + nhapHang.getIdNhapHang());
        if (nhapHang.getIdNhaCungCap() == null || nhapHang.getIdNhaCungCap().isEmpty()) {
            throw new Exception("ID Nhà Cung Cấp không được để trống");
        }
        if (nhapHang.getIdNhanVien() == null || nhapHang.getIdNhanVien().isEmpty()) {
            throw new Exception("ID Nhân Viên không được để trống");
        }
        if (nhapHang.getTongGiaTriNhap() <= 0) {
            throw new Exception("Tổng giá trị nhập phải lớn hơn 0");
        }

        Connection con = null;
        try {
            con = JDBC.getConnection();
            if (con == null) {
                throw new Exception("Không thể kết nối đến cơ sở dữ liệu");
            }
            con.setAutoCommit(false);

            System.out.println("Cập nhật bảng nhap_hang: " + nhapHang.getIdNhapHang());
            nhapHangDAO.update(nhapHang);

            System.out.println("Cập nhật chi tiết nhập hàng: " + chiTiet.getIdChiTietNhapHang());
            chiTietNhapHangBLL.updateChiTietNhapHang(chiTiet);

            con.commit();
            System.out.println("Cập nhật nhập hàng thành công: " + nhapHang.getIdNhapHang());
        } catch (SQLException e) {
            if (con != null) {
                try {
                    System.out.println("Rollback giao dịch do lỗi SQL: " + e.getMessage());
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw new Exception("Lỗi SQL: " + e.getMessage());
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ArrayList<NhapHangDTO> getAllNhapHang() {
        return nhapHangDAO.selectAll();
    }

    public NhapHangDTO getNhapHangById(String idNhapHang) {
        return nhapHangDAO.getById(idNhapHang);
    }

    public ChiTietNhapHangDTO getChiTietNhapHangByIdNhapHang(String idNhapHang) {
        return chiTietNhapHangBLL.getChiTietNhapHangByIdNhapHang(idNhapHang);
    }
}