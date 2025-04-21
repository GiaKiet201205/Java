package GUI.panel;

import DAO.DanhMucDAO;
import DTO.DanhMucDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.EmptyBorder;
import java.util.ArrayList;

public class DanhMucPanel extends JPanel {
    private JPanel danhMucPanel;
    private JTable danhMucTable;
    private DefaultTableModel danhMucTableModel;
    private Color headerColor = new Color(200, 255, 200);
    private DanhMucDAO danhMucDAO;

    public DanhMucPanel() {
        setLayout(new BorderLayout()); 
        setPreferredSize(new Dimension(600, 400)); 
        danhMucDAO = new DanhMucDAO(); // Khởi tạo đối tượng DAO
        createDanhMucPanel(); 
        add(danhMucPanel, BorderLayout.CENTER); 
        add(new JLabel("Danh mục", SwingConstants.CENTER), BorderLayout.NORTH); 
    }

    private void createDanhMucPanel() {
        danhMucPanel = new JPanel(new BorderLayout());

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
        JButton btnAdd = new JButton("Thêm");
        btnAdd.setToolTipText("Thêm danh mục");
        buttonPanel.add(btnAdd);

        // Delete button
        JButton btnDelete = new JButton("Xóa");
        btnDelete.setToolTipText("Xóa danh mục");
        buttonPanel.add(btnDelete);

        // Edit button
        JButton btnEdit = new JButton("Sửa");
        btnEdit.setToolTipText("Sửa danh mục");
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
        JComboBox<String> cmbSearchType = new JComboBox<>(new String[]{"ID danh mục", "Tên danh mục"});
        cmbSearchType.setPreferredSize(new Dimension(120, 25));
        searchPanel.add(cmbSearchType);

        // Search text field
        JTextField txtSearchDanhMuc = new JTextField();
        txtSearchDanhMuc.setPreferredSize(new Dimension(200, 25));
        searchPanel.add(txtSearchDanhMuc);

        // Search button
        JButton btnSearch = new JButton("Tìm kiếm");
        btnSearch.setBackground(new Color(240, 240, 240));
        searchPanel.add(btnSearch);

        // Reset button
        JButton btnReset = new JButton("Làm mới");
        btnReset.setBackground(new Color(240, 240, 240));
        searchPanel.add(btnReset);

        topPanel.add(searchPanel, BorderLayout.EAST);

        danhMucPanel.add(topPanel, BorderLayout.NORTH);

        // Create table for danh mục data
        String[] columns = {"ID danh mục", "Tên danh mục"};
        danhMucTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        danhMucTable = new JTable(danhMucTableModel);
        danhMucTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        danhMucTable.setRowHeight(25);

        // Center the text in cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < danhMucTable.getColumnCount(); i++) {
            danhMucTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(danhMucTable);
        danhMucPanel.add(scrollPane, BorderLayout.CENTER);

        // Add action listeners for buttons
        btnSearch.addActionListener(e -> searchDanhMuc(
                cmbSearchType.getSelectedItem().toString(),
                txtSearchDanhMuc.getText()));

        btnReset.addActionListener(e -> {
            txtSearchDanhMuc.setText("");
            danhMucTableModel.setRowCount(0);
            loadDanhMucData(); // Reload all data after reset
        });

        btnAdd.addActionListener(e -> addDanhMuc());
        btnDelete.addActionListener(e -> deleteDanhMuc(danhMucTable.getSelectedRow()));
        btnEdit.addActionListener(e -> editDanhMuc(danhMucTable.getSelectedRow()));

        // Add listener for Enter key in search field
        txtSearchDanhMuc.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchDanhMuc(
                            cmbSearchType.getSelectedItem().toString(),
                            txtSearchDanhMuc.getText());
                }
            }
        });

        // Load data initially
        loadDanhMucData();
    }

    private void loadDanhMucData() {
        ArrayList<DanhMucDTO> danhMucList = danhMucDAO.selectAll();
        danhMucTableModel.setRowCount(0); // Clear existing rows

        for (DanhMucDTO danhMuc : danhMucList) {
            danhMucTableModel.addRow(new Object[]{danhMuc.getIdDanhMuc(), danhMuc.getTenDanhMuc()});
        }
    }

    private void searchDanhMuc(String searchType, String keyword) {
        if (keyword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        danhMucTableModel.setRowCount(0);

        ArrayList<DanhMucDTO> allData = danhMucDAO.selectAll(); // Get all data from DAO

        int columnIndex = searchType.equals("Tên danh mục") ? 1 : 0;

        for (DanhMucDTO row : allData) {
            if (row.getIdDanhMuc().toLowerCase().contains(keyword.toLowerCase()) ||
                row.getTenDanhMuc().toLowerCase().contains(keyword.toLowerCase())) {
                danhMucTableModel.addRow(new Object[]{row.getIdDanhMuc(), row.getTenDanhMuc()});
            }
        }

        if (danhMucTableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy danh mục phù hợp",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void addDanhMuc() {
        JOptionPane.showMessageDialog(this, "Đang mở form thêm danh mục mới",
                "Thêm danh mục", JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteDanhMuc(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một danh mục để xóa",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String categoryId = danhMucTableModel.getValueAt(selectedRow, 0).toString();
        String categoryName = danhMucTableModel.getValueAt(selectedRow, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa danh mục: " + categoryName + " (" + categoryId + ")?",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            danhMucDAO.delete(categoryId); // Delete from database
            danhMucTableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Đã xóa danh mục thành công",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void editDanhMuc(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một danh mục để sửa",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String categoryId = danhMucTableModel.getValueAt(selectedRow, 0).toString();
        JOptionPane.showMessageDialog(this, "Đang mở form sửa danh mục: " + categoryId,
                "Sửa danh mục", JOptionPane.INFORMATION_MESSAGE);
    }
}
