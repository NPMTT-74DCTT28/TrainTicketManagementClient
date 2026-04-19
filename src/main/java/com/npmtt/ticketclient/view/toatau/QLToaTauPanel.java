package com.npmtt.ticketclient.view.toatau;

import com.npmtt.ticketclient.view.BasePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public final class QLToaTauPanel extends BasePanel {

    private JTextField fieldMaToa;
    private JComboBox<Object> boxTau;
    private JComboBox<Object> boxLoaiToa;

    private JButton buttonThem, buttonSua, buttonXoa, buttonReset;
    private JTable table;

    public QLToaTauPanel() {
        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new BorderLayout());

        // Tiêu đề
        JPanel panelTitle = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelTitle.setBackground(PRIMARY_COLOR);
        JLabel labelTitle = new JLabel("QUẢN LÝ TOA TÀU");
        labelTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        labelTitle.setForeground(Color.WHITE);
        panelTitle.add(labelTitle);

        // Form nhập liệu
        JPanel panelForm = new JPanel(new GridLayout(1, 3, 10, 5));
        panelForm.setBorder(new EmptyBorder(10, 5, 10, 5));
        panelForm.setBackground(Color.WHITE);

        fieldMaToa = new JTextField();
        panelForm.add(createInputField("Mã toa:", fieldMaToa, Color.WHITE));

        boxTau = new JComboBox<>();
        panelForm.add(createInputField("Thuộc tàu:", boxTau, Color.WHITE));

        boxLoaiToa = new JComboBox<>();
        panelForm.add(createInputField("Loại toa:", boxLoaiToa, Color.WHITE));

        // Nút chức năng
        buttonThem = createStyledButton("Thêm", new Dimension(80, 40), PRIMARY_COLOR, Color.WHITE);
        buttonSua = createStyledButton("Sửa", new Dimension(80, 40), new Color(255, 193, 7), Color.BLACK);
        buttonSua.setEnabled(false);
        buttonXoa = createStyledButton("Xóa", new Dimension(80, 40), new Color(220, 53, 69), Color.WHITE);
        buttonXoa.setEnabled(false);
        buttonReset = createStyledButton("Reset", new Dimension(80, 40), new Color(108, 117, 125), Color.WHITE);

        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelButtons.setBackground(Color.WHITE);
        panelButtons.add(buttonThem);
        panelButtons.add(buttonSua);
        panelButtons.add(buttonXoa);
        panelButtons.add(buttonReset);

        // Ghép phần trên
        JPanel panelTop = new JPanel(new BorderLayout(0, 5));
        panelTop.setBackground(Color.WHITE);
        panelTop.add(panelTitle, BorderLayout.NORTH);
        panelTop.add(panelForm, BorderLayout.CENTER);
        panelTop.add(panelButtons, BorderLayout.SOUTH);

        // Bảng hiển thị
        String[] columns = {"ID", "Mã Toa", "Thuộc Tàu", "Loại Toa"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        scrollPane.setBackground(Color.WHITE);

        add(panelTop, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    // --- Getter cho dữ liệu form ---
    public String getMaToa() {
        return fieldMaToa.getText().trim();
    }

    public void setMaToa(String maToa) {
        fieldMaToa.setText(maToa);
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

    // --- Chế độ chỉnh sửa ---
    public void startEditMode() {
        fieldMaToa.setEnabled(false);
        fieldMaToa.setBackground(new Color(240, 240, 240));
        buttonThem.setEnabled(false);
        buttonSua.setEnabled(true);
        buttonXoa.setEnabled(true);
        buttonReset.setEnabled(true);
    }

    public void resetForm() {
        fieldMaToa.setEnabled(true);
        fieldMaToa.setText("");
        fieldMaToa.setBackground(Color.WHITE);
        if (boxTau.getItemCount() > 0) boxTau.setSelectedIndex(0);
        if (boxLoaiToa.getItemCount() > 0) boxLoaiToa.setSelectedIndex(0);
        buttonThem.setEnabled(true);
        buttonSua.setEnabled(false);
        buttonXoa.setEnabled(false);
        table.clearSelection();
    }

    // --- Gắn listener ---
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
}