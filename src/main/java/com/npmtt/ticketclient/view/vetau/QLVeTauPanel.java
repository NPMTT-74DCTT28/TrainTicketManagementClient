package com.npmtt.ticketclient.view.vetau;

import com.npmtt.ticketclient.dto.request.VeTauRequest;
import com.npmtt.ticketclient.dto.response.GheResponse;
import com.npmtt.ticketclient.dto.response.KhachHangResponse;
import com.npmtt.ticketclient.dto.response.LichTrinhResponse;
import com.npmtt.ticketclient.enums.TrangThaiVe;
import com.npmtt.ticketclient.util.SessionManager;
import com.npmtt.ticketclient.view.BasePanel;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.List;

public final class QLVeTauPanel extends BasePanel {
    private JTextField fieldMaVe;
    private JComboBox<KhachHangResponse> boxKhachHang;
    private JComboBox<LichTrinhResponse> boxLichTrinh;
    private JComboBox<GheResponse> boxGhe;
    private JTextField fieldGiaVe;
    private JComboBox<Object> boxTrangThai;
    private JButton buttonThem, buttonSua, buttonXoa, buttonReset, buttonRefresh;
    private DefaultTableModel model;
    private JTable table;

    public QLVeTauPanel() {
        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new BorderLayout(0, 0));
        setBackground(Color.WHITE);

        JPanel panelTop = new JPanel(new BorderLayout(0, 5));
        panelTop.setBackground(Color.WHITE);

        JPanel panelTitle = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelTitle.setBackground(PRIMARY_COLOR);
        JLabel labelTitle = new JLabel("QUẢN LÝ THÔNG TIN VÉ TÀU");
        labelTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        labelTitle.setForeground(Color.WHITE);
        panelTitle.add(labelTitle);

        JPanel panelForm = new JPanel(new GridLayout(3, 3, 5, 5));
        panelForm.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelForm.setBackground(Color.WHITE);

        fieldMaVe = new JTextField();
        panelForm.add(createInputField("Mã vé", fieldMaVe, Color.WHITE));

        boxKhachHang = new JComboBox<>();
        panelForm.add(createInputField("Khách hàng", boxKhachHang, Color.WHITE));

        boxLichTrinh = new JComboBox<>();
        panelForm.add(createInputField("Lịch trình", boxLichTrinh, Color.WHITE));

        boxGhe = new JComboBox<>();
        panelForm.add(createInputField("Ghế", boxGhe, Color.WHITE));

        fieldGiaVe = new JTextField();
        fieldGiaVe.setEditable(false);
        fieldGiaVe.setText(String.valueOf(0));
        panelForm.add(createInputField("Giá vé", fieldGiaVe, Color.WHITE));

        boxTrangThai = new JComboBox<>(TrangThaiVe.values());
        panelForm.add(createInputField("Trạng thái", boxTrangThai, Color.WHITE));

        buttonThem = createStyledButton("Thêm", new Dimension(80, 40), PRIMARY_COLOR, Color.WHITE);
        buttonThem.setEnabled(true);
        buttonSua = createStyledButton("Sửa", new Dimension(80, 40), new Color(200, 200, 40), Color.WHITE);
        buttonSua.setEnabled(false);
        buttonXoa = createStyledButton("Xoá", new Dimension(80, 40), Color.RED, Color.white);
        buttonXoa.setEnabled(false);
        buttonReset = createStyledButton("Reset form", new Dimension(110, 40), PRIMARY_COLOR, Color.WHITE);
        buttonReset.setEnabled(true);
        buttonRefresh = createStyledButton("Làm mới", new Dimension(100, 40), PRIMARY_COLOR, Color.WHITE);
        buttonRefresh.setEnabled(true);

        JButton[] buttons = {buttonThem, buttonSua, buttonXoa, buttonReset, buttonRefresh};

        panelTop.add(panelTitle, BorderLayout.NORTH);
        panelTop.add(panelForm);
        panelTop.add(createButtonField(buttons, Color.white), BorderLayout.SOUTH);

