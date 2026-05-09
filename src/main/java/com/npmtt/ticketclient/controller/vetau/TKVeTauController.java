package com.npmtt.ticketclient.controller.vetau;

import com.npmtt.ticketclient.apiclient.*;
import com.npmtt.ticketclient.dto.response.*;
import com.npmtt.ticketclient.view.vetau.TKVeTauPanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

public class TKVeTauController {
    private final TKVeTauPanel panel;
    private final VeTauApiClient dao;
    private final DefaultTableModel model;
    private final HashMap<Integer, String> mapKH = new HashMap<>();
    private final HashMap<Integer, String> mapLT = new HashMap<>();
    private final HashMap<Integer, String> mapG = new HashMap<>();
    private final HashMap<Integer, String> mapNV = new HashMap<>();

    public TKVeTauController(TKVeTauPanel panel) {
        this.panel = panel;
        this.dao = new VeTauApiClient();
        panel.addTimKiemListener(new TimkiemListener());
        panel.addResetFormListener(new ResetListener());
        panel.addLamMoiListener(new RefreshListener());
        this.model = (DefaultTableModel) panel.getTable().getModel();
        loadKhachHang();
        loadLichTrinh();
        loadGhe();
        loadNhanVien();
        refreshTable();
    }

    private void loadKhachHang() {
        try {
            KhachHangApiClient dao = new KhachHangApiClient();
            mapKH.clear();
            for (KhachHangResponse khachHang : dao.getAllKhachHang()) {
                mapKH.put(
                        khachHang.getId(),
                        khachHang.getHoTen());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadLichTrinh() {
        try {
            LichTrinhApiClient dao = new LichTrinhApiClient();
            mapLT.clear();
            for (LichTrinhResponse lichTrinh : dao.getAllLichTrinh()) {
                mapLT.put(
                        lichTrinh.getId(),
                        lichTrinh.getMaLichTrinh()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadGhe() {
        try {
            GheApiClient dao = new GheApiClient();
            mapG.clear();
            for (GheResponse ghe : dao.getAllGhe()) {
                mapG.put(
                        ghe.getId(),
                        ghe.getSoGhe()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadNhanVien() {
        try {
            NhanVienApiClient dao = new NhanVienApiClient();
            mapNV.clear();
            for (NhanVienResponse nhanVien : dao.getAllNhanVien()) {
                mapNV.put(
                        nhanVien.getId(),
                        nhanVien.getHoTen()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshTable() {
        try {
            model.setRowCount(0);
            List<VeTauResponse> list = dao.getAllVeTau();

            for (VeTauResponse v : list) {
                String tenKhachHang = mapKH.getOrDefault(
                        v.getIdKhachHang(),
                        String.valueOf(v.getIdKhachHang()));
                String tenLichTrinh = mapLT.getOrDefault(
                        v.getIdLichTrinh(),
                        String.valueOf(v.getIdLichTrinh()));
                String tenGhe = mapG.getOrDefault(
                        v.getIdGhe(),
                        String.valueOf(v.getIdGhe()));
                String tenNhanVien = mapNV.getOrDefault(
                        v.getIdNhanVien(),
                        String.valueOf(v.getIdNhanVien()));
                model.addRow(new Object[]{
                        v.getId(),
                        v.getMaVe(),
                        tenKhachHang,
                        tenLichTrinh,
                        tenGhe,
                        tenNhanVien,
                        v.getNgayDatVe(),
                        v.getGiaVe(),
                        v.getTrangThai()
                });
            }
            model.fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
            panel.showError("Lỗi không xác định: " + e.getMessage());
        }
    }

    private class TimkiemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                model.setRowCount(0);
                String maVe = panel.getTuKhoa();
                if (maVe.isEmpty()) {
                    refreshTable();
                    return;
                }

                List<VeTauResponse> list = dao.searchVeTau(maVe);

                for (VeTauResponse vt : list) {
                    String tenKhachHang = mapKH.getOrDefault(
                            vt.getIdKhachHang(),
                            String.valueOf(vt.getIdKhachHang())
                    );
                    String tenLichTrinh = mapLT.getOrDefault(
                            vt.getIdLichTrinh(),
                            String.valueOf(vt.getIdLichTrinh())
                    );
                    String tenGhe = mapG.getOrDefault(
                            vt.getIdGhe(),
                            String.valueOf(vt.getIdGhe())
                    );
                    String tenNhanVien = mapNV.getOrDefault(
                            vt.getIdNhanVien(),
                            String.valueOf(vt.getIdNhanVien())
                    );
                    model.addRow(new Object[]{
                            vt.getId(),
                            vt.getMaVe(),
                            tenKhachHang,
                            tenLichTrinh,
                            tenGhe,
                            tenNhanVien,
                            vt.getNgayDatVe(),
                            vt.getGiaVe(),
                            vt.getTrangThai()
                    });
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi tìm kiếm: " + ex.getMessage());
            }
        }
    }

    private class ResetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            panel.resetForm();
        }
    }

    private class RefreshListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            refreshTable();
        }
    }
}

