package com.npmtt.ticketclient.view.gatau;

import com.npmtt.ticketclient.dto.gatau.GaTauDTO;
import com.npmtt.ticketclient.view.BasePanel;

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

public class QLGaTauPanel extends BasePanel {
    private JTextField fieldMaga, fieldTenga, fieldDiachi, fieldThanhpho;
    private JButton btnthem, btnsua, btnxoa, btnreset;
    private DefaultTableModel model;
    private JTable table;
    private boolean isEditmode = false;

    public QLGaTauPanel() {
        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new BorderLayout());
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0, 123, 255));
        titlePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        JLabel lblTitle = new JLabel("QL Ga Tàu");
        lblTitle.setSize(200, 80);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titlePanel.add(lblTitle);

        JPanel panelTop = new JPanel(new BorderLayout(0, 5));
        JPanel panelForm = new JPanel(new GridLayout(2, 2, 5, 5));
        panelForm.setBorder(new EmptyBorder(10, 5, 10, 5));

        fieldMaga = new JTextField();
        panelForm.add(createInputField("Mã Ga: ", fieldMaga, Color.WHITE));

        fieldTenga = new JTextField();
        panelForm.add(createInputField("Tên Ga: ", fieldTenga, Color.WHITE));

        fieldDiachi = new JTextField();
        panelForm.add(createInputField("Địa chỉ: ", fieldDiachi, Color.WHITE));

        fieldThanhpho = new JTextField();
        panelForm.add(createInputField("Thành phố: ", fieldThanhpho, Color.WHITE));


        btnthem = createStyledButton("Thêm", new Dimension(80, 40), new Color(40, 85, 243), Color.black);
        btnsua = createStyledButton("Sửa", new Dimension(80, 40), new Color(255, 193, 7), Color.black);
        btnxoa = createStyledButton("Xóa", new Dimension(80, 40), new Color(220, 53, 69), Color.black);
        btnreset = createStyledButton("Reset", new Dimension(80, 40), new Color(108, 117, 125), Color.white);
        JButton[] btn = {btnthem, btnsua, btnxoa, btnreset};

        panelTop.add(titlePanel, BorderLayout.NORTH);
        panelTop.add(panelForm);
        panelTop.add(createButtonField(btn, Color.WHITE), BorderLayout.SOUTH);


        String[] columNames = {"id", "Mã Ga", "Tên Ga", "Địa chỉ", "Thành phố"};
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

        TitledBorder tableBorder = new TitledBorder(new LineBorder(Color.LIGHT_GRAY), "Danh sách Ga Tàu",
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

    public String getMaGa() {
        return fieldMaga.getText().trim();
    }

    public void setMaGa(String maNV) {
        fieldMaga.setText(maNV);
    }

    public String getTenga() {
        return fieldTenga.getText().trim();
    }

    public void setTenga(String tenga) {
        fieldTenga.setText(tenga);
    }

    public String getDiachi() {
        return fieldDiachi.getText().trim();
    }

    public void setDiachi(String diachi) {
        fieldDiachi.setText(diachi);
    }

    public String getThanhpho() {
        return fieldThanhpho.getText().trim();
    }

    public void setThanhpho(String thanhpho) {
        fieldThanhpho.setText(thanhpho);
    }

    public JTable getTable() {
        return table;
    }

    public GaTauDTO getGaTau() {
        String maGa = getMaGa();
        String tenga = getTenga();
        String diachi = getDiachi();
        String thanhpho = getThanhpho();
        return new GaTauDTO(maGa, tenga, diachi, thanhpho);
    }

    public void startEditMode() {
        isEditmode = true;
        fieldMaga.setEnabled(false);
        btnthem.setEnabled(false);
        btnsua.setEnabled(true);
        btnxoa.setEnabled(true);

    }

    public void resetForm() {
        isEditmode = false;

        fieldMaga.setEnabled(true);
        fieldMaga.setText("");
        fieldTenga.setEnabled(true);
        fieldTenga.setText("");
        fieldDiachi.setEnabled(true);
        fieldDiachi.setText("");
        fieldThanhpho.setEnabled(true);
        fieldThanhpho.setText("");

        btnthem.setEnabled(true);
        btnsua.setEnabled(false);
        btnxoa.setEnabled(false);
        btnreset.setEnabled(true);
        if (table != null) {
            table.clearSelection();
        }
    }

    public void AddGaTau(ActionListener l) {
        btnthem.addActionListener(l);
    }

    public void EditGaTau(ActionListener l) {
        btnsua.addActionListener(l);
    }

    public void RemoveGaTau(ActionListener l) {
        btnxoa.addActionListener(l);
    }

    public void ResetGaTau(ActionListener l) {
        btnreset.addActionListener(l);
    }

    public void TableMouseClickListener(MouseListener l) {
        table.addMouseListener(l);
    }
}
