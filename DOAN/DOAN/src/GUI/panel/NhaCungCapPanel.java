package GUI.panel;

import DAO.NhaCungCapDAO;
import DTO.NhaCungCapDTO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class NhaCungCapPanel extends JPanel {
    private DefaultTableModel nhaCungCapTableModel;
    private JTable nhaCungCapTable;
    private JPanel nhaCungCapPanel;
    private Color headerColor = new Color(200, 255, 200);
    private JTextField txtSearch;
    private NhaCungCapDAO nhaCungCapDAO;

        public NhaCungCapPanel() {
        nhaCungCapDAO = new NhaCungCapDAO();
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 400));
        createNhaCungCapPanel();
        add(nhaCungCapPanel, BorderLayout.CENTER);
        loadDataToTable();
    }

    private void createNhaCungCapPanel() {
        nhaCungCapPanel = new JPanel(new BorderLayout());

        // Top Panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(headerColor);
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Title Label
        JLabel lblTitle = new JLabel("NHÀ CUNG CẤP", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(lblTitle, BorderLayout.NORTH);

        JLabel lblChucNang = new JLabel("Chức năng");
        topPanel.add(lblChucNang, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setOpaque(false);

        JButton btnAdd = new JButton("Thêm");
        btnAdd.setToolTipText("Thêm nhà cung cấp");
        btnAdd.setBackground(new Color(240, 240, 240));
        buttonPanel.add(btnAdd);

        JButton btnDelete = new JButton("Xóa");
        btnDelete.setToolTipText("Xóa nhà cung cấp");
        btnDelete.setBackground(new Color(240, 240, 240));
        buttonPanel.add(btnDelete);

        JButton btnEdit = new JButton("Sửa");
        btnEdit.setToolTipText("Sửa nhà cung cấp");
        btnEdit.setBackground(new Color(240, 240, 240));
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

        JLabel lblTimKiem = new JLabel("Tìm kiếm");
        searchPanel.add(lblTimKiem);

        JComboBox<String> cmbSearchType = new JComboBox<>(new String[]{"ID Nhà Cung Cấp", "Tên Nhà Cung Cấp"});
        cmbSearchType.setPreferredSize(new Dimension(120, 25));
        searchPanel.add(cmbSearchType);

        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(200, 25));
        searchPanel.add(txtSearch);

        JButton btnSearch = new JButton("Tìm kiếm");
        btnSearch.setBackground(new Color(240, 240, 240));
        searchPanel.add(btnSearch);

        JButton btnReset = new JButton("Làm mới");
        btnReset.setBackground(new Color(240, 240, 240));
        searchPanel.add(btnReset);

        topPanel.add(searchPanel, BorderLayout.EAST);
        nhaCungCapPanel.add(topPanel, BorderLayout.NORTH);

        // Table
        String[] columns = {"ID Nhà Cung Cấp", "Tên Nhà Cung Cấp", "Địa Chỉ", "Số Điện Thoại"};
        nhaCungCapTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        nhaCungCapTable = new JTable(nhaCungCapTableModel);
        nhaCungCapTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        nhaCungCapTable.setRowHeight(25);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < nhaCungCapTable.getColumnCount(); i++) {
            nhaCungCapTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        TableColumnModel columnModel = nhaCungCapTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(200);
        columnModel.getColumn(3).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(nhaCungCapTable);
        nhaCungCapPanel.add(scrollPane, BorderLayout.CENTER);

        // Event
        btnAdd.addActionListener(e -> addNhaCungCap());
        btnDelete.addActionListener(e -> deleteNhaCungCap(nhaCungCapTable.getSelectedRow()));
        btnEdit.addActionListener(e -> editNhaCungCap(nhaCungCapTable.getSelectedRow()));
        btnSearch.addActionListener(e -> searchNhaCungCap(cmbSearchType.getSelectedItem().toString(), txtSearch.getText()));
        btnReset.addActionListener(e -> resetNhaCungCapSearch());

        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchNhaCungCap(cmbSearchType.getSelectedItem().toString(), txtSearch.getText());
                }
            }
        });
    }

    private void loadDataToTable() {
        nhaCungCapTableModel.setRowCount(0);
        ArrayList<NhaCungCapDTO> list = nhaCungCapDAO.selectAll();
        for (NhaCungCapDTO nhaCungCap : list) {
            nhaCungCapTableModel.addRow(new Object[]{
                    nhaCungCap.getIdNhaCungCap(),
                    nhaCungCap.getTenNhaCungCap(),
                    nhaCungCap.getDiaChi(),
                    nhaCungCap.getSdt()
            });
        }
    }

    private void addNhaCungCap() {
        JDialog addDialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Thêm Nhà Cung Cấp", Dialog.ModalityType.APPLICATION_MODAL);
        addDialog.setSize(450, 300);
        addDialog.setLocationRelativeTo(this);
        addDialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(new Color(230, 255, 230));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);

        JLabel lblId = new JLabel("ID Nhà Cung Cấp:");
        lblId.setFont(labelFont);
        JTextField txtId = new JTextField(20);
        txtId.setFont(fieldFont);

        JLabel lblName = new JLabel("Tên Nhà Cung Cấp:");
        lblName.setFont(labelFont);
        JTextField txtName = new JTextField(20);
        txtName.setFont(fieldFont);

        JLabel lblAddress = new JLabel("Địa Chỉ:");
        lblAddress.setFont(labelFont);
        JTextField txtAddress = new JTextField(20);
        txtAddress.setFont(fieldFont);

        JLabel lblPhone = new JLabel("Số Điện Thoại:");
        lblPhone.setFont(labelFont);
        JTextField txtPhone = new JTextField(20);
        txtPhone.setFont(fieldFont);

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(lblId, gbc);
        gbc.gridx = 1; formPanel.add(txtId, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(lblName, gbc);
        gbc.gridx = 1; formPanel.add(txtName, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(lblAddress, gbc);
        gbc.gridx = 1; formPanel.add(txtAddress, gbc);
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(lblPhone, gbc);
        gbc.gridx = 1; formPanel.add(txtPhone, gbc);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.setBackground(new Color(255, 255, 255));
        btnPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
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
        btnPanel.add(btnAdd);
        btnPanel.add(btnCancel);

        addDialog.add(formPanel, BorderLayout.CENTER);
        addDialog.add(btnPanel, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> {
            try {
                if (txtId.getText().trim().isEmpty() || txtName.getText().trim().isEmpty() ||
                    txtAddress.getText().trim().isEmpty() || txtPhone.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(addDialog, "Vui lòng điền đầy đủ các trường bắt buộc!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!txtPhone.getText().trim().matches("\\d{10,11}")) {
                    JOptionPane.showMessageDialog(addDialog, "Số điện thoại phải là 10 hoặc 11 chữ số!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                NhaCungCapDTO ncc = new NhaCungCapDTO();
                ncc.setIdNhaCungCap(txtId.getText().trim());
                ncc.setTenNhaCungCap(txtName.getText().trim());
                ncc.setDiaChi(txtAddress.getText().trim());
                ncc.setSdt(txtPhone.getText().trim());

                if (nhaCungCapDAO.insert(ncc)) {
                    JOptionPane.showMessageDialog(addDialog, "Thêm nhà cung cấp thành công!",
                            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    loadDataToTable();
                    addDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(addDialog, "Thêm nhà cung cấp thất bại!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(addDialog, "Lỗi khi thêm: " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener(e -> addDialog.dispose());
        addDialog.setVisible(true);
    }

    private void editNhaCungCap(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhà cung cấp để sửa",
                    "Informasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String id = nhaCungCapTableModel.getValueAt(selectedRow, 0).toString();
        String name = nhaCungCapTableModel.getValueAt(selectedRow, 1).toString();
        String address = nhaCungCapTableModel.getValueAt(selectedRow, 2).toString();
        String phone = nhaCungCapTableModel.getValueAt(selectedRow, 3).toString();

        JDialog editDialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Sửa Nhà Cung Cấp", Dialog.ModalityType.APPLICATION_MODAL);
        editDialog.setSize(450, 300);
        editDialog.setLocationRelativeTo(this);
        editDialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(new Color(230, 255, 230));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);

        JLabel lblId = new JLabel("ID Nhà Cung Cấp:");
        lblId.setFont(labelFont);
        JTextField txtId = new JTextField(id, 20);
        txtId.setFont(fieldFont);
        txtId.setEditable(false);
        txtId.setBackground(new Color(220, 220, 220));

        JLabel lblName = new JLabel("Tên Nhà Cung Cấp:");
        lblName.setFont(labelFont);
        JTextField txtName = new JTextField(name, 20);
        txtName.setFont(fieldFont);

        JLabel lblAddress = new JLabel("Địa Chỉ:");
        lblAddress.setFont(labelFont);
        JTextField txtAddress = new JTextField(address, 20);
        txtAddress.setFont(fieldFont);

        JLabel lblPhone = new JLabel("Số Điện Thoại:");
        lblPhone.setFont(labelFont);
        JTextField txtPhone = new JTextField(phone, 20);
        txtPhone.setFont(fieldFont);

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(lblId, gbc);
        gbc.gridx = 1; formPanel.add(txtId, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(lblName, gbc);
        gbc.gridx = 1; formPanel.add(txtName, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(lblAddress, gbc);
        gbc.gridx = 1; formPanel.add(txtAddress, gbc);
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(lblPhone, gbc);
        gbc.gridx = 1; formPanel.add(txtPhone, gbc);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.setBackground(new Color(255, 255, 255));
        btnPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
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
        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);

        editDialog.add(formPanel, BorderLayout.CENTER);
        editDialog.add(btnPanel, BorderLayout.SOUTH);

        btnSave.addActionListener(e -> {
            try {
                if (txtName.getText().trim().isEmpty() || txtAddress.getText().trim().isEmpty() || txtPhone.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(editDialog, "Vui lòng điền đầy đủ các trường bắt buộc!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!txtPhone.getText().trim().matches("\\d{10,11}")) {
                    JOptionPane.showMessageDialog(editDialog, "Số điện thoại phải là 10 hoặc 11 chữ số!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                NhaCungCapDTO ncc = new NhaCungCapDTO();
                ncc.setIdNhaCungCap(txtId.getText().trim());
                ncc.setTenNhaCungCap(txtName.getText().trim());
                ncc.setDiaChi(txtAddress.getText().trim());
                ncc.setSdt(txtPhone.getText().trim());

                if (nhaCungCapDAO.update(ncc)) {
                    nhaCungCapTableModel.setValueAt(txtName.getText(), selectedRow, 1);
                    nhaCungCapTableModel.setValueAt(txtAddress.getText(), selectedRow, 2);
                    nhaCungCapTableModel.setValueAt(txtPhone.getText(), selectedRow, 3);
                    JOptionPane.showMessageDialog(editDialog, "Cập nhật nhà cung cấp thành công!",
                            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    editDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(editDialog, "Cập nhật nhà cung cấp thất bại!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(editDialog, "Lỗi khi cập nhật: " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener(e -> editDialog.dispose());
        editDialog.setVisible(true);
    }

    private void deleteNhaCungCap(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhà cung cấp để xóa",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idNhaCungCap = nhaCungCapTableModel.getValueAt(selectedRow, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa nhà cung cấp: " + idNhaCungCap + "?",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (nhaCungCapDAO.delete(idNhaCungCap)) {
                    nhaCungCapTableModel.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(this, "Xóa nhà cung cấp thành công!",
                            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa nhà cung cấp thất bại!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa: " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void searchNhaCungCap(String searchType, String keyword) {
        if (keyword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        nhaCungCapTableModel.setRowCount(0);
        ArrayList<NhaCungCapDTO> allData = nhaCungCapDAO.selectAll();

        for (NhaCungCapDTO ncc : allData) {
            boolean match = false;
            switch (searchType) {
                case "ID Nhà Cung Cấp":
                    if (ncc.getIdNhaCungCap().toLowerCase().contains(keyword.toLowerCase())) {
                        match = true;
                    }
                    break;
                case "Tên Nhà Cung Cấp":
                    if (ncc.getTenNhaCungCap().toLowerCase().contains(keyword.toLowerCase())) {
                        match = true;
                    }
                    break;
            }
            if (match) {
                nhaCungCapTableModel.addRow(new Object[]{
                        ncc.getIdNhaCungCap(),
                        ncc.getTenNhaCungCap(),
                        ncc.getDiaChi(),
                        ncc.getSdt()
                });
            }
        }

        if (nhaCungCapTableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy nhà cung cấp phù hợp",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void resetNhaCungCapSearch() {
        txtSearch.setText("");
        loadDataToTable();
    }
}