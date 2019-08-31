package Problem_6;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private AnchorPane originalAnchorPane;
    @FXML
    private AnchorPane processedAnchorPane;

    @FXML
    private MenuItem uploadImageBtn;

    private final FileChooser fileChooser = new FileChooser();

    private File fileImg;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileChooser.setTitle("Select Image");
        fileChooser.setInitialDirectory(new File("C:/Users"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Photo", "*.jpg", "*.png"));

        uploadImageBtn.setOnAction(event -> {
            fileImg = fileChooser.showOpenDialog(originalAnchorPane.getScene().getWindow());
            VBox vBox = new VBox();
            String filePath = fileImg.toURI().toString();

            try (InputStream input = new FileInputStream(fileImg)) {
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

            Image image = new Image(filePath, (800 * 4032) / 3024, 800, true, true, false);
            ImageView iv = new ImageView();
            iv.setImage(image);
            iv.setPreserveRatio(true);
            iv.setSmooth(true);
            iv.setCache(true);
            vBox.getChildren().addAll(iv, new Label(fileImg.getName()));
            vBox.setPadding(new Insets(1 , 1 , 1 ,1));

            Image processedImg = processingPhoto(image);
            VBox vBox2 = new VBox();
            ImageView iv2 = new ImageView();
            iv2.setImage(processedImg);
            iv2.setPreserveRatio(true);
            iv2.setSmooth(true);
            iv2.setCache(true);
            vBox2.getChildren().addAll(iv2, new Label(fileImg.getName()));
            vBox2.setPadding(new Insets(1 , 1 , 1 ,1));

            originalAnchorPane.getChildren().add(vBox);
            processedAnchorPane.getChildren().add(vBox2);
        });
    }

    //TODO: Отрегулировать фильтр (менять только числа, а не алгоритм)
    private Image processingPhoto(Image image){
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);

        //Это более точная
        BufferedImage bufferedImage2 = SwingFXUtils.fromFXImage(image, null);
        for (int i = 0; i < image.getHeight(); ++i) {
            for (int j = 0; j < image.getWidth(); ++j) {
                javafx.scene.paint.Color color = image.getPixelReader().getColor(j, i);
                if (color.getGreen() > 0.4) {
                    bufferedImage.setRGB(j, i, Color.BLUE.getRGB());
                    if (color.getGreen() > 0.55 && color.getRed() < color.getGreen() && color.getBlue() < color.getRed()) {
                        bufferedImage2.setRGB(j, i, Color.BLUE.getRGB());
                    }
                }
            }
        }
        return SwingFXUtils.toFXImage(bufferedImage2, null);
    }
}
