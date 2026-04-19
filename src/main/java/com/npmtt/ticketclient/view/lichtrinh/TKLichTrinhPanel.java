package com.npmtt.ticketclient.view.lichtrinh;



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
import com.npmtt.ticketclient.enums.TrangThaiLichTrinh;

public class TKLichTrinhPanel extends BasePanel {

    private JTextField fieldTuKhoa;
    private JComboBox<Object> boxTau;
    private JComboBox<Object> boxTuyenDuong;
    private JComboBox<Object> boxTrangThai;

    private JButton buttonTimKiem, buttonReset, buttonLamMoi;
    private JTable table;

    public TKLichTrinhPanel() {
        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new BorderLayout(0, 0));
        setBackground(Color.WHITE);

        JPanel panelTop = new JPanel(new BorderLayout(5, 5));
        panelTop.setBackground(Color.WHITE);

        JPanel panelTitle = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panelTitle.setBackground(PRIMARY_COLOR);
        JLabel labelTitle = new JLabel("TRA CỨU LỊCH TRÌNH");
        labelTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        labelTitle.setForeground(Color.WHITE);
        panelTitle.add(labelTitle);

        JPanel panelForm = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelForm.setBackground(Color.WHITE);


        fieldTuKhoa = new JTextField();
        panelForm.add(createCompactInputField("Mã lịch trình", fieldTuKhoa));

        boxTau = new JComboBox<>();
        boxTau.setPrototypeDisplayValue("--------------------");
        panelForm.add(createCompactInputField("Tàu", boxTau));

        boxTuyenDuong = new JComboBox<>();
        boxTuyenDuong.setPrototypeDisplayValue("--------------------");
        panelForm.add(createCompactInputField("Tuyến đường", boxTuyenDuong));

        boxTrangThai = new JComboBox<>();
        boxTrangThai.addItem("Tất cả");
        for (TrangThaiLichTrinh tt : TrangThaiLichTrinh.values()) {
            boxTrangThai.addItem(tt.toString());
        }
        panelForm.add(createCompactInputField("Trạng thái", boxTrangThai));


        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelButtons.setBackground(Color.WHITE);

        buttonTimKiem = createStyledButton("Tìm kiếm", new Dimension(100, 40), PRIMARY_COLOR, Color.WHITE);
        buttonReset = createStyledButton("Reset form", new Dimension(110, 40), PRIMARY_COLOR, Color.WHITE);
        buttonLamMoi = createStyledButton("Làm mới", new Dimension(100, 40), PRIMARY_COLOR, Color.WHITE);

        panelButtons.add(buttonTimKiem);
        panelButtons.add(buttonReset);
        panelButtons.add(buttonLamMoi);

        panelTop.add(panelTitle, BorderLayout.NORTH);
        panelTop.add(panelForm, BorderLayout.CENTER);
        panelTop.add(panelButtons, BorderLayout.SOUTH);

        Object[] columns = new Object[]{
                "ID", "Mã LT", "Tàu", "Tuyến đường", "Ngày đi", "Ngày đến", "Trạng thái"
        };
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        table = new JTable(model);
        table.setRowHeight(25);

        TableColumnModel columnModel = table.getColumnModel();
        table.removeColumn(columnModel.getColumn(0));

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(SECONDARY_COLOR);
        tableHeader.setForeground(Color.BLACK);
        tableHeader.setOpaque(false);
        tableHeader.setFont(FONT_PLAIN);

        JScrollPane scrollPane = new JScrollPane(table);

        TitledBorder tableBorder = new TitledBorder(new LineBorder(Color.LIGHT_GRAY), "Kết quả tìm kiếm",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, FONT_BOLD, Color.BLACK);

        scrollPane.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), tableBorder));
        scrollPane.setForeground(Color.BLACK);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setFont(FONT_PLAIN);

        JPanel panelTable = new JPanel(new BorderLayout());
        panelTable.setBorder(new EmptyBorder(0, 5, 5, 5));
        panelTable.setBackground(Color.WHITE);
        panelTable.add(scrollPane, BorderLayout.CENTER);

        add(panelTop, BorderLayout.NORTH);
        add(panelTable, BorderLayout.CENTER);
    }


    private JComponent createCompactInputField(String labelText, JComponent component) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(260, 50));

        JLabel label = new JLabel(labelText);
        label.setFont(FONT_PLAIN);
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(90, 30));
        panel.add(label);

        component.setForeground(Color.BLACK);
        component.setPreferredSize(new Dimension(150, 30));
        panel.add(component);

        return panel;
    }

    public String getTuKhoa() {
        return fieldTuKhoa.getText().trim();
    }

    public JComboBox<Object> getBoxTau() {
        return boxTau;
    }

    public JComboBox<Object> getBoxTuyenDuong() {
        return boxTuyenDuong;
    }

    public String getTrangThai() {
        Object selected = boxTrangThai.getSelectedItem();
        if (selected == null || selected.toString().equalsIgnoreCase("Tất cả")) return null;
        return selected.toString();
    }

    public JTable getTable() {
        return table;
    }

    public void resetForm() {
        fieldTuKhoa.setText("");
        if (boxTau.getItemCount() > 0) boxTau.setSelectedIndex(0);
        if (boxTuyenDuong.getItemCount() > 0) boxTuyenDuong.setSelectedIndex(0);
        if (boxTrangThai.getItemCount() > 0) boxTrangThai.setSelectedIndex(0);
    }

    public void addTimKiemListener(ActionListener l) {
        buttonTimKiem.addActionListener(l);
    }

    public void addResetFormListener(ActionListener l) {
        buttonReset.addActionListener(l);
    }

    public void addLamMoiListener(ActionListener l) {
        buttonLamMoi.addActionListener(l);
    }
}