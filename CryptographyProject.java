import java.io.*;
import java.util.Scanner;

public class CryptographyProject {

    // Encrypts a string using a Caesar cipher
    public static String encrypt(String message, int shift) {
        StringBuilder encrypted = new StringBuilder(); // mutable
        for (char c : message.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                char shifted = (char) (((c - base + shift) % 26) + base);
                encrypted.append(shifted);
            } else {
                encrypted.append(c);
            }
        }
        return encrypted.toString();
    }

    // Decrypts a encrypted string using the same Caesar cipher
    public static String decrypt(String encryptedMessage, int shift) {
        StringBuilder decrypted = new StringBuilder();
        for (char c : encryptedMessage.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                char shifted = (char) (((c - base - shift + 26) % 26) + base);
                decrypted.append(shifted);
            } else {
                decrypted.append(c); 
            }
        }
        return decrypted.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println();
            System.out.println("Welcome to the Krish's Cryptography Project!");
            System.out.println();
            System.out.println("1. Encrypt Message");
            System.out.println("2. Decrypt Message");
            System.out.println("3. Encrypt File");
            System.out.println("4. Decrypt File");
            System.out.println("5. Exit");
            System.out.println();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Encrypt a message
                    System.out.print("Enter message to encrypt: ");
                    String messageToEncrypt = scanner.nextLine();

                    System.out.print("Enter the shift value for encryption (0-25): ");
                    int encryptShift = scanner.nextInt();

                    String encryptedMessage = encrypt(messageToEncrypt, encryptShift);
                    System.out.println("Encrypted Message: " + encryptedMessage);
                    break;

                case 2:
                    // Decrypt an encrypted message
                    System.out.print("Enter encrypted message: ");
                    String encryptedMessageToDecrypt = scanner.nextLine();

                    System.out.print("Enter the shift value for decryption (0-25): ");
                    int decryptShift = scanner.nextInt();

                    String decryptedMessage = decrypt(encryptedMessageToDecrypt, decryptShift);
                    System.out.println("Decrypted Message: " + decryptedMessage);
                    break;

                case 3:
                    // Encrypt contents of a file
                    System.out.print("Enter path of the file to encrypt: ");
                    String filePathEncrypt = scanner.nextLine();

                    System.out.print("Enter the shift value for file encryption (0-25): ");
                    int fileEncryptShift = scanner.nextInt();

                    try (BufferedReader fileReader = new BufferedReader(new FileReader(filePathEncrypt));
                            PrintWriter fileWriter = new PrintWriter(getEncryptedFilePath(filePathEncrypt))) {

                        String line;
                        while ((line = fileReader.readLine()) != null) {
                            String encryptedLine = encrypt(line, fileEncryptShift);
                            fileWriter.println(encryptedLine);
                        }
                        System.out.println("File encrypted successfully.");
                    } catch (IOException e) {
                        System.out.println("Error: Unable to encrypt file.");
                    }
                    break;

                case 4:
                    // Decrypt contents of an encrypted file
                    System.out.print("Enter path of the file to decrypt: ");
                    String filePathDecrypt = scanner.nextLine();

                    System.out.print("Enter the shift value for file decryption (0-25): ");
                    int fileDecryptShift = scanner.nextInt();

                    try (BufferedReader fileReader = new BufferedReader(new FileReader(filePathDecrypt));
                            PrintWriter fileWriter = new PrintWriter(getDecryptedFilePath(filePathDecrypt))) {

                        String line;
                        while ((line = fileReader.readLine()) != null) {
                            String decryptedLine = decrypt(line, fileDecryptShift);
                            fileWriter.println(decryptedLine);
                        }
                        System.out.println("File decrypted successfully.");
                    } catch (IOException e) {
                        System.out.println("Error: Unable to decrypt file.");
                    }
                    break;

                case 5:
                    // Exit the program
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 5);

        scanner.close();
    }

    // Helper method to generate the encrypted file path
    private static String getEncryptedFilePath(String originalFilePath) {
        File originalFile = new File(originalFilePath);
        String parentDirectory = originalFile.getParent();
        String fileName = originalFile.getName();
        return parentDirectory + File.separator + "encrypted_" + fileName;
    }

    // Helper method to generate the decrypted file path
    private static String getDecryptedFilePath(String encryptedFilePath) {
        File encryptedFile = new File(encryptedFilePath);
        String parentDirectory = encryptedFile.getParent();
        String fileName = encryptedFile.getName().replace("encrypted_", "");
        return parentDirectory + File.separator + "decrypted_" + fileName;
    }
}

