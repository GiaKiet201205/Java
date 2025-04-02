/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Component;

public class QuanTriVienGUI extends JFrame {
    private JPanel mainPanel, menuPanel, contentPanel;
    private JLabel lblAdmin;
    private JButton btnSanPham, btnHoaDon, btnChiTietHoaDon, btnNhaCungCap;
    private JButton btnNhapHang, btnChiTietNhapHang, btnNhanVien, btnKhachHang;
    private JButton btnDanhMuc, btnPhuongThucTT, btnThongKe, btnHome;
    private CardLayout cardLayout;
    private JPanel nhaCungCapPanel;
    private JPanel hoaDonPanel;
    private JTextField txtSearchID;
    private DefaultTableModel hoaDonTableModel;
    private JTable hoaDonTable;
    private JPanel sanPhamPanel; 
    private DefaultTableModel sanPhamTableModel;  
    private JTable sanPhamTable;  
    private JPanel chiTietHoaDonPanel;
    private DefaultTableModel chiTietHoaDonTableModel;
    private JTable chiTietHoaDonTable;
    private JPanel nhapHangPanel;
    private DefaultTableModel nhapHangTableModel;
    private JTable nhapHangTable;
    private JPanel chiTietNhapHangPanel;
    private DefaultTableModel chiTietNhapHangTableModel;
    private JTable chiTietNhapHangTable;
    private JPanel nhanVienPanel;
    private DefaultTableModel nhanVienTableModel;
    private JTable nhanVienTable;
    private JPanel khachHangPanel;
    private DefaultTableModel khachHangTableModel;
    private JTable khachHangTable;
    
    private Color menuColor = new Color(255, 255, 255);
    private Color headerColor = new Color(160, 250, 160);
    private Font menuFont = new Font("Arial", Font.PLAIN, 16);
    private Font titleFont = new Font("Arial", Font.BOLD, 24);
    private Font menuItemFont = new Font("Arial", Font.PLAIN, 16);
    
    public QuanTriVienGUI() {
        setTitle("Quản Trị Viên");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        
        // Main Panel with BorderLayout
        mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);
        
        // Create Menu Panel (Left side)
        createMenuPanel();
        
        // Create Content Panel (Right side) with CardLayout to switch between views
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Create different panels for each menu item
        createSanPhamPanel();
        createHoaDonPanel();
        createChiTietHoaDonPanel();
        createNhaCungCapPanel();
        createNhapHangPanel();
        createChiTietNhapHangPanel();
        createNhanVienPanel();
        createKhachHangPanel();
        
        // Add all panels to the CardLayout
        contentPanel.add(sanPhamPanel, "SanPham");
        contentPanel.add(nhaCungCapPanel, "NhaCungCap");
        contentPanel.add(hoaDonPanel,"HoaDon");
        contentPanel.add(chiTietHoaDonPanel, "ChiTietHoaDon");
        contentPanel.add(nhapHangPanel, "NhapHang");
        contentPanel.add(chiTietNhapHangPanel, "ChiTietNhapHang");
        contentPanel.add(nhanVienPanel, "NhanVien");
        contentPanel.add(khachHangPanel, "KhachHang");
        
        // Default view
        cardLayout.show(contentPanel, "SanPham");
    }
    
    private void createMenuPanel() {
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(menuColor);
        menuPanel.setPreferredSize(new Dimension(200, getHeight()));
        mainPanel.add(menuPanel, BorderLayout.WEST);
        
        // Admin Title
        JPanel adminPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Căn giữa
        adminPanel.setBackground(menuColor);
        adminPanel.setMaximumSize(new Dimension(200, 60));
        lblAdmin = new JLabel("Admin");
        lblAdmin.setFont(titleFont);
        adminPanel.add(lblAdmin);
        menuPanel.add(adminPanel);
        menuPanel.add(Box.createVerticalStrut(20));

        
        // Create menu items
        btnSanPham = createMenuButton("Sản phẩm");
        btnHoaDon = createMenuButton("Hóa đơn");
        btnChiTietHoaDon = createMenuButton("Chi tiết hóa đơn");
        btnNhaCungCap = createMenuButton("Nhà cung cấp");
        btnNhapHang = createMenuButton("Nhập hàng");
        btnChiTietNhapHang = createMenuButton("Chi tiết nhập hàng");
        btnNhanVien = createMenuButton("Nhân viên");
        btnKhachHang = createMenuButton("Khách hàng");
        btnDanhMuc = createMenuButton("Danh mục");
        btnPhuongThucTT = createMenuButton("Phương thức tt");
        btnThongKe = createMenuButton("Thống kê");
        
        // Add menu items to panel with some vertical space
        addMenuButton(btnSanPham);
        addMenuButton(btnHoaDon);
        addMenuButton(btnChiTietHoaDon);
        addMenuButton(btnNhaCungCap);
        addMenuButton(btnNhapHang);
        addMenuButton(btnChiTietNhapHang);
        addMenuButton(btnNhanVien);
        addMenuButton(btnKhachHang);
        addMenuButton(btnDanhMuc);
        addMenuButton(btnPhuongThucTT);
        addMenuButton(btnThongKe);
        
        // Add space filler
        menuPanel.add(Box.createVerticalGlue());
        
        // Add HOME button at the bottom
        JPanel homePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        homePanel.setBackground(menuColor);
        homePanel.setMaximumSize(new Dimension(200, 50));
        btnHome = new JButton("HOME");
        btnHome.setFont(new Font("Arial", Font.BOLD, 16));
        btnHome.setForeground(Color.RED);
        btnHome.setContentAreaFilled(false);
        btnHome.setBorderPainted(false);
        btnHome.setFocusPainted(false);
        homePanel.add(btnHome);
        menuPanel.add(homePanel);
        

    btnSanPham.addActionListener(e -> {
        cardLayout.show(contentPanel, "SanPham");
    });
    btnNhaCungCap.addActionListener(e -> {
            cardLayout.show(contentPanel, "NhaCungCap");
        });
        
    btnHoaDon.addActionListener(e -> {
        cardLayout.show(contentPanel, "HoaDon");
    });
    btnChiTietHoaDon.addActionListener(e -> {
        cardLayout.show(contentPanel, "ChiTietHoaDon");
    });
    btnNhapHang.addActionListener(e -> {
    cardLayout.show(contentPanel, "NhapHang");
    });
    btnChiTietNhapHang.addActionListener(e -> {
    cardLayout.show(contentPanel, "ChiTietNhapHang");
    });
    btnNhanVien.addActionListener(e -> {
    cardLayout.show(contentPanel, "NhanVien");
    });
    btnKhachHang.addActionListener(e -> {
    cardLayout.show(contentPanel, "KhachHang");
    });
    }
    
    
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(menuItemFont);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMaximumSize(new Dimension(200, 40));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        
        // Mouse listeners for hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(180, 180, 180));
                button.setContentAreaFilled(true);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setContentAreaFilled(false);
            }
        });
        
        return button;
    }
    
    private void addMenuButton(JButton button) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(menuColor);
        panel.setMaximumSize(new Dimension(200, 40));
        panel.add(button);
        menuPanel.add(panel);
    }
    
    private void createSanPhamPanel() {
    sanPhamPanel = new JPanel(new BorderLayout());
    
    // Create top panel for title and functions
    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.setBackground(headerColor);
    topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    
    // Add "Chức năng" label
    JLabel lblChucNang = new JLabel("Chức năng");
    topPanel.add(lblChucNang, BorderLayout.WEST);
    
    // Add function buttons
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    buttonPanel.setOpaque(false);
    
    // Add button
    JButton btnAdd = new JButton();
//    btnAdd.setIcon(new ImageIcon(getClass().getResource("/icons/add.png")));
    btnAdd.setToolTipText("Thêm");
    buttonPanel.add(btnAdd);
    
    // Delete button
    JButton btnDelete = new JButton();
//    btnDelete.setIcon(new ImageIcon(getClass().getResource("/icons/delete.png")));
    btnDelete.setToolTipText("Xóa");
    buttonPanel.add(btnDelete);
    
    // Edit button
    JButton btnEdit = new JButton();
//    btnEdit.setIcon(new ImageIcon(getClass().getResource("/icons/edit.png")));
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
    
    // Add search panel to top right
    JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    searchPanel.setOpaque(false);
    
    JLabel lblTimKiem = new JLabel("Tìm kiếm");
    searchPanel.add(lblTimKiem);
    
    // Create combo box with search options
    JComboBox<String> cmbSearchType = new JComboBox<>(new String[]{"ID sản phẩm", "Tên sản phẩm"});
    cmbSearchType.setPreferredSize(new Dimension(120, 25));
    searchPanel.add(cmbSearchType);
    
    // Search text field
    JTextField txtSearchProduct = new JTextField();
    txtSearchProduct.setPreferredSize(new Dimension(200, 25));
    searchPanel.add(txtSearchProduct);
    
    // Search button
    JButton btnSearch = new JButton("Tìm kiếm");
    btnSearch.setBackground(new Color(240, 240, 240));
    searchPanel.add(btnSearch);
    
    // Reset button
    JButton btnReset = new JButton("Làm mới");
    btnReset.setBackground(new Color(240, 240, 240));
    searchPanel.add(btnReset);
    
    topPanel.add(searchPanel, BorderLayout.EAST);
    
    sanPhamPanel.add(topPanel, BorderLayout.NORTH);
    
    // Create table for product data
    String[] columns = {"ID sản phẩm", "Tên sản phẩm", "Giá", "Số lượng tồn kho", "ID danh mục"};
    sanPhamTableModel = new DefaultTableModel(columns, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Make all cells non-editable
        }
    };
    
    sanPhamTable = new JTable(sanPhamTableModel);
    sanPhamTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    sanPhamTable.setRowHeight(25);
    
    // Center the text in cells
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    for (int i = 0; i < sanPhamTable.getColumnCount(); i++) {
        sanPhamTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }
    
    // Add sample data for demonstration
    addSampleSanPhamData();
    
    JScrollPane scrollPane = new JScrollPane(sanPhamTable);
    sanPhamPanel.add(scrollPane, BorderLayout.CENTER);
    
    // Add action listeners for buttons
    btnSearch.addActionListener(e -> searchSanPham(
            cmbSearchType.getSelectedItem().toString(), 
            txtSearchProduct.getText()));
    
    btnReset.addActionListener(e -> {
        txtSearchProduct.setText("");
        sanPhamTableModel.setRowCount(0);
        addSampleSanPhamData();
    });
    
    btnAdd.addActionListener(e -> addSanPham());
    btnDelete.addActionListener(e -> deleteSanPham(sanPhamTable.getSelectedRow()));
    btnEdit.addActionListener(e -> editSanPham(sanPhamTable.getSelectedRow()));
    
    // Add listener for Enter key in search field
    txtSearchProduct.addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                searchSanPham(
                        cmbSearchType.getSelectedItem().toString(), 
                        txtSearchProduct.getText());
            }
        }
    });
}

