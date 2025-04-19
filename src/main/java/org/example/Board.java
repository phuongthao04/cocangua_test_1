package org.example;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Board extends JFrame {
    private AudioState audioState;
    private boolean musicEnabled;
    private boolean soundEnabled;
    private List<PlayerInfo> players;
    private List<Pawn_UI> redPawns = new ArrayList<>();
    private List<Pawn_UI> orangePawns = new ArrayList<>();
    private List<Pawn_UI> greenPawns = new ArrayList<>();
    private List<Pawn_UI> bluePawns = new ArrayList<>();
    private JLabel background; // Đưa background thành biến toàn cục

    public Board(boolean musicEnabled, boolean soundEnabled, List<PlayerInfo> players) {
        this.audioState = AudioState.getInstance();
        this.musicEnabled = musicEnabled;
        this.soundEnabled = soundEnabled;
        this.players = players;

        initUI();
//        initRedPawns();
//        initOrangePawns();
//        initGreenPawns();
//        initBluePawns();
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

        background = new JLabel(scaledIcon) {
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
                JLabel label = new JLabel(); // Không hiển thị gì với bot
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

                // === Khởi tạo quân cờ tương ứng với màu ===
                Color color = player.getPlayerColor();

                if (color.equals(new Color(219, 68, 55))) {
                    initRedPawns();
                } else if (color.equals(new Color(250, 144, 58))) {
                    initOrangePawns();
                } else if (color.equals(new Color(52, 168, 83))) {
                    initGreenPawns();
                } else if (color.equals(new Color(66, 133, 244))) {
                    initBluePawns();
                }
            }
        }

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

    private void initRedPawns() {
        int[][] redStartPositions = {
                {48, 438}, {84, 438},
                {48, 476}, {84, 476}
        };
        for (int i = 0; i < 4; i++) {
            Pawn_UI p = new Pawn_UI("red", redStartPositions[i][0], redStartPositions[i][1]);
            redPawns.add(p);
            background.add(p);
        }
    }

    private void initGreenPawns() {
        int[][] greenStartPositions = {
                {269, 218}, {308, 218},
                {269, 254}, {308, 254}
        };
        for (int i = 0; i < 4; i++) {
            Pawn_UI p = new Pawn_UI("green", greenStartPositions[i][0], greenStartPositions[i][1]);
            greenPawns.add(p);
            background.add(p);
        }
    }
    private void initBluePawns() {
        int[][] blueStartPositions = {
                {48, 218}, {86, 218},
                {48, 254}, {86, 254}
        };
        for (int i = 0; i < 4; i++) {
            Pawn_UI p = new Pawn_UI("blue", blueStartPositions[i][0], blueStartPositions[i][1]);
            bluePawns.add(p);
            background.add(p);
        }
    }


    private void initOrangePawns() {
        int[][] orangeStartPositions = {
                {271, 438}, {308, 438},
                {271, 476}, {308, 476}
        };
        for (int i = 0; i < 4; i++) {
            Pawn_UI p = new Pawn_UI("orange", orangeStartPositions[i][0], orangeStartPositions[i][1]);
            orangePawns.add(p);
            background.add(p);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            List<PlayerInfo> players = new ArrayList<>(); // Đây là ví dụ, hãy thay đổi tùy vào nhu cầu
            new Board(true, true, players);  // Khởi tạo Board với các tham số bạn muốn cho bot
        });
    }

}
