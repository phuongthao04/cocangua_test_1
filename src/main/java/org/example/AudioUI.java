package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class AudioUI extends JFrame {
    private JCheckBox musicCheckbox;
    private JCheckBox soundCheckbox;
    private SettingUser settingUser;
    private AudioState audioState;
    private boolean isFirstOpen;

    public AudioUI(SettingUser settingUser, boolean musicEnabled, boolean soundEnabled) {
        this.settingUser = settingUser;
        this.audioState = AudioState.getInstance();
        this.isFirstOpen = !musicEnabled && !soundEnabled;

        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(340, 380);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Color.white);
        setLayout(new BorderLayout());

        JPanel greenPanel = new RoundedPanel(30, new Dimension(340, 380));
        greenPanel.setBackground(new Color(173, 224, 253));
        greenPanel.setLayout(new BoxLayout(greenPanel, BoxLayout.Y_AXIS));
        greenPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        greenPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel whitePanel = new RoundedPanel(30, new Dimension(330, 360));
        whitePanel.setBackground(Color.WHITE);
        whitePanel.setLayout(new GridBagLayout());
        whitePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel musicLabel = new JLabel("NHẠC");
        musicLabel.setFont(new Font("Arial", Font.BOLD, 20));
        musicCheckbox = new JCheckBox();
        musicCheckbox.setSelected(isFirstOpen ? true : musicEnabled);
        styleCheckbox(musicCheckbox);

        JLabel soundLabel = new JLabel("TIẾNG");
        soundLabel.setFont(new Font("Arial", Font.BOLD, 20));
        soundCheckbox = new JCheckBox();
        soundCheckbox.setSelected(isFirstOpen ? true : soundEnabled);
        styleCheckbox(soundCheckbox);

        JButton infoTextButton = new JButton("THÔNG TIN");
        styleRoundedButton(infoTextButton, new Color(156, 214, 243));

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 5, 10);
        whitePanel.add(musicLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        whitePanel.add(musicCheckbox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 10, 10);
        whitePanel.add(soundLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        whitePanel.add(soundCheckbox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        whitePanel.add(infoTextButton, gbc);

        greenPanel.add(whitePanel);

        JPanel centerWrap = new JPanel(new GridBagLayout());
        centerWrap.setBackground(new Color(100, 189, 212));
        centerWrap.add(greenPanel);
        add(centerWrap, BorderLayout.CENTER);

        infoTextButton.addActionListener(e -> {
            boolean finalMusicEnabled = musicCheckbox.isSelected();
            boolean finalSoundEnabled = soundCheckbox.isSelected();
            audioState.setMusicEnabled(finalMusicEnabled);
            audioState.setSoundEnabled(finalSoundEnabled);
            if (settingUser != null) {
                settingUser.setAudioSettings(finalMusicEnabled, finalSoundEnabled);
            }
            dispose();
        });
    }

    private void styleCheckbox(JCheckBox box) {
        ImageIcon uncheckedIcon = createCheckboxIcon(new Color(100, 189, 212), false);
        ImageIcon checkedIcon = createCheckboxIcon(new Color(255, 255, 255), true);

        box.setIcon(uncheckedIcon);
        box.setSelectedIcon(checkedIcon);

        box.setOpaque(false);
        box.setFocusPainted(false);
    }

    private ImageIcon createCheckboxIcon(Color color, boolean checked) {
        int size = 20;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setComposite(AlphaComposite.Clear);
        g2.fillRect(0, 0, size, size);
        g2.setComposite(AlphaComposite.SrcOver);

        g2.setColor(new Color(100, 189, 212));
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(2, 2, size - 4, size - 4);

        if (checked) {
            g2.setColor(color);
            g2.fillRect(2, 2, size - 4, size - 4);

            g2.setColor(new Color(0, 102, 204));
            g2.setStroke(new BasicStroke(2));
            g2.drawLine(size / 4, size / 2, size / 2, size - size / 4);
            g2.drawLine(size / 2, size - size / 4, size - size / 4, size / 4);
        }

        g2.dispose();
        return new ImageIcon(image);
    }

    private void styleRoundedButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        button.setOpaque(true);
    }

    class RoundedPanel extends JPanel {
        private int cornerRadius;
        private Dimension preferredSize;

        public RoundedPanel(int radius, Dimension preferredSize) {
            super();
            this.cornerRadius = radius;
            this.preferredSize = preferredSize;
            setOpaque(false);
        }

        @Override
        public Dimension getPreferredSize() {
            return preferredSize != null ? preferredSize : super.getPreferredSize();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
            g2.dispose();
            super.paintComponent(g);
        }
    }
}