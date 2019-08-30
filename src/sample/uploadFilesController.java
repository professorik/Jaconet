package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class uploadFilesController implements Initializable {

    @FXML
    private Button button;
    @FXML
    private AnchorPane root;

    private GridPane gridPane;

    private final
    int max_row = 5;
    private int i = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Files");
        fileChooser.setInitialDirectory(new File("C:/Users/Tony/Desktop/TUI-2019(2)"));
        fileChooser.getExtensionFilters().addAll(//
                new FileChooser.ExtensionFilter("Drone Files" , "*.tlog", "*.jpg" , "*.png"),
                new FileChooser.ExtensionFilter("TLOG", "*.tlog"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));
        gridPane = new GridPane(); gridPane.setPrefHeight(400); gridPane.setPrefWidth(148 * max_row);
        gridPane.setHgap(30); gridPane.setVgap(30);
        ScrollPane scrollPane = new ScrollPane(gridPane); scrollPane.setPrefHeight(400);
        root.getChildren().add(scrollPane);
        button.setOnAction(event -> {
            List<File> files = fileChooser.showOpenMultipleDialog(button.getScene().getWindow());
            if (files.size() > 0){
                for (File file: files){
                    int count = 90;
                    VBox vBox = new VBox();
                    String filePath = file.toURI().toString();

                    try (InputStream input = new FileInputStream(file)) {
                        try {
                            ImageIO.read(input).toString();
                        } catch (Exception e) {
                            filePath = "/resources/unknown.png";
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Image image = new Image(filePath,(count * 4032) / 3024,count,true, true, true);
                    ImageView iv = new ImageView();
                    iv.setImage(image);
                    iv.setPreserveRatio(true);
                    iv.setSmooth(true);
                    iv.setCache(true);
                    vBox.getChildren().addAll(iv, new Label(file.getName().length() <= 20? file.getName() : file.getName().substring(0, 18) + "..."));
                    gridPane.add(vBox , i % max_row , i / max_row);
                    ++i;
                }
            }
            printLog(files);
        });
    }

    private void printLog(List<File> files) {
        for (File file: files){
            System.out.println(file.getName());
        }
    }

}
