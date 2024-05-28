/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prelucrareaimaginilorlaborator;

/**
 *
 * @author anton
 */

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanCompression {
    private Map<Integer, String> huffmanCodes;
    private Map<String, Integer> reverseHuffmanCodes;

    public String compress(BufferedImage image) throws IOException {
        Map<Integer, Integer> frequencyMap = calculateFrequencies(image);
        HuffmanNode root = buildHuffmanTree(frequencyMap);
        huffmanCodes = new HashMap<>();
        generateHuffmanCodes(root, "");

        // Encode the image using Huffman codes
        StringBuilder encodedImage = new StringBuilder();
        int width = image.getWidth();
        int height = image.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int color = image.getRGB(x, y);
                encodedImage.append(huffmanCodes.get(color));
            }
        }

        // Save encoded image and dimensions to file
        try (FileOutputStream fos = new FileOutputStream("compressed_image.huff");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeInt(width);
            oos.writeInt(height);
            oos.writeObject(encodedImage.toString());
        }

        return encodedImage.toString();
    }

    public BufferedImage decompress(String filePath) throws IOException, ClassNotFoundException {
        // Read the compressed file
        String encodedImage;
        int width, height;
        try (FileInputStream fis = new FileInputStream(filePath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            width = ois.readInt();
            height = ois.readInt();
            encodedImage = (String) ois.readObject();
        }

        // Rebuild Huffman tree from the encoded data
        reverseHuffmanCodes = new HashMap<>();
        for (Map.Entry<Integer, String> entry : huffmanCodes.entrySet()) {
            reverseHuffmanCodes.put(entry.getValue(), entry.getKey());
        }

        // Decode the image
        StringBuilder currentCode = new StringBuilder();
        int[] rgbArray = new int[width * height];
        int index = 0;

        for (char bit : encodedImage.toCharArray()) {
            currentCode.append(bit);
            if (reverseHuffmanCodes.containsKey(currentCode.toString())) {
                rgbArray[index++] = reverseHuffmanCodes.get(currentCode.toString());
                currentCode.setLength(0);
            }
        }

        // Create BufferedImage from the rgbArray
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, width, height, rgbArray, 0, width);

        return image;
    }

    private Map<Integer, Integer> calculateFrequencies(BufferedImage image) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        int width = image.getWidth();
        int height = image.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int color = image.getRGB(x, y);
                frequencyMap.put(color, frequencyMap.getOrDefault(color, 0) + 1);
            }
        }
        return frequencyMap;
    }

    private HuffmanNode buildHuffmanTree(Map<Integer, Integer> frequencyMap) {
        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>(
                (a, b) -> Integer.compare(a.frequency, b.frequency)
        );
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            priorityQueue.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        while (priorityQueue.size() > 1) {
            HuffmanNode left = priorityQueue.poll();
            HuffmanNode right = priorityQueue.poll();
            HuffmanNode merged = new HuffmanNode(-1, left.frequency + right.frequency);
            merged.left = left;
            merged.right = right;
            priorityQueue.add(merged);
        }

        return priorityQueue.poll();
    }

    private void generateHuffmanCodes(HuffmanNode root, String code) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            huffmanCodes.put(root.color, code);
        }
        generateHuffmanCodes(root.left, code + "0");
        generateHuffmanCodes(root.right, code + "1");
    }

    public class HuffmanNode {
        int color;
        int frequency;
        HuffmanNode left, right;

        public HuffmanNode(int color, int frequency) {
            this.color = color;
            this.frequency = frequency;
            this.left = null;
            this.right = null;
        }
    }
}
