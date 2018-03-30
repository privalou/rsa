import files.KeyGenerator;
import rsa.PrivateKey;
import rsa.PublicKey;
import services.NetServiceServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static final int PORT = 9999;

    public static void main(String[] args) throws IOException {
        KeyGenerator.generate("aes");
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.err.println("Could not listen on port:" + PORT + "");
        }
        PublicKey publicKey = KeyGenerator.loadPublicKey("aes.public");
        PrivateKey privateKey = KeyGenerator.loadPrivateKey("aes.private");
        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                NetServiceServer netServiceServer = new NetServiceServer(clientSocket, publicKey, privateKey);
                netServiceServer.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverSocket.close();
    }
}


