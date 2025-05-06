package GUI;

import DAO.SanPhamDAO;
import DTO.SanPhamDTO;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AoThunGUI extends JPanel {
    private List<SanPhamDTO> danhSachSanPham;
    private JPanel gridPanel;
    private JButton Prev, Next, btnGioHang;
    private JLabel lblPage;
    private int currentPage = 1;
    private final int itemsPerPage = 9;
    private List<String> cart = new ArrayList<>();
    private int totalPrice = 0;
    private JFrame parentFrame;

    public AoThunGUI(JFrame parentFrame) {
        super();
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE); // Set background to white

        SanPhamDAO spDAO = new SanPhamDAO();
        danhSachSanPham = spDAO.laySanPhamTheoDanhMuc("DM003");

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(100, 200, 100));
        JLabel titleLabel = new JLabel("ÁO THUN", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Grid Panel
        gridPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        gridPanel.setBackground(Color.WHITE); // Set background to white
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(gridPanel, BorderLayout.CENTER);

        // Navigation
        JPanel navigationPanel = new JPanel();
        navigationPanel.setBackground(Color.WHITE); // Set background to white
        Prev = new JButton("<< Trang trước");
        Next = new JButton("Trang sau >>");
        lblPage = new JLabel("Trang: " + currentPage);
        btnGioHang = new JButton("🛒");

        // Set uniform size for buttons
        Dimension buttonSize = new Dimension(120, 30);
        Prev.setPreferredSize(buttonSize);
        Next.setPreferredSize(buttonSize);
        btnGioHang.setPreferredSize(buttonSize);

        Prev.setBackground(new Color(100, 200, 100));
        Prev.setForeground(Color.WHITE);
        Next.setBackground(new Color(100, 200, 100));
        Next.setForeground(Color.WHITE);
        btnGioHang.setBackground(new Color(100, 200, 100));
        btnGioHang.setForeground(Color.WHITE);

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
        btnGioHang.addActionListener(e -> {
            // Note: For a compact GioHangGUI, consider using a JTable or GridLayout with minimal padding
            // and a smaller frame size (e.g., 600x400) to display cart items and total price.
            GioHangGUI gioHang = new GioHangGUI(cart, totalPrice);
            gioHang.setSize(600, 400); // Set smaller size for compact display
            gioHang.setLocationRelativeTo(parentFrame);
            gioHang.setVisible(true);
        });

        navigationPanel.add(Prev);
        navigationPanel.add(lblPage);
        navigationPanel.add(Next);
        navigationPanel.add(btnGioHang);
        add(navigationPanel, BorderLayout.SOUTH);

        loadSanPham();
    }

    public AoThunGUI() {
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
            panel.setBackground(Color.WHITE); // Set product panel background to white

            // Tải và hiển thị hình ảnh
            JLabel lblHinh;
            try {
                if (sp.getHinhAnh() != null && !sp.getHinhAnh().isEmpty()) {
                    ImageIcon icon = new ImageIcon(sp.getHinhAnh());
                    if (icon.getImageLoadStatus() == java.awt.MediaTracker.COMPLETE) {
                        Image scaledImage = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                        lblHinh = new JLabel(new ImageIcon(scaledImage));
                    } else {
                        lblHinh = new JLabel("Invalid Image", SwingConstants.CENTER);
                    }
                } else {
                    lblHinh = new JLabel("No Image", SwingConstants.CENTER);
                }
            } catch (Exception e) {
                lblHinh = new JLabel("Error Loading Image", SwingConstants.CENTER);
                e.printStackTrace();
            }
            lblHinh.setPreferredSize(new Dimension(200, 200));

            JLabel lblTen = new JLabel(tenSanPham + " - " + giaSanPham + "₫", SwingConstants.CENTER);

            JButton btnThemGio = new JButton("Thêm vào giỏ hàng");
            btnThemGio.setBackground(Color.WHITE);
            btnThemGio.setForeground(Color.BLACK);

            btnThemGio.addActionListener(e -> {
                cart.add(tenSanPham);
                totalPrice += giaSanPham;
                // Customize JOptionPane button color
                Color originalButtonBackground = UIManager.getColor("Button.background");
                Color originalButtonForeground = UIManager.getColor("Button.foreground");
                UIManager.put("Button.background", Color.WHITE);
                UIManager.put("Button.foreground", Color.BLACK);
                JOptionPane.showMessageDialog(this, "Đã thêm sản phẩm vào giỏ hàng!");
                // Restore original UI settings
                UIManager.put("Button.background", originalButtonBackground);
                UIManager.put("Button.foreground", originalButtonForeground);
            });

            JPanel bottomPanel = new JPanel(new BorderLayout());
            bottomPanel.setBackground(Color.WHITE); // Set bottom panel background to white
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
            JFrame frame = new JFrame("Danh Mục Áo Thun");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 700);
            frame.setLocationRelativeTo(null);

            frame.add(new AoThunGUI(frame));
            frame.setVisible(true);
        });
    }
}