package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class NguoiDungGUI extends JFrame {

    public NguoiDungGUI() {
        setTitle("Fashion Store");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Colors
        Color headerColor = new Color(160, 250, 160); // Light green for header
        Color buttonColor = new Color(100, 200, 100); // Light green for buttons

        // Panel Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(headerColor);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Logo
        JLabel logoLabel = new JLabel("SSS", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Serif", Font.BOLD, 30));
        logoLabel.setForeground(Color.BLACK);
        headerPanel.add(logoLabel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // Menu Panel
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(Color.WHITE);
        menuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        String[] menuItems = {"Hot trend", "Blog", "CSKH"};
        for (String item : menuItems) {
            JButton menuButton = new JButton(item);
            menuButton.setContentAreaFilled(true);
            menuButton.setBorderPainted(false);
            menuButton.setFont(new Font("Serif", Font.PLAIN, 18));
            menuButton.setOpaque(true);
            menuButton.setBackground(buttonColor);
            menuButton.setForeground(Color.WHITE);
            menuPanel.add(menuButton);

            if (item.equals("Blog")) {
                menuButton.addActionListener(e -> showBlogContent());
            } else if (item.equals("CSKH")) {
                menuButton.addActionListener(e -> showCSKHContent());
            }
        }

        // ComboBox for categories
        String[] categories = {"Chọn danh mục", "Quần Áo", "Giày Dép", "Phụ Kiện"};
        JComboBox<String> categoryComboBox = new JComboBox<>(categories);
        categoryComboBox.setFont(new Font("Serif", Font.PLAIN, 16));
        categoryComboBox.setBackground(buttonColor);
        categoryComboBox.setForeground(Color.WHITE);
        menuPanel.add(categoryComboBox);

        categoryComboBox.addActionListener(e -> {
            String selected = (String) categoryComboBox.getSelectedItem();
            if ("Quần Áo".equals(selected)) showQuanAoGUI();
            else if ("Giày Dép".equals(selected)) showGiayDepGUI();
            else if ("Phụ Kiện".equals(selected)) showPhuKienGUI();
        });

        // Search & Cart Components
        JTextField searchField = new JTextField(20);
        searchField.setBackground(Color.WHITE);
        searchField.setForeground(Color.BLACK); // Adjusted for visibility
        searchField.setCaretColor(Color.BLACK);

        JButton searchButton = new JButton("🔍");
        searchButton.setBackground(buttonColor);
        searchButton.setForeground(Color.WHITE);

        JButton cartButton = new JButton("🛒");
        cartButton.setBackground(buttonColor);
        cartButton.setForeground(Color.WHITE);

        menuPanel.add(searchField);
        menuPanel.add(searchButton);
        menuPanel.add(cartButton);

        add(menuPanel, BorderLayout.CENTER);

        // Product Panel
        JPanel productPanel = new JPanel();
        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));
        productPanel.setBackground(Color.WHITE);
        productPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel productTitle = new JLabel("Các sản phẩm mẫu", SwingConstants.CENTER);
        productTitle.setFont(new Font("Serif", Font.BOLD, 20));
        productTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        productPanel.add(productTitle);

        JPanel productContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        productContainer.setBackground(Color.WHITE);

        String[] imagePaths = {"/images/aopolo.png", "/images/aopolo.png", "/images/aopolo.png"};
        String[] titles = {"Áo Polo Nam", "Áo Polo Nam", "Áo Polo Nam"};

        for (int i = 0; i < imagePaths.length; i++) {
            java.net.URL imageUrl = getClass().getResource(imagePaths[i]);
            if (imageUrl != null) {
                ImageIcon icon = new ImageIcon(imageUrl);
                Image scaledImage = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                ImageIcon resizedIcon = new ImageIcon(scaledImage);
                JLabel productLabel = new JLabel(resizedIcon);

                JLabel titleLabel = new JLabel(titles[i], SwingConstants.CENTER);
                titleLabel.setFont(new Font("Serif", Font.BOLD, 16));

                JPanel itemPanel = new JPanel(new BorderLayout());
                itemPanel.setBackground(Color.WHITE);
                itemPanel.add(titleLabel, BorderLayout.NORTH);
                itemPanel.add(productLabel, BorderLayout.CENTER);

                productContainer.add(itemPanel);
            } else {
                System.out.println("Không tìm thấy ảnh: " + imagePaths[i]);
            }
        }

        productPanel.add(productContainer);
        add(productPanel, BorderLayout.SOUTH);
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

    private void showBlogContent() {
        JOptionPane.showMessageDialog(this, "Đây là page của chúng tôi: ShopQuanAo123");
    }

    private void showCSKHContent() {
        JOptionPane.showMessageDialog(this, "Bạn có thể liên lạc với chúng tôi qua DISCORD:java");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NguoiDungGUI().setVisible(true);
        });
    }
}