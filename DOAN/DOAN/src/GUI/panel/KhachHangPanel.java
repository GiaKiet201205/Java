package GUI.panel;

import DAO.KhachHangDAO;
import DTO.KhachHangDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class KhachHangPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private KhachHangDAO dao = new KhachHangDAO();

    public KhachHangPanel() {
        setLayout(new BorderLayout());
        createUI();
        loadTable();
    }

    private void createUI() {
        // Panel trên cùng
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(200, 255, 200));
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Nhãn chức năng
        JLabel lblChucNang = new JLabel("Chức năng");
        topPanel.add(lblChucNang, BorderLayout.WEST);

        // Panel chứa các nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setOpaque(false);
        JButton btnAdd = new JButton("Thêm");
        JButton btnEdit = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);

        // Panel chứa nhãn mô tả nút
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labelPanel.setOpaque(false);
        labelPanel.add(new JLabel("Thêm"));
        labelPanel.add(Box.createHorizontalStrut(20));
        labelPanel.add(new JLabel("Sửa"));
        labelPanel.add(Box.createHorizontalStrut(20));
        labelPanel.add(new JLabel("Xóa"));
        
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
        JButton btnReset = new JButton("Làm mới");

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

        // Sự kiện chọn hàng trong bảng
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    // Có thể thêm logic nếu cần hiển thị dữ liệu lên các trường nhập liệu
                }
            }
        });
    }

    private void loadTable() {
        model.setRowCount(0);
        ArrayList<KhachHangDTO> list = dao.selectAll();
        for (KhachHangDTO kh : list) {
            model.addRow(new Object[]{kh.getIdKhachHang(), kh.getHoTen(), kh.getEmail(), kh.getSdt()});
        }
    }

    private void showAddDialog() {
        JTextField txtID = new JTextField();
        JTextField txtTen = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtSDT = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("ID Khách hàng:"));
        panel.add(txtID);
        panel.add(new JLabel("Tên khách hàng:"));
        panel.add(txtTen);
        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);
        panel.add(new JLabel("SĐT:"));
        panel.add(txtSDT);

        int result = JOptionPane.showConfirmDialog(this, panel, "Thêm khách hàng mới", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            // Kiểm tra dữ liệu đầu vào
            if (txtID.getText().trim().isEmpty() || txtTen.getText().trim().isEmpty() ||
                txtEmail.getText().trim().isEmpty() || txtSDT.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!");
                return;
            }

            KhachHangDTO kh = new KhachHangDTO(txtID.getText().trim(), txtTen.getText().trim(),
                    txtEmail.getText().trim(), txtSDT.getText().trim());
            if (dao.insert(kh)) {
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                loadTable();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại!");
            }
        }
    }

    private void showEditDialog(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để sửa!");
            return;
        }

        JTextField txtID = new JTextField(model.getValueAt(selectedRow, 0).toString());
        JTextField txtTen = new JTextField(model.getValueAt(selectedRow, 1).toString());
        JTextField txtEmail = new JTextField(model.getValueAt(selectedRow, 2).toString());
        JTextField txtSDT = new JTextField(model.getValueAt(selectedRow, 3).toString());

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("ID Khách hàng:"));
        panel.add(txtID);
        panel.add(new JLabel("Tên khách hàng:"));
        panel.add(txtTen);
        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);
        panel.add(new JLabel("SĐT:"));
        panel.add(txtSDT);

        int result = JOptionPane.showConfirmDialog(this, panel, "Sửa khách hàng", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            // Kiểm tra dữ liệu đầu vào
            if (txtID.getText().trim().isEmpty() || txtTen.getText().trim().isEmpty() ||
                txtEmail.getText().trim().isEmpty() || txtSDT.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!");
                return;
            }

            KhachHangDTO kh = new KhachHangDTO(txtID.getText().trim(), txtTen.getText().trim(),
                    txtEmail.getText().trim(), txtSDT.getText().trim());
            if (dao.update(kh)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                loadTable();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            }
        }
    }

    private void deleteKhachHang(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa khách hàng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String id = model.getValueAt(selectedRow, 0).toString();
            if (dao.delete(id)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                loadTable();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        }
    }

    private void searchKhachHang(String type, String keyword) {
        if (keyword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm");
            return;
        }

        ArrayList<KhachHangDTO> result = new ArrayList<>();
        if (type.equals("ID khách hàng")) {
            KhachHangDTO kh = dao.selectById(keyword.trim());
            if (kh != null) result.add(kh);
        } else if (type.equals("Tên khách hàng")) {
            result = dao.selectByKeyword(keyword.trim());
        } else if (type.equals("Email")) {
            result = dao.selectByKeyword(keyword.trim());  // Dùng selectByKeyword cho cả email
        } else if (type.equals("SĐT")) {
            result = dao.selectByKeyword(keyword.trim());  // Dùng selectByKeyword cho cả sdt
        }
    }
}