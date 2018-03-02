import files.KeyGenerator;
import rsa.PrivateKey;
import rsa.PublicKey;
import services.NetServiceServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static final int PORT = 3222;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            PublicKey publicKey = KeyGenerator.loadPublicKey("qwe.public");
            PrivateKey privateKey = KeyGenerator.loadPrivateKey("qwe.private");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                NetServiceServer netServiceServer = new NetServiceServer(clientSocket, publicKey, privateKey);
                netServiceServer.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
