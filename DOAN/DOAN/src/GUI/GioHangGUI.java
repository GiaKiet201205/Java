package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GioHangGUI extends JFrame {

    public GioHangGUI(List<String> productList, int totalPrice) {
        setTitle("Giỏ Hàng");
        setSize(700, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);  // Thay vì EXIT_ON_CLOSE, dùng DISPOSE_ON_CLOSE để chỉ đóng cửa sổ này
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

        // ===== CENTER PANEL =====
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ====== BÊN TRÁI: THÔNG TIN ======
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

        // Dòng chọn hình thức thanh toán
        JPanel paymentRow = new JPanel(new BorderLayout(5, 5));
        paymentRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JLabel lblPayment = new JLabel("Hình thức thanh toán:");
        lblPayment.setFont(smallFont);
        lblPayment.setPreferredSize(new Dimension(120, 25));

        JComboBox<String> paymentBox = new JComboBox<>(new String[]{"Tiền mặt", "Thẻ tín dụng", "Chuyển khoản", "Ví điện tử"});
        paymentBox.setFont(smallFont);
        paymentBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        paymentBox.setBackground(Color.WHITE);

        paymentRow.add(lblPayment, BorderLayout.WEST);
        paymentRow.add(paymentBox, BorderLayout.CENTER);

        formPanel.add(paymentRow);
        formPanel.add(Box.createVerticalStrut(10));

        leftPanel.add(formPanel, BorderLayout.CENTER);

        // ====== BÊN PHẢI: SẢN PHẨM ======
        JPanel rightPanel = new JPanel(new BorderLayout());

        JPanel productPanel = new JPanel();
        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));

        JLabel rightTitle = new JLabel("Sản phẩm trong giỏ hàng", JLabel.CENTER);
        rightTitle.setFont(new Font("Arial", Font.BOLD, 16));
        rightPanel.add(rightTitle, BorderLayout.NORTH);

        for (String item : productList) {
            JLabel lbl = new JLabel("- " + item);
            lbl.setFont(smallFont);
            productPanel.add(lbl);
        }

        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel totalLabel = new JLabel("Tổng tiền: " + totalPrice + "đ");
        totalLabel.setFont(smallFont);
        totalPanel.add(totalLabel);

        rightPanel.add(productPanel, BorderLayout.CENTER);
        rightPanel.add(totalPanel, BorderLayout.SOUTH);

        centerPanel.add(leftPanel);
        centerPanel.add(rightPanel);
        add(centerPanel, BorderLayout.CENTER);

        // ===== NÚT XÁC NHẬN =====
        JButton btnConfirm = new JButton("Xác nhận thanh toán");
        btnConfirm.setFont(smallFont);
        btnConfirm.setBackground(new Color(100, 200, 100));
        btnConfirm.setForeground(Color.WHITE);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnConfirm);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        List<String> products = new ArrayList<>();
        products.add("Áo Thun - 200,000đ");
        products.add("Quần Jean - 400,000đ");
        products.add("Áo Khoác - 600,000đ");

        new GioHangGUI(products, 1200000);
    }
}
