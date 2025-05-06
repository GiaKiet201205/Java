package GUI.panel;

import DAO.NhaCungCapDAO;
import DTO.NhaCungCapDTO;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.EmptyBorder;
import java.util.ArrayList;

public class NhaCungCapPanel extends JPanel {
    private DefaultTableModel nhaCungCapTableModel;
    private JTable nhaCungCapTable;
    private JPanel nhaCungCapPanel;
    private Color headerColor = new Color(200, 255, 200);
    private JTextField txtSearchID, txtSearchName;
    private NhaCungCapDAO nhaCungCapDAO;

    public NhaCungCapPanel() {
        nhaCungCapDAO = new NhaCungCapDAO(); // Khởi tạo DAO để lấy dữ liệu
        setLayout(new BorderLayout());
        createNhaCungCapPanel();
        loadDataToTable(); // Tải dữ liệu ban đầu
        add(nhaCungCapPanel, BorderLayout.CENTER);
    }

    private void createNhaCungCapPanel() {
        nhaCungCapPanel = new JPanel(new BorderLayout());

        // Top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(headerColor);
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Function buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setOpaque(false);
        JButton btnEdit = new JButton("Sửa");
        btnEdit.setToolTipText("Sửa");
        buttonPanel.add(btnEdit);

        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labelPanel.setOpaque(false);
        labelPanel.add(new JLabel("Sửa nhà cung cấp"));

        JPanel functionPanel = new JPanel(new BorderLayout());
        functionPanel.setOpaque(false);
        functionPanel.add(buttonPanel, BorderLayout.NORTH);
        functionPanel.add(labelPanel, BorderLayout.SOUTH);
        topPanel.add(functionPanel, BorderLayout.CENTER);

        // Search Panel
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 5, 2, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblSearchID = new JLabel("Tìm theo ID:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        searchPanel.add(lblSearchID, gbc);

        txtSearchID = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 0;
        searchPanel.add(txtSearchID, gbc);

        JLabel lblSearchName = new JLabel("Tìm theo tên:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        searchPanel.add(lblSearchName, gbc);

        txtSearchName = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 1;
        searchPanel.add(txtSearchName, gbc);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setOpaque(false);
        JButton btnSearch = new JButton("Tìm kiếm");
        btnSearch.setBackground(new Color(240, 240, 240));
        actionPanel.add(btnSearch);
        JButton btnReset = new JButton("Làm mới");
        btnReset.setBackground(new Color(240, 240, 240));
        actionPanel.add(btnReset);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        searchPanel.add(actionPanel, gbc);
        topPanel.add(searchPanel, BorderLayout.EAST);

        nhaCungCapPanel.add(topPanel, BorderLayout.NORTH);

        // Table
        String[] columns = {
            "ID nhà cung cấp", "Tên nhà cung cấp", "Địa chỉ", "Số điện thoại"
        };

        nhaCungCapTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        nhaCungCapTable = new JTable(nhaCungCapTableModel);
        nhaCungCapTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        nhaCungCapTable.setRowHeight(25);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < nhaCungCapTable.getColumnCount(); i++) {
            nhaCungCapTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(nhaCungCapTable);
        nhaCungCapPanel.add(scrollPane, BorderLayout.CENTER);

        // Event
        btnSearch.addActionListener(e -> searchNhaCungCap(txtSearchID.getText(), txtSearchName.getText()));
        btnReset.addActionListener(e -> resetNhaCungCapSearch());
        btnEdit.addActionListener(e -> editNhaCungCap(nhaCungCapTable.getSelectedRow()));

        txtSearchID.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchNhaCungCap(txtSearchID.getText(), txtSearchName.getText());
                }
            }
        });
    }

    private void loadDataToTable() {
        ArrayList<NhaCungCapDTO> list = nhaCungCapDAO.selectAll();
        for (NhaCungCapDTO nhaCungCap : list) {
            nhaCungCapTableModel.addRow(new Object[]{
                nhaCungCap.getIdNhaCungCap(),
                nhaCungCap.getTenNhaCungCap(),
                nhaCungCap.getDiaChi(),
                nhaCungCap.getSdt()
            });
        }
    }

    private void searchNhaCungCap(String idSearch, String nameSearch) {
        System.out.println("Tìm kiếm nhà cung cấp với:");
        System.out.println("ID chứa: " + idSearch);
        System.out.println("Tên chứa: " + nameSearch);
        JOptionPane.showMessageDialog(this, "Đã tìm kiếm theo các tiêu chí đã chọn", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void resetNhaCungCapSearch() {
        txtSearchID.setText("");
        txtSearchName.setText("");
        nhaCungCapTableModel.setRowCount(0); // Clear all data
        loadDataToTable(); // Load lại dữ liệu
        JOptionPane.showMessageDialog(this, "Đã làm mới bộ lọc tìm kiếm", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void editNhaCungCap(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhà cung cấp để sửa", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String id = nhaCungCapTableModel.getValueAt(selectedRow, 0).toString();
        String name = nhaCungCapTableModel.getValueAt(selectedRow, 1).toString();
        String address = nhaCungCapTableModel.getValueAt(selectedRow, 2).toString();
        String phone = nhaCungCapTableModel.getValueAt(selectedRow, 3).toString();

        JDialog editDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(nhaCungCapPanel), "Sửa nhà cung cấp", true);
        editDialog.setLayout(new GridLayout(5, 2, 10, 10));
        editDialog.setSize(400, 250);
        editDialog.setLocationRelativeTo(null);

        JTextField txtId = new JTextField(id); txtId.setEditable(false);
        JTextField txtName = new JTextField(name);
        JTextField txtAddress = new JTextField(address);
        JTextField txtPhone = new JTextField(phone);

        editDialog.add(new JLabel("ID nhà cung cấp:")); editDialog.add(txtId);
        editDialog.add(new JLabel("Tên nhà cung cấp:")); editDialog.add(txtName);
        editDialog.add(new JLabel("Địa chỉ:")); editDialog.add(txtAddress);
        editDialog.add(new JLabel("Số điện thoại:")); editDialog.add(txtPhone);

        JButton btnSave = new JButton("Lưu");
        JButton btnCancel = new JButton("Hủy");

        btnSave.addActionListener(e -> {
            nhaCungCapTableModel.setValueAt(txtName.getText(), selectedRow, 1);
            nhaCungCapTableModel.setValueAt(txtAddress.getText(), selectedRow, 2);
            nhaCungCapTableModel.setValueAt(txtPhone.getText(), selectedRow, 3);
            editDialog.dispose();
            JOptionPane.showMessageDialog(this, "Thông tin nhà cung cấp đã được cập nhật", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        });

        btnCancel.addActionListener(e -> editDialog.dispose());

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);

        editDialog.add(btnPanel);
        editDialog.setVisible(true);
    }
}
