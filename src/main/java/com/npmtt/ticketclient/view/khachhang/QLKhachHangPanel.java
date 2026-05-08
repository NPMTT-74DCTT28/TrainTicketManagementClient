package com.npmtt.ticketclient.view.khachhang;

import com.npmtt.ticketclient.dto.request.KhachHangRequest;
import com.npmtt.ticketclient.enums.GioiTinh;
import com.npmtt.ticketclient.util.DinhDang;
import com.npmtt.ticketclient.view.BasePanel;
import com.toedter.calendar.JDateChooser;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public final class QLKhachHangPanel extends BasePanel {
    private JTextField fieldCccd;
    private JTextField fieldHoTen;
    private JDateChooser chooserNgaySinh;
    private JComboBox<Object> boxGioiTinh;
    private JTextField fieldSdt;
    private JTextField fieldDiaChi;
    private JButton buttonThem, buttonSua, buttonXoa, buttonReset, buttonRefresh;
    private JTable table;
    private boolean isEditMode = false;

    public QLKhachHangPanel() {
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
        JLabel labelTitle = new JLabel("QUẢN LÝ THÔNG TIN KHÁCH HÀNG");
        labelTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        labelTitle.setForeground(Color.WHITE);
        panelTitle.add(labelTitle);

        JPanel panelForm = new JPanel(new GridLayout(3, 3, 5, 5));
        panelForm.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelForm.setBackground(Color.WHITE);

        fieldCccd = new JTextField();
        panelForm.add(createInputField("CCCD", fieldCccd, Color.WHITE));

        fieldHoTen = new JTextField();
        panelForm.add(createInputField("Họ tên", fieldHoTen, Color.WHITE));

        chooserNgaySinh = new JDateChooser();
        chooserNgaySinh.setDateFormatString(DinhDang.DATE_VN);
        chooserNgaySinh.setDate(new Date());
        chooserNgaySinh.setMaxSelectableDate(new Date(System.currentTimeMillis()));
        panelForm.add(createInputField("Ngày sinh", chooserNgaySinh, Color.WHITE));

        boxGioiTinh = new JComboBox<>(createComboBoxModel(GioiTinh.values()));
        panelForm.add(createInputField("Giới tính", boxGioiTinh, Color.WHITE));

        fieldSdt = new JTextField();
        panelForm.add(createInputField("SĐT", fieldSdt, Color.WHITE));

        fieldDiaChi = new JTextField();
        panelForm.add(createInputField("Địa chỉ", fieldDiaChi, Color.WHITE));

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

        Object[] columns = new Object[]{"Id", "CCCD", "Họ tên", "Ngày sinh", "Giới tính", "SĐT", "Địa chỉ"};
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

    public String getCccd() {
        return fieldCccd.getText().trim();
    }

    public void setCccd(String cccd) {
        fieldCccd.setText(cccd != null ? cccd : "");
    }

    public String getHoTen() {
        return fieldHoTen.getText().trim();
    }

    public void setHoTen(String hoTen) {
        fieldHoTen.setText(hoTen != null ? hoTen : "");
    }

    public LocalDate getNgaySinh() {
        if (chooserNgaySinh.getDate() != null) {
            return chooserNgaySinh.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        return null;
    }

    public void setNgaySinh(LocalDate localDate) {
        if (localDate != null) {
            chooserNgaySinh.setDate(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        } else {
            chooserNgaySinh.setDate(null);
        }
    }

    public String getGioiTinh() {
        Object selectedItem = boxGioiTinh.getSelectedItem();
        if (selectedItem != null && !("Tất cả").equalsIgnoreCase(selectedItem.toString())) {
            return selectedItem.toString();
        }
        return null;
    }

    public void setGioiTinh(String label) {
        if (label != null) {
            for (int i = 0; i < boxGioiTinh.getItemCount(); i++) {
                if (boxGioiTinh.getItemAt(i).toString().equals(label)) {
                    boxGioiTinh.setSelectedIndex(i);
                    return;
                }
            }
        }
    }

    public String getSdt() {
        return fieldSdt.getText().trim();
    }

    public void setSdt(String sdt) {
        fieldSdt.setText(sdt != null ? sdt : "");
    }

    public String getDiaChi() {
        return fieldDiaChi.getText().trim();
    }

    public void setDiaChi(String diaChi) {
        fieldDiaChi.setText(diaChi != null ? diaChi : "");
    }

    public JTable getTable() {
        return table != null ? table : null;
    }

    public String thongBaoLoiDauVao() {
        if (getCccd().isEmpty()) {
            fieldCccd.requestFocus();
            return "Cccd không được để trống.";
        }
        if (getCccd().length() > 20) {
            fieldCccd.requestFocus();
            return "Mã nhân viên quá dài (tối đa 20 ký tự).";
        }
        if (getHoTen().isEmpty()) {
            fieldHoTen.requestFocus();
            return "Họ tên không được để trống.";
        }
        if (getHoTen().length() > 50) {
            fieldHoTen.requestFocus();
            return "Họ tên quá dài (tối đa 50 ký tự)";
        }
        if (getNgaySinh() == null) {
            chooserNgaySinh.requestFocus();
            return "Vui lòng chọn ngày sinh.";
        }
        if (ChronoUnit.YEARS.between(getNgaySinh(), LocalDate.now()) < 18) {
            chooserNgaySinh.requestFocus();
            return "Nhân viên phải từ đủ 18 tuổi trở lên.";
        }
        if (ChronoUnit.YEARS.between(getNgaySinh(), LocalDate.now()) > 65) {
            chooserNgaySinh.requestFocus();
            return "Nhân viên đã quá tuổi lao động.";
        }
        if (getGioiTinh() == null) {
            boxGioiTinh.requestFocus();
            return "Vui lòng chọn giới tính.";
        }
        if (getSdt().isEmpty()) {
            fieldSdt.requestFocus();
            return "Số điện thoại không được để trống.";
        }
        if (!getSdt().matches(DinhDang.DINH_DANG_SDT)) {
            fieldSdt.requestFocus();
            return "Số điện thoại phải bắt đầu bằng số 0 và có 10 chữ số.";
        }
        if (getDiaChi().isEmpty()) {
            fieldDiaChi.requestFocus();
            return "Địa chỉ không được để trống.";
        }
        if (getDiaChi().length() > 255) {
            fieldDiaChi.requestFocus();
            return "Địa chỉ quá dài (tối đa 255 ký tự)";
        }
        return null;
    }

    public KhachHangRequest getKhachHangFromForm() {
        String cccd = getCccd();
        String hoTen = getHoTen();
        String ngaySinh = String.valueOf(getNgaySinh());
        String gioiTinh = getGioiTinh();
        String sdt = getSdt();
        String diaChi = getDiaChi();

        if (isEditMode) {
            return new KhachHangRequest(cccd, hoTen, ngaySinh, gioiTinh, sdt, diaChi);
        } else {
            return new KhachHangRequest(cccd, hoTen, ngaySinh, gioiTinh, sdt, diaChi);
        }
    }

    public void startEditMode() {
        isEditMode = true;
        fieldCccd.setEnabled(false);

        buttonThem.setEnabled(false);
        buttonSua.setEnabled(true);
        buttonXoa.setEnabled(true);
        buttonReset.setEnabled(true);
        buttonRefresh.setEnabled(true);
    }


    public void resetForm() {
        isEditMode = false;

        fieldCccd.setEnabled(true);
        fieldCccd.setText("");

        fieldHoTen.setText("");

        chooserNgaySinh.setDate(new Date());

        if (boxGioiTinh.getItemCount() > 0) {
            boxGioiTinh.setSelectedIndex(0);
        }

        fieldSdt.setText("");

        fieldDiaChi.setText("");

        buttonThem.setEnabled(true);
        buttonSua.setEnabled(false);
        buttonXoa.setEnabled(false);
        buttonReset.setEnabled(true);
        buttonRefresh.setEnabled(true);

        if (table != null) {
            table.clearSelection();
        }
    }

    public void addThemKhachHangListener(ActionListener l) {
        buttonThem.addActionListener(l);
    }

    public void addSuaKhachHangListener(ActionListener l) {
        buttonSua.addActionListener(l);
    }

    public void addXoaKhachHangListener(ActionListener l) {
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
}

