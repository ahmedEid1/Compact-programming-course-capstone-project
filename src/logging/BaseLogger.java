package logging;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

// task: Resource Management: try-with-resources
public class BaseLogger {
    private final String LOG_FILE_PATH;

    public BaseLogger() {
        LOG_FILE_PATH = "log.txt";
    }

    public BaseLogger(String logFilePath) {
        LOG_FILE_PATH = logFilePath;
    }

    public void log(String message) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE_PATH, true))) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
            writer.println(timestamp + " - " + message);
            throw new IOException("test excrption");
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            System.out.println("Try with resource have taken care of closing the writer");
        }
    }
}