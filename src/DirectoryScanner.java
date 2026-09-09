import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DirectoryScanner {
    /**
     * Recursively scans a directory and returns a list of all absolute file paths.
     */
    public static List<String> scan(String directoryPath) throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) {
            return paths
                    .filter(Files::isRegularFile)
                    .filter(Files::isReadable)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        }
    }
}
