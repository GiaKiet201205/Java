package GUI.panel;

import DAO.NhanVienDAO;
import DTO.NhanVienDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class NhanVienPanel extends JPanel {
    private JTable nhanVienTable;
    private DefaultTableModel nhanVienTableModel;
    private NhanVienDAO nhanVienDAO;

    public NhanVienPanel() {
        setLayout(new BorderLayout());

        nhanVienDAO = new NhanVienDAO(); // Initialize NhanVienDAO for database operations

        // Create the top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(200, 255, 200));
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Title Label
        JLabel lblTitle = new JLabel("NHÂN VIÊN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(lblTitle, BorderLayout.NORTH);

        // Add "Chức năng" label
        JLabel lblChucNang = new JLabel("Chức năng");
        topPanel.add(lblChucNang, BorderLayout.WEST);

        // Add function buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setOpaque(false);

        // Add, Delete, Edit buttons
        JButton btnAdd = new JButton("Thêm");
        btnAdd.setToolTipText("Thêm nhân viên");
        btnAdd.setBackground(new Color(240, 240, 240));
        buttonPanel.add(btnAdd);

        JButton btnDelete = new JButton("Xóa");
        btnDelete.setToolTipText("Xóa nhân viên");
        btnDelete.setBackground(new Color(240, 240, 240));
        buttonPanel.add(btnDelete);

        JButton btnEdit = new JButton("Sửa");
        btnEdit.setToolTipText("Sửa nhân viên");
        btnEdit.setBackground(new Color(240, 240, 240));
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
        btnSearch.setBackground(new Color(240, 240, 240));
        searchPanel.add(btnSearch);

        // Reset button
        JButton btnReset = new JButton("Làm mới");
        btnReset.setBackground(new Color(240, 240, 240));
        searchPanel.add(btnReset);

        topPanel.add(searchPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // Create table for staff data
        String[] columns = {"ID Nhân viên", "Tên", "Email", "SĐT", "Chức vụ", "Lương"};
        nhanVienTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };
        nhanVienTable = new JTable(nhanVienTableModel);
        nhanVienTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        nhanVienTable.setRowHeight(25);

        // Center the text in cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < nhanVienTable.getColumnCount(); i++) {
            nhanVienTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

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
        nhanVienTableModel.setRowCount(0); // Clear existing rows
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

        nhanVienTableModel.setRowCount(0);
        ArrayList<NhanVienDTO> allData = nhanVienDAO.selectAll();

        for (NhanVienDTO nv : allData) {
            boolean match = false;
            switch (searchType) {
                case "ID nhân viên":
                    if (nv.getIdNhanVien().toLowerCase().contains(keyword.toLowerCase())) {
                        match = true;
                    }
                    break;
                case "Tên nhân viên":
                    if (nv.getHoTenNV().toLowerCase().contains(keyword.toLowerCase())) {
                        match = true;
                    }
                    break;
                case "Chức vụ":
                    if (nv.getchucVu().toLowerCase().contains(keyword.toLowerCase())) {
                        match = true;
                    }
                    break;
            }
            if (match) {
                nhanVienTableModel.addRow(new Object[]{
                    nv.getIdNhanVien(),
                    nv.getHoTenNV(),
                    nv.getEmail(),
                    nv.getSdt(),
                    nv.getchucVu(),
                    nv.getLuong()
                });
            }
        }

        if (nhanVienTableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên phù hợp", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void addNhanVien() {
        JDialog addDialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Thêm Nhân Viên", Dialog.ModalityType.APPLICATION_MODAL);
        addDialog.setSize(450, 500);
        addDialog.setLocationRelativeTo(this);
        addDialog.setLayout(new BorderLayout());
        addDialog.getContentPane().setBackground(new Color(230, 255, 230));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(new Color(230, 255, 230));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);

        JLabel lblIdNhanVien = new JLabel("ID Nhân Viên:");
        lblIdNhanVien.setFont(labelFont);
        JTextField txtIdNhanVien = new JTextField(20);
        txtIdNhanVien.setFont(fieldFont);

        JLabel lblHoTen = new JLabel("Tên Nhân Viên:");
        lblHoTen.setFont(labelFont);
        JTextField txtHoTen = new JTextField(20);
        txtHoTen.setFont(fieldFont);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(labelFont);
        JTextField txtEmail = new JTextField(20);
        txtEmail.setFont(fieldFont);

        JLabel lblSdt = new JLabel("SĐT:");
        lblSdt.setFont(labelFont);
        JTextField txtSdt = new JTextField(20);
        txtSdt.setFont(fieldFont);

        JLabel lblChucVu = new JLabel("Chức Vụ:");
        lblChucVu.setFont(labelFont);
        JTextField txtChucVu = new JTextField(20);
        txtChucVu.setFont(fieldFont);

        JLabel lblLuong = new JLabel("Lương:");
        lblLuong.setFont(labelFont);
        JTextField txtLuong = new JTextField(20);
        txtLuong.setFont(fieldFont);

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(lblIdNhanVien, gbc);
        gbc.gridx = 1; formPanel.add(txtIdNhanVien, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(lblHoTen, gbc);
        gbc.gridx = 1; formPanel.add(txtHoTen, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(lblEmail, gbc);
        gbc.gridx = 1; formPanel.add(txtEmail, gbc);
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(lblSdt, gbc);
        gbc.gridx = 1; formPanel.add(txtSdt, gbc);
        gbc.gridx = 0; gbc.gridy = 4; formPanel.add(lblChucVu, gbc);
        gbc.gridx = 1; formPanel.add(txtChucVu, gbc);
        gbc.gridx = 0; gbc.gridy = 5; formPanel.add(lblLuong, gbc);
        gbc.gridx = 1; formPanel.add(txtLuong, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(255, 255, 255));
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton btnAdd = new JButton("Thêm");
        btnAdd.setFont(new Font("Arial", Font.BOLD, 14));
        btnAdd.setForeground(Color.BLACK);
        btnAdd.setPreferredSize(new Dimension(100, 35));
        btnAdd.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JButton btnCancel = new JButton("Hủy");
        btnCancel.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancel.setForeground(Color.BLACK);
        btnCancel.setPreferredSize(new Dimension(100, 35));
        btnCancel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnCancel);

        addDialog.add(formPanel, BorderLayout.CENTER);
        addDialog.add(buttonPanel, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> {
            try {
                String idNhanVien = txtIdNhanVien.getText().trim();
                String hoTen = txtHoTen.getText().trim();
                String email = txtEmail.getText().trim();
                String sdt = txtSdt.getText().trim();
                String chucVu = txtChucVu.getText().trim();
                String luongStr = txtLuong.getText().trim();

                // Validation
                if (idNhanVien.isEmpty() || hoTen.isEmpty() || email.isEmpty() || sdt.isEmpty() || chucVu.isEmpty() || luongStr.isEmpty()) {
                    JOptionPane.showMessageDialog(addDialog, "Vui lòng điền đầy đủ thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!sdt.matches("\\d+")) {
                    JOptionPane.showMessageDialog(addDialog, "SĐT phải là số", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double luong;
                try {
                    luong = Double.parseDouble(luongStr);
                    if (luong <= 0) {
                        JOptionPane.showMessageDialog(addDialog, "Lương phải lớn hơn 0", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(addDialog, "Lương phải là số", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Create DTO and add to database
                NhanVienDTO nhanVien = new NhanVienDTO();
                nhanVien.setIdNhanVien(idNhanVien);
                nhanVien.setHoTenNV(hoTen);
                nhanVien.setEmail(email);
                nhanVien.setSdt(sdt);
                nhanVien.setchucVu(chucVu);
                nhanVien.setLuong(luong);

                if (nhanVienDAO.insert(nhanVien)) {
                    nhanVienTableModel.addRow(new Object[]{idNhanVien, hoTen, email, sdt, chucVu, luong});
                    JOptionPane.showMessageDialog(addDialog, "Thêm nhân viên thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    addDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(addDialog, "Thêm nhân viên thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(addDialog, "Lỗi khi thêm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener(e -> addDialog.dispose());

        addDialog.setVisible(true);
    }

    private void deleteNhanVien(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên để xóa", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idNhanVien = nhanVienTableModel.getValueAt(selectedRow, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa nhân viên: " + idNhanVien + "?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (nhanVienDAO.delete(idNhanVien)) {
                    nhanVienTableModel.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa nhân viên thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editNhanVien(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên để sửa", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idNhanVien = nhanVienTableModel.getValueAt(selectedRow, 0).toString();
        NhanVienDTO nhanVien = nhanVienDAO.selectById(idNhanVien);

        if (nhanVien == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu nhân viên", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JDialog editDialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Sửa Nhân Viên", Dialog.ModalityType.APPLICATION_MODAL);
        editDialog.setSize(450, 500);
        editDialog.setLocationRelativeTo(this);
        editDialog.setLayout(new BorderLayout());
        editDialog.getContentPane().setBackground(new Color(230, 255, 230));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(new Color(230, 255, 230));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);

        JLabel lblIdNhanVien = new JLabel("ID Nhân Viên:");
        lblIdNhanVien.setFont(labelFont);
        JTextField txtIdNhanVien = new JTextField(nhanVien.getIdNhanVien(), 20);
        txtIdNhanVien.setFont(fieldFont);
        txtIdNhanVien.setEditable(false);
        txtIdNhanVien.setBackground(new Color(220, 220, 220));

        JLabel lblHoTen = new JLabel("Tên Nhân Viên:");
        lblHoTen.setFont(labelFont);
        JTextField txtHoTen = new JTextField(nhanVien.getHoTenNV(), 20);
        txtHoTen.setFont(fieldFont);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(labelFont);
        JTextField txtEmail = new JTextField(nhanVien.getEmail(), 20);
        txtEmail.setFont(fieldFont);

        JLabel lblSdt = new JLabel("SĐT:");
        lblSdt.setFont(labelFont);
        JTextField txtSdt = new JTextField(nhanVien.getSdt(), 20);
        txtSdt.setFont(fieldFont);

        JLabel lblChucVu = new JLabel("Chức Vụ:");
        lblChucVu.setFont(labelFont);
        JTextField txtChucVu = new JTextField(nhanVien.getchucVu(), 20);
        txtChucVu.setFont(fieldFont);

        JLabel lblLuong = new JLabel("Lương:");
        lblLuong.setFont(labelFont);
        JTextField txtLuong = new JTextField(String.valueOf(nhanVien.getLuong()), 20);
        txtLuong.setFont(fieldFont);

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(lblIdNhanVien, gbc);
        gbc.gridx = 1; formPanel.add(txtIdNhanVien, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(lblHoTen, gbc);
        gbc.gridx = 1; formPanel.add(txtHoTen, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(lblEmail, gbc);
        gbc.gridx = 1; formPanel.add(txtEmail, gbc);
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(lblSdt, gbc);
        gbc.gridx = 1; formPanel.add(txtSdt, gbc);
        gbc.gridx = 0; gbc.gridy = 4; formPanel.add(lblChucVu, gbc);
        gbc.gridx = 1; formPanel.add(txtChucVu, gbc);
        gbc.gridx = 0; gbc.gridy = 5; formPanel.add(lblLuong, gbc);
        gbc.gridx = 1; formPanel.add(txtLuong, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(255, 255, 255));
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton btnSave = new JButton("Lưu");
        btnSave.setFont(new Font("Arial", Font.BOLD, 14));
        btnSave.setForeground(Color.BLACK);
        btnSave.setPreferredSize(new Dimension(100, 35));
        btnSave.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JButton btnCancel = new JButton("Hủy");
        btnCancel.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancel.setForeground(Color.BLACK);
        btnCancel.setPreferredSize(new Dimension(100, 35));
        btnCancel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        editDialog.add(formPanel, BorderLayout.CENTER);
        editDialog.add(buttonPanel, BorderLayout.SOUTH);

        btnSave.addActionListener(e -> {
            try {
                String hoTen = txtHoTen.getText().trim();
                String email = txtEmail.getText().trim();
                String sdt = txtSdt.getText().trim();
                String chucVu = txtChucVu.getText().trim();
                String luongStr = txtLuong.getText().trim();

                // Validation
                if (hoTen.isEmpty() || email.isEmpty() || sdt.isEmpty() || chucVu.isEmpty() || luongStr.isEmpty()) {
                    JOptionPane.showMessageDialog(editDialog, "Vui lòng điền đầy đủ thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!sdt.matches("\\d+")) {
                    JOptionPane.showMessageDialog(editDialog, "SĐT phải là số", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double luong;
                try {
                    luong = Double.parseDouble(luongStr);
                    if (luong <= 0) {
                        JOptionPane.showMessageDialog(editDialog, "Lương phải lớn hơn 0", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(editDialog, "Lương phải là số", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Update DTO
                nhanVien.setHoTenNV(hoTen);
                nhanVien.setEmail(email);
                nhanVien.setSdt(sdt);
                nhanVien.setchucVu(chucVu);
                nhanVien.setLuong(luong);

                // Update database
                if (nhanVienDAO.update(nhanVien)) {
                    nhanVienTableModel.setValueAt(hoTen, selectedRow, 1);
                    nhanVienTableModel.setValueAt(email, selectedRow, 2);
                    nhanVienTableModel.setValueAt(sdt, selectedRow, 3);
                    nhanVienTableModel.setValueAt(chucVu, selectedRow, 4);
                    nhanVienTableModel.setValueAt(luong, selectedRow, 5);
                    JOptionPane.showMessageDialog(editDialog, "Cập nhật nhân viên thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    editDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(editDialog, "Cập nhật nhân viên thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(editDialog, "Lỗi khi cập nhật: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener(e -> editDialog.dispose());

        editDialog.setVisible(true);
    }
}