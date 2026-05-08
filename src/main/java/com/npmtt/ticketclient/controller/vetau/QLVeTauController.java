package com.npmtt.ticketclient.controller.vetau;

import com.npmtt.ticketclient.apiclient.*;
import com.npmtt.ticketclient.dto.request.VeTauRequest;
import com.npmtt.ticketclient.dto.response.*;
import com.npmtt.ticketclient.view.vetau.QLVeTauPanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class QLVeTauController {
    private final QLVeTauPanel panel;
    private final VeTauApiClient dao;
    private final DefaultTableModel model;
    private final HashMap<Integer, String> mapKH = new HashMap<>();
    private final HashMap<Integer, String> mapLT = new HashMap<>();
    private final HashMap<Integer, String> mapG = new HashMap<>();
    private final HashMap<Integer, String> mapNV = new HashMap<>();
    private int selectedRow;

    public QLVeTauController(QLVeTauPanel panel) {
        this.dao = new VeTauApiClient();
        this.panel = panel;
        panel.addThemVeTauListener(new ThemVeTauListener());
        panel.addSuaVeTauListener(new SuaVeTauListener());
        panel.addXoaVeTauListener(new XoaVeTauListener());
        panel.addResetFormListener(new ResetFormListener());
        panel.addRefreshListener(new RefreshListener());
        panel.addTableMouseClickListener(new TableMouseClickListener());

        if (this.panel.getTable() != null) {
            model = (DefaultTableModel) this.panel.getTable().getModel();
        } else {
            model = new DefaultTableModel();
        }
        loadKhachHang();
        loadLichTrinh();
        loadGhe();
        loadNhanVien();

        refresh();
    }

    private void loadKhachHang() {
        try {
            KhachHangApiClient dao = new KhachHangApiClient();
            mapKH.clear();
            List<KhachHangResponse> khachHangs = dao.getAllKhachHang();
            panel.setBoxKhachHang(khachHangs);
            for (KhachHangResponse khachHang : khachHangs ) {
                mapKH.put(
                        khachHang.getId(),
                        khachHang.getHoTen());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadLichTrinh() {
        try {
            LichTrinhApiClient dao = new LichTrinhApiClient();
            mapLT.clear();
            List<LichTrinhResponse> lichTrinhs = dao.getAllLichTrinh();
            panel.setBoxLichTrinh(lichTrinhs);
            for (LichTrinhResponse lichTrinh : lichTrinhs) {
                mapLT.put(
                        lichTrinh.getId(),
                        lichTrinh.getMaLichTrinh()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadGhe() {
        try {
            GheApiClient dao = new GheApiClient();
            mapG.clear();
            List<GheResponse> ghes = dao.getAllGhe();
            panel.setBoxGhe(ghes);
            for (GheResponse ghe : ghes) {
                mapG.put(
                        ghe.getId(),
                        ghe.getSoGhe()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadNhanVien() {
        try {
            NhanVienApiClient dao = new NhanVienApiClient();
            mapNV.clear();
            List<NhanVienResponse> nhanViens = dao.getAllNhanVien();
            panel.setBoxNhanVien(nhanViens);
            for (NhanVienResponse nhanVien : nhanViens) {
                mapNV.put(
                        nhanVien.getId(),
                        nhanVien.getHoTen()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refresh() {
        panel.resetForm();
        try {
            List<VeTauResponse> list = dao.getAllVeTau();
            model.setRowCount(0);
            for (VeTauResponse v : list) {
                String tenKhachHang = mapKH.getOrDefault(v.getIdKhachHang(), String.valueOf(v.getIdKhachHang()));
                String tenLichTrinh = mapLT.getOrDefault(v.getIdLichTrinh(), String.valueOf(v.getIdLichTrinh()));
                String tenGhe = mapG.getOrDefault(v.getIdGhe(), String.valueOf(v.getIdGhe()));
                String tenNhanVien = mapNV.getOrDefault(v.getIdNhanVien(), String.valueOf(v.getIdNhanVien()));

                model.addRow(new Object[]{
                        v.getId(),
                        v.getMaVe(),
                        tenKhachHang,
                        tenLichTrinh,
                        tenGhe,
                        tenNhanVien,
                        v.getNgayDatVe(),
                        v.getGiaVe(),
                        v.getTrangThai()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.fireTableDataChanged();
    }

    public class ThemVeTauListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                VeTauRequest vt = panel.getVeTauFromForm();

                if (vt.getMaVe().isEmpty()) {
                    panel.showError("Vui lòng nhập đầy đủ thông tin!");
                    return;
                }
                if (vt.getNgayDatVe().isBefore(LocalDateTime.now()) ) {
                    panel.showError("Ngày đặt vé phải lớn hơn ngày hiện tại!");
                    return;
                }
                if (vt.getGiaVe() <= 0) {
                    panel.showError("Giá vé phải lớn hơn 0!");
                    return;
                }
                if (dao.createVeTau(vt) != null) {
                    panel.showMessage("Thêm vé tàu thành công");
                    panel.resetForm();
                    refresh();
                } else {
                    panel.showError("Thêm thất bại");
                }
            } catch (NumberFormatException ex) {
                panel.showError("Giá vé phải là số");
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi không xác định: " + ex.getMessage());
            }
        }
    }

    public class SuaVeTauListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                VeTauRequest vt = panel.getVeTauFromForm();
                vt.setId(Integer.parseInt(model.getValueAt(selectedRow, 0).toString()));
                if (vt.getMaVe().isEmpty()) {
                    panel.showError("Vui lòng nhập đầy đủ thông tin");
                    return;
                }
                if (vt.getNgayDatVe().isBefore(LocalDateTime.now())) {
                    panel.showError("Ngày đặt vé phải lớn hơn ngày hiện tại");
                    return;
                }
                if (vt.getGiaVe() <= 0) {
                    panel.showError("Giá vé phải lớn hơn 0!");
                    return;
                }
                if (panel.showConfirm("Bạn có muốn cập nhật thông tin của" + vt.getMaVe() + " không ?")) {
                    if (dao.updateVeTau(vt) != null) {
                        panel.showMessage("Cập nhật thành công");
                        refresh();
                    } else {
                        panel.showError("Cập nhật thất bại");
                    }
                }
            } catch (NumberFormatException ex) {
                panel.showError("Giá vé phải là số");
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi hệ thống: " + ex.getMessage());
            }
        }
    }

    public class XoaVeTauListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());
                if (id < 1) {
                    return;
                }

                if (panel.showConfirm("Bạn có muốn xóa" + id + "không ?")) {
                    if (dao.deleteVeTau(id)) {
                        panel.showMessage("Xóa thành công");
                        refresh();
                    } else {
                        panel.showError("Xóa thất bại");
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi hệ thống: " + ex.getMessage());
            }
        }
    }

    public class ResetFormListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            panel.resetForm();
        }
    }

    public class RefreshListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            refresh();
        }
    }

    public class TableMouseClickListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            panel.startEditMode();
            selectedRow = panel.getTable().getSelectedRow();
            if (selectedRow == -1) {
                return;
            }
            try {
                Object value = model.getValueAt(selectedRow, 0);
                if (value == null) {
                    return;
                }
                int id = Integer.parseInt(value.toString());
                List<VeTauResponse> list = dao.getAllVeTau();
                VeTauResponse selectedVT = null;
                for (VeTauResponse vt : list) {
                    if (vt.getId() == id) {
                        selectedVT = vt;
                        break;
                    }
                }
                panel.setMaVe(model.getValueAt(selectedRow, 1).toString());
                panel.setIdKhachHang(selectedVT.getIdKhachHang());
                panel.setIdLichTrinh(selectedVT.getIdLichTrinh());
                panel.setIdGhe(selectedVT.getIdGhe());
                panel.setIdNhanVien(selectedVT.getIdNhanVien());
                String ngayDatVe = model.getValueAt(selectedRow, 6).toString();
                try {
                    panel.setNgayDatVe(LocalDateTime.parse(ngayDatVe));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    panel.showError("Lỗi khi chuyển đổi ngày tháng: " + ex.getMessage());
                }
                panel.setGiaVe(model.getValueAt(selectedRow, 7).toString());
                panel.setTrangThai(model.getValueAt(selectedRow, 8).toString());
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

