package DTO;

public class NhaCungCapDTO {
    private String idNhaCungCap;
    private String tenNhaCungCap;
    private String diaChi;
    private String sdt;

    // Constructor rỗng
    public NhaCungCapDTO() {}

    // Constructor đầy đủ
    public NhaCungCapDTO(String idNhaCungCap, String tenNhaCungCap, String diaChi, String sdt) {
        this.idNhaCungCap = idNhaCungCap;
        this.tenNhaCungCap = tenNhaCungCap;
        this.diaChi = diaChi;
        this.sdt = sdt;
    }

    // Getter & Setter
    public String getIdNhaCungCap() {
        return idNhaCungCap;
    }

    public void setIdNhaCungCap(String idNhaCungCap) {
        this.idNhaCungCap = idNhaCungCap;
    }

    public String getTenNhaCungCap() {
        return tenNhaCungCap;
    }

    public void setTenNhaCungCap(String tenNhaCungCap) {
        this.tenNhaCungCap = tenNhaCungCap;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
