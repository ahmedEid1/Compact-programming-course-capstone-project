package logging;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

// task: Resource Management: try-with-resources
// task: propagate exceptions to caller
public class BaseLogger {
    private final String LOG_FILE_PATH;

    public BaseLogger() {
        LOG_FILE_PATH = "log.txt";
    }

    public BaseLogger(String logFilePath) {
        LOG_FILE_PATH = logFilePath;
    }

    public void log(String message) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE_PATH, true))) {
//             test: throw new IOException("test");
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
            writer.println(timestamp + " - " + message);
        }
    }
}