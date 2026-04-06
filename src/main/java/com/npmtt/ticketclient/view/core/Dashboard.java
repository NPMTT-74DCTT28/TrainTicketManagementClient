package com.npmtt.ticketclient.view.core;

import com.npmtt.ticketclient.util.DinhDang;
import com.npmtt.ticketclient.view.BasePanel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Dashboard extends BasePanel {

    private static final String ICON_PATH = "src/main/resources/icon/";
    private JLabel labelDoanhThu, labelSoVe, labelChuyenTau;
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

        labelDoanhThu = new JLabel(DinhDang.formatTienVN(0));
        panelCard.add(createKPICard("Doanh thu hôm nay: ",
                labelDoanhThu, new Color(46, 204, 113),
                ICON_PATH + "money.png"));

        labelSoVe = new JLabel("0");
        panelCard.add(createKPICard("Vé đã bán: ",
                labelSoVe, new Color(52, 152, 219),
                ICON_PATH + "ticket.png"));

        labelChuyenTau = new JLabel("0");
        panelCard.add(createKPICard("Chuyến sắp chạy: ",
                labelChuyenTau, new Color(243, 156, 18),
                ICON_PATH + "train.png"));

        add(panelCard, BorderLayout.NORTH);

        chartContainer = new JPanel(new BorderLayout());
        chartContainer.setBackground(Color.white);
        chartContainer.setBorder(new EmptyBorder(10, 10, 10, 10));

        add(chartContainer, BorderLayout.CENTER);
    }

    public JPanel createKPICard(String tieuDe, JLabel value, Color background, String iconPath) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(background);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel(tieuDe.toUpperCase());
        title.setForeground(new Color(255, 255, 255, 200));
        title.setFont(FONT_BOLD);

        value.setForeground(Color.white);
        value.setFont(new Font("Segoe UI", Font.BOLD, 32));

        if (iconPath != null) {
            JLabel icon = new JLabel(new ImageIcon(iconPath));
            panel.add(icon, BorderLayout.EAST);
        }

        panel.add(title, BorderLayout.NORTH);
        panel.add(value, BorderLayout.CENTER);

        return panel;
    }

    public void setCardData(String doanhThu, String soVe, String chuyenTau) {
        labelDoanhThu.setText(doanhThu != null ? DinhDang.formatTienVN(Double.parseDouble(doanhThu)) : DinhDang.formatTienVN(0));
        labelSoVe.setText(soVe != null ? soVe : "0");
        labelChuyenTau.setText(chuyenTau != null ? chuyenTau : "0");
    }

    public void setChart(JFreeChart chart) {
        ChartPanel chartPanel = new ChartPanel(chart);
        chartContainer.removeAll();
        chartContainer.add(chartPanel, BorderLayout.CENTER);
        chartContainer.revalidate();
        chartContainer.repaint();
    }
}
