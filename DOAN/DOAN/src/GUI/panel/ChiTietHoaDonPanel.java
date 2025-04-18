package GUI.panel;

import javax.swing.*;
import javax.swing.table.*; 
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.EmptyBorder;

public class ChiTietHoaDonPanel extends JPanel {
    private DefaultTableModel chiTietTableModel;
    private JTable chiTietTable;
    private JPanel chiTietPanel;
    private Color headerColor = new Color(200, 255, 200);
    private JTextField txtSearchID, txtSearchProduct;

    public ChiTietHoaDonPanel() {
        setLayout(new BorderLayout());
        createChiTietPanel();
        add(chiTietPanel, BorderLayout.CENTER);
    }

    private void createChiTietPanel() {
        chiTietPanel = new JPanel(new BorderLayout());

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
        labelPanel.add(new JLabel("Sửa chi tiết hóa đơn"));

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

        JLabel lblSearchID = new JLabel("Tìm theo ID hóa đơn:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        searchPanel.add(lblSearchID, gbc);

        txtSearchID = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 0;
        searchPanel.add(txtSearchID, gbc);

        JLabel lblSearchProduct = new JLabel("Tìm theo sản phẩm:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        searchPanel.add(lblSearchProduct, gbc);

        txtSearchProduct = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 1;
        searchPanel.add(txtSearchProduct, gbc);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setOpaque(false);
        JButton btnSearch = new JButton("Tìm kiếm");
        JButton btnReset = new JButton("Làm mới");
        actionPanel.add(btnSearch);
        actionPanel.add(btnReset);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        searchPanel.add(actionPanel, gbc);

        topPanel.add(searchPanel, BorderLayout.EAST);
        chiTietPanel.add(topPanel, BorderLayout.NORTH);

        // Table
        String[] columns = {
            "ID Hóa Đơn", "ID Sản Phẩm", "Tên Sản Phẩm",
            "Số Lượng", "Đơn Giá", "Thành Tiền"
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

        JScrollPane scrollPane = new JScrollPane(chiTietTable);
        chiTietPanel.add(scrollPane, BorderLayout.CENTER);

        // Event
        btnSearch.addActionListener(e ->
            searchChiTiet(txtSearchID.getText(), txtSearchProduct.getText()));
        btnReset.addActionListener(e -> resetChiTietSearch());
        btnEdit.addActionListener(e -> editChiTiet(chiTietTable.getSelectedRow()));

        txtSearchID.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchChiTiet(txtSearchID.getText(), txtSearchProduct.getText());
                }
            }
        });
    }

    private void searchChiTiet(String idHoaDon, String sanPham) {
        System.out.println("Tìm kiếm chi tiết hóa đơn:");
        System.out.println("ID hóa đơn: " + idHoaDon);
        System.out.println("Tên sản phẩm: " + sanPham);
        JOptionPane.showMessageDialog(this, "Đã tìm kiếm chi tiết hóa đơn", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void resetChiTietSearch() {
        txtSearchID.setText("");
        txtSearchProduct.setText("");
        chiTietTableModel.setRowCount(0); // Clear data
        JOptionPane.showMessageDialog(this, "Đã làm mới bộ lọc", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void editChiTiet(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một chi tiết để sửa", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idHoaDon = chiTietTableModel.getValueAt(selectedRow, 0).toString();
        String idSanPham = chiTietTableModel.getValueAt(selectedRow, 1).toString();
        String tenSanPham = chiTietTableModel.getValueAt(selectedRow, 2).toString();
        String soLuong = chiTietTableModel.getValueAt(selectedRow, 3).toString();
        String donGia = chiTietTableModel.getValueAt(selectedRow, 4).toString();

        JDialog editDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(chiTietPanel), "Sửa chi tiết hóa đơn", true);
        editDialog.setLayout(new GridLayout(6, 2, 10, 10));
        editDialog.setSize(400, 250);
        editDialog.setLocationRelativeTo(null);

        JTextField txtIdHoaDon = new JTextField(idHoaDon); txtIdHoaDon.setEditable(false);
        JTextField txtIdSanPham = new JTextField(idSanPham); txtIdSanPham.setEditable(false);
        JTextField txtTenSanPham = new JTextField(tenSanPham);
        JTextField txtSoLuong = new JTextField(soLuong);
        JTextField txtDonGia = new JTextField(donGia);

        editDialog.add(new JLabel("ID Hóa Đơn:")); editDialog.add(txtIdHoaDon);
        editDialog.add(new JLabel("ID Sản Phẩm:")); editDialog.add(txtIdSanPham);
        editDialog.add(new JLabel("Tên Sản Phẩm:")); editDialog.add(txtTenSanPham);
        editDialog.add(new JLabel("Số Lượng:")); editDialog.add(txtSoLuong);
        editDialog.add(new JLabel("Đơn Giá:")); editDialog.add(txtDonGia);

        JButton btnLuu = new JButton("Lưu");
        JButton btnHuy = new JButton("Hủy");

        btnLuu.addActionListener(e -> {
            chiTietTableModel.setValueAt(txtTenSanPham.getText(), selectedRow, 2);
            chiTietTableModel.setValueAt(txtSoLuong.getText(), selectedRow, 3);
            chiTietTableModel.setValueAt(txtDonGia.getText(), selectedRow, 4);
            double thanhTien = Double.parseDouble(txtSoLuong.getText()) * Double.parseDouble(txtDonGia.getText());
            chiTietTableModel.setValueAt(String.format("%.2f", thanhTien), selectedRow, 5);
            editDialog.dispose();
        });

        btnHuy.addActionListener(e -> editDialog.dispose());

        editDialog.add(btnLuu); editDialog.add(btnHuy);
        editDialog.setVisible(true);
    }
}

