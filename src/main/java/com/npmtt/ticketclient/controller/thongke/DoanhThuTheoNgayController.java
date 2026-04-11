package com.npmtt.ticketclient.controller.thongke;

import com.npmtt.ticketclient.apiclient.ThongKeApiClient;
import com.npmtt.ticketclient.dto.thongke.DoanhThuTheoNgayDTO;
import com.npmtt.ticketclient.util.DinhDang;
import com.npmtt.ticketclient.view.thongke.TabDoanhThuTheoNgay;
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
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class DoanhThuTheoNgayController {

    private final TabDoanhThuTheoNgay tab;
    private final ThongKeApiClient apiClient;

    public DoanhThuTheoNgayController(TabDoanhThuTheoNgay tabDoanhThuNgay) {
        this.tab = tabDoanhThuNgay;
        this.apiClient = ThongKeApiClient.getInstance();

        this.tab.addThongKeListener(new ThongKeApiClientListener());

        this.tab.setVisible(true);
    }

    private class ThongKeApiClientListener implements ActionListener {
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
                    tab.showWarning("Khoảng thời gian thống kê quá dài (tối đa 365 ngày).");
                    return;
                }

                List<DoanhThuTheoNgayDTO> listData = apiClient.getDoanhThuTheoNgay(tuNgay, denNgay);
                if (listData.isEmpty()) {
                    tab.showMessage("Không có dữ liệu doanh thu trong khoảng thời gian này!");
                    tab.setData(null, null);
                    return;
                }

                DefaultCategoryDataset dataset = new DefaultCategoryDataset();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");

                for (DoanhThuTheoNgayDTO item : listData) {
                    LocalDate date = item.getNgay();
                    String ngayVN = formatter.format(date);
                    dataset.addValue(item.getDoanhThu(), "Doanh thu (VNĐ)", ngayVN);
                    dataset.addValue(item.getSoVeBan(), "Số vé bán", ngayVN);
                }

                JFreeChart chart = ChartFactory.createLineChart(
                        "BIẾN ĐỘNG DOANH THU TỪ " + DinhDang.formatNgayVN(tuNgay) + " ĐẾN " + DinhDang.formatNgayVN(denNgay),
                        "Ngày",
                        "Doanh thu (VNĐ)",
                        dataset,
                        PlotOrientation.VERTICAL,
                        true, false, false
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
