package com.npmtt.ticketclient.controller.ghe;

import com.npmtt.ticketclient.apiclient.GheApiClient;
import com.npmtt.ticketclient.apiclient.ToaTauApiClient;
import com.npmtt.ticketclient.dto.request.GheRequest;
import com.npmtt.ticketclient.dto.response.GheResponse;
import com.npmtt.ticketclient.dto.response.ToaTauResponse;
import com.npmtt.ticketclient.dto.response.TuyenDuongResponse;
import com.npmtt.ticketclient.view.ghe.QLGhePanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;

public class QLGheController {
    private final QLGhePanel panel;
    private final GheApiClient apiClient;
    private final DefaultTableModel model;
    private int selectedRow;
    private HashMap<Integer, String> MapToaTau;


    public QLGheController(QLGhePanel panel) {
        this.panel = panel;
        this.apiClient = new GheApiClient();
        ToaTauApiClient toaTauApiClient = new ToaTauApiClient();
        MapToaTau = new HashMap<>();

        this.panel.addButtonThemActionListener(new QLGheController.ThemGheListener());
        this.panel.addButtonSuaActionListener(new QLGheController.SuaGheListener());
        this.panel.addButtonXoaActionListener(new QLGheController.XoaGheListener());
        this.panel.addButtonResetActionListener(new QLGheController.ResetFormListener());
        this.panel.addTableMouseClickListener(new QLGheController.TableMouseClickListener());

        try {
            List<ToaTauResponse> dsToa = toaTauApiClient.getAllToaTau();
            this.panel.setComboBoxToaTauData(dsToa);
        } catch (Exception exception) {
            panel.showError(exception.getMessage());
        }

        model = (DefaultTableModel) panel.getTable().getModel();

        refresh();
    }

    private void refresh() {
        panel.resetForm();
        try {
            ToaTauApiClient toaTauApiClient = new ToaTauApiClient();
            List<ToaTauResponse> listToaTau = toaTauApiClient.getAllToaTau();

            MapToaTau.clear();
            for (ToaTauResponse toaTau : listToaTau) {
                MapToaTau.put(toaTau.getId(), toaTau.getMaToa());
            }

            panel.setComboBoxToaTauData(listToaTau);

            List<GheResponse> list = apiClient.getAllGhe();
            model.setRowCount(0);
            for (GheResponse ghe : list) {
                model.addRow(new Object[]{
                        ghe.getId(),
                        ghe.getSoGhe(),
                        MapToaTau.getOrDefault(ghe.getIdToaTau(), String.valueOf(ghe.getIdToaTau()))
                });
            }
            model.fireTableDataChanged();
        } catch (Exception exception) {
            panel.showError(exception.getMessage());
        }
    }

    private String validateInput(GheRequest ghe) {
        if (ghe.getSoGhe().isEmpty()) {
            return "Số ghế không được để trống!";
        }
        if (ghe.getIdToaTau() == 0) {
            return "ID toa tàu không được để trống!";
        }
        return null;
    }

    private class ThemGheListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                GheRequest ghe = panel.getGheFromForm();
                ghe.setId(0);

                if (validateInput(ghe) != null) {
                    panel.showWarning(validateInput(ghe));
                    return;
                }

                if (apiClient.createGhe(ghe) != null) {
                    panel.showMessage("Thêm ghế thành công!");
                    panel.resetForm();
                    refresh();
                } else {
                    panel.showError("Thêm thất bại! Vui lòng kiểm tra lại!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError(ex.getMessage());
            }
        }
    }

    private class SuaGheListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                GheRequest ghe = panel.getGheFromForm();
                if (model.getValueAt(selectedRow, 0).toString().isEmpty()) {
                    panel.showWarning("ID ghế không hợp lệ");
                    return;
                }
                ghe.setId(Integer.parseInt(model.getValueAt(selectedRow, 0).toString()));

                if (validateInput(ghe) != null) {
                    panel.showWarning(validateInput(ghe));
                    return;
                }


                if (panel.showConfirm("Bạn có chắc muốn sửa ghế: " + ghe.getSoGhe() + "?")) {
                    if (apiClient.updateGhe(ghe) != null) {
                        panel.showMessage("Sửa thành công!");
                        refresh();
                    } else {
                        panel.showError("Sửa thất bại!");
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError(ex.getMessage());
            }
        }
    }

    private class XoaGheListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());

                if (id < 1) {
                    panel.showWarning("Số ghế không hợp lệ!");
                    return;
                }

                if (panel.showConfirm("Bạn muốn xoá ghế " + id + "?")) {
                    if (apiClient.deleteGhe(id)) {
                        panel.showMessage("Xoá thành công!");
                        refresh();
                    } else {
                        panel.showError("Xóa thất bại! Vui lòng kiểm tra lại!");
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError(ex.getMessage());
            }
        }
    }

    private class ResetFormListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            panel.resetForm();
        }
    }

    private class TableMouseClickListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            panel.startEditMode();

            selectedRow = panel.getTable().getSelectedRow();
            if (selectedRow == -1) return;

            try {
                Object value = model.getValueAt(selectedRow, 0);
                if (value == null) {
                    return;
                }
                int id = Integer.parseInt(value.toString());
                List<GheResponse> list = apiClient.getAllGhe();
                GheResponse selectedGhe = null;
                for (GheResponse ghe : list) {
                    if (ghe.getId() == id) {
                        selectedGhe = ghe;
                        break;
                    }
                }
                panel.setSoGhe(model.getValueAt(selectedRow, 1).toString());
                panel.setIDToaTau(selectedGhe.getIdToaTau());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}