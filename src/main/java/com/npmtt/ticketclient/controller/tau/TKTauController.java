package com.npmtt.ticketclient.controller.tau;

import com.npmtt.ticketclient.apiclient.TauApiClient;
import com.npmtt.ticketclient.dto.tau.TauDTO;
import com.npmtt.ticketclient.view.tau.TKTauPanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TKTauController {

    private final TKTauPanel panel;
    private final TauApiClient dao;
    private final DefaultTableModel tableModel;

    public TKTauController(TKTauPanel panel) {
        this.panel = panel;
        this.dao = new TauApiClient();

        panel.addTimKiemListener(new TimKiemListener());
        panel.addResetFormListener(new ResetFormListener());
        panel.addLamMoiListener(new LamMoiListener());

        this.tableModel = (DefaultTableModel) panel.getTable().getModel();

        refresh();
    }

    private void refresh() {
        try {
            tableModel.setRowCount(0);
            List<TauDTO> list = dao.getAllTau();
            for (TauDTO tau : list) {
                tableModel.addRow(new Object[]{
                        tau.getId(),
                        tau.getMaTau(),
                        tau.getTenTau()
                });
            }

            tableModel.fireTableDataChanged();
        } catch (RuntimeException e) {
            e.printStackTrace();
            panel.showError("Lỗi hệ thống: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            panel.showError("Lỗi không xác định: " + e.getMessage());
        }
    }

    private class TimKiemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                tableModel.setRowCount(0);
                String tuKhoa = panel.getTuKhoa();
                List<TauDTO> list = dao.searchTau(tuKhoa);

                for (TauDTO tau : list) {
                    tableModel.addRow(new Object[]{
                            tau.getId(),
                            tau.getMaTau(),
                            tau.getTenTau()
                    });
                }

                tableModel.fireTableDataChanged();
            } catch (RuntimeException ex) {
                ex.printStackTrace();
                panel.showError("Lỗi hệ thống: " + ex.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi không xác định: " + ex.getMessage());
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
