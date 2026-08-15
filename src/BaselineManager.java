import java.io.*;
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

    /**
     * Reads the baseline.txt file and loads the hashes back into the registry.
     */
    public void loadBaseline(String inputFilePath) throws IOException {
        // BufferedReader is perfect for reading text line-by-line
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line by our delimiter " | "
                String[] parts = line.split(" \\| ");
                if (parts.length == 2) {
                    String hash = parts[0];
                    String filePath = parts[1];
                    // Put it back in the map: filePath is the key, hash is the value
                    baselineRegistry.put(filePath, hash);
                }
            }
        }
    }

    /**
     * Returns the loaded registry so the Verification Engine can use it.
     */
    public Map<String, String> getRegistry() {
        return baselineRegistry;
    }
}