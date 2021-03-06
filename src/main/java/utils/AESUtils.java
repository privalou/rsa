package utils;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * A simple utility class for easily encrypting and decrypting data using the AES algorithm.
 *
 * @author Chad Adams
 */
public class AESUtils {

    /**
     * The constant that denotes the algorithm being used.
     */
    private static final String algorithm = "AES";

    /**
     * The private constructor to prevent instantiation of this object.
     */
    private AESUtils() {

    }

    /**
     * The method that will encrypt data.
     *
     * @param secretKey The key used to encrypt the data.
     * @param data      The data to encrypt.
     * @return The encrypted data.
     */
    public static byte[] encrypt(SecretKey secretKey, byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * The method that will decrypt a piece of encrypted data.
     *
     * @param secretKey The key used to decrypt encrypted data.
     * @param encrypted The encrypted data.
     * @return The decrypted data.
     */
    public static byte[] decrypt(SecretKey secretKey, byte[] encrypted) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(encrypted);
        } catch (Exception ex) {

        }
        return null;
    }

}
