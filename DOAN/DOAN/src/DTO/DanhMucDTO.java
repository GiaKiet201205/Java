package DTO;

public class DanhMucDTO {
    private String idDanhMuc;
    private String tenDanhMuc;

    // Constructor rỗng
    public DanhMucDTO() {}

    // Constructor đầy đủ
    public DanhMucDTO(String idDanhMuc, String tenDanhMuc) {
        this.idDanhMuc = idDanhMuc;
        this.tenDanhMuc = tenDanhMuc;
    }

    // Getter & Setter
    public String getIdDanhMuc() {
        return idDanhMuc;
    }

    public void setIdDanhMuc(String idDanhMuc) {
        this.idDanhMuc = idDanhMuc;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }
}
