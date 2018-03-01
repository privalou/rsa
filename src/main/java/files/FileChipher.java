package files;

import BlockCypher.BlockCipher;
import rsa.PrivateKey;
import rsa.PublicKey;
import utils.AESUtils;
import utils.CipherUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
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
            while ( inputStream.read(buffer)!=-1) {
                outputStream.write(AESUtils.encrypt(secretKey, buffer));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void decrypt(String filename, String resultFilename, PrivateKey privateKey){
        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(filename));
             BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(resultFilename))){
            byte[] encryptedKey = new byte[128];
            inputStream.read(encryptedKey);
            byte[] decrypterKey = privateKey.decrypt(encryptedKey);
            SecretKey secretKey = new SecretKeySpec(decrypterKey,"AES");
            long fileSize = new File(filename).length() - AES_KEY_ZIE;
            byte[] buffer = new byte[(int) fileSize];
            while ( inputStream.read(buffer)!=-1) {
                outputStream.write(AESUtils.decrypt(secretKey, buffer));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
