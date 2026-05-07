package com.npmtt.ticketclient.view.ghe;

import com.npmtt.ticketclient.dto.gatau.GaTauDTO;
import com.npmtt.ticketclient.dto.loaitoa.LoaiToaDTO;
import com.npmtt.ticketclient.dto.request.GheRequest;
import com.npmtt.ticketclient.dto.request.ToaTauRequest;
import com.npmtt.ticketclient.dto.response.ToaTauResponse;
import com.npmtt.ticketclient.view.BasePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.List;

public class QLGhePanel extends BasePanel {
    int k = 0;
    private JTextField fieldSoGhe;
    private JComboBox<ToaTauResponse> ComboBoxIDToaTau;
    private JButton buttonThem;
    private JButton buttonSua;
    private JButton buttonXoa;
    private JButton buttonReset;
    private JTable tblGhe;
    private boolean isEditMode = false;

    public QLGhePanel() {
        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new BorderLayout());

        JPanel panelHome = new JPanel();
        panelHome.setBackground(new Color(152, 251, 152));
        panelHome.setBorder(new EmptyBorder(5, 5, 5, 5));
        JLabel labelHome = new JLabel("Quản lý thông tin ghế");
        labelHome.setSize(200, 80);
        labelHome.setFont(new Font("Segoe UI", Font.BOLD, 20));
        panelHome.add(labelHome);

        JPanel panelTop = new JPanel(new BorderLayout(0, 5));

        JPanel panelForm = new JPanel(new GridLayout(1, 2, 5, 5));
        panelForm.setBorder(new EmptyBorder(10, 5, 10, 5));

        fieldSoGhe = new JTextField();
        panelForm.add(createInputField("Số ghế:  ", fieldSoGhe, Color.WHITE));

        ComboBoxIDToaTau = new JComboBox<>();
        panelForm.add(createInputField("ID toa tàu: ", ComboBoxIDToaTau, Color.WHITE));


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

        Object[] columns = new Object[]{"ID", "Số ghế", "Toa tàu"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        tblGhe = new JTable(tableModel);
        TableColumnModel columnModel = tblGhe.getColumnModel();
        TableColumn columnId = columnModel.getColumn(0);
        tblGhe.removeColumn(columnId);
        JScrollPane scrollPane = new JScrollPane(tblGhe);
        scrollPane.setFont(new Font("Segoe UI", Font.BOLD, 13));
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        add(panelTop, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public String getSoGhe() {
        return fieldSoGhe.getText().trim();
    }

    public void setSoGhe(String soGhe) {
        fieldSoGhe.setText(soGhe);
    }

    public int getIDToaTau() {
        ToaTauResponse selected = (ToaTauResponse) ComboBoxIDToaTau.getSelectedItem();
        return (selected != null) ? selected.getId() : 0;
    }

    public void setIDToaTau(int idToaTau) {
        for (int i = 0; i < ComboBoxIDToaTau.getItemCount(); i++) {
            ToaTauResponse item = ComboBoxIDToaTau.getItemAt(i);
            if (item.getId() == idToaTau) {
                ComboBoxIDToaTau.setSelectedIndex(i);
                break;
            }
        }
    }

    public JTable getTable() {
        return tblGhe;
    }

    public void addButtonThemActionListener(ActionListener a) {
        buttonThem.addActionListener(a);
    }

    public void addButtonSuaActionListener(ActionListener a) {
        buttonSua.addActionListener(a);
    }

    public void addButtonXoaActionListener(ActionListener a) {
        buttonXoa.addActionListener(a);
    }

    public void addButtonResetActionListener(ActionListener a) {
        buttonReset.addActionListener(a);
    }

    public void addTableMouseClickListener(MouseListener l) {
        tblGhe.addMouseListener(l);
    }

    public GheRequest getGheFromForm() {
        String soGhe = getSoGhe();
        int idToaTau = getIDToaTau();

        return new GheRequest(soGhe, idToaTau);
    }

    public void startEditMode() {
        buttonThem.setEnabled(false);
        buttonSua.setEnabled(true);
        buttonXoa.setEnabled(true);
        buttonReset.setEnabled(true);
    }

    public void resetForm() {
        fieldSoGhe.setEnabled(true);
        fieldSoGhe.setText("");
        fieldSoGhe.setBackground(Color.white);

        ComboBoxIDToaTau.setSelectedIndex(-1);
        buttonThem.setEnabled(true);
        buttonSua.setEnabled(false);
        buttonXoa.setEnabled(false);
        buttonReset.setEnabled(true);
        if (tblGhe != null) {
            tblGhe.clearSelection();
        }
    }

    public void setComboBoxToaTauData(List<ToaTauResponse> list) {
        ComboBoxIDToaTau.removeAllItems();
        for (ToaTauResponse toa : list) {
            ComboBoxIDToaTau.addItem(toa);
        }
    }
}