private void addSampleSanPhamData() {
    // Add sample data for demonstration
    Object[][] sampleData = {
        {"SP001", "Áo polo", "24,990,000 VND", "50", "DM001"},
        {"SP002", "Áo polo", "32,490,000 VND", "25", "DM002"},
        {"SP003", "Áo polo", "5,590,000 VND", "100", "DM003"},
        {"SP004", "Áo polo\"", "18,900,000 VND", "15", "DM004"},
        {"SP005", "Áo polo", "84,990,000 VND", "10", "DM005"},
        {"SP006", "Áo polo\"", "8,990,000 VND", "30", "DM002"},
        {"SP007", "Áo polo", "2,490,000 VND", "65", "DM002"},
        {"SP008", "Áo polo", "2,300,000 VND", "45", "DM002"}
    };
    
    for (Object[] row : sampleData) {
        sanPhamTableModel.addRow(row);
    }
}

private void searchSanPham(String searchType, String keyword) {
    if (keyword.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // In a real application, this would query the database
    // For demonstration, we'll just filter the existing rows
    sanPhamTableModel.setRowCount(0);
    
    // Determine which column to search based on search type
    int columnIndex = 0; // Default to ID column
    if (searchType.equals("Tên sản phẩm")) {
        columnIndex = 1;
    }
    
    // Get all sample data and filter
    Object[][] allData = {
        {"SP001", "Áo polo", "24,990,000 VND", "50", "DM001"},
        {"SP002", "Áo polo", "32,490,000 VND", "25", "DM002"},
        {"SP003", "Áo polo", "5,590,000 VND", "100", "DM003"},
        {"SP004", "Áo polo", "18,900,000 VND", "15", "DM004"},
        {"SP005", "Áo polo", "84,990,000 VND", "10", "DM005"},
        {"SP006", "Áo polo", "8,990,000 VND", "30", "DM002"},
        {"SP007", "Áo polo", "2,490,000 VND", "65", "DM002"},
        {"SP008", "Áo polo", "2,300,000 VND", "45", "DM002"}
    };
    
    final int finalColumnIndex = columnIndex;
    
    for (Object[] row : allData) {
        if (row[finalColumnIndex].toString().toLowerCase().contains(keyword.toLowerCase())) {
            sanPhamTableModel.addRow(row);
        }
    }
    
    if (sanPhamTableModel.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm phù hợp", 
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        // Restore all data
        for (Object[] row : allData) {
            sanPhamTableModel.addRow(row);
        }
    }
}

private void addSanPham() {
    // In a real application, this would open a form to add a new product
    JOptionPane.showMessageDialog(this, "Đang mở form thêm sản phẩm mới", 
            "Thêm sản phẩm", JOptionPane.INFORMATION_MESSAGE);
}

private void deleteSanPham(int selectedRow) {
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm để xóa", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    String productId = sanPhamTableModel.getValueAt(selectedRow, 0).toString();
    String productName = sanPhamTableModel.getValueAt(selectedRow, 1).toString();
    
    int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn xóa sản phẩm: " + productName + " (" + productId + ")?", 
            "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
    
    if (confirm == JOptionPane.YES_OPTION) {
        // In a real application, this would delete from the database
        sanPhamTableModel.removeRow(selectedRow);
        JOptionPane.showMessageDialog(this, "Đã xóa sản phẩm thành công", 
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
}

private void editSanPham(int selectedRow) {
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm để sửa", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    String productId = sanPhamTableModel.getValueAt(selectedRow, 0).toString();
    
    // In a real application, this would open a form to edit the product
    JOptionPane.showMessageDialog(this, "Đang mở form sửa sản phẩm: " + productId, 
            "Sửa sản phẩm", JOptionPane.INFORMATION_MESSAGE);
}
    
    private void createNhaCungCapPanel() {
        nhaCungCapPanel = new JPanel(new BorderLayout());
        
        // Create top panel for title and functions
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(headerColor);
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Add "Chức năng" label
        JLabel lblChucNang = new JLabel("Chức năng");
        topPanel.add(lblChucNang, BorderLayout.WEST);
        
        // Add function buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setOpaque(false);
        
        // Add button
        JButton btnAdd = new JButton();
//        btnAdd.setIcon(new ImageIcon(getClass().getResource("/icons/add.png")));
        btnAdd.setToolTipText("Thêm");
        buttonPanel.add(btnAdd);
        
        // Delete button
        JButton btnDelete = new JButton();
//        btnDelete.setIcon(new ImageIcon(getClass().getResource("/icons/delete.png")));
        btnDelete.setToolTipText("Xóa");
        buttonPanel.add(btnDelete);
        
        // Edit button
        JButton btnEdit = new JButton();
//        btnEdit.setIcon(new ImageIcon(getClass().getResource("/icons/edit.png")));
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
        
        // Add search panel to top right
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setOpaque(false);
        
        JLabel lblTimKiem = new JLabel("Tìm kiếm");
        searchPanel.add(lblTimKiem);
        
        // Combo box
        JComboBox<String> cmbSearch = new JComboBox<>(new String[]{"Tất cả"});
        cmbSearch.setPreferredSize(new Dimension(120, 25));
        searchPanel.add(cmbSearch);
        
        // Search text field
        JTextField txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(200, 25));
        searchPanel.add(txtSearch);
        
        // Search button
        JButton btnSearch = new JButton("Làm mới");
        btnSearch.setBackground(new Color(240, 240, 240));
        searchPanel.add(btnSearch);
        
        topPanel.add(searchPanel, BorderLayout.EAST);
        
        nhaCungCapPanel.add(topPanel, BorderLayout.NORTH);
        
        // Create table panel for supplier data
        String[] columns = {"Mã NCC", "Tên nhà cung cấp", "Số điện thoại", "Địa chỉ"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        
        nhaCungCapPanel.add(scrollPane, BorderLayout.CENTER);
    }
    
    private void createHoaDonPanel() {
    hoaDonPanel = new JPanel(new BorderLayout());
    
    // Create top panel for title and functions
    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.setBackground(headerColor);
    topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    
    // Add "Chức năng" label
//    JLabel lblChucNang = new JLabel("Chức năng");
//    topPanel.add(lblChucNang, BorderLayout.WEST);
    
    // Add function buttons
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    buttonPanel.setOpaque(false);
    
    // Edit button
    JButton btnEdit = new JButton();
//    btnEdit.setIcon(new ImageIcon(getClass().getResource("/icons/edit.png")));
    btnEdit.setToolTipText("Sửa");
    buttonPanel.add(btnEdit);
    
    // Add labels below buttons
    JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    labelPanel.setOpaque(false);
    labelPanel.add(new JLabel("Sửa đơn hàng"));
    
    JPanel functionPanel = new JPanel(new BorderLayout());
    functionPanel.setOpaque(false);
    functionPanel.add(buttonPanel, BorderLayout.NORTH);
    functionPanel.add(labelPanel, BorderLayout.SOUTH);
    
    topPanel.add(functionPanel, BorderLayout.CENTER);
    
    // Add search panel to top right
    JPanel searchPanel = new JPanel(new GridBagLayout());
    searchPanel.setOpaque(false);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(2, 5, 2, 5);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    
    // ID Search
    JLabel lblSearchID = new JLabel("Tìm theo ID:");
    gbc.gridx = 0;
    gbc.gridy = 0;
    searchPanel.add(lblSearchID, gbc);
    
    txtSearchID = new JTextField(10);
    gbc.gridx = 1;
    gbc.gridy = 0;
    searchPanel.add(txtSearchID, gbc);
    
    // Date Search
    JLabel lblSearchDate = new JLabel("Tìm theo ngày:");
    gbc.gridx = 0;
    gbc.gridy = 1;
    searchPanel.add(lblSearchDate, gbc);
    
    JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
    datePanel.setOpaque(false);
    
    JLabel lblFrom = new JLabel("Từ:");
    datePanel.add(lblFrom);
    
    JTextField dateFrom = new JTextField(10);
    datePanel.add(dateFrom);
    
    JLabel lblTo = new JLabel("Đến:");
    datePanel.add(lblTo);
    
    JTextField dateTo = new JTextField(10);
    datePanel.add(dateTo);
    
    gbc.gridx = 1;
    gbc.gridy = 1;
    gbc.gridwidth = 2;
    searchPanel.add(datePanel, gbc);
    
    // Search and Reset buttons
    JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    actionPanel.setOpaque(false);
    
    JButton btnSearch = new JButton("Tìm kiếm");
    btnSearch.setBackground(new Color(240, 240, 240));
    actionPanel.add(btnSearch);
    
    JButton btnReset = new JButton("Làm mới");
    btnReset.setBackground(new Color(240, 240, 240));
    actionPanel.add(btnReset);
    
    gbc.gridx = 1;
    gbc.gridy = 2;
    gbc.gridwidth = 2;
    searchPanel.add(actionPanel, gbc);
    
    topPanel.add(searchPanel, BorderLayout.EAST);
    
    hoaDonPanel.add(topPanel, BorderLayout.NORTH);
    
    // Create table panel for invoice data
    String[] columns = {"ID đơn hàng", "ID khách hàng", "Ngày đặt hàng", "Tổng tiền", "ID nhân viên", "Trạng thái"};
    hoaDonTableModel = new DefaultTableModel(columns, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Make all cells non-editable
        }
    };
    
    hoaDonTable = new JTable(hoaDonTableModel);
    hoaDonTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    hoaDonTable.setRowHeight(25);
    
    // Center the text in cells
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    for (int i = 0; i < hoaDonTable.getColumnCount(); i++) {
        hoaDonTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }
    
    // Add sample data for demonstration
    addSampleHoaDonData();
    
    JScrollPane scrollPane = new JScrollPane(hoaDonTable);
    hoaDonPanel.add(scrollPane, BorderLayout.CENTER);
    
    // Add action listeners for buttons
    btnSearch.addActionListener(e -> searchHoaDon(txtSearchID.getText(), dateFrom.getText(), dateTo.getText()));
    btnReset.addActionListener(e -> resetHoaDonSearch());
    btnEdit.addActionListener(e -> editHoaDon(hoaDonTable.getSelectedRow()));
    
    // Add listener for Enter key in search field
    txtSearchID.addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                searchHoaDon(txtSearchID.getText(), dateFrom.getText(), dateTo.getText());
            }
        }
    });
}

