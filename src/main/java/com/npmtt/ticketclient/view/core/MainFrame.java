package com.npmtt.ticketclient.view.core;

import com.npmtt.ticketclient.enums.VaiTro;
import com.npmtt.ticketclient.view.BaseFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

public final class MainFrame extends BaseFrame {

    private JMenu menuTrangChu;
    private JMenuItem trangChu;
    private JMenu menuNhanVien;
    private JMenuItem qlNhanVien, tkNhanVien;
    private JMenu menuTau;
    private JMenuItem qlTau, tkTau;
    private JMenu menuLoaiToa;
    private JMenuItem qlLoaiToa, tkLoaiToa;
    private JMenu menuToaTau;
    private JMenuItem qlToaTau, tkToaTau;
    private JMenu menuGhe;
    private JMenuItem qlGhe, tkGhe;
    private JMenu menuGaTau;
    private JMenuItem qlGaTau, tkGaTau;
    private JMenu menuTuyenDuong;
    private JMenuItem qlTuyenDuong, tkTuyenDuong;
    private JMenu menuLichTrinh;
    private JMenuItem qlLichTrinh, tkLichTrinh;
    private JMenu menuKhachHang;
    private JMenuItem qlKhachHang, tkKhachHang;
    private JMenu menuVeTau;
    private JMenuItem qlVeTau, tkVeTau;
    private JMenu menuThongKe;
    private JMenuItem thongKeDoanhThuNgay, thongKeDoanhThuTuyen, thongKeTyLeLapDay, thongKeKhachVIP, thongKeDoanhSo;
    private JMenu menuCaNhan;
    private JMenuItem thongTinCaNhan, doiMatKhau, dangXuat, thoat;

    private JPanel container;

    public MainFrame() {
        super("Quản lý hệ thống bán vé tàu");
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        initComponents();
        this.setLocationRelativeTo(null);
    }

