package org.example;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.net.URL;
import java.util.List;

public class Board extends JFrame {
    private AudioState audioState;
    private boolean musicEnabled;
    private boolean soundEnabled;
    private List<PlayerInfo> players;

    public Board(boolean musicEnabled, boolean soundEnabled, List<PlayerInfo> players) {
        this.audioState = AudioState.getInstance();
        this.musicEnabled = musicEnabled;
        this.soundEnabled = soundEnabled;
        this.players = players;

        initUI();
    }

    private void initUI() {
        setTitle("Game Board");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);

        int frameWidth = 400;
        int frameHeight = 650;

        URL boardImageURL = getClass().getClassLoader().getResource("images/chessboard.png");
        if (boardImageURL == null) {
            System.out.println("Không tìm thấy ảnh chessboard!");
            return;
        }

        ImageIcon originalIcon = new ImageIcon(boardImageURL);
        Image scaledImage = originalIcon.getImage().getScaledInstance(frameWidth, frameHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel background = new JLabel(scaledIcon) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                g2d.drawImage(scaledImage, -7, 0, null);
            }
        };
        background.setBounds(0, 0, frameWidth, frameHeight);
        background.setLayout(null);
        add(background);

        Font playerFont = new Font("Arial", Font.BOLD, 14);
        Color textColor = Color.WHITE;
        LineBorder border = new LineBorder(Color.WHITE, 2, true);

        int[][] positions = {
                {35, 150},
                {frameWidth - 100 - 40, 150},
                {35, frameHeight - 100},
                {frameWidth - 100 - 40, frameHeight - 100}
        };

        int labelWidth = 100;
        int labelHeight = 26;

        for (int i = 0; i < players.size(); i++) {
            PlayerInfo player = players.get(i);

            if (player.getStatus() == 2) {
                JLabel label = new JLabel(); // không chữ, không màu nền
                label.setOpaque(false);
                label.setBounds(positions[i][0], positions[i][1], labelWidth, labelHeight);
                background.add(label);
            } else {
                JLabel label = new JLabel(player.getName(), SwingConstants.CENTER);
                label.setFont(playerFont);
                label.setForeground(textColor);
                label.setBackground(player.getPlayerColor());
                label.setOpaque(true);
                label.setBorder(border);
                label.setBounds(positions[i][0], positions[i][1], labelWidth, labelHeight);
                background.add(label);
            }
        }

        // Settings button
        URL settingImageURL = getClass().getClassLoader().getResource("images/setting_ic.png");
        if (settingImageURL != null) {
            ImageIcon settingIcon = new ImageIcon(settingImageURL);
            int settingSize = 45;
            Image scaledSettingImage = settingIcon.getImage().getScaledInstance(settingSize, settingSize, Image.SCALE_SMOOTH);
            ImageIcon scaledSettingIcon = new ImageIcon(scaledSettingImage);

            JButton btnSetting = new JButton(scaledSettingIcon);
            btnSetting.setBounds(frameWidth - settingSize - 20, 10, settingSize, settingSize);
            btnSetting.setContentAreaFilled(false);
            btnSetting.setBorderPainted(false);
            btnSetting.addActionListener(e -> {
                new AudioUI(null, audioState.isMusicEnabled(), audioState.isSoundEnabled()).setVisible(true);
            });

            background.add(btnSetting);
        } else {
            System.out.println("Image setting_ic not found!");
        }

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            List<PlayerInfo> samplePlayers = List.of(
                    new PlayerInfo("Người chơi 1", 1, Color.BLUE),
                    new PlayerInfo("Người chơi 2", 2,Color.green),
                    new PlayerInfo("Ẩn người chơi", 0,Color.RED), // sẽ bị ẩn
                    new PlayerInfo("Người chơi 4", 2,Color.orange)
            );

            new Board(true, true, samplePlayers);
        });
    }
}
