package DTO;

public class ChiTietDonHangDTO {
    private String idChiTietDonHang;
    private String idDonHang;
    private String idSanPham;
    private int soLuong;
    private int giaBan;

    // Constructor rỗng
    public ChiTietDonHangDTO() {}

    // Constructor đầy đủ
    public ChiTietDonHangDTO(String idChiTietDonHang, String idDonHang, String idSanPham, int soLuong, int giaBan) {
        this.idChiTietDonHang = idChiTietDonHang;
        this.idDonHang = idDonHang;
        this.idSanPham = idSanPham;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
    }

    // Getter & Setter
    public String getIdChiTietDonHang() {
        return idChiTietDonHang;
    }

    public void setIdChiTietDonHang(String idChiTietDonHang) {
        this.idChiTietDonHang = idChiTietDonHang;
    }

    public String getIdDonHang() {
        return idDonHang;
    }

    public void setIdDonHang(String idDonHang) {
        this.idDonHang = idDonHang;
    }

    public String getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(String idSanPham) {
        this.idSanPham = idSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(int giaBan) {
        this.giaBan = giaBan;
    }
}
