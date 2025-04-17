/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

public class PhuongThucThanhToanDTO {
    private String idPttt;
    private String idDonHang;
    private String loaiThanhToan;
    private String trangThaiThanhToan;

    public PhuongThucThanhToanDTO() {
    }

    public PhuongThucThanhToanDTO (String idPttt, String idDonHang, String loaiThanhToan, String trangThaiThanhToan) {
        this.idPttt = idPttt;
        this.idDonHang = idDonHang;
        this.loaiThanhToan = loaiThanhToan;
        this.trangThaiThanhToan = trangThaiThanhToan;
    }

    public String getIdPttt() {
        return idPttt;
    }

    public void setIdPttt(String idPttt) {
        this.idPttt = idPttt;
    }

    public String getIdDonHang() {
        return idDonHang;
    }

    public void setIdDonHang(String idDonHang) {
        this.idDonHang = idDonHang;
    }

    public String getLoaiThanhToan() {
        return loaiThanhToan;
    }

    public void setLoaiThanhToan(String loaiThanhToan) {
        this.loaiThanhToan = loaiThanhToan;
    }

    public String getTrangThaiThanhToan() {
        return trangThaiThanhToan;
    }

    public void setTrangThaiThanhToan(String trangThaiThanhToan) {
        this.trangThaiThanhToan = trangThaiThanhToan;
    }

}
