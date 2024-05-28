/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prelucrareaimaginilorlaborator;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;
import javax.imageio.ImageIO;
/**
 *
 * @author anton
 */
public class LZW {
    public String LZW_compress(BufferedImage src, String fileName) throws IOException { 
        HashMap<String, Integer> dictionary = new HashMap<>(); 
        int dictSize = 256; 
        String P,BP; 
        String extension="bmp"; 
        byte[] buffer = new byte[3]; 
        boolean isLeft = true; 
        int i,byteToInt; 
        char C; 
        for(i=0;i<256;i++) { 
            dictionary.put(Character.toString((char)i),i); 
        } 
        String[] getFileNameWOExtn = fileName.split("\\."); 
        RandomAccessFile outputFile = new RandomAccessFile(getFileNameWOExtn[0].concat(".lzw"),"rw"); 
        ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
        ImageIO.write(src, extension, baos); 
        byte[] bytes = baos.toByteArray(); 
        InputStream in = new ByteArrayInputStream(bytes);
        byteToInt = in.read();
        if(byteToInt < 0) byteToInt += 256;
        C = (char) byteToInt;
        P = ""+C;
        while(true) {
            byteToInt = in.read();
            if (byteToInt==-1) {
                BP = convertTo12Bit(dictionary.get(P));
                if(isLeft) {
                    buffer[0] = (byte) Integer.parseInt(BP.substring(0,8),2);
                    buffer[1] = (byte) Integer.parseInt(BP.substring(8,12)+"0000",2);
                    outputFile.writeByte(buffer[0]);
                    outputFile.writeByte(buffer[1]);
                }
                else {
                    buffer[1] += (byte) Integer.parseInt(BP.substring(0,4),2);
                    buffer[2] = (byte) Integer.parseInt(BP.substring(4,12),2);
                    for(i=0;i<buffer.length;i++) {
                        outputFile.writeByte(buffer[i]);
                        buffer[i]=0;
                    }
                }
                break;
            }
            if(byteToInt < 0) byteToInt += 256;
            
            C = (char) byteToInt;
            if(dictionary.containsKey(P+C)) {
                P = P+C;
            }
            else {
                BP = convertTo12Bit(dictionary.get(P));
                if(isLeft) {
                    buffer[0] = (byte) Integer.parseInt(BP.substring(0,8),2);
                    buffer[1] = (byte) Integer.parseInt(BP.substring(8,12)+"0000",2);
                }
                else {
                    buffer[1] += (byte) Integer.parseInt(BP.substring(0,4),2);
                    buffer[2] = (byte) Integer.parseInt(BP.substring(4,12),2);
                    for(i=0;i<buffer.length;i++) {
                        outputFile.writeByte(buffer[i]);
                        buffer[i]=0;
                    }
                }
                isLeft = !isLeft;
                if(dictSize < 4096) dictionary.put(P+C,dictSize++);
                P=""+C;
            }
        }
        outputFile.close();
        return getFileNameWOExtn[0].concat(".lzw"); 
    } 
      public String convertTo12Bit(int i) { 
        String to12Bit = Integer.toBinaryString(i); 
        while (to12Bit.length() < 12) to12Bit = "0" + to12Bit; 
        return to12Bit; 
    } 
     public void LZW_decompress(String fileName) throws IOException { 
        String[] arrayOfChar = new String[4096]; 
        String extension = "bmp"; 
        int dictSize = 256, currentword, previousword; 
        byte[] buffer = new byte[3]; 
        boolean isLeft = true; 

        for (int i = 0; i < 256; i++) {
            arrayOfChar[i] = Character.toString((char)i); 
        }

        RandomAccessFile inputFile = new RandomAccessFile(fileName, "r"); 
        RandomAccessFile outputFile = new RandomAccessFile("output_image.".concat(extension), "rw"); 

        try { 
            buffer[0] = inputFile.readByte(); 
            buffer[1] = inputFile.readByte(); 
            previousword = getIntValue(buffer[0], buffer[1], isLeft); 
            isLeft = !isLeft; 
            outputFile.writeBytes(arrayOfChar[previousword]); 

            while (true) { 
                if (isLeft) { 
                    buffer[0] = inputFile.readByte(); 
                    buffer[1] = inputFile.readByte(); 
                    currentword = getIntValue(buffer[0], buffer[1], isLeft); 
                } else { 
                    buffer[2] = inputFile.readByte(); 
                    currentword = getIntValue(buffer[1], buffer[2], isLeft); 
                } 
                isLeft = !isLeft; 

                if (currentword >= dictSize) { 
                    if (dictSize < 4096) { 
                        arrayOfChar[dictSize] = arrayOfChar[previousword] + arrayOfChar[previousword].charAt(0); 
                    } 
                    dictSize++; 
                    outputFile.writeBytes(arrayOfChar[previousword] + arrayOfChar[previousword].charAt(0)); 
                } else { 
                    if (dictSize < 4096) { 
                        arrayOfChar[dictSize] = arrayOfChar[previousword] + arrayOfChar[currentword].charAt(0); 
                    } 
                    dictSize++; 
                    outputFile.writeBytes(arrayOfChar[currentword]); 
                } 
                previousword = currentword; 
            } 
        } catch (EOFException e) { 
            inputFile.close(); 
            outputFile.close(); 
        } 
    } 

    public int getIntValue(byte b1, byte b2, boolean isLeft) { 
        int int1 = b1 & 0xFF; 
        int int2 = b2 & 0xFF; 

        if (isLeft) {
            return (int1 << 4) | (int2 >> 4); 
        } else {
            return ((int1 & 0x0F) << 8) | int2; 
        }
    }
}