private void addSampleHoaDonData() {
    // Add some sample data for demonstration
    Object[][] sampleData = {
        {"HD001", "KH001", "01/04/2024", "1,500,000 VND", "NV001", "Đã thanh toán"},
        {"HD002", "KH002", "02/04/2024", "2,700,000 VND", "NV002", "Đang xử lý"},
        {"HD003", "KH001", "03/04/2024", "950,000 VND", "NV001", "Đã thanh toán"},
        {"HD004", "KH003", "05/04/2024", "3,200,000 VND", "NV003", "Đã hủy"},
        {"HD005", "KH002", "07/04/2024", "1,800,000 VND", "NV002", "Đã thanh toán"}
    };
    
    for (Object[] row : sampleData) {
        hoaDonTableModel.addRow(row);
    }
}

private void searchHoaDon(String idSearch, String fromDate, String toDate) {
    // Trong ứng dụng thực tế, đây sẽ là truy vấn cơ sở dữ liệu
    // Đối với demo, chỉ in ra các tham số tìm kiếm
    System.out.println("Tìm kiếm hóa đơn với:");
    System.out.println("ID chứa: " + idSearch);
    System.out.println("Từ ngày: " + fromDate);
    System.out.println("Đến ngày: " + toDate);
    
    // Thực tế sẽ lọc bảng hoặc truy vấn cơ sở dữ liệu
    JOptionPane.showMessageDialog(this, "Đã tìm kiếm theo các tiêu chí đã chọn", 
            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
}

private void resetHoaDonSearch() {
    txtSearchID.setText("");
    // Reset các ô nhập liệu date
    Component[] components = ((JPanel)hoaDonPanel.getComponent(0)).getComponent(2).getComponents();
    for (Component comp : components) {
        if (comp instanceof JPanel) {
            Component[] subComponents = ((JPanel)comp).getComponents();
            for (Component subComp : subComponents) {
                if (subComp instanceof JTextField) {
                    ((JTextField)subComp).setText("");
                }
            }
        }
    }
    
    // Làm mới bảng với tất cả dữ liệu
    // Trong ứng dụng thực tế, điều này sẽ tải lại tất cả dữ liệu từ cơ sở dữ liệu
    hoaDonTableModel.setRowCount(0);
    addSampleHoaDonData();
    
    JOptionPane.showMessageDialog(this, "Đã làm mới bộ lọc tìm kiếm", 
            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
}

private void editHoaDon(int selectedRow) {
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một đơn hàng để sửa", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    String invoiceId = hoaDonTableModel.getValueAt(selectedRow, 0).toString();
    
    // Trong ứng dụng thực tế, đây sẽ mở một form để chỉnh sửa hóa đơn
    JOptionPane.showMessageDialog(this, "Đang mở form sửa đơn hàng: " + invoiceId, 
            "Sửa đơn hàng", JOptionPane.INFORMATION_MESSAGE);
}

private void createChiTietHoaDonPanel() {
    chiTietHoaDonPanel = new JPanel(new BorderLayout());
    
    // Create top panel for title and functions
    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.setBackground(headerColor);
    topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    
    // Add "Chức năng" label
    JLabel lblChucNang = new JLabel("Chức năng");
    topPanel.add(lblChucNang, BorderLayout.WEST);
    
    // Add function buttons
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    buttonPanel.setOpaque(false);
    
    // Add button
    JButton btnAdd = new JButton();
//    btnAdd.setIcon(new ImageIcon(getClass().getResource("/icons/add.png")));
    btnAdd.setToolTipText("Thêm");
    buttonPanel.add(btnAdd);
    
    // Delete button
    JButton btnDelete = new JButton();
//    btnDelete.setIcon(new ImageIcon(getClass().getResource("/icons/delete.png")));
    btnDelete.setToolTipText("Xóa");
    buttonPanel.add(btnDelete);
    
    // Edit button
    JButton btnEdit = new JButton();
//    btnEdit.setIcon(new ImageIcon(getClass().getResource("/icons/edit.png")));
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
    
    // Add search panel to top right
    JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    searchPanel.setOpaque(false);
    
    JLabel lblTimKiem = new JLabel("Tìm kiếm");
    searchPanel.add(lblTimKiem);
    
    // Create combo box with search options
    JComboBox<String> cmbSearchType = new JComboBox<>(new String[]{"ID CTDH", "ID đơn hàng", "ID sản phẩm"});
    cmbSearchType.setPreferredSize(new Dimension(120, 25));
    searchPanel.add(cmbSearchType);
    
    // Search text field
    JTextField txtSearchCTHD = new JTextField();
    txtSearchCTHD.setPreferredSize(new Dimension(200, 25));
    searchPanel.add(txtSearchCTHD);
    
    // Search button
    JButton btnSearch = new JButton("Tìm kiếm");
    btnSearch.setBackground(new Color(240, 240, 240));
    searchPanel.add(btnSearch);
    
    // Reset button
    JButton btnReset = new JButton("Làm mới");
    btnReset.setBackground(new Color(240, 240, 240));
    searchPanel.add(btnReset);
    
    topPanel.add(searchPanel, BorderLayout.EAST);
    
    chiTietHoaDonPanel.add(topPanel, BorderLayout.NORTH);
    
    // Create table for invoice detail data
    String[] columns = {"ID CTDH", "ID đơn hàng", "ID sản phẩm", "Số lượng", "Giá bán", "Thành tiền"};
    chiTietHoaDonTableModel = new DefaultTableModel(columns, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Make all cells non-editable
        }
    };
    
    chiTietHoaDonTable = new JTable(chiTietHoaDonTableModel);
    chiTietHoaDonTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    chiTietHoaDonTable.setRowHeight(25);
    
    // Center the text in cells
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    for (int i = 0; i < chiTietHoaDonTable.getColumnCount(); i++) {
        chiTietHoaDonTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }
    
    // Add sample data for demonstration
    addSampleChiTietHoaDonData();
    
    JScrollPane scrollPane = new JScrollPane(chiTietHoaDonTable);
    chiTietHoaDonPanel.add(scrollPane, BorderLayout.CENTER);
    
    // Add action listeners for buttons
    btnSearch.addActionListener(e -> searchChiTietHoaDon(
            cmbSearchType.getSelectedItem().toString(), 
            txtSearchCTHD.getText()));
    
    btnReset.addActionListener(e -> {
        txtSearchCTHD.setText("");
        chiTietHoaDonTableModel.setRowCount(0);
        addSampleChiTietHoaDonData();
    });
    
    btnAdd.addActionListener(e -> addChiTietHoaDon());
    btnDelete.addActionListener(e -> deleteChiTietHoaDon(chiTietHoaDonTable.getSelectedRow()));
    btnEdit.addActionListener(e -> editChiTietHoaDon(chiTietHoaDonTable.getSelectedRow()));
    
    // Add listener for Enter key in search field
    txtSearchCTHD.addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                searchChiTietHoaDon(
                        cmbSearchType.getSelectedItem().toString(), 
                        txtSearchCTHD.getText());
            }
        }
    });
}

