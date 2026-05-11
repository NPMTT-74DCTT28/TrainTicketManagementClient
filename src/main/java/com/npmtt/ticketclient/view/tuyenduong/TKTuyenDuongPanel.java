package com.npmtt.ticketclient.view.tuyenduong;


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

public class TKTuyenDuongPanel extends BasePanel {
    private JTextField fieldTimkiem;
    private DefaultTableModel model;
    private JButton btnTimkiem, btnReset;
    private JTable table;

    public TKTuyenDuongPanel() {
        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new BorderLayout());
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0, 123, 255));
        titlePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        JLabel title = new JLabel("Tìm Kiếm Tuyến Đường");
        title.setSize(new Dimension(200, 80));
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titlePanel.add(title);

        JPanel panelTop = new JPanel(new BorderLayout(0, 5));
        JPanel panelFrom = new JPanel(new GridLayout(1, 2, 5, 5));
        panelFrom.setBorder(new EmptyBorder(5, 5, 5, 5));

        fieldTimkiem = new JTextField();
        panelFrom.add(createInputField("Mã / Tên Tuyến", fieldTimkiem, Color.WHITE));

        btnTimkiem = createStyledButton("Tìm Kiếm", new Dimension(100, 40), new Color(0, 123, 255), Color.BLACK);
        btnReset = createStyledButton("Reset", new Dimension(100, 40), new Color(108, 117, 125), Color.BLACK);
        JButton[] btn = {btnTimkiem, btnReset};

        panelTop.add(titlePanel, BorderLayout.NORTH);
        panelTop.add(panelFrom);
        panelTop.add(createButtonField(btn, Color.WHITE), BorderLayout.SOUTH);

        String[] columNames = {"Mã Tuyến", "Tên Tuyến", "Ga Đi", "Ga Đến", "Khoảng Cách (km)", "Giá Cơ Bản"};
        model = new DefaultTableModel(columNames, 0);

        table = new JTable(model);
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(SECONDARY_COLOR);
        tableHeader.setForeground(Color.BLACK);
        tableHeader.setOpaque(false);
        tableHeader.setFont(FONT_PLAIN);

        TitledBorder tableBorder = new TitledBorder(new LineBorder(Color.LIGHT_GRAY), "Danh sách Tuyến Đường",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, FONT_BOLD, Color.BLACK);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 5), tableBorder));
        scrollPane.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JPanel panelTable = new JPanel(new BorderLayout());
        panelTable.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelTable.setBackground(Color.WHITE);
        panelTable.add(scrollPane, BorderLayout.CENTER);

        add(panelTop, BorderLayout.NORTH);
        add(panelTable, BorderLayout.CENTER);
    }

    public String getTimkiem() {
        return fieldTimkiem.getText();
    }

    public JTable getTable() {
        return table;
    }

    public void TimKiemListener(ActionListener l) {
        btnTimkiem.addActionListener(l);
    }

    public void ResetListener(ActionListener l) {
        btnReset.addActionListener(l);
    }
}
