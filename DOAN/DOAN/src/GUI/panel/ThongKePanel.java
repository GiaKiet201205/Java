package GUI.panel;

import BLL.ThongKeBLL;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ThongKePanel extends JPanel {
    private JPanel thongKePanel;
    private Color headerColor = new Color(200, 255, 200);
    private ThongKeBLL thongKeBLL = new ThongKeBLL();
    private DefaultTableModel methodModel, employeeModel, hinhThucModel, sanPhamModel, nhapHangModel;
    private JTable table;
    private JPanel contentPanel;
    private DecimalFormat df = new DecimalFormat("#,###");
    private String selectedTimeFilter = ""; // Biến lưu trữ thời gian được chọn

    public ThongKePanel() {
        setLayout(new BorderLayout());
        createThongKePanel();
        add(thongKePanel, BorderLayout.CENTER);
    }

    private void createThongKePanel() {
        thongKePanel = new JPanel(new BorderLayout());

        // ===== TOP PANEL =====
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(headerColor);
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel lblChucNang = new JLabel("Thống Kê");
        lblChucNang.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(lblChucNang, BorderLayout.WEST);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controlPanel.setOpaque(false);

        JLabel lblFilter = new JLabel("Chọn thời gian:");
        controlPanel.add(lblFilter);
        JComboBox<String> cmbTimeFilter = new JComboBox<>(new String[]{
            "Tất cả", "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6",
            "Tháng 7", "Tháng 8", "Tháng 9", "Năm 2025"
        });
        cmbTimeFilter.setPreferredSize(new Dimension(150, 25));
        controlPanel.add(cmbTimeFilter);

        JButton btnView = new JButton("Xem");
        btnView.setBackground(new Color(60, 141, 188));
        btnView.setForeground(Color.WHITE);
        controlPanel.add(btnView);

        JButton btnRefresh = new JButton("Làm mới");
        controlPanel.add(btnRefresh);

        topPanel.add(controlPanel, BorderLayout.EAST);
        thongKePanel.add(topPanel, BorderLayout.NORTH);

        // ===== CONTENT PANEL =====
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);

        thongKePanel.add(scrollPane, BorderLayout.CENTER);

        // Create sections
        createDoanhThuSection(contentPanel);
        createSanPhamSection(contentPanel);
        createDonHangSection(contentPanel);
        createNhapHangSection(contentPanel);
        createLoiNhuanSection(contentPanel);

        // Actions
        btnView.addActionListener(e -> {
            selectedTimeFilter = (String) cmbTimeFilter.getSelectedItem();
            if (selectedTimeFilter.equals("Tất cả")) {
                selectedTimeFilter = "";
            }
            updateData(); // Chỉ cập nhật dữ liệu, không hiển thị thông báo
        });

        btnRefresh.addActionListener(e -> {
            selectedTimeFilter = "";
            cmbTimeFilter.setSelectedItem("Tất cả");
            refreshData(); // Cập nhật dữ liệu và hiển thị thông báo
        });
    }

    private void createDoanhThuSection(JPanel parent) {
        JLabel lblTitle = new JLabel("THỐNG KÊ DOANH THU");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        parent.add(lblTitle);
        parent.add(Box.createVerticalStrut(10));

        // Control panel with filter and sort combo boxes
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        controlPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Main filter ComboBox
        controlPanel.add(new JLabel("Hiển thị theo:"));
        JComboBox<String> cmbFilterType = new JComboBox<>(new String[]{"Phương thức thanh toán", "Nhân viên", "Hình thức mua hàng"});
        cmbFilterType.setPreferredSize(new Dimension(150, 25));
        controlPanel.add(cmbFilterType);

        // Secondary filter ComboBox
        JComboBox<String> cmbSecondaryFilter = new JComboBox<>();
        cmbSecondaryFilter.setPreferredSize(new Dimension(150, 25));
        cmbSecondaryFilter.setVisible(false);
        controlPanel.add(cmbSecondaryFilter);

        // Sort ComboBox
        controlPanel.add(new JLabel("Sắp xếp:"));
        JComboBox<String> cmbSort = new JComboBox<>(new String[]{"Không sắp xếp", "Doanh thu tăng dần", "Doanh thu giảm dần"});
        cmbSort.setPreferredSize(new Dimension(150, 25));
        controlPanel.add(cmbSort);

        JButton btnView = new JButton("Xem");
        btnView.setBackground(new Color(60, 141, 188));
        btnView.setForeground(Color.WHITE);
        controlPanel.add(btnView);

        parent.add(controlPanel);
        parent.add(Box.createVerticalStrut(10));

        // Table models
        String[] methodColumns = {"STT", "Phương thức", "Doanh thu", "Số đơn hàng", "Trung bình"};
        String[] employeeColumns = {"STT", "Mã NV", "Tên NV", "Doanh thu", "Số đơn hàng", "Trung bình"};
        String[] hinhThucColumns = {"STT", "Hình thức", "Doanh thu", "Số đơn hàng", "Trung bình"};

        methodModel = new DefaultTableModel(methodColumns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        employeeModel = new DefaultTableModel(employeeColumns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        hinhThucModel = new DefaultTableModel(hinhThucColumns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };

        table = new JTable(methodModel);
        table.setRowHeight(25);
        setCenterRenderer(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(750, 150));
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        parent.add(scrollPane);
        parent.add(Box.createVerticalStrut(30));

        // Action listener for filter type ComboBox
        cmbFilterType.addActionListener(e -> {
            String selectedFilter = (String) cmbFilterType.getSelectedItem();
            cmbSecondaryFilter.removeAllItems();
            cmbSecondaryFilter.setVisible(true);

            if (selectedFilter.equals("Phương thức thanh toán")) {
                String[] methodOptions = {"Tiền mặt", "Chuyển khoản"};
                for (String option : methodOptions) {
                    cmbSecondaryFilter.addItem(option);
                }
            } else if (selectedFilter.equals("Nhân viên")) {
                cmbSecondaryFilter.setVisible(false);
                updateEmployeeTable(selectedTimeFilter);
                table.setModel(employeeModel);
                setCenterRenderer(table);
                table.revalidate();
                table.repaint();
            } else if (selectedFilter.equals("Hình thức mua hàng")) {
                String[] hinhThucOptions = {"Online", "Trực tiếp"};
                for (String option : hinhThucOptions) {
                    cmbSecondaryFilter.addItem(option);
                }
            }
        });

        // Action listener for view button
        btnView.addActionListener(e -> {
            String filterType = (String) cmbFilterType.getSelectedItem();
            String sortOption = (String) cmbSort.getSelectedItem();

            if (filterType.equals("Nhân viên")) {
                updateEmployeeTable(selectedTimeFilter);
                table.setModel(employeeModel);
            } else {
                String secondaryFilter = (String) cmbSecondaryFilter.getSelectedItem();
                if (secondaryFilter != null) {
                    if (filterType.equals("Phương thức thanh toán")) {
                        updateMethodTable(secondaryFilter, sortOption, selectedTimeFilter);
                        table.setModel(methodModel);
                    } else if (filterType.equals("Hình thức mua hàng")) {
                        updateHinhThucTable(secondaryFilter, sortOption, selectedTimeFilter);
                        table.setModel(hinhThucModel);
                    }
                }
            }

            setCenterRenderer(table);
            table.revalidate();
            table.repaint();
        });

        // Load initial data
        updateMethodTable("Tiền mặt", "Không sắp xếp", selectedTimeFilter);
    }

    private void updateMethodTable(String methodFilter, String sortOption, String timeFilter) {
        methodModel.setRowCount(0);
        List<Object[]> data = thongKeBLL.getDoanhThuTheoPhuongThuc(methodFilter, timeFilter);
        if (data != null && !data.isEmpty()) {
            data = thongKeBLL.sortData(new ArrayList<>(data), sortOption, 2);
            for (Object[] row : data) {
                row[2] = df.format(row[2]); // Định dạng lại doanh thu
                row[4] = df.format(row[4]); // Định dạng lại trung bình
                methodModel.addRow(row);
            }
        }
    }

    private void updateEmployeeTable(String timeFilter) {
        employeeModel.setRowCount(0);
        List<Object[]> data = thongKeBLL.getDoanhThuTheoNhanVien(timeFilter);
        if (data != null && !data.isEmpty()) {
            for (Object[] row : data) {
                row[3] = df.format(row[3]); // Định dạng lại doanh thu
                row[5] = df.format(row[5]); // Định dạng lại trung bình
                employeeModel.addRow(row);
            }
        }
    }

    private void updateHinhThucTable(String hinhThucFilter, String sortOption, String timeFilter) {
        hinhThucModel.setRowCount(0);
        List<Object[]> data = thongKeBLL.getDoanhThuTheoHinhThuc(hinhThucFilter, timeFilter);
        if (data != null && !data.isEmpty()) {
            data = thongKeBLL.sortData(new ArrayList<>(data), sortOption, 2);
            for (Object[] row : data) {
                row[2] = df.format(row[2]); // Định dạng lại doanh thu
                row[4] = df.format(row[4]); // Định dạng lại trung bình
                hinhThucModel.addRow(row);
            }
        }
    }

    private void createSanPhamSection(JPanel parent) {
        JLabel lblTitle = new JLabel("THỐNG KÊ SẢN PHẨM");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        parent.add(lblTitle);
        parent.add(Box.createVerticalStrut(10));

        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        statsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        statsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Cập nhật dữ liệu thực
        int banChay = thongKeBLL.getSanPhamBanChay(selectedTimeFilter);
        int banCham = thongKeBLL.getSanPhamBanCham(selectedTimeFilter);
        long sapHetHang = thongKeBLL.getSanPhamSapHetHang(selectedTimeFilter);

        statsPanel.add(createStatBox("SẢN PHẨM BÁN CHẠY", String.valueOf(banChay), "sp"));
        statsPanel.add(createStatBox("SẢN PHẨM BÁN CHẬM", String.valueOf(banCham), "sp"));
        statsPanel.add(createStatBox("SẮP HẾT HÀNG", String.valueOf(sapHetHang), "sp"));

        parent.add(statsPanel);
        parent.add(Box.createVerticalStrut(10));

        String[] columns = {"STT", "Mã SP", "Tên SP", "Số lượng bán", "Doanh thu", "Tồn kho"};
        sanPhamModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        JTable table = new JTable(sanPhamModel);
        table.setRowHeight(25);
        setCenterRenderer(table);

        // Load dữ liệu sản phẩm
        List<Object[]> sanPhamData = thongKeBLL.getThongKeSanPham(selectedTimeFilter);
        for (Object[] row : sanPhamData) {
            row[4] = df.format(row[4]); // Định dạng lại doanh thu
            sanPhamModel.addRow(row);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(750, 150));
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        parent.add(scrollPane);
        parent.add(Box.createVerticalStrut(30));
    }

    private void createDonHangSection(JPanel parent) {
        JLabel lblTitle = new JLabel("THỐNG KÊ ĐƠN HÀNG");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        parent.add(lblTitle);
        parent.add(Box.createVerticalStrut(10));

        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        statsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        statsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Cập nhật dữ liệu thực
        Map<String, Integer> donHangStats = thongKeBLL.getThongKeDonHang(selectedTimeFilter);
        statsPanel.add(createStatBox("ĐANG XỬ LÝ", String.valueOf(donHangStats.get("Đang xử lý")), "đơn"));
        statsPanel.add(createStatBox("ĐÃ GIAO", String.valueOf(donHangStats.get("Đã giao")), "đơn"));
        statsPanel.add(createStatBox("ĐÃ HỦY", String.valueOf(donHangStats.get("Đã hủy")), "đơn"));

        parent.add(statsPanel);
        parent.add(Box.createVerticalStrut(10));

        JPanel ratePanel = new JPanel(new GridLayout(1, 2, 20, 0));
        ratePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        ratePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Cập nhật tỷ lệ
        Map<String, String> tyLe = thongKeBLL.getTyLeDonHangOnline(selectedTimeFilter);
        ratePanel.add(createRateBox("TỶ LỆ ONLINE THÀNH CÔNG", tyLe.get("Thành công"), new Color(40, 167, 69)));
        ratePanel.add(createRateBox("TỶ LỆ ONLINE THẤT BẠI", tyLe.get("Thất bại"), new Color(220, 53, 69)));

        parent.add(ratePanel);
        parent.add(Box.createVerticalStrut(30));
    }

    private void createNhapHangSection(JPanel parent) {
        JLabel lblTitle = new JLabel("THỐNG KÊ NHẬP HÀNG");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        parent.add(lblTitle);
        parent.add(Box.createVerticalStrut(10));

        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        statsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        statsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Cập nhật dữ liệu thực
        double tongChiPhi = thongKeBLL.getTongChiPhiNhap(selectedTimeFilter);
        int nhapNhieu = thongKeBLL.getSanPhamNhapNhieuNhat(selectedTimeFilter);
        int nhapIt = thongKeBLL.getSanPhamNhapItNhat(selectedTimeFilter);

        statsPanel.add(createStatBox("TỔNG CHI PHÍ", df.format(tongChiPhi), "VND"));
        statsPanel.add(createStatBox("NHẬP NHIỀU NHẤT", String.valueOf(nhapNhieu), "sp"));
        statsPanel.add(createStatBox("NHẬP ÍT NHẤT", String.valueOf(nhapIt), "sp"));

        parent.add(statsPanel);
        parent.add(Box.createVerticalStrut(10));

        String[] columns = {"STT", "Thời gian", "Mã SP", "Tên SP", "Số lượng", "Chi phí"};
        nhapHangModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        JTable table = new JTable(nhapHangModel);
        table.setRowHeight(25);
        setCenterRenderer(table);

        // Load dữ liệu nhập hàng
        List<Object[]> nhapHangData = thongKeBLL.getThongKeNhapHang(selectedTimeFilter);
        for (Object[] row : nhapHangData) {
            row[5] = df.format(row[5]); // Định dạng lại chi phí
            nhapHangModel.addRow(row);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(750, 150));
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        parent.add(scrollPane);
        parent.add(Box.createVerticalStrut(30));
    }

    private void createLoiNhuanSection(JPanel parent) {
        JLabel lblTitle = new JLabel("THỐNG KÊ LỢI NHUẬN");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        parent.add(lblTitle);
        parent.add(Box.createVerticalStrut(10));

        JPanel statsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        statsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        statsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Cập nhật dữ liệu thực
        Map<String, String> loiNhuan = thongKeBLL.getThongKeLoiNhuan(selectedTimeFilter);
        statsPanel.add(createStatBox("TỔNG LỢI NHUẬN", loiNhuan.get("Tổng lợi nhuận"), ""));
        statsPanel.add(createStatBox("TỶ LỆ LỢI NHUẬN", loiNhuan.get("Tỷ lệ lợi nhuận"), ""));

        parent.add(statsPanel);
        parent.add(Box.createVerticalStrut(30));
    }

    private JPanel createStatBox(String title, String mainValue, String subtitle) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        JLabel lblTitle = new JLabel(title, JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 14));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblMain = new JLabel(mainValue, JLabel.CENTER);
        lblMain.setFont(new Font("Arial", Font.BOLD, 20));
        lblMain.setForeground(new Color(60, 141, 188));
        lblMain.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSub = new JLabel(subtitle, JLabel.CENTER);
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(lblTitle);
        panel.add(lblMain);
        panel.add(lblSub);

        return panel;
    }

    private JPanel createRateBox(String label, String value, Color color) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panel.setBackground(Color.WHITE);

        JLabel lbl = new JLabel(label, JLabel.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 14));
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblValue = new JLabel(value, JLabel.CENTER);
        lblValue.setFont(new Font("Arial", Font.BOLD, 20));
        lblValue.setForeground(color);
        lblValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(lbl);
        panel.add(lblValue);
        return panel;
    }

    private void setCenterRenderer(JTable table) {
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }
    }

    private void updateData() {
        contentPanel.removeAll();
        createDoanhThuSection(contentPanel);
        createSanPhamSection(contentPanel);
        createDonHangSection(contentPanel);
        createNhapHangSection(contentPanel);
        createLoiNhuanSection(contentPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void refreshData() {
        updateData();
        JOptionPane.showMessageDialog(this, "Dữ liệu đã được làm mới!");
    }
}