package Detection;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DetectionApplication extends Application {

    private boolean fl = false;

    private static File mainFile;

    @Override
    public void start(Stage primaryStage) throws Exception {
        File file = new File("src/resources/cloud-storage-uploading-option.png");
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

        buttonStart.setOnAction(event -> { if (mainFile != null) {
            // Image image1 = imageView.getImage();
            // BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image1, null);
            BufferedImage bufferedImage = (new Detection()).Detection(mainFile);
            File outputfile = new File("src/Detection/analyzingPhoto.JPG");
            try {
                ImageIO.write(bufferedImage, "jpg", outputfile);
                File file3 = new File("src/Detection/analyzingPhoto.JPG");
                imageView.setImage(new Image(file3.toURI().toString()));
                fl = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }});

        buttonPhotos.setOnAction(event -> {if (mainFile != null) {
                if (fl) {
                    imageView.setImage(new Image(mainFile.toURI().toString()));
                } else {
                    File file3 = new File("src/sample/analyzingPhoto.JPG");
                    imageView.setImage(new Image(file3.toURI().toString()));
                }
                fl = !fl;
        }});

        primaryStage.setTitle("Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
