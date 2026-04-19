package com.npmtt.ticketclient.view.tau;

import com.npmtt.ticketclient.dto.tau.TauDTO;
import com.npmtt.ticketclient.view.BasePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public class QLTauPanel extends BasePanel {
    private JTextField fieldMaTau, fieldTenTau;
    private JButton buttonThem, buttonSua, buttonXoa, buttonReset;
    private JTable tblTau;
    private DefaultTableModel model;

    public QLTauPanel() {
        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new BorderLayout());

        JPanel panelHome = new JPanel();
        panelHome.setBackground(new Color(152, 251, 152));
        panelHome.setBorder(new EmptyBorder(5, 5, 5, 5));
        JLabel labelHome = new JLabel("Quản lý thông tin Tàu");
        labelHome.setSize(200, 80);
        labelHome.setFont(new Font("Segoe UI", Font.BOLD, 20));
        panelHome.add(labelHome);

        JPanel panelTop = new JPanel(new BorderLayout(0, 5));

        JPanel panelForm = new JPanel(new GridLayout(1, 2, 5, 5));
        panelForm.setBorder(new EmptyBorder(10, 5, 10, 5));

        fieldMaTau = new JTextField();
        panelForm.add(createInputField("Mã tàu: ", fieldMaTau, Color.WHITE));

        fieldTenTau = new JTextField();
        panelForm.add(createInputField("Tên tàu: ", fieldTenTau, Color.WHITE));

        buttonThem = createStyledButton("Thêm", new Dimension(80, 40), PRIMARY_COLOR, Color.WHITE);
        buttonThem.setEnabled(true);
        buttonSua = createStyledButton("Sửa", new Dimension(80, 40), new Color(20, 200, 40), Color.WHITE);
        buttonSua.setEnabled(false);
        buttonXoa = createStyledButton("Xoá", new Dimension(80, 40), Color.RED, Color.WHITE);
        buttonXoa.setEnabled(false);
        buttonReset = createStyledButton("Reset", new Dimension(110, 40), PRIMARY_COLOR, Color.WHITE);
        buttonReset.setEnabled(true);

        JButton[] buttons = {buttonThem, buttonSua, buttonXoa, buttonReset};

        panelTop.add(panelHome, BorderLayout.NORTH);
        panelTop.add(panelForm);
        panelTop.add(createButtonField(buttons, Color.WHITE), BorderLayout.SOUTH);

        model = new DefaultTableModel(new Object[]{"ID", "Mã tàu", "Tên tàu"}, 0);
        tblTau = new JTable(model);
        tblTau.removeColumn(tblTau.getColumnModel().getColumn(0)); // Ẩn cột ID

        add(panelTop, BorderLayout.NORTH);
        add(new JScrollPane(tblTau), BorderLayout.CENTER);
    }

    public void addButtonThemActionListener(ActionListener a) {
        buttonThem.addActionListener(a);
    }

    public void addButtonSuaActionListener(ActionListener a) {
        buttonSua.addActionListener(a);
    }

    public void addButtonXoaActionListener(ActionListener a) {
        buttonXoa.addActionListener(a);
    }

    public void addButtonResetActionListener(ActionListener a) {
        buttonReset.addActionListener(a);
    }

    public void addTableMouseClickListener(MouseListener l) {
        tblTau.addMouseListener(l);
    }

    public String getMaTau() {
        return fieldMaTau.getText().trim();
    }

    public void setMaTau(String ma) {
        fieldMaTau.setText(ma);
    }

    public String getTenTau() {
        return fieldTenTau.getText().trim();
    }

    public void setTenTau(String ten) {
        fieldTenTau.setText(ten);
    }

    public JTable getTable() {
        return tblTau;
    }

    public TauDTO getTauFromForm() {
        String maTau = getMaTau();
        String tenTau = getTenTau();

        return new TauDTO(maTau, tenTau);
    }

    public void startEditMode() {
        buttonThem.setEnabled(false);
        buttonSua.setEnabled(true);
        buttonXoa.setEnabled(true);
        buttonReset.setEnabled(true);
    }

    public void resetForm() {
        fieldMaTau.setEnabled(true);
        fieldMaTau.setText("");
        fieldMaTau.setBackground(Color.white);

        fieldTenTau.setText("");
        buttonThem.setEnabled(true);
        buttonSua.setEnabled(false);
        buttonXoa.setEnabled(false);
        buttonReset.setEnabled(true);
        if (tblTau != null) {
            tblTau.clearSelection();
        }
    }
}