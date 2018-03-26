package utils;

import files.KeyGenerator;
import org.apache.commons.codec.binary.Hex;
import rsa.PrivateKey;
import rsa.PublicKey;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ESignatureUtils {
    private static final String ALGORITH = "SHA-256";

    public static byte[] signFile(String fileName, PrivateKey privateKey){
        File file = new File(fileName);
        byte[] hash = null;
        try(InputStream inputStream = new FileInputStream(file)){
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] buf = new byte[(int) file.length()];
            while (inputStream.available() > 0) {
                inputStream.read(buf);
            }
            hash = digest.digest(buf);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash;
    }
}
