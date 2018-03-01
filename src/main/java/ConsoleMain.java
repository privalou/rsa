import files.FileChipher;
import files.KeyGenerator;
import rsa.PrivateKey;
import rsa.PublicKey;

public class ConsoleMain {
    public static void main(String[] args) throws Exception {
        KeyGenerator.generate("qwe");
        PublicKey publicKey = KeyGenerator.loadPublicKey("qwe.public");
        PrivateKey privateKey = KeyGenerator.loadPrivateKey("qwe.private");
        FileChipher.encrypt("testData.txt", publicKey, "Zhopqa");
        FileChipher.decrypt("testData.txt.aes", "testResult.txt", privateKey);

    }
}
