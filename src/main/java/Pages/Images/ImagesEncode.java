package Pages.Images;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class ImagesEncode {

    public static String LocalImgEncode() throws IOException {

        byte[] fileContent = FileUtils.readFileToByteArray(new File("example.jpeg"));
        String encodedString = Base64.getEncoder().encodeToString(fileContent);
        return encodedString;

    }
    public static String URLImgEncode() throws IOException {
        String encodedString =
                Base64.getEncoder().withoutPadding().encodeToString(GETRequest.GET("http://apimeme.com/meme?meme=Alarm-Clock&top=Top+text&bottom=Bottom+text"));
        return encodedString;
    }
}
