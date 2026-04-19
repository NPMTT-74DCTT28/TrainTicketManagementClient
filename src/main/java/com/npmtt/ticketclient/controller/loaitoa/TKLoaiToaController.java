package com.npmtt.ticketclient.controller.loaitoa;

import com.npmtt.ticketclient.apiclient.LoaiToaApiClient;
import com.npmtt.ticketclient.dto.loaitoa.LoaiToaDTO;
import com.npmtt.ticketclient.view.loaitoa.TKLoaiToaPanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;

public class TKLoaiToaController {

    private final TKLoaiToaPanel panel;
    private final LoaiToaApiClient dao;
    private DefaultTableModel tableModel;

    public TKLoaiToaController(TKLoaiToaPanel panel) {
        this.panel = panel;
        this.dao = new LoaiToaApiClient();

        panel.addTimKiemListener(new TimKiemListener());
        panel.addResetFormListener(new ResetFormListener());
        panel.addLamMoiListener(new LamMoiListener());

        this.tableModel = (DefaultTableModel) panel.getTable().getModel();

        refresh();
    }

    private void refresh() {
        try {
            tableModel.setRowCount(0);
            List<LoaiToaDTO> list = dao.getAllLoaiToa();
            for (LoaiToaDTO loaiToa : list) {
                tableModel.addRow(new Object[]{
                        loaiToa.getId(),
                        loaiToa.getTenLoai(),
                        loaiToa.getHeSoGia()
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
                List<LoaiToaDTO> list = dao.searchLoaiToa(tuKhoa);

                for (LoaiToaDTO loaiToa : list) {
                    tableModel.addRow(new Object[]{
                            loaiToa.getId(),
                            loaiToa.getTenLoai(),
                            loaiToa.getHeSoGia()
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
