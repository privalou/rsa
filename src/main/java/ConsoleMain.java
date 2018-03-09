import rsa.PublicKey;
import services.NetServiceClient;

public class ConsoleMain {
    public static void main(String[] args) throws Exception {
        NetServiceClient netServiceClient = new NetServiceClient();
        PublicKey publicKey = netServiceClient.sendKeyRequest();
//        netServiceClient.sendFile("data.txt", publicKey,"Test");
    }
}
