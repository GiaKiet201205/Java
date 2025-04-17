package GUI.panel;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.EmptyBorder;

public class NhapHangPanel extends JPanel {
    private DefaultTableModel nhapHangTableModel;
    private JTable nhapHangTable;
    private JPanel nhapHangPanel;
    private Color headerColor = new Color(200, 255, 200);
    private JTextField txtSearchID, dateFrom, dateTo;

    public NhapHangPanel() {
        setLayout(new BorderLayout());
        createNhapHangPanel();
        add(nhapHangPanel, BorderLayout.CENTER);
    }

    private void createNhapHangPanel() {
        nhapHangPanel = new JPanel(new BorderLayout());

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
        labelPanel.add(new JLabel("Sửa nhập hàng"));

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

        nhapHangPanel.add(topPanel, BorderLayout.NORTH);

        // Table
        String[] columns = {
            "ID nhập hàng", "Tên sản phẩm", "Ngày nhập",
            "Số lượng", "Giá nhập", "Tổng tiền"
        };

        nhapHangTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        nhapHangTable = new JTable(nhapHangTableModel);
        nhapHangTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        nhapHangTable.setRowHeight(25);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < nhapHangTable.getColumnCount(); i++) {
            nhapHangTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(nhapHangTable);
        nhapHangPanel.add(scrollPane, BorderLayout.CENTER);

        // Event
        btnSearch.addActionListener(e ->
            searchNhapHang(txtSearchID.getText(), dateFrom.getText(), dateTo.getText()));
        btnReset.addActionListener(e -> resetNhapHangSearch());
        btnEdit.addActionListener(e -> editNhapHang(nhapHangTable.getSelectedRow()));

        txtSearchID.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchNhapHang(txtSearchID.getText(), dateFrom.getText(), dateTo.getText());
                }
            }
        });
    }

    private void searchNhapHang(String idSearch, String fromDate, String toDate) {
        System.out.println("Tìm kiếm nhập hàng với:");
        System.out.println("ID chứa: " + idSearch);
        System.out.println("Từ ngày: " + fromDate);
        System.out.println("Đến ngày: " + toDate);
        JOptionPane.showMessageDialog(this, "Đã tìm kiếm theo các tiêu chí đã chọn", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void resetNhapHangSearch() {
        txtSearchID.setText("");
        dateFrom.setText("");
        dateTo.setText("");
        nhapHangTableModel.setRowCount(0); // Clear all data
        // Tạm thời chưa load lại từ DAO ở đây
        JOptionPane.showMessageDialog(this, "Đã làm mới bộ lọc tìm kiếm", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void editNhapHang(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhập hàng để sửa", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String id = nhapHangTableModel.getValueAt(selectedRow, 0).toString();
        String tenSanPham = nhapHangTableModel.getValueAt(selectedRow, 1).toString();
        String ngayNhap = nhapHangTableModel.getValueAt(selectedRow, 2).toString();
        String soLuong = nhapHangTableModel.getValueAt(selectedRow, 3).toString();
        String giaNhap = nhapHangTableModel.getValueAt(selectedRow, 4).toString();
        String tongTien = nhapHangTableModel.getValueAt(selectedRow, 5).toString();

        JDialog editDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(nhapHangPanel), "Sửa nhập hàng", true);
        editDialog.setLayout(new GridLayout(6, 2, 10, 10));
        editDialog.setSize(400, 250);
        editDialog.setLocationRelativeTo(null);

        JTextField txtId = new JTextField(id); txtId.setEditable(false);
        JTextField txtTenSanPham = new JTextField(tenSanPham);
        JTextField txtNgayNhap = new JTextField(ngayNhap);
        JTextField txtSoLuong = new JTextField(soLuong);
        JTextField txtGiaNhap = new JTextField(giaNhap);
        JTextField txtTongTien = new JTextField(tongTien);

        editDialog.add(new JLabel("ID nhập hàng:")); editDialog.add(txtId);
        editDialog.add(new JLabel("Tên sản phẩm:")); editDialog.add(txtTenSanPham);
        editDialog.add(new JLabel("Ngày nhập:")); editDialog.add(txtNgayNhap);
        editDialog.add(new JLabel("Số lượng:")); editDialog.add(txtSoLuong);
        editDialog.add(new JLabel("Giá nhập:")); editDialog.add(txtGiaNhap);
        editDialog.add(new JLabel("Tổng tiền:")); editDialog.add(txtTongTien);

        JButton btnLuu = new JButton("Lưu");
        JButton btnHuy = new JButton("Hủy");

        btnLuu.addActionListener(e -> {
            nhapHangTableModel.setValueAt(txtTenSanPham.getText(), selectedRow, 1);
            nhapHangTableModel.setValueAt(txtNgayNhap.getText(), selectedRow, 2);
            nhapHangTableModel.setValueAt(txtSoLuong.getText(), selectedRow, 3);
            nhapHangTableModel.setValueAt(txtGiaNhap.getText(), selectedRow, 4);
            nhapHangTableModel.setValueAt(txtTongTien.getText(), selectedRow, 5);
            editDialog.dispose();
        });

        btnHuy.addActionListener(e -> editDialog.dispose());

        editDialog.add(btnLuu); editDialog.add(btnHuy);
        editDialog.setVisible(true);
    }
}
