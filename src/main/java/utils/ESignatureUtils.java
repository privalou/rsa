package utils;

import org.apache.commons.codec.binary.Hex;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ESignatureUtils {
    private static final String ALGORITHM = "SHA-256";

    public static byte[] signFile(String fileName) {
        File file = new File(fileName);
        byte[] hash = makeHash(file);
        return hash;
    }

    public static boolean unsignFile(byte[] sourceHash, String fileName) {
        File file = new File(fileName);
        byte[] hash = makeHash(file);
        return Hex.encodeHexString(hash).equals(Hex.encodeHexString(sourceHash));
    }

    private static byte[] makeHash(File file) {
        byte[] hash = null;
        try (InputStream inputStream = new FileInputStream(file)) {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
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
