import files.KeyGenerator;
import org.apache.commons.codec.binary.Hex;
import rsa.PrivateKey;
import rsa.PublicKey;
import services.NetServiceClient;
import utils.ESignatureUtils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class ConsoleMain {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
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
