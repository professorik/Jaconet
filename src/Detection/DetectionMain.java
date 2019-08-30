package Detection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.net.URI;

public class DetectionMain {

    private static final String key1 = "39b989a4434c4b1198a4ef1f49550928";
    private static final String key2 = "0e1bbf08e0b04d86bdea106e0110a766";


    public static void main(String[] args) {
        HttpClient httpclient = HttpClients.createDefault();

        try {
            URIBuilder builder = new URIBuilder("https://westcentralus.api.cognitive.microsoft.com/vision/v1.0/analyze");

            builder.setParameter("visualFeatures", "Objects,Categories");
            builder.setParameter("details", "Celebrities,Landmarks");
            builder.setParameter("language", "en");

            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            request.setHeader("Content-Type", "application/octet-stream");
            request.setHeader("Ocp-Apim-Subscription-Key", key1);

            File file = new File("src/resources/train2.jpg");
            FileEntity reqEntity = new FileEntity(file);
            request.setEntity(reqEntity);

            FileEntity reqEntityF = new FileEntity(file, ContentType.APPLICATION_OCTET_STREAM);

            request.setEntity(reqEntityF);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                System.out.println(EntityUtils.toString(entity));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

/*
{
    "categories":[
        {
        "name":"trans_trainstation",
        "score":0.984375
        }
        ],
            "objects":[{
                "rectangle":{"x":973,"y":366,"w":76,"h":196},
            "object":"person","confidence":0.582},
                {"rectangle":{"x":1050,"y":235,"w":115,"h":360},
            "object":"person","confidence":0.79},
                {"rectangle":{"x":452,"y":207,"w":188,"h":557},
            "object":"person","confidence":0.767},
                {"rectangle":{"x":15,"y":7,"w":934,"h":776},
            "object":"subway train","confidence":0.797,
                "parent":{
                    "object":"train","confidence":0.896,
                "parent":{
                    "object":"Land vehicle","confidence":0.915,
                        "parent":{"object":"Vehicle","confidence":0.918}
                         }
                         }
            }],
                        "requestId":"1b3e701c-25c5-4c44-9517-e89108ce4a89","metadata":{"width":1200,"height":800,"format":"Jpeg"}}
 */

