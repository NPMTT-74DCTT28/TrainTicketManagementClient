package com.npmtt.ticketclient.controller.core;

import com.npmtt.ticketclient.apiclient.ThongKeApiClient;
import com.npmtt.ticketclient.view.core.Dashboard;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;

public class DashboardController {

    private final Dashboard dashboard;
    private final ThongKeApiClient apiClient;

    public DashboardController(Dashboard dashboard) {
        this.dashboard = dashboard;
        this.apiClient = ThongKeApiClient.getInstance();

        loadData();

        Timer timer = new Timer(60000, e -> loadData());
        timer.start();

        this.dashboard.setVisible(true);
    }

    private void loadData() {
        try {
            DefaultCategoryDataset dataset = apiClient.getDoanhThuBayNgay();
            if (dataset != null) {
                JFreeChart chart = ChartFactory.createLineChart(
                        "XU HƯỚNG DOANH THU 7 NGÀY GẦN NHẤT".toUpperCase(),
                        "Ngày",
                        "Doanh thu",
                        dataset,
                        PlotOrientation.VERTICAL,
                        false, false, false
                );

                CategoryPlot plot = chart.getCategoryPlot();
                plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_90);
                ((NumberAxis) plot.getRangeAxis()).setAutoRangeIncludesZero(true);

                dashboard.setChart(chart);
            }
        } catch (Exception e) {
            e.printStackTrace();
            dashboard.showError("Xảy ra lỗi không xác định: " + e.getMessage());
        }
    }
}
