package com.npmtt.ticketclient.view.tuyenduong;

import com.npmtt.ticketclient.dto.gatau.GaTauDTO;
import com.npmtt.ticketclient.dto.request.TuyenDuongRequest;
import com.npmtt.ticketclient.view.BasePanel;
import lombok.Getter;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.List;

public final class QLTuyenDuongPanel extends BasePanel {
    private JTextField fieldMaTuyen, fieldTenTuyen, fieldKhoangcach, fieldGiaCB;
    private JComboBox<GaTauDTO> cboGadi, cboGaden;
    private JButton btnthem, btnsua, btnxoa, btnreset;
    private DefaultTableModel model;
    @Getter
    private JTable table;

    public QLTuyenDuongPanel() {
        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new BorderLayout());
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0, 123, 255));
        titlePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        JLabel title = new JLabel("QL Tuyến Đường");
        title.setSize(new Dimension(200, 80));
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titlePanel.add(title);

        JPanel panelTop = new JPanel(new BorderLayout(0, 5));
        JPanel panelFrom = new JPanel(new GridLayout(3, 2, 5, 5));
        panelFrom.setBorder(new EmptyBorder(5, 5, 5, 5));

        fieldMaTuyen = new JTextField();
        panelFrom.add(createInputField("Mã tuyến ", fieldMaTuyen, Color.WHITE));

        fieldTenTuyen = new JTextField();
        panelFrom.add(createInputField("Tên tuyến ", fieldTenTuyen, Color.WHITE));

        cboGadi = new JComboBox<>();
        panelFrom.add(createInputField("Ga đi ", cboGadi, Color.WHITE));

        fieldKhoangcach = new JTextField();
        panelFrom.add(createInputField("Khoảng cách ", fieldKhoangcach, Color.WHITE));

        cboGaden = new JComboBox<>();
        panelFrom.add(createInputField("Ga đến ", cboGaden, Color.WHITE));

        fieldGiaCB = new JTextField();
        panelFrom.add(createInputField("Giá cơ bản ", fieldGiaCB, Color.WHITE));

        btnthem = createStyledButton("Thêm", new Dimension(80, 40), new Color(40, 85, 243), Color.black);
        btnsua = createStyledButton("Sửa", new Dimension(80, 40), new Color(255, 193, 7), Color.black);
        btnxoa = createStyledButton("Xóa", new Dimension(80, 40), new Color(220, 53, 69), Color.black);
        btnreset = createStyledButton("Reset", new Dimension(80, 40), new Color(108, 117, 125), Color.black);
        JButton[] btn = {btnthem, btnsua, btnxoa, btnreset};

        panelTop.add(titlePanel, BorderLayout.NORTH);
        panelTop.add(panelFrom);
        panelTop.add(createButtonField(btn, Color.WHITE), BorderLayout.SOUTH);

        String[] columNames = {"id", "Mã Tuyến", "Tên Tuyến", "Ga Đi", "Ga Đến", "Khoảng Cách (km)", "Giá Cơ Bản"};
        model = new DefaultTableModel(columNames, 0);

        table = new JTable(model);
        TableColumnModel columnModel = table.getColumnModel();
        TableColumn columnId = columnModel.getColumn(0);
        columnModel.removeColumn(columnId);
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(SECONDARY_COLOR);
        tableHeader.setForeground(Color.BLACK);
        tableHeader.setOpaque(false);
        tableHeader.setFont(FONT_PLAIN);

        TitledBorder tableBorder = new TitledBorder(new LineBorder(Color.LIGHT_GRAY), "Danh sách Tuyến Đường",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, FONT_BOLD, Color.BLACK);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 5), tableBorder));
        scrollPane.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JPanel panelTable = new JPanel(new BorderLayout());
        panelTable.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelTable.setBackground(Color.WHITE);
        panelTable.add(scrollPane, BorderLayout.CENTER);

        add(panelTop, BorderLayout.NORTH);
        add(panelTable, BorderLayout.CENTER);

    }

    public String getMaTuyen() {
        return fieldMaTuyen.getText().trim();
    }

    public void setMaTuyen(String maTuyen) {
        fieldMaTuyen.setText(maTuyen);
    }

    public String getTenTuyen() {
        return fieldTenTuyen.getText().trim();
    }

    public void setTenTuyen(String tenTuyen) {
        fieldTenTuyen.setText(tenTuyen);
    }

    public void setGadi(int idgadi) {
        for (int i = 0; i < cboGadi.getItemCount(); i++) {
            GaTauDTO ga = cboGadi.getItemAt(i);
            if (ga.getId() == idgadi) {
                cboGadi.setSelectedIndex(i);
                break;
            }
        }
    }

    public void setGaden(int idgaden) {
        for (int i = 0; i < cboGaden.getItemCount(); i++) {
            GaTauDTO ga = cboGaden.getItemAt(i);
            if (ga.getId() == idgaden) {
                cboGaden.setSelectedIndex(i);
                break;
            }
        }
    }

    public double getKhoangcach() {
        if (!fieldKhoangcach.getText().trim().isEmpty()) {
            return Double.parseDouble(fieldKhoangcach.getText().trim());
        }
        return 0;
    }

    public void setKhoangcach(String khoangcach) {
        fieldKhoangcach.setText(khoangcach);
    }

    public double getGiaCB() {
        if (!fieldGiaCB.getText().trim().isEmpty()) {
            return Double.parseDouble(fieldGiaCB.getText().trim());
        }
        return 0;
    }

    public void setGiaCB(String giaCB) {
        fieldGiaCB.setText(giaCB);
    }

    public TuyenDuongRequest getTuyenDuong() {
        String maTuyen = getMaTuyen();
        String tenTuyen = getTenTuyen();
        GaTauDTO gaDi = (GaTauDTO) cboGadi.getSelectedItem();
        GaTauDTO gaDen = (GaTauDTO) cboGaden.getSelectedItem();
        int idgadi = gaDi.getId();
        int idgaden = gaDen.getId();
        double khoangcach = getKhoangcach();
        double giaCB = getGiaCB();
        return new TuyenDuongRequest(maTuyen, tenTuyen, idgadi, idgaden, khoangcach, giaCB);
    }

    public void setGatau(List<GaTauDTO> list) {
        cboGadi.removeAllItems();
        cboGaden.removeAllItems();
        for (GaTauDTO gaTau : list) {
            cboGadi.addItem(gaTau);
            cboGaden.addItem(gaTau);
        }
    }


    public void startEditMode() {
        fieldMaTuyen.setEditable(false);
        fieldTenTuyen.setEnabled(true);

        cboGadi.setEnabled(true);
        cboGaden.setEnabled(true);
        fieldKhoangcach.setEnabled(true);
        fieldGiaCB.setEnabled(true);
        btnthem.setEnabled(false);
        btnsua.setEnabled(true);
        btnxoa.setEnabled(true);
        btnreset.setEnabled(true);
    }

    public void resetForm() {
        fieldMaTuyen.setEditable(true);
        fieldMaTuyen.setText("");
        fieldTenTuyen.setEnabled(true);
        fieldTenTuyen.setText("");
        if (cboGadi.getItemCount() > 0) {
            cboGadi.setSelectedIndex(0);
        }
        cboGadi.setEnabled(true);

        if (cboGaden.getItemCount() > 0) {
            cboGaden.setSelectedIndex(0);
        }
        cboGaden.setEnabled(true);
        fieldKhoangcach.setEnabled(true);
        fieldKhoangcach.setText("");
        fieldGiaCB.setEnabled(true);
        fieldGiaCB.setText("");
        btnthem.setEnabled(true);
        btnsua.setEnabled(false);
        btnxoa.setEnabled(false);
    }

    public void AddTuyen(ActionListener l) {
        btnthem.addActionListener(l);
    }

    public void EditTuyen(ActionListener l) {
        btnsua.addActionListener(l);
    }

    public void RemoveTuyen(ActionListener l) {
        btnxoa.addActionListener(l);
    }

    public void ResetTuyen(ActionListener l) {
        btnreset.addActionListener(l);
    }

    public void TableMouseClickListener(MouseListener l) {
        table.addMouseListener(l);
    }
}