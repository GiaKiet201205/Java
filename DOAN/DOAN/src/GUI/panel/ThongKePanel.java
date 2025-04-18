package GUI.panel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ThongKePanel extends JPanel {
    private JPanel thongKePanel;
    public ThongKePanel() {
        setLayout(new BorderLayout());

        thongKePanel = new JPanel();
        thongKePanel.setLayout(new BoxLayout(thongKePanel, BoxLayout.Y_AXIS));
        thongKePanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane thongKeScrollPane = new JScrollPane(thongKePanel);
        thongKeScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        thongKeScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        thongKeScrollPane.setBorder(null);

        add(thongKeScrollPane, BorderLayout.CENTER);

        createThongKeTongHop();
    }

    private void createThongKeTongHop() {
        JLabel lblMainTitle = new JLabel("THỐNG KÊ TỔNG HỢP", JLabel.CENTER);
        lblMainTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblMainTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        thongKePanel.add(lblMainTitle);
        thongKePanel.add(Box.createVerticalStrut(20));

        createDoanhThuSection();
        createSanPhamSection();
        createDonHangSection();
    }

    private void createDoanhThuSection() {
        JLabel lblTitle = new JLabel("THỐNG KÊ DOANH THU");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        thongKePanel.add(lblTitle);
        thongKePanel.add(Box.createVerticalStrut(10));

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        controlPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        controlPanel.add(new JLabel("Thống kê theo:"));
        String[] filterOptions = {"Ngày", "Tháng", "Năm", "Nhân viên", "Sản phẩm", "Nhóm sản phẩm"};
        JComboBox<String> cmbFilter = new JComboBox<>(filterOptions);
        cmbFilter.setPreferredSize(new Dimension(150, 25));
        controlPanel.add(cmbFilter);

        controlPanel.add(Box.createHorizontalStrut(20));
        JButton btnXem = new JButton("Xem");
        btnXem.setBackground(new Color(60, 141, 188));
        btnXem.setForeground(Color.WHITE);
        controlPanel.add(btnXem);

        thongKePanel.add(controlPanel);
        thongKePanel.add(Box.createVerticalStrut(10));

        String[] columns = {"STT", "Thời gian/Đối tượng", "Tổng doanh thu", "Số đơn hàng", "Trung bình"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        JTable table = new JTable(model);
        table.setRowHeight(30);
        setCenterRenderer(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(750, 180));
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        thongKePanel.add(scrollPane);
        thongKePanel.add(Box.createVerticalStrut(30));
    }

    private void createSanPhamSection() {
        JLabel lblTitle = new JLabel("THỐNG KÊ SẢN PHẨM");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        thongKePanel.add(lblTitle);
        thongKePanel.add(Box.createVerticalStrut(10));

        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        statsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        statsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        statsPanel.add(createStatBox("SẢN PHẨM BÁN CHẠY", "", ""));
        statsPanel.add(createStatBox("SẢN PHẨM BÁN CHẬM", "", ""));
        statsPanel.add(createStatBox("TỔNG TỒN KHO", "", ""));

        thongKePanel.add(statsPanel);
        thongKePanel.add(Box.createVerticalStrut(10));

        String[] columns = {"STT", "Mã sản phẩm", "Tên sản phẩm", "Số lượng bán", "Doanh thu", "Tồn kho"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        JTable table = new JTable(model);
        table.setRowHeight(30);
        setCenterRenderer(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(750, 180));
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        thongKePanel.add(scrollPane);
        thongKePanel.add(Box.createVerticalStrut(30));
    }

    private void createDonHangSection() {
        JLabel lblTitle = new JLabel("THỐNG KÊ ĐƠN HÀNG");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        thongKePanel.add(lblTitle);
        thongKePanel.add(Box.createVerticalStrut(10));

        JPanel statsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        statsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        statsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        statsPanel.add(createStatBox("TỔNG ĐƠN HÀNG", "", ""));

        JPanel ratePanel = new JPanel();
        ratePanel.setLayout(new BoxLayout(ratePanel, BoxLayout.Y_AXIS));
        ratePanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        ratePanel.setBackground(Color.WHITE);
        ratePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        JLabel rateTitle = new JLabel("TỶ LỆ ĐƠN HÀNG", JLabel.CENTER);
        rateTitle.setFont(new Font("Arial", Font.BOLD, 14));
        rateTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        ratePanel.add(rateTitle);

        JPanel rateInfoPanel = new JPanel(new GridLayout(1, 2));
        rateInfoPanel.setOpaque(false);

        rateInfoPanel.add(createRateBox("Thành công", "", new Color(40, 167, 69)));
        rateInfoPanel.add(createRateBox("Hủy", "", new Color(220, 53, 69)));

        ratePanel.add(rateInfoPanel);
        statsPanel.add(ratePanel);

        thongKePanel.add(statsPanel);
        thongKePanel.add(Box.createVerticalStrut(30));
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
        panel.setOpaque(false);

        JLabel lbl = new JLabel(label, JLabel.CENTER);
        JLabel lblValue = new JLabel(value, JLabel.CENTER);
        lblValue.setFont(new Font("Arial", Font.BOLD, 20));
        lblValue.setForeground(color);

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
}
