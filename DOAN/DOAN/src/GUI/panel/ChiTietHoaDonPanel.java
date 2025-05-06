package GUI.panel;

import DAO.ChiTietDonHangDAO;
import DTO.ChiTietDonHangDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ChiTietHoaDonPanel extends JPanel {
    private DefaultTableModel chiTietTableModel;
    private JTable chiTietTable;
    private JPanel chiTietPanel;
    private Color headerColor = new Color(200, 255, 200);
    private ChiTietDonHangDAO chiTietDonHangDAO = new ChiTietDonHangDAO();
    private JTextField txtSearch;

    public ChiTietHoaDonPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 400));
        createChiTietPanel();
        add(chiTietPanel, BorderLayout.CENTER);
        loadDataToTable();
    }

    private void createChiTietPanel() {
        chiTietPanel = new JPanel(new BorderLayout());

        // Top Panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(headerColor);
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Title Label
        JLabel lblTitle = new JLabel("CHI TIẾT HÓA ĐƠN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(lblTitle, BorderLayout.NORTH);

        // Function Label
        JLabel lblChucNang = new JLabel("Chức năng");
        topPanel.add(lblChucNang, BorderLayout.WEST);

        // Function Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setOpaque(false);

        JButton btnEdit = new JButton("Sửa");
        btnEdit.setToolTipText("Sửa chi tiết hóa đơn");
        btnEdit.setBackground(new Color(240, 240, 240));
        buttonPanel.add(btnEdit);

        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labelPanel.setOpaque(false);
        labelPanel.add(new JLabel("Sửa"));

        JPanel functionPanel = new JPanel(new BorderLayout());
        functionPanel.setOpaque(false);
        functionPanel.add(buttonPanel, BorderLayout.NORTH);
        functionPanel.add(labelPanel, BorderLayout.SOUTH);

        topPanel.add(functionPanel, BorderLayout.CENTER);

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setOpaque(false);

        JLabel lblTimKiem = new JLabel("Tìm kiếm");
        searchPanel.add(lblTimKiem);

        JComboBox<String> cmbSearchType = new JComboBox<>(new String[]{"ID Chi Tiết Đơn Hàng", "ID Đơn Hàng"});
        cmbSearchType.setPreferredSize(new Dimension(150, 25));
        searchPanel.add(cmbSearchType);

        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(150, 25));
        searchPanel.add(txtSearch);

        JButton btnSearch = new JButton("Tìm kiếm");
        btnSearch.setBackground(new Color(240, 240, 240));
        searchPanel.add(btnSearch);

        JButton btnReset = new JButton("Làm mới");
        btnReset.setBackground(new Color(240, 240, 240));
        searchPanel.add(btnReset);

        JButton btnExportExcel = new JButton("Xuất Excel");
        btnExportExcel.setBackground(new Color(240, 240, 240));
        searchPanel.add(btnExportExcel);

        topPanel.add(searchPanel, BorderLayout.EAST);
        chiTietPanel.add(topPanel, BorderLayout.NORTH);

        // Table
        String[] columns = {
                "ID Chi Tiết Đơn Hàng", "ID Đơn Hàng", "ID Sản Phẩm", "Số Lượng", "Giá Bán"
        };

        chiTietTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        chiTietTable = new JTable(chiTietTableModel);
        chiTietTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        chiTietTable.setRowHeight(25);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < chiTietTable.getColumnCount(); i++) {
            chiTietTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        TableColumnModel columnModel = chiTietTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(100);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(80);
        columnModel.getColumn(4).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(chiTietTable);
        chiTietPanel.add(scrollPane, BorderLayout.CENTER);

        // Events
        btnSearch.addActionListener(e -> searchChiTiet(cmbSearchType.getSelectedItem().toString(), txtSearch.getText()));
        btnReset.addActionListener(e -> resetChiTietSearch());
        btnEdit.addActionListener(e -> editChiTiet(chiTietTable.getSelectedRow()));
        btnExportExcel.addActionListener(e -> exportChiTietToExcel());

        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchChiTiet(cmbSearchType.getSelectedItem().toString(), txtSearch.getText());
                }
            }
        });
    }

    public void loadDataToTable() {
        chiTietTableModel.setRowCount(0);
        List<ChiTietDonHangDTO> danhSachChiTiet = chiTietDonHangDAO.selectAll();

        for (ChiTietDonHangDTO chiTiet : danhSachChiTiet) {
            chiTietTableModel.addRow(new Object[]{
                    chiTiet.getIdChiTietDonHang(),
                    chiTiet.getIdDonHang(),
                    chiTiet.getIdSanPham(),
                    chiTiet.getSoLuong(),
                    chiTiet.getGiaBan()
            });
        }
    }

    private void resetChiTietSearch() {
        txtSearch.setText("");
        loadDataToTable();
    }

    private void searchChiTiet(String searchType, String keyword) {
        if (keyword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        chiTietTableModel.setRowCount(0);
        List<ChiTietDonHangDTO> allData = chiTietDonHangDAO.selectAll();

        for (ChiTietDonHangDTO chiTiet : allData) {
            boolean match = false;
            switch (searchType) {
                case "ID Chi Tiết Đơn Hàng":
                    if (chiTiet.getIdChiTietDonHang().toLowerCase().contains(keyword.toLowerCase())) {
                        match = true;
                    }
                    break;
                case "ID Đơn Hàng":
                    if (chiTiet.getIdDonHang().toLowerCase().contains(keyword.toLowerCase())) {
                        match = true;
                    }
                    break;
            }
            if (match) {
                chiTietTableModel.addRow(new Object[]{
                        chiTiet.getIdChiTietDonHang(),
                        chiTiet.getIdDonHang(),
                        chiTiet.getIdSanPham(),
                        chiTiet.getSoLuong(),
                        chiTiet.getGiaBan()
                });
            }
        }

        if (chiTietTableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy chi tiết hóa đơn phù hợp",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void editChiTiet(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một chi tiết hóa đơn để sửa",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idChiTietDonHang = chiTietTable.getValueAt(selectedRow, 0).toString();
        ChiTietDonHangDTO chiTiet = chiTietDonHangDAO.selectById(idChiTietDonHang);

        if (chiTiet == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu để sửa",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JDialog editDialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Sửa Chi Tiết Hóa Đơn", Dialog.ModalityType.APPLICATION_MODAL);
        editDialog.setSize(450, 300);
        editDialog.setLocationRelativeTo(this);
        editDialog.setLayout(new BorderLayout());
        editDialog.getContentPane().setBackground(new Color(230, 255, 230));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(new Color(230, 255, 230));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);

        JLabel lblIdChiTiet = new JLabel("ID Chi Tiết Đơn Hàng:");
        lblIdChiTiet.setFont(labelFont);
        JTextField txtIdChiTiet = new JTextField(chiTiet.getIdChiTietDonHang(), 20);
        txtIdChiTiet.setFont(fieldFont);
        txtIdChiTiet.setEditable(false);
        txtIdChiTiet.setBackground(new Color(220, 220, 220));

        JLabel lblIdDonHang = new JLabel("ID Đơn Hàng:");
        lblIdDonHang.setFont(labelFont);
        JTextField txtIdDonHang = new JTextField(chiTiet.getIdDonHang(), 20);
        txtIdDonHang.setFont(fieldFont);
        txtIdDonHang.setEditable(false);
        txtIdDonHang.setBackground(new Color(220, 220, 220));

        JLabel lblIdSanPham = new JLabel("ID Sản Phẩm:");
        lblIdSanPham.setFont(labelFont);
        JTextField txtIdSanPham = new JTextField(chiTiet.getIdSanPham(), 20);
        txtIdSanPham.setFont(fieldFont);

        JLabel lblSoLuong = new JLabel("Số Lượng:");
        lblSoLuong.setFont(labelFont);
        JTextField txtSoLuong = new JTextField(String.valueOf(chiTiet.getSoLuong()), 20);
        txtSoLuong.setFont(fieldFont);

        JLabel lblGiaBan = new JLabel("Giá Bán:");
        lblGiaBan.setFont(labelFont);
        JTextField txtGiaBan = new JTextField(String.valueOf(chiTiet.getGiaBan()), 20);
        txtGiaBan.setFont(fieldFont);

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(lblIdChiTiet, gbc);
        gbc.gridx = 1; formPanel.add(txtIdChiTiet, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(lblIdDonHang, gbc);
        gbc.gridx = 1; formPanel.add(txtIdDonHang, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(lblIdSanPham, gbc);
        gbc.gridx = 1; formPanel.add(txtIdSanPham, gbc);
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(lblSoLuong, gbc);
        gbc.gridx = 1; formPanel.add(txtSoLuong, gbc);
        gbc.gridx = 0; gbc.gridy = 4; formPanel.add(lblGiaBan, gbc);
        gbc.gridx = 1; formPanel.add(txtGiaBan, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(255, 255, 255));
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JButton btnLuu = new JButton("Lưu");
        btnLuu.setFont(new Font("Arial", Font.BOLD, 14));
        btnLuu.setForeground(Color.BLACK);
        btnLuu.setPreferredSize(new Dimension(100, 35));
        btnLuu.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JButton btnHuy = new JButton("Hủy");
        btnHuy.setFont(new Font("Arial", Font.BOLD, 14));
        btnHuy.setForeground(Color.BLACK);
        btnHuy.setPreferredSize(new Dimension(100, 35));
        btnHuy.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        buttonPanel.add(btnLuu);
        buttonPanel.add(btnHuy);

        editDialog.add(formPanel, BorderLayout.CENTER);
        editDialog.add(buttonPanel, BorderLayout.SOUTH);

        btnLuu.addActionListener(e -> {
            try {
                if (txtIdSanPham.getText().trim().isEmpty() || txtSoLuong.getText().trim().isEmpty() ||
                        txtGiaBan.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(editDialog, "Vui lòng điền đầy đủ thông tin",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String soLuongStr = txtSoLuong.getText().trim();
                String giaBanStr = txtGiaBan.getText().trim();
                if (!soLuongStr.matches("\\d+") || !giaBanStr.matches("\\d+")) {
                    JOptionPane.showMessageDialog(editDialog, "Số lượng và giá bán phải là số nguyên dương",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                chiTiet.setIdSanPham(txtIdSanPham.getText().trim());
                chiTiet.setSoLuong(Integer.parseInt(soLuongStr));
                chiTiet.setGiaBan(Integer.parseInt(giaBanStr));

                if (chiTietDonHangDAO.update(chiTiet)) {
                    loadDataToTable();
                    JOptionPane.showMessageDialog(editDialog, "Cập nhật chi tiết hóa đơn thành công",
                            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    editDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(editDialog, "Cập nhật chi tiết hóa đơn thất bại",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(editDialog, "Số lượng và giá bán phải là số nguyên hợp lệ",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(editDialog, "Lỗi khi cập nhật: " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnHuy.addActionListener(e -> editDialog.dispose());

        editDialog.setVisible(true);
    }

    private void exportChiTietToExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu fileolese");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files", "xlsx"));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.endsWith(".xlsx")) {
                filePath += ".xlsx";
            }

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("ChiTietHoaDon");

                Row headerRow = sheet.createRow(0);
                for (int col = 0; col < chiTietTable.getColumnCount(); col++) {
                    Cell cell = headerRow.createCell(col);
                    cell.setCellValue(chiTietTable.getColumnName(col));
                }

                for (int row = 0; row < chiTietTable.getRowCount(); row++) {
                    Row excelRow = sheet.createRow(row + 1);
                    for (int col = 0; col < chiTietTable.getColumnCount(); col++) {
                        Cell cell = excelRow.createCell(col);
                        Object value = chiTietTable.getValueAt(row, col);
                        if (value != null) {
                            cell.setCellValue(value.toString());
                        }
                    }
                }

                try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                    workbook.write(fileOut);
                }

                JOptionPane.showMessageDialog(this, "Xuất file Excel thành công!",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Xuất file thất bại: " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}