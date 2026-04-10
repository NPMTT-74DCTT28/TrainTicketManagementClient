package com.npmtt.ticketclient.controller.core;

import com.npmtt.ticketclient.apiclient.AuthApiClient;
import com.npmtt.ticketclient.dto.request.LoginRequest;
import com.npmtt.ticketclient.dto.response.NhanVienResponse;
import com.npmtt.ticketclient.util.SessionManager;
import com.npmtt.ticketclient.view.core.DangNhapFrame;
import com.npmtt.ticketclient.view.core.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DangNhapController {

    private final AuthApiClient apiClient;

    private final DangNhapFrame frame;

    public DangNhapController() {
        this.apiClient = AuthApiClient.getInstance();

        frame = new DangNhapFrame();
        frame.addLoginListener(new LoginListener());
        frame.addExitListener(new ExitListener());
        frame.addWindowCloseListener(new WindowCloseListener());

        frame.setVisible(true);
    }

    static void main(String[] args) {
        new DangNhapController();
    }

    private void exit() {
        if (frame.showConfirm("Bạn chắc chắn muốn thoát ứng dụng?")) {
            frame.dispose();
            System.exit(0);
        }
    }

    private class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String maNhanVien = frame.getMaNV();
                String matKhau = frame.getMatKhau();

                if (maNhanVien.isEmpty() || matKhau.isEmpty()) {
                    frame.showWarning("Vui lòng nhập đủ mã nhân viên và mật khẩu!");
                    return;
                }

                LoginRequest request = LoginRequest.builder()
                        .maNhanVien(maNhanVien)
                        .matKhau(matKhau)
                        .build();
                NhanVienResponse user = apiClient.login(request);
                SessionManager.startSession(user);
                frame.dispose();
                new MainController(new MainFrame());
            } catch (Exception ex) {
                ex.printStackTrace();
                frame.showError(ex.getMessage());
            }
        }
    }

    private class ExitListener implements ActionListener {
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
