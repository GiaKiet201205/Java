package GUI.panel;

import DAO.KhachHangDAO;
import DTO.KhachHangDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class KhachHangPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtId, txtHoTen, txtEmail, txtSdt, txtTim;
    private KhachHangDAO dao = new KhachHangDAO();

    public KhachHangPanel() {
        setLayout(new BorderLayout());
        initComponent();
        loadTable();
    }

    private void initComponent() {
        // North - Form nhập liệu
        JPanel pnlForm = new JPanel(new GridLayout(2, 5, 10, 10));
        txtId = new JTextField();
        txtHoTen = new JTextField();
        txtEmail = new JTextField();
        txtSdt = new JTextField();
        txtTim = new JTextField();

        pnlForm.add(new JLabel("ID Khách hàng"));
        pnlForm.add(new JLabel("Họ tên"));
        pnlForm.add(new JLabel("Email"));
        pnlForm.add(new JLabel("SĐT"));
        pnlForm.add(new JLabel("Tìm ID"));

        pnlForm.add(txtId);
        pnlForm.add(txtHoTen);
        pnlForm.add(txtEmail);
        pnlForm.add(txtSdt);
        pnlForm.add(txtTim);

        add(pnlForm, BorderLayout.NORTH);

        // Center - Table
        model = new DefaultTableModel(new String[]{"ID", "Họ tên", "Email", "SĐT"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // South - Buttons
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnThem = new JButton("Thêm");
        JButton btnSua = new JButton("Sửa");
        JButton btnXoa = new JButton("Xóa");
        JButton btnTim = new JButton("Tìm");

        pnlButtons.add(btnThem);
        pnlButtons.add(btnSua);
        pnlButtons.add(btnXoa);
        pnlButtons.add(btnTim);

        add(pnlButtons, BorderLayout.SOUTH);

        // Event handlers
        btnThem.addActionListener(e -> themKhachHang());
        btnSua.addActionListener(e -> suaKhachHang());
        btnXoa.addActionListener(e -> xoaKhachHang());
        btnTim.addActionListener(e -> timKhachHang());

        table.addMouseListener(new MouseAdapter() {
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
