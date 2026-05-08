package com.npmtt.ticketclient.controller.khachhang;

import com.npmtt.ticketclient.apiclient.KhachHangApiClient;
import com.npmtt.ticketclient.dto.response.KhachHangResponse;
import com.npmtt.ticketclient.view.khachhang.TKKhachHangPanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TKKhachHangController {
    private final TKKhachHangPanel panel;
    private final KhachHangApiClient apiClient;
    private final DefaultTableModel tableModel;

    public TKKhachHangController(TKKhachHangPanel panel) {
        this.panel = panel;
        this.apiClient = KhachHangApiClient.getInstance();

        panel.addTimKiemListener(new TimKiemListener());
        panel.addResetFormListener(new ResetFormListener());
        panel.addLamMoiListener(new LamMoiListener());

        this.tableModel = (DefaultTableModel) panel.getTable().getModel();

        refresh();
    }

    private void refresh() {
        try {
            List<KhachHangResponse> allKhachHang = apiClient.getAllKhachHang();
            tableModel.setRowCount(0);
            for (KhachHangResponse khachHang : allKhachHang) {
                tableModel.addRow(new Object[]{
                        khachHang.getId(),
                        khachHang.getCccd(),
                        khachHang.getHoTen(),
                        khachHang.getNgaySinh(),
                        khachHang.getSdt(),
                        khachHang.getGioiTinh(),
                        khachHang.getDiaChi(),
                });
            }
            tableModel.fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
            panel.showError(e.getMessage());
        }
    }

    private class TimKiemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String keyword = panel.getTuKhoa();
                String gioiTinh = panel.getGioiTinh();
                List<KhachHangResponse> ketQua = apiClient.searchKhachHang(keyword, gioiTinh);
                tableModel.setRowCount(0);
                for (KhachHangResponse khachHang : ketQua) {
                    tableModel.addRow(new Object[]{
                            khachHang.getId(),
                            khachHang.getCccd(),
                            khachHang.getHoTen(),
                            khachHang.getNgaySinh(),
                            khachHang.getSdt(),
                            khachHang.getGioiTinh(),
                            khachHang.getDiaChi(),
                    });
                }
                tableModel.fireTableDataChanged();
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError(ex.getMessage());
            }
        }
    }

    private class ResetFormListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            panel.resetForm();
        }
    }

    private class LamMoiListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            refresh();
        }
    }
}
