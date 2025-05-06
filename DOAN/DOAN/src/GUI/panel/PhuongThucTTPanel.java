package GUI.panel;

import DAO.PhuongThucThanhToanDAO;
import DTO.PhuongThucThanhToanDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class PhuongThucTTPanel extends JPanel {
    private JPanel phuongThucPanel;
    private JTable phuongThucTable;
    private DefaultTableModel phuongThucTableModel;
    private Color headerColor = new Color(200, 255, 200);
    private PhuongThucThanhToanDAO phuongThucThanhToanDAO;

    public PhuongThucTTPanel() {
        phuongThucThanhToanDAO = new PhuongThucThanhToanDAO();
        createPhuongThucTTPanel();
    }

    private void createPhuongThucTTPanel() {
        phuongThucPanel = new JPanel(new BorderLayout());

        // Top Panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(headerColor);
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Tiêu đề
        JLabel lblTitle = new JLabel("PHƯƠNG THỨC THANH TOÁN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(lblTitle, BorderLayout.NORTH);

        // Nhãn chức năng
        JLabel lblChucNang = new JLabel("Chức năng");
        topPanel.add(lblChucNang, BorderLayout.WEST);

        // Function Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setOpaque(false);
        JButton btnAdd = new JButton("Thêm");
        btnAdd.setToolTipText("Thêm phương thức thanh toán");
        btnAdd.setBackground(new Color(240, 240, 240));
        JButton btnDelete = new JButton("Xóa");
        btnDelete.setToolTipText("Xóa phương thức thanh toán");
        btnDelete.setBackground(new Color(240, 240, 240));
        JButton btnEdit = new JButton("Sửa");
        btnEdit.setToolTipText("Sửa phương thức thanh toán");
        btnEdit.setBackground(new Color(240, 240, 240));
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnEdit);

        // Nhãn mô tả nút
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
        btnSearch.setBackground(new Color(240, 240, 240));
        JButton btnReset = new JButton("Làm mới");
        btnReset.setBackground(new Color(240, 240, 240));

        searchPanel.add(lblTimKiem);
        searchPanel.add(cmbSearchType);
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnReset);

        topPanel.add(searchPanel, BorderLayout.EAST);
        phuongThucPanel.add(topPanel, BorderLayout.NORTH);

        // Table
        String[] columns = {"Mã thanh toán", "ID đơn hàng", "Loại thanh toán", "Trạng thái thanh toán"};
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

        // Initial load of data
        loadPhuongThucData();
    }

    private void loadPhuongThucData() {
        phuongThucTableModel.setRowCount(0);
        ArrayList<PhuongThucThanhToanDTO> list = phuongThucThanhToanDAO.selectAll();
        for (PhuongThucThanhToanDTO pttt : list) {
            phuongThucTableModel.addRow(new Object[]{
                    pttt.getIdPttt(),
                    pttt.getIdDonHang(),
                    pttt.getLoaiThanhToan(),
                    pttt.getTrangThaiThanhToan()
            });
        }
    }

    private void searchPhuongThuc(String searchType, String keyword) {
        if (keyword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        phuongThucTableModel.setRowCount(0);

        ArrayList<PhuongThucThanhToanDTO> list = phuongThucThanhToanDAO.selectAll();
        ArrayList<PhuongThucThanhToanDTO> results = new ArrayList<>();
        if (searchType.equals("Mã thanh toán")) {
            for (PhuongThucThanhToanDTO pttt : list) {
                if (pttt.getIdPttt().toLowerCase().contains(keyword.toLowerCase())) {
                    results.add(pttt);
                }
            }
        } else if (searchType.equals("Tên thanh toán")) {
            for (PhuongThucThanhToanDTO pttt : list) {
                if (pttt.getLoaiThanhToan().toLowerCase().contains(keyword.toLowerCase())) {
                    results.add(pttt);
                }
            }
        }

        for (PhuongThucThanhToanDTO pttt : results) {
            phuongThucTableModel.addRow(new Object[]{
                    pttt.getIdPttt(),
                    pttt.getIdDonHang(),
                    pttt.getLoaiThanhToan(),
                    pttt.getTrangThaiThanhToan()
            });
        }

        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy phương thức thanh toán phù hợp",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void addPhuongThuc() {
        JDialog addDialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Thêm Phương Thức Thanh Toán", Dialog.ModalityType.APPLICATION_MODAL);
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

        JLabel lblIdPttt = new JLabel("Mã Thanh Toán:");
        lblIdPttt.setFont(labelFont);
        JTextField txtIdPttt = new JTextField(20);
        txtIdPttt.setFont(fieldFont);

        JLabel lblIdDonHang = new JLabel("ID Đơn Hàng:");
        lblIdDonHang.setFont(labelFont);
        JTextField txtIdDonHang = new JTextField(20);
        txtIdDonHang.setFont(fieldFont);

        JLabel lblLoaiThanhToan = new JLabel("Loại Thanh Toán:");
        lblLoaiThanhToan.setFont(labelFont);
        JTextField txtLoaiThanhToan = new JTextField(20);
        txtLoaiThanhToan.setFont(fieldFont);

        JLabel lblTrangThaiThanhToan = new JLabel("Trạng Thái Thanh Toán:");
        lblTrangThaiThanhToan.setFont(labelFont);
        JComboBox<String> cmbTrangThaiThanhToan = new JComboBox<>(new String[]{"Đã thanh toán", "Chưa thanh toán"});
        cmbTrangThaiThanhToan.setFont(fieldFont);

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(lblIdPttt, gbc);
        gbc.gridx = 1; formPanel.add(txtIdPttt, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(lblIdDonHang, gbc);
        gbc.gridx = 1; formPanel.add(txtIdDonHang, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(lblLoaiThanhToan, gbc);
        gbc.gridx = 1; formPanel.add(txtLoaiThanhToan, gbc);
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(lblTrangThaiThanhToan, gbc);
        gbc.gridx = 1; formPanel.add(cmbTrangThaiThanhToan, gbc);

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
            try {
                String idPttt = txtIdPttt.getText().trim();
                String idDonHang = txtIdDonHang.getText().trim();
                String loaiThanhToan = txtLoaiThanhToan.getText().trim();
                String trangThaiThanhToan = cmbTrangThaiThanhToan.getSelectedItem().toString();

                // Validation
                if (idPttt.isEmpty() || idDonHang.isEmpty() || loaiThanhToan.isEmpty()) {
                    JOptionPane.showMessageDialog(addDialog, "Vui lòng điền đầy đủ thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                PhuongThucThanhToanDTO pttt = new PhuongThucThanhToanDTO();
                pttt.setIdPttt(idPttt);
                pttt.setIdDonHang(idDonHang);
                pttt.setLoaiThanhToan(loaiThanhToan);
                pttt.setTrangThaiThanhToan(trangThaiThanhToan);

                if (phuongThucThanhToanDAO.insert(pttt)) {
                    phuongThucTableModel.addRow(new Object[]{idPttt, idDonHang, loaiThanhToan, trangThaiThanhToan});
                    JOptionPane.showMessageDialog(addDialog, "Thêm phương thức thanh toán thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    addDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(addDialog, "Thêm thất bại! Kiểm tra mã thanh toán hoặc kết nối cơ sở dữ liệu", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(addDialog, "Lỗi khi thêm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener(e -> addDialog.dispose());

        addDialog.setVisible(true);
    }

    private void editPhuongThuc(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phương thức để sửa", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idPttt = phuongThucTableModel.getValueAt(selectedRow, 0).toString();
        String idDonHang = phuongThucTableModel.getValueAt(selectedRow, 1).toString();
        String loaiThanhToan = phuongThucTableModel.getValueAt(selectedRow, 2).toString();
        String trangThaiThanhToan = phuongThucTableModel.getValueAt(selectedRow, 3).toString();

        JDialog editDialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Sửa Phương Thức Thanh Toán", Dialog.ModalityType.APPLICATION_MODAL);
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

        JLabel lblIdPttt = new JLabel("Mã Thanh Toán:");
        lblIdPttt.setFont(labelFont);
        JTextField txtIdPttt = new JTextField(idPttt, 20);
        txtIdPttt.setFont(fieldFont);
        txtIdPttt.setEditable(false);
        txtIdPttt.setBackground(new Color(220, 220, 220));

        JLabel lblIdDonHang = new JLabel("ID Đơn Hàng:");
        lblIdDonHang.setFont(labelFont);
        JTextField txtIdDonHang = new JTextField(idDonHang, 20);
        txtIdDonHang.setFont(fieldFont);

        JLabel lblLoaiThanhToan = new JLabel("Loại Thanh Toán:");
        lblLoaiThanhToan.setFont(labelFont);
        JTextField txtLoaiThanhToan = new JTextField(loaiThanhToan, 20);
        txtLoaiThanhToan.setFont(fieldFont);

        JLabel lblTrangThaiThanhToan = new JLabel("Trạng Thái Thanh Toán:");
        lblTrangThaiThanhToan.setFont(labelFont);
        JComboBox<String> cmbTrangThaiThanhToan = new JComboBox<>(new String[]{"Đã thanh toán", "Chưa thanh toán"});
        cmbTrangThaiThanhToan.setFont(fieldFont);
        cmbTrangThaiThanhToan.setSelectedItem(trangThaiThanhToan);

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(lblIdPttt, gbc);
        gbc.gridx = 1; formPanel.add(txtIdPttt, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(lblIdDonHang, gbc);
        gbc.gridx = 1; formPanel.add(txtIdDonHang, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(lblLoaiThanhToan, gbc);
        gbc.gridx = 1; formPanel.add(txtLoaiThanhToan, gbc);
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(lblTrangThaiThanhToan, gbc);
        gbc.gridx = 1; formPanel.add(cmbTrangThaiThanhToan, gbc);

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
            try {
                String newIdDonHang = txtIdDonHang.getText().trim();
                String newLoaiThanhToan = txtLoaiThanhToan.getText().trim();
                String newTrangThaiThanhToan = cmbTrangThaiThanhToan.getSelectedItem().toString();

                // Validation
                if (newIdDonHang.isEmpty() || newLoaiThanhToan.isEmpty()) {
                    JOptionPane.showMessageDialog(editDialog, "Vui lòng điền đầy đủ thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                PhuongThucThanhToanDTO pttt = new PhuongThucThanhToanDTO();
                pttt.setIdPttt(idPttt);
                pttt.setIdDonHang(newIdDonHang);
                pttt.setLoaiThanhToan(newLoaiThanhToan);
                pttt.setTrangThaiThanhToan(newTrangThaiThanhToan);

                if (phuongThucThanhToanDAO.update(pttt)) {
                    phuongThucTableModel.setValueAt(newIdDonHang, selectedRow, 1);
                    phuongThucTableModel.setValueAt(newLoaiThanhToan, selectedRow, 2);
                    phuongThucTableModel.setValueAt(newTrangThaiThanhToan, selectedRow, 3);
                    JOptionPane.showMessageDialog(editDialog, "Cập nhật phương thức thanh toán thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    editDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(editDialog, "Cập nhật thất bại! Kiểm tra dữ liệu hoặc kết nối cơ sở dữ liệu", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(editDialog, "Lỗi khi cập nhật: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener(e -> editDialog.dispose());

        editDialog.setVisible(true);
    }

    private void deletePhuongThuc(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phương thức để xóa", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idPttt = phuongThucTableModel.getValueAt(selectedRow, 0).toString();
        String loaiThanhToan = phuongThucTableModel.getValueAt(selectedRow, 2).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa phương thức: " + loaiThanhToan + " (" + idPttt + ")?",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (phuongThucThanhToanDAO.delete(idPttt)) {
                    phuongThucTableModel.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(this, "Xóa phương thức thanh toán thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại! Có thể phương thức đang được sử dụng hoặc lỗi kết nối", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}