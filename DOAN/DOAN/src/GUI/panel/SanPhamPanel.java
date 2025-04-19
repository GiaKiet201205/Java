package GUI.panel;

import DAO.SanPhamDAO;
import DTO.SanPhamDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SanPhamPanel extends JPanel {
    private DefaultTableModel sanPhamTableModel;
    private JTable sanPhamTable;
    private JPanel sanPhamPanel;
    private Color headerColor = new Color(200, 255, 200);

    public SanPhamPanel() {
        setLayout(new BorderLayout());
        createSanPhamPanel();
        add(sanPhamPanel, BorderLayout.CENTER);
        loadDataFromDatabase();
    }

    private void createSanPhamPanel() {
        sanPhamPanel = new JPanel(new BorderLayout());

        // ===== TOP PANEL =====
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(headerColor);
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

        JLabel lblTimKiem = new JLabel("Tìm kiếm");
        searchPanel.add(lblTimKiem);

        JComboBox<String> cmbSearchType = new JComboBox<>(new String[]{"ID sản phẩm", "Tên sản phẩm"});
        cmbSearchType.setPreferredSize(new Dimension(120, 25));
        searchPanel.add(cmbSearchType);

        JTextField txtSearchProduct = new JTextField();
        txtSearchProduct.setPreferredSize(new Dimension(200, 25));
        searchPanel.add(txtSearchProduct);

        JButton btnSearch = new JButton("Tìm kiếm");
        searchPanel.add(btnSearch);

        JButton btnReset = new JButton("Làm mới");
        searchPanel.add(btnReset);

        topPanel.add(searchPanel, BorderLayout.EAST);

        sanPhamPanel.add(topPanel, BorderLayout.NORTH);

        // ===== TABLE =====
        String[] columns = {"ID sản phẩm", "Tên sản phẩm", "Giá", "Số lượng tồn kho", "ID danh mục", "Hình ảnh"};
        sanPhamTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        sanPhamTable = new JTable(sanPhamTableModel);
        sanPhamTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sanPhamTable.setRowHeight(25);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < sanPhamTable.getColumnCount(); i++) {
            sanPhamTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(sanPhamTable);
        sanPhamPanel.add(scrollPane, BorderLayout.CENTER);

        // ===== ACTIONS =====
        btnSearch.addActionListener(e -> searchSanPham(cmbSearchType.getSelectedItem().toString(), txtSearchProduct.getText()));

        btnReset.addActionListener(e -> {
            txtSearchProduct.setText("");
            loadDataFromDatabase();
        });

        btnAdd.addActionListener(e -> addSanPham());
        btnDelete.addActionListener(e -> deleteSanPham(sanPhamTable.getSelectedRow()));
        btnEdit.addActionListener(e -> editSanPham(sanPhamTable.getSelectedRow()));

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
            sanPhamTableModel.addRow(new Object[]{
                    sp.getIdSanPham(),
                    sp.getTenSanPham(),
                    sp.getGia(),
                    sp.getSoLuongTonKho(),
                    sp.getIdDanhMuc(),
                    sp.getHinhAnh()
            });
        }
    }

    private void addSanPham() {
        JTextField txtID = new JTextField();
        JTextField txtTen = new JTextField();
        JTextField txtGia = new JTextField();
        JTextField txtSoLuong = new JTextField();
        JTextField txtDanhMuc = new JTextField();
        JTextField txtHinhAnh = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2));
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
        panel.add(txtHinhAnh);

        int result = JOptionPane.showConfirmDialog(this, panel, "Thêm sản phẩm mới",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            SanPhamDTO sp = new SanPhamDTO();
            sp.setIdSanPham(txtID.getText());
            sp.setTenSanPham(txtTen.getText());
            sp.setGia(Integer.parseInt(txtGia.getText()));
            sp.setSoLuongTonKho(Integer.parseInt(txtSoLuong.getText()));
            sp.setIdDanhMuc(txtDanhMuc.getText());
            sp.setHinhAnh(txtHinhAnh.getText());
            sp.setIdNhaCungCap("");

            if (new SanPhamDAO().insert(sp)) {
                JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!");
                loadDataFromDatabase();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm sản phẩm thất bại!");
            }
        }
    }

    private void deleteSanPham(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm để xóa", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String productId = sanPhamTableModel.getValueAt(selectedRow, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa sản phẩm này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (new SanPhamDAO().delete(productId)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                loadDataFromDatabase();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        }
    }

    private void editSanPham(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm để sửa", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JTextField txtID = new JTextField(sanPhamTableModel.getValueAt(selectedRow, 0).toString());
        JTextField txtTen = new JTextField(sanPhamTableModel.getValueAt(selectedRow, 1).toString());
        JTextField txtGia = new JTextField(sanPhamTableModel.getValueAt(selectedRow, 2).toString());
        JTextField txtSoLuong = new JTextField(sanPhamTableModel.getValueAt(selectedRow, 3).toString());
        JTextField txtDanhMuc = new JTextField(sanPhamTableModel.getValueAt(selectedRow, 4).toString());
        JTextField txtHinhAnh = new JTextField(sanPhamTableModel.getValueAt(selectedRow, 5).toString());

        JPanel panel = new JPanel(new GridLayout(0, 2));
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
        panel.add(txtHinhAnh);

        int result = JOptionPane.showConfirmDialog(this, panel, "Sửa sản phẩm", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            SanPhamDTO sp = new SanPhamDTO();
            sp.setIdSanPham(txtID.getText());
            sp.setTenSanPham(txtTen.getText());
            sp.setGia(Integer.parseInt(txtGia.getText()));
            sp.setSoLuongTonKho(Integer.parseInt(txtSoLuong.getText()));
            sp.setIdDanhMuc(txtDanhMuc.getText());
            sp.setHinhAnh(txtHinhAnh.getText());
            sp.setIdNhaCungCap("");

            if (new SanPhamDAO().update(sp)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                loadDataFromDatabase();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            }
        }
    }

    private void searchSanPham(String searchType, String keyword) {
        if (keyword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(sanPhamTableModel);
        sanPhamTable.setRowSorter(sorter);

        int columnIndex = searchType.equals("Tên sản phẩm") ? 1 : 0;
        RowFilter<TableModel, Object> rf = RowFilter.regexFilter("(?i)" + keyword, columnIndex);
        sorter.setRowFilter(rf);
    }
}