private void addSampleChiTietHoaDonData() {
    // Add sample data for demonstration
    Object[][] sampleData = {
        {"CTHD001", "HD001", "SP001", "2", "24,990,000 VND", "49,980,000 VND"},
        {"CTHD002", "HD001", "SP003", "1", "5,590,000 VND", "5,590,000 VND"},
        {"CTHD003", "HD002", "SP002", "1", "32,490,000 VND", "32,490,000 VND"},
        {"CTHD004", "HD002", "SP007", "3", "2,490,000 VND", "7,470,000 VND"},
        {"CTHD005", "HD003", "SP004", "1", "18,900,000 VND", "18,900,000 VND"},
        {"CTHD006", "HD004", "SP005", "1", "84,990,000 VND", "84,990,000 VND"},
        {"CTHD007", "HD005", "SP006", "2", "8,990,000 VND", "17,980,000 VND"},
        {"CTHD008", "HD005", "SP008", "3", "2,300,000 VND", "6,900,000 VND"}
    };
    
    for (Object[] row : sampleData) {
        chiTietHoaDonTableModel.addRow(row);
    }
}

private void searchChiTietHoaDon(String searchType, String keyword) {
    if (keyword.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // In a real application, this would query the database
    // For demonstration, we'll just filter the existing rows
    chiTietHoaDonTableModel.setRowCount(0);
    
    // Determine which column to search based on search type
    int columnIndex = 0; // Default to ID CTDH column
    if (searchType.equals("ID đơn hàng")) {
        columnIndex = 1;
    } else if (searchType.equals("ID sản phẩm")) {
        columnIndex = 2;
    }
    
    // Get all sample data and filter
    Object[][] allData = {
        {"CTHD001", "HD001", "SP001", "2", "24,990,000 VND", "49,980,000 VND"},
        {"CTHD002", "HD001", "SP003", "1", "5,590,000 VND", "5,590,000 VND"},
        {"CTHD003", "HD002", "SP002", "1", "32,490,000 VND", "32,490,000 VND"},
        {"CTHD004", "HD002", "SP007", "3", "2,490,000 VND", "7,470,000 VND"},
        {"CTHD005", "HD003", "SP004", "1", "18,900,000 VND", "18,900,000 VND"},
        {"CTHD006", "HD004", "SP005", "1", "84,990,000 VND", "84,990,000 VND"},
        {"CTHD007", "HD005", "SP006", "2", "8,990,000 VND", "17,980,000 VND"},
        {"CTHD008", "HD005", "SP008", "3", "2,300,000 VND", "6,900,000 VND"}
    };
    
    final int finalColumnIndex = columnIndex;
    
    for (Object[] row : allData) {
        if (row[finalColumnIndex].toString().toLowerCase().contains(keyword.toLowerCase())) {
            chiTietHoaDonTableModel.addRow(row);
        }
    }
    
    if (chiTietHoaDonTableModel.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "Không tìm thấy chi tiết hóa đơn phù hợp", 
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        // Restore all data
        for (Object[] row : allData) {
            chiTietHoaDonTableModel.addRow(row);
        }
    }
}

private void addChiTietHoaDon() {
    // In a real application, this would open a form to add a new invoice detail
    JOptionPane.showMessageDialog(this, "Đang mở form thêm chi tiết hóa đơn mới", 
            "Thêm chi tiết hóa đơn", JOptionPane.INFORMATION_MESSAGE);
}

private void deleteChiTietHoaDon(int selectedRow) {
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một chi tiết hóa đơn để xóa", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    String cthdId = chiTietHoaDonTableModel.getValueAt(selectedRow, 0).toString();
    String hdId = chiTietHoaDonTableModel.getValueAt(selectedRow, 1).toString();
    String spId = chiTietHoaDonTableModel.getValueAt(selectedRow, 2).toString();
    
    int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn xóa chi tiết hóa đơn: " + cthdId + " (Đơn hàng: " + hdId + ", Sản phẩm: " + spId + ")?", 
            "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
    
    if (confirm == JOptionPane.YES_OPTION) {
        // In a real application, this would delete from the database
        chiTietHoaDonTableModel.removeRow(selectedRow);
        JOptionPane.showMessageDialog(this, "Đã xóa chi tiết hóa đơn thành công", 
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
}

private void editChiTietHoaDon(int selectedRow) {
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một chi tiết hóa đơn để sửa", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    String cthdId = chiTietHoaDonTableModel.getValueAt(selectedRow, 0).toString();
    
    // In a real application, this would open a form to edit the invoice detail
    JOptionPane.showMessageDialog(this, "Đang mở form sửa chi tiết hóa đơn: " + cthdId, 
            "Sửa chi tiết hóa đơn", JOptionPane.INFORMATION_MESSAGE);
}

private void createNhapHangPanel() {
    nhapHangPanel = new JPanel(new BorderLayout());
    
    // Create top panel for title and functions
    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.setBackground(headerColor);
    topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    
    // Add "Chức năng" label
    JLabel lblChucNang = new JLabel("Chức năng");
    topPanel.add(lblChucNang, BorderLayout.WEST);
    
    // Add function buttons
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    buttonPanel.setOpaque(false);
    
    // Add button
    JButton btnAdd = new JButton();
//    btnAdd.setIcon(new ImageIcon(getClass().getResource("/icons/add.png")));
    btnAdd.setToolTipText("Thêm");
    buttonPanel.add(btnAdd);
    
    // Delete button
    JButton btnDelete = new JButton();
//    btnDelete.setIcon(new ImageIcon(getClass().getResource("/icons/delete.png")));
    btnDelete.setToolTipText("Xóa");
    buttonPanel.add(btnDelete);
    
    // Edit button
    JButton btnEdit = new JButton();
//    btnEdit.setIcon(new ImageIcon(getClass().getResource("/icons/edit.png")));
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
    
    // Add search panel to top right - using date search similar to HoaDon panel
    JPanel searchPanel = new JPanel(new GridBagLayout());
    searchPanel.setOpaque(false);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(2, 5, 2, 5);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    
    // ID Search
    JLabel lblSearchID = new JLabel("Tìm theo ID:");
    gbc.gridx = 0;
    gbc.gridy = 0;
    searchPanel.add(lblSearchID, gbc);
    
    JTextField txtSearchNhapHangID = new JTextField(10);
    gbc.gridx = 1;
    gbc.gridy = 0;
    searchPanel.add(txtSearchNhapHangID, gbc);
    
    // Date Search
    JLabel lblSearchDate = new JLabel("Tìm theo ngày nhập:");
    gbc.gridx = 0;
    gbc.gridy = 1;
    searchPanel.add(lblSearchDate, gbc);
    
    JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
    datePanel.setOpaque(false);
    
    JLabel lblFrom = new JLabel("Từ:");
    datePanel.add(lblFrom);
    
    JTextField dateFrom = new JTextField(10);
    datePanel.add(dateFrom);
    
    JLabel lblTo = new JLabel("Đến:");
    datePanel.add(lblTo);
    
    JTextField dateTo = new JTextField(10);
    datePanel.add(dateTo);
    
    gbc.gridx = 1;
    gbc.gridy = 1;
    gbc.gridwidth = 2;
    searchPanel.add(datePanel, gbc);
    
    // Search and Reset buttons
    JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    actionPanel.setOpaque(false);
    
    JButton btnSearch = new JButton("Tìm kiếm");
    btnSearch.setBackground(new Color(240, 240, 240));
    actionPanel.add(btnSearch);
    
    JButton btnReset = new JButton("Làm mới");
    btnReset.setBackground(new Color(240, 240, 240));
    actionPanel.add(btnReset);
    
    gbc.gridx = 1;
    gbc.gridy = 2;
    gbc.gridwidth = 2;
    searchPanel.add(actionPanel, gbc);
    
    topPanel.add(searchPanel, BorderLayout.EAST);
    
    nhapHangPanel.add(topPanel, BorderLayout.NORTH);
    
    // Create table for import data
    String[] columns = {"ID Nhập hàng", "ID NCC", "ID nhân viên", "Ngày nhập", "Tổng giá trị nhập"};
    nhapHangTableModel = new DefaultTableModel(columns, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Make all cells non-editable
        }
    };
    
    nhapHangTable = new JTable(nhapHangTableModel);
    nhapHangTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    nhapHangTable.setRowHeight(25);
    
    // Center the text in cells
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    for (int i = 0; i < nhapHangTable.getColumnCount(); i++) {
        nhapHangTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }
    
    // Add sample data for demonstration
    addSampleNhapHangData();
    
    JScrollPane scrollPane = new JScrollPane(nhapHangTable);
    nhapHangPanel.add(scrollPane, BorderLayout.CENTER);
    
    // Add action listeners for buttons
    btnSearch.addActionListener(e -> searchNhapHang(
            txtSearchNhapHangID.getText(), 
            dateFrom.getText(), 
            dateTo.getText()));
    
    btnReset.addActionListener(e -> {
        txtSearchNhapHangID.setText("");
        dateFrom.setText("");
        dateTo.setText("");
        nhapHangTableModel.setRowCount(0);
        addSampleNhapHangData();
    });
    
    btnAdd.addActionListener(e -> addNhapHang());
    btnDelete.addActionListener(e -> deleteNhapHang(nhapHangTable.getSelectedRow()));
    btnEdit.addActionListener(e -> editNhapHang(nhapHangTable.getSelectedRow()));
    
    // Add listener for Enter key in search field
    txtSearchNhapHangID.addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                searchNhapHang(
                        txtSearchNhapHangID.getText(), 
                        dateFrom.getText(), 
                        dateTo.getText());
            }
        }
    });
}

