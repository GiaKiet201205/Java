package GUI;

import javax.swing.*;
import java.awt.*;

public class NguoiDungGUI extends JFrame {

    public NguoiDungGUI() {
        setTitle("Fashion Store - Người Dùng");
        setSize(900, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Cửa sổ hiển thị ở giữa màn hình
        setLayout(new BorderLayout());

        Color headerColor = new Color(160, 250, 160); // Header background
        Color buttonColor = new Color(100, 200, 100); // Button background
        Font buttonFont = new Font("Serif", Font.PLAIN, 18);

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(headerColor);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel logoLabel = new JLabel("SSS", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Serif", Font.BOLD, 30));
        logoLabel.setForeground(Color.BLACK);

        headerPanel.add(logoLabel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // Menu Panel
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        menuPanel.setBackground(Color.WHITE);

        // Menu Buttons
        String[] menuItems = {"Hot trend", "Blog", "CSKH"};
        for (String item : menuItems) {
            JButton menuButton = new JButton(item);
            menuButton.setFont(buttonFont);
            menuButton.setBackground(buttonColor);
            menuButton.setForeground(Color.WHITE);
            menuButton.setFocusPainted(false);
            menuButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

            menuPanel.add(menuButton);

            // Gắn action mẫu
            menuButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Bạn đã chọn: " + item));
        }

        // ComboBox sản phẩm
        String[] categories = {"Chọn danh mục", "Quần Áo", "Giày Dép", "Phụ Kiện"};
        JComboBox<String> categoryComboBox = new JComboBox<>(categories);
        categoryComboBox.setFont(new Font("Serif", Font.PLAIN, 16));
        categoryComboBox.setBackground(buttonColor);
        categoryComboBox.setForeground(Color.WHITE);
        categoryComboBox.setFocusable(false);

        menuPanel.add(categoryComboBox);

        categoryComboBox.addActionListener(e -> {
            String selected = (String) categoryComboBox.getSelectedItem();
            if ("Quần Áo".equals(selected)) showQuanAoGUI();
            else if ("Giày Dép".equals(selected)) showGiayDepGUI();
            else if ("Phụ Kiện".equals(selected)) showPhuKienGUI();
        });

        // Search Field and Buttons
        JTextField searchField = new JTextField(20);
        searchField.setFont(new Font("Serif", Font.PLAIN, 16));
        searchField.setBackground(Color.WHITE);
        searchField.setForeground(Color.BLACK);
        searchField.setCaretColor(Color.BLACK);

        JButton searchButton = new JButton("🔍");
        searchButton.setBackground(buttonColor);
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);

        JButton cartButton = new JButton("🛒");
        cartButton.setBackground(buttonColor);
        cartButton.setForeground(Color.WHITE);
        cartButton.setFocusPainted(false);

        menuPanel.add(searchField);
        menuPanel.add(searchButton);
        menuPanel.add(cartButton);

        add(menuPanel, BorderLayout.CENTER);
    }

    private void showQuanAoGUI() {
        JFrame frame = new JFrame("Danh Mục Quần Áo");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(new QuanAoGUI());
        frame.setVisible(true);
    }

    private void showGiayDepGUI() {
        JFrame frame = new JFrame("Danh Mục Giày Dép");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(new GiayDepGUI());
        frame.setVisible(true);
    }

    private void showPhuKienGUI() {
        JFrame frame = new JFrame("Danh Mục Phụ Kiện");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(new PhuKienGUI());
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NguoiDungGUI().setVisible(true));
    }
}
