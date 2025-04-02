/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        headerPanel.setBackground(new Color(159, 235, 124)); // Light green color
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
        employeePanel.add(txtEmployeeName, gbc);
        
        // Payment Method - thay thế bằng ComboBox
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblPaymentMethod = new JLabel("Phương thức tt");
        lblPaymentMethod.setFont(labelFont);
        employeePanel.add(lblPaymentMethod, gbc);
        
        gbc.gridx = 1;
        String[] paymentMethods = {"ATM", "Tiền mặt", "Chuyển khoản"};
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
        String[] statuses = {"Đã thanh toán", "Chưa thanh toán"};
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
        btnAddOrder.setBackground(new Color(159, 235, 124));
        btnAddOrder.setForeground(Color.WHITE);
        btnAddOrder.setPreferredSize(new Dimension(180, 50));
        btnAddOrder.setBorderPainted(false);
        btnAddOrder.setFocusPainted(false);
        buttonPanel.add(btnAddOrder);
        
        contentPanel.add(buttonPanel);
        
        // Add action listener for the button
        btnAddOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add your order processing logic here
                String paymentMethod = (String) cmbPaymentMethod.getSelectedItem();
                String status = (String) cmbStatus.getSelectedItem();
                
                JOptionPane.showMessageDialog(TrucTiepGUI.this, 
                    "Đơn hàng đã được thêm thành công!\n" +
                    "Phương thức: " + paymentMethod + "\n" +
                    "Trạng thái: " + status,
                    "Thông báo", 
                    JOptionPane.INFORMATION_MESSAGE);
                clearFields();
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