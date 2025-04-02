package GUI;

import javax.swing.*;
import java.awt.*;

public class NguoiDungGUI extends JFrame {

    public NguoiDungGUI() {
        setTitle("Fashion Store");
        setSize(900, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Màu header và màu các nút
        Color headerColor = new Color(160, 250, 160); // Màu header (xanh lá nhạt)
        Color buttonColor = new Color(100, 200, 100); // Màu các nút (xanh lá nhạt)

        // Panel Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(headerColor);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 10, 10));

        // Logo
        JLabel logoLabel = new JLabel("SSS", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Serif", Font.BOLD, 30));
        logoLabel.setForeground(Color.BLACK); 
        
        // Header Layout
        headerPanel.add(logoLabel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // Menu Panel (Màu trắng)
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(Color.WHITE);
        menuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        String[] menuItems = {"Hot trend", "Blog", "CSKH"};
        for (String item : menuItems) {
            JButton menuButton = new JButton(item);
            menuButton.setContentAreaFilled(true); // Bỏ mặc định là không có màu nền
            menuButton.setBackground(buttonColor); // Màu nền là xanh lá nhạt
            menuButton.setBorderPainted(false);
            menuButton.setFont(new Font("Serif", Font.PLAIN, 18));
            menuButton.setForeground(Color.WHITE); // Màu chữ là đen
            menuPanel.add(menuButton);
        }

        // Search & Cart Components
        JTextField searchField = new JTextField(20);
        searchField.setBackground(Color.WHITE);
        searchField.setForeground(Color.WHITE);
        searchField.setCaretColor(Color.WHITE);

        JButton searchButton = new JButton("🔍");
        searchButton.setBackground(buttonColor); // Màu nền xanh lá nhạt
        searchButton.setForeground(Color.WHITE);

        JButton cartButton = new JButton("🛒");
        cartButton.setBackground(buttonColor); // Màu nền xanh lá nhạt
        cartButton.setForeground(Color.WHITE);

        menuPanel.add(searchField);
        menuPanel.add(searchButton);
        menuPanel.add(cartButton);

        add(menuPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NguoiDungGUI().setVisible(true);
        });
    }
}
