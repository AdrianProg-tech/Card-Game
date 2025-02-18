package org.example;

import com.raylib.Raylib;

import java.util.List;

import static com.raylib.Jaylib.*;
import static com.raylib.Jaylib.RAYWHITE;
import static com.raylib.Raylib.ClearBackground;
import static com.raylib.Raylib.DrawRectangle;
import static com.raylib.Raylib.DrawText;//Rysowanie w oknie napisów jak nazwy graczy i nazwy kart albo liczba punktów na karcie
import static org.example.Game.*;


public class GameDraw {
    private Game game; // Reference to the Game instance
    private Player player1; // Reference to Player 1
    private Player player2; // Reference to Player 2

    public GameDraw(Game game, Player player1, Player player2) {
        this.game = game;
        this.player1 = player1;
        this.player2 = player2;
    }
    //Rysowanie w oknie napisów jak nazwy graczy i nazwy kart albo liczba punktów na karcie
    public void draw() {
        if (game.state == Game.GameState.PLAYING) {
            ClearBackground(DARKGRAY);

            if (game.state == Game.GameState.PLAYING) {

                if (game.currentPlayerTurn == 1) {
                    DrawTexture(game.player1TurnTexture,870, 350, WHITE); // Wyświetlanie tekstury dla gracza 1 poprzez obraz monety
                } else if (game.currentPlayerTurn == 2) {
                    DrawTexture(game.player2TurnTexture, 870, 350, WHITE); // Wyświetlanie tekstury dla gracza 2 poprzez obraz monety
                }

                // Wyświetlanie informacji o rundzie i wynikach
                DrawText("Round: " + roundCounter, 20, 2, 20, WHITE);
                DrawText("Player 1 Wins: " + game.player1Wins, 20, 25, 20, BLUE);
                DrawText("Player 2 Wins: " + game.player2Wins, 20, 50,20, RED);

            }
            // Wyświetlanie punktacji gracza
            DrawText(" " + player1.getScore(), 885, 600, 40, BLUE);
            DrawText(" " + player2.getScore(), 885, 150, 40, RED);

            // Rysowanie kart gracza 1 w ręce z pozycją startową xPos = 300
            int xPos = 300;
            int yPos = 600;
            for (Card card : player1.getHand()) {
                DrawTexture(card.getCardTexture(), xPos, yPos, LIGHTGRAY); // Rysuje kwadracik biały w ręce gracza zwany kartą
                DrawText(card.getName(), xPos + 5, yPos + 10, 10, DARKGRAY); // Wypisuje Nazwe karty
                DrawText(String.valueOf(card.getPower()), xPos + 30, yPos + 70, 20, DARKGRAY);  //Wypisuje punkty w kartach
                xPos += 80;
            }

            // Rysowanie kart gracza 2 w ręce
            int xPos2 = 300;
            int yPos2 = 5;
            for (Card card : player2.getHand()) {
                DrawTexture(card.getCardTexture(), xPos2, yPos2, LIGHTGRAY); // Rysuje kwadracik biały w ręce gracza zwany kartą
                card.setPosition(xPos2, yPos2);
                DrawText(card.getName(), xPos2 + 5, yPos2 + 10, 10, DARKGRAY); // Wypisuje Nazwe karty
                DrawText(String.valueOf(card.getPower()), xPos2 + 30, yPos2 + 70, 20, DARKGRAY);  //Wypisuje punkty w kartach
                xPos2 += 80;
            }

            // Rysowanie kart na battlefield front (gracz 1)
            int frontPosX1 = 300; // Początkowa pozycja X dla kart gracza 1 (front)
            int frontPosY1 = 340; // Początkowa pozycja Y dla kart gracza 1 (front)
            for (Card card : game.getBattlefield_front_player1) {
                Raylib.Color cardColor = card.isHighlighted() ? YELLOW : DARKGRAY; // Wybieramy kolor
                DrawTexture(card.getCardTexture(), frontPosX1, frontPosY1, LIGHTGRAY); // Rysuje kwadracik biały w ręce gracza zwany kartą
                card.setPosition(frontPosX1, frontPosY1);
                DrawText(card.getName(), frontPosX1 + 5, frontPosY1 + 10, 10, RAYWHITE); // Wypisujemy nazwę karty
                DrawText(String.valueOf(card.getPower()), frontPosX1 + 30, frontPosY1 + 70, 20, BLUE); // Wypisujemy punkty karty
                frontPosX1 += 80; // Przesunięcie pozycji dla kolejnej karty
            }

// Rysowanie kart na battlefield back (gracz 1)
            int backPosX1 = 300;
            int backPosY1 = 475;
            for (Card card : game.getBattlefield_back_player1) {
                Raylib.Color cardColor = card.isHighlighted() ? YELLOW : DARKGRAY; // Wybieramy kolor
                DrawTexture(card.getCardTexture(), backPosX1, backPosY1, LIGHTGRAY); // Rysuje kwadracik biały w ręce gracza zwany kartą
                card.setPosition(backPosX1, backPosY1);
                DrawText(card.getName(), backPosX1 + 5, backPosY1 + 10, 10, RAYWHITE); // Wypisujemy nazwę karty
                DrawText(String.valueOf(card.getPower()), backPosX1 + 30, backPosY1 + 70, 20, BLUE); // Wypisujemy punkty karty
                backPosX1 += 80; // Przesunięcie pozycji dla kolejnej karty

            }

// Rysowanie kart na battlefield front (gracz 2)
            int frontPosX2 = 300;
            int frontPosY2 = 210;
            for (Card card : game.getBattlefield_front_player2) {
                Raylib.Color cardColor = card.isHighlighted() ? YELLOW : DARKGRAY; // Wybieramy kolor
                DrawTexture(card.getCardTexture(), frontPosX2, frontPosY2, LIGHTGRAY); // Rysuje kwadracik biały w ręce gracza zwany kartą
                card.setPosition(frontPosX2, frontPosY2);
                DrawText(card.getName(), frontPosX2 + 5, frontPosY2 + 10, 10, RAYWHITE); // Wypisujemy nazwę karty
                DrawText(String.valueOf(card.getPower()), frontPosX2 + 30, frontPosY2 + 70, 20, RED); // Wypisujemy punkty karty
                frontPosX2 += 80; // Przesunięcie pozycji dla kolejnej karty
            }

// Rysowanie kart na battlefield back (gracz 2)
            int backPosX2 = 300;
            int backPosY2 = 80;
            for (Card card : game.getBattlefield_back_player2) {
                Raylib.Color cardColor = card.isHighlighted() ? YELLOW : DARKGRAY; // Wybieramy kolor
                DrawTexture(card.getCardTexture(), backPosX2, backPosY2, LIGHTGRAY); // Rysuje kwadracik biały w ręce gracza zwany kartą
                card.setPosition(backPosX2, backPosY2);
                DrawText(card.getName(), backPosX2 + 5, backPosY2 + 10, 10, RAYWHITE); // Wypisujemy nazwę karty
                DrawText(String.valueOf(card.getPower()), backPosX2 + 30, backPosY2 + 70, 20, RED); // Wypisujemy punkty karty
                backPosX2 += 80; // Przesunięcie pozycji dla kolejnej karty
            }

        } else if (game.state == Game.GameState.GAME_OVER) {
            // Wyświetlenie wiadomości o zwycięzcy
            ClearBackground(DARKGRAY);
            DrawText(winnerMessage, 320, 300, 40, RAYWHITE);
        }
    }
}