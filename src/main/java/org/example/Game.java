package org.example;

import com.raylib.Raylib;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.raylib.Jaylib.*;

public class Game {
    public Player player1;
    public Player player2;

    public static int roundCounter; // Licznik rund
    public int player1Wins;  // Liczba wygranych gracza 1
    public int player2Wins;  // Liczba wygranych gracza 2

    public static final int FRONT_LINE_Y_PLAYER1 = 340;
    public static final int BACK_LINE_Y_PLAYER1 = 475;
    public static final int FRONT_LINE_Y_PLAYER2 = 210;
    public static final int BACK_LINE_Y_PLAYER2 = 80;

    // Flaga, czy gracz jest w fazie ataku
    public boolean isAttackPhase = false;
    public boolean isAttackInProgress = false; // Flaga do sprawdzania, czy są ataki w toku

    public  ArrayList<Card> getBattlefield_front_player1;  // Atak ofensywny gracza player 1
    public  ArrayList<Card> getBattlefield_back_player1; // Atak defensywny gracza player 1

    public  ArrayList<Card> getBattlefield_front_player2; // Atak ofensywny gracza player 2
    public  ArrayList<Card> getBattlefield_back_player2; // Atak defensywny gracza player 2

    public Card attackingCard = null; // Przechowuje kartę, która będzie atakować

    public int currentPlayerTurn; // 1 - tura gracza 1, 2 - tura gracza 2
    public Texture player2TurnTexture =  TextureManager.getTexture(102); // Tu działa tekstura monety dla gracza 2
    public Texture player1TurnTexture =  TextureManager.getTexture(101); // Tu działa tekstura monety dla gracza 1
    public Texture winnerTexture;

    //   private Texture redCrossTexture = LoadTexture("Textures/pocisk.png");

    // Stan gry
    public enum GameState {
        PLAYING,
        TIEBREAKER,
        GAME_OVER
    }

    GameState state;
    public static String winnerMessage; //Wypisuje kto wygrał pod koniec gry

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.getBattlefield_front_player1 = new ArrayList<>();
        this.getBattlefield_back_player1 = new ArrayList<>();
        this.getBattlefield_front_player2 = new ArrayList<>();
        this.getBattlefield_back_player2 = new ArrayList<>();

        this.roundCounter = 1;  // od 1 rundy zaczynamy gre
        this.player1Wins = 0; // zaczynamy od 0 punktów dla playera 1
        this.player2Wins = 0; // zaczynamy od 0 punktów dla playera 2

        this.state = GameState.PLAYING;
        Random rand = new Random();  // Random dla monety pokazująca który z graczy zaczyna ture
        this.player1 = player1;
        this.player2 = player2;

        // Losowanie, który gracz zaczyna
        currentPlayerTurn = (new Random().nextInt(2)) + 1;

