package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class QuanAoGUI extends JPanel {
    private List<String> danhSachSanPham; 
    private JPanel gridPanel;
    private JButton Prev, Next;
    private JLabel lblPage;
    private int currentPage = 1;
    private final int itemsPerPage = 9; // 3x3 sản phẩm mỗi trang

    public QuanAoGUI() {
        setLayout(new BorderLayout());
        danhSachSanPham = getDanhSachQuanAo(); // Gộp danh sách quần & áo

        gridPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(gridPanel, BorderLayout.CENTER);

        // Thanh điều hướng trang
        JPanel navigationPanel = new JPanel();
        Prev = new JButton("<< Trang trước");
        Next = new JButton("Trang sau >>");
        lblPage = new JLabel("Trang: " + currentPage);
        
        Prev.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                loadSanPham();
            }
        });

        Next.addActionListener(e -> {
            if (currentPage < getTotalPage()) {
                currentPage++;
                loadSanPham();
            }
        });

        navigationPanel.add(Prev);
        navigationPanel.add(lblPage);
        navigationPanel.add(Next);
        add(navigationPanel, BorderLayout.SOUTH);

        // Load trang đầu tiên
        loadSanPham();
    }

    // Hiển thị sản phẩm lên giao diện
    private void loadSanPham() {
        gridPanel.removeAll();

        int start = (currentPage - 1) * itemsPerPage;
        int end = Math.min(start + itemsPerPage, danhSachSanPham.size());

        for (int i = start; i < end; i++) {
            String tenSanPham = danhSachSanPham.get(i);
            JPanel itemPanel = taoKhungSanPham(tenSanPham);
            gridPanel.add(itemPanel);
        }

        lblPage.setText("Trang: " + currentPage);
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    // Tạo khung sản phẩm đơn giản
    private JPanel taoKhungSanPham(String tenSanPham) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.setLayout(new BorderLayout());

        JLabel lblTen = new JLabel(tenSanPham, SwingConstants.CENTER);
        JLabel lblHinh = new JLabel("[Hình ảnh]", SwingConstants.CENTER);
        lblHinh.setPreferredSize(new Dimension(100, 100));

        panel.add(lblHinh, BorderLayout.CENTER);
        panel.add(lblTen, BorderLayout.SOUTH);
        return panel;
    }

    // Tính số trang
    private int getTotalPage() {
        return (int) Math.ceil((double) danhSachSanPham.size() / itemsPerPage);
    }

    // Gộp danh sách quần & áo
    private List<String> getDanhSachQuanAo() {
        List<String> list = new ArrayList<>();
        list.addAll(getQuan());
        list.addAll(getAo());
        return list;
    }

    private List<String> getQuan() {
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            list.add("Quần " + i);
        }
        return list;
    }
    
    private List<String> getAo() {
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            list.add("Áo " + i);
        }
        return list;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Danh Mục Quần Áo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 500);
            frame.setLocationRelativeTo(null); // Căn giữa màn hình

            frame.add(new QuanAoGUI()); // Sửa thành QuanAoGUI
            frame.setVisible(true);
        });
    }
}

