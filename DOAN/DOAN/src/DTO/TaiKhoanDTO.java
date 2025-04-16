/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

public class TaiKhoanDTO {
    private String idTaiKhoan;
    private String idKhachHang;
    private String tenUser;
    private String password;
    private String phanQuyen;
    private String trangThai;

    public TaiKhoanDTO() {
    }
    
        public TaiKhoanDTO(String idTaiKhoan, String idKhachHang, String tenUser, String password, String phanQuyen, String trangThai) {
        this.idTaiKhoan = idTaiKhoan;
        this.idKhachHang = idKhachHang;
        this.tenUser = tenUser;
        this.password = password;
        this.phanQuyen = phanQuyen;
        this.trangThai = trangThai;
    }
        
    public String getIdTaiKhoan() {
        return idTaiKhoan;
    }

    public void setIdTaiKhoan(String idTaiKhoan) {
        this.idTaiKhoan = idTaiKhoan;
    }

    public String getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(String idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public String getTenUser() {
        return tenUser;
    }

    public void setTenUser(String tenUser) {
        this.tenUser = tenUser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhanQuyen() {
        return phanQuyen;
    }

    public void setPhanQuyen(String phanQuyen) {
        this.phanQuyen = phanQuyen;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    

}