        // Dobranie kart na start
        player1.drawInitialHand(roundCounter);
        player2.drawInitialHand(roundCounter);

    }

   /* public void updateGameState(Player player, List<Integer> battlefieldCardIds) {
        // Zaktualizuj karty na planszy dla danego gracza
        List<Card> battlefieldCards = new ArrayList<>();
        for (Integer cardId : battlefieldCardIds) {
            Card card = player.getHand().stream()
                    .filter(c -> c.getId() == cardId)
                    .findFirst()
                    .orElse(null);
            if (card != null) {
                battlefieldCards.add(card);
            }
        }

        // Dodaj karty do odpowiednich pól bitwy
        if (player == player1) {
            getBattlefield_front_player1.addAll(battlefieldCards);
        } else {
            getBattlefield_front_player2.addAll(battlefieldCards);
        }
    } */

    public void endRound() {
        int player1Score = player1.getScore();
        int player2Score = player2.getScore();
        Texture winnerTexture;

        // Zaktualizowanie wyników na podstawie wyników rundy
        if (player1Score > player2Score) {
            player1Wins++;
        } else if (player2Score > player1Score) {
            player2Wins++;
        } else {
            player1Wins++;
            player2Wins++;
        }

        // Sprawdzenie, czy gra powinna zakończyć się po 2 rundach
        if (roundCounter >= 2) {
            if (player1Wins == player2Wins) {
                // Jeśli po dwóch rundach jest remis, przechodzimy do dogrywki
                state = GameState.TIEBREAKER;
            } else {
                // Jeśli jeden z graczy wygrał więcej rund, gra się kończy
                state = GameState.GAME_OVER;
                winnerMessage = (player2Wins < player1Wins) ? "Player 1 wins!" : "Player 2 wins!";

                // Zapisanie wyniku do pliku po zakończeniu gry
                String winner = (player1Wins > player2Wins) ? "Player 1" : "Player 2";

                File.writeGameResult(winner, roundCounter);
            }
        }else {
            // Przygotowanie do kolejnej rundy
            roundCounter++;
            resetRound();
        }
    }

    //Dogrywka jeśli gracze po w 2 turze mają po jednym punkcje wygrany
    public void tiebreaker() {
        if (state == GameState.TIEBREAKER) {
            player1.drawInitialHand(3);
            player2.drawInitialHand(3);

            getBattlefield_front_player1.clear();
            getBattlefield_back_player1.clear();
            getBattlefield_front_player2.clear();
            getBattlefield_back_player2.clear();

            state = GameState.PLAYING;

            if (player1Wins > player2Wins) {
                //winnerMessage = "Gracz 1 wygrywa grę w dogrywce!";
                winnerMessage = "Player 1 wins!";
                state = GameState.GAME_OVER;
            } else if (player2Wins > player1Wins) {
                //winnerMessage = "Gracz 2 wygrywa grę w dogrywce!";
                winnerMessage = "Player 2 wins!";
                state = GameState.GAME_OVER;
            } else {
                if (roundCounter > 3) {
                    winnerMessage = "DRAW!"; // żeby nie było że gracze będą grać nie wiadomo ile w tej dogrywce więc jak w dogrywce nadal będzie dogrywka to poprostu Remis jest
                    state = GameState.GAME_OVER;
                } else {
                    state = GameState.PLAYING;
                }
            }
        }
    }

    public void resetRound() {
        player1.drawInitialHand(roundCounter);
        player2.drawInitialHand(roundCounter);

        getBattlefield_front_player1.clear();
        getBattlefield_back_player1.clear();
        getBattlefield_front_player2.clear();
        getBattlefield_back_player2.clear();

        state = GameState.PLAYING;
    }


    public void drawCardSet(List<Card> cards, int startX, int startY, int spacingX) {
        int xPos = startX;
        for (Card card : cards) {
            DrawRectangle(xPos, startY, 60, 100, LIGHTGRAY); // Rysowanie tła karty
            DrawText(card.getName(), xPos + 5, startY + 10, 10, DARKGRAY); // Nazwa karty
            DrawText(String.valueOf(card.getPower()), xPos + 20, startY + 40, 20, DARKGRAY); // Moc karty
            xPos += spacingX; // Następna karta
        }
    }


    public void handleAttack(Card attackingCard, Card targetCard, ArrayList<Card> opponentFrontBattlefield, ArrayList<Card> opponentBackBattlefield) {
        if (attackingCard == null || targetCard == null) {
            System.out.println("Błąd: karta atakująca lub cel jest pusty.");
            return;
        }

        // Sprawdzenie, czy cel jest dozwolony
        if (isFrontCard(attackingCard) && !isFrontCard(targetCard)) {
            System.out.println("Karta z frontu może atakować tylko karty z frontu przeciwnika.");
            return;
        }

        if (isBackCard(attackingCard)) {
            // Back może atakować zarówno front, jak i back - brak dodatkowych warunków
        }

        // Oblicz wynik ataku
        targetCard.setPower(Math.max(0, targetCard.getPower() - attackingCard.getAttackPower()));

        // Jeśli moc karty spadnie do 0, usuwamy ją z pola bitwy
        if (targetCard.getPower() == 0) {
            if (!opponentFrontBattlefield.remove(targetCard)) {
                opponentBackBattlefield.remove(targetCard);
            }
        }

        // Resetowanie karty atakującej
        this.attackingCard = null;
    }

    public boolean isFrontCard(Card card) {
        return getBattlefield_front_player1.contains(card) || getBattlefield_front_player2.contains(card);
    }

    public boolean isBackCard(Card card) {
        return getBattlefield_back_player1.contains(card) || getBattlefield_back_player2.contains(card);
    }


    public void highlightAttackableCards(Card attackingCard, ArrayList<Card> opponentFrontBattlefield, ArrayList<Card> opponentBackBattlefield) {
        if (attackingCard == null) return;

        if (isFrontCard(attackingCard)) {
            // Front może atakować tylko front przeciwnika
            for (Card card : opponentFrontBattlefield) {
                card.setHighlighted(true);
                card.setRedCross(true);  // Ustawienie, że karta może być zaatakowana
            }
        } else if (isBackCard(attackingCard)) {
            // Back może atakować zarówno front, jak i back przeciwnika
            for (Card card : opponentFrontBattlefield) {
                card.setHighlighted(true);
                card.setRedCross(true);  // Ustawienie, że karta może być zaatakowana
            }
            for (Card card : opponentBackBattlefield) {
                card.setHighlighted(true);
                card.setRedCross(true);  // Ustawienie, że karta może być zaatakowana
            }
        }
    }

    // Metoda do wybierania celu
    public Card chooseTarget(int mouseX, int mouseY, ArrayList<Card> opponentFrontBattlefield, ArrayList<Card> opponentBackBattlefield) {


        for (Card card : opponentFrontBattlefield) {
            DrawTexture(card.getCardTexture(), card.getX(), card.getY(), RED);
            if (isMouseOverCard(mouseX, mouseY, card)) {
                return card;
            }
        }
        for (Card card : opponentBackBattlefield) {
            DrawTexture(card.getCardTexture(), card.getX(), card.getY(), RED);
            if (isMouseOverCard(mouseX, mouseY, card)) {
                return card;
            }
        }
        return null;
    }


    public boolean hasAttackableCards(ArrayList<Card> frontBattlefield, ArrayList<Card> backBattlefield) {
        for (Card card : frontBattlefield) {
            if (card.getAttackPower() > 0) { // Czy karta może jeszcze zaatakować?
                return true;
            }
        }
        for (Card card : backBattlefield) {
            if (card.getAttackPower() > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean isMouseOverCard(int mouseX, int mouseY, Card card) {
        int cardWidth = 70;  // Ustaw poprawną szerokość karty
        int cardHeight = 100; // Wysokość karty
        return mouseX >= card.getX() && mouseX <= card.getX() + cardWidth &&
                mouseY >= card.getY() && mouseY <= card.getY() + cardHeight;
    }

    public boolean handlePlayerAction(Player player, ArrayList<Card> battlefieldFront, ArrayList<Card> battlefieldBack,
                                       int mouseX, int mouseY, int yStart, int yEnd, ArrayList<Card> opponentFrontBattlefield) {
        int xPos = 300; // Pozycja startowa X dla kart w ręce
        for (Card card : player.getHand()) {
            if (mouseX > xPos && mouseX < xPos + 60 && mouseY > yStart && mouseY < yEnd) {
                // Dodaj kartę na pole
                if (card.isFrontLine()) {
                    battlefieldFront.add(card);
                    int yPosition = (player == player1) ? FRONT_LINE_Y_PLAYER1 : FRONT_LINE_Y_PLAYER2;
                    card.setPosition(xPos, yPosition); // Ustaw pozycję karty na planszy
                } else {
                    battlefieldBack.add(card);
                    int yPosition = (player == player1) ? BACK_LINE_Y_PLAYER1 : BACK_LINE_Y_PLAYER2;
                    card.setPosition(xPos, yPosition); // Ustaw pozycję karty na planszy
                }

                player.playCard(card);

                if (card.getAttackPower() > 0) {
                    attackingCard = card; // Karta gotowa do ataku
                    highlightAttackableCards(card, opponentFrontBattlefield, battlefieldBack);
                    player.addScore(card.getPower());
                } else {
                    player.addScore(card.getPower());
                }
                return true;
            }
            xPos += 80; // Przesuń pozycję do następnej karty
        }
        return false;
    }
}

