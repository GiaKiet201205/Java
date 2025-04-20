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

public class HoaDonPanel extends JPanel {
    private DefaultTableModel hoaDonTableModel;
    private JTable hoaDonTable;
    private JPanel hoaDonPanel;
    private Color headerColor = new Color(200, 255, 200);
    private JTextField txtSearchID, dateFrom, dateTo;

    // Thêm DAO
    private DonHangDAO donHangDAO = new DonHangDAO();

    public HoaDonPanel() {
        setLayout(new BorderLayout());
        createHoaDonPanel();
        add(hoaDonPanel, BorderLayout.CENTER);
        loadDataToTable(); // Load dữ liệu từ SQL lên bảng khi khởi tạo
    }

    private void createHoaDonPanel() {
        hoaDonPanel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(200, 255, 200)); // Giống NhapHangPanel
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Padding giống bên NhapHang

        JLabel lblSearchID = new JLabel("Tìm theo ID:");
        txtSearchID = new JTextField(10);

        JLabel lblDateFrom = new JLabel("Từ ngày:");
        dateFrom = new JTextField(10);

        JLabel lblDateTo = new JLabel("Đến ngày:");
        dateTo = new JTextField(10);

        JButton btnSearch = new JButton("Tìm kiếm");
        JButton btnReset = new JButton("Làm mới");
        JButton btnEdit = new JButton("Sửa");

        topPanel.add(lblSearchID);
        topPanel.add(txtSearchID);
        topPanel.add(lblDateFrom);
        topPanel.add(dateFrom);
        topPanel.add(lblDateTo);
        topPanel.add(dateTo);
        topPanel.add(btnSearch);
        topPanel.add(btnReset);
        topPanel.add(btnEdit);

        hoaDonPanel.add(topPanel, BorderLayout.NORTH);

        // Bảng dữ liệu
        String[] columns = {
            "ID đơn hàng", "ID khách hàng", "Ngày đặt hàng",
            "Tổng tiền", "ID nhân viên", "Trạng thái", "Hình thức mua hàng", "Địa Điểm Giao Hàng"
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

        // === Các nút và sự kiện ===
        btnSearch.addActionListener(e -> 
            searchHoaDon(txtSearchID.getText(), dateFrom.getText(), dateTo.getText()));
        
        btnReset.addActionListener(e -> resetHoaDonSearch());
        
        btnEdit.addActionListener(e -> editHoaDon(hoaDonTable.getSelectedRow()));

        txtSearchID.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchHoaDon(txtSearchID.getText(), dateFrom.getText(), dateTo.getText());
                }
            }
        });
    }

    
    private void loadDataToTable() {
        hoaDonTableModel.setRowCount(0); // Clear bảng

        List<DonHangDTO> danhSachDonHang = donHangDAO.selectAll();

        for (DonHangDTO donHang : danhSachDonHang) {
            hoaDonTableModel.addRow(new Object[] {
                donHang.getIdDonHang(),
                donHang.getIdKhachHang(),
                donHang.getNgayDatHang(),
                donHang.getTongTien(),
                donHang.getIdNhanVien(),
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
        // Giả sử bạn sẽ xây dựng logic tìm kiếm theo các tiêu chí
        JOptionPane.showMessageDialog(this, "Tìm kiếm hiện tại chỉ hiển thị thông báo.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void editHoaDon(int selectedRow) {
        if (selectedRow != -1) {
            String idDonHang = hoaDonTable.getValueAt(selectedRow, 0).toString();
            DonHangDTO donHang = donHangDAO.selectById(idDonHang);
            if (donHang != null) {
                // Mở giao diện sửa hóa đơn, có thể sử dụng JDialog hoặc JFrame
                // Hiển thị thông tin đơn hàng hiện tại để chỉnh sửa
                JOptionPane.showMessageDialog(this, "Sửa hóa đơn ID: " + idDonHang, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một đơn hàng để sửa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }
}
