package org.example;

import com.raylib.Raylib;

import java.util.HashMap;
import java.util.Map;

public class TextureManager {
    private static final Map<Integer, String> texturePaths = new HashMap<>();
    private static final Map<Integer, Raylib.Texture> loadedTextures = new HashMap<>();

    static {
        // Mapowanie ID na ścieżki
        texturePaths.put(1, "Textures/Talie/Talia_W/Bog/Thor1.png");
        texturePaths.put(2, "Textures/Talie/Talia_W/Bog/Freja1.png");
        texturePaths.put(3, "Textures/Talie/Talia_W/Bog/Odyn1.png");
        texturePaths.put(4, "Textures/Talie/Talia_W/Miecz/karta1-1.png");
        texturePaths.put(5, "Textures/Talie/Talia_W/Miecz/karta2-2.png");
        texturePaths.put(6, "Textures/Talie/Talia_W/Miecz/karta3-3.png");
        texturePaths.put(7, "Textures/Talie/Talia_W/Miecz/karta4-4.png");
        texturePaths.put(8, "Textures/Talie/Talia_W/Miecz/karta5-5.png");
        texturePaths.put(9, "Textures/Talie/Talia_W/Luk/karta1-1.png");
        texturePaths.put(10, "Textures/Talie/Talia_W/Luk/karta2-2.png");
        texturePaths.put(11, "Textures/Talie/Talia_W/Luk/karta3-3.png");
        texturePaths.put(12, "Textures/Talie/Talia_W/Luk/karta4-4.png");
        texturePaths.put(13, "Textures/Talie/Talia_W/Luk/karta5-5.png");

        texturePaths.put(20, "Textures/Talie/Talia_R/Bog/Ares1.png");
        texturePaths.put(21, "Textures/Talie/Talia_R/Bog/Janus1.png");
        texturePaths.put(22, "Textures/Talie/Talia_R/Bog/Jowisz1.png");
        texturePaths.put(23, "Textures/Talie/Talia_R/Miecz/Karta1-1.png");
        texturePaths.put(24, "Textures/Talie/Talia_R/Miecz/Karta2-2.png");
        texturePaths.put(25, "Textures/Talie/Talia_R/Miecz/Karta3-3.png");
        texturePaths.put(26, "Textures/Talie/Talia_R/Miecz/Karta4-4.png");
        texturePaths.put(27, "Textures/Talie/Talia_R/Miecz/Karta5-5.png");
        texturePaths.put(28, "Textures/Talie/Talia_R/Luk/Karta1-1.png");
        texturePaths.put(29, "Textures/Talie/Talia_R/Luk/Karta2-2.png");
        texturePaths.put(30, "Textures/Talie/Talia_R/Luk/Karta3-3.png");
        texturePaths.put(31, "Textures/Talie/Talia_R/Luk/Karta4-4.png");
        texturePaths.put(32, "Textures/Talie/Talia_R/Luk/Karta5-5.png");

        texturePaths.put(100, "Textures/Deck.jpg");
        texturePaths.put(103, "Textures/desk_invert.jpg");
        texturePaths.put(101, "Textures/player1_turn.png");
        texturePaths.put(102, "Textures/player2_turn.png");

        texturePaths.put(104, "Textures/Win_B.jpg");
        texturePaths.put(105, "Textures/Win_D.jpg");
        texturePaths.put(106, "Textures/Win_R.jpg");
    }

    public static Raylib.Texture getTexture(int textureID) {
        if (!loadedTextures.containsKey(textureID)) {
            String path = texturePaths.get(textureID);
            if (path == null) {
                throw new IllegalArgumentException("Nie znaleziono ścieżki dla tekstury ID: " + textureID);
            }
            loadedTextures.put(textureID, Raylib.LoadTexture(path));
        }
        return loadedTextures.get(textureID);
    }

    public static void unloadAllTextures() {
        for (Raylib.Texture texture : loadedTextures.values()) {
            Raylib.UnloadTexture(texture);
        }
        loadedTextures.clear();
    }
}
