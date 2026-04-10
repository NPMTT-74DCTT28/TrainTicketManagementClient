package com.npmtt.ticketclient.controller.core;

import com.npmtt.ticketclient.apiclient.AuthApiClient;
import com.npmtt.ticketclient.dto.request.ChangePasswordRequest;
import com.npmtt.ticketclient.util.SessionManager;
import com.npmtt.ticketclient.view.core.DoitMatKhauDialog;
import com.npmtt.ticketclient.view.core.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DoiMatKhauController {

    private final MainFrame parent;
    private final DoitMatKhauDialog dialog;
    private final AuthApiClient authApiClient;

    public DoiMatKhauController(MainFrame parent) {
        this.parent = parent;
        this.dialog = new DoitMatKhauDialog(this.parent);
        this.authApiClient = AuthApiClient.getInstance();

        dialog.addXacNhanListener(new XacNhanListener());
        dialog.addHuyListener(new HuyListener());

        dialog.setVisible(true);
    }

    private class XacNhanListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (dialog.thongBaoLoiDauVao() != null) {
                    parent.showWarning(dialog.thongBaoLoiDauVao());
                    return;
                }

                int id = SessionManager.getCurrentUser().getId();
                String oldPassword = dialog.getMatKhauCu();
                String newPassword = dialog.getMatKhauMoi();
                ChangePasswordRequest request = ChangePasswordRequest.builder()
                        .id(id)
                        .oldPassword(oldPassword)
                        .newPassword(newPassword)
                        .build();
                authApiClient.changePassword(request);
                parent.showMessage("Đổi mật khẩu thành công, vui lòng đăng nhập lại!");
                SessionManager.clearSession();
                dialog.dispose();
                parent.dispose();
                new DangNhapController();
            } catch (Exception ex) {
                ex.printStackTrace();
                parent.showError(ex.getMessage());
            }
        }
    }

    private class HuyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dialog.dispose();
            parent.setVisible(true);
        }
    }
}
