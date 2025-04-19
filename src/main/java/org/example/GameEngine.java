package org.example;

import java.util.*;

public class GameEngine {
    private List<PlayerInfo> players;
    private int currentPlayerIndex;
    private Random dice;
    private Board board;
    private Object PlayerInfo;

    public GameEngine(List<PlayerInfo> players, Board board) {
        this.players = players;
        this.board = board;
        this.currentPlayerIndex = 0;
        this.dice = new Random();
    }

    public GameEngine() {

    }

    public int rollDice() {
        return dice.nextInt(6) + 1;
    }

    public PlayerInfo getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public boolean canMoveOut(int diceRoll) {
        return diceRoll == 6;
    }

    public boolean movePawn(PlayerInfo player, int pawnIndex, int steps) {
        Pawn_UI pawn = player.getPawns().get(pawnIndex);

        // Nếu quân cờ còn trong chuồng
        if (pawn.isInBase()) {
            if (steps == 6) {  // Nếu ra được ngoài chuồng, quân cờ di chuyển đến vị trí xuất phát
                int startPos = board.getStartPosition(player);
                pawn.moveTo(board.getX(startPos), board.getY(startPos), startPos);
                return true; // Quân cờ đã di chuyển ra ngoài chuồng
            } else {
                return false; // Nếu không ra được, quân không di chuyển
            }
        }

        // Quân cờ đã ra ngoài chuồng, tính toán vị trí mới
        int currentPos = pawn.getPositionIndex();
        int newPos = currentPos + steps; // Tính vị trí mới sau khi di chuyển

        if (newPos > board.getEndPosition(player)) {
            return false; // Nếu quân cờ vượt quá đích, không di chuyển
        }

        // Di chuyển quân cờ
        pawn.moveTo(board.getX(newPos), board.getY(newPos), newPos);

        // Kiểm tra ăn quân đối phương
        for (PlayerInfo other : players) {
            if (other != player) { // Chỉ kiểm tra quân đối phương
                for (Pawn_UI otherPawn : other.getPawns()) {
                    // Nếu quân đối phương không ở trong chuồng và ở vị trí trùng với quân đang di chuyển
                    if (!otherPawn.isInBase() && otherPawn.getPositionIndex() == newPos) {
                        // Ăn quân đối phương và đưa về chuồng
                        otherPawn.moveTo(board.getBaseX(other, otherPawn), board.getBaseY(other, otherPawn), -1);
                        System.out.println(player.getName() + " đã ăn quân của " + other.getName());
                    }
                }
            }
        }

        // Kiểm tra nếu quân cờ đã về đích
        if (newPos == board.getEndPosition(player)) {
            System.out.println(player.getName() + " đã đưa 1 quân về chuồng!");

            // Kiểm tra người chơi đã thắng chưa
            if (checkWin(player)) {
                System.out.println(player.getName() + " đã chiến thắng!");
                // Bạn có thể gọi phương thức showLeaderboard hoặc làm gì đó để hiển thị chiến thắng
            }
        }

        return true;
    }


    public boolean checkWin(PlayerInfo player) {
        boolean isWinner = true;
        // Kiểm tra nếu tất cả quân của người chơi đã về đích
        for (Pawn_UI pawn : player.getPawns()) {
            if (pawn.getPositionIndex() != board.getEndPosition(player)) {
                isWinner = false;
                break; // Nếu có quân chưa về đích, người chơi chưa thắng
            }
        }

        if (isWinner) {
            System.out.println(player.getName() + " đã chiến thắng!");
            // Gọi phương thức để hiển thị bảng xếp hạng và cúp vàng
            showLeaderboard(player);
        }

        return isWinner;
    }
    // Phương thức hiển thị bảng xếp hạng
    private void showLeaderboard(PlayerInfo winner) {
        List<PlayerInfo> rankedPlayers = new ArrayList<>(players);
        // Sắp xếp người chơi dựa trên trạng thái hoàn thành
        rankedPlayers.sort((p1, p2) -> {
            // So sánh vị trí cuối cùng của quân cờ để xếp hạng
            int p1Score = 0;
            int p2Score = 0;
            for (Pawn_UI pawn : p1.getPawns()) {
                if (pawn.getPositionIndex() == board.getEndPosition(p1)) p1Score++;
            }
            for (Pawn_UI pawn : p2.getPawns()) {
                if (pawn.getPositionIndex() == board.getEndPosition(p2)) p2Score++;
            }
            return Integer.compare(p2Score, p1Score); // Sắp xếp giảm dần
        });

        // In ra bảng xếp hạng
        System.out.println("Bảng xếp hạng:");
        for (int i = 0; i < rankedPlayers.size(); i++) {
            PlayerInfo player = rankedPlayers.get(i);
            String rank = (i == 0) ? "🥇" : (i == 1) ? "🥈" : (i == 2) ? "🥉" : "";
            System.out.println(rank + " " + player.getName() + (player == winner ? " - 🏆 Cúp vàng!" : ""));
        }
    }

    public void initializeGame(List<PlayerInfo> players) {
            this.players = players;
            this.currentPlayerIndex = 0;
            System.out.println("Game đã được khởi tạo!");
    }
}
