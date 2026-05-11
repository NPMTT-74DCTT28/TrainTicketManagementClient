package com.npmtt.ticketclient.view.core;

import com.npmtt.ticketclient.view.BasePanel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Dashboard extends BasePanel {

    private JPanel chartContainer;

    public Dashboard() {
        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new BorderLayout(0, 0));
        setBackground(Color.white);

        JPanel panelCard = new JPanel(new GridLayout(1, 3, 10, 10));
        panelCard.setBackground(Color.white);
        panelCard.setPreferredSize(new Dimension(0, 100));
        chartContainer = new JPanel(new BorderLayout());
        chartContainer.setBackground(Color.white);
        chartContainer.setBorder(new EmptyBorder(10, 10, 10, 10));

        add(chartContainer, BorderLayout.CENTER);
    }

    public void setChart(JFreeChart chart) {
        ChartPanel chartPanel = new ChartPanel(chart);
        chartContainer.removeAll();
        chartContainer.add(chartPanel);
        chartContainer.revalidate();
        chartContainer.repaint();
    }
}
