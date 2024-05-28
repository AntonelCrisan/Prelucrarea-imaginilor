/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prelucrareaimaginilorlaborator;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

/**
 *
 * @author anton
 */
public class GrayScaleImage {
     public BufferedImage getGrayscaleImage(BufferedImage src) { 
        BufferedImage gImg = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_GRAY); 
        WritableRaster wr = src.getRaster(); 
        WritableRaster gr = gImg.getRaster(); 
        for (int i = 0; i < wr.getWidth(); i++) { 
            for (int j = 0; j < wr.getHeight(); j++) { 
                gr.setSample(i, j, 0, wr.getSample(i, j, 0)); 
            } 
        } 
        gImg.setData(gr); 
        return gImg; 
}
    
}
