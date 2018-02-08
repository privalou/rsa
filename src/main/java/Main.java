import files.FileChipher;
import files.KeyGenerator;
import rsa.PrivateKey;
import rsa.PublicKey;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        KeyGenerator.generate("test");
        PublicKey publicKey = KeyGenerator.loadPublicKey("test.public");
        PrivateKey privateKey = KeyGenerator.loadPrivateKey("test.private");

        FileChipher.encrypt("data.txt", publicKey, 512);
        FileChipher.decrypt("data.txt.rsa", "result.txt", privateKey);
    }
}
