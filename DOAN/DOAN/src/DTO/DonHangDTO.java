package DTO;

import java.util.Date;

public class DonHangDTO {
    private String idDonHang;
    private String idKhachHang;
    private String idNhanVien;
    private int tongTien;
    private Date ngayDatHang;
    private String trangThai;
    private String hinhThucMuaHang;
    private String diaDiemGiao;

    public DonHangDTO() {}
    public DonHangDTO(String idDonHang, String idKhachHang, String idNhanVien, int tongTien, Date ngayDatHang,
                      String trangThai, String hinhThucMuaHang, String diaDiemGiao) {
        this.idDonHang = idDonHang;
        this.idKhachHang = idKhachHang;
        this.idNhanVien = idNhanVien;
        this.tongTien = tongTien;
        this.ngayDatHang = ngayDatHang;
        this.trangThai = trangThai;
        this.hinhThucMuaHang = hinhThucMuaHang;
        this.diaDiemGiao = diaDiemGiao;
    }

    // Getter & Setter
    public String getIdDonHang() {
        return idDonHang;
    }

    public void setIdDonHang(String idDonHang) {
        this.idDonHang = idDonHang;
    }

    public String getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(String idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public String getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(String idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public Date getNgayDatHang() {
        return ngayDatHang;
    }

    public void setNgayDatHang(Date ngayDatHang) {
        this.ngayDatHang = ngayDatHang;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getHinhThucMuaHang() {
        return hinhThucMuaHang;
    }

    public void setHinhThucMuaHang(String hinhThucMuaHang) {
        this.hinhThucMuaHang = hinhThucMuaHang;
    }

    public String getDiaDiemGiao() {
        return diaDiemGiao;
    }

    public void setDiaDiemGiao(String diaDiemGiao) {
        this.diaDiemGiao = diaDiemGiao;
    }
}
