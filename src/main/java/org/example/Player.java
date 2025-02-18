package org.example;

import java.io.PrintWriter;
import java.util.ArrayList;

public class Player {
    private String name;
    private Deck deck;
    private ArrayList<Card> hand;
    private int score;
    private int playerWins; // Liczba wygranych gracza

    public Player(String name, Deck deck) {
        this.name = name;
        this.deck = deck;
        this.hand = new ArrayList<>();
        this.score = 0;
        this.playerWins = 0; // Inicjalizacja liczby wygranych
    }

    public void drawInitialHand(int round) {
        hand.clear(); // Opróżnij rękę na początku każdej rundy
        resetScore();
        int cardsToDraw = switch (round) {
            case 1 -> 6; // 6 kart w pierwszej rundzie
            case 2 -> 4; // 4 karty w drugiej rundzie
            case 3 -> 3; // 3 karty w dogrywce
            default -> 0;
        };

        for (int i = 0; i < cardsToDraw; i++) {
            Card drawnCard = deck.drawCard();
            if (drawnCard != null) {
                hand.add(drawnCard);
            } else {
                System.out.println(name + " cannot draw more cards - deck is empty.");
                break;
            }
        }
    }

    public void resetScore() {
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        score += points;
    }


    public void playCard(Card card) {
        hand.remove(card);
    }
}