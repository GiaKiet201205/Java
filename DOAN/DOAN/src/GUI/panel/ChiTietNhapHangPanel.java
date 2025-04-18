package GUI.panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.EmptyBorder;

public class ChiTietNhapHangPanel extends JPanel {
    private JPanel chiTietNhapHangPanel;
    private JTable chiTietNhapHangTable;
    private DefaultTableModel chiTietNhapHangTableModel;

    public ChiTietNhapHangPanel() {
        chiTietNhapHangPanel = new JPanel(new BorderLayout());
        createChiTietNhapHangPanel();
    }

    private void createChiTietNhapHangPanel() {
        chiTietNhapHangPanel = new JPanel(new BorderLayout());

        // Create top panel for title and functions
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(200, 255, 200)); 
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
        JComboBox<String> cmbSearchType = new JComboBox<>(new String[]{"ID CTNH", "ID Nhập hàng", "ID sản phẩm"});
        cmbSearchType.setPreferredSize(new Dimension(120, 25));
        searchPanel.add(cmbSearchType);

        // Search text field
        JTextField txtSearchCTNH = new JTextField();
        txtSearchCTNH.setPreferredSize(new Dimension(200, 25));
        searchPanel.add(txtSearchCTNH);

        // Search button
        JButton btnSearch = new JButton("Tìm kiếm");
        btnSearch.setBackground(new Color(240, 240, 240));
        searchPanel.add(btnSearch);

        // Reset button
        JButton btnReset = new JButton("Làm mới");
        btnReset.setBackground(new Color(240, 240, 240));
        searchPanel.add(btnReset);

        topPanel.add(searchPanel, BorderLayout.EAST);

        chiTietNhapHangPanel.add(topPanel, BorderLayout.NORTH);

        // Create table for import detail data
        String[] columns = {"ID CTNH", "ID Nhập hàng", "ID sản phẩm", "Số lượng nhập", "Giá nhập", "Thành tiền"};
        chiTietNhapHangTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        chiTietNhapHangTable = new JTable(chiTietNhapHangTableModel);
        chiTietNhapHangTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        chiTietNhapHangTable.setRowHeight(25);

        // Center the text in cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < chiTietNhapHangTable.getColumnCount(); i++) {
            chiTietNhapHangTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(chiTietNhapHangTable);
        chiTietNhapHangPanel.add(scrollPane, BorderLayout.CENTER);

        // Add action listeners for buttons
        btnSearch.addActionListener(e -> searchChiTietNhapHang(cmbSearchType.getSelectedItem().toString(), txtSearchCTNH.getText()));

        btnReset.addActionListener(e -> {
            txtSearchCTNH.setText("");
            loadAllChiTietNhapHang();
        });

        btnAdd.addActionListener(e -> addChiTietNhapHang());
        btnDelete.addActionListener(e -> deleteChiTietNhapHang(chiTietNhapHangTable.getSelectedRow()));
        btnEdit.addActionListener(e -> editChiTietNhapHang(chiTietNhapHangTable.getSelectedRow()));

        // Add listener for Enter key in search field
        txtSearchCTNH.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchChiTietNhapHang(cmbSearchType.getSelectedItem().toString(), txtSearchCTNH.getText());
                }
            }
        });
    }

    private void loadAllChiTietNhapHang() {
        chiTietNhapHangTableModel.setRowCount(0); // Xóa dữ liệu hiện tại
        chiTietNhapHangTableModel.addRow(new Object[]{"CTNH001", "NH001", "SP001", 10, 10000, 100000});
        chiTietNhapHangTableModel.addRow(new Object[]{"CTNH002", "NH002", "SP002", 5, 20000, 100000});
    }

    private void searchChiTietNhapHang(String searchType, String keyword) {
        if (keyword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm", 
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, 
                "Đang tìm kiếm chi tiết nhập hàng theo " + searchType + " với từ khóa: " + keyword,
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void addChiTietNhapHang() {
        // Code for adding ChiTietNhapHang
    }

    private void editChiTietNhapHang(int selectedRow) {
        // Code for editing ChiTietNhapHang
    }

    private void deleteChiTietNhapHang(int selectedRow) {
        // Code for deleting ChiTietNhapHang
    }

    public JPanel getChiTietNhapHangPanel() {
        return chiTietNhapHangPanel;
    }
}

