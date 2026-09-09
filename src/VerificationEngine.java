import java.io.File;
import java.util.Map;

public class VerificationEngine {

    /**
     * Compares the baseline hashes against the current state of the files
     * and flags any new untracked files in the directory.
     */
    public static void verify(Map<String, String> baselineRegistry, java.util.List<String> currentFiles) {
        boolean isTampered = false;
        System.out.println("Starting integrity verification...\n");

        // Check baseline files for modifications or deletions
        for (Map.Entry<String, String> entry : baselineRegistry.entrySet()) {
            String filePath = entry.getKey();
            String expectedHash = entry.getValue();

            File file = new File(filePath);

            if (!file.exists()) {
                System.out.println("\u001B[31m[-] MISSING: " + filePath + "\u001B[0m");
                isTampered = true;
                continue;
            }

            try {
                String currentHash = FileHasher.getSHA256Hash(filePath);
                if (!currentHash.equals(expectedHash)) {
                    System.out.println("\u001B[31m[!] MODIFIED: " + filePath + " (Hashes do not match!)\u001B[0m");
                    isTampered = true;
                } else {
                    System.out.println("\u001B[32m[+] OK: " + filePath + "\u001B[0m");
                }
            } catch (Exception e) {
                System.out.println("\u001B[33m[!] ERROR: Could not read " + filePath + "\u001B[0m");
                isTampered = true;
            }
        }

        // Check the live directory for any new files not in the baseline
        for (String liveFile : currentFiles) {
            if (!baselineRegistry.containsKey(liveFile)) {
                System.out.println("\u001B[33m[?] UNTRACKED: " + liveFile + " (New file detected!)\u001B[0m");
                isTampered = true;
            }
        }

        System.out.println("------------------------------------------------");
        if (!isTampered) {
            System.out.println("\u001B[32m[SUCCESS] All files verified. No tampering detected.\u001B[0m");
        } else {
            System.out.println("\u001B[31m[WARNING] Integrity check failed! System may be compromised.\u001B[0m");
        }
    }
}