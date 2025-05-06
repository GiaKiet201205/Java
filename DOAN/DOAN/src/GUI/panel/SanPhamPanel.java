package GUI.panel;

import DAO.SanPhamDAO;
import DTO.SanPhamDTO;
import config.JDBC;
import GUI.TrangChuGUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SanPhamPanel extends JPanel {
    private DefaultTableModel sanPhamTableModel;
    private JTable sanPhamTable;
    private JPanel sanPhamPanel;
    private final Color headerColor = new Color(200, 255, 200);
    private final Set<String> outOfStockProducts = new HashSet<>();
    private final TrangChuGUI trangChuGUI;

    public SanPhamPanel(TrangChuGUI trangChuGUI) {
        this.trangChuGUI = trangChuGUI;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 400));
        createSanPhamPanel();
        add(sanPhamPanel, BorderLayout.CENTER);
        loadDataFromDatabase();
    }

    private void createSanPhamPanel() {
        sanPhamPanel = new JPanel(new BorderLayout());

        // Top Panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(headerColor);
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel lblTitle = new JLabel("SẢN PHẨM", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(lblTitle, BorderLayout.NORTH);
        
        JLabel lblChucNang = new JLabel("Chức năng");
        topPanel.add(lblChucNang, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setOpaque(false);

        JButton btnAdd = new JButton("Thêm");
        btnAdd.setToolTipText("Thêm sản phẩm");
        btnAdd.setBackground(new Color(240, 240, 240));
        buttonPanel.add(btnAdd);

        JButton btnDelete = new JButton("Xóa");
        btnDelete.setToolTipText("Xóa sản phẩm");
        btnDelete.setBackground(new Color(240, 240, 240));
        buttonPanel.add(btnDelete);

        JButton btnEdit = new JButton("Sửa");
        btnEdit.setToolTipText("Sửa sản phẩm");
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

        JComboBox<String> cmbSearchType = new JComboBox<>(new String[]{"ID sản phẩm", "Tên sản phẩm"});
        cmbSearchType.setPreferredSize(new Dimension(120, 25));
        searchPanel.add(cmbSearchType);

        JTextField txtSearchProduct = new JTextField();
        txtSearchProduct.setPreferredSize(new Dimension(200, 25));
        searchPanel.add(txtSearchProduct);

        JButton btnSearch = new JButton("Tìm kiếm");
        btnSearch.setBackground(new Color(240, 240, 240));
        searchPanel.add(btnSearch);

        JButton btnReset = new JButton("Làm mới");
        btnReset.setBackground(new Color(240, 240, 240));
        searchPanel.add(btnReset);

        topPanel.add(searchPanel, BorderLayout.EAST);
        sanPhamPanel.add(topPanel, BorderLayout.NORTH);

        // Table
        String[] columns = {"ID sản phẩm", "Tên sản phẩm", "Giá", "Số lượng tồn kho", "ID danh mục", "Hình ảnh", "ID nhà cung cấp"};
        sanPhamTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        sanPhamTable = new JTable(sanPhamTableModel);
        sanPhamTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sanPhamTable.setRowHeight(25);

        // Custom renderer for out-of-stock products
        sanPhamTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String idSanPham = table.getModel().getValueAt(row, 0).toString();
                c.setBackground(outOfStockProducts.contains(idSanPham) ? Color.RED : (isSelected ? table.getSelectionBackground() : Color.WHITE));
                return c;
            }
        });

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < sanPhamTable.getColumnCount(); i++) {
            sanPhamTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        TableColumnModel columnModel = sanPhamTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(100);
        columnModel.getColumn(5).setPreferredWidth(150);
        columnModel.getColumn(6).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(sanPhamTable);
        sanPhamPanel.add(scrollPane, BorderLayout.CENTER);

        // Actions
        btnAdd.addActionListener(e -> addSanPham());
        btnEdit.addActionListener(e -> editSanPham(sanPhamTable.getSelectedRow()));
        btnDelete.addActionListener(e -> markProductOutOfStock(sanPhamTable.getSelectedRow()));
        btnSearch.addActionListener(e -> searchSanPham(cmbSearchType.getSelectedItem().toString(), txtSearchProduct.getText()));
        btnReset.addActionListener(e -> {
            txtSearchProduct.setText("");
            loadDataFromDatabase();
        });

        txtSearchProduct.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchSanPham(cmbSearchType.getSelectedItem().toString(), txtSearchProduct.getText());
                }
            }
        });
    }

    private void loadDataFromDatabase() {
        sanPhamTableModel.setRowCount(0);
        SanPhamDAO dao = new SanPhamDAO();
        ArrayList<SanPhamDTO> list = dao.selectAll();
        for (SanPhamDTO sp : list) {
            if (sp.getSoLuongTonKho() > 0) {
                sanPhamTableModel.addRow(new Object[]{
                        sp.getIdSanPham(),
                        sp.getTenSanPham(),
                        sp.getGia(),
                        sp.getSoLuongTonKho(),
                        sp.getIdDanhMuc(),
                        sp.getHinhAnh(),
                        sp.getIdNhaCungCap()
                });
            }
        }
        sanPhamTable.repaint();
    }

    private void addSanPham() {
        JDialog addDialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Thêm Sản Phẩm", Dialog.ModalityType.APPLICATION_MODAL);
        addDialog.setSize(400, 400);
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

        JLabel lblIdSanPham = new JLabel("ID Sản Phẩm:");
        lblIdSanPham.setFont(labelFont);
        JTextField txtIdSanPham = new JTextField(20);
        txtIdSanPham.setFont(fieldFont);

        JLabel lblTenSanPham = new JLabel("Tên Sản Phẩm:");
        lblTenSanPham.setFont(labelFont);
        JTextField txtTenSanPham = new JTextField(20);
        txtTenSanPham.setFont(fieldFont);

        JLabel lblGia = new JLabel("Giá:");
        lblGia.setFont(labelFont);
        JTextField txtGia = new JTextField(20);
        txtGia.setFont(fieldFont);

        JLabel lblSoLuong = new JLabel("Số Lượng Tồn Kho:");
        lblSoLuong.setFont(labelFont);
        JTextField txtSoLuong = new JTextField(20);
        txtSoLuong.setFont(fieldFont);

        JLabel lblIdDanhMuc = new JLabel("ID Danh Mục:");
        lblIdDanhMuc.setFont(labelFont);
        JTextField txtIdDanhMuc = new JTextField(20);
        txtIdDanhMuc.setFont(fieldFont);

        JLabel lblHinhAnh = new JLabel("Hình Ảnh:");
        lblHinhAnh.setFont(labelFont);
        JTextField txtHinhAnh = new JTextField(20);
        txtHinhAnh.setFont(fieldFont);

        JButton btnChonAnh = new JButton("Chọn ảnh");
        btnChonAnh.setFont(fieldFont);
        btnChonAnh.setBackground(new Color(255, 255, 255));

        JLabel lblIdNhaCungCap = new JLabel("ID Nhà Cung Cấp:");
        lblIdNhaCungCap.setFont(labelFont);
        JTextField txtIdNhaCungCap = new JTextField(20);
        txtIdNhaCungCap.setFont(fieldFont);

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(lblIdSanPham, gbc);
        gbc.gridx = 1; formPanel.add(txtIdSanPham, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(lblTenSanPham, gbc);
        gbc.gridx = 1; formPanel.add(txtTenSanPham, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(lblGia, gbc);
        gbc.gridx = 1; formPanel.add(txtGia, gbc);
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(lblSoLuong, gbc);
        gbc.gridx = 1; formPanel.add(txtSoLuong, gbc);
        gbc.gridx = 0; gbc.gridy = 4; formPanel.add(lblIdDanhMuc, gbc);
        gbc.gridx = 1; formPanel.add(txtIdDanhMuc, gbc);
        gbc.gridx = 0; gbc.gridy = 5; formPanel.add(lblHinhAnh, gbc);
        gbc.gridx = 1; 
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(txtHinhAnh, BorderLayout.CENTER);
        imagePanel.add(btnChonAnh, BorderLayout.EAST);
        formPanel.add(imagePanel, gbc);
        gbc.gridx = 0; gbc.gridy = 6; formPanel.add(lblIdNhaCungCap, gbc);
        gbc.gridx = 1; formPanel.add(txtIdNhaCungCap, gbc);

        btnChonAnh.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn ảnh sản phẩm");
            int result = fileChooser.showOpenDialog(addDialog);
            if (result == JFileChooser.APPROVE_OPTION) {
                txtHinhAnh.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

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
            if (txtIdSanPham.getText().trim().isEmpty() || txtTenSanPham.getText().trim().isEmpty() ||
                txtGia.getText().trim().isEmpty() || txtSoLuong.getText().trim().isEmpty() ||
                txtIdDanhMuc.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(addDialog, "Vui lòng điền đầy đủ các trường bắt buộc!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int gia, soLuong;
            try {
                gia = Integer.parseInt(txtGia.getText().trim());
                soLuong = Integer.parseInt(txtSoLuong.getText().trim());
                if (gia < 0 || soLuong < 0) {
                    JOptionPane.showMessageDialog(addDialog, "Giá và số lượng không được âm!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(addDialog, "Giá và số lượng phải là số nguyên!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            SanPhamDTO sp = new SanPhamDTO();
            sp.setIdSanPham(txtIdSanPham.getText().trim());
            sp.setTenSanPham(txtTenSanPham.getText().trim());
            sp.setGia(gia);
            sp.setSoLuongTonKho(soLuong);
            sp.setIdDanhMuc(txtIdDanhMuc.getText().trim());
            sp.setHinhAnh(txtHinhAnh.getText().trim());
            sp.setIdNhaCungCap(txtIdNhaCungCap.getText().trim());

            SanPhamDAO dao = new SanPhamDAO();
            if (dao.insert(sp)) {
                JOptionPane.showMessageDialog(addDialog, "Thêm sản phẩm thành công!",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadDataFromDatabase();
                if (soLuong > 0) {
                    trangChuGUI.addProductToView(sp);
                }
                addDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(addDialog, "Thêm sản phẩm thất bại! Vui lòng kiểm tra ID nhà cung cấp.",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener(e -> addDialog.dispose());
        addDialog.setVisible(true);
    }

    private void editSanPham(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để sửa!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idSanPham = sanPhamTableModel.getValueAt(selectedRow, 0).toString();
        String tenSanPham = sanPhamTableModel.getValueAt(selectedRow, 1).toString();
        int gia = Integer.parseInt(sanPhamTableModel.getValueAt(selectedRow, 2).toString());
        int soLuong = Integer.parseInt(sanPhamTableModel.getValueAt(selectedRow, 3).toString());
        String idDanhMuc = sanPhamTableModel.getValueAt(selectedRow, 4).toString();
        String hinhAnh = sanPhamTableModel.getValueAt(selectedRow, 5).toString();
        String idNhaCungCap = sanPhamTableModel.getValueAt(selectedRow, 6).toString();

        JDialog editDialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Sửa Sản Phẩm", Dialog.ModalityType.APPLICATION_MODAL);
        editDialog.setSize(400, 400);
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

        JLabel lblIdSanPham = new JLabel("ID Sản Phẩm:");
        lblIdSanPham.setFont(labelFont);
        JTextField txtIdSanPham = new JTextField(idSanPham, 20);
        txtIdSanPham.setFont(fieldFont);
        txtIdSanPham.setEditable(false);
        txtIdSanPham.setBackground(new Color(220, 220, 220));

        JLabel lblTenSanPham = new JLabel("Tên Sản Phẩm:");
        lblTenSanPham.setFont(labelFont);
        JTextField txtTenSanPham = new JTextField(tenSanPham, 20);
        txtTenSanPham.setFont(fieldFont);

        JLabel lblGia = new JLabel("Giá:");
        lblGia.setFont(labelFont);
        JTextField txtGia = new JTextField(String.valueOf(gia), 20);
        txtGia.setFont(fieldFont);

        JLabel lblSoLuong = new JLabel("Số Lượng Tồn Kho:");
        lblSoLuong.setFont(labelFont);
        JTextField txtSoLuong = new JTextField(String.valueOf(soLuong), 20);
        txtSoLuong.setFont(fieldFont);

        JLabel lblIdDanhMuc = new JLabel("ID Danh Mục:");
        lblIdDanhMuc.setFont(labelFont);
        JTextField txtIdDanhMuc = new JTextField(idDanhMuc, 20);
        txtIdDanhMuc.setFont(fieldFont);

        JLabel lblHinhAnh = new JLabel("Hình Ảnh:");
        lblHinhAnh.setFont(labelFont);
        JTextField txtHinhAnh = new JTextField(hinhAnh, 20);
        txtHinhAnh.setFont(fieldFont);

        JButton btnChonAnh = new JButton("Chọn ảnh");
        btnChonAnh.setFont(fieldFont);
        btnChonAnh.setBackground(new Color(255, 255, 255));

        JLabel lblIdNhaCungCap = new JLabel("ID Nhà Cung Cấp:");
        lblIdNhaCungCap.setFont(labelFont);
        JTextField txtIdNhaCungCap = new JTextField(idNhaCungCap, 20);
        txtIdNhaCungCap.setFont(fieldFont);

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(lblIdSanPham, gbc);
        gbc.gridx = 1; formPanel.add(txtIdSanPham, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(lblTenSanPham, gbc);
        gbc.gridx = 1; formPanel.add(txtTenSanPham, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(lblGia, gbc);
        gbc.gridx = 1; formPanel.add(txtGia, gbc);
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(lblSoLuong, gbc);
        gbc.gridx = 1; formPanel.add(txtSoLuong, gbc);
        gbc.gridx = 0; gbc.gridy = 4; formPanel.add(lblIdDanhMuc, gbc);
        gbc.gridx = 1; formPanel.add(txtIdDanhMuc, gbc);
        gbc.gridx = 0; gbc.gridy = 5; formPanel.add(lblHinhAnh, gbc);
        gbc.gridx = 1; 
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(txtHinhAnh, BorderLayout.CENTER);
        imagePanel.add(btnChonAnh, BorderLayout.EAST);
        formPanel.add(imagePanel, gbc);
        gbc.gridx = 0; gbc.gridy = 6; formPanel.add(lblIdNhaCungCap, gbc);
        gbc.gridx = 1; formPanel.add(txtIdNhaCungCap, gbc);

        btnChonAnh.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn ảnh sản phẩm");
            int result = fileChooser.showOpenDialog(editDialog);
            if (result == JFileChooser.APPROVE_OPTION) {
                txtHinhAnh.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

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
                if (txtTenSanPham.getText().trim().isEmpty() || txtGia.getText().trim().isEmpty() ||
                    txtSoLuong.getText().trim().isEmpty() || txtIdDanhMuc.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(editDialog, "Vui lòng điền đầy đủ các trường bắt buộc!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int giaValue, soLuongValue;
                try {
                    giaValue = Integer.parseInt(txtGia.getText().trim());
                    soLuongValue = Integer.parseInt(txtSoLuong.getText().trim());
                    if (giaValue < 0 || soLuongValue < 0) {
                        JOptionPane.showMessageDialog(editDialog, "Giá và số lượng không được âm!",
                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(editDialog, "Giá và số lượng phải là số nguyên!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                SanPhamDTO sp = new SanPhamDTO();
                sp.setIdSanPham(idSanPham);
                sp.setTenSanPham(txtTenSanPham.getText().trim());
                sp.setGia(giaValue);
                sp.setSoLuongTonKho(soLuongValue);
                sp.setIdDanhMuc(txtIdDanhMuc.getText().trim());
                sp.setHinhAnh(txtHinhAnh.getText().trim());
                sp.setIdNhaCungCap(txtIdNhaCungCap.getText().trim());

                SanPhamDAO dao = new SanPhamDAO();
                if (dao.update(sp)) {
                    JOptionPane.showMessageDialog(editDialog, "Sửa sản phẩm thành công!",
                            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    loadDataFromDatabase();
                    editDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(editDialog, "Sửa sản phẩm thất bại!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(editDialog, "Lỗi khi sửa: " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener(e -> editDialog.dispose());
        editDialog.setVisible(true);
    }

    private void searchSanPham(String searchType, String keyword) {
        if (keyword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        sanPhamTableModel.setRowCount(0);
        SanPhamDAO dao = new SanPhamDAO();
        ArrayList<SanPhamDTO> allData = dao.selectAll();

        for (SanPhamDTO sp : allData) {
            boolean match = false;
            if (sp.getSoLuongTonKho() > 0) {
                switch (searchType) {
                    case "ID sản phẩm":
                        if (sp.getIdSanPham().toLowerCase().contains(keyword.toLowerCase())) {
                            match = true;
                        }
                        break;
                    case "Tên sản phẩm":
                        if (sp.getTenSanPham().toLowerCase().contains(keyword.toLowerCase())) {
                            match = true;
                        }
                        break;
                }
                if (match) {
                    sanPhamTableModel.addRow(new Object[]{
                            sp.getIdSanPham(),
                            sp.getTenSanPham(),
                            sp.getGia(),
                            sp.getSoLuongTonKho(),
                            sp.getIdDanhMuc(),
                            sp.getHinhAnh(),
                            sp.getIdNhaCungCap()
                    });
                }
            }
        }

        if (sanPhamTableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm phù hợp",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void markProductOutOfStock(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idSanPham = sanPhamTableModel.getValueAt(selectedRow, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa sản phẩm: " + idSanPham + "?",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                SanPhamDAO dao = new SanPhamDAO();
                if (dao.delete(idSanPham)) {
                    sanPhamTableModel.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công!",
                            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa sản phẩm thất bại!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa: " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}