private void addSampleNhapHangData() {
    // Add sample data for demonstration
    Object[][] sampleData = {
        {"NH001", "NCC001", "NV001", "01/04/2024", "35,750,000 VND"},
        {"NH002", "NCC002", "NV002", "05/04/2024", "48,900,000 VND"},
        {"NH003", "NCC001", "NV001", "10/04/2024", "22,500,000 VND"},
        {"NH004", "NCC003", "NV003", "15/04/2024", "67,350,000 VND"},
        {"NH005", "NCC002", "NV002", "20/04/2024", "42,800,000 VND"},
        {"NH006", "NCC004", "NV001", "25/04/2024", "15,600,000 VND"},
        {"NH007", "NCC005", "NV003", "28/04/2024", "53,200,000 VND"}
    };
    
    for (Object[] row : sampleData) {
        nhapHangTableModel.addRow(row);
    }
}

private void searchNhapHang(String idSearch, String fromDate, String toDate) {
    // Trong ứng dụng thực tế, đây sẽ là truy vấn cơ sở dữ liệu
    // Cho demo, ta sẽ lọc dữ liệu mẫu
    nhapHangTableModel.setRowCount(0);
    
    // Lấy dữ liệu mẫu
    Object[][] allData = {
        {"NH001", "NCC001", "NV001", "01/04/2024", "35,750,000 VND"},
        {"NH002", "NCC002", "NV002", "05/04/2024", "48,900,000 VND"},
        {"NH003", "NCC001", "NV001", "10/04/2024", "22,500,000 VND"},
        {"NH004", "NCC003", "NV003", "15/04/2024", "67,350,000 VND"},
        {"NH005", "NCC002", "NV002", "20/04/2024", "42,800,000 VND"},
        {"NH006", "NCC004", "NV001", "25/04/2024", "15,600,000 VND"},
        {"NH007", "NCC005", "NV003", "28/04/2024", "53,200,000 VND"}
    };
    
    boolean hasIdSearch = !idSearch.trim().isEmpty();
    boolean hasDateSearch = !fromDate.trim().isEmpty() || !toDate.trim().isEmpty();
    
    // Nếu không có điều kiện tìm kiếm, hiển thị tất cả dữ liệu
    if (!hasIdSearch && !hasDateSearch) {
        for (Object[] row : allData) {
            nhapHangTableModel.addRow(row);
        }
        return;
    }
    
    // Lọc dữ liệu theo các điều kiện
    for (Object[] row : allData) {
        boolean matchId = true;
        boolean matchDate = true;
        
        // Kiểm tra điều kiện ID
        if (hasIdSearch) {
            matchId = row[0].toString().toLowerCase().contains(idSearch.toLowerCase());
        }
        
        // Kiểm tra điều kiện ngày (đơn giản hóa, trong thực tế sẽ phức tạp hơn)
        if (hasDateSearch) {
            String dateStr = row[3].toString();
            
            // Giả định ngày có định dạng dd/MM/yyyy
            if (!fromDate.trim().isEmpty() && !toDate.trim().isEmpty()) {
                // Cả hai ngày đều được chỉ định, kiểm tra nằm trong khoảng
                // Đây là kiểm tra đơn giản dựa trên chuỗi, trong thực tế sẽ phức tạp hơn
                matchDate = dateStr.compareTo(fromDate) >= 0 && dateStr.compareTo(toDate) <= 0;
            } else if (!fromDate.trim().isEmpty()) {
                // Chỉ có ngày bắt đầu
                matchDate = dateStr.compareTo(fromDate) >= 0;
            } else if (!toDate.trim().isEmpty()) {
                // Chỉ có ngày kết thúc
                matchDate = dateStr.compareTo(toDate) <= 0;
            }
        }
        
        // Thêm dòng vào bảng nếu thỏa mãn tất cả điều kiện
        if (matchId && matchDate) {
            nhapHangTableModel.addRow(row);
        }
    }
    
    // Thông báo nếu không tìm thấy kết quả
    if (nhapHangTableModel.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu nhập hàng phù hợp", 
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        // Khôi phục tất cả dữ liệu
        for (Object[] row : allData) {
            nhapHangTableModel.addRow(row);
        }
    }
}

