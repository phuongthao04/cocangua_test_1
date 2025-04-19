package org.example;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerInfo {
    private String name;
    private int status;
    private Color playerColor;
    private List<Pawn_UI> pawns;

    public PlayerInfo(String name, int status, Color playerColor, List<Pawn_UI> pawns) {
        this.name = name;
        this.status = status;
        this.playerColor = playerColor;
        this.pawns = pawns;
    }

    public PlayerInfo(String name, int playerState, Color playerColor) {
        this.name = name;
        this.status = playerState;
        this.playerColor = playerColor;
        this.pawns = new ArrayList<>(); // Tạo một danh sách quân cờ trống nếu không truyền vào
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

    public List<Pawn_UI> getPawns() {
        return pawns;
    }

    public void setPawns(List<Pawn_UI> pawns) {
        this.pawns = pawns;
    }
}