        Object[] columns = new Object[]{"Id", "Mã vé", "Khách hàng", "Lịch trình", "Ghế", "Nhân viên bán vé", "Ngày đặt vé", "Giá vé", "Trạng thái"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.removeColumn(columnModel.getColumn(0));

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(SECONDARY_COLOR);
        tableHeader.setForeground(Color.BLACK);
        tableHeader.setOpaque(false);
        tableHeader.setFont(FONT_PLAIN);

        JScrollPane scrollPane = new JScrollPane(table);

        TitledBorder tableBorder = new TitledBorder(new LineBorder(Color.LIGHT_GRAY), "Danh sách khách hàng", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, FONT_BOLD, Color.BLACK);
        scrollPane.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 5), tableBorder));
        scrollPane.setForeground(Color.BLACK);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setFont(FONT_PLAIN);

        JPanel panelTable = new JPanel(new BorderLayout());
        panelTable.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelTable.setBackground(Color.WHITE);
        panelTable.add(scrollPane, BorderLayout.CENTER);

        add(panelTop, BorderLayout.NORTH);
        add(panelTable, BorderLayout.CENTER);
    }

    private DefaultComboBoxModel<Object> createComboBoxModel(Object[] values) {
        DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<>();

        model.addElement("Tất cả");

        for (Object value : values) {
            model.addElement(value);
        }

        return model;
    }

    public String getMaVe() { return fieldMaVe.getText().trim();}

    public void setMaVe(String maVe) { fieldMaVe.setText(maVe != null ? maVe : "");}

    public int getIdKhachHang(){
        KhachHangResponse selected = (KhachHangResponse) boxKhachHang.getSelectedItem();
        if (selected != null) {
            return selected.getId();
        }
        return 0;
    }

    public void setIdKhachHang(int idKhachHang) {
        for (int i = 0; i < boxKhachHang.getItemCount(); i++) {
            KhachHangResponse kh = boxKhachHang.getItemAt(i);
            if (kh.getId() == idKhachHang) {
                boxKhachHang.setSelectedIndex(i);
                break;
            }
        }
    }

    public int getIdLichTrinh(){
        LichTrinhResponse selected = (LichTrinhResponse) boxLichTrinh.getSelectedItem();
        if (selected != null) {
            return selected.getId();
        }
        return 0;
    }

    public void setIdLichTrinh(int idLichTrinh) {
        for (int i = 0; i < boxLichTrinh.getItemCount(); i++) {
            LichTrinhResponse lt = boxLichTrinh.getItemAt(i);
            if (lt.getId() == idLichTrinh) {
                boxLichTrinh.setSelectedIndex(i);
                break;
            }
        }
    }

    public int getIdGhe(){
        GheResponse selected = (GheResponse) boxGhe.getSelectedItem();
        if (selected != null) {
            return selected.getId();
        }
        return 0;
    }

    public void setIdGhe(int idGhe) {
        for (int i = 0; i < boxGhe.getItemCount(); i++) {
            GheResponse gh = boxGhe.getItemAt(i);
            if (gh.getId() == idGhe) {
                boxGhe.setSelectedIndex(i);
                break;
            }
        }
    }

    public double getGiaVe() {
        if (!fieldGiaVe.getText().trim().isEmpty()) {
            return Double.parseDouble(fieldGiaVe.getText().trim());
        }
        return 0;
    }

    public void setGiaVe(String giaVe) { fieldGiaVe.setText(giaVe != null ? giaVe : "");}

    public String getTrangThai() {
        Object selectedItem = boxTrangThai.getSelectedItem();
        if (selectedItem != null && !("Tất cả").equalsIgnoreCase(selectedItem.toString())) {
            return selectedItem.toString();
        }
        return null;
    }

    public void setTrangThai(String label) {
        if (label != null) {
            for (int i = 0; i < boxTrangThai.getItemCount(); i++) {
                if (boxTrangThai.getItemAt(i).toString().equals(label)) {
                    boxTrangThai.setSelectedIndex(i);
                    return;
                }
            }
        }
    }

    public JTable getTable() {
        return table != null ? table : null;
    }

    public VeTauRequest getVeTauFromForm(){
        String maVe = getMaVe();
        int idKhachHang = getIdKhachHang();
        int idLichTrinh = getIdLichTrinh();
        int idGhe = getIdGhe();
        int idNhanVien = SessionManager.getCurrentUser().getId();
        double giaVe = getGiaVe();
        String trangThai = getTrangThai();

        return new VeTauRequest(maVe, idKhachHang, idLichTrinh, idGhe, idNhanVien, giaVe, trangThai);
    }

    public void startEditMode() {
        fieldMaVe.setEditable(false);

        buttonThem.setEnabled(false);
        buttonSua.setEnabled(true);
        buttonXoa.setEnabled(true);
        buttonReset.setEnabled(true);
        buttonRefresh.setEnabled(true);
    }

    public void resetForm() {
        fieldMaVe.setEditable(true);
        fieldMaVe.setText("");
        boxKhachHang.setSelectedIndex(0);
        boxLichTrinh.setSelectedIndex(0);
        boxGhe.setSelectedIndex(0);
        if (boxTrangThai.getItemCount() > 0) {
            boxTrangThai.setSelectedIndex(0);
        }

        buttonThem.setEnabled(true);
        buttonSua.setEnabled(false);
        buttonXoa.setEnabled(false);
        buttonReset.setEnabled(true);
        buttonRefresh.setEnabled(true);

        if (table != null) {
            table.clearSelection();
        }
    }

    public void setBoxKhachHang(List<KhachHangResponse> list) {
        boxKhachHang.removeAllItems();
        for (KhachHangResponse kh : list) {
            boxKhachHang.addItem(kh);
        }
    }
    public void setBoxLichTrinh(List<LichTrinhResponse> list) {
        boxLichTrinh.removeAllItems();
        for (LichTrinhResponse lt : list) {
            boxLichTrinh.addItem(lt);
        }
    }
    public void setBoxGhe(List<GheResponse> list) {
        boxGhe.removeAllItems();
        for (GheResponse g : list) {
            boxGhe.addItem(g);
        }
    }

    public void addThemVeTauListener(ActionListener l) {
        buttonThem.addActionListener(l);
    }

    public void addSuaVeTauListener(ActionListener l) {
        buttonSua.addActionListener(l);
    }

    public void addXoaVeTauListener(ActionListener l) {
        buttonXoa.addActionListener(l);
    }

    public void addResetFormListener(ActionListener l) {
        buttonReset.addActionListener(l);
    }

    public void addRefreshListener(ActionListener l) {
        buttonRefresh.addActionListener(l);
    }

    public void addTableMouseClickListener(MouseListener l) {
        table.addMouseListener(l);
    }

    public void addboxLichTrinhListener(ActionListener l) {
        boxLichTrinh.addActionListener(l);
    }

    public void addboxGheListener(ActionListener l) {
        boxGhe.addActionListener(l);
    }
}
