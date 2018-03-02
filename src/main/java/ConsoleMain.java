import files.FileChipher;
import files.KeyGenerator;
import rsa.PrivateKey;
import rsa.PublicKey;
import services.NetServiceClient;

public class ConsoleMain {
    public static void main(String[] args) throws Exception {
        KeyGenerator.generate("qwe");
        PublicKey publicKey = KeyGenerator.loadPublicKey("qwe.public");
        PrivateKey privateKey = KeyGenerator.loadPrivateKey("qwe.private");
//        FileChipher.encrypt("data.txt", publicKey, "Zhopqa");
//        FileChipher.decrypt("data.txt.aes", "result.txt", privateKey);
        NetServiceClient netServiceClient = new NetServiceClient();
        netServiceClient.sendFile("data.txt", publicKey,"Zhopqa");

    }
}
