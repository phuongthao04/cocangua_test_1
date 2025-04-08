package org.example;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class SettingUser extends JFrame {
    private JButton btnStart, btnSettings, btnRules;
    private JButton[] playerButtons;
    private JTextField[] playerNames;
    private int[] playerStates = {0, 0, 0, 0};
    private String[] iconPaths = {
            "images/img_user/icons/user.png",
            "images/img_user/icons/cpu.png",
            "images/img_user/icons/none.png"
    };

    public SettingUser() {
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
                new Color(0xff, 0xa8, 0xaa),
                new Color(0xcc, 0xed, 0xd1),
                new Color(0xff, 0xda, 0xc2)
        };

        for (int i = 0; i < 4; i++) {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 15));
            row.setOpaque(false);

            playerButtons[i] = new JButton(loadIcon(iconPaths[0], 40, 40));
            playerButtons[i].setPreferredSize(new Dimension(60, 45));
            playerButtons[i].setBackground(colors[i]);
            playerButtons[i].setFocusPainted(false);

            playerNames[i] = new JTextField("NGƯỜI CHƠI " + (i + 1), 15);
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

        btnSettings.addActionListener(e -> showSettings());
        btnRules.addActionListener(e -> showRules());
        btnStart.addActionListener(e -> startGame());
    }

    private void togglePlayer(int index) {
        playerStates[index] = (playerStates[index] + 1) % 3;
        playerButtons[index].setIcon(loadIcon(iconPaths[playerStates[index]], 40, 40));

        switch (playerStates[index]) {
            case 0:
                playerNames[index].setText("NGƯỜI CHƠI " + (index + 1));
                playerNames[index].setEditable(true);
                playerNames[index].setForeground(Color.BLACK);
                break;
            case 1:
                playerNames[index].setText("CHƠI VỐI MÁY");
                playerNames[index].setEditable(false);
                playerNames[index].setForeground(Color.GRAY);
                break;
            case 2:
                playerNames[index].setText("KHÔNG CÓ NGƯỜI CHƠI");
                playerNames[index].setEditable(false);
                playerNames[index].setForeground(Color.GRAY);
                break;
        }
    }

    private void showSettings() {
        JOptionPane.showMessageDialog(this, "Tùy chọn cài đặt đang phát triển...");
    }

    private void showRules() {
        JOptionPane.showMessageDialog(this, "Luật chơi đang cập nhật...");
    }

    private void startGame() {
        SwingUtilities.invokeLater(() -> {
            new Board();
            this.dispose();
        });
    }

    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            try {
                URL imageUrl = getClass().getClassLoader().getResource(imagePath);
                if (imageUrl != null) {
                    backgroundImage = new ImageIcon(imageUrl).getImage();
                } else {
                    System.out.println("Không tìm thấy ảnh: " + imagePath);
                }
            } catch (Exception e) {
                System.out.println("Lỗi khi load ảnh nền: " + e.getMessage());
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
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
        SwingUtilities.invokeLater(() -> {
            SettingUser game = new SettingUser();
            game.setVisible(true);
        });
    }
}