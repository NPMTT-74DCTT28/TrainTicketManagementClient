package com.npmtt.ticketclient.controller.core;

import com.npmtt.ticketclient.controller.gatau.QLGaTauController;
import com.npmtt.ticketclient.controller.gatau.TKGaTauController;
import com.npmtt.ticketclient.controller.lichtrinh.QLLichTrinhController;
import com.npmtt.ticketclient.controller.lichtrinh.TKLichTrinhController;
import com.npmtt.ticketclient.controller.nhanvien.QLNhanVienController;
import com.npmtt.ticketclient.controller.nhanvien.TKNhanVienController;
import com.npmtt.ticketclient.controller.thongke.*;
import com.npmtt.ticketclient.controller.toatau.QLToaTauController;
import com.npmtt.ticketclient.controller.toatau.TKToaTauController;
import com.npmtt.ticketclient.controller.tuyenduong.QLTuyenDuongController;
import com.npmtt.ticketclient.controller.tuyenduong.TKTuyenDuongController;
import com.npmtt.ticketclient.enums.VaiTro;
import com.npmtt.ticketclient.util.SessionManager;
import com.npmtt.ticketclient.view.core.Dashboard;
import com.npmtt.ticketclient.view.core.MainFrame;
import com.npmtt.ticketclient.view.gatau.QLGaTauPanel;
import com.npmtt.ticketclient.view.gatau.TKGaTauPanel;
import com.npmtt.ticketclient.view.lichtrinh.QLLichTrinhPanel;
import com.npmtt.ticketclient.view.lichtrinh.TKLichTrinhPanel;
import com.npmtt.ticketclient.view.nhanvien.QLNhanVienPanel;
import com.npmtt.ticketclient.view.nhanvien.TKNhanVienPanel;
import com.npmtt.ticketclient.view.thongke.*;
import com.npmtt.ticketclient.view.toatau.QLToaTauPanel;
import com.npmtt.ticketclient.view.toatau.TKToaTauPanel;
import com.npmtt.ticketclient.view.tuyenduong.QLTuyenDuongPanel;
import com.npmtt.ticketclient.view.tuyenduong.TKTuyenDuongPanel;

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

        if (SessionManager.getCurrentUser().getToken().isEmpty()) {
            mainFrame.showWarning("Bạn chưa đăng nhập, vui lòng đăng nhập để sử dụng.");
            mainFrame.dispose();
            new DangNhapController();
            return;
        }
        mainFrame.setXinChao(SessionManager.getCurrentUser().getHoTen());
        String vaiTroString = SessionManager.getCurrentUser().getVaiTro().toString();
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
        new DashboardController(dashboard);
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
            QLToaTauPanel panel = new QLToaTauPanel();
            mainFrame.showPanel(panel);
            new QLToaTauController(panel);
        }
    }

    private class TKToaTauListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TKToaTauPanel panel = new TKToaTauPanel();
            mainFrame.showPanel(panel);
            new TKToaTauController(panel);
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
            QLGaTauPanel panel = new QLGaTauPanel();
            mainFrame.showPanel(panel);
            new QLGaTauController(panel);
        }
    }

    private class TKGaTauListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TKGaTauPanel panel = new TKGaTauPanel();
            mainFrame.showPanel(panel);
            new TKGaTauController(panel);
        }
    }

    private class QLTuyenDuongListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            QLTuyenDuongPanel panel = new QLTuyenDuongPanel();
            mainFrame.showPanel(panel);
            new QLTuyenDuongController(panel);
        }
    }

    private class TKTuyenDuongListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TKTuyenDuongPanel panel = new TKTuyenDuongPanel();
            mainFrame.showPanel(panel);
            new TKTuyenDuongController(panel);
        }
    }

    private class QLLichTrinhListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            QLLichTrinhPanel panel = new QLLichTrinhPanel();
            mainFrame.showPanel(panel);
            new QLLichTrinhController(panel);
        }
    }

    private class TKLichTrinhListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TKLichTrinhPanel panel = new TKLichTrinhPanel();
            mainFrame.showPanel(panel);
            new TKLichTrinhController(panel);
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
            TabDoanhThuTheoTuyen tab = new TabDoanhThuTheoTuyen();
            mainFrame.showPanel(tab);
            new DoanhThuTheoTuyenController(tab);
        }
    }

    private class ThongKeTyLeLapDayListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TabTyLeLapDay tab = new TabTyLeLapDay();
            mainFrame.showPanel(tab);
            new TyLeLapDayController(tab);
        }
    }

    private class ThongKeKhachHangVIPListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TabKhachHangVip tab = new TabKhachHangVip();
            mainFrame.showPanel(tab);
            new KhachHangVipController(tab);
        }
    }

    private class ThongKeDoanhSoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TabDoanhSo tab = new TabDoanhSo();
            mainFrame.showPanel(tab);
            new DoanhSoController(tab);
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
                SessionManager.setCurrentUser(null);
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
