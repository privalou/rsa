package services;

import files.FileChipher;
import rsa.PrivateKey;
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
            this.socket = new Socket("localhost", 9999);
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection");
            System.exit(-1);
        }
    }

    public NetServiceClient(Socket socket) {
        try {
            this.socket = socket;
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

    public void sendBytes(String filename, PublicKey publicKey, String aesKey) {
        FileChipher.encrypt(filename, publicKey, aesKey);
        try {
            outputStream.writeUTF(Messages.SENDING_BYTES);
            outputStream.flush();
            File file = new File(filename + ".aes");
            long fileSize = file.length();
            outputStream.writeLong(fileSize);
            outputStream.flush();
            InputStream inputStream = new FileInputStream(file);
            byte[] buf = new byte[(int) file.length()];
            while (inputStream.available() > 0) {
                inputStream.read(buf);
            }
            outputStream.write(buf);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendBytes(byte[] encryptedHash, PublicKey esKey, String fileName, PublicKey aesPK, String aesKey) {
        FileChipher.encrypt(fileName, aesPK, aesKey);
        try {
            outputStream.writeUTF(Messages.SENDING_SIGNED_MESSAGE);
            outputStream.flush();
            outputStream.write(encryptedHash.length);
            outputStream.flush();
            outputStream.write(encryptedHash);
            outputStream.flush();
            outputStream.writeObject(esKey);
            outputStream.flush();
            outputStream.writeUTF(new File(fileName).getName());
            outputStream.flush();
            File file = new File(fileName + ".aes");
            long fileSize = file.length();
            outputStream.writeLong(fileSize);
            outputStream.flush();
            InputStream inputStream = new FileInputStream(file);
            byte[] buf = new byte[(int) file.length()];
            while (inputStream.available() > 0) {
                inputStream.read(buf);
            }
            outputStream.write(buf);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PublicKey sendKeyRequest() {
        PublicKey publicKey = null;
        try {
            outputStream.writeUTF(Messages.PUBLIC_KEY_REQUEST);
            outputStream.flush();
            publicKey = (PublicKey) inputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return publicKey;
    }
}
