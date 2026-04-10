package com.npmtt.ticketclient.controller.nhanvien;

import com.npmtt.ticketclient.apiclient.NhanVienApiClient;
import com.npmtt.ticketclient.dto.request.NhanVienRequest;
import com.npmtt.ticketclient.dto.response.NhanVienResponse;
import com.npmtt.ticketclient.view.nhanvien.QLNhanVienPanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.util.List;

public class QLNhanVienController {
    private final QLNhanVienPanel panel;
    private final NhanVienApiClient apiClient;
    private final DefaultTableModel tableModel;
    private int selectedRow;

    public QLNhanVienController(QLNhanVienPanel panel) {
        this.panel = panel;
        this.apiClient = NhanVienApiClient.getInstance();
        panel.addThemNhanVienListener(new ThemNhanVienListener());
        panel.addSuaNhanVienListener(new SuaNhanVienListener());
        panel.addXoaNhanVienListener(new XoaNhanVienListener());
        panel.addResetFormListener(new ResetFormListener());
        panel.addRefreshListener(new RefreshListener());
        panel.addTableMouseClickListener(new TableMouseClickListener());

        if (this.panel.getTable() != null) {
            tableModel = (DefaultTableModel) this.panel.getTable().getModel();
        } else {
            tableModel = new DefaultTableModel();
        }

        refresh();
    }

    private void refresh() {
        try {
            List<NhanVienResponse> danhSachNhanVien = apiClient.getAllNhanVien();
            tableModel.setRowCount(0);

            for (NhanVienResponse nhanVien : danhSachNhanVien) {
                tableModel.addRow(new Object[]{
                        nhanVien.getId(),
                        nhanVien.getMaNhanVien(),
                        nhanVien.getHoTen(),
                        nhanVien.getNgaySinh(),
                        nhanVien.getGioiTinh(),
                        nhanVien.getSdt(),
                        nhanVien.getEmail(),
                        nhanVien.getDiaChi(),
                        nhanVien.getVaiTro()
                });
            }

            tableModel.fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
            panel.showError(e.getMessage());
        }
    }

    private class ThemNhanVienListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (panel.thongBaoLoiDauVao() != null) {
                    panel.showWarning(panel.thongBaoLoiDauVao());
                    return;
                }

                NhanVienRequest newNhanVien = panel.getNhanVienFromForm();
                newNhanVien.setId(-1);
                NhanVienResponse ketQua = apiClient.createNhanVien(newNhanVien);
                panel.showMessage("Thêm nhân viên " + ketQua.getHoTen() + " thành công!");
                refresh();
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError(ex.getMessage());
                refresh();
            }
        }
    }

    private class SuaNhanVienListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (panel.thongBaoLoiDauVao() != null) {
                    panel.showWarning(panel.thongBaoLoiDauVao());
                    return;
                }

                NhanVienRequest edited = panel.getNhanVienFromForm();
                if (!panel.showConfirm("Bạn muốn cập nhật thông tin nhân viên " + edited.getHoTen() + "?")) return;
                edited.setId(Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString()));
                NhanVienResponse ketQua = apiClient.updateNhanVien(edited);
                panel.showMessage("Cập nhật nhân viên " + ketQua.getHoTen() + " thành công!");
                refresh();
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError(ex.getMessage());
                refresh();
            }
        }
    }

    private class XoaNhanVienListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (!panel.showConfirm("Bạn chắc chắn muốn xoá?")) return;
                int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                apiClient.deleteNhanVien(id);
                panel.showMessage("Xoá nhân viên thành công!");
                refresh();
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError(ex.getMessage());
                refresh();
            }
        }
    }

    private class ResetFormListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            panel.resetForm();
        }
    }

    private class RefreshListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            refresh();
        }
    }

    private class TableMouseClickListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            panel.startEditMode();

            if (panel.getTable() != null) {
                selectedRow = panel.getTable().getSelectedRow();
            }

            if (selectedRow == -1) {
                return;
            }

            panel.setMaNhanVien(tableModel.getValueAt(selectedRow, 1).toString());
            panel.setHoTen(tableModel.getValueAt(selectedRow, 2).toString());

            String ngaySinh = tableModel.getValueAt(selectedRow, 3).toString();

            try {
                panel.setNgaySinh(LocalDate.parse(ngaySinh));
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi khi chuyển đổi ngày tháng: " + ex.getMessage());
            }

            panel.setGioiTinh(tableModel.getValueAt(selectedRow, 4).toString());

            panel.setSdt(tableModel.getValueAt(selectedRow, 5).toString());

            Object emailObj = tableModel.getValueAt(selectedRow, 6);
            panel.setEmail(emailObj != null ? emailObj.toString() : "");

            Object diaChiObj = tableModel.getValueAt(selectedRow, 7);
            panel.setDiaChi(diaChiObj != null ? diaChiObj.toString() : "");

            panel.setVaiTro(tableModel.getValueAt(selectedRow, 8).toString());
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
