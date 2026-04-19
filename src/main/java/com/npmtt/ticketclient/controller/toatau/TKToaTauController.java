package com.npmtt.ticketclient.controller.toatau;

import com.npmtt.ticketclient.apiclient.LoaiToaApiClient;
import com.npmtt.ticketclient.apiclient.TauApiClient;
import com.npmtt.ticketclient.apiclient.ToaTauApiClient;
import com.npmtt.ticketclient.dto.loaitoa.LoaiToaDTO;
import com.npmtt.ticketclient.dto.response.ToaTauResponse;
import com.npmtt.ticketclient.dto.tau.TauDTO;
import com.npmtt.ticketclient.view.toatau.TKToaTauPanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TKToaTauController {

    private final TKToaTauPanel panel;
    private final ToaTauApiClient toaTauApiClient;
    private final TauApiClient tauApiClient;
    private final LoaiToaApiClient loaiToaApiClient;
    private final DefaultTableModel tableModel;

    private Map<Integer, String> mapTau = new HashMap<>();
    private Map<Integer, String> mapLoaiToa = new HashMap<>();

    public TKToaTauController(TKToaTauPanel panel) {
        this.panel = panel;
        this.toaTauApiClient = new ToaTauApiClient();
        this.tauApiClient = new TauApiClient();
        this.loaiToaApiClient = new LoaiToaApiClient();
        this.tableModel = (DefaultTableModel) panel.getTable().getModel();

        loadComboBoxData();
        refresh();

        panel.addTimKiemListener(new TimKiemListener());
        panel.addResetFormListener(e -> panel.resetForm());
        panel.addLamMoiListener(e -> refresh());
    }

    private void loadComboBoxData() {
        try {
            // Tàu
            List<TauDTO> listTau = tauApiClient.getAllTau();
            panel.getBoxTau().removeAllItems();
            panel.getBoxTau().addItem("Tất cả");
            mapTau.clear();
            for (TauDTO t : listTau) {
                String display = t.getMaTau() + " - " + t.getTenTau();
                mapTau.put(t.getId(), display);
                panel.getBoxTau().addItem(new ComboItem(t.getId(), display));
            }

            // Loại toa
            List<LoaiToaDTO> listLoai = loaiToaApiClient.getAllLoaiToa();
            panel.getBoxLoaiToa().removeAllItems();
            panel.getBoxLoaiToa().addItem("Tất cả");
            mapLoaiToa.clear();
            for (LoaiToaDTO lt : listLoai) {
                String display = lt.getTenLoai() + " (HS: " + lt.getHeSoGia() + ")";
                mapLoaiToa.put(lt.getId(), display);
                panel.getBoxLoaiToa().addItem(new ComboItem(lt.getId(), display));
            }
        } catch (Exception e) {
            e.printStackTrace();
            panel.showError("Lỗi tải danh mục: " + e.getMessage());
        }
    }

    private void refresh() {
        try {
            List<ToaTauResponse> list = toaTauApiClient.getAllToaTau();
            updateTable(list);
        } catch (Exception e) {
            e.printStackTrace();
            panel.showError("Lỗi tải dữ liệu: " + e.getMessage());
        }
    }

    private void updateTable(List<ToaTauResponse> list) {
        tableModel.setRowCount(0);
        for (ToaTauResponse tt : list) {
            String tenTau = mapTau.getOrDefault(tt.getIdTau(), String.valueOf(tt.getIdTau()));
            String tenLoai = mapLoaiToa.getOrDefault(tt.getIdLoaiToa(), String.valueOf(tt.getIdLoaiToa()));
            tableModel.addRow(new Object[]{tt.getId(), tt.getMaToa(), tenTau, tenLoai});
        }
        tableModel.fireTableDataChanged();
    }

    private static class ComboItem {
        private final int id;
        private final String label;
        public ComboItem(int id, String label) { this.id = id; this.label = label; }
        public int getId() { return id; }
        @Override public String toString() { return label; }
    }

    private class TimKiemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String keyword = panel.getTuKhoa();

                // Lấy id tàu nếu chọn cụ thể
                Object selectedTau = panel.getBoxTau().getSelectedItem();
                int idTau = 0;
                if (selectedTau instanceof ComboItem) {
                    idTau = ((ComboItem) selectedTau).getId();
                }
                final int finalIdTau = idTau; // tạo biến final để dùng trong lambda

                // Lấy id loại toa nếu chọn cụ thể
                Object selectedLoai = panel.getBoxLoaiToa().getSelectedItem();
                int idLoaiToa = 0;
                if (selectedLoai instanceof ComboItem) {
                    idLoaiToa = ((ComboItem) selectedLoai).getId();
                }
                final int finalIdLoaiToa = idLoaiToa; // tạo biến final để dùng trong lambda

                // Gọi API tìm kiếm theo từ khóa
                List<ToaTauResponse> ketQua = toaTauApiClient.searchToaTau(keyword);

                // Lọc bổ sung theo tàu và loại toa nếu có chọn
                if (finalIdTau > 0) {
                    ketQua.removeIf(tt -> tt.getIdTau() != finalIdTau);
                }
                if (finalIdLoaiToa > 0) {
                    ketQua.removeIf(tt -> tt.getIdLoaiToa() != finalIdLoaiToa);
                }

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