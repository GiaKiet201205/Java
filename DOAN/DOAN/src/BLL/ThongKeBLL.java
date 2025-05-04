package BLL;

import DAO.ChiTietDonHangDAO;
import DAO.ChiTietNhapHangDAO;
import DAO.DonHangDAO;
import DAO.NhanVienDAO;
import DAO.NhapHangDAO;
import DAO.PhuongThucThanhToanDAO;
import DAO.SanPhamDAO;
import DTO.ChiTietDonHangDTO;
import DTO.ChiTietNhapHangDTO;
import DTO.DonHangDTO;
import DTO.NhanVienDTO;
import DTO.NhapHangDTO;
import DTO.PhuongThucThanhToanDTO;
import DTO.SanPhamDTO;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ThongKeBLL {
    private DonHangDAO donHangDAO = new DonHangDAO();
    private ChiTietDonHangDAO chiTietDonHangDAO = new ChiTietDonHangDAO();
    private NhapHangDAO nhapHangDAO = new NhapHangDAO();
    private ChiTietNhapHangDAO chiTietNhapHangDAO = new ChiTietNhapHangDAO();
    private SanPhamDAO sanPhamDAO = new SanPhamDAO();
    private NhanVienDAO nhanVienDAO = new NhanVienDAO();
    private PhuongThucThanhToanDAO phuongThucThanhToanDAO = new PhuongThucThanhToanDAO();
    private DecimalFormat df = new DecimalFormat("#,###");
    private SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
    private SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    private SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

    // Thống kê doanh thu theo phương thức thanh toán
    public List<Object[]> getDoanhThuTheoPhuongThuc(String loaiThanhToan, String thoiGian) {
        List<PhuongThucThanhToanDTO> pttts = phuongThucThanhToanDAO.selectAll();
        List<DonHangDTO> donHangs = donHangDAO.selectAll();
        Map<String, Double> doanhThuMap = new HashMap<>();
        Map<String, Integer> soDonHangMap = new HashMap<>();

        for (PhuongThucThanhToanDTO pttt : pttts) {
            if (pttt.getLoaiThanhToan().equals(loaiThanhToan)) {
                DonHangDTO donHang = donHangDAO.selectById(pttt.getIdDonHang());
                if (donHang != null) {
                    String keyMonth = monthFormat.format(donHang.getNgayDatHang());
                    String keyYear = yearFormat.format(donHang.getNgayDatHang());
                    boolean matchesTimeFilter = thoiGian == null || thoiGian.isEmpty() || 
                        thoiGian.equals("Tháng " + keyMonth) || thoiGian.equals("Năm " + keyYear);
                    if (matchesTimeFilter) {
                        String key = loaiThanhToan;
                        doanhThuMap.merge(key, (double) donHang.getTongTien(), Double::sum);
                        soDonHangMap.merge(key, 1, Integer::sum);
                    }
                }
            }
        }

        List<Object[]> result = new ArrayList<>();
        int stt = 1;
        for (String key : doanhThuMap.keySet()) {
            double doanhThu = doanhThuMap.get(key);
            int soDonHang = soDonHangMap.get(key);
            double trungBinh = soDonHang > 0 ? doanhThu / soDonHang : 0;
            result.add(new Object[]{stt++, key, doanhThu, soDonHang, trungBinh});
        }
        return result;
    }

    // Thống kê doanh thu theo nhân viên
    public List<Object[]> getDoanhThuTheoNhanVien(String thoiGian) {
        List<DonHangDTO> donHangs = donHangDAO.selectAll();
        Map<String, Double> doanhThuMap = new HashMap<>();
        Map<String, Integer> soDonHangMap = new HashMap<>();
        Map<String, String> tenNhanVienMap = new HashMap<>();

        for (DonHangDTO donHang : donHangs) {
            String keyMonth = monthFormat.format(donHang.getNgayDatHang());
            String keyYear = yearFormat.format(donHang.getNgayDatHang());
            boolean matchesTimeFilter = thoiGian == null || thoiGian.isEmpty() || 
                thoiGian.equals("Tháng " + keyMonth) || thoiGian.equals("Năm " + keyYear);
            if (matchesTimeFilter) {
                String idNhanVien = donHang.getIdNhanVien();
                doanhThuMap.merge(idNhanVien, (double) donHang.getTongTien(), Double::sum);
                soDonHangMap.merge(idNhanVien, 1, Integer::sum);
                NhanVienDTO nhanVien = nhanVienDAO.selectById(idNhanVien);
                tenNhanVienMap.put(idNhanVien, nhanVien != null ? nhanVien.getHoTenNV() : "NV " + idNhanVien);
            }
        }

        List<Object[]> result = new ArrayList<>();
        int stt = 1;
        for (String idNhanVien : doanhThuMap.keySet()) {
            double doanhThu = doanhThuMap.get(idNhanVien);
            int soDonHang = soDonHangMap.get(idNhanVien);
            double trungBinh = soDonHang > 0 ? doanhThu / soDonHang : 0;
            result.add(new Object[]{stt++, idNhanVien, tenNhanVienMap.get(idNhanVien), doanhThu, soDonHang, trungBinh});
        }
        return result;
    }

    // Thống kê doanh thu theo hình thức mua hàng (Online/Trực tiếp)
    public List<Object[]> getDoanhThuTheoHinhThuc(String hinhThuc, String thoiGian) {
        List<DonHangDTO> donHangs = donHangDAO.selectAll();
        Map<String, Double> doanhThuMap = new HashMap<>();
        Map<String, Integer> soDonHangMap = new HashMap<>();

        for (DonHangDTO donHang : donHangs) {
            if (donHang.getHinhThucMuaHang().equals(hinhThuc)) {
                String keyMonth = monthFormat.format(donHang.getNgayDatHang());
                String keyYear = yearFormat.format(donHang.getNgayDatHang());
                boolean matchesTimeFilter = thoiGian == null || thoiGian.isEmpty() || 
                    thoiGian.equals("Tháng " + keyMonth) || thoiGian.equals("Năm " + keyYear);
                if (matchesTimeFilter) {
                    String key = hinhThuc;
                    doanhThuMap.merge(key, (double) donHang.getTongTien(), Double::sum);
                    soDonHangMap.merge(key, 1, Integer::sum);
                }
            }
        }

        List<Object[]> result = new ArrayList<>();
        int stt = 1;
        for (String key : doanhThuMap.keySet()) {
            double doanhThu = doanhThuMap.get(key);
            int soDonHang = soDonHangMap.get(key);
            double trungBinh = soDonHang > 0 ? doanhThu / soDonHang : 0;
            result.add(new Object[]{stt++, key, doanhThu, soDonHang, trungBinh});
        }
        return result;
    }

    // Thống kê sản phẩm
    public List<Object[]> getThongKeSanPham(String thoiGian) {
        List<ChiTietDonHangDTO> chiTiets = chiTietDonHangDAO.selectAll();
        List<DonHangDTO> donHangs = donHangDAO.selectAll();
        Map<String, Integer> soLuongBanMap = new HashMap<>();
        Map<String, Double> doanhThuMap = new HashMap<>();
        Map<String, Integer> tonKhoMap = new HashMap<>();
        Map<String, String> tenSanPhamMap = new HashMap<>();

        for (DonHangDTO donHang : donHangs) {
            String keyMonth = monthFormat.format(donHang.getNgayDatHang());
            String keyYear = yearFormat.format(donHang.getNgayDatHang());
            boolean matchesTimeFilter = thoiGian == null || thoiGian.isEmpty() || 
                thoiGian.equals("Tháng " + keyMonth) || thoiGian.equals("Năm " + keyYear);
            if (matchesTimeFilter) {
                for (ChiTietDonHangDTO chiTiet : chiTiets) {
                    if (chiTiet.getIdDonHang().equals(donHang.getIdDonHang())) {
                        String idSanPham = chiTiet.getIdSanPham();
                        soLuongBanMap.merge(idSanPham, chiTiet.getSoLuong(), Integer::sum);
                        doanhThuMap.merge(idSanPham, chiTiet.getSoLuong() * (double) chiTiet.getGiaBan(), Double::sum);
                    }
                }
            }
        }

        List<SanPhamDTO> sanPhams = sanPhamDAO.selectAll();
        for (SanPhamDTO sp : sanPhams) {
            tonKhoMap.put(sp.getIdSanPham(), sp.getSoLuongTonKho());
            tenSanPhamMap.put(sp.getIdSanPham(), sp.getTenSanPham());
        }

        List<Object[]> result = new ArrayList<>();
        int stt = 1;
        for (String idSanPham : soLuongBanMap.keySet()) {
            double doanhThu = doanhThuMap.get(idSanPham);
            result.add(new Object[]{
                stt++,
                idSanPham,
                tenSanPhamMap.getOrDefault(idSanPham, "SP " + idSanPham),
                soLuongBanMap.get(idSanPham),
                doanhThu,
                tonKhoMap.getOrDefault(idSanPham, 0)
            });
        }
        return result;
    }

    // Thống kê sản phẩm bán chạy
    public int getSanPhamBanChay(String thoiGian) {
        List<Object[]> sanPhamData = getThongKeSanPham(thoiGian);
        if (sanPhamData.isEmpty()) return 0;
        return (int) sanPhamData.stream()
            .max(Comparator.comparingInt(row -> (int) row[3]))
            .map(row -> row[3])
            .orElse(0);
    }

    // Thống kê sản phẩm bán chậm
    public int getSanPhamBanCham(String thoiGian) {
        List<Object[]> sanPhamData = getThongKeSanPham(thoiGian);
        if (sanPhamData.isEmpty()) return 0;
        return (int) sanPhamData.stream()
            .min(Comparator.comparingInt(row -> (int) row[3]))
            .map(row -> row[3])
            .orElse(0);
    }

    // Thống kê sản phẩm sắp hết hàng
    public long getSanPhamSapHetHang(String thoiGian) {
        List<Object[]> sanPhamData = getThongKeSanPham(thoiGian);
        return sanPhamData.stream()
            .filter(row -> (int) row[5] < 10)
            .count();
    }

    // Thống kê đơn hàng
    public Map<String, Integer> getThongKeDonHang(String thoiGian) {
        List<DonHangDTO> donHangs = donHangDAO.selectAll();
        Map<String, Integer> result = new HashMap<>();
        result.put("Đang xử lý", 0);
        result.put("Đã giao", 0);
        result.put("Đã hủy", 0);

        for (DonHangDTO donHang : donHangs) {
            String keyMonth = monthFormat.format(donHang.getNgayDatHang());
            String keyYear = yearFormat.format(donHang.getNgayDatHang());
            boolean matchesTimeFilter = thoiGian == null || thoiGian.isEmpty() || 
                thoiGian.equals("Tháng " + keyMonth) || thoiGian.equals("Năm " + keyYear);
            if (matchesTimeFilter) {
                String trangThai = donHang.getTrangThai();
                if (result.containsKey(trangThai)) {
                    result.merge(trangThai, 1, Integer::sum);
                }
            }
        }
        return result;
    }

    // Tỷ lệ đơn hàng online
    public Map<String, String> getTyLeDonHangOnline(String thoiGian) {
        List<PhuongThucThanhToanDTO> pttts = phuongThucThanhToanDAO.selectAll();
        List<DonHangDTO> donHangs = donHangDAO.selectAll();
        int onlineThanhCong = 0, onlineThatBai = 0, totalOnline = 0;

        for (PhuongThucThanhToanDTO pttt : pttts) {
            if (pttt.getLoaiThanhToan().equals("Online")) {
                DonHangDTO donHang = donHangDAO.selectById(pttt.getIdDonHang());
                if (donHang != null) {
                    String keyMonth = monthFormat.format(donHang.getNgayDatHang());
                    String keyYear = yearFormat.format(donHang.getNgayDatHang());
                    boolean matchesTimeFilter = thoiGian == null || thoiGian.isEmpty() || 
                        thoiGian.equals("Tháng " + keyMonth) || thoiGian.equals("Năm " + keyYear);
                    if (matchesTimeFilter) {
                        totalOnline++;
                        if (donHang.getTrangThai().equals("Đã giao")) {
                            onlineThanhCong++;
                        } else if (donHang.getTrangThai().equals("Đã hủy")) {
                            onlineThatBai++;
                        }
                    }
                }
            }
        }

        double tyLeThanhCong = totalOnline > 0 ? (onlineThanhCong * 100.0 / totalOnline) : 0;
        double tyLeThatBai = totalOnline > 0 ? (onlineThatBai * 100.0 / totalOnline) : 0;

        Map<String, String> result = new HashMap<>();
        result.put("Thành công", String.format("%.2f%%", tyLeThanhCong));
        result.put("Thất bại", String.format("%.2f%%", tyLeThatBai));
        return result;
    }

    // Thống kê nhập hàng
    public List<Object[]> getThongKeNhapHang(String thoiGian) {
        List<NhapHangDTO> nhapHangs = nhapHangDAO.selectAll();
        List<ChiTietNhapHangDTO> chiTiets = chiTietNhapHangDAO.selectAll();
        Map<String, Integer> soLuongNhapMap = new HashMap<>();
        Map<String, Double> chiPhiMap = new HashMap<>();
        Map<String, String> tenSanPhamMap = new HashMap<>();

        for (ChiTietNhapHangDTO chiTiet : chiTiets) {
            String idSanPham = chiTiet.getIdSanPham();
            soLuongNhapMap.merge(idSanPham, chiTiet.getSoLuongNhap(), Integer::sum);
            chiPhiMap.merge(idSanPham, chiTiet.getSoLuongNhap() * (double) chiTiet.getGiaNhap(), Double::sum);
        }

        List<SanPhamDTO> sanPhams = sanPhamDAO.selectAll();
        for (SanPhamDTO sp : sanPhams) {
            tenSanPhamMap.put(sp.getIdSanPham(), sp.getTenSanPham());
        }

        List<Object[]> result = new ArrayList<>();
        int stt = 1;
        for (NhapHangDTO nhapHang : nhapHangs) {
            String keyMonth = monthFormat.format(nhapHang.getNgayNhap());
            String keyYear = yearFormat.format(nhapHang.getNgayNhap());
            boolean matchesTimeFilter = thoiGian == null || thoiGian.isEmpty() || 
                thoiGian.equals("Tháng " + keyMonth) || thoiGian.equals("Năm " + keyYear);
            if (matchesTimeFilter) {
                for (ChiTietNhapHangDTO chiTiet : chiTietNhapHangDAO.selectAll()) {
                    if (chiTiet.getIdNhapHang().equals(nhapHang.getIdNhapHang())) {
                        double chiPhi = chiPhiMap.get(chiTiet.getIdSanPham());
                        result.add(new Object[]{
                            stt++,
                            sdf.format(nhapHang.getNgayNhap()),
                            chiTiet.getIdSanPham(),
                            tenSanPhamMap.getOrDefault(chiTiet.getIdSanPham(), "SP " + chiTiet.getIdSanPham()),
                            soLuongNhapMap.get(chiTiet.getIdSanPham()),
                            chiPhi
                        });
                    }
                }
            }
        }
        return result;
    }

    // Tổng chi phí nhập hàng
    public double getTongChiPhiNhap(String thoiGian) {
        List<NhapHangDTO> nhapHangs = nhapHangDAO.selectAll();
        double tongChiPhi = 0;
        for (NhapHangDTO nhapHang : nhapHangs) {
            String keyMonth = monthFormat.format(nhapHang.getNgayNhap());
            String keyYear = yearFormat.format(nhapHang.getNgayNhap());
            boolean matchesTimeFilter = thoiGian == null || thoiGian.isEmpty() || 
                thoiGian.equals("Tháng " + keyMonth) || thoiGian.equals("Năm " + keyYear);
            if (matchesTimeFilter) {
                tongChiPhi += nhapHang.getTongGiaTriNhap();
            }
        }
        return tongChiPhi;
    }

    // Sản phẩm nhập nhiều nhất
    public int getSanPhamNhapNhieuNhat(String thoiGian) {
        List<Object[]> nhapHangData = getThongKeNhapHang(thoiGian);
        if (nhapHangData.isEmpty()) return 0;
        return (int) nhapHangData.stream()
            .max(Comparator.comparingInt(row -> (int) row[4]))
            .map(row -> row[4])
            .orElse(0);
    }

    // Sản phẩm nhập ít nhất
    public int getSanPhamNhapItNhat(String thoiGian) {
        List<Object[]> nhapHangData = getThongKeNhapHang(thoiGian);
        if (nhapHangData.isEmpty()) return 0;
        return (int) nhapHangData.stream()
            .min(Comparator.comparingInt(row -> (int) row[4]))
            .map(row -> row[4])
            .orElse(0);
    }

    // Thống kê lợi nhuận
    public Map<String, String> getThongKeLoiNhuan(String thoiGian) {
        List<DonHangDTO> donHangs = donHangDAO.selectAll();
        List<NhapHangDTO> nhapHangs = nhapHangDAO.selectAll();

        double tongDoanhThu = 0;
        double tongChiPhi = 0;

        for (DonHangDTO donHang : donHangs) {
            String keyMonth = monthFormat.format(donHang.getNgayDatHang());
            String keyYear = yearFormat.format(donHang.getNgayDatHang());
            boolean matchesTimeFilter = thoiGian == null || thoiGian.isEmpty() || 
                thoiGian.equals("Tháng " + keyMonth) || thoiGian.equals("Năm " + keyYear);
            if (matchesTimeFilter) {
                tongDoanhThu += donHang.getTongTien();
            }
        }

        for (NhapHangDTO nhapHang : nhapHangs) {
            String keyMonth = monthFormat.format(nhapHang.getNgayNhap());
            String keyYear = yearFormat.format(nhapHang.getNgayNhap());
            boolean matchesTimeFilter = thoiGian == null || thoiGian.isEmpty() || 
                thoiGian.equals("Tháng " + keyMonth) || thoiGian.equals("Năm " + keyYear);
            if (matchesTimeFilter) {
                tongChiPhi += nhapHang.getTongGiaTriNhap();
            }
        }

        double loiNhuan = tongDoanhThu - tongChiPhi;
        double tyLeLoiNhuan = tongDoanhThu > 0 ? (loiNhuan * 100.0 / tongDoanhThu) : 0;

        Map<String, String> result = new HashMap<>();
        result.put("Tổng lợi nhuận", df.format(loiNhuan) + " VND");
        result.put("Tỷ lệ lợi nhuận", String.format("%.2f%%", tyLeLoiNhuan));
        return result;
    }

    // Sắp xếp dữ liệu
    public List<Object[]> sortData(List<Object[]> data, String sortOption, int doanhThuIndex) {
        if (data == null || data.isEmpty()) return data;

        try {
            if (sortOption.equals("Doanh thu tăng dần")) {
                data.sort((a, b) -> {
                    double numA = (double) a[doanhThuIndex];
                    double numB = (double) b[doanhThuIndex];
                    return Double.compare(numA, numB);
                });
            } else if (sortOption.equals("Doanh thu giảm dần")) {
                data.sort((a, b) -> {
                    double numA = (double) a[doanhThuIndex];
                    double numB = (double) b[doanhThuIndex];
                    return Double.compare(numB, numA);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Nếu có lỗi, giữ nguyên dữ liệu
        }

        // Cập nhật lại STT sau khi sắp xếp
        for (int i = 0; i < data.size(); i++) {
            data.get(i)[0] = i + 1;
        }
        return data;
    }
}