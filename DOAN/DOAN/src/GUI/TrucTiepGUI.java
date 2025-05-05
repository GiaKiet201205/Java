package GUI;

import BLL.HoaDonBLL;
import BLL.NhanVienBLL;
import BLL.SanPhamBLL;

import DAO.ChiTietDonHangDAO;
import DTO.ChiTietDonHangDTO;
import DTO.DonHangDTO;
import DTO.NhanVienDTO;
import DTO.SanPhamDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrucTiepGUI extends JFrame {
    private JTextField txtProductID, txtProductName, txtPrice, txtQuantity;
    private JTextField txtTotal;
    private JTextField txtEmployeeID, txtEmployeeName;
    private JComboBox<String> cmbPaymentMethod, cmbStatus;
    private JButton btnAddOrder;
    
    // Tạo font với cỡ chữ lớn hơn
    private Font labelFont = new Font("Arial", Font.PLAIN, 14);
    private Font textFieldFont = new Font("Arial", Font.PLAIN, 14);
    private Font buttonFont = new Font("Arial", Font.BOLD, 16);
    private Font comboBoxFont = new Font("Arial", Font.PLAIN, 14);

    // Khởi tạo BLL và DAO
    private SanPhamBLL sanPhamBLL = new SanPhamBLL();
    private NhanVienBLL nhanVienBLL = new NhanVienBLL();
    private HoaDonBLL hoaDonBLL = new HoaDonBLL();
    private ChiTietDonHangDAO chiTietDonHangDAO = new ChiTietDonHangDAO();

    public TrucTiepGUI() {
        setTitle("QUẢN LÝ BÁN HÀNG");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        
        // Main panel with border layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        setContentPane(mainPanel);
        
        // Header panel with title
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(100, 200, 100)); // Light green color
        headerPanel.setPreferredSize(new Dimension(800, 80));
        JLabel titleLabel = new JLabel("QUẢN LÝ BÁN HÀNG");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(20, 50, 20, 50));
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Product information panel
        JPanel productPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);
        
        // Product ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblProductID = new JLabel("ID sản phẩm");
        lblProductID.setFont(labelFont);
        productPanel.add(lblProductID, gbc);
        
        gbc.gridx = 1;
        txtProductID = new JTextField(15);
        txtProductID.setFont(textFieldFont);
        txtProductID.setBackground(new Color(240, 240, 240));
        productPanel.add(txtProductID, gbc);
        
        // Product Name
        gbc.gridx = 2;
        JLabel lblProductName = new JLabel("Tên sản phẩm");
        lblProductName.setFont(labelFont);
        productPanel.add(lblProductName, gbc);
        
        gbc.gridx = 3;
        txtProductName = new JTextField(15);
        txtProductName.setFont(textFieldFont);
        txtProductName.setBackground(new Color(240, 240, 240));
        txtProductName.setEditable(false); // Không cho chỉnh sửa
        productPanel.add(txtProductName, gbc);
        
        // Price
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblPrice = new JLabel("Giá(VND)");
        lblPrice.setFont(labelFont);
        productPanel.add(lblPrice, gbc);
        
        gbc.gridx = 1;
        txtPrice = new JTextField(15);
        txtPrice.setFont(textFieldFont);
        txtPrice.setBackground(new Color(240, 240, 240));
        txtPrice.setEditable(false); // Không cho chỉnh sửa
        productPanel.add(txtPrice, gbc);
        
        // Quantity
        gbc.gridx = 2;
        JLabel lblQuantity = new JLabel("Số lượng");
        lblQuantity.setFont(labelFont);
        productPanel.add(lblQuantity, gbc);
        
        gbc.gridx = 3;
        txtQuantity = new JTextField(15);
        txtQuantity.setFont(textFieldFont);
        txtQuantity.setBackground(new Color(240, 240, 240));
        productPanel.add(txtQuantity, gbc);
        
        contentPanel.add(productPanel);
        
        // Total panel
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTotal = new JLabel("Tổng tiền");
        lblTotal.setFont(labelFont);
        totalPanel.add(lblTotal);
        
        txtTotal = new JTextField(15);
        txtTotal.setFont(textFieldFont);
        txtTotal.setBackground(new Color(240, 240, 240));
        txtTotal.setEditable(false); // Không cho chỉnh sửa
        totalPanel.add(txtTotal);
        contentPanel.add(totalPanel);
        
        // Add some vertical space
        contentPanel.add(Box.createVerticalStrut(30));
        
        // Employee information panel
        JPanel employeePanel = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);
        
        // Employee ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblEmployeeID = new JLabel("ID nhân viên bán");
        lblEmployeeID.setFont(labelFont);
        employeePanel.add(lblEmployeeID, gbc);
        
        gbc.gridx = 1;
        txtEmployeeID = new JTextField(15);
        txtEmployeeID.setFont(textFieldFont);
        txtEmployeeID.setBackground(new Color(240, 240, 240));
        employeePanel.add(txtEmployeeID, gbc);
        
        // Employee Name
        gbc.gridx = 2;
        JLabel lblEmployeeName = new JLabel("Tên nhân viên bán");
        lblEmployeeName.setFont(labelFont);
        employeePanel.add(lblEmployeeName, gbc);
        
        gbc.gridx = 3;
        txtEmployeeName = new JTextField(15);
        txtEmployeeName.setFont(textFieldFont);
        txtEmployeeName.setBackground(new Color(240, 240, 240));
        txtEmployeeName.setEditable(false); // Không cho chỉnh sửa
        employeePanel.add(txtEmployeeName, gbc);
        
        // Payment Method - thay thế bằng ComboBox
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblPaymentMethod = new JLabel("Phương thức tt");
        lblPaymentMethod.setFont(labelFont);
        employeePanel.add(lblPaymentMethod, gbc);
        
        gbc.gridx = 1;
        String[] paymentMethods = {"Tiền mặt", "Chuyển khoản"};
        cmbPaymentMethod = new JComboBox<>(paymentMethods);
        cmbPaymentMethod.setFont(comboBoxFont);
        cmbPaymentMethod.setBackground(new Color(240, 240, 240));
        cmbPaymentMethod.setPreferredSize(new Dimension(txtEmployeeID.getPreferredSize().width, 30));
        employeePanel.add(cmbPaymentMethod, gbc);
        
        // Status - thay thế bằng ComboBox
        gbc.gridx = 2;
        JLabel lblStatus = new JLabel("Trạng thái tt");
        lblStatus.setFont(labelFont);
        employeePanel.add(lblStatus, gbc);
        
        gbc.gridx = 3;
        String[] statuses = {"Đã thanh toán"};
        cmbStatus = new JComboBox<>(statuses);
        cmbStatus.setFont(comboBoxFont);
        cmbStatus.setBackground(new Color(240, 240, 240));
        cmbStatus.setPreferredSize(new Dimension(txtEmployeeName.getPreferredSize().width, 30));
        employeePanel.add(cmbStatus, gbc);
        
        contentPanel.add(employeePanel);
        
        // Add some vertical space
        contentPanel.add(Box.createVerticalStrut(60));
        
        // Add order button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnAddOrder = new JButton("Thêm đơn hàng");
        btnAddOrder.setFont(buttonFont);
        btnAddOrder.setBackground(new Color(100, 200, 100));
        btnAddOrder.setForeground(Color.WHITE);
        btnAddOrder.setPreferredSize(new Dimension(180, 50));
        btnAddOrder.setBorderPainted(false);
        btnAddOrder.setFocusPainted(false);
        buttonPanel.add(btnAddOrder);
        
        contentPanel.add(buttonPanel);
        
        // Thêm DocumentListener để tự động điền thông tin sản phẩm
        txtProductID.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateProductInfo();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateProductInfo();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateProductInfo();
            }

            private void updateProductInfo() {
                String idSanPham = txtProductID.getText().trim();
                if (!idSanPham.isEmpty()) {
                    SanPhamDTO sp = sanPhamBLL.getSanPhamById(idSanPham);
                    if (sp != null) {
                        txtProductName.setText(sp.getTenSanPham());
                        txtPrice.setText(String.valueOf(sp.getGia()));
                        updateTotal();
                    } else {
                        txtProductName.setText("");
                        txtPrice.setText("");
                        txtTotal.setText("");
                    }
                } else {
                    txtProductName.setText("");
                    txtPrice.setText("");
                    txtTotal.setText("");
                }
            }
        });

        // Thêm DocumentListener để tự động điền thông tin nhân viên
        txtEmployeeID.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateEmployeeInfo();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateEmployeeInfo();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateEmployeeInfo();
            }

            private void updateEmployeeInfo() {
                String idNhanVien = txtEmployeeID.getText().trim();
                if (!idNhanVien.isEmpty()) {
                    NhanVienDTO nv = nhanVienBLL.getNhanVienById(idNhanVien);
                    if (nv != null) {
                        txtEmployeeName.setText(nv.getHoTenNV());
                    } else {
                        txtEmployeeName.setText("");
                    }
                } else {
                    txtEmployeeName.setText("");
                }
            }
        });

        // Thêm DocumentListener để tự động tính tổng tiền
        txtQuantity.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTotal();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTotal();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateTotal();
            }
        });

        // Add action listener for the button
        btnAddOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Kiểm tra tính hợp lệ của dữ liệu trước khi thêm đơn hàng
                String idSanPham = txtProductID.getText().trim();
                String idNhanVien = txtEmployeeID.getText().trim();
                String quantityStr = txtQuantity.getText().trim();
                String totalStr = txtTotal.getText().trim();
                int soLuongMua = Integer.parseInt(txtQuantity.getText().trim());

                // Lấy sản phẩm hiện tại
                SanPhamDTO sanPham = sanPhamBLL.getSanPhamById(idSanPham);
                if (sanPham == null) {
                    JOptionPane.showMessageDialog(null, "Sản phẩm không tồn tại!");
                    return;
                }

                int tonKhoHienTai = sanPham.getSoLuongTonKho();
                if (soLuongMua > tonKhoHienTai) {
                    JOptionPane.showMessageDialog(null, "Không đủ hàng trong kho!");
                    return;
                }

                // Kiểm tra các trường bắt buộc
                if (idSanPham.isEmpty() || idNhanVien.isEmpty() || quantityStr.isEmpty() || totalStr.isEmpty()) {
                    JOptionPane.showMessageDialog(TrucTiepGUI.this, 
                        "Vui lòng nhập đầy đủ thông tin!", 
                        "Lỗi", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Kiểm tra ID sản phẩm
                SanPhamDTO sp = sanPhamBLL.getSanPhamById(idSanPham);
                if (sp == null) {
                    JOptionPane.showMessageDialog(TrucTiepGUI.this, 
                        "ID sản phẩm không hợp lệ!", 
                        "Lỗi", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Kiểm tra ID nhân viên
                NhanVienDTO nv = nhanVienBLL.getNhanVienById(idNhanVien);
                if (nv == null) {
                    JOptionPane.showMessageDialog(TrucTiepGUI.this, 
                        "ID nhân viên không hợp lệ!", 
                        "Lỗi", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Kiểm tra số lượng
                int quantity;
                try {
                    quantity = Integer.parseInt(quantityStr);
                    if (quantity <= 0) {
                        JOptionPane.showMessageDialog(TrucTiepGUI.this, 
                            "Số lượng phải lớn hơn 0!", 
                            "Lỗi", 
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(TrucTiepGUI.this, 
                        "Số lượng phải là số nguyên!", 
                        "Lỗi", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Kiểm tra tổng tiền
                int tongTien;
                try {
                    tongTien = Integer.parseInt(totalStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(TrucTiepGUI.this, 
                        "Tổng tiền không hợp lệ!", 
                        "Lỗi", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Tạo đối tượng DonHangDTO
                DonHangDTO donHang = new DonHangDTO();

                // Lấy thông tin từ giao diện
                String trangThai = (String) cmbStatus.getSelectedItem();
                String hinhThucMuaHang = "Trực tiếp";
                String diaDiemGiao = "Tại cửa hàng";

                // Gọi createDonHang với idKhachHang là null
                String idDonHang = hoaDonBLL.createDonHang(donHang, "Admin", idNhanVien, tongTien, 
                                                          trangThai, hinhThucMuaHang, diaDiemGiao);

                if (idDonHang != null) {
            // Tạo đối tượng ChiTietDonHangDTO
            ChiTietDonHangDTO chiTiet = new ChiTietDonHangDTO();
            chiTiet.setIdDonHang(idDonHang);
            chiTiet.setIdSanPham(idSanPham);
            chiTiet.setSoLuong(quantity);
            chiTiet.setGiaBan(sp.getGia());

            // Lưu chi tiết đơn hàng
            boolean chiTietSuccess = chiTietDonHangDAO.insert(chiTiet);
            if (chiTietSuccess) {
                JOptionPane.showMessageDialog(TrucTiepGUI.this, 
                    "Đơn hàng đã được thêm thành công!\n" +
                    "ID đơn hàng: " + idDonHang + "\n" +
                    "Hình thức: " + hinhThucMuaHang + "\n" +
                    "Trạng thái: " + trangThai,
                    "Thông báo", 
                    JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                    } else {
                        // Xóa đơn hàng nếu lưu chi tiết thất bại
                        hoaDonBLL.deleteDonHang(idDonHang);
                        JOptionPane.showMessageDialog(TrucTiepGUI.this, 
                            "Lỗi khi lưu chi tiết đơn hàng!", 
                            "Lỗi", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(TrucTiepGUI.this, 
                        "Lỗi khi thêm đơn hàng vào cơ sở dữ liệu!", 
                        "Lỗi", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        // Footer panel with border
        JPanel footerPanel = new JPanel();
        footerPanel.setPreferredSize(new Dimension(900, 10));
        footerPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLUE));
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
    }
    
    private void clearFields() {
        txtProductID.setText("");
        txtProductName.setText("");
        txtPrice.setText("");
        txtQuantity.setText("");
        txtTotal.setText("");
        txtEmployeeID.setText("");
        txtEmployeeName.setText("");
        cmbPaymentMethod.setSelectedIndex(0);
        cmbStatus.setSelectedIndex(0);
    }

    private void updateTotal() {
        try {
            String priceStr = txtPrice.getText().trim();
            String quantityStr = txtQuantity.getText().trim();
            if (!priceStr.isEmpty() && !quantityStr.isEmpty()) {
                int price = Integer.parseInt(priceStr);
                int quantity = Integer.parseInt(quantityStr);
                if (quantity > 0) {
                    int total = price * quantity;
                    txtTotal.setText(String.valueOf(total));
                } else {
                    txtTotal.setText("");
                }
            } else {
                txtTotal.setText("");
            }
        } catch (NumberFormatException e) {
            txtTotal.setText("");
        }
    }
    
    public static void main(String[] args) {
        // Set look and feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TrucTiepGUI().setVisible(true);
            }
        });
    }
}