package GUI;

import GUI.panel.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class QuanTriVienGUI extends JFrame {
    private JPanel mainPanel, menuPanel, contentPanel;
    private JLabel lblAdmin;
    private JButton btnSanPham, btnHoaDon, btnChiTietHoaDon, btnNhaCungCap;
    private JButton btnNhapHang, btnChiTietNhapHang, btnNhanVien, btnKhachHang;
    private JButton btnDanhMuc, btnPhuongThucTT, btnThongKe, btnHome;
    private CardLayout cardLayout;

    // Các panel từ package GUI.panel
    private SanPhamPanel sanPhamPanel;
    private NhaCungCapPanel nhaCungCapPanel;
    private HoaDonPanel hoaDonPanel;
    private ChiTietHoaDonPanel chiTietHoaDonPanel;
    private NhapHangPanel nhapHangPanel;
    private ChiTietNhapHangPanel chiTietNhapHangPanel;
    private NhanVienPanel nhanVienPanel;
    private KhachHangPanel khachHangPanel;
    private DanhMucPanel danhMucPanel;
    private PhuongThucTTPanel phuongThucTTPanel;
    private ThongKePanel thongKePanel;

    private Color menuColor = new Color(255, 255, 255);
    private Font titleFont = new Font("Arial", Font.BOLD, 24);
    private Font menuItemFont = new Font("Arial", Font.PLAIN, 16);

    public QuanTriVienGUI() {
        setTitle("Quản Trị Viên");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        // Main Panel với BorderLayout
        mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        // Tạo Menu Panel
        createMenuPanel();

        // Tạo Content Panel với CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Khởi tạo các panel
        sanPhamPanel = new SanPhamPanel();
        nhaCungCapPanel = new NhaCungCapPanel();
        hoaDonPanel = new HoaDonPanel();
        chiTietHoaDonPanel = new ChiTietHoaDonPanel();
        nhapHangPanel = new NhapHangPanel();
        chiTietNhapHangPanel = new ChiTietNhapHangPanel();
        nhanVienPanel = new NhanVienPanel();
        khachHangPanel = new KhachHangPanel();
        danhMucPanel = new DanhMucPanel();
        phuongThucTTPanel = new PhuongThucTTPanel();
        thongKePanel = new ThongKePanel();

        // Thêm các panel vào CardLayout
        contentPanel.add(sanPhamPanel, "SanPham");
        contentPanel.add(nhaCungCapPanel, "NhaCungCap");
        contentPanel.add(hoaDonPanel, "HoaDon");
        contentPanel.add(chiTietHoaDonPanel, "ChiTietHoaDon");
        contentPanel.add(nhapHangPanel, "NhapHang");
        contentPanel.add(chiTietNhapHangPanel, "ChiTietNhapHang");
        contentPanel.add(nhanVienPanel, "NhanVien");
        contentPanel.add(khachHangPanel, "KhachHang");
        contentPanel.add(danhMucPanel, "DanhMuc");
        contentPanel.add(phuongThucTTPanel, "PhuongThucTT");
        contentPanel.add(thongKePanel, "ThongKe");

        // Mặc định hiển thị panel Sản phẩm
        cardLayout.show(contentPanel, "SanPham");
    }

    private void createMenuPanel() {
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(menuColor);
        menuPanel.setPreferredSize(new Dimension(200, getHeight()));
        mainPanel.add(menuPanel, BorderLayout.WEST);

        // Tiêu đề Admin
        JPanel adminPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        adminPanel.setBackground(menuColor);
        adminPanel.setMaximumSize(new Dimension(200, 60));
        lblAdmin = new JLabel("Admin");
        lblAdmin.setFont(titleFont);
        adminPanel.add(lblAdmin);
        menuPanel.add(adminPanel);
        menuPanel.add(Box.createVerticalStrut(20));

        // Tạo các nút menu
        btnSanPham = createMenuButton("Sản phẩm");
        btnNhaCungCap = createMenuButton("Nhà cung cấp");
        btnHoaDon = createMenuButton("Hóa đơn");
        btnChiTietHoaDon = createMenuButton("Chi tiết hóa đơn");
        btnNhapHang = createMenuButton("Nhập hàng");
        btnChiTietNhapHang = createMenuButton("Chi tiết nhập hàng");
        btnNhanVien = createMenuButton("Nhân viên");
        btnKhachHang = createMenuButton("Khách hàng");
        btnDanhMuc = createMenuButton("Danh mục");
        btnPhuongThucTT = createMenuButton("Phương thức TT");
        btnThongKe = createMenuButton("Thống kê");

        // Thêm các nút vào menu
        addMenuButton(btnSanPham);
        addMenuButton(btnNhaCungCap);
        addMenuButton(btnHoaDon);
        addMenuButton(btnChiTietHoaDon);
        addMenuButton(btnNhapHang);
        addMenuButton(btnChiTietNhapHang);
        addMenuButton(btnNhanVien);
        addMenuButton(btnKhachHang);
        addMenuButton(btnDanhMuc);
        addMenuButton(btnPhuongThucTT);
        addMenuButton(btnThongKe);

        // Thêm nút HOME
        JPanel homePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        homePanel.setBackground(menuColor);
        homePanel.setMaximumSize(new Dimension(200, 50));
        btnHome = new JButton("HOME");
        btnHome.setFont(new Font("Arial", Font.BOLD, 16));
        btnHome.setForeground(Color.RED);
        btnHome.setContentAreaFilled(false);
        btnHome.setBorderPainted(false);
        btnHome.setFocusPainted(false);
        homePanel.add(btnHome);
        menuPanel.add(homePanel);

        // Thêm trình xử lý sự kiện cho các nút
        btnSanPham.addActionListener(e -> cardLayout.show(contentPanel, "SanPham"));
        btnNhaCungCap.addActionListener(e -> cardLayout.show(contentPanel, "NhaCungCap"));
        btnHoaDon.addActionListener(e -> cardLayout.show(contentPanel, "HoaDon"));
        btnChiTietHoaDon.addActionListener(e -> cardLayout.show(contentPanel, "ChiTietHoaDon"));
        btnNhapHang.addActionListener(e -> cardLayout.show(contentPanel, "NhapHang"));
        btnChiTietNhapHang.addActionListener(e -> cardLayout.show(contentPanel, "ChiTietNhapHang"));
        btnNhanVien.addActionListener(e -> cardLayout.show(contentPanel, "NhanVien"));
        btnKhachHang.addActionListener(e -> cardLayout.show(contentPanel, "KhachHang"));
        btnDanhMuc.addActionListener(e -> cardLayout.show(contentPanel, "DanhMuc"));
        btnPhuongThucTT.addActionListener(e -> cardLayout.show(contentPanel, "PhuongThucTT"));
        btnThongKe.addActionListener(e -> cardLayout.show(contentPanel, "ThongKe"));
        btnHome.addActionListener(e -> cardLayout.show(contentPanel, "SanPham"));
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(menuItemFont);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMaximumSize(new Dimension(200, 40));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(180, 180, 180));
                button.setContentAreaFilled(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setContentAreaFilled(false);
            }
        });

        return button;
    }

    private void addMenuButton(JButton button) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(menuColor);
        panel.setMaximumSize(new Dimension(200, 40));
        panel.add(button);
        menuPanel.add(panel);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            QuanTriVienGUI gui = new QuanTriVienGUI();
            gui.setVisible(true);
        });
    }
}