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
    private JPanel khachHangPanel;
    private Color headerColor = new Color(200, 255, 200);
    private JTextField txtId, txtHoTen, txtEmail, txtSdt, txtTim;
    private KhachHangDAO dao = new KhachHangDAO();

    public KhachHangPanel() {
        setLayout(new BorderLayout());
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
            model.addRow(new Object[]{
                kh.getIdKhachHang(),
                kh.getHoTen(),
                kh.getEmail(),
                kh.getSdt()
            });
        }
    }

    private void themKhachHang() {
        KhachHangDTO kh = getKhachHangFromForm();
        if (dao.insert(kh)) {
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
            loadTable();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại!");
        }
    }

    private void suaKhachHang() {
        KhachHangDTO kh = getKhachHangFromForm();
        if (dao.update(kh)) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            loadTable();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
        }
    }

    private void xoaKhachHang() {
        String id = txtId.getText();
        if (dao.delete(id)) {
            JOptionPane.showMessageDialog(this, "Xóa thành công!");
            loadTable();
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thất bại!");
        }
    }

    private void timKhachHang() {
        String id = txtTim.getText();
        KhachHangDTO kh = dao.selectById(id);
        if (kh != null) {
            model.setRowCount(0);
            model.addRow(new Object[]{
                kh.getIdKhachHang(),
                kh.getHoTen(),
                kh.getEmail(),
                kh.getSdt()
            });
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng!");
        }
    }

    private KhachHangDTO getKhachHangFromForm() {
        return new KhachHangDTO(
            txtId.getText().trim(),
            txtHoTen.getText().trim(),
            txtEmail.getText().trim(),
            txtSdt.getText().trim()
        );
    }
}