package GUI;

import BLL.DanhMucBLL;
import DTO.DanhMucDTO;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class NguoiDungGUI extends JFrame {

    private JComboBox<String> categoryComboBox;
    private DanhMucBLL danhMucBLL;

    public NguoiDungGUI() {
        setTitle("Fashion Store - Người Dùng");
        setSize(900, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Khởi tạo DanhMucBLL và đăng ký GUI
        danhMucBLL = new DanhMucBLL();
        danhMucBLL.registerNguoiDungGUI(this);

        Color headerColor = new Color(160, 250, 160);
        Color buttonColor = new Color(100, 200, 100);
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

            menuButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Bạn đã chọn: " + item));
        }

        // ComboBox sản phẩm
        categoryComboBox = new JComboBox<>();
        categoryComboBox.setFont(new Font("Serif", Font.PLAIN, 16));
        categoryComboBox.setBackground(buttonColor);
        categoryComboBox.setForeground(Color.WHITE);
        categoryComboBox.setFocusable(false);
        updateCategoryComboBox(); // Tải danh mục từ cơ sở dữ liệu
        menuPanel.add(categoryComboBox);

        categoryComboBox.addActionListener(e -> {
            String selected = (String) categoryComboBox.getSelectedItem();
            if (selected != null && !selected.equals("Chọn danh mục")) {
                showCategoryProducts(selected);
            }
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

    // Cập nhật JComboBox với danh mục từ cơ sở dữ liệu
    public void updateCategoryComboBox() {
        categoryComboBox.removeAllItems();
        categoryComboBox.addItem("Chọn danh mục");
        ArrayList<DanhMucDTO> danhMucList = danhMucBLL.getAllDanhMuc();
        for (DanhMucDTO dm : danhMucList) {
            categoryComboBox.addItem(dm.getTenDanhMuc());
        }
    }

    private void showCategoryProducts(String categoryName) {
        JFrame frame = new JFrame("Danh Mục " + categoryName);
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Tùy thuộc vào danh mục, hiển thị giao diện tương ứng
        if (categoryName.equals("Quần jean")) {
            frame.add(new QuanJeanGUI());
        } else if (categoryName.equals("Quần short")) {
            frame.add(new QuanShortGUI());
        } else if (categoryName.equals("Áo thun")) {
            frame.add(new AoThunGUI());
        } else if (categoryName.equals("Áo khoác")) {
            frame.add(new AoKhoacGUI());
        } else if (categoryName.equals("Áo hoodie")) {
            frame.add(new AoHoodieGUI());
        }
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NguoiDungGUI().setVisible(true));
    }
}