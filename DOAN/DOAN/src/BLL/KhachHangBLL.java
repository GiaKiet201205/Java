package BLL;

import DAO.KhachHangDAO;
import DTO.KhachHangDTO;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Lớp KhachHangBLL xử lý logic nghiệp vụ cho khách hàng.
 * Đảm bảo kiểm tra dữ liệu và gọi các phương thức từ KhachHangDAO.
 */
public class KhachHangBLL {
    private KhachHangDAO dao;

    public KhachHangBLL() {
        dao = new KhachHangDAO();
    }

    /**
     * Lấy danh sách tất cả khách hàng từ cơ sở dữ liệu.
     * @return Danh sách KhachHangDTO
     */
    public ArrayList<KhachHangDTO> getAllKhachHang() {
        return dao.selectAll();
    }

    /**
     * Thêm một khách hàng mới sau khi kiểm tra dữ liệu.
     * @param kh KhachHangDTO chứa thông tin khách hàng
     * @return Thông báo kết quả
     */
    public String addKhachHang(KhachHangDTO kh) {
        // Kiểm tra dữ liệu đầu vào (không kiểm tra ID vì sẽ tự động sinh)
        String validationResult = validateKhachHang(kh, false);
        if (!validationResult.equals("Hợp lệ")) {
            return validationResult;
        }

        // Thêm khách hàng vào cơ sở dữ liệu
        if (dao.insert(kh)) {
            return "Thêm khách hàng thành công!";
        } else {
            return "Thêm khách hàng thất bại!";
        }
    }

    /**
     * Cập nhật thông tin khách hàng sau khi kiểm tra dữ liệu.
     * @param kh KhachHangDTO chứa thông tin cập nhật
     * @return Thông báo kết quả
     */
    public String updateKhachHang(KhachHangDTO kh) {
        // Kiểm tra dữ liệu đầu vào (bao gồm ID)
        String validationResult = validateKhachHang(kh, true);
        if (!validationResult.equals("Hợp lệ")) {
            return validationResult;
        }

        // Kiểm tra khách hàng có tồn tại
        if (!dao.isCustomerExist(kh.getIdKhachHang())) {
            return "Khách hàng không tồn tại!";
        }

        // Cập nhật khách hàng trong cơ sở dữ liệu
        if (dao.update(kh)) {
            return "Cập nhật khách hàng thành công!";
        } else {
            return "Cập nhật khách hàng thất bại!";
        }
    }

    /**
     * Xóa khách hàng (chỉ trên giao diện, không xóa trong cơ sở dữ liệu).
     * @param idKhachHang ID của khách hàng cần xóa
     * @return Thông báo kết quả
     */
    public String deleteKhachHang(String idKhachHang) {
        // Kiểm tra ID hợp lệ
        if (idKhachHang == null || idKhachHang.trim().isEmpty()) {
            return "ID khách hàng không hợp lệ!";
        }

        // Gọi phương thức delete từ DAO (được thiết kế để không xóa trong cơ sở dữ liệu)
        if (dao.delete(idKhachHang)) {
            return "Xóa khách hàng thành công!";
        } else {
            return "Xóa khách hàng thất bại!";
        }
    }

    public ArrayList<KhachHangDTO> searchKhachHang(String type, String keyword) {
        ArrayList<KhachHangDTO> result = new ArrayList<>();

        if (keyword == null || keyword.trim().isEmpty()) {
            return result; // Trả về danh sách rỗng nếu từ khóa không hợp lệ
        }

        if (type.equals("ID khách hàng")) {
            KhachHangDTO kh = dao.selectById(keyword.trim());
            if (kh != null) {
                result.add(kh);
            }
        } else if (type.equals("Tên khách hàng") || type.equals("Email") || type.equals("SĐT")) {
            result = dao.selectByKeyword(keyword.trim());
        }

        return result;
    }
    
    private String validateKhachHang(KhachHangDTO kh, boolean checkId) {
        // Kiểm tra ID (chỉ khi cập nhật)
        if (checkId && (kh.getIdKhachHang() == null || kh.getIdKhachHang().trim().isEmpty())) {
            return "ID khách hàng không được để trống!";
        }

        // Kiểm tra các trường không được rỗng
        if (kh.getHoTen() == null || kh.getHoTen().trim().isEmpty()) {
            return "Họ tên không được để trống!";
        }
        if (kh.getEmail() == null || kh.getEmail().trim().isEmpty()) {
            return "Email không được để trống!";
        }
        if (kh.getSdt() == null || kh.getSdt().trim().isEmpty()) {
            return "Số điện thoại không được để trống!";
        }

        // Kiểm tra định dạng email
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!Pattern.matches(emailRegex, kh.getEmail())) {
            return "Email không đúng định dạng!";
        }

        // Kiểm tra định dạng số điện thoại (chỉ chứa số, 10-11 chữ số)
        String sdtRegex = "^[0-9]{10,11}$";
        if (!Pattern.matches(sdtRegex, kh.getSdt())) {
            return "Số điện thoại không hợp lệ! Phải có 10-11 chữ số.";
        }

        return "Hợp lệ";
    }
}