package Pages.Images;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ImagePreDownload {

    public static void imgDownload() throws IOException {

        URL url = new URL("http://apimeme.com/meme?meme=Alarm-Clock&top=Top+text&bottom=Bottom+text");
        InputStream in = url.openStream();
        Files.copy(in, Paths.get("example.jpeg"), StandardCopyOption.REPLACE_EXISTING);
        in.close();

    }
}