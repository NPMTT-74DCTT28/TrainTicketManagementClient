package com.npmtt.ticketclient.controller.gatau;

import com.npmtt.ticketclient.apiclient.GaTauApiClient;
import com.npmtt.ticketclient.dto.gatau.GaTauDTO;
import com.npmtt.ticketclient.view.gatau.QLGaTauPanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class QLGaTauController {
    private final QLGaTauPanel panel;
    private final GaTauApiClient dao;
    private final DefaultTableModel model;
    private int selectedRow;


    public QLGaTauController(QLGaTauPanel panel) {
        this.dao = new GaTauApiClient();
        this.panel = panel;
        panel.AddGaTau(new AddGaTau());
        panel.EditGaTau(new EditGaTau());
        panel.RemoveGaTau(new RemoveGaTau());
        panel.ResetGaTau(new ResetGaTau());
        panel.TableMouseClickListener(new TableMouseClickListener());
        model = (DefaultTableModel) panel.getTable().getModel();
        refresh();
    }

    private void refresh() {
        panel.resetForm();
        try {
            List<GaTauDTO> List = dao.getAllGaTau();
            model.setRowCount(0);
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
        } catch (Exception e) {
            panel.showError(e.getMessage());
        }
    }

    private class AddGaTau implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                GaTauDTO gaTau = panel.getGaTau();
                if (gaTau.getMaGa().isEmpty() || gaTau.getTenGa().isEmpty() || gaTau.getDiaChi().isEmpty() || gaTau.getThanhPho().isEmpty()) {
                    panel.showError("Vui lòng nhập đầy đủ!");
                    return;
                }
                if (dao.createGaTau(gaTau) != null) {
                    panel.showMessage("Thêm Ga tàu thành công!");
                    panel.resetForm();
                    refresh();
                } else {
                    panel.showError("Thêm thất bại!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi hệ thống: " + ex.getMessage());
            }
        }
    }

    private class EditGaTau implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                GaTauDTO gaTau = panel.getGaTau();
                gaTau.setId(Integer.parseInt(model.getValueAt(selectedRow, 0).toString()));
                if (gaTau.getMaGa().isEmpty() || gaTau.getTenGa().isEmpty() || gaTau.getDiaChi().isEmpty() || gaTau.getThanhPho().isEmpty()) {
                    panel.showError("Vui lòng nhập đầy đủ!");
                    return;
                }
                if (panel.showConfirm("Bạn có muốn cập nhật thông tin của " + gaTau.getMaGa() + " không ?")) {
                    if (dao.updateGaTau(gaTau) != null) {
                        panel.showMessage("Cập nhật thành công!");
                        refresh();
                    } else {
                        panel.showError("Cập nhật thất bại!!!!");
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi hệ thống: " + ex.getMessage());
            }
        }
    }

    private class RemoveGaTau implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());
                if (id < 1) {
                    return;
                }
                if (panel.showConfirm("Bạn có muốn xóa " + id + " không ?")) {
                    if (dao.deleteGaTau(id)) {
                        panel.showMessage("Xóa thành công!");
                        refresh();
                    } else {
                        panel.showError("Xóa thất bại!!!!");
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi hệ thống: " + ex.getMessage());
            }
        }
    }

    private class ResetGaTau implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            panel.resetForm();
        }
    }

    private class TableMouseClickListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            panel.startEditMode();
            selectedRow = panel.getTable().getSelectedRow();
            if (selectedRow == -1) {
                return;
            }
            panel.setMaGa(model.getValueAt(selectedRow, 1).toString());
            panel.setTenga(model.getValueAt(selectedRow, 2).toString());
            panel.setDiachi(model.getValueAt(selectedRow, 3).toString());
            panel.setThanhpho(model.getValueAt(selectedRow, 4).toString());
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