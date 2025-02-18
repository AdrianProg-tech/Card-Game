package org.example;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private ArrayList<Card> cards;

    public Deck(List<Card> cards) {
        this.cards = new ArrayList<>(cards);
    }

    public Card drawCard() {
        if (cards.isEmpty()) {
            System.out.println("Warning: Attempted to draw from an empty deck.");
            return null;
        }
        return cards.remove(cards.size() - 1); // Usunięcie górnej karty
    }
}
