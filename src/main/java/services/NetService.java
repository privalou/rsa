package services;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class NetService {

    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public NetService(InetAddress inetAddress){
        try {
            this.socket = new Socket(inetAddress, 6666);
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection");
            System.exit(-1);
        }
    }
}
