import java.io.File;
import java.util.Map;

public class VerificationEngine {

    /**
     * Compares the baseline hashes against the current state of the files.
     */
    public static void verify(Map<String, String> baselineRegistry) {
        boolean isTampered = false;
        System.out.println("Starting integrity verification...\n");

        for (Map.Entry<String, String> entry : baselineRegistry.entrySet()) {
            String filePath = entry.getKey();
            String expectedHash = entry.getValue();

            File file = new File(filePath);

            // Check 1: Did someone delete the file?
            if (!file.exists()) {
                System.out.println("[-] MISSING: " + filePath);
                isTampered = true;
                continue;
            }

            try {
                // Check 2: Does the hash match?
                String currentHash = FileHasher.getSHA256Hash(filePath);
                if (!currentHash.equals(expectedHash)) {
                    System.out.println("[!] MODIFIED: " + filePath + " (Hashes do not match!)");
                    isTampered = true;
                } else {
                    System.out.println("[+] OK: " + filePath);
                }
            } catch (Exception e) {
                System.out.println("[!] ERROR: Could not read " + filePath);
                isTampered = true;
            }
        }

        System.out.println("------------------------------------------------");
        if (!isTampered) {
            System.out.println("[SUCCESS] All files verified. No tampering detected.");
        } else {
            System.out.println("[WARNING] Integrity check failed! System may be compromised.");
        }
    }
}