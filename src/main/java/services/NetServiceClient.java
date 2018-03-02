package services;

import files.FileChipher;
import rsa.PublicKey;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetServiceClient {
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public NetServiceClient() {
        try {
            this.socket = new Socket("localhost", 3222);
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection");
            System.exit(-1);
        }
    }

    public void sendFile(String filename, PublicKey publicKey, String aesKey) {
        FileChipher.encrypt(filename, publicKey, aesKey);
        try {
            outputStream.writeUTF(Messages.SENDING_CIPHERED_FILE);
            File file = new File(filename + ".aes");
            outputStream.writeObject(file);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
