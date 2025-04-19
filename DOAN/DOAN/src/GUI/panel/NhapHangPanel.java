package GUI.panel;

import DAO.NhapHangDAO;
import DTO.NhapHangDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.List;

public class NhapHangPanel extends JPanel {
    private JTable nhapHangTable;
    private DefaultTableModel nhapHangTableModel;
    private JPanel nhapHangPanel;

    public NhapHangPanel() {
        setLayout(new BorderLayout());
        createNhapHangPanel();
        add(nhapHangPanel, BorderLayout.CENTER);
        loadDataFromDatabase();
    }

    private void createNhapHangPanel() {
        nhapHangPanel = new JPanel(new BorderLayout());

        // ===== Top Panel =====
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(200, 255, 200));
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel lblChucNang = new JLabel("Chức năng");
        topPanel.add(lblChucNang, BorderLayout.WEST);

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

        nhapHangPanel.add(topPanel, BorderLayout.NORTH);

        // ===== Table Panel =====
        String[] columnNames = {"ID Nhập Hàng", "ID Nhà Cung Cấp", "ID Nhân Viên", "Ngày Nhập", "Tổng Giá Trị Nhập"};
        nhapHangTableModel = new DefaultTableModel(null, columnNames);
        nhapHangTable = new JTable(nhapHangTableModel);

        // Set table properties
        nhapHangTable.setFillsViewportHeight(true);
        TableColumnModel columnModel = nhapHangTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(150);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(nhapHangTable);
        nhapHangPanel.add(scrollPane, BorderLayout.CENTER);
    }

    // Method to load data from database using NhapHangDAO
    private void loadDataFromDatabase() {
        NhapHangDAO nhapHangDAO = new NhapHangDAO();
        List<NhapHangDTO> nhapHangList = nhapHangDAO.selectAll();  // Lấy danh sách nhập hàng từ cơ sở dữ liệu

        // Clear the table before adding new data
        nhapHangTableModel.setRowCount(0);

        // Add data to table
        for (NhapHangDTO nhapHang : nhapHangList) {
            Object[] rowData = {
                nhapHang.getIdNhapHang(),
                nhapHang.getIdNhaCungCap(),
                nhapHang.getIdNhanVien(),
                nhapHang.getNgayNhap(),
                nhapHang.getTongGiaTriNhap()
            };
            nhapHangTableModel.addRow(rowData);
        }
    }
}
