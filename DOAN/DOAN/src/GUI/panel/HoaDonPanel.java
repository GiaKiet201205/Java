package GUI.panel;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.EmptyBorder;

public class HoaDonPanel extends JPanel {
    private DefaultTableModel hoaDonTableModel;
    private JTable hoaDonTable;
    private JPanel hoaDonPanel;
    private Color headerColor = new Color(200, 255, 200);
    private JTextField txtSearchID, dateFrom, dateTo;

    public HoaDonPanel() {
        setLayout(new BorderLayout());
        createHoaDonPanel();
        add(hoaDonPanel, BorderLayout.CENTER);
    }

    private void createHoaDonPanel() {
        hoaDonPanel = new JPanel(new BorderLayout());

        // Top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(headerColor);
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Function buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setOpaque(false);
        JButton btnEdit = new JButton();
        btnEdit.setToolTipText("Sửa");
        buttonPanel.add(btnEdit);

        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labelPanel.setOpaque(false);
        labelPanel.add(new JLabel("Sửa đơn hàng"));

        JPanel functionPanel = new JPanel(new BorderLayout());
        functionPanel.setOpaque(false);
        functionPanel.add(buttonPanel, BorderLayout.NORTH);
        functionPanel.add(labelPanel, BorderLayout.SOUTH);
        topPanel.add(functionPanel, BorderLayout.CENTER);

        // Search Panel
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 5, 2, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblSearchID = new JLabel("Tìm theo ID:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        searchPanel.add(lblSearchID, gbc);

        txtSearchID = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 0;
        searchPanel.add(txtSearchID, gbc);

        JLabel lblSearchDate = new JLabel("Tìm theo ngày:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        searchPanel.add(lblSearchDate, gbc);

        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        datePanel.setOpaque(false);
        JLabel lblFrom = new JLabel("Từ:");
        datePanel.add(lblFrom);
        dateFrom = new JTextField(10);
        datePanel.add(dateFrom);
        JLabel lblTo = new JLabel("Đến:");
        datePanel.add(lblTo);
        dateTo = new JTextField(10);
        datePanel.add(dateTo);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        searchPanel.add(datePanel, gbc);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setOpaque(false);
        JButton btnSearch = new JButton("Tìm kiếm");
        btnSearch.setBackground(new Color(240, 240, 240));
        actionPanel.add(btnSearch);
        JButton btnReset = new JButton("Làm mới");
        btnReset.setBackground(new Color(240, 240, 240));
        actionPanel.add(btnReset);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        searchPanel.add(actionPanel, gbc);
        topPanel.add(searchPanel, BorderLayout.EAST);

        hoaDonPanel.add(topPanel, BorderLayout.NORTH);

        // Table
        String[] columns = {
            "ID đơn hàng", "ID khách hàng", "Ngày đặt hàng",
            "Tổng tiền", "ID nhân viên", "Trạng thái", "Hình thức mua hàng"
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

        // Event
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

    private void searchHoaDon(String idSearch, String fromDate, String toDate) {
        System.out.println("Tìm kiếm hóa đơn với:");
        System.out.println("ID chứa: " + idSearch);
        System.out.println("Từ ngày: " + fromDate);
        System.out.println("Đến ngày: " + toDate);
        JOptionPane.showMessageDialog(this, "Đã tìm kiếm theo các tiêu chí đã chọn", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void resetHoaDonSearch() {
        txtSearchID.setText("");
        dateFrom.setText("");
        dateTo.setText("");
        hoaDonTableModel.setRowCount(0); // Clear all data
        // Tạm thời chưa load lại từ DAO ở đây
        JOptionPane.showMessageDialog(this, "Đã làm mới bộ lọc tìm kiếm", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void editHoaDon(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một đơn hàng để sửa", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String id = hoaDonTableModel.getValueAt(selectedRow, 0).toString();
        String khachHang = hoaDonTableModel.getValueAt(selectedRow, 1).toString();
        String ngayDat = hoaDonTableModel.getValueAt(selectedRow, 2).toString();
        String tongTien = hoaDonTableModel.getValueAt(selectedRow, 3).toString();
        String nhanVien = hoaDonTableModel.getValueAt(selectedRow, 4).toString();
        String trangThai = hoaDonTableModel.getValueAt(selectedRow, 5).toString();

        JDialog editDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(hoaDonPanel), "Sửa đơn hàng", true);
        editDialog.setLayout(new GridLayout(7, 2, 10, 10));
        editDialog.setSize(400, 300);
        editDialog.setLocationRelativeTo(null);

        JTextField txtId = new JTextField(id); txtId.setEditable(false);
        JTextField txtKhachHang = new JTextField(khachHang);
        JTextField txtNgayDat = new JTextField(ngayDat);
        JTextField txtTongTien = new JTextField(tongTien);
        JTextField txtNhanVien = new JTextField(nhanVien);
        JTextField txtTrangThai = new JTextField(trangThai);

        editDialog.add(new JLabel("ID đơn hàng:")); editDialog.add(txtId);
        editDialog.add(new JLabel("ID khách hàng:")); editDialog.add(txtKhachHang);
        editDialog.add(new JLabel("Ngày đặt hàng:")); editDialog.add(txtNgayDat);
        editDialog.add(new JLabel("Tổng tiền:")); editDialog.add(txtTongTien);
        editDialog.add(new JLabel("ID nhân viên:")); editDialog.add(txtNhanVien);
        editDialog.add(new JLabel("Trạng thái:")); editDialog.add(txtTrangThai);

        JButton btnLuu = new JButton("Lưu");
        JButton btnHuy = new JButton("Hủy");

        btnLuu.addActionListener(e -> {
            hoaDonTableModel.setValueAt(txtKhachHang.getText(), selectedRow, 1);
            hoaDonTableModel.setValueAt(txtNgayDat.getText(), selectedRow, 2);
            hoaDonTableModel.setValueAt(txtTongTien.getText(), selectedRow, 3);
            hoaDonTableModel.setValueAt(txtNhanVien.getText(), selectedRow, 4);
            hoaDonTableModel.setValueAt(txtTrangThai.getText(), selectedRow, 5);
            editDialog.dispose();
        });

        btnHuy.addActionListener(e -> editDialog.dispose());

        editDialog.add(btnLuu); editDialog.add(btnHuy);
        editDialog.setVisible(true);
    }
}
