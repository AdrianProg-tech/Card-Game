package org.example;

import com.raylib.Raylib;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.raylib.Raylib.LoadTexture;
import static com.raylib.Raylib.UnloadTexture;

public class Card  implements Serializable {
    private static final long serialVersionUID = 1L; //
    private String name;
    private final int ID;
    private int power;
    private boolean specialAbility;
    private static boolean frontLine;
    private boolean isHighlighted = false;
    private int x; // Pozycja X karty
    private int y; // Pozycja Y karty
    private int attackPower; // Wartość, o którą zmniejsza moc przeciwnika
    private String imagePath; // Ścieżka do obrazka karty
    private int imageID; // ID obrazka karty
    private boolean hasAttacked;
    private boolean hasRedCross = false;

    public Card(String name, int ID, int power, boolean specialAbility, boolean frontLine, int attackPower, int imageID) {
        this.name = name;
        this.power = power;
        this.specialAbility = specialAbility;
        this.frontLine = frontLine;
        this.attackPower = attackPower;
        //this.imagePath = imagePath;
        this.ID = ID;
        this.hasAttacked = false;

        this.imageID = imageID;
    }

    public void setRedCross(boolean value) {
        hasRedCross = value;
    }

    // Gettery i settery
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }

    public void setHighlighted(boolean highlighted) {
        isHighlighted = highlighted;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        // Uwzględnij punkty ataku, jeśli karta ma je zdefiniowane
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public static boolean isFrontLine() {
        return frontLine;
    }

    public Raylib.Texture getCardTexture() {
        return TextureManager.getTexture(imageID);
    }

    @Override
    public String toString() {
        return name + " (" + ID + ") - Power: " + power + (specialAbility ? " [Special Ability]" : "");
    }

    // Generowanie przykładowej talii
    public static List<Card> generateDeckForPlayer1() {
        List<Card> deck = new ArrayList<>();

        // Dodawanie przykładowych kart z grafikami
        deck.add(new Card("", 1, 15, true, true, 10, 1));
        deck.add(new Card("", 2, 8, false, false, 5, 2));
        deck.add(new Card("", 3, 10, false, false, 8, 3));
        deck.add(new Card("", 4, 5, true, true, 0, 4));
        deck.add(new Card("", 5, 6, true, true, 0, 5));
        deck.add(new Card("", 6, 8, true, true, 0, 6));
        deck.add(new Card("", 7, 7, true, true, 0, 7));
        deck.add(new Card("", 8, 2, true, true, 0, 8));
        deck.add(new Card("", 9, 2, false, false, 0, 9));
        deck.add(new Card("", 10, 5, false, false, 0, 10));
        deck.add(new Card("", 11, 5, false, false, 0, 11));
        deck.add(new Card("", 12, 6, false, false, 0, 12));
        deck.add(new Card("", 13, 8, false, false, 0, 13));

        Collections.shuffle(deck);
        return deck;
    }

    public static List<Card> generateDeckForPlayer2() {
        List<Card> deck = new ArrayList<>();

        deck.add(new Card("", 20, 15, true, true, 10, 20));
        deck.add(new Card("", 21, 8, false, false, 5, 21));
        deck.add(new Card("", 22, 10, false, false, 8, 22));
        deck.add(new Card("", 23, 5, true, true, 0, 23));
        deck.add(new Card("", 24, 6, true, true, 0, 24));
        deck.add(new Card("", 25, 8, true, true, 0, 25));
        deck.add(new Card("", 26, 7, true, true, 0, 26));
        deck.add(new Card("", 27, 2, true, true, 0, 27));
        deck.add(new Card("", 28, 2, false, false, 0, 28));
        deck.add(new Card("", 29, 5, false, false, 0, 29));
        deck.add(new Card("", 30, 5, false, false, 0, 30));
        deck.add(new Card("", 31, 6, false, false, 0, 31));
        deck.add(new Card("", 32, 8, false, false, 0, 32));
        Collections.shuffle(deck);

        return deck;
    }
}
