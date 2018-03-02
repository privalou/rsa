import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
   static final int PORT = 3222;
   public static void main(String[] args){

      try( ServerSocket serverSocket = new ServerSocket(PORT)){
         while (true){
            Socket clientSocket = serverSocket.accept();
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
