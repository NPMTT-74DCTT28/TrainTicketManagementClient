package com.npmtt.ticketclient.view.lichtrinh;

import com.npmtt.ticketclient.dto.request.LichTrinhRequest;
import com.npmtt.ticketclient.dto.response.TuyenDuongResponse;
import com.npmtt.ticketclient.dto.tau.TauDTO;
import com.npmtt.ticketclient.enums.TrangThaiLichTrinh;
import com.npmtt.ticketclient.util.DinhDang;
import com.npmtt.ticketclient.view.BasePanel;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class QLLichTrinhPanel extends BasePanel {

    private JTextField fieldMaLichTrinh;
    private JComboBox<TauDTO> boxTau;
    private JComboBox<TuyenDuongResponse> boxTuyenDuong;
    private JSpinner spinnerNgayDi;
    private JSpinner spinnerNgayDen;
    private JComboBox<TrangThaiLichTrinh> boxTrangThai;

    private JButton buttonThem, buttonSua, buttonXoa, buttonReset;
    private JTable table;
    private boolean isEditMode = false;

    public QLLichTrinhPanel() {
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
        JLabel labelTitle = new JLabel("QUẢN LÝ LỊCH TRÌNH CHẠY TÀU");
        labelTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        labelTitle.setForeground(Color.WHITE);
        panelTitle.add(labelTitle);

        JPanel panelForm = new JPanel(new GridLayout(3, 2, 10, 10));
        panelForm.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelForm.setBackground(Color.WHITE);

        fieldMaLichTrinh = new JTextField();
        panelForm.add(createInputField("Mã lịch trình", fieldMaLichTrinh, Color.WHITE));

        boxTau = new JComboBox<>();
        panelForm.add(createInputField("Tàu", boxTau, Color.WHITE));

        boxTuyenDuong = new JComboBox<>();
        panelForm.add(createInputField("Tuyến đường", boxTuyenDuong, Color.WHITE));

        boxTrangThai = new JComboBox<>(TrangThaiLichTrinh.values());
        panelForm.add(createInputField("Trạng thái", boxTrangThai, Color.WHITE));

        spinnerNgayDi = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editorDi = new JSpinner.DateEditor(spinnerNgayDi, DinhDang.DATE_TIME_VN);
        spinnerNgayDi.setEditor(editorDi);
        panelForm.add(createInputField("Thời gian đi", spinnerNgayDi, Color.WHITE));

        spinnerNgayDen = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editorDen = new JSpinner.DateEditor(spinnerNgayDen, DinhDang.DATE_TIME_VN);
        spinnerNgayDen.setEditor(editorDen);
        panelForm.add(createInputField("Thời gian đến", spinnerNgayDen, Color.WHITE));

        buttonThem = createStyledButton("Thêm", new Dimension(80, 40), PRIMARY_COLOR, Color.WHITE);
        buttonSua = createStyledButton("Sửa", new Dimension(80, 40), new Color(200, 200, 40), Color.WHITE);
        buttonSua.setEnabled(false);
        buttonXoa = createStyledButton("Xoá", new Dimension(80, 40), Color.RED, Color.white);
        buttonXoa.setEnabled(false);
        buttonReset = createStyledButton("Reset form", new Dimension(110, 40), PRIMARY_COLOR, Color.WHITE);

        JButton[] buttons = {buttonThem, buttonSua, buttonXoa, buttonReset};

        panelTop.add(panelTitle, BorderLayout.NORTH);
        panelTop.add(panelForm, BorderLayout.CENTER);
        panelTop.add(createButtonField(buttons, Color.WHITE), BorderLayout.SOUTH);

        Object[] columns = new Object[]{"ID", "Mã LT", "Tàu", "Tuyến đường", "Ngày đi", "Ngày đến", "Trạng thái"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

        table = new JTable(tableModel);
        table.setRowHeight(25);

        TableColumnModel columnModel = table.getColumnModel();
        TableColumn columnId = columnModel.getColumn(0);
        table.removeColumn(columnId);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(SECONDARY_COLOR);
        tableHeader.setForeground(Color.BLACK);
        tableHeader.setOpaque(false);
        tableHeader.setFont(FONT_PLAIN);

        JScrollPane scrollPane = new JScrollPane(table);

        TitledBorder tableBorder = new TitledBorder(new LineBorder(Color.LIGHT_GRAY), "Danh sách lịch trình",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, FONT_BOLD, Color.BLACK);
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

    public String getMaLichTrinh() {
        return fieldMaLichTrinh.getText().trim();
    }

    public void setMaLichTrinh(String text) {
        fieldMaLichTrinh.setText(text);
    }

    public JComboBox<TauDTO> getBoxTau() {
        return boxTau;
    }

    public void setTau(int idTau) {
        for (int i = 0; i < boxTau.getItemCount(); i++) {
            TauDTO t = boxTau.getItemAt(i);
            if (t.getId() == idTau) {
                boxTau.setSelectedIndex(i);
                return;
            }
        }
    }

    public JComboBox<TuyenDuongResponse> getBoxTuyenDuong() {
        return boxTuyenDuong;
    }

    public void setTuyenDuong(int idTuyen) {
        for (int i = 0; i < boxTuyenDuong.getItemCount(); i++) {
            TuyenDuongResponse t = boxTuyenDuong.getItemAt(i);
            if (t.getId() == idTuyen) {
                boxTuyenDuong.setSelectedIndex(i);
                return;
            }
        }
    }

    public LocalDateTime getNgayDi() {
        Date date = (Date) spinnerNgayDi.getValue();
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public void setNgayDi(LocalDateTime ldt) {
        if (ldt == null) spinnerNgayDi.setValue(new Date());
        else spinnerNgayDi.setValue(Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant()));
    }

    public LocalDateTime getNgayDen() {
        Date date = (Date) spinnerNgayDen.getValue();
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public void setNgayDen(LocalDateTime ldt) {
        if (ldt == null) spinnerNgayDen.setValue(new Date());
        else spinnerNgayDen.setValue(Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant()));
    }

    public String getTrangThai() {
        return boxTrangThai.getSelectedItem().toString();
    }

    public void setTrangThai(String status) {
        for (int i = 0; i < boxTrangThai.getItemCount(); i++) {
            TrangThaiLichTrinh item = boxTrangThai.getItemAt(i);
            if (item.toString().equals(status)) {
                boxTrangThai.setSelectedIndex(i);
                return;
            }
        }
    }

    public LichTrinhRequest getLichTrinhFromForm() {
        String ma = getMaLichTrinh();
        TauDTO tau = (TauDTO) boxTau.getSelectedItem();
        TuyenDuongResponse tuyen = (TuyenDuongResponse) boxTuyenDuong.getSelectedItem();
        String di = getNgayDi().toString();
        String den = getNgayDen().toString();
        String status = getTrangThai();

        if (tau == null || tuyen == null) return null;

        return new LichTrinhRequest(ma, tau.getId(), tuyen.getId(), di, den, status);
    }

    public void startEditMode() {
        isEditMode = true;
        fieldMaLichTrinh.setEnabled(false);
        buttonThem.setEnabled(false);
        buttonSua.setEnabled(true);
        buttonXoa.setEnabled(true);
        buttonReset.setEnabled(true);
    }

    public void resetForm() {
        isEditMode = false;
        fieldMaLichTrinh.setEnabled(true);
        fieldMaLichTrinh.setText("");
        if (boxTau.getItemCount() > 0) boxTau.setSelectedIndex(0);
        if (boxTuyenDuong.getItemCount() > 0) boxTuyenDuong.setSelectedIndex(0);
        spinnerNgayDi.setValue(new Date());
        spinnerNgayDen.setValue(new Date());
        if (boxTrangThai.getItemCount() > 0) boxTrangThai.setSelectedIndex(0);

        buttonThem.setEnabled(true);
        buttonSua.setEnabled(false);
        buttonXoa.setEnabled(false);
        buttonReset.setEnabled(true);
        table.clearSelection();
    }

    public void addThemListener(ActionListener l) {
        buttonThem.addActionListener(l);
    }

    public void addSuaListener(ActionListener l) {
        buttonSua.addActionListener(l);
    }

    public void addXoaListener(ActionListener l) {
        buttonXoa.addActionListener(l);
    }

    public void addResetListener(ActionListener l) {
        buttonReset.addActionListener(l);
    }

    public void addTableMouseListener(MouseListener l) {
        table.addMouseListener(l);
    }

    public JTable getTable() {
        return table;
    }
}