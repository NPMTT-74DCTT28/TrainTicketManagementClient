package com.npmtt.ticketclient.controller.lichtrinh;


import com.npmtt.ticketclient.apiclient.LichTrinhApiClient;
import com.npmtt.ticketclient.apiclient.TauApiClient;
import com.npmtt.ticketclient.apiclient.TuyenDuongApiclient;
import com.npmtt.ticketclient.dto.request.LichTrinhRequest;
import com.npmtt.ticketclient.dto.response.LichTrinhResponse;
import com.npmtt.ticketclient.dto.response.TuyenDuongResponse;
import com.npmtt.ticketclient.dto.tau.TauDTO;
import com.npmtt.ticketclient.view.lichtrinh.QLLichTrinhPanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDateTime;
import java.util.List;

public class QLLichTrinhController {

    private final QLLichTrinhPanel panel;
    private final LichTrinhApiClient lichTrinhDAO;
    private final TauApiClient tauDAO;
    private final TuyenDuongApiclient tuyenDuongDAO;
    private final DefaultTableModel tableModel;
    private int selectedId = -1;

    private List<TauDTO> listTau;
    private List<TuyenDuongResponse> listTuyen;

    public QLLichTrinhController(QLLichTrinhPanel panel) {
        this.panel = panel;
        this.lichTrinhDAO = new LichTrinhApiClient();
        this.tauDAO = new TauApiClient();
        this.tuyenDuongDAO = new TuyenDuongApiclient();
        this.tableModel = (DefaultTableModel) panel.getTable().getModel();

        panel.addThemListener(new ThemListener());
        panel.addSuaListener(new SuaListener());
        panel.addXoaListener(new XoaListener());
        panel.addResetListener(new ResetListener());
        panel.addTableMouseListener(new TableMouseListener());

        loadComboBoxData();
        refresh();
    }

    private void loadComboBoxData() {
        try {
            listTau = tauDAO.getAllTau();
            panel.getBoxTau().removeAllItems();
            for (TauDTO t : listTau) panel.getBoxTau().addItem(t);

            listTuyen = tuyenDuongDAO.getAllTuyenDuong();
            panel.getBoxTuyenDuong().removeAllItems();
            for (TuyenDuongResponse td : listTuyen) panel.getBoxTuyenDuong().addItem(td);

        } catch (Exception e) {
            e.printStackTrace();
            panel.showError("Lỗi tải dữ liệu Tàu/Tuyến: " + e.getMessage());
        }
    }

    private void refresh() {
        panel.resetForm();
        selectedId = -1;

        try {
            List<LichTrinhResponse> list = lichTrinhDAO.getAllLichTrinh();
            tableModel.setRowCount(0);

            for (LichTrinhResponse lt : list) {
                String tenTau = getTenTauById(lt.getIdTau());
                String tenTuyen = getTenTuyenById(lt.getIdTuyenDuong());

                tableModel.addRow(new Object[]{
                        lt.getId(),
                        lt.getMaLichTrinh(),
                        tenTau,
                        tenTuyen,
                        lt.getNgayDi(),
                        lt.getNgayDen(),
                        lt.getTrangThai()
                });
            }
            tableModel.fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
            panel.showError("Lỗi tải danh sách lịch trình: " + e.getMessage());
        }
    }

    private String validate(LichTrinhRequest lt) {
        if (lt.getMaLichTrinh() == null || lt.getMaLichTrinh().trim().isEmpty())
            return "Vui lòng nhập mã lịch trình!";
        if (lt.getNgayDi() == null || lt.getNgayDen() == null)
            return "Ngày đi và ngày đến không được để trống!";

        LocalDateTime ngaydi = LocalDateTime.parse(lt.getNgayDi());
        LocalDateTime ngayden = LocalDateTime.parse(lt.getNgayDen());
        if (ngaydi.isAfter(ngayden))
            return "Ngày đi phải trước ngày đến!";
        if (ngaydi.isEqual(ngayden))
            return "Ngày đi và đến không được trùng nhau!";
        return null;
    }

    private String getTenTauById(int id) {
        if (listTau == null) return String.valueOf(id);
        return listTau.stream().filter(t -> t.getId() == id).findFirst().map(TauDTO::getTenTau).orElse(String.valueOf(id));
    }

    private String getTenTuyenById(int id) {
        if (listTuyen == null) return String.valueOf(id);
        return listTuyen.stream().filter(t -> t.getId() == id).findFirst().map(TuyenDuongResponse::getTenTuyen).orElse(String.valueOf(id));
    }

    private class ThemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                LichTrinhRequest lt = panel.getLichTrinhFromForm();
                if (lt == null) return;

                String err = validate(lt);
                if (err != null) {
                    panel.showWarning(err);
                    return;
                }
                if (lichTrinhDAO.createLichTrinh(lt) != null) {
                    panel.showMessage("Thêm thành công!");
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

    private class SuaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (selectedId == -1) {
                    panel.showWarning("Chưa chọn dòng để sửa!");
                    return;
                }

                LichTrinhRequest lt = panel.getLichTrinhFromForm();
                if (lt == null) return;
                lt.setId(selectedId);

                String err = validate(lt);
                if (err != null) {
                    panel.showWarning(err);
                    return;
                }

                if (panel.showConfirm("Bạn muốn cập nhật lịch trình này?")) {
                    if (lichTrinhDAO.updateLichTrinh(lt) != null) {
                        panel.showMessage("Cập nhật thành công!");
                        refresh();
                    } else {
                        panel.showError("Cập nhật thất bại!");
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi hệ thống: " + ex.getMessage());
            }
        }
    }

    private class XoaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (selectedId == -1) {
                    panel.showWarning("Chưa chọn dòng để xoá!");
                    return;
                }

                String ma = panel.getMaLichTrinh();
                if (panel.showConfirm("Bạn chắc chắn muốn xoá lịch trình " + ma + "?")) {
                    if (lichTrinhDAO.deleteLichTrinh(selectedId)) {
                        panel.showMessage("Xoá thành công!");
                        refresh();
                    } else {
                        panel.showError("Xoá thất bại!");
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi xoá dữ liệu: " + ex.getMessage());
            }
        }
    }

    private class ResetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            panel.resetForm();
        }
    }

    private class TableMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            int row = panel.getTable().getSelectedRow();
            if (row == -1) return;

            selectedId = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
            String maLT = tableModel.getValueAt(row, 1).toString();

            panel.startEditMode();
            panel.setMaLichTrinh(maLT);

            String tenTau = tableModel.getValueAt(row, 2).toString();
            for (TauDTO t : listTau) {
                if (t.getTenTau().equals(tenTau)) {
                    panel.setTau(t.getId());
                    break;
                }
            }

            String tenTuyen = tableModel.getValueAt(row, 3).toString();
            for (TuyenDuongResponse td : listTuyen) {
                if (td.getTenTuyen().equals(tenTuyen)) {
                    panel.setTuyenDuong(td.getId());
                    break;
                }
            }

            try {
                String strDi = tableModel.getValueAt(row, 4).toString();
                String strDen = tableModel.getValueAt(row, 5).toString();

                panel.setNgayDi(java.time.LocalDateTime.parse(strDi));
                panel.setNgayDen(java.time.LocalDateTime.parse(strDen));

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            String tt = tableModel.getValueAt(row, 6).toString();
            panel.setTrangThai(tt);
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