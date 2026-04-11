package com.npmtt.ticketclient.controller.thongke;

import com.npmtt.ticketclient.apiclient.ThongKeApiClient;
import com.npmtt.ticketclient.dto.thongke.DoanhSoDTO;
import com.npmtt.ticketclient.view.thongke.TabDoanhSo;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

public class DoanhSoController {

    private final TabDoanhSo tab;
    private final ThongKeApiClient apiClient;

    public DoanhSoController(TabDoanhSo tab) {
        this.tab = tab;
        this.apiClient = ThongKeApiClient.getInstance();

        this.tab.addThongKeListener(new ThongKeListener());

        this.tab.setVisible(true);
    }

    private class ThongKeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int thang = tab.getThang();
                int nam = tab.getNam();

                int currentYear = LocalDate.now().getYear();
                int currentMonth = LocalDate.now().getMonthValue();

                if (thang < 1 || thang > 12) {
                    tab.showWarning("Tháng phải từ 1-12.");
                    return;
                }

                if (nam > currentYear || (nam == currentYear && thang > currentMonth)) {
                    tab.showWarning("Không thể thống kê dữ liệu trong tương lai.");
                    return;
                }

                List<DoanhSoDTO> listData = apiClient.getDoanhSo(thang, nam);
                listData.removeIf(doanhSoNhanVien -> doanhSoNhanVien.getDoanhSo() == 0);
                if (listData.isEmpty()) {
                    tab.showMessage("Không có dữ liệu thống kê trong tháng " + thang + "/" + nam + "!");
                    tab.setData(null, null);
                    return;
                }

                DefaultPieDataset dataset = new DefaultPieDataset();
                for (DoanhSoDTO item : listData) {
                    dataset.setValue(item.getHoTen(), item.getDoanhSo());
                }

                JFreeChart chart = ChartFactory.createPieChart(
                        "CƠ CẤU DOANH SỐ THEO NHÂN VIÊN THÁNG " + thang + "/" + nam,
                        dataset,
                        true, false, false
                );

                PiePlot plot = (PiePlot) chart.getPlot();
                plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                        "{0}: {2}", new DecimalFormat("0"), new DecimalFormat("0%")
                ));
                plot.setLabelFont(new Font("Segoe UI", Font.PLAIN, 12));

                tab.setData(chart, listData);
            } catch (Exception ex) {
                ex.printStackTrace();
                tab.showError("Lỗi khi gọi API: " + ex.getMessage());
            }
        }
    }
}
