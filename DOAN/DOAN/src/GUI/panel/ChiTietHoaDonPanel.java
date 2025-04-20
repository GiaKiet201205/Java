    package GUI.panel;

import DAO.ChiTietDonHangDAO;
import DTO.ChiTietDonHangDTO;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class ChiTietHoaDonPanel extends JPanel {
    private DefaultTableModel chiTietTableModel;
    private JTable chiTietTable;
    private JPanel chiTietPanel;
    private Color headerColor = new Color(200, 255, 200);
    private ChiTietDonHangDAO chiTietDonHangDAO;
    private DecimalFormat decimalFormat = new DecimalFormat("#,###");

    public ChiTietHoaDonPanel() {
        chiTietDonHangDAO = new ChiTietDonHangDAO();
        setLayout(new BorderLayout());
        createChiTietPanel();
        add(chiTietPanel, BorderLayout.CENTER);
        loadChiTietData(); // Load data on initialization
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
        JButton btnEdit = new JButton("Sửa");
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

        // Reset button
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setOpaque(false);
        JButton btnReset = new JButton("Làm mới");
        actionPanel.add(btnReset);
        topPanel.add(actionPanel, BorderLayout.EAST);

        chiTietPanel.add(topPanel, BorderLayout.NORTH);

        // Table
        String[] columns = {
            "ID Chi Tiết Đơn Hàng", "ID Đơn Hàng", "ID Sản Phẩm", "Số Lượng",
            "Đơn Giá"
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

        // Event listeners
        btnReset.addActionListener(e -> resetChiTietSearch());
        btnEdit.addActionListener(e -> editChiTiet(chiTietTable.getSelectedRow()));
    }

    private void loadChiTietData() {
        // Fetch all data from database and populate the table
        List<ChiTietDonHangDTO> chiTietList = chiTietDonHangDAO.selectAll();
        chiTietTableModel.setRowCount(0); // Clear existing data

        for (ChiTietDonHangDTO chiTiet : chiTietList) {
            Object[] row = {
                chiTiet.getIdChiTietDonHang(),
                chiTiet.getIdDonHang(),
                chiTiet.getIdSanPham(),
                chiTiet.getSoLuong(),
                decimalFormat.format(chiTiet.getGiaBan())
            };
            chiTietTableModel.addRow(row);
        }
    }

    private void resetChiTietSearch() {
        chiTietTableModel.setRowCount(0); // Clear data
        loadChiTietData(); // Reload data after reset
        JOptionPane.showMessageDialog(this, "Đã làm mới dữ liệu", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void editChiTiet(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một chi tiết để sửa", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idChiTietDonHang = chiTietTableModel.getValueAt(selectedRow, 0).toString();
        String idDonHang = chiTietTableModel.getValueAt(selectedRow, 1).toString();
        String idSanPham = chiTietTableModel.getValueAt(selectedRow, 2).toString();
        String soLuong = chiTietTableModel.getValueAt(selectedRow, 3).toString();
        String donGia = chiTietTableModel.getValueAt(selectedRow, 4).toString().replace(",", "");

        JDialog editDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(chiTietPanel), "Sửa chi tiết hóa đơn", true);
        editDialog.setLayout(new GridLayout(6, 2, 10, 10));
        editDialog.setSize(400, 250);
        editDialog.setLocationRelativeTo(null);

        JTextField txtIdChiTietDonHang = new JTextField(idChiTietDonHang);
        txtIdChiTietDonHang.setEditable(false);
        JTextField txtIdDonHang = new JTextField(idDonHang);
        txtIdDonHang.setEditable(false);
        JTextField txtIdSanPham = new JTextField(idSanPham);
        txtIdSanPham.setEditable(false);
        JTextField txtSoLuong = new JTextField(soLuong);
        JTextField txtDonGia = new JTextField(donGia);

        editDialog.add(new JLabel("ID Chi Tiết Đơn Hàng:"));
        editDialog.add(txtIdChiTietDonHang);
        editDialog.add(new JLabel("ID Đơn Hàng:"));
        editDialog.add(txtIdDonHang);
        editDialog.add(new JLabel("ID Sản Phẩm:"));
        editDialog.add(txtIdSanPham);
        editDialog.add(new JLabel("Số Lượng:"));
        editDialog.add(txtSoLuong);
        editDialog.add(new JLabel("Đơn Giá:"));
        editDialog.add(txtDonGia);

        JButton btnLuu = new JButton("Lưu");
        JButton btnHuy = new JButton("Hủy");

        btnLuu.addActionListener(e -> {
            try {
                int soLuongMoi = Integer.parseInt(txtSoLuong.getText());
                int donGiaMoi = Integer.parseInt(txtDonGia.getText());

                // Cập nhật bảng GUI
                chiTietTableModel.setValueAt(soLuongMoi, selectedRow, 3);
                chiTietTableModel.setValueAt(decimalFormat.format(donGiaMoi), selectedRow, 4);

                // Cập nhật cơ sở dữ liệu
                ChiTietDonHangDTO chiTiet = chiTietDonHangDAO.selectById(idChiTietDonHang);
                chiTiet.setSoLuong(soLuongMoi);
                chiTiet.setGiaBan(donGiaMoi);
                chiTietDonHangDAO.update(chiTiet);

                JOptionPane.showMessageDialog(this, "Cập nhật thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                editDialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Số lượng và đơn giá phải là số nguyên", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnHuy.addActionListener(e -> editDialog.dispose());

        editDialog.add(btnLuu);
        editDialog.add(btnHuy);
        editDialog.setVisible(true);
    }
}