package GUI.panel;

import DAO.KhachHangDAO;
import DTO.KhachHangDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class KhachHangPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
<<<<<<< HEAD
    private JPanel khachHangPanel;
    private Color headerColor = new Color(200, 255, 200);
    private JTextField txtId, txtHoTen, txtEmail, txtSdt, txtTim;
=======
>>>>>>> 8128f3057c8b1d972c57fa5955ec92b262831009
    private KhachHangDAO dao = new KhachHangDAO();

    public KhachHangPanel() {
        setLayout(new BorderLayout());
<<<<<<< HEAD
        createKhachHangPanel();
        add(khachHangPanel, BorderLayout.CENTER);
        loadTable();
    }

    private void createKhachHangPanel() {
        khachHangPanel = new JPanel(new BorderLayout());

        // Top Panel - Header
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(headerColor);
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel lblId = new JLabel("ID Khách hàng:");
        txtId = new JTextField(10);
        JLabel lblHoTen = new JLabel("Họ tên:");
        txtHoTen = new JTextField(10);
        JLabel lblEmail = new JLabel("Email:");
        txtEmail = new JTextField(10);
        JLabel lblSdt = new JLabel("SĐT:");
        txtSdt = new JTextField(10);
        JLabel lblTim = new JLabel("Tìm ID:");
        txtTim = new JTextField(10);
        JButton btnThem = new JButton("Thêm");
        JButton btnSua = new JButton("Sửa");
        JButton btnXoa = new JButton("Xóa");
        JButton btnTim = new JButton("Tìm");

        topPanel.add(lblId);
        topPanel.add(txtId);
        topPanel.add(lblHoTen);
        topPanel.add(txtHoTen);
        topPanel.add(lblEmail);
        topPanel.add(txtEmail);
        topPanel.add(lblSdt);
        topPanel.add(txtSdt);
        topPanel.add(lblTim);
        topPanel.add(txtTim);
        topPanel.add(btnThem);
        topPanel.add(btnSua);
        topPanel.add(btnXoa);
        topPanel.add(btnTim);

        khachHangPanel.add(topPanel, BorderLayout.NORTH);

        // Center - Table
        String[] columns = {"ID", "Họ tên", "Email", "SĐT"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(25);

        // Căn giữa nội dung các cột
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        khachHangPanel.add(scrollPane, BorderLayout.CENTER);

        // Event handlers
        btnThem.addActionListener(e -> themKhachHang());
        btnSua.addActionListener(e -> suaKhachHang());
        btnXoa.addActionListener(e -> xoaKhachHang());
        btnTim.addActionListener(e -> timKhachHang());

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    txtId.setText(model.getValueAt(row, 0).toString());
                    txtHoTen.setText(model.getValueAt(row, 1).toString());
                    txtEmail.setText(model.getValueAt(row, 2).toString());
                    txtSdt.setText(model.getValueAt(row, 3).toString());
                }
            }
=======

        // ==== TOP PANEL ====
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(200, 255, 200));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblChucNang = new JLabel("Chức năng");
        topPanel.add(lblChucNang, BorderLayout.WEST);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setOpaque(false);

        JButton btnAdd = new JButton("Thêm");
        JButton btnDelete = new JButton("Xóa");
        JButton btnEdit = new JButton("Sửa");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnEdit);

        // Labels
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

        JLabel lblSearch = new JLabel("Tìm kiếm");
        JComboBox<String> cmbSearchType = new JComboBox<>(new String[]{"ID khách hàng", "Tên khách hàng","Email","SĐT"});
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

        // ==== TABLE ====
        model = new DefaultTableModel(new String[]{"ID", "Họ tên", "Email", "SĐT"}, 0);
        table = new JTable(model);
        table.setRowHeight(25);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // ==== LOAD DATA ====
        loadTable();

        // ==== ACTIONS ====
        btnAdd.addActionListener(e -> showAddDialog());
        btnEdit.addActionListener(e -> showEditDialog(table.getSelectedRow()));
        btnDelete.addActionListener(e -> deleteKhachHang(table.getSelectedRow()));
        btnSearch.addActionListener(e -> searchKhachHang(cmbSearchType.getSelectedItem().toString(), txtSearch.getText()));
        btnReset.addActionListener(e -> {
            txtSearch.setText("");
            loadTable();
>>>>>>> 8128f3057c8b1d972c57fa5955ec92b262831009
        });

        // Sự kiện nhấn Enter trên txtTim
        txtTim.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    timKhachHang();
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
        panel.add(new JLabel("ID Khách hàng:")); panel.add(txtID);
        panel.add(new JLabel("Tên khách hàng:")); panel.add(txtTen);
        panel.add(new JLabel("Email:")); panel.add(txtEmail);
        panel.add(new JLabel("SĐT:")); panel.add(txtSDT);

        int result = JOptionPane.showConfirmDialog(this, panel, "Thêm khách hàng mới", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            KhachHangDTO kh = new KhachHangDTO(txtID.getText(), txtTen.getText(), txtEmail.getText(), txtSDT.getText());
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
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để sửa");
            return;
        }

        JTextField txtID = new JTextField(model.getValueAt(selectedRow, 0).toString());
        JTextField txtTen = new JTextField(model.getValueAt(selectedRow, 1).toString());
        JTextField txtEmail = new JTextField(model.getValueAt(selectedRow, 2).toString());
        JTextField txtSDT = new JTextField(model.getValueAt(selectedRow, 3).toString());

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("ID Khách hàng:")); panel.add(txtID);
        panel.add(new JLabel("Tên khách hàng:")); panel.add(txtTen);
        panel.add(new JLabel("Email:")); panel.add(txtEmail);
        panel.add(new JLabel("SĐT:")); panel.add(txtSDT);

        int result = JOptionPane.showConfirmDialog(this, panel, "Sửa khách hàng", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            KhachHangDTO kh = new KhachHangDTO(txtID.getText(), txtTen.getText(), txtEmail.getText(), txtSDT.getText());
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
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để xóa");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa khách hàng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            // Không xóa khỏi DB, chỉ xóa khỏi bảng giao diện
            model.removeRow(selectedRow);
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

    if (result.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng!");
    } else {
        model.setRowCount(0);
        for (KhachHangDTO kh : result) {
            model.addRow(new Object[]{kh.getIdKhachHang(), kh.getHoTen(), kh.getEmail(), kh.getSdt()});
        }
    }
<<<<<<< HEAD
}
=======
}

}
>>>>>>> 8128f3057c8b1d972c57fa5955ec92b262831009
