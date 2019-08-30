package sample;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.*;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import javafx.animation.KeyFrame;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import java.awt.Frame.*;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ImageViewDemo2 extends Application {


    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    @Override
    public void start(Stage primaryStage) throws Exception {
        File file = new File("src/DronePhotos/2016_0101_000725_002.JPG");
        Image image = new Image(file.toURI().toString());
        ImageView imageView = new ImageView(image);

        Button buttonStart = new Button("Run");

        FlowPane root = new FlowPane();
        root.setPadding(new Insets(20));
        root.setHgap(20);

        root.getChildren().addAll(buttonStart, imageView);
        imageView.setX(100); imageView.setY(100); imageView.setLayoutX(100);imageView.setLayoutY(100);
        imageView.setFitWidth(1000); imageView.setFitHeight(750);

        Scene scene = new Scene(root, 1200, 800);

        buttonStart.setOnAction(event -> {
            ArrayList<File> images = initArray();
            Collections.sort(images, new Comparator<File>() {
                @Override
                public int compare(File lhs, File rhs) {
                    // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                    return (int) (getImageDate(lhs).getTime() - getImageDate(rhs).getTime());
                }
            });
            Timeline timeline = new Timeline();

            Duration totalDelay = Duration.ZERO;
            for (File file1 : images) {
                System.out.println(ANSI_GREEN + file1.getName() + ANSI_RESET);
                KeyFrame frame = new KeyFrame(totalDelay, e -> imageView.setImage(new Image(file1.toURI().toString())));
                timeline.getKeyFrames().add(frame);
                totalDelay = totalDelay.add(new Duration(750));
            }

            timeline.play();
        });


        primaryStage.setTitle("Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Date getImageDate(File file){
        Metadata metadata = null;
        try {
            metadata = ImageMetadataReader.readMetadata(file);
        } catch (ImageProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);

        Date date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
        return  date;
    }

    private ArrayList<File> initArray(){
        File f = new File("src/DronePhotos");
        return new ArrayList<>(Arrays.asList(f.listFiles()));
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}