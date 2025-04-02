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
    
    private Color menuColor = new Color(204, 204, 204);
    private Color headerColor = new Color(240, 240, 240);
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
        createNhaCungCapPanel();
        createHoaDonPanel();
        createSanPhamPanel();
        
        // Add all panels to the CardLayout
        contentPanel.add(sanPhamPanel, "SanPham");
        contentPanel.add(nhaCungCapPanel, "NhaCungCap");
        contentPanel.add(hoaDonPanel,"HoaDon");
        
        // Default view
        cardLayout.show(contentPanel, "NhaCungCap");
    }
    
    private void createMenuPanel() {
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(menuColor);
        menuPanel.setPreferredSize(new Dimension(200, getHeight()));
        mainPanel.add(menuPanel, BorderLayout.WEST);
        
        // Admin Title
        JPanel adminPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
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