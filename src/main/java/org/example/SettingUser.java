package org.example;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SettingUser extends JFrame {
    private JButton btnStart, btnSettings, btnRules;
    private JButton[] playerButtons;
    private JTextField[] playerNames;
    private int[] playerStates = {0, 0, 0, 0};
    private JPanel rulesPanel;
    private AudioState audioState;
    List<PlayerInfo> players = new ArrayList<>();
    private String[] iconPaths = {
            "images/img_user/icons/user.png",
            "images/img_user/icons/cpu.png",
            "images/img_user/icons/none.png"
    };

    public SettingUser() {
        audioState = AudioState.getInstance();

        setTitle("Cờ Cá Ngựa");
        setSize(400, 650);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel background = new BackgroundPanel("images/img_user/BackGround.png");
        background.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new GridLayout(6, 1, 5, 5));

        playerButtons = new JButton[4];
        playerNames = new JTextField[4];
        Color[] colors = {
                new Color(0xb5, 0xdd, 0xff),
                new Color(0xcc, 0xed, 0xd1),
                new Color(0xff, 0xa8, 0xaa),
                new Color(0xff, 0xda, 0xc2)
        };

        for (int i = 0; i < 4; i++) {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 15));
            row.setOpaque(false);

            playerButtons[i] = new JButton(loadIcon(iconPaths[0], 40, 40));
            playerButtons[i].setPreferredSize(new Dimension(60, 45));
            playerButtons[i].setBackground(colors[i]);
            playerButtons[i].setFocusPainted(false);

            playerNames[i] = new JTextField("PLAYER " + (i + 1), 15);
            playerNames[i].setFont(new Font("Segoe UI", Font.BOLD, 16));
            playerNames[i].setPreferredSize(new Dimension(100, 45));
            playerNames[i].setBackground(colors[i]);
            playerNames[i].setHorizontalAlignment(JTextField.CENTER);

            int index = i;
            playerButtons[i].addActionListener(e -> togglePlayer(index));

            row.add(playerButtons[i]);
            row.add(playerNames[i]);
            panel.add(row);
        }

        btnStart = new JButton(loadIcon("images/img_user/icons/start.png", 40, 40));
        btnStart.setPreferredSize(new Dimension(100, 50));
        btnStart.setBackground(colors[2]);
        btnStart.setFocusPainted(false);

        btnSettings = new JButton(loadIcon("images/img_user/icons/settings.png", 40, 40));
        btnSettings.setPreferredSize(new Dimension(100, 50));
        btnSettings.setBackground(colors[3]);
        btnSettings.setFocusPainted(false);

        btnRules = new JButton(loadIcon("images/img_user/icons/rules.png", 40, 40));
        btnRules.setPreferredSize(new Dimension(100, 50));
        btnRules.setBackground(colors[1]);
        btnRules.setFocusPainted(false);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        bottomPanel.setOpaque(false);
        bottomPanel.add(btnSettings);
        bottomPanel.add(btnStart);
        bottomPanel.add(btnRules);

        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setOpaque(false);
        wrapperPanel.setLayout(new BorderLayout());
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(180, 0, 0, 0));

        wrapperPanel.add(panel, BorderLayout.NORTH);
        wrapperPanel.add(bottomPanel, BorderLayout.SOUTH);

        background.add(wrapperPanel, BorderLayout.CENTER);
        setContentPane(background);

        btnStart.addActionListener(e -> startGame());

        btnSettings.addActionListener(e -> new AudioUI(this, audioState.isMusicEnabled(), audioState.isSoundEnabled()).setVisible(true));
        btnRules.addActionListener(e -> rulesPanel.setVisible(true));

        createRulesPanel();
    }

    private void createRulesPanel() {
        rulesPanel = new JPanel(null);
        rulesPanel.setBounds(0, 0, getWidth(), getHeight());
        rulesPanel.setBackground(new Color(100, 189, 212));
        rulesPanel.setVisible(false);

        JPanel dialog = new JPanel();
        dialog.setLayout(null);
        dialog.setBounds(30, 20, 320, 520);
        dialog.setBackground(Color.WHITE);
        dialog.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 108, 108), 8, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JEditorPane rulesText = new JEditorPane();
        rulesText.setContentType("text/html");
        rulesText.setEditable(false);
        rulesText.setText(
                "<html><body style='font-family:Segoe UI; font-size:11px; color:#cc0000;'>"
                        + "<h2 style='text-align:center; color:#8B0000;'>Luật chơi</h2>"
                        + "<table style='width:100%;'>"
                        + "<tr><td><b>Tung xúc xắc:</b></td><td>Ai tung được \"1\", \"6\", hoặc cặp số giống nhau thì được tung tiếp.</td></tr>"
                        + "<tr><td><b>Ra quân:</b></td><td>Cần tung \"1\", \"6\" hoặc cặp số giống nhau mới ra quân.</td></tr>"
                        + "<tr><td><b>Di chuyển quân:</b></td><td>Đi đúng số bước. Đá quân đối phương nếu vào đúng ô.</td></tr>"
                        + "<tr><td><b>Vào chuồng:</b></td><td>Đi đúng số thứ tự 3→6 để vào chuồng. Ai đưa 4 quân vào trước là thắng.</td></tr>"
                        + "</table></body></html>"
        );
        rulesText.setBounds(15, 10, 290, 480);
        dialog.add(rulesText);

        rulesPanel.add(dialog);

        JButton btnBack = new JButton("OK");
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnBack.setForeground(Color.WHITE);
        btnBack.setBounds(150, 550, 100, 40);
        btnBack.setBackground(new Color(255, 128, 128));
        btnBack.setFocusPainted(false);
        btnBack.setBorder(BorderFactory.createLineBorder(new Color(255, 128, 128), 5, true));
        btnBack.addActionListener(e -> rulesPanel.setVisible(false));
        rulesPanel.add(btnBack);

        getLayeredPane().add(rulesPanel, JLayeredPane.POPUP_LAYER);
    }

    private void togglePlayer(int index) {
        playerStates[index] = (playerStates[index] + 1) % 3;
        playerButtons[index].setIcon(loadIcon(iconPaths[playerStates[index]], 40, 40));

        switch (playerStates[index]) {
            case 0:
                playerNames[index].setText("PLAYER " + (index + 1));
                playerNames[index].setEditable(true);
                playerNames[index].setForeground(Color.BLACK);
                break;
            case 1:
                playerNames[index].setText("BOT");
                playerNames[index].setEditable(false);
                playerNames[index].setForeground(Color.GRAY);
                break;
            case 2:
                playerNames[index].setText("KHÔNG CÓ NGƯỜI CHƠI");
                playerNames[index].setEditable(false);
                playerNames[index].setForeground(Color.GRAY);
                break;
        }

        checkStartButtonState();
    }

    private void checkStartButtonState() {
        int inactiveCount = 0;
        int humanCount = 0;

        for (int i = 0; i < 4; i++) {
            if (playerStates[i] == 2) inactiveCount++;
            if (playerStates[i] == 0) humanCount++;
        }

        btnStart.setEnabled(!(inactiveCount >= 3 || humanCount == 0));
    }

    private void startGame() {
        players.clear();

        Color[] playerColors = {
                new Color(0xb5, 0xdd, 0xff),
                new Color(0xcc, 0xed, 0xd1),
                new Color(0xff, 0xa8, 0xaa),
                new Color(0xff, 0xda, 0xc2)
        };

        for (int i = 0; i < 4; i++) {
            if (i >= playerStates.length || i >= playerNames.length) continue;

            String name = playerNames[i].getText().trim();
            players.add(new PlayerInfo(name, playerStates[i], playerColors[i]));
        }

        // Gọi lớp GameEngine để khởi tạo logic game
        GameEngine gameEngine = new GameEngine();
        gameEngine.initializeGame(players);

        // Giao diện chính của bàn cờ
        new Board(audioState.isMusicEnabled(), audioState.isSoundEnabled(), players);
        this.dispose();
    }

    public void setAudioSettings(boolean musicEnabled, boolean soundEnabled) {
        audioState.setMusicEnabled(musicEnabled);
        audioState.setSoundEnabled(soundEnabled);
    }

    private ImageIcon loadIcon(String path, int width, int height) {
        URL imageUrl = getClass().getClassLoader().getResource(path);
        if (imageUrl != null) {
            ImageIcon icon = new ImageIcon(imageUrl);
            Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        } else {
            System.out.println("Không tìm thấy ảnh: " + path);
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SettingUser().setVisible(true));
    }
}
