/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prelucrareaimaginilorlaborator;

import java.awt.Color;
import java.awt.Paint;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.lang.Math;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import javafx.embed.swing.SwingNode;
import javafx.event.EventHandler;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseEvent;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;


/**
 *
 * @bogdan
 *
 *
API-urile JavaFX au fost portate direct în Java, bazându-se mai mult pe standarde Internet, cum ar fi CSS pentru stilizarea controalelor sau ARIA pentru specificări referitoare la accesibilitate.
 
Graful de Scene
Implementarea unei aplicații JavaFX implică proiectarea și dezvoltarea unui graf de scene (eng. Scene Graph), 
structură ierarhică de noduri ce conţine elementele vizuale ale interfeţei grafice cu utilizatorul, 
care poate trata diferite evenimente legate de acestea şi care poate fi redată.

Un element din graful de scene (= un nod) este identificat în mod unic, fiind caracterizat printr-o clasă de stil şi 
un volum care îl delimitează. 
Fiecare nod are un părinte (cu excepția nodului rădăcină), putând avea nici unul, unul sau mai mulţi copii.
De asemenea, pentru un astfel de element pot fi definite efecte (estompări sau umbre), opacitate, transformări, 
mecanisme de tratare a diferitelor evenimente (care vizează interacţiunea cu utilizatorul) precum şi starea aplicaţiei.

Spre diferenţă de Swing sau AWT (Abstract Window Toolkit), JavaFX conţine pe lângă mecanisme de dispunere a conţinutului, controale, imagini sau obiecte multimedia şi 
primitive pentru elemente grafice (ca fi texte sau figuri geometice cu care se pot crea animaţii, folosind metodele puse la dispoziţie de API-urile javafx.animation).

API-ul javafx.scene permite construirea următoarelor conţinuturi:

noduri: forme 2D şi 3D, imagini, conţinut multimedia şi conţinut Internet, text, controale pentru interacţiunea cu utilizatorul, grafice, containere;
stări: transformări (poziţionări şi orientări ale nodurilor), efecte vizuale;
efecte: obiecte care modifică aspectul nodurilor (mecanisme de estompare, umbre, reglarea culorilor).
Mecanisme de Dispunere a Conţinutului
Controalele din graful de scene pot fi grupate în containere sau panouri în mod flexibil, folosind mai multe mecanisme de dispunere a conținutului (eng. layout).

API-ul JavaFX defineşte mai multe clase de tip container pentru dispunerea elementelor, în pachetul javafx.scene.layout:

BorderPane dispune nodurile conţinute în regiunile de sus, jos, dreapta, stânga sau centru;
HBox îşi aranjează conţinutul orizontal pe un singur rând;
VBox îşi aranjează conţinutul vertical pe o singură coloană;
StackPane utilizează o stivă de noduri afişând elementele unele peste altele, din spate către față;
GridPane permite utilizatorului să îşi definească un tabel (format din rânduri şi coloane) în care să poată fi încadrate elementele conţinute;
FlowPane dispune elementele fie orizontal, fie vertical, în funcţie de limitele specificate de programator (lungime pentru dispunere orizontală, respectiv înălţime pentru dispunere verticală);
TilePane plasează nodurile conţinute în celule de dimensiuni uniforme;
AnchorPane oferă programatorilor posibilitatea de a defini noduri ancoră (referinţă) în funcţie de colţurile de jos / sus, din stânga / dreapta sau raportat la centrul containerului sau panoului.
Diferitele moduri de dispunere pot fi imbricate în cadrul unei aplicaţii JavaFX pentru a se obţine funcţionalitatea dorită.
 
 */