private void addNhapHang() {
    // Trong ứng dụng thực tế, đây sẽ mở một form để tạo phiếu nhập hàng mới
    JOptionPane.showMessageDialog(this, "Đang mở form tạo phiếu nhập hàng mới", 
            "Thêm phiếu nhập hàng", JOptionPane.INFORMATION_MESSAGE);
}

private void deleteNhapHang(int selectedRow) {
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu nhập hàng để xóa", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    String nhapHangId = nhapHangTableModel.getValueAt(selectedRow, 0).toString();
    String nccId = nhapHangTableModel.getValueAt(selectedRow, 1).toString();
    String ngayNhap = nhapHangTableModel.getValueAt(selectedRow, 3).toString();
    
    int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn xóa phiếu nhập hàng: " + nhapHangId + 
            " (NCC: " + nccId + ", Ngày: " + ngayNhap + ")?", 
            "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
    
    if (confirm == JOptionPane.YES_OPTION) {
        // Trong ứng dụng thực tế, đây sẽ xóa dữ liệu từ cơ sở dữ liệu
        nhapHangTableModel.removeRow(selectedRow);
        JOptionPane.showMessageDialog(this, "Đã xóa phiếu nhập hàng thành công", 
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
}

private void editNhapHang(int selectedRow) {
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu nhập hàng để sửa", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    String nhapHangId = nhapHangTableModel.getValueAt(selectedRow, 0).toString();
    
    // Trong ứng dụng thực tế, đây sẽ mở một form để chỉnh sửa phiếu nhập hàng
    JOptionPane.showMessageDialog(this, "Đang mở form sửa phiếu nhập hàng: " + nhapHangId, 
            "Sửa phiếu nhập hàng", JOptionPane.INFORMATION_MESSAGE);
}

private void createChiTietNhapHangPanel() {
    chiTietNhapHangPanel = new JPanel(new BorderLayout());
    
    // Create top panel for title and functions
    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.setBackground(headerColor);
    topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    
    // Add "Chức năng" label
    JLabel lblChucNang = new JLabel("Chức năng");
    topPanel.add(lblChucNang, BorderLayout.WEST);
    
    // Add function buttons
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    buttonPanel.setOpaque(false);
    
    // Add button
    JButton btnAdd = new JButton();
//    btnAdd.setIcon(new ImageIcon(getClass().getResource("/icons/add.png")));
    btnAdd.setToolTipText("Thêm");
    buttonPanel.add(btnAdd);
    
    // Delete button
    JButton btnDelete = new JButton();
//    btnDelete.setIcon(new ImageIcon(getClass().getResource("/icons/delete.png")));
    btnDelete.setToolTipText("Xóa");
    buttonPanel.add(btnDelete);
    
    // Edit button
    JButton btnEdit = new JButton();
//    btnEdit.setIcon(new ImageIcon(getClass().getResource("/icons/edit.png")));
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
    
    // Add search panel to top right
    JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    searchPanel.setOpaque(false);
    
    JLabel lblTimKiem = new JLabel("Tìm kiếm");
    searchPanel.add(lblTimKiem);
    
    // Create combo box with search options
    JComboBox<String> cmbSearchType = new JComboBox<>(new String[]{"ID CTNH", "ID Nhập hàng", "ID sản phẩm"});
    cmbSearchType.setPreferredSize(new Dimension(120, 25));
    searchPanel.add(cmbSearchType);
    
    // Search text field
    JTextField txtSearchCTNH = new JTextField();
    txtSearchCTNH.setPreferredSize(new Dimension(200, 25));
    searchPanel.add(txtSearchCTNH);
    
    // Search button
    JButton btnSearch = new JButton("Tìm kiếm");
    btnSearch.setBackground(new Color(240, 240, 240));
    searchPanel.add(btnSearch);
    
    // Reset button
    JButton btnReset = new JButton("Làm mới");
    btnReset.setBackground(new Color(240, 240, 240));
    searchPanel.add(btnReset);
    
    topPanel.add(searchPanel, BorderLayout.EAST);
    
    chiTietNhapHangPanel.add(topPanel, BorderLayout.NORTH);
    
    // Create table for import detail data
    String[] columns = {"ID CTNH", "ID Nhập hàng", "ID sản phẩm", "Số lượng nhập", "Giá nhập", "Thành tiền"};
    chiTietNhapHangTableModel = new DefaultTableModel(columns, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Make all cells non-editable
        }
    };
    
    chiTietNhapHangTable = new JTable(chiTietNhapHangTableModel);
    chiTietNhapHangTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    chiTietNhapHangTable.setRowHeight(25);
    
    // Center the text in cells
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    for (int i = 0; i < chiTietNhapHangTable.getColumnCount(); i++) {
        chiTietNhapHangTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }
    
    JScrollPane scrollPane = new JScrollPane(chiTietNhapHangTable);
    chiTietNhapHangPanel.add(scrollPane, BorderLayout.CENTER);
    
    // Add action listeners for buttons
    btnSearch.addActionListener(e -> searchChiTietNhapHang(
            cmbSearchType.getSelectedItem().toString(), 
            txtSearchCTNH.getText()));
    
    btnReset.addActionListener(e -> {
        txtSearchCTNH.setText("");
        chiTietNhapHangTableModel.setRowCount(0);
    });
    
    btnAdd.addActionListener(e -> addChiTietNhapHang());
    btnDelete.addActionListener(e -> deleteChiTietNhapHang(chiTietNhapHangTable.getSelectedRow()));
    btnEdit.addActionListener(e -> editChiTietNhapHang(chiTietNhapHangTable.getSelectedRow()));
    
    // Add listener for Enter key in search field
    txtSearchCTNH.addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                searchChiTietNhapHang(
                        cmbSearchType.getSelectedItem().toString(), 
                        txtSearchCTNH.getText());
            }
        }
    });
}

