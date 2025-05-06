package GUI.panel;

import BLL.DanhMucBLL;
import DTO.DanhMucDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class DanhMucPanel extends JPanel {
    private JTable danhMucTable;
    private DefaultTableModel danhMucTableModel;
    private DanhMucBLL danhMucBLL;

    public DanhMucPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 400));
        danhMucBLL = new DanhMucBLL();
        taoGiaoDienDanhMuc();
    }

    private void taoGiaoDienDanhMuc() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(200, 255, 200));
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel lblChucNang = new JLabel("Chức năng");
        topPanel.add(lblChucNang, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setOpaque(false);
        JButton btnAdd = new JButton("Thêm");
        JButton btnDelete = new JButton("Xóa");
        JButton btnEdit = new JButton("Sửa");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnEdit);

        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labelPanel.setOpaque(false);
        labelPanel.add(new JLabel("Thêm"));
        labelPanel.add(Box.createHorizontalStrut(20));
        labelPanel.add(new JLabel("Xóa"));
        labelPanel.add(Box.createHorizontalStrut(20));
        labelPanel.add(new JLabel("Sửa"));

        JPanel functionPanel = new JPanel(new BorderLayout());
        functionPanel.setOpaque(false);
        functionPanel.add(buttonPanel, BorderLayout.NORTH);
        functionPanel.add(labelPanel, BorderLayout.SOUTH);
        topPanel.add(functionPanel, BorderLayout.CENTER);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setOpaque(false);
        JLabel lblSearch = new JLabel("Tìm kiếm");
        JComboBox<String> cmbSearchType = new JComboBox<>(new String[]{"ID danh mục", "Tên danh mục"});
        JTextField txtSearch = new JTextField();
        JButton btnSearch = new JButton("Tìm kiếm");
        JButton btnReset = new JButton("Làm mới");
        cmbSearchType.setPreferredSize(new Dimension(120, 25));
        txtSearch.setPreferredSize(new Dimension(200, 25));
        searchPanel.add(lblSearch);
        searchPanel.add(cmbSearchType);
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnReset);
        topPanel.add(searchPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        danhMucTableModel = new DefaultTableModel(new String[]{"ID danh mục", "Tên danh mục"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        danhMucTable = new JTable(danhMucTableModel);
        danhMucTable.setRowHeight(25);
        danhMucTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < danhMucTable.getColumnCount(); i++) {
            danhMucTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        add(new JScrollPane(danhMucTable), BorderLayout.CENTER);

        taiDanhSachDanhMuc();

        btnAdd.addActionListener(e -> themDanhMuc());
        btnEdit.addActionListener(e -> suaDanhMuc(danhMucTable.getSelectedRow()));
        btnDelete.addActionListener(e -> deleteDanhMuc(danhMucTable.getSelectedRow()));
        btnSearch.addActionListener(e -> searchDanhMuc(cmbSearchType.getSelectedItem().toString(), txtSearch.getText()));
        btnReset.addActionListener(e -> {
            txtSearch.setText("");
            taiDanhSachDanhMuc();
        });

        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchDanhMuc(cmbSearchType.getSelectedItem().toString(), txtSearch.getText());
                }
            }
        });
    }

    private void taiDanhSachDanhMuc() {
        danhMucTableModel.setRowCount(0);
        ArrayList<DanhMucDTO> list = danhMucBLL.getAllDanhMuc();
        for (DanhMucDTO dm : list) {
            danhMucTableModel.addRow(new Object[]{dm.getIdDanhMuc(), dm.getTenDanhMuc()});
        }
    }

    private void themDanhMuc() {
        JTextField txtID = new JTextField();
        JTextField txtTen = new JTextField();
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("ID Danh mục:"));
        panel.add(txtID);
        panel.add(new JLabel("Tên Danh mục:"));
        panel.add(txtTen);

        int result = JOptionPane.showConfirmDialog(this, panel, "Thêm danh mục mới", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            DanhMucDTO dm = new DanhMucDTO(txtID.getText(), txtTen.getText());
            if (danhMucBLL.addDanhMuc(dm)) {
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                taiDanhSachDanhMuc();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại! Kiểm tra ID danh mục hoặc kết nối cơ sở dữ liệu.");
            }
        }
    }

    private void suaDanhMuc(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một danh mục để sửa");
            return;
        }

        JTextField txtID = new JTextField(danhMucTableModel.getValueAt(selectedRow, 0).toString());
        JTextField txtTen = new JTextField(danhMucTableModel.getValueAt(selectedRow, 1).toString());
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("ID Danh mục:"));
        panel.add(txtID);
        panel.add(new JLabel("Tên Danh mục:"));
        panel.add(txtTen);

        int result = JOptionPane.showConfirmDialog(this, panel, "Sửa danh mục", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            DanhMucDTO dm = new DanhMucDTO(txtID.getText(), txtTen.getText());
            if (danhMucBLL.updateDanhMuc(dm)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                taiDanhSachDanhMuc();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại! Kiểm tra ID danh mục hoặc kết nối cơ sở dữ liệu.");
            }
        }
    }

    private void deleteDanhMuc(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một danh mục để xóa");
            return;
        }

        String id = danhMucTableModel.getValueAt(selectedRow, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa danh mục " + id + "?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (danhMucBLL.deleteDanhMuc(id)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                taiDanhSachDanhMuc();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại! Có thể danh mục đang được sử dụng hoặc lỗi kết nối.");
            }
        }
    }

    private void searchDanhMuc(String type, String keyword) {
        if (keyword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm");
            return;
        }
        danhMucTableModel.setRowCount(0);
        ArrayList<DanhMucDTO> list = danhMucBLL.getAllDanhMuc();
        for (DanhMucDTO dm : list) {
            if ((type.equals("ID danh mục") && dm.getIdDanhMuc().toLowerCase().contains(keyword.toLowerCase())) ||
                (type.equals("Tên danh mục") && dm.getTenDanhMuc().toLowerCase().contains(keyword.toLowerCase()))) {
                danhMucTableModel.addRow(new Object[]{dm.getIdDanhMuc(), dm.getTenDanhMuc()});
            }
        }
        if (danhMucTableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy danh mục phù hợp");
        }
    }
}