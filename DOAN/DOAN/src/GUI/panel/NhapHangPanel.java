package GUI.panel;

import BLL.NhapHangBLL;
import DTO.ChiTietNhapHangDTO;
import DTO.NhapHangDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NhapHangPanel extends JPanel {
    private JTable nhapHangTable;
    private DefaultTableModel nhapHangTableModel;
    private JPanel nhapHangPanel;
    private Color headerColor = new Color(200, 255, 200);
    private NhapHangBLL nhapHangBLL;

    public NhapHangPanel() {
        nhapHangBLL = new NhapHangBLL();
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 400));
        createNhapHangPanel();
        add(nhapHangPanel, BorderLayout.CENTER);
        add(new JLabel("Nhập Hàng", SwingConstants.CENTER), BorderLayout.NORTH);
        loadDataFromDatabase();
    }

    private void createNhapHangPanel() {
        nhapHangPanel = new JPanel(new BorderLayout());

        // ===== Top Panel =====
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(headerColor);
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel lblChucNang = new JLabel("Chức năng");
        topPanel.add(lblChucNang, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setOpaque(false);

        JButton btnAdd = new JButton("Thêm");
        btnAdd.setToolTipText("Thêm nhập hàng");
        buttonPanel.add(btnAdd);

        JButton btnDelete = new JButton("Xóa");
        btnDelete.setToolTipText("Xóa nhập hàng");
        buttonPanel.add(btnDelete);

        JButton btnEdit = new JButton("Sửa");
        btnEdit.setToolTipText("Sửa nhập hàng");
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

        JComboBox<String> cmbSearchType = new JComboBox<>(new String[]{"ID Nhập Hàng", "ID Nhà Cung Cấp", "ID Nhân Viên"});
        cmbSearchType.setPreferredSize(new Dimension(120, 25));
        searchPanel.add(cmbSearchType);

        JTextField txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(200, 25));
        searchPanel.add(txtSearch);

        JButton btnSearch = new JButton("Tìm kiếm");
        btnSearch.setBackground(new Color(240, 240, 240));
        searchPanel.add(btnSearch);

        JButton btnReset = new JButton("Làm mới");
        btnReset.setBackground(new Color(240, 240, 240));
        searchPanel.add(btnReset);

        topPanel.add(searchPanel, BorderLayout.EAST);

        nhapHangPanel.add(topPanel, BorderLayout.NORTH);

        String[] columnNames = {"ID Nhập Hàng", "ID Nhà Cung Cấp", "ID Nhân Viên", "Ngày Nhập", "Tổng Giá Trị Nhập"};
        nhapHangTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        nhapHangTable = new JTable(nhapHangTableModel);
        nhapHangTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        nhapHangTable.setRowHeight(25);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < nhapHangTable.getColumnCount(); i++) {
            nhapHangTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        TableColumnModel columnModel = nhapHangTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(150);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(nhapHangTable);
        nhapHangPanel.add(scrollPane, BorderLayout.CENTER);

        btnSearch.addActionListener(e -> searchNhapHang(
                cmbSearchType.getSelectedItem().toString(),
                txtSearch.getText()));

        btnReset.addActionListener(e -> {
            txtSearch.setText("");
            nhapHangTableModel.setRowCount(0);
            loadDataFromDatabase();
        });

        btnAdd.addActionListener(e -> addNhapHang());
        btnDelete.addActionListener(e -> deleteNhapHang(nhapHangTable.getSelectedRow()));
        btnEdit.addActionListener(e -> editNhapHang(nhapHangTable.getSelectedRow()));

        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchNhapHang(
                            cmbSearchType.getSelectedItem().toString(),
                            txtSearch.getText());
                }
            }
        });
    }

    private void loadDataFromDatabase() {
        List<NhapHangDTO> nhapHangList = nhapHangBLL.getAllNhapHang();
        nhapHangTableModel.setRowCount(0);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (NhapHangDTO nhapHang : nhapHangList) {
            nhapHangTableModel.addRow(new Object[]{
                    nhapHang.getIdNhapHang(),
                    nhapHang.getIdNhaCungCap(),
                    nhapHang.getIdNhanVien(),
                    sdf.format(nhapHang.getNgayNhap()),
                    nhapHang.getTongGiaTriNhap()
            });
        }
    }

    private void searchNhapHang(String searchType, String keyword) {
        if (keyword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        nhapHangTableModel.setRowCount(0);
        List<NhapHangDTO> allData = nhapHangBLL.getAllNhapHang();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (NhapHangDTO nhapHang : allData) {
            boolean match = false;
            switch (searchType) {
                case "ID Nhập Hàng":
                    if (nhapHang.getIdNhapHang().toLowerCase().contains(keyword.toLowerCase())) {
                        match = true;
                    }
                    break;
                case "ID Nhà Cung Cấp":
                    if (nhapHang.getIdNhaCungCap().toLowerCase().contains(keyword.toLowerCase())) {
                        match = true;
                    }
                    break;
                case "ID Nhân Viên":
                    if (nhapHang.getIdNhanVien().toLowerCase().contains(keyword.toLowerCase())) {
                        match = true;
                    }
                    break;
            }
            if (match) {
                nhapHangTableModel.addRow(new Object[]{
                        nhapHang.getIdNhapHang(),
                        nhapHang.getIdNhaCungCap(),
                        nhapHang.getIdNhanVien(),
                        sdf.format(nhapHang.getNgayNhap()),
                        nhapHang.getTongGiaTriNhap()
                });
            }
        }

        if (nhapHangTableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy nhập hàng phù hợp",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void addNhapHang() {
        JDialog addDialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Thêm Nhập Hàng", Dialog.ModalityType.APPLICATION_MODAL);
        addDialog.setSize(450, 500);
        addDialog.setLocationRelativeTo(this);
        addDialog.setLayout(new BorderLayout());
        addDialog.getContentPane().setBackground(new Color(240, 248, 255));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);

        JLabel lblIdNhaCungCap = new JLabel("ID Nhà Cung Cấp:");
        lblIdNhaCungCap.setFont(labelFont);
        JTextField txtIdNhaCungCap = new JTextField(20);
        txtIdNhaCungCap.setFont(fieldFont);
        txtIdNhaCungCap.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 1));

        JLabel lblIdNhanVien = new JLabel("ID Nhân Viên:");
        lblIdNhanVien.setFont(labelFont);
        JTextField txtIdNhanVien = new JTextField(20);
        txtIdNhanVien.setFont(fieldFont);
        txtIdNhanVien.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 1));

        JLabel lblIdSanPham = new JLabel("ID Sản Phẩm:");
        lblIdSanPham.setFont(labelFont);
        JTextField txtIdSanPham = new JTextField(20);
        txtIdSanPham.setFont(fieldFont);
        txtIdSanPham.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 1));

        JLabel lblNgayNhap = new JLabel("Ngày Nhập (dd/MM/yyyy):");
        lblNgayNhap.setFont(labelFont);
        JTextField txtNgayNhap = new JTextField(new SimpleDateFormat("dd/MM/yyyy").format(new Date()), 20);
        txtNgayNhap.setFont(fieldFont);
        txtNgayNhap.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 1));

        JLabel lblSoLuongNhap = new JLabel("Số Lượng Nhập:");
        lblSoLuongNhap.setFont(labelFont);
        JTextField txtSoLuongNhap = new JTextField(20);
        txtSoLuongNhap.setFont(fieldFont);
        txtSoLuongNhap.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 1));

        JLabel lblGiaNhap = new JLabel("Giá Nhập:");
        lblGiaNhap.setFont(labelFont);
        JTextField txtGiaNhap = new JTextField(20);
        txtGiaNhap.setFont(fieldFont);
        txtGiaNhap.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 1));

        JLabel lblTongGiaTriNhap = new JLabel("Tổng Giá Trị Nhập:");
        lblTongGiaTriNhap.setFont(labelFont);
        JTextField txtTongGiaTriNhap = new JTextField(20);
        txtTongGiaTriNhap.setFont(fieldFont);
        txtTongGiaTriNhap.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 1));
        txtTongGiaTriNhap.setEditable(false);
        txtTongGiaTriNhap.setBackground(new Color(220, 220, 220));

        txtSoLuongNhap.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updateTongGiaTri(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updateTongGiaTri(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updateTongGiaTri(); }

            private void updateTongGiaTri() {
                try {
                    int soLuong = Integer.parseInt(txtSoLuongNhap.getText().trim());
                    double giaNhap = Double.parseDouble(txtGiaNhap.getText().trim());
                    double tongGiaTri = soLuong * giaNhap;
                    txtTongGiaTriNhap.setText(String.format("%.2f", tongGiaTri));
                } catch (NumberFormatException ex) {
                    txtTongGiaTriNhap.setText("");
                }
            }
        });

        txtGiaNhap.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updateTongGiaTri(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updateTongGiaTri(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updateTongGiaTri(); }

            private void updateTongGiaTri() {
                try {
                    int soLuong = Integer.parseInt(txtSoLuongNhap.getText().trim());
                    double giaNhap = Double.parseDouble(txtGiaNhap.getText().trim());
                    double tongGiaTri = soLuong * giaNhap;
                    txtTongGiaTriNhap.setText(String.format("%.2f", tongGiaTri));
                } catch (NumberFormatException ex) {
                    txtTongGiaTriNhap.setText("");
                }
            }
        });

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(lblIdNhaCungCap, gbc);
        gbc.gridx = 1; formPanel.add(txtIdNhaCungCap, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(lblIdNhanVien, gbc);
        gbc.gridx = 1; formPanel.add(txtIdNhanVien, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(lblIdSanPham, gbc);
        gbc.gridx = 1; formPanel.add(txtIdSanPham, gbc);
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(lblNgayNhap, gbc);
        gbc.gridx = 1; formPanel.add(txtNgayNhap, gbc);
        gbc.gridx = 0; gbc.gridy = 4; formPanel.add(lblSoLuongNhap, gbc);
        gbc.gridx = 1; formPanel.add(txtSoLuongNhap, gbc);
        gbc.gridx = 0; gbc.gridy = 5; formPanel.add(lblGiaNhap, gbc);
        gbc.gridx = 1; formPanel.add(txtGiaNhap, gbc);
        gbc.gridx = 0; gbc.gridy = 6; formPanel.add(lblTongGiaTriNhap, gbc);
        gbc.gridx = 1; formPanel.add(txtTongGiaTriNhap, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(240, 248, 255));
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JButton btnAdd = new JButton("Thêm");
        btnAdd.setFont(new Font("Arial", Font.BOLD, 14));
        btnAdd.setBackground(new Color(50, 205, 50));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setPreferredSize(new Dimension(100, 35));
        JButton btnCancel = new JButton("Hủy");
        btnCancel.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancel.setBackground(new Color(220, 20, 60));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setPreferredSize(new Dimension(100, 35));
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnCancel);

        addDialog.add(formPanel, BorderLayout.CENTER);
        addDialog.add(buttonPanel, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> {
            try {
                // Validate input
                if (txtIdNhaCungCap.getText().trim().isEmpty() || txtIdNhanVien.getText().trim().isEmpty() ||
                    txtIdSanPham.getText().trim().isEmpty() || txtNgayNhap.getText().trim().isEmpty() ||
                    txtSoLuongNhap.getText().trim().isEmpty() || txtGiaNhap.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(addDialog, "Vui lòng điền đầy đủ thông tin",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Tạo NhapHangDTO
                NhapHangDTO nhapHang = new NhapHangDTO();
                nhapHang.setIdNhaCungCap(txtIdNhaCungCap.getText().trim());
                nhapHang.setIdNhanVien(txtIdNhanVien.getText().trim());
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                nhapHang.setNgayNhap(sdf.parse(txtNgayNhap.getText().trim()));
                nhapHang.setTongGiaTriNhap(Double.parseDouble(txtTongGiaTriNhap.getText().trim()));

                // Tạo ChiTietNhapHangDTO
                ChiTietNhapHangDTO chiTiet = new ChiTietNhapHangDTO();
                chiTiet.setIdSanPham(txtIdSanPham.getText().trim());
                chiTiet.setSoLuongNhap(Integer.parseInt(txtSoLuongNhap.getText().trim()));
                chiTiet.setGiaNhap(Integer.parseInt(txtGiaNhap.getText().trim()));

                // Thêm qua BLL
                nhapHangBLL.addNhapHang(nhapHang, chiTiet);
                JOptionPane.showMessageDialog(addDialog, "Thêm nhập hàng thành công",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadDataFromDatabase();
                addDialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(addDialog, "Lỗi khi thêm: " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener(e -> addDialog.dispose());

        addDialog.setVisible(true);
    }

    private void deleteNhapHang(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhập hàng để xóa",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idNhapHang = nhapHangTableModel.getValueAt(selectedRow, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa nhập hàng: " + idNhapHang + "?",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                nhapHangBLL.deleteNhapHang(idNhapHang);
                nhapHangTableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Đã xóa nhập hàng thành công",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa: " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editNhapHang(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhập hàng để sửa",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idNhapHang = nhapHangTableModel.getValueAt(selectedRow, 0).toString();
        NhapHangDTO nhapHang = nhapHangBLL.getNhapHangById(idNhapHang);
        ChiTietNhapHangDTO chiTiet = nhapHangBLL.getChiTietNhapHangByIdNhapHang(idNhapHang);

        if (nhapHang == null || chiTiet == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu để sửa",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JDialog editDialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Sửa Nhập Hàng", Dialog.ModalityType.APPLICATION_MODAL);
        editDialog.setSize(450, 500);
        editDialog.setLocationRelativeTo(this);
        editDialog.setLayout(new BorderLayout());
        editDialog.getContentPane().setBackground(new Color(240, 248, 255));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);

        JLabel lblIdNhapHang = new JLabel("ID Nhập Hàng:");
        lblIdNhapHang.setFont(labelFont);
        JTextField txtIdNhapHang = new JTextField(nhapHang.getIdNhapHang(), 20);
        txtIdNhapHang.setFont(fieldFont);
        txtIdNhapHang.setEditable(false);
        txtIdNhapHang.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 1));

        JLabel lblIdNhaCungCap = new JLabel("ID Nhà Cung Cấp:");
        lblIdNhaCungCap.setFont(labelFont);
        JTextField txtIdNhaCungCap = new JTextField(nhapHang.getIdNhaCungCap(), 20);
        txtIdNhaCungCap.setFont(fieldFont);
        txtIdNhaCungCap.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 1));

        JLabel lblIdNhanVien = new JLabel("ID Nhân Viên:");
        lblIdNhanVien.setFont(labelFont);
        JTextField txtIdNhanVien = new JTextField(nhapHang.getIdNhanVien(), 20);
        txtIdNhanVien.setFont(fieldFont);
        txtIdNhanVien.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 1));

        JLabel lblIdSanPham = new JLabel("ID Sản Phẩm:");
        lblIdSanPham.setFont(labelFont);
        JTextField txtIdSanPham = new JTextField(chiTiet.getIdSanPham(), 20);
        txtIdSanPham.setFont(fieldFont);
        txtIdSanPham.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 1));

        JLabel lblNgayNhap = new JLabel("Ngày Nhập (dd/MM/yyyy):");
        lblNgayNhap.setFont(labelFont);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        JTextField txtNgayNhap = new JTextField(sdf.format(nhapHang.getNgayNhap()), 20);
        txtNgayNhap.setFont(fieldFont);
        txtNgayNhap.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 1));

        JLabel lblSoLuongNhap = new JLabel("Số Lượng Nhập:");
        lblSoLuongNhap.setFont(labelFont);
        JTextField txtSoLuongNhap = new JTextField(String.valueOf(chiTiet.getSoLuongNhap()), 20);
        txtSoLuongNhap.setFont(fieldFont);
        txtSoLuongNhap.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 1));

        JLabel lblGiaNhap = new JLabel("Giá Nhập:");
        lblGiaNhap.setFont(labelFont);
        JTextField txtGiaNhap = new JTextField(String.valueOf(chiTiet.getGiaNhap()), 20);
        txtGiaNhap.setFont(fieldFont);
        txtGiaNhap.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 1));

        JLabel lblTongGiaTriNhap = new JLabel("Tổng Giá Trị Nhập:");
        lblTongGiaTriNhap.setFont(labelFont);
        JTextField txtTongGiaTriNhap = new JTextField(String.valueOf(nhapHang.getTongGiaTriNhap()), 20);
        txtTongGiaTriNhap.setFont(fieldFont);
        txtTongGiaTriNhap.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 1));
        txtTongGiaTriNhap.setEditable(false);
        txtTongGiaTriNhap.setBackground(new Color(220, 220, 220));

        txtSoLuongNhap.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updateTongGiaTri(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updateTongGiaTri(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updateTongGiaTri(); }

            private void updateTongGiaTri() {
                try {
                    int soLuong = Integer.parseInt(txtSoLuongNhap.getText().trim());
                    double giaNhap = Double.parseDouble(txtGiaNhap.getText().trim());
                    double tongGiaTri = soLuong * giaNhap;
                    txtTongGiaTriNhap.setText(String.format("%.2f", tongGiaTri));
                } catch (NumberFormatException ex) {
                    txtTongGiaTriNhap.setText("");
                }
            }
        });

        txtGiaNhap.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updateTongGiaTri(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updateTongGiaTri(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updateTongGiaTri(); }

            private void updateTongGiaTri() {
                try {
                    int soLuong = Integer.parseInt(txtSoLuongNhap.getText().trim());
                    double giaNhap = Double.parseDouble(txtGiaNhap.getText().trim());
                    double tongGiaTri = soLuong * giaNhap;
                    txtTongGiaTriNhap.setText(String.format("%.2f", tongGiaTri));
                } catch (NumberFormatException ex) {
                    txtTongGiaTriNhap.setText("");
                }
            }
        });

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(lblIdNhapHang, gbc);
        gbc.gridx = 1; formPanel.add(txtIdNhapHang, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(lblIdNhaCungCap, gbc);
        gbc.gridx = 1; formPanel.add(txtIdNhaCungCap, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(lblIdNhanVien, gbc);
        gbc.gridx = 1; formPanel.add(txtIdNhanVien, gbc);
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(lblIdSanPham, gbc);
        gbc.gridx = 1; formPanel.add(txtIdSanPham, gbc);
        gbc.gridx = 0; gbc.gridy = 4; formPanel.add(lblNgayNhap, gbc);
        gbc.gridx = 1; formPanel.add(txtNgayNhap, gbc);
        gbc.gridx = 0; gbc.gridy = 5; formPanel.add(lblSoLuongNhap, gbc);
        gbc.gridx = 1; formPanel.add(txtSoLuongNhap, gbc);
        gbc.gridx = 0; gbc.gridy = 6; formPanel.add(lblGiaNhap, gbc);
        gbc.gridx = 1; formPanel.add(txtGiaNhap, gbc);
        gbc.gridx = 0; gbc.gridy = 7; formPanel.add(lblTongGiaTriNhap, gbc);
        gbc.gridx = 1; formPanel.add(txtTongGiaTriNhap, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(240, 248, 255));
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JButton btnSave = new JButton("Lưu");
        btnSave.setFont(new Font("Arial", Font.BOLD, 14));
        btnSave.setBackground(new Color(50, 205, 50));
        btnSave.setForeground(Color.WHITE);
        btnSave.setPreferredSize(new Dimension(100, 35));
        JButton btnCancel = new JButton("Hủy");
        btnCancel.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancel.setBackground(new Color(220, 20, 60));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setPreferredSize(new Dimension(100, 35));
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        editDialog.add(formPanel, BorderLayout.CENTER);
        editDialog.add(buttonPanel, BorderLayout.SOUTH);

        btnSave.addActionListener(e -> {
            try {
                // Validate input
                if (txtIdNhaCungCap.getText().trim().isEmpty() || txtIdNhanVien.getText().trim().isEmpty() ||
                    txtIdSanPham.getText().trim().isEmpty() || txtNgayNhap.getText().trim().isEmpty() ||
                    txtSoLuongNhap.getText().trim().isEmpty() || txtGiaNhap.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(editDialog, "Vui lòng điền đầy đủ thông tin",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Cập nhật NhapHangDTO
                nhapHang.setIdNhaCungCap(txtIdNhaCungCap.getText().trim());
                nhapHang.setIdNhanVien(txtIdNhanVien.getText().trim());
                nhapHang.setNgayNhap(sdf.parse(txtNgayNhap.getText().trim()));
                nhapHang.setTongGiaTriNhap(Double.parseDouble(txtTongGiaTriNhap.getText().trim()));

                // Cập nhật ChiTietNhapHangDTO
                chiTiet.setIdSanPham(txtIdSanPham.getText().trim());
                chiTiet.setSoLuongNhap(Integer.parseInt(txtSoLuongNhap.getText().trim()));
                chiTiet.setGiaNhap(Integer.parseInt(txtGiaNhap.getText().trim()));

                // Lưu qua BLL
                nhapHangBLL.updateNhapHang(nhapHang, chiTiet);
                JOptionPane.showMessageDialog(editDialog, "Cập nhật nhập hàng thành công",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadDataFromDatabase();
                editDialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(editDialog, "Lỗi khi cập nhật: " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener(e -> editDialog.dispose());

        editDialog.setVisible(true);
    }
}