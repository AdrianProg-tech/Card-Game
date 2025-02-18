package org.example;

import com.raylib.Raylib;

import static com.raylib.Jaylib.*;
import static com.raylib.Raylib.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        InitWindow(1000, 800, "Nail Card Game - Player 1");
        SetTargetFPS(100);

        Texture background = TextureManager.getTexture(100); // Tu tekstura tła
        Texture winnerTexturePlayer1 = TextureManager.getTexture(104); // Tekstura dla gracza 1
        winnerTexturePlayer1.width(1000); // Wymiary okna x
        winnerTexturePlayer1.height(800); // Wymiary okna y
        Texture winnerTexturePlayer2 = TextureManager.getTexture(106); // Tekstura dla gracza 2
        winnerTexturePlayer2.width(1000); // Wymiary okna x
        winnerTexturePlayer2.height(800); // Wymiary okna y
        Texture drawTexture = TextureManager.getTexture(105); // Tekstura remisu
        drawTexture.width(1000); // Wymiary okna x
        drawTexture.height(800); // Wymiary okna y
        

        background.width(1000); // Wymiary okna x
        background.height(800); // Wymiary okna y


        // Tworzenie talii z przykładowych kart generowanych przez Card.generateDeck()
        List<Card> deck1Cards = Card.generateDeckForPlayer1();
        Deck deck1 = new Deck(deck1Cards);

        List<Card> deck2Cards = Card.generateDeckForPlayer2();
        Deck deck2 = new Deck(deck2Cards);

        Player player1 = new Player("Player 1", deck1);

        Player player2 = new Player("Player 2", deck2);
        Game game = new Game(player1, player2);
        GameDraw gameDraw = new GameDraw(game, player1, player2);
        GameUpdate gameUpdate = new GameUpdate(game, player1, player2);

        // Główna pętla gry
        while (!WindowShouldClose()) {
            BeginDrawing();
            ClearBackground(RAYWHITE);

            // Rysowanie tła
            DrawTexture(background, 0, 0, WHITE);

            // Aktualizacja i rysowanie gry
            gameUpdate.update();
            gameDraw.draw();

            // Sprawdzenie, czy gra się skończyła
            if (game.state == Game.GameState.GAME_OVER) {
                if (game.player1Wins > game.player2Wins) {
                    DrawTexture(winnerTexturePlayer1, 0, 0, WHITE);
                } else if (game.player2Wins > game.player1Wins) {
                    DrawTexture(winnerTexturePlayer2, 0, 0, WHITE);
                } else {
                    DrawTexture(drawTexture, 0, 0, WHITE);
                }
            }

            EndDrawing();
        }

        // Zwolnienie zasobów
        UnloadTexture(background);
        UnloadTexture(winnerTexturePlayer1);
        UnloadTexture(winnerTexturePlayer2);
        UnloadTexture(drawTexture);
        CloseWindow();
    }
}
