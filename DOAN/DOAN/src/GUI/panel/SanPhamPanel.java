package GUI.panel;

import DAO.SanPhamDAO;
import DTO.SanPhamDTO;
import config.JDBC;
import GUI.TrangChuGUI;

import javax.swing.*;
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
        this.trangChuGUI = trangChuGUI; // Lưu trữ tham chiếu đến TrangChuGUI
        setLayout(new BorderLayout());
        createSanPhamPanel(); // Khởi tạo giao diện
        add(sanPhamPanel, BorderLayout.CENTER); // Thêm panel chính vào layout
        loadDataFromDatabase(); // Tải dữ liệu từ cơ sở dữ liệu
    }

    private void createSanPhamPanel() {
        sanPhamPanel = new JPanel(new BorderLayout());

        // Top Panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(headerColor);
        topPanel.setPreferredSize(new Dimension(800, 80));

        // Function Panel
        JPanel functionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        functionPanel.setOpaque(false);

        JButton btnAdd = new JButton("Thêm");
        JButton btnEdit = new JButton("Sửa");
        JButton btnOutOfStock = new JButton("Xóa");

        functionPanel.add(btnAdd);
        functionPanel.add(btnEdit);
        functionPanel.add(btnOutOfStock);

        topPanel.add(functionPanel, BorderLayout.WEST);

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setOpaque(false);

        JComboBox<String> cmbSearchType = new JComboBox<>(new String[]{"ID sản phẩm", "Tên sản phẩm"});
        cmbSearchType.setPreferredSize(new Dimension(120, 25));
        JTextField txtSearchProduct = new JTextField(15);
        JButton btnSearch = new JButton("Tìm kiếm");
        JButton btnReset = new JButton("Làm mới");

        searchPanel.add(new JLabel("Tìm kiếm:"));
        searchPanel.add(cmbSearchType);
        searchPanel.add(txtSearchProduct);
        searchPanel.add(btnSearch);
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

        JScrollPane scrollPane = new JScrollPane(sanPhamTable);
        sanPhamPanel.add(scrollPane, BorderLayout.CENTER);

        // Actions
        btnAdd.addActionListener(e -> addSanPham());
        btnEdit.addActionListener(e -> editSanPham(sanPhamTable.getSelectedRow()));
        btnOutOfStock.addActionListener(e -> markProductOutOfStock(sanPhamTable.getSelectedRow()));
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
    sanPhamTableModel.setRowCount(0); // Xóa hết dữ liệu cũ trong bảng
    SanPhamDAO dao = new SanPhamDAO();
    ArrayList<SanPhamDTO> list = dao.selectAll(); // Select lại tất cả sản phẩm từ DB
    for (SanPhamDTO sp : list) {
        // Chỉ thêm các sản phẩm có số lượng tồn kho > 0
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
    sanPhamTable.repaint(); // Đảm bảo bảng được vẽ lại sau khi cập nhật
}

    private void addSanPham() {
        JTextField txtID = new JTextField();
        JTextField txtTen = new JTextField();
        JTextField txtGia = new JTextField();
        JTextField txtSoLuong = new JTextField();
        JTextField txtDanhMuc = new JTextField();
        JTextField txtHinhAnh = new JTextField();
        JButton btnChonAnh = new JButton("Chọn ảnh");
        btnChonAnh.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn ảnh sản phẩm");
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                txtHinhAnh.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        JTextField txtNhaCungCap = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(new JLabel("ID sản phẩm:"));
        panel.add(txtID);
        panel.add(new JLabel("Tên sản phẩm:"));
        panel.add(txtTen);
        panel.add(new JLabel("Giá:"));
        panel.add(txtGia);
        panel.add(new JLabel("Số lượng tồn kho:"));
        panel.add(txtSoLuong);
        panel.add(new JLabel("ID danh mục:"));
        panel.add(txtDanhMuc);
        panel.add(new JLabel("Hình ảnh:"));
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(txtHinhAnh, BorderLayout.CENTER);
        imagePanel.add(btnChonAnh, BorderLayout.EAST);
        panel.add(imagePanel);
        panel.add(new JLabel("ID nhà cung cấp:"));
        panel.add(txtNhaCungCap);

        int result = JOptionPane.showConfirmDialog(this, panel, "Thêm sản phẩm mới",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            // Validate input
            if (txtID.getText().trim().isEmpty() || txtTen.getText().trim().isEmpty() || txtGia.getText().trim().isEmpty() ||
                    txtSoLuong.getText().trim().isEmpty() || txtDanhMuc.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ các trường bắt buộc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int gia, soLuong;
            try {
                gia = Integer.parseInt(txtGia.getText().trim());
                soLuong = Integer.parseInt(txtSoLuong.getText().trim());
                if (gia < 0 || soLuong < 0) {
                    JOptionPane.showMessageDialog(this, "Giá và số lượng không được âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Giá và số lượng phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            SanPhamDTO sp = new SanPhamDTO();
            sp.setIdSanPham(txtID.getText().trim());
            sp.setTenSanPham(txtTen.getText().trim());
            sp.setGia(gia);
            sp.setSoLuongTonKho(soLuong);
            sp.setIdDanhMuc(txtDanhMuc.getText().trim());
            sp.setHinhAnh(txtHinhAnh.getText().trim());
            sp.setIdNhaCungCap(txtNhaCungCap.getText().trim());

            SanPhamDAO dao = new SanPhamDAO();
            if (dao.insert(sp)) {
                JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!");
                loadDataFromDatabase(); // Cập nhật bảng
                if (soLuong > 0) { // Chỉ thêm vào TrangChuGUI nếu số lượng tồn kho > 0
                    trangChuGUI.addProductToView(sp); // Thêm sản phẩm vào TrangChuGUI
                }
            } else {
                JOptionPane.showMessageDialog(this, "Thêm sản phẩm thất bại! Vui lòng kiểm tra ID nhà cung cấp.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editSanPham(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lấy dữ liệu từ bảng
        String idSanPham = sanPhamTableModel.getValueAt(selectedRow, 0).toString();
        String tenSanPham = sanPhamTableModel.getValueAt(selectedRow, 1).toString();
        int gia = Integer.parseInt(sanPhamTableModel.getValueAt(selectedRow, 2).toString());
        int soLuong = Integer.parseInt(sanPhamTableModel.getValueAt(selectedRow, 3).toString());
        String idDanhMuc = sanPhamTableModel.getValueAt(selectedRow, 4).toString();
        String hinhAnh = sanPhamTableModel.getValueAt(selectedRow, 5).toString();
        String idNhaCungCap = sanPhamTableModel.getValueAt(selectedRow, 6).toString();

        // Hiển thị hộp thoại nhập dữ liệu mới
        JTextField tenSanPhamField = new JTextField(tenSanPham);
        JTextField giaField = new JTextField(String.valueOf(gia));
        JTextField soLuongField = new JTextField(String.valueOf(soLuong));
        JTextField idDanhMucField = new JTextField(idDanhMuc);
        JTextField hinhAnhField = new JTextField(hinhAnh);
        JTextField idNhaCungCapField = new JTextField(idNhaCungCap);

        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("Tên sản phẩm:"));
        panel.add(tenSanPhamField);
        panel.add(new JLabel("Giá:"));
        panel.add(giaField);
        panel.add(new JLabel("Số lượng tồn kho:"));
        panel.add(soLuongField);
        panel.add(new JLabel("ID danh mục:"));
        panel.add(idDanhMucField);
        panel.add(new JLabel("Hình ảnh:"));
        panel.add(hinhAnhField);
        panel.add(new JLabel("ID nhà cung cấp:"));
        panel.add(idNhaCungCapField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Sửa sản phẩm", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                // Tạo DTO mới từ dữ liệu người dùng nhập
                SanPhamDTO sp = new SanPhamDTO();
                sp.setIdSanPham(idSanPham);
                sp.setTenSanPham(tenSanPhamField.getText());
                sp.setGia(Integer.parseInt(giaField.getText()));
                sp.setSoLuongTonKho(Integer.parseInt(soLuongField.getText()));
                sp.setIdDanhMuc(idDanhMucField.getText());
                sp.setHinhAnh(hinhAnhField.getText());
                sp.setIdNhaCungCap(idNhaCungCapField.getText());

                // Gọi DAO để cập nhật
                SanPhamDAO dao = new SanPhamDAO();
                if (dao.update(sp)) {
                    JOptionPane.showMessageDialog(this, "Sửa sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    loadDataFromDatabase(); // làm mới bảng
                } else {
                    JOptionPane.showMessageDialog(this, "Sửa sản phẩm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số cho giá và số lượng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void searchSanPham(String searchType, String keyword) {
        if (keyword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(sanPhamTableModel);
        sanPhamTable.setRowSorter(sorter);
        int columnIndex = searchType.equals("Tên sản phẩm") ? 1 : 0;
        RowFilter<TableModel, Object> rf = RowFilter.regexFilter("(?i)" + keyword, columnIndex);
        sorter.setRowFilter(rf);
    }

    private void markProductOutOfStock(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lấy ID sản phẩm từ bảng
        String idSanPham = sanPhamTableModel.getValueAt(selectedRow, 0).toString();

        // Xóa sản phẩm khỏi bảng
        sanPhamTableModel.removeRow(selectedRow);

        // Thông báo
        JOptionPane.showMessageDialog(this, "Xóa thành cong!");
    }
}