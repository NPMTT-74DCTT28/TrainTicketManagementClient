package com.npmtt.ticketclient.controller.loaitoa;

import com.npmtt.ticketclient.apiclient.LoaiToaApiClient;
import com.npmtt.ticketclient.dto.loaitoa.LoaiToaDTO;
import com.npmtt.ticketclient.view.loaitoa.QLLoaiToaPanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.util.List;

public class QLLoaiToaController {

    private final QLLoaiToaPanel panel;
    private final LoaiToaApiClient dao;
    private final DefaultTableModel model;
    private int selectedRow;

    public QLLoaiToaController(QLLoaiToaPanel panel) {
        this.dao = new LoaiToaApiClient();

        this.panel = panel;
        panel.setButtonThemActionListener(new ThemLoaiToaListener());
        panel.setButtonSuaActionListener(new SuaLoaiToaListener());
        panel.setButtonXoaActionListener(new XoaLoaiToaListener());
        panel.setButtonResetActionListener(new ResetFormListener());
        panel.addTableMouseClickListener(new TableMouseClickListener());

        model = (DefaultTableModel) panel.getTable().getModel();

        refresh();
    }

    private void refresh() {
        try {
            panel.resetForm();

            List<LoaiToaDTO> list = dao.getAllLoaiToa();
            model.setRowCount(0);

            for (LoaiToaDTO loaiToa : list) {
                model.addRow(new Object[]{
                        loaiToa.getId(),
                        loaiToa.getTenLoai(),
                        loaiToa.getHeSoGia()
                });
            }
            model.fireTableDataChanged();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private String validateInput(LoaiToaDTO loaiToa) {
        if (loaiToa.getTenLoai() == null || loaiToa.getTenLoai().trim().isEmpty()) {
            return "Tên loại toa không được để trống!";
        }

        BigDecimal heSoGia = loaiToa.getHeSoGia();

        if (heSoGia == null) {
            return "Hệ số giá không được để trống!";
        }

        if (heSoGia.compareTo(BigDecimal.ZERO) <= 0 || heSoGia.compareTo(new BigDecimal("2")) >= 0) {
            return "Hệ số giá phải lớn hơn 0 và bé hơn 2!";
        }

        return null;
    }

    private class ThemLoaiToaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                LoaiToaDTO loaiToa = panel.getLoaiToaFromForm();

                if (validateInput(loaiToa) != null) {
                    panel.showWarning(validateInput(loaiToa));
                    return;
                }

                if (dao.createLoaiToa(loaiToa) != null) {
                    panel.showMessage("Thêm loại toa thành công!");
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

    private class SuaLoaiToaListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                LoaiToaDTO loaiToa = panel.getLoaiToaFromForm();
                loaiToa.setId(Integer.parseInt(model.getValueAt(selectedRow, 0).toString()));

                if (validateInput(loaiToa) != null) {
                    panel.showWarning(validateInput(loaiToa));
                    return;
                }

                if (panel.showConfirm("Bạn có chắc muốn sửa loại toa: " + loaiToa.getTenLoai() + "?")) {
                    if (dao.updateLoaiToa(loaiToa) != null) {
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

    private class XoaLoaiToaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());

                if (id < 1) {
                    panel.showWarning("Tên loại toa không hợp lệ!");
                    return;
                }

                if (panel.showConfirm("Bạn muốn xoá loại toa " + id + "?")) {
                    if (dao.deleteLoaiToa(id)) {
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

            panel.setTenLoai(model.getValueAt(selectedRow, 1).toString());
            panel.setHeSoGia(model.getValueAt(selectedRow, 2).toString());

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
