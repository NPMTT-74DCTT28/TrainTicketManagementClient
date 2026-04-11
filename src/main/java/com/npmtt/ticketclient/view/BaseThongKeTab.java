package com.npmtt.ticketclient.view;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public abstract class BaseThongKeTab<T> extends BasePanel {

    protected JPanel mainContainer;
    protected JTable table;
    protected DefaultTableModel tableModel;
    protected DefaultTableCellRenderer leftRenderer;
    protected DefaultTableCellRenderer centerRenderer;
    protected DefaultTableCellRenderer rightRenderer;

    public BaseThongKeTab() {
        leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);

        centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new BorderLayout(0, 0));

        mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBackground(Color.WHITE);
        mainContainer.setBorder(new EmptyBorder(10, 10, 10, 10));

        mainContainer.add(new JLabel("Chưa có dữ liệu.", JLabel.CENTER), BorderLayout.CENTER);

        tableModel = new DefaultTableModel(getTenCot(), 0);
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        add(mainContainer, BorderLayout.CENTER);
    }

    protected void veBang(List<T> listData) {
        mainContainer.removeAll();

        if (listData != null && !listData.isEmpty()) {
            JLabel labelTitle = new JLabel(getTieuDeBang(), JLabel.CENTER);
            labelTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
            labelTitle.setBorder(new EmptyBorder(0, 0, 10, 0));
            mainContainer.add(labelTitle, BorderLayout.NORTH);

            tableModel.setRowCount(0);
            int stt = 1;
            for (T item : listData) {
                tableModel.addRow(getRowData(stt++, item));
            }
            mainContainer.add(new JScrollPane(table), BorderLayout.CENTER);

            String summary = getTextTongKet(listData);
            if (summary != null) {
                JLabel labelTong = new JLabel(summary, JLabel.RIGHT);
                labelTong.setFont(new Font("Segoe UI", Font.BOLD, 16));
                labelTong.setForeground(Color.RED);
                mainContainer.add(labelTong, BorderLayout.SOUTH);
            }
        } else {
            mainContainer.add(new JLabel("Chưa có dữ liệu.", JLabel.CENTER), BorderLayout.CENTER);
        }
        refreshUI();
    }

    protected void veBieuDo(JFreeChart chart) {
        mainContainer.removeAll();

        if (chart != null) {
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setMouseWheelEnabled(true);
            chartPanel.setMinimumDrawWidth(0);
            chartPanel.setMinimumDrawHeight(0);
            mainContainer.add(chartPanel, BorderLayout.CENTER);
        } else {
            mainContainer.add(new JLabel("Chưa có dữ liệu.", JLabel.CENTER), BorderLayout.CENTER);
        }

        refreshUI();
    }

    protected void refreshUI() {
        mainContainer.revalidate();
        mainContainer.repaint();
    }

    protected abstract String[] getTenCot();

    protected abstract String getTieuDeBang();

    protected abstract Object[] getRowData(int stt, T item);

    protected abstract String getTextTongKet(List<T> listData);
}