/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

public class SanPhamDTO {
    private String idSanPham;
    private String idDanhMuc;
    private String idNhaCungCap;
    private String hinhAnh;
    private int gia;
    private String tenSanPham;
    private int soLuongTonKho;

    public SanPhamDTO() {
    }

    public SanPhamDTO(String idSanPham, String idDanhMuc, String idNhaCungCap, String hinhAnh, int gia, String tenSanPham, int soLuongTonKho) {
        this.idSanPham = idSanPham;
        this.idDanhMuc = idDanhMuc;
        this.idNhaCungCap = idNhaCungCap;
        this.hinhAnh = hinhAnh;
        this.gia = gia;
        this.tenSanPham = tenSanPham;
        this.soLuongTonKho = soLuongTonKho;
    }

    // Getter và Setter cho từng thuộc tính

    public String getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(String idSanPham) {
        this.idSanPham = idSanPham;
    }

    public String getIdDanhMuc() {
        return idDanhMuc;
    }

    public void setIdDanhMuc(String idDanhMuc) {
        this.idDanhMuc = idDanhMuc;
    }

    public String getIdNhaCungCap() {
        return idNhaCungCap;
    }

    public void setIdNhaCungCap(String idNhaCungCap) {
        this.idNhaCungCap = idNhaCungCap;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public int getSoLuongTonKho() {
        return soLuongTonKho;
    }

    public void setSoLuongTonKho(int soLuongTonKho) {
        this.soLuongTonKho = soLuongTonKho;
    }

}
