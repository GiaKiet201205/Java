package GUI.panel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class ThongKePanel extends JPanel {
    private JPanel thongKePanel;
    private Color headerColor = new Color(200, 255, 200);

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

        JLabel lblFilter = new JLabel("Lọc theo:");
        controlPanel.add(lblFilter);

        JComboBox<String> cmbFilter = new JComboBox<>(new String[]{"Tháng", "Quý", "Năm"});
        cmbFilter.setPreferredSize(new Dimension(120, 25));
        controlPanel.add(cmbFilter);

        JButton btnRefresh = new JButton("Làm mới");
        controlPanel.add(btnRefresh);

        topPanel.add(controlPanel, BorderLayout.EAST);
        thongKePanel.add(topPanel, BorderLayout.NORTH);

        // ===== CONTENT PANEL =====
        JPanel contentPanel = new JPanel();
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
        btnRefresh.addActionListener(e -> refreshData());
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
        JComboBox<String> cmbFilterType = new JComboBox<>(new String[]{"Tháng/Quý/Năm", "Hình thức", "Nhân viên"});
        cmbFilterType.setPreferredSize(new Dimension(150, 25));
        controlPanel.add(cmbFilterType);

        // Secondary filter ComboBox (initially invisible)
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

        // Table models for different filters
        String[] timeColumns = {"STT", "Thời gian", "Doanh thu", "Số đơn hàng", "Trung bình"};
        String[] methodColumns = {"STT", "Hình thức", "Doanh thu", "Số đơn hàng", "Trung bình"};
        String[] employeeColumns = {"STT", "Mã NV", "Tên NV", "Doanh thu", "Số đơn hàng", "Trung bình"};

        DefaultTableModel timeModel = new DefaultTableModel(timeColumns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        DefaultTableModel methodModel = new DefaultTableModel(methodColumns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        DefaultTableModel employeeModel = new DefaultTableModel(employeeColumns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };

        JTable table = new JTable(timeModel);
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

            if (selectedFilter.equals("Tháng/Quý/Năm")) {
                String[] timeOptions = {"Tháng 1", "Tháng 2", "Tháng 3", "Quý 1", "Quý 2", "Quý 3", "Quý 4", "Năm 2023", "Năm 2024"};
                for (String option : timeOptions) {
                    cmbSecondaryFilter.addItem(option);
                }
            } else if (selectedFilter.equals("Hình thức")) {
                String[] methodOptions = {"Tiền mặt", "Thẻ", "Chuyển khoản"};
                for (String option : methodOptions) {
                    cmbSecondaryFilter.addItem(option);
                }
            } else if (selectedFilter.equals("Nhân viên")) {
                cmbSecondaryFilter.setVisible(false);
                // Directly update employee table
                updateEmployeeTable(employeeModel, null, (String) cmbSort.getSelectedItem());
                table.setModel(employeeModel);
                setCenterRenderer(table);
                table.revalidate();
                table.repaint();
            }
        });

        // Action listener for view button
        btnView.addActionListener(e -> {
            String filterType = (String) cmbFilterType.getSelectedItem();
            String sortOption = (String) cmbSort.getSelectedItem();

            if (filterType.equals("Nhân viên")) {
                updateEmployeeTable(employeeModel, null, sortOption);
                table.setModel(employeeModel);
            } else {
                String secondaryFilter = (String) cmbSecondaryFilter.getSelectedItem();
                if (secondaryFilter != null) {
                    if (filterType.equals("Tháng/Quý/Năm")) {
                        updateTimeTable(timeModel, secondaryFilter, sortOption);
                        table.setModel(timeModel);
                    } else if (filterType.equals("Hình thức")) {
                        updateMethodTable(methodModel, secondaryFilter, sortOption);
                        table.setModel(methodModel);
                    }
                }
            }

            setCenterRenderer(table);
            table.revalidate();
            table.repaint();
        });
    }

    private void updateTimeTable(DefaultTableModel model, String timeFilter, String sortOption) {
        model.setRowCount(0);
        // Sample data - replace with actual data retrieval
        Object[][] sampleData = {
            {1, timeFilter, "10,000,000", 50, "200,000"},
            {2, timeFilter + " (2)", "15,000,000", 75, "200,000"},
            {3, timeFilter + " (3)", "12,000,000", 60, "200,000"}
        };

        // Apply sorting
        if (sortOption.equals("Doanh thu tăng dần")) {
            java.util.Arrays.sort(sampleData, (a, b) -> {
                String valA = (String) a[2];
                String valB = (String) b[2];
                return Integer.compare(
                    Integer.parseInt(valA.replace(",", "")),
                    Integer.parseInt(valB.replace(",", ""))
                );
            });
        } else if (sortOption.equals("Doanh thu giảm dần")) {
            java.util.Arrays.sort(sampleData, (a, b) -> {
                String valA = (String) a[2];
                String valB = (String) b[2];
                return Integer.compare(
                    Integer.parseInt(valB.replace(",", "")),
                    Integer.parseInt(valA.replace(",", ""))
                );
            });
        }

        for (Object[] row : sampleData) {
            model.addRow(row);
        }
    }

    private void updateMethodTable(DefaultTableModel model, String methodFilter, String sortOption) {
        model.setRowCount(0);
        // Sample data - replace with actual data retrieval
        Object[][] sampleData = {
            {1, methodFilter, "8,000,000", 40, "200,000"},
            {2, methodFilter + " (2)", "12,000,000", 60, "200,000"},
            {3, methodFilter + " (3)", "10,000,000", 50, "200,000"}
        };

        // Apply sorting
        if (sortOption.equals("Doanh thu tăng dần")) {
            java.util.Arrays.sort(sampleData, (a, b) -> {
                String valA = (String) a[2];
                String valB = (String) b[2];
                return Integer.compare(
                    Integer.parseInt(valA.replace(",", "")),
                    Integer.parseInt(valB.replace(",", ""))
                );
            });
        } else if (sortOption.equals("Doanh thu giảm dần")) {
            java.util.Arrays.sort(sampleData, (a, b) -> {
                String valA = (String) a[2];
                String valB = (String) b[2];
                return Integer.compare(
                    Integer.parseInt(valB.replace(",", "")),
                    Integer.parseInt(valA.replace(",", ""))
                );
            });
        }

        for (Object[] row : sampleData) {
            model.addRow(row);
        }
    }

    private void updateEmployeeTable(DefaultTableModel model, String employeeFilter, String sortOption) {
        model.setRowCount(0);
        // Sample data - replace with actual data retrieval
        Object[][] sampleData = {
            {1, "NV001", "Nguyễn Văn A", "9,000,000", 45, "200,000"},
            {2, "NV002", "Trần Thị B", "13,000,000", 65, "200,000"},
            {3, "NV003", "Lê Văn C", "11,000,000", 55, "200,000"}
        };

        // Apply sorting
        if (sortOption.equals("Doanh thu tăng dần")) {
            java.util.Arrays.sort(sampleData, (a, b) -> {
                String valA = (String) a[3];
                String valB = (String) b[3];
                return Integer.compare(
                    Integer.parseInt(valA.replace(",", "")),
                    Integer.parseInt(valB.replace(",", ""))
                );
            });
        } else if (sortOption.equals("Doanh thu giảm dần")) {
            java.util.Arrays.sort(sampleData, (a, b) -> {
                String valA = (String) a[3];
                String valB = (String) b[3];
                return Integer.compare(
                    Integer.parseInt(valB.replace(",", "")),
                    Integer.parseInt(valA.replace(",", ""))
                );
            });
        }

        for (Object[] row : sampleData) {
            model.addRow(row);
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

        statsPanel.add(createStatBox("SẢN PHẨM BÁN CHẠY", "0", "sp"));
        statsPanel.add(createStatBox("SẢN PHẨM BÁN CHẬM", "0", "sp"));
        statsPanel.add(createStatBox("SẮP HẾT HÀNG", "0", "sp"));

        parent.add(statsPanel);
        parent.add(Box.createVerticalStrut(10));

        String[] columns = {"STT", "Mã SP", "Tên SP", "Số lượng bán", "Doanh thu", "Tồn kho"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        JTable table = new JTable(model);
        table.setRowHeight(25);
        setCenterRenderer(table);

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

        statsPanel.add(createStatBox("ĐANG XỬ LÝ", "0", "đơn"));
        statsPanel.add(createStatBox("ĐÃ GIAO", "0", "đơn"));
        statsPanel.add(createStatBox("ĐÃ HỦY", "0", "đơn"));

        parent.add(statsPanel);
        parent.add(Box.createVerticalStrut(10));

        JPanel ratePanel = new JPanel(new GridLayout(1, 2, 20, 0));
        ratePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        ratePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        ratePanel.add(createRateBox("TỶ LỆ ONLINE THÀNH CÔNG", "0%", new Color(40, 167, 69)));
        ratePanel.add(createRateBox("TỶ LỆ ONLINE THẤT BẠI", "0%", new Color(220, 53, 69)));

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

        statsPanel.add(createStatBox("TỔNG CHI PHÍ", "0", "VND"));
        statsPanel.add(createStatBox("NHẬP NHIỀU NHẤT", "0", "sp"));
        statsPanel.add(createStatBox("NHẬP ÍT NHẤT", "0", "sp"));

        parent.add(statsPanel);
        parent.add(Box.createVerticalStrut(10));

        String[] columns = {"STT", "Thời gian", "Mã SP", "Tên SP", "Số lượng", "Chi phí"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        JTable table = new JTable(model);
        table.setRowHeight(25);
        setCenterRenderer(table);

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

        statsPanel.add(createStatBox("TỔNG LỢI NHUẬN", "0", "VND"));
        statsPanel.add(createStatBox("TỶ LỆ LỢI NHUẬN", "0%", ""));

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

    private void refreshData() {
        // TODO: Implement data refresh logic
        JOptionPane.showMessageDialog(this, "Dữ liệu đã được làm mới!");
    }
}