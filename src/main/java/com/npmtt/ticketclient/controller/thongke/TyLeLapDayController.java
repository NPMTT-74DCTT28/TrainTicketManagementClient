package com.npmtt.ticketclient.controller.thongke;

import com.npmtt.ticketclient.apiclient.ThongKeApiClient;
import com.npmtt.ticketclient.dto.thongke.TyLeLapDayDTO;
import com.npmtt.ticketclient.util.DinhDang;
import com.npmtt.ticketclient.view.thongke.TabTyLeLapDay;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class TyLeLapDayController {

    private final TabTyLeLapDay tab;
    private final ThongKeApiClient apiClient;

    public TyLeLapDayController(TabTyLeLapDay tab) {
        this.tab = tab;
        this.apiClient = ThongKeApiClient.getInstance();

        this.tab.addThongKeListener(new ThongKeListener());

        this.tab.setVisible(true);
    }

    private class ThongKeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                LocalDate tuNgay = tab.getTuNgay();
                LocalDate denNgay = tab.getDenNgay();

                if (tuNgay == null || denNgay == null) {
                    tab.showWarning("Vui lòng chọn đủ ngày bắt đầu và ngày kết thúc.");
                    return;
                }

                if (tuNgay.isAfter(denNgay)) {
                    tab.showWarning("Ngày bắt đầu không được lớn hơn ngày kết thúc.");
                    return;
                }

                if (ChronoUnit.DAYS.between(tuNgay, denNgay) > 365) {
                    tab.showWarning("Khoảng thời gian thống kê quá dài (tối đa 365 ngày)");
                    return;
                }

                List<TyLeLapDayDTO> listData = apiClient.getTyLeLapDay(tuNgay, denNgay);

                if (listData.isEmpty()) {
                    tab.showMessage("Không có dữ liệu thống kê trong khoảng thời gian này!");
                    tab.setData(null, null);
                    return;
                }

                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                for (TyLeLapDayDTO item : listData) {
                    dataset.addValue(item.getTyLeLapDay(), "Tỷ lệ (%)", item.getTenTau());
                }

                JFreeChart chart = ChartFactory.createBarChart(
                        "SO SÁNH TỶ LỆ LẤP ĐẦY GIỮA CÁC TÀU TỪ "
                                + DinhDang.formatNgayVN(tuNgay)
                                + " ĐẾN " + DinhDang.formatNgayVN(denNgay),
                        "Tên tàu",
                        "Tỷ lệ (%)",
                        dataset,
                        PlotOrientation.VERTICAL,
                        false, false, false
                );

                CategoryPlot plot = chart.getCategoryPlot();
                plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_90);
                ((NumberAxis) plot.getRangeAxis()).setAutoRangeIncludesZero(true);

                tab.setData(chart, listData);
            } catch (Exception ex) {
                ex.printStackTrace();
                tab.showError("Lỗi khi gọi API: " + ex.getMessage());
            }
        }
    }
}
