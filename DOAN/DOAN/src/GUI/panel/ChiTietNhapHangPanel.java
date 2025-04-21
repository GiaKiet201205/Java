package GUI.panel;

import DAO.ChiTietNhapHangDAO;
import DTO.ChiTietNhapHangDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class ChiTietNhapHangPanel extends JPanel {
    private JPanel mainPanel;
    private JTable table;
    private DefaultTableModel tableModel;
    private Color headerColor = new Color(200, 255, 200);
    private ChiTietNhapHangDAO chiTietNhapHangDAO;

    public ChiTietNhapHangPanel() {
        chiTietNhapHangDAO = new ChiTietNhapHangDAO();
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 400));
        createMainPanel();
        add(mainPanel, BorderLayout.CENTER);
        add(new JLabel("Chi Tiết Nhập Hàng", SwingConstants.CENTER), BorderLayout.NORTH);
    }

        // Bảng chi tiết nhập hàng
        String[] columns = {"ID", "Mã Nhập Hàng", "Mã Sản Phẩm", "Số Lượng Nhập", "Giá Nhập"};
        table = new JTable();
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        // Panel chứa nút
        JPanel panelButtons = new JPanel();
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        panelButtons.add(btnEdit);
        panelButtons.add(btnDelete);
        add(panelButtons, BorderLayout.SOUTH);
        
        // Thêm sự kiện cho các nút
        
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý sửa chi tiết nhập hàng
                editChiTietNhapHang();
            }
        });
        
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý xóa chi tiết nhập hàng
                deleteChiTietNhapHang();
            }
        });
        
        // Hiển thị dữ liệu chi tiết nhập hàng
    private void createMainPanel() {
        mainPanel = new JPanel(new BorderLayout());

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

        // Edit button
        JButton btnEdit = new JButton("Sửa");
        btnEdit.setToolTipText("Sửa chi tiết nhập hàng");
        buttonPanel.add(btnEdit);

        // Add labels below buttons
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labelPanel.setOpaque(false);
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
        JComboBox<String> cmbSearchType = new JComboBox<>(new String[]{"ID Chi Tiết", "Mã Nhập Hàng", "Mã Sản Phẩm"});
        cmbSearchType.setPreferredSize(new Dimension(120, 25));
        searchPanel.add(cmbSearchType);

        // Search text field
        JTextField txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(200, 25));
        searchPanel.add(txtSearch);

        // Search button
        JButton btnSearch = new JButton("Tìm kiếm");
        btnSearch.setBackground(new Color(240, 240, 240));
        searchPanel.add(btnSearch);

        // Reset button
        JButton btnReset = new JButton("Làm mới");
        btnReset.setBackground(new Color(240, 240, 240));
        searchPanel.add(btnReset);

        topPanel.add(searchPanel, BorderLayout.EAST);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Create table for chi tiết nhập hàng data
        String[] columns = {"ID Chi Tiết", "Mã Nhập Hàng", "Mã Sản Phẩm", "Số Lượng Nhập", "Giá Nhập"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(25);

        // Center the text in cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add action listeners for buttons
        btnSearch.addActionListener(e -> searchChiTietNhapHang(
                cmbSearchType.getSelectedItem().toString(),
                txtSearch.getText()));

        btnReset.addActionListener(e -> {
            txtSearch.setText("");
            tableModel.setRowCount(0);
            loadData(); // Reload all data after reset
        });

        btnEdit.addActionListener(e -> editChiTietNhapHang(table.getSelectedRow()));

        // Add listener for Enter key in search field
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchChiTietNhapHang(
                            cmbSearchType.getSelectedItem().toString(),
                            txtSearch.getText());
                }
            }
        });

        // Load data initially
        loadData();
    }

    private void loadData() {
        ArrayList<ChiTietNhapHangDTO> chiTietNhapHangList = chiTietNhapHangDAO.selectAll();
        tableModel.setRowCount(0); // Clear existing rows


    // Phương thức sửa chi tiết nhập hàng
    private void editChiTietNhapHang() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String idChiTietNhapHang = table.getValueAt(selectedRow, 0).toString();
            String idNhapHang = table.getValueAt(selectedRow, 1).toString();
            String idSanPham = table.getValueAt(selectedRow, 2).toString();
            int soLuongNhap = Integer.parseInt(table.getValueAt(selectedRow, 3).toString());
            int giaNhap = Integer.parseInt(table.getValueAt(selectedRow, 4).toString());

        for (ChiTietNhapHangDTO item : chiTietNhapHangList) {
            tableModel.addRow(new Object[]{
                    item.getIdChiTietNhapHang(),
                    item.getIdNhapHang(),
                    item.getIdSanPham(),
                    item.getSoLuongNhap(),
                    item.getGiaNhap()
            });
        }
    }

    private void searchChiTietNhapHang(String searchType, String keyword) {
        if (keyword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }


        tableModel.setRowCount(0);

        ArrayList<ChiTietNhapHangDTO> allData = chiTietNhapHangDAO.selectAll();

        for (ChiTietNhapHangDTO item : allData) {
            boolean match = false;
            switch (searchType) {
                case "ID Chi Tiết":
                    if (item.getIdChiTietNhapHang().toLowerCase().contains(keyword.toLowerCase())) {
                        match = true;
                    }
                    break;
                case "Mã Nhập Hàng":
                    if (item.getIdNhapHang().toLowerCase().contains(keyword.toLowerCase())) {
                        match = true;
                    }
                    break;
                case "Mã Sản Phẩm":
                    if (item.getIdSanPham().toLowerCase().contains(keyword.toLowerCase())) {
                        match = true;
                    }
                    break;
            }
            if (match) {
                tableModel.addRow(new Object[]{
                        item.getIdChiTietNhapHang(),
                        item.getIdNhapHang(),
                        item.getIdSanPham(),
                        item.getSoLuongNhap(),
                        item.getGiaNhap()
                });
            }
        }

        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy chi tiết nhập hàng phù hợp",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }


private void editChiTietNhapHang(int selectedRow) {
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một chi tiết nhập hàng để sửa",
                "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Lấy dữ liệu từ dòng được chọn
    String idChiTietNhapHang = tableModel.getValueAt(selectedRow, 0).toString();
    String idNhapHang = tableModel.getValueAt(selectedRow, 1).toString();
    String idSanPham = tableModel.getValueAt(selectedRow, 2).toString();
    String soLuongNhap = tableModel.getValueAt(selectedRow, 3).toString();
    String giaNhap = tableModel.getValueAt(selectedRow, 4).toString();

    // Tạo dialog sửa thông tin
    JDialog editDialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Sửa Chi Tiết Nhập Hàng", Dialog.ModalityType.APPLICATION_MODAL);
    editDialog.setSize(450, 400);
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

    // Tạo các thành phần giao diện
    JLabel lblIdChiTietNhapHang = new JLabel("ID Chi Tiết:");
    lblIdChiTietNhapHang.setFont(labelFont);
    JTextField txtIdChiTietNhapHang = new JTextField(idChiTietNhapHang, 20);
    txtIdChiTietNhapHang.setFont(fieldFont);
    txtIdChiTietNhapHang.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 1));
    txtIdChiTietNhapHang.setEditable(false);
    txtIdChiTietNhapHang.setBackground(new Color(220, 220, 220));

    JLabel lblIdNhapHang = new JLabel("Mã Nhập Hàng:");
    lblIdNhapHang.setFont(labelFont);
    JTextField txtIdNhapHang = new JTextField(idNhapHang, 20);
    txtIdNhapHang.setFont(fieldFont);
    txtIdNhapHang.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 1));
    txtIdNhapHang.setEditable(false);
    txtIdNhapHang.setBackground(new Color(220, 220, 220));

    JLabel lblIdSanPham = new JLabel("Mã Sản Phẩm:");
    lblIdSanPham.setFont(labelFont);
    JTextField txtIdSanPham = new JTextField(idSanPham, 20);
    txtIdSanPham.setFont(fieldFont);
    txtIdSanPham.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 1));

    JLabel lblSoLuongNhap = new JLabel("Số Lượng Nhập:");
    lblSoLuongNhap.setFont(labelFont);
    JTextField txtSoLuongNhap = new JTextField(soLuongNhap, 20);
    txtSoLuongNhap.setFont(fieldFont);
    txtSoLuongNhap.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 1));

    JLabel lblGiaNhap = new JLabel("Giá Nhập:");
    lblGiaNhap.setFont(labelFont);
    JTextField txtGiaNhap = new JTextField(giaNhap, 20);
    txtGiaNhap.setFont(fieldFont);
    txtGiaNhap.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 1));

    // Thêm các thành phần vào form
    gbc.gridx = 0; gbc.gridy = 0; formPanel.add(lblIdChiTietNhapHang, gbc);
    gbc.gridx = 1; formPanel.add(txtIdChiTietNhapHang, gbc);
    gbc.gridx = 0; gbc.gridy = 1; formPanel.add(lblIdNhapHang, gbc);
    gbc.gridx = 1; formPanel.add(txtIdNhapHang, gbc);
    gbc.gridx = 0; gbc.gridy = 2; formPanel.add(lblIdSanPham, gbc);
    gbc.gridx = 1; formPanel.add(txtIdSanPham, gbc);
    gbc.gridx = 0; gbc.gridy = 3; formPanel.add(lblSoLuongNhap, gbc);
    gbc.gridx = 1; formPanel.add(txtSoLuongNhap, gbc);
    gbc.gridx = 0; gbc.gridy = 4; formPanel.add(lblGiaNhap, gbc);
    gbc.gridx = 1; formPanel.add(txtGiaNhap, gbc);

    // Tạo panel chứa các nút
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

    // Xử lý sự kiện nút Hủy
    btnCancel.addActionListener(e -> editDialog.dispose());
    
    // Xử lý sự kiện nút Lưu
    btnSave.addActionListener(e -> {
        try {
            // Kiểm tra và chuyển đổi dữ liệu nhập vào
            String newIdSanPham = txtIdSanPham.getText().trim();
            if (newIdSanPham.isEmpty()) {
                JOptionPane.showMessageDialog(editDialog, "Mã sản phẩm không được để trống!", 
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int newSoLuong;
            try {
                newSoLuong = Integer.parseInt(txtSoLuongNhap.getText().trim());
                if (newSoLuong <= 0) {
                    JOptionPane.showMessageDialog(editDialog, "Số lượng phải lớn hơn 0!", 
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(editDialog, "Số lượng phải là số nguyên!", 
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            double newGiaNhap;
            try {
                newGiaNhap = Double.parseDouble(txtGiaNhap.getText().trim());
                if (newGiaNhap <= 0) {
                    JOptionPane.showMessageDialog(editDialog, "Giá nhập phải lớn hơn 0!", 
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(editDialog, "Giá nhập phải là số!", 
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Cập nhật dữ liệu vào bảng
            tableModel.setValueAt(newIdSanPham, selectedRow, 2);
            tableModel.setValueAt(newSoLuong, selectedRow, 3);
            tableModel.setValueAt(newGiaNhap, selectedRow, 4);
            
            JOptionPane.showMessageDialog(editDialog, "Cập nhật chi tiết nhập hàng thành công!", 
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            editDialog.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(editDialog, "Lỗi: " + ex.getMessage(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    });

    editDialog.setVisible(true);
}
}