package GUI;

import DAO.SanPhamDAO;
import DTO.SanPhamDTO;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AoHoodieGUI extends JPanel {
    private List<SanPhamDTO> danhSachSanPham;
    private JPanel gridPanel;
    private JButton Prev, Next, btnGioHang;
    private JLabel lblPage;
    private int currentPage = 1;
    private final int itemsPerPage = 9;
    private List<String> cart = new ArrayList<>();
    private int totalPrice = 0;
    private JFrame parentFrame;

    public AoHoodieGUI(JFrame parentFrame) {
        super();
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());

        SanPhamDAO spDAO = new SanPhamDAO();
        danhSachSanPham = spDAO.laySanPhamTheoDanhMuc("DM005");

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(100, 200, 100));
        JLabel titleLabel = new JLabel("ÁO HOODIE", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Grid Panel
        gridPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(gridPanel, BorderLayout.CENTER);

        // Navigation
        JPanel navigationPanel = new JPanel();
        Prev = new JButton("<< Trang trước");
        Next = new JButton("Trang sau >>");
        lblPage = new JLabel("Trang: " + currentPage);

        Prev.setBackground(new Color(100, 200, 100));
        Prev.setForeground(Color.WHITE);
        Next.setBackground(new Color(100, 200, 100));
        Next.setForeground(Color.WHITE);

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

        // Nút giỏ hàng
        btnGioHang = new JButton("🛒");
        btnGioHang.setBackground(new Color(100, 200, 100));
        btnGioHang.setForeground(Color.WHITE);
        btnGioHang.addActionListener(e -> {
            new GioHangGUI(cart, totalPrice).setVisible(true);
        });

        navigationPanel.add(Prev);
        navigationPanel.add(lblPage);
        navigationPanel.add(Next);
        navigationPanel.add(btnGioHang);
        add(navigationPanel, BorderLayout.SOUTH);

        loadSanPham();
    }

    public AoHoodieGUI() {
        this(new JFrame());
    }

    private void loadSanPham() {
        gridPanel.removeAll();

        int start = (currentPage - 1) * itemsPerPage;
        int end = Math.min(start + itemsPerPage, danhSachSanPham.size());

        for (int i = start; i < end; i++) {
            SanPhamDTO sp = danhSachSanPham.get(i);
            String tenSanPham = sp.getTenSanPham();
            int giaSanPham = sp.getGia();

            JPanel panel = new JPanel();
            panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            panel.setLayout(new BorderLayout());

            JLabel lblTen = new JLabel(tenSanPham + " - " + giaSanPham + "₫", SwingConstants.CENTER);
            JLabel lblHinh = new JLabel("[Hình ảnh]", SwingConstants.CENTER);
            lblHinh.setPreferredSize(new Dimension(100, 100));

            JButton btnThemGio = new JButton("Thêm vào giỏ hàng");
            btnThemGio.setBackground(Color.WHITE);
            btnThemGio.setForeground(Color.BLACK);

            btnThemGio.addActionListener(e -> {
                cart.add(tenSanPham);
                totalPrice += giaSanPham;
                JOptionPane.showMessageDialog(this, "Đã thêm sản phẩm vào giỏ hàng!");
            });

            JPanel bottomPanel = new JPanel(new BorderLayout());
            bottomPanel.add(lblTen, BorderLayout.CENTER);
            bottomPanel.add(btnThemGio, BorderLayout.SOUTH);

            panel.add(lblHinh, BorderLayout.CENTER);
            panel.add(bottomPanel, BorderLayout.SOUTH);

            gridPanel.add(panel);
        }

        lblPage.setText("Trang: " + currentPage);
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private int getTotalPage() {
        return (int) Math.ceil((double) danhSachSanPham.size() / itemsPerPage);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Danh Mục Áo Hoodie");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 500);
            frame.setLocationRelativeTo(null);

            frame.add(new AoThunGUI(frame));
            frame.setVisible(true);
        });
    }
}