    @Override
    protected void initComponents() {
        setLayout(new BorderLayout());

        container = new JPanel();
        container.setBorder(new EmptyBorder(5, 5, 5, 5));

        JScrollPane scrollPane = new JScrollPane(container);
        add(scrollPane, BorderLayout.CENTER);

        menuTrangChu = new JMenu("Trang chủ");
        trangChu = new JMenuItem("Quay về trang chủ");
        menuTrangChu.add(trangChu);

        menuCaNhan = new JMenu("Xin chào");
        thongTinCaNhan = new JMenuItem("Thông tin cá nhân");
        doiMatKhau = new JMenuItem("Đổi mật khẩu");
        dangXuat = new JMenuItem("Đăng xuất");
        thoat = new JMenuItem("Thoát ứng dụng");
        menuCaNhan.add(thongTinCaNhan);
        menuCaNhan.add(doiMatKhau);
        menuCaNhan.add(dangXuat);
        menuCaNhan.add(thoat);

        menuNhanVien = new JMenu("Nhân viên");
        qlNhanVien = new JMenuItem("Quản lý thông tin");
        tkNhanVien = new JMenuItem("Tra cứu");
        menuNhanVien.add(qlNhanVien);
        menuNhanVien.add(tkNhanVien);

        menuTau = new JMenu("Tàu");
        qlTau = new JMenuItem("Quản lý thông tin");
        tkTau = new JMenuItem("Tra cứu");
        menuTau.add(qlTau);
        menuTau.add(tkTau);

        menuLoaiToa = new JMenu("Loại toa");
        qlLoaiToa = new JMenuItem("Quản lý thông tin");
        tkLoaiToa = new JMenuItem("Tra cứu");
        menuLoaiToa.add(qlLoaiToa);
        menuLoaiToa.add(tkLoaiToa);

        menuToaTau = new JMenu("Toa tàu");
        qlToaTau = new JMenuItem("Quản lý thông tin");
        tkToaTau = new JMenuItem("Tra cứu");
        menuToaTau.add(qlToaTau);
        menuToaTau.add(tkToaTau);

        menuGhe = new JMenu("Ghế");
        qlGhe = new JMenuItem("Quản lý thông tin");
        tkGhe = new JMenuItem("Tra cứu");
        menuGhe.add(qlGhe);
        menuGhe.add(tkGhe);

        menuGaTau = new JMenu("Ga tàu");
        qlGaTau = new JMenuItem("Quản lý thông tin");
        tkGaTau = new JMenuItem("Tra cứu");
        menuGaTau.add(qlGaTau);
        menuGaTau.add(tkGaTau);

        menuTuyenDuong = new JMenu("Tuyến đường");
        qlTuyenDuong = new JMenuItem("Quản lý thông tin");
        tkTuyenDuong = new JMenuItem("Tra cứu");
        menuTuyenDuong.add(qlTuyenDuong);
        menuTuyenDuong.add(tkTuyenDuong);

        menuLichTrinh = new JMenu("Lịch trình");
        qlLichTrinh = new JMenuItem("Quản lý thông tin");
        tkLichTrinh = new JMenuItem("Tra cứu");
        menuLichTrinh.add(qlLichTrinh);
        menuLichTrinh.add(tkLichTrinh);

        menuKhachHang = new JMenu("Khách hàng");
        qlKhachHang = new JMenuItem("Quản lý thông tin");
        tkKhachHang = new JMenuItem("Tra cứu");
        menuKhachHang.add(qlKhachHang);
        menuKhachHang.add(tkKhachHang);

        menuVeTau = new JMenu("Vé tàu");
        qlVeTau = new JMenuItem("Quản lý thông tin");
        tkVeTau = new JMenuItem("Tra cứu");
        menuVeTau.add(qlVeTau);
        menuVeTau.add(tkVeTau);

        menuThongKe = new JMenu("Thống kê");
        thongKeDoanhThuNgay = new JMenuItem("Doanh thu theo ngày");
        thongKeDoanhThuTuyen = new JMenuItem("Doanh thu theo tuyến");
        thongKeTyLeLapDay = new JMenuItem("Tỷ lệ lấp đầy tàu");
        thongKeKhachVIP = new JMenuItem("Khách hàng VIP");
        thongKeDoanhSo = new JMenuItem("Doanh số bán hàng");
        menuThongKe.add(thongKeDoanhThuNgay);
        menuThongKe.add(thongKeDoanhThuTuyen);
        menuThongKe.add(thongKeTyLeLapDay);
        menuThongKe.add(thongKeKhachVIP);
        menuThongKe.add(thongKeDoanhSo);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menuTrangChu);
        menuBar.add(menuNhanVien);
        menuBar.add(menuTau);
        menuBar.add(menuLoaiToa);
        menuBar.add(menuToaTau);
        menuBar.add(menuGhe);
        menuBar.add(menuGaTau);
        menuBar.add(menuTuyenDuong);
        menuBar.add(menuLichTrinh);
        menuBar.add(menuKhachHang);
        menuBar.add(menuVeTau);
        menuBar.add(menuThongKe);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(menuCaNhan);

