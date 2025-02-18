package org.example;

import java.util.ArrayList;

import static com.raylib.Raylib.*;

public class GameUpdate {
    private static final int MOUSE_LEFT_BUTTON = 0;
    private Game game;
    private Player player1;
    private Player player2;

    public GameUpdate(Game game, Player player1, Player player2) {
        this.game = game;
        this.player1 = player1;
        this.player2 = player2;
    }


    public void update() {
        if (game.state == Game.GameState.PLAYING) {
            if (IsMouseButtonPressed(MOUSE_LEFT_BUTTON)) {
                int mouseX = GetMouseX();
                int mouseY = GetMouseY();

                // Faza zagrywania karty
                if (game.attackingCard == null) {
                    if (game.currentPlayerTurn == 1) {
                        boolean cardPlayed = game.handlePlayerAction(player1, game.getBattlefield_front_player1, game.getBattlefield_back_player1, mouseX, mouseY, 600, 700, game.getBattlefield_front_player2);
                        if (cardPlayed && game.attackingCard == null) {
                            game.currentPlayerTurn = 2; // Zmiana tury, jeśli karta nie atakuje
                        }
                    } else if (game.currentPlayerTurn == 2) {
                        boolean cardPlayed = game.handlePlayerAction(player2, game.getBattlefield_front_player2, game.getBattlefield_back_player2, mouseX, mouseY, 0, 100, game.getBattlefield_front_player1);
                        if (cardPlayed && game.attackingCard == null) {
                            game.currentPlayerTurn = 1; // Zmiana tury, jeśli karta nie atakuje
                        }
                    }
                }
                // Faza wyboru celu
                else {
                    ArrayList<Card> opponentFrontBattlefield = game.currentPlayerTurn == 1 ? game.getBattlefield_front_player2 : game.getBattlefield_front_player1;
                    ArrayList<Card> opponentBackBattlefield = game.currentPlayerTurn == 1 ? game.getBattlefield_back_player2 : game.getBattlefield_back_player1;

                    Card targetCard = game.chooseTarget(mouseX, mouseY, opponentFrontBattlefield, opponentBackBattlefield);

                    if (targetCard != null) {
                        game.handleAttack(game.attackingCard, targetCard, opponentFrontBattlefield, opponentBackBattlefield);
                        game.attackingCard = null; // Zresetuj po ataku
                        game.currentPlayerTurn = game.currentPlayerTurn == 1 ? 2 : 1; // Zmień turę
                    }
                }
            }

            // Sprawdzenie, czy obaj gracze zagrali wszystkie karty
            if (player1.getHand().isEmpty() && player2.getHand().isEmpty()) {
                game.endRound();
            }
        } else if (game.state == Game.GameState.TIEBREAKER) {
            game.tiebreaker();
        }
    }
}