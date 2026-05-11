package com.npmtt.ticketclient.controller.khachhang;

import com.npmtt.ticketclient.apiclient.KhachHangApiClient;
import com.npmtt.ticketclient.dto.request.KhachHangRequest;
import com.npmtt.ticketclient.dto.response.KhachHangResponse;
import com.npmtt.ticketclient.view.khachhang.QLKhachHangPanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.util.List;

public class QLKhachHangController {
    private final QLKhachHangPanel panel;
    private final KhachHangApiClient apiClient;
    private final DefaultTableModel tableModel;
    private int selectedRow;

    public QLKhachHangController(QLKhachHangPanel panel) {
        this.panel = panel;
        this.apiClient = KhachHangApiClient.getInstance();
        panel.addThemKhachHangListener(new ThemKhachHangListener());
        panel.addSuaKhachHangListener(new SuaKhachHangListener());
        panel.addXoaKhachHangListener(new XoaKhachHangListener());
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
            List<KhachHangResponse> danhSachKhachHang = apiClient.getAllKhachHang();
            tableModel.setRowCount(0);

            for (KhachHangResponse khachHang : danhSachKhachHang) {
                tableModel.addRow(new Object[]{
                        khachHang.getId(),
                        khachHang.getCccd(),
                        khachHang.getHoTen(),
                        khachHang.getNgaySinh(),
                        khachHang.getGioiTinh(),
                        khachHang.getSdt(),
                        khachHang.getDiaChi()
                });
            }

            tableModel.fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
            panel.showError(e.getMessage());
        }
    }

    private class ThemKhachHangListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (panel.thongBaoLoiDauVao() != null) {
                    panel.showWarning(panel.thongBaoLoiDauVao());
                    return;
                }

                KhachHangRequest newKhachHang = panel.getKhachHangFromForm();
                newKhachHang.setId(-1);
                KhachHangResponse ketQua = apiClient.createKhachHang(newKhachHang);
                panel.showMessage("Thêm khách hàng " + ketQua.getHoTen() + " thành công!");
                refresh();
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError(ex.getMessage());
                refresh();
            }
        }
    }

    private class SuaKhachHangListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (panel.thongBaoLoiDauVao() != null) {
                    panel.showWarning(panel.thongBaoLoiDauVao());
                    return;
                }

                KhachHangRequest edited = panel.getKhachHangFromForm();
                if (!panel.showConfirm("Bạn muốn cập nhật thông tin khách hàng " + edited.getHoTen() + "?")) return;
                edited.setId(Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString()));
                KhachHangResponse ketQua = apiClient.updateKhachHang(edited);
                panel.showMessage("Cập nhật khách hàng " + ketQua.getHoTen() + " thành công!");
                refresh();
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError(ex.getMessage());
                refresh();
            }
        }
    }

    private class XoaKhachHangListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (!panel.showConfirm("Bạn chắc chắn muốn xoá?")) return;
                int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                if (apiClient.deleteKhachHang(id)) {
                    panel.showMessage("Xóa khách hàng thành công!");
                } else {
                    panel.showError("Xóa khách hàng thất bại");
                }
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

            panel.setCccd(tableModel.getValueAt(selectedRow, 1).toString());
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

            Object diaChiObj = tableModel.getValueAt(selectedRow, 6);
            panel.setDiaChi(diaChiObj != null ? diaChiObj.toString() : "");
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
