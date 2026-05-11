package com.npmtt.ticketclient.controller.tau;

import com.npmtt.ticketclient.apiclient.TauApiClient;
import com.npmtt.ticketclient.dto.tau.TauDTO;
import com.npmtt.ticketclient.view.tau.QLTauPanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class QLTauController {
    private final QLTauPanel panel;
    private final TauApiClient dao;
    private final DefaultTableModel model;
    private int selectedRow;

    public QLTauController(QLTauPanel panel) {

        this.dao = new TauApiClient();

        this.panel = panel;
        this.panel.addButtonThemActionListener(new ThemTauListener());
        this.panel.addButtonSuaActionListener(new SuaTauListener());
        this.panel.addButtonXoaActionListener(new XoaTauListener());
        this.panel.addButtonResetActionListener(new ResetFormListener());
        this.panel.addTableMouseClickListener(new TableMouseClickListener());

        model = (DefaultTableModel) panel.getTable().getModel();

        refresh();
    }

    private void refresh() {
        try {
            panel.resetForm();
            List<TauDTO> list = dao.getAllTau();
            model.setRowCount(0);

            for (TauDTO tau : list) {
                model.addRow(new Object[]{
                        tau.getId(),
                        tau.getMaTau(),
                        tau.getTenTau()
                });
            }
            model.fireTableDataChanged();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private String validateInput(TauDTO tau) {
        if (tau.getMaTau().isEmpty()) {
            return "Mã tàu không được để trống!";
        }
        if (tau.getTenTau().isEmpty()) {
            return "Tên tàu không được để trống!";
        }
        return null;
    }

    private class ThemTauListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                TauDTO tau = panel.getTauFromForm();

                if (validateInput(tau) != null) {
                    panel.showWarning(validateInput(tau));
                    return;
                }

                if (dao.createTau(tau) != null) {
                    panel.showMessage("Thêm tàu thành công!");
                    panel.resetForm();
                    refresh();
                } else {
                    panel.showError("Thêm thất bại! Vui lòng kiểm tra lại!");
                }
            } catch (RuntimeException ex) {
                ex.printStackTrace();
                panel.showError("Lỗi hệ thống: " + ex.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi không xác định: " + ex.getMessage());
            }
        }
    }

    private class SuaTauListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                TauDTO tau = panel.getTauFromForm();
                tau.setId(Integer.parseInt(model.getValueAt(selectedRow, 0).toString()));

                if (validateInput(tau) != null) {
                    panel.showWarning(validateInput(tau));
                    return;
                }

                if (panel.showConfirm("Bạn có chắc muốn sửa loại toa: " + tau.getMaTau() + "?")) {
                    if (dao.updateTau(tau) != null) {
                        panel.showMessage("Sửa thành công!");
                        refresh();
                    } else {
                        panel.showError("Sửa thất bại!");
                    }
                }
            } catch (RuntimeException ex) {
                panel.showError("Lỗi hệ thống: " + ex.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi không xác định: " + ex.getMessage());
            }
        }
    }

    private class XoaTauListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());

                if (id < 1) {
                    panel.showWarning("Tên loại toa không hợp lệ!");
                    return;
                }

                if (panel.showConfirm("Bạn muốn xoá tàu " + id + "?")) {
                    if (dao.deleteTau(id)) {
                        panel.showMessage("Xoá thành công!");
                        refresh();
                    } else {
                        panel.showError("Xóa thất bại! Vui lòng kiểm tra lại!");
                    }
                }
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

            panel.setMaTau(model.getValueAt(selectedRow, 1).toString());
            panel.setTenTau(model.getValueAt(selectedRow, 2).toString());

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