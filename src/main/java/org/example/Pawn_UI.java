package org.example;

import javax.swing.*;
import java.awt.*;

public class Pawn_UI extends JLabel {
    private String color; // ví dụ: "red", "blue"
    private int positionIndex = -1; // vị trí trên đường đi (-1 là trong chuồng)

    public Pawn_UI(String color, int x, int y) {
        this.color = color;
        ImageIcon originalIcon = new ImageIcon("src/images/" + color + "_pawn.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH); // scale nhỏ lại
        this.setIcon(new ImageIcon(scaledImage));
        this.setBounds(x, y, 34, 34); // vừa khít ô tròn
        this.setOpaque(false);


    }


    public void moveTo(int x, int y, int newIndex) {
        this.setLocation(x, y);
        this.positionIndex = newIndex;
    }

    public String getColor() {
        return color;
    }

    public int getPositionIndex() {
        return positionIndex;
    }

    public boolean isInBase() {
        return positionIndex == -1;
    }
}
