package view.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;

import javafx.event.ActionEvent;
import rsa.PublicKey;
import services.NetServiceClient;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.ResourceBundle;

public class ControllerMain implements Initializable {
    public Button transferButton;
    public TextField ipField;
    public TextField fileField;
    private Desktop desktop = Desktop.getDesktop();

    @FXML
    private Button findFileButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void findFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        fileField.appendText(file.getName());
        if (file != null) {
            openFile(file);
        }
    }

    private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void transferFile(ActionEvent event) {
        String serverIp = ipField.getText();
        try {
            InetAddress inetAddress = InetAddress.getByName(serverIp);
            Socket socket = new Socket(inetAddress, 9999);
            NetServiceClient netServiceClient = new NetServiceClient(socket);
            PublicKey publicKey = netServiceClient.sendKeyRequest();
            netServiceClient.sendFile(fileField.getText(), publicKey, "Test");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
