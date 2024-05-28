/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prelucrareaimaginilorlaborator;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author anton
 */
public class Filtru {
    
    public BufferedImage mediere(BufferedImage src){ 
        BufferedImage dst = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB); 
        int i,j; 
        int k,l; 
        int w,h; 
        double[][] v = new double[5][5]; 
        //coeficientii mastii de filtrare  
        v[0][0]=1.0/9.0; v[0][1]=1.0/9.0; v[0][2]=1.0/9.0;v[0][3]=1.0/9.0;v[0][4]=1.0/9.0;

        v[1][0]=1.0/9.0; v[1][1]=1.0/9.0; v[1][2]=1.0/9.0; v[1][3]=1.0/9.0; v[1][4]=1.0/9.0;

        v[2][0]=1.0/9.0; v[2][1]=1.0/9.0; v[2][2]=1.0/9.0;v[2][3]=1.0/9.0; v[2][4]=1.0/9.0;
        
        v[3][0]=1.0/9.0; v[3][1]=1.0/9.0; v[3][2]=1.0/9.0; v[3][3]=1.0/9.0; v[3][4]=1.0/9.0; 
        
        v[4][0]=1.0/9.0; v[4][1]=1.0/9.0; v[4][2]=1.0/9.0; v[4][3]=1.0/9.0; v[4][4]=1.0/9.0; 
        
