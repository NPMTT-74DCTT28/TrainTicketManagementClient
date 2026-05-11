package com.npmtt.ticketclient.view.core;

import com.npmtt.ticketclient.util.DinhDang;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class DoitMatKhauDialog extends JDialog {

    private JTextField fieldMatKhauCu;
    private JPasswordField fieldMatKhauMoi;
    private JPasswordField fieldMatKhauXacNhan;
    private JButton buttonXacNhan, buttonHuy;

    public DoitMatKhauDialog(Frame parent) {
        super(parent, "Đổi mật khẩu", true);
        initComnponents();
        setLocationRelativeTo(parent);
    }

    private void initComnponents() {
        setSize(500, 400);
        setLayout(new BorderLayout());
        setResizable(false);

        JPanel panelTitle = new JPanel();
        panelTitle.setBackground(new Color(60, 179, 113)); // PRIMARY_COLOR của bạn
        JLabel lblTitle = new JLabel("ĐỔI MẬT KHẨU");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        panelTitle.add(lblTitle);

        JPanel pnlForm = new JPanel(new GridLayout(3, 1, 10, 10));
        pnlForm.setBorder(new EmptyBorder(20, 20, 20, 20));
        pnlForm.setBackground(Color.WHITE);

        fieldMatKhauCu = new JPasswordField();
        pnlForm.add(createInputField("Mật khẩu cũ*", fieldMatKhauCu, Color.WHITE));

        fieldMatKhauMoi = new JPasswordField();
        pnlForm.add(createInputField("Mật khẩu mới*", fieldMatKhauMoi, Color.WHITE));

        fieldMatKhauXacNhan = new JPasswordField();
        pnlForm.add(createInputField("<html>Mật khẩu<br>xác nhận*</html>", fieldMatKhauXacNhan, Color.WHITE));

        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlButton.setBackground(Color.WHITE);

        buttonXacNhan = new JButton("Lưu thay đổi");
        buttonXacNhan.setFont(new Font("Segoe UI", Font.BOLD, 14));
        buttonXacNhan.setPreferredSize(new Dimension(120, 40));
        buttonXacNhan.setBackground(new Color(60, 179, 113));
        buttonXacNhan.setForeground(Color.WHITE);

        buttonHuy = new JButton("Huỷ");
        buttonXacNhan.setFont(new Font("Segoe UI", Font.BOLD, 14));
        buttonHuy.setPreferredSize(new Dimension(80, 40));
        buttonHuy.setBackground(new Color(223, 197, 123));
        buttonHuy.setForeground(Color.WHITE);

        pnlButton.add(buttonXacNhan);
        pnlButton.add(buttonHuy);

        add(panelTitle, BorderLayout.NORTH);
        add(pnlForm, BorderLayout.CENTER);
        add(pnlButton, BorderLayout.SOUTH);
    }

    private JPanel createInputField(String labelText, JComponent component, Color background) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(background);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(120, 50));
        panel.add(label);

        component.setForeground(Color.BLACK);
        component.setPreferredSize(new Dimension(200, 50));
        panel.add(component);

        return panel;
    }

    public String getMatKhauCu() {
        return fieldMatKhauCu.getText().trim();
    }

    public String getMatKhauMoi() {
        return new String(fieldMatKhauMoi.getPassword());
    }

    public String getMatKhauXacNhan() {
        return new String(fieldMatKhauXacNhan.getPassword());
    }

    public String thongBaoLoiDauVao() {
        if (getMatKhauCu().isEmpty()) {
            fieldMatKhauCu.requestFocus();
            return "Mật khẩu cũ không được để trống!";
        }
        if (getMatKhauMoi().isEmpty()) {
            fieldMatKhauMoi.requestFocus();
            return "Mật khẩu mới không được để trống!";
        }
        if (!getMatKhauMoi().matches(DinhDang.MAT_KHAU_MANH)) {
            fieldMatKhauMoi.requestFocus();
            return "Mật khẩu phải từ 8-20 ký tự, bao gồm ít nhất 1 chữ hoa, " +
                    "1 chữ thường, 1 chữ số, 1 ký tự đặc biệt và không chứa khoảng trắng!";
        }
        if (getMatKhauXacNhan().isEmpty()) {
            fieldMatKhauXacNhan.requestFocus();
            return "Mật khẩu xác nhận không được để trống!";
        }
        if (!getMatKhauXacNhan().equals(getMatKhauMoi())) {
            fieldMatKhauXacNhan.requestFocus();
            return "Mật khẩu xác nhận không trùng khớp!";
        }
        return null;
    }

    public void addXacNhanListener(ActionListener l) {
        buttonXacNhan.addActionListener(l);
    }

    public void addHuyListener(ActionListener l) {
        buttonHuy.addActionListener(l);
    }

    public void showMessage(String thongBao) {
        JOptionPane.showMessageDialog(this, thongBao, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showWarning(String thongBao) {
        JOptionPane.showMessageDialog(this, thongBao, "Cảnh báo", JOptionPane.WARNING_MESSAGE);
    }

    public void showError(String thongBao) {
        JOptionPane.showMessageDialog(this, thongBao, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    public boolean showConfirm(String thongBao) {
        int option = JOptionPane.showConfirmDialog(this, thongBao, "Xác nhận", JOptionPane.YES_NO_OPTION);
        return option == JOptionPane.YES_OPTION;
    }
}
