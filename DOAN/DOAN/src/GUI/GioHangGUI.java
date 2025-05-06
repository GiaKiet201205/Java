package GUI;

import BLL.Session;
import BLL.TaiKhoanBLL;
import DAO.DonHangDAO;
import DAO.NhanVienDAO;
import DTO.DonHangDTO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Timestamp;
import java.util.List;

public class GioHangGUI extends JFrame {

    private List<String> productList;
    private int totalPrice;
    private JTextField[] textFields;
    private JComboBox<String> paymentBox;

    public GioHangGUI(List<String> productList, int totalPrice) {
        this.productList = productList;
        this.totalPrice = totalPrice;
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
        textFields = new JTextField[labels.length];

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

        paymentBox = new JComboBox<>(new String[]{"Tiền mặt", "Chuyển khoản"});
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
        TaiKhoanBLL taiKhoanBLL = new TaiKhoanBLL();
        btnConfirm.addActionListener(e -> {
            if (!taiKhoanBLL.isUserLoggedIn()) {
                JOptionPane.showMessageDialog(this, "Bạn chưa đăng nhập!", 
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
                taiKhoanBLL.showLoginForm(this);
                return;
            }

            String hoTen = textFields[0].getText().trim();
            String email = textFields[1].getText().trim();
            String sdt = textFields[2].getText().trim();
            String diaChi = textFields[3].getText().trim();
            String hinhThucThanhToan = (String) paymentBox.getSelectedItem();

            // Kiểm tra thông tin đầu vào
            if (hoTen.isEmpty() || email.isEmpty() || sdt.isEmpty() || diaChi.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                String idKhachHang = Session.getIdKhachHang();

                // Lấy ID nhân viên
                NhanVienDAO nhanVienDAO = new NhanVienDAO();
                String idNhanVien = "NV001";

                // Tạo đơn hàng
                DonHangDTO donHang = new DonHangDTO();
                donHang.setIdKhachHang(idKhachHang);
                donHang.setIdNhanVien(idNhanVien);
                donHang.setTongTien(totalPrice);
                donHang.setNgayDatHang(new Timestamp(System.currentTimeMillis()));
                donHang.setTrangThai("Đang xử lý");
                donHang.setHinhThucMuaHang("Online");
                donHang.setDiaDiemGiao(diaChi);

                DonHangDAO donHangDAO = new DonHangDAO();
                String idDonHang = donHangDAO.generateNextId();
                donHang.setIdDonHang(idDonHang);
                donHangDAO.insert(donHang);

                JOptionPane.showMessageDialog(this, "Thanh toán thành công! Hóa đơn đã được lưu.", 
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Đóng cửa sổ giỏ hàng sau khi hoàn thành
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi thanh toán: " + ex.getMessage(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
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