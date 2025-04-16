/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

public class PhanQuyenDTO {
    private int idPhanQuyen;
    private String tenQuyen;

    public PhanQuyenDTO() {
    }

    public PhanQuyenDTO(int idPhanQuyen, String tenQuyen) {
        this.idPhanQuyen = idPhanQuyen;
        this.tenQuyen = tenQuyen;
    }

    public int getIdPhanQuyen() {
        return idPhanQuyen;
    }

    public void setIdPhanQuyen(int idPhanQuyen) {
        this.idPhanQuyen = idPhanQuyen;
    }

    public String getTenQuyen() {
        return tenQuyen;
    }

    public void setTenQuyen(String tenQuyen) {
        this.tenQuyen = tenQuyen;
    }

}