package com.npmtt.ticketclient.view.nhanvien;

import com.npmtt.ticketclient.enums.GioiTinh;
import com.npmtt.ticketclient.enums.VaiTro;
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

public class TKNhanVienPanel extends BasePanel {

    private JTextField fieldTuKhoa;
    private JComboBox<Object> boxGioiTinh;
    private JComboBox<Object> boxVaiTro;
    private JButton buttonTimKiem, buttonReset, buttonLamMoi;
    private JTable table;

    public TKNhanVienPanel() {
        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new BorderLayout(0, 0));
        setBackground(Color.WHITE);

        JPanel panelTop = new JPanel(new BorderLayout(5, 5));
        panelTop.setBackground(Color.WHITE);

        JPanel panelTitle = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelTitle.setBackground(PRIMARY_COLOR);
        JLabel labelTitle = new JLabel("DANH SÁCH NHÂN VIÊN");
        labelTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        labelTitle.setForeground(Color.WHITE);
        panelTitle.add(labelTitle);

        JPanel panelForm = new JPanel(new GridLayout(1, 3, 5, 5));
        panelForm.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelForm.setBackground(Color.WHITE);

        fieldTuKhoa = new JTextField();
        panelForm.add(createInputField("<html>Tên / SĐT /<br>Email / Địa chỉ</html>", fieldTuKhoa, Color.WHITE));

        boxGioiTinh = new JComboBox<>(createComboBoxModel(GioiTinh.values()));
        panelForm.add(createInputField("Giới tính", boxGioiTinh, Color.WHITE));

        boxVaiTro = new JComboBox<>(createComboBoxModel(VaiTro.values()));
        panelForm.add(createInputField("Vai trò", boxVaiTro, Color.WHITE));

        buttonTimKiem = createStyledButton("Tìm kiếm", new Dimension(100, 40), PRIMARY_COLOR, Color.WHITE);
        buttonReset = createStyledButton("Reset form", new Dimension(110, 40), PRIMARY_COLOR, Color.WHITE);
        buttonLamMoi = createStyledButton("Làm mới", new Dimension(100, 40), PRIMARY_COLOR, Color.WHITE);
        JButton[] buttons = new JButton[]{buttonTimKiem, buttonReset, buttonLamMoi};

        panelTop.add(panelTitle, BorderLayout.NORTH);
        panelTop.add(panelForm, BorderLayout.CENTER);
        panelTop.add(createButtonField(buttons, Color.WHITE), BorderLayout.SOUTH);

        Object[] columns = new Object[]{
                "ID", "Mã nhân viên", "Họ tên", "Ngày sinh", "Giới tính",
                "SĐT", "e-Mail", "Địa chỉ", "Vai trò"
        };
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        table = new JTable(model);
        TableColumnModel columnModel = table.getColumnModel();
        TableColumn columnId = columnModel.getColumn(0);
        table.removeColumn(columnId);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(SECONDARY_COLOR);
        tableHeader.setForeground(Color.BLACK);
        tableHeader.setOpaque(false);
        tableHeader.setFont(FONT_PLAIN);

        JScrollPane scrollPane = new JScrollPane(table);

        TitledBorder tableBorder = new TitledBorder(new LineBorder(Color.LIGHT_GRAY), "Danh sách nhân viên",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, FONT_BOLD, Color.BLACK);

        scrollPane.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), tableBorder));
        scrollPane.setForeground(Color.BLACK);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setFont(FONT_PLAIN);

        JPanel panelTable = new JPanel(new BorderLayout());
        panelTable.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelTable.setBackground(Color.WHITE);
        panelTable.add(scrollPane, BorderLayout.CENTER);

        add(panelTop, BorderLayout.NORTH);
        add(panelTable, BorderLayout.CENTER);
    }

    private DefaultComboBoxModel<Object> createComboBoxModel(Object[] values) {
        DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<>();

        model.addElement("Tất cả");

        for (Object value : values) {
            model.addElement(value);
        }

        return model;
    }

    public String getTuKhoa() {
        return fieldTuKhoa.getText().trim();
    }

    public String getGioiTinh() {
        Object selectedItem = boxGioiTinh.getSelectedItem();
        if (selectedItem == null || selectedItem.toString().equalsIgnoreCase("Tất cả")) {
            return null;
        }
        return selectedItem.toString();
    }

    public String getVaiTro() {
        Object selectedItem = boxVaiTro.getSelectedItem();
        if (selectedItem == null || selectedItem.toString().equalsIgnoreCase("Tất cả")) {
            return null;
        }
        return selectedItem.toString();
    }

    public JTable getTable() {
        return table;
    }

    public void resetForm() {
        fieldTuKhoa.setText("");
        if (boxGioiTinh.getItemCount() > 0) {
            boxGioiTinh.setSelectedIndex(0);
        }
        if (boxVaiTro.getItemCount() > 0) {
            boxVaiTro.setSelectedIndex(0);
        }
    }

    public void addTimKiemListener(ActionListener l) {
        buttonTimKiem.addActionListener(l);
    }

    public void addResetFormListener(ActionListener l) {
        buttonReset.addActionListener(l);
    }

    public void addLamMoiListener(ActionListener l) {
        buttonLamMoi.addActionListener(l);
    }
}
