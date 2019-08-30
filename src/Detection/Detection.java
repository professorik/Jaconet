package Detection;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;

public class Detection {
    private static final String key1 = "39b989a4434c4b1198a4ef1f49550928";
    private static final String key2 = "0e1bbf08e0b04d86bdea106e0110a766";

    public Detection() {
    }

    public BufferedImage Detection(File file) {
        HttpClient httpclient = HttpClients.createDefault();
        try {
            URIBuilder builder = new URIBuilder("https://westcentralus.api.cognitive.microsoft.com/vision/v1.0/analyze");
            builder.setParameter("visualFeatures", "Objects");
            builder.setParameter("details", "Celebrities,Landmarks");
            builder.setParameter("language", "en");
            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            request.setHeader("Content-Type", "application/octet-stream");
            request.setHeader("Ocp-Apim-Subscription-Key", key1);

            FileEntity reqEntity = new FileEntity(file);
            request.setEntity(reqEntity);

            FileEntity reqEntityF = new FileEntity(file, ContentType.APPLICATION_OCTET_STREAM);

            request.setEntity(reqEntityF);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                Image image = new Image(file.toURI().toString());
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
                ArrayList<ArrayList<String>> array = getResults(EntityUtils.toString(entity));
               // ArrayList<Pair<String, Color>> tag_color = new ArrayList<>();
                ArrayList<String> tags = new ArrayList<>();
                ArrayList<Color> colors = new ArrayList<Color>(){{
                    add(Color.RED);
                    add(Color.GREEN);
                    add(Color.CYAN);
                    add(Color.orange);
                    add(Color.magenta);
                }};
                for (ArrayList<String> kar : array) {
                    String i = kar.get(1);
                    int x, y, w, h;
                    w = Integer.valueOf(i.substring(5, i.indexOf(",\"x\"")));
                    x = Integer.valueOf(i.substring(i.indexOf(",\"x\"") + 5, i.indexOf(",\"h\"")));
                    h = Integer.valueOf(i.substring(i.indexOf(",\"h\"") + 5, i.indexOf(",\"y\"")));
                    y = Integer.valueOf(i.substring(i.indexOf(",\"y\"") + 5, i.length() - 1));
                    System.out.println(i + " " + w + " " + x + " " + h + " " + y);
                    Graphics graphics = bufferedImage.getGraphics();
                    System.out.println(kar.get(0));
                    if (tags.indexOf(kar.get(0)) == -1){
                        graphics.setColor(colors.get(tags.size() % colors.size()));
                        tags.add(kar.get(0));
                    }else{
                        graphics.setColor(colors.get(tags.indexOf(kar.get(0))));
                    }
                    graphics.drawRect(x, y, 1, h);
                    graphics.drawRect(x, y, w, 1);
                    graphics.drawRect(x + w - 1, y, 1, h);
                    graphics.drawRect(x, y + h - 1, w, 1);

                    graphics.setColor(Color.BLUE);
                    //font size == 24
                    graphics.setFont(new Font("TimesRoman", Font.PLAIN, bufferedImage.getWidth()/30));
                    if (graphics instanceof Graphics2D) {
                        Graphics2D g2 = (Graphics2D) graphics;
                        //g2.setFont(new Font("TimesRoman", Font.PLAIN, 150));
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);

                        g2.drawString(kar.get(0), x, y + h);// + graphics.getFont().getSize());
                    }
                    /*
                    for (int j = x; j < x + w; ++j) {
                      bufferedImage.setRGB(j, y + 1 , Color.RED.getRGB());
                      bufferedImage.setRGB(j, y + h - 1, Color.RED.getRGB());
                    }
                    for (int j = y; j < y + h; ++j) {
                      bufferedImage.setRGB(x + 1, j, Color.RED.getRGB());
                      bufferedImage.setRGB(x + w - 1, j, Color.RED.getRGB());
                     }*/
                }
                return bufferedImage;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<ArrayList<String>> getResults(String result) throws JSONException {
        //     System.out.println(result);
        JSONObject jsonObj = new JSONObject(result);
        if (!jsonObj.has("objects")) {
            return new ArrayList<>(new ArrayList<>());
        } else {
            ArrayList<ArrayList<String>> mainResult = new ArrayList<>(new ArrayList<>());
            JSONArray objectsArr = (JSONArray) jsonObj.get("objects");
            for (int i = 0; i < objectsArr.length(); i++) {
                JSONObject object = objectsArr.getJSONObject(i);
                //TODO проверки
                if (object.has("rectangle") && object.has("object")) {
                    mainResult.add(new ArrayList<String>() {{
                        add(object.get("object").toString());
                        add(object.get("rectangle").toString());
                       /* JSONArray arr = (JSONArray) object.get("rectangle");
                        add(arr.getJSONObject(0).toString());
                        add(arr.getJSONObject(1).toString());
                        add(arr.getJSONObject(2).toString());
                        add(arr.getJSONObject(3).toString());*/
                    }});
                }
            }
            return mainResult;
        }
    }
}
