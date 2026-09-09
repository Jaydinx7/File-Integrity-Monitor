# File Integrity Monitor (FIM)

A robust command-line tool built in Java to secure directories and verify data integrity using the SHA-256 cryptographic hashing algorithm.

## Features
*   **Recursive Scanning:** Maps entire directory trees efficiently using Java NIO.
*   **Baseline Management:** Generates and saves a registry of known-good file states.
*   **Tamper Detection:** Identifies modified files, missing files, and alerts on new untracked files.
*   **Robust Error Handling:** Safely skips unreadable files and handles missing baselines without crashing.

## How to Compile
Navigate to the source directory and compile the Java files:
```bash
cd src
javac *.java
```

## How to Run
Run the tool directly from the `src` folder.

**1. Initialize a new baseline:**
```bash
java Main --init <path_to_directory>
```

**2. Verify directory integrity:**
```bash
java Main --check <path_to_directory>
```
