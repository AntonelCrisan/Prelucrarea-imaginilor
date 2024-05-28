/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prelucrareaimaginilorlaborator;

import java.awt.Color;

/**
 *
 * @author anton
 */
public class Eticheta {
    public Color getRandomColor() {
        return new Color((int) (Math.random() * 0x1000000));
    }

    public boolean isBlack(int rgb) {
        Color c = new Color(rgb);
        return c.getRed() == 0 && c.getGreen() == 0 && c.getBlue() == 0;
    }

    // Funcție auxiliară pentru verificarea dacă un index este valid
    public boolean isValidIndex(int row, int col, int height, int width) {
        return row >= 0 && row < height && col >= 0 && col < width;
    }
    
}
