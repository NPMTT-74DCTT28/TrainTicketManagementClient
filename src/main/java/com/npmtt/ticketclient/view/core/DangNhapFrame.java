package com.npmtt.ticketclient.view.core;

import com.npmtt.ticketclient.view.BaseFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

public final class DangNhapFrame extends BaseFrame {

    private JTextField fieldMaNV;
    private JPasswordField fieldMatKhau;
    private JButton buttonDangNhap;
    private JButton buttonThoat;

    public DangNhapFrame() {
        super("Đăng nhập");
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        initComponents();
        this.setLocationRelativeTo(null);
    }

    @Override
    protected void initComponents() {
        this.setSize(400, 260);
        this.setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        formPanel.setBackground(Color.white);

        fieldMaNV = new JTextField();
        formPanel.add(createInputField("Mã nhân viên", fieldMaNV, Color.WHITE));

        fieldMatKhau = new JPasswordField();
        formPanel.add(createInputField("Mật khẩu", fieldMatKhau, Color.WHITE));

        mainPanel.add(formPanel, BorderLayout.CENTER);

        buttonDangNhap = createStyledButton("Đăng nhập", new Dimension(110, 40), SECONDARY_COLOR, Color.BLACK);
        buttonThoat = createStyledButton("Thoát", new Dimension(80, 40), new Color(200, 200, 40), Color.BLACK);
        JButton[] buttons = new JButton[]{buttonDangNhap, buttonThoat};

        mainPanel.add(createButtonField(buttons, Color.WHITE), BorderLayout.SOUTH);

        this.add(mainPanel);
    }

    public String getMaNV() {
        return fieldMaNV.getText().trim();
    }

    public void setMaNV(String maNV) {
        fieldMaNV.setText(maNV);
    }

    public String getMatKhau() {
        return new String(fieldMatKhau.getPassword()).trim();
    }

    public void setMatKhau(String matKhau) {
        fieldMatKhau.setText(matKhau);
    }

    public void addLoginListener(ActionListener l) {
        buttonDangNhap.addActionListener(l);
    }

    public void addExitListener(ActionListener l) {
        buttonThoat.addActionListener(l);
    }

    public void addWindowCloseListener(WindowListener l) {
        addWindowListener(l);
    }
}
