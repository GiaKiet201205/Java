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
        setTitle("Cửa Hàng Thời Trang");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        danhMucBLL = new DanhMucBLL();
        danhMucBLL.registerTrangChuGUI(this);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(160, 250, 160));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel logoLabel = new JLabel("SSS", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Serif", Font.BOLD, 30));

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

        loginButton.addActionListener(e -> new DangNhapGUI(this).setVisible(true));
        registerButton.addActionListener(e -> new DangKiGUI(TrangChuGUI.this).setVisible(true));

        headerPanel.add(logoLabel, BorderLayout.CENTER);
        headerPanel.add(authPanel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(Color.WHITE);
        menuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        String[] menuItems = {"Hot Trend", "Blog", "CSKH"};
        for (String item : menuItems) {
            JButton menuButton = new JButton(item);
            menuButton.setContentAreaFilled(false);
            menuButton.setBorderPainted(false);
            menuButton.setFont(new Font("Serif", Font.PLAIN, 18));
            menuButton.setOpaque(true);
            menuButton.setBackground(new Color(100, 200, 100));
            menuButton.setForeground(Color.WHITE);
            menuPanel.add(menuButton);

            if (item.equals("Hot Trend")) {
                menuButton.addActionListener(e -> showHotTrendContent());
            } else if (item.equals("Blog")) {
                menuButton.addActionListener(e -> showBlogContent());
            } else if (item.equals("CSKH")) {
                menuButton.addActionListener(e -> showCSKHContent());
            }
        }

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

        searchField = new JTextField(20);
        JButton searchButton = new JButton("🔍");
        searchButton.setBackground(new Color(100, 200, 100));
        searchButton.setForeground(Color.WHITE);

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

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(menuPanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        productDisplayPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        productDisplayPanel.setBackground(Color.WHITE);
        productDisplayPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(productDisplayPanel);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setOpaque(true);
        scrollPane.getVerticalScrollBar().setBackground(Color.WHITE);
        scrollPane.getHorizontalScrollBar().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        loadProducts(null);
    }

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

    private void showHotTrendContent() {
        JFrame trendFrame = new JFrame("Hot Trend - Xu Hướng Thời Trang");
        trendFrame.setSize(800, 600);
        trendFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        trendFrame.setLocationRelativeTo(null);
        trendFrame.setLayout(new BorderLayout());
        trendFrame.setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Xu Hướng Thời Trang Nổi Bật", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 28));
        titleLabel.setForeground(new Color(100, 200, 100));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JLabel introLabel = new JLabel("<html><p style='width: 700px; text-align: justify;'>"
            + "Chào mừng bạn đến với góc xu hướng thời trang của ShopQuanAo123! Chúng tôi mang đến những phong cách mới nhất, "
            + "từ thời trang đường phố năng động đến những bộ sưu tập thanh lịch, giúp bạn luôn dẫn đầu trong mọi hoàn cảnh. "
            + "Mùa này, hãy khám phá những kiểu dáng đang làm mưa làm gió trên toàn cầu!</p></html>");
        introLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        introLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(introLabel);
        contentPanel.add(Box.createVerticalStrut(20));

        JLabel trendLabel = new JLabel("<html><p style='width: 700px; text-align: justify;'>"
            + "<b>Xu Hướng Thu 2025:</b> Mùa thu năm nay, những gam màu đậm như xanh ngọc lục bảo, đỏ rượu vang và nâu đất đang chiếm lĩnh sàn diễn thời trang. "
            + "Áo blazer oversize kết hợp với quần jeans ôm hoặc quần tây may đo tạo nên vẻ ngoài hiện đại và sành điệu. Đừng quên thêm một chiếc khăn len mỏng "
            + "hoặc mũ beret để hoàn thiện phong cách. Ngoài ra, những chiếc áo khoác trench coat dài cùng bốt cao cổ cũng là lựa chọn không thể bỏ qua.</p></html>");
        trendLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        trendLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(trendLabel);
        contentPanel.add(Box.createVerticalStrut(20));

        JLabel tipsLabel = new JLabel("<html><p style='width: 700px; text-align: justify;'>"
            + "<b>Mẹo Phối Đồ:</b> Để nổi bật, hãy thử kết hợp áo thun basic với quần short vải và áo khoác bomber cho phong cách trẻ trung. "
            + "Nếu bạn yêu thích sự thanh lịch, một chiếc áo sơ mi lụa phối cùng chân váy midi và giày cao gót sẽ là lựa chọn hoàn hảo. "
            + "Đừng ngần ngại thử nghiệm với các lớp layer như áo len mỏng bên trong áo khoác da để tạo điểm nhấn độc đáo. "
            + "Hãy ghé qua cửa hàng của chúng tôi để tìm những món đồ phù hợp với phong cách của bạn!</p></html>");
        tipsLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        tipsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(tipsLabel);
        contentPanel.add(Box.createVerticalStrut(20));

        JButton shopButton = new JButton("Khám Phá Bộ Sưu Tập");
        shopButton.setFont(new Font("Serif", Font.BOLD, 16));
        shopButton.setBackground(new Color(100, 200, 100));
        shopButton.setForeground(Color.WHITE);
        shopButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        shopButton.addActionListener(e -> trendFrame.dispose());
        contentPanel.add(shopButton);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setBorder(null);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        trendFrame.add(mainPanel);
        trendFrame.setVisible(true);
    }

    private void showBlogContent() {
        JFrame blogFrame = new JFrame("Blog - Tin Tức Thời Trang");
        blogFrame.setSize(800, 600);
        blogFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        blogFrame.setLocationRelativeTo(null);
        blogFrame.setLayout(new BorderLayout());
        blogFrame.setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Chào Mừng Đến Với Blog Thời Trang", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 28));
        titleLabel.setForeground(new Color(100, 200, 100));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JLabel introLabel = new JLabel("<html><p style='width: 700px; text-align: justify;'>"
            + "Blog thời trang của ShopQuanAo123 là nơi bạn tìm thấy nguồn cảm hứng bất tận! Chúng tôi cập nhật liên tục các xu hướng mới, "
            + "hướng dẫn phối đồ chi tiết và những bí quyết để nâng tầm phong cách cá nhân. Hãy cùng khám phá những bài viết thú vị để biến mỗi ngày "
            + "trở thành một sàn diễn thời trang của riêng bạn!</p></html>");
        introLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        introLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(introLabel);
        contentPanel.add(Box.createVerticalStrut(20));

        JLabel articleLabel = new JLabel("<html><p style='width: 700px; text-align: justify;'>"
            + "<b>Thời Trang Mùa Thu 2025:</b> Mùa thu này, hãy làm mới tủ đồ của bạn với những gam màu ấm áp như cam cháy, vàng mù tạt và nâu đất. "
            + "Áo khoác trench coat dài, khăn len oversized và bốt cao cổ là bộ ba hoàn hảo cho tiết trời se lạnh. Bạn có thể phối áo thun basic với quần short vải "
            + "và thêm một chiếc áo cardigan mỏng để tạo phong cách năng động nhưng vẫn tinh tế. Đối với những dịp đặc biệt, hãy thử áo sơ mi lụa kết hợp với chân váy bút chì.</p></html>");
        articleLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        articleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(articleLabel);
        contentPanel.add(Box.createVerticalStrut(20));

        JLabel styleLabel = new JLabel("<html><p style='width: 700px; text-align: justify;'>"
            + "<b>Bí Quyết Tạo Phong Cách:</b> Để tạo ấn tượng mạnh mẽ, hãy chú ý đến các chi tiết nhỏ như phụ kiện. Một chiếc đồng hồ tinh tế, khăn quàng cổ hoặc túi xách mini "
            + "có thể nâng cấp toàn bộ outfit. Ngoài ra, hãy thử nghiệm với các lớp layer – ví dụ, mặc áo len mỏng bên trong áo khoác da hoặc kết hợp áo hoodie với áo blazer. "
            + "Quan trọng nhất, hãy luôn tự tin với phong cách của bạn! Ghé thăm ShopQuanAo123 để tìm những món đồ phù hợp với mọi cá tính.</p></html>");
        styleLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        styleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(styleLabel);
        contentPanel.add(Box.createVerticalStrut(20));

        JButton visitShopButton = new JButton("Xem Bộ Sưu Tập");
        visitShopButton.setFont(new Font("Serif", Font.BOLD, 16));
        visitShopButton.setBackground(new Color(100, 200, 100));
        visitShopButton.setForeground(Color.WHITE);
        visitShopButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        visitShopButton.addActionListener(e -> blogFrame.dispose());
        contentPanel.add(visitShopButton);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setBorder(null);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        blogFrame.add(mainPanel);
        blogFrame.setVisible(true);
    }

    private void showCSKHContent() {
        JFrame cskhFrame = new JFrame("Chăm Sóc Khách Hàng - Liên Hệ");
        cskhFrame.setSize(800, 600);
        cskhFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cskhFrame.setLocationRelativeTo(null);
        cskhFrame.setLayout(new BorderLayout());
        cskhFrame.setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Chúng Tôi Luôn Sẵn Sàng Hỗ Trợ!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 28));
        titleLabel.setForeground(new Color(100, 200, 100));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JLabel introLabel = new JLabel("<html><p style='width: 700px; text-align: justify;'>"
            + "ShopQuanAo123 luôn đặt sự hài lòng của khách hàng lên hàng đầu. Đội ngũ chăm sóc khách hàng của chúng tôi sẵn sàng hỗ trợ bạn với mọi vấn đề, "
            + "từ theo dõi đơn hàng, tư vấn sản phẩm đến giải đáp các thắc mắc về dịch vụ. Chúng tôi cam kết mang đến trải nghiệm mua sắm tuyệt vời nhất!</p></html>");
        introLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        introLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(introLabel);
        contentPanel.add(Box.createVerticalStrut(20));

        JLabel contactInfo = new JLabel("<html>"
            + "<p style='width: 700px;'><b>Liên Hệ Với Chúng Tôi:</b></p>"
            + "<p style='width: 700px;'>- <b>Email:</b> hotro@shopquanao123.com</p>"
            + "<p style='width: 700px;'>- <b>Điện thoại:</b> (+84) 123 456 789</p>"
            + "<p style='width: 700px;'>- <b>Discord:</b> ShopQuanAo123#1234</p>"
            + "<p style='width: 700px;'>- <b>Giờ làm việc:</b> Thứ Hai - Thứ Bảy, 9h-18h</p>"
            + "<p style='width: 700px; text-align: justify;'>Bạn có thể liên hệ qua email hoặc điện thoại để được hỗ trợ ngay lập tức. Ngoài ra, tham gia cộng đồng Discord của chúng tôi để nhận thông tin cập nhật và tương tác trực tiếp với đội ngũ!</p>"
            + "</html>");
        contactInfo.setFont(new Font("Serif", Font.PLAIN, 16));
        contactInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(contactInfo);
        contentPanel.add(Box.createVerticalStrut(20));

        JLabel faqLabel = new JLabel("<html><p style='width: 700px; text-align: justify;'>"
            + "<b>Câu Hỏi Thường Gặp:</b><br>"
            + "- <b>Làm thế nào để theo dõi đơn hàng?</b> Đăng nhập vào tài khoản của bạn và truy cập mục 'Đơn Hàng Của Tôi' để xem trạng thái đơn hàng.<br>"
            + "- <b>Chính sách đổi trả của shop là gì?</b> Chúng tôi hỗ trợ đổi trả trong vòng 30 ngày đối với các sản phẩm chưa sử dụng và còn nguyên trạng.<br>"
            + "- <b>Shop có giao hàng quốc tế không?</b> Có, chúng tôi giao hàng đến hơn 50 quốc gia. Vui lòng xem chi tiết tại trang thông tin vận chuyển.<br>"
            + "- <b>Tôi có thể hủy đơn hàng không?</b> Bạn có thể hủy đơn hàng trước khi nó được xử lý. Liên hệ ngay với chúng tôi qua email hoặc điện thoại để được hỗ trợ.<br>"
            + "- <b>Làm sao để chọn size phù hợp?</b> Mỗi sản phẩm đều có bảng size chi tiết. Nếu cần tư vấn, đội ngũ của chúng tôi luôn sẵn sàng giúp bạn!</p></html>");
        faqLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        faqLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(faqLabel);
        contentPanel.add(Box.createVerticalStrut(20));

        JButton contactButton = new JButton("Tham Gia Discord");
        contactButton.setFont(new Font("Serif", Font.BOLD, 16));
        contactButton.setBackground(new Color(100, 200, 100));
        contactButton.setForeground(Color.WHITE);
        contactButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        contactButton.addActionListener(e -> JOptionPane.showMessageDialog(cskhFrame, "Tham gia Discord: ShopQuanAo123#1234"));
        contentPanel.add(contactButton);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setBorder(null);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        cskhFrame.add(mainPanel);
        cskhFrame.setVisible(true);
    }

    public void onLoginSuccess(String vaiTro) {
        setVisible(false);
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
        productPanel.setBackground(Color.WHITE);
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
                    imageLabel = new JLabel("Hình Ảnh Không Hợp Lệ", SwingConstants.CENTER);
                }
            } else {
                imageLabel = new JLabel("Không Có Hình Ảnh", SwingConstants.CENTER);
            }
        } catch (Exception e) {
            imageLabel = new JLabel("Lỗi Tải Hình Ảnh", SwingConstants.CENTER);
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