package com.npmtt.ticketclient.controller.core;

import com.npmtt.ticketclient.controller.nhanvien.QLNhanVienController;
import com.npmtt.ticketclient.controller.nhanvien.TKNhanVienController;
import com.npmtt.ticketclient.controller.thongke.DoanhThuTheoNgayController;
import com.npmtt.ticketclient.enums.VaiTro;
import com.npmtt.ticketclient.util.SessionManager;
import com.npmtt.ticketclient.view.core.Dashboard;
import com.npmtt.ticketclient.view.core.MainFrame;
import com.npmtt.ticketclient.view.nhanvien.QLNhanVienPanel;
import com.npmtt.ticketclient.view.nhanvien.TKNhanVienPanel;
import com.npmtt.ticketclient.view.thongke.TabDoanhThuTheoNgay;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainController {

    private final MainFrame mainFrame;

    public MainController(MainFrame frame) {
        this.mainFrame = frame;

        mainFrame.addTrangChuListener(new TrangChuListener());
        mainFrame.addNhanVienListener(new QLNhanVienListener(), new TKNhanVienListener());
        mainFrame.addTauListener(new QLTauListener(), new TKTauListener());
        mainFrame.addLoaiToaListener(new QLLoaiToaListener(), new TKLoaiToaListener());
        mainFrame.addToaTauListener(new QLToaTauListener(), new TKToaTauListener());
        mainFrame.addGheListener(new QLGheListener(), new TKGheListener());
        mainFrame.addGaTauListener(new QLGaTauListener(), new TKGaTauListener());
        mainFrame.addTuyenDuongListener(new QLTuyenDuongListener(), new TKTuyenDuongListener());
        mainFrame.addLichTrinhListener(new QLLichTrinhListener(), new TKLichTrinhListener());
        mainFrame.addKhachHangListener(new QLKhachHangListener(), new TKKhachHangListener());
        mainFrame.addVeTauListener(new QLVeTauListener(), new TKVeTauListener());

        if (SessionManager.getCurrentUser() == null) {
            mainFrame.showWarning("Bạn chưa đăng nhập, vui lòng đăng nhập để sử dụng.");
            mainFrame.dispose();
            new DangNhapController();
            return;
        }
        mainFrame.setXinChao(SessionManager.getCurrentUser().getHoTen());
        String vaiTroString = SessionManager.getCurrentUser().getVaiTro();
        VaiTro vaiTro = VaiTro.fromLabel(vaiTroString);
        mainFrame.hienMenuTheoQuyen(vaiTro);
        showTrangChu();

        mainFrame.addThongKeDoanhThuNgayListener(new ThongKeDoanhThuNgayListener());
        mainFrame.addThongKeDoanhThuTuyenListener(new ThongKeDoanhThuTuyenListener());
        mainFrame.addThongKeTyLeLapDayListener(new ThongKeTyLeLapDayListener());
        mainFrame.addThongKeKhachVIPListener(new ThongKeKhachHangVIPListener());
        mainFrame.addThongKeDoanhSoListener(new ThongKeDoanhSoListener());
        mainFrame.addThongTinCaNhanListener(new ThongTinCaNhanListener());
        mainFrame.addDoiMatKhauListener(new DoiMatKhauListener());
        mainFrame.addDangXuatListener(new DangXuatListener());
        mainFrame.addThoatListener(new ThoatListener());
        mainFrame.addWindowCloseListener(new WindowCloseListener());

        mainFrame.setVisible(true);
    }

    private void showTrangChu() {
        Dashboard dashboard = new Dashboard();
        mainFrame.showPanel(dashboard);
    }

    private void exit() {
        if (mainFrame.showConfirm("Bạn chắc chắn muốn thoát ứng dụng?")) {
            mainFrame.dispose();
            System.exit(0);
        }
    }

    private class TrangChuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            showTrangChu();
        }
    }

    private class QLNhanVienListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            QLNhanVienPanel qlNhanVienPanel = new QLNhanVienPanel();
            mainFrame.showPanel(qlNhanVienPanel);
            new QLNhanVienController(qlNhanVienPanel);
        }
    }

    private class TKNhanVienListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TKNhanVienPanel panel = new TKNhanVienPanel();
            mainFrame.showPanel(panel);
            new TKNhanVienController(panel);
        }
    }

    private class QLTauListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class TKTauListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class QLLoaiToaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class TKLoaiToaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class QLToaTauListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class TKToaTauListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class QLGheListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class TKGheListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class QLGaTauListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class TKGaTauListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class QLTuyenDuongListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class TKTuyenDuongListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class QLLichTrinhListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class TKLichTrinhListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class QLKhachHangListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class TKKhachHangListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class QLVeTauListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class TKVeTauListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class ThongKeDoanhThuNgayListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TabDoanhThuTheoNgay tab = new TabDoanhThuTheoNgay();
            mainFrame.showPanel(tab);
            new DoanhThuTheoNgayController(tab);
        }
    }

    private class ThongKeDoanhThuTuyenListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class ThongKeTyLeLapDayListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class ThongKeKhachHangVIPListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class ThongKeDoanhSoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class ThongTinCaNhanListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new ThongTinCaNhanController(mainFrame);
        }
    }

    private class DoiMatKhauListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new DoiMatKhauController(mainFrame);
        }
    }

    private class DangXuatListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (mainFrame.showConfirm("Bạn chắc chắn muốn đăng xuất?")) {
                mainFrame.dispose();
                SessionManager.clearSession();
                new DangNhapController();
            }
        }
    }

    private class ThoatListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            exit();
        }
    }

    private class WindowCloseListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            exit();
        }
    }
}
