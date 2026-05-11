package com.npmtt.ticketclient.controller.tuyenduong;


import com.npmtt.ticketclient.apiclient.GaTauApiClient;
import com.npmtt.ticketclient.apiclient.TuyenDuongApiclient;
import com.npmtt.ticketclient.dto.gatau.GaTauDTO;
import com.npmtt.ticketclient.dto.response.TuyenDuongResponse;
import com.npmtt.ticketclient.view.tuyenduong.TKTuyenDuongPanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

public class TKTuyenDuongController {
    private final TKTuyenDuongPanel panel;
    private final TuyenDuongApiclient dao;
    private final DefaultTableModel model;
    private final HashMap<Integer, String> map = new HashMap<>();

    public TKTuyenDuongController(TKTuyenDuongPanel panel) {
        this.panel = panel;
        this.dao = new TuyenDuongApiclient();
        panel.TimKiemListener(new TimkiemListener());
        panel.ResetListener(new ResetListener());
        this.model = (DefaultTableModel) panel.getTable().getModel();
        loadGa();
        refreshTable();
    }

    private void loadGa() {
        try {
            GaTauApiClient dao = new GaTauApiClient();
            map.clear();
            for (GaTauDTO gaTau : dao.getAllGaTau()) {
                map.put(
                        gaTau.getId(),
                        gaTau.getMaGa() + " - " + gaTau.getTenGa()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshTable() {
        try {
            model.setRowCount(0);
            List<TuyenDuongResponse> list = dao.getAllTuyenDuong();

            for (TuyenDuongResponse tuyenDuong : list) {
                String gaDi = map.getOrDefault(
                        tuyenDuong.getIdGaDi(),
                        String.valueOf(tuyenDuong.getIdGaDi())
                );
                String gaDen = map.getOrDefault(
                        tuyenDuong.getIdGaDen(),
                        String.valueOf(tuyenDuong.getIdGaDen())
                );
                model.addRow(new Object[]{
                        tuyenDuong.getMaTuyen(),
                        tuyenDuong.getTenTuyen(),
                        gaDi,
                        gaDen,
                        tuyenDuong.getKhoangCachKm(),
                        tuyenDuong.getGiaCoBan(),

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
                String timkiem = panel.getTimkiem();
                if (timkiem.isEmpty()) {
                    refreshTable();
                    return;
                }

                List<TuyenDuongResponse> list = dao.searchTuyenDuong(timkiem);

                for (TuyenDuongResponse td : list) {
                    String gaDi = map.getOrDefault(
                            td.getIdGaDi(),
                            String.valueOf(td.getIdGaDi())
                    );
                    String gaDen = map.getOrDefault(
                            td.getIdGaDen(),
                            String.valueOf(td.getIdGaDen())
                    );

                    model.addRow(new Object[]{
                            td.getMaTuyen(),
                            td.getTenTuyen(),
                            gaDi,
                            gaDen,
                            td.getKhoangCachKm(),
                            td.getGiaCoBan()
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
            refreshTable();
        }
    }
}
