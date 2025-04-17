package DTO;

public class NhanVienDTO {
    private String idNhanVien;
    private String hoTenNV;
    private String email;
    private String sdt;
    private String chucVu;
    private int luong;

    // Constructor rỗng
    public NhanVienDTO() {}

    // Constructor đầy đủ
    public NhanVienDTO(String idNhanVien, String hoTenNV, String email, String sdt, String chucVu , int luong) {
        this.idNhanVien = idNhanVien;
        this.hoTenNV = hoTenNV;
        this.email = email;
        this.sdt = sdt;
        this.chucVu = chucVu;
        this.luong = luong;
    }

    // Getter và Setter
    public String getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(String idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public String getHoTenNV() {
        return hoTenNV;
    }

    public void setHoTenNV(String hoTenNV) {
        this.hoTenNV = hoTenNV;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getchucVu() {
        return chucVu;
    }

    public void setchucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public int getLuong() {
        return luong;
    }

    public void setLuong(int luong) {
        this.luong = luong;
    }
}

