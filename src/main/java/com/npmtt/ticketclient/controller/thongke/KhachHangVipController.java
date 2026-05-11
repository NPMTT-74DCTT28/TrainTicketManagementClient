package com.npmtt.ticketclient.controller.thongke;

import com.npmtt.ticketclient.apiclient.ThongKeApiClient;
import com.npmtt.ticketclient.dto.thongke.KhachHangVipDTO;
import com.npmtt.ticketclient.view.thongke.TabKhachHangVip;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class KhachHangVipController {

    private final TabKhachHangVip tab;
    private final ThongKeApiClient apiClient;

    public KhachHangVipController(TabKhachHangVip tab) {
        this.tab = tab;
        this.apiClient = ThongKeApiClient.getInstance();

        this.tab.addThongKeListener(new ThongKeListener());

        this.tab.setVisible(true);
    }

    private class ThongKeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int soLuong = tab.getSoLuong();
                if (soLuong <= 0) {
                    tab.showWarning("Số lượng phải từ 1 trở lên.");
                    return;
                }

                List<KhachHangVipDTO> listData = apiClient.getKhachHangVIP(soLuong);
                if (listData.isEmpty()) {
                    tab.showMessage("Không tìm thấy dữ liệu khách hàng!");
                    tab.setData(null, null);
                    return;
                }

                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                for (KhachHangVipDTO item : listData) {
                    dataset.addValue(item.getTongTienChiTieu(), "Tổng chi tiêu (VNĐ)", item.getHoTen());
                }

                JFreeChart chart = ChartFactory.createBarChart(
                        "TOP " + soLuong + " KHÁCH HÀNG CHI TIÊU NHIỀU NHẤT",
                        "Họ tên",
                        "Tổng chi tiêu (VNĐ)",
                        dataset,
                        PlotOrientation.HORIZONTAL,
                        false, false, false
                );

                CategoryPlot plot = chart.getCategoryPlot();
                plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
                ((NumberAxis) plot.getRangeAxis()).setAutoRangeIncludesZero(true);

                tab.setData(chart, listData);
            } catch (Exception ex) {
                ex.printStackTrace();
                tab.showError("Lỗi khi gọi API: " + ex.getMessage());
            }
        }
    }
}
