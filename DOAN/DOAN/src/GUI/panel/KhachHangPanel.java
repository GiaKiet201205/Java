package GUI.panel;

import BLL.KhachHangBLL;
import DTO.KhachHangDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Lớp KhachHangPanel hiển thị giao diện quản lý khách hàng.
 * Sử dụng KhachHangBLL để xử lý logic nghiệp vụ.
 */
public class KhachHangPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private KhachHangBLL bll;

    public KhachHangPanel() {
        try {
            bll = new KhachHangBLL();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi khởi tạo KhachHangBLL: " + e.getMessage());
        }
        setLayout(new BorderLayout());
        createUI();
        loadTable();
    }

    private void createUI() {
        // Panel trên cùng
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(200, 255, 200));
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Tiêu đề
        JLabel lblTitle = new JLabel("KHÁCH HÀNG", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(lblTitle, BorderLayout.NORTH);

        // Nhãn chức năng
        JLabel lblChucNang = new JLabel("Chức năng");
        topPanel.add(lblChucNang, BorderLayout.WEST);

        // Panel chứa các nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setOpaque(false);
        JButton btnAdd = new JButton("Thêm");
        btnAdd.setToolTipText("Thêm khách hàng");
        btnAdd.setBackground(new Color(240, 240, 240));
        JButton btnDelete = new JButton("Xóa");
        btnDelete.setToolTipText("Xóa khách hàng");
        btnDelete.setBackground(new Color(240, 240, 240));
        JButton btnEdit = new JButton("Sửa");
        btnEdit.setToolTipText("Sửa khách hàng");
        btnEdit.setBackground(new Color(240, 240, 240));
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnEdit);

        // Panel chứa nhãn mô tả nút
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

        // Panel tìm kiếm
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setOpaque(false);
        JLabel lblSearch = new JLabel("Tìm kiếm");
        JComboBox<String> cmbSearchType = new JComboBox<>(new String[]{"ID khách hàng", "Tên khách hàng", "Email", "SĐT"});
        JTextField txtSearch = new JTextField();
        JButton btnSearch = new JButton("Tìm kiếm");
        btnSearch.setBackground(new Color(240, 240, 240));
        JButton btnReset = new JButton("Làm mới");
        btnReset.setBackground(new Color(240, 240, 240));

        cmbSearchType.setPreferredSize(new Dimension(120, 25));
        txtSearch.setPreferredSize(new Dimension(200, 25));
        searchPanel.add(lblSearch);
        searchPanel.add(cmbSearchType);
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnReset);

        topPanel.add(searchPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Bảng hiển thị dữ liệu
        model = new DefaultTableModel(new String[]{"ID", "Họ tên", "Email", "SĐT"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setRowHeight(25);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Căn giữa nội dung cột
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Gán sự kiện
        btnAdd.addActionListener(e -> showAddDialog());
        btnEdit.addActionListener(e -> showEditDialog(table.getSelectedRow()));
        btnDelete.addActionListener(e -> deleteKhachHang(table.getSelectedRow()));
        btnSearch.addActionListener(e -> searchKhachHang(cmbSearchType.getSelectedItem().toString(), txtSearch.getText()));
        btnReset.addActionListener(e -> {
            txtSearch.setText("");
            loadTable();
        });

        // Sự kiện nhấn Enter trong ô tìm kiếm
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchKhachHang(cmbSearchType.getSelectedItem().toString(), txtSearch.getText());
                }
            }
        });
    }

    private void loadTable() {
        model.setRowCount(0);
        ArrayList<KhachHangDTO> list = bll.getAllKhachHang();
        for (KhachHangDTO kh : list) {
            model.addRow(new Object[]{kh.getIdKhachHang(), kh.getHoTen(), kh.getEmail(), kh.getSdt()});
        }
    }

    private void showAddDialog() {
        JDialog addDialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Thêm Khách Hàng", Dialog.ModalityType.APPLICATION_MODAL);
        addDialog.setSize(450, 400);
        addDialog.setLocationRelativeTo(this);
        addDialog.setLayout(new BorderLayout());
        addDialog.getContentPane().setBackground(new Color(230, 255, 230));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(new Color(230, 255, 230));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);

        JLabel lblHoTen = new JLabel("Tên Khách Hàng:");
        lblHoTen.setFont(labelFont);
        JTextField txtHoTen = new JTextField(20);
        txtHoTen.setFont(fieldFont);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(labelFont);
        JTextField txtEmail = new JTextField(20);
        txtEmail.setFont(fieldFont);

        JLabel lblSdt = new JLabel("SĐT:");
        lblSdt.setFont(labelFont);
        JTextField txtSdt = new JTextField(20);
        txtSdt.setFont(fieldFont);

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(lblHoTen, gbc);
        gbc.gridx = 1; formPanel.add(txtHoTen, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(lblEmail, gbc);
        gbc.gridx = 1; formPanel.add(txtEmail, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(lblSdt, gbc);
        gbc.gridx = 1; formPanel.add(txtSdt, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(255, 255, 255));
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton btnAdd = new JButton("Thêm");
        btnAdd.setFont(new Font("Arial", Font.BOLD, 14));
        btnAdd.setForeground(Color.BLACK);
        btnAdd.setPreferredSize(new Dimension(100, 35));
        btnAdd.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JButton btnCancel = new JButton("Hủy");
        btnCancel.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancel.setForeground(Color.BLACK);
        btnCancel.setPreferredSize(new Dimension(100, 35));
        btnCancel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnCancel);

        addDialog.add(formPanel, BorderLayout.CENTER);
        addDialog.add(buttonPanel, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> {
            KhachHangDTO kh = new KhachHangDTO("", txtHoTen.getText().trim(), txtEmail.getText().trim(), txtSdt.getText().trim());
            String message = bll.addKhachHang(kh);
            JOptionPane.showMessageDialog(addDialog, message);
            if (message.contains("thành công")) {
                loadTable();
                addDialog.dispose();
            }
        });

        btnCancel.addActionListener(e -> addDialog.dispose());

        addDialog.setVisible(true);
    }

    private void showEditDialog(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để sửa!");
            return;
        }

        JDialog editDialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Sửa Khách Hàng", Dialog.ModalityType.APPLICATION_MODAL);
        editDialog.setSize(450, 400);
        editDialog.setLocationRelativeTo(this);
        editDialog.setLayout(new BorderLayout());
        editDialog.getContentPane().setBackground(new Color(230, 255, 230));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(new Color(230, 255, 230));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);

        JLabel lblIdKhachHang = new JLabel("ID Khách Hàng:");
        lblIdKhachHang.setFont(labelFont);
        JTextField txtIdKhachHang = new JTextField(model.getValueAt(selectedRow, 0).toString(), 20);
        txtIdKhachHang.setFont(fieldFont);
        txtIdKhachHang.setEditable(false);
        txtIdKhachHang.setBackground(new Color(220, 220, 220));

        JLabel lblHoTen = new JLabel("Tên Khách Hàng:");
        lblHoTen.setFont(labelFont);
        JTextField txtHoTen = new JTextField(model.getValueAt(selectedRow, 1).toString(), 20);
        txtHoTen.setFont(fieldFont);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(labelFont);
        JTextField txtEmail = new JTextField(model.getValueAt(selectedRow, 2).toString(), 20);
        txtEmail.setFont(fieldFont);

        JLabel lblSdt = new JLabel("SĐT:");
        lblSdt.setFont(labelFont);
        JTextField txtSdt = new JTextField(model.getValueAt(selectedRow, 3).toString(), 20);
        txtSdt.setFont(fieldFont);

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(lblIdKhachHang, gbc);
        gbc.gridx = 1; formPanel.add(txtIdKhachHang, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(lblHoTen, gbc);
        gbc.gridx = 1; formPanel.add(txtHoTen, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(lblEmail, gbc);
        gbc.gridx = 1; formPanel.add(txtEmail, gbc);
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(lblSdt, gbc);
        gbc.gridx = 1; formPanel.add(txtSdt, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(255, 255, 255));
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton btnSave = new JButton("Lưu");
        btnSave.setFont(new Font("Arial", Font.BOLD, 14));
        btnSave.setForeground(Color.BLACK);
        btnSave.setPreferredSize(new Dimension(100, 35));
        btnSave.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JButton btnCancel = new JButton("Hủy");
        btnCancel.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancel.setForeground(Color.BLACK);
        btnCancel.setPreferredSize(new Dimension(100, 35));
        btnCancel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        editDialog.add(formPanel, BorderLayout.CENTER);
        editDialog.add(buttonPanel, BorderLayout.SOUTH);

        btnSave.addActionListener(e -> {
            KhachHangDTO kh = new KhachHangDTO(txtIdKhachHang.getText().trim(), txtHoTen.getText().trim(), txtEmail.getText().trim(), txtSdt.getText().trim());
            String message = bll.updateKhachHang(kh);
            JOptionPane.showMessageDialog(editDialog, message);
            if (message.contains("thành công")) {
                loadTable();
                editDialog.dispose();
            }
        });

        btnCancel.addActionListener(e -> editDialog.dispose());

        editDialog.setVisible(true);
    }

    private void deleteKhachHang(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa khách hàng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String id = model.getValueAt(selectedRow, 0).toString();
            String message = bll.deleteKhachHang(id);
            JOptionPane.showMessageDialog(this, message);
            if (message.contains("thành công")) {
                model.removeRow(selectedRow);
            }
        }
    }

    private void searchKhachHang(String type, String keyword) {
        if (keyword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm!");
            return;
        }

        model.setRowCount(0);
        ArrayList<KhachHangDTO> result = bll.searchKhachHang(type, keyword);
        for (KhachHangDTO kh : result) {
            model.addRow(new Object[]{kh.getIdKhachHang(), kh.getHoTen(), kh.getEmail(), kh.getSdt()});
        }
        if (result.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng phù hợp!");
        }
    }
}