package BLL;

import DTO.TaiKhoanDTO;

public class Session {
    private static TaiKhoanDTO currentTaiKhoan;

    public static void setCurrentTaiKhoan(TaiKhoanDTO taiKhoan) {
        currentTaiKhoan = taiKhoan;
    }

    public static TaiKhoanDTO getCurrentTaiKhoan() {
        return currentTaiKhoan;
    }

    public static String getIdKhachHang() {
        if (currentTaiKhoan != null) {
            return currentTaiKhoan.getIdKhachHang(); // Lấy id_khach_hang
        }
        return null;
    }

    public static void clearSession() {
        currentTaiKhoan = null;
    }
}
