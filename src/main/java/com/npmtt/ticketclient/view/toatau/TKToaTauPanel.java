package com.npmtt.ticketclient.view.toatau;

import com.npmtt.ticketclient.view.BasePanel;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionListener;

public class TKToaTauPanel extends BasePanel {

    private JTextField fieldTuKhoa;
    private JComboBox<Object> boxTau;
    private JComboBox<Object> boxLoaiToa;

    private JButton buttonTimKiem, buttonReset, buttonLamMoi;
    private JTable table;

    public TKToaTauPanel() {
        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Tiêu đề
        JPanel panelTitle = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelTitle.setBackground(PRIMARY_COLOR);
        JLabel labelTitle = new JLabel("TRA CỨU TOA TÀU");
        labelTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        labelTitle.setForeground(Color.WHITE);
        panelTitle.add(labelTitle);

        // Form tìm kiếm
        JPanel panelForm = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelForm.setBackground(Color.WHITE);

        fieldTuKhoa = new JTextField(15);
        panelForm.add(createInputField("Mã toa", fieldTuKhoa, Color.WHITE));

        boxTau = new JComboBox<>();
        boxTau.setPrototypeDisplayValue("--------------------------");
        panelForm.add(createInputField("Thuộc tàu", boxTau, Color.WHITE));

        boxLoaiToa = new JComboBox<>();
        boxLoaiToa.setPrototypeDisplayValue("--------------------------");
        panelForm.add(createInputField("Loại toa", boxLoaiToa, Color.WHITE));

        // Nút chức năng
        buttonTimKiem = createStyledButton("Tìm kiếm", new Dimension(100, 40), PRIMARY_COLOR, Color.WHITE);
        buttonReset = createStyledButton("Reset form", new Dimension(110, 40), PRIMARY_COLOR, Color.WHITE);
        buttonLamMoi = createStyledButton("Làm mới", new Dimension(100, 40), PRIMARY_COLOR, Color.WHITE);

        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelButtons.setBackground(Color.WHITE);
        panelButtons.add(buttonTimKiem);
        panelButtons.add(buttonReset);
        panelButtons.add(buttonLamMoi);

        // Ghép phần trên
        JPanel panelTop = new JPanel(new BorderLayout(5, 5));
        panelTop.setBackground(Color.WHITE);
        panelTop.add(panelTitle, BorderLayout.NORTH);
        panelTop.add(panelForm, BorderLayout.CENTER);
        panelTop.add(panelButtons, BorderLayout.SOUTH);

        // Bảng kết quả
        String[] columns = {"ID", "Mã Toa", "Thuộc Tàu", "Loại Toa"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setRowHeight(25);
        // Ẩn cột ID
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(SECONDARY_COLOR);
        tableHeader.setForeground(Color.BLACK);
        tableHeader.setFont(FONT_PLAIN);

        JScrollPane scrollPane = new JScrollPane(table);
        TitledBorder tableBorder = new TitledBorder(new LineBorder(Color.LIGHT_GRAY), "Kết quả tìm kiếm",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, FONT_BOLD, Color.BLACK);
        scrollPane.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), tableBorder));
        scrollPane.setBackground(Color.WHITE);

        JPanel panelTable = new JPanel(new BorderLayout());
        panelTable.setBorder(new EmptyBorder(0, 5, 5, 5));
        panelTable.setBackground(Color.WHITE);
        panelTable.add(scrollPane, BorderLayout.CENTER);

        add(panelTop, BorderLayout.NORTH);
        add(panelTable, BorderLayout.CENTER);
    }

    // --- Getter dữ liệu ---
    public String getTuKhoa() {
        return fieldTuKhoa.getText().trim();
    }

    public JComboBox<Object> getBoxTau() {
        return boxTau;
    }

    public JComboBox<Object> getBoxLoaiToa() {
        return boxLoaiToa;
    }

    public JTable getTable() {
        return table;
    }

    public void resetForm() {
        fieldTuKhoa.setText("");
        if (boxTau.getItemCount() > 0) boxTau.setSelectedIndex(0);
        if (boxLoaiToa.getItemCount() > 0) boxLoaiToa.setSelectedIndex(0);
    }

    // --- Gắn listener ---
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