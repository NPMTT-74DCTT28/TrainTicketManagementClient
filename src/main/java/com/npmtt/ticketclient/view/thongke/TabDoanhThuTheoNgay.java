package com.npmtt.ticketclient.view.thongke;

import com.npmtt.ticketclient.dto.thongke.DoanhThuTheoNgayDTO;
import com.npmtt.ticketclient.util.DinhDang;
import com.npmtt.ticketclient.view.BaseThongKeTab;
import com.toedter.calendar.JDateChooser;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class TabDoanhThuTheoNgay extends BaseThongKeTab<DoanhThuTheoNgayDTO> {

    private JDateChooser chooserTuNgay;
    private JDateChooser chooserDenNgay;
    private JButton buttonThongKe;

    private JToggleButton buttonXemBieuDo;

    private JFreeChart chart;
    private List<DoanhThuTheoNgayDTO> listData;

    public TabDoanhThuTheoNgay() {
        super();
    }

    @Override
    protected void initComponents() {
        super.initComponents();

        JPanel panelNorth = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelNorth.setBackground(Color.WHITE);
        panelNorth.setBorder(new MatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY));

        chooserTuNgay = new JDateChooser();
        chooserTuNgay.setDateFormatString(DinhDang.DATE_VN);
        chooserTuNgay.setDate(new Date());
        chooserTuNgay.setMaxSelectableDate(new Date(System.currentTimeMillis()));

        chooserDenNgay = new JDateChooser();
        chooserDenNgay.setDateFormatString(DinhDang.DATE_VN);
        chooserDenNgay.setDate(new Date());
        chooserDenNgay.setMaxSelectableDate(new Date(System.currentTimeMillis()));

        buttonThongKe = createStyledButton("Xem kết quả", new Dimension(120, 40), SECONDARY_COLOR, Color.BLACK);

        buttonXemBieuDo = new JToggleButton("Biểu đồ");
        buttonXemBieuDo.setPreferredSize(new Dimension(80, 40));
        buttonXemBieuDo.setBackground(Color.WHITE);
        buttonXemBieuDo.setSelected(true);

        JToggleButton buttonXemBang = new JToggleButton("Bảng số liệu");
        buttonXemBang.setPreferredSize(new Dimension(110, 40));
        buttonXemBang.setBackground(Color.WHITE);

        ButtonGroup viewModeGroup = new ButtonGroup();
        viewModeGroup.add(buttonXemBieuDo);
        viewModeGroup.add(buttonXemBang);

        panelNorth.add(createInputField("Từ ngày", chooserTuNgay, Color.WHITE));
        panelNorth.add(createInputField("Đến ngày", chooserDenNgay, Color.WHITE));
        panelNorth.add(buttonThongKe);
        panelNorth.add(Box.createHorizontalStrut(30));
        panelNorth.add(buttonXemBieuDo);
        panelNorth.add(buttonXemBang);

        add(panelNorth, BorderLayout.NORTH);

        if (table.getColumnCount() >= 4) {
            table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
            table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
            table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
            table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        }

        buttonXemBieuDo.addActionListener(e -> super.veBieuDo(chart));
        buttonXemBang.addActionListener(e -> super.veBang(listData));
    }

    @Override
    protected String[] getTenCot() {
        return new String[]{"STT", "Ngày", "Số vé bán", "Doanh thu"};
    }

    @Override
    protected String getTieuDeBang() {
        return "CHI TIẾT DOANH THU TỪ " + DinhDang.formatNgayVN(getTuNgay()) + " ĐẾN " + DinhDang.formatNgayVN(getDenNgay());
    }

    @Override
    protected Object[] getRowData(int stt, DoanhThuTheoNgayDTO item) {
        return new Object[]{stt, DinhDang.formatNgayVN(item.getNgay()), item.getSoVeBan(), DinhDang.formatTienVN(item.getDoanhThu()),};
    }

    @Override
    protected String getTextTongKet(List<DoanhThuTheoNgayDTO> listData) {
        double tongDoanhThu = 0;
        for (DoanhThuTheoNgayDTO item : listData) {
            tongDoanhThu += item.getDoanhThu();
        }
        return "TỔNG DOANH THU: " + DinhDang.formatTienVN(tongDoanhThu);
    }

    public void setData(JFreeChart chart, List<DoanhThuTheoNgayDTO> list) {
        this.chart = chart;
        this.listData = list;

        if (buttonXemBieuDo.isSelected()) {
            super.veBieuDo(this.chart);
        } else {
            super.veBang(listData);
        }
    }

    public LocalDate getTuNgay() {
        if (chooserTuNgay.getDate() != null) {
            return chooserTuNgay.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        return null;
    }

    public LocalDate getDenNgay() {
        if (chooserDenNgay.getDate() != null) {
            return chooserDenNgay.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        return null;
    }

    public void addThongKeListener(ActionListener l) {
        buttonThongKe.addActionListener(l);
    }
}