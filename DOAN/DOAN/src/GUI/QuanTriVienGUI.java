/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuanTriVienGUI extends JFrame {
    public QuanTriVienGUI() {
        setTitle("Quản Trị Viên");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel chính chứa sidebar và nội dung
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(200, 600));
        sidebar.setBackground(Color.LIGHT_GRAY);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Admin");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(titleLabel);
        sidebar.add(Box.createVerticalStrut(20));

        String[] menuItems = {"Sản phẩm", "Hóa đơn", "Chi tiết hóa đơn", "Nhà cung cấp", "Nhập hàng", 
                              "Chi tiết nhập hàng", "Nhân viên", "Khách hàng", "Danh mục", 
                              "Phương thức tt", "Thống kê"};
        
        for (String item : menuItems) {
            JLabel label = new JLabel(item);
            label.setForeground(Color.DARK_GRAY);
            label.setFont(new Font("Arial", Font.PLAIN, 18));
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            label.setCursor(new Cursor(Cursor.HAND_CURSOR));
            sidebar.add(label);
            sidebar.add(Box.createVerticalStrut(10));

            label.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    JOptionPane.showMessageDialog(null, "Bạn đã chọn: " + item);
                }
            });
        }

        JLabel homeLabel = new JLabel("HOME");
        homeLabel.setForeground(Color.RED);
        homeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        homeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        homeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(homeLabel);

        homeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JOptionPane.showMessageDialog(null, "Quay về trang chủ");
            }
        });

        // Nội dung chính
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);

        // Thêm sidebar và nội dung chính vào mainPanel
        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new QuanTriVienGUI().setVisible(true);
        });
    }
}