package sample;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageViewDemo3 extends Application {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private boolean fl = false;

    private static File mainFile;

    @Override
    public void start(Stage primaryStage) throws Exception {
        File file = new File("src/DronePhotos/2016_0101_000725_002.JPG");
        Image image = new Image(file.toURI().toString());
        ImageView imageView = new ImageView(image);

        Button buttonStart = new Button("Run");
        Button buttonUpload = new Button("Upload");
        Button buttonPhotos = new Button("Original/Edited");

        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Files");
        fileChooser.setInitialDirectory(new File("C:/Users/Tony/Desktop/TUI-2019(2)"));
        fileChooser.getExtensionFilters().addAll(//
                //new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("Drone Files" , "*.tlog", "*.jpg" , "*.png"),
                new FileChooser.ExtensionFilter("TLOG", "*.tlog"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));


        buttonUpload.setOnAction(event -> {
           mainFile = fileChooser.showOpenDialog(buttonUpload.getScene().getWindow());
           if (mainFile != null)
           imageView.setImage(new Image(mainFile.toURI().toString()));
        });

        FlowPane root = new FlowPane();
        root.setPadding(new Insets(20));
        root.setHgap(20);

        root.getChildren().addAll(buttonStart, buttonUpload, buttonPhotos, imageView);
        imageView.setX(100);
        imageView.setY(100);
        imageView.setLayoutX(100);
        imageView.setLayoutY(100);
        imageView.setFitWidth(1000);
        imageView.setFitHeight(750);

        Scene scene = new Scene(root, 1200, 800);

         /*
        Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.BLACK, Color.WHITE, Color.ORANGE, Color.yellow, Color.red,  Color.getHSBColor(2 , 4 , 5)};
        for (Color color: colors) {
            System.out.println(color.getRGB() + "; " + color);
        }

        Arrays.sort(colors, new Comparator<Color>() {
            @Override
            public int compare(Color o1, Color o2) {
                return o1.getRGB() - o2.getRGB();
            }
            //4750180
            //4750140
        });
        System.out.println("After");
        for (Color color: colors) {
            System.out.println(color.getRGB() + "; " + color);
        }
        */

        buttonStart.setOnAction(event -> {
            int count = 0;
            Image image1 = imageView.getImage();
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image1, null);



            BufferedImage bufferedImage2 = SwingFXUtils.fromFXImage(image1, null);

            System.out.println(image1.getWidth() + " " + image1.getHeight());
            for (int i = 0; i < image1.getHeight(); ++i) {
                for (int j = 0; j < image1.getWidth(); ++j) {
                    javafx.scene.paint.Color color = image1.getPixelReader().getColor(j, i);
                    if (color.getGreen() > 0.4) {
                        count++;
                        bufferedImage.setRGB(j, i,  /*new java.awt.Color((float) color.getRed(),
                              (float) color.getGreen(),
                              (float) color.getBlue(),
                              (float) color.getOpacity()).getRGB())*/ Color.BLUE.getRGB());
                        if (color.getGreen() > 0.55 && color.getRed() < color.getGreen() && color.getBlue() < color.getRed()) {
                            bufferedImage2.setRGB(j, i, Color.BLUE.getRGB());
                        }
                    }
                }
            }
            File outputfile = new File("src/sample/image.JPG");
            File outputfile2 = new File("src/sample/image1.JPG");
            try {
                ImageIO.write(bufferedImage, "jpg", outputfile);
                ImageIO.write(bufferedImage2, "jpg", outputfile2);
                File file3 = new File("src/sample/image1.JPG");
                imageView.setImage(new Image(file3.toURI().toString()));
                fl = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(count);
        });

        buttonPhotos.setOnAction(event -> {
            if (mainFile != null) {
                if (fl) {
                    imageView.setImage(new Image(mainFile.toURI().toString()));
                } else {
                    File file3 = new File("src/sample/image1.JPG");
                    imageView.setImage(new Image(file3.toURI().toString()));
                }
                fl = !fl;
            }
        });


        primaryStage.setTitle("Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
