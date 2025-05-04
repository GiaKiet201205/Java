package GUI.panel;

import DAO.DonHangDAO;
import DTO.DonHangDTO;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class HoaDonPanel extends JPanel {
    private DefaultTableModel hoaDonTableModel;
    private JTable hoaDonTable;
    private JPanel hoaDonPanel;
    private Color headerColor = new Color(200, 255, 200);
    private JTextField txtSearchID, dateFrom, dateTo;
    private DonHangDAO donHangDAO = new DonHangDAO();

    public HoaDonPanel() {
        setLayout(new BorderLayout());
        createHoaDonPanel();
        add(hoaDonPanel, BorderLayout.CENTER);
        loadDataToTable();
    }

    private void createHoaDonPanel() {
        hoaDonPanel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(headerColor);
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel lblSearchID = new JLabel("Tìm theo ID:");
        txtSearchID = new JTextField(10);
        JLabel lblDateFrom = new JLabel("Từ ngày:");
        dateFrom = new JTextField(10);
        JLabel lblDateTo = new JLabel("Đến ngày:");
        dateTo = new JTextField(10);
        JButton btnSearch = new JButton("Tìm kiếm");
        JButton btnReset = new JButton("Làm mới");
        JButton btnEdit = new JButton("Sửa");
        JButton btnExportExcel = new JButton("Xuất Excel");

        topPanel.add(lblSearchID);
        topPanel.add(txtSearchID);
        topPanel.add(lblDateFrom);
        topPanel.add(dateFrom);
        topPanel.add(lblDateTo);
        topPanel.add(dateTo);
        topPanel.add(btnSearch);
        topPanel.add(btnReset);
        topPanel.add(btnEdit);
        topPanel.add(btnExportExcel);
        
        hoaDonPanel.add(topPanel, BorderLayout.NORTH);

        String[] columns = {
            "ID Đơn Hàng", "ID Khách Hàng", "ID Nhân Viên", "Tổng Tiền", "Ngày Đặt Hàng", "Trạng Thái", "Hình Thức Mua Hàng", "Địa Điểm Giao Hàng"
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

        JScrollPane scrollPane = new JScrollPane(hoaDonTable);
        hoaDonPanel.add(scrollPane, BorderLayout.CENTER);

        btnSearch.addActionListener(e -> searchHoaDon(txtSearchID.getText(), dateFrom.getText(), dateTo.getText()));
        btnReset.addActionListener(e -> resetHoaDonSearch());
        btnEdit.addActionListener(e -> editHoaDon(hoaDonTable.getSelectedRow()));
        btnExportExcel.addActionListener(e -> exportHoaDonToExcel());


        txtSearchID.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchHoaDon(txtSearchID.getText(), dateFrom.getText(), dateTo.getText());
                }
            }
        });
    }

    public void loadDataToTable() {
        hoaDonTableModel.setRowCount(0);
        List<DonHangDTO> danhSachDonHang = donHangDAO.selectAll();

        for (DonHangDTO donHang : danhSachDonHang) {
            hoaDonTableModel.addRow(new Object[] {
                donHang.getIdDonHang(),
                donHang.getIdKhachHang(),
                donHang.getIdNhanVien(),
                donHang.getTongTien(),
                donHang.getNgayDatHang(),
                donHang.getTrangThai(),
                donHang.getHinhThucMuaHang(),
                donHang.getDiaDiemGiao()
            });
        }
    }

    private void resetHoaDonSearch() {
        txtSearchID.setText("");
        dateFrom.setText("");
        dateTo.setText("");
        loadDataToTable();
        JOptionPane.showMessageDialog(this, "Đã làm mới bộ lọc tìm kiếm", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void searchHoaDon(String idSearch, String fromDate, String toDate) {
        JOptionPane.showMessageDialog(this, "Tìm kiếm hiện tại chỉ hiển thị thông báo.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void editHoaDon(int selectedRow) {
        if (selectedRow != -1) {
            String idDonHang = hoaDonTable.getValueAt(selectedRow, 0).toString();
            DonHangDTO donHang = donHangDAO.selectById(idDonHang);
            if (donHang != null) {
                JDialog editDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(hoaDonPanel), "Sửa hóa đơn", true);
                editDialog.setLayout(new GridLayout(10, 2, 10, 10));
                editDialog.setSize(500, 500);
                editDialog.setLocationRelativeTo(null);

                JTextField txtIdDonHang = new JTextField(donHang.getIdDonHang());
                txtIdDonHang.setEditable(false);
                JTextField txtIdKhachHang = new JTextField(donHang.getIdKhachHang());
                JTextField txtIdNhanVien = new JTextField(donHang.getIdNhanVien());
                JTextField txtTongTien = new JTextField(String.valueOf(donHang.getTongTien()));
                JTextField txtNgayDatHang = new JTextField(donHang.getNgayDatHang().toString());
                txtNgayDatHang.setEditable(false);
                JTextField txtTrangThai = new JTextField(donHang.getTrangThai());
                JTextField txtHinhThucMuaHang = new JTextField(donHang.getHinhThucMuaHang());
                JTextField txtDiaDiemGiao = new JTextField(donHang.getDiaDiemGiao());

                editDialog.add(new JLabel("ID Đơn Hàng:"));
                editDialog.add(txtIdDonHang);
                editDialog.add(new JLabel("ID Khách Hàng:"));
                editDialog.add(txtIdKhachHang);
                editDialog.add(new JLabel("ID Nhân Viên:"));
                editDialog.add(txtIdNhanVien);
                editDialog.add(new JLabel("Tổng Tiền:"));
                editDialog.add(txtTongTien);
                editDialog.add(new JLabel("Ngày Đặt Hàng:"));
                editDialog.add(txtNgayDatHang);
                editDialog.add(new JLabel("Trạng Thái:"));
                editDialog.add(txtTrangThai);
                editDialog.add(new JLabel("Hình Thức Mua Hàng:"));
                editDialog.add(txtHinhThucMuaHang);
                editDialog.add(new JLabel("Địa Điểm Giao Hàng:"));
                editDialog.add(txtDiaDiemGiao);

                JButton btnLuu = new JButton("Lưu");
                JButton btnHuy = new JButton("Hủy");

                btnLuu.addActionListener(e -> {
                    try {
                        donHang.setTongTien(Integer.parseInt(txtTongTien.getText()));
                        donHang.setTrangThai(txtTrangThai.getText());
                        donHang.setHinhThucMuaHang(txtHinhThucMuaHang.getText());
                        donHang.setDiaDiemGiao(txtDiaDiemGiao.getText());
                        donHang.setIdKhachHang(txtIdKhachHang.getText());
                        donHang.setIdNhanVien(txtIdNhanVien.getText());

                        if (donHangDAO.update(donHang)) {
                            loadDataToTable();
                            JOptionPane.showMessageDialog(this, "Cập nhật thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            editDialog.dispose();
                        } else {
                            JOptionPane.showMessageDialog(this, "Cập nhật thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Tổng tiền phải là số nguyên", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                });

                btnHuy.addActionListener(e -> editDialog.dispose());

                editDialog.add(btnLuu);
                editDialog.add(btnHuy);
                editDialog.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một đơn hàng để sửa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void exportHoaDonToExcel() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
    fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files", "xlsx"));

    int userSelection = fileChooser.showSaveDialog(this);

    if (userSelection == JFileChooser.APPROVE_OPTION) {
        String filePath = fileChooser.getSelectedFile().getAbsolutePath();
        if (!filePath.endsWith(".xlsx")) {
            filePath += ".xlsx"; // đảm bảo có đuôi .xlsx
        }

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("HoaDon");

            // Tạo dòng header
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < hoaDonTable.getColumnCount(); col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(hoaDonTable.getColumnName(col));
            }

            // Ghi dữ liệu các dòng
            for (int row = 0; row < hoaDonTable.getRowCount(); row++) {
                Row excelRow = sheet.createRow(row + 1); // +1 vì dòng 0 đã là header
                for (int col = 0; col < hoaDonTable.getColumnCount(); col++) {
                    Cell cell = excelRow.createCell(col);
                    Object value = hoaDonTable.getValueAt(row, col);
                    if (value != null) {
                        cell.setCellValue(value.toString());
                    }
                }
            }

            // Ghi file ra đĩa
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }

            JOptionPane.showMessageDialog(this, "Xuất file Excel thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Xuất file thất bại: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
}
