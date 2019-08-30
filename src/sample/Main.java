package sample;

import com.drew.imaging.ImageProcessingException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/sample/sample.fxml"));
        primaryStage.setTitle("Upload Files");
        primaryStage.getIcons().add(new Image("/resources/cloud-storage-uploading-option.png"));
        primaryStage.setScene(new Scene(root, 600, 275));
        primaryStage.show();
    }


    public static void main(String[] args) throws ImageProcessingException, IOException {


        //launch(args);
        File[] input = {
                new File("src/sample/result/banana_two.jpg"),
                new File("src/sample/result/banana_three.jpg"),
                new File("src/sample/result/banana_one.jpg"),
                new File("src/sample/result/banana_two.jpg"),
                new File("src/sample/result/banana_three.jpg"),
                new File("src/sample/result/banana_one.jpg"),
                new File("src/sample/result/banana_two.jpg"),
                new File("src/sample/result/banana_three.jpg"),
                new File("src/sample/result/banana_one.jpg"),
                new File("src/sample/result/banana_two.jpg"),
                new File("src/sample/result/banana_three.jpg"),
                new File("src/sample/result/banana_one.jpg"),
                new File("src/sample/result/banana_two.jpg"),
                new File("src/sample/result/banana_three.jpg"),
                new File("src/sample/result/banana_one.jpg"),
                new File("src/sample/result/banana_two.jpg"),
                new File("src/sample/result/banana_three.jpg"),
                new File("src/sample/result/banana_one.jpg"),
                new File("src/sample/result/banana_two.jpg"),
                new File("src/sample/result/banana_three.jpg"),
                new File("src/sample/result/banana_one.jpg"),
                new File("src/sample/result/banana_two.jpg"),
                new File("src/sample/result/banana_three.jpg"),
                new File("src/sample/result/banana_one.jpg"),
                new File("src/sample/result/banana_two.jpg"),
                new File("src/sample/result/banana_three.jpg"),
                new File("src/sample/result/banana_one.jpg"),
                new File("src/sample/result/banana_two.jpg"),
                new File("src/sample/result/banana_three.jpg"),
                new File("src/sample/result/banana_one.jpg"),
                new File("src/sample/result/banana_two.jpg"),
                new File("src/sample/result/banana_three.jpg"),
                new File("src/sample/result/banana_one.jpg"),
                new File("src/sample/result/banana_two.jpg"),
                new File("src/sample/result/banana_three.jpg"),
                new File("src/sample/result/banana_one.jpg"),
                new File("src/sample/result/banana_two.jpg"),
                new File("src/sample/result/banana_three.jpg"),
                new File("src/sample/result/banana_one.jpg"),
                new File("src/sample/result/banana_two.jpg"),
                new File("src/sample/result/banana_three.jpg"),
                new File("src/sample/result/banana_one.jpg"),
                new File("src/sample/result/banana_two.jpg"),
                new File("src/sample/result/banana_three.jpg"),
                new File("src/sample/result/banana_one.jpg"),
                new File("src/sample/result/banana_two.jpg"),
                new File("src/sample/result/banana_three.jpg"),
                new File("src/sample/result/banana_one.jpg"),
                new File("src/sample/result/banana_two.jpg"),
                new File("src/sample/result/banana_three.jpg"),
                new File("src/sample/result/banana_one.jpg"),
                new File("src/sample/result/banana_one.jpg")};
        Denoise denoise = new Denoise();
        BufferedImage image = denoise.Denoise2(input, 100);
        //File outputfile = new File("src/sample/result/result_finish.jpg");
        File outputfile = new File("src/sample/result/banana_test2.jpg");
        ImageIO.write(image, "jpg", outputfile);
        System.out.println("finish");
        /*File jpegFile = new File("src/image.JPG");//"C:\\Users\\Tony\\Desktop\\2016_0101_002343_136.JPG");
        Metadata metadata = ImageMetadataReader.readMetadata(jpegFile);
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                System.out.println(tag);
            }
        }
        ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);

        Date date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
        System.out.println(date);*/
    }
}
