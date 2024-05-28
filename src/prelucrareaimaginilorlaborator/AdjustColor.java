/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prelucrareaimaginilorlaborator;

/**
 *
 * @author anton
 */
public class AdjustColor {
    public int adjustColor(int rgb, int dif) {
        int result;
        if (dif>0) {
            if (rgb+dif>255) {
                result = 255; //rgb + dif - 255; 
            } else {
                result = rgb + dif;
            }
        } else {
            if (rgb+dif>0) {
                result = rgb + dif;
            } else {
                result = 0; //255 + rgb + dif;
            }
        }
        return result;
    }
    
}
