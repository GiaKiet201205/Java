package DTO;

public class ChiTietNhapHangDTO {
    private String idChiTietNhapHang;
    private String idNhapHang;
    private String idSanPham;
    private int soLuongNhap;
    private int giaNhap;
    
    public ChiTietNhapHangDTO(){
       }
    public ChiTietNhapHangDTO(String idChiTietNhapHang, String idNhapHang, String idSanPham, int soLuongNhap, int giaNhap ){
        this.idChiTietNhapHang = idChiTietNhapHang;
        this.idNhapHang = idNhapHang;
        this.idSanPham = idSanPham;
        this.soLuongNhap = soLuongNhap;
        this.giaNhap = giaNhap;
    }
    
    public String getIdChiTietNhapHang(){
        return idChiTietNhapHang;
    }
    public void setIdChiTietNhapHang(String idChiTietNhapHang ){
        this.idChiTietNhapHang = idChiTietNhapHang;
    }
    public String getIdNhapHang(){
        return idNhapHang;
    }
    public void setIdNhapHang(String idNhapHang){
        this.idNhapHang = idNhapHang;
    }
    public String getIdSanPham(){
        return idSanPham;
    }
    public void setIdSanPham(String idSanPham){
        this.idSanPham = idSanPham;
    } 
    public int getSoLuongNhap(){
        return soLuongNhap;
    }
    public void setSoLuongNhap(int soLuongNhap){
        this.soLuongNhap = soLuongNhap;
    }
    public int getGiaNhap(){
        return giaNhap;
    }
    public void setGiaNhap(int giaNhap){
        this.giaNhap = giaNhap;
    }
}