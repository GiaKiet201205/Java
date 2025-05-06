package GUI.panel;

import BLL.DanhMucBLL;
import DTO.DanhMucDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class DanhMucPanel extends JPanel {
    private JTable danhMucTable;
    private DefaultTableModel danhMucTableModel;
    private DanhMucBLL danhMucBLL;

    public DanhMucPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 400));
        danhMucBLL = new DanhMucBLL();
        taoGiaoDienDanhMuc();
    }

    private void taoGiaoDienDanhMuc() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(200, 255, 200));
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Tiêu đề
        JLabel lblTitle = new JLabel("DANH MỤC", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(lblTitle, BorderLayout.NORTH);

        // Nhãn chức năng
        JLabel lblChucNang = new JLabel("Chức năng");
        topPanel.add(lblChucNang, BorderLayout.WEST);

        // Panel chứa các nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setOpaque(false);
        JButton btnAdd = new JButton("Thêm");
        btnAdd.setToolTipText("Thêm danh mục");
        btnAdd.setBackground(new Color(240, 240, 240));
        JButton btnDelete = new JButton("Xóa");
        btnDelete.setToolTipText("Xóa danh mục");
        btnDelete.setBackground(new Color(240, 240, 240));
        JButton btnEdit = new JButton("Sửa");
        btnEdit.setToolTipText("Sửa danh mục");
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
        JComboBox<String> cmbSearchType = new JComboBox<>(new String[]{"ID danh mục", "Tên danh mục"});
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
        danhMucTableModel = new DefaultTableModel(new String[]{"ID danh mục", "Tên danh mục"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        danhMucTable = new JTable(danhMucTableModel);
        danhMucTable.setRowHeight(25);
        danhMucTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < danhMucTable.getColumnCount(); i++) {
            danhMucTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        add(new JScrollPane(danhMucTable), BorderLayout.CENTER);

        taiDanhSachDanhMuc();

        // Gán sự kiện
        btnAdd.addActionListener(e -> themDanhMuc());
        btnEdit.addActionListener(e -> suaDanhMuc(danhMucTable.getSelectedRow()));
        btnDelete.addActionListener(e -> deleteDanhMuc(danhMucTable.getSelectedRow()));
        btnSearch.addActionListener(e -> searchDanhMuc(cmbSearchType.getSelectedItem().toString(), txtSearch.getText()));
        btnReset.addActionListener(e -> {
            txtSearch.setText("");
            taiDanhSachDanhMuc();
        });

        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchDanhMuc(cmbSearchType.getSelectedItem().toString(), txtSearch.getText());
                }
            }
        });
    }

    private void taiDanhSachDanhMuc() {
        danhMucTableModel.setRowCount(0);
        ArrayList<DanhMucDTO> list = danhMucBLL.getAllDanhMuc();
        for (DanhMucDTO dm : list) {
            danhMucTableModel.addRow(new Object[]{dm.getIdDanhMuc(), dm.getTenDanhMuc()});
        }
    }

    private void themDanhMuc() {
        JDialog addDialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Thêm Danh Mục", Dialog.ModalityType.APPLICATION_MODAL);
        addDialog.setSize(450, 300);
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

        JLabel lblIdDanhMuc = new JLabel("ID Danh Mục:");
        lblIdDanhMuc.setFont(labelFont);
        JTextField txtIdDanhMuc = new JTextField(20);
        txtIdDanhMuc.setFont(fieldFont);

        JLabel lblTenDanhMuc = new JLabel("Tên Danh Mục:");
        lblTenDanhMuc.setFont(labelFont);
        JTextField txtTenDanhMuc = new JTextField(20);
        txtTenDanhMuc.setFont(fieldFont);

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(lblIdDanhMuc, gbc);
        gbc.gridx = 1; formPanel.add(txtIdDanhMuc, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(lblTenDanhMuc, gbc);
        gbc.gridx = 1; formPanel.add(txtTenDanhMuc, gbc);

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
            DanhMucDTO dm = new DanhMucDTO(txtIdDanhMuc.getText(), txtTenDanhMuc.getText());
            if (danhMucBLL.addDanhMuc(dm)) {
                JOptionPane.showMessageDialog(addDialog, "Thêm thành công!");
                taiDanhSachDanhMuc();
                addDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(addDialog, "Thêm thất bại! Kiểm tra ID danh mục hoặc kết nối cơ sở dữ liệu.");
            }
        });

        btnCancel.addActionListener(e -> addDialog.dispose());

        addDialog.setVisible(true);
    }

    private void suaDanhMuc(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một danh mục để sửa");
            return;
        }

        JDialog editDialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Sửa Danh Mục", Dialog.ModalityType.APPLICATION_MODAL);
        editDialog.setSize(450, 300);
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

        JLabel lblIdDanhMuc = new JLabel("ID Danh Mục:");
        lblIdDanhMuc.setFont(labelFont);
        JTextField txtIdDanhMuc = new JTextField(danhMucTableModel.getValueAt(selectedRow, 0).toString(), 20);
        txtIdDanhMuc.setFont(fieldFont);
        txtIdDanhMuc.setEditable(false);
        txtIdDanhMuc.setBackground(new Color(220, 220, 220));

        JLabel lblTenDanhMuc = new JLabel("Tên Danh Mục:");
        lblTenDanhMuc.setFont(labelFont);
        JTextField txtTenDanhMuc = new JTextField(danhMucTableModel.getValueAt(selectedRow, 1).toString(), 20);
        txtTenDanhMuc.setFont(fieldFont);

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(lblIdDanhMuc, gbc);
        gbc.gridx = 1; formPanel.add(txtIdDanhMuc, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(lblTenDanhMuc, gbc);
        gbc.gridx = 1; formPanel.add(txtTenDanhMuc, gbc);

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
            DanhMucDTO dm = new DanhMucDTO(txtIdDanhMuc.getText(), txtTenDanhMuc.getText());
            if (danhMucBLL.updateDanhMuc(dm)) {
                JOptionPane.showMessageDialog(editDialog, "Cập nhật thành công!");
                taiDanhSachDanhMuc();
                editDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(editDialog, "Cập nhật thất bại! Kiểm tra ID danh mục hoặc kết nối cơ sở dữ liệu.");
            }
        });

        btnCancel.addActionListener(e -> editDialog.dispose());

        editDialog.setVisible(true);
    }

    private void deleteDanhMuc(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một danh mục để xóa");
            return;
        }

        String id = danhMucTableModel.getValueAt(selectedRow, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa danh mục " + id + "?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (danhMucBLL.deleteDanhMuc(id)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                taiDanhSachDanhMuc();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại! Có thể danh mục đang được sử dụng hoặc lỗi kết nối.");
            }
        }
    }

    private void searchDanhMuc(String type, String keyword) {
        if (keyword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm");
            return;
        }
        danhMucTableModel.setRowCount(0);
        ArrayList<DanhMucDTO> list = danhMucBLL.getAllDanhMuc();
        for (DanhMucDTO dm : list) {
            if ((type.equals("ID danh mục") && dm.getIdDanhMuc().toLowerCase().contains(keyword.toLowerCase())) ||
                (type.equals("Tên danh mục") && dm.getTenDanhMuc().toLowerCase().contains(keyword.toLowerCase()))) {
                danhMucTableModel.addRow(new Object[]{dm.getIdDanhMuc(), dm.getTenDanhMuc()});
            }
        }
        if (danhMucTableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy danh mục phù hợp");
        }
    }
}