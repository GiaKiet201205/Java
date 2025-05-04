package BLL;

import DAO.DanhMucDAO;
import DTO.DanhMucDTO;
import GUI.NguoiDungGUI;
import GUI.TrangChuGUI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DanhMucBLL {
    private DanhMucDAO danhMucDAO;
    private Set<TrangChuGUI> trangChuGUIs;
    private Set<NguoiDungGUI> nguoiDungGUIs;

    public DanhMucBLL() {
        danhMucDAO = new DanhMucDAO();
        trangChuGUIs = new HashSet<>();
        nguoiDungGUIs = new HashSet<>();
    }

    // Đăng ký GUI để nhận thông báo cập nhật
    public void registerTrangChuGUI(TrangChuGUI gui) {
        trangChuGUIs.add(gui);
    }

    public void registerNguoiDungGUI(NguoiDungGUI gui) {
        nguoiDungGUIs.add(gui);
    }

    // Lấy danh sách danh mục
    public ArrayList<DanhMucDTO> getAllDanhMuc() {
        return danhMucDAO.selectAll();
    }

    // Thêm danh mục
    public boolean addDanhMuc(DanhMucDTO danhMuc) {
        boolean success = danhMucDAO.insert(danhMuc);
        if (success) {
            notifyGUIs();
        }
        return success;
    }

    // Sửa danh mục
    public boolean updateDanhMuc(DanhMucDTO danhMuc) {
        boolean success = danhMucDAO.update(danhMuc);
        if (success) {
            notifyGUIs();
        }
        return success;
    }

    // Xóa danh mục
    public boolean deleteDanhMuc(String idDanhMuc) {
        boolean success = danhMucDAO.delete(idDanhMuc);
        if (success) {
            notifyGUIs();
        }
        return success;
    }

    // Thông báo cập nhật danh mục cho các GUI
    private void notifyGUIs() {
        for (TrangChuGUI gui : trangChuGUIs) {
            gui.updateCategoryComboBox();
        }
        for (NguoiDungGUI gui : nguoiDungGUIs) {
            gui.updateCategoryComboBox();
        }
    }
}
