package files;

import rsa.PrivateKey;
import rsa.PublicKey;
import utils.AESUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class FileChipher {
    public static final int BUFFER_SIZE = 1024;
    public static final int AES_KEY_ZIE = 128;

    public static void encrypt(String filename, PublicKey publicKey, String aesKeyName) {
        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(filename));
             BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filename + ".aes"))) {
            byte[] key = aesKeyName.getBytes();
            key = Arrays.copyOf(key, 16);
            SecretKey secretKey = new SecretKeySpec(key, "AES");
            long fileSize = new File(filename).length();
            byte[] buffer = new byte[(int) fileSize];
            byte[] decryptedKey = publicKey.encrypt(secretKey.getEncoded());
            outputStream.write(decryptedKey);
            while (inputStream.read(buffer) != -1) {
                outputStream.write(AESUtils.encrypt(secretKey, buffer));
            }
            inputStream.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void decrypt(String filename, String resultFilename, PrivateKey privateKey) {
        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(filename));
             BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(resultFilename))) {
            byte[] encryptedKey = new byte[128];
            inputStream.read(encryptedKey);
            byte[] decryptedKey = privateKey.decrypt(encryptedKey);
            SecretKey secretKey = new SecretKeySpec(decryptedKey, "AES");
            long fileSize = new File(filename).length() - AES_KEY_ZIE;
            byte[] buffer = new byte[(int) fileSize];
            while (inputStream.read(buffer) != -1) {
                outputStream.write(AESUtils.decrypt(secretKey, buffer));
            }
            inputStream.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void decryptBytes(byte[] data, PrivateKey privateKey, String resultFilename){
        try(BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(resultFilename))) {
            byte[] encryptedKey = new byte[128];
            encryptedKey = Arrays.copyOfRange(data,0, encryptedKey.length);
            byte[] decryptedKey = privateKey.decrypt(encryptedKey);
            String string = new String(decryptedKey);
            SecretKey secretKey = new SecretKeySpec(decryptedKey,"AES");
            long fileSize = data.length-AES_KEY_ZIE;
            byte[] buffer = Arrays.copyOfRange(data,encryptedKey.length, data.length);
            outputStream.write(AESUtils.decrypt(secretKey, buffer));
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
