package org.example;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.net.URL;

public class Board extends JFrame {
    public Board() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        int frameWidth = 400;
        int frameHeight = 650;

        // Load ảnh nền bàn cờ
        URL boardImageURL = getClass().getClassLoader().getResource("images/chessboard.png");
        if (boardImageURL == null) {
            System.out.println("Không tìm thấy ảnh chessboard!");
            return;
        }
        ImageIcon originalIcon = new ImageIcon(boardImageURL);

        int newWidth = frameWidth;
        int newHeight = frameHeight;
        Image scaledImage = originalIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);
        setLayout(null);

        // Hiển thị ảnh nền
        JLabel background = new JLabel(scaledIcon) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                g2d.drawImage(scaledImage, 0, 0, null);
            }
        };
        background.setBounds(-8, 0, newWidth, newHeight);
        add(background);

        // Font, màu, viền
        Font playerFont = new Font("Arial", Font.BOLD, 14);
        Color textColor = Color.WHITE;
        LineBorder border = new LineBorder(Color.WHITE, 2, true);
        Color bluePlayer = new Color(66, 133, 244);
        Color greenPlayer = new Color(52, 168, 83);
        Color redPlayer = new Color(219, 68, 55);
        Color orangePlayer = new Color(244, 180, 0);

        int labelWidth = 100;
        int labelHeight = 26;

        JLabel player1 = new JLabel("Người chơi 1", SwingConstants.CENTER);
        player1.setFont(playerFont);
        player1.setForeground(textColor);
        player1.setBackground(bluePlayer);
        player1.setOpaque(true);
        player1.setBorder(border);
        player1.setBounds(35, 150, labelWidth, labelHeight);
        background.add(player1);

        JLabel player2 = new JLabel("Người chơi 2", SwingConstants.CENTER);
        player2.setFont(playerFont);
        player2.setForeground(textColor);
        player2.setBackground(greenPlayer);
        player2.setOpaque(true);
        player2.setBorder(border);
        player2.setBounds(frameWidth - labelWidth - 40, 150, labelWidth, labelHeight);
        background.add(player2);

        JLabel player3 = new JLabel("Người chơi 3", SwingConstants.CENTER);
        player3.setFont(playerFont);
        player3.setForeground(textColor);
        player3.setBackground(redPlayer);
        player3.setOpaque(true);
        player3.setBorder(border);
        player3.setBounds(35, frameHeight - 100, labelWidth, labelHeight);
        background.add(player3);

        JLabel player4 = new JLabel("Người chơi 4", SwingConstants.CENTER);
        player4.setFont(playerFont);
        player4.setForeground(textColor);
        player4.setBackground(orangePlayer);
        player4.setOpaque(true);
        player4.setBorder(border);
        player4.setBounds(frameWidth - labelWidth - 40, frameHeight - 100, labelWidth, labelHeight);
        background.add(player4);

        // ✅ Load ảnh setting icon đúng cách
        URL settingImageURL = getClass().getClassLoader().getResource("images/setting_ic.png");
        if (settingImageURL == null) {
            System.out.println("Không tìm thấy ảnh setting_ic!");
        } else {
            ImageIcon settingIcon = new ImageIcon(settingImageURL);
            int settingSize = 45;
            Image scaledSettingImage = settingIcon.getImage().getScaledInstance(settingSize, settingSize, Image.SCALE_SMOOTH);
            ImageIcon scaledSettingIcon = new ImageIcon(scaledSettingImage);

            JButton btnSetting = new JButton(scaledSettingIcon);
            btnSetting.setBounds(frameWidth - settingSize - 20, 10, settingSize, settingSize);
            btnSetting.setContentAreaFilled(false);
            btnSetting.setBorderPainted(false);
            background.add(btnSetting);
        }

        setVisible(true);
    }

    public static void main(String[] args) {
        new Board();
    }
}
