package GUI;

import DAO.DonHangDAO;
import DAO.KhachHangDAO;  // Import KhachHangDAO
import DAO.NhanVienDAO;
import DTO.DonHangDTO;
import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class GioHangGUI extends JFrame {

    public GioHangGUI(List<String> productList, int totalPrice) {
        setTitle("Giỏ Hàng");
        setSize(700, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        Font smallFont = new Font("Arial", Font.PLAIN, 13);

        // ===== HEADER =====
        JPanel headerPanel = new JPanel();
        JLabel headerLabel = new JLabel("GIỎ HÀNG", JLabel.CENTER);
        headerPanel.setBackground(new Color(100, 200, 100));
        headerLabel.setFont(new Font("Arial", Font.BOLD, 25));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // ===== CENTER =====
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // === LEFT ===
        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        JLabel leftTitle = new JLabel("Thông tin giao hàng", JLabel.CENTER);
        leftTitle.setFont(new Font("Arial", Font.BOLD, 16));
        leftPanel.add(leftTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] labels = {"Tên người nhận:", "Email:", "Số điện thoại:", "Địa chỉ:"};
        JTextField[] textFields = new JTextField[labels.length];

        for (int i = 0; i < labels.length; i++) {
            JPanel row = new JPanel(new BorderLayout(5, 5));
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

            JLabel lbl = new JLabel(labels[i]);
            lbl.setFont(smallFont);
            lbl.setPreferredSize(new Dimension(120, 25));

            JTextField tf = new JTextField();
            tf.setFont(smallFont);
            tf.setPreferredSize(new Dimension(300, 25));
            tf.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            textFields[i] = tf;
            row.add(lbl, BorderLayout.WEST);
            row.add(tf, BorderLayout.CENTER);

            formPanel.add(row);
            formPanel.add(Box.createVerticalStrut(10));
        }

        // Thanh toán
        JPanel paymentRow = new JPanel(new BorderLayout(5, 5));
        JLabel lblPayment = new JLabel("Hình thức thanh toán:");
        lblPayment.setFont(smallFont);
        lblPayment.setPreferredSize(new Dimension(120, 25));

        JComboBox<String> paymentBox = new JComboBox<>(new String[]{"Tiền mặt", "Thẻ tín dụng", "Chuyển khoản", "Ví điện tử"});
        paymentBox.setFont(smallFont);
        paymentBox.setBackground(Color.WHITE);

        paymentRow.add(lblPayment, BorderLayout.WEST);
        paymentRow.add(paymentBox, BorderLayout.CENTER);
        formPanel.add(paymentRow);

        leftPanel.add(formPanel, BorderLayout.CENTER);

        // === RIGHT ===
        JPanel rightPanel = new JPanel(new BorderLayout());
        JLabel rightTitle = new JLabel("Sản phẩm trong giỏ hàng", JLabel.CENTER);
        rightTitle.setFont(new Font("Arial", Font.BOLD, 16));
        rightPanel.add(rightTitle, BorderLayout.NORTH);

        JPanel productPanel = new JPanel();
        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));
        for (String item : productList) {
            productPanel.add(new JLabel("- " + item));
        }

        JPanel totalPanel = new JPanel();
        JLabel totalLabel = new JLabel("Tổng tiền: " + totalPrice + "đ");
        totalPanel.add(totalLabel);

        rightPanel.add(productPanel, BorderLayout.CENTER);
        rightPanel.add(totalPanel, BorderLayout.SOUTH);

        centerPanel.add(leftPanel);
        centerPanel.add(rightPanel);
        add(centerPanel, BorderLayout.CENTER);

        // ===== BOTTOM BUTTON =====
        JButton btnConfirm = new JButton("Xác nhận thanh toán");
        btnConfirm.setBackground(new Color(100, 200, 100));
        btnConfirm.setForeground(Color.WHITE);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnConfirm);
        add(buttonPanel, BorderLayout.SOUTH);

        // ===== SỰ KIỆN XÁC NHẬN =====
        btnConfirm.addActionListener(e -> {
            String hoTen = textFields[0].getText().trim();
            String email = textFields[1].getText().trim();
            String sdt = textFields[2].getText().trim();
            String diaChi = textFields[3].getText().trim();
            String hinhThucThanhToan = (String) paymentBox.getSelectedItem();

            if (hoTen.isEmpty() || email.isEmpty() || sdt.isEmpty() || diaChi.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                
                KhachHangDAO khachHangDAO = new KhachHangDAO();
                String idKhachHang = khachHangDAO.generateNextId(); // Tạo mã khách hàng mới

                NhanVienDAO nhanVienDAO = new NhanVienDAO();
                String idNhanVien = nhanVienDAO.generateNextId(); // Tạo mã nhân viên mới

                // Tạo đơn hàng
                DonHangDTO donHang = new DonHangDTO();
                donHang.setIdKhachHang(idKhachHang);  // Đặt mã khách hàng
                donHang.setIdNhanVien(idNhanVien);  // Đặt mã nhân viên từ NhanVienDAO
                donHang.setTongTien(totalPrice);
                donHang.setNgayDatHang(new Timestamp(System.currentTimeMillis()));
                donHang.setTrangThai("Đã thanh toán");
                donHang.setHinhThucMuaHang(hinhThucThanhToan);
                donHang.setDiaDiemGiao(diaChi);

                DonHangDAO donHangDAO = new DonHangDAO();
                String idDonHang = donHangDAO.generateNextId(); // Tạo mã đơn hàng mới
                donHang.setIdDonHang(idDonHang);
                String idInserted = donHangDAO.insert(donHang);

                if (idDonHang == null) {
                    JOptionPane.showMessageDialog(this, "Không thể lưu hóa đơn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Hiển thị hóa đơn đơn giản
                JOptionPane.showMessageDialog(this, "Thanh toán thành công! Hóa đơn đã được lưu.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi thanh toán: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        List<String> products = List.of("Áo Thun - 200000đ", "Quần Jean - 400000đ");
        new GioHangGUI(products, 600000);
    }
}
