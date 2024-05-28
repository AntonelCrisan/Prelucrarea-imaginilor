/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prelucrareaimaginilorlaborator;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 *
 * @author anton
 */
public class Histogram {
    public int[] calculateHistogram(BufferedImage image) throws IOException { 
        // Dimensiunile imaginii 
        int width = image.getWidth(); 
        int height = image.getHeight(); 
       // Inițializarea histogramă 
        int[] histogram = new int[256]; // Histograma pentru tonuri de gri 
        // Parcurgerea imaginii și calcularea histogramei 
        for (int y = 0; y < height; y++) { 
            for (int x = 0; x < width; x++) { 
                int pixel = image.getRGB(x, y); 
                // Extrage componentele R, G, B și calculează media pentru a obține tonul de gri 
                int red = (pixel >> 16) & 0xff; 
                int green = (pixel >> 8) & 0xff; 
                int blue = pixel & 0xff; 
                int gray = (red + green + blue) / 3; 
                // Incrementarea corespunzătoare a valorii în histogramă 
                histogram[gray]++; 
            } 
        } 
        return histogram; 
}
    
}
