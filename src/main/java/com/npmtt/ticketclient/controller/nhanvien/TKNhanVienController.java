package com.npmtt.ticketclient.controller.nhanvien;

import com.npmtt.ticketclient.apiclient.NhanVienApiClient;
import com.npmtt.ticketclient.dto.response.NhanVienResponse;
import com.npmtt.ticketclient.view.nhanvien.TKNhanVienPanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TKNhanVienController {

    private final TKNhanVienPanel panel;
    private final NhanVienApiClient apiClient;
    private final DefaultTableModel tableModel;

    public TKNhanVienController(TKNhanVienPanel panel) {
        this.panel = panel;
        this.apiClient = NhanVienApiClient.getInstance();

        panel.addTimKiemListener(new TimKiemListener());
        panel.addResetFormListener(new ResetFormListener());
        panel.addLamMoiListener(new LamMoiListener());

        this.tableModel = (DefaultTableModel) panel.getTable().getModel();

        refresh();
    }

    private void refresh() {
        try {
            List<NhanVienResponse> allNhanVien = apiClient.getAllNhanVien();
            tableModel.setRowCount(0);
            for (NhanVienResponse nhanVien : allNhanVien) {
                tableModel.addRow(new Object[]{
                        nhanVien.getId(),
                        nhanVien.getMaNhanVien(),
                        nhanVien.getHoTen(),
                        nhanVien.getNgaySinh(),
                        nhanVien.getGioiTinh(),
                        nhanVien.getSdt(),
                        nhanVien.getEmail(),
                        nhanVien.getDiaChi(),
                        nhanVien.getVaiTro()
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
            //TODO: Gọi API để tìm kiếm thông tin nhân viên và load lại bảng
            try {
                String keyword = panel.getTuKhoa();
                String gioiTinh = panel.getGioiTinh();
                String vaiTro = panel.getVaiTro();
                List<NhanVienResponse> ketQua = apiClient.searchNhanVien(keyword, gioiTinh, vaiTro);
                tableModel.setRowCount(0);
                for (NhanVienResponse nhanVien : ketQua) {
                    tableModel.addRow(new Object[]{
                            nhanVien.getId(),
                            nhanVien.getMaNhanVien(),
                            nhanVien.getHoTen(),
                            nhanVien.getNgaySinh(),
                            nhanVien.getGioiTinh(),
                            nhanVien.getSdt(),
                            nhanVien.getEmail(),
                            nhanVien.getDiaChi(),
                            nhanVien.getVaiTro()
                    });
                }
                tableModel.fireTableDataChanged();
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError(ex.getMessage());
                refresh();
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
