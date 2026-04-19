package com.npmtt.ticketclient.view.thongke;

import com.npmtt.ticketclient.dto.thongke.KhachHangVipDTO;
import com.npmtt.ticketclient.util.DinhDang;
import com.npmtt.ticketclient.view.BaseThongKeTab;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class TabKhachHangVip extends BaseThongKeTab<KhachHangVipDTO> {

    private JSpinner spinnerSoLuong;

    private JButton buttonThongKe;

    private JToggleButton buttonXemBieuDo, buttonXemBang;

    private JFreeChart currentChart;
    private List<KhachHangVipDTO> currentList;

    public TabKhachHangVip() {
        super();
    }

    @Override
    protected void initComponents() {
        super.initComponents();

        JPanel panelNorth = new JPanel();
        panelNorth.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelNorth.setBackground(Color.WHITE);
        panelNorth.setBorder(new MatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY));

        spinnerSoLuong = new JSpinner();
        spinnerSoLuong.setModel(new SpinnerNumberModel(1, 1, null, 1));

        buttonThongKe = createStyledButton("Xem kết quả", new Dimension(120, 40), SECONDARY_COLOR, Color.BLACK);

        buttonXemBieuDo = new JToggleButton("Biểu đồ");
        buttonXemBieuDo.setBackground(Color.WHITE);
        buttonXemBieuDo.setPreferredSize(new Dimension(80, 40));
        buttonXemBieuDo.setSelected(true);

        buttonXemBang = new JToggleButton("Bảng số liệu");
        buttonXemBang.setBackground(Color.WHITE);
        buttonXemBang.setPreferredSize(new Dimension(110, 40));

        ButtonGroup viewModeGroup = new ButtonGroup();
        viewModeGroup.add(buttonXemBieuDo);
        viewModeGroup.add(buttonXemBang);

        panelNorth.add(createInputField("Số lượng KH", spinnerSoLuong, Color.WHITE));
        panelNorth.add(buttonThongKe);
        panelNorth.add(Box.createHorizontalStrut(30));
        panelNorth.add(buttonXemBieuDo);
        panelNorth.add(buttonXemBang);

        add(panelNorth, BorderLayout.NORTH);

        if (table.getColumnCount() >= 3) {
            table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
            table.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
            table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
            table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        }

        buttonXemBieuDo.addActionListener(e -> super.veBieuDo(currentChart));
        buttonXemBang.addActionListener(e -> super.veBang(currentList));
    }

    @Override
    protected String[] getTenCot() {
        return new String[]{"Hạng", "Họ tên", "SĐT", "Tổng chi tiêu"};
    }

    @Override
    protected String getTieuDeBang() {
        return "BẢNG XẾP HẠNG " + getSoLuong() + " KHÁCH HÀNG CHI TIÊU NHIỀU NHẤT";
    }

    @Override
    protected Object[] getRowData(int stt, KhachHangVipDTO item) {
        return new Object[]{
                stt,
                item.getHoTen(),
                item.getSdt(),
                DinhDang.formatTienVN(item.getTongTienChiTieu())
        };
    }

    @Override
    protected String getTextTongKet(List<KhachHangVipDTO> listData) {
        return "";
    }

    public void setData(JFreeChart chart, List<KhachHangVipDTO> listData) {
        this.currentChart = chart;
        this.currentList = listData;

        if (buttonXemBieuDo.isSelected()) {
            super.veBieuDo(currentChart);
        } else if (buttonXemBang.isSelected()) {
            super.veBang(currentList);
        }
    }

    public int getSoLuong() {
        if (spinnerSoLuong.getValue() != null) {
            return Integer.parseInt(spinnerSoLuong.getValue().toString());
        }
        return 0;
    }

    public void addThongKeListener(ActionListener listener) {
        buttonThongKe.addActionListener(listener);
    }
}
