package com.npmtt.ticketclient.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public abstract class BasePanel extends JPanel {

    protected final Color PRIMARY_COLOR = new Color(60, 179, 113);
    protected final Color SECONDARY_COLOR = new Color(153, 204, 255);
    protected final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    protected final Font FONT_PLAIN = new Font("Segoe UI", Font.PLAIN, 14);

    public BasePanel() {
        setFont(FONT_PLAIN);
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1200, 640));
    }

    public void showMessage(String thongBao) {
        JOptionPane.showMessageDialog(this, thongBao, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showWarning(String thongBao) {
        JOptionPane.showMessageDialog(this, thongBao, "Chú ý", JOptionPane.WARNING_MESSAGE);
    }

    public void showError(String thongBao) {
        JOptionPane.showMessageDialog(this, thongBao, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    public boolean showConfirm(String thongBao) {
        int option = JOptionPane.showConfirmDialog(this, thongBao, "Xác nhận", JOptionPane.YES_NO_OPTION);
        return option == JOptionPane.YES_OPTION;
    }

    protected JComponent createInputField(String labelText, JComponent component, Color background) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(background);

        JLabel label = new JLabel(labelText);
        label.setFont(FONT_PLAIN);
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(100, 50));
        panel.add(label);

        component.setForeground(Color.BLACK);
        component.setPreferredSize(new Dimension(200, 50));
        panel.add(component);

        return panel;
    }

    protected JComponent createButtonField(JButton[] buttons, Color background) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.setBackground(background);

        for (JButton button : buttons) {
            panel.add(button);
        }

        return panel;
    }

    protected JButton createStyledButton(String text, Dimension buttonSize, Color background, Color foreground) {
        JButton button = new JButton(text);
        button.setFont(FONT_BOLD);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setPreferredSize(buttonSize);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    protected abstract void initComponents();
}
