package GUI;

import BLL.DanhMucBLL;
import DAO.SanPhamDAO;
import DTO.DanhMucDTO;
import DTO.SanPhamDTO;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class NguoiDungGUI extends JFrame {

    private JPanel productDisplayPanel;
    private JComboBox<String> categoryComboBox;
    private DanhMucBLL danhMucBLL;
    private JTextField searchField;

    public NguoiDungGUI() {
        setTitle("Fashion Store - Người Dùng");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setBackground(Color.WHITE); // Đặt nền JFrame thành màu trắng

        // Khởi tạo DanhMucBLL và đăng ký GUI
        danhMucBLL = new DanhMucBLL();
        danhMucBLL.registerNguoiDungGUI(this);

        // Panel Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(160, 250, 160)); // Giữ màu xanh giống TrangChuGUI
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Logo
        JLabel logoLabel = new JLabel("SSS", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Serif", Font.BOLD, 30));
        headerPanel.add(logoLabel, BorderLayout.CENTER);

        // Menu Panel
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        menuPanel.setBackground(Color.WHITE); // Đặt nền menuPanel thành màu trắng

        String[] menuItems = {"Hot trend", "Blog", "CSKH"};
        for (String item : menuItems) {
            JButton menuButton = new JButton(item);
            menuButton.setContentAreaFilled(false);
            menuButton.setBorderPainted(false);
            menuButton.setFont(new Font("Serif", Font.PLAIN, 18));
            menuButton.setOpaque(true);
            menuButton.setBackground(new Color(100, 200, 100));
            menuButton.setForeground(Color.WHITE);
            menuPanel.add(menuButton);

            if (item.equals("Blog")) {
                menuButton.addActionListener(e -> showBlogContent());
            } else if (item.equals("CSKH")) {
                menuButton.addActionListener(e -> showCSKHContent());
            }
        }

        // ComboBox sản phẩm
        categoryComboBox = new JComboBox<>();
        categoryComboBox.setFont(new Font("Serif", Font.PLAIN, 16));
        categoryComboBox.setBackground(new Color(100, 200, 100));
        categoryComboBox.setForeground(Color.WHITE);
        updateCategoryComboBox();
        menuPanel.add(categoryComboBox);

        categoryComboBox.addActionListener(e -> {
            String selected = (String) categoryComboBox.getSelectedItem();
            if (selected != null && !selected.equals("Chọn danh mục")) {
                showCategoryProducts(selected);
            } else {
                loadProducts(null);
            }
        });

        // Search
        searchField = new JTextField(20);
        JButton searchButton = new JButton("🔍");
        searchButton.setBackground(new Color(100, 200, 100));
        searchButton.setForeground(Color.WHITE);
        searchButton.setPreferredSize(new Dimension(100, 30)); // Đặt kích thước nhất quán

        // Chức năng tìm kiếm
        searchButton.addActionListener(e -> {
            String keyword = searchField.getText().trim().toLowerCase();
            SanPhamDAO dao = new SanPhamDAO();
            ArrayList<SanPhamDTO> allProducts = dao.selectAll();
            ArrayList<SanPhamDTO> filteredProducts = new ArrayList<>();

            for (SanPhamDTO sp : allProducts) {
                if (sp.getSoLuongTonKho() > 0 && sp.getTenSanPham().toLowerCase().contains(keyword)) {
                    filteredProducts.add(sp);
                }
            }

            if (filteredProducts.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm nào!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }

            loadProducts(filteredProducts);
        });

        menuPanel.add(searchField);
        menuPanel.add(searchButton);

        // Thêm menuPanel vào một panel trung gian
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE); // Đặt nền topPanel thành màu trắng
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(menuPanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Product Display Panel
        productDisplayPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        productDisplayPanel.setBackground(Color.WHITE); // Đặt nền productDisplayPanel thành màu trắng
        productDisplayPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(productDisplayPanel);
        scrollPane.setBackground(Color.WHITE); // Đặt nền scrollPane thành màu trắng
        scrollPane.setOpaque(true);
        scrollPane.getVerticalScrollBar().setBackground(Color.WHITE);
        scrollPane.getHorizontalScrollBar().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        loadProducts(null);
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
        frame.setSize(800, 700);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        if (categoryName.equals("Quần jean")) {
            frame.add(new QuanJeanGUI(frame));
        } else if (categoryName.equals("Quần short")) {
            frame.add(new QuanShortGUI(frame));
        } else if (categoryName.equals("Áo thun")) {
            frame.add(new AoThunGUI(frame));
        } else if (categoryName.equals("Áo khoác")) {
            frame.add(new AoKhoacGUI(frame));
        } else if (categoryName.equals("Áo hoodie")) {
            frame.add(new AoHoodieGUI(frame));
        }
        frame.setVisible(true);
    }

    private void showBlogContent() {
        JOptionPane.showMessageDialog(this, "Đây là page của chúng tôi: ShopQuanAo123");
    }

    private void showCSKHContent() {
        JOptionPane.showMessageDialog(this, "Bạn có thể liên lạc với chúng tôi qua DISCORD:java");
    }

    private void loadProducts(ArrayList<SanPhamDTO> productList) {
        productDisplayPanel.removeAll();

        if (productList == null) {
            SanPhamDAO dao = new SanPhamDAO();
            productList = dao.selectAll();
        }

        for (SanPhamDTO sp : productList) {
            if (sp.getSoLuongTonKho() > 0) {
                addProductToView(sp);
            }
        }

        productDisplayPanel.revalidate();
        productDisplayPanel.repaint();
    }

    public void addProductToView(SanPhamDTO sp) {
        JPanel productPanel = new JPanel(new BorderLayout());
        productPanel.setBackground(Color.WHITE); // Đặt nền productPanel thành màu trắng
        productPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        productPanel.putClientProperty("idSanPham", sp.getIdSanPham());

        JLabel imageLabel;
        try {
            if (sp.getHinhAnh() != null && !sp.getHinhAnh().isEmpty()) {
                ImageIcon icon = new ImageIcon(sp.getHinhAnh());
                if (icon.getImageLoadStatus() == java.awt.MediaTracker.COMPLETE) {
                    Image scaledImage = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    imageLabel = new JLabel(new ImageIcon(scaledImage));
                } else {
                    imageLabel = new JLabel("Invalid Image", SwingConstants.CENTER);
                }
            } else {
                imageLabel = new JLabel("No Image", SwingConstants.CENTER);
            }
        } catch (Exception e) {
            imageLabel = new JLabel("Error Loading Image", SwingConstants.CENTER);
            e.printStackTrace();
        }
        imageLabel.setPreferredSize(new Dimension(200, 200));

        JLabel nameLabel = new JLabel(sp.getTenSanPham(), SwingConstants.CENTER);
        nameLabel.setFont(new Font("Serif", Font.BOLD, 14));

        productPanel.add(imageLabel, BorderLayout.CENTER);
        productPanel.add(nameLabel, BorderLayout.SOUTH);

        productDisplayPanel.add(productPanel);

        productDisplayPanel.revalidate();
        productDisplayPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NguoiDungGUI().setVisible(true));
    }
}