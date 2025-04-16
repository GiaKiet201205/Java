package DTO;

public class KhachHangDTO {
    private String idKhachHang;

    private String hoTen;
    private String email;
    private String sdt;

    // Constructor rỗng
    public KhachHangDTO() {}

    // Constructor đầy đủ
    public KhachHangDTO(String idKhachHang, String hoTen, String email, String sdt) {
        this.idKhachHang = idKhachHang;

        this.hoTen = hoTen;
        this.email = email;
        this.sdt = sdt;
    }

    // Getter và Setter
    public String getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(String idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
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
}
