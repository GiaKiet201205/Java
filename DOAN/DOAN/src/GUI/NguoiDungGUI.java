package GUI;

import javax.swing.*;
import java.awt.*;

public class NguoiDungGUI extends JFrame {

    public NguoiDungGUI() {
        setTitle("Fashion Store");
        setSize(900, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Logo
        JLabel logoLabel = new JLabel("SSS", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Serif", Font.BOLD, 30));

        // Header Layout
        headerPanel.add(logoLabel, BorderLayout.CENTER);
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

        // Search & Cart Components (Inline with Menu)
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("🔍");
        JButton cartButton = new JButton("🛒");

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