        w=src.getWidth(); 
        h=src.getHeight(); 
        for(i=1;i<w-1;i++){ 
            for(j=1;j<h-1;j++){ 
                //suma ponderata  
                double sumr=0,sumg=0,sumb=0; 
                for(k=-1;k<2;k++){ 
                    for(l=-1;l<2;l++){ 
                        int pixel = src.getRGB(i + k, j + l); 
                        Color c = new Color(pixel, true); 
                        sumr += v[k + 1][l + 1] * c.getRed(); 
                        sumg += v[k + 1][l + 1] * c.getGreen(); 
                        sumb += v[k + 1][l + 1] * c.getBlue(); 
                        Color nc = new Color((int) sumr, (int) sumg, (int) sumb); 
                        dst.setRGB(i, j, nc.getRGB()); 
                    } 
                } 
            } 
        } 
        return dst;         
    }
    
    public BufferedImage accentuare(BufferedImage src) { 
        BufferedImage dst = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB); 
        int i,j; 
        int k,l; 
        int w,h; 
        double sumr,sumg,sumb; 
        double[][] v=new double[3][3]; 
        //coeficientii mastii  
        v[0][0]=0; v[0][1]=-1./4; v[0][2]=0; 
        v[1][0]=-1./4; v[1][1]=1; v[1][2]=-1./4; 
        v[2][0]=0; v[2][1]=-1./4; v[2][2]=0; 
        w=src.getWidth(); 
        h=src.getHeight(); 
        for(i=1;i<w-1;i++){ 
            for(j=1;j<h-1;j++){ 
                sumr=0; 
                sumg=0; 
                sumb=0; 
                for(k=-1;k<2;k++) { 
                    for(l=-1;l<2;l++) { 
                        int pixel = src.getRGB(i + k, j + l); 
                        Color c = new Color(pixel, true); 
                        sumr+=1.*v[k+1][l+1]*c.getRed(); 
                        sumg+=1.*v[k+1][l+1]*c.getGreen(); 
                        sumb+=1.*v[k+1][l+1]*c.getBlue(); 
                        int nivr=c.getRed(); 
                        //nivr=(int)(nivr+0.6*sumr); 
                        int nivg=c.getGreen(); 
                        //nivg=(int)(nivg+0.6*sumg); 
                        int nivb=c.getBlue(); 
                        //nivb=(int)(nivb+0.6*sumb); 
                        
                        Color nc = new Color((int) new AdjustColor().adjustColor(nivr, (int) (0.6*sumr)),  
                                (int) new AdjustColor().adjustColor(nivg, (int) (0.6*sumg)),  
                                (int) new AdjustColor().adjustColor(nivb, (int) (0.6*sumb))); 
                        dst.setRGB(i, j, nc.getRGB());             
                    } 
                } 
            } 
        } 
        return dst; 
    }
    public BufferedImage minim(BufferedImage src) { 
        BufferedImage dst = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB); 
        int i,j; 
        int w,h; 
        int k,aux; 
        int m,n; 
        int med; 
        int[] sir=new int[9]; 
        w=src.getWidth(); 
        h=src.getHeight(); 
        for(i=1;i<w-1;i++) { 
            for(j=1;j<h-1;j++){ 
                //formarea unui sir din elementele vecinatatii 3x3 
                k=0; 
                for(m=-1;m<2;m++) { 
                    for(n=-1;n<2;n++){ 
                        int pixel = src.getRGB(i+m,j+n); 
                        Color c = new Color(pixel, true); 
                        sir[k]=c.getRed(); 
                        k++; 
                    } 
                } 
                //ordonarea crescatoare a valorilor pixelilor 
                //metoda BUBBLESORT 
                k=0; 
                while(k==0){ 
                    k=1; 
                    for(m=0;m<8;m++){ 
                        if(sir[m]>sir[m+1]){ 
                            aux=sir[m]; 
                            sir[m]=sir[m+1]; 
                            sir[m+1]=aux; 
                            k=0; 
                        } 
                    } 
                } 
                //elementul median  
                med=sir[0]; 
                Color nc = new Color(med,med,med); 
                dst.setRGB(i, j, nc.getRGB());             
            } 
        } 
        return dst; 
    } 
    
    public BufferedImage median(BufferedImage src) { 
        BufferedImage dst = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB); 
        int i,j;
        int w,h; 
        int k,aux; 
        int m,n; 
        int med; 
        int[] sir=new int[9]; 
        w=src.getWidth(); 
        h=src.getHeight(); 
        for(i=1;i<w-1;i++) { 
            for(j=1;j<h-1;j++){ 
                //formarea unui sir din elementele vecinatatii 3x3 
                k=0; 
                for(m=-1;m<2;m++) { 
                    for(n=-1;n<2;n++){ 
                        int pixel = src.getRGB(i+m,j+n); 
                        Color c = new Color(pixel, true); 
                        sir[k]=c.getRed(); 
                        k++; 
                    } 
                } 
                //ordonarea crescatoare a valorilor pixelilor 
                //metoda BUBBLESORT 
                k=0; 
                while(k==0){ 
                    k=1; 
                    for(m=0;m<8;m++){ 
                        if(sir[m]>sir[m+1]){ 
                            aux=sir[m]; 
                            sir[m]=sir[m+1]; 
                            sir[m+1]=aux; 
                            k=0; 
                        } 
                    } 
                } 
                //elementul median  
                med=sir[4]; 
                Color nc = new Color(med,med,med); 
                dst.setRGB(i, j, nc.getRGB());             
            } 
        } 
        return dst; 
    }
    
    public BufferedImage maxim(BufferedImage src) { 
        BufferedImage dst = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB); 
        int i,j; 
        int w,h; 
        int k,aux; 
        int m,n; 
        int med; 
        int[] sir=new int[9]; 
        w=src.getWidth(); 
        h=src.getHeight(); 
        for(i=1;i<w-1;i++) { 
            for(j=1;j<h-1;j++){ 
                //formarea unui sir din elementele vecinatatii 3x3 
                k=0; 
                for(m=-1;m<2;m++) { 
                    for(n=-1;n<2;n++){ 
                        int pixel = src.getRGB(i+m,j+n); 
                        Color c = new Color(pixel, true); 
                        sir[k]=c.getRed(); 
                        k++; 
                    } 
                } 
                k=0; 
                while(k==0){ 
                    k=1; 
                    for(m=0;m<8;m++){ 
                        if(sir[m]>sir[m+1]){ 
                            aux=sir[m]; 
                            sir[m]=sir[m+1]; 
                            sir[m+1]=aux; 
                            k=0; 
                        } 
                    } 
                } 
                med=sir[8]; 
                Color nc = new Color(med,med,med); 
                dst.setRGB(i, j, nc.getRGB());             
            } 
        } 
        return dst; 
    }
    
    public BufferedImage dilate(BufferedImage src, boolean dilateBackgroundPixel, float iteratii) {
        BufferedImage dst = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        int width = src.getWidth();
        int height = src.getHeight();
        // Aplicăm dilatarea de iteratii ori
        for (int i = 0; i < iteratii; i++) {
            int output[] = new int[width * height];
            int targetValue = (dilateBackgroundPixel == true) ? 0 : 255;
            int reverseValue = (targetValue == 255) ? 0 : 255;
            // Algoritmul de dilatare
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if ((new Color(src.getRGB(x, y))).getRed() == targetValue) {
                        boolean flag = false;

                        for (int ty = y - 1; ty <= y + 1 && !flag; ty++) {
                            for (int tx = x - 1; tx <= x + 1 && !flag; tx++) {
                                if (ty >= 0 && ty < height && tx >= 0 && tx < width) {
                                    if ((new Color(src.getRGB(tx, ty))).getRed() != targetValue) {
                                        flag = true;
                                        output[x + y * width] = reverseValue;
                                    }
                                }
                            }
                        }
                        if (!flag) {
                            output[x + y * width] = targetValue;
                        }
                    } else {
                        output[x + y * width] = reverseValue;
                    }
                }
            }
            // Salvăm rezultatul pentru următoarea iterație sau pentru imaginea finală
            for (int j = 0; j < width * height; j++) {
                dst.getRaster().setSample(j % width, j / width, 0, output[j]);
            }
            // Setăm imaginea dilatată ca sursă pentru următoarea iterație
            src = dst;
        }
        return dst;
    }
    public BufferedImage filterLaplacian(BufferedImage src) { 
        BufferedImage grayScale = new GrayScaleImage().getGrayscaleImage(src); 
        BufferedImage dst = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB); 
        int width = src.getWidth(); 
        int height = src.getHeight(); 
        double[][] v = new double[3][3]; 
        //coeficientii mastii  
        v[0][0] = -1; 
        v[0][1] = -1; 
        v[0][2] = -1; 
        v[1][0] = -1; 
        v[1][1] = 8; 
        v[1][2] = -1; 
        v[2][0] = -1; 
        v[2][1] = -1; 
        v[2][2] = -1; 
        //3*3 Laplacian filter (-1,-1,-1), (-1,8,-1), (-1,-1,-1) 
        for (int y = 1; y < height - 1; y++) { 
            for (int x = 1; x < width - 1; x++) { 
                int sum = 0; 
                for (int m = -1; m < 2; m++) { 
                    for (int n = -1; n < 2; n++) { 
                        int pixel = src.getRGB(x + m, y + n); 
                        sum += v[m+1][n+1] * (pixel & 0xff); 
                    } 
                } 
                int a = ((grayScale.getRGB(x, y) >> 24) & 0xff); 
                dst.setRGB(x, y, ((a << 24) | (sum << 16) | (sum << 8) | (sum))); 
            } 
        } 
        return dst; 
    }
    
}
