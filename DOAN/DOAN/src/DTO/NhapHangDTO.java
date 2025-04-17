/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.util.Date;

public class NhapHangDTO {
    private String idNhapHang;
    private String idNhaCungCap;
    private String idNhanVien;
    private Date ngayNhap;
    private int tongGiaTriNhap;

    public NhapHangDTO() {
    }

    public NhapHangDTO(String idNhapHang, String idNhaCungCap, String idNhanVien, Date ngayNhap, int tongGiaTriNhap) {
        this.idNhapHang = idNhapHang;
        this.idNhaCungCap = idNhaCungCap;
        this.idNhanVien = idNhanVien;
        this.ngayNhap = ngayNhap;
        this.tongGiaTriNhap = tongGiaTriNhap;
    }

    public String getIdNhapHang() {
        return idNhapHang;
    }

    public void setIdNhapHang(String idNhapHang) {
        this.idNhapHang = idNhapHang;
    }

    public String getIdNhaCungCap() {
        return idNhaCungCap;
    }

    public void setIdNhaCungCap(String idNhaCungCap) {
        this.idNhaCungCap = idNhaCungCap;
    }

    public String getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(String idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public int getTongGiaTriNhap() {
        return tongGiaTriNhap;
    }

    public void setTongGiaTriNhap(int tongGiaTriNhap) {
        this.tongGiaTriNhap = tongGiaTriNhap;
    }

}