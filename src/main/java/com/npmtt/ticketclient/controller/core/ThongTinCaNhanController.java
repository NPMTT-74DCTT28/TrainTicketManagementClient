package com.npmtt.ticketclient.controller.core;

import com.npmtt.ticketclient.apiclient.NhanVienApiClient;
import com.npmtt.ticketclient.dto.request.NhanVienRequest;
import com.npmtt.ticketclient.dto.response.NhanVienResponse;
import com.npmtt.ticketclient.util.SessionManager;
import com.npmtt.ticketclient.view.core.MainFrame;
import com.npmtt.ticketclient.view.core.ThongTinCaNhanDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class ThongTinCaNhanController {

    private final MainFrame parent;
    private final ThongTinCaNhanDialog dialog;
    private final NhanVienResponse currentUser;
    private final NhanVienApiClient apiClient;

    public ThongTinCaNhanController(MainFrame parent) {
        this.parent = parent;
        this.dialog = new ThongTinCaNhanDialog(this.parent);
        this.apiClient = NhanVienApiClient.getInstance();
        this.currentUser = SessionManager.getCurrentUser();

        loadThongTin();

        this.dialog.addXacNhanListener(new XacNhanListener());
        this.dialog.addQuayLaiListener(new QuayLaiListener());

        this.dialog.setVisible(true);
    }

    private void loadThongTin() {
        if (currentUser != null) {
            dialog.setHoTen(currentUser.getHoTen());
            dialog.setNgaySinh(LocalDate.parse(currentUser.getNgaySinh()));
            dialog.setGioiTinh(currentUser.getGioiTinh());
            dialog.setSdt(currentUser.getSdt());
            dialog.setEmail(currentUser.getEmail());
            dialog.setDiaChi(currentUser.getDiaChi());
        }
    }

    private class XacNhanListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (dialog.thongBaoLoiDauVao() != null) {
                    dialog.showWarning(dialog.thongBaoLoiDauVao());
                    return;
                }

                currentUser.setHoTen(dialog.getHoTen());
                currentUser.setNgaySinh(String.valueOf(dialog.getNgaySinh()));
                currentUser.setGioiTinh(dialog.getGioiTinh());
                currentUser.setSdt(dialog.getSdt());
                currentUser.setEmail(dialog.getEmail());
                currentUser.setDiaChi(dialog.getDiaChi());

                NhanVienRequest request = NhanVienRequest.builder()
                        .id(currentUser.getId())
                        .hoTen(currentUser.getHoTen())
                        .ngaySinh(currentUser.getNgaySinh())
                        .gioiTinh(currentUser.getGioiTinh())
                        .sdt(currentUser.getSdt())
                        .email(currentUser.getEmail())
                        .diaChi(currentUser.getDiaChi())
                        .vaiTro(currentUser.getVaiTro())
                        .build();

                NhanVienResponse result = apiClient.updateNhanVien(request);
                if (result == null) return;
                parent.showMessage("Cập nhật thông tin cá nhân thành công!");
                dialog.dispose();
                SessionManager.startSession(result);
            } catch (Exception ex) {
                ex.printStackTrace();
                parent.showError(ex.getMessage());
            }
        }
    }

    private class QuayLaiListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (parent != null) {
                dialog.dispose();
            }
        }
    }
}