public class PrelucrareaImaginilorLaborator extends Application {
    BufferedImage bufferedImag;
    Label name;
    int difR=0,difG=0,difB=0;
    private int BINS = 0;
    @Override
    public void start(Stage mainStage) {
        //Avem nevoie de o modalitate de a alege un fisier imagine
        //Vom folosi FileChooser
        //
        ImageView imageView = new ImageView();
        StackPane root = new StackPane();
        Scene scene = new Scene(root);
        MenuBar menuBar = new MenuBar();
        Label labelR = new Label();
        Label labelG = new Label();
        Label labelB = new Label();
        Label valoarePixel = new Label();
        Menu menuFile = new Menu("Fisiere");
        MenuItem menuItem_Open = new MenuItem("Alege Imagine");
        MenuItem menuItem_Save = new MenuItem("Salveaza Imagine");
        SeparatorMenuItem sep = new SeparatorMenuItem();
        MenuItem menuItem_Exit = new MenuItem("Iesire");
        menuFile.getItems().addAll(menuItem_Open,menuItem_Save,sep,menuItem_Exit);
        Menu menuChange = new Menu("Modificare");
        MenuItem menuItem_RGB = new MenuItem("RGB");
        MenuItem menuItem_Gray = new MenuItem("Gri");
        MenuItem menuItem_YUV = new MenuItem("YUV");
        MenuItem menuItem_YCbCr = new MenuItem("YCbCr");
        MenuItem menuItem_HSV = new MenuItem("HSV");
        MenuItem menuItemHistogram = new MenuItem("Histograma");
        MenuItem menuItemEticheta = new MenuItem("Eticheta");
        Menu menuFilter = new Menu("Filtru");
        MenuItem menuItemMediation = new MenuItem("Filtru Mediere");
        MenuItem menuItemEnhancer = new MenuItem("Filtru Accentuare");
        MenuItem menuItemMin = new MenuItem("Filtru Minim");
        MenuItem menuItemMedian= new MenuItem("Filtru Median");
        MenuItem menuItemMax = new MenuItem("Filtru Maxim");
        MenuItem menuItemLaplacian = new MenuItem("Filtru Laplacian");
        MenuItem menuItemDilate = new MenuItem("Filtru Dilatare");
        MenuItem menuItemEdgeDetected = new MenuItem("Filtru Detectare Contur");
        Menu menuCompress = new Menu("Compresie");
        MenuItem menuItemLZWCompress = new MenuItem("Compresie LZW");
        MenuItem menuItemHuffmanCompress = new MenuItem("Compresie Huffman");
        MenuItem menuItemLZWDeCompress = new MenuItem("Decompresie LZW");
        MenuItem menuItemHuffmanDeCompress = new MenuItem("Decompresie Huffman");
        menuItem_RGB.setDisable(true);
        menuItem_Gray.setDisable(true);
        menuItem_YUV.setDisable(true);
        menuItem_YCbCr.setDisable(true);
        menuItem_HSV.setDisable(true);
        menuItemHistogram.setDisable(true);
        menuItemEticheta.setDisable(true);
        menuItemMediation.setDisable(true);
        menuItemEnhancer.setDisable(true);
        menuItemMedian.setDisable(true);
        menuItemMin.setDisable(true);
        menuItemMax.setDisable(true);
        menuItemLaplacian.setDisable(true);
        menuItemDilate.setDisable(true);
        menuItemEdgeDetected.setDisable(true);
        menuItemLZWCompress.setDisable(true);
        menuItemHuffmanCompress.setDisable(true);
        menuChange.getItems().addAll(menuItem_RGB, menuItem_Gray, menuItem_YUV, menuItem_YCbCr,
                menuItem_HSV, menuItemHistogram, menuItemEticheta);
        menuBar.getMenus().addAll(menuFile, menuChange, menuFilter, menuCompress);
        menuFilter.getItems().addAll(menuItemMediation, menuItemEnhancer,menuItemMin,menuItemMedian, menuItemMax, menuItemLaplacian,
                menuItemDilate, menuItemEdgeDetected);
        menuCompress.getItems().addAll(menuItemLZWCompress, menuItemLZWDeCompress, menuItemHuffmanCompress, menuItemHuffmanDeCompress);
        VBox vbox = new VBox(menuBar, labelR, labelG, labelB, valoarePixel);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(0, 10, 0, 10));
        ScrollPane sp = new ScrollPane();
        vbox.getChildren().add(sp);
        //alegere imagine
        menuItem_Open.setOnAction((ActionEvent event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Afiseaza Imagine");
            File file = fileChooser.showOpenDialog(mainStage);
            if (file != null) {
                try {
                    VBox vbOpen = new VBox();
                    name = new Label(file.getAbsolutePath());
                    bufferedImag = ImageIO.read(file);
                    menuItem_Gray.setDisable(false);
                    menuItem_YUV.setDisable(false);
                    menuItem_YCbCr.setDisable(false);
                    menuItem_HSV.setDisable(false);
                    menuItemHistogram.setDisable(false);
                    menuItemEticheta.setDisable(false);
                    menuItemMediation.setDisable(false);
                    menuItemEnhancer.setDisable(false);
                    menuItemMedian.setDisable(false);
                    menuItemMin.setDisable(false);
                    menuItemMax.setDisable(false);
                    menuItemLaplacian.setDisable(false);
                    menuItemDilate.setDisable(false);
                    menuItemEdgeDetected.setDisable(false);
                    menuItemLZWCompress.setDisable(false);
                    menuItemHuffmanCompress.setDisable(false);
                    BufferedImage imageN = new BufferedImage(bufferedImag.getWidth(), bufferedImag.getHeight(), BufferedImage.TYPE_INT_ARGB);
                    for (int y = 0; y < bufferedImag.getHeight(); y++) {
                        for (int x = 0; x < bufferedImag.getWidth(); x++) {
                           //Retrieving contents of a pixel
                            int pixel = bufferedImag.getRGB(x, y);
                            Color color = new Color(pixel, true);
                           //Retrieving the R G B values
                           int alpha = color.getAlpha();
                           int red = color.getRed();
                           int green = color.getGreen();
                           int blue = color.getBlue();
                           Color myWhite = new Color(red, green, blue, alpha);
                           imageN.setRGB(x, y, myWhite.getRGB());
                        }
                    }
        
                   Image image = SwingFXUtils.toFXImage(imageN, null);
                    vbOpen.getChildren().addAll(name,imageView);
                    imageView.setFitHeight(400);
                    imageView.setPreserveRatio(true);
                    imageView.setImage(image);
                    imageView.setSmooth(true);
                    imageView.setCache(true);
                    sp.setContent(vbOpen);
                    menuBar.getMenus().get(1).getItems().get(0).setDisable(false);
                } catch (IOException ex) {
                    Logger.getLogger(PrelucrareaImaginilorLaborator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            });
       
        
        // Gasire pixel si culoarea imaginii initiale pe baza pozitiei mouse-ului pe imagine
        imageView.setOnMouseClicked(mouseEvent -> {
            int pixel = bufferedImag.getRGB((int)mouseEvent.getX(), (int)mouseEvent.getY());
            Color color = new Color(pixel, true);
             valoarePixel.setText("Valoarea pixelului la " + (int)mouseEvent.getX() + ", " + (int)mouseEvent.getY() + " este: " + pixel);
            labelR.setText("Rosu: " + Integer.toString(color.getRed()));
            labelG.setText ("Verde: " + Integer.toString(color.getGreen()));
            labelB.setText ("Albastru: " + Integer.toString(color.getBlue()));
});
        //Salvare imagine
        menuItem_Save.setOnAction((ActionEvent event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Salvare Imagine");
            File file = fileChooser.showSaveDialog(mainStage);
            if (file != null) {
                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(imageView.getImage(),null), "png", file);
                } catch (IOException ex) {
                    Logger.getLogger(PrelucrareaImaginilorLaborator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                });
        //Menu RGB
        menuItem_RGB.setOnAction((ActionEvent event) -> {
            ScrollPane spR = new ScrollPane();
            ScrollPane spG = new ScrollPane();
            ScrollPane spB = new ScrollPane();
            ScrollPane spRGB = new ScrollPane();

            BufferedImage imageR = new BufferedImage(bufferedImag.getWidth(), bufferedImag.getHeight(), BufferedImage.TYPE_INT_RGB);
            BufferedImage imageG = new BufferedImage(bufferedImag.getWidth(), bufferedImag.getHeight(), BufferedImage.TYPE_INT_RGB);
            BufferedImage imageB = new BufferedImage(bufferedImag.getWidth(), bufferedImag.getHeight(), BufferedImage.TYPE_INT_RGB);
            BufferedImage imageRGB = new BufferedImage(bufferedImag.getWidth(), bufferedImag.getHeight(), BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < bufferedImag.getHeight(); y++) {
                for (int x = 0; x < bufferedImag.getWidth(); x++) {
                   //Retrieving contents of a pixel
                   int pixel = bufferedImag.getRGB(x,y);
                   //Creating a Color object from pixel value
                   Color color = new Color(pixel, true);
                   //Retrieving the R G B values
                   int red = color.getRed();
                   int green = color.getGreen();
                   int blue = color.getBlue();
                   Color myR = new Color(red, 0, 0);
                   Color myG = new Color(0, green, 0);
                   Color myB = new Color(0, 0, blue);
                   imageR.setRGB(x, y, myR.getRGB());
                   imageG.setRGB(x, y, myG.getRGB());
                   imageB.setRGB(x, y, myB.getRGB());
                   imageRGB.setRGB(x, y, pixel);
                }
            }

            Image imgR = SwingFXUtils.toFXImage(imageR, null);
            ImageView imageViewR = new ImageView();
            imageViewR.setFitHeight(400);
            imageViewR.setPreserveRatio(true);
            imageViewR.setImage(imgR);
            imageViewR.setSmooth(true);
            imageViewR.setCache(true);
            spR.setContent(imageViewR);
            
            Image imgG = SwingFXUtils.toFXImage(imageG, null);
            ImageView imageViewG = new ImageView();
            imageViewG.setFitHeight(400);
            imageViewG.setPreserveRatio(true);
            imageViewG.setImage(imgG);
            imageViewG.setSmooth(true);
            imageViewG.setCache(true);
            spG.setContent(imageViewG);
            
            Image imgB = SwingFXUtils.toFXImage(imageB, null);
            ImageView imageViewB = new ImageView();
            imageViewB.setFitHeight(400);
            imageViewB.setPreserveRatio(true);
            imageViewB.setImage(imgB);
            imageViewB.setSmooth(true);
            imageViewB.setCache(true);
            spB.setContent(imageViewB);
            VBox vbRGB = new VBox();
          
            Image imgRGB = SwingFXUtils.toFXImage(imageRGB, null);
            ImageView imageViewRGB = new ImageView();
            imageViewRGB.setFitHeight(400);
            imageViewRGB.setPreserveRatio(true);
            imageViewRGB.setImage(imgRGB);
            imageViewRGB.setSmooth(true);
            imageViewRGB.setCache(true);
            
   
            Slider sliderRGB = new Slider();
            Slider sliderR=new Slider();
            Slider sliderG=new Slider();
            Slider sliderB=new Slider();
            sliderR.setMin(0);
            sliderG.setMin(0);
            sliderB.setMin(0);
            sliderRGB.setMin(0);
            sliderR.setMax(255);
            sliderG.setMax(255);
            sliderB.setMax(255);
            sliderRGB.setMax(255);
            
             sliderR.setValue(0);
            sliderG.setValue(0);
            sliderB.setValue(0);
            sliderRGB.setValue(0);
            
             sliderR.setShowTickLabels(true);
            sliderG.setShowTickLabels(true);
            sliderB.setShowTickLabels(true);
            sliderRGB.setShowTickLabels(true);
            
            sliderR.setShowTickMarks(true);
            sliderG.setShowTickMarks(true);
            sliderB.setShowTickMarks(true);
            sliderRGB.setShowTickMarks(true);
            
             sliderR.setMajorTickUnit(50);
            sliderG.setMajorTickUnit(50);
            sliderB.setMajorTickUnit(50);
            sliderRGB.setMajorTickUnit(50);
            sliderR.setMinorTickCount(5);
            sliderG.setMinorTickCount(5);
            sliderB.setMinorTickCount(5);
            sliderRGB.setMinorTickCount(5);
            
            sliderR.setBlockIncrement(1);
            sliderG.setBlockIncrement(1);
            sliderB.setBlockIncrement(1);
            sliderRGB.setBlockIncrement(1);
            
            AdjustColor adjColor = new AdjustColor();
            sliderRGB.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                int dif = new_val.intValue();// - old_val.intValue();
                for (int y = 0; y < bufferedImag.getHeight(); y++) {
                    for (int x = 0; x < bufferedImag.getWidth(); x++) {
                        int pixel = bufferedImag.getRGB(x, y);
                        Color color = new Color(pixel, true);
                        //Retrieving the R G B values
                        int red = adjColor.adjustColor( color.getRed(), dif);
                        int green  = adjColor.adjustColor(color.getGreen(), dif);
                        int blue = adjColor.adjustColor(color.getBlue(), dif);
                        Color myRGB = new Color(red, green, blue);
                        imageRGB.setRGB(x, y, myRGB.getRGB());
                    }
                }
                imageViewR.setImage(SwingFXUtils.toFXImage(imageR, null));
                imageViewG.setImage(SwingFXUtils.toFXImage(imageG, null));
                imageViewB.setImage(SwingFXUtils.toFXImage(imageB, null));
                imageViewRGB.setImage(SwingFXUtils.toFXImage(imageRGB, null));
            });
           
            
            sliderR.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                 difR = new_val.intValue();// - old_val.intValue();
                 for (int y = 0; y < bufferedImag.getHeight(); y++) {
                    for (int x = 0; x < bufferedImag.getWidth(); x++) {
                        int pixel = bufferedImag.getRGB(x, y);
                        Color color = new Color(pixel, true);
                        //Retrieving the R G B values
                        int red = adjColor.adjustColor( color.getRed(), difR);
                        int green = adjColor.adjustColor(color.getGreen(), difG);
                        int blue = adjColor.adjustColor(color.getBlue(), difB);
                        Color myRGB = new Color(red, green, blue);
                        imageRGB.setRGB(x, y, myRGB.getRGB());
                    }
                }
                imageViewRGB.setImage(SwingFXUtils.toFXImage(imageRGB, null));
                
            });
            sliderG.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                 difG = new_val.intValue();// - old_val.intValue();
                 for (int y = 0; y < bufferedImag.getHeight(); y++) {
                    for (int x = 0; x < bufferedImag.getWidth(); x++) {
                        int pixel = bufferedImag.getRGB(x, y);
                        Color color = new Color(pixel, true);
                        //Retrieving the R G B values
                        int red = adjColor.adjustColor( color.getRed(), difR);
                        int green = adjColor.adjustColor(color.getGreen(), difG);
                        int blue = adjColor.adjustColor(color.getBlue(), difB);                      
                        Color myRGB = new Color(red, green, blue);
                        imageRGB.setRGB(x, y, myRGB.getRGB());
                    }
                }
                imageViewRGB.setImage(SwingFXUtils.toFXImage(imageRGB, null));
            });
            sliderB.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                difB = new_val.intValue();// - old_val.intValue();
                for (int y = 0; y < bufferedImag.getHeight(); y++) {
                    for (int x = 0; x < bufferedImag.getWidth(); x++) {
                        int pixel = bufferedImag.getRGB(x, y);
                        Color color = new Color(pixel, true);
                        //Retrieving the R G B values
                         int red = adjColor.adjustColor( color.getRed(), difR);
                        int green = adjColor.adjustColor(color.getGreen(), difG);
                        int blue = adjColor.adjustColor(color.getBlue(), difB);
                        Color myRGB = new Color(red, green, blue);
                        imageRGB.setRGB(x, y, myRGB.getRGB());
                    }
                }
                imageViewRGB.setImage(SwingFXUtils.toFXImage(imageRGB, null));
            });
            vbRGB.getChildren().addAll(imageViewRGB,sliderR,sliderG,sliderB);
            spRGB.setContent(vbRGB);
            Stage secondStage = new Stage();
            Scene sceneRGB = new Scene(new HBox(), 1700, 800);
            ((HBox)sceneRGB.getRoot()).getChildren().addAll(spR, spG, spB, spRGB);
            secondStage.setTitle(name.getText());
            secondStage.setScene(sceneRGB);
            secondStage.show();            
        });
        //Menu pentru obtinerea celor 3 nuante de gri
        menuItem_Gray.setOnAction((ActionEvent event) -> {
            ScrollPane scollPaneGray1 = new ScrollPane();
            ScrollPane scollPaneGray2 = new ScrollPane();
            ScrollPane scollPaneGray3 = new ScrollPane();
            BufferedImage imageGray1 = new BufferedImage(bufferedImag.getWidth(), bufferedImag.getHeight(), BufferedImage.TYPE_INT_RGB);
            BufferedImage imageGray2 = new BufferedImage(bufferedImag.getWidth(), bufferedImag.getHeight(), BufferedImage.TYPE_INT_RGB);
            BufferedImage imageGray3 = new BufferedImage(bufferedImag.getWidth(), bufferedImag.getHeight(), BufferedImage.TYPE_INT_RGB);
             for (int y = 0; y < bufferedImag.getHeight(); y++) {
                for (int x = 0; x < bufferedImag.getWidth(); x++) {
                   //Retrieving contents of a pixel
                   int pixel = bufferedImag.getRGB(x,y);
                   //Creating a Color object from pixel value
                   Color color = new Color(pixel, true);
                   //Retrieving the R G B values
                   int red = color.getRed();
                   int green = color.getGreen();
                   int blue = color.getBlue();
                   int gray1 = (red + green + blue) / 3;
                   //prima formula pentru gri
                   Color grayV1 = new Color(gray1, gray1, gray1);
                   imageGray1.setRGB(x, y, grayV1.getRGB());
                   //A doua formula pentru gri
                    int gray2 = (int)(0.299 * red + 0.587 * green + 0.114* blue);
                   Color grayV2 = new Color(gray2, gray2, gray2);
                   imageGray2.setRGB(x, y, grayV2.getRGB());
                   //A treia formula pentru gri
                   int maxRGB = Math.max(Math.max(red, green), blue);
                   int minRGB = Math.min(Math.min(red, green), blue);
                   int gray3 = (maxRGB + minRGB) / 2;
                   Color grayV3 = new Color(gray3, gray3, gray3);
                   imageGray3.setRGB(x, y, grayV3.getRGB());
                }
            }
            VBox vbG1 = new VBox();
            Image imgGray1 = SwingFXUtils.toFXImage(imageGray1, null);
            ImageView imageViewGray1 = new ImageView();
            imageViewGray1.setFitHeight(400);
            imageViewGray1.setPreserveRatio(true);
            imageViewGray1.setImage(imgGray1);
            imageViewGray1.setSmooth(true);
            imageViewGray1.setCache(true);
            VBox vbG2 = new VBox();  
            
            Image imgGray2 = SwingFXUtils.toFXImage(imageGray2, null);
            ImageView imageViewGray2 = new ImageView();
            imageViewGray2.setFitHeight(400);
            imageViewGray2.setPreserveRatio(true);
            imageViewGray2.setImage(imgGray2);
            imageViewGray2.setSmooth(true);
            imageViewGray2.setCache(true);
            VBox vbG3 = new VBox();   
            
            Image imgGray3 = SwingFXUtils.toFXImage(imageGray3, null);
            ImageView imageViewGray3 = new ImageView();
            imageViewGray3.setFitHeight(400);
            imageViewGray3.setPreserveRatio(true);
            imageViewGray3.setImage(imgGray3);
            imageViewGray3.setSmooth(true);
            imageViewGray3.setCache(true);
            vbG1.getChildren().addAll(imageViewGray1);
            scollPaneGray1.setContent(vbG1);
            vbG2.getChildren().addAll(imageViewGray2);
            scollPaneGray2.setContent(vbG2);
            vbG3.getChildren().addAll(imageViewGray3);
            scollPaneGray3.setContent(vbG3);
            
            Stage trdStage = new Stage();
            Scene sceneGray = new Scene(new HBox(), 900, 500);
            ((HBox)sceneGray.getRoot()).getChildren().addAll(scollPaneGray1, scollPaneGray2, scollPaneGray3);
            trdStage.setTitle(name.getText());
            trdStage.setScene(sceneGray);
            trdStage.show();            
        });
        //Menu pentru YUV
        menuItem_YUV.setOnAction((ActionEvent event) -> {
            ScrollPane scollPaneYUV = new ScrollPane();      
            BufferedImage imageYUV = new BufferedImage(bufferedImag.getWidth(), bufferedImag.getHeight(), BufferedImage.TYPE_INT_RGB);
             for (int y = 0; y < bufferedImag.getHeight(); y++) {
                for (int x = 0; x < bufferedImag.getWidth(); x++) {
                    //Retrieving contents of a pixel
                    int pixel = bufferedImag.getRGB(x,y);
                    //Creating a Color object from pixel value
                    Color color = new Color(pixel, true);
                    //Retrieving the R G B value
                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();
                    int Y = (int) (0.3 * red + 0.6* green + 0.1 * blue);
                    int U = (int)(0.74*(red-Y)+0.26*(blue-Y));
                    int V = (int) (0.48*(red-Y)+0.41*(blue-Y));
                    Y = Math.min(Math.max(Y, 0), 255);
                    U = Math.min(Math.max(U, 0), 255);
                    V= Math.min(Math.max(V, 0), 255);
                    Color rgbColor = new Color(Y, U, V);
                    imageYUV.setRGB(x,y,rgbColor.getRGB());
                }
            }
            VBox vbYUV = new VBox();
            Image imgYUV = SwingFXUtils.toFXImage(imageYUV, null);
            ImageView imageViewYUV = new ImageView();
            imageViewYUV.setFitHeight(400);
            imageViewYUV.setPreserveRatio(true);
            imageViewYUV.setImage(imgYUV);
            imageViewYUV.setSmooth(true);
            imageViewYUV.setCache(true);
            
            vbYUV.getChildren().addAll(imageViewYUV);
            scollPaneYUV.setContent(vbYUV);
            Stage YUVStage = new Stage();
            Scene sceneYUV = new Scene(new HBox(), 900, 500);
            ((HBox)sceneYUV.getRoot()).getChildren().addAll(scollPaneYUV);
            YUVStage.setTitle(name.getText());
            YUVStage.setScene(sceneYUV);
            YUVStage.show(); 
        });
        //Menu pentru imaginea in luminanta/crominanta (YCbCr)
        menuItem_YCbCr.setOnAction((ActionEvent event) -> {
            ScrollPane scollPaneYCbCr = new ScrollPane();      
            BufferedImage imageYUV = new BufferedImage(bufferedImag.getWidth(), bufferedImag.getHeight(), BufferedImage.TYPE_INT_RGB);
             for (int y = 0; y < bufferedImag.getHeight(); y++) {
                for (int x = 0; x < bufferedImag.getWidth(); x++) {
                   //Retrieving contents of a pixel
                   int pixel = bufferedImag.getRGB(x,y);
                   //Creating a Color object from pixel value
                   Color color = new Color(pixel, true);
                   //Retrieving the R G B values
                   int red = color.getRed();
                   int green  = color.getGreen();
                   int blue = color.getBlue();
                   int Y = (int) (0.299 * red + 0.587 * green + 0.114 * blue);
                   int Cb = (int) (0.1687* red - 0.3313 * green + 0.498 * blue + 128);
                   int Cr = (int) (0.498 * red - 0.4187 * green - 0.0813 * blue + 128);
                   Color YUV = new Color(Y, Cb, Cr);
                   imageYUV.setRGB(x, y, YUV.getRGB());
                }
            }
            VBox vbYCbBr = new VBox();
            Image imgYCBbR = SwingFXUtils.toFXImage(imageYUV, null);
            ImageView imageViewYCbBr = new ImageView();
            imageViewYCbBr.setFitHeight(400);
            imageViewYCbBr.setPreserveRatio(true);
            imageViewYCbBr.setImage(imgYCBbR);
            imageViewYCbBr.setSmooth(true);
            imageViewYCbBr.setCache(true);
            
            vbYCbBr.getChildren().addAll(imageViewYCbBr);
            scollPaneYCbCr.setContent(vbYCbBr);
            Stage YCbBrStage = new Stage();
            Scene sceneYCbBr = new Scene(new HBox(), 900, 500);
            ((HBox)sceneYCbBr.getRoot()).getChildren().addAll(scollPaneYCbCr);
            YCbBrStage.setTitle(name.getText());
            YCbBrStage.setScene(sceneYCbBr);
            YCbBrStage.show(); 
        });
        //Conversie a benzilor RGB in spatiu HSV
        menuItem_HSV.setOnAction((ActionEvent event) -> {
            ScrollPane scollPaneHSV = new ScrollPane();   
            BufferedImage imageHSV = new BufferedImage(bufferedImag.getWidth(), bufferedImag.getHeight(), BufferedImage.TYPE_INT_RGB);
            for (int y = 0; y < bufferedImag.getHeight(); y++) {
                for (int x = 0; x < bufferedImag.getWidth(); x++) {
                    //Retrieving contents of a pixel
                    int pixel = bufferedImag.getRGB(x, y);
                    Color color = new Color(pixel, true);
                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();
                    float r = red / 255.0f;
                    float g = green / 255.0f;
                    float b = blue / 255.0f;
                    float maxRGB = Math.max(Math.max(r, g), b);
                    float minRGB = Math.min(Math.min(r, g), b);
                    float c = maxRGB - minRGB;
                    //value
                    float v = maxRGB;
                    //saturation
                    float s;
                    float h = 0; // initialize h to 0
                    if (v != 0) {
                        s = c / v;
                    }else{
                        s = 0;
                    }
                    //hue
                    if(c != 0){
                        if(maxRGB == r){
                            h = 60 * (g - b) / c;
                        }
                        else if(maxRGB == g){
                            h = 120 + 60 * (b - r) / c;
                        }
                        else if(maxRGB == b){
                            h = 240 + 60 * (r - g) / c;
                        }
                    }else{
                        h = 0;
                    }if(h < 0){
                        h = h + 360;
                    }
                    // Convert h, s, v to the range [0, 255]
                    float h_norm = h * 255 / 360;
                    float s_norm = s * 255;
                    float v_norm = v * 255;
                    // Create HSV color
                    Color HSV = Color.getHSBColor(h_norm, s_norm, v_norm);
                    imageHSV.setRGB(x, y, HSV.getRGB());
                }
            }
            VBox vbHSV = new VBox();
            Image imgHSV = SwingFXUtils.toFXImage(imageHSV, null);
            ImageView imageViewHSV = new ImageView();
            imageViewHSV.setFitHeight(400);
            imageViewHSV.setPreserveRatio(true);
            imageViewHSV.setImage(imgHSV);
            imageViewHSV.setSmooth(true);
            imageViewHSV.setCache(true);
            
            vbHSV.getChildren().addAll(imageViewHSV);
            scollPaneHSV.setContent(vbHSV);
            Stage HSVStage = new Stage();
            Scene sceneHSV = new Scene(new HBox(), 900, 500);
            ((HBox)sceneHSV.getRoot()).getChildren().addAll(scollPaneHSV);
            HSVStage.setTitle(name.getText());
            HSVStage.setScene(sceneHSV);
            HSVStage.show(); 
        });
        //Implemenatarea unei histograme pentru o imagine gri
         menuItemHistogram.setOnAction((ActionEvent event) -> {
            ScrollPane spH = new ScrollPane(); 
            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();
            BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
            BufferedImage imageGray = new BufferedImage(bufferedImag.getWidth(), bufferedImag.getHeight(), BufferedImage.TYPE_INT_RGB);
             for (int y = 0; y < bufferedImag.getHeight(); y++) {
                for (int x = 0; x < bufferedImag.getWidth(); x++) {
                   //Retrieving contents of a pixel
                   int pixel = bufferedImag.getRGB(x,y);
                   //Creating a Color object from pixel value
                   Color color = new Color(pixel, true);
                   //Retrieving the R G B values
                   int red = color.getRed();
                   int green = color.getGreen();
                   int blue = color.getBlue();
                   int gray = (red + green + blue) / 3;
                   Color grayV3 = new Color(gray, gray, gray);
                   imageGray.setRGB(x, y, grayV3.getRGB());
                }
            }
            try {
                Histogram calcHistogram = new Histogram();
                int[] histogram = calcHistogram.calculateHistogram(bufferedImag);
                XYChart.Series<String, Number> series = new XYChart.Series<>();
                for (int i = 0; i < histogram.length; i++) {
                    series.getData().add(new XYChart.Data<>(String.valueOf(i), histogram[i]));
                }
                barChart.getData().add(series);
                spH.setContent(barChart);
            } catch (IOException e) {
                e.printStackTrace();
            }
            VBox vbHistogram = new VBox(barChart);
            Image imgHistogram = SwingFXUtils.toFXImage(imageGray, null);
            ImageView imageViewHistogram = new ImageView();
            imageViewHistogram.setFitHeight(400);
            imageViewHistogram.setPreserveRatio(true);
            imageViewHistogram.setImage(imgHistogram);
            imageViewHistogram.setSmooth(true);
            imageViewHistogram.setCache(true);

            vbHistogram.getChildren().addAll(imageViewHistogram);
            spH.setContent(vbHistogram);
            Stage HistogramStage = new Stage();
            Scene sceneHistogram = new Scene(new HBox(), 900, 500);
            ((HBox)sceneHistogram.getRoot()).getChildren().addAll(spH);
            HistogramStage.setTitle(name.getText()); // Utilizarea etichetei pentru titlu
            HistogramStage.setScene(sceneHistogram);
            HistogramStage.show(); 
        });
         //Functia care va genera  o imagine  color pornind de la matricea de etichete
        menuItemEticheta.setOnAction((ActionEvent event) -> {
            Eticheta eticheta = new Eticheta();
            ScrollPane scollPaneEticheta = new ScrollPane();
            BufferedImage imageEtichete = new BufferedImage(bufferedImag.getWidth(), bufferedImag.getHeight(), BufferedImage.TYPE_INT_RGB);
            int[][] labels = new int[bufferedImag.getHeight()][bufferedImag.getWidth()];
            int numLabels = 0;
            // Etichetare componentelor conectate folosind algoritmul BFS
            for (int i = 0; i < bufferedImag.getHeight(); ++i) {
                for (int j = 0; j < bufferedImag.getWidth(); ++j) {
                    if (labels[i][j] == 0 && eticheta.isBlack(bufferedImag.getRGB(j, i))) {
                        numLabels++;
                        Queue<int[]> queue = new LinkedList<>();
                        labels[i][j] = numLabels;
                        queue.add(new int[]{i, j});
                        while (!queue.isEmpty()) {
                            int[] curr = queue.remove();
                            int currRow = curr[0];
                            int currCol = curr[1];
                            for (int k = -1; k <= 1; k++) {
                                for (int m = -1; m <= 1; m++) {
                                    int newRow = currRow + k;
                                    int newCol = currCol + m;
                                    if (eticheta.isValidIndex(newRow, newCol, bufferedImag.getHeight(), bufferedImag.getWidth())
                                            && labels[newRow][newCol] == 0
                                            && eticheta.isBlack(bufferedImag.getRGB(newCol, newRow))) {
                                        labels[newRow][newCol] = numLabels;
                                        queue.add(new int[]{newRow, newCol});
                                    }
                                }
                            }
                        }
                    }
                }
            }
            // Generează o culoare aleatoare unică pentru fiecare etichetă și setează pixelii corespunzători
            Map<Integer, Color> labelColors = new HashMap<>();
            for (int i = 1; i <= numLabels; i++) {
                labelColors.put(i, eticheta.getRandomColor());
            }
            for (int i = 0; i < bufferedImag.getHeight(); ++i) {
                for (int j = 0; j < bufferedImag.getWidth(); ++j) {
                    int label = labels[i][j];
                    if (label != 0) {
                        imageEtichete.setRGB(j, i, labelColors.get(label).getRGB());
                    } else {
                        imageEtichete.setRGB(j, i, Color.WHITE.getRGB());
                    }
                }
            }
            VBox vbEticheta = new VBox();
            Image imgEticheta = SwingFXUtils.toFXImage(imageEtichete, null);
            ImageView imageViewEticheta = new ImageView();
            imageViewEticheta.setFitHeight(400);
            imageViewEticheta.setPreserveRatio(true);
            imageViewEticheta.setImage(imgEticheta);
            imageViewEticheta.setSmooth(true);
            imageViewEticheta.setCache(true);

            vbEticheta.getChildren().addAll(imageViewEticheta);
            scollPaneEticheta.setContent(vbEticheta);
            Stage etichetaStage = new Stage();
            Scene sceneEticheta = new Scene(new HBox(), 1700, 800);
            ((HBox)sceneEticheta.getRoot()).getChildren().addAll(scollPaneEticheta);
            etichetaStage.setTitle(name.getText()); // Utilizarea etichetei pentru titlu
            etichetaStage.setScene(sceneEticheta);
            etichetaStage.show(); 
    });
        //Functia pentru filtru mediere
        menuItemMediation.setOnAction((ActionEvent event) -> {
            Filtru filtruMediere = new Filtru();
            ScrollPane spMed = new ScrollPane(); 
            BufferedImage mediaton = filtruMediere.mediere(bufferedImag);
            VBox vbMediation = new VBox();
            Image imgMediation = SwingFXUtils.toFXImage(mediaton, null);
            ImageView imageViewMediation = new ImageView();
            imageViewMediation.setFitHeight(400);
            imageViewMediation.setPreserveRatio(true);
            imageViewMediation.setImage(imgMediation);
            imageViewMediation.setSmooth(true);
            imageViewMediation.setCache(true);

            vbMediation.getChildren().addAll(imageViewMediation);
            spMed.setContent(vbMediation);
            Stage MediationStage = new Stage();
            Scene sceneMediation = new Scene(new HBox(), 900, 500);
            ((HBox)sceneMediation.getRoot()).getChildren().addAll(spMed);
            MediationStage.setTitle(name.getText()); // Utilizarea etichetei pentru titlu
            MediationStage.setScene(sceneMediation);
            MediationStage.show(); 
     });
        
        //Functia pentru filtru accentuare
         menuItemEnhancer.setOnAction((ActionEvent event) -> {
            ScrollPane spE = new ScrollPane(); 
            BufferedImage enhancer = new Filtru().accentuare(bufferedImag);
            VBox vbEnhancer = new VBox();
            Image imgMediation = SwingFXUtils.toFXImage(enhancer, null);
            ImageView imageViewEnhance = new ImageView();
            imageViewEnhance.setFitHeight(400);
            imageViewEnhance.setPreserveRatio(true);
            imageViewEnhance.setImage(imgMediation);
            imageViewEnhance.setSmooth(true);
            imageViewEnhance.setCache(true);

            vbEnhancer.getChildren().addAll(imageViewEnhance);
            spE.setContent(vbEnhancer);
            Stage enhancerStage = new Stage();
            Scene sceneEnhancer = new Scene(new HBox(), 900, 500);
            ((HBox)sceneEnhancer.getRoot()).getChildren().addAll(spE);
            enhancerStage.setTitle(name.getText()); // Utilizarea etichetei pentru titlu
            enhancerStage.setScene(sceneEnhancer);
            enhancerStage.show(); 
     });
         //Functia pentru filtru median
          menuItemMedian.setOnAction((ActionEvent event) -> {
            ScrollPane spMed = new ScrollPane(); 
            BufferedImage median = new Filtru().median(bufferedImag);
            VBox vbMedian = new VBox();
            Image imgMedian = SwingFXUtils.toFXImage(median, null);
            ImageView imageViewMedian = new ImageView();
            imageViewMedian.setFitHeight(400);
            imageViewMedian.setPreserveRatio(true);
            imageViewMedian.setImage(imgMedian);
            imageViewMedian.setSmooth(true);
            imageViewMedian.setCache(true);

            vbMedian.getChildren().addAll(imageViewMedian);
            spMed.setContent(vbMedian);
            Stage MedianStage = new Stage();
            Scene sceneMedian= new Scene(new HBox(), 900, 500);
            ((HBox)sceneMedian.getRoot()).getChildren().addAll(spMed);
            MedianStage.setTitle(name.getText()); // Utilizarea etichetei pentru titlu
            MedianStage.setScene(sceneMedian);
            MedianStage.show(); 
     });
          //Functia pentru filtru minim plecat de la filtru median
        menuItemMin.setOnAction((ActionEvent event) -> {
            ScrollPane spMin = new ScrollPane(); 
            BufferedImage minim = new Filtru().minim(bufferedImag);
            VBox vbMin = new VBox();
            Image imgMin = SwingFXUtils.toFXImage(minim, null);
            ImageView imageViewMin = new ImageView();
            imageViewMin.setFitHeight(400);
            imageViewMin.setPreserveRatio(true);
            imageViewMin.setImage(imgMin);
            imageViewMin.setSmooth(true);
            imageViewMin.setCache(true);

            vbMin.getChildren().addAll(imageViewMin);
            spMin.setContent(vbMin);
            Stage MinStage = new Stage();
            Scene sceneMin = new Scene(new HBox(), 900, 500);
            ((HBox)sceneMin.getRoot()).getChildren().addAll(spMin);
            MinStage.setTitle(name.getText()); // Utilizarea etichetei pentru titlu
            MinStage.setScene(sceneMin);
            MinStage.show(); 
     });
        //Functia pentru filtru maxim plecat de la filtru median
        menuItemMax.setOnAction((ActionEvent event) -> {
            ScrollPane spMax = new ScrollPane(); 
            BufferedImage maxim = new Filtru().maxim(bufferedImag);
            VBox vbMax = new VBox();
            Image imgMax = SwingFXUtils.toFXImage(maxim, null);
            ImageView imageViewMax = new ImageView();
            imageViewMax.setFitHeight(400);
            imageViewMax.setPreserveRatio(true);
            imageViewMax.setImage(imgMax);
            imageViewMax.setSmooth(true);
            imageViewMax.setCache(true);
            vbMax.getChildren().addAll(imageViewMax);
            spMax.setContent(vbMax);

            Stage MaxStage = new Stage();
            Scene sceneMax = new Scene(new HBox(), 900, 500);
            ((HBox)sceneMax.getRoot()).getChildren().addAll(spMax);
            MaxStage.setTitle(name.getText()); // Utilizarea etichetei pentru titlu
            MaxStage.setScene(sceneMax);
            MaxStage.show(); 
     });
        //Functia pentru filtru dilatare
         menuItemDilate.setOnAction((ActionEvent event) -> {
            Spinner<Integer> iterationsSpinner = new Spinner<>(1, 10, 1);
            ScrollPane spDilate = new ScrollPane();
            Button buttonFalse = new Button();
            buttonFalse.setText("Dilatare off");
            Button buttonTrue = new Button();
            buttonTrue.setText("Dilatare on");
            VBox vbDilate = new VBox(iterationsSpinner, buttonFalse, buttonTrue);
            Image imgDilate = SwingFXUtils.toFXImage(bufferedImag, null);
            ImageView imageViewDilate = new ImageView();
            imageViewDilate.setFitHeight(400);
            imageViewDilate.setPreserveRatio(true);
            imageViewDilate.setImage(imgDilate);
            imageViewDilate.setSmooth(true);
            imageViewDilate.setCache(true);
            vbDilate.getChildren().addAll(imageViewDilate);
            spDilate.setContent(vbDilate);
            //Spinner pentru iteratiile filtrului
            iterationsSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
                int iterations = newValue;
                BufferedImage median = new Filtru().dilate(bufferedImag, getDilateBackGroundPixel(), iterations);
                imageViewDilate.setImage(SwingFXUtils.toFXImage(median, null));
    });
            //Button pentru dezactivarea dilatarii
            buttonFalse.setOnAction((ActionEvent ev) -> {
                 setDilateBackGroundPixel(false);
             });
            //Button pentru activarea dilatarii
            buttonTrue.setOnAction((ActionEvent ev) -> {
                 setDilateBackGroundPixel(true);
             });
             Stage DilateStage = new Stage();
             Scene sceneDilate = new Scene(new HBox(),900, 500);
             ((HBox)sceneDilate.getRoot()).getChildren().addAll(spDilate);
             DilateStage.setTitle(name.getText()); // Utilizarea etichetei pentru titlu
             DilateStage.setScene(sceneDilate);
             DilateStage.show(); 
});
         //Functia pentru filtru laplacian
          menuItemLaplacian.setOnAction((ActionEvent event) -> {
            ScrollPane spLaplacian = new ScrollPane(); 
            BufferedImage laplacian = new Filtru().filterLaplacian(bufferedImag);
            VBox vbLaplacian = new VBox();
            Image imgLaplacian = SwingFXUtils.toFXImage(laplacian, null);
            ImageView imageViewLaplacian = new ImageView();
            imageViewLaplacian.setFitHeight(400);
            imageViewLaplacian.setPreserveRatio(true);
            imageViewLaplacian.setImage(imgLaplacian);
            imageViewLaplacian.setSmooth(true);
            imageViewLaplacian.setCache(true);
            vbLaplacian.getChildren().addAll(imageViewLaplacian);
            spLaplacian.setContent(vbLaplacian);

            Stage LaplacianStage = new Stage();
            Scene sceneLaplacian = new Scene(new HBox(), 900, 500);
            ((HBox)sceneLaplacian.getRoot()).getChildren().addAll(spLaplacian);
            LaplacianStage.setTitle(name.getText()); // Utilizarea etichetei pentru titlu
            LaplacianStage.setScene(sceneLaplacian);
            LaplacianStage.show(); 
     });
          //Functia pentru filtru detectare contru
         menuItemEdgeDetected.setOnAction((ActionEvent event) -> {
            Spinner<Integer> iterationsSpinner = new Spinner<>(0, 3, 1);
            ScrollPane spEdgeDetected = new ScrollPane();
            VBox vbEdgeDetected = new VBox(iterationsSpinner);
            Image imgDilate = SwingFXUtils.toFXImage(bufferedImag, null);
            ImageView imageViewEdgeDetected = new ImageView();
            imageViewEdgeDetected.setFitHeight(400);
            imageViewEdgeDetected.setPreserveRatio(true);
            imageViewEdgeDetected.setImage(imgDilate);
            imageViewEdgeDetected.setSmooth(true);
            imageViewEdgeDetected.setCache(true);
            vbEdgeDetected.getChildren().addAll(imageViewEdgeDetected);
            spEdgeDetected.setContent(vbEdgeDetected);
            //Spinner pentru iteratiile filtrului
            iterationsSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
                int iterations = newValue;
                BufferedImage median = new EdgeDetected().edgeDetect(bufferedImag, iterations);
                imageViewEdgeDetected.setImage(SwingFXUtils.toFXImage(median, null));
    });
            Stage EdgeDetectedStage = new Stage();
            Scene sceneEdgeDetected = new Scene(new HBox(),900, 500);
            ((HBox)sceneEdgeDetected.getRoot()).getChildren().addAll(spEdgeDetected);
            EdgeDetectedStage.setTitle(name.getText()); // Utilizarea etichetei pentru titlu
            EdgeDetectedStage.setScene(sceneEdgeDetected);
            EdgeDetectedStage.show(); 
});
         
         menuItemLZWCompress.setOnAction((ActionEvent event) -> {
            ScrollPane spLZW = new ScrollPane(); 
            VBox vbZLW = new VBox();
            Button saveFile = new Button();
            saveFile.setText("Salvati imaginea");
            Label CompressZLW = new Label();
            Label error = new Label();
            String LZWCompress;
            try {
                LZWCompress = new LZW().LZW_compress(bufferedImag,name.getText());
                CompressZLW.setText("Compresie LZW: " +LZWCompress);
                saveFile.setOnAction((ActionEvent ev) -> {
                    File file = new File(LZWCompress);
                        try {
                            ImageIO.write(SwingFXUtils.fromFXImage(imageView.getImage(),null), "lzw", file);
                            error.setText("Salvat cu succes!");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            error.setText("Eroare la salvarea imaginii!");
                        }
             });
            } catch (IOException ex) {
                Logger.getLogger(PrelucrareaImaginilorLaborator.class.getName()).log(Level.SEVERE, null, ex);
            }
            vbZLW.getChildren().addAll(CompressZLW, saveFile, error);
            spLZW.setContent(vbZLW);

            Stage ZLWStage = new Stage();
            Scene sceneZLW = new Scene(new HBox(), 900, 500);
            ((HBox)sceneZLW.getRoot()).getChildren().addAll(spLZW);
            ZLWStage.setTitle(name.getText()); // Utilizarea etichetei pentru titlu
            ZLWStage.setScene(sceneZLW);
            ZLWStage.show(); 
     });
        menuItemLZWDeCompress.setOnAction((ActionEvent event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Selecteaza fisierul cu extensia .lzw");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("LZW Compressed Image", "*.lzw"));
            File file = fileChooser.showOpenDialog(mainStage);
            if (file != null) {
                try {
                    VBox vbOpen = new VBox();
                    String outputFilePath = "output_image.bmp";
                    new LZW().LZW_decompress(file.getAbsolutePath());
                    File outputFile = new File(outputFilePath);
                    BufferedImage bufferedImage = ImageIO.read(outputFile);
                    name = new Label(outputFile.getAbsolutePath());
                    BufferedImage imageN = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
                    for (int y = 0; y < bufferedImage.getHeight(); y++) {
                        for (int x = 0; x < bufferedImage.getWidth(); x++) {
                           //Retrieving contents of a pixel
                            int pixel = bufferedImage.getRGB(x, y);
                            Color color = new Color(pixel, true);
                           //Retrieving the R G B values
                           int alpha = color.getAlpha();
                           int red = color.getRed();
                           int green = color.getGreen();
                           int blue = color.getBlue();
                           Color myWhite = new Color(red, green, blue, alpha);
                           imageN.setRGB(x, y, myWhite.getRGB());
                        }
                    }
                    Image image = SwingFXUtils.toFXImage(imageN, null);
                    vbOpen.getChildren().addAll(name,imageView);
                    imageView.setFitHeight(400);
                    imageView.setPreserveRatio(true);
                    imageView.setImage(image);
                    imageView.setSmooth(true);
                    imageView.setCache(true);
                    sp.setContent(vbOpen);
                    menuBar.getMenus().get(1).getItems().get(0).setDisable(false);
                } catch (IOException ex) {
                    Logger.getLogger(PrelucrareaImaginilorLaborator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
           
        });
        menuItemHuffmanCompress.setOnAction((ActionEvent event) -> {
            ScrollPane spLZW = new ScrollPane(); 
            VBox vbZLW = new VBox();
            Button saveFile = new Button();
            saveFile.setText("Salvati imaginea");
            Label CompressZLW = new Label();
            Label error = new Label();
            String HuffmanCompress;
            try {
                HuffmanCompress = new HuffmanCompression().compress(bufferedImag);
                CompressZLW.setText("Compresie Huffman: " + HuffmanCompress);
                saveFile.setOnAction((ActionEvent ev) -> {
                    FileChooser fileChooserSave = new FileChooser();
                    fileChooserSave.getExtensionFilters().add(new FileChooser.ExtensionFilter("Huffman Compressed Image", "*.huff"));
                    File file = fileChooserSave.showSaveDialog(mainStage);
                    if (file != null) {
                        try (FileOutputStream fos = new FileOutputStream(file)) {
                            fos.write(HuffmanCompress.getBytes());
                            error.setText("Salvat cu succes!");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            error.setText("Eroare la salvarea imaginii!");
                        }
                    }
                });
            } catch (IOException ex) {
                Logger.getLogger(PrelucrareaImaginilorLaborator.class.getName()).log(Level.SEVERE, null, ex);
            }
            vbZLW.getChildren().addAll(CompressZLW, saveFile, error);
            spLZW.setContent(vbZLW);

            Stage ZLWStage = new Stage();
            Scene sceneZLW = new Scene(new HBox(), 900, 500);
            ((HBox)sceneZLW.getRoot()).getChildren().addAll(spLZW);
            ZLWStage.setTitle(name.getText()); // Utilizarea etichetei pentru titlu
            ZLWStage.setScene(sceneZLW);
            ZLWStage.show(); 
     });
        menuItemHuffmanDeCompress.setOnAction((ActionEvent event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Selecteaza fisierul cu extensia .huff");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Huffman Compressed Image", "*.huff"));
            File file = fileChooser.showOpenDialog(mainStage);
            if (file != null) {
                try {
                    VBox vbOpen = new VBox();
                    BufferedImage bufferedImage = new HuffmanCompression().decompress(file.getAbsolutePath());
                    BufferedImage imageN = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
                    for (int y = 0; y < bufferedImage.getHeight(); y++) {
                        for (int x = 0; x < bufferedImage.getWidth(); x++) {
                           //Retrieving contents of a pixel
                            int pixel = bufferedImage.getRGB(x, y);
                            Color color = new Color(pixel, true);
                           //Retrieving the R G B values
                           int alpha = color.getAlpha();
                           int red = color.getRed();
                           int green = color.getGreen();
                           int blue = color.getBlue();
                           Color myWhite = new Color(red, green, blue, alpha);
                           imageN.setRGB(x, y, myWhite.getRGB());
                        }
                    }
                    Image image = SwingFXUtils.toFXImage(imageN, null);
                    vbOpen.getChildren().addAll(name,imageView);
                    imageView.setFitHeight(400);
                    imageView.setPreserveRatio(true);
                    imageView.setImage(image);
                    imageView.setSmooth(true);
                    imageView.setCache(true);
                    sp.setContent(vbOpen);
                    menuBar.getMenus().get(1).getItems().get(0).setDisable(false);
                } catch (IOException ex) {
                    Logger.getLogger(PrelucrareaImaginilorLaborator.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(PrelucrareaImaginilorLaborator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
           
        });
        //Functia pentru iesirea din program
        menuItem_Exit.setOnAction((ActionEvent event) -> {
            Platform.exit();
            System.exit(0);            
        });
        
        ((StackPane)scene.getRoot()).getChildren().addAll(vbox);
        mainStage.setTitle("Prelucrarea Imaginilor");
        mainStage.setScene(scene);
        mainStage.show();
        mainStage.setMaximized(true);
    }
    //getter si setter pentru dezactivarea sau activarea dilatarii
    private boolean dilateBackgroundPixel;
    public boolean getDilateBackGroundPixel(){
        return dilateBackgroundPixel;
    }
    public void setDilateBackGroundPixel(boolean dilateBackgroundPixel){
        this.dilateBackgroundPixel = dilateBackgroundPixel;
    } 
    public static void main(String[] args) {
        launch(args);
    }

 }
