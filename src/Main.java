import java.util.List;
import java.io.File;

public class Main {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    public static void main(String[] args){
        if (args.length != 2){
            System.out.println(ANSI_YELLOW + "Usage: java Main <mode> <directory>" + ANSI_RESET);
            System.out.println("Modes: --init (Create baseline) | --check (Verify integrity)");
            return;
        }

        String mode = args[0];
        String directoryPath = args[1];
        String baselineFile = "baseline.txt";
        BaselineManager manager = new BaselineManager();

        try {
            if (mode.equals("--init")){
                System.out.println("Initializing baseline for directory: "+ directoryPath);
                List<String> files = DirectoryScanner.scan(directoryPath);

                if (files.isEmpty()){
                    System.out.println(ANSI_YELLOW + "No files found in the specified directory."+ ANSI_RESET);
                    return;
                }

                manager.createBaselineFromDirectory(files);
                manager.saveBaseline(baselineFile);
                System.out.println(ANSI_GREEN+ "\n[+] SUCCESS: Baseline saved to " + baselineFile + ANSI_RESET);
            } else if (mode.equals("--check")) {
                File baseline = new File(baselineFile);
                if (!baseline.exists()) {
                    System.out.println(ANSI_RED + "[!] ERROR: No baseline found. Please run --init first." + ANSI_RESET);
                    return;
                }

                System.out.println("Loading baseline from: " + baselineFile);
                manager.loadBaseline(baselineFile);

                List<String> currentFiles = DirectoryScanner.scan(directoryPath);
                VerificationEngine.verify(manager.getRegistry(), currentFiles);
            } else {
                System.out.println(ANSI_RED + "Invalid mode. Please use --init or --check."+ANSI_RESET);
            }
        } catch (Exception e) {
            System.out.println(ANSI_RED + "[!] FATAL ERROR: " + e.getMessage() + ANSI_RESET);
        }
    }
}
