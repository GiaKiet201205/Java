package GUI.panel;

import DAO.ChiTietNhapHangDAO;
import DTO.ChiTietNhapHangDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChiTietNhapHangPanel extends JPanel {
    private JTable table;
    private JButton btnAdd, btnEdit, btnDelete;
    private ChiTietNhapHangDAO chiTietNhapHangDAO;

    public ChiTietNhapHangPanel() {
        chiTietNhapHangDAO = new ChiTietNhapHangDAO();
        setLayout(new BorderLayout());

        // Bảng chi tiết nhập hàng
        String[] columns = {"ID", "Mã Nhập Hàng", "Mã Sản Phẩm", "Số Lượng Nhập", "Giá Nhập"};
        table = new JTable();
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        // Panel chứa nút
        JPanel panelButtons = new JPanel();
        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        panelButtons.add(btnAdd);
        panelButtons.add(btnEdit);
        panelButtons.add(btnDelete);
        add(panelButtons, BorderLayout.SOUTH);
        
        // Thêm sự kiện cho các nút
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý thêm chi tiết nhập hàng
                addChiTietNhapHang();
            }
        });
        
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý sửa chi tiết nhập hàng
                editChiTietNhapHang();
            }
        });
        
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý xóa chi tiết nhập hàng
                deleteChiTietNhapHang();
            }
        });
        
        // Hiển thị dữ liệu chi tiết nhập hàng
        loadData();
    }

    // Phương thức để tải dữ liệu vào bảng
    private void loadData() {
        ArrayList<ChiTietNhapHangDTO> chiTietNhapHangList = chiTietNhapHangDAO.selectAll();
        String[][] data = new String[chiTietNhapHangList.size()][5];
        
        for (int i = 0; i < chiTietNhapHangList.size(); i++) {
            ChiTietNhapHangDTO item = chiTietNhapHangList.get(i);
            data[i][0] = item.getIdChiTietNhapHang();
            data[i][1] = item.getIdNhapHang();
            data[i][2] = item.getIdSanPham();
            data[i][3] = String.valueOf(item.getSoLuongNhap());
            data[i][4] = String.valueOf(item.getGiaNhap());
        }
        
        table.setModel(new javax.swing.table.DefaultTableModel(data, new String[]{"ID", "Mã Nhập Hàng", "Mã Sản Phẩm", "Số Lượng Nhập", "Giá Nhập"}));
    }

    // Phương thức thêm chi tiết nhập hàng
    private void addChiTietNhapHang() {
        String idChiTietNhapHang = JOptionPane.showInputDialog(this, "Nhập ID chi tiết nhập hàng:");
        String idNhapHang = JOptionPane.showInputDialog(this, "Nhập mã nhập hàng:");
        String idSanPham = JOptionPane.showInputDialog(this, "Nhập mã sản phẩm:");
        int soLuongNhap = Integer.parseInt(JOptionPane.showInputDialog(this, "Nhập số lượng nhập:"));
        int giaNhap = Integer.parseInt(JOptionPane.showInputDialog(this, "Nhập giá nhập:"));

        ChiTietNhapHangDTO newChiTiet = new ChiTietNhapHangDTO(idChiTietNhapHang, idNhapHang, idSanPham, soLuongNhap, giaNhap);
        if (chiTietNhapHangDAO.insert(newChiTiet)) {
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
            loadData(); // Tải lại dữ liệu sau khi thêm
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại!");
        }
    }

    // Phương thức sửa chi tiết nhập hàng
    private void editChiTietNhapHang() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String idChiTietNhapHang = table.getValueAt(selectedRow, 0).toString();
            String idNhapHang = table.getValueAt(selectedRow, 1).toString();
            String idSanPham = table.getValueAt(selectedRow, 2).toString();
            int soLuongNhap = Integer.parseInt(table.getValueAt(selectedRow, 3).toString());
            int giaNhap = Integer.parseInt(table.getValueAt(selectedRow, 4).toString());

            ChiTietNhapHangDTO chiTiet = new ChiTietNhapHangDTO(idChiTietNhapHang, idNhapHang, idSanPham, soLuongNhap, giaNhap);
            if (chiTietNhapHangDAO.update(chiTiet)) {
                JOptionPane.showMessageDialog(this, "Sửa thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Sửa thất bại!");
            }
        }
    }

    // Phương thức xóa chi tiết nhập hàng
    private void deleteChiTietNhapHang() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String idChiTietNhapHang = table.getValueAt(selectedRow, 0).toString();
            if (chiTietNhapHangDAO.delete(idChiTietNhapHang)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        }
    }
}
