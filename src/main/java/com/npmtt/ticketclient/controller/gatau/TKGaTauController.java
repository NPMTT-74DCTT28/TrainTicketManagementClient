package com.npmtt.ticketclient.controller.gatau;

import com.npmtt.ticketclient.apiclient.GaTauApiClient;
import com.npmtt.ticketclient.dto.gatau.GaTauDTO;
import com.npmtt.ticketclient.view.gatau.TKGaTauPanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TKGaTauController {
    private final TKGaTauPanel panel;
    private final GaTauApiClient dao;
    private final DefaultTableModel model;

    public TKGaTauController(TKGaTauPanel panel) {
        this.panel = panel;
        this.dao = new GaTauApiClient();
        panel.TimKiemListener(new TimkiemListener());
        panel.ResetListener(new ResetListener());
        this.model = (DefaultTableModel) panel.getTable().getModel();
        refreshTable();
    }

    private void refreshTable() {
        try {
            model.setRowCount(0);
            List<GaTauDTO> List = dao.getAllGaTau();
            for (GaTauDTO gaTau : List) {
                model.addRow(new Object[]{
                        gaTau.getId(),
                        gaTau.getMaGa(),
                        gaTau.getTenGa(),
                        gaTau.getDiaChi(),
                        gaTau.getThanhPho()
                });
            }
            model.fireTableDataChanged();
        } catch (Exception ex) {
            ex.printStackTrace();
            panel.showError("Lỗi không xác định: " + ex.getMessage());
        }
    }

    public class TimkiemListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {

                model.setRowCount(0);
                String timKiem = panel.getTimkiem();
                List<GaTauDTO> List = dao.searchGaTau(timKiem);

                if (timKiem.isEmpty()) {
                    refreshTable();
                    return;
                }

                for (GaTauDTO gaTau : List) {
                    model.addRow(new Object[]{
                            gaTau.getId(),
                            gaTau.getMaGa(),
                            gaTau.getTenGa(),
                            gaTau.getDiaChi(),
                            gaTau.getThanhPho()
                    });
                }
                model.fireTableDataChanged();
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi không xác định: " + ex.getMessage());
            }
        }
    }

    public class ResetListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            refreshTable();
        }
    }
}