private void searchChiTietNhapHang(String searchType, String keyword) {
    if (keyword.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // Trong ứng dụng thực tế, đây sẽ truy vấn cơ sở dữ liệu
    JOptionPane.showMessageDialog(this, 
            "Đang tìm kiếm chi tiết nhập hàng theo " + searchType + " với từ khóa: " + keyword,
            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
}

private void addChiTietNhapHang() {
    // Trong ứng dụng thực tế, đây sẽ mở một form để thêm chi tiết nhập hàng mới
    JOptionPane.showMessageDialog(this, "Đang mở form thêm chi tiết nhập hàng mới", 
            "Thêm chi tiết nhập hàng", JOptionPane.INFORMATION_MESSAGE);
}

private void deleteChiTietNhapHang(int selectedRow) {
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một chi tiết nhập hàng để xóa", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // Trong ứng dụng thực tế, đây sẽ lấy thông tin từ dòng được chọn
    JOptionPane.showMessageDialog(this, "Đã xóa chi tiết nhập hàng thành công", 
            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    chiTietNhapHangTableModel.removeRow(selectedRow);
}

private void editChiTietNhapHang(int selectedRow) {
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một chi tiết nhập hàng để sửa", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // Trong ứng dụng thực tế, đây sẽ lấy ID từ dòng được chọn
    JOptionPane.showMessageDialog(this, "Đang mở form sửa chi tiết nhập hàng", 
            "Sửa chi tiết nhập hàng", JOptionPane.INFORMATION_MESSAGE);
}
    
private void createNhanVienPanel() {
    nhanVienPanel = new JPanel(new BorderLayout());
    
    // Create top panel for title and functions
    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.setBackground(headerColor);
    topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    
    // Add "Chức năng" label
    JLabel lblChucNang = new JLabel("Chức năng");
    topPanel.add(lblChucNang, BorderLayout.WEST);
    
    // Add function buttons
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    buttonPanel.setOpaque(false);
    
    // Add button
    JButton btnAdd = new JButton();
//    btnAdd.setIcon(new ImageIcon(getClass().getResource("/icons/add.png")));
    btnAdd.setToolTipText("Thêm");
    buttonPanel.add(btnAdd);
    
    // Delete button
    JButton btnDelete = new JButton();
//    btnDelete.setIcon(new ImageIcon(getClass().getResource("/icons/delete.png")));
    btnDelete.setToolTipText("Xóa");
    buttonPanel.add(btnDelete);
    
    // Edit button
    JButton btnEdit = new JButton();
//    btnEdit.setIcon(new ImageIcon(getClass().getResource("/icons/edit.png")));
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
    
    // Add search panel to top right
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
    
    nhanVienPanel.add(topPanel, BorderLayout.NORTH);
    
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
    nhanVienPanel.add(scrollPane, BorderLayout.CENTER);
    
    // Add action listeners for buttons
    btnSearch.addActionListener(e -> searchNhanVien(
            cmbSearchType.getSelectedItem().toString(), 
            txtSearchNV.getText()));
    
    btnReset.addActionListener(e -> {
        txtSearchNV.setText("");
        nhanVienTableModel.setRowCount(0);
    });
    
    btnAdd.addActionListener(e -> addNhanVien());
    btnDelete.addActionListener(e -> deleteNhanVien(nhanVienTable.getSelectedRow()));
    btnEdit.addActionListener(e -> editNhanVien(nhanVienTable.getSelectedRow()));
    
    // Add listener for Enter key in search field
    txtSearchNV.addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                searchNhanVien(
                        cmbSearchType.getSelectedItem().toString(), 
                        txtSearchNV.getText());
            }
        }
    });
}

