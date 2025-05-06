package GUI;

import BLL.DanhMucBLL;
import DAO.SanPhamDAO;
import DTO.DanhMucDTO;
import DTO.SanPhamDTO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class TrangChuGUI extends JFrame {
    
    private JPanel productDisplayPanel;
    private JComboBox<String> categoryComboBox;
    private DanhMucBLL danhMucBLL;
    private JTextField searchField;

    public TrangChuGUI() {
        setTitle("Fashion Store");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setBackground(Color.WHITE); // Đặt nền JFrame thành màu trắng

        // Khởi tạo DanhMucBLL và đăng ký GUI
        danhMucBLL = new DanhMucBLL();
        danhMucBLL.registerTrangChuGUI(this);

        // Panel Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(160, 250, 160));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Logo
        JLabel logoLabel = new JLabel("SSS", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Serif", Font.BOLD, 30));

        // Login & Register Buttons
        JPanel authPanel = new JPanel();
        authPanel.setBackground(new Color(160, 250, 160));
        JButton loginButton = new JButton("Đăng nhập");
        JButton registerButton = new JButton("Đăng ký");
        Font buttonFont = new Font("Serif", Font.BOLD, 16);
        loginButton.setFont(buttonFont);
        registerButton.setFont(buttonFont);

        loginButton.setBackground(new Color(100, 200, 100));
        loginButton.setForeground(Color.WHITE);
        registerButton.setBackground(new Color(100, 200, 100));
        registerButton.setForeground(Color.WHITE);

        Dimension buttonSize = new Dimension(100, 30);
        loginButton.setPreferredSize(buttonSize);
        registerButton.setPreferredSize(buttonSize);

        loginButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        registerButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        authPanel.add(loginButton);
        authPanel.add(registerButton);

        // Liên kết giao diện đăng nhập
        loginButton.addActionListener(e -> {
            new DangNhapGUI(this).setVisible(true);
        });

        // Liên kết giao diện đăng ký
        registerButton.addActionListener((ActionEvent e) -> {
            DangKiGUI dangki = new DangKiGUI(TrangChuGUI.this);
            dangki.setVisible(true);
        });

        // Header Layout
        headerPanel.add(logoLabel, BorderLayout.CENTER);
        headerPanel.add(authPanel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // Menu Panel
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(Color.WHITE); // Đặt nền menuPanel thành màu trắng
        menuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

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
        updateCategoryComboBox(); // Tải danh mục từ cơ sở dữ liệu
        menuPanel.add(categoryComboBox);

        categoryComboBox.addActionListener(e -> {
            String selected = (String) categoryComboBox.getSelectedItem();
            if (selected != null && !selected.equals("Chọn danh mục")) {
                showCategoryProducts(selected);
            } else {
                loadProducts(null); // Tải lại tất cả sản phẩm nếu chọn "Chọn danh mục"
            }
        });

        // Search
        searchField = new JTextField(20);
        JButton searchButton = new JButton("🔍");
        searchButton.setBackground(new Color(100, 200, 100));
        searchButton.setForeground(Color.WHITE);

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

        // Thêm menuPanel vào một panel trung gian để tránh ghi đè
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
        scrollPane.getVerticalScrollBar().setBackground(Color.WHITE); // Thanh cuộn dọc màu trắng
        scrollPane.getHorizontalScrollBar().setBackground(Color.WHITE); // Thanh cuộn ngang màu trắng
        add(scrollPane, BorderLayout.CENTER);

        loadProducts(null); // Tải tất cả sản phẩm ban đầu
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

        // Tùy thuộc vào danh mục, hiển thị giao diện tương ứng
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

    // Hàm xử lý sau khi đăng nhập thành công
    public void onLoginSuccess(String vaiTro) {
        setVisible(false); // Ẩn TrangChuGUI
        if ("admin".equalsIgnoreCase(vaiTro)) {
            QuanTriVienGUI quanTriVienGUI = new QuanTriVienGUI(this);
            quanTriVienGUI.setVisible(true);
        } else {
            NguoiDungGUI nguoiDungGUI = new NguoiDungGUI();
            nguoiDungGUI.setVisible(true);
        }
    }

    private void loadProducts(ArrayList<SanPhamDTO> productList) {
        productDisplayPanel.removeAll();

        // Nếu productList là null, tải tất cả sản phẩm từ DAO
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
        productPanel.putClientProperty("idSanPham", sp.getIdSanPham()); // Lưu ID sản phẩm

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

    public void removeProductFromView(String idSanPham) {
        for (Component comp : productDisplayPanel.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel productPanel = (JPanel) comp;
                String panelId = (String) productPanel.getClientProperty("idSanPham");
                if (idSanPham.equals(panelId)) {
                    productDisplayPanel.remove(productPanel);
                    break;
                }
            }
        }
        productDisplayPanel.revalidate();
        productDisplayPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TrangChuGUI().setVisible(true));
    }
}