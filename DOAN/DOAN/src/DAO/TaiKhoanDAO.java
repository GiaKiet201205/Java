/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import DTO.TaiKhoanDTO;
import java.util.ArrayList;
import config.JDBC;
import java.sql.*;

/**
 *
 * @author nguye
 */
public class TaiKhoanDAO {
    public ArrayList<TaiKhoanDTO> selectAll(){
        ArrayList<TaiKhoanDTO> ds = new ArrayList<>();
        String sql = "SELECT * FROM tai_khoan";
        
        try(Connection con = JDBC.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery()){
            
            while(rs.next()){
                TaiKhoanDTO tk = new TaiKhoanDTO();
                tk.setIdTaiKhoan(rs.getString("id_tai_khoan"));
                tk.setIdKhachHang(rs.getString("id_khach_hang"));
                tk.setTenUser(rs.getString("ten_user"));
                tk.setPassword(rs.getString("password"));
                tk.setPhanQuyen(rs.getString("phan_quyen"));
                tk.setTrangThai(rs.getString("trang_thai"));
                ds.add(tk);
            }    
        } catch (SQLException e){
            e.printStackTrace();
        }
        return ds;
    }
    
    public boolean insert(TaiKhoanDTO tk){
        String sql = "INSERT INTO tai_khoan VALUES (?, ?, ?, ?, ?, ?)";
        
        try(Connection con = JDBC.getConnection();
            PreparedStatement pst = con.prepareStatement(sql)){
            
            pst.setString(1, tk.getIdTaiKhoan());
            pst.setString(2, tk.getIdKhachHang());
            pst.setString(3, tk.getTenUser());
            pst.setString(4, tk.getPassword());
            pst.setString(5, tk.getPhanQuyen());
            pst.setString(6, tk.getTrangThai());
            
            return pst.executeUpdate() > 0;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean update(TaiKhoanDTO tk) {
        String sql = "UPDATE tai_khoan SET id_khach_hang=?, ten_user=?, password=?, phan_quyen=?, trang_thai=? WHERE id_tai_khoan=?";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, tk.getIdKhachHang());
            pst.setString(2, tk.getTenUser());
            pst.setString(3, tk.getPassword());
            pst.setString(4, tk.getPhanQuyen());
            pst.setString(5, tk.getTrangThai());
            pst.setString(6, tk.getIdTaiKhoan());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String idTaiKhoan) {
        String sql = "DELETE FROM tai_khoan WHERE id_tai_khoan=?";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, idTaiKhoan);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public TaiKhoanDTO login(String tenUser, String password) {
        String sql = "SELECT * FROM tai_khoan WHERE ten_user=? AND password=?";
        TaiKhoanDTO tk = null;

        try (Connection con = JDBC.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, tenUser);
            pst.setString(2, password);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    tk = new TaiKhoanDTO(
                        rs.getString("id_tai_khoan"),
                        rs.getString("id_khach_hang"),
                        rs.getString("ten_user"),
                        rs.getString("password"),
                        rs.getString("phan_quyen"),
                        rs.getString("trang_thai")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tk;
    }

    public boolean isUsernameExists(String username) {
    String sql = "SELECT COUNT(*) FROM tai_khoan WHERE ten_user = ?";
    
    try (Connection con = JDBC.getConnection();
         PreparedStatement pst = con.prepareStatement(sql)) {
        
        pst.setString(1, username); // Gán giá trị của tên người dùng vào câu truy vấn
        
        try (ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0; // Nếu có ít nhất một bản ghi, trả về true
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return false; // Trả về false nếu không có tên người dùng trong cơ sở dữ liệu
}

}