        setJMenuBar(menuBar);
    }

    public void setXinChao(String tenNhanVien) {
        menuCaNhan.setText("Xin chào, " + tenNhanVien);
    }

    public void showPanel(JPanel panel) {
        if (container != null) {
            container.removeAll();
            container.add(panel);
            container.revalidate();
            container.repaint();
        }
    }

    public void hienMenuTheoQuyen(VaiTro vaiTro) {
        boolean isLoggedIn = (vaiTro != null);
        boolean isAdmin = (vaiTro == VaiTro.ADMIN);

        menuTrangChu.setVisible(isLoggedIn);

        menuNhanVien.setVisible(isAdmin);
        menuLoaiToa.setVisible(isAdmin);
        menuGhe.setVisible(isAdmin);
        menuThongKe.setVisible(isAdmin);

        menuTau.setVisible(isLoggedIn);
        menuToaTau.setVisible(isLoggedIn);
        menuGaTau.setVisible(isLoggedIn);
        menuTuyenDuong.setVisible(isLoggedIn);
        menuLichTrinh.setVisible(isLoggedIn);
        menuKhachHang.setVisible(isLoggedIn);
        menuVeTau.setVisible(isLoggedIn);

        trangChu.setVisible(isLoggedIn);

        qlTau.setVisible(isAdmin);
        tkTau.setVisible(isLoggedIn);

        qlToaTau.setVisible(isAdmin);
        tkToaTau.setVisible(isLoggedIn);

        qlGaTau.setVisible(isAdmin);
        tkGaTau.setVisible(isLoggedIn);

        qlTuyenDuong.setVisible(isAdmin);
        tkTuyenDuong.setVisible(isLoggedIn);

        thongTinCaNhan.setVisible(isLoggedIn);
        doiMatKhau.setVisible(isLoggedIn);
        dangXuat.setVisible(isLoggedIn);
    }

    public void addTrangChuListener(ActionListener listener) {
        trangChu.addActionListener(listener);
    }

    public void addNhanVienListener(ActionListener qlListener, ActionListener tkListener) {
        qlNhanVien.addActionListener(qlListener);
        tkNhanVien.addActionListener(tkListener);
    }

    public void addTauListener(ActionListener qlListener, ActionListener tkListener) {
        qlTau.addActionListener(qlListener);
        tkTau.addActionListener(tkListener);
    }

    public void addLoaiToaListener(ActionListener qlListener, ActionListener tkListener) {
        qlLoaiToa.addActionListener(qlListener);
        tkLoaiToa.addActionListener(tkListener);
    }

    public void addToaTauListener(ActionListener qlListener, ActionListener tkListener) {
        qlToaTau.addActionListener(qlListener);
        tkToaTau.addActionListener(tkListener);
    }

    public void addGheListener(ActionListener qlListener, ActionListener tkListener) {
        qlGhe.addActionListener(qlListener);
        tkGhe.addActionListener(tkListener);
    }

    public void addGaTauListener(ActionListener qlListener, ActionListener tkListener) {
        qlGaTau.addActionListener(qlListener);
        tkGaTau.addActionListener(tkListener);
    }

    public void addTuyenDuongListener(ActionListener qlListener, ActionListener tkListener) {
        qlTuyenDuong.addActionListener(qlListener);
        tkTuyenDuong.addActionListener(tkListener);
    }

    public void addLichTrinhListener(ActionListener qlListener, ActionListener tkListener) {
        qlLichTrinh.addActionListener(qlListener);
        tkLichTrinh.addActionListener(tkListener);
    }

    public void addKhachHangListener(ActionListener qlListener, ActionListener tkListener) {
        qlKhachHang.addActionListener(qlListener);
        tkKhachHang.addActionListener(tkListener);
    }

    public void addVeTauListener(ActionListener qlListener, ActionListener tkListener) {
        qlVeTau.addActionListener(qlListener);
        tkVeTau.addActionListener(tkListener);
    }

    public void addThongKeDoanhThuNgayListener(ActionListener listener) {
        thongKeDoanhThuNgay.addActionListener(listener);
    }

    public void addThongKeDoanhThuTuyenListener(ActionListener listener) {
        thongKeDoanhThuTuyen.addActionListener(listener);
    }

    public void addThongKeTyLeLapDayListener(ActionListener listener) {
        thongKeTyLeLapDay.addActionListener(listener);
    }

    public void addThongKeKhachVIPListener(ActionListener listener) {
        thongKeKhachVIP.addActionListener(listener);
    }

    public void addThongKeDoanhSoListener(ActionListener listener) {
        thongKeDoanhSo.addActionListener(listener);
    }

    public void addThongTinCaNhanListener(ActionListener listener) {
        thongTinCaNhan.addActionListener(listener);
    }

    public void addDoiMatKhauListener(ActionListener listener) {
        doiMatKhau.addActionListener(listener);
    }

    public void addDangXuatListener(ActionListener listener) {
        dangXuat.addActionListener(listener);
    }

    public void addThoatListener(ActionListener listener) {
        thoat.addActionListener(listener);
    }

    public void addWindowCloseListener(WindowListener listener) {
        addWindowListener(listener);
    }
}
