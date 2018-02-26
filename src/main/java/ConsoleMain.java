import files.KeyGenerator;
import rsa.PrivateKey;
import rsa.PublicKey;
import utils.AESUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;

public class ConsoleMain {
    public static void main(String[] args) throws Exception {
        KeyGenerator.generate("new");
        PublicKey publicKey = KeyGenerator.loadPublicKey("new.public");
        PrivateKey privateKey = KeyGenerator.loadPrivateKey("new.private");
        byte[] message = "Hello world!".getBytes("UTF-8");
        byte[] key = "Best key".getBytes("UTF-8");

        System.out.println("Original key and message are : " + new String(message) + " , " + new String(key));

        key = Arrays.copyOf(key, 16);

        SecretKey secretKey = new SecretKeySpec(key, "AES");

        System.out.println("Secret key text from AES: ");

        System.out.println(bytesToHex(secretKey.getEncoded()));

        System.out.println("Encrypted data: ");

//        Cipher cipher = Cipher.getInstance("AES");
//        cipher.init(Cipher.ENCRYPT_MODE,secretKey);
//        byte[] encData = cipher.doFinal(message);

        byte[] encData = AESUtils.encrypt(secretKey, message);

        System.out.println(bytesToHex(encData));

        System.out.println("Encrypted key is: ");

        byte[] encKey = publicKey.encrypt(secretKey.getEncoded());

        System.out.println(bytesToHex(encKey));

        System.out.println("Decrypted key is: ");

        byte[] decKey = privateKey.decrypt(encKey);

        System.out.println(bytesToHex(decKey));

        System.out.println("Secret key text from AES after decryption: ");

        SecretKey newSecretkey = new SecretKeySpec(decKey, "AES");;

        System.out.println(newSecretkey.getEncoded().hashCode());

        System.out.println("Decrypted data: ");

        byte[] decData = AESUtils.decrypt(newSecretkey, encData);

        System.out.println(bytesToHex(decData));

        System.out.println(new String(decData));

    }
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
