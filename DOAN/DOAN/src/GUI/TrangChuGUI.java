package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class TrangChuGUI extends JFrame {
    public TrangChuGUI() {
        setTitle("Fashion Store");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Logo
        JLabel logoLabel = new JLabel("SSS", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Serif", Font.BOLD, 30));
        
        // Login & Register Buttons
        JPanel authPanel = new JPanel();
        authPanel.setBackground(Color.WHITE);
        JButton loginButton = new JButton("Đăng nhập");
        JButton registerButton = new JButton("Đăng ký");
        Font buttonFont = new Font("Serif", Font.BOLD, 16);
        loginButton.setFont(buttonFont);
        registerButton.setFont(buttonFont);
        
        Dimension buttonSize = new Dimension(100,30);
        loginButton.setPreferredSize(buttonSize);
        registerButton.setPreferredSize(buttonSize);

        loginButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        registerButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        authPanel.add(loginButton);
        authPanel.add(registerButton);

        // Header Layout
        headerPanel.add(logoLabel, BorderLayout.CENTER);
        headerPanel.add(authPanel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // Menu Panel (with search & cart)
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(Color.WHITE);
        menuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        String[] menuItems = {"Hot trend", "Blog", "CSKH"};
        for (String item : menuItems) {
            JButton menuButton = new JButton(item);
            menuButton.setContentAreaFilled(false);
            menuButton.setBorderPainted(false);
            menuButton.setFont(new Font("Serif", Font.PLAIN, 18));
            menuPanel.add(menuButton);
        }

        // ComboBox Sản phẩm
        String[] categories = {"Chọn danh mục", "Quần Áo", "Giày Dép", "Phụ Kiện"};
        JComboBox<String> categoryComboBox = new JComboBox<>(categories);
        categoryComboBox.setFont(new Font("Serif", Font.PLAIN, 16));
        menuPanel.add(categoryComboBox);

        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = (String) categoryComboBox.getSelectedItem();
                if ("Quần Áo".equals(selectedCategory)) {
                    showQuanAoGUI();
                } else if ("Giày Dép".equals(selectedCategory)) {
                    showGiayDepGUI();
                } else if ("Phụ Kiện".equals(selectedCategory)) {
                    showPhuKienGUI();
                }
            }
        });
        
        // Search & Cart Components (Inline with Menu)
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("🔍");
        JButton cartButton = new JButton("🛒");

        menuPanel.add(searchField);
        menuPanel.add(searchButton);
        menuPanel.add(cartButton);

        add(menuPanel, BorderLayout.CENTER);

        // Product Panel
        JPanel productPanel = new JPanel();
        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));
        productPanel.setBackground(Color.WHITE);
        productPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Tiêu đề chính giữa
        JLabel productTitle = new JLabel("Các sản phẩm mẫu", SwingConstants.CENTER);
        productTitle.setFont(new Font("Serif", Font.BOLD, 20));
        productTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        productPanel.add(productTitle);

        // Tạo panel con để căn giữa tiêu đề và hình ảnh
        JPanel productContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        productContainer.setBackground(Color.WHITE);

        String[] imagePaths = {"/images/aopolo.png", "/images/aopolo.png", "/images/aopolo.png"};
        String[] titles = {"Áo Polo Nam", "Áo Polo Nam", "Áo Polo Nam"};

        for (int i = 0; i < imagePaths.length; i++) {
            URL imageUrl = getClass().getResource(imagePaths[i]);
            if (imageUrl != null) {
                // Load ảnh
                ImageIcon icon = new ImageIcon(imageUrl);
                Image scaledImage = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                ImageIcon resizedIcon = new ImageIcon(scaledImage);
                JLabel productLabel = new JLabel(resizedIcon);

                // Tiêu đề sản phẩm
                JLabel titleLabel = new JLabel(titles[i], SwingConstants.CENTER);
                titleLabel.setFont(new Font("Serif", Font.BOLD, 16));

                // Panel chứa tiêu đề + ảnh
                JPanel itemPanel = new JPanel();
                itemPanel.setLayout(new BorderLayout());
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TrangChuGUI().setVisible(true);
        });
    }
}
