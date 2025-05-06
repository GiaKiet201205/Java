package GUI.panel;

import DAO.NhanVienDAO;
import DTO.NhanVienDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class NhanVienPanel extends JPanel {
    private JTable nhanVienTable;
    private DefaultTableModel nhanVienTableModel;
    private NhanVienDAO nhanVienDAO;

    public NhanVienPanel() {
        setLayout(new BorderLayout());

        nhanVienDAO = new NhanVienDAO();  // Tạo đối tượng NhanVienDAO để kết nối và lấy dữ liệu

        // Create the top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(200, 255, 200));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add "Chức năng" label
        JLabel lblChucNang = new JLabel("Chức năng");
        topPanel.add(lblChucNang, BorderLayout.WEST);

        // Add function buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setOpaque(false);

        // Add, Delete, Edit buttons
        JButton btnAdd = new JButton("Thêm");
        btnAdd.setToolTipText("Thêm");
        buttonPanel.add(btnAdd);

        JButton btnDelete = new JButton("Xóa");
        btnDelete.setToolTipText("Xóa");
        buttonPanel.add(btnDelete);

        JButton btnEdit = new JButton("Sửa");
        btnEdit.setToolTipText("Sửa");
        buttonPanel.add(btnEdit);

        // Add labels below buttons
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

        // Add search panel to the right
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setOpaque(false);

        JLabel lblTimKiem = new JLabel("Tìm kiếm");
        searchPanel.add(lblTimKiem);

        // Create combo box with search options
        JComboBox<String> cmbSearchType = new JComboBox<>(new String[]{"ID nhân viên", "Tên nhân viên", "Chức vụ"});
        cmbSearchType.setPreferredSize(new Dimension(120, 25));
        searchPanel.add(cmbSearchType);

        // Search text field
        JTextField txtSearchNV = new JTextField();
        txtSearchNV.setPreferredSize(new Dimension(200, 25));
        searchPanel.add(txtSearchNV);

        // Search button
        JButton btnSearch = new JButton("Tìm kiếm");
        searchPanel.add(btnSearch);

        // Reset button
        JButton btnReset = new JButton("Làm mới");
        searchPanel.add(btnReset);

        topPanel.add(searchPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // Create table for staff data
        String[] columns = {"ID Nhân viên", "Tên", "Email", "SĐT", "Chức vụ", "Lương"};
        nhanVienTableModel = new DefaultTableModel(columns, 0);
        nhanVienTable = new JTable(nhanVienTableModel);
        nhanVienTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        nhanVienTable.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(nhanVienTable);
        add(scrollPane, BorderLayout.CENTER);

        // Load data from the database when the panel is initialized
        loadNhanVienData();

        // Add action listeners
        btnAdd.addActionListener(e -> addNhanVien());
        btnDelete.addActionListener(e -> deleteNhanVien(nhanVienTable.getSelectedRow()));
        btnEdit.addActionListener(e -> editNhanVien(nhanVienTable.getSelectedRow()));
        btnSearch.addActionListener(e -> searchNhanVien(cmbSearchType.getSelectedItem().toString(), txtSearchNV.getText()));
        btnReset.addActionListener(e -> {
            txtSearchNV.setText("");
            nhanVienTableModel.setRowCount(0);
            loadNhanVienData(); // Reload data after reset
        });
    }

    // Method to load all employees from the database into the table
    private void loadNhanVienData() {
        ArrayList<NhanVienDTO> ds = nhanVienDAO.selectAll();
        for (NhanVienDTO nv : ds) {
            Object[] row = {
                nv.getIdNhanVien(),
                nv.getHoTenNV(),
                nv.getEmail(),
                nv.getSdt(),
                nv.getchucVu(),
                nv.getLuong()
            };
            nhanVienTableModel.addRow(row);
        }
    }

    private void searchNhanVien(String searchType, String keyword) {
        if (keyword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "Đang tìm kiếm nhân viên theo " + searchType + " với từ khóa: " + keyword, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void addNhanVien() {
        JTextField txtID = new JTextField();
        JTextField txtTen = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtSDT = new JTextField();
        JTextField txtChucVu = new JTextField();
        JTextField txtLuong = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("ID Nhân viên:"));
        panel.add(txtID);
        panel.add(new JLabel("Tên nhân viên:"));
        panel.add(txtTen);
        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);
        panel.add(new JLabel("SĐT:"));
        panel.add(txtSDT);
        panel.add(new JLabel("Chức vụ:"));
        panel.add(txtChucVu);
        panel.add(new JLabel("Lương:"));
        panel.add(txtLuong);

        int result = JOptionPane.showConfirmDialog(this, panel, "Thêm nhân viên mới", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            Object[] newRow = {
                txtID.getText(), txtTen.getText(), txtEmail.getText(),
                txtSDT.getText(), txtChucVu.getText(), txtLuong.getText()
            };
            nhanVienTableModel.addRow(newRow);
        }
    }

    private void deleteNhanVien(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên để xóa", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        nhanVienTableModel.removeRow(selectedRow);
    }

    private void editNhanVien(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên để sửa", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JTextField txtID = new JTextField(nhanVienTableModel.getValueAt(selectedRow, 0).toString());
        JTextField txtTen = new JTextField(nhanVienTableModel.getValueAt(selectedRow, 1).toString());
        JTextField txtEmail = new JTextField(nhanVienTableModel.getValueAt(selectedRow, 2).toString());
        JTextField txtSDT = new JTextField(nhanVienTableModel.getValueAt(selectedRow, 3).toString());
        JTextField txtChucVu = new JTextField(nhanVienTableModel.getValueAt(selectedRow, 4).toString());
        JTextField txtLuong = new JTextField(nhanVienTableModel.getValueAt(selectedRow, 5).toString());

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("ID Nhân viên:"));
        panel.add(txtID);
        panel.add(new JLabel("Tên nhân viên:"));
        panel.add(txtTen);
        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);
        panel.add(new JLabel("SĐT:"));
        panel.add(txtSDT);
        panel.add(new JLabel("Chức vụ:"));
        panel.add(txtChucVu);
        panel.add(new JLabel("Lương:"));
        panel.add(txtLuong);

        int result = JOptionPane.showConfirmDialog(this, panel, "Sửa nhân viên", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            nhanVienTableModel.setValueAt(txtID.getText(), selectedRow, 0);
            nhanVienTableModel.setValueAt(txtTen.getText(), selectedRow, 1);
            nhanVienTableModel.setValueAt(txtEmail.getText(), selectedRow, 2);
            nhanVienTableModel.setValueAt(txtSDT.getText(), selectedRow, 3);
            nhanVienTableModel.setValueAt(txtChucVu.getText(), selectedRow, 4);
            nhanVienTableModel.setValueAt(txtLuong.getText(), selectedRow, 5);
        }
    }
}
