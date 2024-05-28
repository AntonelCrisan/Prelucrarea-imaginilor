/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prelucrareaimaginilorlaborator;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 *
 * @author anton
 */
public class EdgeDetected {
    //Functia pentru calcularea detecatrii cunturului
    public BufferedImage edgeDetect(BufferedImage src, int filter_type) { 
        String HORIZONTAL_FILTER = "Horizontal Filter"; 
        String VERTICAL_FILTER = "Vertical Filter"; 
        String SOBEL_FILTER_VERTICAL = "Sobel Vertical Filter"; 
        String SOBEL_FILTER_HORIZONTAL = "Sobel Horizontal Filter"; 
        String SCHARR_FILTER_VETICAL = "Scharr Vertical Filter"; 
        String SCHARR_FILTER_HORIZONTAL = "Scharr Horizontal Filter"; 
        double[][] FILTER_VERTICAL = {{1, 0, -1}, {1, 0, -1}, {1, 0, -1}}; 
        double[][] FILTER_HORIZONTAL = {{1, 1, 1}, {0, 0, 0}, {-1, -1, -1}}; 
        double[][] FILTER_SOBEL_V = {{1, 0, -1}, {2, 0, -2}, {1, 0, -1}}; 
        double[][] FILTER_SOBEL_H = {{1, 2, 1}, {0, 0, 0}, {-1, -2, -1}}; 
        double[][] FILTER_SCHARR_V = {{3, 0, -3}, {10, 0, -10}, {3, 0, -3}}; 
        double[][] FILTER_SCHARR_H = {{3, 10, 3}, {0, 0, 0}, {-3, -10, -3}}; 
        HashMap<String, double[][]> filterMap; 
        int width = src.getWidth(); 
        int height = src.getHeight(); 
        double[][][] image = new double[3][height][width]; 
        for (int i = 0; i < height; i++) { 
            for (int j = 0; j < width; j++) { 
                Color color = new Color(src.getRGB(j, i)); 
                image[0][i][j] = color.getRed(); 
                image[1][i][j] = color.getGreen(); 
                image[2][i][j] = color.getBlue(); 
            } 
        } 
        double[][] filter = null; 
        switch (filter_type) { 
            case 1: 
                filter = FILTER_VERTICAL; 
            case 2: 
                filter = FILTER_HORIZONTAL; 
            case 3: 
                filter = FILTER_SOBEL_V; 
            case 4: 
                filter = FILTER_SOBEL_H; 
            case 5: 
                filter = FILTER_SCHARR_V; 
            case 6: 
                filter = FILTER_SCHARR_H; 
        } 
        double[][] redConv = convolutionType2(image[0], height, width, filter, 3, 3, 1); 
        double[][] greenConv = convolutionType2(image[1], height, width, filter, 3, 3, 1); 
        double[][] blueConv = convolutionType2(image[2], height, width, filter, 3, 3, 1); 
        double[][] convolvedPixels = new double[redConv.length][redConv[0].length]; 
        for (int i = 0; i < redConv.length; i++) { 
            for (int j = 0; j < redConv[i].length; j++) { 
                convolvedPixels[i][j] = redConv[i][j] + greenConv[i][j] + blueConv[i][j]; 
            } 
        } 
        
        BufferedImage writeBackImage = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB); 
        for (int i = 0; i < convolvedPixels.length; i++) { 
            for (int j = 0; j < convolvedPixels[i].length; j++) { 
                Color color = new Color(fixOutOfRangeRGBValues((int) convolvedPixels[i][j]), fixOutOfRangeRGBValues((int)convolvedPixels[i][j]), fixOutOfRangeRGBValues((int)convolvedPixels[i][j])); 
                writeBackImage.setRGB(j, i, color.getRGB()); 
            } 
        } 
        return writeBackImage; 
    }
    private int fixOutOfRangeRGBValues(double value) { 
        if (value < 0.0) { 
            value = -value; 
        } 
        if (value > 255) { 
            return 255; 
        } else { 
            return (int) value; 
        } 
    }
    public double[][] convolutionType2(double[][] input, int width, int height, double[][] kernel, int kernelWidth, int kernelHeight, int iterations) { 
        double[][] newInput = (double[][]) input.clone(); 
        double[][] output = (double[][]) input.clone(); 
        for (int i = 0; i < iterations; ++i) { 
            output = convolution2DPadded(newInput, width, height, kernel, kernelWidth, kernelHeight); 
            newInput = (double[][]) output.clone(); 
        } 
        return output; 
    }
    public double[][] convolution2DPadded(double[][] input, int width, int height, double[][] kernel, int kernelWidth, int kernelHeight) { 
        int smallWidth = width - kernelWidth + 1; 
        int smallHeight = height - kernelHeight + 1; 
        int top = kernelHeight / 2; 
        int left = kernelWidth / 2; 
        double small[][] = convolution2D(input, width, height, kernel, kernelWidth, kernelHeight); 
        double large[][] = new double[width][height]; 
        for (int j = 0; j < height; ++j) { 
            for (int i = 0; i < width; ++i) { 
                large[i][j] = 0; 
            } 
        } 
        for (int j = 0; j < smallHeight; ++j) { 
            for (int i = 0; i < smallWidth; ++i) { 
                large[i + left][j + top] = small[i][j]; 
            } 
        } 
        return large; 
    }
     public double[][] convolution2D(double[][] input, int width, int height, double[][] kernel, int kernelWidth, int kernelHeight) { 
        int smallWidth = width - kernelWidth + 1; 
        int smallHeight = height - kernelHeight + 1; 
        double[][] output = new double[smallWidth][smallHeight]; 
        for (int i = 0; i < smallWidth; ++i) { 
            for (int j = 0; j < smallHeight; ++j) { 
                output[i][j] = 0; 
            } 
        } 
        for (int i = 0; i < smallWidth; ++i) { 
            for (int j = 0; j < smallHeight; ++j) { 
                output[i][j] = singlePixelConvolution(input, i, j, kernel, kernelWidth, kernelHeight); 
            } 
        } 
        return output; 
    }
     public double singlePixelConvolution(double[][] input, int x, int y, double[][] k, int kernelWidth, int kernelHeight) { 
        double output = 0; 
        for (int i = 0; i < kernelWidth; ++i) { 
            for (int j = 0; j < kernelHeight; ++j) { 
                output = output + (input[x + i][y + j] * k[i][j]); 
            } 
        } 
        return output; 
    }
    
}
