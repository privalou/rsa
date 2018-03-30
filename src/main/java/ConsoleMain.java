import files.KeyGenerator;
import org.apache.commons.codec.binary.Hex;
import rsa.PrivateKey;
import rsa.PublicKey;
import services.NetServiceClient;
import utils.AESUtils;
import utils.ESignatureUtils;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ConsoleMain {
    public static void main(String[] args) throws IOException {
//        File file = new File("data.txt");
//        try (InputStream inputStream = new FileInputStream(file)) {
//            MessageDigest digest = MessageDigest.getInstance("SHA-256");
//            byte[] buf = new byte[(int) file.length()];
//            while (inputStream.available() > 0) {
//                inputStream.read(buf);
//            }
//            byte[] hash = digest.digest(buf);
//            System.out.println(Hex.encodeHexString(hash));
//            KeyGenerator.generate("Electronic signature");
//            PublicKey publicKey = KeyGenerator.loadPublicKey("Electronic signature.public");
//            PrivateKey privateKey = KeyGenerator.loadPrivateKey("Electronic signature.private");
//            byte[] encHash = privateKey.encrypt(hash);
//            System.out.println(Hex.encodeHexString(encHash));
//            byte[] decHash = publicKey.decrypt(encHash);
//            System.out.println(Hex.encodeHexString(decHash));
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        NetServiceClient netServiceClient = new NetServiceClient();
        KeyGenerator.generate("eSignature");
        PublicKey publicKey = KeyGenerator.loadPublicKey("eSignature.public");
        PrivateKey privateKey = KeyGenerator.loadPrivateKey("eSignature.private");
        PublicKey aeskey = netServiceClient.sendKeyRequest();
        byte[] hash = ESignatureUtils.signFile("data.txt");
        System.out.println(Hex.encodeHexString(hash));
        byte[] enchHash = privateKey.encrypt(hash);
        netServiceClient.sendBytes(enchHash,publicKey,"data.txt", aeskey, "Test");
    }
}
