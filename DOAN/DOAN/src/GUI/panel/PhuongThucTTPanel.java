package GUI.panel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PhuongThucTTPanel extends JPanel {
    private JPanel phuongThucPanel;
    private JTable phuongThucTable;
    private DefaultTableModel phuongThucTableModel;
    private Color headerColor = new Color(200, 255, 200);

    public PhuongThucTTPanel() {
        createPhuongThucTTPanel();
    }

    private void createPhuongThucTTPanel() {
        phuongThucPanel = new JPanel(new BorderLayout());

        // Top Panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(headerColor);
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel lblChucNang = new JLabel("Chức năng");
        topPanel.add(lblChucNang, BorderLayout.WEST);

        // Function Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setOpaque(false);
        JButton btnAdd = new JButton("Thêm");
        JButton btnDelete = new JButton("Xóa");
        JButton btnEdit = new JButton("Sửa");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnEdit);

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

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setOpaque(false);

        JLabel lblTimKiem = new JLabel("Tìm kiếm");
        JComboBox<String> cmbSearchType = new JComboBox<>(new String[]{"Mã thanh toán", "Tên thanh toán"});
        cmbSearchType.setPreferredSize(new Dimension(120, 25));
        JTextField txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(200, 25));
        JButton btnSearch = new JButton("Tìm kiếm");
        JButton btnReset = new JButton("Làm mới");

        searchPanel.add(lblTimKiem);
        searchPanel.add(cmbSearchType);
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnReset);

        topPanel.add(searchPanel, BorderLayout.EAST);
        phuongThucPanel.add(topPanel, BorderLayout.NORTH);

        // Table
        String[] columns = {"Mã thanh toán", "Tên thanh toán"};
        phuongThucTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        phuongThucTable = new JTable(phuongThucTableModel);
        phuongThucTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        phuongThucTable.setRowHeight(25);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < phuongThucTable.getColumnCount(); i++) {
            phuongThucTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(phuongThucTable);
        phuongThucPanel.add(scrollPane, BorderLayout.CENTER);

        // Button actions
        btnSearch.addActionListener(e -> searchPhuongThuc(
                cmbSearchType.getSelectedItem().toString(),
                txtSearch.getText()
        ));

        btnReset.addActionListener(e -> {
            txtSearch.setText("");
            phuongThucTableModel.setRowCount(0);
            loadPhuongThucData();
        });

        btnAdd.addActionListener(e -> addPhuongThuc());
        btnDelete.addActionListener(e -> deletePhuongThuc(phuongThucTable.getSelectedRow()));
        btnEdit.addActionListener(e -> editPhuongThuc(phuongThucTable.getSelectedRow()));

        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchPhuongThuc(
                            cmbSearchType.getSelectedItem().toString(),
                            txtSearch.getText()
                    );
                }
            }
        });

        // Add panel to main view
        this.setLayout(new BorderLayout());
        this.add(phuongThucPanel, BorderLayout.CENTER);
    }

    private void loadPhuongThucData() {
        // Load real data from database or file
    }

    private void searchPhuongThuc(String searchType, String keyword) {
        if (keyword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        phuongThucTableModel.setRowCount(0);

        // Thực hiện tìm kiếm từ CSDL hoặc danh sách dữ liệu thực tế
    }

    private void addPhuongThuc() {
        JOptionPane.showMessageDialog(this, "Đang mở form thêm phương thức thanh toán mới",
                "Thêm phương thức", JOptionPane.INFORMATION_MESSAGE);
    }

    private void deletePhuongThuc(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phương thức để xóa",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String id = phuongThucTableModel.getValueAt(selectedRow, 0).toString();
        String name = phuongThucTableModel.getValueAt(selectedRow, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa phương thức: " + name + " (" + id + ")?",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            phuongThucTableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Đã xóa phương thức thanh toán thành công",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void editPhuongThuc(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phương thức để sửa",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String id = phuongThucTableModel.getValueAt(selectedRow, 0).toString();

        JOptionPane.showMessageDialog(this, "Đang mở form sửa phương thức: " + id,
                "Sửa phương thức", JOptionPane.INFORMATION_MESSAGE);
    }
}
