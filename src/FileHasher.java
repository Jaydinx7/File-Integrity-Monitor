import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileHasher {

    /**
     * Generates a SHA-256 hash for the given file path.
     */
    public static String getSHA256Hash(String filePath) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        FileInputStream fis = new FileInputStream(filePath);

        byte[] buffer = new byte[4096];
        int bytesRead;

        while ((bytesRead = fis.read(buffer)) != -1) {
            digest.update(buffer, 0, bytesRead);
        }

        fis.close();

        byte[] hashBytes = digest.digest();
        return bytesToHex(hashBytes);
    }

    /**
     * Converts the raw byte array into a readable hexadecimal string.
     */
    private static String bytesToHex(byte[] hash){
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash){
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1){
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}// tampering with the file
