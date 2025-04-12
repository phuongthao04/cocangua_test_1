
package org.example;
import java.awt.*;
public class PlayerInfo {
    private String name;
    private int status;
    private Color playerColor; // Thêm thuộc tính màu cho người chơi

    // Constructor, getter và setter cho playerColor
    public PlayerInfo(String name, int status, Color playerColor) {
        this.name = name;
        this.status = status;
        this.playerColor = playerColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Color getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(Color playerColor) {
        this.playerColor = playerColor;
    }
}
