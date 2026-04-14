package com.npmtt.ticketclient.controller.tuyenduong;

import com.npmtt.ticketclient.apiclient.GaTauApiClient;
import com.npmtt.ticketclient.apiclient.TuyenDuongApiclient;
import com.npmtt.ticketclient.dto.gatau.GaTauDTO;
import com.npmtt.ticketclient.dto.request.TuyenDuongRequest;
import com.npmtt.ticketclient.dto.response.TuyenDuongResponse;
import com.npmtt.ticketclient.view.tuyenduong.QLTuyenDuongPanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;

public class QLTuyenDuongController {
    private final QLTuyenDuongPanel panel;
    private final TuyenDuongApiclient dao;
    private final DefaultTableModel model;
    private HashMap<Integer, String> mapGaTau = new HashMap<>();
    private int selectedRow;

    public QLTuyenDuongController(QLTuyenDuongPanel panel) {
        this.dao = new TuyenDuongApiclient();
        this.panel = panel;
        panel.AddTuyen(new AddTuyen());
        panel.EditTuyen(new EditTuyen());
        panel.RemoveTuyen(new RemoveTuyen());
        panel.ResetTuyen(new ResetTuyen());
        panel.TableMouseClickListener(new TableMouseClickListener());
        model = (DefaultTableModel) panel.getTable().getModel();
        refresh();
    }

    public void refresh() {
        panel.resetForm();
        try {
            GaTauApiClient gaDAO = new GaTauApiClient();
            List<GaTauDTO> listgatau = gaDAO.getAllGaTau();

            mapGaTau.clear();
            for (GaTauDTO ga : listgatau) {
                mapGaTau.put(ga.getId(), ga.getMaGa() + " - " + ga.getTenGa());
            }

            panel.setGatau(listgatau);

            List<TuyenDuongResponse> list = dao.getAllTuyenDuong();
            model.setRowCount(0);
            for (TuyenDuongResponse d : list) {
                String tenGaDi = mapGaTau.getOrDefault(d.getIdGaDi(), String.valueOf(d.getIdGaDi()));
                String tenGaDen = mapGaTau.getOrDefault(d.getIdGaDen(), String.valueOf(d.getIdGaDen()));

                model.addRow(new Object[]{
                        d.getId(),
                        d.getMaTuyen(),
                        d.getTenTuyen(),
                        tenGaDi,
                        tenGaDen,
                        d.getKhoangCachKm(),
                        d.getGiaCoBan()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.fireTableDataChanged();
    }

    public class AddTuyen implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                TuyenDuongRequest td = panel.getTuyenDuong();

                if (td.getMaTuyen().isEmpty() || td.getTenTuyen().isEmpty()) {
                    panel.showError("Vui lòng nhập đầy đủ thông tin!");
                    return;
                }

                if (td.getKhoangCach() <= 0 || td.getGiaCoBan() <= 0) {
                    panel.showError("Khoảng cách / giá cơ bản phải lớn hon 0!");
                    return;
                }
                if (td.getIdGaDi() == td.getIdGaDen()) {
                    panel.showError("Ga đi và Ga đến không được trùng nhau!");
                    return;
                }
                if (dao.createTuyenDuong(td)!=null) {
                    panel.showMessage("Thêm tuyến đường thành công!");
                    panel.resetForm();
                    refresh();
                } else {
                    panel.showError("Thêm thất bại!");
                }
            } catch (NumberFormatException ex) {
                panel.showError("Khoảng cách / Giá cơ bản phải là số!");
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi không xác định: " + ex.getMessage());
            }
        }
    }

    public class EditTuyen implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                TuyenDuongRequest td = panel.getTuyenDuong();
                td.setId(Integer.parseInt(model.getValueAt(selectedRow, 0).toString()));
                if (td.getMaTuyen().isEmpty() || td.getTenTuyen().isEmpty()) {
                    panel.showError("Vui lòng nhập đầy đủ thông tin!");
                    return;
                }
                if (td.getKhoangCach() <= 0 || td.getGiaCoBan() <= 0) {
                    panel.showError("Khoảng cách / giá cơ bản phải lớn hon 0!");
                    return;
                }
                if (td.getIdGaDi() == td.getIdGaDen()) {
                    panel.showError("Ga đi và Ga đến không được trùng nhau!");
                    return;
                }
                if (panel.showConfirm("Bạn có muốn cập nhật thông tin của" + td.getMaTuyen() + " không ?")) {
                    if (dao.updateTuyenDuong(td)!=null) {
                        panel.showMessage("Cập nhật thành công!");
                        refresh();
                    } else {
                        panel.showError("Cập nhật thất bại!");
                    }
                }
            } catch (NumberFormatException ex) {
                panel.showError("Khoảng cách / Giá cơ bản phải là số!");
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi hệ thống: " + ex.getMessage());
            }
        }
    }

    public class RemoveTuyen implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());
                if (id<1) {
                    return;
                }

                if (panel.showConfirm("Bạn có muốn xóa" + id + " không ?")) {
                    if (dao.deleteTuyenDuong(id)) {
                        panel.showMessage("Xóa thành công!");
                        refresh();
                    } else {
                        panel.showError("Xóa thất bại!");
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi hệ thống: " + ex.getMessage());
            }
        }
    }

    public class ResetTuyen implements ActionListener {
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
            try{
                Object value = model.getValueAt(selectedRow, 0);
                if (value == null) {
                    return;
                }
                int id = Integer.parseInt(value.toString());
                List<TuyenDuongResponse> list = dao.getAllTuyenDuong();
                TuyenDuongResponse selectedTD = null;
                for (TuyenDuongResponse td : list) {
                    if (td.getId() == id) {
                        selectedTD = td;
                        break;
                    }
                }
                panel.setMaTuyen(model.getValueAt(selectedRow, 1).toString());
                panel.setTenTuyen(model.getValueAt(selectedRow, 2).toString());
                panel.setGadi(selectedTD.getIdGaDi());
                panel.setGaden(selectedTD.getIdGaDen());
                panel.setKhoangcach(model.getValueAt(selectedRow, 5).toString());
                panel.setGiaCB(model.getValueAt(selectedRow, 6).toString());
            }catch (Exception ex){
                ex.printStackTrace();
            };

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