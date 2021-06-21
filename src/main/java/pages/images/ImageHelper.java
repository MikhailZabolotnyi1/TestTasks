package pages.images;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

public class ImageHelper {

    public static void imgDownload() throws IOException {

        URL url = new URL("http://apimeme.com/meme?meme=Alarm-Clock&top=Top+text&bottom=Bottom+text");
        InputStream in = url.openStream();
        Files.copy(in, Paths.get("example.jpeg"), StandardCopyOption.REPLACE_EXISTING);
        in.close();

    }

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
