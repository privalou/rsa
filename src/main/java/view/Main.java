package view;

import files.FileChipher;
import files.KeyGenerator;
import javafx.application.Application;
import javafx.stage.Stage;
import rsa.PrivateKey;
import rsa.PublicKey;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        KeyGenerator.generate("test");
        PublicKey publicKey = KeyGenerator.loadPublicKey("test.public");
        PrivateKey privateKey = KeyGenerator.loadPrivateKey("test.private");

        FileChipher.encrypt("data.txt", publicKey, 512);
        FileChipher.decrypt("data.txt.rsa", "result.txt", privateKey);
    }
}
