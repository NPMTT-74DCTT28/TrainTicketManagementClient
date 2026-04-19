package com.npmtt.ticketclient.controller.toatau;

import com.npmtt.ticketclient.apiclient.LoaiToaApiClient;
import com.npmtt.ticketclient.apiclient.TauApiClient;
import com.npmtt.ticketclient.apiclient.ToaTauApiClient;
import com.npmtt.ticketclient.dto.loaitoa.LoaiToaDTO;
import com.npmtt.ticketclient.dto.request.ToaTauRequest;
import com.npmtt.ticketclient.dto.response.ToaTauResponse;
import com.npmtt.ticketclient.dto.tau.TauDTO;
import com.npmtt.ticketclient.view.toatau.QLToaTauPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QLToaTauController {

    private final QLToaTauPanel panel;
    private final ToaTauApiClient toaTauApiClient;
    private final TauApiClient tauApiClient;
    private final LoaiToaApiClient loaiToaApiClient;
    private final DefaultTableModel model;

    private List<ToaTauResponse> listToaTau;
    private Map<Integer, String> mapTau = new HashMap<>();
    private Map<Integer, String> mapLoaiToa = new HashMap<>();
    private int selectedRow = -1;

    public QLToaTauController(QLToaTauPanel panel) {
        this.panel = panel;
        this.toaTauApiClient = new ToaTauApiClient();
        this.tauApiClient = new TauApiClient();
        this.loaiToaApiClient = new LoaiToaApiClient();

        panel.addThemListener(new ThemListener());
        panel.addSuaListener(new SuaListener());
        panel.addXoaListener(new XoaListener());
        panel.addResetListener(new ResetListener());
        panel.getTable().addMouseListener(new TableMouseClickListener());

        model = (DefaultTableModel) panel.getTable().getModel();
        loadComboBoxData();
        refresh();
    }

    private void loadComboBoxData() {
        try {
            // Load danh sách tàu
            List<TauDTO> listTau = tauApiClient.getAllTau();
            panel.getBoxTau().removeAllItems();
            mapTau.clear();
            for (TauDTO t : listTau) {
                String display = t.getMaTau() + " - " + t.getTenTau();
                mapTau.put(t.getId(), display);
                panel.getBoxTau().addItem(new ComboItem(t.getId(), display));
            }

            // Load danh sách loại toa
            List<LoaiToaDTO> listLoai = loaiToaApiClient.getAllLoaiToa();
            panel.getBoxLoaiToa().removeAllItems();
            mapLoaiToa.clear();
            for (LoaiToaDTO lt : listLoai) {
                String display = lt.getTenLoai() + " (HS: " + lt.getHeSoGia() + ")";
                mapLoaiToa.put(lt.getId(), display);
                panel.getBoxLoaiToa().addItem(new ComboItem(lt.getId(), display));
            }
        } catch (Exception e) {
            e.printStackTrace();
            panel.showError("Lỗi tải dữ liệu danh mục: " + e.getMessage());
        }
    }

    private void refresh() {
        panel.resetForm();
        selectedRow = -1;
        try {
            listToaTau = toaTauApiClient.getAllToaTau();
            updateTable(listToaTau);
        } catch (Exception e) {
            e.printStackTrace();
            panel.showError("Lỗi tải dữ liệu toa tàu: " + e.getMessage());
        }
    }

    private void updateTable(List<ToaTauResponse> list) {
        model.setRowCount(0);
        for (ToaTauResponse tt : list) {
            String tenTau = mapTau.getOrDefault(tt.getIdTau(), String.valueOf(tt.getIdTau()));
            String tenLoai = mapLoaiToa.getOrDefault(tt.getIdLoaiToa(), String.valueOf(tt.getIdLoaiToa()));
            model.addRow(new Object[]{tt.getId(), tt.getMaToa(), tenTau, tenLoai});
        }
        model.fireTableDataChanged();
    }

    // Lớp helper cho JComboBox
    private static class ComboItem {
        private final int id;
        private final String label;

        public ComboItem(int id, String label) {
            this.id = id;
            this.label = label;
        }

        public int getId() { return id; }

        @Override
        public String toString() { return label; }
    }

    private class ThemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String maToa = panel.getMaToa();
                if (maToa.isEmpty()) {
                    panel.showWarning("Vui lòng nhập mã toa!");
                    return;
                }
                ComboItem selectedTau = (ComboItem) panel.getBoxTau().getSelectedItem();
                ComboItem selectedLoai = (ComboItem) panel.getBoxLoaiToa().getSelectedItem();
                if (selectedTau == null || selectedLoai == null) {
                    panel.showWarning("Vui lòng chọn Tàu và Loại toa!");
                    return;
                }

                ToaTauRequest request = new ToaTauRequest(maToa, selectedTau.getId(), selectedLoai.getId());
                ToaTauResponse response = toaTauApiClient.createToaTau(request);
                if (response != null) {
                    panel.showMessage("Thêm toa tàu thành công!");
                    refresh();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi: " + ex.getMessage());
            }
        }
    }

    private class SuaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                selectedRow = panel.getTable().getSelectedRow();
                if (selectedRow == -1) {
                    panel.showWarning("Vui lòng chọn toa tàu để sửa!");
                    return;
                }

                int idToa = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());
                String maToa = panel.getMaToa();
                if (maToa.isEmpty()) {
                    panel.showWarning("Mã toa không được để trống!");
                    return;
                }
                ComboItem selectedTau = (ComboItem) panel.getBoxTau().getSelectedItem();
                ComboItem selectedLoai = (ComboItem) panel.getBoxLoaiToa().getSelectedItem();
                if (selectedTau == null || selectedLoai == null) {
                    panel.showWarning("Vui lòng chọn Tàu và Loại toa!");
                    return;
                }

                ToaTauRequest request = ToaTauRequest.builder()
                        .id(idToa)
                        .maToa(maToa)
                        .idTau(selectedTau.getId())
                        .idLoaiToa(selectedLoai.getId())
                        .build();

                if (panel.showConfirm("Cập nhật toa " + maToa + "?")) {
                    ToaTauResponse response = toaTauApiClient.updateToaTau(request);
                    if (response != null) {
                        panel.showMessage("Cập nhật thành công!");
                        refresh();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi: " + ex.getMessage());
            }
        }
    }

    private class XoaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                selectedRow = panel.getTable().getSelectedRow();
                if (selectedRow == -1) {
                    panel.showWarning("Vui lòng chọn toa tàu để xóa!");
                    return;
                }

                int idToa = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());
                String maToa = model.getValueAt(selectedRow, 1).toString();

                if (panel.showConfirm("Xóa toa " + maToa + "?")) {
                    if (toaTauApiClient.deleteToaTau(idToa)) {
                        panel.showMessage("Xóa thành công!");
                        refresh();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi: " + ex.getMessage());
            }
        }
    }

    private class ResetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            refresh();
        }
    }

    private class TableMouseClickListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            selectedRow = panel.getTable().getSelectedRow();
            if (selectedRow == -1) return;

            panel.startEditMode();

            int idToa = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());

            // Tìm đối tượng trong list đã load
            ToaTauResponse selected = null;
            for (ToaTauResponse tt : listToaTau) {
                if (tt.getId() == idToa) {
                    selected = tt;
                    break;
                }
            }
            if (selected == null) return;

            panel.setMaToa(selected.getMaToa());

            // Chọn tàu trong combobox
            JComboBox boxTau = panel.getBoxTau();
            for (int i = 0; i < boxTau.getItemCount(); i++) {
                ComboItem item = (ComboItem) boxTau.getItemAt(i);
                if (item.getId() == selected.getIdTau()) {
                    boxTau.setSelectedIndex(i);
                    break;
                }
            }

            // Chọn loại toa trong combobox
            JComboBox boxLoai = panel.getBoxLoaiToa();
            for (int i = 0; i < boxLoai.getItemCount(); i++) {
                ComboItem item = (ComboItem) boxLoai.getItemAt(i);
                if (item.getId() == selected.getIdLoaiToa()) {
                    boxLoai.setSelectedIndex(i);
                    break;
                }
            }
        }

        @Override public void mousePressed(MouseEvent e) {}
        @Override public void mouseReleased(MouseEvent e) {}
        @Override public void mouseEntered(MouseEvent e) {}
        @Override public void mouseExited(MouseEvent e) {}
    }
}