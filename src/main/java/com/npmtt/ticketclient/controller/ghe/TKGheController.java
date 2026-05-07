package com.npmtt.ticketclient.controller.ghe;

import com.npmtt.ticketclient.apiclient.GheApiClient;
import com.npmtt.ticketclient.dto.response.GheResponse;
import com.npmtt.ticketclient.view.ghe.TKGhePanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TKGheController {

    private final TKGhePanel panel;
    private final GheApiClient apiClient;
    private DefaultTableModel tableModel;

    public TKGheController(TKGhePanel panel) {
        this.panel = panel;
        this.apiClient = new GheApiClient();

        panel.addTimKiemListener(new TimKiemListener());
        panel.addResetFormListener(new ResetFormListener());
        panel.addLamMoiListener(new LamMoiListener());

        this.tableModel = (DefaultTableModel) panel.getTable().getModel();

        refresh();
    }

    private void refresh() {
        try {
            tableModel.setRowCount(0);
            List<GheResponse> list = apiClient.getAllGhe();
            System.out.println(list.get(0));
            for (GheResponse ghe : list) {
                tableModel.addRow(new Object[]{
                        ghe.getId(),
                        ghe.getSoGhe(),
                        ghe.getIdToaTau()
                });
            }

            tableModel.fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
            panel.showError( e.getMessage());
        }
    }

    private class TimKiemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                tableModel.setRowCount(0);
                String tuKhoa = panel.getTuKhoa();
                List<GheResponse> list = apiClient.searchGhe(tuKhoa);

                for (GheResponse ghe : list) {
                    tableModel.addRow(new Object[]{
                            ghe.getId(),
                            ghe.getSoGhe(),
                            ghe.getIdToaTau()
                    });
                }

                tableModel.fireTableDataChanged();
            }catch (Exception ex) {
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