private void searchNhanVien(String searchType, String keyword) {
    if (keyword.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // Trong ứng dụng thực tế, đây sẽ truy vấn cơ sở dữ liệu
    JOptionPane.showMessageDialog(this, 
            "Đang tìm kiếm nhân viên theo " + searchType + " với từ khóa: " + keyword,
            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
}

private void addNhanVien() {
    // Trong ứng dụng thực tế, đây sẽ mở một form để thêm nhân viên mới
    JOptionPane.showMessageDialog(this, "Đang mở form thêm nhân viên mới", 
            "Thêm nhân viên", JOptionPane.INFORMATION_MESSAGE);
}

private void deleteNhanVien(int selectedRow) {
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên để xóa", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // Trong ứng dụng thực tế, đây sẽ lấy thông tin từ dòng được chọn
    JOptionPane.showMessageDialog(this, "Đã xóa nhân viên thành công", 
            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    nhanVienTableModel.removeRow(selectedRow);
}

private void editNhanVien(int selectedRow) {
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên để sửa", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // Trong ứng dụng thực tế, đây sẽ lấy ID từ dòng được chọn
    JOptionPane.showMessageDialog(this, "Đang mở form sửa thông tin nhân viên", 
            "Sửa nhân viên", JOptionPane.INFORMATION_MESSAGE);
}

private void createKhachHangPanel() {
    khachHangPanel = new JPanel(new BorderLayout());
    
    // Create top panel for title and functions
    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.setBackground(headerColor);
    topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    
    // Add "Chức năng" label
    JLabel lblChucNang = new JLabel("Chức năng");
    topPanel.add(lblChucNang, BorderLayout.WEST);
    
    // Add function buttons
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    buttonPanel.setOpaque(false);
    
    // Add button
    JButton btnAdd = new JButton();
//    btnAdd.setIcon(new ImageIcon(getClass().getResource("/icons/add.png")));
    btnAdd.setToolTipText("Thêm");
    buttonPanel.add(btnAdd);
    
    // Delete button
    JButton btnDelete = new JButton();
//    btnDelete.setIcon(new ImageIcon(getClass().getResource("/icons/delete.png")));
    btnDelete.setToolTipText("Xóa");
    buttonPanel.add(btnDelete);
    
    // Edit button
    JButton btnEdit = new JButton();
//    btnEdit.setIcon(new ImageIcon(getClass().getResource("/icons/edit.png")));
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
    
    // Add search panel to top right
    JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    searchPanel.setOpaque(false);
    
    JLabel lblTimKiem = new JLabel("Tìm kiếm");
    searchPanel.add(lblTimKiem);
    
    // Create combo box with search options
    JComboBox<String> cmbSearchType = new JComboBox<>(new String[]{"ID khách hàng", "Tên khách hàng", "SĐT"});
    cmbSearchType.setPreferredSize(new Dimension(120, 25));
    searchPanel.add(cmbSearchType);
    
    // Search text field
    JTextField txtSearchKH = new JTextField();
    txtSearchKH.setPreferredSize(new Dimension(200, 25));
    searchPanel.add(txtSearchKH);
    
    // Search button
    JButton btnSearch = new JButton("Tìm kiếm");
    btnSearch.setBackground(new Color(240, 240, 240));
    searchPanel.add(btnSearch);
    
    // Reset button
    JButton btnReset = new JButton("Làm mới");
    btnReset.setBackground(new Color(240, 240, 240));
    searchPanel.add(btnReset);
    
    topPanel.add(searchPanel, BorderLayout.EAST);
    
    khachHangPanel.add(topPanel, BorderLayout.NORTH);
    
    // Create table for customer data
    String[] columns = {"ID khách hàng", "Tên", "Email", "SĐT", "Password", "Username"};
    khachHangTableModel = new DefaultTableModel(columns, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Make all cells non-editable
        }
    };
    
    khachHangTable = new JTable(khachHangTableModel);
    khachHangTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    khachHangTable.setRowHeight(25);
    
    // Center the text in cells
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    for (int i = 0; i < khachHangTable.getColumnCount(); i++) {
        khachHangTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }
    
    // Add sample data for demonstration
    addSampleKhachHangData();
    
    JScrollPane scrollPane = new JScrollPane(khachHangTable);
    khachHangPanel.add(scrollPane, BorderLayout.CENTER);
    
    // Add action listeners for buttons
    btnSearch.addActionListener(e -> searchKhachHang(
            cmbSearchType.getSelectedItem().toString(),
            txtSearchKH.getText()));
    
    btnReset.addActionListener(e -> {
        txtSearchKH.setText("");
        khachHangTableModel.setRowCount(0);
        addSampleKhachHangData();
    });
    
    btnAdd.addActionListener(e -> addKhachHang());
    btnDelete.addActionListener(e -> deleteKhachHang(khachHangTable.getSelectedRow()));
    btnEdit.addActionListener(e -> editKhachHang(khachHangTable.getSelectedRow()));
    
    // Add listener for Enter key in search field
    txtSearchKH.addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                searchKhachHang(
                        cmbSearchType.getSelectedItem().toString(),
                        txtSearchKH.getText());
            }
        }
    });
}

private void addSampleKhachHangData() {
    // Add sample data for demonstration
    Object[][] sampleData = {
        {"KH001", "Nguyễn Văn A", "nguyenvana@email.com", "0901234567", "********", "nguyenvana"},
        {"KH002", "Trần Thị B", "tranthib@email.com", "0912345678", "********", "tranthib"},
        {"KH003", "Lê Văn C", "levanc@email.com", "0923456789", "********", "levanc"},
        {"KH004", "Phạm Thị D", "phamthid@email.com", "0934567890", "********", "phamthid"},
        {"KH005", "Hoàng Văn E", "hoangvane@email.com", "0945678901", "********", "hoangvane"},
        {"KH006", "Ngô Thị F", "ngothif@email.com", "0956789012", "********", "ngothif"},
        {"KH007", "Vũ Văn G", "vuvang@email.com", "0967890123", "********", "vuvang"},
        {"KH008", "Đặng Thị H", "dangthih@email.com", "0978901234", "********", "dangthih"}
    };
    
    for (Object[] row : sampleData) {
        khachHangTableModel.addRow(row);
    }
}

private void searchKhachHang(String searchType, String keyword) {
    if (keyword.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // In a real application, this would query the database
    // For demonstration, we'll just filter the existing rows
    khachHangTableModel.setRowCount(0);
    
    // Determine which column to search based on search type
    int columnIndex = 0; // Default to ID column
    if (searchType.equals("Tên khách hàng")) {
        columnIndex = 1;
    } else if (searchType.equals("SĐT")) {
        columnIndex = 3;
    }
    
    // Get all sample data and filter
    Object[][] allData = {
        {"KH001", "Nguyễn Văn A", "nguyenvana@email.com", "0901234567", "********", "nguyenvana"},
        {"KH002", "Trần Thị B", "tranthib@email.com", "0912345678", "********", "tranthib"},
        {"KH003", "Lê Văn C", "levanc@email.com", "0923456789", "********", "levanc"},
        {"KH004", "Phạm Thị D", "phamthid@email.com", "0934567890", "********", "phamthid"},
        {"KH005", "Hoàng Văn E", "hoangvane@email.com", "0945678901", "********", "hoangvane"},
        {"KH006", "Ngô Thị F", "ngothif@email.com", "0956789012", "********", "ngothif"},
        {"KH007", "Vũ Văn G", "vuvang@email.com", "0967890123", "********", "vuvang"},
        {"KH008", "Đặng Thị H", "dangthih@email.com", "0978901234", "********", "dangthih"}
    };
    
    final int finalColumnIndex = columnIndex;
    
    for (Object[] row : allData) {
        if (row[finalColumnIndex].toString().toLowerCase().contains(keyword.toLowerCase())) {
            khachHangTableModel.addRow(row);
        }
    }
    
    if (khachHangTableModel.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng phù hợp", 
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        // Restore all data
        for (Object[] row : allData) {
            khachHangTableModel.addRow(row);
        }
    }
}

private void addKhachHang() {
    // In a real application, this would open a form to add a new customer
    JOptionPane.showMessageDialog(this, "Đang mở form thêm khách hàng mới", 
            "Thêm khách hàng", JOptionPane.INFORMATION_MESSAGE);
}

private void deleteKhachHang(int selectedRow) {
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để xóa", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    String khId = khachHangTableModel.getValueAt(selectedRow, 0).toString();
    String khName = khachHangTableModel.getValueAt(selectedRow, 1).toString();
    
    int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn xóa khách hàng: " + khName + " (" + khId + ")?", 
            "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
    
    if (confirm == JOptionPane.YES_OPTION) {
        // In a real application, this would delete from the database
        khachHangTableModel.removeRow(selectedRow);
        JOptionPane.showMessageDialog(this, "Đã xóa khách hàng thành công", 
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
}

private void editKhachHang(int selectedRow) {
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để sửa", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    String khId = khachHangTableModel.getValueAt(selectedRow, 0).toString();
    
    // In a real application, this would open a form to edit the customer
    JOptionPane.showMessageDialog(this, "Đang mở form sửa khách hàng: " + khId, 
            "Sửa khách hàng", JOptionPane.INFORMATION_MESSAGE);
}

    // Main method to test the GUI
    public static void main(String[] args) {
        try {
            // Set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            QuanTriVienGUI gui = new QuanTriVienGUI();
            gui.setVisible(true);
        });
    }
}