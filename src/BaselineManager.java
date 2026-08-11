import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BaselineManager {

    private Map<String, String> baselineRegistry;

    public BaselineManager() {
        this.baselineRegistry = new HashMap<>();
    }

    /**
     * Adds a file's path and its corresponding hash to the registry.
     */
    public void addFileToBaseline(String filePath, String fileHash) {
        baselineRegistry.put(filePath, fileHash);
    }

    /**
     * Saves the current registry of hashes to a text file.
     * We use a delimiter like " | " so it's easy to read and parse later.
     */
    public void saveBaseline(String outputFilePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            for (Map.Entry<String, String> entry : baselineRegistry.entrySet()) {
                writer.write(entry.getValue() + " | " + entry.getKey());
                writer.newLine();
            }
        }
    }
}