package com.npmtt.ticketclient.controller.lichtrinh;


import com.npmtt.ticketclient.apiclient.LichTrinhApiClient;
import com.npmtt.ticketclient.apiclient.TauApiClient;
import com.npmtt.ticketclient.apiclient.TuyenDuongApiclient;
import com.npmtt.ticketclient.dto.response.LichTrinhResponse;
import com.npmtt.ticketclient.dto.response.TuyenDuongResponse;
import com.npmtt.ticketclient.dto.tau.TauDTO;
import com.npmtt.ticketclient.view.lichtrinh.TKLichTrinhPanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TKLichTrinhController {

    private final TKLichTrinhPanel panel;
    private final LichTrinhApiClient lichTrinhDAO;
    private final TauApiClient tauDAO;
    private final TuyenDuongApiclient tuyenDuongDAO;
    private final DefaultTableModel tableModel;

    private List<TauDTO> listTauCache;
    private List<TuyenDuongResponse> listTuyenCache;

    public TKLichTrinhController(TKLichTrinhPanel panel) {
        this.panel = panel;
        this.lichTrinhDAO = new LichTrinhApiClient();
        this.tauDAO = new TauApiClient();
        this.tuyenDuongDAO = new TuyenDuongApiclient();
        this.tableModel = (DefaultTableModel) panel.getTable().getModel();

        loadComboBoxData();

        refresh();

        panel.addTimKiemListener(new TimKiemListener());
        panel.addResetFormListener(e -> panel.resetForm());
        panel.addLamMoiListener(e -> refresh());
    }

    private void loadComboBoxData() {
        try {
            listTauCache = tauDAO.getAllTau();
            panel.getBoxTau().removeAllItems();
            panel.getBoxTau().addItem("Tất cả");
            for (TauDTO t : listTauCache) {
                panel.getBoxTau().addItem(t);
            }

            listTuyenCache = tuyenDuongDAO.getAllTuyenDuong();
            panel.getBoxTuyenDuong().removeAllItems();
            panel.getBoxTuyenDuong().addItem("Tất cả");
            for (TuyenDuongResponse td : listTuyenCache) {
                panel.getBoxTuyenDuong().addItem(td);
            }
        } catch (Exception e) {
            e.printStackTrace();
            panel.showError("Lỗi tải dữ liệu Tàu/Tuyến: " + e.getMessage());
        }
    }

    private void refresh() {
        try {
            tableModel.setRowCount(0);
            List<LichTrinhResponse> list = lichTrinhDAO.getAllLichTrinh();
            updateTable(list);
        } catch (Exception e) {
            e.printStackTrace();
            panel.showError("Lỗi hệ thống: " + e.getMessage());
        }
    }

    private void updateTable(List<LichTrinhResponse> list) {
        tableModel.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (LichTrinhResponse lt : list) {
            String tenTau = getTenTauById(lt.getIdTau());
            String tenTuyen = getTenTuyenById(lt.getIdTuyenDuong());

            tableModel.addRow(new Object[]{
                    lt.getId(),
                    lt.getMaLichTrinh(),
                    tenTau,
                    tenTuyen,
                    (lt.getNgayDi() != null) ? lt.getNgayDi() : "",
                    (lt.getNgayDen() != null) ? lt.getNgayDen() : "",
                    lt.getTrangThai()
            });
        }
        tableModel.fireTableDataChanged();
    }

    private String getTenTauById(int id) {
        if (listTauCache == null) return String.valueOf(id);
        for (TauDTO t : listTauCache) {
            if (t.getId() == id) return t.getTenTau();
        }
        return String.valueOf(id);
    }

    private String getTenTuyenById(int id) {
        if (listTuyenCache == null) return String.valueOf(id);
        for (TuyenDuongResponse td : listTuyenCache) {
            if (td.getId() == id) return td.getTenTuyen();
        }
        return String.valueOf(id);
    }

    private class TimKiemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String tuKhoa = panel.getTuKhoa();

                int idTau = 0;
                Object selectedTau = panel.getBoxTau().getSelectedItem();
                if (selectedTau instanceof TauDTO) {
                    idTau = ((TauDTO) selectedTau).getId();
                }

                int idTuyen = 0;
                Object selectedTuyen = panel.getBoxTuyenDuong().getSelectedItem();
                if (selectedTuyen instanceof TuyenDuongResponse) {
                    idTuyen = ((TuyenDuongResponse) selectedTuyen).getId();
                }

                String trangThai = panel.getTrangThai();

                List<LichTrinhResponse> ketQua = lichTrinhDAO.searchLichTrinh(tuKhoa);
                updateTable(ketQua);

                if (ketQua.isEmpty()) {
                    panel.showMessage("Không tìm thấy kết quả phù hợp!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi tìm kiếm: " + ex.getMessage());
            }
        }
    }
}