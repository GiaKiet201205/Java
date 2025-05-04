package GUI;

import DAO.SanPhamDAO;
import DTO.SanPhamDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class NguoiDungGUI extends JFrame {

    private JPanel productDisplayPanel;
    private JPanel contentPanel;
    private JTextField searchField;

    class BackgroundPanel extends JPanel {
        private final Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            ImageIcon icon = new ImageIcon(getClass().getResource("/images/" + imagePath));
            backgroundImage = icon.getImage();
            if (backgroundImage == null || icon.getIconWidth() == -1) {
                System.err.println("Lỗi: Không tìm thấy ảnh nền tại /images/" + imagePath);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            } else {
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        }
    }

    public NguoiDungGUI() {
        setTitle("Fashion Store - Người Dùng");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(160, 250, 160));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Logo
        JLabel logoLabel = new JLabel("SSS", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Serif", Font.BOLD, 30));

        headerPanel.add(logoLabel, BorderLayout.CENTER);

        // Menu Panel
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(Color.WHITE);
        menuPanel.setPreferredSize(new Dimension(1000, 50));
        menuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Nút Trang Chủ
        JButton homeButton = new JButton("Trang chủ");
        homeButton.setBackground(new Color(100, 200, 100));
        homeButton.setForeground(Color.WHITE);
        homeButton.setFont(new Font("Serif", Font.PLAIN, 18));
        homeButton.setPreferredSize(new Dimension(120, 30));
        homeButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        homeButton.addActionListener(e -> setContent(new JScrollPane(productDisplayPanel)));
        menuPanel.add(homeButton);

        // Các nút chức năng
        String[] menuItems = {"Hot trend", "Blog", "CSKH"};
        for (String item : menuItems) {
            JButton menuButton = new JButton(item);
            menuButton.setFont(new Font("Serif", Font.PLAIN, 18));
            menuButton.setBackground(new Color(100, 200, 100));
            menuButton.setForeground(Color.WHITE);
            menuButton.setPreferredSize(new Dimension(120, 30));
            menuButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            menuPanel.add(menuButton);

            if (item.equals("Hot trend")) {
                menuButton.addActionListener(e -> showHotTrendContent());
            } else if (item.equals("Blog")) {
                menuButton.addActionListener(e -> showBlogContent());
            } else if (item.equals("CSKH")) {
                menuButton.addActionListener(e -> showCSKHContent());
            }
        }

        // ComboBox danh mục
        String[] categories = {"Chọn danh mục", "Quần Áo", "Giày Dép", "Phụ Kiện"};
        JComboBox<String> categoryComboBox = new JComboBox<>(categories);
        categoryComboBox.setFont(new Font("Serif", Font.PLAIN, 16));
        categoryComboBox.setBackground(new Color(100, 200, 100));
        categoryComboBox.setForeground(Color.WHITE);
        categoryComboBox.setPreferredSize(new Dimension(150, 30));
        categoryComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        menuPanel.add(categoryComboBox);

        categoryComboBox.addActionListener(e -> {
            String selected = (String) categoryComboBox.getSelectedItem();
            if ("Quần Áo".equals(selected)) showQuanAoGUI();
            else if ("Giày Dép".equals(selected)) showGiayDepGUI();
            else if ("Phụ Kiện".equals(selected)) showPhuKienGUI();
        });

        // Search
        searchField = new JTextField(15);
        searchField.setFont(new Font("Serif", Font.PLAIN, 16));
        searchField.setMaximumSize(new Dimension(200, 30));
        JButton searchButton = new JButton("🔍");
        searchButton.setBackground(new Color(100, 200, 100));
        searchButton.setForeground(Color.WHITE);
        searchButton.setPreferredSize(new Dimension(40, 30));
        searchButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        menuPanel.add(searchField);
        menuPanel.add(searchButton);

        // Log để kiểm tra
        System.out.println("Đã thêm searchField, searchButton vào menuPanel");

        // Hành động tìm kiếm
        searchButton.addActionListener(e -> searchProducts());

        // Top panel chứa header và menu
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(menuPanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Product Display Panel
        productDisplayPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        productDisplayPanel.setBackground(Color.WHITE);
        productDisplayPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.add(new JScrollPane(productDisplayPanel), BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);

        loadProducts();
    }

    private void searchProducts() {
        String keyword = searchField.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            loadProducts();
            return;
        }

        SanPhamDAO dao = new SanPhamDAO();
        ArrayList<SanPhamDTO> productList = dao.selectAll();
        productDisplayPanel.removeAll();

        int maxProducts = 9;
        int count = 0;
        for (SanPhamDTO sp : productList) {
            if (sp.getSoLuongTonKho() > 0 && count < maxProducts &&
                sp.getTenSanPham().toLowerCase().contains(keyword)) {
                addProductToView(sp);
                count++;
            }
        }

        while (count < maxProducts) {
            productDisplayPanel.add(new JPanel());
            count++;
        }

        productDisplayPanel.revalidate();
        productDisplayPanel.repaint();
    }

    private void setContent(JComponent component) {
        contentPanel.removeAll();
        contentPanel.add(component, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
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

    private void showHotTrendContent() {
        BackgroundPanel backgroundPanel = new BackgroundPanel("anhnenHotTrend.jpg");
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.setPreferredSize(new Dimension(800, 600)); // Điều chỉnh kích thước

        // Panel con để chứa nội dung với nền bán trong suốt
        JPanel contentSubPanel = new JPanel();
        contentSubPanel.setLayout(new BoxLayout(contentSubPanel, BoxLayout.Y_AXIS));
        contentSubPanel.setOpaque(true);
        contentSubPanel.setBackground(new Color(255, 255, 255, 200)); // Trắng bán trong suốt
        contentSubPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Tiêu đề chính
        JLabel titleLabel = new JLabel("<html><h1 style='text-align: center; color: #2E8B57;'>Xu Hướng Thời Trang 2025</h1></html>");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentSubPanel.add(titleLabel);
        contentSubPanel.add(Box.createVerticalStrut(20));

        // Phần giới thiệu
        JLabel introLabel = new JLabel("<html><p style='font-size: 14px; text-align: justify;'>" +
                "Khám phá những xu hướng thời trang nổi bật nhất năm 2025! Từ phong cách tối giản, " +
                "sắc màu pastel nhẹ nhàng đến những thiết kế đậm chất tương lai với chất liệu bền vững. " +
                "Dưới đây là những gợi ý giúp bạn luôn dẫn đầu xu hướng.</p></html>");
        introLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentSubPanel.add(introLabel);
        contentSubPanel.add(Box.createVerticalStrut(15));

        // Danh sách xu hướng
        JLabel trendsLabel = new JLabel("<html>" +
                "<h2 style='color: #2E8B57;'>Top 5 Xu Hướng Nổi Bật</h2>" +
                "<ul style='font-size: 14px;'>" +
                "<li><b>Phong cách Tối Giản (Minimalism)</b>: Áo thun trắng, quần âu ống suông kết hợp với phụ kiện tinh tế.</li>" +
                "<li><b>Sắc màu Pastel</b>: Những gam màu nhẹ nhàng như hồng phấn, xanh mint đang trở lại mạnh mẽ.</li>" +
                "<li><b>Chất liệu Bền Vững</b>: Sử dụng vải tái chế và chất liệu thân thiện với môi trường.</li>" +
                "<li><b>Phụ kiện Tương Lai</b>: Kính mắt oversized, túi xách metallic với thiết kế độc đáo.</li>" +
                "<li><b>Layering Đa Lớp</b>: Kết hợp áo len, áo khoác dài và khăn choàng để tạo phong cách cá tính.</li>" +
                "</ul></html>");
        trendsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentSubPanel.add(trendsLabel);
        contentSubPanel.add(Box.createVerticalStrut(15));

        // Lời kết
        JLabel conclusionLabel = new JLabel("<html><p style='font-size: 14px; text-align: justify;'>" +
                "Hãy thử ngay những xu hướng này để làm mới phong cách của bạn! Đừng quên ghé qua cửa hàng của chúng tôi để tìm kiếm những sản phẩm phù hợp nhất.</p></html>");
        conclusionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentSubPanel.add(conclusionLabel);

        backgroundPanel.add(contentSubPanel, BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane(backgroundPanel);
        scrollPane.getVerticalScrollBar().setBackground(new Color(200, 230, 200)); // Đặt màu nền cho thanh cuộn
        scrollPane.getHorizontalScrollBar().setBackground(new Color(200, 230, 200));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        setContent(scrollPane);
    }

    private void showBlogContent() {
        BackgroundPanel panel = new BackgroundPanel("anhnenTrangChu.jpg");
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(800, 600)); // Điều chỉnh kích thước

        // Panel con để chứa nội dung với nền bán trong suốt
        JPanel contentSubPanel = new JPanel();
        contentSubPanel.setLayout(new BoxLayout(contentSubPanel, BoxLayout.Y_AXIS));
        contentSubPanel.setOpaque(true);
        contentSubPanel.setBackground(new Color(255, 255, 255, 200)); // Trắng bán trong suốt
        contentSubPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Tiêu đề chính
        JLabel titleLabel = new JLabel("<html><h1 style='text-align: center; color: #2E8B57;'>Blog Thời Trang</h1></html>");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentSubPanel.add(titleLabel);
        contentSubPanel.add(Box.createVerticalStrut(20));

        // Bài viết 1
        JLabel article1Label = new JLabel("<html>" +
                "<h2 style='color: #2E8B57;'>1. Cách Phối Đồ Đi Làm Năng Động</h2>" +
                "<p style='font-size: 14px; text-align: justify;'>" +
                "Đi làm không nhất thiết phải nhàm chán! Hãy thử kết hợp một chiếc áo sơ mi trắng với quần âu ống suông, " +
                "thêm một đôi giày thể thao trắng để tạo điểm nhấn năng động. Đừng quên một chiếc túi tote để hoàn thiện phong cách.</p></html>");
        article1Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentSubPanel.add(article1Label);
        contentSubPanel.add(Box.createVerticalStrut(15));

        // Bài viết 2
        JLabel article2Label = new JLabel("<html>" +
                "<h2 style='color: #2E8B57;'>2. Màu Sắc Nổi Bật Năm 2025</h2>" +
                "<p style='font-size: 14px; text-align: justify;'>" +
                "Năm 2025 đánh dấu sự trở lại của các gam màu pastel như hồng phấn, xanh mint, và vàng nhạt. Những màu sắc này " +
                "không chỉ mang lại cảm giác nhẹ nhàng mà còn dễ dàng phối đồ cho mọi dịp, từ đi làm đến đi chơi.</p></html>");
        article2Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentSubPanel.add(article2Label);
        contentSubPanel.add(Box.createVerticalStrut(15));

        // Bài viết 3
        JLabel article3Label = new JLabel("<html>" +
                "<h2 style='color: #2E8B57;'>3. Gợi Ý Mix Đồ Theo Phong Cách Hàn Quốc</h2>" +
                "<p style='font-size: 14px; text-align: justify;'>" +
                "Phong cách Hàn Quốc luôn được yêu thích với sự trẻ trung và tinh tế. Hãy thử một chiếc áo len oversize kết hợp với " +
                "chân váy xếp ly, thêm một đôi bốt cổ ngắn và kính mắt trong suốt để tạo nên vẻ ngoài đậm chất K-style.</p></html>");
        article3Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentSubPanel.add(article3Label);
        contentSubPanel.add(Box.createVerticalStrut(15));

        // Bài viết 4
        JLabel article4Label = new JLabel("<html>" +
                "<h2 style='color: #2E8B57;'>4. Cách Chọn Phụ Kiện Cho Trang Phục Dạ Hội</h2>" +
                "<p style='font-size: 14px; text-align: justify;'>" +
                "Một chiếc váy dạ hội sẽ trở nên nổi bật hơn khi bạn chọn đúng phụ kiện. Hãy ưu tiên những món đồ như khuyên tai dài, " +
                "túi clutch ánh kim, và giày cao gót mũi nhọn để tạo nên vẻ ngoài sang trọng và quyến rũ.</p></html>");
        article4Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentSubPanel.add(article4Label);

        panel.add(contentSubPanel, BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.getVerticalScrollBar().setBackground(new Color(200, 230, 200)); // Đặt màu nền cho thanh cuộn
        scrollPane.getHorizontalScrollBar().setBackground(new Color(200, 230, 200));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        setContent(scrollPane);
    }

    private void showCSKHContent() {
        BackgroundPanel panel = new BackgroundPanel("anhnendki.jpeg");
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(800, 600)); // Điều chỉnh kích thước

        // Panel con để chứa nội dung với nền bán trong suốt
        JPanel contentSubPanel = new JPanel();
        contentSubPanel.setLayout(new BoxLayout(contentSubPanel, BoxLayout.Y_AXIS));
        contentSubPanel.setOpaque(true);
        contentSubPanel.setBackground(new Color(255, 255, 255, 200)); // Trắng bán trong suốt
        contentSubPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Tiêu đề chính
        JLabel titleLabel = new JLabel("<html><h1 style='text-align: center; color: #2E8B57;'>Hỗ Trợ Khách Hàng</h1></html>");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentSubPanel.add(titleLabel);
        contentSubPanel.add(Box.createVerticalStrut(20));

        // Thông tin liên hệ
        JLabel contactLabel = new JLabel("<html>" +
                "<h2 style='color: #2E8B57;'>Liên Hệ Với Chúng Tôi</h2>" +
                "<p style='font-size: 14px;'>" +
                "<b>Hotline:</b> 1900 123 456 (8:00 - 22:00, Thứ 2 - Chủ Nhật)<br>" +
                "<b>Email:</b> hotro@fashionstore.vn<br>" +
                "<b>Địa chỉ:</b> 123 Đường Thời Trang, Quận 1, TP. Hồ Chí Minh<br>" +
                "<b>Facebook:</b> <a href='#'>fb.com/fashionstore</a><br>" +
                "<b>Instagram:</b> <a href='#'>@fashionstore.vn</a></p></html>");
        contactLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentSubPanel.add(contactLabel);
        contentSubPanel.add(Box.createVerticalStrut(15));

        // Chính sách hỗ trợ
        JLabel policyLabel = new JLabel("<html>" +
                "<h2 style='color: #2E8B57;'>Chính Sách Hỗ Trợ</h2>" +
                "<ul style='font-size: 14px;'>" +
                "<li><b>Đổi trả:</b> Hỗ trợ đổi trả trong vòng 7 ngày nếu sản phẩm lỗi hoặc không đúng mô tả.</li>" +
                "<li><b>Giao hàng:</b> Miễn phí giao hàng toàn quốc cho đơn từ 500.000 VNĐ.</li>" +
                "<li><b>Bảo hành:</b> Bảo hành 30 ngày cho các sản phẩm phụ kiện.</li>" +
                "</ul></html>");
        policyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentSubPanel.add(policyLabel);
        contentSubPanel.add(Box.createVerticalStrut(15));

        // Câu hỏi thường gặp
        JLabel faqLabel = new JLabel("<html>" +
                "<h2 style='color: #2E8B57;'>Câu Hỏi Thường Gặp</h2>" +
                "<p style='font-size: 14px;'>" +
                "<b>Q: Làm thế nào để kiểm tra trạng thái đơn hàng?</b><br>" +
                "A: Bạn có thể kiểm tra trạng thái đơn hàng qua email xác nhận hoặc liên hệ hotline.<br><br>" +
                "<b>Q: Tôi có thể hủy đơn hàng không?</b><br>" +
                "A: Đơn hàng có thể hủy trong vòng 2 giờ sau khi đặt. Vui lòng liên hệ ngay với chúng tôi.</p></html>");
        faqLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentSubPanel.add(faqLabel);

        panel.add(contentSubPanel, BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.getVerticalScrollBar().setBackground(new Color(200, 230, 200)); // Đặt màu nền cho thanh cuộn
        scrollPane.getHorizontalScrollBar().setBackground(new Color(200, 230, 200));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        setContent(scrollPane);
    }

    private void loadProducts() {
        SanPhamDAO dao = new SanPhamDAO();
        ArrayList<SanPhamDTO> productList = dao.selectAll();
        productDisplayPanel.removeAll();

        if (productList == null || productList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không thể tải danh sách sản phẩm!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int maxProducts = 9;
        int count = 0;
        for (SanPhamDTO sp : productList) {
            if (sp.getSoLuongTonKho() > 0 && count < maxProducts) {
                addProductToView(sp);
                count++;
            }
        }

        while (count < maxProducts) {
            productDisplayPanel.add(new JPanel());
            count++;
        }

        productDisplayPanel.revalidate();
        productDisplayPanel.repaint();
    }

    public void addProductToView(SanPhamDTO sp) {
        JPanel productPanel = new JPanel(new BorderLayout());
        productPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        productPanel.setBackground(Color.WHITE);
        productPanel.putClientProperty("idSanPham", sp.getIdSanPham());

        JLabel imageLabel;
        try {
            if (sp.getHinhAnh() != null && !sp.getHinhAnh().isEmpty()) {
                ImageIcon icon = new ImageIcon(sp.getHinhAnh());
                if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                    Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
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

        JLabel nameLabel = new JLabel(sp.getTenSanPham(), SwingConstants.CENTER);
        nameLabel.setFont(new Font("Serif", Font.BOLD, 14));

        productPanel.add(imageLabel, BorderLayout.CENTER);
        productPanel.add(nameLabel, BorderLayout.SOUTH);

        productDisplayPanel.add(productPanel);
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
        loadProducts();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NguoiDungGUI().setVisible(true));
    }
}