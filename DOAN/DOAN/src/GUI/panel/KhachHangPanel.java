package GUI.panel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.EmptyBorder;

public class KhachHangPanel extends JPanel {
    private JTable khachHangTable;
    private DefaultTableModel khachHangTableModel;
    private final Color headerColor = new Color(200, 255, 200);
    
    public KhachHangPanel() {
        setLayout(new BorderLayout());
        createKhachHangPanel();
    }

    private void createKhachHangPanel() {
        JPanel khachHangPanel = new JPanel(new BorderLayout());

        // Create top panel for title and functions
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(headerColor);
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Add "Chức năng" label
        JLabel lblChucNang = new JLabel("Chức năng");
        topPanel.add(lblChucNang, BorderLayout.WEST);

        // Add function buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setOpaque(false);

        // Add button
        JButton btnAdd = new JButton();
        btnAdd.setToolTipText("Thêm");
        buttonPanel.add(btnAdd);

        // Delete button
        JButton btnDelete = new JButton();
        btnDelete.setToolTipText("Xóa");
        buttonPanel.add(btnDelete);

        // Edit button
        JButton btnEdit = new JButton();
        btnEdit.setToolTipText("Sửa");
        buttonPanel.add(btnEdit);

        // Add labels below buttons
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labelPanel.setOpaque(false);
        labelPanel.add(new JLabel("Thêm"));
        labelPanel.add(Box.createHorizontalStrut(20));
        labelPanel.add(new JLabel("Xóa"));
        labelPanel.add(Box.createHorizontalStrut(20));
        labelPanel.add(new JLabel("Sửa"));

        JPanel functionPanel = new JPanel(new BorderLayout());
        functionPanel.setOpaque(false);
        functionPanel.add(buttonPanel, BorderLayout.NORTH);
        functionPanel.add(labelPanel, BorderLayout.SOUTH);

        topPanel.add(functionPanel, BorderLayout.CENTER);

        // Add search panel to top right
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setOpaque(false);

        JLabel lblTimKiem = new JLabel("Tìm kiếm");
        searchPanel.add(lblTimKiem);

        // Create combo box with search options
        JComboBox<String> cmbSearchType = new JComboBox<>(new String[]{"ID khách hàng", "Tên khách hàng", "SĐT"});
        cmbSearchType.setPreferredSize(new Dimension(120, 25));
        searchPanel.add(cmbSearchType);

        // Search text field
        JTextField txtSearchKH = new JTextField();
        txtSearchKH.setPreferredSize(new Dimension(200, 25));
        searchPanel.add(txtSearchKH);

        // Search button
        JButton btnSearch = new JButton("Tìm kiếm");
        btnSearch.setBackground(new Color(240, 240, 240));
        searchPanel.add(btnSearch);

        // Reset button
        JButton btnReset = new JButton("Làm mới");
        btnReset.setBackground(new Color(240, 240, 240));
        searchPanel.add(btnReset);

        topPanel.add(searchPanel, BorderLayout.EAST);

        khachHangPanel.add(topPanel, BorderLayout.NORTH);

        // Create table for customer data
        String[] columns = {"ID khách hàng", "Tên", "Email", "SĐT", "Password", "Username"};
        khachHangTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        khachHangTable = new JTable(khachHangTableModel);
        khachHangTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        khachHangTable.setRowHeight(25);

        // Center the text in cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < khachHangTable.getColumnCount(); i++) {
            khachHangTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(khachHangTable);
        khachHangPanel.add(scrollPane, BorderLayout.CENTER);

        // Add action listeners for buttons
        btnSearch.addActionListener(e -> searchKhachHang(
                cmbSearchType.getSelectedItem().toString(),
                txtSearchKH.getText()));

        btnReset.addActionListener(e -> {
            txtSearchKH.setText("");
            khachHangTableModel.setRowCount(0);
        });

        btnAdd.addActionListener(e -> addKhachHang());
        btnDelete.addActionListener(e -> deleteKhachHang(khachHangTable.getSelectedRow()));
        btnEdit.addActionListener(e -> editKhachHang(khachHangTable.getSelectedRow()));

        // Add listener for Enter key in search field
        txtSearchKH.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchKhachHang(
                            cmbSearchType.getSelectedItem().toString(),
                            txtSearchKH.getText());
                }
            }
        });

        this.add(khachHangPanel);
    }

    private void searchKhachHang(String searchType, String keyword) {
        if (keyword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // For demonstration purposes, assume search queries are handled in this method
        // In a real application, you would query the database or data model for results

        // Reset table and perform search (the search logic is omitted for brevity)
    }

    private void addKhachHang() {
        JTextField txtID = new JTextField();
        JTextField txtTen = new JTextField();
        JTextField txtDiaChi = new JTextField();
        JTextField txtSoDT = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("ID khách hàng:"));
        panel.add(txtID);
        panel.add(new JLabel("Tên khách hàng:"));
        panel.add(txtTen);
        panel.add(new JLabel("Địa chỉ:"));
        panel.add(txtDiaChi);
        panel.add(new JLabel("Số điện thoại:"));
        panel.add(txtSoDT);

        int result = JOptionPane.showConfirmDialog(this, panel, "Thêm khách hàng mới",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            Object[] newRow = {
                txtID.getText(), txtTen.getText(), txtDiaChi.getText(),
                txtSoDT.getText()
            };
            khachHangTableModel.addRow(newRow);
        }
    }

    private void deleteKhachHang(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để xóa",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa khách hàng?", 
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // In a real application, this would delete from the database
            khachHangTableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Đã xóa khách hàng thành công",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void editKhachHang(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để sửa",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JTextField txtID = new JTextField(khachHangTableModel.getValueAt(selectedRow, 0).toString());
        JTextField txtTen = new JTextField(khachHangTableModel.getValueAt(selectedRow, 1).toString());
        JTextField txtDiaChi = new JTextField(khachHangTableModel.getValueAt(selectedRow, 2).toString());
        JTextField txtSoDT = new JTextField(khachHangTableModel.getValueAt(selectedRow, 3).toString());

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("ID khách hàng:"));
        panel.add(txtID);
        panel.add(new JLabel("Tên khách hàng:"));
        panel.add(txtTen);
        panel.add(new JLabel("Địa chỉ:"));
        panel.add(txtDiaChi);
        panel.add(new JLabel("Số điện thoại:"));
        panel.add(txtSoDT);

        int result = JOptionPane.showConfirmDialog(this, panel, "Sửa thông tin khách hàng",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            khachHangTableModel.setValueAt(txtID.getText(), selectedRow, 0);
            khachHangTableModel.setValueAt(txtTen.getText(), selectedRow, 1);
            khachHangTableModel.setValueAt(txtDiaChi.getText(), selectedRow, 2);
            khachHangTableModel.setValueAt(txtSoDT.getText(), selectedRow, 3);
        }
    }
}
