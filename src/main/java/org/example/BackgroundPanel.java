package org.example;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class BackgroundPanel extends JPanel {
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
