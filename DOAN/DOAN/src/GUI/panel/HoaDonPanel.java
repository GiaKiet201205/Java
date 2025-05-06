package GUI.panel;

import DAO.DonHangDAO;
import DTO.DonHangDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.filechooser.FileNameExtensionFilter;

public class HoaDonPanel extends JPanel {
    private DefaultTableModel hoaDonTableModel;
    private JTable hoaDonTable;
    private JPanel hoaDonPanel;
    private Color headerColor = new Color(200, 255, 200);
    private DonHangDAO donHangDAO = new DonHangDAO();
    private JTextField txtSearch;
    private JTextField dateFrom;
    private JTextField dateTo;

    public HoaDonPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 400));
        createHoaDonPanel();
        add(hoaDonPanel, BorderLayout.CENTER);
        loadDataToTable();
    }

    private void createHoaDonPanel() {
        hoaDonPanel = new JPanel(new BorderLayout());

        // Top Panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(headerColor);
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Title Label
        JLabel lblTitle = new JLabel("HÓA ĐƠN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(lblTitle, BorderLayout.NORTH);

        JLabel lblChucNang = new JLabel("Chức năng");
        topPanel.add(lblChucNang, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setOpaque(false);

        JButton btnEdit = new JButton("Sửa");
        btnEdit.setToolTipText("Sửa hóa đơn");
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

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setOpaque(false);

        JLabel lblTimKiem = new JLabel("Tìm kiếm");
        searchPanel.add(lblTimKiem);

        JComboBox<String> cmbSearchType = new JComboBox<>(new String[]{"ID Đơn Hàng", "Ngày Đặt Hàng"});
        cmbSearchType.setPreferredSize(new Dimension(120, 25));
        searchPanel.add(cmbSearchType);

        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(100, 25));
        searchPanel.add(txtSearch);

        JLabel lblDateFrom = new JLabel("Từ ngày:");
        dateFrom = new JTextField(10);
        dateFrom.setPreferredSize(new Dimension(100, 25));
        searchPanel.add(lblDateFrom);
        searchPanel.add(dateFrom);

        JLabel lblDateTo = new JLabel("Đến ngày:");
        dateTo = new JTextField(10);
        dateTo.setPreferredSize(new Dimension(100, 25));
        searchPanel.add(lblDateTo);
        searchPanel.add(dateTo);

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
        hoaDonPanel.add(topPanel, BorderLayout.NORTH);

        String[] columns = {
                "ID Đơn Hàng", "ID Khách Hàng", "ID Nhân Viên", "Tổng Tiền", "Ngày Đặt Hàng",
                "Trạng Thái", "Hình Thức Mua Hàng", "Địa Điểm Giao Hàng"
        };
        
        hoaDonTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        hoaDonTable = new JTable(hoaDonTableModel);
        hoaDonTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        hoaDonTable.setRowHeight(25);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < hoaDonTable.getColumnCount(); i++) {
            hoaDonTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        TableColumnModel columnModel = hoaDonTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(100);
        columnModel.getColumn(5).setPreferredWidth(100);
        columnModel.getColumn(6).setPreferredWidth(100);
        columnModel.getColumn(7).setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(hoaDonTable);
        hoaDonPanel.add(scrollPane, BorderLayout.CENTER);

        btnSearch.addActionListener(e -> searchHoaDon(cmbSearchType.getSelectedItem().toString(), txtSearch.getText(), dateFrom.getText(), dateTo.getText()));
        btnReset.addActionListener(e -> resetHoaDonSearch());
        btnEdit.addActionListener(e -> editHoaDon(hoaDonTable.getSelectedRow()));
        btnExportExcel.addActionListener(e -> exportHoaDonToExcel());

        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchHoaDon(cmbSearchType.getSelectedItem().toString(), txtSearch.getText(), dateFrom.getText(), dateTo.getText());
                }
            }
        });

        dateFrom.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchHoaDon(cmbSearchType.getSelectedItem().toString(), txtSearch.getText(), dateFrom.getText(), dateTo.getText());
                }
            }
        });

        dateTo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchHoaDon(cmbSearchType.getSelectedItem().toString(), txtSearch.getText(), dateFrom.getText(), dateTo.getText());
                }
            }
        });
    }

    public void loadDataToTable() {
        hoaDonTableModel.setRowCount(0);
        List<DonHangDTO> danhSachDonHang = donHangDAO.selectAll();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        for (DonHangDTO donHang : danhSachDonHang) {
            hoaDonTableModel.addRow(new Object[]{
                    donHang.getIdDonHang(),
                    donHang.getIdKhachHang(),
                    donHang.getIdNhanVien(),
                    donHang.getTongTien(),
                    sdf.format(donHang.getNgayDatHang()),
                    donHang.getTrangThai(),
                    donHang.getHinhThucMuaHang(),
                    donHang.getDiaDiemGiao()
            });
        }
    }

    private void resetHoaDonSearch() {
        txtSearch.setText("");
        dateFrom.setText("");
        dateTo.setText("");
        loadDataToTable();
    }

    private void searchHoaDon(String searchType, String keyword, String fromDate, String toDate) {
        if (searchType.equals("ID Đơn Hàng") && keyword.trim().isEmpty() && fromDate.trim().isEmpty() && toDate.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ít nhất một tiêu chí tìm kiếm!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        hoaDonTableModel.setRowCount(0);
        List<DonHangDTO> allData = donHangDAO.selectAll();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = null, endDate = null;

        // Parse date inputs
        try {
            if (!fromDate.trim().isEmpty()) {
                startDate = sdf.parse(fromDate);
            }
            if (!toDate.trim().isEmpty()) {
                endDate = sdf.parse(toDate);
            }
            if (startDate != null && endDate != null && startDate.after(endDate)) {
                JOptionPane.showMessageDialog(this, "Ngày bắt đầu không được lớn hơn ngày kết thúc!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Định dạng ngày không hợp lệ (dd/MM/yyyy)",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (DonHangDTO donHang : allData) {
            boolean idMatch = true;
            boolean dateMatch = true;

            // Filter by ID
            if (searchType.equals("ID Đơn Hàng") && !keyword.trim().isEmpty()) {
                idMatch = donHang.getIdDonHang().toLowerCase().contains(keyword.toLowerCase());
            }

            // Filter by date range
            if (startDate != null) {
                dateMatch = !donHang.getNgayDatHang().before(startDate);
            }
            if (endDate != null) {
                dateMatch = dateMatch && !donHang.getNgayDatHang().after(endDate);
            }

            if (idMatch && dateMatch) {
                hoaDonTableModel.addRow(new Object[]{
                        donHang.getIdDonHang(),
                        donHang.getIdKhachHang(),
                        donHang.getIdNhanVien(),
                        donHang.getTongTien(),
                        sdf.format(donHang.getNgayDatHang()),
                        donHang.getTrangThai(),
                        donHang.getHinhThucMuaHang(),
                        donHang.getDiaDiemGiao()
                });
            }
        }

        if (hoaDonTableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn phù hợp",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void editHoaDon(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một đơn hàng để sửa",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idDonHang = hoaDonTable.getValueAt(selectedRow, 0).toString();
        DonHangDTO donHang = donHangDAO.selectById(idDonHang);

        if (donHang == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu để sửa",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JDialog editDialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Sửa Hóa Đơn", Dialog.ModalityType.APPLICATION_MODAL);
        editDialog.setSize(450, 500);
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

        JLabel lblIdDonHang = new JLabel("ID Đơn Hàng:");
        lblIdDonHang.setFont(labelFont);
        JTextField txtIdDonHang = new JTextField(donHang.getIdDonHang(), 20);
        txtIdDonHang.setFont(fieldFont);
        txtIdDonHang.setEditable(false);
        txtIdDonHang.setBackground(new Color(220, 220, 220));

        JLabel lblIdKhachHang = new JLabel("ID Khách Hàng:");
        lblIdKhachHang.setFont(labelFont);
        JTextField txtIdKhachHang = new JTextField(donHang.getIdKhachHang(), 20);
        txtIdKhachHang.setFont(fieldFont);

        JLabel lblIdNhanVien = new JLabel("ID Nhân Viên:");
        lblIdNhanVien.setFont(labelFont);
        JTextField txtIdNhanVien = new JTextField(donHang.getIdNhanVien(), 20);
        txtIdNhanVien.setFont(fieldFont);

        JLabel lblTongTien = new JLabel("Tổng Tiền:");
        lblTongTien.setFont(labelFont);
        JTextField txtTongTien = new JTextField(String.valueOf(donHang.getTongTien()), 20);
        txtTongTien.setFont(fieldFont);

        JLabel lblNgayDatHang = new JLabel("Ngày Đặt Hàng (dd/MM/yyyy):");
        lblNgayDatHang.setFont(labelFont);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        JTextField txtNgayDatHang = new JTextField(sdf.format(donHang.getNgayDatHang()), 20);
        txtNgayDatHang.setFont(fieldFont);
        txtNgayDatHang.setEditable(false);
        txtNgayDatHang.setBackground(new Color(220, 220, 220));

        JLabel lblTrangThai = new JLabel("Trạng Thái:");
        lblTrangThai.setFont(labelFont);
        String[] trangThaiOptions = {"Đang xử lý", "Đã giao", "Đã hủy"};
        JComboBox<String> cbTrangThai = new JComboBox<>(trangThaiOptions);
        cbTrangThai.setFont(fieldFont);
        cbTrangThai.setSelectedItem(donHang.getTrangThai());

        JLabel lblHinhThucMuaHang = new JLabel("Hình Thức Mua Hàng:");
        lblHinhThucMuaHang.setFont(labelFont);
        JTextField txtHinhThucMuaHang = new JTextField(donHang.getHinhThucMuaHang(), 20);
        txtHinhThucMuaHang.setFont(fieldFont);

        JLabel lblDiaDiemGiao = new JLabel("Địa Điểm Giao Hàng:");
        lblDiaDiemGiao.setFont(labelFont);
        JTextField txtDiaDiemGiao = new JTextField(donHang.getDiaDiemGiao(), 20);
        txtDiaDiemGiao.setFont(fieldFont);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(lblIdDonHang, gbc);
        gbc.gridx = 1;
        formPanel.add(txtIdDonHang, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(lblIdKhachHang, gbc);
        gbc.gridx = 1;
        formPanel.add(txtIdKhachHang, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(lblIdNhanVien, gbc);
        gbc.gridx = 1;
        formPanel.add(txtIdNhanVien, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(lblTongTien, gbc);
        gbc.gridx = 1;
        formPanel.add(txtTongTien, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(lblNgayDatHang, gbc);
        gbc.gridx = 1;
        formPanel.add(txtNgayDatHang, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(lblTrangThai, gbc);
        gbc.gridx = 1;
        formPanel.add(cbTrangThai, gbc);
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(lblHinhThucMuaHang, gbc);
        gbc.gridx = 1;
        formPanel.add(txtHinhThucMuaHang, gbc);
        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(lblDiaDiemGiao, gbc);
        gbc.gridx = 1;
        formPanel.add(txtDiaDiemGiao, gbc);

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
                if (txtIdKhachHang.getText().trim().isEmpty() || txtIdNhanVien.getText().trim().isEmpty() ||
                        txtTongTien.getText().trim().isEmpty() || txtHinhThucMuaHang.getText().trim().isEmpty() ||
                        txtDiaDiemGiao.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(editDialog, "Vui lòng điền đầy đủ thông tin",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String tongTienStr = txtTongTien.getText().trim();
                if (!tongTienStr.matches("\\d+")) {
                    JOptionPane.showMessageDialog(editDialog, "Tổng tiền phải là số nguyên dương",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                donHang.setIdKhachHang(txtIdKhachHang.getText().trim());
                donHang.setIdNhanVien(txtIdNhanVien.getText().trim());
                donHang.setTongTien(Integer.parseInt(tongTienStr));
                donHang.setTrangThai((String) cbTrangThai.getSelectedItem());
                donHang.setHinhThucMuaHang(txtHinhThucMuaHang.getText().trim());
                donHang.setDiaDiemGiao(txtDiaDiemGiao.getText().trim());

                if (donHangDAO.update(donHang)) {
                    loadDataToTable();
                    JOptionPane.showMessageDialog(editDialog, "Cập nhật hóa đơn thành công",
                            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    editDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(editDialog, "Cập nhật hóa đơn thất bại",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(editDialog, "Tổng tiền phải là số nguyên hợp lệ",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(editDialog, "Lỗi khi cập nhật: " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnHuy.addActionListener(e -> editDialog.dispose());

        editDialog.setVisible(true);
    }

    private void exportHoaDonToExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files", "xlsx"));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.endsWith(".xlsx")) {
                filePath += ".xlsx";
            }

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("HoaDon");

                Row headerRow = sheet.createRow(0);
                for (int col = 0; col < hoaDonTable.getColumnCount(); col++) {
                    Cell cell = headerRow.createCell(col);
                    cell.setCellValue(hoaDonTable.getColumnName(col));
                }

                for (int row = 0; row < hoaDonTable.getRowCount(); row++) {
                    Row excelRow = sheet.createRow(row + 1);
                    for (int col = 0; col < hoaDonTable.getColumnCount(); col++) {
                        Cell cell = excelRow.createCell(col);
                        Object value = hoaDonTable.getValueAt(row, col);
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