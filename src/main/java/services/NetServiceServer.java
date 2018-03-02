package services;

import files.FileChipher;
import rsa.PrivateKey;
import rsa.PublicKey;

import java.io.*;
import java.net.Socket;

public class NetServiceServer extends Thread {

    private Socket clientSocket;
    private boolean running = true;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public NetServiceServer(Socket socket, PublicKey publicKey, PrivateKey privateKey) {
        this.clientSocket = socket;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    @Override
    public void run() {
        try {
            final InputStream inputStream = clientSocket.getInputStream();
            ObjectInputStream in = new ObjectInputStream(inputStream);
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            while (running) {
                String clientCommand = in.readUTF();
                switch (clientCommand.toLowerCase()) {
                    case Messages.SENDING_CIPHERED_FILE:
                        File file = (File) in.readObject();
                        file.length();
                        FileChipher.decrypt(file.getName(), "result.txt", privateKey);
                        break;
                    case Messages.PUBLIC_KEY_REQUEST:
                        out.writeObject(publicKey);
                        out.flush();
                        break;
                    default:
                        Thread.sleep(1000);
                        break;
                }
            }
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection");
            System.exit(-1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
