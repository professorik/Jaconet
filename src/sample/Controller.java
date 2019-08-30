package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private AnchorPane pane;
    @FXML
    private ImageView imageView;
    @FXML
    private Button fileChooserButton;
    @FXML
    private ListView<String> nameList;

    private ObservableList<String> observableList;

    Label information = new Label();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        observableList = FXCollections.observableArrayList();

        information.setText("Для того, чтобы загрузить файлы - \n" +
                "нажмите кнопку \'Загрузить файлы\'.\n" +
                "Далее выберете нужный формат файлов\n" +
                "и сами файлы и нажмите кнопку 'Open'.");
        pane.getChildren().add(information);
        information.setLayoutX(imageView.getLayoutX() + 20); information.setLayoutY(imageView.getLayoutY() + 100);
        information.setVisible(false);

        imageView.setOnMouseExited(event -> System.out.println(ImageViewDemo2.ANSI_PURPLE + "KAR" + ImageViewDemo2.ANSI_RESET));
        imageView.setOnMouseEntered(event -> System.out.println(ImageViewDemo2.ANSI_GREEN + "KAR" + ImageViewDemo2.ANSI_RESET));
        //imageView.setOnMouseClicked(event -> System.out.println(ImageViewDemo2.ANSI_BLACK + "KAR" + ImageViewDemo2.ANSI_RESET));
        //imageView.setOnMouseDragEntered(event -> System.out.println(ImageViewDemo2.ANSI_BLUE + "KAR" + ImageViewDemo2.ANSI_RESET));
        //imageView.setOnMouseDragged(event -> System.out.println(ImageViewDemo2.ANSI_CYAN + "KAR" + ImageViewDemo2.ANSI_RESET));
       // imageView.setOnMouseMoved(event -> System.out.println(ImageViewDemo2.ANSI_RED + "KAR" + ImageViewDemo2.ANSI_RESET));
        //imageView.setOnMouseReleased(event -> System.out.println(ImageViewDemo2.ANSI_WHITE + "KAR" + ImageViewDemo2.ANSI_RESET));
        //imageView.setOnMousePressed(event -> System.out.println(ImageViewDemo2.ANSI_YELLOW + "KAR" + ImageViewDemo2.ANSI_RESET));
       // imageView.setOnMouseDragOver(event -> System.out.println("KAR"));

        imageView.setOnMouseEntered(event -> {
            imageView.setVisible(false);
            information.setVisible(true);
            System.out.println(ImageViewDemo2.ANSI_GREEN + "KAR" + ImageViewDemo2.ANSI_RESET);
        });
        imageView.setOnMouseExited(event -> {
            System.out.println(event.getScreenX() + " " + event.getX() + " " +  event.getSceneX());
            if (!(event.getScreenX() < imageView.getX() + imageView.getFitWidth())) {
                //information.setOnMouseExited(event1 -> {
                imageView.setVisible(true);
                information.setVisible(false);
                System.out.println(ImageViewDemo2.ANSI_PURPLE + "KAR" + ImageViewDemo2.ANSI_RESET);
            }
           // });
        });

        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Files");
        fileChooser.setInitialDirectory(new File("C:/Users/Tony/Desktop/TUI-2019(2)"));
        fileChooser.getExtensionFilters().addAll(//
                //new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("Drone Files" , "*.tlog", "*.jpg" , "*.png"),
                new FileChooser.ExtensionFilter("TLOG", "*.tlog"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));


        fileChooserButton.setOnAction(event -> {
            List<File> files = fileChooser.showOpenMultipleDialog(fileChooserButton.getScene().getWindow());
            printLog(files);
        });
    }

    private void printLog(List<File> files) {
        observableList.clear();
        if (files == null || files.isEmpty()) {
            return;
        }
        for (File file : files) {
           observableList.add(file.getName());
        }
        nameList.setItems(observableList);
        nameList.refresh();
    }

}
