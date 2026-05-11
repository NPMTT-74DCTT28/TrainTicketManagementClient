package com.npmtt.ticketclient.view.loaitoa;

import com.npmtt.ticketclient.dto.loaitoa.LoaiToaDTO;
import com.npmtt.ticketclient.view.BasePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.math.BigDecimal;

public class QLLoaiToaPanel extends BasePanel {
    int k = 0;
    private JTextField fieldTenLoai;
    private JTextField fieldHeSoGia;
    private JButton buttonThem;
    private JButton buttonSua;
    private JButton buttonXoa;
    private JButton buttonReset;
    private JTable tblLoaiToa;
    private DefaultTableModel tableModel;

    public QLLoaiToaPanel() {
        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new BorderLayout());

        JPanel panelHome = new JPanel();
        panelHome.setBackground(new Color(152, 251, 152));
        panelHome.setBorder(new EmptyBorder(5, 5, 5, 5));
        JLabel labelHome = new JLabel("Quản lý thông tin loại toa");
        labelHome.setSize(200, 80);
        labelHome.setFont(new Font("Segoe UI", Font.BOLD, 20));
        panelHome.add(labelHome);

        JPanel panelTop = new JPanel(new BorderLayout(0, 5));

        JPanel panelForm = new JPanel(new GridLayout(1, 2, 5, 5));
        panelForm.setBorder(new EmptyBorder(10, 5, 10, 5));

        fieldTenLoai = new JTextField();
        panelForm.add(createInputField("Tên loại toa:  ", fieldTenLoai, Color.WHITE));

        fieldHeSoGia = new JTextField();
        panelForm.add(createInputField("Hệ số giá: ", fieldHeSoGia, Color.WHITE));

        buttonThem = createStyledButton("Thêm", new Dimension(80, 40), PRIMARY_COLOR, Color.WHITE);
        buttonThem.setEnabled(true);
        buttonSua = createStyledButton("Sửa", new Dimension(80, 40), new Color(20, 200, 40), Color.WHITE);
        buttonSua.setEnabled(false);
        buttonXoa = createStyledButton("Xoá", new Dimension(80, 40), Color.RED, Color.WHITE);
        buttonXoa.setEnabled(false);
        buttonReset = createStyledButton("Reset", new Dimension(110, 40), PRIMARY_COLOR, Color.WHITE);
        buttonReset.setEnabled(true);

        JButton[] buttons = {buttonThem, buttonSua, buttonXoa, buttonReset};

        panelTop.add(panelHome, BorderLayout.NORTH);
        panelTop.add(panelForm);
        panelTop.add(createButtonField(buttons, Color.WHITE), BorderLayout.SOUTH);

        Object[] columns = new Object[]{"ID", "Tên loại toa", "Hệ số giá"};
        tableModel = new DefaultTableModel(columns, 0);
        tblLoaiToa = new JTable(tableModel);

        TableColumnModel columnModel = tblLoaiToa.getColumnModel();
        TableColumn columnID = columnModel.getColumn(0);
        tblLoaiToa.removeColumn(columnID);

        JScrollPane scrollPane = new JScrollPane(tblLoaiToa);
        scrollPane.setFont(new Font("Segoe UI", Font.BOLD, 13));
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        add(panelTop, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void setButtonThemActionListener(ActionListener a) {
        buttonThem.addActionListener(a);
    }

    public void setButtonSuaActionListener(ActionListener a) {
        buttonSua.addActionListener(a);
    }

    public void setButtonXoaActionListener(ActionListener a) {
        buttonXoa.addActionListener(a);
    }

    public void setButtonResetActionListener(ActionListener a) {
        buttonReset.addActionListener(a);
    }

    public String getTenLoai() {
        return fieldTenLoai.getText().trim();
    }

    public void setTenLoai(String tenLoai) {
        fieldTenLoai.setText(tenLoai);
    }

    public BigDecimal getHeSoGia() {
        String input = fieldHeSoGia.getText().trim();

        if (input.isEmpty()) {
            return BigDecimal.ZERO;
        } else {
            try {
                return new BigDecimal(input);
            } catch (NumberFormatException e) {
                return BigDecimal.ZERO;
            }
        }
    }

    public void setHeSoGia(String maTau) {
        fieldHeSoGia.setText(maTau);
    }

    public JTable getTable() {
        return tblLoaiToa;
    }

    public LoaiToaDTO getLoaiToaFromForm() {
        String tenLoaiToa = getTenLoai();
        BigDecimal heSoGia = getHeSoGia();

        return new LoaiToaDTO(tenLoaiToa, heSoGia);
    }

    public void startEditMode() {
        buttonThem.setEnabled(false);
        buttonSua.setEnabled(true);
        buttonXoa.setEnabled(true);
        buttonReset.setEnabled(true);
    }

    public void resetForm() {
        fieldTenLoai.setText("");
        fieldTenLoai.setBackground(Color.white);

        fieldHeSoGia.setText("");
        buttonThem.setEnabled(true);
        buttonSua.setEnabled(false);
        buttonXoa.setEnabled(false);
        buttonReset.setEnabled(true);
        if (tblLoaiToa != null) {
            tblLoaiToa.clearSelection();
        }
    }

    public void addTableMouseClickListener(MouseListener l) {
        tblLoaiToa.addMouseListener(l);
    }

}
